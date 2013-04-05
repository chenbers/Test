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

import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.rally.Project;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestFolder;

public class CopyTestPlanAndCases {

	private String username;
	private String password;
	private RallyWebServices workspace;
	private HashMap<String, HashMap<String, String>> copyFolders = new HashMap<String, HashMap<String, String>>();
	
	private static List<String> rejectedFolders = new ArrayList<String>();
//	static {
//		rejectedFolders.add("SINET Deployment");
//		rejectedFolders.add("Slim-Dash and Dash");
//		rejectedFolders.add("Seeing Machines");
//		rejectedFolders.add("HandHeld or QSI");
//		rejectedFolders.add("Blast Zones");
//		rejectedFolders.add("Post Rollout");
//		rejectedFolders.add("Driver Portal");
//		rejectedFolders.add("Development");
//		rejectedFolders.add("Halliburton Wireline");
//		rejectedFolders.add("Download (06)");
//		rejectedFolders.add("Download (07)");
//	}

	public CopyTestPlanAndCases(String username, String password, RallyWebServices space) {
		this.username=username;
		this.password=password;
		this.workspace=space;
	}
	
	
	public void copyTestCases(JSONObject newProject) throws JSONException {
		TestCase tc = new TestCase(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			String oldFolder = itr.next();
			String _ref = copyFolders.get(oldFolder).get("_ref");
			NameValuePair[] filterBy = {new NameValuePair("TestFolder.ObjectID",oldFolder)};
			List<JSONArray> testCases = tc.getTestCases(true, filterBy);
			for (JSONArray reply: testCases) {
				for (int i=0;i<reply.length();i++) {
					JSONObject testCase = reply.getJSONObject(i);
					testCase.put(TestCase.Fields.PROJECT.toString(), newProject);
					testCase.getJSONObject(TestCase.Fields.TEST_FOLDER.toString()).put("_ref", _ref);
					testCase.remove(TestCase.Fields.CREATION_DATE.toString());
					testCase.remove(TestCase.Fields.LAST_BUILD.toString());
					testCase.remove(TestCase.Fields.LAST_RUN.toString());
					testCase.remove(TestCase.Fields.LAST_VERDICT.toString());
					testCase.remove(TestCase.Fields.DEFECT_STATUS.toString());
					
					tc.createNewTestCase(testCase);
				}
			}
		}
	}
	
	public void moveTestCases(JSONObject newProject) throws JSONException {
		TestCase tc = new TestCase(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			String oldFolder = itr.next();
			String _ref = copyFolders.get(oldFolder).get("_ref");
			NameValuePair[] filterBy = {new NameValuePair("TestFolder.ObjectID",oldFolder)};
			List<JSONArray> testCases = tc.getTestCases(true, filterBy);
			for (JSONArray reply: testCases) {
				for (int i=0;i<reply.length();i++) {
					JSONObject testCase = reply.getJSONObject(i);
					testCase.put(TestCase.Fields.PROJECT.toString(), newProject);
					testCase.getJSONObject(TestCase.Fields.TEST_FOLDER.toString()).put("_ref", _ref);
					tc.update(testCase);
				}
			}
		}
	}
	
	
	public void deleteTestCases() throws JSONException {
		TestCase tc = new TestCase(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			String oldFolder = itr.next();
			NameValuePair[] filterBy = {new NameValuePair("TestFolder.ObjectID",oldFolder)};
			List<JSONArray> testCases = tc.getTestCases(true, filterBy);
			for (JSONArray reply: testCases) {
				for (int i=0;i<reply.length();i++) {
					JSONObject testCase = reply.getJSONObject(i);
					tc.getHttp().deleteObject(testCase);
				}
			}
		}
	}
	
	
	
