package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Handles RefundCreate results
 */
abstract public class RefundCreateResultHandler extends ResultHandler {

    /**
     * Will be called on http response code 200
     *
     * @param originTransactionId
     * @param refundTransactionId
     */
    abstract protected void onSuccess(String originTransactionId, String refundTransactionId);

    /**
     * Will be called on another http response code than 200
     *
     * @param result
     * @param errorMessage
     * @param httpResponseCode
     */
    abstract protected void onError(String result, String errorMessage, int httpResponseCode);

    protected void handleSuccess(final HttpResult refundCreateResult) throws ResultHandleException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(refundCreateResult.getResultStream());

            String originTransactionId =
                    document.getElementsByTagName("origin-transaction-id").item(0).getTextContent();
            String refundTransactionId =
                    document.getElementsByTagName("refund-transaction-id").item(0).getTextContent();

            onSuccess(originTransactionId, refundTransactionId);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ResultHandleException("Could not handle refundCreateResult", e);
        }
    }

    protected void handleError(final HttpResult refundCreateResult) throws ResultHandleException {
        try {
            ErrorResultParser errorResultParser = new ErrorResultParser();
            errorResultParser.parse(refundCreateResult.getResultStream());

            onError(errorResultParser.getResult(), errorResultParser.getErrorMessage(),
                    refundCreateResult.getResponseCode());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ResultHandleException("Could not handle refundCreateResult", e);
        }
    }
}
