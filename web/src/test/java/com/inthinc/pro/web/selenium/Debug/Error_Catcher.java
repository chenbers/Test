package com.inthinc.pro.web.selenium.Debug;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import com.thoughtworks.selenium.SeleniumException;


public class Error_Catcher {
	
	
	private static HashMap<String, HashMap<String, String>> errors = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, String> errorList;
	
	/**
	 * Take an Assertion Error and add it to the error list
	 * 
	 * @param errorName
	 * @param AssertionError
	 */
	public void Error(String name, AssertionError error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("AssertionError", getStackTrace(error));
	}
	
	/**
	 * Take a Selenium Exception and add it to the error list
	 * 
	 * @param errorName
	 * @param SeleniumException
	 */
	public void Error(String name, SeleniumException error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("SeleniumException", getStackTrace(error));
	}
	
	
	/**
	 * Take a generel Exception and add it to the error list
	 * 
	 * @param name
	 * @param error
	 */
	public void Error(String name, Exception error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Exception", getStackTrace(error));
	}
	
	
	/**
	 * Take the Actual and add it to our string
	 * 
	 * @param name
	 * @param error
	 */
	public void Error(String name, String error){
		
		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Actual", error);
	}
	
	/**
	 * Take the expected string for comparison against the actual
	 * 
	 * @param name
	 * @param expected
	 */
	public void Expected( String name, String expected){

		if (!errors.containsKey(name)){
			add_error(name);
		}
		
		errors.get(name).put("Expected", expected);
	}
	
	/**
	 * Add an error by Name
	 * 
	 * @param name
	 */
	public void add_error(String name){
		errorList  = new HashMap<String, String>();
		errors.put(name, errorList);
		
	}
	
	public HashMap<String, HashMap<String, String>> get_errors(){
		return errors;
	}
	
	private static String getStackTrace(Throwable stack){
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		stack.printStackTrace(printWriter);
		return result.toString();
	}
}
