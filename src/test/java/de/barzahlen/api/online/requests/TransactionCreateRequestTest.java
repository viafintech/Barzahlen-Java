package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TransactionCreateRequestTest {

    private Transaction transaction;
    private TransactionCreateRequest request;

    @Before
    public void setUp() {
        request = new TransactionCreateRequest();
        transaction = new Transaction()
                .setCustomerEmail("foo@bar.com")
                .setAmount("10.00")
                .setCurrency("EUR")
                .setOrderId("123")
                .setCustomerStreetNr("Teststr.")
                .setCustomerZipcode("10247")
                .setCustomerCity("Berlin")
                .setCustomerCountry("DE")
                .setLanguage("de");
        request.initialize(transaction);
    }

    @Test
    public void testCorrectPath() {
        assertEquals(request.getPath(), "transactions/create");
    }

    @Test
    public void testParametersOrderParameter1() {
        assertEquals(request.getParametersOrder().get(0), "shop_id");
    }

    @Test
    public void testParametersOrderParameter2() {
        assertEquals(request.getParametersOrder().get(1), "customer_email");
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
        assertEquals(request.getParametersOrder().get(5), "order_id");
    }

    @Test
    public void testParametersOrderParameter7() {
        assertEquals(request.getParametersOrder().get(6), "customer_street_nr");
    }

    @Test
    public void testParametersOrderParameter8() {
        assertEquals(request.getParametersOrder().get(7), "customer_zipcode");
    }

    @Test
    public void testParametersOrderParameter9() {
        assertEquals(request.getParametersOrder().get(8), "customer_city");
    }

    @Test
    public void testParametersOrderParameter10() {
        assertEquals(request.getParametersOrder().get(9), "customer_country");
    }

    @Test
    public void testParametersOrderParameter11() {
        assertEquals(request.getParametersOrder().get(10), "custom_var_0");
    }

    @Test
    public void testParametersOrderParameter12() {
        assertEquals(request.getParametersOrder().get(11), "custom_var_1");
    }

    @Test
    public void testParametersOrderParameter13() {
        assertEquals(request.getParametersOrder().get(12), "custom_var_2");
    }

    @Test
    public void testParametersOrderParameter14() {
        assertEquals(request.getParametersOrder().get(13), "payment_key");
    }

    @Test
    public void testParametersContainsCorrectCustomerEmail() {
        assertEquals(request.getParameters().get("customer_email"), transaction.getCustomerEmail());
    }

    @Test
    public void testParametersContainsCorrectAmount() {
        assertEquals(request.getParameters().get("amount"), transaction.getAmount());
    }

    @Test
    public void testParametersContainsCorrectCurrency() {
        assertEquals(request.getParameters().get("currency"), transaction.getCurrency());
    }

    @Test
    public void testParametersContainsCorrectLanguage() {
        assertEquals(request.getParameters().get("language"), transaction.getLanguage());
    }

    @Test
    public void testParametersContainsCorrectOrderId() {
        assertEquals(request.getParameters().get("order_id"), transaction.getOrderId());
    }

    @Test
    public void testParametersContainsCorrectCustomerStreetNr() {
        assertEquals(request.getParameters().get("customer_street_nr"), transaction.getCustomerStreetNr());
    }

    @Test
    public void testParametersContainsCorrectCustomerZipCode() {
        assertEquals(request.getParameters().get("customer_zipcode"), transaction.getCustomerZipcode());
    }

    @Test
    public void testParametersContainsCorrectCustomerCity() {
        assertEquals(request.getParameters().get("customer_city"), transaction.getCustomerCity());
    }

    @Test
    public void testParametersContainsCorrectCustomerCountry() {
        assertEquals(request.getParameters().get("customer_country"), transaction.getCustomerCountry());
    }

    @Test
    public void testParametersContainsCorrectCustomerVar0() {
        assertEquals(request.getParameters().get("custom_var_0"), transaction.getCustomVar0());
    }

    @Test
    public void testParametersContainsCorrectCustomerVar1() {
        assertEquals(request.getParameters().get("custom_var_1"), transaction.getCustomVar1());
    }

    @Test
    public void testParametersContainsCorrectCustomerVar2() {
        assertEquals(request.getParameters().get("custom_var_2"), transaction.getCustomVar2());
    }
}
