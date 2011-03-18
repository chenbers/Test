package com.inthinc.pro.backing;

import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;

import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.ChartColor;
import com.inthinc.pro.charts.DateLabels;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class IdlePercentageBean extends BaseBean {

	private static final long serialVersionUID = -588572761374842763L;
	private static final Logger logger = Logger.getLogger(IdlePercentageBean.class);
	private ScoreDAO scoreDAO;
	private Integer groupID;
	private Group group;

	private DurationBean durationBean;
    private ReportCriteriaService reportCriteriaService;

	private String chartDef;
	private String totalDriving;
	private String totalIdling;
	
	private Integer totalEMUVehicles;
	private Integer totalVehicles;
	
	private static final String DRIVING_BAR_COLOR = ChartColor.GRAY.toString();
	private static final String IDLE_BAR_COLOR = ChartColor.GREEN.toString(); 
	private static final String IDLE_LINE_COLOR = ChartColor.DARK_GREEN.toString(); 

	public void init(){
		
	}

	public void createChart() {
		List<IdlePercentItem> idlePercentItemList = scoreDAO.getIdlePercentItems(getGroupID(), getDurationBean().getDuration());

		initChartData(idlePercentItemList);
	}

	void initChartData(List<IdlePercentItem> idlePercentItemList) {

		List<Float> drivingValues = new ArrayList<Float>();
		List<Float> idlingValues = new ArrayList<Float>();
		List<Float> percentValues = new ArrayList<Float>();
		List<Date> dateValues = new ArrayList<Date>();
		List<String> toolTipText = new ArrayList<String>();
		
		String toolTipTextTemplate = "{0}&lt;BR&gt;" +
			MessageUtil.getMessageString("idling_percentage_driving_bar_label", getLocale()) +" {1} " + getTimeLabel() + "&lt;BR&gt;" + 
			MessageUtil.getMessageString("idling_percentage_idling_bar_label", getLocale())+ " {2}" + getTimeLabel() + "&lt;BR&gt;" +
			MessageUtil.getMessageString("idling_percentage_idling_line_label", getLocale())+ " {3}";

		DateLabels dateLabels = new DateLabels(getLocale());
		NumberFormat percentFormat = NumberFormat.getPercentInstance(getLocale());
		percentFormat.setMinimumFractionDigits(2);
		percentFormat.setMaximumFractionDigits(2);


		Long totalDriving = 0l;
		Long totalIdling = 0l;
        setTotalEMUVehicles(0);
        setTotalVehicles(0);
		for (IdlePercentItem item : idlePercentItemList) {
			Float driveTime = getHours(item.getDrivingTime());
			Float idleTime = getHours(item.getIdlingTime());
			Long diff = item.getDrivingTime() - item.getIdlingTime();
			diff = (diff < 0l) ? 0 : diff;
			drivingValues.add(getHours(diff));
			idlingValues.add(idleTime);
			Float percent = MathUtil.percent(item.getIdlingTime(), item.getDrivingTime()).floatValue();
			percentValues.add(percent);
			dateValues.add(item.getDate());
			totalDriving += item.getDrivingTime();
			totalIdling += item.getIdlingTime();
			
			// this ends up being the last count in the list that is > 0(i.e. last item)
			if (item.getNumVehicles() != null && item.getNumVehicles() > 0) {
			    setTotalEMUVehicles(item.getNumEMUVehicles());
			    setTotalVehicles(item.getNumVehicles());
			}
			String dateLabel = (getDurationBean().getDuration().equals(Duration.DAYS)) ? dateLabels.getDayLabel(item.getDate()) : dateLabels.getMonthLabel(item.getDate()); 

			toolTipText.add(MessageFormat.format(toolTipTextTemplate, dateLabel, 
					formatDecimal(driveTime), formatDecimal(idleTime), 
					percentFormat.format(percent.doubleValue()/100d)));

		}		
		
		Bar2DMultiAxisChart bar2DMultiAxisChart = new Bar2DMultiAxisChart(getDurationBean().getDuration(), dateValues);
		StringBuilder chartBuilder = new StringBuilder();
		chartBuilder.append(bar2DMultiAxisChart.getControlParameters());
		chartBuilder.append("SYAxisMaxValue=\'100\' ");
		chartBuilder.append("decimalSeparator=\'");
		chartBuilder.append(GraphicUtil.getDecimalSeparator(getLocale()));
		chartBuilder.append("\' thousandSeparator=\'");
		chartBuilder.append(GraphicUtil.getGroupingSeparator(getLocale()));
		chartBuilder.append("\'> ");

		chartBuilder.append(bar2DMultiAxisChart.getCategories(getLocale()));
		chartBuilder.append("<dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_idling_bar_label", getLocale()), 
				IDLE_BAR_COLOR, false, idlingValues, toolTipText));
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_driving_bar_label", getLocale()),
				DRIVING_BAR_COLOR, false, drivingValues, toolTipText));
		chartBuilder.append("</dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_idling_line_label", getLocale()), 
				IDLE_LINE_COLOR, true, percentValues, toolTipText));
		chartBuilder.append(bar2DMultiAxisChart.getClose());
		setChartDef(chartBuilder.toString());
