package de.barzahlen.api.online;

import de.barzahlen.util.HashUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Generates has from parameters
 */
public class HashGenerator {

    private final List<String> parametersOrder;
    private final String keyParameterName;

    public HashGenerator(final List<String> parametersOrder, final String keyParameterName) {
        this.parametersOrder = parametersOrder;
        this.keyParameterName = keyParameterName;
    }

    /**
     * Generates the hash
     *
     * @param key
     * @param parameters
     * @return hash as String or null if not successful
     */
    public String generateHash(final String key, final Map<String, String> parameters) {
        String hash;

        try {
            String parametersString = buildParametersString(key, parameters);
            hash = HashUtil.calculateHash(parametersString, HashUtil.SHA512, HashUtil.UTF8);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            hash = null;
        }

        return hash;
    }

    private String buildParametersString(final String key, final Map<String, String> parameters) {
        String parametersString = "";

        for (String parameterName : parametersOrder) {
            String value;
            if (parameterName.equals(keyParameterName)) {
                value = key;
            } else {
                value = parameters.get(parameterName);
            }

            if (value != null) {
                parametersString += value;
            }

            parametersString += ";";
        }

        if (parametersString.length() > 0) {
            parametersString = parametersString.substring(0, parametersString.length() - 1);
        }

        return parametersString;
    }
}
