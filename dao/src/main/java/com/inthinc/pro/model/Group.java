package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Group extends BaseEntity
{
    @ID
    private Integer groupID;
    
    @Column(name = "acctID")
    private Integer accountID;
    private String name;
    private Integer parentID;
    private GroupStatus status;
    
    public Group()
    {
        super();
    }
    public Group(Integer groupID, Integer accountID, String name, Integer parentID)
    {
        super();
        this.groupID = groupID;
        this.accountID = accountID;
        this.name = name;
        this.parentID = parentID;
        this.status = GroupStatus.GROUP_ACTIVE;
    }
    
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Integer getAccountID()
    {
        return accountID;
    }
    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
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
    public GroupStatus getStatus()
    {
        return status;
    }
    public void setStatus(GroupStatus status)
    {
        this.status = status;
    }
}
