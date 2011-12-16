package com.inthinc.pro.automation.utils;

/*
 * Example StackToString.toString(e);
 * 
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class StackToString {

	public final static String toString(Throwable stack){
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		stack.printStackTrace(printWriter);
		return result.toString();
	}

	public static <T> String toString(T[] printToScreen) {
		StringWriter writer = new StringWriter();
		for (Object object : printToScreen){
			writer.write(object.toString() + "\n");
		}
		return writer.toString();
	}
}
