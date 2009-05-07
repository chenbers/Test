package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.MessageUtil;

public class VehicleReportBean extends BaseReportBean<VehicleReportItem> implements PersonChangeListener
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

    @Override
    protected void loadDBData()
    {
        vehiclesData = 
            scoreDAO.getVehicleReportData(
            		getEffectiveGroupId(),
                    Duration.TWELVE);
        
        for(VehicleReportItem v: vehiclesData)
        {
            //Driver, none assigned
            if ( v.getDriver() == null || v.getDriver().getPerson() == null ) {
                Driver d = new Driver();
                Person p = new Person();
                p.setFirst(MessageUtil.getMessageString("reports_none_assigned"));
                d.setPerson(p);
                v.setDriver(d);
            }
        }         
    }

    @Override
    public void personListChanged()
    {
        loadDBData();
        search();
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
//        if ( this.vehicleData.size() > 0 ) {
//            this.vehicleData.clear();
//        }   
        vehicleData = new ArrayList <VehicleReportItem>();
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
            if ( v.getDriver() == null || v.getDriver().getPerson() == null ) {
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
    
    @Override
    protected void filterResults(List<VehicleReportItem> data)
    {
        // TODO Auto-generated method stub
        
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
        ReportCriteria reportCriteria = getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(vehicleData);
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(vehicleData);
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), Duration.TWELVE);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(vehicleData);
        reportCriteria.setLocale(getUser().getLocale());
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

	@Override
	protected void setDisplayData(List<VehicleReportItem> displayData) {

		vehicleData = displayData;
	}
	
	public Map<String, TableColumn> getVehicleColumns()
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
	public String vehicleAction()
	{
	    return "go_vehicle";
	}
	public String reportVehicleSpeedAction()
	{
	    return "go_reportVehicleSpeed";
	}
	public String reportVehicleStyleAction()
	{
	    return "go_reportVehicleStyle";
	}
}
