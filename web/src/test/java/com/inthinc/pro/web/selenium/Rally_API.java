package com.inthinc.pro.web.selenium;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
 * 
 * This class provides a way to send test case results
 * to Rally.  
 * <p>
 * URI = https://rally1.rallydev.com/slm/webservice/1.21/testcaseresult/create.js?workspace=https://rally1.rallydev.com/slm/webservice/1.21/workspace/558474157
 * <br />
 * {<br />
 * 	TestCaseResult:<br />
 * 		{<br />
 * 			Build: 3.0<br />
 * 			Date: 2010-11-16T19:53:29.000Z<br />
 * 			Notes: This was done in Java<br />We successfully sent the results<br />
 * 			TestCase: {<br />
 * 					_ref: https://rally1.rallydev.com/slm/webservice/1.21/testcase/2433294998.js<br />
 * 					_refObjectName: test Result Test<br />
 * 					_type: TestCase<br />
 * 			}<br />
 * 			Verdict: Pass<br />
 * 		}<br />
 * }
 * 
 * @author dtanner
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
     * Method createJSON<br />
     * <br />
     * Pass a Workspace, "Sand Box" or "Inthinc"<br />
     * Pass the Formatted Test Case ID "TCXXXX"<br />
     * Pass the build of the portal, this is required and can be anything<br />
     * Send the time as a GregorianCalendar, not sure if we should use anything else<br />
     * Send the notes as a string<br />
     * Send the Verdict = "Blocked", "Error", "Fail", "Inconclusive", "Pass"<br />
     * 
     * @param workspace
     * @param testCase
     * @param build
     * @param date
     * @param notes as a hashmap
     * @param verdict
     * @throws JSONException 
     * @throws IOException 
     * @throws HttpException 
     * @throws Exception 
     */
    public void createJSON(String workspace, 
    		String testCase, String build, GregorianCalendar date, 
    		HashMap<String, HashMap<String, String>> errors, String verdict ) throws JSONException, HttpException, IOException {
    	createJSON(workspace, testCase,build,date,formatString(errors),verdict);
    }
    
    
    /**
     * Method createJSON<br />
     * <br />
     * Pass a Workspace, "Sand Box" or "Inthinc"<br />
     * Pass the Formatted Test Case ID "TCXXXX"<br />
     * Pass the build of the portal, this is required and can be anything<br />
     * Send the time as a GregorianCalendar, not sure if we should use anything else<br />
     * Send the notes as a string<br />
     * Send the Verdict = "Blocked", "Error", "Fail", "Inconclusive", "Pass"<br />
     * 
     * @param workspace
     * @param testCase
     * @param build
     * @param date
     * @param notes as a string to be sent up to Rally
     * @param verdict
     * @throws JSONException 
     * @throws IOException 
     * @throws HttpException 
     * @throws Exception 
     */
    public Boolean createJSON(String workspace, 
    		String testCase, String build, GregorianCalendar date, 
    		String notes, String verdict ) throws JSONException, HttpException, IOException {
    	
    	Boolean results = false;
    	this.testCase = new JSONObject();
    	testCaseResults = new JSONObject();
		setWorkspace(workspace);
		getTestCase(testCase);
		setBuild(build);
		setDate(date);
		setNotes(notes);
		setVerdict(verdict);
		this.testCase.put("TestCaseResult", testCaseResults);
		results = sendTestCaseResults();
		return results;
    } 
    
    
    /**
     * Method setWorkspace<br />
     * <br />
     * Set the workspace object so our results go to the correct one<br />
     * SandBox = https://rally1.rallydev.com/slm/webservice/1.21/workspace/558474157<br />
     * Inthinc = https://rally1.rallydev.com/slm/webservice/1.21/workspace/665449629<br />
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
     * Method getTestCase<br />
     * <br />
     * Create a new Get Method, then use the testCase_base as our URL base<br />
     * testCase_base = hostname+"testcase.js"<br />
     * <br />
     * Define our workspace as a filter, we need the _ref for our workspace<br />
     * then we add the parameters to our query<br />
     * <br />
     * ?workspace=WorkspaceAddress&query=( FormattedID = TCXXX )&start=1&pagesize=20<br />
     * <br />
     * Then we set the query string with setQueryString(query)  This adds our query to the end of the URI<br />
     * We then send the request using httpRequest<br />
     * Turn the response body into a string for JSON to consume, <br />
     * Turn the string into a JSONObject.  To get the results we have to do the following<br />
     * <br />
     * JSONObject.getJSONObject("QueryResult").getJSONArray("Results").getJSONObject(0)<br />
     * We should only get one result because of our query filter, then we add the <br />
     * test case object we got to the testCaseResults JSONObject<br />
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
     * Method setBuild<br />
     * <br />
     * add the build to the testCaseResult JSONObject
     * 
     * @param build
     * @throws JSONException
     */
    private void setBuild(String build) throws JSONException{
    	testCaseResults.put("Build", build);
    }
    
    
    /**
     * Method setDate<br />
     * <br />
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
     * Method setNotes<br />
     * <br />
     * add the notes to the testCaseResults JSONObject
     * 
     * @param notes
     * @throws JSONException
     */
    private void setNotes(String notes) throws JSONException{
    	testCaseResults.put("Notes", notes);
    }
    
    /**
     * Method setVerdict<br />
     * <br />
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
	 * Format a HashMap so it looks nice in Rally:<br />
	 * <br />
	 * ErrorName<br />
	 * ----Error 1
	 * --------Error Message Stack Trace
	 * ----Error 2
	 * --------Error Message Stack Trace
	 * 
	 * 
	 * @param errors
	 * @return
	 */
	private String formatString(HashMap<String, HashMap<String, String>> errors){
		String errorString = "";
		Set<String> outerKeys = errors.keySet();
		Iterator<String> outerItr = outerKeys.iterator();
		while (outerItr.hasNext()){
			String outerKey = outerItr.next();
			errorString += (outerKey + "<br />");
			Set<String> innerKeys = errors.get(outerKey).keySet();
			Iterator<String> innerItr = innerKeys.iterator();
			while (innerItr.hasNext()){
				String innerKey = innerItr.next();
				errorString += ("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+innerKey+"<br />");
				errorString += ("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+errors.get(outerKey).get(innerKey)+"<br /><br />");
			}
		}
		return errorString;
	}
    
    /**
     * Method sendTestCaseResults()<br />
     * <br />
     * Create a post method using the host and final address for imports.<br />
     * <br />
     * hostname = "https://rally1.rallydev.com/slm/webservice/1.21/"<br />
     * import_result = "testcaseresult/create.js"<br />
     * <br />
     * Then add the JSON string as a StringRequestEntity to the post method<br />
     * Set the entity<br />
     * Call httpRequest to send our post
     * @throws IOException 
     * @throws HttpException 
     * @throws Exception
     */
    private final Boolean sendTestCaseResults() throws HttpException, IOException{
    	Boolean results = false;
    	post_test_case_results = new PostMethod(hostname+import_result);
        RequestEntity requestEntity = new StringRequestEntity(testCase.toString(), "application/json", "UTF-8");
        post_test_case_results.setRequestEntity(requestEntity);
        httpRequest(post_test_case_results);
        
        return results;
    }
   
    
	/**
	 * Method httpRequest<br />
	 * <br />
	 * Using the given HttpMethod, execute it and get the status code<br />
	 * Depending on the status, print an error or success method, consume the body<br />
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


}
