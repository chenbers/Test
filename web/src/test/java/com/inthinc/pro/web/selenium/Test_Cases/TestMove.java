package com.inthinc.pro.web.selenium.Test_Cases;

//import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.inthinc.pro.rally.Project;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;

public class TestMove {
	public static void main(String[] args) {
		try {
			String username="dtanner@inthinc.com", password="aOURh7PL5v";
			String secondProject="Automation";
			RallyWebServices space = RallyWebServices.INTHINC;
			
			TestCase caseWorker = new TestCase(username, password, space);
			
//			final Logger logger = Logger.getLogger(RallyWebServices.class);
//			logger.setLevel(Level.DEBUG);
			Project other = new Project(username, password);
			JSONObject otherProject = other.getProject(secondProject, space);
			
			JSONObject testCase = caseWorker.getTestCase("TC1896");
			testCase.remove("TestFolder");
			testCase.put("Project", otherProject);
			caseWorker.update(testCase);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
