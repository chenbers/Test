package com.inthinc.pro.model.aggregation;

public class DriverForgivenData {

    public DriverForgivenData() {
    }

    public DriverForgivenData(Long noteID, Integer forgivenByUserID, String reason, Integer driverID) {
        this.noteID = noteID;
        this.forgivenByUserID = forgivenByUserID;
        this.reason = reason;
        this.driverID = driverID;
    }

    private Integer driverID;

    private Long noteID;
    
    private Integer forgivenByUserID;

    private String reason;

    public Long getNoteID() {
        return noteID;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    public Integer getForgivenByUserID() {
        return forgivenByUserID;
    }

    public void setForgivenByUserID(Integer forgivenByUserID) {
        this.forgivenByUserID = forgivenByUserID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
}
