package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
@SuppressWarnings("unchecked")
public abstract class BaseEmbeddedServerITCase implements ApplicationContextAware   {
    
        protected static ApplicationContext applicationContext;
        private static int port;
        private static final String domain = "localhost";

        protected static final String url = "http://" + domain + ":" + port + "/service/api";
            
        protected HttpClient httpClient; 
        protected ClientExecutor clientExecutor;
        protected ServiceClient client;

        @BeforeClass
        public static void beforeClass() throws Exception{
            
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
            Credentials defaultcreds = new UsernamePasswordCredentials("TEST_4846", "password");
            httpClient.getState().setCredentials(new AuthScope(domain, port, AuthScope.ANY_REALM), defaultcreds);
            clientExecutor = new ApacheHttpClientExecutor(httpClient);
            
            client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + port , clientExecutor );

        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;        
       }
        
       public int getPort() {
           return port;
       }
}
