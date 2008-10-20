package com.inthinc.pro.model;

public class Account extends BaseEntity {

  private Integer accountID;
  private Integer timeZone;
  private String externalID;
  private Integer provider;
  private Integer pin;
  private Address mailingAddress;
  private Address billingAddress;
  private Integer enabled;
  private Integer expired;
  private Integer spam;

  public Integer getAccountID() {
    return accountID;
  }

  public void setAccountID(Integer accountID) {
    this.accountID = accountID;
  }

  public Integer getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(Integer timeZone) {
    this.timeZone = timeZone;
  }

  public String getExternalID() {
    return externalID;
  }

  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

  public Integer getProvider() {
    return provider;
  }

  public void setProvider(Integer provider) {
    this.provider = provider;
  }

  public Integer getPin() {
    return pin;
  }

  public void setPin(Integer pin) {
    this.pin = pin;
  }

  public Address getMailingAddress() {
    return mailingAddress;
  }

  public void setMailingAddress(Address mailingAddress) {
    this.mailingAddress = mailingAddress;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer enabled) {
    this.enabled = enabled;
  }

  public Integer getExpired() {
    return expired;
  }

  public void setExpired(Integer expired) {
    this.expired = expired;
  }

  public Integer getSpam() {
    return spam;
  }

  public void setSpam(Integer spam) {
    this.spam = spam;
  }

}
