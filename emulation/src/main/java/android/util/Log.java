package android.util;

import com.inthinc.pro.automation.logging.Log.Level;

public class Log {
    
    private static void log(Level level, String toPrint, Object ...string ){
        if (!toPrint.contains("%s")){
            toPrint += " %s";
        }
    	com.inthinc.pro.automation.logging.Log.print(toPrint, 3, level, string);
    }

	public static int d(String TAG, String string) {
		log(Level.DEBUG, TAG, string);
		return 1;
	}

	public static int i(String TAG, String string) {
		log(Level.INFO, TAG, string);
		return 1;
	}

	public static int e(String TAG, String string) {
		log(Level.ERROR, TAG, string);
		return 1;
	}

	public static int wtf(String TAG, String string) {
		log(Level.TRACE, TAG, string);
		return 1;
	}

}