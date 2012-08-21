package com.inthinc.pro.dao.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthinc.pro.automation.models.Group;
import com.inthinc.pro.model.form.FormQuestion;
import com.inthinc.pro.model.form.FormSubmission;

public class FormsServiceDAOTest {

    protected HttpClient httpClient;

    @Before
    public void before() {

    }

    @Test
    public void formsServiceDAOTest() {

        FormsServiceDAO formsDAO = new FormsServiceDAO();

        formsDAO.setProtocol("http");
        formsDAO.setHost("dev.tiwipro.com");
        formsDAO.setPort(8080);
        formsDAO.setUsername("jhoward");
        formsDAO.setPassword("password");
        formsDAO.setPath("forms_service");
        FormSubmission questions = formsDAO.getForm(new Date().getTime(), 1);
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

    @SuppressWarnings("unused")
    @Test
    public void testJackson() {
        //
        // fieldID;
        // private String question;
        // private String answer
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"fieldID\":1, \"question\":\"Are the brakes good\",\"answer\":\"no\"}";
        try {
            FormQuestion value = mapper.readValue(json, FormQuestion.class);
            int i = 0;

        } catch (IOException ioe) {
            int i = 0;
        }

    }
    @SuppressWarnings("unused")
    @Test
    public void testEndPoints(){
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
                FormSubmission value = mapper.readValue(body, FormSubmission.class);
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
}
