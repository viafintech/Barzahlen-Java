package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Handles ResendEmail results
 */
abstract public class ResendEmailResultHandler extends ResultHandler {

    /**
     * Will be called on http response code 200
     */
    abstract protected void onSuccess();

    /**
     * Will be called on another http response code than 200
     *
     * @param result
     * @param errorMessage
     * @param httpResponseCode
     */
    abstract protected void onError(String result, String errorMessage, int httpResponseCode);

    protected void handleSuccess(final HttpResult resendEmailResult) throws ResultHandleException {
        onSuccess();
    }

    protected void handleError(final HttpResult resendEmailResult) throws ResultHandleException {
        try {
            ErrorResultParser errorResultParser = new ErrorResultParser();
            errorResultParser.parse(resendEmailResult.getResultStream());

            onError(errorResultParser.getResult(), errorResultParser.getErrorMessage(),
                    resendEmailResult.getResponseCode());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ResultHandleException("Could not handle resendEmailResult", e);
        }
    }
}
