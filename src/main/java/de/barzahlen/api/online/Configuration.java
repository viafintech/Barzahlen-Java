package de.barzahlen.api.online;

/**
 * Configuration for the Barzahlen API
 */
public class Configuration {

    private final static String PRODUCTION_URL = "https://api.barzahlen.de/v1/";
    private final static String SANDBOX_URL = "https://api-sandbox.barzahlen.de/v1/";

    private boolean sandboxMode;
    private String shopId;
    private String paymentKey;
    private String notificationKey;

    public Configuration() {
    }

    public Configuration(boolean sandboxMode, String shopId, String paymentKey, String notificationKey) {
        this.sandboxMode = sandboxMode;
        this.shopId = shopId;
        this.paymentKey = paymentKey;
        this.notificationKey = notificationKey;
    }

    public String getBaseUrl() {
        if (sandboxMode) {
            return SANDBOX_URL;
        } else {
            return PRODUCTION_URL;
        }
    }

    public boolean isSandboxMode() {
        return sandboxMode;
    }

    public String getShopId() {
        return shopId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public String getNotificationKey() {
        return notificationKey;
    }
}
