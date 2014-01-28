package de.barzahlen.http.requests;

import java.util.Map;

/**
 * A dummy request
 */
public class DummyRequest implements HttpRequest {

    private final Map<String, String> parameters;


    public DummyRequest(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }
}
