package com.inthinc.pro.web.selenium;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.inthinc.pro.web.selenium.portal.Singleton;
/****************************************************************************************
 * Purpose: 
 * @author 
 * Last Update:  
 ****************************************************************************************/

public class TestClassTemplate extends Selenium_Server {
	
	//Define Class Objects
	private final String testcase_object_form_id = "//form[@id='testcaseForm']/";

	protected static Core selenium;

	public TestClassTemplate(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public TestClassTemplate(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public TestClassTemplate( Core sel ){
			selenium = sel;
		}
	
	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
		
	public static void main( String[] args){
			try {
						TestClassTemplate.setUp();
					} catch (Exception e) {
						e.printStackTrace();
					}
			TestClassTemplate login;
			login = new TestClassTemplate();
			//login.test_self();
			Object errors = "";
			errors = login.get_errors().get_errors();
			System.out.println(errors.toString());	
			try{
						tearDown();
					}catch(Exception e){
						e.printStackTrace();
					}
			assertTrue(errors.toString()=="{}");
		}

	
}

