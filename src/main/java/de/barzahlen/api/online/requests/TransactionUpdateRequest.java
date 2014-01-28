package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.requests.resulthandler.TransactionUpdateResultHandler;
import de.barzahlen.http.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionUpdateRequest implements BarzahlenApiRequest {

    private TransactionUpdateResultHandler transactionUpdateResultHandler;
    private String transactionId;
    private String orderId;


    public void initialize(String transactionId, String orderId) {
        this.transactionId = transactionId;
        this.orderId = orderId;
    }

    @Override
    public String getPath() {
        return "transactions/update";
    }

    @Override
    public List<String> getParametersOrder() {
        List<String> parametersOrder = new ArrayList<>(4);

        parametersOrder.add("shop_id");
        parametersOrder.add("transaction_id");
        parametersOrder.add("order_id");
        parametersOrder.add("payment_key");

        return parametersOrder;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(3);

        parameters.put("transaction_id", transactionId);
        parameters.put("order_id", orderId);

        return parameters;
    }

    public void setTransactionUpdateResultHandler(final TransactionUpdateResultHandler transactionUpdateResultHandler) {
        this.transactionUpdateResultHandler = transactionUpdateResultHandler;
    }

    @Override
    public void handleResult(final HttpResult result) {
        if (transactionUpdateResultHandler != null) {
            transactionUpdateResultHandler.handleResult(result);
        }
    }
}
