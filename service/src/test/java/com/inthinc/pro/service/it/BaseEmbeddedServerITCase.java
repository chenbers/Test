package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;

public abstract class BaseEmbeddedServerITCase {

    private static int port;
    public static final String DOMAIN = "localhost";

    protected static final String url = "http://" + DOMAIN + ":" + port + "/service/api";

    protected ApplicationContext applicationContext;
    protected HttpClient httpClient;
    protected ClientExecutor clientExecutor;
    protected ServiceClient client;

    @BeforeClass
    public static void beforeClass() throws Exception {

        Server server = new Server(0);
        server.addHandler(new WebAppContext("src/main/webapp", "/service"));
        server.start();
        port = server.getConnectors()[0].getLocalPort();
        System.out.println("Port is " + port);
    }

    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("mraby", "password");
        httpClient.getState().setCredentials(new AuthScope(DOMAIN, port, AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + port, clientExecutor);

    }

    public int getPort() {
        return port;
    }
    
    public String getDomain() {
        return DOMAIN;
    }
}
