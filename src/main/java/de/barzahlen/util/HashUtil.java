package de.barzahlen.util;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calculates hashes for values
 */
public class HashUtil {

    public static final String SHA512 = "SHA-512";
    public static final String UTF8 = "UTF-8";

    private HashUtil() {
    }

    /**
     * Calculates hash for a value
     */
    public static String calculateHash(final String value, final String hashAlgorithm, final String encoding)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md;
        String out;

        md = MessageDigest.getInstance(hashAlgorithm);
        md.update(value.getBytes(encoding));

        byte[] mb = md.digest();

        out = Hex.encodeHexString(mb);

        return out;
    }
}
