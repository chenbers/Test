package com.inthinc.pro.aggregation.model;

import java.util.Date;
import java.util.List;


public class Trip {
    private Long tripID = 0L;
    private Long vehicleID = 0L;
    private Long driverID = 0L;
    private Long deviceID = 0L;
    private Date startTime;
    private Date endTime;
    private Integer mileage = 0;
    private Integer idleTime = 0;
    private Long mileageOffset = 0L;

    private TripStatus status;
    private TripQuality quality;
    
	private Long startNoteID = 0L;
	private Long endNoteID = 0L;
	private Integer startNoteType = 0;
	private Integer endNoteType = 0;
	private Long startOdometer = 0L;
	private Long endOdometer = 0L;
    

    public Trip() {
        super();
    }

    public Trip(Long tripID, Long vehicleID, Date startTime, Date endTime, Integer mileage) {
        super();
        this.tripID = tripID;
        this.vehicleID = vehicleID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mileage = mileage;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }


    public Long getMileageOffset() {
		return mileageOffset;
	}

	public void setMileageOffset(Long mileageOffset) {
		this.mileageOffset = mileageOffset;
	}

	public Integer getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}

	public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int compareTo(Trip trip) {
        return trip.getStartTime().compareTo(getStartTime());
    }


//    public boolean isEventsExist() {
//        return events != null && events.size() > 0;
//    }

    public Long getTripID() {
        return tripID;
    }

    public void setTripID(Long tripID) {
        this.tripID = tripID;
    }

    public Long getDriverID() {
        return driverID;
    }

    public void setDriverID(Long driverID) {
        this.driverID = driverID;
    }
    
    public Long getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Long deviceId2) {
		this.deviceID = deviceId2;
	}

	public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public TripQuality getQuality() {
        if (quality == null)
            return TripQuality.UNKNOWN;
        return quality;
    }

    public void setQuality(TripQuality quality) {
        this.quality = quality;
    }

	public Long getStartNoteID() {
		return startNoteID;
	}

	public void setStartNoteID(Long startNoteID) {
		this.startNoteID = startNoteID;
	}

	public Long getEndNoteID() {
		return endNoteID;
	}

	public void setEndNoteID(Long endNoteID) {
		this.endNoteID = endNoteID;
	}

	public Integer getStartNoteType() {
		return startNoteType;
	}

	public void setStartNoteType(Integer startNoteType) {
		this.startNoteType = startNoteType;
	}

	public Integer getEndNoteType() {
		return endNoteType;
	}

	public void setEndNoteType(Integer endNoteType) {
		this.endNoteType = endNoteType;
	}

	public Long getStartOdometer() {
		return startOdometer;
	}

	public void setStartOdometer(Long startOdometer) {
		this.startOdometer = startOdometer;
	}

	public Long getEndOdometer() {
		return endOdometer;
	}

	public void setEndOdometer(Long endOdometer) {
		this.endOdometer = endOdometer;
	}
}
