package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Device extends BaseEntity
{
    @ID
    private Integer      deviceID;
    @Column(name = "acctID")
    private Integer      accountID;
    @Column(updateable = false)
    private Integer      vehicleID;
    @Column(name = "baseID")
    private Integer      baselineID;
    private DeviceStatus status;
    private String       name;
    private String       mcmid;
    private String       sim;
    private String       phone;
    private String       ephone;
    private Date         activated;

    public Integer getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID)
    {
        this.deviceID = deviceID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public Integer getBaselineID()
    {
        return baselineID;
    }

    public void setBaselineID(Integer baseID)
    {
        this.baselineID = baseID;
    }

    public DeviceStatus getStatus()
    {
        return status;
    }

    public void setStatus(DeviceStatus status)
    {
        this.status = status;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMcmid()
    {
        return mcmid;
    }

    public void setMcmid(String mcmid)
    {
        this.mcmid = mcmid;
    }

    public String getSim()
    {
        return sim;
    }

    public void setSim(String sim)
    {
        this.sim = sim;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEphone()
    {
        return ephone;
    }

    public void setEphone(String ephone)
    {
        this.ephone = ephone;
    }

    public Date getActivated()
    {
        return activated;
    }

    public void setActivated(Date activated)
    {
        this.activated = activated;
    }
}
