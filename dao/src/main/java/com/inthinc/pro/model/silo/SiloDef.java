package com.inthinc.pro.model.silo;

public class SiloDef {
    private Integer siloID;
    private String url;
    private String host;
    private Integer port;
    private String proxyHost;
    private Integer proxyPort;
    public SiloDef() {
    }

    public Integer getSiloID() {
        return siloID;
    }

    public void setSiloID(Integer siloID) {
        this.siloID = siloID;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String toString() {
        return url;
    }
    
    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }



}