//System.out.println(chartBuilder.toString());		
		setTotalDriving(formatDecimal(getHours(totalDriving)) + " " + getTimeLabel());
		setTotalIdling(formatDecimal(getHours(totalIdling)) + " " + getTimeLabel()
				+ " (" + percentFormat.format(MathUtil.percent(totalIdling, totalDriving).doubleValue()/100d) + ")");

	}

	private String formatDecimal(double d) {
        NumberFormat format = NumberFormat.getInstance(getLocale());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        return format.format(d+.005);
	}

	private Float getHours(Long sec) {
		 return (sec == null) ? 0f: DateUtil.convertSecondsToHours(sec);
	}

	private String getTimeLabel() {
		return MessageUtil.getMessageString("idling_percentage_time_label", getLocale());
	}

	public IdlingData getIdlingData() {

		IdlingData idlingData = new IdlingData();
		idlingData.setTotalDriving(totalDriving);
		idlingData.setTotalIdling(totalIdling);
		idlingData.setChartDef(chartDef);
		idlingData.setStatsMessage(MessageUtil.formatMessageString("idling_percentage_stats_msg", totalEMUVehicles,totalVehicles));
		
		return idlingData;
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
    public ReportCriteria buildReportCriteria()
    {
		if (groupID == null) {
			setGroupID(getUser().getGroupID());
		}
        ReportCriteria reportCriteria = reportCriteriaService.getIdlePercentageReportCriteria(getGroupID(), durationBean.getDuration(), getLocale());
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
	
	public ScoreDAO getScoreDAO() {
		return scoreDAO;
	}
	public void setScoreDAO(ScoreDAO scoreDAO) {
		this.scoreDAO = scoreDAO;
	}

	public String getTotalDriving() {
		if (totalDriving == null)
			createChart();
		return totalDriving;
	}

	public void setTotalDriving(String totalDriving) {
		this.totalDriving = totalDriving;
	}

	public String getTotalIdling() {
		if (totalIdling == null)
			createChart();
		return totalIdling;
	}

	public void setTotalIdling(String totalIdling) {
		this.totalIdling = totalIdling;
	}

	public Integer getTotalEMUVehicles() {
		if (totalEMUVehicles == null)
			return 0;
		return totalEMUVehicles;
	}

	public void setTotalEMUVehicles(Integer totalEMUVehicles) {
		this.totalEMUVehicles = totalEMUVehicles;
	}

	public Integer getTotalVehicles() {
		if (totalVehicles == null)
			return 0;
		return totalVehicles;
	}

	public void setTotalVehicles(Integer totalVehicles) {
		this.totalVehicles = totalVehicles;
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
    
    public class IdlingData {
    	private String totalDriving;
    	private String totalIdling;
    	private String chartDef;
    	private String statsMessage;
    	
		public String getTotalDriving() {
			return totalDriving;
		}
		public void setTotalDriving(String totalDriving) {
			this.totalDriving = totalDriving;
		}
		public String getTotalIdling() {
			return totalIdling;
		}
		public void setTotalIdling(String totalIdling) {
			this.totalIdling = totalIdling;
		}
		public String getChartDef() {
			return chartDef;
		}
		public void setChartDef(String chartDef) {
			this.chartDef = chartDef;
		}
		public String getStatsMessage() {
			return statsMessage;
		}
		public void setStatsMessage(String statsMessage) {
			this.statsMessage = statsMessage;
		}
    }

	public String getChartDef() {
		return chartDef;
	}
}
