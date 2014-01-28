package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.Transaction;
import de.barzahlen.api.online.requests.resulthandler.TransactionCreateResultHandler;
import de.barzahlen.http.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A create transaction request
 */
public class TransactionCreateRequest implements BarzahlenApiRequest {

    private TransactionCreateResultHandler transactionCreateResultHandler;
    private Transaction transaction;


    public void initialize(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String getPath() {
        return "transactions/create";
    }

    @Override
    public List<String> getParametersOrder() {
        List<String> parametersOrder = new ArrayList<>(14);

        parametersOrder.add("shop_id");
        parametersOrder.add("customer_email");
        parametersOrder.add("amount");
        parametersOrder.add("currency");
        parametersOrder.add("language");
        parametersOrder.add("order_id");
        parametersOrder.add("customer_street_nr");
        parametersOrder.add("customer_zipcode");
        parametersOrder.add("customer_city");
        parametersOrder.add("customer_country");
        parametersOrder.add("custom_var_0");
        parametersOrder.add("custom_var_1");
        parametersOrder.add("custom_var_2");
        parametersOrder.add("payment_key");

        return parametersOrder;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(12);

        parameters.put("customer_email", transaction.getCustomerEmail());
        parameters.put("amount", transaction.getAmount());
        parameters.put("currency", transaction.getCurrency());
        parameters.put("order_id", transaction.getOrderId());
        parameters.put("customer_street_nr", transaction.getCustomerStreetNr());
        parameters.put("customer_zipcode", transaction.getCustomerZipcode());
        parameters.put("customer_city", transaction.getCustomerCity());
        parameters.put("customer_country", transaction.getCustomerCountry());
        parameters.put("language", transaction.getLanguage());
        parameters.put("custom_var_0", transaction.getCustomVar0());
        parameters.put("custom_var_1", transaction.getCustomVar1());
        parameters.put("custom_var_2", transaction.getCustomVar2());

        return parameters;
    }

    public void setTransactionCreateResultHandler(final TransactionCreateResultHandler transactionCreateResultHandler) {
        this.transactionCreateResultHandler = transactionCreateResultHandler;
    }

    @Override
    public void handleResult(final HttpResult result) {
        if (transactionCreateResultHandler != null) {
            transactionCreateResultHandler.handleResult(result);
        }
    }
}
