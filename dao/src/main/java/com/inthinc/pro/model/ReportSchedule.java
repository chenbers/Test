package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class ReportSchedule
{
    @ID
    private Integer reportScheduleID;
    
    @Column(name = "acctID")
    private Integer accountID;
    
    @Column(name = "reportID")
    private Integer reportGroupID;
    
    private Integer userID;
    private List<String> emailTo;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date lastExecutedDate; //The last time this schedule was executed.
    private List<Boolean> dayOfWeek;
    private Integer driverID;
    private Integer vehicleID;
    private Integer groupID;
    private Status status;
    private Integer durationID;
    
    
    public ReportSchedule(Integer reportScheduleID,Integer accountID,Integer reportID,Integer userID,List<String> emailTo,String name,Date startDate
            ,Date endDate,List<Boolean> dayOfWeek,Integer driverID,Integer vehicleID,Integer groupID,Status status,Date lastExecutedDate)
    {
        this.reportScheduleID = reportScheduleID;
        this.accountID = accountID;
        this.reportGroupID = reportID;
        this.userID = userID;
        this.emailTo = emailTo;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayOfWeek = dayOfWeek;
        this.driverID = driverID;
        this.vehicleID = vehicleID;
        this.groupID= groupID;
        this.status = status;
        this.lastExecutedDate = lastExecutedDate;
    }
    
    public Integer getReportScheduleID()
    {
        return reportScheduleID;
    }
    public void setReportScheduleID(Integer reportScheduleID)
    {
        this.reportScheduleID = reportScheduleID;
    }
    public Integer getUserID()
    {
        return userID;
    }
    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }
    public List<String> getEmailTo()
    {
        return emailTo;
    }
    public void setEmailTo(List<String> emailTo)
    {
        this.emailTo = emailTo;
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
    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
    public List<Boolean> getDayOfWeek()
    {
        return dayOfWeek;
    }
    public void setDayOfWeek(List<Boolean> dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }
    public Integer getDriverID()
    {
        return driverID;
    }
    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }
    public Integer getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Status getStatus()
    {
        return status;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }
   
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("reportScheduleID="); sb.append(this.reportScheduleID);sb.append(",");
        sb.append("reportGroupID="); sb.append(this.reportGroupID);sb.append(",");
        sb.append("userID="); sb.append(this.userID);sb.append(",");
        sb.append("groupID="); sb.append(this.groupID);sb.append(",");
        sb.append("driverID="); sb.append(this.driverID);sb.append(",");
        sb.append("vehicle="); sb.append(this.vehicleID);sb.append(",");
        sb.append(")");
        return sb.toString();
    }

    public void setDurationID(Integer durationID)
    {
        this.durationID = durationID;
    }

    public Integer getDurationID()
    {
        return durationID;
    }

    public void setReportGroupID(Integer reportGroupID)
    {
        this.reportGroupID = reportGroupID;
    }

    public Integer getReportGroupID()
    {
        return reportGroupID;
    }

    public void setLastExecutedDate(Date lastExecutedDate)
    {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastExecutedDate()
    {
        return lastExecutedDate;
    }
    
}
