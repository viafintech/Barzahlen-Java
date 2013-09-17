package de.barzahlen.response;

import de.barzahlen.tools.HashTools;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "response")
public class ResendEmailResponse {

	/**
	 * The transaction id
	 */
	@Element(name = "transaction-id")
	private String transactionId;

	/**
	 * Result value
	 */
	@Element
	private String result;

	/**
	 * Information hash
	 */
	@Element
	private String hash;

	public String getTransactionId() {
		return transactionId;
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
		fields.add(result);
		fields.add(paymentKey);

		String hashString = StringUtils.join(fields, ";");

		return HashTools.getHash(hashString);
	}

	@Override
	public String toString() {
		return "ResendEmailResponse{" +
				"transactionId='" + transactionId + '\'' +
				", result='" + result + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}
}
