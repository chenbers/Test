package com.inthinc.pro.service;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;

@SuppressWarnings("unchecked")
public abstract class BaseTest<T> {

    private Class<T> serviceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    protected T service;
    private HttpClient client;
    private static final int port = 7696;
    private static final String domain = "localhost";
    private static final String url = "http://" + domain + ":" + port + "/service/api";
    
    @Before
    public void before() {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        client = new HttpClient(params);

        Credentials defaultcreds = new UsernamePasswordCredentials("mraby", "password");
        client.getState().setCredentials(new AuthScope("localhost", port, AuthScope.ANY_REALM), defaultcreds);
        service = ProxyFactory.create(serviceClass, url, client);
        System.out.println("Created Service Client: " + serviceClass.getSimpleName());
    }

    public T getService() {
        return service;
    }

}
