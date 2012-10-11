package com.inthinc.pro.automation.utils;

/*
 * Example StackToString.toString(e);
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutomationStringUtil {


	
	
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
    

    private static String jsonString;
    private static Integer depth = 0;
    

    @SuppressWarnings("unchecked")
    public static <T> String toString(T item) {
    	depth = 0;
        StringWriter aStringAString = new StringWriter();
        try {
            if (item instanceof JSONObject) {
                aStringAString.write("{\n");
                printJSONObject(aStringAString, (JSONObject) item);
                aStringAString.write("}\n");
            } else if (item instanceof JSONArray) {
                aStringAString.write("[\n");
                printJSONArray(aStringAString, (JSONArray) item);
                aStringAString.write("]\n");
            } else if (item instanceof Throwable){
                Writer result = new StringWriter();
                PrintWriter printWriter = new PrintWriter(result);
                ((Throwable) item).printStackTrace(printWriter);
                return result.toString();
            } else if (item instanceof Object[]){
                StringWriter writer = new StringWriter();
                for (Object object : (T[])item){
                    writer.write(object.toString() + "\n");
                }
                return writer.toString();
            } else {
                return item.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonString = aStringAString.toString();
        try {
            aStringAString.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private static void printJSONObject(StringWriter aStringAString, JSONObject item) throws JSONException {
        Iterator<?> itr = item.keys();
        while (itr.hasNext()) {
            String next = (String) itr.next();
            if (!(item.get(next) instanceof JSONObject)) {
            	aStringAString.write(StringUtils.repeat("\t", depth));
            }
            if (item.get(next) instanceof String || item.get(next) == null) {
                printPair(aStringAString, next, item.getString(next));
            } else if (item.get(next) instanceof Integer){ 
                printPair(aStringAString, next, item.getInt(next));
            } else if (item.get(next) instanceof JSONObject) {
                aStringAString.write("{ \"" + next + "\":\n");
                depth ++;
                printJSONObject(aStringAString, item.getJSONObject(next));
                depth --;
                aStringAString.write(StringUtils.repeat("\t", depth) + "}\n");
            } else if (item.get(next) instanceof JSONArray) {
                aStringAString.write("\"" + next + "\": [\n");
                printJSONArray(aStringAString, item.getJSONArray(next));
                aStringAString.write(StringUtils.repeat("\t", depth) + "]\n");
            } else {
                printLine(aStringAString, next);
            }

        }
    }

    private static void printPair(StringWriter stringer, String key, String value) {
        if (value == null) {
            value = "";
        }
        stringer.write("\"" + key + "\":" + "\"" + value + "\",\n");
    }
    
    private static void printPair(StringWriter stringer, String key, int value){
        stringer.write("\"" + key + "\":" + value + ",\n");
    }

    private static void printLine(StringWriter stringer, String line) {
        stringer.write("\"" + line + "\"\n");
    }
    
    private static void printLine(StringWriter stringer, Integer line) {
        stringer.write("\"" + line + "\"\n");
    }

    private static void printJSONArray(StringWriter stringMeUp, JSONArray array) throws JSONException {
        depth ++;
        stringMeUp.write(StringUtils.repeat("\t", depth));
        for (int i = 0; i < array.length(); i++) {

            if (array.get(i) instanceof String){
                printLine(stringMeUp, array.getString(i));
            } else if (array.get(i) instanceof Integer) {
                printLine(stringMeUp, array.getInt(i));
            } else if (array.get(i) instanceof JSONObject) {
                printJSONObject(stringMeUp, array.getJSONObject(i));
            } else if (array.get(i) instanceof JSONArray) {
                printJSONArray(stringMeUp, array.getJSONArray(i));
            } 
            if (i < array.length() - 1) {
                stringMeUp.write(StringUtils.repeat("\t", depth));
            }
        }
        depth --;
    }

}
