package com.inthinc.pro.automation.logging;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.automation.utils.AutomationStringUtil;
import com.inthinc.pro.rally.PrettyJSON;

/**
 * Simple class to handle different levels of debugging,<br />
 * using custom logging method.
 * @author dtanner
 *
 */
public class Log {

    /**
     * Internal method to handle overloading
     * @param level
     * @param toPrint
     * @param string
     */
    private static void log(Level level, String toPrint, Object... string) {
        print(toPrint, 3, level, string);
    }
    
    /**
     * Internal method to handle one print statement
     * @param level
     * @param print
     */
    private static void log(Level level, Object print){
        print("%s", 3, level, print);
    }

    /** 
     * Debug method that handles a format <code>TAG</code> <br />
     * and then a vararg list of objects to print
     * @param TAG
     * @param string
     */
    public static void debug(String TAG, Object... string) {
        log(Level.DEBUG, TAG, string);
    }

    /**
     * Debug method that will print the object to the console
     * @param print
     */
    public static void debug(Object print) {
        log(Level.DEBUG, print);
    }

    /** 
     * Info method that handles a format <code>TAG</code> <br />
     * and then a vararg list of objects to print
     * @param TAG
     * @param string
     */
    public static void info(String TAG, Object... string) {
        log(Level.INFO, TAG, string);
    }

    /**
     * Info method that will print the object to the console
     * @param print
     */
    public static void info(Object print) {
        log(Level.INFO, print);
    }

    /** 
     * Error method that handles a format <code>TAG</code> <br />
     * and then a vararg list of objects to print
     * @param TAG
     * @param string
     */
    public static void error(String TAG, Object... string) {
        log(Level.ERROR, TAG, string);
    }

    /**
     * Error method that will print the object to the console
     * @param print
     */
    public static void error(Object print) {
        log(Level.ERROR, print);
    }

    /** 
     * Trace method that handles a format <code>TAG</code> <br />
     * and then a vararg list of objects to print
     * @param TAG
     * @param string
     */
    public static void trace(String TAG, Object... string) {
        log(Level.TRACE, TAG, string);
    }

    /**
     * Trace method that will print the object to the console
     * @param print
     */
    public static void trace(Object print) {
        log(Level.TRACE, print);
    }

    /** 
     * Warning method that handles a format <code>TAG</code> <br />
     * and then a vararg list of objects to print
     * @param TAG
     * @param string
     */
    public static void warning(String TAG, Object... string) {
        log(Level.WARNING, TAG, string);
    }
    
    /**
     * Warning method that will print the object to the console
     * @param print
     */
    public static void warning(Object print){
        log(Level.WARNING, print);
    }
    

    /**
     * Simple enum to handle the different print levels.
     * @author dtanner
     *
     */
    public static enum Level{
        INFO,
        WARNING,
        DEBUG,
        ERROR,
        TRACE,
    }
    
    /**
     * This print method will print things out based on the <code>Level</code><br />
     * that is provided, then will step back in the stack trace to to check<br />
     * what level of printing that class has, if it is not supposed to print<br />
     * we will stop and return.<br />
     * Otherwise we will format the string and print it out with the class<br />
     * name that called log, and the line number, then print out the statement.
     * 
     * @param format
     * @param stepsBack
     * @param level
     * @param items
     */
    public static void print(final String format, int stepsBack, final Level level, final Object ...items){
        stepsBack++;
        StackTraceElement element = Thread.currentThread().getStackTrace()[stepsBack];
        
        Logger logger = LoggerFactory.getLogger(element.getClassName());
        boolean print = false;
        switch (level) {
            case INFO:    print = logger.isInfoEnabled(); break;
            case WARNING: print = logger.isWarnEnabled(); break;
            case DEBUG:   print = logger.isDebugEnabled(); break;
            case ERROR:   print = logger.isErrorEnabled(); break;
            case TRACE:   print = logger.isTraceEnabled(); break;
            default :     print = logger.isDebugEnabled(); break;
            
        }
        if (!print) return;
            
        
        Object[] converted = new Object[items.length+2];
        converted[0] = element.getFileName().replace(".java", "").replace(".class", "");
        converted[1] = new Integer(element.getLineNumber()).toString();
        for (int i=0+2;i<converted.length;i++){
            Object item = items[i-2];
            if (item == null){
                converted[i] = "error";
            } else if (item instanceof Throwable){
                converted[i] = AutomationStringUtil.toString((Throwable) item);
            } else if (item instanceof JSONObject){
                converted[i] = PrettyJSON.toString(item);
            } else if (item instanceof StackTraceElement[]){
                converted[i] = AutomationStringUtil.toString((StackTraceElement[]) item);
            } else if (item instanceof Number){
                converted[i] = item;
            } else {
                converted[i] = item.toString();
            }
        }
        String msg = String.format("(%s:%3s) - " + format + "\n", converted);
        switch (level) {
            case INFO:    logger.info(msg); break;
            case WARNING: logger.warn(msg); break;
            case DEBUG:   logger.debug(msg); break;
            case ERROR:   logger.error(msg); break;
            case TRACE:   logger.trace(msg); break;
            default :     logger.debug(msg); break;
        }
    }

}
