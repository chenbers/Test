package com.inthinc.pro.automation.jbehave;

import java.util.ArrayList;
import java.util.List;

public class RegexTerms {
    
    public final static List<String> typeReg = new ArrayList<String>();
    static {
        typeReg.add("\\w*(?=\\s+as\\s+\\w*)\\b");  // Used when Saving a variable
        typeReg.add("\\w*(?=\\s+is\\s+\\w*)\\b");  // Used to compare a variable
        typeReg.add("\\w*(?=\\s+for\\s+\\w*)\\b"); // Used to compare a variable
        typeReg.add("\\w+$");                      // Standard action should end in the type.
    }
    
    
    public static final String onPage = "on\\s+the\\s+.*\\s+page";
    public static final String getPageName = "(?<=the ).*(?= [Pp]age)";
    public static final String getElementName = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)*)(?=\\s+***)";
    public static final String getColumnName = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)*)(?=\\s+column)";
    public static final String getRowName = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)*)(?=\\s+row)";
    
    public static final String addLowercaseWord = "\\s+[a-z]+";
    
    public static final String getMethod = "(?<=I )[a-z]+";
    
    public static final String getVariable = "(?<=is ).*";
    public static final String setVariable = "(?<=as ).*";
    
    public static final List<String> saveAlias = new ArrayList<String>();
    static {
        saveAlias.add("save");
        saveAlias.add("retain");
        saveAlias.add("remember");
    }
    
    

    
}
