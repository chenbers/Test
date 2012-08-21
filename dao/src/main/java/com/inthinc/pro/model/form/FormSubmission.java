package com.inthinc.pro.model.form;

import java.util.List;

public class FormSubmission {
    
    private Long timestamp;
    private Integer vehicleID;
    private String formTitle;
    private List<FormQuestion> dataList;
    
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public String getFormTitle() {
        return formTitle;
    }
    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }
    public List<FormQuestion> getDataList() {
        return dataList;
    }
    public void setDataList(List<FormQuestion> dataList) {
        this.dataList = dataList;
    }    
    @Override
    public String toString() {
        StringBuilder formSubmission = new StringBuilder("\n"+formTitle + "\n ");
        for(FormQuestion fq : dataList){
            formSubmission.append(fq.toString());
        }
        return formSubmission.toString();
    }
    
}
