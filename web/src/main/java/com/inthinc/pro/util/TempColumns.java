package com.inthinc.pro.util;

public class TempColumns
{
    String colName;
    Boolean colValue;
    
    public TempColumns(String colName, Boolean colValue) {
        this.colName = colName;
        this.colValue = colValue;
    }

    public String getColName()
    {
      return colName;
    }

    public void setColName(String colName)
    {
        this.colName = colName;
    }

    public Boolean getColValue()
    {
        return colValue;
    }

    public void setColValue(Boolean colValue)
    {
        this.colValue = colValue;
    }
}
