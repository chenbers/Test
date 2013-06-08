package com.inthinc.pro.model.dvir;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.form.SubmissionData;

public class DVIRInspectionRepairReport {
    private Date dateTime;
    private String driverName;
    private String vehicleName;
    private Integer vehicleID;
    private String groupName;
    private Integer groupID;
    private String attr_formDefinitionID;
    private String attr_formSubmissionTime;
    private String attr_inspectorName;
    private String attr_mechanicName;
    private String attr_signOffName;
    private String attr_comments;
    
    private SubmissionData submissionData;
    
    private List<SubmissionData> submissionDataList;
    
    public DVIRInspectionRepairReport() {}
    
    public DVIRInspectionRepairReport(Date dateTime, String driverName, String vehicleName, Integer vehicleID, String groupName, Integer groupID, String attr_formDefinitionID,
                    String attr_formSubmissionTime, String attr_inspectorName, String attr_mechanicName, String attr_signOffName, String attr_comments) {
        this.dateTime = dateTime;
        this.driverName = driverName;
        this.vehicleName = vehicleName;
        this.vehicleID = vehicleID;
        this.groupName = groupName;
        this.groupID = groupID;
        
        this.attr_formDefinitionID = attr_formDefinitionID;
        this.attr_formSubmissionTime = attr_formSubmissionTime;
        this.attr_inspectorName = attr_inspectorName;
        this.attr_mechanicName = attr_mechanicName;
        this.attr_signOffName = attr_signOffName;
        this.attr_comments = attr_comments;
    }
    
    public Date getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public String getVehicleName() {
        return vehicleName;
    }
    
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    
    public Integer getVehicleID() {
        return vehicleID;
    }
    
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public Integer getGroupID() {
        return groupID;
    }
    
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    
    public String getAttr_formDefinitionID() {
        return attr_formDefinitionID;
    }
    
    public void setAttr_formDefinitionID(String attr_formDefinitionID) {
        this.attr_formDefinitionID = attr_formDefinitionID;
    }
    
    public String getAttr_formSubmissionTime() {
        return attr_formSubmissionTime;
    }
    
    public void setAttr_formSubmissionTime(String attr_formSubmissionTime) {
        this.attr_formSubmissionTime = attr_formSubmissionTime;
    }
    
    public String getAttr_inspectorName() {
        return attr_inspectorName;
    }
    
    public void setAttr_inspectorName(String attr_inspectorName) {
        this.attr_inspectorName = attr_inspectorName;
    }
    
    public String getAttr_mechanicName() {
        return attr_mechanicName;
    }
    
    public void setAttr_mechanicName(String attr_mechanicName) {
        this.attr_mechanicName = attr_mechanicName;
    }
    
    public String getAttr_signOffName() {
        return attr_signOffName;
    }
    
    public void setAttr_signOffName(String attr_signOffName) {
        this.attr_signOffName = attr_signOffName;
    }
    
    public String getAttr_comments() {
        return attr_comments;
    }
    
    public void setAttr_comments(String attr_comments) {
        this.attr_comments = attr_comments;
    }
    
    public SubmissionData getSubmissionData() {
        return submissionData;
    }
    
    public void setSubmissionData(SubmissionData submissionData) {
        this.submissionData = submissionData;
    }
    
    public List<SubmissionData> getSubmissionDataList() {
        List<SubmissionData> retVal = null;
        if (submissionData != null) {
            retVal = new ArrayList<SubmissionData>();
            retVal.add(submissionData);
        }
        return retVal;
    }
    
    public void setSubmissionDataList(List<SubmissionData> submissionDataList) {
        this.submissionDataList = submissionDataList;
    }
}
