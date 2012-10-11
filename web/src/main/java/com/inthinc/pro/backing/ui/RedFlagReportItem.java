package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagReportItem extends NotificationReportItem<RedFlagReportItem>
{
    
    private static final Logger logger = Logger.getLogger(RedFlagReportItem.class);
    
    private RedFlag redFlag;

	private Zone zone;
	private String mphString;
	private MeasurementType measurementType;
	
    private final static String UNKNOWN_DRIVER = "unknown_driver";
	
    

	public RedFlagReportItem(RedFlag redFlag, GroupHierarchy groupHierarchy,MeasurementType measurementType, DateFormat dateFormatter)
    {
        this.redFlag = redFlag;

        Event event = redFlag.getEvent();
        
        if (event == null)
        {
            logger.error("Unable to retrieve Event for Red Flag: ");
            return;
        }
        
        dateFormatter.setTimeZone(redFlag.getTimezone());
        setDate(dateFormatter.format(event.getTime()));
        
        Group group = groupHierarchy.getGroup(event.getGroupID());
        if (group != null)
        {
            setGroup(group.getName());
            setGroupID(event.getGroupID());
        }
        else
        {
            setGroup("");
            setGroupID(null);
        }
        
        // Addition of unknown driver requires a check for non-null person object
        if ( event.getDriver().getPerson() == null ) {
            setDriverName(MessageUtil.getMessageString(UNKNOWN_DRIVER,LocaleBean.getCurrentLocale()));
        } else {        
            setDriverName(event.getDriver().getPerson().getFullName());
        }
        setVehicleName(event.getVehicle().getName());
//        String catFormat = MessageUtil.getMessageString("redflags_cat" + redFlag.getEvent().getEventCategory().toString(),LocaleBean.getCurrentLocale());
//        setCategory(MessageFormat.format(catFormat, new Object[] {MessageUtil.getMessageString(redFlag.getEvent().getEventType().toString(),LocaleBean.getCurrentLocale())}));
//        setCategory(redFlag.getEvent().getEventCategory().toString());
        setMphString(MessageUtil.getMessageString(measurementType.toString()+"_mph"));
        setMeasurementType(measurementType);
        setDetail(event, measurementType);
    }
    
    private void setDetail(Event event, MeasurementType measurementType) {
        if (event instanceof StatusEvent) {
            String statusString = MessageUtil.getMessageString(((StatusEvent)event).getStatusMessageKey());
            setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType()), measurementType, statusString));
        }
        else {
            String mphString = MessageUtil.getMessageString(measurementType.toString() + "_mph");
            setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType()), measurementType, mphString));
        }
    }

    public MeasurementType getMeasurementType() {
		return measurementType;
	}


	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}


	@Override
	public String getCategory() {

		return EventCategory.getCategoryForEventType(redFlag.getEvent().getEventType()).toString();
	}
    public String getEventType(){
    	
    	return redFlag.getEvent().getEventType().toString();
    }

	@Override
	public String getDetail() {
		return redFlag.getEvent().getDetails(MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType().name()),measurementType,mphString); 
	}


	public RedFlag getRedFlag()
    {
        return redFlag;
    }

    public void setRedFlag(RedFlag redFlag)
    {
        this.redFlag = redFlag;
    }

	public Zone getZone() {
	    return zone;
	}

	public void setZone(Zone zone) {
	    this.zone = zone;
	}
    @Override
    public int compareTo(RedFlagReportItem o)
    {
        return this.getRedFlag().getLevel().compareTo(o.getRedFlag().getLevel());
    }

    public String getDescription(){
    	
    	return redFlag.getLevel().getDescription();
    }
    public String getMphString(){
    	return mphString;
    }


	public void setMphString(String mphString) {
		this.mphString = mphString;
	}
    
}

