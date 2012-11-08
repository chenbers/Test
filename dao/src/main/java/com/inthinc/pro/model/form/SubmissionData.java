package com.inthinc.pro.model.form;

import java.util.List;

public class SubmissionData {

    private Integer driverID;
    private String driverName;
    private String driverTimeZoneID;
    private Integer vehicleID;
    private String vehicleName;
    private Integer groupID;
    private String groupName;
    private String groupPath;
    private Integer formID;
    private Long timestamp;
    private Boolean approved;
    private String formTitle;
    private List<SubmissionDataItem> dataList;

    public SubmissionData() {

    }

    public SubmissionData(Integer driverID, String driverName, Integer vehicleID, String vehicleName, Integer groupID, Integer formID, Long timestamp, Boolean approved, String formTitle) {
        super();
        this.driverID = driverID;
        this.driverName = driverName;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.groupID = groupID;
        this.formID = formID;
        this.timestamp = timestamp;
        this.approved = approved;
        this.formTitle = formTitle;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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

    public Integer getFormID() {
        return formID;
    }

    public void setFormID(Integer formID) {
        this.formID = formID;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public List<SubmissionDataItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<SubmissionDataItem> dataList) {
        this.dataList = dataList;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getDriverTimeZoneID() {
        return driverTimeZoneID;
    }

    public void setDriverTimeZoneID(String driverTimeZoneID) {
        this.driverTimeZoneID = driverTimeZoneID;
    }
}
