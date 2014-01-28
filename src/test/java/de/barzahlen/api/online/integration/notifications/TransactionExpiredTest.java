package de.barzahlen.api.online.integration.notifications;

import de.barzahlen.api.online.BarzahlenNotificationHandler;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.notifications.TransactionExpiredNotificationHandler;
import de.barzahlen.http.requests.DummyRequest;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.DummyResponse;
import de.barzahlen.http.responses.HttpResponse;
import de.barzahlen.logger.Logger;
import de.barzahlen.logger.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TransactionExpiredTest {
    private static final String SHOP_ID = "";
    private static final String PAYMENT_KEY = "";
    private static final String NOTIFICATION_KEY = "";
    private static final boolean SANDBOX_MODE = true;

    @Ignore
    @Test
    public void test() {
        final Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE);

        Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);

        BarzahlenNotificationHandler notificationRequestHandler = new BarzahlenNotificationHandler(configuration);
        notificationRequestHandler.registerNotificationHandler(new TransactionExpiredNotificationHandler
                (configuration) {
            @Override
            protected void onTransactionExpired(final String transactionId, final String shopId,
                                                final String customerEmail, final String amount, final String currency,
                                                final String orderId, final String customVar0, final String customVar1,
                                                final String customVar2) {
                logger.info("TransactionExpiredRefund.onTransactionExpired", "onTransactionExpired");
                logger.info("TransactionExpiredRefund.onTransactionExpired", "transactionId: "
                        + transactionId);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "shopId: " + shopId);
                logger.info("TransactionExpiredRefund.onTransactionExpired",
                        "customerEmail: " + customerEmail);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "amount: " + amount);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "currency: " + currency);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "orderId: " + orderId);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "customVar0: " + customVar0);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "customVar1: " + customVar1);
                logger.info("TransactionExpiredRefund.onTransactionExpired", "customVar2: " + customVar2);
            }
        });

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("state", "expired");
        requestParameters.put("transaction_id", "123");
        requestParameters.put("shop_id", SHOP_ID);
        requestParameters.put("customer_email", "foo@example.com");
        requestParameters.put("amount", "10.00");
        requestParameters.put("currency", "EUR");
        requestParameters.put("order_id", "123");
        requestParameters.put("hash", "");

        HttpRequest httpRequest = new DummyRequest(requestParameters);
        HttpResponse httpResponse = new DummyResponse();

        notificationRequestHandler.handleRequest(httpRequest, httpResponse);
    }
}
