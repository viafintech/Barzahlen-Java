# Barzahlen Payment Module Java SDK (v2.0.0)

## Copyright
(c) 2014, Zerebro Internet GmbH
http://www.barzahlen.de

## Preparation

### Merchant Data
The merchant credentials, which are necessary to handle payments with Barzahlen, can be received athttps://partner.barzahlen.de. After a successful registration a shop ID is assigned to you as well as a payment and a notification key. Furthermore you can set you callback URL, which is used to send updates on payment and refund transactions.

## Usage
Requests errors are able to throw exceptions, so it's recommended to enclose them in a try/catch block, so the errors can be managed properly.

You can find examples how to use the sdk under sdk/test/de/barzahlen/api/online/integration

If you want to use another HttpClient than HttpsURLConnection, you can create your own and implement the interface HttpClient.

### Notifications
For using notifications, you must implement HttpRequest and HttpResponse and pass it to BarzahlenNotificationHandler .handleRequest. Here is an example for servlets:

public class ServletRequest implements HttpRequest {

    private HttpServletRequest httpServletRequest;

    @Override
    public String getPath() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public Map<String, String> getParameters() {
        return httpServletRequest.getParameterMap();
    }
}

public class ServletResponse implements HttpResponse {

    private HttpServletResponse httpServletResponse;

    @Override
    public OutputStream getOutputStream() {
        return httpServletResponse.getOutputStream();
    }

    @Override
    public void setHeader(final String name, final String value) {
        return httpServletResponse.setHeader(name, value);
    }
}


## Support
The Barzahlen Team will happily assist you with any problems or questions.

Send us an email to support@barzahlen.de or use the contact form at http://www.barzahlen.de/partner/integration.