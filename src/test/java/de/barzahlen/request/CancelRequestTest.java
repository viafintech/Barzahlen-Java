package de.barzahlen.request;

import de.barzahlen.request.xml.CancelXMLInfo;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the CancelRequest class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class CancelRequestTest {

	/**
	 * A variable to store a CancelRequest object, in order to test its
	 * features
	 */
	protected CancelRequest cancelRequest;

	/**
	 * Sets up initial requirements for the tests
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.cancelRequest = new CancelRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664",
				"de905f2dece63d04efc1e631d9c1c060e45bc28c");
	}

	/**
	 * Tests the resend cancel request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testResend() throws Exception {
		this.cancelRequest.cancel(new HashMap<String, String>());
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
				this.cancelRequest.assembleParameters(parameters));
	}

	/**
	 * Tests the method that executes a server request
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testExecuteServerRequest() throws Exception {
		this.cancelRequest.executeServerRequest("https://127.0.0.1/", "");
	}

	/**
	 * Tests the method that compare two hashes
	 */
	@Test
	public void testCompareHashes() {
		try {
			CancelRequest.XML_INFO = new CancelXMLInfo();
			String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			test += "<response>\n";
			test += "<transaction-id>19449282</transaction-id>\n";
			test += "<result>0</result>\n";
			test += "<hash>d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce</hash>\n";
			test += "</response>";
			CancelRequest.XML_INFO.readXMLFile(test, 200);
			assertTrue("Compare Hashes", this.cancelRequest.compareHashes());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the method that executes the error action with a xml error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionXML1() throws Exception {
		this.cancelRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(CancelRequestTest.class),
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
		this.cancelRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(CancelRequestTest.class),
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
		this.cancelRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(CancelRequestTest.class),
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
		this.cancelRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(CancelRequestTest.class),
				"Parameters error message 1", "Parameters error message 2");
	}

	/**
	 * Tests the method that executes the error action with a hash error (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testErrorActionHash1() throws Exception {
		this.cancelRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(CancelRequestTest.class),
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
		this.cancelRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(CancelRequestTest.class),
				"Hash error message 1", "Hash error message 2");
	}
}
