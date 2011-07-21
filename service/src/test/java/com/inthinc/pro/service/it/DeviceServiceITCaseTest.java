package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Device;
@Ignore
public class DeviceServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(DeviceServiceITCaseTest.class);
    private static int DEVICE_ID = 8;
    private static String IMEI = "011596000061501";
    private static String SERIAL_NUMBER = "1234567890";
    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/*TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME*/ "mraby", "password");
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }

    /*
     * Disabling tests because of unstable back end data.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/"+DEVICE_ID);
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/"+DEVICE_ID+".xml");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in XML");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/"+DEVICE_ID+".json");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/"+DEVICE_ID+".fastinfoset");
       ClientResponse<Device> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Device device = response.getEntity(Device.class);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in fastInfoset");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceIMEIDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/imei/"+IMEI);
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceIMEIXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/imei/"+IMEI+".xml");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceIMEIJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/imei/"+IMEI+".json");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceIMEIFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/imei/"+IMEI+".fastinfoset");
       ClientResponse<Device> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Device device = response.getEntity(Device.class);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in fastInfoset");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceSerialNumberDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/serialnum/"+SERIAL_NUMBER);
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceSerialNumberXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/serialnum/"+SERIAL_NUMBER+".xml");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceSerialNumberJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/serialnum/"+SERIAL_NUMBER+".json");
       ClientResponse<Device> response = request.get();
       Device device = response.getEntity(Device.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDeviceSerialNumberFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/device/serialnum/"+SERIAL_NUMBER+".fastinfoset");
       ClientResponse<Device> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Device device = response.getEntity(Device.class);
        assertNotNull(device);
        assertEquals(device.getDeviceID(),new Integer(DEVICE_ID));
        logger.info("Device retrieved successfully in fastInfoset");
    }
    
}
