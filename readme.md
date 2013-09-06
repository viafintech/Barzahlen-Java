# Barzahlen Payment Module Java SDK (v1.0.1)

## Copyright
(c) 2013, Zerebro Internet GmbH  
http://www.barzahlen.de

## Preparation

### Merchant Data
The merchant credentials, which are necessary to handle payments with Barzahlen, can be received at https://partner.barzahlen.de. After a successful registration a shop ID is assigned to you as well as a payment and a notification key. Furthermore you can set you callback URL, which is used to send updates on payment and refund transactions.
	
## Usage
Requests errors are able to throw exceptions, so it's recommended to enclose them in a try/catch block, so the errors can be managed properly.

### Send requests to Barzahlen
You can execute requests to Barzahlen server just instantiating the proper class with its parameters. Those parameters are the shop identifier, the payment key and the notification key. Sandbox mode also could be enabled for testing purposes. Apart from those basic requirements, there is also an extra option to enable debugging messages into a log, which is enabled by default.

```java
CreateRequest createRequest = new CreateRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  
RefundRequest refundRequest = new RefundRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  
ResendEmailRequest resendEmailRequest = new ResendEmailRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  
UpdateRequest updateRequest = new UpdateRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  
CancelRequest cancelRequest = new CancelRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  

try {
  createRequest.create(order_parameters);  
  resendEmailRequest.resendEmail(order_parameters);  
  this.refundRequest.refund(order_parameters);  
  this.updateRequest.updateOrder(order_parameters);  
  this.cancelRequest.cancelOrder(order_parameters);  
}  
catch(Exception e) {  
  // Customize your exception behavior  
}
```
After a successful query (if there are no exceptions) parsed XML from the server can be accessed.

```java
CreateRequest.XML_INFO  
RefundRequest.XML_INFO  
ResendEmailRequest.XML_INFO  
UpdateRequest.XML_INFO  
CancelRequest.XML_INFO
```

### Notifications received from Barzahlen
Classes for notifications can be instantiated in the same way than before, passing the server request and response by parameters.

```java
PaymentNotification paymentNotification = new PaymentNotification(request, response);  
RefundNotification refundNotification = new RefundNotification(request, response);
```

Set the parameters for those notifications, so they know about the shop settings.

```java
paymentNotification.setParameters(true, "12838","926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");  
refundNotification.setParameters(true, "12838","926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
```

Once the notifications are set, they are ready to check the provided information against the server request. The parameters that should be passed are specified in the documentation.

```java
try {  
  paymentNotification.checkNotification(parameters);  
  refundNotification.checkNotification(parameters);  
}  
catch(Exception e) {  
  // Customize your exception behavior  
}
```

## Support
The Barzahlen Team will happily assist you with any problems or questions.

Send us an email to support@barzahlen.de or use the contact form at http://www.barzahlen.de/partner/integration.
