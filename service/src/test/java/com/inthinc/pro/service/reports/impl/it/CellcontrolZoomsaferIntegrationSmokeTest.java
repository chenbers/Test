package com.inthinc.pro.service.reports.impl.it;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import com.inthinc.pro.service.reports.AssetService;

public class CellcontrolZoomsaferIntegrationSmokeTest {

    private static int port;

    private static AssetService assetServiceClient;

    /**
     * Dummy test required to avoid JUnit initialization errors.
     */
    @Test
    public void testDummy() {}

    // @BeforeClass
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

        assetServiceClient = ProxyFactory.create(AssetService.class, "http://localhost:" + port + "/service/api", clientExecutor);
    }

    // @Test
    public void testAssetService() throws InterruptedException {
        Response resp = assetServiceClient.getRedFlagCount(1);
        System.out.println(resp.getStatus());
        Thread.sleep(Long.MAX_VALUE);
    }
}
