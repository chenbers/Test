package com.inthinc.pro.backing;

import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.IdlePercentageBean.IdlingData;
import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.ChartColor;
import com.inthinc.pro.charts.DateLabels;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class SpeedPercentageBean extends BaseBean {

    private static final long serialVersionUID = -8680673642726461850L;

    // private static final Logger logger = Logger.getLogger(SpeedPercentageBean.class);

    private ScoreDAO scoreDAO;
    private Integer groupID;
    private Group group;

    private DurationBean durationBean;
    private ReportCriteriaService reportCriteriaService;

    private String chartDef;
    private String totalDistance;
    private String totalSpeeding;

    private static final String DISTANCE_BAR_COLOR = ChartColor.GRAY.toString();
    private static final String SPEED_BAR_COLOR = ChartColor.BLUE.toString();
    private static final String SPEED_LINE_COLOR = ChartColor.DARK_BLUE.toString();

    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }
    public void init(){
    
    }
    public void createChart() {
        List<SpeedPercentItem> speedPercentItemList = scoreDAO.getSpeedPercentItems(getGroupID(), getDurationBean().getDuration(), getGroupHierarchy());
        initChartData(speedPercentItemList);
    }

    void initChartData(List<SpeedPercentItem> speedPercentItemList) {

        List<Long> distanceValues = new ArrayList<Long>();
        List<Long> speedValues = new ArrayList<Long>();
        List<Float> percentValues = new ArrayList<Float>();
        List<Date> dateValues = new ArrayList<Date>();
        List<String> toolTipText = new ArrayList<String>();
        Long totalDistance = 0l;
        Long totalSpeedDistance = 0l;
        String toolTipTextTemplate = "{0}&lt;BR&gt;" + MessageUtil.getMessageString("speeding_percentage_distance_bar_label", getLocale()) + " {1}&lt;BR&gt;"
                + MessageUtil.getMessageString("speeding_percentage_speeding_bar_label", getLocale()) + " {2}&lt;BR&gt;"
                + MessageUtil.getMessageString("speeding_percentage_speeding_line_label", getLocale()) + " {3}";

        DateLabels dateLabels = new DateLabels(getLocale());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(getLocale());
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        NumberFormat percentFormat = NumberFormat.getPercentInstance(getLocale());
        percentFormat.setMinimumFractionDigits(2);
        percentFormat.setMaximumFractionDigits(2);

        for (SpeedPercentItem item : speedPercentItemList) {
            Long distance = getDistance(item.getMiles());
            Long speedDistance = getDistance(item.getMilesSpeeding());
            distanceValues.add(distance - speedDistance);
            speedValues.add(speedDistance);
            Float percent = MathUtil.percent(speedDistance, distance).floatValue();
            percentValues.add(percent);
            totalDistance += distance;
            totalSpeedDistance += speedDistance;
            dateValues.add(item.getDate());
            String dateLabel = (getDurationBean().getDuration().equals(Duration.DAYS)) ? dateLabels.getDayLabel(item.getDate()) : dateLabels.getMonthLabel(item.getDate());
            toolTipText.add(MessageFormat.format(toolTipTextTemplate, dateLabel, numberFormat.format(distance) + " " + getDistanceLabel(), numberFormat.format(speedDistance) + " "
                    + getDistanceLabel(), percentFormat.format(percent.doubleValue() / 100d)));
        }
        Bar2DMultiAxisChart bar2DMultiAxisChart = new Bar2DMultiAxisChart(getDurationBean().getDuration(), dateValues);
        StringBuilder chartBuilder = new StringBuilder();
        chartBuilder.append(bar2DMultiAxisChart.getControlParameters());
        chartBuilder.append(" SYAxisMaxValue=\'50\' ");
        chartBuilder.append("decimalSeparator=\'");
        chartBuilder.append(GraphicUtil.getDecimalSeparator(getLocale()));
        chartBuilder.append("\' thousandSeparator=\'");
        chartBuilder.append(GraphicUtil.getGroupingSeparator(getLocale()));
        chartBuilder.append("\'> ");
        chartBuilder.append(bar2DMultiAxisChart.getCategories(getLocale()));
        chartBuilder.append("<dataset>");
        chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_speeding_bar_label", getLocale()), SPEED_BAR_COLOR, false, speedValues, toolTipText));
        chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_distance_bar_label", getLocale()), DISTANCE_BAR_COLOR, false, distanceValues, toolTipText));
        chartBuilder.append("</dataset>");
        chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_speeding_line_label", getLocale()), SPEED_LINE_COLOR, true, percentValues, toolTipText));
        chartBuilder.append(bar2DMultiAxisChart.getClose());
        setChartDef(chartBuilder.toString());
        // System.out.println(chartBuilder.toString());
        setTotalDistance(totalDistance + " " + getDistanceLabel());
        setTotalSpeeding(totalSpeedDistance + " " + getDistanceLabel() + " (" + percentFormat.format(MathUtil.percent(totalSpeedDistance, totalDistance).doubleValue() / 100d) + ")");

    }

    private Long getDistance(Number miles) {
        return (miles == null) ? 0l : MeasurementConversionUtil.convertMilesToKilometers(miles.longValue() / 100l, getPerson().getMeasurementType()).longValue();
    }

    private String getDistanceLabel() {
        return MessageUtil.getMessageString(getMeasurementType().toString() + "_mi", getLocale());
    }

    public String getTotalDistance() {
        if (totalDistance == null)
            createChart();
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTotalSpeeding() {
        if (totalSpeeding == null)
            createChart();
        return totalSpeeding;
    }

    public void setTotalSpeeding(String totalSpeeding) {
        this.totalSpeeding = totalSpeeding;
    }

    public String getChartDef() {

        if (chartDef == null)
            createChart();
        return chartDef;
    }

    public void setChartDef(String chartDef) {
        this.chartDef = chartDef;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
        group = getGroupHierarchy().getGroup(groupID);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public DurationBean getDurationBean() {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }
	public SpeedingData getSpeedingData() {

		SpeedingData speedingData = new SpeedingData();
		speedingData.setTotalDistance(totalDistance);
		speedingData.setTotalSpeeding(totalSpeeding);
		speedingData.setChartDef(chartDef);
		
		return speedingData;
	}

    public ReportCriteria buildReportCriteria() {
        if (groupID == null) {
            setGroupID(getUser().getGroupID());
        }
        ReportCriteria reportCriteria = reportCriteriaService.getSpeedPercentageReportCriteria(getGroupID(), durationBean.getDuration(), getLocale(), getGroupHierarchy());
        reportCriteria.setLocale(getLocale());
        reportCriteria.setReportDate(new Date(), getPerson().getTimeZone());
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
        return reportCriteria;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public void durationChangeActionListener(ActionEvent event) {
//        createChart();
    }
	public void setDecimalSeparator(char decimalSeparator){
		
	}
	public void setThousandSeparator(char thousandSeparator){
		
	}
	public char getDecimalSeparator(){
		return new DecimalFormatSymbols(getLocale()).getDecimalSeparator();
	}
	public char getThousandSeparator(){
		return new DecimalFormatSymbols(getLocale()).getGroupingSeparator();
	}
    
    public class SpeedingData {
    	
    	private String totalDistance;
    	private String totalSpeeding;
    	private String chartDef;
    	
		public String getTotalDistance() {
			return totalDistance;
		}
		public void setTotalDistance(String totalDistance) {
			this.totalDistance = totalDistance;
		}
		public String getTotalSpeeding() {
			return totalSpeeding;
		}
		public void setTotalSpeeding(String totalSpeeding) {
			this.totalSpeeding = totalSpeeding;
		}
		public String getChartDef() {
			return chartDef;
		}
		public void setChartDef(String chartDef) {
			this.chartDef = chartDef;
		}
    }
}
