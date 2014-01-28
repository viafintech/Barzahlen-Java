package de.barzahlen.api.online;

import de.barzahlen.api.online.notifications.NotificationHandler;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BarzahlenNotificationHandlerTest {
    @Test
    public void testNotificationWillBeHandled() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        NotificationHandler mockedHandler = Mockito.mock(NotificationHandler.class);
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "foo");

        when(mockedHandler.getResponsibleState()).thenReturn("foo");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        BarzahlenNotificationHandler notificationRequestHandler = new BarzahlenNotificationHandler(mockedConfiguration);
        notificationRequestHandler.registerNotificationHandler(mockedHandler);
        notificationRequestHandler.handleRequest(mockedRequest, mockedResponse);

        verify(mockedHandler).handleRequest(mockedRequest, mockedResponse);
    }

    @Test
    public void testCorrectNotificationHandlerWillBeCalled() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        NotificationHandler mockedHandler1 = Mockito.mock(NotificationHandler.class);
        NotificationHandler mockedHandler2 = Mockito.mock(NotificationHandler.class);
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "foo");

        when(mockedHandler1.getResponsibleState()).thenReturn("foo");
        when(mockedHandler2.getResponsibleState()).thenReturn("bar");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        BarzahlenNotificationHandler notificationRequestHandler = new BarzahlenNotificationHandler(mockedConfiguration);
        notificationRequestHandler.registerNotificationHandler(mockedHandler1);
        notificationRequestHandler.handleRequest(mockedRequest, mockedResponse);

        verify(mockedHandler1).handleRequest(mockedRequest, mockedResponse);
    }

    @Test(expected = BarzahlenNotificationNotSuccessfulException.class)
    public void testExceptionIsThrownOnNotExistentHandler() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        NotificationHandler mockedHandler1 = Mockito.mock(NotificationHandler.class);
        NotificationHandler mockedHandler2 = Mockito.mock(NotificationHandler.class);
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "xxx");

        when(mockedHandler1.getResponsibleState()).thenReturn("foo");
        when(mockedHandler2.getResponsibleState()).thenReturn("bar");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        BarzahlenNotificationHandler notificationRequestHandler = new BarzahlenNotificationHandler(mockedConfiguration);
        notificationRequestHandler.registerNotificationHandler(mockedHandler1);
        notificationRequestHandler.handleRequest(mockedRequest, mockedResponse);

        verify(mockedHandler1).handleRequest(mockedRequest, mockedResponse);
    }
}
