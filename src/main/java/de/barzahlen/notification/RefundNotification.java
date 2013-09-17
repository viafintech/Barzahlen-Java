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
	protected PrintWriter outStream;

	/**
	 * The transaction state retrieved from the server request
	 */
	protected String state;

	/**
	 * The refund transaction identifier retrieved from the server request
	 */
	protected String refundTransactionId;

	/**
	 * The origin transaction identifier retrieved from the server request
	 */
	protected String originTransactionId;

	/**
	 * The shop identifier retrieved from the server request
	 */
	protected String shopId;

	/**
	 * The customer email retrieved from the server request
	 */
	protected String customerEmail;

	/**
	 * The transaction amount retrieved from the server request
	 */
	protected String amount;

	/**
	 * The currency retrieved from the server request
	 */
	protected String currency;

	/**
	 * The origin order identifier from the server request
	 */
	protected String originOrderId;

	/**
	 * The custom variable (0) retrieved from the server request
	 */
	protected String customVar0;

	/**
	 * The custom variable (1) retrieved from the server request
	 */
	protected String customVar1;

	/**
	 * The custom variable (2) retrieved from the server request
	 */
	protected String customVar2;

	/**
	 * The hash retrieved from the server request
	 */
	protected String hash;

	/**
	 * Constructor with parameters
	 *
	 * @throws IOException
	 */
	public RefundNotification(NotificationConfiguration notificationConfiguration) throws IOException {
		super(notificationConfiguration);

		this.outStream = this.getResponse().getWriter();
		this.state = this.getRequest().getParameter(STATE);
		this.refundTransactionId = this.getRequest().getParameter(REFUND_TRANSACTION_ID);
		this.originTransactionId = this.getRequest().getParameter(ORIGIN_TRANSACTION_ID);
		this.shopId = this.getRequest().getParameter(SHOP_ID);
		this.customerEmail = this.getRequest().getParameter(CUSTOMER_EMAIL);
		this.amount = this.getRequest().getParameter(AMOUNT);
		this.currency = this.getRequest().getParameter(CURRENCY);
		this.originOrderId = this.getRequest().getParameter(ORIGIN_ORDER_ID);
		this.customVar0 = this.getRequest().getParameter(CUSTOM_VAR_0);
		this.customVar1 = this.getRequest().getParameter(CUSTOM_VAR_1);
		this.customVar2 = this.getRequest().getParameter(CUSTOM_VAR_2);
		this.hash = this.getRequest().getParameter(HASH);
	}

	@Override
	public boolean checkNotification(Map<String, String> parameters) throws Exception {
		String message = this.state + ";" + this.refundTransactionId + ";" + this.originTransactionId + ";" + this.shopId + ";"
				+ this.customerEmail + ";" + this.amount + ";" + this.currency + ";" + this.originOrderId + ";" + this.customVar0
				+ ";" + this.customVar1 + ";" + this.customVar2 + ";" + this.getNotificationKey();

		this.getResponse().setStatus(HttpServletResponse.SC_OK);

		// Check if everything is ok
		if (!this.getShopId().equals(this.shopId)) {
			if (isSandboxMode()) {
				logger.debug("Data received is not correct (shop id is incorrect).");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.SHOP_ID_ERROR;
			this.outStream.println("Data received is not correct (shop id is incorrect).");
			throw new NotificationException("Data received is not correct (shop id is incorrect).");
		}

		if (!this.state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_COMPLETED) && !this.state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_EXPIRED)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.TRANSACTION_STATE_ERROR;
			this.outStream.println("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
			throw new NotificationException("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
		}

		if (!parameters.get("origin_transaction_id").equals(this.originTransactionId)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Origin transaction ID not correct.");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR;
			this.outStream.println("Data received in callback not correct: Origin transaction ID not correct.");
			throw new NotificationException("Data received in callback not correct: Origin transaction ID not correct.");
		}

		if (!parameters.get("origin_order_id").equals(this.originOrderId)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Origin order ID not correct.");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_ORIGIN_ORDER_ID_ERROR;
			this.outStream.println("Data received in callback not correct: Origin order ID not correct.");
			throw new NotificationException("Data received in callback not correct: Origin order ID not correct.");
		}

		if (!parameters.get("refund_state").equals(ServerRequest.BARZAHLEN_REFUND_ORDER_PENDING)) {
			if (isSandboxMode()) {
				logger.debug("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.BARZAHLEN_TRANSACTION_STATE_ERROR;
			this.outStream.println("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
			throw new NotificationException("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
		}

		if (!parameters.get("customer_email").equals(this.customerEmail)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Customer email not correct.");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.CUSTOMER_EMAIL_ERROR;
			this.outStream.println("Data received in callback not correct: Customer email not correct.");
			throw new NotificationException("Data received in callback not correct: Customer email not correct.");
		}

		if (!parameters.get("currency").equals(this.currency)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Currency not correct.");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.CURRENCY_ERROR;
			this.outStream.println("Data received in callback not correct: Currency not correct.");
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

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.AMOUNT_ERROR;
			this.outStream.println("Data received in callback not correct: Amount not correct.");
			throw new NotificationException("Data received in callback not correct: Amount not correct.");
		}

		if (!HashTools.getHash(message).equals(this.hash)) {
			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: Hash not correct.");
			}

			this.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			notificationErrorCode = NotificationErrorCode.HASH_ERROR;
			this.outStream.println("Data received in callback not correct: Hash not correct.");
			throw new NotificationException("Data received in callback not correct: Hash not correct.");
		}

		notificationErrorCode = NotificationErrorCode.SUCCESS;
		return true;

	}
}
