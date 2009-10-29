package com.inthinc.pro.backing.ui;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
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
    

	public RedFlagReportItem(RedFlag redFlag, GroupHierarchy groupHierarchy,MeasurementType measurementType)
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
        }
        else
        {
            setGroup("");
        }
        
        setDriverName(event.getDriver().getPerson().getFullName());
        setVehicleName(event.getVehicle().getName());
//        String catFormat = MessageUtil.getMessageString("redflags_cat" + redFlag.getEvent().getEventCategory().toString(),LocaleBean.getCurrentLocale());
//        setCategory(MessageFormat.format(catFormat, new Object[] {MessageUtil.getMessageString(redFlag.getEvent().getEventType().toString(),LocaleBean.getCurrentLocale())}));
//        setCategory(redFlag.getEvent().getEventCategory().toString());
        setMphString(MessageUtil.getMessageString(measurementType.toString()+"_mph"));
        setMeasurementType(measurementType);
        setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType().name(),LocaleBean.getCurrentLocale()),measurementType,mphString));
    }
    

    public MeasurementType getMeasurementType() {
		return measurementType;
	}


	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}


	@Override
	public String getCategory() {

		return redFlag.getEvent().getEventCategory().toString();
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

