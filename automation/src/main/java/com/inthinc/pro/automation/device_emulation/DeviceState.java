package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceEnums.WSHOSState;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.objects.WaysmartDevice.Direction;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;

public class DeviceState {

    private int accountID = 1;

    private int avgRpm = 500;

    private int avgSpeed = 50;
    private int boundaryID = 1;

    private Integer course = 100;

    private int driverID = 1;
    private String employeeID = "";
    private boolean exceedingRPMLimit = false;
    private int gracePeriod = 5;
    private Heading heading = Heading.NORTH;
    private boolean heavyDuty = false;

    private WSHOSState hosState = WSHOSState.OFF_DUTY;
    private Boolean ignition_state = false;
    private final String imei;

    private String lastDownload;

    private int linkID = 0;

    private Integer mapRev = 0;

    private int maxRpm = 500;

    private int maxSpeedLimit = 75;
    private String mcmID;

    private int odometer = 0;

    private boolean offRoad = false;
    private Boolean power_state = false;

    private final ProductType productVersion;

    private Object reply;

    private int sats = 15;
    private int sbsLinkID = 0;

    private Map<Integer, MapSection> sbsModule;

    private int sbsSpeedLimit = 50;

    private boolean seatbeltEngaged = true;
    private final Map<DeviceProps, String> settings;

    private int severeSpeedThreshold = 5;

    private int severSpeedSeconds = 60;

	private int speed = 0;

	private Boolean speeding = false;

	private int speedingBuffer = 5;

	private int speedingDistance = 0;

	private int speedingSpeedLimit = 75;

	private final AutomationCalendar speedingStartTime;

	private final AutomationCalendar speedingStopTime;

	private Double speedLimit = 75.0;

	private boolean speedModuleEnabled = true;

	private int stateID = 47;

	private String stateName = "UT";

	private final AutomationCalendar time;

	private final AutomationCalendar time_last;

	private int timeBetweenNotes = 15;

	private int timeSinceLastLoc = 0;

	private int topSpeed = 0;

	private String vehicleID;

	private Direction waysDirection = Direction.wifi;

	private int weatherSpeedLimitPercent = 50;

	private int WMP = 17013, MSP = 50;

	private int zoneID = 0;

	private int zoneSpeedLimit = 50;

