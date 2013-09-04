package de.barzahlen.request.xml;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests the CreateXMLInfo class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class CreateXMLInfoTest {

	/**
	 * Tests the xml parsing (1)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReadXMLFile1() {
		CreateXMLInfo tester = new CreateXMLInfo();

		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		test += "<response>\n";
		test += "<transaction-id>18280423</transaction-id>\n";
		test += "<payment-slip-link>https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf</payment-slip-link>\n";
		test += "<expiration-notice>Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.</expiration-notice>\n";
		test += "<infotext-1>Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.</infotext-1>\n";
		test += "<infotext-2>Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a></infotext-2>\n";
		test += "<result>0</result>\n";
		test += "<hash>cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218</hash>\n";
		test += "</response>";

		try {
			tester.readXMLFile(test, 200);

			assertEquals("Transaction ID", "18280423", tester.getTransactionId());
			assertEquals(
					"Payment Slip",
					"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf",
					tester.getPaymentSlipLink());
			assertEquals("Expiration Notice",
					"Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.",
					tester.getExpirationNotice());
			assertEquals(
					"Infotext 1",
					"Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.",
					tester.getInfotext1());
			assertEquals(
					"Infotext 2",
					"Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a>",
					tester.getInfotext2());
			assertEquals("Result", 0, tester.getResult());
			assertEquals(
					"Hash",
					"cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218",
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
		CreateXMLInfo tester = new CreateXMLInfo();

		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		test += "<response>\n";
		// There is an error here in the tag
		test += "<ransaction-id>18280423</transaction-id>\n";
		test += "<payment-slip-link>https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf</payment-slip-link>\n";
		test += "<expiration-notice>Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.</expiration-notice>\n";
		test += "<infotext-1>Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.</infotext-1>\n";
		test += "<infotext-2>Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a></infotext-2>\n";
		test += "<result>0</result>\n";
		test += "<hash>cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218</hash>\n";
		test += "</response>";

		try {
			tester.readXMLFile(test, 200);

			assertEquals("Transaction ID", "18280423", tester.getTransactionId());
			assertEquals(
					"Payment Slip",
					"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf",
					tester.getPaymentSlipLink());
			assertEquals("Expiration Notice",
					"Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.",
					tester.getExpirationNotice());
			assertEquals(
					"Infotext 1",
					"Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.",
					tester.getInfotext1());
			assertEquals(
					"Infotext 2",
					"Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a>",
					tester.getInfotext2());
			assertEquals("Result", 0, tester.getResult());
			assertEquals(
					"Hash",
					"cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218",
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
		CreateXMLInfo tester = new CreateXMLInfo();

		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		test += "<response>\n";
		test += "transaction-id>18280423</transaction-id>\n";
		test += "<payment-slip-link>https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf</payment-slip-link>\n";
		test += "<expiration-notice>Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.</expiration-notice>\n";
		test += "<infotext-1>Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.</infotext-1>\n";
		test += "<infotext-2>Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a></infotext-2>\n";
		test += "<result>0</result>\n";
		test += "<hash>cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218</hash>\n";
		test += "</response>";

		try {
			tester.readXMLFile(test, 200);

			assertEquals("Transaction ID", "18280423", tester.getTransactionId());
			assertEquals(
					"Payment Slip",
					"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf",
					tester.getPaymentSlipLink());
			assertEquals("Expiration Notice",
					"Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.",
					tester.getExpirationNotice());
			assertEquals(
					"Infotext 1",
					"Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.",
					tester.getInfotext1());
			assertEquals(
					"Infotext 2",
					"Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a>",
					tester.getInfotext2());
			assertEquals("Result", 0, tester.getResult());
			assertEquals(
					"Hash",
					"cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218",
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
		CreateXMLInfo tester = new CreateXMLInfo();

		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		test += "<response>\n";
		// There is one tag missing here (transaction-id)
		// test += "<transaction-id>18280423</transaction-id>\n";
		test += "<payment-slip-link>https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf</payment-slip-link>\n";
		test += "<expiration-notice>Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.</expiration-notice>\n";
		test += "<infotext-1>Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.</infotext-1>\n";
		test += "<infotext-2>Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a></infotext-2>\n";
		test += "<result>0</result>\n";
		test += "<hash>cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218</hash>\n";
		test += "</response>";

		try {
			tester.readXMLFile(test, 200);

			// assertEquals("Transaction ID", "18280423",
			// tester.getTransactionId());
			assertEquals(
					"Payment Slip",
					"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf",
					tester.getPaymentSlipLink());
			assertEquals("Expiration Notice",
					"Ihr Zahlschein ist 14 Tage gultig. Bitte bezahlen Sie Ihre Bestellung innerhalb der nachsten 14 Tage.",
					tester.getExpirationNotice());
			assertEquals(
					"Infotext 1",
					"Drucken Sie diesen Zahlschein aus und nehmen Sie ihn mit zum Barzahlen-Partner in Ihrer Nahe. Den nachsten Barzahlen-Partner finden Sie unter <a href=\"http://www.barzahlen.de/filialfinder\" target=\"_blank\">www.barzahlen.de/filialfinder</a> oder unterwegs mit unserer App.<br />Wir haben Ihnen den Zahlschein als PDF zusatzlich an folgende E-Mail geschickt: piripo.powa@gmail.com.<br />Sollte sich kein Popup mit dem Barzahlen-Zahlschein geoffnet haben, klicken Sie bitte <a href=\"https://payments.barzahlen.de/download/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c/Zahlschein_Barzahlen.pdf\" target=\"_blank\">hier</a> fur den manuellen Download.",
					tester.getInfotext1());
			assertEquals(
					"Infotext 2",
					"Wenn Sie keinen Drucker besitzen, konnen Sie sich den Zahlschein alternativ auf Ihr Handy schicken lassen. <a href=\"https://f.bar-bezahlen.nl/customer/sms/3000000000029/41c2808c49b9e9a3771239c459c7c78005a79546494fd645d26fafea1de3902c\" target=\"_blank\">Klicken Sie dazu hier.</a>",
					tester.getInfotext2());
			assertEquals("Result", 0, tester.getResult());
			assertEquals(
					"Hash",
					"cf2d03b5f155409d994db4d147ae9c9afbbf6da1f2c7f948c370740c300cbc09a5d675f2ed75cceb3ada07285002e610e81e230ff3146a81d5af7a9490b71218",
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
		CreateXMLInfo tester = new CreateXMLInfo();

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
