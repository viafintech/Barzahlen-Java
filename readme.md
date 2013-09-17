# Barzahlen Payment Module Java SDK (v1.0.4)

## Preparation

### Merchant Data
The merchant credentials, which are necessary to handle payments with Barzahlen, can be received at https://partner.barzahlen.de. After a successful registration a shop ID is assigned to you as well as a payment and a notification key. Furthermore you can set you callback URL, which is used to send updates on payment and refund transactions.
    
## Usage
Requests errors are able to throw exceptions, so it's recommended to enclose them in a try/catch block, so the errors can be managed properly.

### Send requests to Barzahlen
You can execute requests to Barzahlen server just instantiating the proper class with its parameters. Those parameters are the shop identifier, the payment key and the notification key. Sandbox mode also could be enabled for testing purposes, which also enables a more verbose output. You can disable the sanbox box mode by passing ```false``` as first parameter or leave it completly.

```java
Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_IP, PAYMENT_KEY, NOTIFICATION_KEY);
CreateRequest createRequest  = new CreateRequest(configuration);  
```

You can instanciate a request with the following classes:
* ```CreateRequest``` to create a payment ([details](http://integration.barzahlen.de/api/funktionen/zahlungs-transaktionen))
* ```CancelRequest``` to cancel a payment ([details](http://integration.barzahlen.de/api/funktionen/stornierung))
* ```RefundRequest``` to refund a payment ([details](http://integration.barzahlen.de/api/funktionen/rueckgabe-transaktionen))
* ```ResendEmailRequest``` to resend the email to your customer ([details](http://integration.barzahlen.de/api/funktionen/erneuter-e-mail-versand))
* ```UpdateRequest``` to update your order id in the payment request ([details](http://integration.barzahlen.de/api/funktionen/bestellnummer-aktualisieren))

### Response
After a successful query (if there are no exceptions) parsed XML from the server can be accessed via the Response bean.
```java
CreateResponse createResponse = null;
try {
  createResponse = createRequest.create(order_parameters);
}  
catch(Exception e) {  
  // Customize your exception behavior  
}

String transactionId = createResponse.getTransactionId();
```

### Notifications received from Barzahlen
Classes for notifications can be instantiated in the same way than before, passing the configuration with server request and response as parameters.

```java
NotificationConfiguration notificationConfiguration = new NotificationConfiguration(SANDBOX_MODE, request, response);
Configuration configuration = new Configuration(SANDBOX_MODE, SHOP_ID, PAYMENT_KEY, NOTIFICATION_KEY);
notificationConfiguration.applyConfiguration(configuration);

PaymentNotification paymentNotification = new PaymentNotification(notificationConfiguration);
```
You can instanciate a notification with the following classes:
* ```PaymentNotification``` [details](http://integration.barzahlen.de/api/benachrichtigungen/zahlungs-transaktionen)
* ```RefundNotification``` [details](http://integration.barzahlen.de/api/benachrichtigungen/rueckgabe-transaktionen)

Once the notifications are set, they are ready to check the provided information against the server request. The parameters that should be passed are specified in the documentation.

```java
try {  
  paymentNotification.checkNotification(parameters);
}  
catch(Exception e) {  
  // Customize your exception behavior  
}
```

## Support
The Barzahlen Team will happily assist you with any problems or questions.

Send us an email to support@barzahlen.de or use the contact form at http://www.barzahlen.de/partner/integration.

## Copyright
&copy; 2013, Zerebro Internet GmbH  
http://www.barzahlen.de

## License
[GPL v3](http://opensource.org/licenses/GPL-3.0)