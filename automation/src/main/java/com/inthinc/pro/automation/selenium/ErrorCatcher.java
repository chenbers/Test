package com.inthinc.pro.automation.selenium;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.utils.MasterTest.ErrorLevel;
import com.inthinc.pro.automation.utils.StackToString;
import com.thoughtworks.selenium.SeleniumException;

/****************************************************************************************
 * Purpose: To catch the errors raised by Selenium, and format them into a nice HashMap<br />
 * <p>
 * The idea is to take a stack trace, or string, and tie it to an error name.<br />
 * Then associate that error with a name for easy reading to see what broke.<br />
 * 
 * @author dtanner
 * @see HashMap
 */
public class ErrorCatcher implements InvocationHandler {
    private final static Logger logger = Logger.getLogger(ErrorCatcher.class);
    private HashMap<String, HashMap<String, String>> errors = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, String> errorList;
    private final CoreMethodLib delegate;

    public ErrorCatcher(CoreMethodLib delegate){
    	this.delegate=delegate;
    }
    
    public CoreMethodInterface newInstance(){
    	return (CoreMethodInterface) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), getInterfaces(delegate.getClass()), this);
    }
    
    private static Class<?>[] getInterfaces(Class<?> c) {
        List<Class<?>> result = new ArrayList<Class<?>>();
        if (c.isInterface()) {
            result.add(c);
        } else {
            do {
                addInterfaces(c, result);
                c = c.getSuperclass();
            } while (c != null);
        }
        for (int i = 0; i < result.size(); ++i) {
            addInterfaces(result.get(i), result);
        }
        return result.toArray(new Class<?>[result.size()]);
    }
    
    private static void addInterfaces(Class<?> c, List<Class<?>> list) {
        for (Class<?> intf: c.getInterfaces()) {
            if (!list.contains(intf)) {
                list.add(intf);
            }
        }
    }

    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
    	Object result = null;
    	try {
    	    logger.debug("before method " + method.getName());
    	    result = method.invoke(delegate, args);
        } catch (InvocationTargetException e){
        	sortErrors(e.getCause());
        } catch (Exception e) {
        	sortErrors(e);
    	} finally {
    		logger.debug("after method " + method.getName());
    	}
    	return result;
    }
    
    private void sortErrors(Throwable e) {
    	if (e instanceof SeleniumException){
    		addError(delegate.getErrorName(), e);
    	} else {
    		addError(delegate.getErrorName(), e);
    	}
	}

	/**
     * Add an error by Name<br />
     * 
     * @param errorName
     */
    private void add_error(String name) {
        errorList = new HashMap<String, String>();
        errors.put(name, errorList);
    }
    
    /**
     * Adds the error to the error list<br />
     * 
     * @param name
     * @param error
     */
    public void addError(String name, Object error) {
    	addError(name, error, ErrorLevel.ERROR);
    }

    /**
     * Adds the error to the error list<br />
     * 
     * @param name
     * @param error
     * @param level
     */
    public void addError(String name, Object error, ErrorLevel level) {
        String errorStr = null;
        String type = error.getClass().getSimpleName();
        if (error instanceof String) {
            errorStr = addStackTrace((String) error);
            type = "Warning";
        } else if (error instanceof Throwable) {
            // type = "Framework Thrown Exception";
            errorStr = RallyStrings.toString((Throwable) error);
        } else if (error instanceof StackTraceElement[]) {
            type = "Tester Thrown Error";
            errorStr = RallyStrings.toString((StackTraceElement[]) error);
        }
        addError(name, type, errorStr, level);
    }
    
    private String addStackTrace(String error){
    	StackTraceElement[] stack = Thread.currentThread().getStackTrace();
    	Integer length = stack.length;
    	String errorStr = error +"\n\n"+RallyStrings.toString(Arrays.copyOfRange(stack,4,length));
    	return errorStr;
    }
    

    /**
     * Helper method for other error(name, error) methods. <br />
     * 
     * @param name
     * @param type
     *            of error
     * @param error
     *            text
     */
    private void addError(String name, String type, String error, ErrorLevel level ) {
        logger.info("\n"+name + " :\n\t" + type + " :\n" + error);
        if (!errors.containsKey(name)) {
            add_error(name);
        }
        errors.get(name).put(type, error);
        if (level == ErrorLevel.FAIL){
            throw new AssertionError(error);
        }
    }
    
    /**
     * Take the expected string for comparison against the actual<br />
     * 
     * @param errorName
     * @param expected
     */
    public void addExpected(String name, String expected) {

        if (!errors.containsKey(name)) {
            add_error(name);
        }

        errors.get(name).put("Expected", expected);
    }

    /**
     * @return the errors we have stored in a double HashMap
     * @see HashMap
     */
    public HashMap<String, HashMap<String, String>> get_errors() {
        return errors;
    }

    public Boolean isEmpty() {
        return errors.isEmpty();
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        Iterator<String> itr = errors.keySet().iterator();
        String newLine = "\n", tab = "\t";

        while (itr.hasNext()) {
            String next = itr.next();
            stringWriter.write(next + newLine);
            Set<String> itr2 = errors.get(next).keySet();
            Iterator<String> innerItr = itr2.iterator();
            while (innerItr.hasNext()) {
                String insideNext = innerItr.next();
                String callIt = errors.get(next).get(insideNext);

                stringWriter.write(tab);
                stringWriter.write(insideNext);
                stringWriter.write(newLine);
                if (!callIt.startsWith(StringUtils.repeat(tab, 2))) {
                    stringWriter.write(StringUtils.repeat(tab, 2));
                }
                stringWriter.write(callIt);
                stringWriter.write(StringUtils.repeat(newLine, 2));
            }
        }
        String sendMeOut = stringWriter.toString();
        try {
            stringWriter.close();
        } catch (IOException e) {
            logger.fatal(StackToString.toString(e));
        }
        return sendMeOut;
    }
}