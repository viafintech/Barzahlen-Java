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
package barzahlen.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import barzahlen.Barzahlen;
import barzahlen.request.xml.CreateXMLInfo;

/**
 * Implements the create transaction web service
 * 
 * @author Jesus Javier Nuno Garcia
 */
public final class CreateRequest extends ServerRequest {

    /**
     * Log file for the logger.
     */
    private static Logger createRequestLog = Logger.getLogger(CreateRequest.class);

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
    protected String assembleParameters(HashMap<String, String> _parameters) {
        String hashMessage = this.shopId + ";" + _parameters.get("customer_email") + ";" + _parameters.get("amount") + ";"
                + _parameters.get("currency") + ";" + _parameters.get("language") + ";" + _parameters.get("order_id") + ";"
                + _parameters.get("customer_street_nr") + ";" + _parameters.get("customer_zipcode") + ";"
                + _parameters.get("customer_city") + ";" + _parameters.get("customer_country") + ";" + _parameters.get("custom_var_0")
                + ";" + _parameters.get("custom_var_1") + ";" + _parameters.get("custom_var_2") + ";" + this.paymentKey;

        String hash = createHash(hashMessage);

        String params = "shop_id=" + this.shopId + "&customer_email=" + _parameters.get("customer_email") + "&amount="
                + _parameters.get("amount") + "&currency=" + _parameters.get("currency") + "&language=" + _parameters.get("language")
                + "&order_id=" + _parameters.get("order_id") + "&customer_street_nr=" + _parameters.get("customer_street_nr")
                + "&customer_zipcode=" + _parameters.get("customer_zipcode") + "&customer_city=" + _parameters.get("customer_city")
                + "&customer_country=" + _parameters.get("customer_country") + "&custom_var_0=" + _parameters.get("custom_var_0")
                + "&custom_var_1=" + _parameters.get("custom_var_1") + "&custom_var_2=" + _parameters.get("custom_var_2") + "&hash=" + hash;

        return params;
    }

    @Override
    protected boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception {
        URL url = new URL(_targetURL);
        HttpsURLConnection httpCon = (HttpsURLConnection) url.openConnection();
        httpCon.setRequestMethod("POST");
        httpCon.setDoOutput(true);
        httpCon.setDoInput(true);
        httpCon.setUseCaches(false);

        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        out.write(_urlParameters);
        out.flush();
        out.close();

        BufferedReader br;

        int responseCode = httpCon.getResponseCode();

        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(httpCon.getErrorStream()));
        }

        String input = "";
        String xml = "";

        while ((input = br.readLine()) != null) {
            xml += input + "\n";
        }

        br.close();

        boolean xmlResult = CreateRequest.XML_INFO.readXMLFile(xml, responseCode);

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            createRequestLog.debug("Response code: " + responseCode + ". Response message: " + httpCon.getResponseMessage()
                    + ". Parameters sent: " + ServerRequest.formatReadableParameters(_urlParameters));
            createRequestLog.debug(xml);
            createRequestLog.debug(CreateRequest.XML_INFO.getTransactionId());
            createRequestLog.debug(CreateRequest.XML_INFO.getPaymentSlipLink());
            createRequestLog.debug(CreateRequest.XML_INFO.getExpirationNotice());
            createRequestLog.debug(CreateRequest.XML_INFO.getInfotext1());
            createRequestLog.debug(CreateRequest.XML_INFO.getInfotext2());
            createRequestLog.debug(String.valueOf(CreateRequest.XML_INFO.getResult()));
            createRequestLog.debug(CreateRequest.XML_INFO.getHash());
        }

        if (!xmlResult) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.XML_ERROR, createRequestLog,
                    "Payment slip request failed - retry", "Error received from the server. Response code: " + responseCode
                            + ". Response message: " + httpCon.getResponseMessage());
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
        String hash = new String();

        String data = CreateRequest.XML_INFO.getTransactionId() + ";" + CreateRequest.XML_INFO.getPaymentSlipLink() + ";"
                + CreateRequest.XML_INFO.getExpirationNotice() + ";" + CreateRequest.XML_INFO.getInfotext1() + ";"
                + CreateRequest.XML_INFO.getInfotext2() + ";" + CreateRequest.XML_INFO.getResult() + ";" + this.paymentKey;
        hash = createHash(data);

        if (hash.equals(CreateRequest.XML_INFO.getHash())) {
            return true;
        }

        return false;
    }
}
