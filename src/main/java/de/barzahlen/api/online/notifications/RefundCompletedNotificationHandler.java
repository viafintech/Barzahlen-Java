package de.barzahlen.api.online.notifications;

import de.barzahlen.api.online.Configuration;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;

import java.util.Map;

/**
 * Handles refund completed request
 */
abstract public class RefundCompletedNotificationHandler extends RefundNotificationHandler {

    public RefundCompletedNotificationHandler(final Configuration configuration) {
        super(configuration);
    }

    /**
     * Will be called on Refund completed event
     *
     * @param refundTransactionId
     * @param originTransactionId
     * @param shopId
     * @param customerEmail
     * @param amount
     * @param currency
     * @param orderId
     * @param customVar0
     * @param customVar1
     * @param customVar2
     */
    abstract protected void onRefundCompleted(final String refundTransactionId, final String originTransactionId,
                                              final String shopId, final String customerEmail, final String amount,
                                              final String currency, final String orderId, final String customVar0,
                                              final String customVar1,
                                              final String customVar2);

    @Override
    public void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        Map<String, String> parameters = httpRequest.getParameters();

        validateRequest(parameters);

        onRefundCompleted(parameters.get("refund_transaction_id"), parameters.get("origin_transaction_id"),
                parameters.get("shop_id"), parameters.get("customer_email"), parameters.get("amount"),
                parameters.get("currency"), parameters.get("order_id"), parameters.get("custom_var_0"),
                parameters.get("custom_var_1"), parameters.get("custom_var_2"));
    }

    public String getResponsibleState() {
        return "refund_completed";
    }
}
