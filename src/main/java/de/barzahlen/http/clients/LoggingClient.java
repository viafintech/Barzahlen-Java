package de.barzahlen.http.clients;

import de.barzahlen.http.HttpResult;
import de.barzahlen.logger.Logger;

import java.util.Map;

/**
 * Uses a Logger for log all http requests
 */
public class LoggingClient implements HttpClient {

    private final HttpClient httpClient;
    private final Logger logger;

    public LoggingClient(final HttpClient httpClient, final Logger logger) {
        this.httpClient = httpClient;
        this.logger = logger;
    }

    @Override
    public HttpResult post(final String targetUrl, final Map<String, String> parameters) {

        logger.debug("HttpClient", "post - " + targetUrl + "?" + parameters);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            logger.debug("HttpClient", " param: " + entry.getKey() + "=" + entry.getValue());
        }

        return httpClient.post(targetUrl, parameters);
    }

    @Override
    public HttpResult post(final String targetUrl, final String parameters) {
        logger.debug("HttpClient", "post - " + targetUrl + "?" + parameters);
        return httpClient.post(targetUrl, parameters);
    }
}
