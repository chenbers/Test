package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
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
public abstract class BaseITCase implements ApplicationContextAware {
    
        private ApplicationContext applicationContext;
        private static final int port = 8080;
        private static final String domain = "localhost";
        protected static final String url = "http://" + domain + ":" + port + "/service/api";
            
        protected HttpClient httpClient;
        protected ClientExecutor clientExecutor;

        @Before
        public void before() {
            RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

            HttpClientParams params = new HttpClientParams();
            params.setAuthenticationPreemptive(true);
            httpClient = new HttpClient(params);
            Credentials defaultcreds = new UsernamePasswordCredentials("mraby", "password");
            httpClient.getState().setCredentials(new AuthScope(domain, port, AuthScope.ANY_REALM), defaultcreds);
            clientExecutor = new ApacheHttpClientExecutor(httpClient);

        }


        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;        
        }
}
