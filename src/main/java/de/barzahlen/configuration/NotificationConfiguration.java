package de.barzahlen.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotificationConfiguration extends Configuration {

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public NotificationConfiguration(boolean sandBoxMode, HttpServletRequest request, HttpServletResponse response) {
		super(sandBoxMode, null, null, null);
		this.request = request;
		this.response = response;
	}

	public NotificationConfiguration(HttpServletRequest request, HttpServletResponse response) {
		super(false, null, null, null);
		this.request = request;
		this.response = response;
	}

	public void applyConfiguration(Configuration configuration) {
		setNotificationKey(configuration.getNotificationKey());
		setPaymentKey(configuration.getPaymentKey());
		setShopId(configuration.getShopId());
		setSandBoxMode(configuration.isSandBoxMode());
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "NotificationConfiguration{" +
				"request=" + request +
				", response=" + response +
				'}';
	}
}
