package com.inthinc.device;

public class CassandraPropertiesBean {

    private String address;
    private String clusterName;
    private String keyspaceName;
    private Integer minutes;
    private Integer seconds;
    private Integer threads;
    private Integer poolSize;
    private boolean autoDiscovery;
    
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    
    @Override 
    public String toString(){
        String toString = String.format("\naddress=%s\n clusterName=%s\n keyspaceName=%s\n runFor %d:%d\n maxThreads=%d\n poolSize=%d\n useAutoDiscovery=%s\n", 
                address,clusterName,keyspaceName,minutes,seconds,threads, poolSize, autoDiscovery);
        return toString;
    }
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
    public String getClusterName() {
        return clusterName;
    }
    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }
    public String getKeyspaceName() {
        return keyspaceName;
    }
}
