package de.barzahlen.api.online.integration.requests;

import de.barzahlen.api.online.BarzahlenApi;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.Transaction;
import de.barzahlen.api.online.requests.TransactionCreateRequest;
import de.barzahlen.api.online.requests.resulthandler.TransactionCreateResultHandler;
import de.barzahlen.http.HttpClientFactory;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.LoggingClient;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

public class TransactionCreateTest {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void testSuccessful() {
        Transaction transaction = new Transaction()
                .setCustomerEmail("foo@example.com")
                .setAmount("10.00")
                .setCurrency("EUR")
                .setOrderId("123")
                .setCustomerStreetNr("Teststr.")
                .setCustomerZipcode("10247")
                .setCustomerCity("Berlin")
                .setCustomerCountry("DE")
                .setLanguage("de");

        sendRequest(transaction);
    }

    @Ignore
    @Test
    public void testError() {
        Transaction transaction = new Transaction()
                .setCustomerEmail("foo@example.com")
                .setAmount("10.00")
                .setCurrency("EUR")
                .setOrderId("123")
                .setCustomerStreetNr("Teststr.")
                .setCustomerZipcode("10247")
                .setCustomerCity("Berlin")
                .setCustomerCountry("")
                .setLanguage("de");

        sendRequest(transaction);
    }

    private void sendRequest(Transaction transaction) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE);

        HttpClient httpClient = HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection);

        HttpClient loggingClient = new LoggingClient(httpClient, logger);

        Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
        BarzahlenApi barzahlenApi = new BarzahlenApi(loggingClient, configuration);

        TransactionCreateRequest createRequest = new TransactionCreateRequest();
        createRequest.setTransactionCreateResultHandler(new TransactionCreateResultHandlerImpl(logger));

        createRequest.initialize(transaction);
        barzahlenApi.handle(createRequest);
    }

    class TransactionCreateResultHandlerImpl extends TransactionCreateResultHandler {
        private final Logger logger;

        TransactionCreateResultHandlerImpl(final Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void onSuccess(String transactionId, String paymentSlipLink, String ExpirationNotice,
                                 String infotext1, String infotext2) {
            logger.info("TransactionCreate.onSuccess", "success");
            logger.info("TransactionCreate.onSuccess", "transactionId: " + transactionId);
            logger.info("TransactionCreate.onSuccess", "paymentSlipLink: " + paymentSlipLink);
            logger.info("TransactionCreate.onSuccess", "ExpirationNotice: " + ExpirationNotice);
            logger.info("TransactionCreate.onSuccess", "infotext1: " + infotext1);
            logger.info("TransactionCreate.onSuccess", "infotext2: " + infotext2);
        }

        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            logger.info("TransactionCreate.onError", "error");
            logger.info("TransactionCreate.onError", "result: " + result);
            logger.info("TransactionCreate.onError", "errorMessage: " + errorMessage);
            logger.info("TransactionCreate.onError", "httpResponseCode: " + String.valueOf
                    (httpResponseCode));
        }
    }
}
