package de.barzahlen.http.responses;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A dummy response
 */
public class DummyResponse implements HttpResponse {

    private final OutputStream outputStream;
    private final Map<String, String> headers;


    public DummyResponse() {
        this.outputStream = new ByteArrayOutputStream();
        this.headers = new HashMap<>();
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void setHeader(final String name, final String value) {
        headers.put(name, value);
    }
}
