package de.barzahlen;

import de.barzahlen.response.CancelResponse;
import de.barzahlen.response.CreateResponse;
import junit.framework.Assert;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleXmlTest {

	private static final Logger logger = LoggerFactory.getLogger(SimpleXmlTest.class);

	@Test
	public void cancel() {
		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<response>\n" +
				"  <transaction-id>111111111</transaction-id>\n" +
				"  <result>0</result>\n" +
				"  <hash>413a5d9bf3d5512db7e8b172b65b81fa2be2d791b9cd850eeb6a001caa7d5dd50b5c9b7df826fb8373374dd06ff34794862bb8e74680e8dbda55726965be3fc5</hash>\n" +
				"</response>";

		Serializer serializer = new Persister();

		CancelResponse cancelResponse = null;
		try {
			cancelResponse = serializer.read(CancelResponse.class, test);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}

		Assert.assertNotNull(cancelResponse);
		Assert.assertEquals("111111111", cancelResponse.getTransactionId());
		Assert.assertEquals("413a5d9bf3d5512db7e8b172b65b81fa2be2d791b9cd850eeb6a001caa7d5dd50b5c9b7df826fb8373374dd06ff34794862bb8e74680e8dbda55726965be3fc5", cancelResponse.getHash());
		Assert.assertEquals("toHash", "413a5d9bf3d5512db7e8b172b65b81fa2be2d791b9cd850eeb6a001caa7d5dd50b5c9b7df826fb8373374dd06ff34794862bb8e74680e8dbda55726965be3fc5", cancelResponse.toHash("your-payment-key"));
	}

	@Test
	public void create() {
		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<response>\n" +
				"  <transaction-id>11111111</transaction-id>\n" +
				"  <payment-slip-link>http-splip-link</payment-slip-link>\n" +
				"  <expiration-notice>Ihr Zahlschein ist bis zum 23.09.2013 g&amp;uuml;ltig. Bitte begleichen Sie Ihre Bestellung innerhalb dieses Zeitraums.</expiration-notice>\n" +
				"  <infotext-1>&lt;strong&gt;Die n&amp;auml;chste Barzahlen-Partnerfiliale in Ihrer Umgebung finden Sie ganz einfach mit unserem Filialfinder unter &lt;a href=\"https://partner.barzahlen.de/customer/filialfinder/48973004/52.519171/13.4060912\" style=\"color: #63A924;\" target=\"_blank\"&gt;www.barzahlen.de/filialfinder&lt;/a&gt; oder mit unserer Barzahlen-App.&lt;/strong&gt;</infotext-1>\n" +
				"  <infotext-2></infotext-2>\n" +
				"  <result>0</result>\n" +
				"  <hash>22674b3c47720d208f2fdc2735cbf173f60d359d9ead5a76ce986e4e4468c55c93c9db7f99cb2cdb752f72a94dc707ca2ac26ecb2b1a390fb1485bd11beba743</hash>\n" +
				"</response>\n";

		Serializer serializer = new Persister();

		CreateResponse createResponse = null;
		try {
			createResponse = serializer.read(CreateResponse.class, test);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}

		Assert.assertNotNull(createResponse);
		Assert.assertEquals("11111111", createResponse.getTransactionId());
		Assert.assertEquals("22674b3c47720d208f2fdc2735cbf173f60d359d9ead5a76ce986e4e4468c55c93c9db7f99cb2cdb752f72a94dc707ca2ac26ecb2b1a390fb1485bd11beba743", createResponse.getHash());
		Assert.assertEquals("toHash", "22674b3c47720d208f2fdc2735cbf173f60d359d9ead5a76ce986e4e4468c55c93c9db7f99cb2cdb752f72a94dc707ca2ac26ecb2b1a390fb1485bd11beba743", createResponse.toHash("cb86f9ae54c9c914a7cf990ccbd874a85da481eb"));
	}

}
