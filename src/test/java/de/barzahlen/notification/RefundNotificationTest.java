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
 * Tests the refund notification
 *
 * @author Jesus Javier Nuno Garcia
 */
public class RefundNotificationTest {

	/**
	 * A variable to store a RefundNotification object, in order to test its
	 * features
	 */
	protected RefundNotification refundNotification;

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
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		assertTrue("Check Notification", this.refundNotification.checkNotification(_parameters));
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters2() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12837");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
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
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (4)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters4() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (5)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters5() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("9991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (6)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters6() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("73");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (7)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters7() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (8)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters8() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("email@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (9)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters9() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (10)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters10() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash"))
				.thenReturn(
						"cd80e31cd28bd9fd4c364f43e31f011cf014c9edaf82c3fd4d27eca56f313fc47a4a7f76825a633f7aa915bbfe618d1d50f041558ae4edc7cbe4152e1a78e95b");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "10.00");

		this.refundNotification.checkNotification(_parameters);
	}

	/**
	 * Tests the method that checks the parameters received from the server
	 * response against the ones passed by parameter. (11)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testCheckParameters11() throws Exception {
		Mockito.when(this.mockRequest.getParameter("state")).thenReturn("refund_completed");
		Mockito.when(this.mockRequest.getParameter("refund_transaction_id")).thenReturn("20055408");
		Mockito.when(this.mockRequest.getParameter("origin_transaction_id")).thenReturn("19991103");
		Mockito.when(this.mockRequest.getParameter("shop_id")).thenReturn("12838");
		Mockito.when(this.mockRequest.getParameter("customer_email")).thenReturn("myemail@example.com");
		Mockito.when(this.mockRequest.getParameter("amount")).thenReturn("1.00");
		Mockito.when(this.mockRequest.getParameter("currency")).thenReturn("EUR");
		Mockito.when(this.mockRequest.getParameter("origin_order_id")).thenReturn("473");
		Mockito.when(this.mockRequest.getParameter("hash")).thenReturn("");

		NotificationConfiguration notificationConfiguration = new NotificationConfiguration(true, this.mockRequest, this.mockResponse);
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		notificationConfiguration.applyConfiguration(configuration);

		this.refundNotification = new RefundNotification(notificationConfiguration);

		HashMap<String, String> _parameters = new HashMap<String, String>();
		_parameters.put("origin_transaction_id", "19991103");
		_parameters.put("origin_order_id", "473");
		_parameters.put("refund_state", "refund_pending");
		_parameters.put("customer_email", "myemail@example.com");
		_parameters.put("currency", "EUR");
		_parameters.put("amount", "1.00");

		this.refundNotification.checkNotification(_parameters);
	}
}
