package de.barzahlen.api.online;

import de.barzahlen.api.online.notifications.NotificationHandler;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles incoming notifications and delegate them to the responsible handler
 */
public class BarzahlenNotificationHandler {

    private final Configuration configuration;
    private final Map<String, NotificationHandler> notificationHandlers;


    public BarzahlenNotificationHandler(Configuration configuration) {
        this.configuration = configuration;
        this.notificationHandlers = new HashMap<>();
    }

    /**
     * Register notificationHandler
     *
     * @param notificationHandler
     */
    public void registerNotificationHandler(final NotificationHandler notificationHandler) {
        notificationHandlers.put(notificationHandler.getResponsibleState(), notificationHandler);
    }

    /**
     * Delegate request to responsible handler
     *
     * @param httpRequest
     * @param httpResponse
     */
    public void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        NotificationHandler responsibleHandler = notificationHandlers.get(httpRequest.getParameters().get("state"));
        if (responsibleHandler != null) {
            responsibleHandler.handleRequest(httpRequest, httpResponse);
        } else {
            throw new BarzahlenNotificationNotSuccessfulException("Could not find responsible handler");
        }
    }
}
