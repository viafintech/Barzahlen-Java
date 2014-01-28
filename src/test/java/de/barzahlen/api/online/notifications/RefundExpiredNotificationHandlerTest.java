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

public class RefundExpiredNotificationHandlerTest {

    @Test
    public void testResponsibleStateIsCorrect() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        RefundExpiredNotificationHandler mockedHandler = spy(new RefundExpiredNotificationHandlerImpl
                (mockedConfiguration));

        assertEquals("refund_expired", mockedHandler.getResponsibleState());
    }

    @Test
    public void testCorrectParametersInHandleRequest() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        RefundExpiredNotificationHandler mockedHandler = spy(new RefundExpiredNotificationHandlerImpl
                (mockedConfiguration));
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "refund_expired");
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
                "3eddf0e4225972740d74a940d82700300cfcdbe9b8b1f33876e8b2bee99e40600d4d56b10abf0b38d510044a43c4b46e6fcb95296570ea489d2b6185fdd533ac");

        when(mockedConfiguration.getNotificationKey()).thenReturn("notification_key");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        mockedHandler.handleRequest(mockedRequest, mockedResponse);

        Mockito.verify(mockedHandler).onRefundExpired(
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

    class RefundExpiredNotificationHandlerImpl extends RefundExpiredNotificationHandler {

        protected RefundExpiredNotificationHandlerImpl(final Configuration configuration) {
            super(configuration);
        }

        @Override
        protected void onRefundExpired(final String refundTransactionId, final String originTransactionId,
                                       final String shopId, final String customerEmail, final String amount,
                                       final String currency, final String orderId, final String customVar0,
                                       final String customVar1, final String customVar2) {

        }

    }
}
