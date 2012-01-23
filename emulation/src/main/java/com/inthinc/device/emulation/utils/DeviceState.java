package com.inthinc.device.emulation.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.TripFlags;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSFlags;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSState;
import com.inthinc.device.emulation.enums.DeviceEnums.HosRuleSet;
import com.inthinc.device.emulation.enums.DeviceEnums.ViolationFlags;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.MapSection;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.MasterTest;

public class DeviceState {

    private int accountID = 1;

    private int avgRpm = 500;

    private int avgSpeed = 50;
    private int boundaryID = 1;

    private int tripFlags = 0;

    private Integer course = 100;
    private int driverID = 1;
    private String employeeID = "";
    private boolean exceedingRPMLimit = false;
    private final AutomationCalendar gpsLockTime;
    private Integer GPSPercent = 95;
    private int gracePeriod = 5;

    private Heading heading = Heading.NORTH;
    private int headlightOffDistanceX100 = 0;
    private boolean headlightsOff = false;

    private boolean heavyDuty = false;

    private int highIdle = 0;

    private HOSFlags hosFlags = HOSFlags.OFF_DUTY;

    private HosRuleSet hosRuleSet = HosRuleSet.US_7DAY;
    
    private HOSState hosState = HOSState.OFF_DUTY;

    private Boolean ignition_state = false;
    private final String imei;

    private String lastDownload;

    private int linkID = 0;
    private int lowIdle = 0;
    private Integer lowPowerTimeout = 900;

    private Integer mapRev = 0;

    private int maxRpm = 500;

    private int maxSpeedLimit = 75;
    private String mcmID;
    private int mpg = 99;
    
    private int mpgDistanceX100 = 0;
    private boolean noDriver = true;
    private int noDriverDistanceX100 = 0;
    
    private boolean noTrailer = true;
    private int noTrailerDistanceX100 = 0;
	private int nSpeedLimit1to30MilesX100 = 0;
	private int nSpeedLimit31to40MilesX100 = 0;
	private int nSpeedLimit41to54MilesX100 = 0;
	
	private int nSpeedLimit55to64MilesX100 = 0;
	
	private int nSpeedLimit65to80MilesX100 = 0;

	private Integer OBDPercent = 5;

	private String occupantID = "";

	private int odometerX100 = 0;

	private boolean offRoad = false;

	private int pointsPassedTheFilter = 95;

	private Boolean power_state = false;

	private final ProductType productVersion;

	private Object reply;

	private long rfid = 0x0l;

	private int rfidHigh = 0;

	private int rfidLow = 0;
	
	private boolean rfOff = false;
	
	private int RFOffDistanceX100 = 0;
	private int sats = 15;

	private int sbsLinkID = 0;
	
	private Map<Integer, MapSection> sbsModule;

	private int sbsSpeedLimit = 50;

	private int seatbeltDistanceX100 = 0;

	private boolean seatbeltEngaged = true;

	private int seatBeltTopSpeed = 0;

	private int seatbeltViolationDistanceX100 = 0;

	private final Map<DeviceProps, String> settings;

	private int severeSpeedThreshold = 5;

	private int severSpeedSeconds = 60;

	private int speed = 0;

	private Boolean speeding = false;
	
	private int speedingBuffer = 5;

	private int speedingDistanceX100 = 0;

	private int speedingSpeedLimit = 75;

	private final AutomationCalendar speedingStartTime;

	private final AutomationCalendar speedingStopTime;

	private int speedLimit = 75;

	private boolean speedModuleEnabled = true;

	private int stateID = 47;

	private String stateName = "UT";

	private final AutomationCalendar time;

	private final AutomationCalendar time_last;

	private int timeBetweenNotes = 15;

	private int timeSinceLastLoc = 0;

	private int topSpeed = 0;

	private int tripDuration = 0;

	private String vehicleID;

	private int violationFlags = 0x0;

	private Direction waysDirection = Direction.wifi;
	
	private int weatherSpeedLimitPercent = 50;

	private int WMP = 17013, MSP = 50;

	private int zoneID = 0;

	private int zoneSpeedLimit = 50;
	
