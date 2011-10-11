package com.inthinc.pro.automation.selenium;

import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.utils.MasterTest.ErrorLevel;
import com.inthinc.pro.rally.RallyStrings;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;
import com.thoughtworks.selenium.SeleniumException;

/****************************************************************************************
 * Purpose: To catch the errors raised by Selenium, and format them into a nice <code>Map</code><br />
 * <p>
 * The idea is to take a stack trace, or string, and tie it to an error name.<br />
 * Then associate that error with a name for easy reading to see what broke.<br />
 * 
 * @author dtanner
 * @see Map
 */
public class ErrorCatcher implements InvocationHandler {

    private final static Logger logger = Logger.getLogger(ErrorCatcher.class);
    private Map<ErrorLevel, Map<String, Map<String, String>>> severity;
    private Map<String, Map<String, String>> errors;
    private Map<String, String> errorList;
    private final CoreMethodLib delegate;
    private Long startTime;
    private Long DEFAULT_TEST_TIMEOUT = 10*60L;
    
    public ErrorCatcher(){
        startTime = System.currentTimeMillis()/1000;
        delegate = null;
        severity = new HashMap<ErrorLevel, Map<String, Map<String, String>>>();
        for (ErrorLevel level: EnumSet.allOf(ErrorLevel.class)){
            if (level.equals(ErrorLevel.FATAL)){
                continue;
            }
            errors = new HashMap<String, Map<String, String>>();
            severity.put(level, errors);
        }
    }

    public ErrorCatcher(CoreMethodLib delegate) {
        startTime = System.currentTimeMillis()/1000;
        this.delegate = delegate;
        severity = new HashMap<ErrorLevel, Map<String, Map<String, String>>>();
        for (ErrorLevel level: EnumSet.allOf(ErrorLevel.class)){
            if (level.equals(ErrorLevel.FATAL)){
                continue;
            }
            errors = new HashMap<String, Map<String, String>>();
            severity.put(level, errors);
        }
    }

    public CoreMethodInterface newInstanceOfSelenium() {
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
        for (Class<?> intf : c.getInterfaces()) {
            if (!list.contains(intf)) {
                list.add(intf);
            }
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            if(startTime+DEFAULT_TEST_TIMEOUT  < currentTime() )
                addError("Test timed out", "Test timed out after "+(currentTime()-startTime)+" seconds", ErrorLevel.FATAL);
            logger.debug("before method " + method.getName());
            result = method.invoke(delegate, args);
        } catch (InvocationTargetException e) {
            sortErrors(e.getCause(), method, args);
        } catch (Exception e) {
            sortErrors(e, method, args);
        } finally {
            logger.debug("after method " + method.getName());
        }
        return result;
    }

    private void sortErrors(Throwable e, Method method, Object[] args) {
        StringWriter writer = new StringWriter();
        ErrorLevel level = ErrorLevel.ERROR;
        writer.write(method.getName() + "( ");
        if (args != null) {
            int length = args.length;
            for (Object arg : args) {
                if (arg instanceof String) {
                    writer.write("\"" + arg.toString() + "\"");
                } else {
                    writer.write(arg.toString());
                }
                if (--length != 0) {
                    writer.write(", ");
                }
            }
        }
        writer.write(" );\n");
        if (args != null){
            for (Object arg : args) {
                if (arg instanceof SeleniumEnumWrapper) {
                    writer.write(arg.toString() + ": " + ((SeleniumEnumWrapper) arg).getLocatorsAsString() + "\n");
                }
            }
        }

        if (e instanceof SeleniumException) {
            addError(writer.toString(), e, level);
        } else {
            addError(writer.toString(), e, level);
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
//    public void addError(String name, Object error) {
//        addError(name, error, ErrorLevel.ERROR);
//    }

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
//            type = "Framework Thrown Exception";
            errorStr = RallyStrings.toString((Throwable) error);
        } else if (error instanceof StackTraceElement[]) {
            type = "Tester Thrown Error";
            errorStr = RallyStrings.toString((StackTraceElement[]) error);
        }
        if (errorStr.contains("Timed out")){
            level = ErrorLevel.WARN;
        }
        addError(name, type, errorStr, level);
    }

