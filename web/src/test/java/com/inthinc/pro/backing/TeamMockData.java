package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public class TeamMockData {
    
    public static final Integer TEAM_GROUP_ID = 1;
    public static final Integer TEAM_PARENT_GROUP_ID = 1;
    public static final Integer DRIVER_ID = 1;
    public static final Integer ACCOUNT_ID = 1;
    public static final Integer SCORE_OVERALL = 45;
    public static final Integer SCORE_OVERALL_UT = 40;
    public static final Integer SCORE_NA = -1;
    
    public static final Integer TREND_SCORE_DEFAULT = 25;
    public static final Integer TREND_SCORE_TODAY = 28;
    public static final Integer TREND_SCORE_ONE_DAY_AGO = 29;
    public static final Integer TREND_SCORE_TWO_DAY_AGO = 30;
    public static final Integer TREND_SCORE_THREE_DAY_AGO = 31;
    public static final Integer TREND_SCORE_FOUR_DAY_AGO = 32;
    public static final Integer TREND_SCORE_FIVE_DAY_AGO = 33;
    
    public static final Integer TREND_SCORE_TODAY_UT = 40;
    public static final Integer TREND_SCORE_ONE_DAY_AGO_UT = 40;
    public static final Integer TREND_SCORE_TWO_DAY_AGO_UT= 40;
    public static final Integer TREND_SCORE_THREE_DAY_AGO_UT = 40;
    public static final Integer TREND_SCORE_FOUR_DAY_AGO_UT = 40;
    public static final Integer TREND_SCORE_FIVE_DAY_AGO_UT = 40;
    public static final Integer TREND_SCORE_MONTH_UT = 45;

    
    public static final Integer TREND_SCORE_MONTH = 45;
    
    public static final String PERSON_FIRST_NAME = "PfirstName";
    public static final String PERSON_LAST_NAME = "PlastName";
    
    public static final String GROUP_NAME = "Test_Group";
    
    // Scorable Entity list switch
    public enum SEListSwitch {
        DAY_30, // 30 day list
        MONTH,  // Month List
        EMPTY,  // Empty List
        NULL    // Null ;
    }
    
    private SEListSwitch listSwitch;
    
    public SEListSwitch getListSwitch() {
        return listSwitch;
    }

    public void setListSwitch(SEListSwitch listSwitch) {
        this.listSwitch = listSwitch;
    }

    public MockGroupReportDAO getMockGroupReportDAO() {
        return new MockGroupReportDAO();
    }

    private class MockGroupReportDAO implements GroupReportDAO {

        @Override
        public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScoresWithUserTimeZone(Integer groupID, Interval interval, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappersUT();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScoresWithUserTimeZone(Integer groupID, DateTime day, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappersUT();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getGroupTrendWrapperList();
        }

        @Override
        public Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }
        
    }
    
    public List<DriverVehicleScoreWrapper> getDriverVehicleScoreWrappers() {
        List<DriverVehicleScoreWrapper> retVal = new ArrayList<DriverVehicleScoreWrapper>();
        
        Person person = new Person();
        person.setAcctID(ACCOUNT_ID);
        person.setFirst(PERSON_FIRST_NAME);
        person.setLast(PERSON_LAST_NAME);
        
        Driver driver = new Driver();
        driver.setGroupID(TEAM_GROUP_ID);
        driver.setDriverID(DRIVER_ID);
        driver.setPerson(person);
        
        Score score = new Score();
        score.setOverall(SCORE_OVERALL);
        
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();
        dvsw.setDriver(driver);
        dvsw.setScore(score);
        
        retVal.add(dvsw);
        
        return retVal;
    }

    public List<DriverVehicleScoreWrapper> getDriverVehicleScoreWrappersUT() {
        List<DriverVehicleScoreWrapper> retVal = getDriverVehicleScoreWrappers();
        retVal.get(0).getScore().setOverall(SCORE_OVERALL_UT);
        return retVal;
    }
    
    public List<GroupTrendWrapper> getGroupTrendWrapperList() {
        List<GroupTrendWrapper> retVal = new ArrayList<GroupTrendWrapper>();
        
        GroupTrendWrapper groupTrendWrapper = new GroupTrendWrapper();
        groupTrendWrapper.setGroup(this.getGroup());
        
        if(this.listSwitch == SEListSwitch.DAY_30) {
            groupTrendWrapper.setTrendList(getTrendListByDay());
        } else if (this.listSwitch == SEListSwitch.MONTH) {
            groupTrendWrapper.setTrendList(getTrendListByMonth());
        } else if (this.listSwitch == SEListSwitch.EMPTY){
            groupTrendWrapper.setTrendList( new ArrayList<Trend>());
        }  else if (this.listSwitch == SEListSwitch.NULL){
            groupTrendWrapper.setTrendList(null);
        }
        
        retVal.add(groupTrendWrapper);
        
        return retVal;
    }
    
    public List<Trend> getTrendListByDay() {
        List<Trend> retVal = new ArrayList<Trend>();
        
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withZone(DateTimeZone.UTC);
        dateTime = dateTime.minusDays(30);
        
        for(int i = 0; i < 30; i ++) {
            Trend trend = new Trend();
            if( i == 24 ){
                trend.setOverall(TREND_SCORE_FIVE_DAY_AGO);
            } else if ( i == 25 ){
                trend.setOverall(TREND_SCORE_FOUR_DAY_AGO);
            } else if ( i == 26 ){
                trend.setOverall(TREND_SCORE_THREE_DAY_AGO);
            } else if ( i == 27 ){
                trend.setOverall(TREND_SCORE_TWO_DAY_AGO);
            } else if ( i == 28 ){
                trend.setOverall(TREND_SCORE_ONE_DAY_AGO);
            }  else if ( i == 29 ){
                trend.setOverall(TREND_SCORE_TODAY);
            } else {
                trend.setOverall(TREND_SCORE_DEFAULT);
            }
            
            dateTime = dateTime.plusDays(1);
            
            trend.setEndingDate(dateTime.toDate());
            
            retVal.add(trend);
        }
        
        return retVal;
    }
    
    public List<Trend> getTrendListByMonth() {
        List<Trend> retVal = new ArrayList<Trend>();
        
        DateTime dateTime = new DateTime();
        dateTime = dateTime.dayOfMonth().withMinimumValue();
        dateTime = dateTime.minusMonths(4);
        
        for(int i = 0; i < 3; i++) {
            Trend trend = new Trend();
            if( i == 2 ){
                trend.setOverall(TREND_SCORE_MONTH);
            } else {
                trend.setOverall(TREND_SCORE_DEFAULT);
            }
            
            dateTime = dateTime.plusMonths(1);
            
            trend.setEndingDate(dateTime.toDate());
            
            retVal.add(trend);
        }
        
        return retVal;
    }
    
    public Group getGroup() {
        Group retVal = new Group(TEAM_GROUP_ID, ACCOUNT_ID, GROUP_NAME, TEAM_PARENT_GROUP_ID);
        
        return retVal;
    }
}
