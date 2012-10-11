package com.inthinc.pro.automation.interfaces;



public interface SeleniumEnums extends TextEnum {
    //TODO: place Strings that are used repeatedly here, until they can be pulled from .properties (potentially messages.properties)
    public final static String email = "E-mail";
    public final static String emailReport = email + " This Report";
    public final static String exportPDF = "Export To PDF";
    public final static String exportExcel = "Export To Excel";
    public final static String cancel = "Cancel";
    public final static String save = "Save";
    public final static String delete = "Delete";
    public final static String batchEdit = "Batch Edit";
    public final static String search = "Search";
    public final static String editColumns = "Edit Columns";
    public final static String addresses = "E-mail Address(es): (e-mail addresses separated by a comma)";
    
    public final static String appUrl = "app"; 
    public final static String configuratorUrl = "configurator";
    
	public String[] getIDs();
	
	public String getURL();
}
