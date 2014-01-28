package de.barzahlen.helper;

import org.mockito.ArgumentMatcher;

import java.util.Map;

public class MapMatcher<K, V> extends ArgumentMatcher {

    private final Map<K, V> expected;
    private Map<K, V> got;

    public MapMatcher(final Map<K, V> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(final Object argument) {
        got = (Map) argument;

        return checkSize() && checkValues();
    }

    private boolean checkSize() {
        return expected.size() == got.size();
    }

    private boolean checkValues() {
        boolean identical = true;

        for (Map.Entry<K, V> entry : expected.entrySet()) {
            V gottenValue = got.get(entry.getKey());
            if (!gottenValue.equals(entry.getValue())) {
                identical = false;
                break;
            }
        }

        return identical;
    }
}