package com.inthinc.pro.automation.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@XmlRootElement
public class Device extends BaseEntity {
    private static final long serialVersionUID = 2865030663439253720L;

    private Integer deviceID;
    private Integer accountID;
    private Integer vehicleID;
    private DeviceStatus status;
    private String name;
    private String imei; // waysmart iridium imei, tiwipro imei
    private String sim;
    private String serialNum;// tiwipro and waysmart
    private String phone;
    
    @JsonIgnore
    private final AutomationCalendar activated = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    private Integer baseID;
    private Integer productVer;

    private Integer firmwareVersion;
    private Integer witnessVersion;
    private String emuMd5;
    private ProductType productVersion;
    private String mcmid; // Waysmart mcmid
    private String altimei; // Alternate Waysmart imei
    /*
     * waySmartSerialNumber
     * 
     * MCM[0-9][0-9][0-9][0-9][0-9][0-9]
     * 
     * The Iridium IMEI will match this rule:
     * 
     * List of valid prefixes: 30000300 30003401 30012400 30012401 30023401
     * 
     * followed by 7 digits
     */

    public Device() {
        super();
    }

    public Device(Integer deviceID, Integer accountID, DeviceStatus status, String name, String imei, String sim, String serialNum, String phone) {
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

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("activated")
    public String getActivatedString() {
        return activated.toString();
    }

    public AutomationCalendar getActivated() {
        return activated;
    }

    @JsonProperty("activated")
    public void setActivated(String activated) {
        this.activated.setDate(activated);
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Integer getBaseID() {
        return baseID;
    }

    public void setBaseID(Integer baseID) {
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
    public boolean isCrashTraceAppletCapable() {
        // TODO: jwimmer: another stopgap until a better way to determine device capabilities is implemented
        return (ProductType.WAYSMART.equals(this.productVersion));
    }

    public boolean isWaySmart() {
        return ProductType.WAYSMART.equals(this.productVersion);
    }

    public ProductType getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(ProductType productVersion) {
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Device: [name=" + name + ", deviceID=" + deviceID + ", imei=" + this.imei + ", altimei=" + this.altimei + " ]";
    }

    public boolean isGPRSOnly() {
        return getImei() != null && getMcmid() != null && getImei().equalsIgnoreCase(getMcmid());
    }

    public Integer getProductVer() {
        return productVer;
    }

    public void setProductVer(Integer productVer) {
        this.productVer = productVer;
        if (productVer != null) {
            setProductVersion(ProductType.getProductTypeFromVersion(productVer));
        }
    }
}
