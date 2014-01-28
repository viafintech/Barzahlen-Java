package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.requests.resulthandler.TransactionCancelResultHandler;
import de.barzahlen.http.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A cancel transaction request
 */
public class TransactionCancelRequest implements BarzahlenApiRequest {

    private TransactionCancelResultHandler transactionCancelResultHandler;
    private String transactionId;
    private String language;


    public void initialize(String transactionId, String language) {
        this.transactionId = transactionId;
        this.language = language;
    }

    @Override
    public String getPath() {
        return "transactions/cancel";
    }

    @Override
    public List<String> getParametersOrder() {
        List<String> parametersOrder = new ArrayList<>(4);

        parametersOrder.add("shop_id");
        parametersOrder.add("transaction_id");
        parametersOrder.add("language");
        parametersOrder.add("payment_key");

        return parametersOrder;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(2);

        parameters.put("transaction_id", transactionId);
        parameters.put("language", language);

        return parameters;
    }

    public void setTransactionCancelResultHandler(final TransactionCancelResultHandler transactionCancelResultHandler) {
        this.transactionCancelResultHandler = transactionCancelResultHandler;
    }

    @Override
    public void handleResult(final HttpResult result) {
        if (transactionCancelResultHandler != null) {
            transactionCancelResultHandler.handleResult(result);
        }
    }
}
