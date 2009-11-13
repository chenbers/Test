package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.ChartColor;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

public class SpeedPercentageBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8680673642726461850L;

//	private static final Logger logger = Logger.getLogger(SpeedPercentageBean.class);

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

	private void init() {

		if (groupID == null) {
			setGroupID(getUser().getGroupID());
		}

		List<SpeedPercentItem> speedPercentItemList = scoreDAO.getSpeedPercentItems(getGroupID(), getDurationBean().getDuration());

		initChartData(speedPercentItemList);
	}

	void initChartData(List<SpeedPercentItem> speedPercentItemList) {

		List<Long> distanceValues = new ArrayList<Long>();
		List<Long> speedValues = new ArrayList<Long>();
		List<Long> percentValues = new ArrayList<Long>();
		List<Date> dateValues = new ArrayList<Date>();
		Long totalDistance = 0l;
		Long totalSpeedDistance = 0l;
		for (SpeedPercentItem item : speedPercentItemList) {
			Long distance = getDistance(item.getMiles());
			Long speedDistance = getDistance(item.getMilesSpeeding());
			distanceValues.add(distance);
			speedValues.add(speedDistance);
			Long percent = (distance == 0l) ? 0l : ((speedDistance * 100l) / distance);
			if (percent > 100)
				percent = 100l;
			percentValues.add(percent);
			totalDistance += distance;
			totalSpeedDistance += speedDistance;
			dateValues.add(item.getDate());
		}
		Bar2DMultiAxisChart bar2DMultiAxisChart = new Bar2DMultiAxisChart(getDurationBean().getDuration(), dateValues);
//				getDurationBean().getDuration());
		StringBuilder chartBuilder = new StringBuilder();
		chartBuilder.append(bar2DMultiAxisChart.getControlParameters());
		chartBuilder.append(bar2DMultiAxisChart.getCategories(getLocale()));
		chartBuilder.append("<dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_speeding_bar_label", getLocale()), 
				SPEED_BAR_COLOR, false, speedValues));
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_distance_bar_label", getLocale()),
				DISTANCE_BAR_COLOR, false, distanceValues));
		chartBuilder.append("</dataset>");
		chartBuilder.append(bar2DMultiAxisChart.getSeries(MessageUtil.getMessageString("speeding_percentage_speeding_line_label", getLocale()), 
				SPEED_LINE_COLOR, true, percentValues));
		chartBuilder.append(bar2DMultiAxisChart.getClose());
		setChartDef(chartBuilder.toString());
		setTotalDistance(totalDistance + " " + getDistanceLabel());
		setTotalSpeeding(totalSpeedDistance + " " + getDistanceLabel()
				+ " (" + formatPercent((totalDistance == 0) ? 0 : ((totalSpeedDistance * 100d) / totalDistance)) + "%)");

	}

	private String formatPercent(double d) {
        NumberFormat format = NumberFormat.getInstance(getLocale());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        return format.format(d + .005);
	}

	private Long getDistance(Number miles) {
		 return (miles == null) ? 0l: MeasurementConversionUtil.convertMilesToKilometers(miles.longValue() / 100l, getPerson().getMeasurementType()).longValue();
	}

	private String getDistanceLabel() {
		return MessageUtil.getMessageString(getMeasurementType().toString()
				+ "_mi", getLocale());
	}

	public String getTotalDistance() {
		if (totalDistance == null)
			init();
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTotalSpeeding() {
		if (totalSpeeding == null)
			init();
		return totalSpeeding;
	}

	public void setTotalSpeeding(String totalSpeeding) {
		this.totalSpeeding = totalSpeeding;
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
        ReportCriteria reportCriteria = reportCriteriaService.getSpeedPercentageReportCriteria(getGroupID(), durationBean.getDuration(), getLocale());
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
	
	
}
