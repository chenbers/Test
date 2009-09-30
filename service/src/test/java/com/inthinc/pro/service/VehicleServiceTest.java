package com.inthinc.pro.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.impl.VehicleServiceImpl;

public class VehicleServiceTest extends BaseTest{

	private Logger log = LoggerFactory.getLogger(VehicleServiceTest.class);

	private final String urlprefix = "http://localhost:" + port + "";
	
	@Before
	public void setUp() throws Exception {
		this.addPerRequestResource(VehicleServiceImpl.class);
	}
	
	@Test
	public void testGetVehicles() throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(urlprefix + "/vehicles");
		int status = client.executeMethod(method);
		assertEquals(200, status);
		String str = method.getResponseBodyAsString();
		log.debug(str);
		assertEquals(str, str);
		Vehicle vehicle;
	}

	@Test
	public void testAddVehicle() throws HttpException, IOException, JAXBException {
		
		StringWriter writer = new StringWriter();

        final JAXBContext context = JAXBContext.newInstance(Vehicle.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		Vehicle vehicle = new Vehicle();
		
		vehicle.setName("Porsche");
		vehicle.setGroupID(2);
		vehicle.setStatus(Status.ACTIVE);

		
        marshaller.marshal(vehicle, writer);
		System.out.println(writer.toString());
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(urlprefix + "/vehicle");
		method.setRequestBody(writer.toString());
		method.setRequestHeader("content-type", "application/xml");
		int status = client.executeMethod(method);
		assertEquals(200, status);
		String str = method.getResponseBodyAsString();
		System.out.println(str);
		assertEquals(str, str);
		
		
		
		
	}

}
