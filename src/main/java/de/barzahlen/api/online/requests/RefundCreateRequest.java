package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.requests.resulthandler.RefundCreateResultHandler;
import de.barzahlen.http.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundCreateRequest implements BarzahlenApiRequest {

    private RefundCreateResultHandler refundCreateResultHandler;
    private String transactionId;
    private String amount;
    private String currency;
    private String language;


    public void initialize(String transactionId, String amount, String currency, String language) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.language = language;
    }

    @Override
    public String getPath() {
        return "transactions/refund";
    }

    @Override
    public List<String> getParametersOrder() {
        List<String> parametersOrder = new ArrayList<>(6);

        parametersOrder.add("shop_id");
        parametersOrder.add("transaction_id");
        parametersOrder.add("amount");
        parametersOrder.add("currency");
        parametersOrder.add("language");
        parametersOrder.add("payment_key");

        return parametersOrder;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(4);

        parameters.put("transaction_id", transactionId);
        parameters.put("amount", amount);
        parameters.put("currency", currency);
        parameters.put("language", language);

        return parameters;
    }

    public void setRefundCreateResultHandler(final RefundCreateResultHandler refundCreateResultHandler) {
        this.refundCreateResultHandler = refundCreateResultHandler;
    }

    @Override
    public void handleResult(final HttpResult result) {
        if (refundCreateResultHandler != null) {
            refundCreateResultHandler.handleResult(result);
        }
    }
}
