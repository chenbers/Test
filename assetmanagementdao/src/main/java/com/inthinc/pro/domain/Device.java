package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.configurator.model.DeviceStatus;
import com.inthinc.pro.configurator.model.ProductType;
@XmlRootElement
@Entity
@Table(name="device")
public class Device
{
    @Id
    private Integer           deviceID;
    @Column(name = "acctID")
    private Integer           accountID;
    private DeviceStatus      status;
    private String            name;
    private String            imei; //waysmart iridium imei, tiwipro imei
    private String            sim;
    private String			  serialNum;//tiwipro and waysmart
    private String            phone;
	@Temporal(TemporalType.TIMESTAMP)
    private Date              activated;
    private Integer           baseID;
    
    @Column(name="firmVer")
    private Integer	firmwareVersion;
    @Column(name="witnessVer")
    private Integer	witnessVersion;
    private String emuMd5;
    @Column(name="productVer")
    private Integer productVersion;
    private String mcmid;   //Waysmart mcmid
    @Column(name = "altImei")
    private String altimei; //Alternate Waysmart imei
    
    //unused stuff just for now to get Hibernate working
    private String ephone;
    private String speedset;
    private String brake;
    private String accel;
    private String turn;
    private String vert;
	@Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    
/* 
 * waySmartSerialNumber
 * 
 * MCM[0-9][0-9][0-9][0-9][0-9][0-9]

 * The Iridium IMEI will match this rule:

 * List of valid prefixes:
 * 30000300
 * 30003401
 * 30012400
 * 30012401
 * 30023401

 * followed by 7 digits 
 */
	public Device()
    {
        super();
    }
    public Device(Integer deviceID, Integer accountID, DeviceStatus status, String name, String imei, String sim, String serialNum, String phone)
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

    /**
     * Determine if this device is capable of receiving a text message?
     * 
     * @return true if device can receive a text message, otherwise false
     */
    public boolean isTextMsgReceiveCapable() {
        // TODO: jwimmer: this is a stopgap until a better way to determine device capabilities is implemented
        return ProductType.WAYSMART.equals(this.productVersion);
    }
    
    /**
     * Determine if this device is capable of producing a Waysmart (accelerometer based???) crash trace.
     * 
     * @return true if device can produce a Waysmart (accelerometer based???) crash trace, otherwise false.
     */
    public boolean isCrashTraceAppletCapable(){
        //TODO: jwimmer: another stopgap until a better way to determine device capabilities is implemented
        return (ProductType.WAYSMART.equals(this.productVersion));
    }

    public boolean isWaySmart() {
        return ProductType.WAYSMART.equals(this.productVersion);
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
//    @Override
//    public String toString() {
//        return "Device: [name="+name+", deviceID="+deviceID+", imei="+this.imei+", altimei="+this.altimei+" ]";
//    }
    public boolean isGPRSOnly() {
        return getImei() != null && getMcmid() != null && getImei().equalsIgnoreCase(getMcmid());
    }
	public String getEphone() {
		return ephone;
	}
	public void setEphone(String ephone) {
		this.ephone = ephone;
	}
	public String getSpeedset() {
		return speedset;
	}
	public void setSpeedset(String speedset) {
		this.speedset = speedset;
	}
	public String getBrake() {
		return brake;
	}
	public void setBrake(String brake) {
		this.brake = brake;
	}
	public String getAccel() {
		return accel;
	}
	public void setAccel(String accel) {
		this.accel = accel;
	}
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
	}
	public String getVert() {
		return vert;
	}
	public void setVert(String vert) {
		this.vert = vert;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	@Override
	public String toString() {
		return "Device [deviceID=" + deviceID + ", accountID=" + accountID
				+", status=" + status + ", name="
				+ name + ", imei=" + imei + ", sim=" + sim + ", serialNum="
				+ serialNum + ", phone=" + phone + ", activated=" + activated
				+ ", baseID=" + baseID + ", firmwareVersion=" + firmwareVersion
				+ ", witnessVersion=" + witnessVersion + ", emuMd5=" + emuMd5
				+ ", productVersion=" + productVersion + ", mcmid=" + mcmid
				+ ", altimei=" + altimei + ", ephone=" + ephone + ", speedset="
				+ speedset + ", brake=" + brake + ", accel=" + accel
				+ ", turn=" + turn + ", vert=" + vert + ", modified="
				+ modified + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountID == null) ? 0 : accountID.hashCode());
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		result = prime * result + ((mcmid == null) ? 0 : mcmid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((productVersion == null) ? 0 : productVersion.hashCode());
		result = prime * result
				+ ((serialNum == null) ? 0 : serialNum.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (accountID == null) {
			if (other.accountID != null)
				return false;
		} else if (!accountID.equals(other.accountID))
			return false;
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		if (mcmid == null) {
			if (other.mcmid != null)
				return false;
		} else if (!mcmid.equals(other.mcmid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productVersion == null) {
			if (other.productVersion != null)
				return false;
		} else if (!productVersion.equals(other.productVersion))
			return false;
		if (serialNum == null) {
			if (other.serialNum != null)
				return false;
		} else if (!serialNum.equals(other.serialNum))
			return false;
		return true;
	}
}
