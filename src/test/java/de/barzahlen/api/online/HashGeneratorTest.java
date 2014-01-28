package de.barzahlen.api.online;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HashGeneratorTest {
    @Test
    public void testHashWillBeCorrectGenerated() {
        String expectedHash =
                "a4ac1ce8a0a5ea0f9759dc06a6ee9c8ae677b51106633736db736c69eb126a95159a8f8d4678169cf13b8086a096a4f7fc470b4c31fd0d561a9e61b5464a452a";

        List<String> parametersOrder = new ArrayList<>();
        parametersOrder.add("foo1");
        parametersOrder.add("foo2");
        parametersOrder.add("foo3");
        parametersOrder.add("paymentKey");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("foo1", "foo1");
        parameters.put("foo2", "bar2");
        parameters.put("foo3", "bar3");

        HashGenerator hashGenerator = new HashGenerator(parametersOrder, "paymentKey");
        String generatedHash = hashGenerator.generateHash("key", parameters);

        assertEquals(expectedHash, generatedHash);
    }
}
