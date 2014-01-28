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

public class TransactionPaidNotificationHandlerTest {

    @Test
    public void testResponsibleStateIsCorrect() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        TransactionPaidNotificationHandler mockedHandler = spy(new TransactionPaidNotificationHandlerImpl
                (mockedConfiguration));

        assertEquals("paid", mockedHandler.getResponsibleState());
    }

    @Test
    public void testCorrectParametersInHandleRequest() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        TransactionPaidNotificationHandler mockedHandler = spy(new TransactionPaidNotificationHandlerImpl
                (mockedConfiguration));
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "paid");
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
                "249d21d6fea932cc0ae7252c695cf2720b8e9d17adb13090ecfcaa3c996d049259152956b17bc4ceba28790e573c630ff0eddc888087effd29505e0dde7a4c3f");

        when(mockedConfiguration.getNotificationKey()).thenReturn("notification_key");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        mockedHandler.handleRequest(mockedRequest, mockedResponse);

        Mockito.verify(mockedHandler).onTransactionPaid(
                parameters.get("transaction_id"),
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

    class TransactionPaidNotificationHandlerImpl extends TransactionPaidNotificationHandler {

        protected TransactionPaidNotificationHandlerImpl(final Configuration configuration) {
            super(configuration);
        }

        @Override
        protected void onTransactionPaid(final String transactionId, final String shopId, final String customerEmail,
                                         final String amount, final String currency, final String orderId,
                                         final String customVar0, final String customVar1, final String customVar2) {

        }


    }
}
