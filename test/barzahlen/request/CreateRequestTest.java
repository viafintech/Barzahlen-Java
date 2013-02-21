package barzahlen.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import barzahlen.request.CreateRequest;
import barzahlen.request.xml.CreateXMLInfo;

/**
 * Tests the CreateRequest class
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class CreateRequestTest {

    /**
     * A variable to store a CreateRequest object, in order to test its features
     */
    protected CreateRequest createRequest;

    /**
     * Sets up initial requirements for the tests
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.createRequest = new CreateRequest(true, "12838", "926b038c5437f78256e046cfb925229161621664",
                "de905f2dece63d04efc1e631d9c1c060e45bc28c");
    }

    /**
     * Tests the create request
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testCreate() throws Exception {
        this.createRequest.create(new HashMap<String, String>());
    }

    /**
     * Tests the method that assembles parameters for a post request
     */
    @Test
    public void testAssembleParameters() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("shop_id", "12838");
        parameters.put("customer_email", "piripo.powa@gmail.com");
        parameters.put("amount", "1.00");
        parameters.put("currency", "EUR");
        parameters.put("language", "DE");
        parameters.put("order_id", "434");
        parameters.put("customer_street_nr", "street 11");
        parameters.put("customer_zipcode", "11111");
        parameters.put("customer_city", "Berlin");
        parameters.put("customer_country", "DE");
        parameters.put("custom_var_0", "");
        parameters.put("custom_var_1", "");
        parameters.put("custom_var_2", "");

        assertEquals(
                "Assemble Parameters",
                "shop_id=12838&customer_email=piripo.powa@gmail.com&amount=1.00&currency=EUR&language=DE&order_id=434&customer_street_nr=street 11&customer_zipcode=11111&customer_city=Berlin&customer_country=DE&custom_var_0=&custom_var_1=&custom_var_2=&hash=43e37e59c40b6289647dfe15eb7ffd754b603854b47d48c9cce4701d048c2e6f471fc6a1794e609d9336177ee741283f7e5f3d0b1b1ef8b1eca2b02afd9b3f65",
                this.createRequest.assembleParameters(parameters));
    }

    /**
     * Tests the method that executes a server request
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testExecuteServerRequest() throws Exception {
        this.createRequest.executeServerRequest("https://api-sandbox-staging.barzahlen.de/v1/transactions/resend_email", "");
    }

    /**
     * Tests the method that compare two hashes
     */
    @Test
    public void testCompareHashes() {
        try {
            CreateRequest.XML_INFO = new CreateXMLInfo();
            String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            test += "<response>\n";
            test += "<transaction-id>18280423</transaction-id>\n";
            test += "<payment-slip-link>https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf</payment-slip-link>\n";
            test += "<expiration-notice>Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.</expiration-notice>\n";
            test += "<infotext-1>Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.</infotext-1>\n";
            test += "<infotext-2>Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a></infotext-2>\n";
            test += "<result>0</result>\n";
            test += "<hash>bc703820d0ec9e34e294c251f83954fb5d264e8c48926d8d9fe191284372b930001d5497ec3bbc8748d601bac917aa3e87dfefb2a458500c055772e33a637746</hash>\n";
            test += "</response>";
            CreateRequest.XML_INFO.readXMLFile(test, 200);

            assertTrue("Compare Hashes", this.createRequest.compareHashes());
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
        this.createRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(CreateRequestTest.class),
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
        this.createRequest.errorAction("", "", RequestErrorCode.XML_ERROR, Logger.getLogger(CreateRequestTest.class),
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
        this.createRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(CreateRequestTest.class),
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
        this.createRequest.errorAction("", "", RequestErrorCode.PARAMETERS_ERROR, Logger.getLogger(CreateRequestTest.class),
                "Parameters error message 1", "Parameters error message 2");
    }

    /**
     * Tests the method that executes the error action with a hash error (1)
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testErrorActionHash1() throws Exception {
        this.createRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(CreateRequestTest.class),
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
        this.createRequest.errorAction("", "", RequestErrorCode.HASH_ERROR, Logger.getLogger(CreateRequestTest.class),
                "Hash error message 1", "Hash error message 2");
    }
}
