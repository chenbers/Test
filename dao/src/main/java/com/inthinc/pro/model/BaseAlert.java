package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;

public abstract class BaseAlert extends BaseEntity
{
    @Column(updateable = false)
    public static final int MIN_TOD = 0;
    @Column(updateable = false)
    public static final int MAX_TOD = 1439;

    @Column(name = "acctID")
    private Integer         accountID;
    private Integer         groupID;
    private String          name;
    private String          description;
    private Integer         startTOD;
    private Integer         stopTOD;
    private List<Integer>   dayOfWeek;
    private List<Integer>   notifyPersonIDs;
    private List<String>    emailTo;

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getStartTOD()
    {
        return startTOD;
    }

    public void setStartTOD(Integer startTOD)
    {
        this.startTOD = startTOD;
    }

    public Integer getStopTOD()
    {
        return stopTOD;
    }

    public void setStopTOD(Integer stopTOD)
    {
        this.stopTOD = stopTOD;
    }

    public List<Integer> getDayOfWeek()
    {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<Integer> dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getNotifyPersonIDs()
    {
        return notifyPersonIDs;
    }

    public void setNotifyPersonIDs(List<Integer> notifyUserIDs)
    {
        this.notifyPersonIDs = notifyUserIDs;
    }

    public List<String> getEmailTo()
    {
        return emailTo;
    }

    public void setEmailTo(List<String> emailTo)
    {
        this.emailTo = emailTo;
    }
}
