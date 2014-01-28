package de.barzahlen.api.online.integration.requests;

import de.barzahlen.api.online.BarzahlenApi;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.requests.TransactionUpdateRequest;
import de.barzahlen.api.online.requests.resulthandler.TransactionUpdateResultHandler;
import de.barzahlen.http.HttpClientFactory;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.LoggingClient;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

public class TransactionUpdate {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void testSuccessful() {
        String transactionId = "60600216";
        String orderId = "123";

        sendRequest(transactionId, orderId);
    }

    @Ignore
    @Test
    public void testError() {
        String transactionId = "123";
        String orderId = "123";

        sendRequest(transactionId, orderId);
    }

    private void sendRequest(String transactionId, String orderId) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE);

        HttpClient httpClient = HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection);

        HttpClient loggingClient = new LoggingClient(httpClient, logger);

        Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
        BarzahlenApi barzahlenApi = new BarzahlenApi(loggingClient, configuration);

        TransactionUpdateRequest updateRequest = new TransactionUpdateRequest();
        updateRequest.setTransactionUpdateResultHandler(new TransactionUpdateResultHandlerImpl(logger));

        updateRequest.initialize(transactionId, orderId);
        barzahlenApi.handle(updateRequest);
    }

    class TransactionUpdateResultHandlerImpl extends TransactionUpdateResultHandler {
        private final Logger logger;

        TransactionUpdateResultHandlerImpl(final Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void onSuccess() {
            logger.info("TransactionUpdate.onSuccess", "success");
        }

        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            logger.info("TransactionUpdate.onError", "error");
            logger.info("TransactionUpdate.onError", "result: " + result);
            logger.info("TransactionUpdate.onError", "errorMessage: " + errorMessage);
            logger.info("TransactionUpdate.onError", "httpResponseCode: " + String.valueOf
                    (httpResponseCode));
        }
    }
}
