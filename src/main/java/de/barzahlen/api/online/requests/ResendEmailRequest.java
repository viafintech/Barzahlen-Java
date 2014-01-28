package de.barzahlen.api.online.requests;

import de.barzahlen.api.online.requests.resulthandler.ResendEmailResultHandler;
import de.barzahlen.http.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A resend email request
 */
public class ResendEmailRequest implements BarzahlenApiRequest {

    private ResendEmailResultHandler resendEmailResultHandler;
    private String transactionId;
    private String language;


    public void initialize(String transactionId, String language) {
        this.transactionId = transactionId;
        this.language = language;
    }

    @Override
    public String getPath() {
        return "transactions/resend_email";
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

    public void setResendEmailResultHandler(final ResendEmailResultHandler resendEmailResultHandler) {
        this.resendEmailResultHandler = resendEmailResultHandler;
    }

    @Override
    public void handleResult(final HttpResult result) {
        if (resendEmailResultHandler != null) {
            resendEmailResultHandler.handleResult(result);
        }
    }
}
