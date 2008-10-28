package com.inthinc.pro.model;

public class SafetyDevice extends BaseEntity
{
    Integer     id;
    Integer     type;                // tiwiPro, waySmart, etc
    Integer     assignedVehicleId;   // vehicleId assigned to device
    String      imei;
    String      deviceIdentification; // name/id defined by company
    String      smsNumber;           // ?? phone number
    String      firmwareVersion;     // ??
    PhoneNumber eCallNumber;         // ??

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getAssignedVehicleId()
    {
        return assignedVehicleId;
    }

    public void setAssignedVehicleId(Integer assignedVehicleId)
    {
        this.assignedVehicleId = assignedVehicleId;
    }

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }

    public String getDeviceIdentification()
    {
        return deviceIdentification;
    }

    public void setDeviceIdentification(String deviceIdentification)
    {
        this.deviceIdentification = deviceIdentification;
    }

    public String getSmsNumber()
    {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber)
    {
        this.smsNumber = smsNumber;
    }

    public String getFirmwareVersion()
    {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion)
    {
        this.firmwareVersion = firmwareVersion;
    }

    public PhoneNumber getECallNumber()
    {
        return eCallNumber;
    }

    public void setECallNumber(PhoneNumber callNumber)
    {
        eCallNumber = callNumber;
    }
}
