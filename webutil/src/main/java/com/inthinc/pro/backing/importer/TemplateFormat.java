package com.inthinc.pro.backing.importer;


public abstract class TemplateFormat {
    
    public abstract ColumnFormat[] getColumns();

    public int getNumColumns() {
        return getColumns().length;
    }

    public boolean isColumnManditory(int columnIndex) {
        return getColumns()[columnIndex].isManditory();
    }

    public boolean isColumnValid(int columnIndex, String value) {
        return getColumns()[columnIndex].getValidator().isValid(value);
    }

    public String getInvalidMessage(int columnIndex, String value) {
        return getColumns()[columnIndex].getValidator().getInvalidMessage(value);
    }
    
    public String getColumnLabel(int columnIndex) {
        return getColumns()[columnIndex].getName();
    }
    
}
