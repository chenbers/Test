package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

//import com.inthinc.pro.backing.listener.DurationChangeListener;
import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.charts.FusionColumnChart;
import com.inthinc.pro.charts.FusionMultiAreaChart;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public abstract class BasePerformanceBean extends BaseBean 
{
    private static final Logger logger = Logger.getLogger(BasePerformanceBean.class);
    
    protected static final Integer EMPTY_SCORE_VALUE = -1;
    
    protected DurationBean durationBean;
    protected TableStatsBean tableStatsBean;
    protected ReportRenderer reportRenderer;
    protected GroupDAO groupDAO;
    protected GroupTreeNodeImpl groupTreeNodeImpl;

    protected VehicleBean vehicleBean;
    protected DriverBean driverBean;

    protected Map<String, Integer> scoreMap;
    protected Map<String, String> styleMap;
    protected Map<String, String> trendMap;
    
    

    protected abstract List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType);

    protected abstract List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType);

	/*
     * Create Fusion Charts Multi Line chart.
     */
    public String createFusionLineDef(Integer id, Duration duration, ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());
        List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(duration, getLocale());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            }
            else
            {
                sb.append(line.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(line.getClose());
        return sb.toString();
    }

    /*
     * Create Fusion Charts Multi Line chart. Set no Drive days to dashed line. Integrated bar chart for mileage
     */
    public String createFusionMultiLineDef(Integer id, Duration duration, ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        FusionMultiAreaChart multiAreaChart = new FusionMultiAreaChart();

        // Start XML Data
        sb.append(multiAreaChart.getControlParameters());

        List<ScoreableEntity> cumulativeList = getTrendCumulative(id, duration, scoreType);
        List<ScoreableEntity> dailyList = getTrendDaily(id, duration, scoreType);

        List<String> catLabelList = GraphicUtil.createMonthList(duration,getLocale());

        Double cumulativeValues[] = new Double[cumulativeList.size()];
        Double odometerValues[] = new Double[dailyList.size()];

        // Start Categories List
        sb.append(multiAreaChart.getCategoriesStart());

        for (int i = 0; i < cumulativeList.size(); i++)
        {
            // Get mileage for day.
            if (dailyList.get(i).getIdentifierNum() != null)
                odometerValues[i] = MeasurementConversionUtil.convertMilesToKilometers((dailyList.get(i).getIdentifierNum().longValue()  / 100D), getPerson().getMeasurementType()).doubleValue();

            // Set Score to NULL on non driving days.
            if (odometerValues[i] == null || odometerValues[i] == 0)
                cumulativeValues[i] = null;
            else
                cumulativeValues[i] = cumulativeList.get(i).getScore() == null ? null : cumulativeList.get(i).getScore() / 10D;

            sb.append(multiAreaChart.getCategoryLabel(catLabelList.get(i)));
        }
        sb.append(multiAreaChart.getCategoriesEnd());

        // Not displaying daily score in chart.
        sb.append(multiAreaChart.getChartAreaDataSet(MessageUtil.getMessageString("driver_chart_cumulative"), "#B0CB48", cumulativeValues, catLabelList));
        sb.append(multiAreaChart.getChartBarDataSet(MessageUtil.getMessageString(getMeasurementType() + "_Miles"), "#C0C0C0", odometerValues, catLabelList));
        sb.append(multiAreaChart.getClose());
        return sb.toString();
    }

    /*
     * Create Jasper Charts Multi Line chart for Speed,Style,SeatBelt.
     */
    public List<CategorySeriesData> createJasperMultiLineDef(Integer id, List<ScoreType> scoreTypes, Duration duration)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType scoreType : scoreTypes)
        {
            List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

            List<String> monthList = GraphicUtil.createMonthList(duration,MessageUtil.getMessageString("shortDateFormat") /*"M/dd"*/,getLocale());
            int count = 0;
            for (ScoreableEntity se : scoreList)
            {
                Double score = null;
                if (se.getScore() != null)
                    score = se.getScore() / 10.0D;

                returnList.add(new CategorySeriesData(scoreType.getKey(), monthList.get(count).toString(), score, monthList.get(count).toString()));

                count++;
            }
        }
        return returnList;
    }

    /*
     * Create Jasper Charts Single Line chart for Speed,Style,SeatBelt.
     */
    public List<CategorySeriesData> createSingleJasperDef(Integer id, ScoreType scoreType, Duration duration)
    {
        List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<String> monthList = GraphicUtil.createMonthList(duration, MessageUtil.getMessageString("shortDateFormat") /*"M/dd"*/,getLocale());

        int count = 0;
        for (ScoreableEntity se : scoreList)
        {
            Double score = null;
            if (se.getScore() != null)
                score = se.getScore() / 10.0D;

            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString(scoreType.toString()), monthList.get(count).toString(), score, monthList.get(count).toString()));
            count++;
        }
        return chartDataList;
    }

    /*
     * Create Fusion Charts XML bar chart for Coaching Events.
     */
    public String createColumnDef(Integer id, ScoreType scoreType, Duration duration)
    {
        StringBuffer sb = new StringBuffer();
        FusionColumnChart column = new FusionColumnChart();

        // Start XML Data
        sb.append(column.getControlParameters());

        List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(duration,getLocale());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(column.getChartItem(new Object[] { e.getScore(), monthList.get(cnt) }));
            }
            else
            {
                sb.append(column.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(column.getClose());

        return sb.toString();
    }

    public Map<String, Integer> getScoreMap()
    {
        return scoreMap;
    }

    public void setScoreMap(Map<String, Integer> scoreMap)
    {
        this.scoreMap = scoreMap;
    }

    public Map<String, String> getStyleMap()
    {
        return styleMap;
    }

    public void setStyleMap(Map<String, String> styleMap)
    {
        this.styleMap = styleMap;
    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    public Map<String, String> getTrendMap()
    {
        return trendMap;
    }

    public void setTrendMap(Map<String, String> trendMap)
    {
        this.trendMap = trendMap;
    }

    public TableStatsBean getTableStatsBean()
    {
        return tableStatsBean;
    }

    public void setTableStatsBean(TableStatsBean tableStatsBean)
    {
        this.tableStatsBean = tableStatsBean;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupTreeNodeImpl getGroupTreeNodeImpl()
    {
        return groupTreeNodeImpl;
    }

    public void setGroupTreeNodeImpl(GroupTreeNodeImpl groupTreeNodeImpl)
    {
        this.groupTreeNodeImpl = groupTreeNodeImpl;
    }

    public Driver getDriver()
    {
        return driverBean.getDriver();
    }

    public void setDriver(Driver driver)
    {
        driverBean.setDriver(driver);
    }

    public Integer getDriverID()
    {
        return driverBean.getDriverID();
    }

    public void setDriverID(Integer driverID)
    {
        this.driverBean.setDriverID(driverID);
        this.groupTreeNodeImpl = new GroupTreeNodeImpl(groupDAO.findByID(this.driverBean.getDriver().getGroupID()),getGroupHierarchy());
    }

    public Vehicle getVehicle()
    {
        return vehicleBean.getVehicle();
    }

    public void setVehicle(Vehicle vehicle)
    {
        vehicleBean.setVehicle(vehicle);
    }

    public Integer getVehicleID()
    {
        return vehicleBean.getVehicleID();
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleBean.setVehicleID(vehicleID);
        this.groupTreeNodeImpl = new GroupTreeNodeImpl(groupDAO.findByID(this.vehicleBean.getVehicle().getGroupID()),getGroupHierarchy());
    }

    public VehicleBean getVehicleBean()
    {
        return vehicleBean;
    }

    public void setVehicleBean(VehicleBean vehicleBean)
    {
        this.vehicleBean = vehicleBean;
    }

    public DriverBean getDriverBean()
    {
        return driverBean;
    }

    public void setDriverBean(DriverBean driverBean)
    {
        this.driverBean = driverBean;
    }
    
    public abstract void setDuration(Duration duration);

}
