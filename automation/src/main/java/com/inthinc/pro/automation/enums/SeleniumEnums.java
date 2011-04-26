package com.inthinc.pro.automation.enums;

public interface SeleniumEnums {
    
    public static String email = "E-mail";
    public static String emailReport = email + " This Report";
    public static String exportPDF = "Export To PDF";
    public static String exportExcel = "Export To Excel";
    public static String cancel = "Cancel";
    public static String save = "Save";
    public static String delete = "Delete";
    public static String batchEdit = "Batch Edit";
    public static String search = "Search";
    public static String appUrl = "/app";
    public static String editColumns = "Edit Columns";
	
	public String getText();
	
	public void setText(String text);

	public String getID();
	
	public String getURL();

	public String getXpath();

	public String getXpath_alt();
	
	
}
