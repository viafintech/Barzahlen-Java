package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class RefundCreateResultHandlerTest {

    @Test
    public void testOnSuccessHandlerWillBeCalledOnHttpResponseCode200() {
        RefundCreateResultHandlerImpl mockedHandler = spy(new RefundCreateResultHandlerImpl());
        mockedHandler.handleResult(buildSuccessfulResult(
                "originTransactionId", "refundTransactionId", "result", "hash"));
        verify(mockedHandler).onSuccess(anyString(), anyString());
    }

    @Test
    public void testOnErrorHandlerWillBeCalledOnOtherHttpResponseCodeThan200() {
        RefundCreateResultHandlerImpl mockedHandler = spy(new RefundCreateResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(400, "1", "bad request"));
        verify(mockedHandler).onError(anyString(), anyString(), anyInt());
    }

    @Test
    public void tetstOnSuccessGetCorrectParameters() {
        String originTransactionId = "originTransactionId";
        String refundTransactionId = "refundTransactionId";

        RefundCreateResultHandlerImpl mockedHandler = spy(new RefundCreateResultHandlerImpl());
        mockedHandler.handleResult(buildSuccessfulResult(
                originTransactionId, refundTransactionId, "result", "hash"));
        verify(mockedHandler).onSuccess(eq(originTransactionId), eq(refundTransactionId));
    }

    @Test
    public void tetstOnErrorGetCorrectParameters() {
        String result = "result";
        String errorMessage = "result";
        int httpResponseCode = 400;

        RefundCreateResultHandlerImpl mockedHandler = spy(new RefundCreateResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(httpResponseCode, result, errorMessage));
        verify(mockedHandler).onError(errorMessage, result, httpResponseCode);
    }

    private HttpResult buildSuccessfulResult(final String originTransactionId, final String refundTransactionId,
                                             final String result, final String hash) {
        HttpResult httpResult;
        try {
            httpResult = new HttpResult(200, "ok", new ByteArrayInputStream(
                    ("<?xml version=\"1.0\" " +
                            "encoding=\"UTF-8\"?>\n" +
                            "<response>\n" +
                            "  <origin-transaction-id>" + originTransactionId + "</origin-transaction-id>\n" +
                            "  <refund-transaction-id>" + refundTransactionId + "</refund-transaction-id>\n" +
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

    class RefundCreateResultHandlerImpl extends RefundCreateResultHandler {
        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            new String();
        }

        @Override
        protected void onSuccess(final String originTransactionId, final String refundTransactionId) {
            new String();
        }
    }
}