	public void moveTestCases(String oldFolderID, String newFolderID) throws JSONException, URIException {
		TestCase tc = new TestCase(username, password, workspace);
		TestFolder tf = new TestFolder(tc);
		JSONObject oldFolder = tf.getFolder(false, new NameValuePair("FormattedID", oldFolderID));
		JSONObject newFolder = tf.getFolder(false, new NameValuePair("FormattedID", newFolderID));
		
		String[] old_raw_ref = oldFolder.getString("_ref").split("/");
		String[] new_raw_ref = newFolder.getString("_ref").split("/");
		
		String old_ref = old_raw_ref[old_raw_ref.length - 1].replace(".js", "");
		String new_ref = new_raw_ref[new_raw_ref.length - 1].replace(".js", "");
		
		NameValuePair[] folderFilter = {new NameValuePair("TestFolder.ObjectID",old_ref)};
		
		List<JSONArray> testCases = tc.getTestCases(true, folderFilter);
		for (JSONArray reply: testCases) {
			for (int i=0;i<reply.length();i++) {
				JSONObject testCase = reply.getJSONObject(i);
				testCase.getJSONObject("TestFolder").put("_ref", newFolder.getString("_ref"));
				tc.update(testCase);
			}
		}
	}
	


	private void moveTestCases(String folderToMoveTo, int[] testCases) throws URIException, JSONException {
		TestFolder tf = new TestFolder(username, password, workspace);
		TestCase tc = new TestCase(tf);
		
		JSONObject newFolder = tf.getFolder(false, new NameValuePair("FormattedID", folderToMoveTo));
		for (int testCase : testCases) {
			String next = "TC" + testCase;
			JSONObject actual = tc.get_test_case_by_ID(next, true);
			actual.put("TestFolder", newFolder);
			tc.update(actual);
			next = "";
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
				JSONObject child = children.getJSONObject(i);
				if (rejectedFolders.contains(child.getString("_refObjectName"))) {
					System.out.println("Rejected: " + child.getString("_refObjectName"));
					continue;
				}
				String[] _ref = child.getString("_ref").split("/");
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
	
	public void removeTestCasesByFolder(String folder) throws URIException, JSONException {
		TestFolder tf = new TestFolder(username, password, workspace);
		JSONObject folderObj = tf.getFolder(false, new NameValuePair("FormattedID", folder));
		String[] _ref = folderObj.getString("_ref").split("/");
		String folderID = _ref[_ref.length-1].replace(".js", "");
		TestCase tc = new TestCase(tf);
		List<JSONArray> cases = tc.getTestCases(new NameValuePair("TestFolder.ObjectID", folderID));
		for (JSONArray array : cases) {
			for (int i=0; i<array.length(); ++i) {
				tc.getHttp().deleteObject(array.getJSONObject(i));
			}
		}
		
	}
	
	public void deleteOldFolders() {
		if (copyFolders.isEmpty())getFolders();
		TestFolder folderDeath = new TestFolder(username, password, workspace);
		Iterator<String> itr = copyFolders.keySet().iterator();
		while (itr.hasNext()) {
			folderDeath.deleteFolder(itr.next());
		}
	}
	

	
	
	public static void main(String[] args) {
		AutomationCalendar start = new AutomationCalendar();
		try {
			String username=RallyWebServices.username, password=RallyWebServices.password;
			String secondProject="";
			String folderName = "TF";
			RallyWebServices space = RallyWebServices.SANDBOX;
//			Project other = new Project(username, password);
//			JSONObject otherProject = other.getProject(secondProject, space);
			
			CopyTestPlanAndCases testMe = new CopyTestPlanAndCases(username, password, space);
//			testMe.copyFolders(folderName);
//			testMe.deleteOldFolders();
//			testMe.createFolderStruc(otherProject);
//			testMe.moveTestCases(otherProject);
			
//			testMe.removeTestCasesByFolder(folderName);
//			testMe.moveTestCases("TF", "TF");
//			testMe.deleteTestCases();
			
//			String folderToMoveTo = "TF";
//			int[] testCases = {};
//			testMe.moveTestCases(folderToMoveTo, testCases);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		AutomationCalendar stop = new AutomationCalendar();
		System.out.println("Start: " + start);
		System.out.println("Stop : " + stop);
		System.out.println("Total time: " + stop.compareTo(start) + " seconds");
	}


}
