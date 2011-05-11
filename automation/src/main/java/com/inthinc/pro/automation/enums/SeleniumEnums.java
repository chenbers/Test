package com.inthinc.pro.automation.enums;


public interface SeleniumEnums extends TextEnum {
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
	
	public String[] getIDs();
	
	public String getURL();
}
