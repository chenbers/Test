package com.inthinc.pro.service;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

//any classes that extend this one will also have access to these configurations
@ContextConfiguration(locations={"classpath:spring/applicationContext-serverPropertiesLocal.xml",
							  "classpath:spring/applicationContext-dao.xml",
                              "classpath:spring/applicationContext-daoBeans.xml",
                              "classpath:spring/applicationContext-appBeans.xml",
                              "classpath:spring/applicationContext-beans.xml",
                              "classpath:spring/applicationContext-security.xml"})
//                              loader=com.inthinc.pro.spring.test.WebSessionContextLoader.class)


public class BaseTest extends Assert{
 
 protected static TJWSEmbeddedJaxrsServer server;
 
 protected static ResteasyDeployment deployment;
 
 protected static int port = 8989;
// private final String urlprefix = "http://localhost:" + port + "";
 protected static final String urlprefix = "http://localhost:8080/services/api/";

 
// @BeforeClass
 public static void initialize() throws Exception{
  server = new TJWSEmbeddedJaxrsServer();
  deployment = new ResteasyDeployment();
  server.setDeployment(deployment);
  server.setPort(port);

  SpringBeanProcessor processor 
      = new  SpringBeanProcessor(deployment.getRegistry(), deployment.getProviderFactory());
  ClassPathResource cpr = new ClassPathResource("spring/applicationContext-beans.xml");
  ConfigurableBeanFactory factory = new XmlBeanFactory(cpr);
 
  factory.addBeanPostProcessor((BeanPostProcessor) processor);
  server.start();

 }
 
 public void addPerRequestResource(Class<?> clazz) {
  deployment.getRegistry().addPerRequestResource(clazz);
 }
 
 @AfterClass
 public static void destroy() throws Exception{
  if (server != null) {
   server.stop();
  }
 }
}
