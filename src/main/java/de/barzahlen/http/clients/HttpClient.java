package de.barzahlen.http.clients;

import de.barzahlen.http.HttpResult;

import java.util.Map;

/**
 * Implements basic methods for a http client
 */
public interface HttpClient {
    /**
     * Post request with specified parameters to a url
     *
     * @param targetUrl
     * @param parameters
     * @return result
     */
    public HttpResult post(final String targetUrl, final Map<String, String> parameters);

    /**
     * Post request with specified parameters to a url
     *
     * @param targetUrl
     * @param parameters
     * @return result
     */
    public HttpResult post(final String targetUrl, final String parameters);
}
