package de.barzahlen.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class HashUtilTest {
    @Test
    public void testCalculatedSha512IsCorrect() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String message = "1337";
        String expectedHash =
                "6f0ac65fe01188660aad900bfe16c566ebf0e56c0a7d4a15bd831049108de80bd3a2fbf1a8b91662433a40458ec208a207cab073f190bd65b889e95e4fca8e09";

        assertEquals(expectedHash, HashUtil.calculateHash(message, HashUtil.SHA512, HashUtil.UTF8));
    }
}
