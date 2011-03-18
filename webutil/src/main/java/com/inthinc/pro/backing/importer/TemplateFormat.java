package com.inthinc.pro.backing.importer;

public abstract class TemplateFormat {
    
    public abstract int getNumColumns();
    
    public abstract boolean isColumnManditory(int columnIndex);
    
    public abstract boolean isColumnValid(int columnIndex, String value);

    public abstract String getInvalidMessage(int columnIndex);
}
