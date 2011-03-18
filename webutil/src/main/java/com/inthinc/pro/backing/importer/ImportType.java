package com.inthinc.pro.backing.importer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ImportType {
    DRIVERS(1, "Drivers", "DriverTemplate.xls"),
    VEHICLES(2, "Vehicles", "VehicleTemplate.xls");
    
    private int    code;
    private String description;
    private String template;

    private ImportType(int code, String description, String template)
    {
        this.code = code;
        this.description = description;
        this.template = template;
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

}
