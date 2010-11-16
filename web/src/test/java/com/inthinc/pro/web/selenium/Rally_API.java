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

/**
 * @author dtanner
 * This class provides a way to send test case results
 * to Rally.  
 * 
 * URI = https://rally1.rallydev.com/slm/webservice/1.21/testcaseresult/create.js?workspace=https://rally1.rallydev.com/slm/webservice/1.21/workspace/558474157
 * 
 * {
 * 	TestCaseResult:
 * 		{
 * 			Build: 3.0
 * 			Date: 2010-11-16T19:53:29.000Z
 * 			Notes: This was done in Java<br />We successfully sent the results
 * 			TestCase: {
 * 					_ref: https://rally1.rallydev.com/slm/webservice/1.21/testcase/2433294998.js
 * 					_refObjectName: test Result Test
 * 					_type: TestCase
 * 			}
 * 			Verdict: Pass
 * 		}
 * }
 * 
 * 
 */
public class Rally_API {
    private final String hostname = "https://rally1.rallydev.com/slm/webservice/1.21/";
    private final String import_result = "testcaseresult/create.js";
    private final String testCase_base = hostname+"testcase.js";
    private final String SandBox_ID = "558474157";
    private final String Inthinc_ID = "665449629";
    private final String SandBox_address = hostname+"workspace/"+SandBox_ID;
    private final String Inthinc_address = hostname+"workspace/"+Inthinc_ID;
    
    private final String[] verdicts = { "Blocked", "Error", "Fail", "Inconclusive", "Pass" };

    private Boolean Inthinc = false;
    
    private Credentials defultcreds;
    
    private HttpClient httpClient;
    
    private JSONObject testCaseResults;
    private JSONObject testCase;
    private JSONObject workspace;
    
    private PostMethod post_test_case_results;
    
    private String xsdDate;
    
    private XsdDatetimeFormat xdf;
    
    
    /**
     * Constructor for Rally API
     * 
     * Specify a username and password for the httpClient.  
     * We first get a new HttpClient, set a connection timeout,
     * tell the client that we are using a username and password, 
     * then add the credentials to the httpClient.
     * 
     * @param username
     * @param password
     */
    public Rally_API(String username, String password){
    	httpClient = new HttpClient();
    	httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
    	httpClient.getParams().setAuthenticationPreemptive(true);
    	defultcreds = new UsernamePasswordCredentials(username, password);
    	httpClient.getState().setCredentials(new AuthScope("rally1.rallydev.com", 443), defultcreds);
    }
    
    
    /**
     * Method createJSON
     * 
     * Pass a Workspace, "Sand Box" or "Inthinc"
     * Pass the Formatted Test Case ID "TCXXXX"
     * Pass the build of the portal, this is required and can be anything
     * Send the time as a GregorianCalendar, not sure if we should use anything else
     * Send the notes as a string
     * Send the Verdict = "Blocked", "Error", "Fail", "Inconclusive", "Pass"
     * 
     * @param workspace
     * @param testCase
     * @param build
     * @param date
     * @param notes
     * @param verdict
     * @return
     * @throws JSONException 
     * @throws IOException 
     * @throws HttpException 
     */
    public void createJSON(String workspace, String testCase, String build, GregorianCalendar date, String notes, String verdict ) throws JSONException, HttpException, IOException{
    	this.testCase = new JSONObject();
    	
    	testCaseResults = new JSONObject();
    		setWorkspace(workspace);
    		getTestCase(testCase);
    		setBuild(build);
    		setDate(date);
    		setNotes(notes);
    		setVerdict(verdict);
    		this.testCase.put("TestCaseResult", testCaseResults);
    }    
    
    
    /**
     * Method setWorkspace
     * 
     * Set the workspace object so our results go to the correct one
     * SandBox = https://rally1.rallydev.com/slm/webservice/1.21/workspace/558474157
     * Inthinc = https://rally1.rallydev.com/slm/webservice/1.21/workspace/665449629
     * 
     * @param workspace
     * @throws JSONException
     */
    private void setWorkspace(String workspace) throws JSONException{
    	this.workspace = new JSONObject();
    	this.workspace.put("_refObjectName", workspace);
    	this.workspace.put("_type", "Workspace");
    	if (workspace.compareTo("Sand Box")==0){
    		this.workspace.put("_ref", SandBox_address);
    		Inthinc = false;
    	}
    	if (workspace.compareTo("Inthinc")==0){
    		this.workspace.put("_ref", Inthinc_address);
    		Inthinc = true;
    	}
    	testCaseResults.put("Workspace", this.workspace);
    }
    
