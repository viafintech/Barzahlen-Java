package de.barzahlen.request;

import de.barzahlen.configuration.Configuration;
import de.barzahlen.enums.RequestErrorCode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Tests the UpdateRequest class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class UpdateRequestTest {

	/**
	 * A variable to store a UpdateRequest object, in order to test its features
	 */
	protected UpdateRequest updateRequest;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Configuration configuration = new Configuration(true, "12838", "926b038c5437f78256e046cfb925229161621664", "de905f2dece63d04efc1e631d9c1c060e45bc28c");
		this.updateRequest = new UpdateRequest(configuration);
	}

	/**
	 * Tests the update order request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testUpdateOrder() throws Exception {
		this.updateRequest.updateOrder(new HashMap<String, String>());
	}

	/**
	 * Tests the method that assembles parameters for a post request
	 */
	@Test
	public void testAssembleParameters() {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("transaction_id", "19449282");
		parameters.put("order_id", "637");

		assertEquals(
				"Assemble Parameters",
				"shop_id=12838&transaction_id=19449282&order_id=637&hash=cac5f99b17c72e142d8824dae515c1181f39ecccdcc8a8ffaf42f12df5609ee826f817aa5003d4fc4bc314bd0b84fa8e2b4a0cf8ac258fe3b9e1745967eef88d",
				this.updateRequest.assembleParameters(parameters));
	}

	/**
	 * Tests the method that executes a server request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testExecuteServerRequest() throws Exception {
		this.updateRequest.executeServerRequest("https://127.0.0.1/", "");
	}

	/**
	 * Tests the method that executes the error action with a xml error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionXML1() throws Exception {
		this.updateRequest.errorAction("", "", RequestErrorCode.XML_ERROR,
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
		this.updateRequest.errorAction("", "", RequestErrorCode.XML_ERROR,
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
		this.updateRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR,
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
		this.updateRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR,
				"Parameters error message 1", "Parameters error message 2");
	}

	/**
	 * Tests the method that executes the error action with a hash error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionHash1() throws Exception {
		this.updateRequest.errorAction("", "", RequestErrorCode.HASH_ERROR,
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
		this.updateRequest.errorAction("", "", RequestErrorCode.HASH_ERROR,
				"Hash error message 1", "Hash error message 2");
	}
}
