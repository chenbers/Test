package com.inthinc.pro.web.selenium;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * We'll use Apache Commons HttpClient to connect and test the following
 * REST operations:
 * - POST
 * - DELETE
 * - GET
 * - PUT
 */
public class Rally_API {
    private static String hostname = "https://rally1.rallydev.com/slm/webservice/1.21/testcaseresult/create.js?";
    private static String query;
    private HttpClient httpClient;
    private JSONObject testCaseResults;
    
    private final String[] verdicts = { "Blocked", "Error", "Fail", "Inconclusive", "Pass" };
    private XsdDatetimeFormat xdf;
    private String xsdDate;
    private PostMethod post;
    
    public Rally_API(String username, String password){
    	this(username, password, "Sand Box", "tiwiPro");
    }
    
    public Rally_API(String username, String password, String workspace, String project){
    	httpClient = new HttpClient();
    	httpClient.getParams().setAuthenticationPreemptive(true);
        post = new PostMethod(hostname);
    	Credentials defultcreds = new UsernamePasswordCredentials(username, password);
    	httpClient.getState().setCredentials(new AuthScope(hostname, 0), defultcreds);
    	setWorkspace(workspace);
    	setProject(project);
    }


    /**
     * corresponding to URL: http://localhost:7650/autoStats/AutoStatService/add
     * @throws Exception
     */
    @Test
    public final void sendTestCaseResults() throws Exception {
    	
        String jsonValue = HTTP.toString(testCaseResults);
        RequestEntity requestEntity = new StringRequestEntity(jsonValue, "application/json", "UTF-8");
        post.setRequestEntity(requestEntity);
        int statusCode = httpClient.executeMethod(post);
        if (statusCode != HttpStatus.SC_OK) {
            fail("POST method failed: " + post.getStatusLine());
        } else {
            System.out.println("POST method succeeded: " + post.getStatusLine());
            byte[] httpResponse = post.getResponseBody();
            System.out.println(httpResponse.toString());
        }
        post.releaseConnection();
        
    }
        
    private void setWorkspace(String workspace){
    	post.addRequestHeader("workspace", workspace);
    }
    
    private void setProject(String project){
    	post.addRequestHeader("project", project);
    }
    
    
    public Boolean setJSON(String testCase, String build, GregorianCalendar date, String notes, String verdict ){
    	testCaseResults = new JSONObject();
    	try{
    		setTestCase(testCase);
    		setDate(date);
    		setNotes(notes);
    		setVerdict(verdict);
    		setBuild(build);
    	}catch(JSONException e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    
    private void setTestCase(String testCase) throws JSONException{
    	testCaseResults.put("TestCase", testCase);
    }
    
    private void setVerdict(String verdict) throws JSONException{
    	Boolean valid = false;
    	for (int entry = 0; entry < verdicts.length; entry++){
    		if (verdicts[entry].compareTo(verdict)==0){
    			valid = true;
    			break;
    		}
    	}
    	assert(valid);
    	testCaseResults.put("Verdict", verdict);
    }
    
    public void setTestSet(String testSet) throws JSONException{
    	testCaseResults.put("TestSet", testSet);
    }
    
    private void setBuild(String build) throws JSONException{
    	testCaseResults.put("Build", build);
    }
        
    private void setNotes(String notes) throws JSONException{
    	testCaseResults.put("Notes", notes);
    }
    
    public void setDuration(Double duration) throws JSONException{
    	testCaseResults.put("Duration", duration);
    }
    
    private void setDate(GregorianCalendar date) throws JSONException{
    	xdf = new XsdDatetimeFormat ();
        xdf.setTimeZone("MST");
    	xsdDate = xdf.format(date.getTime());
    	testCaseResults.put("Date", xsdDate);
    }

}
