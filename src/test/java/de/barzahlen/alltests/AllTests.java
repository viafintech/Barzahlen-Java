package de.barzahlen.alltests;

import de.barzahlen.BarzahlenTest;
import de.barzahlen.notification.PaymentNotificationTest;
import de.barzahlen.notification.RefundNotificationTest;
import de.barzahlen.request.*;
import de.barzahlen.request.xml.*;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Executes all available tests
 *
 * @author Jesus Javier Nuno Garcia
 */
@RunWith(Suite.class)
@SuiteClasses({BarzahlenTest.class, CreateRequestTest.class, CreateXMLInfoTest.class, RefundRequestTest.class, RefundXMLInfoTest.class,
		ResendEmailRequestTest.class, ResendEmailXMLInfoTest.class, ServerRequestTest.class, UpdateRequestTest.class,
		UpdateXMLInfoTest.class, PaymentNotificationTest.class, RefundNotificationTest.class, XMLInfoTest.class})
public class AllTests extends TestCase {
	// Empty
}
