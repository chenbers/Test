package com.inthinc.pro.automation;

public class CassandraPropertiesBean {

    private String defaultAddress;
    private String ec2ip;
    private Integer minutes;
    private Integer seconds;
    private Integer threads;
    private Integer poolSize;
    private boolean autoDiscovery;
    private boolean useDefaultNode;
    
    
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
        if (ec2ip.startsWith("ec2") && ec2ip.endsWith(".com")){
            this.ec2ip = ec2ip;
        } else {
            this.ec2ip = "ec2-" + ec2ip + ".compute-1.amazonaws.com";
        }
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
    public void setThreads(Integer threads) {
        this.threads = threads;
    }
    public Integer getThreads() {
        return threads;
    }
    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }
    public Integer getPoolSize() {
        return poolSize;
    }
    public void setAutoDiscovery(boolean autoDiscovery) {
        this.autoDiscovery = autoDiscovery;
    }
    public boolean isAutoDiscovery() {
        return autoDiscovery;
    }
    public void setUseDefaultNode(boolean useDefaultNode) {
        this.useDefaultNode = useDefaultNode;
    }
    public boolean isUseDefaultNode() {
        return useDefaultNode;
    }
    
    @Override 
    public String toString(){
        String toString = String.format("defaultAddress=%s\n ec2ip=%s\n useDefaultNod=%s\n runFor %d:%d\n maxThreads=%d\n poolSize=%d\n useAutoDiscovery=%s\n", 
                defaultAddress,ec2ip,useDefaultNode,minutes,seconds,threads, poolSize, autoDiscovery);
        return toString;
    }
}
