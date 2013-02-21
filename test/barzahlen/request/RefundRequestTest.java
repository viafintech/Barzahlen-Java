package barzahlen.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import barzahlen.request.RefundRequest;
import barzahlen.request.xml.RefundXMLInfo;

/**
 * Tests the RefundRequest class
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class RefundRequestTest {

    /**
     * A variable to store a RefundRequest object, in order to test its features
     */
    protected RefundRequest refundRequest;

    /**
     * Sets up initial requirements for the tests
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.refundRequest = new RefundRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664",
                "de905f2dece63d04efc1e631d9c1c060e45bc28c");
    }

    /**
     * Tests the refund request
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testRefund() throws Exception {
        this.refundRequest.refund(new HashMap<String, String>());
    }

    /**
     * Tests the method that assembles parameters for a post request
     */
    @Test
    public void testAssembleParameters() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("amount", "1.00");
        parameters.put("transaction_id", "19449282");
        parameters.put("currency", "EUR");
        parameters.put("language", "DE");

        assertEquals(
                "Assemble Parameters",
                "shop_id=12838&transaction_id=19449282&amount=1.00&currency=EUR&language=DE&hash=3fcfe4f3d103abd97e2b8cacf4d001c6ac9e77c692b551dd7cd82ad0c7cbc63e9bd1529cce3742415a11f42e481217c41096d405a63a7b5ce37f6d81c590d7e5",
                this.refundRequest.assembleParameters(parameters));
    }

    /**
     * Tests the method that executes a server request
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testExecuteServerRequest() throws Exception {
        this.refundRequest.executeServerRequest("https://127.0.0.1/", "");
    }

    /**
     * Tests the method that compare two hashes
     */
    @Test
    public void testCompareHashes() {
        try {
            RefundRequest.XML_INFO = new RefundXMLInfo();
            String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            test += "<response>\n";
            test += "<origin-transaction-id>19449282</origin-transaction-id>\n";
            test += "<refund-transaction-id>19724587</refund-transaction-id>\n";
            test += "<result>0</result>\n";
            test += "<hash>1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed</hash>\n";
            test += "</response>";
            RefundRequest.XML_INFO.readXMLFile(test, 200);
            assertTrue("Compare Hashes", this.refundRequest.compareHashes());
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
        this.refundRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(RefundRequestTest.class),
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
        this.refundRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(RefundRequestTest.class),
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
        this.refundRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(RefundRequestTest.class),
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
        this.refundRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(RefundRequestTest.class),
                "Parameters error message 1", "Parameters error message 2");
    }

    /**
     * Tests the method that executes the error action with a hash error (1)
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testErrorActionHash1() throws Exception {
        this.refundRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(RefundRequestTest.class),
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
        this.refundRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(RefundRequestTest.class),
                "Hash error message 1", "Hash error message 2");
    }
}
