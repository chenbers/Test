package com.inthinc.pro.rally;

import org.apache.commons.httpclient.URIException;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;


public class Project extends RallyObject{
	

	
	public Project(String username, String password){
		http=new RallyHTTP(username, password);
	}
	
	public JSONObject getProject(String name, RallyWebServices space) {
		return getProject(name, space, false);
	}
	public JSONObject getProject(String name, RallyWebServices space, Boolean fetch) {
		http.setWorkspace(space);
		try {
			String queryString = http.constructFilter("( Name = \"" + name + "\" )");
			http.constructQuery(queryString, 1, 20, fetch);
			http.getObjects(RallyWebServices.PROJECT);
			return http.getResults().getJSONObject(0);
		} catch (JSONException e) {
			Log.error(e);
		} catch (URIException e) {
			Log.error(e);
		}
		return null;
	}

}