    /**
     * Method getTestCase
     * 
     * Create a new Get Method, then use the testCase_base as our URL base
     * testCase_base = hostname+"testcase.js"
     * 
     * Define our workspace as a filter, we need the _ref for our workspace
     * then we add the parameters to our query
     * 
     * ?workspace=WorkspaceAddress&query=( FormattedID = TCXXX )&start=1&pagesize=20
     * 
     * Then we set the query string with setQueryString(query)  This adds our query to the end of the URI
     * We then send the request using httpRequest
     * Turn the response body into a string for JSON to consume, 
     * Turn the string into a JSONObject.  To get the results we have to do the following
     * 
     * JSONObject.getJSONObject("QueryResult").getJSONArray("Results").getJSONObject(0)
     * We should only get one result because of our query filter, then we add the 
     * test case object we got to the testCaseResults JSONObject
     * 
     * @param testCase
     * @throws HttpException
     * @throws IOException
     * @throws JSONException
     */
    private void getTestCase(String testCase) throws HttpException, IOException, JSONException{
    	GetMethod get_testCase = new GetMethod();
    	String query = "";
    	get_testCase = new GetMethod(testCase_base);
    	if (Inthinc) query = "workspace="+Inthinc_address;
    	else if (!Inthinc) query = "workspace="+SandBox_address;
    	query += "&query="+("( FormattedID = "+testCase+" )").replace(" ", "%20").replace("=", "%3d");
    	query += "&pagesize=20";
    	query += "&start=1";
    	get_testCase.setQueryString(query);
    	
    	httpRequest(get_testCase);
    	String response = new String(get_testCase.getResponseBody());
    	
    	workspace = new JSONObject(response);
    	JSONObject testCaseObject = workspace.getJSONObject("QueryResult").getJSONArray("Results").getJSONObject(0);
    	testCaseResults.put("TestCase", testCaseObject);
    }
    

    /**
     * Method setBuild
     * 
     * add the build to the testCaseResult JSONObject
     * 
     * @param build
     * @throws JSONException
     */
    private void setBuild(String build) throws JSONException{
    	testCaseResults.put("Build", build);
    }
    
    
    /**
     * Method setDate
     * 
     * add the time we executed the test.  This has to be an xml string, so we will convert it from a 
     * date object
     * 
     * @param date
     * @throws JSONException
     */
    private void setDate(GregorianCalendar date) throws JSONException{
    	xdf = new XsdDatetimeFormat ();
        xdf.setTimeZone("MST");
    	xsdDate = xdf.format(date.getTime());
    	testCaseResults.put("Date", xsdDate);
    }
   
        
    /**
     * Method setNotes
     * 
     * add the notes to the testCaseResults JSONObject
     * 
     * @param notes
     * @throws JSONException
     */
    private void setNotes(String notes) throws JSONException{
    	testCaseResults.put("Notes", notes);
    }
    
    /**
     * Method setVerdict
     * 
     * add the verdict to our TestCaseResult JSON Object
     * also validate it is one of the valid options
     * 
     * @param verdict
     * @throws JSONException
     */
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
    
    /**
     * Method setDuration (optional)
     * 
     * add the duration to our testCaseResult JSONObject
     * 
     * @param duration
     * @throws JSONException
     */
    public void setDuration(Double duration) throws JSONException{
    	testCaseResults.put("Duration", duration);
    }
    
    /**
     * Method sendTestCaseResults()
     * 
     * Create a post method using the host and final address for imports.
     * 
     * hostname = "https://rally1.rallydev.com/slm/webservice/1.21/"
     * import_result = "testcaseresult/create.js"
     * 
     * Then add the JSON string as a StringRequestEntity to the post method
     * Set the entity
     * Call httpRequest to send our post
     * 
     * @throws Exception
     */
    public final void sendTestCaseResults() throws Exception {
    	post_test_case_results = new PostMethod(hostname+import_result);
        RequestEntity requestEntity = new StringRequestEntity(testCase.toString(), "application/json", "UTF-8");
        post_test_case_results.setRequestEntity(requestEntity);
        httpRequest(post_test_case_results);
        
    }
   
    
	/**
	 * Method httpRequest
	 * 
	 * Using the given HttpMethod, execute it and get the status code
	 * Depending on the status, print an error or success method, consume the body
	 * then finally release the connection
	 * 
	 * @param mehod
	 * @throws IOException
	 * @throws HttpException
	 */
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
     
    
    
    public static void main(String[] args){
    	Rally_API rally = new Rally_API("dtanner@inthinc.com", "aOURh7PL5v");    	
    	try {
    		rally.createJSON("Sand Box", "TC158", "3.0", (GregorianCalendar) GregorianCalendar.getInstance(), "This was done in Java<br>We successfully sent the results", "Pass");
			rally.sendTestCaseResults();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
