package android.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.utils.MasterTest;

public class Log {
    private static final Logger log4j = Logger.getLogger(Log.class);
    
    private static void log(Level level, String toPrint, Object ...string ){
        if (log4j.getLevel() == null){
            if (log4j.getParent().getLevel()==null){
                log4j.setLevel(log4j.getParent().getParent().getLevel());
            } else {
                log4j.setLevel(log4j.getParent().getLevel());
            }
                
        }
        if (level.isGreaterOrEqual(log4j.getLevel())){
            if (string!=null){
                toPrint = String.format(toPrint, string);
            }
            MasterTest.print(toPrint, level, 4);    
        }
        
    }
	
//	public static void d(String print){
//		log(print, Level.DEBUG);
//	}

	public static void d(String TAG, Object ...string) {
		log(Level.DEBUG, TAG, string);
	}

//	public static void i(String print){
//		log(print, Level.INFO);
//	}
	
	public static void i(String TAG, Object ...string) {
		log(Level.INFO, TAG, string);
	}
	
//	public static void e(String print){
//		log(Level.ERROR, print);
//	}
	
	public static void e(String TAG, Object ...string) {
		log(Level.ERROR, TAG, string);
	}

//	public static void wtf(String print){
//		log(Level.FATAL, print);
//	}
	
	public static void wtf(String TAG, Object ...string) {
		log(Level.FATAL, TAG, string);
	}

}
