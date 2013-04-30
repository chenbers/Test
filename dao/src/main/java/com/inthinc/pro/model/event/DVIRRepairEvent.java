package com.inthinc.pro.model.event;

import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

public class DVIRRepairEvent extends Event {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;
    
    /*ATTR_DVIR_MECHANIC_ID_STR(24598, 10, false), // string 10         fixed length,              \0 filled
    ATTR_DVIR_INSPECTOR_ID_STR(24599, 10, true), // string 10 max, variable length,              \0 terminated
    ATTR_DVIR_SIGNOFF_ID_STR(24600, 10, true),   // string 10 max, variable length,              \0 terminated
    ATTR_DVIR_COMMENTS(24601, 60, true), */
    
    @EventAttrID(name="ATTR_DVIR_MECHANIC_ID_STR")
    private String mechanicID;
    @EventAttrID(name="ATTR_DVIR_INSPECTOR_ID_STR")
    private String inspectorID;
    @EventAttrID(name="ATTR_DVIR_SIGNOFF_ID_STR")
    private String signOffID;
    @EventAttrID(name="ATTR_DVIR_COMMENTS")
    private String comments;
    
    private static EventAttr[] eventAttrList = {
        EventAttr.ATTR_DVIR_MECHANIC_ID_STR,
        EventAttr.ATTR_DVIR_INSPECTOR_ID_STR,
        EventAttr.ATTR_DVIR_SIGNOFF_ID_STR,
        EventAttr.ATTR_DVIR_COMMENTS
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }
    
    public DVIRRepairEvent(){
        super();
    }
    
    public DVIRRepairEvent(String mechanicID, String inspectorID, String signOffID, String comments){
        super();
        this.mechanicID = mechanicID;
        this.inspectorID = inspectorID;
        this.signOffID = signOffID;
        this.comments = comments;
    }
    
    public DVIRRepairEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
                    String mechanicID, String inspectorID, String signOffID, String comments){
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.mechanicID = mechanicID;
        this.inspectorID = inspectorID;
        this.signOffID = signOffID;
        this.comments = comments;
    }

    public String getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(String mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getInspectorID() {
        return inspectorID;
    }

    public void setInspectorID(String inspectorID) {
        this.inspectorID = inspectorID;
    }

    public String getSignOffID() {
        return signOffID;
    }

    public void setSignOffID(String signOffID) {
        this.signOffID = signOffID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    @Override
    public boolean isValidEvent() {
        boolean isValidMechanicID = mechanicID != null && mechanicID.length() > 0;
        boolean isValidInspectorID = inspectorID != null && inspectorID.length() > 0;
        boolean isValidSiginOffID = signOffID != null && signOffID.length() > 0;
        
        return isValidMechanicID && isValidInspectorID && isValidSiginOffID; 
    }
}
