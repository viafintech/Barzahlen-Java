package de.barzahlen.api.online;

import de.barzahlen.api.online.requests.BarzahlenApiRequest;
import de.barzahlen.http.HttpRequestException;
import de.barzahlen.http.HttpResult;
import de.barzahlen.http.clients.HttpClient;
import de.barzahlen.util.MapUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles request and sends them to the Barzahlen-API
 */
public class BarzahlenApi {

    private final HttpClient httpClient;
    private final Configuration configuration;


    public BarzahlenApi(final HttpClient httpClient, final Configuration configuration) {
        this.httpClient = httpClient;
        this.configuration = configuration;
    }

    /**
     * Handles a request
     *
     * @param request
     */
    public void handle(final BarzahlenApiRequest request) {
        try {
            HttpResult result = httpClient.post(
                    buildTargetUrl(request),
                    buildParameters(request)
            );

            request.handleResult(result);
        } catch (HttpRequestException e) {
            throw new BarzahlenRequestNotSuccessfulException("Could not send request", e);
        }
    }

    private String buildTargetUrl(final BarzahlenApiRequest request) {
        return configuration.getBaseUrl() + request.getPath();
    }

    private Map<String, String> buildParameters(final BarzahlenApiRequest request) {
        Map<String, String> parameters = new LinkedHashMap<>(request.getParameters());

        parameters.put("shop_id", configuration.getShopId());
        parameters.put("hash", buildHash(request.getParametersOrder(), parameters));

        return MapUtil.removeNullValues(parameters);
    }

    private String buildHash(final List<String> parametersOrder, final Map<String, String> parameters) {
        HashGenerator hashGenerator = new HashGenerator(parametersOrder, "paymentKey");
        return hashGenerator.generateHash(configuration.getPaymentKey(), parameters);
    }
}
