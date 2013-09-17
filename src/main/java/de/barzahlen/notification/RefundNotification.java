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
public final class RefundNotification extends Notification {

	/**
	 * Log file for the logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(RefundNotification.class);

	/**
	 * A print writer to show some output in the browser.
	 */
	private PrintWriter outStream;

	/**
	 * The transaction state retrieved from the server request
	 */
	private String state;

	/**
	 * The refund transaction identifier retrieved from the server request
	 */
	private String refundTransactionId;

	/**
	 * The origin transaction identifier retrieved from the server request
	 */
	private String originTransactionId;

	/**
	 * The shop identifier retrieved from the server request
	 */
	private String shopId;

	/**
	 * The customer email retrieved from the server request
	 */
	private String customerEmail;

	/**
	 * The transaction amount retrieved from the server request
	 */
	private String amount;

	/**
	 * The currency retrieved from the server request
	 */
	private String currency;

	/**
	 * The origin order identifier from the server request
	 */
	private String originOrderId;

	/**
	 * The custom variable (0) retrieved from the server request
	 */
	private String customVar0;

	/**
	 * The custom variable (1) retrieved from the server request
	 */
	private String customVar1;

	/**
	 * The custom variable (2) retrieved from the server request
	 */
	private String customVar2;

	/**
	 * The hash retrieved from the server request
	 */
	private String hash;

	/**
	 * Constructor with parameters
	 *
	 * @throws IOException
	 */
	public RefundNotification(NotificationConfiguration notificationConfiguration) throws IOException {
		super(notificationConfiguration);

		outStream = getResponse().getWriter();
		state = getRequest().getParameter(STATE);
		refundTransactionId = getRequest().getParameter(REFUND_TRANSACTION_ID);
		originTransactionId = getRequest().getParameter(ORIGIN_TRANSACTION_ID);
		shopId = getRequest().getParameter(SHOP_ID);
		customerEmail = getRequest().getParameter(CUSTOMER_EMAIL);
		amount = getRequest().getParameter(AMOUNT);
		currency = getRequest().getParameter(CURRENCY);
		originOrderId = getRequest().getParameter(ORIGIN_ORDER_ID);
		customVar0 = getRequest().getParameter(CUSTOM_VAR_0);
		customVar1 = getRequest().getParameter(CUSTOM_VAR_1);
		customVar2 = getRequest().getParameter(CUSTOM_VAR_2);
		hash = getRequest().getParameter(HASH);
	}

	@Override
	public boolean checkNotification(Map<String, String> parameters) throws Exception {
		String message = state + ";" + refundTransactionId + ";" + originTransactionId + ";" + shopId + ";"
				+ customerEmail + ";" + amount + ";" + currency + ";" + originOrderId + ";" + customVar0
				+ ";" + customVar1 + ";" + customVar2 + ";" + getNotificationKey();

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

		if (!state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_COMPLETED) && !state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_EXPIRED)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.TRANSACTION_STATE_ERROR;
			outStream.println("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
			throw new NotificationException("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
		}

		if (!parameters.get("origin_transaction_id").equals(originTransactionId)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Origin transaction ID not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR;
			outStream.println("Data received in callback not correct: Origin transaction ID not correct.");
			throw new NotificationException("Data received in callback not correct: Origin transaction ID not correct.");
		}

		if (!parameters.get("origin_order_id").equals(originOrderId)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Origin order ID not correct.");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_ORIGIN_ORDER_ID_ERROR;
			outStream.println("Data received in callback not correct: Origin order ID not correct.");
			throw new NotificationException("Data received in callback not correct: Origin order ID not correct.");
		}

		if (!parameters.get("refund_state").equals(ServerRequest.BARZAHLEN_REFUND_ORDER_PENDING)) {
			if (isSandboxMode()) {
				logger.debug("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
			}

			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_TRANSACTION_STATE_ERROR;
			outStream.println("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
			throw new NotificationException("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
		}

		if (!parameters.get("customer_email").equals(customerEmail)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Customer email not correct.");
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
		String formattedAmount = df.format(Double.valueOf(amount)).replace(',', '.');

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

	public String getRefundTransactionId() {
		return refundTransactionId;
	}

	public String getOriginTransactionId() {
		return originTransactionId;
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

	public String getOriginOrderId() {
		return originOrderId;
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
