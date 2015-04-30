package com.inthinc.pro.backing;

import java.io.Serializable;

/**
 * Holds server information.
 */
public class ServerInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String hostname;

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    /**
     * Gets hostname.
     *
     * @return hostname
     */
    public String getHostname() {
        return hostname;
    }
}
