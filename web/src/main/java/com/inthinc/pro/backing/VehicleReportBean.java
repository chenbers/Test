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
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;

public class VehicleReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    private static final List<String> AVAILABLE_COLUMNS;
    private Map<String, Boolean> vehicleColumns = new HashMap<String, Boolean>();
    
    private VehicleDAO vehicleDAO;
    private ScoreDAO scoreDAO;
    
    private VehicleReportItem vrt = null;
    
    private Integer numRowsPerPg = 25;
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = null;

    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("makeModelYear");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("milesDriven");
        AVAILABLE_COLUMNS.add("overall");
        AVAILABLE_COLUMNS.add("speed");
        AVAILABLE_COLUMNS.add("style");
    }
    
    public void init() {               
        initData();
        
//--->Replace this with DAO for a "load" of the preferences for this particular table        
        for ( int i = 0; i < VehicleReportBean.AVAILABLE_COLUMNS.size(); i++ ) {
            this.vehicleColumns.put(VehicleReportBean.AVAILABLE_COLUMNS.get(i),true);
        }
    }
    
    private void initData() {
        List <Vehicle> vehiclesData = new ArrayList<Vehicle>();
        vehiclesData = vehicleDAO.getVehiclesInGroupHierarchy(
                getUser().getPerson().getGroupID());
     
        Vehicle v = null;
        
        Driver d = new Driver();
        Person p = new Person();
        p.setFirst("Need");
        p.setLast("Data");
        d.setPerson(p);
       
        ScoreableEntity s = null;        
        vrt = new VehicleReportItem();
                
        for ( int i = 0; i < vehiclesData.size(); i++ ) {
            v = (Vehicle)vehiclesData.get(i);            
            
            //Vehicle
            vrt = new VehicleReportItem();
            vrt.setVehicleID(v.getVehicleID());
            vrt.setMakeModelYear(v.getMake() + "/" + v.getModel() + "/" + v.getYear());         
            
            //Scores, full year
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(
                    endDate, 
                    Duration.TWELVE.getNumberOfDays());                                            
            s = scoreDAO.getAverageScoreByType(v.getGroupID(),startDate,endDate,ScoreType.SCORE_OVERALL);
            vrt.setOverallScore(s.getScore());
            s = scoreDAO.getAverageScoreByType(v.getGroupID(),startDate,endDate,ScoreType.SCORE_SPEEDING);
            vrt.setSpeedScore(s.getScore());
            s = scoreDAO.getAverageScoreByType(v.getGroupID(),startDate,endDate,ScoreType.SCORE_DRIVING_STYLE);
            vrt.setStyleScore(s.getScore());
            setStyles();
            
            //Needed
            vrt.setGroup("North");  
            vrt.setDriver(d);
            vrt.setMilesDriven(202114);
            
            vehicleData.add(vrt);            
        }        
        
        maxCount = vehicleData.size();
        resetCounts();
    }
        

    public List<VehicleReportItem> getVehicleData()
    {
        logger.debug("getting");   
        if ( vehicleData.size() > 0 ) {
            return vehicleData;
        } else {
            return new ArrayList<VehicleReportItem>();
        }
    }

    public void setDriverData(List<VehicleReportItem> vehicleData)
    {
        this.vehicleData = vehicleData;
    }
    
    public void search() {     
        logger.debug("searching");
        
        Driver d = new Driver();
        Person p = new Person();
        p.setFirst("Need");
        p.setLast("Data");
        d.setPerson(p);
        
        if ( this.vehicleData.size() > 0 ) {
            this.vehicleData.clear();
        }
//--->Replace this with DAO for searching
        //Test search reset                
        if ( this.searchFor.trim().length() != 0 ) {
            vrt = new VehicleReportItem();
            vrt.setGroup("Hidden");
            vrt.setVehicleID(123456);
            vrt.setMakeModelYear("Studebaker/S20/1908");
            vrt.setDriver(d);
            vrt.setMilesDriven(190833);
            vrt.setOverallScore(34);
            vrt.setSpeedScore(21);
            vrt.setStyleScore(45);
            setStyles();                
            vehicleData.add(vrt);
            
            this.maxCount = vehicleData.size();
        } else {
            initData();
        }
        
        resetCounts();       
    }
    
    private void resetCounts() {
        this.start = 1;
        this.end = this.numRowsPerPg;
        if ( this.vehicleData.size() <= this.end ) {
            this.end = this.vehicleData.size();
        } 
    }
    
    public void saveColumns() {  
        //In here for saving the column preferences 
        logger.debug("save columns");
    }
    
    public Map<String, Boolean> getVehicleColumns()    
    {
        //Need to do the check to prevent odd access behavior
        if ( vehicleColumns.size() > 0 ) {     
            return vehicleColumns;
        } else {
            return new HashMap<String, Boolean>();
        }
    }

    public void setVehicleColumns(Map<String, Boolean> vehicleColumns)
    {
        this.vehicleColumns = vehicleColumns;
    }
    
    private void setStyles() {
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        
        sb.setScore(vrt.getOverallScore());
        vrt.setStyleOverall(sb.getScoreStyle());
       
        sb.setScore(vrt.getSpeedScore());
        vrt.setStyleSpeed(sb.getScoreStyle());
        
        sb.setScore(vrt.getStyleScore());
        vrt.setStyleStyle(sb.getScoreStyle());
        
    }
    
    public void scrollerListener(DataScrollerEvent se)     
    {        
        logger.debug("scoll event page: " + se.getPage() + 
                " old " + se.getOldScrolVal() + " new " + se.getNewScrolVal() +
                " total " + this.vehicleData.size());
        
        this.start = (se.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (se.getPage())*this.numRowsPerPg;
        //Partial page
        if ( this.end > this.vehicleData.size() ) {
            this.end = this.vehicleData.size();
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

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

}
