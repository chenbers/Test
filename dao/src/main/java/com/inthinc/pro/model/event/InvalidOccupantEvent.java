package com.inthinc.pro.model.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvalidOccupantEvent extends LoginEvent {
    public EventType getEventType() {
        return EventType.INVALID_OCCUPANT;
    }
}
