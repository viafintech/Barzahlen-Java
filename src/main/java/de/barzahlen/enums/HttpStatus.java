package de.barzahlen.enums;

public enum HttpStatus {
	OK (200),
	BAD_REQUEST (400),
	NOT_FOUND (404),
	;

	private final int statusCode;

	HttpStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
