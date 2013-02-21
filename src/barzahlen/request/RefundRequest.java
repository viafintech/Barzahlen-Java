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
import barzahlen.request.xml.RefundXMLInfo;

/**
 * Implements the refund web service
 * 
 * @author Jesus Javier Nuno Garcia
 */
public final class RefundRequest extends ServerRequest {

    /**
     * Log file for the logger.
     */
    private static Logger refundRequestLog = Logger.getLogger(RefundRequest.class.getName());

    /**
     * The xml info retrieved from the server response.
     */
    public static RefundXMLInfo XML_INFO;

    static {
        XML_INFO = new RefundXMLInfo();
    }

    /**
     * Default constructor
     */
    public RefundRequest() {
        super();
        refundRequestLog.addAppender(this.logAppender.getConsoleAppender());
        refundRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Constructor with parameters for the "refund" request
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
    public RefundRequest(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
        super(_sandbox, _shopId, _paymentKey, _notificationKey);
        refundRequestLog.addAppender(this.logAppender.getConsoleAppender());
        refundRequestLog.addAppender(this.logAppender.getFileAppender());
    }

    /**
     * Executes the refund for a specific order.
     * 
     * @param _parameters
     *            The parameters for the request. There are necessary
     *            "order_id", "amount","transaction_id", "currency" (ISO 4217)
     *            and "language" (ISO 639-1).
     * @throws Exception
     */
    public boolean refund(HashMap<String, String> _parameters) throws Exception {
        return executeServerRequest(Barzahlen.BARZAHLEN_REFUND_URL, assembleParameters(_parameters));
    }

    @Override
    protected String assembleParameters(HashMap<String, String> _parameters) {
        String hashMessage = this.shopId + ";" + _parameters.get("transaction_id") + ";" + _parameters.get("amount") + ";"
                + _parameters.get("currency") + ";" + _parameters.get("language") + ";" + this.paymentKey;

        String hash = createHash(hashMessage);

        String params = "shop_id=" + this.shopId + "&transaction_id=" + _parameters.get("transaction_id") + "&amount="
                + _parameters.get("amount") + "&currency=" + _parameters.get("currency") + "&language=" + _parameters.get("language")
                + "&hash=" + hash;

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

        boolean xmlResult = RefundRequest.XML_INFO.readXMLFile(xml, responseCode);

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            refundRequestLog.debug("Response code: " + responseCode + ". Response message: " + httpCon.getResponseMessage()
                    + ". Parameters: " + ServerRequest.formatReadableParameters(_urlParameters));
            refundRequestLog.debug(xml);
            refundRequestLog.debug(RefundRequest.XML_INFO.getOriginTransactionId());
            refundRequestLog.debug(RefundRequest.XML_INFO.getRefundTransactionId());
            refundRequestLog.debug(String.valueOf(RefundRequest.XML_INFO.getResult()));
            refundRequestLog.debug(RefundRequest.XML_INFO.getHash());
        }

        if (!xmlResult) {
            return errorAction(
                    _targetURL,
                    _urlParameters,
                    RequestErrorCode.XML_ERROR,
                    refundRequestLog,
                    "Refund request failed - retry",
                    "Error received from the server. Response code: " + responseCode + ". Response message: "
                            + httpCon.getResponseMessage());
        } else if (!RefundRequest.XML_INFO.checkParameters()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.PARAMETERS_ERROR, refundRequestLog,
                    "Refund request failed - retry", "There are errors with parameters received from the server.");
        } else if (!compareHashes()) {
            return errorAction(_targetURL, _urlParameters, RequestErrorCode.HASH_ERROR, refundRequestLog, "Refund request failed - retry",
                    "Data received is not correct (hashes do not match)");
        } else {
            BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;
            return true;
        }
    }

    @Override
    protected boolean compareHashes() {
        String hash = new String();

        String data = RefundRequest.XML_INFO.getOriginTransactionId() + ";" + RefundRequest.XML_INFO.getRefundTransactionId() + ";"
                + RefundRequest.XML_INFO.getResult() + ";" + this.paymentKey;
        hash = createHash(data);

        if (hash.equals(RefundRequest.XML_INFO.getHash())) {
            return true;
        }

        return false;
    }
}
