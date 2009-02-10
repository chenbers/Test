 package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;

@SimpleName(simpleName="ReportPref")
public class ReportSchedule
{
    @ID
    @Column(name="reportPrefID")
    private Integer reportScheduleID;
    
    @Column(name = "acctID")
    private Integer accountID;
    
    @Column(name = "reportID")
    private Integer reportID;
    
    private Integer userID;
    private List<String> emailTo;
    private String name;
    private Date startDate;
    private Date endDate;
    private Integer timeOfDay;
    private Date lastDate; //The last time this schedule was executed.
    private List<Boolean> dayOfWeek;
    private Integer driverID;
    private Integer vehicleID;
    private Integer groupID;
    private Status status;
    
    @Column(name="duration")
    private Duration reportDuration;

    private Occurrence occurrence;
    
    public ReportSchedule()
    {
        
    }
    
    public ReportSchedule(Integer reportScheduleID,Integer accountID,Integer reportID,Integer userID,List<String> emailTo,String name,Date startDate
            ,Date endDate,List<Boolean> dayOfWeek,Integer driverID,Integer vehicleID,Integer groupID,Status status,Date lastDate,Integer timeOfDay)
    {
        this.reportScheduleID = reportScheduleID;
        this.accountID = accountID;
        this.reportID = reportID;
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
        this.setLastDate(lastDate);
        this.timeOfDay = timeOfDay;
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
    
    public String getEmailToAsString(){
        StringBuilder sb = new StringBuilder("");
        if(emailTo != null && !emailTo.isEmpty())
        {
            for(String email:emailTo)
            {
                sb.append(email + ",");
            }
        }
        
        return sb.toString();
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
        sb.append("reportGroupID="); sb.append(this.reportID);sb.append(",");
        sb.append("userID="); sb.append(this.userID);sb.append(",");
        sb.append("groupID="); sb.append(this.groupID);sb.append(",");
        sb.append("driverID="); sb.append(this.driverID);sb.append(",");
        sb.append("vehicle="); sb.append(this.vehicleID);sb.append(",");
        sb.append(")");
        return sb.toString();
    }

    public void setReportID(Integer reportID)
    {
        this.reportID = reportID;
    }

    public Integer getReportID()
    {
        return reportID;
    }

    public void setTimeOfDay(Integer timeOfDay)
    {
        this.timeOfDay = timeOfDay;
    }

    public Integer getTimeOfDay()
    {
        return timeOfDay;
    }

    public void setReportDuration(Duration reportDuration)
    {
        this.reportDuration = reportDuration;
    }

    public Duration getReportDuration()
    {
        return reportDuration;
    }

    public void setOccurrence(Occurrence occurrence)
    {
        this.occurrence = occurrence;
    }

    public Occurrence getOccurrence()
    {
        return occurrence;
    }

    public void setLastDate(Date lastDate)
    {
        this.lastDate = lastDate;
    }

    public Date getLastDate()
    {
        return lastDate;
    }
    
}
