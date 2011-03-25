package com.inthinc.pro.rally;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.web.selenium.StackToString;

public class TestCase {

	private final static Logger logger = Logger
			.getLogger(RallyWebServices.class);

	private Map<String, Boolean> customFields = new HashMap<String, Boolean>();
	private HTTPCommands http;

	public TestCase(String username, String password, RallyWebServices space) {
		http = new HTTPCommands(username, password);
		http.setWorkspace(space);
	}

	public TestCase(HTTPCommands http) {
		this.http = http;
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
	 * ?workspace=WorkspaceAddress&query=( FormattedID = TCXXX
	 * )&start=1&pagesize=20<br />
	 * <br />
	 * Then we set the query string with setQueryString(query) This adds our<br />
	 * query to the end of the URI<br />
	 * We then send the request using httpRequest<br />
	 * Turn the response body into a string for JSON to consume, <br />
	 * Turn the string into a JSONObject. To get the results we have to do the<br />
	 * following<br />
	 * <br />
	 * JSONObject.getJSONObject("QueryResult").getJSONArray("Results").<br />
	 * getJSONObject(0)<br />
	 * We should only get one result because of our query filter, then we add<br />
	 * the <br />
	 * test case object we got to the testCaseResults JSONObject<br />
	 * 
	 * @param testCaseFormattedID
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject get_test_case_by_ID(String testCaseFormattedID,
			Boolean fetch) {
		return getTestCase(
				new NameValuePair("FormattedID", testCaseFormattedID), fetch);
	}

	public JSONObject getTestCase(String testCaseFormattedID) {
		return getTestCase(
				new NameValuePair("FormattedID", testCaseFormattedID), false);
	}

	public JSONObject getTestCase(NameValuePair searchParams) {
		return getTestCase(searchParams, false);
	}

	public JSONObject getTestCase(NameValuePair searchParams, Boolean fetch) {
		try {
			String filter = http.constructFilter(searchParams);
			if (fetch)
				http.constructQuery(filter, 1, 200, fetch);
			else
				http.constructQuery(filter, 1, 200);
			http.getObjects(RallyWebServices.TEST_CASE);
			return http.getResults().getJSONObject(0);
		} catch (HttpException e) {
			logger.fatal(StackToString.toString(e));
		} catch (JSONException e) {
			logger.fatal(StackToString.toString(e));
		}
		return null;
	}

	public List<JSONArray> getTestCases(NameValuePair pair) {
		NameValuePair[] set = { pair };
		return getTestCases(set, false);
	}

	public List<JSONArray> getTestCases(NameValuePair pair, Boolean fetch) {
		NameValuePair[] set = { pair };
		return getTestCases(set, fetch);
	}

	public List<JSONArray> getTestCases(NameValuePair[] filterBy) {
		return getTestCases(filterBy, false);
	}

	public List<JSONArray> getTestCases(NameValuePair[] filterBy, Boolean fetch) {
		List<JSONArray> getAll = new ArrayList<JSONArray>();
		try {
			String filter = http.constructFilter(filterBy);
			boolean more = true;
			Integer start = 1, received = 0;
			do {
				if (fetch)
					http.constructQuery(filter, start, 200, fetch);
				else
					http.constructQuery(filter, start, 200);
				http.getObjects(RallyWebServices.TEST_CASE);
				JSONArray reply = http.getResults();
				received += reply.length();
				getAll.add(reply);
				Integer total = http.getQueryResult()
						.getInt("TotalResultCount");
				if (total < received || start > total) {
					more = false;
					break;
				}
				start = received + 1;

			} while (more);

		} catch (HttpException e) {
			logger.fatal(StackToString.toString(e));
		} catch (JSONException e) {
			logger.fatal(StackToString.toString(e));
		}
		return getAll;
	}

	public JSONArray[] update(JSONObject testCase) {
		http.postObjects(RallyWebServices.TEST_CASE,
				processForCustomFields(testCase));
		System.out.println(http.getQueryResult());
		JSONArray[] sendme = new JSONArray[2];
		try {
			sendme[0] = http.getErrors();
			sendme[0] = http.getWarnings();
		} catch (JSONException e) {
			logger.fatal(StackToString.toString(e));
		}
		return sendme;
	}

	private void getFieldValues() {
		NameValuePair search = new NameValuePair("Name", "Test Case");
		String filter;
		try {
			filter = http.constructFilter(search);
			http.constructQuery(filter, 1, 2, true);
			http.getObjects(RallyWebServices.TYPE_DEFINITION);
			JSONObject rallyTestCase = http.getResults().getJSONObject(0);
			JSONArray attrs = rallyTestCase.getJSONArray("Attributes");
			for (int i = 0; i < attrs.length(); i++) {
				JSONObject field = attrs.getJSONObject(i);
				customFields.put(field.getString("ElementName"), field
						.getBoolean("Custom"));
			}
		} catch (URIException e) {
			logger.fatal(StackToString.toString(e));
		} catch (JSONException e) {
			logger.fatal(StackToString.toString(e));
		}
	}

	private JSONObject processForCustomFields(JSONObject testCase) {
		if (customFields.isEmpty())
			getFieldValues();
		JSONArray keys = testCase.names();
		for (int i = 0; i < keys.length(); i++) {
			try {
				if (keys.getString(i) == null)
					continue;
				String key = keys.getString(i);
				if (customFields.containsKey(key)) {
					if (customFields.get(key)) {
						Object values = testCase.get(key);
						testCase.remove(key);
						key = "Custom:" + key.toLowerCase().replace(" ", "_");
						testCase.put(key, values);
					}
				}
			} catch (JSONException e) {
				logger.fatal(StackToString.toString(e));
			}
		}
		return testCase;
	}

	public JSONArray[] deleteCasesByProject(String project)
			throws URIException, JSONException {
		List<JSONArray> list = getTestCases(new NameValuePair("Project.Name",
				project), false);
		for (JSONArray array : list) {
			for (int i = 0; i < array.length(); i++) {
				http.deleteObject(array.getJSONObject(i));
			}
		}
		return null;
	}
}
