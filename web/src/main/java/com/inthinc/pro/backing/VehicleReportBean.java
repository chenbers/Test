package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.util.MessageUtil;

public class VehicleReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    //vehiclesData is the ONE read from the db, vehicleData is what is displayed
    private List <Vehicle> vehiclesData = new ArrayList<Vehicle>();
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    
    private static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> vehicleColumns;
    
    private TablePreference tablePref;
    
    private VehicleDAO vehicleDAO;
    private ScoreDAO scoreDAO;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private VehicleReportItem vrt = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "vehiclereport_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";

    
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
        vehiclesData = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());
        loadResults(vehiclesData);
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
        Driver d = new Driver();
        Person p = new Person();
        p.setFirst("Need");
        p.setLast("Data");
        d.setPerson(p);
        
        if ( this.vehicleData.size() > 0 ) {
            this.vehicleData.clear();
        }
                      
        if ( this.searchFor.trim().length() != 0 ) {     
            try { 
                Integer id = Integer.parseInt(this.searchFor.trim());   
                String compareToID = Integer.toString(id.intValue());
                List <Vehicle> matchedVehicles = new ArrayList<Vehicle>();    
                
                for ( int i = 0; i < vehiclesData.size(); i++ ) {
                    Vehicle v = (Vehicle)vehiclesData.get(i);                   
                    String localID = Integer.toString(v.getVehicleID());
                    
                    //Fuzzy
                    int index1 = localID.indexOf(compareToID);                    
                    if (index1 != -1) {                        
                        matchedVehicles.add(v);
                    }
                }
                
                loadResults(matchedVehicles);             
                this.maxCount = matchedVehicles.size();
                
            //Looking for non-integer error for input search string
            } catch (Exception e) {}

        } else {
            loadResults(vehiclesData);
            this.maxCount = vehiclesData.size();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <Vehicle> vehiclesData) 
    {
        Vehicle v = null;
        Group g = null;        
        Driver d = new Driver();        
       
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
                        
            //Group
            g = groupDAO.getGroupByID(v.getGroupID());
            vrt.setGroup(g.getName());
            vrt.setGroupID(g.getGroupID());
            
            //Driver
            if ( v.getDriverID() != null ) {
                d = driverDAO.getDriverByID(v.getDriverID());
            //None assigned
            } else {
                Person p = new Person();
                p.setFirst("None");
                p.setLast("Assigned");
                d.setPerson(p);
            }
            vrt.setDriver(d);
                        
            //Needed                    
            vrt.setMilesDriven(202114);
            
            vehicleData.add(vrt);            
        }     
    }
    
    private void resetCounts() {
        this.start = 1;
        
        //None found
        if ( this.vehicleData.size() < 1 ) {
            this.start = 0;
        }
        
        this.end = this.numRowsPerPg;
        
        //Fewer than a page
        if ( this.vehicleData.size() <= this.end ) {
            this.end = this.vehicleData.size();
        } else if ( this.start == 0 ) {
            this.end = 0;
        }
    }
    
    public void saveColumns() {  
        //In here for saving the column preferences 
        logger.debug("save columns");
        
        TablePreference pref = getTablePref();
        int cnt = 0;
        for (String column : AVAILABLE_COLUMNS)
        {
            pref.getVisible().set(cnt, vehicleColumns.get(column).getVisible());
        }
        setTablePref(pref);
     // TODO: currently throws a not implemented exception        
//      tablePreferenceDAO.update(pref);        
    }
    
    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.VEHICLE_REPORT))
                {
                    setTablePref(pref);
                    return tablePref;
                }
            }
            tablePref = new TablePreference();
            tablePref.setUserID(getUser().getUserID());
            tablePref.setTableType(TableType.VEHICLE_REPORT);
            List<Boolean>visibleList = new ArrayList<Boolean>();
            for (String column : AVAILABLE_COLUMNS)
            {
                visibleList.add(new Boolean(true));
            }
            tablePref.setVisible(visibleList);
            
        }
        
        
        return tablePref;
    }
    
    public void setTablePref(TablePreference tablePref)
    {
        this.tablePref = tablePref;
    }
    
    public Map<String,TableColumn> getVehicleColumns()    
    {
        //Need to do the check to prevent odd access behavior
        if ( vehicleColumns == null ) {     
            List<Boolean> visibleList = getTablePref().getVisible();
            vehicleColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(COLUMN_LABEL_PREFIX+column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);
                    
                vehicleColumns.put(column, tableColumn);
                        
            }
        } 
        
        return vehicleColumns;
    }

    public void setVehicleColumns(Map<String,  TableColumn> vehicleColumns)
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

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

}
