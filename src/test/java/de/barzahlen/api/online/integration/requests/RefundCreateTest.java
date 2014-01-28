package de.barzahlen.api.online.integration.requests;

import de.barzahlen.api.online.BarzahlenApi;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.requests.RefundCreateRequest;
import de.barzahlen.api.online.requests.resulthandler.RefundCreateResultHandler;
import de.barzahlen.http.HttpClientFactory;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.LoggingClient;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

public class RefundCreateTest {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void testSuccessful() {
        String transactionId = "60598478";
        String amount = "1.00";
        String currency = "EUR";
        String language = "de";

        sendRequest(transactionId, amount, currency, language);
    }

    @Ignore
    @Test
    public void testError() {
        String transactionId = "123";
        String amount = "1.00";
        String currency = "EUR";
        String language = "de";

        sendRequest(transactionId, amount, currency, language);
    }

    private void sendRequest(String transactionId, String amount, String currency, String language) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE);

        HttpClient httpClient = HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection);

        HttpClient loggingClient = new LoggingClient(httpClient, logger);

        Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
        BarzahlenApi barzahlenApi = new BarzahlenApi(loggingClient, configuration);

        RefundCreateRequest createRequest = new RefundCreateRequest();
        createRequest.setRefundCreateResultHandler(new RefundCreateResultHandlerImpl(logger));

        createRequest.initialize(transactionId, amount, currency, language);
        barzahlenApi.handle(createRequest);
    }

    class RefundCreateResultHandlerImpl extends RefundCreateResultHandler {
        private final Logger logger;

        RefundCreateResultHandlerImpl(final Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void onSuccess(String originTransactionId, String refundTransactionId) {
            logger.info("RefundCreate.onSuccess", "success");
            logger.info("RefundCreate.onSuccess", "originTransactionId: " + originTransactionId);
            logger.info("RefundCreate.onSuccess", "refundTransactionId: " + refundTransactionId);
        }

        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            logger.info("RefundCreate.onError", "error");
            logger.info("RefundCreate.onError", "result: " + result);
            logger.info("RefundCreate.onError", "errorMessage: " + errorMessage);
            logger.info("RefundCreate.onError", "httpResponseCode: " + String.valueOf(httpResponseCode));
        }
    }
}
