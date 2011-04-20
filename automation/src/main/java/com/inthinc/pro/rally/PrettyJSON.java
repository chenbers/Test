package com.inthinc.pro.rally;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrettyJSON {

    private static String jsonString;
    private static Integer depth = 0;

    public static String toString(Object item) {
        StringWriter aStringAString = new StringWriter();
        try {
            if (item instanceof JSONObject) {
                printJSONObject(aStringAString, (JSONObject) item);
            } else if (item instanceof JSONArray) {
                printJSONArray(aStringAString, (JSONArray) item);
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
            aStringAString.write(StringUtils.repeat("\t", depth));
            if (item.get(next) instanceof String || item.get(next) == null) {
                printPair(aStringAString, next, item.getString(next));
            } else if (item.get(next) instanceof JSONObject) {
                aStringAString.write("{ \"" + next + "\":\n");
                depth += 1;
                printJSONObject(aStringAString, item.getJSONObject(next));
                depth -= 1;
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

    private static void printLine(StringWriter stringer, String line) {
        stringer.write("\"" + line + "\"\n");
    }

    private static void printJSONArray(StringWriter stringMeUp, JSONArray array) throws JSONException {
        depth += 1;
        for (int i = 0; i < array.length(); i++) {

            if (array.get(i) instanceof String)
                printLine(stringMeUp, array.getString(i));
            else if (array.get(i) instanceof JSONObject) {
                printJSONObject(stringMeUp, array.getJSONObject(i));
            } else if (array.get(i) instanceof JSONArray) {
                printJSONArray(stringMeUp, array.getJSONArray(i));
            }
            if (i < array.length() - 1) {
                stringMeUp.write(StringUtils.repeat("\t", depth));
            }
        }
        depth -= 1;
    }
}
