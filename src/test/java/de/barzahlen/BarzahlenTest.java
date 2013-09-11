package de.barzahlen;

import de.barzahlen.configuration.Configuration;
import de.barzahlen.request.CreateRequest;
import de.barzahlen.tools.HashTools;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Barzahlen class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class BarzahlenTest {

	/**
	 * A variable to store a CreateRequest object, in order to test its features
	 */
	protected Barzahlen barzahlen;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Configuration configuration = new Configuration(true, "12838", "123456789", "987654321");
		this.barzahlen = new Barzahlen(configuration);
	}

	/**
	 * Tests the sandbox and debug variables
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSetSandboxDebugMode() {
		Configuration configuration = new Configuration(true, null, null, null);
		CreateRequest createRequest = new CreateRequest(configuration);

		Assert.assertTrue(createRequest.isSandboxMode());

		assertEquals("Sandbox Urls", "https://api-sandbox.barzahlen.de/v1/transactions/create", Barzahlen.BARZAHLEN_CREATE_URL);
		assertEquals("Sandbox Urls", "https://api-sandbox.barzahlen.de/v1/transactions/resend_email",
				Barzahlen.BARZAHLEN_RESEND_EMAIL_URL);
		assertEquals("Sandbox Urls", "https://api-sandbox.barzahlen.de/v1/transactions/refund", Barzahlen.BARZAHLEN_REFUND_URL);
		assertEquals("Sandbox Urls", "https://api-sandbox.barzahlen.de/v1/transactions/update", Barzahlen.BARZAHLEN_UPDATE_URL);

		configuration = new Configuration(false, null, null, null);
		createRequest = new CreateRequest(configuration);

		Assert.assertFalse(createRequest.isSandboxMode());

		assertEquals("Debug Urls", "https://api.barzahlen.de/v1/transactions/create", Barzahlen.BARZAHLEN_CREATE_URL);
		assertEquals("Debug Urls", "https://api.barzahlen.de/v1/transactions/resend_email", Barzahlen.BARZAHLEN_RESEND_EMAIL_URL);
		assertEquals("Debug Urls", "https://api.barzahlen.de/v1/transactions/refund", Barzahlen.BARZAHLEN_REFUND_URL);
		assertEquals("Debug Urls", "https://api.barzahlen.de/v1/transactions/update", Barzahlen.BARZAHLEN_UPDATE_URL);
	}

	/**
	 * Tests the method that creates a sha-512 hash (1)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCreateHash1() {
		assertTrue("Hash test",
				"da72511660e27c6ab5b20b2828d123dd1243331407e0b0063f1ae0a308c9fb2d3426708208a7cabcda0af1e0e1b32ea2bce06f72e25bfb15e58f11375b4a8bf6"
						.equals(HashTools.getHash("hash message")));
	}

	/**
	 * Tests the method that creates a sha-512 hash (2)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCreateHash2() {
		// Introduce the hash from an empty string
		assertTrue("Hash test",
				"cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"
						.equals(HashTools.getHash("")));
	}

	/**
	 * Tests the method that creates a sha-512 hash (3)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCreateHash3() {
		// Introduce a hash with errors
		assertFalse("Hash test",
				"1a72511660e27c6ab5b20b2828d123dd1243331407e0b0063f1ae0a308c9fb2d3426708208a7cabcda0af1e0e1b32ea2bce06f72e25bfb15e58f11375b4a8bf6"
						.equals(HashTools.getHash("hash message")));
	}

	/**
	 * Tests the methods that set and return the shop id
	 */
	@Test
	public void testGetShopId() {
		assertEquals("Get Shop Id", "12838", this.barzahlen.getShopId());
	}

	/**
	 * Tests the methods that set and return the payment key
	 */
	@Test
	public void testGetPaymentKey() {
		assertEquals("Get Payment Key", "123456789", this.barzahlen.getPaymentKey());
	}

	/**
	 * Tests the methods that set and return the notification key
	 */
	@Test
	public void testGetNotificationKey() {
		assertEquals("Get Notification Key", "987654321", this.barzahlen.getNotificationKey());
	}
}
