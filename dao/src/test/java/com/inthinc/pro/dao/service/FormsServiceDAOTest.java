package com.inthinc.pro.dao.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.models.Group;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.SubmissionDataItem;
import com.inthinc.pro.model.form.TriggerType;

public class FormsServiceDAOTest {

    protected HttpClient httpClient;

    @Before
    public void before() {

    }

//    @Ignore
    @Test
    public void formsServiceDAOGetFormTest() {

        FormsServiceDAO formsDAO = new FormsServiceDAO();
//http://qa.tiwipro.com:8483/forms_service
        formsDAO.setProtocol("http");
        formsDAO.setHost("dev.tiwipro.com");
        formsDAO.setPort(8080);
        formsDAO.setUsername("jhoward");
        formsDAO.setPassword("password");
        formsDAO.setPath("forms_service");
        SubmissionData formSubmission = formsDAO.getForm(new Date().getTime(), 1);
        assertNotNull(formSubmission);
    }
 
    @Test
    public void formsServiceDAOGetFormQATest() {

        FormsServiceDAO formsDAO = new FormsServiceDAO();
//http://qa.tiwipro.com:8483/forms_service
        formsDAO.setProtocol("https");
        formsDAO.setHost("qa.tiwipro.com");
        formsDAO.setPort(8483);
        formsDAO.setUsername("jhoward");
        formsDAO.setPassword("password");
        formsDAO.setPath("forms_service");
        SubmissionData formSubmission = formsDAO.getForm(new Date().getTime(), 1);
        assertNotNull(formSubmission);
    }
    @SuppressWarnings("unused")
    @Test
    public void testHttp() {
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("jhoward", "password");
        httpClient.getState().setCredentials(new AuthScope("dev-pro.inthinc.com", 8082, AuthScope.ANY_REALM), defaultcreds);

        HttpMethod method = new GetMethod("http://dev-pro.inthinc.com:8082/service/api/group/4917.json");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] responseBody = method.getResponseBody();
                String body = method.getResponseBodyAsString();
                ObjectMapper mapper = new ObjectMapper();
                Group value = mapper.readValue(body, Group.class);
                int i = 0;
            }
        } catch (HttpException he) {
            int i = 0;
        } catch (IOException ioe) {
            int i = 0;

        } finally {
            method.releaseConnection();
        }
    }

    @Test
    public void testJacksonSubmissionDataItem(){
        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"tag\":1, \"question\":\"Are the brakes good\",\"answer\":\"no\"}";
        try {
            SubmissionDataItem value = mapper.readValue(json, SubmissionDataItem.class);
            assertEquals("1",value.getTag());
            assertEquals("Are the brakes good",value.getQuestion());
            assertEquals("no",value.getAnswer());

        } catch (IOException ioe) {
            fail();
        }
        
    }
    @Test
    public void testJacksonSubmissionDate(){
        ObjectMapper mapper = new ObjectMapper();
//        private Integer driverID;
//        private Integer vehicleID;
//        private Integer groupID;
//        private Integer formID;
//        private Long timestamp;
//        private Boolean approved;
//        private String formTitle;
//        private List<SubmissionDataItem> dataList;

        SubmissionData submissionData = new SubmissionData();
        submissionData.setApproved(false);
        submissionData.setDriverID(1);
        submissionData.setDriverName("Jacquie Howard");
        submissionData.setFormID(100);
        submissionData.setFormTitle("Form Title");
        submissionData.setGroupID(4917);
        submissionData.setTimestamp(102030423L);
        submissionData.setVehicleID(200);
        submissionData.setVehicleName("Jacquie's Car");
        List<SubmissionDataItem> dataList = new ArrayList<SubmissionDataItem>();
        for (int i=0; i<4; i++){
            SubmissionDataItem submissionDataItem = new SubmissionDataItem();
            submissionDataItem.setTag(""+i);
            submissionDataItem.setQuestion("question"+i);
            submissionDataItem.setAnswer("answer"+i);
            dataList.add(submissionDataItem);
        }
        submissionData.setDataList(dataList );
        
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json;
        try {
            json = mapper.writeValueAsString(submissionData);
            String jsonString = "{\"driverID\":1,\"driverName\":\"Jacquie Howard\",\"vehicleID\":200,\"vehicleName\":\"Jacquie's Car\",\"groupID\":4917,\"formID\":100,\"timestamp\":102030423,\"approved\":false,"+
            "\"formTitle\":\"Form Title\","+
                    "\"dataList\":[{\"tag\":\"0\",\"question\":\"question0\",\"answer\":\"answer0\"},"+
                                  "{\"tag\":\"1\",\"question\":\"question1\",\"answer\":\"answer1\"},"+
                                  "{\"tag\":\"2\",\"question\":\"question2\",\"answer\":\"answer2\"},"+
                                  "{\"tag\":\"3\",\"question\":\"question3\",\"answer\":\"answer3\"}]}";
            assertEquals(jsonString,json);
            
            SubmissionData readSubmissionData = mapper.readValue(json, SubmissionData.class);
            assertEquals(submissionData.getDriverID(),readSubmissionData.getDriverID());
            assertEquals(submissionData.getDataList().get(0).getTag(),readSubmissionData.getDataList().get(0).getTag());
            
        } catch (JsonGenerationException e) {
            fail();
        } catch (JsonMappingException e) {
            fail();
        } catch (IOException e) {
            fail();
        }        
    }
    @Test
    public void testSubmissionEndPoints(){
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("jhoward", "password");
        httpClient.getState().setCredentials(new AuthScope("dev.tiwipro.com", 8080, AuthScope.ANY_REALM), defaultcreds);

        Long now = new Date().getTime();
        HttpMethod method = new GetMethod("http://dev.tiwipro.com:8080/forms_service/submission/"+now+"/1");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream body = method.getResponseBodyAsStream();
                ObjectMapper mapper = new ObjectMapper();
                SubmissionData value = mapper.readValue(body, SubmissionData.class);
                assertNotNull(value);
            }
        } catch (HttpException he) {
            fail();
        } catch (IOException ioe) {
            fail();

        } finally {
            method.releaseConnection();
        }
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testSubmissionsEndPoints(){
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("jhoward", "password");
        httpClient.getState().setCredentials(new AuthScope("dev.tiwipro.com", 8080, AuthScope.ANY_REALM), defaultcreds);

        HttpMethod method = new GetMethod("http://dev.tiwipro.com:8080/forms_service/submissions/1/2011-08-21/2012-08-31/11546");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        List<SubmissionData> submissions = new ArrayList<SubmissionData>();

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                TypeReference<List<SubmissionData>> ref = new TypeReference<List<SubmissionData>>(){};
                InputStream jsonBody = method.getResponseBodyAsStream();
                ObjectMapper mapper = new ObjectMapper();
                submissions.addAll((List<SubmissionData>) mapper.readValue(jsonBody, ref));
                assertTrue(submissions != null);
            }
        } catch (HttpException he) {
            fail();
        } catch (IOException ioe) {
            fail();

        } finally {
            method.releaseConnection();
        }
    }
    
    @Test
    public void formsServiceDAOTest(){
        FormsServiceDAO formsDAO = new FormsServiceDAO();
        formsDAO.setProtocol("http");
        formsDAO.setHost("dev.tiwipro.com");
        formsDAO.setPort(8080);
        formsDAO.setUsername("jhoward");
        formsDAO.setPassword("password");
        formsDAO.setPath("forms_service");
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusYears(1);
        List<SubmissionData> formSubmissions = formsDAO.getSubmissions(TriggerType.PRE_TRIP, dateTime.toDate(), new Date(), 11546);
        assertTrue(formSubmissions != null);
       
    }
}
