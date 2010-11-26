package com.inthinc.pro.service.phonecontrol.impl.it;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class CellcontrolIntegrationTest {

    // A port of 0 will make jetty look for an available port.
    private static final int TEST_PORT = 0;
    private static Server server = new Server(TEST_PORT);
    private static int actualPort;

    @BeforeClass
    public static void setUp() throws Exception {
        server.addHandler(new WebAppContext("src/main/webapp", "/service"));
        server.start();
        actualPort = server.getConnectors()[0].getLocalPort();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testDisableCellPhone() throws InterruptedException {
    // TODO Implement integration test.
    }
}
