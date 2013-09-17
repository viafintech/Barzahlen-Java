package de.barzahlen.tools;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashTools {

	public static final String DEFAULT_HASH_ALGORITHM = "SHA-512";
	public static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Utility classes should have a hidden constructor
	 */
	private HashTools() {
	}

	/**
	 * Creates the SHA512 hash of a message.
	 *
	 * @param message The message to create the hash from
	 * @return The SHA512 hash
	 */
	public static String getHash(String message) {
		return getHash(message, DEFAULT_HASH_ALGORITHM, DEFAULT_ENCODING);
	}

	/**
	 * Creates a hash with the given hash algorithm in the
	 * given encoding
	 *
	 * @param message       Message to hash
	 * @param hashAlgorithm Hash algorithm to use
	 * @param encoding      Encoding
	 * @return Hex string based on the message
	 */
	public static String getHash(String message, String hashAlgorithm, String encoding) {
		MessageDigest md;
		String out = "";

		try {
			md = MessageDigest.getInstance(hashAlgorithm);
			md.update(message.getBytes(encoding));

			byte[] mb = md.digest();

			out = Hex.encodeHexString(mb);
		} catch (NoSuchAlgorithmException e) {
			//
		} catch (UnsupportedEncodingException e) {
			//
		}

		return out;
	}

}
