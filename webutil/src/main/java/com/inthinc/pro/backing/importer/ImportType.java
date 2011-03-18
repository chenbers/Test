package com.inthinc.pro.backing.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ImportType {
    DRIVERS(1, "Drivers", "DriverTemplate.xls", new DriverTemplateFormat()),
    VEHICLES(2, "Vehicles", "VehicleTemplate.xls", new VehicleTemplateFormat());
    
    private int    code;
    private String description;
    private String template;
    private TemplateFormat templateFormat;

    private ImportType(int code, String description, String template, TemplateFormat templateFormat)
    {
        this.code = code;
        this.description = description;
        this.template = template;
        this.templateFormat = templateFormat;
    }

    public TemplateFormat getTemplateFormat() {
        return templateFormat;
    }

    public void setTemplateFormat(TemplateFormat templateFormat) {
        this.templateFormat = templateFormat;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    private static final Map<Integer, ImportType> lookup = new HashMap<Integer, ImportType>();
    static
    {
        for (ImportType p : EnumSet.allOf(ImportType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static ImportType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    
    public static String PACKAGE_PATH = "com/inthinc/pro/download/";
    public InputStream loadTemplate() throws IOException
    {
        String path = PACKAGE_PATH + getTemplate();
        InputStream inputStream = BulkImportBean.class.getClassLoader().getResourceAsStream(path);
        if(inputStream == null){
            throw new IOException("Could not find file: " + getTemplate());
        }
        return inputStream;
    }


}
