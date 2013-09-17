package de.barzahlen.notification;

import de.barzahlen.configuration.Configuration;
import de.barzahlen.configuration.NotificationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Tests the payment notification
 *
 * @author Jesus Javier Nuno Garcia
 */
public class PaymentNotificationTest {

	/**
	 * A variable to store a PaymentNotification object, in order to test its
	 * features
	 */
	protected PaymentNotification paymentNotification;

	/**
	 * A variable for mocking a HttpServletRequest
	 */
	protected HttpServletRequest mockRequest;

	/**
	 * A variable for mocking a HttpServletResponse
	 */
	protected HttpServletResponse mockResponse;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.mockRequest = Mockito.mock(HttpServletRequest.class);
		this.mockResponse = Mockito.mock(HttpServletResponse.class);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (1)
	 *
	 * @throws Exception
	 */
	@Test
	public void testCheckParameters1() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash")).thenReturn("4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		assertTrue("Check Notification", this.paymentNotification.checkNotification(_parameters));
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters2() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12837");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (3)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters3() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (4)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters4() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (5)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters5() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (6)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters6() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (7)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters7() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (8)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters8() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"4335cdd747d369867882b8e8b9f7ea7fcffc095cace517e6ec6bc8708f7b38c17cf8a2abeaa82b882036aeb4e362cd417a37d76802ce4c3ac7fdedbce06d96ea");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "10.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (9)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters9() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("paid");
		Mockito.when(this.mockRequest.getParameter("transaction_id")).thenReturn("22136907");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("order_id")).thenReturn("641");
		Mockito.when(this.mockRequest.getParameter("hash")).thenReturn("");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.paymentNotification = new PaymentNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("barzahlen_transaction_id", "22136907");
		_parameters.put("barzahlen_transaction_state", "pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");
		_parameters.put("order_id", "641");

		this.paymentNotification.checkNotification(_parameters);
	}
}
