package com.inthinc.pro.model;

import java.text.MessageFormat;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoDriverEvent extends Event{

	private static final long serialVersionUID = 1L;

	public NoDriverEvent() {
		super();
	}
    public EventType getEventType()
    {
        return EventType.NO_DRIVER;
    }
    public EventCategory getEventCategory()
    {
        return EventCategory.WARNING;
    }

}
