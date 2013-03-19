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
import com.inthinc.pro.rally.Project;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestFolder;

public class MoveByFolder {
	
	private String username;
	private String password;
	private RallyWebServices workspace;
	private HashMap<String, HashMap<String, String>> copyFolders = new HashMap<String, HashMap<String, String>>();

	public MoveByFolder(String username, String password, RallyWebServices space) {
		this.username=username;
		this.password=password;
		this.workspace=space;
	}
	
	
	public void moveTestCases(JSONObject newProject) throws JSONException {
		TestCase caseWorker = new TestCase(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			String oldFolder = itr.next();
			String _ref = copyFolders.get(oldFolder).get("_ref");
			NameValuePair[] filterBy = {new NameValuePair("TestFolder.ObjectID",oldFolder)};
			List<JSONArray> testCases = caseWorker.getTestCases(true, filterBy);
			for (JSONArray reply: testCases) {
				for (int i=0;i<reply.length();i++) {
					JSONObject testCase = reply.getJSONObject(i);
					testCase.put("Project", newProject);
					testCase.getJSONObject("TestFolder").put("_ref", _ref);
					caseWorker.update(testCase);
				}
			}
		}
	}
	
	public void copyFolders(String formattedID) throws URIException, JSONException {
		TestFolder cabinet = new TestFolder(username, password, workspace);
		NameValuePair[] filters = { new NameValuePair("FormattedID", formattedID)};
		copyFolders = new HashMap<String, HashMap<String, String>>();
		JSONObject topDog = cabinet.getFolder(true, filters);
		getChildren(cabinet, topDog.getString("ObjectID"), "");
		
		
	}
	
	private void getChildren(TestFolder cabinet, String objectID, String folderStruc) throws URIException, JSONException {
		NameValuePair[] filters = {new NameValuePair("ObjectID", objectID)};
		JSONObject topDog = cabinet.getFolder(true, filters);
		HashMap<String, String> additives = new HashMap<String, String>();
		copyFolders.put(topDog.getString("ObjectID"), additives);
		if (folderStruc.equals("")) {
			folderStruc += topDog.getString("Name");
		}else folderStruc += "->" + topDog.getString("Name");
		additives.put("FolderStruc", folderStruc);
		if (topDog.get("Children") instanceof JSONArray) {
			JSONArray children = topDog.getJSONArray("Children");
			for (int i=0;i<children.length();i++) {
				String[] _ref = children.getJSONObject(i).getString("_ref").split("/");
				String folderID = _ref[_ref.length-1].replace(".js", "");
				additives.put("Child "+i, folderID);
				getChildren(cabinet, folderID, folderStruc);
			}
		}
	}
	
	
	public void createFolderStruc(JSONObject project) throws URIException, JSONException {
		if (copyFolders.isEmpty()) {
			getFolders();
		}
		TestFolder folderCommand = new TestFolder(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		NameValuePair[] parameters;
		while (itr.hasNext()) {
			String oldID = itr.next();
			HashMap<String, String> folder = copyFolders.get(oldID);
			String[] folderStruc = folder.get("FolderStruc").split("->");
			
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
					parent = folderCommand.getFolder(true, parameters);
					folder.put("New FolderID", parent.getString("ObjectID"));
					folder.put("_ref", parent.getString("_ref"));
				}catch(JSONException e) {
					folderCommand.createFolder(newFolder, project, parent);
					parent = folderCommand.getFolder(true, parameters);
					folder.put("New FolderID", parent.getString("ObjectID"));
					folder.put("_ref", parent.getString("_ref"));
				} catch (URIException e) {
					e.printStackTrace();
				}
			}
		}
		ObjectReadWrite save = new ObjectReadWrite();
		save.writeObject(copyFolders, "RallyFolders.dat");
	}
	
	
	@SuppressWarnings("unchecked")
	private void getFolders() {

		ObjectReadWrite read = new ObjectReadWrite();
		ArrayList<Object> folders = read.readObject("RallyFolders.dat");
		Iterator<Object> itr = folders.iterator();
		while (itr.hasNext()) {
			Object next = itr.next();
			if (next instanceof HashMap<?,?>) {
				copyFolders = (HashMap<String, HashMap<String, String>>) next;
			}
		}
	}

	private void deleteOldFolders() {
		if (copyFolders.isEmpty())getFolders();
		TestFolder folderDeath = new TestFolder(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			folderDeath.deleteFolder(itr.next());
		}
	}
	
	
	public static void main(String[] args) {
		try {
			String username="dtanner@inthinc.com", password="aOURh7PL5v";
			String firstProject="tiwiPro", secondProject="tiwiPro Firmware";
			String folderName = "TF582";
			RallyWebServices space = RallyWebServices.INTHINC;
			
			Project other = new Project(username, password);
//			JSONObject otherProject = other.getProject(secondProject, space);
			TestFolder folderCommand = new TestFolder(username, password, space);
//			folderCommand.deleteFoldersByProject(secondProject);
//			TestCase cases = new TestCase(username, password, space);
//			cases.deleteCasesByProject(secondProject);
			
			MoveByFolder testMe = new MoveByFolder(username, password, space);
			testMe.copyFolders(folderName);
//			testMe.createFolderStruc(otherProject);
//			testMe.moveTestCases(otherProject);
			testMe.deleteOldFolders();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
