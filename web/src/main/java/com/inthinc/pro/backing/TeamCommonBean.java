package com.inthinc.pro.backing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;

public class TeamCommonBean extends BaseBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final String[] DAZE = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String TODAY = "Today";
    private static final String YESTERDAY = "Yesterday";
    private Map<String,String> dayLabels = new HashMap<String,String>();
	
	private Integer groupID;
    private Group group;
    
    private DateMidnight startTime;
    private DateMidnight endTime;
    
    private DurationBean durationBean;
    
    public void init() {
    	
        if ( this.durationBean.getDuration().equals(Duration.DAYS) ) {
            this.durationBean.setDuration(Duration.TWODAY);
        }
        getReportTimes();
        
    }
    
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {

		if(groupID!= null){
			
		    group = getGroupHierarchy().getGroup(groupID);
		}
		this.groupID = groupID;
		    
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
    public DurationBean getDurationBean() {
        return durationBean;
    }
    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }
    public Map<String, String> getDayLabels() {

        if ( dayLabels.isEmpty() ) {
            dayLabels.put("0", TeamCommonBean.TODAY);
            dayLabels.put("1", TeamCommonBean.YESTERDAY);
                        
            GregorianCalendar today = new GregorianCalendar();
            
            // Gets the day of the week, adds seven then get five entries going 
            //  backward in the array from there (It starts with sunday as 1)
            int day = today.get(Calendar.DAY_OF_WEEK);   
            day += 7;
            for ( int i = 0; i < 5; i++ ) {                         
                dayLabels.put(Integer.toString(i+2),DAZE[day-2]);   
                day--;
            }            
        }

        return dayLabels;
    }

    public void setDayLabels(HashMap<String, String> dayLabels) {
        this.dayLabels = dayLabels;
    }

    public void getReportTimes() {   
        
        // Get the time zone info
        DateTimeZone dtz = DateTimeZone.getDefault();
        TimeZone user = this.getProUser().getUser().getPerson().getTimeZone();
        if ( user != null ) {
            dtz.forTimeZone(user);
        }

        // Find the time zone adjusted date
        if (            getDurationBean().getDuration().equals(Duration.ONEDAY) ) {
            this.setStartTime((new DateMidnight().plusDays(0)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().plusDays(1)).withZoneRetainFields(dtz));
            
        } else if (     getDurationBean().getDuration().equals(Duration.TWODAY) ) {
            this.setStartTime((new DateMidnight().minusDays(1)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().plusDays(0)).withZoneRetainFields(dtz));            
            
        } else if (     getDurationBean().getDuration().equals(Duration.THREEDAY) ) {
            this.setStartTime((new DateMidnight().minusDays(2)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().minusDays(1)).withZoneRetainFields(dtz));
            
        } else if (     getDurationBean().getDuration().equals(Duration.FOURDAY) ) {            
            this.setStartTime((new DateMidnight().minusDays(3)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().minusDays(2)).withZoneRetainFields(dtz));
           
        } else if (     getDurationBean().getDuration().equals(Duration.FIVEDAY) ) {
            this.setStartTime((new DateMidnight().minusDays(4)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().minusDays(3)).withZoneRetainFields(dtz));
            
        } else if (     getDurationBean().getDuration().equals(Duration.SIXDAY) ) {
            this.setStartTime((new DateMidnight().minusDays(5)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().minusDays(4)).withZoneRetainFields(dtz));
            
        } else if (     getDurationBean().getDuration().equals(Duration.SEVENDAY) ) {
            this.setStartTime((new DateMidnight().minusDays(6)).withZoneRetainFields(dtz));
            this.setEndTime((new DateMidnight().minusDays(5)).withZoneRetainFields(dtz));
            
        } 
      
        // Back to GMT
        this.startTime = new DateMidnight(this.getStartTime().getMillis(),DateTimeZone.forID("UTC"));
        this.endTime = new DateMidnight(this.getEndTime().getMillis(),DateTimeZone.forID("UTC"));
    }

	public DateMidnight getStartTime() {
		return startTime;
	}

	public void setStartTime(DateMidnight startTime) {
		this.startTime = startTime;
	}

	public DateMidnight getEndTime() {
		return endTime;
	}

	public void setEndTime(DateMidnight endTime) {
		this.endTime = endTime;
	}

}
