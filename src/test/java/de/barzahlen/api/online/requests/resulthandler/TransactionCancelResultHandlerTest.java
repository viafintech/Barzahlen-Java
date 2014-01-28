package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class TransactionCancelResultHandlerTest {

    @Test
    public void testOnSuccessHandlerWillBeCalledOnHttpResponseCode200() {
        TransactionCancelResultHandlerImpl mockedHandler = spy(new TransactionCancelResultHandlerImpl());
        mockedHandler.handleResult(buildSuccessfulResult(
                "transactionId", "result", "hash"));
        verify(mockedHandler).onSuccess();
    }

    @Test
    public void testOnErrorHandlerWillBeCalledOnOtherHttpResponseCodeThan200() {
        TransactionCancelResultHandlerImpl mockedHandler = spy(new TransactionCancelResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(400, "1", "bad request"));
        verify(mockedHandler).onError(anyString(), anyString(), anyInt());
    }

    @Test
    public void tetstOnErrorGetCorrectParameters() {
        String result = "result";
        String errorMessage = "result";
        int httpResponseCode = 400;

        TransactionCancelResultHandlerImpl mockedHandler = spy(new TransactionCancelResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(httpResponseCode, result, errorMessage));
        verify(mockedHandler).onError(errorMessage, result, httpResponseCode);
    }

    private HttpResult buildSuccessfulResult(final String transactionId, final String result, final String hash) {
        HttpResult httpResult;
        try {
            httpResult = new HttpResult(200, "ok", new ByteArrayInputStream(
                    ("<?xml version=\"1.0\" " +
                            "encoding=\"UTF-8\"?>\n" +
                            "<response>\n" +
                            "  <transaction-id>" + transactionId + "</transaction-id>\n" +
                            "  <result>" + result + "</result>\n" +
                            "  <hash>" + hash + "</hash>\n" +
                            "</response>").getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            httpResult = null;
            e.printStackTrace();
        }

        return httpResult;
    }

    private HttpResult buildErrorResult(final int httpResponseCode, final String result, final String errorMessage) {
        HttpResult httpResult;
        try {
            httpResult = new HttpResult(httpResponseCode, "ok", new ByteArrayInputStream(
                    ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "  <response>\n" +
                            "  <result>" + result + "</result>\n" +
                            "  <error-message>" + errorMessage + "</error-message>\n" +
                            "  </response>").getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            httpResult = null;
            e.printStackTrace();
        }

        return httpResult;
    }

    class TransactionCancelResultHandlerImpl extends TransactionCancelResultHandler {
        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            new String();
        }

        @Override
        protected void onSuccess() {
            new String();
        }
    }
}
