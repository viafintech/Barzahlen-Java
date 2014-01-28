package de.barzahlen.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Helpers for using Maps
 */
public class MapUtil {

    private MapUtil() {
    }

    /**
     * Removes Entries with null as value and returns a new Map
     *
     * @param parameters
     * @return new map with no null values
     */
    public static Map<String, String> removeNullValues(final Map<String, String> parameters) {
        Map<String, String> cleanedParameters = new HashMap<>(parameters.size(), 1);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getValue() != null) {
                cleanedParameters.put(entry.getKey(), entry.getValue());
            }
        }

        return cleanedParameters;
    }
}
