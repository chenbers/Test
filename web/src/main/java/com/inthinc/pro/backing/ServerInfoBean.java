package com.inthinc.pro.backing;

import java.io.Serializable;

/**
 * Holds server information.
 */
public class ServerInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Gets hostname.
     *
     * @return hostname
     */
    public String getHostname() {

        String hostname = System.getenv("HOSTNAME");

        if (hostname == null)
            hostname = System.getenv("COMPUTERNAME");

        if (hostname == null)
            hostname = "";

        return hostname;
    }
}
