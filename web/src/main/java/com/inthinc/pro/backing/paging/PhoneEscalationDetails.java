package com.inthinc.pro.backing.paging;

public class PhoneEscalationDetails {
    private String phoneNumber;
    private PhoneEscalationStatus status;
    private Integer attempt;
    private Integer maxAttempts;
    public PhoneEscalationDetails(String phoneNumber, PhoneEscalationStatus status, Integer attempt, Integer maxAttempts) {
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.attempt = attempt;
        this.maxAttempts = maxAttempts;
    }
    public PhoneEscalationStatus getStatus() {
        return status;
    }
    public void setStatus(PhoneEscalationStatus status) {
        this.status = status;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Integer getAttempt() {
        return attempt;
    }
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
    public Integer getMaxAttempts() {
        return maxAttempts;
    }
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
