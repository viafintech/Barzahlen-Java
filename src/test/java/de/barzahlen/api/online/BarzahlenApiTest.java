package de.barzahlen.api.online;

import de.barzahlen.api.online.requests.BarzahlenApiRequest;
import de.barzahlen.helper.MapMatcher;
import de.barzahlen.http.HttpResult;
import de.barzahlen.http.clients.HttpClient;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BarzahlenApiTest {
    @Test
    public void testHttpClientGetsCorrectUrl() {
        String baseUrl = "https://api.barzahlen.de/v1/";
        String urlPath = "test";

        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        BarzahlenApiRequest mockedApiRequest = Mockito.mock(BarzahlenApiRequest.class);

        when(mockedConfiguration.getBaseUrl()).thenReturn(baseUrl);
        when(mockedApiRequest.getPath()).thenReturn(urlPath);

        BarzahlenApi barzahlenApi = new BarzahlenApi(mockedHttpClient, mockedConfiguration);
        barzahlenApi.handle(mockedApiRequest);

        verify(mockedHttpClient).post(eq(baseUrl + urlPath), anyMap());
    }

    @Test
    public void testHttpClientGetsCorrectParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("foo1", "foo1");
        parameters.put("foo2", "bar2");
        parameters.put("foo3", "bar3");

        Map<String, String> expectedParameters = new HashMap<>(parameters);
        expectedParameters.put("hash",
                "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e");
        expectedParameters.put("shop_id", "123");

        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        BarzahlenApiRequest mockedApiRequest = Mockito.mock(BarzahlenApiRequest.class);

        when(mockedConfiguration.getShopId()).thenReturn("123");
        when(mockedApiRequest.getParameters()).thenReturn(parameters);

        BarzahlenApi barzahlenApi = new BarzahlenApi(mockedHttpClient, mockedConfiguration);
        barzahlenApi.handle(mockedApiRequest);

        verify(mockedHttpClient).post(anyString(), (Map<String, String>) argThat(new MapMatcher(expectedParameters)));
    }

    @Test
    public void testRequestHandleWillBeCalled() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("foo1", "foo1");

        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        HttpResult mockedHttpResult = Mockito.mock(HttpResult.class);
        BarzahlenApiRequest mockedApiRequest = Mockito.mock(BarzahlenApiRequest.class);

        when(mockedConfiguration.getShopId()).thenReturn("123");
        when(mockedHttpClient.post(anyString(), (Map<String, String>) anyObject())).thenReturn(mockedHttpResult);
        when(mockedApiRequest.getParameters()).thenReturn(parameters);

        BarzahlenApi barzahlenApi = new BarzahlenApi(mockedHttpClient, mockedConfiguration);
        barzahlenApi.handle(mockedApiRequest);

        verify(mockedApiRequest).handleResult(mockedHttpResult);
    }
}
