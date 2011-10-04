package com.inthinc.pro.rally;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.StackToString;

public class TestSet extends RallyObject {

	private final static Logger logger = Logger
			.getLogger(TestSet.class);


	public TestSet(String username, String password, RallyWebServices space) {
		http = new RallyHTTP(username, password);
		http.setWorkspace(space);
	}

	public TestSet(RallyHTTP http) {
		this.http = http;
	}
	

	/**
	 * Method getTestSet<br />
	 * <br />
	 * Create a new Get Method, then use the testSet_base as our URL base<br />
	 * testSet_base = hostname+"testset.js"<br />
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
	 * test set object we got to the testSetResults JSONObject<br />
	 * 
	 * @param testSetFormattedID
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject get_test_set_by_ID(String testSetFormattedID,
			Boolean fetch) {
		return getTestSet(
				new NameValuePair("FormattedID", testSetFormattedID), fetch);
	}

	public JSONObject getTestSet(String testSetFormattedID) {
		return getTestSet(
				new NameValuePair("FormattedID", testSetFormattedID), false);
	}

	public JSONObject getTestSet(NameValuePair searchParams) {
		return getTestSet(searchParams, false);
	}

	public JSONObject getTestSet(NameValuePair searchParams, Boolean fetch) {
		try {
			String filter = http.constructFilter(searchParams);
			if (fetch)
				http.constructQuery(filter, 1, 200, fetch);
			else
				http.constructQuery(filter, 1, 200);

			http.getObjects(RallyWebServices.TEST_SET);
			return http.getResults().getJSONObject(0);
		} catch (HttpException e) {
			logger.fatal(StackToString.toString(e));
		} catch (JSONException e) {
			logger.fatal(StackToString.toString(e));
		}
		return null;
	}

	public List<JSONArray> getTestSets(NameValuePair pair) {
		NameValuePair[] set = { pair };
		return getTestSets(set, false);
	}

	public List<JSONArray> getTestSets(NameValuePair pair, Boolean fetch) {
		NameValuePair[] set = { pair };
		return getTestSets(set, fetch);
	}

	public List<JSONArray> getTestSets(NameValuePair[] filterBy) {
		return getTestSets(filterBy, false);
	}

	public List<JSONArray> getTestSets(NameValuePair[] filterBy, Boolean fetch) {
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
				http.getObjects(RallyWebServices.TEST_SET);
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



	public JSONArray[] deleteSetsByProject(String project)
			throws URIException, JSONException {
		List<JSONArray> list = getTestSets(new NameValuePair("Project.Name",
				project), false);
		for (JSONArray array : list) {
			for (int i = 0; i < array.length(); i++) {
				http.deleteObject(array.getJSONObject(i));
			}
		}
		return null;
	}
}
