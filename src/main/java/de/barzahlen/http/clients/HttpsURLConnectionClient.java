package de.barzahlen.http.clients;

import de.barzahlen.http.HttpRequestException;
import de.barzahlen.http.HttpResult;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

/**
 * Use a HttpsURLConnection for requesting
 */
public class HttpsURLConnectionClient implements HttpClient {

    private HttpsURLConnection httpCon;

    @Override
    public HttpResult post(final String targetUrl, final Map<String, String> parameters) {
        return post(targetUrl, buildParametersString(parameters));
    }

    @Override
    public HttpResult post(final String targetUrl, final String parameters) {
        try {
            InputStream resultStream;
            int responseCode;
            String responseMessage;

            httpCon = (HttpsURLConnection) new URL(targetUrl).openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);

            transferParameters(parameters);

            responseCode = httpCon.getResponseCode();
            responseMessage = httpCon.getResponseMessage();

            if (responseCode == 200) {
                resultStream = httpCon.getInputStream();
            } else {
                resultStream = httpCon.getErrorStream();
            }

            return new HttpResult(responseCode, responseMessage, resultStream);
        } catch (IOException e) {
            throw new HttpRequestException("Could not read request result", e);
        }
    }

    private void transferParameters(final String parameters) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        out.write(parameters);
        out.flush();
        out.close();
    }

    private String buildParametersString(final Map<String, String> parameters) {
        StringBuilder parametersStringBuilder = new StringBuilder();
        String parametersString = "";

        if (parameters.size() > 0) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                parametersStringBuilder
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            parametersString = parametersStringBuilder.toString();
            parametersString = parametersString.substring(0, parametersStringBuilder.toString().length() - 1);
        }

        return parametersString;
    }
}
