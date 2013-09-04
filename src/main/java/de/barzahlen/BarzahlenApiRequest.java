package de.barzahlen;

import de.barzahlen.request.xml.XMLInfo;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Scanner;

public class BarzahlenApiRequest {
    private String targetUrl;

    private HttpsURLConnection httpCon;
    private XMLInfo xmlInfo;

    private int responseCode;
    private String responseMessage;
    private String result;

    public BarzahlenApiRequest(String targetUrl, XMLInfo xmlInfo) {
        this.targetUrl = targetUrl;
        this.xmlInfo = xmlInfo;
    }

    public boolean doRequest(String parameters) throws Exception {
        initConnection();
        transferParameters(parameters);
        transferResult();

        return xmlInfo.readXMLFile(result, responseCode);
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
