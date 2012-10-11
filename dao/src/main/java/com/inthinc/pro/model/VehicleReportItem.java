package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VehicleReportItem extends BaseEntity implements Comparable<VehicleReportItem>
{
    /**
     * 
     */
    private static final long serialVersionUID = -199959826868545534L;

    private String groupName;
    private Integer groupID;
    private String driverName;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private String vehicleYMM;
    private Number milesDriven;
    private Number odometer;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    
    public Number getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Number milesDriven)
    {
        this.milesDriven = milesDriven;
    }
    public Integer getOverallScore()
    {
        return overallScore;
    }
    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
    }
    public Integer getSpeedScore()
    {
        return speedScore;
    }
    public void setSpeedScore(Integer speedScore)
    {
        this.speedScore = speedScore;
    }
    public Integer getStyleScore()
    {
        return styleScore;
    }
    public void setStyleScore(Integer styleScore)
    {
        this.styleScore = styleScore;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Number getOdometer() {
        return odometer;
    }
    public void setOdometer(Number odometer) {
        this.odometer = odometer;
    }
    public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Integer getDriverID() {
		return driverID;
	}
	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public Integer getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}
    public String getVehicleYMM() {
		return vehicleYMM;
	}
	public void setVehicleYMM(String vehicleYMM) {
		this.vehicleYMM = vehicleYMM;
	}
    @Override
    public int compareTo(VehicleReportItem item)
    {
    	if (vehicleName != null && item.getVehicleName() != null)
            return vehicleName.toLowerCase().compareTo(item.getVehicleName().toLowerCase());
    	return 0;
    }
    	

}
