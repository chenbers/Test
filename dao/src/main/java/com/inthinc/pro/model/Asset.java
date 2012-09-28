package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Asset extends BaseEntity
{
    private String tiwiproID;
    private String assignedVehicle;
    private String IMEI;
    private String phoneNbr;
    private String status;
    
    public String getTiwiproID()
    {
        return tiwiproID;
    }
    public void setTiwiproID(String tiwiproID)
    {
        this.tiwiproID = tiwiproID;
    }
    public String getAssignedVehicle()
    {
        return assignedVehicle;
    }
    public void setAssignedVehicle(String assignedVehicle)
    {
        this.assignedVehicle = assignedVehicle;
    }
    public String getIMEI()
    {
        return IMEI;
    }
    public void setIMEI(String imei)
    {
        IMEI = imei;
    }
    public String getPhoneNbr()
    {
        return phoneNbr;
    }
    public void setPhoneNbr(String phoneNbr)
    {
        this.phoneNbr = phoneNbr;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
}
