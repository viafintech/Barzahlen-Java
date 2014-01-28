package de.barzahlen.http.requests;

import java.util.Map;

/**
 * Represents a http request
 */
public interface HttpRequest {
    /**
     * Returns parameters
     *
     * @return parameters
     */
    public Map<String, String> getParameters();
}
