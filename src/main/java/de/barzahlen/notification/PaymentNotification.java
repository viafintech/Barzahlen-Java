/**
 * Barzahlen Payment Module SDK
 *
 * NOTICE OF LICENSE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 *
 * @copyright Copyright (c) 2012 Zerebro Internet GmbH (http://www.barzahlen.de/)
 * @author Jesus Javier Nuno Garcia
 * @license http://opensource.org/licenses/GPL-3.0  GNU General Public License, version 3 (GPL-3.0)
 */
package de.barzahlen.notification;

import de.barzahlen.configuration.NotificationConfiguration;
import de.barzahlen.enums.NotificationErrorCode;
import de.barzahlen.exceptions.NotificationException;
import de.barzahlen.request.ServerRequest;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Class that implements the checking for the payment notification.
 *
 * @author Jesus Javier Nuno Garcia
 */
public final class PaymentNotification extends Notification {

	/**
	 * Log file for the logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaymentNotification.class);

	/**
	 * A print writer to show some output in the browser.
	 */
	private PrintWriter outStream;

	/**
	 * The transaction state retrieved from the server getRequest()
	 */
	private String state;

	/**
	 * The transaction identifier retrieved from the server getRequest()
	 */
	private String transactionId;

	/**
	 * The shop identifier retrieved from the server getRequest()
	 */
	private String shopId;

	/**
	 * The customer email retrieved from the server getRequest()
	 */
	private String customerEmail;

	/**
	 * The transaction amount retrieved from the server getRequest()
	 */
	private String amount;

	/**
	 * The currency retrieved from the server getRequest()
	 */
	private String currency;

	/**
	 * The order identifier from the server getRequest()
	 */
	private String orderId;

	/**
	 * The custom variable (0) retrieved from the server getRequest()
	 */
	private String customVar0;

	/**
	 * The custom variable (1) retrieved from the server getRequest()
	 */
	private String customVar1;

	/**
	 * The custom variable (2) retrieved from the server getRequest()
	 */
	private String customVar2;

	/**
	 * The hash retrieved from the server getRequest()
	 */
	private String hash;

	/**
	 * Constructor with parameters
	 *
	 * @throws IOException
	 */
	public PaymentNotification(NotificationConfiguration notificationConfiguration) throws IOException {
		super(notificationConfiguration);

		outStream = getResponse().getWriter();
		state = getRequest().getParameter(STATE);
		transactionId = getRequest().getParameter(TRANSACTION_ID);
		shopId = getRequest().getParameter(SHOP_ID);
		customerEmail = getRequest().getParameter(CUSTOMER_EMAIL);
		amount = getRequest().getParameter(AMOUNT);
		currency = getRequest().getParameter(CURRENCY);
		orderId = getRequest().getParameter(ORDER_ID);
		customVar0 = getRequest().getParameter(CUSTOM_VAR_0);
		customVar1 = getRequest().getParameter(CUSTOM_VAR_1);
		customVar2 = getRequest().getParameter(CUSTOM_VAR_2);
		hash = getRequest().getParameter(HASH);
	}

	@Override
	public boolean checkNotification(Map<String, String> parameters) throws NotificationException {
		String message = state + ";" + transactionId + ";" + shopId + ";" + customerEmail + ";" + amount
				+ ";" + currency + ";" + orderId + ";" + customVar0 + ";" + customVar1 + ";" + customVar2
				+ ";" + getNotificationKey();

		getResponse().setStatus(HttpServletResponse.SC_OK);

		// Check if everything is ok
		if (!getShopId().equals(shopId)) {
			if (isSandboxMode()) {
				logger.debug("Data received is not correct (shop id is incorrect).");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);

			notificationErrorCode = NotificationErrorCode.SHOP_ID_ERROR;

			outStream.println("Data received is not correct (shop id is incorrect).");
			throw new NotificationException("Data received is not correct (shop id is incorrect).");
		}

		if (!state.equals(ServerRequest.BARZAHLEN_ORDER_PAID) && !state.equals(ServerRequest.BARZAHLEN_ORDER_EXPIRED)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.TRANSACTION_STATE_ERROR;
			outStream.println("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");
			throw new NotificationException("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");
		}

		if (!parameters.get("barzahlen_transaction_id").equals(transactionId)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Transaction ID not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR;
			outStream.println("Data received in callback not correct: Transaction ID not correct.");
			throw new NotificationException("Data received in callback not correct: Transaction ID not correct.");
		}

		if (!parameters.get("barzahlen_transaction_state").equals(ServerRequest.BARZAHLEN_ORDER_PENDING)) {
			if (isSandboxMode()) {
				logger.debug("Database doesn't have the barzahlen transaction state set to \"pending\".");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_TRANSACTION_STATE_ERROR;
			outStream.println("Database doesn't have the barzahlen transaction state set to \"pending\".");
			throw new NotificationException("Database doesn't have the barzahlen transaction state set to \"pending\".");
		}

		if (!parameters.get("customer_email").equals(customerEmail)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Customer email not correct. (" + customerEmail + " vs " + parameters.get("customer_email") + ")");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.CUSTOMER_EMAIL_ERROR;
			outStream.println("Data received in callback not correct: Customer email not correct.");
			throw new NotificationException("Data received in callback not correct: Customer email not correct.");
		}

		if (!parameters.get("currency").equals(currency)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Currency not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.CURRENCY_ERROR;
			outStream.println("Data received in callback not correct: Currency not correct.");
			throw new NotificationException("Data received in callback not correct: Currency not correct.");
		}

		String amount = parameters.get(Notification.AMOUNT);
		DecimalFormat df = new DecimalFormat("#.00");

		amount = df.format(Double.valueOf(amount)).replace(',', '.');
		String formattedAmount = df.format(Double.valueOf(this.amount)).replace(',', '.');

		if (!amount.equals(formattedAmount)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Amount not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.AMOUNT_ERROR;
			outStream.println("Data received in callback not correct: Amount not correct.");
			throw new NotificationException("Data received in callback not correct: Amount not correct.");
		}

		if (!HashTools.getHash(message).equals(hash)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Hash not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.HASH_ERROR;
			outStream.println("Data received in callback not correct: Hash not correct.");
			throw new NotificationException("Data received in callback not correct: Hash not correct.");
		}

		notificationErrorCode = NotificationErrorCode.SUCCESS;
		return true;
	}

	public String getState() {
		return state;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getCustomVar0() {
		return customVar0;
	}

	public String getCustomVar1() {
		return customVar1;
	}

	public String getCustomVar2() {
		return customVar2;
	}
}
