package com.inthinc.pro.web.selenium;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class Rally_API {
    private final String hostname = "https://rally1.rallydev.com/";
    private final String import_result = "slm/webservice/1.21/testcaseresult/create.js?"; 
    private final String SandBox_ID = "558474157";
    private final String Inthinc_ID = "665449629";
    private final String request_end = "&query=&start=1&pagesize=20";
    private final String SandBox_address = "https://rally1.rallydev.com/slm/webservice/1.21/workspace/"+SandBox_ID;
    private final String SandBox_query = "https://rally1.rallydev.com/slm/webservice/1.21/testcase.js?workspace="+SandBox_address;
    private final String Inthinc_address = "https://rally1.rallydev.com/slm/webservice/1.21/workspace/"+Inthinc_ID;
    private final String Inthinc_query = "https://rally1.rallydev.com/slm/webservice/1.21/testcase.js?workspace="+Inthinc_address;

    private HttpClient httpClient;
    private JSONObject testCaseResults;
    private JSONObject testCase;
    private JSONObject workspace;
    
    private Boolean Inthinc = false;
    
        
    
    private final String[] verdicts = { "Blocked", "Error", "Fail", "Inconclusive", "Pass" };
    private XsdDatetimeFormat xdf;
    private String xsdDate;
    private PostMethod post;
    
    
    public Rally_API(String username, String password){
    	httpClient = new HttpClient();
    	httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
    	httpClient.getParams().setAuthenticationPreemptive(true);
        post = new PostMethod(hostname+import_result);
    	Credentials defultcreds = new UsernamePasswordCredentials(username, password);
    	httpClient.getState().setCredentials(new AuthScope("rally1.rallydev.com", 443), defultcreds);
    }


    public final void sendTestCaseResults() throws Exception {
        RequestEntity requestEntity = new StringRequestEntity(testCase.toString(), "application/json", "UTF-8");
        post.setRequestEntity(requestEntity);
        httpRequest(post);
        
    }

	private void httpRequest(HttpMethod mehod) throws IOException, HttpException {
		int statusCode = httpClient.executeMethod(mehod);
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("POST method failed: " + mehod.getStatusLine());
            mehod.getResponseBodyAsString();
        } else {
            System.out.println("POST method succeeded: " + mehod.getStatusLine());
            System.out.println(mehod.getResponseBodyAsString());
        }
        mehod.releaseConnection();
	}
	
	
    private final void getTestCase(String testCase) throws HttpException, IOException, JSONException{
    	GetMethod get_testCase = new GetMethod();
    	String query = "";
    	if (Inthinc){
    		query = Inthinc_query+"&query=(%20FormattedID%20%3D%20"+testCase+"%20)"+request_end;
    	}
    	else if (!Inthinc){
    		query = SandBox_query+"&query=(%20FormattedID%20%3D%20"+testCase+"%20)"+request_end;
    	}
    	get_testCase = new GetMethod(query);
    	httpRequest(get_testCase);
    	String response = new String(get_testCase.getResponseBody());
    	
    	workspace = new JSONObject(response);
    	JSONObject resultsArr = workspace.getJSONObject("QueryResult").getJSONArray("Results").getJSONObject(0);
    	testCaseResults.put("TestCase", resultsArr);
    }
        
    
    public Boolean createJSON(String workspace, String testCase, String build, GregorianCalendar date, String notes, String verdict ){
    	this.testCase = new JSONObject();
    	
    	testCaseResults = new JSONObject();
    	try{
    		setWorkspace(workspace);
    		getTestCase(testCase);
    		setDate(date);
    		setNotes(notes);
    		setVerdict(verdict);
    		setBuild(build);
    		this.testCase.put("TestCaseResult", testCaseResults);
    	}catch(JSONException e){
    		e.printStackTrace();
    		return false;
    	} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return true;
    }    
    
    private void setWorkspace(String workspace) throws JSONException{
    	this.workspace = new JSONObject();
    	this.workspace.put("_refObjectName", workspace);
    	this.workspace.put("_type", "Workspace");
    	if (workspace.compareTo("Sand Box")==0)this.workspace.put("_ref", SandBox_address);
    	if (workspace.compareTo("Inthinc")==0){
    		this.workspace.put("_ref", Inthinc_address);
    		Inthinc = true;
    	}
    	testCaseResults.put("Workspace", this.workspace);
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
     
    
    
    public static void main(String[] args){
    	Rally_API rally = new Rally_API("dtanner@inthinc.com", "aOURh7PL5v");    	
    	rally.createJSON("Sand Box", "TC158", "3.0", (GregorianCalendar) GregorianCalendar.getInstance(), "This was done in Java", "Pass");
    	try {
			rally.sendTestCaseResults();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
