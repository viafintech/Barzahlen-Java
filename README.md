# Barzahlen Payment Module Java SDK (v2.0.1)

## Copyright
(c) 2014 Cash Payment Solutions GmbH

https://www.cashpaymentsolutions.com/de/

https://www.barzahlen.de/

## Preparation

### Merchant Data
The merchant credentials, which are necessary to handle payments with Barzahlen, can be received at https://partner.barzahlen.de. After a successful registration a shop ID is assigned to you as well as a payment and a notification key. Furthermore you can set you callback URL, which is used to send updates on payment and refund transactions.

## Usage
Requests errors are able to throw exceptions, so it's recommended to enclose them in a try/catch block, so the errors can be managed properly.

You can find examples how to use the sdk under src/main/java/test/de/barzahlen/api/online/integration

If you want to use another HttpClient than HttpsURLConnection, you can create your own and implement the interface HttpClient.

## Logger

There is a logger that puts all messages to the console. If you want to use your own, you can implement the interface Logger.

```java
Logger logger = LoggerFactory.getLogger(LoggerFactory.LoggerType.CONSOLE); // Get the console Logger
```

## HttpClient

If you want to use another HttpClient than HttpsURLConnection, you can create your own and implement the interface HttpClient.

For logging all HttpRequests you can use the LoggingClient. It has two parameters: the httpClient and the logger

```java
HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection); // Get the HttpClient
HttpClient loggingClient = new LoggingClient(httpClient, logger); // Create LoggingClient
```

### Requests
You can send requests by create an instance of a Request class and pass it to the handle method of the BarzahlenApi Class.

```java
HttpClient httpClient = HttpClientFactory.getHttpClient(HttpClientFactory.HttpClientType.HttpsURLConnection);
Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
BarzahlenApi barzahlenApi = new BarzahlenApi(httpClient, configuration);

TransactionCreateRequest createRequest = new TransactionCreateRequest();
createRequest.setTransactionCreateResultHandler(new TransactionCreateResultHandler() {
    @Override
    protected void onSuccess(final String transactionId, final String paymentSlipLink, final String expirationNotice, final String infotext1, final String infotext2) {

    }

    @Override
    protected void onError(final String result, final String errorMessage, final int httpResponseCode) {

    }
})
createRequest.initialize(transaction);
barzahlenApi.handle(createRequest);
```

### Notifications
For using notifications, you must implement HttpRequest and HttpResponse and pass it to BarzahlenNotificationHandler.handleRequest. Here is an example for servlets:

```java
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
```

```java
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
```

## Support
The Barzahlen Team will happily assist you with any problems or questions.

Send us an email to support@barzahlen.de or use the contact form at https://www.barzahlen.de/partner/integration.