package com.inthinc.pro.model.hos;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;

public class HOSDeltaRecord {

    private HOSStatus status;
    private Date date;

    public HOSDeltaRecord() {}

    public HOSDeltaRecord(HOSStatus status, Date date) {
        this.status = status;
        this.date = date;
    }

    public HOSStatus getStatus() {
        return status;
    }

    public void setStatus(HOSStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
