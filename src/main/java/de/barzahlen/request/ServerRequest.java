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
 * @copyright   Copyright (c) 2012 Zerebro Internet GmbH (http://www.barzahlen.de/)
 * @author      Jesus Javier Nuno Garcia
 * @license     http://opensource.org/licenses/GPL-3.0  GNU General Public License, version 3 (GPL-3.0)
 */
package de.barzahlen.request;

import de.barzahlen.Barzahlen;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Parent class for all server requests.
 * 
 * @author Jesus Javier Nuno Garcia
 */
public abstract class ServerRequest extends Barzahlen {

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
     * Default constructor
     */
    public ServerRequest() {
        super();
    }

    /**
     * Constructor with parameters
     * 
     * @param _sandbox
     *            True if wanted sandbox mode to be enabled
     * @param _shopId
     *            The shop identifier
     * @param _paymentKey
     *            The payment key
     * @param _notificationKey
     *            The notification key
     */
    public ServerRequest(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
        super();
        setParameters(_sandbox, _shopId, _paymentKey, _notificationKey);
        setDebuggingMode(true);
    }

    /**
     * Takes a string with the parameters for a post request and formats it in
     * something more human readable.
     * 
     * @param _parameters
     *            The post parameters
     * @return The parameters formatted
     */
    public static String formatReadableParameters(String _parameters) {
        String[] params = _parameters.replace("&", ", &").split("&");

        int length = params.length;
        String finalParams = "{";

        for (int i = 0; i < length; i++) {
            params[i] = params[i].replace("=", " => ");
            finalParams += params[i];
        }

        return finalParams + "}";
    }

    /**
     * Reacts to an error when a server request is executed. Does also the
     * "retry" feature, that is, retries the requests when an error occur.
     * 
     * @param _targetURL
     *            The url to retry the request
     * @param _urlParameters
     *            The parameters for the url to retry the request
     * @param _errorCode
     *            The error code
     * @param _logger
     *            The log, in order to log details when debugging is enabled
     * @param _errorMessage1
     *            The error message to show when first request fails
     * @param _errorMessage2
     *            The error message to show when second request fails
     * @return True if the request is successful. An exception is thrown
     *         otherwise
     * @throws Exception
     */
    protected boolean errorAction(String _targetURL, String _urlParameters, RequestErrorCode _errorCode, Logger _logger,
            String _errorMessage1, String _errorMessage2) throws Exception {
        if (ServerRequest.BARZAHLEN_REQUEST_RETRY) {
            ServerRequest.BARZAHLEN_REQUEST_RETRY = false;
            if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                _logger.debug(_errorMessage1);
            }

            return executeServerRequest(_targetURL, _urlParameters);
        }

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            _logger.debug(_errorMessage2);
        }

        ServerRequest.BARZAHLEN_REQUEST_RETRY = true;
        BARZAHLEN_REQUEST_ERROR_CODE = _errorCode;

        throw new Exception(_errorMessage2);
    }

    /**
     * Builds all the parameters necessary for the refund request.
     *
     * @param _parameters
     *            The parameters to assemble
     * @return The parameters already assembled, ready for the post request.
     */
    protected String assembleParameters(HashMap<String, String> _parameters) {
        HashMap<String, String> parameters = new HashMap<String, String>(_parameters);

        parameters.put("shop_id", this.shopId);
        parameters.put("payment_key", this.paymentKey);

        String hash = createHash(getParametersTemplate(), parameters);

        parameters.remove("payment_key");

        StringBuilder parametersString = new StringBuilder(createParametersString(getParametersTemplate(), parameters));

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
     * @return String[]
     */
    protected abstract String[] getParametersTemplate();

    /**
     * Executes a http request via post
     * 
     * @param _targetURL
     *            The url to make the request to
     * @param _urlParameters
     *            The parameters of the http post request
     * @throws IOException
     * @throws SAXException
     * @throws Exception
     */
    protected abstract boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception;

    /**
     * Compares the hash of the data received from the server to the one inside
     * the object.
     * 
     * @return True if the hashes are the same. False otherwise.
     */
    protected abstract boolean compareHashes();
}
