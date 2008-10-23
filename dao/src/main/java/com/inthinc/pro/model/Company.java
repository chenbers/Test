package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class Company
{
    @ID
    private Integer companyID;
    
    private String name;
    
    public Company()
    {
        
    }
    public Company(Integer companyID, String name)
    {
        this.companyID = companyID;
        this.name = name;
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
    
    
    

}
