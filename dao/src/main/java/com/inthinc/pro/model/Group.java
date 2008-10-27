package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class Group extends BaseEntity
{
    @ID
    private Integer groupID;
    
    private Integer companyID;
    private String name;
    private Integer parentID;
    
    public Group()
    {
        super();
    }
    public Group(Integer groupID, Integer companyID, String name, Integer parentID)
    {
        super();
        this.groupID = groupID;
        this.companyID = companyID;
        this.name = name;
        this.parentID = parentID;
    }
    
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Integer getCompanyID()
    {
        return companyID;
    }
    public void setCompanyID(Integer companyID)
    {
        this.companyID = companyID;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Integer getParentID()
    {
        return parentID;
    }
    public void setParentID(Integer parentID)
    {
        this.parentID = parentID;
    }
    
    
    
}
