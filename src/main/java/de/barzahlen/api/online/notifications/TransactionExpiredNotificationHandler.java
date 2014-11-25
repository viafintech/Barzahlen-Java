package de.barzahlen.api.online.notifications;

import de.barzahlen.api.online.Configuration;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;

import java.util.Map;

/**
 * Handles transaction expired request
 */
abstract public class TransactionExpiredNotificationHandler extends TransactionNotificationHandler {

    public TransactionExpiredNotificationHandler(final Configuration configuration) {
        super(configuration);
    }

    /**
     * Will be called on Transaction expired event
     *
     * @param transactionId
     * @param shopId
     * @param customerEmail
     * @param amount
     * @param currency
     * @param orderId
     * @param customVar0
     * @param customVar1
     * @param customVar2
     */
    abstract protected void onTransactionExpired(final String transactionId, final String shopId,
                                                 final String customerEmail, final String amount, final String currency,
                                                 final String orderId, final String customVar0, final String customVar1,
                                                 final String customVar2);

    @Override
    public void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        Map<String, String> parameters = httpRequest.getParameters();

        validateRequest(parameters);

        onTransactionExpired(parameters.get("transaction_id"), parameters.get("shop_id"),
                parameters.get("customer_email"), parameters.get("amount"), parameters.get("currency"),
                parameters.get("order_id"), parameters.get("custom_var_0"), parameters.get("custom_var_1"),
                parameters.get("custom_var_2"));
    }

    public String getResponsibleState() {
        return "expired";
    }
}
