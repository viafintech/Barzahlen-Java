package de.barzahlen.http.responses;

import java.io.OutputStream;

/**
 * Represents a http response that should be send to the client
 */
public interface HttpResponse {

    /**
     * Returns outputStream for writing data to the response
     *
     * @return outputStream
     */
    public OutputStream getOutputStream();

    /**
     * Sets a response header
     *
     * @param name
     * @param value
     */
    public void setHeader(final String name, final String value);
}
