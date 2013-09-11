package de.barzahlen.request;

import de.barzahlen.configuration.Configuration;
import de.barzahlen.enums.RequestErrorCode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ResendEmailRequest class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class ResendEmailRequestTest {

	/**
	 * A variable to store a ResendEmailRequest object, in order to test its
	 * features
	 */
	protected ResendEmailRequest resendEmailRequest;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		this.resendEmailRequest = new ResendEmailRequest(configuration);
	}

	/**
	 * Tests the resend email request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testResend() throws Exception {
		this.resendEmailRequest.resendEmail(new HashMap<String, String>());
	}

	/**
	 * Tests the method that assembles parameters for a post request
	 */
	@Test
	public void testAssembleParameters() {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("transaction_id", "19449282");
		parameters.put("currency", "EUR");
		parameters.put("language", "DE");

		assertEquals(
				"Assemble Parameters",
				"shop_id=12838&transaction_id=19449282&language=DE&hash=9af9f7cc38d880561f878880848ad246a301cd1a290ef557c39064aae8c0ea3281358f463b998492b6e83d16f398c2fd54e0c390174df543e0d15c3c69295163",
				this.resendEmailRequest.assembleParameters(parameters));
	}

	/**
	 * Tests the method that executes a server request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testExecuteServerRequest() throws Exception {
		this.resendEmailRequest.executeServerRequest("https://127.0.0.1/", "");
	}

	/**
	 * Tests the method that executes the error action with a xml error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionXML1() throws Exception {
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.XML_ERROR,
				"XML error message 1", "XML error message 2");
	}

	/**
	 * Tests the method that executes the error action a xml error (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionXML2() throws Exception {
		ServerRequest.BARZAHLEN_REQUEST_RETRY = false;
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.XML_ERROR,
				"XML error message 1", "XML error message 2");
	}

	/**
	 * Tests the method that executes the error action with a parameters error
	 * (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionParameters1() throws Exception {
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR,
				"Parameters error message 1", "Parameters error message 2");
	}

	/**
	 * Tests the method that executes the error action a parameters error (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionParameters2() throws Exception {
		ServerRequest.BARZAHLEN_REQUEST_RETRY = false;
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR,
				"Parameters error message 1", "Parameters error message 2");
	}

	/**
	 * Tests the method that executes the error action with a hash error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionHash1() throws Exception {
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.HASH_ERROR,
				"Hash error message 1", "Hash error message 2");
	}

	/**
	 * Tests the method that executes the error action a hash error (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionHash2() throws Exception {
		ServerRequest.BARZAHLEN_REQUEST_RETRY = false;
		this.resendEmailRequest.errorAction("", "", RequestErrorCode.HASH_ERROR,
				"Hash error message 1", "Hash error message 2");
	}
}
