package com.inthinc.pro.aggregation.model;

import java.util.Date;
public class Device
{
    private Integer           deviceID;
    private Integer           accountID;
    private Integer           vehicleID;
    private Integer      status;
    private String            name;
    private String            imei; //waysmart iridium imei, tiwipro imei
    private String            sim;
    private String			  serialNum;//tiwipro and waysmart
    private String            phone;
    private Date              activated;
    private Integer           baseID;
    
    private Integer	firmwareVersion;
    private Integer	witnessVersion;
    private String emuMd5;
    private Integer productVersion;
    private String mcmid;   //Waysmart mcmid
    private String altimei; //Alternate Waysmart imei

	public Device()
    {
        super();
    }
    public Device(Integer deviceID, Integer accountID, Integer status, String name, String imei, String sim, String serialNum, String phone)
    {
        super();
        this.deviceID = deviceID;
        this.accountID = accountID;
        this.status = status;
        this.name = name;
        this.imei = imei;
        this.sim = sim;
        this.serialNum = serialNum;
        this.phone = phone;
    }

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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
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

    public Date getActivated()
    {
        return activated;
    }

    public void setActivated(Date activated)
    {
        this.activated = activated;
    }

    public String getImei()
    {
        return imei;
    }
    public void setImei(String imei)
    {
        this.imei = imei;
    }
    public String getSerialNum()
    {
        return serialNum;
    }
    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }
    public Integer getBaseID()
    {
        return baseID;
    }
    public void setBaseID(Integer baseID)
    {
        this.baseID = baseID;
    }
	public Integer getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(Integer firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public Integer getWitnessVersion() {
		return witnessVersion;
	}
	public void setWitnessVersion(Integer witnessVersion) {
		this.witnessVersion = witnessVersion;
	}
	public String getEmuMd5() {
		return emuMd5;
	}
	public void setEmuMd5(String emuMd5) {
		this.emuMd5 = emuMd5;
	}


    public boolean isWaySmart() {
        return 2 == this.productVersion;
    }
    public Integer getProductVersion() {
        return productVersion;
    }
    public void setProductVersion(Integer productVersion) {
        this.productVersion = productVersion;
    }
    public String getMcmid() {
        return mcmid;
    }
    public void setMcmid(String mcmid) {
        this.mcmid = mcmid;
    }
    public String getAltimei() {
        return altimei;
    }
    public void setAltimei(String altimei) {
        this.altimei = altimei;
    }
    public String toString() {
        return "Device: [name="+name+", deviceID="+deviceID+", imei="+this.imei+", altimei="+this.altimei+" ]";
    }
    public boolean isGPRSOnly() {
        return getImei() != null && getMcmid() != null && getImei().equalsIgnoreCase(getMcmid());
    }
}
