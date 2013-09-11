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
import de.barzahlen.request.ServerRequest;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;

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
	protected PrintWriter outStream;

	/**
	 * The transaction state retrieved from the server request
	 */
	protected String _state;

	/**
	 * The transaction identifier retrieved from the server request
	 */
	protected String _transactionId;

	/**
	 * The shop identifier retrieved from the server request
	 */
	protected String _shopId;

	/**
	 * The customer email retrieved from the server request
	 */
	protected String _customerEmail;

	/**
	 * The transaction amount retrieved from the server request
	 */
	protected String _amount;

	/**
	 * The currency retrieved from the server request
	 */
	protected String _currency;

	/**
	 * The order identifier from the server request
	 */
	protected String _orderId;

	/**
	 * The custom variable (0) retrieved from the server request
	 */
	protected String _customVar0;

	/**
	 * The custom variable (1) retrieved from the server request
	 */
	protected String _customVar1;

	/**
	 * The custom variable (2) retrieved from the server request
	 */
	protected String _customVar2;

	/**
	 * The hash retrieved from the server request
	 */
	protected String _hash;

	/**
	 * Constructor with parameters
	 *
	 * @throws IOException
	 */
	public PaymentNotification(NotificationConfiguration notificationConfiguration) throws IOException {
		super(notificationConfiguration);

		this.outStream = this.response.getWriter();
		this._state = this.request.getParameter(STATE);
		this._transactionId = this.request.getParameter(TRANSACTION_ID);
		this._shopId = this.request.getParameter(SHOP_ID);
		this._customerEmail = this.request.getParameter(CUSTOMER_EMAIL);
		this._amount = this.request.getParameter(AMOUNT);
		this._currency = this.request.getParameter(CURRENCY);
		this._orderId = this.request.getParameter(ORDER_ID);
		this._customVar0 = this.request.getParameter(CUSTOM_VAR_0);
		this._customVar1 = this.request.getParameter(CUSTOM_VAR_1);
		this._customVar2 = this.request.getParameter(CUSTOM_VAR_2);
		this._hash = this.request.getParameter(HASH);
	}

	@Override
	public boolean checkNotification(HashMap<String, String> _parameters) throws Exception {
		String message = this._state + ";" + this._transactionId + ";" + this._shopId + ";" + this._customerEmail + ";" + this._amount
				+ ";" + this._currency + ";" + this._orderId + ";" + this._customVar0 + ";" + this._customVar1 + ";" + this._customVar2
				+ ";" + this.getNotificationKey();

		this.response.setStatus(HttpServletResponse.SC_OK);

		// Check if everything is ok
		if (getShopId().equals(this._shopId)) {

			if (this._state.equals(ServerRequest.BARZAHLEN_ORDER_PAID) || this._state.equals(ServerRequest.BARZAHLEN_ORDER_EXPIRED)) {

				if (_parameters.get("barzahlen_transaction_id").equals(this._transactionId)) {

					if (_parameters.get("barzahlen_transaction_state").equals(ServerRequest.BARZAHLEN_ORDER_PENDING)) {

						if (_parameters.get("customer_email").equals(this._customerEmail)) {

							if (_parameters.get("currency").equals(this._currency)) {
								String am = _parameters.get("amount");
								DecimalFormat df = new DecimalFormat("#.00");
								am = df.format(Double.valueOf(am)).replace(',', '.');
								String _am = df.format(Double.valueOf(this._amount)).replace(',', '.');

								if (am.equals(_am)) {

									if (HashTools.getHash(message).equals(this._hash)) {
										BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.SUCCESS;
										return true;
									}

									if (isSandboxMode()) {
										logger.debug("Data received in callback not correct: Hash not correct.");
									}

									this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.HASH_ERROR;
									this.outStream.println("Data received in callback not correct: Hash not correct.");
									throw new Exception("Data received in callback not correct: Hash not correct.");
								}

								if (isSandboxMode()) {
									logger.debug("Data received in callback not correct: Amount not correct.");
								}

								this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.AMOUNT_ERROR;
								this.outStream.println("Data received in callback not correct: Amount not correct.");
								throw new Exception("Data received in callback not correct: Amount not correct.");

							}

							if (isSandboxMode()) {
								logger.debug("Data received in callback not correct: Currency not correct.");
							}

							this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.CURRENCY_ERROR;
							this.outStream.println("Data received in callback not correct: Currency not correct.");
							throw new Exception("Data received in callback not correct: Currency not correct.");

						}

						if (isSandboxMode()) {
							logger.debug("Data received in callback not correct: Customer email not correct. (" + this._customerEmail + " vs " + _parameters.get("customer_email") + ")");
						}

						this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.CUSTOMER_EMAIL_ERROR;
						this.outStream.println("Data received in callback not correct: Customer email not correct.");
						throw new Exception("Data received in callback not correct: Customer email not correct.");

					}

					if (isSandboxMode()) {
						logger.debug("Database doesn't have the barzahlen transaction state set to \"pending\".");
					}

					this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.BARZAHLEN_TRANSACTION_STATE_ERROR;
					this.outStream.println("Database doesn't have the barzahlen transaction state set to \"pending\".");
					throw new Exception("Database doesn't have the barzahlen transaction state set to \"pending\".");

				}

				if (isSandboxMode()) {
					logger.debug("Data received in callback not correct: Transaction ID not correct.");
				}

				this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR;
				this.outStream.println("Data received in callback not correct: Transaction ID not correct.");
				throw new Exception("Data received in callback not correct: Transaction ID not correct.");

			}

			if (isSandboxMode()) {
				logger.debug("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");
			}

			this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.TRANSACTION_STATE_ERROR;
			this.outStream.println("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");
			throw new Exception("Data received in callback not correct: The transaction state is neither \"paid\" nor \"expired\".");

		}

		if (isSandboxMode()) {
			logger.debug("Data received is not correct (shop id is incorrect).");
		}

		this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.SHOP_ID_ERROR;
		this.outStream.println("Data received is not correct (shop id is incorrect).");
		throw new Exception("Data received is not correct (shop id is incorrect).");

	}
}
