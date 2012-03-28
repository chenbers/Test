package android.util;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.utils.MasterTest;

public class Log {

	/**
	 * t : Trace level logger
	 * @param print message to log
	 */
	public static void t(String print){
		MasterTest.print(print, Level.TRACE, 3);
	}

	/**
	 * t: Trace level logger
	 * @param TAG
	 * @param string
	 */
	public static void t(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.TRACE, string);
	}
	
	public static void d(String print){
		MasterTest.print(print, Level.DEBUG, 3);
	}

	public static void d(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.DEBUG, string);
	}

	public static void i(String print){
		MasterTest.print(print, Level.INFO, 3);
	}
	
	public static void i(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.INFO, string);
	}
	
	public static void e(String print){
		MasterTest.print(print, Level.ERROR, 3);
	}
	
	public static void e(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.ERROR, string);
	}

	public static void wtf(String print){
		MasterTest.print(print, Level.FATAL, 3);
	}
	
	public static void wtf(String TAG, Object ...string) {
		MasterTest.print(TAG, Level.FATAL, string);
	}

}
