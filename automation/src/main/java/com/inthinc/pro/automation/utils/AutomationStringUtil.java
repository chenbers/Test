package com.inthinc.pro.automation.utils;

/*
 * Example StackToString.toString(e);
 * 
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

public class AutomationStringUtil {

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
	
	
	public static String capitalizeFirstLettersTokenizer ( final String s ) {
        return capitalizeString(s, " ");
    }

    public static String capitalizeString(final String s, final String split){
        StringTokenizer st = new StringTokenizer( s, split, true );
        StringBuilder sb = new StringBuilder();
         
        while ( st.hasMoreTokens() ) {
            String token = st.nextToken();
            token = String.format( "%s%s",
                                    Character.toUpperCase(token.charAt(0)),
                                    token.substring(1).toLowerCase() );
            sb.append( token );
        }
            
        return sb.toString();
    }

    public static String captalizeEnumName(final String s){
        String formatted = capitalizeString(s, "_").replace("_", "");
        return Character.toLowerCase(formatted.charAt(0)) + formatted.substring(1);
    }
    
    

    public static String switchCase(final String s){
        StringWriter writer = new StringWriter();
        for (Character c : s.toCharArray()){
            if(Character.isUpperCase(c)){
                writer.write(Character.toLowerCase(c));
            } else {
                writer.write(Character.toUpperCase(c));
            }
        }
        return writer.toString();
    }
    
    public static String unescapeHtml(String original) {
        return StringEscapeUtils.unescapeHtml(original);
    }
    

}