	public DeviceState(String imei, ProductType type) {
        time = new AutomationCalendar();
        time_last = new AutomationCalendar();
        speedingStartTime = new AutomationCalendar();
        speedingStopTime = new AutomationCalendar();
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

	public int getAvgSpeed() {
		return avgSpeed;
	}

	public int getBoundaryID() {
        return boundaryID;
    }

	public Integer getCourse() {
		return course;
	}
	
	public int getDriverID() {
        return driverID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public int getGracePeriod() {
		return gracePeriod;
	}

    public Heading getHeading() {
        return heading;
    }

    public WSHOSState getHosState() {
		return hosState;
	}

    public WSHOSState getHOSState() {
		return hosState;
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

    public int getMaxSpeedLimit() {
		return maxSpeedLimit;
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

    public int getSats() {
        return sats;
    }

    public int getSbsLinkID() {
		return sbsLinkID;
	}

    public Map<Integer, MapSection> getSbsModule() {
        return sbsModule;
    }

    public int getSbsSpeedLimit() {
		return sbsSpeedLimit;
	}

    public Map<DeviceProps, String> getSettings() {
        return settings;
    }

    public int getSevereSpeedThreshold() {
		return severeSpeedThreshold;
	}

    public int getSeverSpeedSeconds() {
		return severSpeedSeconds;
	}

    public int getSpeed() {
        return speed;
    }

    public Boolean getSpeeding() {
		return speeding;
	}

    public int getSpeedingBuffer() {
		return speedingBuffer;
	}

    public int getSpeedingDistance() {
		return speedingDistance;
	}

    public int getSpeedingSpeedLimit() {
		return speedingSpeedLimit;
	}

    public AutomationCalendar getSpeedingStartTime() {
		return speedingStartTime;
	}

    public AutomationCalendar getSpeedingStopTime() {
		return speedingStopTime;
	}

    public Double getSpeedLimit() {
        return speedLimit;
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

    public int getTopSpeed() {
		return topSpeed;
	}

    public String getVehicleID() {
        return vehicleID;
    }

    public Direction getWaysDirection() {
        return waysDirection;
    }

    public int getWeatherSpeedLimitPercent() {
		return weatherSpeedLimitPercent;
	}

    public int getWMP() {
        return WMP;
    }

    public int getZoneID() {
		return zoneID;
	}

    public int getZoneSpeedLimit() {
		return zoneSpeedLimit;
	}

    public void incrementTime(Integer increment) {
        time_last.setDate(time);
        time.addToSeconds(increment);
    }

    public boolean isExceedingRPMLimit() {
		return exceedingRPMLimit;
	}

    public boolean isHeavyDuty() {
		return heavyDuty;
	}

    public boolean isOffRoad() {
		return offRoad;
	}

    public boolean isSeatbeltEngaged() {
		return seatbeltEngaged;
	}

    public Boolean isSpeeding() {
        return speeding;
    }

    public boolean isSpeedModuleEnabled() {
		return speedModuleEnabled;
	}

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setAvgRpm(int avgRpm) {
		this.avgRpm = avgRpm;
	}

    public void setAvgSpeed(int avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

    public void setBoundaryID(int boundaryID) {
        this.boundaryID = boundaryID;
    }

    public void setCourse(Integer course) {
		this.course = course;
	}

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setExceedingRPMLimit(boolean exceedingRPMLimit) {
		this.exceedingRPMLimit = exceedingRPMLimit;
	}

    public void setGracePeriod(int gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public void setHeavyDuty(boolean heavyDuty) {
		this.heavyDuty = heavyDuty;
	}

    public void setHosState(WSHOSState hosState) {
		this.hosState = hosState;
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

	public void setMaxRpm(int maxRpm) {
		this.maxRpm = maxRpm;
	}

	public void setMaxSpeedLimit(int maxSpeedLimit) {
		this.maxSpeedLimit = maxSpeedLimit;
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

	public void setOffRoad(boolean offRoad) {
		this.offRoad = offRoad;
	}

	public void setPower_state(Boolean power_state) {
        this.power_state = power_state;
    }

	public void setReply(Object reply) {
        this.reply = reply;
    }

	public void setSats(int sats) {
        this.sats = sats;
    }

	public void setSbsLinkID(int sbsLinkID) {
		this.sbsLinkID = sbsLinkID;
	}

	public void setSbsModule(Map<Integer, MapSection> sbsModule) {
        this.sbsModule = sbsModule;
    }

	public void setSbsSpeedLimit(int sbsSpeedLimit) {
		this.sbsSpeedLimit = sbsSpeedLimit;
	}

	public void setSeatbeltEngaged(boolean seatbeltEngaged) {
		this.seatbeltEngaged = seatbeltEngaged;
	}

	public void setSetting(DeviceProps propertyID,
            String setting) {
        settings.put(propertyID, setting);
    }

	public void setSettings(Map<DeviceProps, String> settings) {
        this.settings.putAll(settings);
    }


	public void setSevereSpeedThreshold(int severeSpeedThreshold) {
		this.severeSpeedThreshold = severeSpeedThreshold;
	}

	public void setSeverSpeedSeconds(int severSpeedSeconds) {
		this.severSpeedSeconds = severSpeedSeconds;
	}

	public void setSpeed(int speed) {
        this.speed = speed;
    }

	public void setSpeeding(Boolean speeding) {
        this.speeding = speeding;
    }

	public void setSpeedingBuffer(int speedingBuffer) {
		this.speedingBuffer = speedingBuffer;
	}

	public void setSpeedingDistance(int speedingDistance) {
		this.speedingDistance = speedingDistance;
	}

	public void setSpeedingSpeedLimit(int speedingSpeedLimit) {
		this.speedingSpeedLimit = speedingSpeedLimit;
	}

	public void setSpeedLimit(Double speedLimit) {
		this.speedLimit = speedLimit;
	}

	public void setSpeedLimit(Integer speedLimit) {
		setSpeedLimit(speedLimit.doubleValue());
	}

	public void setSpeedModuleEnabled(boolean speedModuleEnabled) {
		this.speedModuleEnabled = speedModuleEnabled;
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

	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}

	public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
	
	public void setWaysDirection(Direction waysDirection) {
        this.waysDirection = waysDirection;
    }

	public void setWeatherSpeedLimitPercent(int weatherSpeedLimitPercent) {
		this.weatherSpeedLimitPercent = weatherSpeedLimitPercent;
	}

	public void setWMP(int wMP) {
        WMP = wMP;
    }

	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}

	public void setZoneSpeedLimit(int zoneSpeedLimit) {
		this.zoneSpeedLimit = zoneSpeedLimit;
	}
}
