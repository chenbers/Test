package com.inthinc.pro.model.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidDriverEvent extends LoginEvent {
    @Override
    public EventType getEventType() {
        return EventType.NEW_DRIVER;
    }
}
