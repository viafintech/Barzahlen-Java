package barzahlen.request.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import barzahlen.request.xml.ResendEmailXMLInfo;

/**
 * Tests the ResendEmailXMLInfo class
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class ResendEmailXMLInfoTest {

    /**
     * Tests the xml parsing (1)
     */
    @SuppressWarnings("static-method")
    @Test
    public void testReadXMLFile1() {
        ResendEmailXMLInfo tester = new ResendEmailXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        test += "<transaction-id>19449282</origin-transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Transaction ID", "19449282", tester.getTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce",
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
        ResendEmailXMLInfo tester = new ResendEmailXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        // There is an error here in the tag
        test += "<ransaction-id>19449282</transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Transaction ID", "19449282", tester.getTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce",
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
        ResendEmailXMLInfo tester = new ResendEmailXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        test += "transaction-id>19449282</transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            assertEquals("Transaction ID", "19449282", tester.getTransactionId());
            assertEquals("Result", 0, tester.getResult());
            assertEquals(
                    "Hash",
                    "d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce",
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
        ResendEmailXMLInfo tester = new ResendEmailXMLInfo();

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        test += "<response>\n";
        // There is one tag missing here (transaction-id)
        // test += "<transaction-id>19449282</transaction-id>\n";
        test += "<result>0</result>\n";
        test += "<hash>d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce</hash>\n";
        test += "</response>";

        try {
            tester.readXMLFile(test, 200);

            // assertEquals("Origin Transaction ID", "19449282",
            // tester.getOriginTransactionId());
            // assertEquals("Result", 0, tester.getResult());
            // assertEquals(
            // "Hash",
            // "d3650a67d54da5ec6193a9bca08f4954db8038ed1d5b108553d7f5e37989f548cf9ddc39d3223485f5255b5b379c443725552887a0b63fe4e12ae4a99a2a0fce",
            // tester.getHash());
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
        ResendEmailXMLInfo tester = new ResendEmailXMLInfo();

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
