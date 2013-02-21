package barzahlen.request.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import barzahlen.request.xml.RefundXMLInfo;

/**
 * Tests the RefundXMLInfo class
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class RefundXMLInfoTest {

    /**
     * Tests the xml parsing (1)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile1() {
        RefundXMLInfo tester = new RefundXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        test += "<origin-transaction-id>19449282</origin-transaction-id>\n";
        test += "<refund-transaction-id>19724587</refund-transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Origin Transaction ID", "19449282", tester.getOriginTransactionId());
            assertEquals("Refund Transaction ID", "19724587", tester.getRefundTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed",
                    tester.getHash());
            assertTrue(tester.checkParameters());
        } catch (SAXException e) {
            assertFalse(tester.checkParameters());
        } catch (IOException e) {
            assertFalse(tester.checkParameters());
        }
    }

    /**
     * Tests the xml parsing (2)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile2() {
        RefundXMLInfo tester = new RefundXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        // There is an error here in the tag
        test += "<rigin-transaction-id>19449282</origin-transaction-id>\n";
        test += "<refund-transaction-id>19724587</refund-transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Origin Transaction ID", "19449282", tester.getOriginTransactionId());
            assertEquals("Refund Transaction ID", "19724587", tester.getRefundTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed",
                    tester.getHash());
            assertTrue(tester.checkParameters());
        } catch (SAXException e) {
            assertFalse(tester.checkParameters());
        } catch (IOException e) {
            assertFalse(tester.checkParameters());
        }
    }

    /**
     * Tests the xml parsing (3)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile3() {
        RefundXMLInfo tester = new RefundXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        test += "origin-transaction-id>19449282</origin-transaction-id>\n";
        test += "<refund-transaction-id>19724587</refund-transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Origin Transaction ID", "19449282", tester.getOriginTransactionId());
            assertEquals("Refund Transaction ID", "19724587", tester.getRefundTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed",
                    tester.getHash());
            assertTrue(tester.checkParameters());
        } catch (SAXException e) {
            assertFalse(tester.checkParameters());
        } catch (IOException e) {
            assertFalse(tester.checkParameters());
        }
    }

    /**
     * Tests the xml parsing (4)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile4() {
        RefundXMLInfo tester = new RefundXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        // There is one tag missing here (origin-transaction-id)
        // test += "<origin-transaction-id>19449282</origin-transaction-id>\n";
        test += "<refund-transaction-id>19724587</refund-transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            // assertEquals("Origin Transaction ID", "19449282",
            // tester.getOriginTransactionId());
            assertEquals("Refund Transaction ID", "19724587", tester.getRefundTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "1aba92229fd3f73acf9c45a83af2244cec5833550e0ed605f3170baa4f59cfa108253ec20dc6eeddfdbae19e82c438c96b1a5853cc26ab2a6910ba9a8da5f1ed",
                    tester.getHash());
            assertFalse(tester.checkParameters());
        } catch (SAXException e) {
            assertFalse(tester.checkParameters());
        } catch (IOException e) {
            assertFalse(tester.checkParameters());
        }
    }

    /**
     * Tests the xml parsing (5)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile5() {
        RefundXMLInfo tester = new RefundXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        test += "<result>0</result>\n";
        test += "<error-message>There has been an error with the request</error-message>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 400);

            assertEquals("Result", 0, tester.getResult());
            assertEquals("error-message", "There has been an error with the request", tester.getErrorMessage());
            assertFalse(tester.checkParameters());
        } catch (SAXException e) {
            assertFalse(tester.checkParameters());
        } catch (IOException e) {
            assertFalse(tester.checkParameters());
        }
    }
}
