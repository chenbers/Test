package com.inthinc.pro.automation.logging;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.PrettyJSON;

public class Log {

    private static void log(Level level, String toPrint, Object... string) {
        print(toPrint, 3, level, string);
    }
    
    private static void log(Level level, Object print){
        print("%s", 3, level, print);
    }

    public static void debug(String TAG, Object... string) {
        log(Level.DEBUG, TAG, string);
    }

    public static void debug(Object print) {
        log(Level.DEBUG, print);
    }

    public static void info(String TAG, Object... string) {
        log(Level.INFO, TAG, string);
    }

    public static void info(Object print) {
        log(Level.INFO, print);
    }

    public static void error(String TAG, Object... string) {
        log(Level.ERROR, TAG, string);
    }

    public static void error(Object print) {
        log(Level.ERROR, print);
    }

    public static void trace(String TAG, Object... string) {
        log(Level.TRACE, TAG, string);
    }

    public static void trace(Object print) {
        log(Level.TRACE, print);
    }

    public static void warning(String TAG, Object... string) {
        log(Level.WARNING, TAG, string);
    }
    
    public static void warning(Object print){
        log(Level.WARNING, print);
    }
    

    public static enum Level{
        INFO,
        WARNING,
        DEBUG,
        ERROR,
        TRACE,
    }
    
    public static void print(String format, int stepsBack, Level level, Object ...items){
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
                converted[i] = StackToString.toString((Throwable) item);
            } else if (item instanceof JSONObject){
                converted[i] = PrettyJSON.toString(item);
            } else if (item instanceof StackTraceElement[]){
                converted[i] = StackToString.toString((StackTraceElement[]) item);
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
