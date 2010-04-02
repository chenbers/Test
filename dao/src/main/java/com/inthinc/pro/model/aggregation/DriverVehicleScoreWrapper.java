package com.inthinc.pro.model.aggregation;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;

@XmlRootElement
public class DriverVehicleScoreWrapper {
    private Driver driver;
    private Vehicle vehicle;
    @Column(name="driveQ")
    private Score score;
    private String scoreStyle;
    private Boolean summary;

	public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public String getScoreStyle() {
        return scoreStyle;
    }

    public void setScoreStyle(String scoreStyle) {
        this.scoreStyle = scoreStyle;
    }

    public Boolean getSummary() {
		return summary;
	}

	public void setSummary(Boolean summary) {
		this.summary = summary;
	}


    @Override
    public String toString() {
        return "DriverVehicleScoreWrapper [driver=" + driver + ", score=" + score + ", vehicle=" + vehicle + "]";
    }

}
