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

import de.barzahlen.logging.BarzahlenAppender;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Parent class for all the SDK. Stores the variables shared among all classes.
 *
 * @author Jesus Javier Nuno Garcia
 */
public class Barzahlen {

	/**
	 * Indicates whether it is sandbox or debug mode.
	 */
	protected static SandboxDebugMode sandboxDebugMode;

	/**
	 * Indicates whether the log detail mode (debugging) is enabled (enabled by
	 * default).
	 */
	protected static boolean BARZAHLEN_DEBUGGING_MODE;

	/**
	 * The Url to send the create transaction request
	 */
	protected static String BARZAHLEN_CREATE_URL;

	/**
	 * The Url for the resend email request
	 */
	protected static String BARZAHLEN_RESEND_EMAIL_URL;

	/**
	 * The Url to send the request
	 */
	protected static String BARZAHLEN_REFUND_URL;

	/**
	 * The Url to send the request
	 */
	protected static String BARZAHLEN_UPDATE_URL;

	/**
	 * The Url to send the cancel request
	 */
	protected static String BARZAHLEN_CANCEL_URL;

	static {
		BARZAHLEN_CREATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/create";
		BARZAHLEN_RESEND_EMAIL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/resend_email";
		BARZAHLEN_REFUND_URL = "https://api-sandbox.barzahlen.de/v1/transactions/refund";
		BARZAHLEN_UPDATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/update";
		BARZAHLEN_CANCEL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/cancel";
		BARZAHLEN_DEBUGGING_MODE = true;
	}

	/**
	 * Enables either sandbox or debug mode for requests.
	 */
	public static void setDebuggingMode(boolean mode) {
		if (mode) {
			BARZAHLEN_DEBUGGING_MODE = true;
		} else {
			BARZAHLEN_DEBUGGING_MODE = false;
		}
	}

	/**
	 * Returns the sandbox or debug mode
	 *
	 * @return the sandboxDebugMode
	 */
	public static SandboxDebugMode getSandboxDebugMode() {
		return sandboxDebugMode;
	}

	/**
	 * Gets the debugging mode value
	 *
	 * @return the debugging mode value
	 */
	public static boolean getDebuggingMode() {
		return BARZAHLEN_DEBUGGING_MODE;
	}

	/**
	 * The Barzahlen log appender
	 */
	protected BarzahlenAppender logAppender;

	/**
	 * The shop identifier.
	 */
	protected String shopId;

	/**
	 * The payment key.
	 */
	protected String paymentKey;

	/**
	 * The notification key.
	 */
	protected String notificationKey;

	/**
	 * Default constructor. Initializes the log appender.
	 */
	public Barzahlen() {
		this.logAppender = new BarzahlenAppender();
	}

	/**
	 * Enables either sandbox or debug mode for requests.
	 */
	public static void setSandboxDebugMode(SandboxDebugMode mode) {
		if (mode == SandboxDebugMode.SANDBOX) {
			sandboxDebugMode = SandboxDebugMode.SANDBOX;
			BARZAHLEN_CREATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/create";
			BARZAHLEN_RESEND_EMAIL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/resend_email";
			BARZAHLEN_REFUND_URL = "https://api-sandbox.barzahlen.de/v1/transactions/refund";
			BARZAHLEN_UPDATE_URL = "https://api-sandbox.barzahlen.de/v1/transactions/update";
			BARZAHLEN_CANCEL_URL = "https://api-sandbox.barzahlen.de/v1/transactions/cancel";
		} else if (mode == SandboxDebugMode.DEBUG) {
			sandboxDebugMode = SandboxDebugMode.DEBUG;
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
	 * @param template
	 * @param parameters
	 * @return The SHA512 hash
	 */
	public static String createHash(String[] template, HashMap<String, String> parameters) {
		StringBuilder message = new StringBuilder();

		for (String value : template) {
			message.append(parameters.get(value));
			message.append(";");
		}

		message.deleteCharAt(message.length() - 1);

		return calculateHash(message.toString());
	}

	/**
	 * Creates the SHA512 hash of a message.
	 *
	 * @param message The message to create the hash from
	 * @return The SHA512 hash
	 */
	public static String calculateHash(String message) {
		MessageDigest md;
		String out = "";

		try {
			md = MessageDigest.getInstance("SHA-512");

			md.update(message.getBytes());
			byte[] mb = md.digest();

			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp).byteValue());
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}

		} catch (NoSuchAlgorithmException e) {
			//
		}

		return out;
	}


	/**
	 * Creates parameter list for url from list of strings
	 *
	 * @param template
	 * @param parameters
	 * @return parameters for url
	 */
	public static String createParametersString(String[] template, HashMap<String, String> parameters) {
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

	/**
	 * Set all parameters
	 *
	 * @param _sandbox         True if wanted sandbox mode to be enabled
	 * @param _shopId          The shop identifier
	 * @param _paymentKey      The payment key
	 * @param _notificationKey The notification key
	 */
	public void setParameters(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
		if (_sandbox) {
			sandboxDebugMode = SandboxDebugMode.SANDBOX;
			setSandboxDebugMode(sandboxDebugMode);
		} else {
			sandboxDebugMode = SandboxDebugMode.DEBUG;
			setSandboxDebugMode(sandboxDebugMode);
		}

		this.shopId = _shopId;
		this.paymentKey = _paymentKey;
		this.notificationKey = _notificationKey;
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
	 * Sets a new shop id
	 *
	 * @param _shopId the shopId to set
	 */
	public void setShopId(String _shopId) {
		this.shopId = _shopId;
	}

	/**
	 * Sets a new payment key
	 *
	 * @param _paymentKey the paymentKey to set
	 */
	public void setPaymentKey(String _paymentKey) {
		this.paymentKey = _paymentKey;
	}

	/**
	 * Sets a new notification key
	 *
	 * @param _notificationKey the notificationKey to set
	 */
	public void setNotificationKey(String _notificationKey) {
		this.notificationKey = _notificationKey;
	}
}
