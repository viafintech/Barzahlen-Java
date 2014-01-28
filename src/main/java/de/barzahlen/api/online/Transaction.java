package de.barzahlen.api.online;

public class Transaction {
    private String customerEmail;
    private String amount;
    private String currency;
    private String orderId;
    private String customerStreetNr;
    private String customerZipcode;
    private String customerCity;
    private String customerCountry;
    private String language;
    private String customVar0;
    private String customVar1;
    private String customVar2;

    public Transaction() {
    }

    public Transaction(String customerEmail, String amount, String currency, String orderId, String customerStreetNr,
                       String customerZipcode, String customerCity, String customerCountry, String language,
                       String customVar0, String customVar1, String customVar2) {
        this.customerEmail = customerEmail;
        this.amount = amount;
        this.currency = currency;
        this.orderId = orderId;
        this.customerStreetNr = customerStreetNr;
        this.customerZipcode = customerZipcode;
        this.customerCity = customerCity;
        this.customerCountry = customerCountry;
        this.language = language;
        this.customVar0 = customVar0;
        this.customVar1 = customVar1;
        this.customVar2 = customVar2;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Transaction setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public Transaction setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Transaction setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public Transaction setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getCustomerStreetNr() {
        return customerStreetNr;
    }

    public Transaction setCustomerStreetNr(String customerStreetNr) {
        this.customerStreetNr = customerStreetNr;
        return this;
    }

    public String getCustomerZipcode() {
        return customerZipcode;
    }

    public Transaction setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
        return this;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public Transaction setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
        return this;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public Transaction setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Transaction setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCustomVar0() {
        return customVar0;
    }

    public Transaction setCustomVar0(String customVar0) {
        this.customVar0 = customVar0;
        return this;
    }

    public String getCustomVar1() {
        return customVar1;
    }

    public Transaction setCustomVar1(String customVar1) {
        this.customVar1 = customVar1;
        return this;
    }

    public String getCustomVar2() {
        return customVar2;
    }

    public Transaction setCustomVar2(String customVar2) {
        this.customVar2 = customVar2;
        return this;
    }
}
