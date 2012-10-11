package com.inthinc.pro.model.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidOccupantEvent extends LoginEvent {
    public EventType getEventType() {
        return EventType.NEW_OCCUPANT;
    }
}
