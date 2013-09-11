package de.barzahlen.response;

import de.barzahlen.tools.HashTools;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "response")
public class CreateResponse {

	@Element(name = "transaction-id")
	private String transactionId = "";

	@Element(name = "payment-slip-link")
	private String paymentSlipLink = "";

	private String expirationNotice = "";
	private String infotext1 = "";
	private String infotext2 = "";

	@Element
	private String result;

	@Element
	private String hash;

	public String getTransactionId() {
		return transactionId;
	}

	public String getPaymentSlipLink() {
		return paymentSlipLink;
	}

	@Element(name = "expiration-notice")
	public String getExpirationNotice() {
		return expirationNotice;
	}

	@Element(name = "expiration-notice")
	public void setExpirationNotice(String expirationNotice) {
		this.expirationNotice = expirationNotice;
	}

	@Element(name = "infotext-1", required = false)
	public String getInfotext1() {
		return infotext1;
	}

	@Element(name = "infotext-1", required = false)
	public void setInfotext1(String infotext1) {
		this.infotext1 = infotext1;
	}

	@Element(name = "infotext-2", required = false)
	public String getInfotext2() {
		return infotext2;
	}

	@Element(name = "infotext-2", required = false)
	public void setInfotext2(String infotext2) {
		this.infotext2 = infotext2;
	}

	public String getResult() {
		return result;
	}

	public String getHash() {
		return hash;
	}

	/**
	 * Returns a SHA-512 hash of all fields (minus the hash from the server)
	 * and your private payment key to compare with the hash from the server
	 *
	 * @param paymentKey Your private payment key
	 * @return The hash
	 */
	public String toHash(String paymentKey) {
		List<String> fields = new ArrayList<String>();
		fields.add(transactionId);
		fields.add(paymentSlipLink);
		fields.add(expirationNotice);
		fields.add(infotext1);
		fields.add(infotext2);
		fields.add(result);
		fields.add(paymentKey);

		String hashString = StringUtils.join(fields, ";");

		return HashTools.getHash(hashString);
	}

	@Override
	public String toString() {
		return "CreateXml{" +
				"transactionId='" + transactionId + '\'' +
				", paymentSlipLink='" + paymentSlipLink + '\'' +
				", expirationNotice='" + expirationNotice + '\'' +
				", infotext1='" + infotext1 + '\'' +
				", infotext2='" + infotext2 + '\'' +
				", result='" + result + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}
}
