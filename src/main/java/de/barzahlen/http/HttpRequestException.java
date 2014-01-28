package de.barzahlen.http;

/**
 * Thrown when a error occurs while handling a request
 */
public class HttpRequestException extends RuntimeException {
    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
