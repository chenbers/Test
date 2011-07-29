package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.HttpClient;
import org.jboss.resteasy.client.ClientExecutor;
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

    public Integer getPort() {
        return port;
    }
    
    public String getDomain() {
        return DOMAIN;
    }
}
