package de.barzahlen.alltests;

import de.barzahlen.BarzahlenTest;
import de.barzahlen.notification.PaymentNotificationTest;
import de.barzahlen.notification.RefundNotificationTest;
import de.barzahlen.request.*;
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
@SuiteClasses({BarzahlenTest.class, CreateRequestTest.class, RefundRequestTest.class,
		ResendEmailRequestTest.class, ServerRequestTest.class, UpdateRequestTest.class,
		PaymentNotificationTest.class, RefundNotificationTest.class})
public class AllTests extends TestCase {
	// Empty
}
