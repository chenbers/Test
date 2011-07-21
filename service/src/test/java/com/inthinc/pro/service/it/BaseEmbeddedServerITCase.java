package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;

public abstract class BaseEmbeddedServerITCase {

    private static int port = 8080;
    private static final String DOMAIN = "localhost";

    //Test for lds church
//    private static int port = 8083;
//    private static final String DOMAIN = "ec2-50-18-138-155.us-west-1.compute.amazonaws.com";
    protected static final String url = "http://" + DOMAIN + ":" + port + "/service/api";

    private static final String adminUser="jhoward";
//    private static final String adminUser="QA";
    private static final String adminPassword="password";
//    private static final String adminUser="LDS Church";
//    private static final String adminPassword="L2ovDiFk2jO1";
//    
    public static String getAdminuser() {
		return adminUser;
	}

	public static String getAdminpassword() {
		return adminPassword;
	}

	protected ApplicationContext applicationContext;
    protected HttpClient httpClient;
    protected ClientExecutor clientExecutor;
    protected ServiceClient client;

//    @BeforeClass
    public static void beforeClass() throws Exception {

        Server server = new Server(0);
        server.addHandler(new WebAppContext("src/main/webapp", "/service"));
        server.start();
        port = server.getConnectors()[0].getLocalPort();
        System.out.println("Port is " + port);
    }

//    @Before
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
