package com.inthinc.pro.service;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// any classes that extend this one will also have access to these configurations
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
public class BaseTest implements ApplicationContextAware {

    protected static final TJWSEmbeddedJaxrsServer server = new TJWSEmbeddedJaxrsServer();
    protected ApplicationContext applicationContext;

    private static Integer port;
    private static String urlprefix;

    public BaseTest(Integer port) {
        BaseTest.port = port;
        BaseTest.urlprefix = "http://localhost:" + port;
    }

    @BeforeClass
    public static void beforeClass() {
        server.setPort(port);
        server.start();
    }

    @Before
    public void before() {
        //TODO: scan classloader for classes that contain @Path instead of hard coding
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("userService", applicationContext, UserService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("deviceService", applicationContext, DeviceService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("driverService", applicationContext, DriverService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("vehicleService", applicationContext, VehicleService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("groupService", applicationContext, GroupService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("personService", applicationContext, PersonService.class));
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static Integer getPort() {
        return port;
    }

    public static String getUrlprefix() {
        return urlprefix;
    }

}
