package com.inthinc.pro.reports;

public enum FormatType
{
    PDF(".pdf","pdf document","application/pdf"),
    EXCEL(".xls","Excel Document","application/xls"),
    HTML(".html","HTML Document","text/html; charset=UTF-8"),
    CSV(".csv","CSV Document","");
    
    private String suffix;
    private String label;
    private String contentType;
    
    private FormatType(String suffix,String label,String contentType)
    {
        this.suffix = suffix;
        this.label = label;
        this.contentType = contentType;
    }
    
    public String getSuffix()
    {
        return suffix;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public String getContentType()
    {
        return contentType;
    }
}
