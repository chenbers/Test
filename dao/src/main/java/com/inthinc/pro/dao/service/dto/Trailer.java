package com.inthinc.pro.dao.service.dto;

import java.util.Date;

import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TrailerPairingType;

public class Trailer {
    private Integer trailerID;
    private Account account;
    private Status status;
    private Date pairingDate;
    private Device device;
    private TrailerPairingType pairingType;
    
    private String name;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private String vin;
    private Integer weight;
    private String license;
    private Integer stateID;
    private Date modified;
    
    // TODO: these might be going away
    private Integer odometer;
    private Integer absOdometer;
    private Date warrantyStart;
    private Date warrantyStop;
    private Date aggDate;
    private Date newAggDate;

    public Trailer() {}

    public Integer getTrailerID() {
        return trailerID;
    }

    public void setTrailerID(Integer trailerID) {
        this.trailerID = trailerID;
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

    public TrailerPairingType getPairingType() {
        return pairingType;
    }

    public void setPairingType(TrailerPairingType pairingType) {
        this.pairingType = pairingType;
    }

}
