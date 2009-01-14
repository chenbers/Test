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
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class DriverReportBean extends BaseReportBean implements TablePrefOptions
{
    private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    //driversData is the ONE read from the db, driverData is what is displayed
    private List <DriverReportItem> driversData = new ArrayList<DriverReportItem>(); 
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    
    private ScoreDAO scoreDAO;
    private TablePreferenceDAO tablePreferenceDAO;
   
    private DriverReportItem drt = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "driverReports_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String secret = "";

    private TablePref tablePref;
    
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
        tablePref = new TablePref(this);

        searchFor = checkForRequestMap(); 
        
        this.driversData = 
            scoreDAO.getDriverReportData(            
                    getUser().getGroupID(),
                    Duration.TWELVE);
        
        //Bean creation could be from Reports selection or
        //  search on main menu. This accounts for a search
        //  from the main menu w/ never having been to the 
        //  Drivers report page.
        if (  super.isMainMenu() ) {  
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
        if ( this.driverData.size() > 0 ) {
            this.driverData.clear();
        }
        
        if ( this.searchFor.trim().length() != 0 ) {
            String trimmedSearch = this.searchFor.trim().toLowerCase();            
            List <DriverReportItem> matchedDrivers = new ArrayList<DriverReportItem>();    
            
            for ( DriverReportItem d: this.driversData ) {                
                Person p = d.getDriver().getPerson();
                
                int index1;
                int index2;
                int index3;
                                                
                if ( p != null ) {
                    
                    // first name
                    String lowerCaseFirst = 
                        p.getFirst().toLowerCase();
                    index1 = 
                        lowerCaseFirst.indexOf(trimmedSearch);                    
                    if (index1 != -1) {                        
                        matchedDrivers.add(d);
                    }
                
                    // last name
                    String lowerCaseLast = 
                        p.getLast().toLowerCase();
                    index2 = 
                        lowerCaseLast.indexOf(trimmedSearch);                    
                    if ((index1 == -1) && 
                        (index2 != -1)) {                        
                        matchedDrivers.add(d);
                    }
                    
                    // emp id
                    String lowerCaseEmployeeID = d.getDriver().getPerson().getEmpid();
                    index3 = 
                        lowerCaseEmployeeID.indexOf(trimmedSearch);                    
                    if ((index1 == -1) && 
                        (index2 == -1) &&
                        (index3 != -1) ) {                        
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
            
            //Driver
            drt.setDriver(d.getDriver());
            
            //Group name
            drt.setGroup(this.getGroupHierarchy().getGroup(d.getGroupID()).getName());
                        
            //Vehicle, none assigned
            if ( d.getVehicle() == null ) {
                Vehicle v = new Vehicle();                
                v.setName("None Assigned");
                drt.setVehicle(v);
            }  
            
            //Where to go - make sure you go to the correct level            
            drt.setGoTo(contextPath + this.getGroupHierarchy().getGroupLevel(d.getGroupID()).getUrl() +
                    "?groupID="+d.getGroupID());                      
            
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
    
    private void setStyles() 
    {   
        if ( drt.getOverallScore() != null ) {            
            drt.setStyleOverall(ScoreBox.GetStyleFromScore(floatToInteger(drt.getOverallScore()), ScoreBoxSizes.SMALL));
        }
        
        if ( drt.getSeatBeltScore() != null ) {
            drt.setStyleSeatBelt(ScoreBox.GetStyleFromScore(floatToInteger(drt.getSeatBeltScore()), ScoreBoxSizes.SMALL));      
        }
        
        if ( drt.getSpeedScore() != null ) {
            drt.setStyleSpeed(ScoreBox.GetStyleFromScore(floatToInteger(drt.getSpeedScore()), ScoreBoxSizes.SMALL));
        }
        
        if ( drt.getStyleScore() != null ) {
            drt.setStyleStyle(ScoreBox.GetStyleFromScore(floatToInteger(drt.getStyleScore()), ScoreBoxSizes.SMALL));
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
        String searchForLocal = checkForRequestMap();
        String search = searchForLocal.toLowerCase().trim();
        if ( (search.length() != 0) && (!search.equalsIgnoreCase(this.searchFor)) ) 
        {
            this.searchFor = searchForLocal.toLowerCase().trim();
        }
              
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else if ( this.searchFor.trim().length() != 0 ) {
            checkOnSearch();
        } else {
            loadResults(this.driversData);
        }   
        
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.DRIVER_REPORT;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

    public TablePref getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref tablePref)
    {
        this.tablePref = tablePref;
    }
    
    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.DRIVER_REPORT,getGroupHierarchy().getTopGroup().getName(),null);
        reportCriteria.setMainDataset(driverData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.DRIVER_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(driverData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.DRIVER_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(driverData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }
}