	public DeviceState(String imei, ProductType type) {
        time = new AutomationCalendar(WebDateFormat.NOTE_PRECISE_TIME);
        time_last = new AutomationCalendar(WebDateFormat.NOTE_PRECISE_TIME);
        speedingStartTime = new AutomationCalendar(WebDateFormat.NOTE_PRECISE_TIME);
        speedingStopTime = new AutomationCalendar(WebDateFormat.NOTE_PRECISE_TIME);
        gpsLockTime = new AutomationCalendar(WebDateFormat.NOTE_PRECISE_TIME);
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
	
	public Integer getAccountID() {
        return accountID;
    }
	
	public Integer getAvgRpm() {
        return avgRpm;
    }
	
	public Integer getAvgSpeed() {
		return avgSpeed;
	}
	
	public Integer getBoundaryID() {
        return boundaryID;
    }
	
	public Integer getTripFlags() {
		return tripFlags & 0xF0;
	}

	public Integer getCourse() {
		return course;
	}

	public Integer getDriverID() {
        return driverID;
    }

	public String getEmployeeID() {
        return employeeID;
    }

	public AutomationCalendar getGpsLockTime() {
		return gpsLockTime;
	}

	public Integer getGPSPercent() {
		return GPSPercent;
	}

	public Integer getGracePeriod() {
		return gracePeriod;
	}

	public Heading getHeading() {
        return heading;
    }

	public Integer getHeadlightOffDistanceX100() {
		return headlightOffDistanceX100;
	}

	public int getHighIdle() {
		return highIdle;
	}

	public HOSFlags getHosFlags() {
		return hosFlags;
	}

	public HosRuleSet getHosRuleSet() {
		return hosRuleSet ;
	}

	public HOSState getHosState() {
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

	public Integer getLinkID() {
        return linkID;
    }

	public int getLowIdle() {
		return lowIdle;
	}

	public Integer getLowPowerTimeout() {
		return lowPowerTimeout ;
	}

	public Integer getMapRev() {
        return mapRev;
    }

	public Integer getMaxRpm() {
        return maxRpm;
    }

	public Integer getMaxSpeedLimit() {
		return maxSpeedLimit;
	}

	public String getMcmID() {
        return mcmID;
    }
	

	public Integer getMpg() {
		return mpg;
	}

	public Integer getMpgDistanceX100() {
		return mpgDistanceX100;
	}

	public Integer getMSP() {
        return MSP;
    }

	public Integer getNoDriverDistanceX100() {
		return noDriverDistanceX100;
	}

	public Integer getNoTrailerDistanceX100() {
		return noTrailerDistanceX100;
	}

	public Integer getnSpeedLimit1to30MilesX100() {
		return nSpeedLimit1to30MilesX100;
	}

	public Integer getnSpeedLimit31to40MilesX100() {
		return nSpeedLimit31to40MilesX100;
	}

	public Integer getnSpeedLimit41to54MilesX100() {
		return nSpeedLimit41to54MilesX100;
	}

	public Integer getnSpeedLimit55to64MilesX100() {
		return nSpeedLimit55to64MilesX100;
	}

	public Integer getnSpeedLimit65to80MilesX100() {
		return nSpeedLimit65to80MilesX100;
	}

	public Integer getOBDPercent() {
		return OBDPercent;
	}

	public String getOccupantID() {
		return occupantID;
	}

	public Integer getOdometerX100() {
        return odometerX100;
    }

	public Integer getPointsPassedTheFilter() {
		return pointsPassedTheFilter;
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

	public long getRfid() {
		return rfid;
	}

	public int getRfidHigh() {
		return rfidHigh;
	}
	
	public int getRfidLow() {
		return rfidLow;
	}

    public Integer getRFOffDistanceX100() {
		return RFOffDistanceX100;
	}

    public Integer getSats() {
        return sats;
    }

    public Integer getSbsLinkID() {
		return sbsLinkID;
	}

    public Map<Integer, MapSection> getSbsModule() {
        return sbsModule;
    }

    public Integer getSbsSpeedLimit() {
		return sbsSpeedLimit;
	}

    public Integer getSeatbeltDistanceX100() {
		return seatbeltDistanceX100;
	}


    public Integer getSeatbeltTopSpeed() {
		return seatBeltTopSpeed;
	}

    public Integer getSeatBeltTopSpeed() {
		return seatBeltTopSpeed;
	}

    public int getSeatbeltViolationDistanceX100() {
		return seatbeltViolationDistanceX100;
	}

    public Map<DeviceProps, String> getSettings() {
        return settings;
    }

    public Integer getSevereSpeedThreshold() {
		return severeSpeedThreshold;
	}

    public Integer getSeverSpeedSeconds() {
		return severSpeedSeconds;
	}

    public Integer getSpeed() {
        return speed;
    }

    public Boolean getSpeeding() {
		return speeding;
	}

    public Integer getSpeedingBuffer() {
		return speedingBuffer;
	}

    public Integer getSpeedingDistanceX100() {
		return speedingDistanceX100;
	}

    public Integer getSpeedingSpeedLimit() {
		return speedingSpeedLimit;
	}

    public AutomationCalendar getSpeedingStartTime() {
		return speedingStartTime;
	}

    public AutomationCalendar getSpeedingStopTime() {
		return speedingStopTime;
	}

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public Integer getStateID() {
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

    public Integer getTimeBetweenNotes() {
        return timeBetweenNotes;
    }

    public Integer getTimeSinceLastLoc() {
        return timeSinceLastLoc;
    }

    public Integer getTopSpeed() {
		return topSpeed;
	}

    public int getTripDuration() {
		return tripDuration;
	}

    public String getVehicleID() {
        return vehicleID;
    }

    public int getViolationFlags() {
		return violationFlags;
	}

    public Direction getWaysDirection() {
        return waysDirection;
    }

    public Integer getWeatherSpeedLimitPercent() {
		return weatherSpeedLimitPercent;
	}

    public Integer getWMP() {
        return WMP;
    }

    public Integer getZoneID() {
		return zoneID;
	}

    public Integer getZoneSpeedLimit() {
		return zoneSpeedLimit;
	}

    public DeviceState incrementTime(Integer increment) {
        time_last.setDate(time);
        time.addToSeconds(increment);
		return this;
    }

    public DeviceState incrementTripDistanceX100(int delta) {
		mpgDistanceX100 += delta;
		
		odometerX100 += delta;
		
		seatbeltDistanceX100     = seatbeltEngaged ? seatbeltDistanceX100     + delta : seatbeltDistanceX100;
		noDriverDistanceX100     = noDriver        ? noDriverDistanceX100     + delta : noDriverDistanceX100; 
		noTrailerDistanceX100    = noTrailer       ? noTrailerDistanceX100    + delta : noTrailerDistanceX100;
		headlightOffDistanceX100 = headlightsOff   ? headlightOffDistanceX100 + delta : headlightOffDistanceX100;
		RFOffDistanceX100        = rfOff           ? RFOffDistanceX100        + delta : RFOffDistanceX100;
		
		
		if (speedLimit <= 30){
        	nSpeedLimit1to30MilesX100 += delta;
        } else if (speedLimit <= 40){
        	nSpeedLimit31to40MilesX100 += delta;
        } else if (speedLimit <= 54){
        	nSpeedLimit41to54MilesX100 += delta;
        } else if (speedLimit <= 64){
        	nSpeedLimit55to64MilesX100 += delta;
        } else if (speedLimit <= 80){
        	nSpeedLimit65to80MilesX100 += delta;
        }

		return this;
	}

    public boolean isExceedingRPMLimit() {
		return exceedingRPMLimit;
	}

    public boolean isHeadlightsOff() {
		return headlightsOff;
	}

    public boolean isHeavyDuty() {
		return heavyDuty;
	}

    public boolean isNoDriver() {
		return noDriver;
	}

    public boolean isNoTrailer() {
		return noTrailer;
	}

    public boolean isOffRoad() {
		return offRoad;
	}

    public boolean isRfOff() {
		return rfOff;
	}

    public boolean isSeatbeltEngaged() {
		return seatbeltEngaged;
	}

    public boolean isSpeeding() {
        return speeding;
    }

    public boolean isSpeedModuleEnabled() {
		return speedModuleEnabled;
	}

    public DeviceState removeViolationFlags(ViolationFlags ...flags){
		int remove = 0;
		for (ViolationFlags flag : flags){
			remove = (remove | flag.getIndex()) & 0xff;
		}
		violationFlags = (violationFlags & ~remove) & 0xff;
		return this;
	}

    public DeviceState setAccountID(int accountID) {
        this.accountID = accountID;
		return this;
    }

    public DeviceState setAvgRpm(int avgRpm) {
		this.avgRpm = avgRpm;
		return this;
	}

    public DeviceState setAvgSpeed(int avgSpeed) {
		this.avgSpeed = avgSpeed;
		return this;
	}

    public DeviceState setBoundaryID(int boundaryID) {
        this.boundaryID = boundaryID;
		return this;
    }

    public DeviceState setCourse(Integer course) {
		this.course = course;
		return this;
	}

    public DeviceState setDriverID(int driverID) {
        this.driverID = driverID;
		return this;
    }

    public DeviceState setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
		return this;
    }

    public DeviceState setExceedingRPMLimit(boolean exceedingRPMLimit) {
		this.exceedingRPMLimit = exceedingRPMLimit;
		if (exceedingRPMLimit){
        	setViolationFlags(ViolationFlags.RPM);
        } else {
        	removeViolationFlags(ViolationFlags.RPM);
        }
		return this;
	}

    public DeviceState setGPSPercent(Integer GPSPercent) {
		if ( 100 < GPSPercent || GPSPercent < 0){
			throw new IllegalArgumentException(GPSPercent + " is not a legal value between 0 and 100");
		}
		this.GPSPercent = GPSPercent;
		OBDPercent = 100 - GPSPercent;
		return this;
	}

    public DeviceState setGracePeriod(int gracePeriod) {
		this.gracePeriod = gracePeriod;
		return this;
	}

	public DeviceState setHeading(Heading heading) {
        this.heading = heading;
		return this;
    }

    public DeviceState setHeadlightOffDistanceX100(int headlightOffDistance) {
		this.headlightOffDistanceX100 = headlightOffDistance;
		return this;
	}

    public DeviceState setHeadlightsOff(boolean headlightsOff) {
		this.headlightsOff = headlightsOff;
		return this;
	}

    public DeviceState setHeavyDuty(boolean heavyDuty) {
		this.heavyDuty = heavyDuty;
		return this;
	}

    public DeviceState setHighIdle(int highIdle) {
		this.highIdle = highIdle;
		return this;
	}

    public DeviceState setTripFlags(TripFlags... tripFlags) {
    	if (tripFlags.length == 0){
    		this.tripFlags = 0x00;
    	} else {
			for (TripFlags flag : tripFlags){
				this.tripFlags = (flag.getIndex() | this.tripFlags);
			}
    	}
		return this;
	}

    public DeviceState setHosFlags(HOSFlags hosFlags) {
		this.hosFlags = hosFlags;
		return this;
	}

    public DeviceState setHosRuleSet(HosRuleSet hosRuleSet) {
		this.hosRuleSet = hosRuleSet;
		return this;
	}
    
    public DeviceState setHosState(HOSState hosState) {
		this.hosState = hosState;
		return this;
	}

    public DeviceState setIgnition_state(Boolean ignition_state) {
        this.ignition_state = ignition_state;
		return this;
    }
    
    public DeviceState setLastDownload(String lastDownload) {
        this.lastDownload = lastDownload;
		return this;
    }

	public DeviceState setLinkID(int linkID) {
        this.linkID = linkID;
		return this;
    }

	public DeviceState setLowIdle(int lowIdle) {
		this.lowIdle = lowIdle;
		return this;
	}

	public DeviceState setLowPowerTimeout(Integer lowPowerTimeout) {
		this.lowPowerTimeout = lowPowerTimeout;
		return this;
	}

	public DeviceState setMapRev(Integer mapRev) {
        this.mapRev = mapRev;
		return this;
    }

	public DeviceState setMaxRpm(int maxRpm) {
		this.maxRpm = maxRpm;
		return this;
	}

	public DeviceState setMaxSpeedLimit(int maxSpeedLimit) {
		this.maxSpeedLimit = maxSpeedLimit;
		return this;
	}

	public DeviceState setMcmID(String mcmID) {
        if (mcmID != null && mcmID.length() > 9){
            throw new IllegalArgumentException("MCMID has a max value of 9 characters.");
        }
        this.mcmID = mcmID;
		return this;
    }

	public DeviceState setMpg(int mpg) {
		this.mpg = mpg;
		return this;
	}

	public DeviceState setMpgDistanceX100(int mpgDistance) {
		this.mpgDistanceX100 = mpgDistance;
		return this;
	}

	public DeviceState setMSP(int mSP) {
        MSP = mSP;
		return this;
    }

	public DeviceState setNoDriver(boolean noDriver) {
		this.noDriver = noDriver;
		return this;
	}

	public DeviceState setNoDriverDistanceX100(int noDriverDistance) {
		this.noDriverDistanceX100 = noDriverDistance;
		return this;
	}

	public DeviceState setNoTrailer(boolean noTrailer) {
		this.noTrailer = noTrailer;
		return this;
	}

	public DeviceState setNoTrailerDistanceX100(int noTrailerDistance) {
		this.noTrailerDistanceX100 = noTrailerDistance;
		return this;
	}

	public DeviceState setnSpeedLimit1to30MilesX100(int nSpeedLimit1to30MilesX100) {
		this.nSpeedLimit1to30MilesX100 = nSpeedLimit1to30MilesX100;
		return this;
	}

	public DeviceState setnSpeedLimit31to40MilesX100(int nSpeedLimit31to40MilesX100) {
		this.nSpeedLimit31to40MilesX100 = nSpeedLimit31to40MilesX100;
		return this;
	}

	public DeviceState setnSpeedLimit41to54MilesX100(int nSpeedLimit41to54MilesX100) {
		this.nSpeedLimit41to54MilesX100 = nSpeedLimit41to54MilesX100;
		return this;
	}


	public DeviceState setnSpeedLimit55to64MilesX100(int nSpeedLimit55to64MilesX100) {
		this.nSpeedLimit55to64MilesX100 = nSpeedLimit55to64MilesX100;
		return this;
	}

	public DeviceState setnSpeedLimit65to80MilesX100(int nSpeedLimit65to80MilesX100) {
		this.nSpeedLimit65to80MilesX100 = nSpeedLimit65to80MilesX100;
		return this;
	}

	public DeviceState setOBDPercent(Integer OBDPercent) {
		if ( 100 < OBDPercent || OBDPercent < 0){
			throw new IllegalArgumentException(OBDPercent + " is not a legal value between 0 and 100");
		}
		this.OBDPercent = OBDPercent;
		GPSPercent = 100 - OBDPercent;
		return this;
	}

	public DeviceState setOccupantID(String occupantID) {
		this.occupantID  = occupantID;
		return this;
	}

	public DeviceState setOdometerX100(Double odometer) {
        setOdometerX100(odometer.intValue());
		return this;
    }

	public DeviceState setOdometerX100(int odometer) {
        this.odometerX100 = odometer;
		return this;
    }

	public DeviceState setOffRoad(boolean offRoad) {
		this.offRoad = offRoad;
		return this;
	}

	public DeviceState setPointsPassedTheFilter(int pointsPassedTheFilter) {
		this.pointsPassedTheFilter = pointsPassedTheFilter;
		return this;
	}

	public DeviceState setPower_state(Boolean power_state) {
        this.power_state = power_state;
		return this;
    }

	public DeviceState setReply(Object reply) {
        this.reply = reply;
		return this;
    }

	public DeviceState setRfid(int high, int low){

		rfidHigh = high;
		rfidLow = low;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
		
		try {
            dos.writeInt(high);
            dos.writeInt(low);
            dos.flush();
        } catch (IOException e) {
        	MasterTest.print(e, Level.FATAL);
        }
        
        ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        
		try {
			rfid = dis.readLong();
		} catch (IOException e) {
			MasterTest.print(e, Level.FATAL);
		}
		return this;
	}

	public DeviceState setRfid(long rfid) {
		this.rfid = rfid;

		rfidHigh = (int) ((rfid >> 4 * Byte.SIZE ) );
		rfidLow = (int) (rfid & 0xFFFFFFFF);
		return this;
	}

	public DeviceState setRfidHigh(int rfidHigh) {
		setRfid(rfidHigh, rfidLow);
		return this;
	}

	public DeviceState setRfidLow(int rfidLow) {
		setRfid(rfidHigh, rfidLow);
		return this;
	}

	public DeviceState setRfOff(boolean rfOff) {
		this.rfOff = rfOff;
		return this;
	}

	public DeviceState setRFOffDistanceX100(int rFOffDistance) {
		RFOffDistanceX100 = rFOffDistance;
		return this;
	}
	
	public DeviceState setSats(int sats) {
        this.sats = sats;
		return this;
    }

	public DeviceState setSbsLinkID(int sbsLinkID) {
		this.sbsLinkID = sbsLinkID;
		return this;
	}

	public DeviceState setSbsModule(Map<Integer, MapSection> sbsModule) {
        this.sbsModule = sbsModule;
		return this;
    }

	public DeviceState setSbsSpeedLimit(int sbsSpeedLimit) {
		this.sbsSpeedLimit = sbsSpeedLimit;
		return this;
	}

	public DeviceState setSeatbeltDistanceX100(int seatbeltDistance) {
		this.seatbeltDistanceX100 = seatbeltDistance;
		return this;
	}

	public DeviceState setSeatbeltEngaged(boolean seatbeltEngaged) {
		this.seatbeltEngaged = seatbeltEngaged;
		if (seatbeltEngaged){
			removeViolationFlags(ViolationFlags.SEATBELT);
		} else {
			setViolationFlags(ViolationFlags.SEATBELT);
		}
		return this;
	}

	public DeviceState setSeatBeltTopSpeed(int seatBeltTopSpeed) {
		this.seatBeltTopSpeed = seatBeltTopSpeed;
		return this;
	}

	public DeviceState setSeatbeltViolationDistanceX100(
			int seatbeltViolationDistanceX100) {
		this.seatbeltViolationDistanceX100 = seatbeltViolationDistanceX100;
		return this;
	}

	public DeviceState setSetting(DeviceProps propertyID,
            String setting) {
        settings.put(propertyID, setting);
		return this;
    }

	public DeviceState setSettings(Map<DeviceProps, String> settings) {
        this.settings.putAll(settings);
		return this;
    }

	public DeviceState setSevereSpeedThreshold(int severeSpeedThreshold) {
		this.severeSpeedThreshold = severeSpeedThreshold;
		return this;
	}

	public DeviceState setSeverSpeedSeconds(int severSpeedSeconds) {
		this.severSpeedSeconds = severSpeedSeconds;
		return this;
	}

	public DeviceState setSpeed(int speed) {
        this.speed = speed;
		return this;
    }

	public DeviceState setSpeeding(Boolean speeding) {
        this.speeding = speeding;
        if (speeding){
        	setViolationFlags(ViolationFlags.SPEEDING);
        } else {
        	removeViolationFlags(ViolationFlags.SPEEDING);
        }
		return this;
    }

	public DeviceState setSpeedingBuffer(int speedingBuffer) {
		this.speedingBuffer = speedingBuffer;
		return this;
	}

	public DeviceState setSpeedingDistanceX100(int speedingDistance) {
		this.speedingDistanceX100 = speedingDistance;
		return this;
	}

	public DeviceState setSpeedingSpeedLimit(int speedingSpeedLimit) {
		this.speedingSpeedLimit = speedingSpeedLimit;
		return this;
	}

	public DeviceState setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		return this;
	}

	public DeviceState setSpeedModuleEnabled(boolean speedModuleEnabled) {
		this.speedModuleEnabled = speedModuleEnabled;
		return this;
	}

	public DeviceState setStateID(int stateID) {
        this.stateID = stateID;
		return this;
    }

	public DeviceState setStateName(String stateName) {
        this.stateName = stateName;
		return this;
    }
	
	public DeviceState setTimeBetweenNotes(int timeBetweenNotes) {
        this.timeBetweenNotes = timeBetweenNotes;
		return this;
    }

	public DeviceState setTimeSinceLastLoc(int timeSinceLastLoc) {
        this.timeSinceLastLoc = timeSinceLastLoc;
		return this;
    }

	public DeviceState setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
		return this;
	}

	public DeviceState setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
		return this;
	}

	public DeviceState setTripDuration(Long delta) {
		setTripDuration(delta.intValue());		
		return this;
	}
	public DeviceState setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
		return this;
    }

	public DeviceState setViolationFlags(ViolationFlags ...flags) {
		for (ViolationFlags flag : flags){
			violationFlags = (violationFlags | flag.getIndex()) & 0xff;
		}
		return this;
		
	}

	public DeviceState setWaysDirection(Direction waysDirection) {
        this.waysDirection = waysDirection;
		return this;
    }

	public DeviceState setWeatherSpeedLimitPercent(int weatherSpeedLimitPercent) {
		this.weatherSpeedLimitPercent = weatherSpeedLimitPercent;
		return this;
	}

	public DeviceState setWMP(int wMP) {
        WMP = wMP;
		return this;
    }

	public DeviceState setZoneID(int zoneID) {
		this.zoneID = zoneID;
		return this;
	}

	public DeviceState setZoneSpeedLimit(int zoneSpeedLimit) {
		this.zoneSpeedLimit = zoneSpeedLimit;
		return this;
	}
}
