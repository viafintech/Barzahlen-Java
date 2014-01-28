package de.barzahlen.api.online.integration.notifications;

import de.barzahlen.api.online.BarzahlenNotificationHandler;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.notifications.RefundCompletedNotificationHandler;
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

public class RefundCompletedTest {
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
        notificationRequestHandler.registerNotificationHandler(new RefundCompletedNotificationHandler
                (configuration) {
            @Override
            protected void onRefundCompleted(final String refundTransactionId, final String originTransactionId,
                                             final String shopId, final String customerEmail, final String amount,
                                             final String currency, final String orderId, final String customVar0,
                                             final String customVar1,
                                             final String customVar2) {
                logger.info("RefundCompletedRefund.onRefundCompleted", "onRefundCompleted");
                logger.info("RefundCompletedRefund.onRefundCompleted", "refundTransactionId: "
                        + refundTransactionId);
                logger.info("RefundCompletedRefund.onRefundCompleted", "originTransactionId: "
                        + originTransactionId);
                logger.info("RefundCompletedRefund.onRefundCompleted", "shopId: " + shopId);
                logger.info("RefundCompletedRefund.onRefundCompleted",
                        "customerEmail: " + customerEmail);
                logger.info("RefundCompletedRefund.onRefundCompleted", "amount: " + amount);
                logger.info("RefundCompletedRefund.onRefundCompleted", "currency: " + currency);
                logger.info("RefundCompletedRefund.onRefundCompleted", "orderId: " + orderId);
                logger.info("RefundCompletedRefund.onRefundCompleted", "customVar0: " + customVar0);
                logger.info("RefundCompletedRefund.onRefundCompleted", "customVar1: " + customVar1);
                logger.info("RefundCompletedRefund.onRefundCompleted", "customVar2: " + customVar2);
            }
        });

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("state", "refund_completed");
        requestParameters.put("refund_transaction_id", "1234");
        requestParameters.put("origin_transaction_id", "1235");
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
