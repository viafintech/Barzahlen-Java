package de.barzahlen.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestException extends Exception {

	private static final Logger logger = LoggerFactory.getLogger(RequestException.class);

	public RequestException(String message) {
		super(message);

		log(message);
	}

	private void log(String message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(RequestException.class.getCanonicalName());
		stringBuilder.append(": ");
		stringBuilder.append(message);

		logger.error(stringBuilder.toString());
	}

}
