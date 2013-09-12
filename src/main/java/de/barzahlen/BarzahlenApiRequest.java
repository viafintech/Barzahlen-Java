package de.barzahlen;

import de.barzahlen.response.ErrorResponse;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Scanner;

public class BarzahlenApiRequest {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BarzahlenApiRequest.class);

	private final String targetUrl;

	private Class responseClass = null;
	private Object response = null;

	private HttpsURLConnection httpCon;

	private int responseCode;
	private String responseMessage;
	private String result;

	public BarzahlenApiRequest(String targetUrl, Class responseClass) {
		this.targetUrl = targetUrl;
		this.responseClass = responseClass;
	}

	public boolean doRequest(String parameters) throws Exception {
		initConnection();
		transferParameters(parameters);
		transferResult();

		Serializer serializer = new Persister();

		if (responseCode == 200) {
			response = serializer.read(responseClass, result);

			return true;
		} else {
			// invoke error response bean
			response = serializer.read(ErrorResponse.class, result);

			return false;
		}
	}

	private void transferResult() throws IOException {
		responseCode = httpCon.getResponseCode();
		responseMessage = httpCon.getResponseMessage();

		InputStream resultStream;

		if (responseCode == 200) {
			resultStream = httpCon.getInputStream();
		} else {
			resultStream = httpCon.getErrorStream();
		}

		Scanner s = new Scanner(resultStream).useDelimiter("\\A");
		result = s.hasNext() ? s.next() : "";

		resultStream.close();
	}

	public Object getResponse() {
		return response;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public String getResult() {
		return this.result;
	}

	private void transferParameters(String parameters) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(parameters);
		out.flush();
		out.close();
	}

	private void initConnection() throws IOException {
		httpCon = (HttpsURLConnection) new URL(targetUrl).openConnection();
		httpCon.setRequestMethod("POST");
		httpCon.setDoOutput(true);
		httpCon.setDoInput(true);
		httpCon.setUseCaches(false);
	}
}
