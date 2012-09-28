package com.inthinc.pro.rally;

import org.apache.commons.httpclient.URIException;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;

public class TypeDefinition extends RallyObject {
	
		
		public TypeDefinition(String username, String password){
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
				http.getObjects(RallyWebServices.TYPE_DEFINITION);
				return http.getResponse().getResults().getJSONObject(0);
			} catch (JSONException e) {
				Log.debug(e);
			} catch (URIException e) {
				Log.debug(e);
			}
			return null;
		}

}
