package com.inthinc.pro.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestFolder;



public class Testing {
	
	private String username;
	private String password;
	private RallyWebServices workspace;
	private HashMap<String, HashMap<String, String>> inheritance = new HashMap<String, HashMap<String, String>>();


	public void setUsernamePassword(String username, String password, RallyWebServices workspace) {
		this.username=username;
		this.password=password;
		this.workspace=workspace;
	}
	
	public void moveTestCases(String project1, JSONObject project2) {
		if (inheritance.isEmpty()) {
			getFolders();
		}
		NameValuePair[] pair = new NameValuePair[1];
		NameValuePair filter = new NameValuePair("Project.name",project1);
		pair[0] = filter;
		TestCase testing = new TestCase(username, password, workspace);
		List<JSONArray> hello = testing.getTestCases(pair, true);
		String folderID = null;
		for (JSONArray array: hello) {
			for (int i=0; i<array.length();i++) {
				JSONObject testCase = null;
				try {
					testCase = array.getJSONObject(i);
					testCase.put("Project",project2);
					String folderURL = testCase.getJSONObject("TestFolder").getString("_ref");
					String[] folderArray = folderURL.split("/");
					folderID = folderArray[folderArray.length-1].replace(".js", "");
					String _ref = inheritance.get(folderID).get("_ref");
					testCase.getJSONObject("TestFolder").put("_ref", _ref);
					testing.update(testCase);
					folderID=null;
				} catch (JSONException e) {
					testing.update(testCase);
					e.printStackTrace();
				}catch (NullPointerException e) {
					throw new NullPointerException();
				}
			}
		}
		testing = null;
	}
	
	
	
	public void copyFolders(String projectName ){
		ArrayList<String> copies = new ArrayList<String>();
		List<JSONArray> arrays;
		inheritance = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> lineage;
		TestFolder folderFile = new TestFolder(username, password, workspace);
		arrays = folderFile.getFoldersByProject(projectName, true);
		Iterator<JSONArray> itr = arrays.iterator();
		while (itr.hasNext()) {
			JSONArray next = itr.next();
			for (int i=0;i<next.length();i++) {
				try {
					JSONObject folder = next.getJSONObject(i);
					lineage = new HashMap<String, String>();
					String name = folder.getString("Name");
					lineage.put("Name", name);
					String folderID = folder.getString("ObjectID");
					inheritance.put(folderID, lineage);
					JSONArray children = folder.getJSONArray("Children");
					for (int j=0;j<children.length();j++) {
						lineage.put("Child "+j, children.getJSONObject(j).getString("_refObjectName"));
					}
					try{
						Object item = folder.get("Parent");
						if (item instanceof JSONObject) {
							JSONObject parent = (JSONObject) item;
							String[] url = parent.getString("_ref").split("/");
							lineage.put("Parent", parent.getString("_refObjectName"));
							lineage.put("ParentID", url[url.length-1].replace(".js", ""));
						}else lineage.put("None", "None");
					}catch(JSONException e) {
						e.printStackTrace();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		Iterator<String> itr2 = inheritance.keySet().iterator();
		while(itr2.hasNext()) {
			String folderID = itr2.next();
			String folderStruc = inheritance.get(folderID).get("Name");
			if (inheritance.get(folderID).containsKey("Parent")) {
				folderStruc = hasParent(folderID, folderStruc);
			}
			inheritance.get(folderID).put("Folder Structure", folderStruc.replace("%", ""));
			copies.add(folderStruc.replace("%", ""));
		}
	}
	
	private String hasParent(String folderID, String folderStruc) {
		if( inheritance.get(folderID).containsKey("Parent")) {
			String parent = inheritance.get(folderID).get("Parent");
			String parentID = inheritance.get(folderID).get("ParentID");
			folderStruc = parent + "->" + folderStruc;
			if (inheritance.get(parentID).containsKey("Parent")) {
				folderStruc = hasParent(parentID, folderStruc);
			}
		}
		return folderStruc;
	}
	
	
	
	public void createFolderStruc(JSONObject project) throws URIException, JSONException {
		TestFolder folderCommand = new TestFolder(username, password, workspace);
		Iterator<String> itr = inheritance.keySet().iterator();
		NameValuePair[] parameters;
		while (itr.hasNext()) {
			String oldID = itr.next();
			HashMap<String, String> folder = inheritance.get(oldID);
			String[] folderStruc = folder.get("Folder Structure").split("->");
			
			JSONObject parent = null;
			parameters = new NameValuePair[2];
			for (String newFolder: folderStruc) {
				try{
					if (parent!=null) {
						parameters = new NameValuePair[3];
						parameters[2] = new NameValuePair("Parent.ObjectID", parent.getString("ObjectID"));
					}
					parameters[0] = new NameValuePair("Name",newFolder); 
					parameters[1] = new NameValuePair("Project.Name", project.getString("_refObjectName"));
					parent = folderCommand.getFolder(parameters, true);
					folder.put("New FolderID", parent.getString("ObjectID"));
					folder.put("_ref", parent.getString("_ref"));
				}catch(JSONException e) {
					folderCommand.createFolder(newFolder, project, parent);
					parent = folderCommand.getFolder(parameters, true);
					folder.put("New FolderID", parent.getString("ObjectID"));
					folder.put("_ref", parent.getString("_ref"));
				} catch (URIException e) {
					e.printStackTrace();
				}
			}
		}
		ObjectReadWrite save = new ObjectReadWrite();
		save.writeObject(inheritance, "RallyFolders.dat");
	}
	
	@SuppressWarnings("unchecked")
	private void getFolders() {

		ObjectReadWrite read = new ObjectReadWrite();
		ArrayList<Object> folders = read.readObject("RallyFolders.dat");
		Iterator<Object> itr = folders.iterator();
		while (itr.hasNext()) {
			Object next = itr.next();
			if (next instanceof HashMap<?,?>) {
				inheritance = (HashMap<String, HashMap<String, String>>) next;
			}
		}
	}
	
	
	public static void main(String[] args) {
		try {
			String username="dtanner@inthinc.com", password="aOURh7PL5v";
			String firstProject="Portal Merge", secondProject="waySmart Firmware";
			RallyWebServices space = RallyWebServices.INTHINC;
			
//			Project other = new Project(username, password);
//			JSONObject otherProject = other.getProject(secondProject, space);
			TestFolder folderCommand = new TestFolder(username, password, space);
//			folderCommand.deleteFoldersByProject(secondProject);
//			TestCase cases = new TestCase(username, password, space);
//			cases.deleteCasesByProject(secondProject);
			
			Testing testMe = new Testing();
			testMe.setUsernamePassword(username, password, space);
//			testMe.copyFolders(firstProject);
//			testMe.createFolderStruc(otherProject);
//			testMe.moveTestCases(firstProject, otherProject);
			folderCommand.deleteFoldersByProject(firstProject);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
