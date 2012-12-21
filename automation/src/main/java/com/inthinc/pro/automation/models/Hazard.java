package com.inthinc.pro.automation.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.interfaces.CustomUrl;
import com.inthinc.pro.automation.interfaces.HasCustomUrl;
import com.inthinc.pro.automation.objects.AutomationCalendar;
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Hazard extends BaseEntity implements HasCustomUrl{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7047018039868431818L;
	private Integer hazardID;                       // unique id from the portal
    private Integer acctID;
    //private final int reported;                     // unix time when it was reported   //TODO: deterimine if they still want to store REPORTED separate from START
    private final AutomationCalendar startTime = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    private final AutomationCalendar endTime = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    //private final int shelflife;                  // active for this many seconds since reported     //probably not necessary because of endTime
    @XmlElement(name = "radius")
    private Integer radiusMeters;                   // distance in meters
    private Integer radiusInUnits;
    private MeasurementLengthType radiusUnits;
    private HazardType type;                        // type of road hazard
    @XmlElement(name = "details")
    private String description = "";                // details of the hazard (max 60 chars)
    private Integer driverID;
    private Integer userID;
    private Integer vehicleID;
    private Integer deviceID;
    @XmlElement(name = "lat")
    private Double latitude;                        // latitude   (degrees * 1e6) ...   int on device
    @XmlElement(name = "long")
    private Double longitude;                       // longitude  (degrees * 1e6) ...   int on device
    @XmlAttribute
    private Integer stateID; 
    private String location = "";
    private HazardStatus status;
    //private final boolean urgent;                   // true if urgent                   //TODO: determine if I need to worry about Urgent?  I thought PRD stated ALL are urgent???
    //private final String group;                     // group name (max 40 chars)        //TODO: determine if group is necessary???
    public String testDeleteMes = "tests";
    
    private Driver view_driver;
    private User view_user;
    private State view_state;
    @JsonIgnore
	private HazardUrls customUrl;
    
    public Hazard() {
        super();
    }
//    public Hazard(byte[] rawData){
//        ByteBuffer wrapped = ByteBuffer.wrap(rawData);
//        short packetSize = wrapped.getShort();
//        Integer hazardID = wrapped.getInt();
//        int type = (int)wrapped.get();
//        //LatLng location =  wrapped.get// not seeing a build in way to pull 6 bytes back out and into 2 longs???
//        
//        
//    }
    @XmlElement(name = "typeID")
    @JsonProperty(value = "typeID")
    public int getTypeID(){
        return type.getCode();
    }
    @XmlElement(name = "hazardID")
    @JsonProperty(value = "hazardID")
    public Integer getHazardID() {
        return hazardID;
    }
    public long getStartTime() {
        return startTime.epochSecondsInt();
    }
    public long getEndTime() {
        return endTime.epochSecondsInt();
    }
    @JsonProperty(value = "radiusMeters")
    public Integer getRadiusMeters() {
        if(radiusMeters!=null){
            return radiusMeters;
        } else if(getType()==null){
            return radiusMeters;
        }

        return ((Double)getType().getRadius()).intValue();
    }
    @JsonProperty(value = "description")
    public String getDescription() {
        return description;
    }
    @JsonProperty(value = "lat")
    public Double getLat(){
        return getLatitude();
    }
    @JsonProperty(value = "lng")
    public Double getLng() {
        return getLongitude();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hazardID="+this.hazardID);
        buffer.append(", location="+this.location);
        buffer.append(", status="+this.status);
        buffer.append(", type="+this.type);
        buffer.append(", description="+this.description);
        return buffer.toString();
    }
    public Driver getDriver() {
        return view_driver;
    }
    public void setDriver(Driver driver){
        this.view_driver = driver;
    }
    public User getUser() {
        return view_user;
    }
    public void setUser(User user){
        this.view_user = user;
    }
    public State getState() {
        return view_state;
    }
    public void setState(State state){
        this.view_state = state;
    }

    public void setStartTime(int epochSeconds) {
        this.startTime.setDate(epochSeconds);
    }
    
    public AutomationCalendar getStartTimeCalendar() {
    	return startTime;
    }
    
    public void setEndTime(int epochSeconds) {
        this.endTime.setDate(epochSeconds);
    }
    
    public AutomationCalendar getEndTimeCalendar() {
    	return endTime;
    }
    public HazardType getType() {
        return type;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public Integer getDeviceID() {
        return deviceID;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public Integer getStateID() {
        return stateID;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getAccountID() {
        return getAcctID();
    }
    public void setAccountID(Integer acctID) {
        this.setAcctID(acctID);
    }
    public HazardStatus getStatus() {
        return status;
    }
    public void setStatus(HazardStatus status) {
        this.status = status;
    }
    @JsonProperty(value = "hazardID")
    public void setHazardID(Integer hazardID) {
        this.hazardID = hazardID;
    }
    public Integer getAcctID() {
        return acctID;
    }
    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    
    public void setRadiusMeters(Double radiusMeters) {
        this.radiusMeters = radiusMeters.intValue();
    }
    
    public void setType(HazardType type) {
        this.type = type;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    
    public void setLat(Double lat) {
    	setLatitude(lat);
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public void setLng(Double lng) {
    	setLongitude(lng);
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }
    public MeasurementLengthType getRadiusUnits() {
        return radiusUnits!=null?radiusUnits:MeasurementLengthType.ENGLISH_MILES;
    }
    
    public void setRadiusUnits(MeasurementLengthType radiusUnits) {
        this.radiusUnits = radiusUnits;
    }

    public Integer getRadiusInUnits() {
        Integer calculatedFromMeters =(getRadiusUnits()!=null && getRadiusMeters()!=null)?(Integer) getRadiusUnits().convertFromMeters(getRadiusMeters()).intValue():null;
        return radiusInUnits!=null?radiusInUnits:calculatedFromMeters;
    }
    public void setRadiusInUnits(Integer radiusInUnits) {
        this.radiusInUnits = radiusInUnits;
    }
    
    @JsonProperty(value = "typeID")
    public void setTypeID(int typeID){
    	type = HazardType.valueOf(typeID);
        
    }
    
	@Override
    @JsonIgnore
	public <T extends CustomUrl> void setCustomUrl(T custom) {
		if (!(custom instanceof HazardUrls)) {
			throw new IllegalArgumentException("Cannot use " + custom.getClass().getSimpleName() + " as a Hazard URL");
		} 
		
		this.customUrl = (HazardUrls) custom;
		
	}
	@Override
    @JsonIgnore
	public String getCustomUrl() {
		
		return customUrl.getRHUrl();
	}
	
	public static class HazardUrls implements CustomUrl {
		public final String mcmID;
		public final String lat;
		public final String lng;
		
		public HazardUrls(String mcmID, String lat, String lng) {
			this.mcmID = mcmID;
			this.lat = lat;
			this.lng = lng;
		}
		
		public String getRHUrl() {
			return String.format("roadhazard/getRH/%s/%s/%s.json", mcmID, lat, lng);
		}
	}
}
