package com.inthinc.pro.service.it;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.service.AddressService;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.service.VehicleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
@SuppressWarnings("unchecked")
public abstract class BaseTest implements ApplicationContextAware {

//    private Class<T> serviceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//    protected T service;
    private ApplicationContext applicationContext;
//    private static final int port = 8081;
//    private static final String domain = "dev-pro.inthinc.com";
    private static final int port = 8080;
    private static final String domain = "localhost";
    protected static final String url = "http://" + domain + ":" + port + "/service/api";
        
    protected HttpClient httpClient;
    protected ClientExecutor clientExecutor;
    
    protected AddressService addressService;
    protected DeviceService deviceService;
    protected DriverService driverService;
    protected GroupService groupService;
    protected PersonService personService;
    protected UserService userService;
    protected VehicleService vehicleService;

    @Before
    public void before() {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);

        Credentials defaultcreds = new UsernamePasswordCredentials("mraby", "password");
        httpClient.getState().setCredentials(new AuthScope(domain, port, AuthScope.ANY_REALM), defaultcreds);
//        clientExecutor = new ApacheHttpClientExecutor(httpClient);
//        service = ProxyFactory.create(serviceClass, url, client);
//        System.out.println("Created Service Client: " + serviceClass.getSimpleName());
        
//        addressService = ProxyFactory.create(AddressService.class, url, httpClient);
//        deviceService = ProxyFactory.create(DeviceService.class, url, httpClient);
//        driverService = ProxyFactory.create(DriverService.class, url, httpClient);
//        groupService = ProxyFactory.create(GroupService.class, url, httpClient);
//        personService = ProxyFactory.create(PersonService.class, url, httpClient);
//        userService = ProxyFactory.create(UserService.class, url, httpClient);
//        vehicleService = ProxyFactory.create(VehicleService.class, url, httpClient);
    }

    
//    public <E> E getService(Class<E> serviceClass) {
//        return ProxyFactory.create(serviceClass, url, clientExecutor);
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;        
    }

}
