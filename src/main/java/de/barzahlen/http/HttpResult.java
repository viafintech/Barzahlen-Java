package de.barzahlen.http;

import java.io.InputStream;

/**
 * Represents a result from an http request
 */
public class HttpResult {

    private final int responseCode;
    private final String responseMessage;
    private final InputStream resultStream;


    public HttpResult(final int responseCode, final String responseMessage, final InputStream resultStream) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.resultStream = resultStream;
    }

    /**
     * Returns response
     *
     * @return response
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Returns response message
     *
     * @return response message
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * Returns result stream
     *
     * @return result stream
     */
    public InputStream getResultStream() {
        return resultStream;
    }
}
