package com.inthinc.pro.reports.hos.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.hos.model.RuleSetType;

@XmlRootElement
public class ViolationsDetail implements Comparable<ViolationsDetail> {
    
    private String groupName;
    private String timeStr;
    private Date notificationTime;
    private String employeeId;
    private String driverName;
    private String vehicleId;
    private RuleSetType ruleType;
    List<Violation> violationsList;
    
    private Integer driverID;
    private Integer vehicleID;
    private Integer groupID;
    
    
    public ViolationsDetail()
    {
        
    }
    public ViolationsDetail(String groupName, Date notificationTime, String employeeId, String driverName, String vehicleId, RuleSetType ruleType, List<Violation> violationsList) {
        this.groupName = groupName;
        this.notificationTime = notificationTime;
        this.employeeId = employeeId;
        this.driverName = driverName;
        this.vehicleId = vehicleId;
        this.ruleType = ruleType;
        this.violationsList = violationsList;
    }
    @XmlTransient
    public RuleSetType getRuleType() {
        return ruleType;
    }
    public void setRuleType(RuleSetType ruleType) {
        this.ruleType = ruleType;
    }
    public List<Violation> getViolationsList() {
        return violationsList;
    }
    public void setViolationsList(List<Violation> violationsList) {
        this.violationsList = violationsList;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    @XmlElement(name="time")
    public Date getNotificationTime() {
        return notificationTime;
    }
    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    @XmlElement(name="vehicleName")
    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    @XmlTransient
    public String getTimeStr() {
        return timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Override
    public int compareTo(ViolationsDetail o) {
        
        int cmp = driverName.compareTo(o.getDriverName());
        if (cmp == 0) {
            cmp = notificationTime.compareTo(o.getNotificationTime());
        }
        
        return cmp;
    }

    public void dump() {
        System.out.println("new ViolationsDetail(\"" + getGroupName() + "\"," + 
                "new Date("+getNotificationTime().getTime() + "l)," + 
                "\"" + getEmployeeId() + "\"," + 
                "\"" + getDriverName() + "\"," + 
                "\"" + getVehicleId() + "\"," + 
                "RuleSetType." + getRuleType().getName() + "," +
                "Arrays.asList(");
        for (Violation v : violationsList) {
                System.out.println("    new Violation(" +
                        "RuleSetType." + getRuleType().getName() + "," +
                        "RuleViolationTypes." + v.getType().getName() + "," +
                        v.getMinutes() + "l),");
        }
        System.out.println(")),");
          
      }

}
