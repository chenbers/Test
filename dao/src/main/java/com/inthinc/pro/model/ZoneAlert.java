package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZoneAlert extends RedFlagOrZoneAlert
{

    @Column(updateable = false)
    private static final long serialVersionUID = 3066238032590993441L;
    @Column(updateable = false)
    public static final Integer DEFAULT_BUMP_LEVEL = 3;

    @ID
    @Column(name="alertID")
    private Integer           zoneAlertID;

    public ZoneAlert(Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo,
            Integer zoneAlertID, Integer zoneID, Boolean arrival, Boolean departure)
    {
        super(AlertMessageType.ALERT_TYPE_ZONES,accountID, userID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo, null,
                null, null, null, null,null,zoneID, arrival, departure);
        this.zoneAlertID = zoneAlertID;
    }

//    public ZoneAlert(Integer zoneAlertID, Integer zoneID, Boolean arrival, Boolean departure)
//    {
//        super();
//        this.zoneAlertID = zoneAlertID;
//        this.zoneID = zoneID;
//        this.arrival = arrival;
//        this.departure = departure;
//    }

    public ZoneAlert()
    {
        super();
    }
    
    public Integer getZoneAlertID()
    {
        return zoneAlertID;
    }

    public void setZoneAlertID(Integer zoneAlertID)
    {
        this.zoneAlertID = zoneAlertID;
    }


    @Override
    public Integer getId() {

        return zoneAlertID;
    }

    @Override
    public void setId(Integer id) {

        zoneAlertID = id;
    }
    
    @Override
    public AlertMessageType getType() {

        return AlertMessageType.ALERT_TYPE_ZONES;
    }

}
