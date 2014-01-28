package de.barzahlen.api.online.notifications;

import de.barzahlen.api.online.Configuration;
import de.barzahlen.http.requests.HttpRequest;
import de.barzahlen.http.responses.HttpResponse;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RefundCompletedNotificationHandlerTest {

    @Test
    public void testResponsibleStateIsCorrect() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        RefundCompletedNotificationHandler mockedHandler = spy(new RefundCompletedNotificationHandlerImpl
                (mockedConfiguration));

        assertEquals("refund_completed", mockedHandler.getResponsibleState());
    }

    @Test
    public void testCorrectParametersInHandleRequest() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        RefundCompletedNotificationHandler mockedHandler = spy(new RefundCompletedNotificationHandlerImpl
                (mockedConfiguration));
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "refund_completed");
        parameters.put("transaction_id", "transaction_id");
        parameters.put("shop_id", "shop_id");
        parameters.put("customer_email", "customer_email");
        parameters.put("amount", "amount");
        parameters.put("currency", "currency");
        parameters.put("order_id", "order_id");
        parameters.put("custom_var_0", "custom_var_0");
        parameters.put("custom_var_1", "custom_var_1");
        parameters.put("custom_var_2", "custom_var_2");
        parameters.put("hash",
                "f7a22b2bfa2bdc66b3fefba0e942dea86759a294dcb1ce3612bc1bf9dc0866d52c7eb4cef3a221d0aad319e96d67000c4a9671fb0c44448d73128911f8105a01");

        when(mockedConfiguration.getNotificationKey()).thenReturn("notification_key");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        mockedHandler.handleRequest(mockedRequest, mockedResponse);

        Mockito.verify(mockedHandler).onRefundCompleted(
                parameters.get("refund_transaction_id"),
                parameters.get("origin_transaction_id"),
                parameters.get("shop_id"),
                parameters.get("customer_email"),
                parameters.get("amount"),
                parameters.get("currency"),
                parameters.get("order_id"),
                parameters.get("custom_var_0"),
                parameters.get("custom_var_1"),
                parameters.get("custom_var_2")
        );
    }

    class RefundCompletedNotificationHandlerImpl extends RefundCompletedNotificationHandler {

        protected RefundCompletedNotificationHandlerImpl(final Configuration configuration) {
            super(configuration);
        }

        @Override
        protected void onRefundCompleted(final String refundTransactionId, final String originTransactionId,
                                         final String shopId, final String customerEmail, final String amount,
                                         final String currency, final String orderId, final String customVar0,
                                         final String customVar1, final String customVar2) {

        }

    }
}
