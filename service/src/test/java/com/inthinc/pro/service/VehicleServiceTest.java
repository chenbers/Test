package com.inthinc.pro.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.util.GenericType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.impl.VehicleServiceImpl;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;

public class VehicleServiceTest 
extends Assert
//extends BaseTest
{
//
//	 protected static final String urlprefix = "http://localhost:8080/services/api/";
////	private Logger log = LoggerFactory.getLogger(VehicleServiceTest.class);
//	
//	@Before
//	public void setUp() throws Exception {
////		this.addPerRequestResource(VehicleServiceImpl.class);
//	}
//	
//	@Test
//	public void testGetVehicles() throws Exception {
//		
//		VehicleService proxy = ProxyFactory.create(VehicleService.class, urlprefix);
//		ClientResponse<Vehicle> response = (ClientResponse)proxy.getAll();
//		List<Vehicle> list = (List<Vehicle>)response.getEntity((new ArrayList<Vehicle>()).getClass());
//		
//		assertEquals(200, response.getStatus());
//		
//		String vehicle = (String)response.getEntity(String.class);
//		this.assertNotNull(vehicle);
//	}
//
//	@Test
//	public void testGetVehicle() throws Exception {
//		
//		
//		
//		VehicleService proxy = ProxyFactory.create(VehicleService.class, urlprefix);
//		ClientResponse<Vehicle> response = (ClientResponse<Vehicle>)proxy.get(1513);
//		Vehicle vehicle = (Vehicle)response.getEntity();
//	
////		Vehicle vehicle = proxy.get(1513);
////		assertEquals(200, response.getStatus());
//		
//		this.assertNotNull(vehicle);
//	}
//
//	@Test
//	public void testAddVehicle() throws HttpException, IOException, JAXBException {
//		
//		StringWriter writer = new StringWriter();
//
//        final JAXBContext context = JAXBContext.newInstance(Vehicle.class);
//        final Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//
//		Vehicle vehicle = new Vehicle();
//		
//		vehicle.setName("Porsche");
//		vehicle.setGroupID(2);
//		vehicle.setStatus(Status.ACTIVE);
//
//		
//        marshaller.marshal(vehicle, writer);
//		System.out.println(writer.toString());
//		
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod(urlprefix + "/vehicle");
//		method.setRequestBody(writer.toString());
//		method.setRequestHeader("content-type", "application/xml");
//		int status = client.executeMethod(method);
//		assertEquals(200, status);
//		String str = method.getResponseBodyAsString();
//		System.out.println(str);
//		assertEquals(str, str);
//		
//		
//		
//		
//	}
//	
//	@Test
//	public void generateDoc()
//	{
//		Class[] clazzes = {DeviceService.class, GroupService.class, PersonService.class, UserService.class, DriverService.class, VehicleService.class};
//		for(Class service: clazzes)
//		{
//			String entity = service.getSimpleName();
//			entity=entity.substring(0, entity.length()-"Service".length());
//			System.out.println(service.getSimpleName());
//			for(Annotation annot: service.getAnnotations())
//			{
////				System.out.println(annot.annotationType().getSimpleName() + "-" + getAnnotValue(annot));
//			}
//			
//			
//			
//			for(Method method: service.getMethods())
//			{
//				
//				String consumes = null;
//				String httpmethod = null;
//				String path=null;
//				
//				for(Annotation annot: method.getAnnotations())
//				{
//					String name = annot.annotationType().getSimpleName();
//					String value = getAnnotValue(annot);
//
//					if (name.equals("Consumes"))
//						consumes=value;
//					else if (name.equals("Path"))
//						path=value;
//					else if (value==null)
//						httpmethod=name;					
//				}
//				String out = this.toInitialUpper(method.getName()) + " " + entity;
//				if (consumes!=null)
//					out+="s";
//				
//				System.out.println("  " + out);
//				System.out.println("   " + httpmethod + " " + path);
//				if (consumes!=null)
//					System.out.println("   Consumes: " + consumes);
//					
//			}
//			System.out.println();
//			System.out.println();
//		}
//			
//	}
//	
//	private String getAnnotValue(Annotation annot) 
//	{
//		Method[] methods=annot.getClass().getMethods();
//		try {
//			Method getValue = annot.getClass().getMethod("value", null);
//			if (getValue.getReturnType().getSimpleName().equals("String"))
//				return (String) getValue.invoke(annot, null);
//			else
//				return ((String[]) getValue.invoke(annot, null))[0];
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return null;
//		
//	}
//	
//	private String toInitialUpper(String value)
//	{
//		return value.substring(0,1).toUpperCase()+value.substring(1);
//	}

}
