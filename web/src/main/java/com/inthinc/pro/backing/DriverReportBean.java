package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.inthinc.pro.reports.ReportCriteria;

public class DriverReportBean extends BaseReportBean<DriverReportItem> implements PersonChangeListener
{

    private static final Logger logger = Logger.getLogger(DriverReportBean.class);

    // driversData is the ONE read from the db, driverData is what is displayed
    private List<DriverReportItem> driversData = new ArrayList<DriverReportItem>();
    private List<DriverReportItem> driverData = new ArrayList<DriverReportItem>();

    static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 2, 3, 4, 5, 7, 8 };

    private ScoreDAO scoreDAO;

    private final static String COLUMN_LABEL_PREFIX = "driverReports_";

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_empid");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("distanceDriven");
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
        this.driversData = scoreDAO.getDriverReportData(getUser().getGroupID(), Duration.TWELVE);

        // Once loaded, set the group name NOW so it can be searchable IMMEDIATELY
        for (DriverReportItem dri : this.driversData)
        {
            dri.setGroup(this.getGroupHierarchy().getGroup(dri.getGroupID()).getName());
            dri.setLocale(getLocale());
        }

    }

    @Override
    public void personListChanged()
    {
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

        // Filter if search is based on group.
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
    protected void loadResults(List<DriverReportItem> drvsData)
    {
        driverData = new ArrayList<DriverReportItem>();

        for (DriverReportItem d : drvsData)
        {
            setScoreBoxStyles(d);
            driverData.add(d);
        }

        this.maxCount = this.driverData.size();
        resetCounts();
    }
    
    @Override
    public String fieldValue(DriverReportItem item, String column)
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

    private void setScoreBoxStyles(DriverReportItem drt)
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
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }

    private ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), Duration.TWELVE, getLocale());
        reportCriteria.setMainDataset(driverData);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
        
        return reportCriteria;
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
    protected void setDisplayData(List<DriverReportItem> displayData)
    {

        driverData = displayData;

    }

    public Map<String, TableColumn> getDriverColumns()
    {
        return super.getTableColumns();
    }
    
    @Override
    public String getMappingId()
    {
      
        return "pretty:driversReport";
    }
    
    @Override
    public String getMappingIdWithCriteria()
    {
        return "pretty:driversReportWithCriteria";
    }

    public List<DriverReportItem> getDriversData()
    {
        return driversData;
    }

    public void setDriversData(List<DriverReportItem> driversData)
    {
        this.driversData = driversData;
    }
    
    
    
    
}
