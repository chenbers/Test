package com.inthinc.pro.model.aggregation;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;

public class ScoreWrapper {
    private Driver driver;
    private Vehicle vehicle;
    private Score score;

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

}
