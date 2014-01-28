package de.barzahlen.api.online.integration.requests;

import de.barzahlen.api.online.BarzahlenApi;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.requests.TransactionCancelRequest;
import de.barzahlen.api.online.requests.resulthandler.TransactionCancelResultHandler;
import de.barzahlen.http.HttpClientFactory;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.LoggingClient;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

public class TransactionCancelTest {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void testSuccessful() {
        String transactionId = "60599502";
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

        TransactionCancelRequest transactionCancelRequest = new TransactionCancelRequest();
        transactionCancelRequest.setTransactionCancelResultHandler(new TransactionCancelResultHandlerImpl(logger));

        transactionCancelRequest.initialize(transactionId, orderId);
        barzahlenApi.handle(transactionCancelRequest);
    }

    class TransactionCancelResultHandlerImpl extends TransactionCancelResultHandler {
        private final Logger logger;

        TransactionCancelResultHandlerImpl(final Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void onSuccess() {
            logger.info("TransactionCancel.onSuccess", "success");
        }

        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            logger.info("TransactionCancel.onError", "error");
            logger.info("TransactionCancel.onError", "result: " + result);
            logger.info("TransactionCancel.onError", "errorMessage: " + errorMessage);
            logger.info("TransactionCancel.onError", "httpResponseCode: " + String.valueOf
                    (httpResponseCode));
        }
    }
}
