package android.util;

public class Log extends com.inthinc.pro.automation.logging.Log {
    
    private static void log(Level level, String toPrint, Object ...string ){
        print(toPrint, 3, level, string);
    }
	
	public static void d(String TAG, Object ...string) {
		log(Level.DEBUG, TAG, string);
	}
	
	public static void i(String TAG, Object ...string) {
		log(Level.INFO, TAG, string);
	}
	
	public static void e(String TAG, Object ...string) {
		log(Level.ERROR, TAG, string);
	}
	
	public static void wtf(String TAG, Object ...string) {
		log(Level.TRACE, TAG, string);
	}

}
