package com.inthinc.pro.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * Holds a list of vehicle data and trip data.
 */
@XmlRootElement
public class VehicleTripViewList {

    private List<VehicleTripView> vehicleTripViews;

    public List<VehicleTripView> getVehicleTripViews() {
        return vehicleTripViews;
    }

    public void setVehicleTripViews(List<VehicleTripView> vehicleTripViews) {
        this.vehicleTripViews = vehicleTripViews;
    }
}
