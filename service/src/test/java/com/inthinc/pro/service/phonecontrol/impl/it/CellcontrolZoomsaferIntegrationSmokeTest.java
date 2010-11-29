package com.inthinc.pro.service.phonecontrol.impl.it;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import com.inthinc.pro.service.phonecontrol.CellPhoneService;

public class CellcontrolZoomsaferIntegrationSmokeTest {

    private static int port;

    private static CellPhoneService cellPhoneServiceClient;

    /**
     * Dummy test required to avoid JUnit initialization errors.
     */
    @Test
    public void testDummy() {}

    @BeforeClass
    public static void setUp() throws Exception {
        Server server = new Server(0);
        server.addHandler(new WebAppContext("src/main/webapp", "/service"));
        server.start();
        port = server.getConnectors()[0].getLocalPort();
        System.out.println("Port is " + port);
        
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        HttpClient httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("TEST_4846", "password");
        httpClient.getState().setCredentials(new AuthScope("localhost", port, AuthScope.ANY_REALM), defaultcreds);
        ApacheHttpClientExecutor clientExecutor = new ApacheHttpClientExecutor(httpClient);

        cellPhoneServiceClient = ProxyFactory.create(CellPhoneService.class, "http://localhost:" + port + "/service/api/", clientExecutor);
    }

    /*
     * This method should not be added to automatic execution during the integration test. It should be run manually as a smoke test since it hits the actual Cellcontrol service.
     */
    // DO NOT RUN THIS TEST AS PART OF THE AUTOMATED SUITE
//    @Test
    public void testDisablePhone() throws InterruptedException {
        Response resp = cellPhoneServiceClient.processStartMotionEvent(77711);
        System.out.println(resp.getStatus());
        Thread.sleep(Long.MAX_VALUE);
    }
}
