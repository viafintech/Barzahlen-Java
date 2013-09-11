package de.barzahlen.response;

import de.barzahlen.tools.HashTools;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "response")
public class RefundResponse {

	/**
	 * The origin transaction id
	 */
	@Element(name = "origin-transaction-id")
	protected String originTransactionId;

	/**
	 * The refund transaction id
	 */
	@Element(name = "refund-transaction-id")
	protected String refundTransactionId;

	/**
	 * Result value
	 */
	@Element
	protected String result;

	/**
	 * Information hash
	 */
	@Element
	protected String hash;

	public String getOriginTransactionId() {
		return originTransactionId;
	}

	public String getRefundTransactionId() {
		return refundTransactionId;
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
		fields.add(originTransactionId);
		fields.add(refundTransactionId);
		fields.add(result);
		fields.add(paymentKey);

		String hashString = StringUtils.join(fields, ";");

		return HashTools.getHash(hashString);
	}

	@Override
	public String toString() {
		return "RefundResponse{" +
				"originTransactionId='" + originTransactionId + '\'' +
				", refundTransactionId='" + refundTransactionId + '\'' +
				", result='" + result + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}
}
