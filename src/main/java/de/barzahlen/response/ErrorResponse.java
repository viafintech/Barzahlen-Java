package de.barzahlen.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class ErrorResponse {

	@Element
	private String result;

	@Element(name = "error-message")
	private String errorMessage;

	public String getResult() {
		return result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"result='" + result + '\'' +
				", errorMessage='" + errorMessage + '\'' +
				'}';
	}
}
