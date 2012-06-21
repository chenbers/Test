package com.inthinc.pro.automation.jbehave;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @see <code>Pattern</code>
 * @author dtanner
 *
 */


public class RegexTerms {
    
    public static final List<String> typeReg = new ArrayList<String>();
    static {
        typeReg.add("\\w+(?=\\s+as\\s+\\w*)\\b");  // Used when Saving a variable
        typeReg.add("\\w+(?=\\s+is\\s+\\w*)\\b");  // Used to compare a variable
        typeReg.add("\\w+(?=\\s+for\\s+\\w*)\\b"); // Used to compare a variable
        typeReg.add("\\w+$");                      // Standard action should end in the type.
        

        typeReg.add("\\w+(?=\\s+contains\\s+\\w*)\\b"); // Used to compare a variable or text
        typeReg.add("\\w+(?=\\s+does\\s+\\w*)\\b"); // Used to compare a variable or text
    }
    
    
    public static final String onPage = "on\\s+the\\s+.*\\s+page";
    public static final String getPageName = "(?<=the\\s).*(?=\\s+[Pp]age)";
    public static final String getElementName = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)*)(?=\\s+***)";
    
    public static final String getRowNumber = "\\p{Digit}*(?=\\w+\\s+Row)"; // Grabs the number before the suffix
    public static final String getRowTextNumber = "\\w+(?=\\s+Row)";
    
    public static final String addLowercaseWordSpaceBefore = "\\s+[a-z]+"; // Matches any lower case word with a space in front
    public static final String addAnyCaseWordSpaceAfter = "\\w+\\s+";
    
    public static final String getMethod = "(?<=I\\s)[a-z]+"; // The method name should come right after I
    
    public static final String getVariable = "(?<=***\\sis\\s).*";
    public static final String setVariable = "(?<=***\\sas\\s).*";
    
    public static final String saveAlias = "(save|retain|remember)"; // Any of these just become an alias

    public static final String testCase = "[Tt][Cc]\\p{Digit}+"; // Rally test cases are TC####
    public static final String defect = "[Dd][Ee]\\p{Digit}+"; // Rally defects are DE####
    
    
    public static final String popupStep = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)*)(?=\\s+popup)";
    
    public static final String calendarDayDelta = "\\p{Digit}*(?=\\s+day|\\s+days)";
    public static final String calendarMonthDelta = "\\p{Digit}*(?=\\s+month|\\s+months)";
    public static final String calendarYearDelta = "\\p{Digit}*(?=\\s+year|\\s+years)";
    
    public static final String calendarSubtract = "[Pp]ast";
    public static final String calendarAdd = "[Ff]uture";
    
    public static final String findRow = "find\\s+the\\s+[Rr]ow";
    public static final String rowVariable = "(?<=has\\s).*(?=\\s+in)";
    public static final String saveRowVariable = "(?<=save\\sit\\sas\\s).*";
    
    /**
     * An abstracted method to extract matching strings.
     * 
     * @param pattern
     * @param toSearch
     * @return
     */
    public static String getMatch(String pattern, String toSearch){
        Pattern pat = Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher mat = pat.matcher(toSearch);
        return mat.find() ? toSearch.substring(mat.start(), mat.end()) : "";
    }

    
}
