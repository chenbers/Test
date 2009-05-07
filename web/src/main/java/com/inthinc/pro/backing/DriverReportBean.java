package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.MessageUtil;

public class DriverReportBean extends BaseReportBean<DriverReportItem> implements PersonChangeListener
{
    

	private static final Logger logger = Logger.getLogger(DriverReportBean.class);
    
    //driversData is the ONE read from the db, driverData is what is displayed
    private List <DriverReportItem> driversData = new ArrayList<DriverReportItem>(); 
    private List <DriverReportItem> driverData = new ArrayList<DriverReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 2, 3, 4, 5, 7, 8 };  
    
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
            if(dri.getVehicle() == null){
                Vehicle v = new Vehicle();                
                v.setName(MessageUtil.getMessageString("reports_none_assigned"));
                dri.setVehicle(v);
            }
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
    protected void filterResults(List<DriverReportItem> filteredTableData)
    {
        
        //Filter if search is based on group.
        if (!getEffectiveGroupId().equals(getUser().getGroupID()))
        {
            filteredTableData.clear();
            
            for (DriverReportItem item : this.driversData)
            {
                if (item.getGroupID().equals(getEffectiveGroupId()))
                {
                    filteredTableData.add(item);
                }
            }
        }
        
    }

    @Override
    protected void loadResults(List <DriverReportItem> drvsData)
    {
//        if ( this.driverData.size() > 0 ) {
//            this.driverData.clear();
//        }
    	driverData = new ArrayList<DriverReportItem>();
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        for ( DriverReportItem d: drvsData ) {
            drt = d;   
            setStyles();
            
            //Driver
            drt.setDriver(d.getDriver());
                        
            //Vehicle, none assigned
            if ( d.getVehicle() == null ) {
                Vehicle v = new Vehicle();                
                v.setName(MessageUtil.getMessageString("reports_none_assigned"));
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
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }


    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

	@Override
	protected void setDisplayData(List<DriverReportItem> displayData) {

		driverData = displayData;
		
	}
	
	public Map<String, TableColumn> getDriverColumns()
	{
	    return super.getTableColumns();
	}
	

    /*
        When using Facelets <ui:include>, you can not pass a String as a <ui:param> that equals the action. For example:
                        <ui:include src="/includes/scoreBox.xhtml">
                                <ui:param name="action" value="go_reportVehicleStyle" /> 
                        </ui:include>
                        
        Instead, you must pass a bean and the name of the action that will be called, like this:                                 
                        <ui:include src="/includes/scoreBox.xhtml">
                                <ui:param name="actionBean" value="#{vehicleReportBean}" /> 
                                <ui:param name="action" value="reportVehicleStyleAction" /> 
                        </ui:include> 
                        
        The following actions exist to accomplish what was described above
     */
	public String reportDriverSpeedAction()
	{
	    return "go_reportDriverSpeed";
	}
	public String reportDriverStyleAction()
	{
	    return "go_reportDriverStyle";
	}
	public String reportDriverSeatBeltAction()
	{
	    return "go_reportDriverSeatBelt";
	}
}