    private String addStackTrace(String error) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        Integer length = stack.length;
        String errorStr = error + "\n\n" + RallyStrings.toString(Arrays.copyOfRange(stack, 4, length));
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
    private void addError(String name, String type, String error, ErrorLevel level) {
        logger.info("\n" + level + ": " +name + "\n\t" + type + " :\n" + error + "\n");
        ErrorLevel temp = level;
        if (level.equals(ErrorLevel.FATAL)){
            level = ErrorLevel.FAIL;
        }
        errors = severity.get(level);
        if (!errors.containsKey(name)) {
            add_error(name);
        }
        errors.get(name).put(type, error);
        if (temp.equals(ErrorLevel.FATAL)) {
            throw new AssertionError(error);
        }
    }

    /**
     * @return the errors we have stored in a triple HashMap
     * @see HashMap
     */
    public Map<ErrorLevel, Map<String, Map<String, String>>> get_errors() {
        return severity;
    }

    public Boolean hasFail(){
        return !severity.get(ErrorLevel.FAIL).isEmpty();
    }
    
    public Boolean hasCompare(){
        return !severity.get(ErrorLevel.COMPARE).isEmpty();
    }
    
    public Boolean hasInconclusive(){
        return !severity.get(ErrorLevel.INCONCLUSIVE).isEmpty();
    }
    
    public Boolean hasWarn(){
        return !severity.get(ErrorLevel.WARN).isEmpty();
    }
    
    public Boolean hasError(){
        return !severity.get(ErrorLevel.ERROR).isEmpty();
    }
    
    public Verdicts getHighestLevel(){
        ErrorLevel temp;
        if (hasFail()){
            temp = ErrorLevel.FAIL;
        } else if (hasError()){
            temp = ErrorLevel.ERROR;
        } else if (hasWarn()){
            temp = ErrorLevel.WARN;
        } else if (hasInconclusive()){
            temp = ErrorLevel.INCONCLUSIVE;
        } else if (hasCompare()){
            temp = ErrorLevel.COMPARE;
        } else {
            temp = ErrorLevel.PASS;
        }
        return temp.getVerdict();
    }
    
    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        Iterator<ErrorLevel> itrs = severity.keySet().iterator();
        String newLine = "\n", tab = "\t";
        int tabLevel = 0;
        
        while (itrs.hasNext()){
            ErrorLevel level = itrs.next();
            Iterator<String> itr = severity.get(level).keySet().iterator();
            logger.debug(severity.get(level));
            errors = severity.get(level);
            if (severity.get(level).isEmpty()){
                continue;
            }
            stringWriter.write(level.name() + " <" + newLine);
            while (itr.hasNext()) {
                String next = itr.next();
                stringWriter.write(StringUtils.repeat(tab, ++tabLevel));
                stringWriter.write(next + " <" + newLine);
                Set<String> itr2 = errors.get(next).keySet();
                Iterator<String> innerItr = itr2.iterator();
                while (innerItr.hasNext()) {
                    String insideNext = innerItr.next();
                    String callIt = errors.get(next).get(insideNext);
                    stringWriter.write(StringUtils.repeat(tab, ++tabLevel));
                    stringWriter.write(insideNext + "<" + newLine);

                    if (!callIt.startsWith(StringUtils.repeat(tab, ++tabLevel))) {
                        stringWriter.write(StringUtils.repeat(tab, tabLevel--));
                    }
                    stringWriter.write(callIt);
                    
                    stringWriter.write(StringUtils.repeat(tab, tabLevel));
                    stringWriter.write(">" + newLine);
                }
                stringWriter.write(StringUtils.repeat(tab, --tabLevel));
                stringWriter.write(">");
                stringWriter.write(StringUtils.repeat(newLine, 1));
                --tabLevel;
            }
            stringWriter.write(">");
            stringWriter.write(StringUtils.repeat(newLine, 1));
        }
        return stringWriter.toString();
    }
    public static Long currentTime(){
        return System.currentTimeMillis()/1000;
    }
}