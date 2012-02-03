package android.util;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.utils.MasterTest;

public class Log {

	public static void d(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.DEBUG, 2, string);
	}
	
	public static void i(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.INFO, 2, string);
	}
	
	public static void e(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.ERROR, 2, string);
	}

	public static void wtf(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.FATAL, 2, string);
	}

}
