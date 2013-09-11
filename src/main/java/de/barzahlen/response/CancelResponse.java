package de.barzahlen.response;

import de.barzahlen.tools.HashTools;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "response")
public class CancelResponse {

	@Element(name = "transaction-id")
	protected String transactionId;

	@Element
	protected String result;

	@Element
	protected String hash;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
		fields.add(result);
		fields.add(paymentKey);

		String hashString = StringUtils.join(fields, ';');

		return HashTools.getHash(hashString);
	}

	@Override
	public String toString() {
		return "CancelXml{" +
				"transactionId='" + transactionId + '\'' +
				", result='" + result + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}
}
