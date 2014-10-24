package com.inthinc.pro.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Holds vehicle data and trip data.
 */
@XmlRootElement
public class VehicleTripView {

    @JsonIgnore
    @XmlTransient
    private Vehicle vehicle;

    private Date lastTrip;

    public VehicleTripView(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleTripView() {
    }

    public Date getLastTrip() {
        return lastTrip;
    }

    public void setLastTrip(Date lastTrip) {
        this.lastTrip = lastTrip;
    }

    @XmlElement
    public Integer getVehicleID() {
        if (vehicle == null)
            return null;

        return vehicle.getVehicleID();
    }

    public void setVehicleID(Integer vehicleID) {
        if (vehicle != null)
            this.vehicle.setVehicleID(vehicleID);
    }

    @XmlElement
    public Integer getGroupID() {
        if (vehicle == null)
            return null;

        return vehicle.getGroupID();
    }

    public void setGroupID(Integer groupID) {
        if (vehicle != null)
            vehicle.setGroupID(groupID);
    }

    @XmlElement
    public Status getStatus() {
        if (vehicle == null)
            return null;

        return vehicle.getStatus();
    }

    public void setStatus(Status status) {
        if (vehicle != null)
            vehicle.setStatus(status);
    }

    @XmlElement
    public String getName() {
        if (vehicle == null)
            return null;

        return vehicle.getName();
    }

    public void setName(String name) {
        if (vehicle != null)
            vehicle.setName(name);
    }

    @XmlElement
    public String getMake() {
        if (vehicle == null)
            return null;

        return vehicle.getMake();
    }

    public void setMake(String make) {
        if (vehicle != null)
            vehicle.setMake(make);
    }

    @XmlElement
    public String getModel() {
        if (vehicle == null)
            return null;

        return vehicle.getModel();
    }

    public void setModel(String model) {
        if (vehicle != null)
            vehicle.setModel(model);
    }

    @XmlElement
    public Integer getYear() {
        if (vehicle == null)
            return null;

        return vehicle.getYear();
    }

    public void setYear(Integer year) {
        if (vehicle != null)
            vehicle.setYear(year);
    }

    @XmlElement
    public String getColor() {
        if (vehicle == null)
            return null;

        return vehicle.getColor();
    }

    public void setColor(String color) {
        if (vehicle != null)
            vehicle.setColor(color);
    }

    @XmlElement
    public VehicleType getVtype() {
        if (vehicle == null)
            return null;

        return vehicle.getVtype();
    }

    public void setVtype(VehicleType vtype) {
        if (vehicle != null)
            vehicle.setVtype(vtype);
    }

    @XmlElement
    public String getVIN() {
        if (vehicle == null)
            return null;

        return vehicle.getVIN();
    }

    public void setVIN(String VIN) {
        if (vehicle != null)
            vehicle.setVIN(VIN);
    }

    @XmlElement
    public Integer getWeight() {
        if (vehicle == null)
            return null;

        return vehicle.getWeight();
    }

    public void setWeight(Integer weight) {
        if (vehicle != null)
            vehicle.setWeight(weight);
    }

    @XmlElement
    public String getLicense() {
        if (vehicle == null)
            return null;

        return vehicle.getLicense();
    }

    public void setLicense(String license) {
        if (vehicle != null)
            vehicle.setLicense(license);
    }

    @XmlElement
    public State getState() {
        if (vehicle == null)
            return null;

        return vehicle.getState();
    }

    public void setState(State state) {
        if (vehicle != null)
            vehicle.setState(state);
    }

    @XmlElement
    public Integer getDriverID() {
        if (vehicle == null)
            return null;

        return vehicle.getDriverID();
    }

    public void setDriverID(Integer driverID) {
        if (vehicle != null)
            vehicle.setDriverID(driverID);
    }

    @XmlElement
    public String getDriverName() {
        if (vehicle == null)
            return null;

        return vehicle.getDriverName();
    }

    public void setDriverName(String driverName) {
        if (vehicle != null)
            vehicle.setDriverName(driverName);
    }

    @XmlElement
    public String getGroupName() {
        if (vehicle == null)
            return null;

        return vehicle.getGroupName();
    }

    public void setGroupName(String groupName) {
        if (vehicle != null)
            vehicle.setGroupName(groupName);
    }

    @XmlElement
    public Integer getDeviceID() {
        if (vehicle == null)
            return null;

        return vehicle.getDeviceID();
    }

    public void setDeviceID(Integer deviceID) {
        if (vehicle != null)
            vehicle.setDeviceID(deviceID);
    }

    @XmlElement
    public Integer getOdometer() {
        if (vehicle == null)
            return null;

        return vehicle.getOdometer();
    }

    public void setOdometer(Integer odometer) {
        if (vehicle != null)
            vehicle.setOdometer(odometer);
    }

    @XmlElement
    public Boolean getIfta() {
        if (vehicle == null)
            return null;

        return vehicle.getIfta();
    }

    public void setIfta(Boolean ifta) {
        if (vehicle != null)
            vehicle.setIfta(ifta);
    }

    @XmlElement
    public Device getDevice() {
        if (vehicle == null)
            return null;

        return vehicle.getDevice();
    }

    public void setDevice(Device device) {
        if (vehicle != null)
            vehicle.setDevice(device);
    }
}
