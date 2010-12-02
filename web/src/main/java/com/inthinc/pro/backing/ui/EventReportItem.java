package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Alert;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.util.MessageUtil;

public class EventReportItem extends NotificationReportItem<EventReportItem> {

    private static final Logger logger = Logger.getLogger(EventReportItem.class);

    private Event event;
    private RedFlagLevel level;
    private boolean alert;

    private long noteID;

    /* added for pagination (JASPER) -- may be able to remove others */
    public EventReportItem(Event event, MeasurementType measurementType, DateFormat dateFormatter) {
        
        this.event = event;
        this.level = RedFlagLevel.INFO;

        if (event.getDriverTimeZone() != null) {
            dateFormatter.setTimeZone(event.getDriverTimeZone());
        }
        setDate(dateFormatter.format(event.getTime()));
        setGroup(event.getGroupName());
        setGroupID(event.getGroupID());

        setDriverName(event.getDriverName() == null ? MessageUtil.getMessageString("unknown_driver") : event.getDriverName());
        setVehicleName(event.getVehicleName() == null ? MessageUtil.getMessageString("unassigned") : event.getVehicleName());

        String catFormat = MessageUtil.getMessageString("redflags_cat" + EventCategory.getCategoryForEventType(event.getEventType()).toString());
        setCategory(MessageFormat.format(catFormat, new Object[] { MessageUtil.getMessageString(event.getEventType().toString()) }));

        setDetail(event, measurementType);
        setNoteID(event.getNoteID());
    }

    private void setDetail(Event event, MeasurementType measurementType) {
        if (event.getClass().isInstance(StatusEvent.class)) {
            String statusString = MessageUtil.getMessageString(((StatusEvent)event).getStatusMessageKey());
            setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType()), measurementType, statusString));
        }
        else {
            String mphString = MessageUtil.getMessageString(measurementType.toString() + "_mph");
            setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType()), measurementType, mphString));
        }
    }

    /* END - added for pagination -- may be able to remove others */

    public EventReportItem(Event event, Alert rfAlert, GroupHierarchy groupHierarchy, MeasurementType measurementType, DateFormat dateFormatter) {
        this.event = event;
        alert = (rfAlert != null);
        if (rfAlert != null) {
            level = rfAlert.getLevel();
        } else
            level = RedFlagLevel.INFO;

        TimeZone tz = (event.getDriver() == null || event.getDriver().getPerson() == null) ? TimeZone.getDefault() : event.getDriver().getPerson().getTimeZone();
        dateFormatter.setTimeZone((tz == null) ? TimeZone.getDefault() : tz);
        setDate(dateFormatter.format(event.getTime()));

        Group group = groupHierarchy.getGroup(event.getGroupID());
        if (group != null) {
            setGroup(group.getName());
            setGroupID(event.getGroupID());
        } else {
            setGroup("");
            setGroupID(null);
        }

        setDriverName((event.getDriver() == null || event.getDriver().getPerson() == null) ? MessageUtil.getMessageString("unassigned") : event.getDriver().getPerson().getFullName());
        setVehicleName((event.getVehicle() == null) ? MessageUtil.getMessageString("unassigned") : event.getVehicle().getName());

        String catFormat = MessageUtil.getMessageString("redflags_cat" + EventCategory.getCategoryForEventType(event.getEventType()).toString());
        setCategory(MessageFormat.format(catFormat, new Object[] { MessageUtil.getMessageString(event.getEventType().toString()) }));

        setDetail(event, measurementType);

        setNoteID(event.getNoteID());
    }

    public EventReportItem(Event event, TimeZone tz, MeasurementType measurementType, DateFormat dateFormatter) {
        this.event = event;

        dateFormatter.setTimeZone((tz == null) ? TimeZone.getDefault() : tz);
        setDate(dateFormatter.format(event.getTime()));

        String catFormat = MessageUtil.getMessageString("redflags_cat" + EventCategory.getCategoryForEventType(event.getEventType()).toString());
        setCategory(MessageFormat.format(catFormat, new Object[] { MessageUtil.getMessageString(event.getEventType().toString()) }));

        setDetail(event, measurementType);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public RedFlagLevel getLevel() {
        return level;
    }

    public void setLevel(RedFlagLevel level) {
        this.level = level;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    @Override
    public int compareTo(EventReportItem o) {
        return this.getEvent().getTime().compareTo(o.getEvent().getTime());
    }

    public long getNoteID() {
        return noteID;
    }

    public void setNoteID(long noteID) {
        this.noteID = noteID;
    }

}
