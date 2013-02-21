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
import barzahlen.request.xml.ResendEmailXMLInfo;

/**
 * Implements the resend email webservice
 * 
 * @author Jesus Javier Nuno Garcia
 */
public final class ResendEmailRequest extends ServerRequest {

    /**
     * Log file for the logger.
     */
    private static Logger resendEmailRequestLog = Logger.getLogger(ResendEmailRequest.class.getName());

    /**
     * The xml info retrieved from the server response.
     */
    public static ResendEmailXMLInfo XML_INFO;

    static {
        XML_INFO = new ResendEmailXMLInfo();
    }

    /**
     * Default constructor.
     */
    public ResendEmailRequest() {
        super();
        resendEmailRequestLog.addAppender(this.logAppender.getConsoleAppender());
        resendEmailRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Constructor with parameters for the "resend email" request
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
    public ResendEmailRequest(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
        super(_sandbox, _shopId, _paymentKey, _notificationKey);
        resendEmailRequestLog.addAppender(this.logAppender.getConsoleAppender());
        resendEmailRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Executes the resend email for a specific order.
     * 
     * @param _parameters
     *            The parameters for the request. There are necessary
     *            "order_id", "transaction_id", "transaction_state" and
     *            "language" (ISO 639-1).
     * @throws Exception
     */
    public boolean resendEmail(HashMap<String, String> _parameters) throws Exception {
        return executeServerRequest(Barzahlen.BARZAHLEN_RESEND_EMAIL_URL, assembleParameters(_parameters));
    }

    @Override
    protected String assembleParameters(HashMap<String, String> _parameters) {

        String hashMessage = this.shopId + ";" + _parameters.get("transaction_id") + ";" + _parameters.get("language") + ";"
                + this.paymentKey;

        String hash = createHash(hashMessage);

        String params = "shop_id=" + this.shopId + "&transaction_id=" + _parameters.get("transaction_id") + "&language="
                + _parameters.get("language") + "&hash=" + hash;

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

        boolean xmlResult = ResendEmailRequest.XML_INFO.readXMLFile(xml, responseCode);

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            resendEmailRequestLog.debug("Response code: " + responseCode + ". Response message: " + httpCon.getResponseMessage()
                    + ". Parameters: " + ServerRequest.formatReadableParameters(_urlParameters));
            resendEmailRequestLog.debug(xml);
            resendEmailRequestLog.debug(ResendEmailRequest.XML_INFO.getTransactionId());
            resendEmailRequestLog.debug(String.valueOf(ResendEmailRequest.XML_INFO.getResult()));
            resendEmailRequestLog.debug(ResendEmailRequest.XML_INFO.getHash());
        }

        if (!xmlResult) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.XML_ERROR, resendEmailRequestLog,
                    "Resend email request failed - retry", "Error received from the server. Response code: " + responseCode
                            + ". Response message: " + httpCon.getResponseMessage());
        } else if (!ResendEmailRequest.XML_INFO.checkParameters()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.PARAMETERS_ERROR, resendEmailRequestLog,
                    "Resend email request failed - retry", "There are errors with parameters received from the server.");
        } else if (!compareHashes()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.HASH_ERROR, resendEmailRequestLog,
                    "Resend email request failed - retry", "Data received is not correct (hashes do not match)");
        } else {
            BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;
            return true;
        }
    }

    @Override
    protected boolean compareHashes() {
        String hash = new String();

        String data = ResendEmailRequest.XML_INFO.getTransactionId() + ";" + ResendEmailRequest.XML_INFO.getResult() + ";"
                + this.paymentKey;
        hash = createHash(data);

        if (hash.equals(ResendEmailRequest.XML_INFO.getHash())) {
            return true;
        }

        return false;
    }
}
