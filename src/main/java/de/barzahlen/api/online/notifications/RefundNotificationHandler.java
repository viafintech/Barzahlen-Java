package de.barzahlen.api.online.notifications;

import de.barzahlen.api.online.BarzahlenNotificationNotSuccessfulException;
import de.barzahlen.api.online.Configuration;
import de.barzahlen.api.online.HashGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parent for refund related notifications
 */
abstract public class RefundNotificationHandler implements NotificationHandler {

    private final Configuration configuration;

    protected RefundNotificationHandler(final Configuration configuration) {
        this.configuration = configuration;
    }

    protected void validateRequest(final Map<String, String> parameters) {
        List<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("state");
        requiredParameters.add("refund_transaction_id");
        requiredParameters.add("origin_transaction_id");
        requiredParameters.add("shop_id");
        requiredParameters.add("customer_email");
        requiredParameters.add("amount");
        requiredParameters.add("currency");
        requiredParameters.add("origin_order_id");
        requiredParameters.add("custom_var_0");
        requiredParameters.add("custom_var_1");
        requiredParameters.add("custom_var_2");
        requiredParameters.add("notificationKey");

        HashGenerator hashGenerator = new HashGenerator(requiredParameters, "notificationKey");
        String hash = hashGenerator.generateHash(configuration.getNotificationKey(), parameters);

        if (!hash.equals(parameters.get("hash"))) {
            throw new BarzahlenNotificationNotSuccessfulException("received hash don't equals calculated hash");
        }
    }
}
