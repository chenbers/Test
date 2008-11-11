package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.ScoreType;

public class DriverReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    private static final List<String> AVAILABLE_COLUMNS;
    private Map<String, Boolean> driverColumns = new HashMap<String, Boolean>();
    
    private DriverDAO driverDAO;
    private ScoreDAO scoreDAO;
    private GroupDAO groupDAO;
   
    private DriverReportItem drt = null;
    
    private Integer numRowsPerPg = 25;
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";

    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("employeeID");
        AVAILABLE_COLUMNS.add("employee");
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("milesDriven");
        AVAILABLE_COLUMNS.add("overall");
        AVAILABLE_COLUMNS.add("speed");
        AVAILABLE_COLUMNS.add("style");
        AVAILABLE_COLUMNS.add("seatBelt");
    }
    
    public void init() {               
        initData();
        
//--->Replace this with DAO for a "load" of the preferences for this particular table        
        for ( int i = 0; i < DriverReportBean.AVAILABLE_COLUMNS.size(); i++ ) {
            this.driverColumns.put(DriverReportBean.AVAILABLE_COLUMNS.get(i),true);
        }
    }
    
    private void initData() {
        List <Driver> driversData = new ArrayList<Driver>();        
        driversData = driverDAO.getAllDrivers(getUser().getPerson().getGroupID());        
        loadResults(driversData);     
        maxCount = driverData.size();        
        resetCounts();
    }
        

    public List<DriverReportItem> getDriverData()
    {
        logger.debug("getting");   
        if ( driverData.size() > 0 ) {
            return driverData;
        } else {
            return new ArrayList<DriverReportItem>();
        }
    }

    public void setDriverData(List<DriverReportItem> driverData)
    {
        this.driverData = driverData;
    }
    
    public void search() {     
        logger.debug("searching");
        
        if ( this.driverData.size() > 0 ) {
            this.driverData.clear();
        }
//--->Replace this with DAO for searching
        //Test search reset                
        if ( this.searchFor.trim().length() != 0 ) {
            drt = new DriverReportItem();
            drt.setEmployee("Ivebeen Searchedfor");
            drt.setEmployeeID(123456789);
            drt.setGroup("Hidden");
            drt.setMilesDriven(112233);
            drt.setOverallScore(12);
            drt.setSeatBeltScore(23);
            drt.setSpeedScore(34);
            drt.setStyleScore(45);
            setStyles();
            drt.setVehicleID("AA-123");                
            driverData.add(drt);
            
            this.maxCount = driverData.size();
        } else {
            initData();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <Driver> driversData)
    {
        Driver d = null;
        Person p = null;
        ScoreableEntity s = null;
        Group g = null;
       
        for ( int i = 0; i < driversData.size(); i++ ) {
            d = (Driver)driversData.get(i);
            p = d.getPerson();
            
            //Employee and driver
            drt = new DriverReportItem();
            drt.setEmployee(p.getFirst() + " " + p.getLast());
            drt.setEmployeeID(new Integer(p.getEmpid()));
            drt.setDriver(d);
            
            //Scores, full year
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(
                    endDate, 
                    Duration.TWELVE.getNumberOfDays());                                            
            s = scoreDAO.getAverageScoreByType(d.getGroupID(),startDate,endDate,ScoreType.SCORE_OVERALL);
            drt.setOverallScore(s.getScore());
            s = scoreDAO.getAverageScoreByType(d.getGroupID(),startDate,endDate,ScoreType.SCORE_SEATBELT);
            drt.setSeatBeltScore(s.getScore());
            s = scoreDAO.getAverageScoreByType(d.getGroupID(),startDate,endDate,ScoreType.SCORE_SPEEDING);
            drt.setSpeedScore(s.getScore());
            s = scoreDAO.getAverageScoreByType(d.getGroupID(),startDate,endDate,ScoreType.SCORE_DRIVING_STYLE);
            drt.setStyleScore(s.getScore());
            setStyles();
            
            //Group
            g = groupDAO.getGroupByID(d.getGroupID());
            drt.setGroup(g.getName());
            drt.setGroupID(g.getGroupID());
            
            //Needed            
            drt.setMilesDriven(202114);
            drt.setVehicleID("AE-114");           
            
            driverData.add(drt);            
        }
    }
    
    private void resetCounts() {
        this.start = 1;
        this.end = this.numRowsPerPg;
        if ( this.driverData.size() <= this.end ) {
            this.end = this.driverData.size();
        } 
    }
    
    public void saveColumns() {  
        //In here for saving the column preferences 
        logger.debug("save columns");
    }
    
    public Map<String, Boolean> getDriverColumns()    
    {
        //Need to do the check to prevent odd access behavior
        if ( driverColumns.size() > 0 ) {     
            return driverColumns;
        } else {
            return new HashMap<String, Boolean>();
        }
    }

    public void setDriverColumns(Map<String, Boolean> driverColumns)
    {
        this.driverColumns = driverColumns;
    }
    
    private void setStyles() {
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        
        sb.setScore(drt.getOverallScore());
        drt.setStyleOverall(sb.getScoreStyle());
        
        sb.setScore(drt.getSeatBeltScore());
        drt.setStyleSeatBelt(sb.getScoreStyle());
        
        sb.setScore(drt.getSpeedScore());
        drt.setStyleSpeed(sb.getScoreStyle());
        
        sb.setScore(drt.getStyleScore());
        drt.setStyleStyle(sb.getScoreStyle());
        
    }
    
    public void scrollerListener(DataScrollerEvent se)     
    {        
        logger.debug("scoll event page: " + se.getPage() + 
                " old " + se.getOldScrolVal() + " new " + se.getNewScrolVal() +
                " total " + this.driverData.size());
        
        this.start = (se.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (se.getPage())*this.numRowsPerPg;
        //Partial page
        if ( this.end > this.driverData.size() ) {
            this.end = this.driverData.size();
        }
    }  

    
    public Integer getMaxCount()
    {
        return maxCount;
    }


    public void setMaxCount(Integer maxCount)
    {        
        this.maxCount = maxCount;
    }


    public Integer getStart()
    {
        return start;
    }


    public void setStart(Integer start)
    {
        this.start = start;
    }


    public Integer getEnd()
    {
        return end;
    }


    public void setEnd(Integer end)
    {
        this.end = end;
    }


    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }


    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }


    public String getSearchFor()
    {
        return searchFor;
    }


    public void setSearchFor(String searchFor)
    {
        this.searchFor = searchFor;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

}
