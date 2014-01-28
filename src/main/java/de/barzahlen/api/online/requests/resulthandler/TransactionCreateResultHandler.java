package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Handles TransactionCreate results
 */
abstract public class TransactionCreateResultHandler extends ResultHandler {

    /**
     * Will be called on http response code 200
     *
     * @param transactionId
     * @param paymentSlipLink
     * @param expirationNotice
     * @param infotext1
     * @param infotext2
     */
    abstract protected void onSuccess(String transactionId, String paymentSlipLink, String expirationNotice,
                                      String infotext1, String infotext2);

    /**
     * Will be called on another http response code than 200
     *
     * @param result
     * @param errorMessage
     * @param httpResponseCode
     */
    abstract protected void onError(String result, String errorMessage, int httpResponseCode);

    protected void handleSuccess(final HttpResult transactionCreateResult) throws ResultHandleException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(transactionCreateResult.getResultStream());

            String transactionId = document.getElementsByTagName("transaction-id").item(0).getTextContent();
            String paymentSlipLink = document.getElementsByTagName("payment-slip-link").item(0).getTextContent();
            String expirationNotice = document.getElementsByTagName("expiration-notice").item(0).getTextContent();
            String infotext1 = document.getElementsByTagName("infotext-1").item(0).getTextContent();
            String infotext2 = document.getElementsByTagName("infotext-2").item(0).getTextContent();

            onSuccess(transactionId, paymentSlipLink, expirationNotice, infotext1, infotext2);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ResultHandleException("Could not handle transactionCreateResult", e);
        }
    }

    protected void handleError(final HttpResult transactionCancelResult) throws ResultHandleException {
        try {
            ErrorResultParser errorResultParser = new ErrorResultParser();
            errorResultParser.parse(transactionCancelResult.getResultStream());

            onError(errorResultParser.getResult(), errorResultParser.getErrorMessage(),
                    transactionCancelResult.getResponseCode());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ResultHandleException("Could not handle transactionCancelResult", e);
        }
    }
}
