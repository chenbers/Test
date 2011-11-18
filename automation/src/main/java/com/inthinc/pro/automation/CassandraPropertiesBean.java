package com.inthinc.pro.automation;

public class CassandraPropertiesBean {

    private String defaultAddress;
    private String ec2ip;
    private Integer minutes;
    private Integer seconds;
    
    
    public String getDefaultAddress() {
        return defaultAddress;
    }
    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    public String getEc2ip() {
        return ec2ip;
    }
    public void setEc2ip(String ec2ip) {
        this.ec2ip = "ec2-" + ec2ip + ".compute-1.amazonaws.com";
    }
    public Integer getMinutes() {
        return minutes;
    }
    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
    public Integer getSeconds() {
        return seconds;
    }
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
}
