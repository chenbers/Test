package com.inthinc.pro.rally;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;

public class TestFolder extends RallyObject {
	

	public TestFolder(RallyObject ro) {
		http = ro.getHttp();
	}
	
	public TestFolder(String username, String password, RallyWebServices space){
		http=new RallyHTTP(username, password);
		http.setWorkspace(space);
	}
	
	
	public JSONObject getFolder(String name) throws URIException, JSONException {
		return getFolder(name,  false);
	}
	
	public JSONObject getFolder(String name, Boolean fetch) throws JSONException, URIException {
		return getFolder(fetch, new NameValuePair("Name", name));
	}
	
	public JSONObject getFolder(Boolean fetch, NameValuePair ...parameters) throws JSONException, URIException {
		String queryString = http.constructFilter(parameters);
		http.constructQuery(queryString, 1, 20, fetch);
		http.getObjects(RallyWebServices.TEST_FOLDER);
		return http.getResponse().getResults().getJSONObject(0);
	}
	
	public JSONArray[] updateFolder(JSONObject folder) {
		http.postObjects(RallyWebServices.TEST_FOLDER, folder);
		System.out.println(http.getResponse());
		JSONArray[] sendme ={http.getResponse().getErrors(), http.getResponse().getWarnings()}; 
		return sendme;
	}
	
	public List<JSONArray> getFoldersByProject( String project){
		return getAllFolders(new NameValuePair("Project.name", project));
	}
	public List<JSONArray> getFoldersByProject( String project, Boolean fetch){
		return getAllFolders(fetch, new NameValuePair("Project.name", project));
	}
	
	public List<JSONArray> getAllFolders( NameValuePair ...fetchingNames){
		return getAllFolders(false, fetchingNames);
	}
	
	
	public List<JSONArray> getAllFolders(Boolean fetch, NameValuePair ...filterBy){
		Integer pageSize = 200;
    	List<JSONArray> getAll = new ArrayList<JSONArray>();
    	try {
    		String filter = http.constructFilter(filterBy);
    		boolean more = true;
    		Integer start = 1, received=0;
    		do {
    			if (fetch) http.constructQuery( filter, start, pageSize, fetch);
		    	else http.constructQuery(filter, start, pageSize);
				http.getObjects(RallyWebServices.TEST_FOLDER);
				JSONArray reply =http.getResponse().getResults();
				received += reply.length();
				getAll.add(reply);
				
				Integer total = http.getResponse().getTotalResultCount();
				if (total <= received || start > total) {
					more = false;
					break;
				}
				start = received+1;
				
    		}while (more);
			
		} catch (HttpException e) {
			Log.error(e);
		}
	    return getAll;
    }
	


	public JSONArray[] createFolder(String foldername, JSONObject project, JSONObject parent) throws JSONException {
		http.postObjects(RallyWebServices.TEST_FOLDER, createJSONFolder(foldername, project, parent), true);
		return null;
		
	}
	public JSONArray[] createFolder( String foldername, JSONObject project ) throws JSONException {
		return createFolder(foldername, project, null);
	}
	

	public JSONObject createJSONFolder(String name, JSONObject project) throws JSONException {
		return createJSONFolder(name, project, null);
	}
	private JSONObject createJSONFolder(String name, JSONObject project, JSONObject parent) throws JSONException {
		JSONObject folder = new JSONObject();
		folder.put("Name", name);
		folder.put("Project", project);
		folder.put("Workspace", http.getWorkspace());
		if (parent != null) folder.put("Parent", parent);
		return folder;
	}

	public JSONArray[] deleteFoldersByProject(String project) throws URIException, JSONException {
		List<JSONArray> list = getAllFolders(new NameValuePair("Project.Name", project));
		for (JSONArray array: list) {
			for (int i=0;i<array.length();i++) {
				http.deleteObject(array.getJSONObject(i));
			}
		}
		return null;
	}


	public void deleteFolder(String next) {
		http.deleteObject(RallyWebServices.TEST_FOLDER, next);
	}

}
