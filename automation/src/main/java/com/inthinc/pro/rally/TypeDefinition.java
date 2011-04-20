package com.inthinc.pro.rally;

import org.apache.commons.httpclient.URIException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.StackToString;

public class TypeDefinition {
	
	private final static Logger logger = Logger.getLogger(TypeDefinition.class);

		
		private HTTPCommands http;
		
		public TypeDefinition(String username, String password){
			http=new HTTPCommands(username, password);
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
				return http.getResults().getJSONObject(0);
			} catch (JSONException e) {
				logger.debug(StackToString.toString(e));
			} catch (URIException e) {
				logger.debug(StackToString.toString(e));
			}
			return null;
		}

}
