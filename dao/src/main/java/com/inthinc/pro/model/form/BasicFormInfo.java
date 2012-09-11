package com.inthinc.pro.model.form;


public class BasicFormInfo {
    private Integer formID;
    private String name;
    private Integer trigger;
    private String md5Hash;

    public BasicFormInfo() {

    }

    public BasicFormInfo(Integer formID, String name, TriggerType triggerType, String md5Hash) {
        this.formID = formID;
        this.name = name;
        this.trigger = triggerType.getCode();
        this.md5Hash = md5Hash;
    }

    public Integer getFormID() {
        return formID;
    }

    public void setFormID(Integer formID) {
        this.formID = formID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrigger() {
        return trigger;
    }

    public void setTrigger(Integer trigger) {
        this.trigger = trigger;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

}
