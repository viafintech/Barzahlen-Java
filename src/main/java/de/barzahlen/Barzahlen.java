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
package de.barzahlen;

import de.barzahlen.configuration.Configuration;
import de.barzahlen.configuration.NotificationConfiguration;
import de.barzahlen.tools.HashTools;

import java.util.List;
import java.util.Map;

/**
 * Parent class for all the SDK. Stores the variables shared among all classes.
 *
 * @author Jesus Javier Nuno Garcia
 */
public class Barzahlen {

	/**
	 * The Url to send the create transaction request
	 */
	public static String BARZAHLEN_CREATE_URL;

	/**
	 * The Url for the resend email request
	 */
	public static String BARZAHLEN_RESEND_EMAIL_URL;

	/**
	 * The Url to send the request
	 */
	public static String BARZAHLEN_REFUND_URL;

	/**
	 * The Url to send the request
	 */
	public static String BARZAHLEN_UPDATE_URL;

	/**
	 * The Url to send the cancel request
	 */
	public static String BARZAHLEN_CANCEL_URL;

	static {
		BARZAHLEN_CREATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/create";
		BARZAHLEN_RESEND_EMAIL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/resend_email";
		BARZAHLEN_REFUND_URL = "https://api-sandbox.barzahlen.de/v1/transactions/refund";
		BARZAHLEN_UPDATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/update";
		BARZAHLEN_CANCEL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/cancel";
	}

	/**
	 *
	 */
	private Configuration configuration = null;

	/**
	 *
	 */
	private NotificationConfiguration notificationConfiguration = null;

	/**
	 *
	 */
	private boolean sandboxMode;

	/**
	 * The shop identifier.
	 */
	private String shopId;

	/**
	 * The payment key.
	 */
	private String paymentKey;

	/**
	 * The notification key.
	 */
	private String notificationKey;

	/**
	 * Default constructor. Initializes the log appender.
	 */
	public Barzahlen(Configuration configuration) {
		this.configuration = configuration;
		this.shopId = configuration.getShopId();
		this.paymentKey = configuration.getPaymentKey();
		this.notificationKey = configuration.getNotificationKey();

		setSandboxMode(configuration.isSandBoxMode());
	}

	public Barzahlen(NotificationConfiguration notificationConfiguration) {
		this.notificationConfiguration = notificationConfiguration;

		shopId = notificationConfiguration.getShopId();
		paymentKey = notificationConfiguration.getPaymentKey();
		notificationKey = notificationConfiguration.getNotificationKey();
	}

	/**
	 * Enables either sandbox or debug mode for requests.
	 */
	private void setSandboxMode(boolean mode) {
		sandboxMode = mode;

		if (sandboxMode) {
			BARZAHLEN_CREATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/create";
			BARZAHLEN_RESEND_EMAIL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/resend_email";
			BARZAHLEN_REFUND_URL = "https://api-sandbox.barzahlen.de/v1/transactions/refund";
			BARZAHLEN_UPDATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/update";
			BARZAHLEN_CANCEL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/cancel";
		} else {
			BARZAHLEN_CREATE_URL = "https://api.barzahlen.de/v1/transactions/create";
			BARZAHLEN_RESEND_EMAIL_URL = "https://api.barzahlen.de/v1/transactions/resend_email";
			BARZAHLEN_REFUND_URL = "https://api.barzahlen.de/v1/transactions/refund";
			BARZAHLEN_UPDATE_URL = "https://api.barzahlen.de/v1/transactions/update";
			BARZAHLEN_CANCEL_URL = "https://api.barzahlen.de/v1/transactions/cancel";
		}
	}

	/**
	 * Creates hash from parameters list, order like in the template
	 *
	 * @param template   Ordered string array with all fields
	 * @param parameters Parameters mapped to correct field
	 * @return SHA512 hash as string
	 */
	public String createHash(List<String> template, Map<String, String> parameters) {
		StringBuilder message = new StringBuilder();

		for (String value : template) {
			String parameter = parameters.get(value);

			if (parameter != null) {
				message.append(parameter);
			}

			message.append(";");
		}

		message.deleteCharAt(message.length() - 1);

		return HashTools.getHash(message.toString());
	}

	/**
	 * Creates parameter list for url from list of strings
	 *
	 * @param template   Ordered string array with all fields
	 * @param parameters Parameters mapped to correct field
	 * @return parameters for url
	 */
	public String createParametersString(List<String> template, Map<String, String> parameters) {
		StringBuilder parametersString = new StringBuilder();

		for (String value : template) {
			if (parameters.containsKey(value)) {
				parametersString.append(value);
				parametersString.append("=");
				parametersString.append(parameters.get(value));
				parametersString.append("&");
			}
		}

		parametersString.deleteCharAt(parametersString.length() - 1);

		return parametersString.toString();
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns the shop id
	 *
	 * @return the shopId
	 */
	public String getShopId() {
		return this.shopId;
	}

	/**
	 * Returns the payment key
	 *
	 * @return the paymentKey
	 */
	public String getPaymentKey() {
		return this.paymentKey;
	}

	/**
	 * Returns the notification key
	 *
	 * @return the notificationKey
	 */
	public String getNotificationKey() {
		return this.notificationKey;
	}

	/**
	 * Returns the sandbox or debug mode
	 *
	 * @return the sandboxDebugMode
	 */
	public boolean isSandboxMode() {
		return sandboxMode;
	}
}
