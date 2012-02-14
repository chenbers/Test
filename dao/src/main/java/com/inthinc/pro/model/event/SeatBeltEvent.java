package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;




@XmlRootElement
public class SeatBeltEvent extends Event {

	/*
	 * Note from Matt: waySmart devices have less precision then tiwiPros. If a severe seatbelt note occurs on a waySmart that has a distance less than 0.05, the waySmart firmware will round down to 0.
	 * Since it has been determined by Casey, Eric Capps, and Carleton that we cannot display 0 to the customer in this scenario (I argued strongly that we should display what is in the database and not
	 * allow for special cases), I will detect when a seatbelt event has a distance of 0 and change it to MIN_DISTANCE (which is then divide by 100). The code to do this is very ugly. 
	 * DO NOT manipulate the 'distance' instance variable directly; use the accessor methods instead.
	 */
	private static final Integer MIN_DISTANCE = 10;
	
	private static final long serialVersionUID = 1L;
    @EventAttrID(name="TOP_SPEED")
    private Integer topSpeed;
    @EventAttrID(name="AVG_SPEED")
    private Integer avgSpeed;
    @EventAttrID(name="DISTANCE")
    private Integer distance;
    @EventAttrID(name="MAX_RPM")
    private Integer maxRPM;


    private static EventAttr[] eventAttrList = {
        EventAttr.TOP_SPEED,
        EventAttr.DISTANCE,
        EventAttr.MAX_RPM
    };

    public SeatBeltEvent() {
		super();
	}

	public SeatBeltEvent(Long noteID, Integer vehicleID, NoteType type,
			Date time, Integer speed, Integer odometer, Double latitude,
			Double longitude, Integer avgSpeed, Integer topSpeed,
			Integer distance) {
		super(noteID, vehicleID, type, time, speed, odometer, latitude,
				longitude);
		this.avgSpeed = avgSpeed;
		this.topSpeed = topSpeed;
		//READ NOTE ABOVE
		if (distance == 0)
			this.distance = MIN_DISTANCE;
		else
			this.distance = distance;

	}

	public Integer getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Integer avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		//READ NOTE ABOVE
		if (distance == 0)
			this.distance = MIN_DISTANCE;
		else
			this.distance = distance;
	}

	public Integer getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(Integer maxRPM) {
        this.maxRPM = maxRPM;
    }

	public Integer getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(Integer topSpeed) {
		this.topSpeed = topSpeed;
	}

	public EventType getEventType() {
		return EventType.SEATBELT;
	}

	@Override
	public String getDetails(String formatStr, MeasurementType measurementType,
			String... mString) {
		// distance is x100
		float tempDistance = 0.0f;
		if (getDistance() != null) {
			tempDistance = getDistance().floatValue() / 100.0f;
		}

		if (measurementType.equals(MeasurementType.METRIC)) {
			tempDistance = MeasurementConversionUtil.fromMilesToKilometers(
					tempDistance).floatValue();
		}

		return MessageFormat.format(formatStr, new Float(tempDistance),
				mString[1]);
	}
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

}
