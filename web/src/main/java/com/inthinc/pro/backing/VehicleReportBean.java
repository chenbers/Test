package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class VehicleReportBean extends BaseReportBean
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    //vehiclesData is the ONE read from the db, vehicleData is what is displayed
    private List <VehicleReportItem> vehiclesData = new ArrayList<VehicleReportItem>();
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    
            static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> vehicleColumns;
    private Vector tmpColumns = new Vector(); 
    
    private TablePreference tablePref;
    
    private ScoreDAO scoreDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private VehicleReportItem vrt = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "vehiclereport_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String secret = "";
    
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
    
    public VehicleReportBean()
    {
        super();
    }
    
    public void init() 
    {   
        searchFor = checkForRequestMap();        
        vehiclesData = 
            scoreDAO.getVehicleReportData(
                    getUser().getPerson().getGroupID(),
                    Duration.TWELVE);
        
        //Bean creation could be from Reports selection or
        //  search on main menu. This accounts for a search
        //  from the main menu w/ never having been to the 
        //  Vehicles report page.
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.vehiclesData);
        }
    }
 
    
    public List<VehicleReportItem> getVehicleData()
    {        
        return vehicleData;
    }
    
    
    private void checkOnSearch() 
    {
        if ( (searchFor != null) && 
             (searchFor.trim().length() != 0) ) 
        {
            search();
        } else {
            loadResults(this.vehiclesData);
        }
        
        maxCount = this.vehicleData.size();        
        resetCounts();        
    }

    public void setDriverData(List<VehicleReportItem> vehicleData)
    {
        this.vehicleData = vehicleData;
    }
    
    public void search() 
    {                     
        if ( this.vehicleData.size() > 0 ) {
            this.vehicleData.clear();
        }
                      
        if ( this.searchFor.trim().length() != 0 ) {     
            try {                 
                String trimmedSearch = this.searchFor.trim().toLowerCase();
                List <VehicleReportItem> matchedVehicles = new ArrayList<VehicleReportItem>();    
                
                for ( int i = 0; i < vehiclesData.size(); i++ ) {
                    VehicleReportItem v = vehiclesData.get(i);                   
                    String vehicleID = Integer.toString(v.getVehicle().getVehicleID());
                    
                    int index1;
                    int index2;
                    int index3;
                                                          
                    // vehicle ID
                    index1 = vehicleID.indexOf(trimmedSearch);                    
                    if (index1 != -1) {                        
                        matchedVehicles.add(v);
                    }
                    
                    // make                    
                    index2 = v.getVehicle().getMake().toLowerCase().indexOf(trimmedSearch);                    
                    if ((index1 == -1) && 
                        (index2 != -1) ) {                        
                        matchedVehicles.add(v);
                    }
                    
                    // model                    
                    index3 = v.getVehicle().getModel().toLowerCase().indexOf(trimmedSearch);                    
                    if ((index1 == -1) && 
                        (index2 == -1) &&
                        (index3 != -1)) {                        
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
    
    private void loadResults(List <VehicleReportItem> vehicData) 
    {
        if ( this.vehicleData.size() > 0 ) {
            this.vehicleData.clear();
        }   
        
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                
        for ( VehicleReportItem v: vehicData ) {          
            vrt = v;
            setStyles();
                        
            //Group name
            vrt.setGroup(this.getGroupHierarchy().getGroup(v.getGroupID()).getName());
                        
            //Where to go - make sure you go to the correct level            
            vrt.setGoTo(contextPath + this.getGroupHierarchy().getGroupLevel(v.getGroupID()).getUrl() +
                    "?groupID="+v.getGroupID());   
            
            //Driver, none assigned
            if ( v.getDriver() == null ) {
                Driver d = new Driver();
                Person p = new Person();
                p.setFirst("None");
                p.setLast("Assigned");
                d.setPerson(p);
                vrt.setDriver(d);
            }            
            
            vehicleData.add(vrt);            
        }    
                
        this.maxCount = this.vehicleData.size();   
        resetCounts();            
    }
    
    private void resetCounts() 
    {
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
    
    public void saveColumns() 
    {  
        //To data store
        TablePreference pref = getTablePref();
        int cnt = 0;
        for (String column : AVAILABLE_COLUMNS)
        {
            pref.getVisible().set(cnt++, vehicleColumns.get(column).getVisible());
        }
        setTablePref(pref);
        tablePreferenceDAO.update(pref);
        
        //Update tmp
        Iterator it = this.vehicleColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.vehicleColumns.get(key);  

            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    Boolean b = ((TableColumn)value).getVisible();
                    tc.setColValue(b);
                }
            }
        }       
    }
    
    public void cancelColumns() 
    {        
        //Update live
        Iterator it = this.vehicleColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.vehicleColumns.get(key);  
  
            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    this.vehicleColumns.get(key).setVisible(tc.getColValue());
                }
            }
        }

    }    
    
    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {

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
            Integer tablePrefID = getTablePreferenceDAO().create(getUser().getUserID(), tablePref);
            tablePref.setTablePrefID(tablePrefID);
            setTablePref(tablePref);
            
            
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
                tmpColumns.add(new TempColumns(column,tableColumn.getVisible()));
                        
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
        
        if ( vrt.getOverallScore() != null ) {
            sb.setScore(vrt.getOverallScore());
            vrt.setStyleOverall(sb.getScoreStyle());
        } 
        
        if ( vrt.getSpeedScore() != null ) {
            sb.setScore(vrt.getSpeedScore());
            vrt.setStyleSpeed(sb.getScoreStyle());
        }
        
        if ( vrt.getStyleScore() != null ) {
            sb.setScore(vrt.getStyleScore());
            vrt.setStyleStyle(sb.getScoreStyle());
        }
        
    }
    
    public void scrollerListener(DataScrollerEvent se)     
    {              
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

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

    public String getSecret()
    {
        searchFor = checkForRequestMap();        
              
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.vehiclesData);
        }   
        
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }
}
