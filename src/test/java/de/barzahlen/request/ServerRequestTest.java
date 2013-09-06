package de.barzahlen.request;

import de.barzahlen.Barzahlen;
import de.barzahlen.enums.SandboxDebugMode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Tests the ServerRequest class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class ServerRequestTest {

	/**
	 * A variable to store a CreateRequest object, in order to test its features
	 */
	protected ServerRequest serverRequest;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.serverRequest = new ServerRequest() {

			@Override
			protected boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception {
				return false;
			}

			@Override
			protected boolean compareHashes() {
				return false;
			}

			@Override
			protected String assembleParameters(HashMap<String, String> _parameters) {
				return null;
			}

			@Override
			protected String[] getParametersTemplate() {
				return new String[0];
			}
		};
		Barzahlen.setDebuggingMode(true);
	}

	@Test
	public void testSetParameters() {
		this.serverRequest.setParameters(true, "12838", "926b038c5437f78256e046cfb925229161621664",
				"de905f2dece63d04efc1e631d9c1c060e45bc28c");

		assertEquals(SandboxDebugMode.SANDBOX, Barzahlen.getSandboxDebugMode());
		assertEquals("12838", this.serverRequest.getShopId());
		assertEquals("926b038c5437f78256e046cfb925229161621664", this.serverRequest.getPaymentKey());
		assertEquals("de905f2dece63d04efc1e631d9c1c060e45bc28c", this.serverRequest.getNotificationKey());
	}

	/**
	 * Tests the method that formats the requests parameters to something easy
	 * readable (1)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testFormatReadableParameters1() {
		assertTrue(
				"Format readable parameters test",
				"{state => paid, transaction_id => 20412206, shop_id => 12838, customer_email => piripo.powa@gmail.com, amount => 10.00, currency => EUR, order_id => 501, custom_var_0 => , custom_var_1 => , custom_var_2 => , hash => 366b7c98b5222e31ba839f8dd316857490be9e9585c74158eabffc37c3a2f966a964bd266ab38421dc8829e200856b89fd4dc846688ff58f44696b27461d7454}".equals(ServerRequest
						.formatReadableParameters("state=paid&transaction_id=20412206&shop_id=12838&customer_email=piripo.powa@gmail.com&amount=10.00&currency=EUR&order_id=501&custom_var_0=&custom_var_1=&custom_var_2=&hash=366b7c98b5222e31ba839f8dd316857490be9e9585c74158eabffc37c3a2f966a964bd266ab38421dc8829e200856b89fd4dc846688ff58f44696b27461d7454")));
	}

	/**
	 * Tests the method that formats the requests parameters to something easy
	 * readable (2)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testFormatReadableParameters2() {
		assertFalse(
				"Format readable parameters test",
				"state => paid, transaction_id => 20412206, shop_id => 12838, customer_email => piripo.powa@gmail.com, amount => 10.00, currency => EUR, order_id => 501, custom_var_0 => , custom_var_1 => , custom_var_2 => , hash => 366b7c98b5222e31ba839f8dd316857490be9e9585c74158eabffc37c3a2f966a964bd266ab38421dc8829e200856b89fd4dc846688ff58f44696b27461d7454}".equals(ServerRequest
						.formatReadableParameters("state=paid&transaction_id=20412206&shop_id=12838&customer_email=piripo.powa@gmail.com&amount=10.00&currency=EUR&order_id=501&custom_var_0=&custom_var_1=&custom_var_2=&hash=366b7c98b5222e31ba839f8dd316857490be9e9585c74158eabffc37c3a2f966a964bd266ab38421dc8829e200856b89fd4dc846688ff58f44696b27461d7454")));
	}
}
