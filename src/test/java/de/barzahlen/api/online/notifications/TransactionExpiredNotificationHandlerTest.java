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

public class TransactionExpiredNotificationHandlerTest {

    @Test
    public void testResponsibleStateIsCorrect() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        TransactionExpiredNotificationHandler mockedHandler = spy(new TransactionExpiredNotificationHandlerImpl
                (mockedConfiguration));

        assertEquals("expired", mockedHandler.getResponsibleState());
    }

    @Test
    public void testCorrectParametersInHandleRequest() {
        Configuration mockedConfiguration = Mockito.mock(Configuration.class);
        TransactionExpiredNotificationHandler mockedHandler = spy(new TransactionExpiredNotificationHandlerImpl
                (mockedConfiguration));
        HttpRequest mockedRequest = Mockito.mock(HttpRequest.class);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("state", "expired");
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
                "1c99c040ea0e3f26e861dc570b9fd0666c4664d29d81704287b659b904581d767764eda7decec367105840505325505bb69af455129da4d79b88d1356c39ac26");

        when(mockedConfiguration.getNotificationKey()).thenReturn("notification_key");
        when(mockedRequest.getParameters()).thenReturn(parameters);

        mockedHandler.handleRequest(mockedRequest, mockedResponse);

        Mockito.verify(mockedHandler).onTransactionExpired(
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

    class TransactionExpiredNotificationHandlerImpl extends TransactionExpiredNotificationHandler {

        protected TransactionExpiredNotificationHandlerImpl(final Configuration configuration) {
            super(configuration);
        }

        @Override
        protected void onTransactionExpired(final String transactionId, final String shopId, final String customerEmail,
                                            final String amount, final String currency, final String orderId,
                                            final String customVar0, final String customVar1, final String customVar2) {

        }
    }
}
