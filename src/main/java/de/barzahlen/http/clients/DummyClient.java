package de.barzahlen.http.clients;

import de.barzahlen.http.HttpResult;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * Always returns an empty 200 response
 */
public class DummyClient implements HttpClient {
    @Override
    public HttpResult post(final String targetUrl, final Map<String, String> parameters) {
        return buildResult();
    }

    @Override
    public HttpResult post(final String targetUrl, final String parameters) {
        return buildResult();
    }

    private HttpResult buildResult() {
        return new HttpResult(200, "ok", new ByteArrayInputStream(new byte[0]));
    }
}
