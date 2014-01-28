package de.barzahlen.api.online.notifications;

import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;

/**
 * Notification from Barzahlen
 */
public interface NotificationHandler {
    /**
     * Handles the httpRequest
     *
     * @param httpRequest
     * @param httpResponse
     */
    public void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse);

    /**
     * Returns the state for which the handler is responsible
     *
     * @return state
     */
    public String getResponsibleState();
}
