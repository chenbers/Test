package com.inthinc.pro.web.selenium.Debug;

import java.util.HashMap;

import com.thoughtworks.selenium.SeleniumException;


public class Error_Catcher {
	
	
	private static HashMap<String, HashMap<String, String>> errors = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, String> errorList;
	
	public void Error(String name, AssertionError error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("AssertionError", error.getStackTrace().toString());
	}
	
	public void Error(String name, SeleniumException error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("SeleniumException", error.getStackTrace().toString());
	}
	
	
	public void Error(String name, Exception error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Exception", error.getStackTrace().toString());
	}
	
	
	public void Error(String name, String error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Actual", error);
	}
	
	public void Expected( String name, String expected){

		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Expected", expected);
	}
	
	public void add_error(String name){
		errorList  = new HashMap<String, String>();
		errors.put(name, errorList);
		
	}
	
	public HashMap<String, HashMap<String, String>> get_errors(){
		
		return errors;
	}
	
	
}
