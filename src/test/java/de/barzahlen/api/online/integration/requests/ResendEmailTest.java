package de.barzahlen.api.online.integration.requests;

import de.barzahlen.api.online.BarzahlenApi;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.requests.ResendEmailRequest;
import de.barzahlen.api.online.requests.resulthandler.ResendEmailResultHandler;
import de.barzahlen.http.HttpClientFactory;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.http.clients.LoggingClient;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

public class ResendEmailTest {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void testSuccessful() {
        String transactionId = "60599912";
        String language = "de";

        sendRequest(transactionId, language);
    }

    @Ignore
    @Test
    public void testError() {
        String transactionId = "123";
        String language = "de";

        sendRequest(transactionId, language);
    }

    private void sendRequest(String transactionId, String language) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE);

        HttpClient httpClient = HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection);
        HttpClient loggingClient = new LoggingClient(httpClient, logger);

        Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
        BarzahlenApi barzahlenApi = new BarzahlenApi(loggingClient, configuration);

        ResendEmailRequest resendEmailRequest = new ResendEmailRequest();
        resendEmailRequest.setResendEmailResultHandler(new ResendEmailResultHandlerImpl(logger));

        resendEmailRequest.initialize(transactionId, language);
        barzahlenApi.handle(resendEmailRequest);
    }

    class ResendEmailResultHandlerImpl extends ResendEmailResultHandler {
        private final Logger logger;

        ResendEmailResultHandlerImpl(final Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void onSuccess() {
            logger.info("ResendEmail.onSuccess", "success");
        }

        @Override
        protected void onError(final String result, final String errorMessage, final int httpResponseCode) {
            logger.info("ResendEmail.onError", "error");
            logger.info("ResendEmail.onError", "result: " + result);
            logger.info("ResendEmail.onError", "errorMessage: " + errorMessage);
            logger.info("ResendEmail.onError", "httpResponseCode: " + String.valueOf(httpResponseCode));
        }
    }
}
