package de.barzahlen.api.online.requests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionCancelRequestTest {

    private String transactionId;
    private String language;

    private TransactionCancelRequest request;

    @Before
    public void setUp() {
        request = new TransactionCancelRequest();
        transactionId = "23123";
        language = "de";
        request.initialize(transactionId, language);
    }

    @Test
    public void testCorrectPath() {
        assertEquals(request.getPath(), "transactions/cancel");
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
        assertEquals(request.getParametersOrder().get(2), "language");
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
    public void testParametersContainsCorrectLanguage() {
        assertEquals(request.getParameters().get("language"), language);
    }
}
