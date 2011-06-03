package com.inthinc.pro.automation.enums;


public interface SeleniumEnums extends TextEnum {
    //TODO: place Strings that are used repeatedly here, until they can be pulled from .properties (potentially messages.properties)
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
    public static String addresses = "E-mail Address(es): (e-mail addresses separated by a comma)";
    
    
    public static String appUrl = "/app"; 
	
	public String[] getIDs();
	
	public String getURL();
}
