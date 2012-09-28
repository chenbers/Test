package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@SimpleName(simpleName="TablePref")
public class TablePreference
{
    @ID
    private Integer tablePrefID;
    
    private Integer userID;
    private TableType tableType;
    private List<Boolean> visible;
    
    private String flags;
    
    
    public TablePreference()
    {
        
    }
    public TablePreference(Integer tablePrefID, Integer userID, TableType tableType, List<Boolean> visible)
    {
        super();
        this.tablePrefID = tablePrefID;
        this.userID = userID;
        this.tableType = tableType;
        this.visible = visible;
    }
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
    public String getFlags()
    {
        return flags;
    }
    public void setFlags(String flags)
    {
        this.flags = flags;
    }
}
