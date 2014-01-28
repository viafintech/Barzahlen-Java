package de.barzahlen.api.online;

public class BarzahlenNotificationNotSuccessfulException extends RuntimeException {
    public BarzahlenNotificationNotSuccessfulException(String message) {
        super(message);
    }

    public BarzahlenNotificationNotSuccessfulException(String message, Throwable cause) {
        super(message, cause);
    }
}
