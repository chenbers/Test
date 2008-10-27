package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class Driver extends BaseEntity
{

    @ID
    private Integer driverID;

    private Integer companyID;
    private Integer groupID;
    private String firstName;
    private String lastName;
    private DOTType dot;

    public Driver(Integer driverID, Integer companyID, Integer groupID, String firstName, String lastName)
    {
        super();
        this.driverID = driverID;
        this.companyID = companyID;
        this.groupID = groupID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getDriverID()
    {
        return driverID;
    }


    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }


    public Integer getCompanyID()
    {
        return companyID;
    }


    public void setCompanyID(Integer companyID)
    {
        this.companyID = companyID;
    }


    public Integer getGroupID()
    {
        return groupID;
    }


    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }


    public String getFirstName()
    {
        return firstName;
    }


    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }


    public String getLastName()
    {
        return lastName;
    }


    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public DOTType getDot()
    {
        return dot;
    }


    public void setDot(DOTType dot)
    {
        this.dot = dot;
    }


        
}
