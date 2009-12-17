package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.ReportCriteria;

public class VehicleReportBean extends BaseReportBean<VehicleReportItem> implements PersonChangeListener
{
    private static final Logger logger = Logger.getLogger(VehicleReportBean.class);
    
    //vehiclesData is the ONE read from the db, vehicleData is what is displayed
    private List <VehicleReportItem> vehiclesData = new ArrayList<VehicleReportItem>();
    private List <VehicleReportItem> vehicleData = new ArrayList<VehicleReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    
    private ScoreDAO scoreDAO;
    
    private final static String COLUMN_LABEL_PREFIX = "vehicleReports_";
    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("makeModelYear");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("distanceDriven");
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
        
        // Once loaded, set the group name NOW so it can be searchable IMMEDIATELY
        for (VehicleReportItem vri : this.vehiclesData)
        {
        	if (this.getGroupHierarchy().getGroup(vri.getGroupID()) == null)
        	{
        		logger.info("groupID " + vri.getGroupID());
        		logger.info("vehicleID " + vri.getVehicle().getVehicleID());
        		logger.info("driverID " + vri.getVehicle().getDriverID());
        	}
            vri.setGroup(this.getGroupHierarchy().getGroup(vri.getGroupID()).getName());
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
        vehicleData = new ArrayList <VehicleReportItem>();
                
        for ( VehicleReportItem v: vehicData ) {          
            setStyles(v);  
            //Group name
            v.setGroup(this.getGroupHierarchy().getGroup(v.getGroupID()).getName());
            vehicleData.add(v);            
        }    
                
        this.maxCount = this.vehicleData.size();   
        resetCounts();            
    }
    
    @Override
    protected void filterResults(List<VehicleReportItem> data)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String fieldValue(VehicleReportItem item, String column)
    {
        if("distanceDriven".equals(column))
        {
            if(getMeasurementType().equals(MeasurementType.ENGLISH))
                return item.getMilesDriven().toString();
            else
                return MeasurementConversionUtil.fromMilesToKilometers(item.getMilesDriven().doubleValue()).toString();
        }
        
        return super.fieldValue(item, column);
    }

    private void setStyles(VehicleReportItem vrt) 
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
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }
    
    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReportCriteria(),getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }
    
    private ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), Duration.TWELVE, getLocale());
        reportCriteria.setLocale(getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(vehicleData);
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
        return reportCriteria;
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
	
	
	@Override
    public String getMappingId()
    {
        return "pretty:vehiclesReport";
    }
    
    @Override
    public String getMappingIdWithCriteria()
    {
        return "pretty:vehiclesReportWithCriteria";
    }

}
