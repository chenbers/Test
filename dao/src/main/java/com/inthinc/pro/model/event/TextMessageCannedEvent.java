package com.inthinc.pro.model.event;

import java.util.Date;

@SuppressWarnings("serial")
public class TextMessageCannedEvent extends TextMessageEvent {
    private static EventAttr[] eventAttrList = {
        EventAttr.EVENT_CODE,
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }
    
    public TextMessageCannedEvent()
    {
        super();
    }

    public TextMessageCannedEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
            Integer textId, String textMsg)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude, textId, textMsg);

    }
}
