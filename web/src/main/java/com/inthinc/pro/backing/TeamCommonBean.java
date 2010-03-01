package com.inthinc.pro.backing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

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
    
    private Date startTime;
    private Date endTime;
    
    private DurationBean durationBean;
    
    public void init() {
    	
        this.durationBean.setDuration(Duration.TWODAY);

        this.startTime = getReportStartTime().toDate();
        this.endTime = new DateTime().minusDays(0).toDateMidnight().toDate();
        
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

    public DateMidnight getReportStartTime() {
        DateMidnight local = new DateTime().minusDays(30).toDateMidnight();
      
        if (            getDurationBean().getDuration().equals(Duration.ONEDAY) ) {
            return new DateTime().minusDays(Duration.ONEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     getDurationBean().getDuration().equals(Duration.TWODAY) ) {
            return new DateTime().minusDays(Duration.TWODAY.getNumberOfDays()).toDateMidnight();
                
        } else if (     getDurationBean().getDuration().equals(Duration.THREEDAY) ) {
            return new DateTime().minusDays(Duration.THREEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     getDurationBean().getDuration().equals(Duration.FOURDAY) ) {            
            return new DateTime().minusDays(Duration.FOURDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     getDurationBean().getDuration().equals(Duration.FIVEDAY) ) {
            return new DateTime().minusDays(Duration.FIVEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     getDurationBean().getDuration().equals(Duration.SIXDAY) ) {
            return new DateTime().minusDays(Duration.SIXDAY.getNumberOfDays()).toDateMidnight(); 
            
        } else if (     getDurationBean().getDuration().equals(Duration.SEVENDAY) ) {
            return new DateTime().minusDays(Duration.SEVENDAY.getNumberOfDays()).toDateMidnight();                  
            
        } else if (     getDurationBean().getDuration().equals(Duration.TWELVE) ) {
            return new DateTime().minusDays(Duration.TWELVE.getNumberOfDays()).toDateMidnight();
        }
        
        
        return local;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
