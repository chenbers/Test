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
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class DriverReportBean extends BaseReportBean   
{
    private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    //driversData is the ONE read from the db, driverData is what is displayed
    private List <DriverReportItem> driversData = new ArrayList<DriverReportItem>(); 
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    
            static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> driverColumns;
    private Vector tmpColumns = new Vector();    
    
    private TablePreference tablePref;
    
    private ScoreDAO scoreDAO;
    private TablePreferenceDAO tablePreferenceDAO;
   
    private DriverReportItem drt = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "driverreport_";
    
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
        AVAILABLE_COLUMNS.add("employeeID");
        AVAILABLE_COLUMNS.add("employee");
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("milesDriven");
        AVAILABLE_COLUMNS.add("overall");
        AVAILABLE_COLUMNS.add("speed");
        AVAILABLE_COLUMNS.add("style");
        AVAILABLE_COLUMNS.add("seatBelt");
    }
    
    public DriverReportBean() 
    {
        super();
    }
    
    public void init() 
    {          
        searchFor = checkForRequestMap();        
        this.driversData = 
            scoreDAO.getDriverReportData(            
                    getUser().getPerson().getGroupID(),
                    Duration.TWELVE);
        
        //Bean creation could be from Reports selection or
        //  search on main menu. This accounts for a search
        //  from the main menu w/ never having been to the 
        //  Drivers report page.
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.driversData);
        }
    }
        

    public List<DriverReportItem> getDriverData()
    {   
        return this.driverData;
    }
    
    private void checkOnSearch() 
    {
        if ( (searchFor != null) && 
             (searchFor.trim().length() != 0) ) 
        {
            search();
        } else {
            loadResults(this.driversData);
        }
        
        maxCount = this.driverData.size();        
        resetCounts();        
    }

    public void setDriverData(List<DriverReportItem> drvrData)
    {
        this.driverData = drvrData;
    }
    
    public void search() 
    {     
        //Search by last name             
        if ( this.searchFor.trim().length() != 0 ) {
            String trimmedSearch = this.searchFor.trim();            
            List <DriverReportItem> matchedDrivers = new ArrayList<DriverReportItem>();    
            
            for ( int i = 0; i < this.driversData.size(); i++ ) {
                DriverReportItem d = this.driversData.get(i);
                Person p = d.getDriver().getPerson();
                
                //Fuzzy
                if ( p != null ) {   
                    String lowerCaseLast = 
                        p.getLast().toLowerCase();
                    int index1 = 
                        lowerCaseLast.indexOf(trimmedSearch);                    
                    if (index1 != -1) {                        
                        matchedDrivers.add(d);
                    }
                }
            }
            
            loadResults(matchedDrivers);             
            this.maxCount = matchedDrivers.size();
            
        //Nothing entered, show them all
        } else {
            loadResults(this.driversData);
            this.maxCount = this.driverData.size();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <DriverReportItem> drvsData)
    {
        if ( this.driverData.size() > 0 ) {
            this.driverData.clear();
        }
       
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        for ( DriverReportItem d: drvsData ) {
            drt = d;   
            setStyles();
            
            //Group name
            drt.setGroup(this.getGroupHierarchy().getGroup(d.getGroupID()).getName());
            
            //Where to go 
            drt.setGoTo(contextPath + "/secured/team.faces?groupID="+d.getGroupID());                      
            
            driverData.add(drt);            
        }
        
        this.maxCount = this.driverData.size();   
        resetCounts();            
    }
    
    private void resetCounts() 
    {        
        this.start = 1;
        
        //None found
        if ( this.driverData.size() < 1 ) {
            this.start = 0;
        }
        
        this.end = this.numRowsPerPg;
        
        //Fewer than a page
        if ( this.driverData.size() <= this.end ) {
            this.end = this.driverData.size();
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
            pref.getVisible().set(cnt, driverColumns.get(column).getVisible());
        }
        setTablePref(pref);
        tablePreferenceDAO.update(pref);
        
        //Update tmp
        Iterator it = this.driverColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.driverColumns.get(key);  

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
        Iterator it = this.driverColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.driverColumns.get(key);  
  
            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    this.driverColumns.get(key).setVisible(tc.getColValue());
                }
            }
        }

    }

    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.DRIVER_REPORT))
                {
                    setTablePref(pref);
                    return tablePref;
                }
            }
            tablePref = new TablePreference();
            tablePref.setUserID(getUser().getUserID());
            tablePref.setTableType(TableType.DRIVER_REPORT);
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
    
    public Map<String,TableColumn> getDriverColumns()    
    {
        if ( driverColumns == null ) {     
            List<Boolean> visibleList = getTablePref().getVisible();
            driverColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(COLUMN_LABEL_PREFIX+column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);
                    
                driverColumns.put(column, tableColumn);
                tmpColumns.add(new TempColumns(column,tableColumn.getVisible()));
                        
            }
        } 
        
        return driverColumns;
    }

    public void setDriverColumns(Map<String,  TableColumn> driverColumns)
    {
        this.driverColumns = driverColumns;
    }
    
    private void setStyles() 
    {
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        
        if ( drt.getOverallScore() != null ) {
            sb.setScore(drt.getOverallScore());
            drt.setStyleOverall(sb.getScoreStyle());
        }
        
        if ( drt.getSeatBeltScore() != null ) {
            sb.setScore(drt.getSeatBeltScore());
            drt.setStyleSeatBelt(sb.getScoreStyle());
        }
        
        if ( drt.getSpeedScore() != null ) {
            sb.setScore(drt.getSpeedScore());
            drt.setStyleSpeed(sb.getScoreStyle());
        }
        
        if ( drt.getStyleScore() != null ) {
            sb.setScore(drt.getStyleScore());
            drt.setStyleStyle(sb.getScoreStyle());
        }
        
    }
    
    public void scrollerListener(DataScrollerEvent se)     
    {               
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
            loadResults(this.driversData);
        }   
        
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

}
