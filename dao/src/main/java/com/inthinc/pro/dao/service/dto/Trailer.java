package com.inthinc.pro.dao.service.dto;

import java.util.Date;

import com.inthinc.pro.model.Status;

public class Trailer {
    private Integer trailerID;
    private Group group;
    private Account account;
    private Status status;
    private String name;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private String vin;
    private Integer weight;
    private String license;
    private Integer stateID;
    private Integer odometer;
    private Integer absOdometer;
    private String groupPath;
    private Date modified;
    private Date warrantyStart;
    private Date warrantyStop;
    private Date aggDate;
    private Date newAggDate;
    private Date pairingDate;
    private Device device;
    private Vehicle vehicle;
    private Driver driver;

    public Trailer() {}

    public Integer getTrailerID() {
        return trailerID;
    }

    public void setTrailerID(Integer trailerID) {
        this.trailerID = trailerID;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public Integer getAbsOdometer() {
        return absOdometer;
    }

    public void setAbsOdometer(Integer absOdometer) {
        this.absOdometer = absOdometer;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getWarrantyStart() {
        return warrantyStart;
    }

    public void setWarrantyStart(Date warrantyStart) {
        this.warrantyStart = warrantyStart;
    }

    public Date getWarrantyStop() {
        return warrantyStop;
    }

    public void setWarrantyStop(Date warrantyStop) {
        this.warrantyStop = warrantyStop;
    }

    public Date getAggDate() {
        return aggDate;
    }

    public void setAggDate(Date aggDate) {
        this.aggDate = aggDate;
    }

    public Date getNewAggDate() {
        return newAggDate;
    }

    public void setNewAggDate(Date newAggDate) {
        this.newAggDate = newAggDate;
    }

    public Date getPairingDate() {
        return pairingDate;
    }

    public void setPairingDate(Date pairingDate) {
        this.pairingDate = pairingDate;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
