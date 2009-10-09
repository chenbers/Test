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
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
@SuppressWarnings("unchecked")
public abstract class BaseTest<T> implements ApplicationContextAware {

    private Class<T> serviceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    protected T service;
    protected HttpClient client;
    private ApplicationContext applicationContext;
    private static final int port = 8080;
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;        
    }

}
