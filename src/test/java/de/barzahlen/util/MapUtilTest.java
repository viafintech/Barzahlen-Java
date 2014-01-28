package de.barzahlen.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MapUtilTest {
    @Test
    public void testRemoveNullValueRemovesNullValues() {
        Map<String, String> mapWithNull = new HashMap<>();

        mapWithNull.put("foo1", "bar1");
        mapWithNull.put("foo2", "bar2");
        mapWithNull.put("foo3", "bar3");
        mapWithNull.put("null1", null);
        mapWithNull.put("null2", null);

        Map<String, String> mapWithoutNull = MapUtil.removeNullValues(mapWithNull);

        assertFalse(mapWithoutNull.containsKey("null1"));
    }

    @Test
    public void testRemoveNullValueHoldsNonNullValues() {
        Map<String, String> mapWithNull = new HashMap<>();

        mapWithNull.put("foo1", "bar1");
        mapWithNull.put("foo2", "bar2");
        mapWithNull.put("foo3", "bar3");
        mapWithNull.put("null1", null);
        mapWithNull.put("null2", null);

        Map<String, String> mapWithoutNull = MapUtil.removeNullValues(mapWithNull);

        assertTrue(mapWithoutNull.containsKey("foo3"));
    }
}
