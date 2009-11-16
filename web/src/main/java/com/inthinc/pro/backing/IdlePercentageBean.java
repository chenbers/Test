package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.ChartColor;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

public class IdlePercentageBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -588572761374842763L;
//	private static final Logger logger = Logger.getLogger(IdlePercentageBean.class);
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



	private void init() {

		if (groupID == null) {
			setGroupID(getUser().getGroupID());
		}

		List<IdlePercentItem> idlePercentItemList = scoreDAO.getIdlePercentItems(getGroupID(), getDurationBean().getDuration());

		initChartData(idlePercentItemList);
	}

	void initChartData(List<IdlePercentItem> idlePercentItemList) {

		List<Float> drivingValues = new ArrayList<Float>();
		List<Float> idlingValues = new ArrayList<Float>();
		List<Integer> percentValues = new ArrayList<Integer>();
		List<Date> dateValues = new ArrayList<Date>();

		Long totalDriving = 0l;
		Long totalIdling = 0l;
		for (IdlePercentItem item : idlePercentItemList) {
			drivingValues.add(getHours(item.getDrivingTime()));
			idlingValues.add(getHours(item.getIdlingTime()));
			Integer percent = MathUtil.percent(item.getIdlingTime(), item.getDrivingTime());
			percentValues.add(percent);
			dateValues.add(item.getDate());
			totalDriving += item.getDrivingTime();
			totalIdling += item.getIdlingTime();
			
			// this ends up being the last count in the list (i.e. last item)
			setTotalEMUVehicles(item.getNumEMUVehicles());
			setTotalVehicles(item.getNumVehicles());
		}		
		
		Bar2DMultiAxisChart bar2DMultiAxisChart = new Bar2DMultiAxisChart(getDurationBean().getDuration(), dateValues);
		StringBuilder chartBuilder = new StringBuilder();
		chartBuilder.append(bar2DMultiAxisChart.getControlParameters());
		chartBuilder.append(bar2DMultiAxisChart.getCategories(getLocale()));
		chartBuilder.append("<dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_idling_bar_label", getLocale()), 
				IDLE_BAR_COLOR, false, idlingValues));
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_driving_bar_label", getLocale()),
				DRIVING_BAR_COLOR, false, drivingValues));
		chartBuilder.append("</dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("idling_percentage_idling_line_label", getLocale()), 
				IDLE_LINE_COLOR, true, percentValues));
		chartBuilder.append(bar2DMultiAxisChart.getClose());
		setChartDef(chartBuilder.toString());
		setTotalDriving(formatDecimal(getHours(totalDriving)) + " " + getTimeLabel());
		setTotalIdling(formatDecimal(getHours(totalIdling)) + " " + getTimeLabel()
				+ " (" + formatDecimal((totalDriving == 0l) ? 0d : (( (float)totalIdling * 100f) / (float)totalDriving)) + "%)");

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

	public String getChartDef() {

		if (chartDef == null)
			init();
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
			init();
		return totalDriving;
	}

	public void setTotalDriving(String totalDriving) {
		this.totalDriving = totalDriving;
	}

	public String getTotalIdling() {
		if (totalIdling == null)
			init();
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

}
