package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;

public class SeleniumEnum{
    
    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public static List<String> locators(SeleniumEnums anEnum) {
        ArrayList<String> locators = new ArrayList<String>();
        if(anEnum.getID() != null)
            locators.add(anEnum.getID());
        if(anEnum.getXpath()!= null)
            locators.add(anEnum.getXpath());
        if(anEnum.getXpath_alt() != null)
            locators.add(anEnum.getXpath_alt());
        return locators;
    }
    

    public interface SeleniumEnums {
        //TODO: jwimmer: question for dTanner: not sure what these Strings are?  let's leverage enums for situations when we need more than just key/value pairs.  These look like things that should be in .properties files? or possibly even pulled from messages.properties
        public static String email = "E-mail";
        public static String emailReport = email + " This Report";
        public static String exportPDF = "Export To PDF";
        public static String exportExcel = "Export To Excel";
        public static String cancel = "Cancel";
        public static String save = "Save";
        public static String delete = "Delete";
        public static String batchEdit = "Batch Edit";
        public static String search = "Search";
        public static String editColumns = "Edit Columns";
        
        public static String appUrl = "/app"; //TODO: jwimmer: question for dTanner: this is captured in automation.properties
    	
    	public String getText();
    	
    	public void setText(String text);
    
    	public String getID();
    	
    	public String getURL();
    
    	public String getXpath();
    
    	public String getXpath_alt();
    	
    	public List<String> getLocators();
    	
    	public SeleniumEnums replaceWord(String word);
    	
    	public SeleniumEnums replaceNumber(String number);
    	
    }
}
