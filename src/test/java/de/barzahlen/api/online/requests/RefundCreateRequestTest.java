package de.barzahlen.api.online.requests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RefundCreateRequestTest {

    private String transactionId;
    private String amount;
    private String currency;
    private String language;

    private RefundCreateRequest request;

    @Before
    public void setUp() {
        request = new RefundCreateRequest();
        transactionId = "23123";
        amount = "20.1";
        currency = "EUR";
        language = "de";
        request.initialize(transactionId, amount, currency, language);
    }

    @Test
    public void testCorrectPath() {
        assertEquals(request.getPath(), "transactions/refund");
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
        assertEquals(request.getParametersOrder().get(2), "amount");
    }

    @Test
    public void testParametersOrderParameter4() {
        assertEquals(request.getParametersOrder().get(3), "currency");
    }

    @Test
    public void testParametersOrderParameter5() {
        assertEquals(request.getParametersOrder().get(4), "language");
    }

    @Test
    public void testParametersOrderParameter6() {
        assertEquals(request.getParametersOrder().get(5), "payment_key");
    }

    @Test
    public void testParametersContainsCorrectTransactionId() {
        assertEquals(request.getParameters().get("transaction_id"), transactionId);
    }

    @Test
    public void testParametersContainsCorrectAmount() {
        assertEquals(request.getParameters().get("amount"), amount);
    }

    @Test
    public void testParametersContainsCorrectCurrency() {
        assertEquals(request.getParameters().get("currency"), currency);
    }

    @Test
    public void testParametersContainsCorrectLanguage() {
        assertEquals(request.getParameters().get("language"), language);
    }
}
