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

import de.barzahlen.Barzahlen;
import de.barzahlen.configuration.NotificationConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Parent class for all notifications
 *
 * @author Jesus Javier Nuno Garcia
 */
public abstract class Notification extends Barzahlen {

	/**
	 * Keeps track of the errors with the notifications.
	 */
	public static NotificationErrorCode BARZAHLEN_NOTIFICATION_ERROR_CODE;

	/**
	 * Constant for the "state" request parameter
	 */
	protected static final String STATE;

	/**
	 * Constant for the "transactionId" request parameter
	 */
	protected static final String TRANSACTION_ID;

	/**
	 * Constant for the "shopId" request parameter
	 */
	protected static final String SHOP_ID;

	/**
	 * Constant for the "customerEmail" request parameter
	 */
	protected static final String CUSTOMER_EMAIL;

	/**
	 * Constant for the "amount" request parameter
	 */
	protected static final String AMOUNT;

	/**
	 * Constant for the "currency" request parameter
	 */
	protected static final String CURRENCY;

	/**
	 * Constant for the "orderId" request parameter
	 */
	protected static final String ORDER_ID;

	/**
	 * Constant for the "refundTransactionId" request parameter
	 */
	protected static final String REFUND_TRANSACTION_ID;

	/**
	 * Constant for the "originTransactionId" request parameter
	 */
	protected static final String ORIGIN_TRANSACTION_ID;

	/**
	 * Constant for the "originOrderId" request parameter
	 */
	protected static final String ORIGIN_ORDER_ID;

	/**
	 * Constant for the "customVar0" request parameter
	 */
	protected static final String CUSTOM_VAR_0;

	/**
	 * Constant for the "customVar1" request parameter
	 */
	protected static final String CUSTOM_VAR_1;

	/**
	 * Constant for the "customVar2" request parameter
	 */
	protected static final String CUSTOM_VAR_2;

	/**
	 * Constant for the "hash" request parameter
	 */
	protected static final String HASH;

	static {
		STATE = "state";
		TRANSACTION_ID = "transaction_id";
		SHOP_ID = "shop_id";
		CUSTOMER_EMAIL = "customer_email";
		AMOUNT = "amount";
		CURRENCY = "currency";
		ORDER_ID = "order_id";
		REFUND_TRANSACTION_ID = "refund_transaction_id";
		ORIGIN_TRANSACTION_ID = "origin_transaction_id";
		ORIGIN_ORDER_ID = "origin_order_id";
		CUSTOM_VAR_0 = "custom_var_0";
		CUSTOM_VAR_1 = "custom_var_1";
		CUSTOM_VAR_2 = "custom_var_2";
		HASH = "hash";
	}

	/**
	 * The server request
	 */
	protected final HttpServletRequest request;

	/**
	 * The server response
	 */
	protected final HttpServletResponse response;

	/**
	 * Constructor with parameters
	 */
	public Notification(NotificationConfiguration notificationConfiguration) {
		super(notificationConfiguration);

		this.request = notificationConfiguration.getRequest();
		this.response = notificationConfiguration.getResponse();
	}

	/**
	 * Checks if the notification received from the server is valid.
	 *
	 * @param _parameters The parameters to be compared with the notification received.
	 *                    They should be "barzahlen_transaction_id",
	 *                    "barzahlen_transaction_state", "customer_email", "currency"
	 *                    (ISO 4217) and "amount" for PaymentNotification and
	 *                    "origin_transaction_id", "refund_state", "customer_email",
	 *                    "currency" (ISO 4217) and "amount" for RefundNotification.
	 * @return True if the notification is valid. Throws an exception otherwise.
	 * @throws Exception
	 */
	public abstract boolean checkNotification(HashMap<String, String> _parameters) throws Exception;
}
