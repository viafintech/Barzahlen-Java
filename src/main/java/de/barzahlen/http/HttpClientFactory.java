package de.barzahlen.http;

import de.barzahlen.http.clients.DummyClient;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.HttpsURLConnectionClient;

/**
 * Creates a http client
 */
public class HttpClientFactory {
    public enum HttpClientType {
        HttpsURLConnection,
        DummyHttpClient,
    }

    /**
     * Creates a specific http client
     *
     * @param httpClientType
     * @return http client
     */
    public static HttpClient getHttpClient(final HttpClientType httpClientType) {
        HttpClient httpClient;

        switch (httpClientType) {
            case HttpsURLConnection:
                httpClient = new HttpsURLConnectionClient();
                break;
            case DummyHttpClient:
                httpClient = new DummyClient();
                break;
            default:
                httpClient = null;
        }

        return httpClient;
    }
}
