package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCriteria;

public class DriverReportBean extends BaseReportBean<DriverReportItem> implements PersonChangeListener
{
    
    private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    //driversData is the ONE read from the db, driverData is what is displayed
    private List <DriverReportItem> driversData = new ArrayList<DriverReportItem>(); 
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    
    private ScoreDAO scoreDAO;
   
    private DriverReportItem drt = null;
    
    private final static String COLUMN_LABEL_PREFIX = "driverReports_";
    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_empid");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("milesDriven");
        AVAILABLE_COLUMNS.add("overallScore");
        AVAILABLE_COLUMNS.add("speedScore");
        AVAILABLE_COLUMNS.add("styleScore");
        AVAILABLE_COLUMNS.add("seatBeltScore");
    }
    
    public DriverReportBean() 
    {
        super();
    }

    @Override
    protected void loadDBData()
    {
        this.driversData = 
            scoreDAO.getDriverReportData(            
                    getUser().getGroupID(),
                    Duration.TWELVE);
        //Once loaded, set the group name NOW so it can be searchable IMMEDIATELY
        for ( DriverReportItem dri : this.driversData ) {
            dri.setGroup(this.getGroupHierarchy().getGroup(dri.getGroupID()).getName());
        }
    }

    @Override
    public void personListChanged()
    {
        loadDBData();
        search();
    }

    public List<DriverReportItem> getDriverData()
    {   
        return this.driverData;
    }

    public void setDriverData(List<DriverReportItem> drvrData)
    {
        this.driverData = drvrData;
    }

    @Override
    protected List<DriverReportItem> getDBData()
    {
        return driversData;
    }

    @Override
    protected List<DriverReportItem> getDisplayData()
    {
        return driverData;
    }

    @Override
    protected void loadResults(List <DriverReportItem> drvsData)
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
    
    private void setStyles() 
    {      
        drt.setStyleOverall(ScoreBox.GetStyleFromScore(drt.getOverallScore(), ScoreBoxSizes.SMALL));
   
        drt.setStyleSeatBelt(ScoreBox.GetStyleFromScore(drt.getSeatBeltScore(), ScoreBoxSizes.SMALL));      
  
        drt.setStyleSpeed(ScoreBox.GetStyleFromScore(drt.getSpeedScore(), ScoreBoxSizes.SMALL));
 
        drt.setStyleStyle(ScoreBox.GetStyleFromScore(drt.getStyleScore(), ScoreBoxSizes.SMALL));
       
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
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
        return TableType.DRIVER_REPORT;
    }
    
    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }
}
