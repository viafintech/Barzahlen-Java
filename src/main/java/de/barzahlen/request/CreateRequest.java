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
import de.barzahlen.BarzahlenApiRequest;
import de.barzahlen.request.xml.CreateXMLInfo;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Implements the create transaction web service
 * 
 * @author Jesus Javier Nuno Garcia
 */
public final class CreateRequest extends ServerRequest {

    /**
     * Log file for the logger.
     */
    private static final Logger createRequestLog = Logger.getLogger(CreateRequest.class.getName());

    /**
     * The xml info retrieved from the server response.
     */
    public static CreateXMLInfo XML_INFO;

    static {
        XML_INFO = new CreateXMLInfo();
    }

    /**
     * Default constructor.
     */
    public CreateRequest() {
        super();
        createRequestLog.addAppender(this.logAppender.getConsoleAppender());
        createRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Constructor with parameters for the "create" request
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
    public CreateRequest(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
        super(_sandbox, _shopId, _paymentKey, _notificationKey);
        createRequestLog.addAppender(this.logAppender.getConsoleAppender());
        createRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Executes the create request.
     * 
     * @param _parameters
     *            The parameters for the request. There are necessary "order_id"
     *            and "currency" (ISO 4217).
     * @throws Exception
     */
    public boolean create(HashMap<String, String> _parameters) throws Exception {
        return executeServerRequest(Barzahlen.BARZAHLEN_CREATE_URL, assembleParameters(_parameters));
    }

    @Override
    protected String[] getParametersTemplate() {
        String parametersTemplate[] = new String[14];
        parametersTemplate[0] = "shop_id";
        parametersTemplate[1] = "customer_email";
        parametersTemplate[2] = "amount";
        parametersTemplate[3] = "currency";
        parametersTemplate[4] = "language";
        parametersTemplate[5] = "order_id";
        parametersTemplate[6] = "customer_street_nr";
        parametersTemplate[7] = "customer_zipcode";
        parametersTemplate[8] = "customer_city";
        parametersTemplate[9] = "customer_country";
        parametersTemplate[10] = "custom_var_0";
        parametersTemplate[11] = "custom_var_1";
        parametersTemplate[12] = "custom_var_2";
        parametersTemplate[13] = "payment_key";

        return parametersTemplate;
    }

    @Override
    protected boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception {
        BarzahlenApiRequest request = new BarzahlenApiRequest(_targetURL,CreateRequest.XML_INFO);
        boolean successful = request.doRequest(_urlParameters);

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            createRequestLog.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage()
                    + ". Parameters sent: " + ServerRequest.formatReadableParameters(_urlParameters));
            createRequestLog.debug(request.getResult());
            createRequestLog.debug(CreateRequest.XML_INFO.getTransactionId());
            createRequestLog.debug(CreateRequest.XML_INFO.getPaymentSlipLink());
            createRequestLog.debug(CreateRequest.XML_INFO.getExpirationNotice());
            createRequestLog.debug(CreateRequest.XML_INFO.getInfotext1());
            createRequestLog.debug(CreateRequest.XML_INFO.getInfotext2());
            createRequestLog.debug(String.valueOf(CreateRequest.XML_INFO.getResult()));
            createRequestLog.debug(CreateRequest.XML_INFO.getHash());
        }

        if (!successful) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.XML_ERROR, createRequestLog,
                    "Payment slip request failed - retry", "Error received from the server. Response code: " + request.getResponseCode()
                    + ". Response message: " + request.getResponseMessage());
        } else if (!CreateRequest.XML_INFO.checkParameters()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.PARAMETERS_ERROR, createRequestLog,
                    "Payment slip request failed - retry", "There are errors with parameters received from the server.");
        } else if (!compareHashes()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.HASH_ERROR, createRequestLog,
                    "Payment slip request failed - retry", "Data received is not correct (hashes do not match)");
        } else {
            BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;
            return true;
        }
    }

    @Override
    protected boolean compareHashes() {
        String data = CreateRequest.XML_INFO.getTransactionId() + ";" + CreateRequest.XML_INFO.getPaymentSlipLink() + ";"
                + CreateRequest.XML_INFO.getExpirationNotice() + ";" + CreateRequest.XML_INFO.getInfotext1() + ";"
                + CreateRequest.XML_INFO.getInfotext2() + ";" + CreateRequest.XML_INFO.getResult() + ";" + this.paymentKey;
        String hash = calculateHash(data);

		return hash.equals(CreateRequest.XML_INFO.getHash());
	}
}
