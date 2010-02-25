package com.inthinc.pro.backing;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class TeamStatisticsBean extends BaseBean {

//  Request scope bean for new team page    
    private static final long serialVersionUID = 1L;
    
    private static final String[] DAZE = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String TODAY = "Today";
    private static final String YESTERDAY = "Yesterday";

    private List<DriverVehicleScoreWrapper> driverStatistics;
    private Integer groupID;
    private Group group;
    private HashMap<String,String> dayLabels = new HashMap<String,String>();

    private GroupReportDAO groupReportDAO;  
    private TeamCommonBean teamCommonBean;    

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        DateMidnight endTime = new DateTime().minusDays(0).toDateMidnight();
//        DateMidnight startTime = new DateTime().minusDays(20).toDateMidnight();
        DateMidnight startTime = getReportStartTime();
        
//        if (this.driverStatistics == null) {
//            driverStatistics = groupReportDAO.getDriverScores(getGroupID(), Duration.DAYS);
            driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), startTime.toDateTime(), endTime.toDateTime());
            loadScoreStyles();
//        }
        return driverStatistics;
    }

    public void setDriverStatistics(List<DriverVehicleScoreWrapper> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }

    public Integer getGroupID() {
        return teamCommonBean.getGroupID();
    }

    public void setGroupID(Integer groupID) {
        // TODO: probably need some checks here on groupID and the group object        
        group = getGroupHierarchy().getGroup(groupID);
        this.groupID = groupID;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public HashMap<String, String> getDayLabels() {

        if ( dayLabels.isEmpty() ) {
            dayLabels.put("0", this.TODAY);
            dayLabels.put("1", this.YESTERDAY);
                        
            GregorianCalendar today = new GregorianCalendar();
            
            // Gets the day of the week, adds one then add five entries from there (It starts with sunday as 1)
            int day = today.get(Calendar.DAY_OF_WEEK);
            day--;
            for ( int i = 2; i < 7; i++ ) {           
                day--;
                
                // Check if resetting
                if ( day == 0 ) {
                    day = 7;
                }                
                
                dayLabels.put(Integer.toString(i),DAZE[day]);                 
            }
            
        }
        return dayLabels;
    }

    public void setDayLabels(HashMap<String, String> dayLabels) {
        this.dayLabels = dayLabels;
    }

    private DateMidnight getReportStartTime() {
        DateMidnight local = new DateTime().minusDays(30).toDateMidnight();
      
        if (            teamCommonBean.getDurationBean().getDuration().equals(Duration.ONEDAY) ) {
            return new DateTime().minusDays(Duration.ONEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.TWODAY) ) {
            return new DateTime().minusDays(Duration.TWODAY.getNumberOfDays()).toDateMidnight();
                
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.THREEDAY) ) {
            return new DateTime().minusDays(Duration.THREEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.FOURDAY) ) {            
            return new DateTime().minusDays(Duration.FOURDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.FIVEDAY) ) {
            return new DateTime().minusDays(Duration.FIVEDAY.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.SIXDAY) ) {
            return new DateTime().minusDays(Duration.SIXDAY.getNumberOfDays()).toDateMidnight(); 
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.SEVENDAY) ) {
            return new DateTime().minusDays(Duration.SEVENDAY.getNumberOfDays()).toDateMidnight();                  
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.DAYS) ) {
            return new DateTime().minusDays(Duration.DAYS.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.THREE) ) {
            return new DateTime().minusDays(Duration.THREE.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.SIX) ) {
            return new DateTime().minusDays(Duration.SIX.getNumberOfDays()).toDateMidnight();
            
        } else if (     teamCommonBean.getDurationBean().getDuration().equals(Duration.TWELVE) ) {
            return new DateTime().minusDays(Duration.TWELVE.getNumberOfDays()).toDateMidnight();
        }
        
        
        return local;
    }
    
    private void loadScoreStyles() {
        for ( DriverVehicleScoreWrapper dvsw: driverStatistics) {
            Integer anotherNumber = dvsw.getScore().getOverall().intValue();
            dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
                    anotherNumber, ScoreBoxSizes.SMALL));
        }
    }
}
