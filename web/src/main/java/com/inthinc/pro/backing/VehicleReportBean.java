package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCriteria;

public class VehicleReportBean extends BaseReportBean<VehicleReportItem>
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    //vehiclesData is the ONE read from the db, vehicleData is what is displayed
    private List <VehicleReportItem> vehiclesData = new ArrayList<VehicleReportItem>();
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    
    private ScoreDAO scoreDAO;
    
    private VehicleReportItem vrt = null;
    
    private final static String COLUMN_LABEL_PREFIX = "vehicleReports_";
    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("makeModelYear");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("milesDriven");
        AVAILABLE_COLUMNS.add("overallScore");
        AVAILABLE_COLUMNS.add("speedScore");
        AVAILABLE_COLUMNS.add("styleScore");
    }
    
    public VehicleReportBean()
    {
        super();
    }
    
    public void init() 
    {   
        setTablePref(new TablePref(this));

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

    public void setVehicleData(List<VehicleReportItem> vehicleData)
    {
        this.vehicleData = vehicleData;
    }

    @Override
    protected List<VehicleReportItem> getDBData()
    {
        return vehiclesData;
    }

    @Override
    protected List<VehicleReportItem> getDisplayData()
    {
        return vehicleData;
    }

    @Override
    protected void loadResults(List <VehicleReportItem> vehicData) 
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

    private void setStyles() 
    {
        
        vrt.setStyleOverall(ScoreBox.GetStyleFromScore(vrt.getOverallScore(), ScoreBoxSizes.SMALL));
 
        vrt.setStyleSpeed(ScoreBox.GetStyleFromScore(vrt.getSpeedScore(), ScoreBoxSizes.SMALL));
       
        vrt.setStyleStyle(ScoreBox.GetStyleFromScore(vrt.getStyleScore(), ScoreBoxSizes.SMALL));
   
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }
    
    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),null);
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.VEHICLE_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(vehicleData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
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
    public TableType getTableType()
    {
        return TableType.VEHICLE_REPORT;
    }
}
