package com.inthinc.pro.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

@XmlRootElement
public class RedFlagAlert extends RedFlagOrZoneAlert {

    @Column(updateable = false)
    private static final long serialVersionUID = -1621262257747114161L;
    @Column(updateable = false)
    public static final Integer DEFAULT_LEVEL = 2;
    @Column(updateable = false)
    public static final Integer DEFAULT_BUMP_LEVEL = 3;
    @ID
    @Column(name = "alertID")
    private Integer redFlagAlertID;
    
    public RedFlagAlert() {
    }

    public RedFlagAlert(AlertMessageType type, Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo, Integer[] speedSettings,
            Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, RedFlagLevel severityLevel
            ) {
        super(type,accountID, userID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo, speedSettings,
                hardAcceleration, hardBrake, hardTurn, hardVertical,severityLevel, null, null, null);
    }

    public Integer getRedFlagAlertID() {
        return redFlagAlertID;
    }

    @Override
    public Integer getId() {
        return redFlagAlertID;
    }

    @Override
    public void setId(Integer id) {
        this.redFlagAlertID = id;
    }

    public void setRedFlagAlertID(Integer redFlagAlertID) {
        this.redFlagAlertID = redFlagAlertID;
    }


}
