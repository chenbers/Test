package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.ID;

public class TablePreference
{
    @ID
    private Integer tablePrefID;
    
    private Integer userID;
    private TableType tableType;
    private List<Boolean> visible;
    public Integer getTablePrefID()
    {
        return tablePrefID;
    }
    public void setTablePrefID(Integer tablePrefID)
    {
        this.tablePrefID = tablePrefID;
    }
    public Integer getUserID()
    {
        return userID;
    }
    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }
    public TableType getTableType()
    {
        return tableType;
    }
    public void setTableType(TableType tableType)
    {
        this.tableType = tableType;
    }
    public List<Boolean> getVisible()
    {
        return visible;
    }
    public void setVisible(List<Boolean> visible)
    {
        this.visible = visible;
    }
}
