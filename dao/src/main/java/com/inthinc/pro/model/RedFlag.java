package com.inthinc.pro.model;

import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.event.Event;

@XmlRootElement
public class RedFlag extends BaseEntity
{
    @Column(updateable=false)
    private RedFlagLevel level;
    
    @Column(updateable=false)
    private AlertSentStatus sent;
    
    @Column(name="note",updateable=false)
    private Event event;
    
    @Column(name="tzName",updateable=false)
    private TimeZone timezone;
    
    private Integer msgID;
    
    public RedFlag()
    {
    }
    public RedFlag(Integer redFlagID, RedFlagLevel level,  AlertSentStatus sent, Event event, TimeZone timezone)
    {
        this.level = level;
        this.sent = sent;
        this.event = event;
        this.timezone = timezone;
    }
    

    public RedFlagLevel getLevel()
    {
        return level;
    }

    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }

    public AlertSentStatus getSent()
    {
        return sent;
    }

    public void setSent(AlertSentStatus sent)
    {
        this.sent = sent;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
    public TimeZone getTimezone()
    {
        return timezone;
    }
    public void setTimezone(TimeZone timezone)
    {
        this.timezone = timezone;
    }

    public Integer getMsgID() {
        return msgID;
    }
    public void setMsgID(Integer msgID) {
        this.msgID = msgID;
    }

}
