package com.inthinc.pro.web.selenium;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Try {
	private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("js");
    private Selenium selenium;
    private Error_Catcher errors;
    
    public Try(Selenium selenium, Error_Catcher errors){
    	this.errors = errors;
    	this.selenium = selenium;
    }
    
    public Try(Selenium selenium){
    	errors = new Error_Catcher();
    	this.selenium = selenium;
    }
	
	public void compare_strings(String command, Selenium selenium, String name, String expected){
		
		String evaluated = new String();
		try{
			evaluated = (String)engine.eval(command); 
			assert(evaluated==expected);
		}catch(ScriptException se){
			errors.Error(name+" ScriptExecution Error", se);
		}catch(AssertionError e){
			errors.Error(name, evaluated);
			errors.Expected(name, expected);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	
	public void assert_false( String command, String name){
		
		try{
			Boolean evaluated = (Boolean)engine.eval(command); 
			assert(!(evaluated));
		}catch(ScriptException se){
			errors.Error(name+" ScriptExecution Error", se);
		}catch(AssertionError e){
			errors.Error(name, e);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void assert_true( String command, String name){
		
		try{
			Boolean evaluated = (Boolean)engine.eval(command); 
			assert(evaluated);
		}catch(ScriptException se){
			errors.Error(name+" ScriptExecution Error", se);
		}catch(AssertionError e){
			errors.Error(name, e);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void selenium_command( String command, String name){
		try{
			engine.eval(command); 
		}catch(ScriptException se){
			errors.Error(name+" ScriptExecution Error", se);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
}
