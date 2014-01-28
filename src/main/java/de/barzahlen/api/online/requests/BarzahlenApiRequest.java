package de.barzahlen.api.online.requests;

import de.barzahlen.http.HttpResult;

import java.util.List;
import java.util.Map;

/**
 * A request to the Barzahlen-API
 */
public interface BarzahlenApiRequest {

    /**
     * Returns url path
     *
     * @return url path
     */
    public String getPath();

    /**
     * Returns order of parameters for hash calculation
     *
     * @return parameters order
     */
    public List<String> getParametersOrder();

    /**
     * Returns parameters transferred in the request
     *
     * @return parameters
     */
    public Map<String, String> getParameters();

    /**
     * Should handle result of the request
     *
     * @param result
     */
    public void handleResult(final HttpResult result);

}
