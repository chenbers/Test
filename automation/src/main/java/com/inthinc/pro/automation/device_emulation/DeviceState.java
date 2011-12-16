package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;

public class DeviceState {

    private int accountID = 1;

    private int boundaryID = 1;

    private int driverID = 1;
    private String employeeID = "";

    private Heading heading = Heading.NORTH;

    private Boolean ignition_state = false;
    private final String imei;
    private String lastDownload;
    private int linkID = 0;
    private Integer mapRev = 0;
    private String mcmID;

    private int odometer = 0;
    private Boolean power_state = false;
    private final ProductType productVersion;

    private Object reply;

    private Boolean rpm_violation = false;

    private int sats = 9;

    private Map<Integer, MapSection> sbsModule;

    private Boolean seatbelt_violation = false;
    private final Map<DeviceProps, String> settings;

    private int speed;

    private Double speedLimit = 75.0;
    private int speedingSpeedLimit;

    private Boolean speeding = false;

    private int stateID = 47;

    private String stateName = "UT";

    private final AutomationCalendar time;
    private final AutomationCalendar time_last;

    private int timeBetweenNotes = 15;

    private int timeSinceLastLoc = 0;

    private String vehicleID;
    private Direction waysDirection = Direction.wifi;
    private int WMP = 17013, MSP = 50;

    private int maxRpm = 500;

    private int avgRpm = 500;

    public DeviceState(String imei, ProductType type) {
        time = new AutomationCalendar();
        time_last = new AutomationCalendar();
        this.imei = imei;
        this.productVersion = type;
        settings = new HashMap<DeviceProps, String>();
    }

    public AutomationCalendar copyTime() {
        return time.copy();
    }

    public String get_setting(DeviceProps propertyID) {
        return settings.get(propertyID);
    }

    public int getAccountID() {
        return accountID;
    }

    public int getAvgRpm() {
        return avgRpm;
    }

    public void getAvgRpm(int avgRpm) {
        this.avgRpm = avgRpm;
    }

    public int getBoundaryID() {
        return boundaryID;
    }


    public int getDriverID() {
        return driverID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public Heading getHeading() {
        return heading;
    }

    public Boolean getIgnition_state() {
        return ignition_state;
    }

    public String getImei() {
        return imei;
    }

    public String getLastDownload() {
        return lastDownload;
    }

    public int getLinkID() {
        return linkID;
    }

    public Integer getMapRev() {
        return mapRev;
    }

    public int getMaxRpm() {
        return maxRpm;
    }

    public void getMaxRpm(int maxRpm) {
        this.maxRpm = maxRpm;
    }

    public String getMcmID() {
        return mcmID;
    }

    public int getMSP() {
        return MSP;
    }

    public int getOdometer() {
        return odometer;
    }

    public Boolean getPower_state() {
        return power_state;
    }

    public ProductType getProductVersion() {
        return productVersion;
    }

    public Object getReply() {
        return reply;
    }

    public Boolean getRpm_violation() {
        return rpm_violation;
    }

    public int getSats() {
        return sats;
    }

    public Map<Integer, MapSection> getSbsModule() {
        return sbsModule;
    }

    public Boolean getSeatbelt_violation() {
        return seatbelt_violation;
    }

    public Map<DeviceProps, String> getSettings() {
        return settings;
    }

    public int getSpeed() {
        return speed;
    }

    public Double getSpeed_limit() {
        return speedLimit;
    }

    public Boolean getSpeeding() {
        return speeding;
    }

    public int getSpeedingSpeedLimit() {
		return speedingSpeedLimit;
	}

    public int getStateID() {
        return stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public AutomationCalendar getTime() {
        return time;
    }

    public AutomationCalendar getTime_last() {
        return time_last;
    }

    public int getTimeBetweenNotes() {
        return timeBetweenNotes;
    }

    public int getTimeSinceLastLoc() {
        return timeSinceLastLoc;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public Direction getWaysDirection() {
        return waysDirection;
    }

    public int getWMP() {
        return WMP;
    }

    public void incrementTime(Integer increment) {
        time_last.setDate(time);
        time.addToSeconds(increment);
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setBoundaryID(int boundaryID) {
        this.boundaryID = boundaryID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public void setIgnition_state(Boolean ignition_state) {
        this.ignition_state = ignition_state;
    }

    public void setLastDownload(String lastDownload) {
        this.lastDownload = lastDownload;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public void setMapRev(Integer mapRev) {
        this.mapRev = mapRev;
    }

    public void setMcmID(String mcmID) {
        if (mcmID != null && mcmID.length() > 9){
            throw new IllegalArgumentException("MCMID has a max value of 9 characters.");
        }
        this.mcmID = mcmID;
    }

    public void setMSP(int mSP) {
        MSP = mSP;
    }

    public void setOdometer(Double odometer) {
        setOdometer(odometer.intValue());
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public void setPower_state(Boolean power_state) {
        this.power_state = power_state;
    }

    public void setReply(Object reply) {
        this.reply = reply;
    }

    public void setRpm_violation(Boolean rpm_violation) {
        this.rpm_violation = rpm_violation;
    }

    public void setSats(int sats) {
        this.sats = sats;
    }

    public void setSbsModule(Map<Integer, MapSection> sbsModule) {
        this.sbsModule = sbsModule;
    }

    public void setSeatbelt_violation(Boolean seatbelt_violation) {
        this.seatbelt_violation = seatbelt_violation;
    }

    public void setSetting(DeviceProps propertyID,
            String setting) {
        settings.put(propertyID, setting);
    }

    public void setSettings(Map<DeviceProps, String> settings) {
        this.settings.putAll(settings);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpeed_limit(Double speed_limit) {
        this.speedLimit = speed_limit;
    }

    public void setSpeeding(Boolean speeding) {
        this.speeding = speeding;
    }

    public void setSpeedingSpeedLimit(int speedingSpeedLimit) {
		this.speedingSpeedLimit = speedingSpeedLimit;
	}

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public void setTimeBetweenNotes(int timeBetweenNotes) {
        this.timeBetweenNotes = timeBetweenNotes;
    }

    public void setTimeSinceLastLoc(int timeSinceLastLoc) {
        this.timeSinceLastLoc = timeSinceLastLoc;
    }
    
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

	public void setWaysDirection(Direction waysDirection) {
        this.waysDirection = waysDirection;
    }

	public void setWMP(int wMP) {
        WMP = wMP;
    }
}
