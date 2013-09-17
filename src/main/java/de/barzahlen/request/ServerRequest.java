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
package de.barzahlen.request;

import de.barzahlen.Barzahlen;
import de.barzahlen.configuration.Configuration;
import de.barzahlen.enums.RequestErrorCode;
import de.barzahlen.exceptions.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parent class for all server requests.
 *
 * @author Jesus Javier Nuno Garcia
 */
public abstract class ServerRequest extends Barzahlen {

	private static final Logger logger = LoggerFactory.getLogger(ServerRequest.class);

	/**
	 * Keeps track of the errors with the server requests.
	 */
	public static RequestErrorCode BARZAHLEN_REQUEST_ERROR_CODE;

	/**
	 * Barzahlen transaction state for pending orders
	 */
	public static final String BARZAHLEN_ORDER_PENDING;

	/**
	 * Barzahlen transaction state for paid orders
	 */
	public static final String BARZAHLEN_ORDER_PAID;

	/**
	 * Barzahlen transaction state for expired orders
	 */
	public static final String BARZAHLEN_ORDER_EXPIRED;

	/**
	 * Barzahlen refund state for pending refunds
	 */
	public static final String BARZAHLEN_REFUND_ORDER_PENDING;

	/**
	 * Barzahlen refund state for completed refunds
	 */
	public static final String BARZAHLEN_REFUND_ORDER_COMPLETED;

	/**
	 * Barzahlen refund state for expired refunds
	 */
	public static final String BARZAHLEN_REFUND_ORDER_EXPIRED;

	/**
	 * Indicates whether the request should be sent again in case of error or
	 * not.
	 */
	public static boolean BARZAHLEN_REQUEST_RETRY;

	// Initializes the static variables of this class.
	static {
		BARZAHLEN_ORDER_PENDING = "pending";
		BARZAHLEN_ORDER_PAID = "paid";
		BARZAHLEN_ORDER_EXPIRED = "expired";
		BARZAHLEN_REFUND_ORDER_PENDING = "refund_pending";
		BARZAHLEN_REFUND_ORDER_COMPLETED = "refund_completed";
		BARZAHLEN_REFUND_ORDER_EXPIRED = "refund_expired";
		BARZAHLEN_REQUEST_RETRY = true;
	}

	/**
	 * Constructor with parameters
	 */
	public ServerRequest(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Takes a string with the parameters for a post request and formats it in
	 * something more human readable.
	 *
	 * @param parameters The post parameters
	 * @return The parameters formatted
	 */
	public static String formatReadableParameters(String parameters) {
		String[] params = parameters.replace("&", ", &").split("&");

		String finalParams = "{";
		for (String param : params) {
			finalParams += param.replace("=", " => ");
		}

		return finalParams + "}";
	}

	/**
	 * Reacts to an error when a server request is executed. Does also the
	 * "retry" feature, that is, retries the requests when an error occur.
	 *
	 * @param targetUrl     The url to retry the request
	 * @param urlParameters The parameters for the url to retry the request
	 * @param errorCode     The error code
	 * @param errorMessage1 The error message to show when first request fails
	 * @param errorMessage2 The error message to show when second request fails
	 * @return True if the request is successful. An exception is thrown otherwise
	 * @throws Exception
	 */
	protected boolean errorAction(String targetUrl, String urlParameters, RequestErrorCode errorCode, String errorMessage1, String errorMessage2) throws Exception {
		if (ServerRequest.BARZAHLEN_REQUEST_RETRY) {
			logger.warn("Request to Barzahlen failed, try to retry");
			ServerRequest.BARZAHLEN_REQUEST_RETRY = false;

			if (isSandboxMode()) {
				logger.debug("Retrying call to {}", targetUrl);
				logger.debug("Error message 1 {}", errorMessage1);
				logger.debug("Error message 2 {}", errorMessage2);
			}

			return executeServerRequest(targetUrl, urlParameters);
		}

		// retry did not help
		ServerRequest.BARZAHLEN_REQUEST_RETRY = true;
		BARZAHLEN_REQUEST_ERROR_CODE = errorCode;

		throw new RequestException(errorMessage2);
	}

	/**
	 * Builds all the parameters necessary for the refund request.
	 *
	 * @param parameters The parameters to assemble
	 * @return The parameters already assembled, ready for the post request.
	 */
	protected String assembleParameters(Map<String, String> parameters) {
		HashMap<String, String> parameterMap = new HashMap<String, String>(parameters);

		parameterMap.put("shop_id", getShopId());
		parameterMap.put("payment_key", getPaymentKey());

		String hash = createHash(getParametersTemplate(), parameterMap);

		parameterMap.remove("payment_key");

		StringBuilder parametersString = new StringBuilder(createParametersString(getParametersTemplate(), parameterMap));

		parametersString.append("&");
		parametersString.append("hash");
		parametersString.append("=");
		parametersString.append(hash);

		return parametersString.toString();
	}

	/**
	 * Returns an string array with names of variables that could be used
	 * for the request, in the order where they will be used to generate the hash
	 *
	 * @return List
	 */
	protected abstract List<String> getParametersTemplate();

	/**
	 * Executes a http request via post
	 *
	 * @param targetUrl     The url to make the request to
	 * @param urlParameters The parameters of the http post request
	 * @throws Exception
	 */
	protected abstract boolean executeServerRequest(String targetUrl, String urlParameters) throws Exception;

	/**
	 * Compares the hash of the data received from the server to the one inside
	 * the object.
	 *
	 * @return True if the hashes are the same. False otherwise.
	 */
	protected abstract boolean compareHashes();
}
