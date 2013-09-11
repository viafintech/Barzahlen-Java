package de.barzahlen.configuration;

public class Configuration {

	private boolean sandBoxMode = false;
	private String shopId = null;
	private String paymentKey = null;
	private String notificationKey = null;

	/**
	 * @param sandBoxMode
	 * @param shopId
	 * @param paymentKey
	 * @param notificationKey
	 */
	public Configuration(boolean sandBoxMode, String shopId, String paymentKey, String notificationKey) {
		this.sandBoxMode = sandBoxMode;
		this.shopId = shopId;
		this.paymentKey = paymentKey;
		this.notificationKey = notificationKey;
	}

	/**
	 * @param shopId
	 * @param paymentKey
	 * @param notificationKey
	 */
	public Configuration(String shopId, String paymentKey, String notificationKey) {
		this.shopId = shopId;
		this.paymentKey = paymentKey;
		this.notificationKey = notificationKey;
	}

	public boolean isSandBoxMode() {
		return sandBoxMode;
	}

	public String getPaymentKey() {
		return paymentKey;
	}

	public String getNotificationKey() {
		return notificationKey;
	}

	public String getShopId() {
		return shopId;
	}

	protected void setSandBoxMode(boolean sandBoxMode) {
		this.sandBoxMode = sandBoxMode;
	}

	protected void setShopId(String shopId) {
		this.shopId = shopId;
	}

	protected void setPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}

	protected void setNotificationKey(String notificationKey) {
		this.notificationKey = notificationKey;
	}

	@Override
	public String toString() {
		return "RequestConfiguration{" +
				"sandBoxMode=" + sandBoxMode +
				", paymentKey='" + paymentKey + '\'' +
				", notificationKey='" + notificationKey + '\'' +
				", shopId='" + shopId + '\'' +
				'}';
	}
}
