package com.inthinc.pro.model.hos;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class DriverHoursRecord extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6390658799480605750L;
	private Date date;
    private Integer driverID;
    private Double hoursThisDay;
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
}
