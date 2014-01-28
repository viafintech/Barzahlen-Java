package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;

abstract public class ResultHandler {

    /**
     * Handles successful results
     *
     * @param result
     * @throws ResultHandleException
     */
    protected abstract void handleSuccess(final HttpResult result) throws ResultHandleException;

    /**
     * Handles error results
     *
     * @param result
     * @throws ResultHandleException
     */
    protected abstract void handleError(final HttpResult result) throws ResultHandleException;

    /**
     * Handle http result
     *
     * @param result
     */
    public void handleResult(final HttpResult result) {
        if (result.getResponseCode() == 200) {
            handleSuccess(result);
        } else {
            handleError(result);
        }
    }
}
