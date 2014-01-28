package de.barzahlen.api.online.requests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionUpdateRequestTest {

    private String transactionId;
    private String orderId;

    private TransactionUpdateRequest request;

    @Before
    public void setUp() {
        request = new TransactionUpdateRequest();
        transactionId = "23123";
        orderId = "34234";
        request.initialize(transactionId, orderId);
    }

    @Test
    public void testCorrectPath() {
        assertEquals(request.getPath(), "transactions/update");
    }

    @Test
    public void testParametersOrderParameter1() {
        assertEquals(request.getParametersOrder().get(0), "shop_id");
    }

    @Test
    public void testParametersOrderParameter2() {
        assertEquals(request.getParametersOrder().get(1), "transaction_id");
    }

    @Test
    public void testParametersOrderParameter3() {
        assertEquals(request.getParametersOrder().get(2), "order_id");
    }

    @Test
    public void testParametersOrderParameter4() {
        assertEquals(request.getParametersOrder().get(3), "payment_key");
    }

    @Test
    public void testParametersContainsCorrectTransactionId() {
        assertEquals(request.getParameters().get("transaction_id"), transactionId);
    }

    @Test
    public void testParametersContainsCorrectOrderId() {
        assertEquals(request.getParameters().get("order_id"), orderId);
    }
}
