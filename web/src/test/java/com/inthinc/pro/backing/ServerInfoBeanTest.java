package com.inthinc.pro.backing;

import junit.framework.TestCase;

import java.io.IOException;

public class ServerInfoBeanTest extends TestCase {
    private final ServerInfoBean serverInfoBean = new ServerInfoBean();

    /**
     * Hostname can never be null.
     *
     * @throws java.io.IOException
     */
    public void testHostnameNotNull() throws IOException {
        assertNotNull(serverInfoBean.getHostname());
    }
}
