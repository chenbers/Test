package com.inthinc.pro.automation.jbehave;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.enums.JBehaveTermMatchers;

public class AutoStepVariables {
    

    private static ThreadLocal<Map<String, String>> variables = new ThreadLocal<Map<String, String>>();
    

    public static boolean checkBoolean(String stepAsString){
        if (stepAsString.contains("not")){
            return false;
        }
        return true;
    }
    
    public static String getValue(String variable){
        if (variable.startsWith("\"") && variable.endsWith("\"")){
            return variable.substring(1, variable.length()-1);
        } else {
            return getVariables().get(variable);
        }
    }


    public static String getComparator(String stepAsString){
        String toReturn = "";
        
        stepAsString = StringEscapeUtils.unescapeJava(stepAsString);
        stepAsString = StringEscapeUtils.unescapeHtml(stepAsString);
        
        String elementType = JBehaveTermMatchers.getAlias(stepAsString);
        elementType = elementType == null ? "": elementType;
        String variable = RegexTerms.getMatch(RegexTerms.getVariable.replace("***", elementType), stepAsString);
        
        Map<String, String> temp = getVariables();
        if (temp.containsKey(variable)){
            toReturn = temp.get(variable);   
        } else if (variable.contains("\"")){
            toReturn = variable.substring(variable.indexOf("\"") + 1, variable.lastIndexOf("\""));
        } else {
            variable = getVariableName(stepAsString);
            if (!variable.isEmpty()){
                return temp.get(variable);
            } else if (stepAsString.contains("\"")){
                int quote = stepAsString.indexOf("\"") + 1;
                toReturn = stepAsString.substring(quote, stepAsString.indexOf("\"", quote));
                toReturn = StringEscapeUtils.unescapeJava(toReturn);
                toReturn = StringEscapeUtils.unescapeHtml(toReturn);
            }
        }
        toReturn = toReturn == null ? "" : toReturn;
        return toReturn;
    }
    
    public static String getVariableName(String stepAsString){
        for (String key : getVariables().keySet()){
            if (stepAsString.contains(key)){
                return key;
            }
        }
        return "";
    }
    

    public static void setComparator(final String stepAsString, final Object obj){
        String value = obj.toString();
        value = StringEscapeUtils.unescapeJava(value);
        value = StringEscapeUtils.unescapeHtml(value);
        
        String elementType = JBehaveTermMatchers.getAlias(stepAsString);
        String key = RegexTerms.getMatch(RegexTerms.setVariable.replace("***", elementType), stepAsString);
        
        if (!key.isEmpty()){
            getVariables().put(key, value.toString());
        }
    }

    public static Map<String, String> getVariables(){
        if (variables.get()==null){
            variables.set(new HashMap<String, String>());
        }
        return variables.get();
    }

}
