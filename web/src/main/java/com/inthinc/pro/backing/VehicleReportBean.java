package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class VehicleReportBean extends BaseReportBean implements TablePrefOptions
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    //vehiclesData is the ONE read from the db, vehicleData is what is displayed
    private List <VehicleReportItem> vehiclesData = new ArrayList<VehicleReportItem>();
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    
    private ScoreDAO scoreDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private VehicleReportItem vrt = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "vehicleReports_";
    
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
        tablePref = new TablePref(this);

        searchFor = checkForRequestMap();        
        vehiclesData = 
            scoreDAO.getVehicleReportData(
                    getUser().getGroupID(),
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
                    String vehicleID = v.getVehicle().getName().trim().toLowerCase();
                    
                    int index1;
                    int index2;
                    int index3;
                    int index4;
                                                          
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
                        (index3 != -1) ) {                                      
                        matchedVehicles.add(v);                    
                    }
                    
                    // driver name                    
                    index4 = v.getDriver().getPerson().getFullName().trim().toLowerCase().indexOf(trimmedSearch);                                  
                    if ((index1 == -1) &&
                        (index2 == -1) &&
                        (index3 == -1) &&
                        (index4 != -1) ) {                                 
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

    private void setStyles() {
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        
        if ( vrt.getOverallScore() != null ) {
            vrt.setStyleOverall(ScoreBox.GetStyleFromScore(floatToInteger(vrt.getOverallScore()), ScoreBoxSizes.SMALL));
        } 
        
        if ( vrt.getSpeedScore() != null ) {
            vrt.setStyleSpeed(ScoreBox.GetStyleFromScore(floatToInteger(vrt.getSpeedScore()), ScoreBoxSizes.SMALL));
        }
        
        if ( vrt.getStyleScore() != null ) {
            vrt.setStyleStyle(ScoreBox.GetStyleFromScore(floatToInteger(vrt.getStyleScore()), ScoreBoxSizes.SMALL));
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
    
    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),null);
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
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
            loadResults(this.vehiclesData);
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
        return TableType.VEHICLE_REPORT;
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
}
