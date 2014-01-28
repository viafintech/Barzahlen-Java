package de.barzahlen.api.online.requests.resulthandler;

import de.barzahlen.http.HttpResult;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class TransactionCreateResultHandlerTest {

    @Test
    public void testOnSuccessHandlerWillBeCalledOnHttpResponseCode200() {
        TransactionCreateResultHandlerImpl mockedHandler = spy(new TransactionCreateResultHandlerImpl());
        mockedHandler.handleResult(buildSuccessfulResult(
                "transactionId", "paymentSlipLink", "expirationNotice", "infotext1", "infotext2", "result", "hash"));
        verify(mockedHandler).onSuccess(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testOnErrorHandlerWillBeCalledOnOtherHttpResponseCodeThan200() {
        TransactionCreateResultHandlerImpl mockedHandler = spy(new TransactionCreateResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(400, "1", "bad request"));
        verify(mockedHandler).onError(anyString(), anyString(), anyInt());
    }

    @Test
    public void tetstOnSuccessGetCorrectParameters() {
        String transactionId = "transactionId";
        String paymentSlipLink = "paymentSlipLink";
        String expirationNotice = "expirationNotice";
        String infotext1 = "infotext1";
        String infotext2 = "infotext2";

        TransactionCreateResultHandlerImpl mockedHandler = spy(new TransactionCreateResultHandlerImpl());
        mockedHandler.handleResult(buildSuccessfulResult(
                transactionId, paymentSlipLink, expirationNotice, infotext1, infotext2, "result", "hash"));
        verify(mockedHandler).onSuccess(eq(transactionId), eq(paymentSlipLink), eq(expirationNotice), eq(infotext1),
                eq(infotext2));
    }

    @Test
    public void tetstOnErrorGetCorrectParameters() {
        String result = "result";
        String errorMessage = "result";
        int httpResponseCode = 400;

        TransactionCreateResultHandlerImpl mockedHandler = spy(new TransactionCreateResultHandlerImpl());
        mockedHandler.handleResult(buildErrorResult(httpResponseCode, result, errorMessage));
        verify(mockedHandler).onError(errorMessage, result, httpResponseCode);
    }

    private HttpResult buildSuccessfulResult(final String transactionId, final String paymentSlipLink,
                                             final String expirationNotice, final String infotext1,
                                             final String infotext2, final String result, final String hash) {
        HttpResult httpResult;
        try {
            httpResult = new HttpResult(200, "ok", new ByteArrayInputStream(
                    ("<?xml version=\"1.0\" " +
                            "encoding=\"UTF-8\"?>\n" +
                            "<response>\n" +
                            "  <transaction-id>" + transactionId + "</transaction-id>\n" +
                            "  <payment-slip-link>" + paymentSlipLink + "</payment-slip-link>\n" +
                            "  <expiration-notice>" + expirationNotice + "</expiration-notice>\n" +
                            "  <infotext-1>" + infotext1 + "</infotext-1>\n" +
                            "  <infotext-2>" + infotext2 + "</infotext-2>\n" +
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

    class TransactionCreateResultHandlerImpl extends TransactionCreateResultHandler {
        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            new String();
        }

        @Override
        protected void onSuccess(final String transactionId, final String paymentSlipLink,
                                 final String ExpirationNotice, final String infotext1, final String infotext2) {
            new String();
        }
    }
}
