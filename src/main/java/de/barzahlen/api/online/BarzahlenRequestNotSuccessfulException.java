package de.barzahlen.api.online;

public class BarzahlenRequestNotSuccessfulException extends RuntimeException {
    public BarzahlenRequestNotSuccessfulException(String message, Throwable cause) {
        super(message, cause);
    }
}
