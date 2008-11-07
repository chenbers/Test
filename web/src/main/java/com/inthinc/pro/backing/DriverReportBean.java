package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.DriverReportItem;

public class DriverReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    private static final List<String> AVAILABLE_COLUMNS;
    private Map<String, Boolean> driverColumns = new HashMap<String, Boolean>();
    
    private DriverReportItem drt = null;
    
    private Integer numRowsPerPg = 2;
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = null;

    
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

//Replace this with DAO for a "search" for all drivers for a given logged-in user 
        initData();
        
        for ( int i = 0; i < DriverReportBean.AVAILABLE_COLUMNS.size(); i++ ) {
            this.driverColumns.put(DriverReportBean.AVAILABLE_COLUMNS.get(i),true);
        }
    }
    
    private void initData() {
        drt = new DriverReportItem();
        drt.setEmployee("John Doe");
        drt.setEmployeeID(123456);
        drt.setGroup("North");
        drt.setMilesDriven(202114);
        drt.setOverallScore(43);
        drt.setSeatBeltScore(22);
        drt.setSpeedScore(12);
        drt.setStyleScore(34);
        setStyles();
        drt.setVehicleID("AE-114");
        driverData.add(drt);

        drt = new DriverReportItem();        
        drt.setEmployee("Mary Doe");
        drt.setEmployeeID(64321);
        drt.setGroup("South");
        drt.setMilesDriven(111114);
        drt.setOverallScore(23);
        drt.setSeatBeltScore(42);
        drt.setSpeedScore(42);
        drt.setStyleScore(14);
        setStyles();
        drt.setVehicleID("DD-432");
        driverData.add(drt);

        drt = new DriverReportItem();
        drt.setEmployee("Hyrum Doe");
        drt.setEmployeeID(45631);
        drt.setGroup("Central");
        drt.setMilesDriven(2114);
        drt.setOverallScore(22);               
        drt.setSeatBeltScore(15);
        drt.setSpeedScore(49);
        drt.setStyleScore(33);
        setStyles();
        drt.setVehicleID("JKD-324");
        driverData.add(drt);

        drt = new DriverReportItem();
        drt.setEmployee("Frank Zappa");
        drt.setEmployeeID(666666);
        drt.setGroup("East");
        drt.setMilesDriven(224561114);
        drt.setOverallScore(11);               
        drt.setSeatBeltScore(45);
        drt.setSpeedScore(29);
        drt.setStyleScore(50);
        setStyles();
        drt.setVehicleID("FZ-109");
        driverData.add(drt);     

        drt = new DriverReportItem();
        drt.setEmployee("Onemore PieceOfData");
        drt.setEmployeeID(99999);
        drt.setGroup("West");
        drt.setMilesDriven(4561114);
        drt.setOverallScore(31);               
        drt.setSeatBeltScore(15);
        drt.setSpeedScore(23);
        drt.setStyleScore(40);
        setStyles();
        drt.setVehicleID("ZZ-0999");
        driverData.add(drt);     
        
        maxCount = driverData.size();
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
        
        //Test search reset
        logger.debug("searching for: " + this.searchFor);
        
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
        
        //Reset count parameters
        this.start = 1;
        this.end = this.numRowsPerPg;
        if ( this.driverData.size() <= this.end ) {
            this.end = this.driverData.size();
        }        
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


    public Integer getMaxCount()
    {
        return maxCount;
    }


    public void setMaxCount(Integer maxCount)
    {
        logger.debug("maxCount is: " + this.maxCount);
        this.maxCount = maxCount;
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
            this.end = this.start + ( this.end - this.driverData.size() ) - 1;
        }
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
}
