package com.inthinc.pro.model.performance;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class DriverHoursRecord extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6390658799480605750L;
	private DateTime day;
    private Integer driverID;
    private Double hoursThisDay;
    

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Double getHoursThisDay() {
        return hoursThisDay;
    }

    public void setHoursThisDay(Double hoursThisDay) {
        this.hoursThisDay = hoursThisDay;
    }

    public DateTime getDay() {
        return day;
    }

    public void setDay(DateTime day) {
        this.day = day;
    }

}
