package barzahlen.alltests;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import barzahlen.BarzahlenTest;
import barzahlen.notification.PaymentNotificationTest;
import barzahlen.notification.RefundNotificationTest;
import barzahlen.request.CreateRequestTest;
import barzahlen.request.RefundRequestTest;
import barzahlen.request.ResendEmailRequestTest;
import barzahlen.request.ServerRequestTest;
import barzahlen.request.UpdateRequestTest;
import barzahlen.request.xml.CreateXMLInfoTest;
import barzahlen.request.xml.RefundXMLInfoTest;
import barzahlen.request.xml.ResendEmailXMLInfoTest;
import barzahlen.request.xml.UpdateXMLInfoTest;
import barzahlen.request.xml.XMLInfoTest;

/**
 * Executes all available tests
 * 
 * @author Jesus Javier Nuno Garcia
 */
@RunWith(Suite.class)
@SuiteClasses({ BarzahlenTest.class, CreateRequestTest.class, CreateXMLInfoTest.class, RefundRequestTest.class, RefundXMLInfoTest.class,
        ResendEmailRequestTest.class, ResendEmailXMLInfoTest.class, ServerRequestTest.class, UpdateRequestTest.class,
        UpdateXMLInfoTest.class, PaymentNotificationTest.class, RefundNotificationTest.class, XMLInfoTest.class })
public class AllTests extends TestCase {
    // Empty
}
