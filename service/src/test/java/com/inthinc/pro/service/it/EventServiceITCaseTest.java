package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.service.pagination.EventPage;
@Ignore
//run against localhost - update dates as necessary

public class EventServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static final int GROUP_ID = 1;
    private static final int DRIVER_ID = 3;
    private static final int VEHICLE_ID = 2;
    
    private static final String START_DATE = "20110201";
    private static final String END_DATE = "20110306";
    
	
    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(getAdminuser(), getAdminpassword());
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://"+getDomain()+":" + getPort(), clientExecutor);
    }
    
    @Test
    public void getEventCountTest() throws Exception {

        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/count");
        
        ClientResponse<Integer> response = request.get();
        Integer count = response.getEntity(Integer.class);
        assertEquals(new Integer(135), count);
    }

    @Test
    public void getEventsForGroupFirstPageTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE);

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(135,page.getTotalCount());
        String nextRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20";
        assertEquals(nextRequestQuery, page.getNext());
    }
    @Test
    public void getEventsForGroupFirstPageNoEndDateTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE);

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(922,page.getTotalCount());
        String endDate = "20111205";
        String nextRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+endDate+"/page;start=20;pageCount=20";
        assertEquals(nextRequestQuery, page.getNext());
    }
    @Test
    public void getEventsForGroupNextPageTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20");

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(135,page.getTotalCount());
        String nextRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=40;pageCount=20";
        assertEquals(nextRequestQuery, page.getNext());
        String prevRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=0;pageCount=20";
        assertEquals(prevRequestQuery, page.getPrevious());
    }
    @Test
    public void getEventsForGroupLastPageTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=130;pageCount=20");

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(135,page.getTotalCount());
        assertEquals(5,page.getPageCount());
        assertEquals(null, page.getNext());
        String prevRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/all/"+START_DATE+"/"+END_DATE+"/page;start=110;pageCount=20";
        assertEquals(prevRequestQuery, page.getPrevious());
    }
    @Test
    public void getEventsForGroupSingleEventTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/speeding/"+START_DATE+"/"+END_DATE);

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(60,page.getTotalCount());
        String nextRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/speeding/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20";
        assertEquals(nextRequestQuery, page.getNext());
    }
    @Test
    public void getEventsForGroupMultipleEventTest() throws Exception{
    	
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/speeding,hard_turn/"+START_DATE+"/"+END_DATE);

        ClientResponse<EventPage> response = request.get();
        EventPage page = response.getEntity(EventPage.class);
        assertNotNull(page);
        assertEquals(124,page.getTotalCount());
        String nextRequestQuery= "http://localhost:8080/service/api/group/"+GROUP_ID+
        		"/events/speeding,hard_turn/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20";
        assertEquals(nextRequestQuery, page.getNext());
    }
//    @Test
//    public void getEventsForDriverSingleEventTest() throws Exception{
//    	
//        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+
//        		"/events/speeding/"+START_DATE+"/"+END_DATE);
//
//        ClientResponse<EventPage> response = request.get();
//        EventPage page = response.getEntity(EventPage.class);
//        assertNotNull(page);
//        assertEquals(60,page.getTotalCount());
//        String nextRequestQuery= "http://localhost:8080/service/api/driver/"+DRIVER_ID+
//        		"/events/speeding/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20";
//        assertEquals(nextRequestQuery, page.getNext());
//    }
//
//  @Test
//  public void getEventsForVehicleSingleEventTest() throws Exception{
//  	
//      ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+
//      		"/events/speeding/"+START_DATE+"/"+END_DATE);
//
//      ClientResponse<EventPage> response = request.get();
//      EventPage page = response.getEntity(EventPage.class);
//      assertNotNull(page);
//      assertEquals(60,page.getTotalCount());
//      String nextRequestQuery= "http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+
//      		"/events/speeding/"+START_DATE+"/"+END_DATE+"/page;start=20;pageCount=20";
//      assertEquals(nextRequestQuery, page.getNext());
//  }
//
}
