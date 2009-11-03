package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSeatBeltBean extends BasePerformanceEventsBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1083371265347339772L;
//	private static final Logger logger = Logger.getLogger(VehicleSeatBeltBean.class);
    private Integer seatBeltScore;
    private String seatBeltScoreHistoryOverall;
    private String seatBeltScoreStyle;

    public VehicleSeatBeltBean() {
        super();
        selectedBreakdown = "OVERALL";
    }
    @Override
    protected List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendCumulative(id, EntityType.ENTITY_VEHICLE, duration, scoreType);
    }

    protected List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendDaily(id, EntityType.ENTITY_VEHICLE, duration, scoreType);
    }

    @Override
    protected void initScores() {
		ScoreableEntity se = getPerformanceDataBean().getAverageScore(getVehicle().getVehicleID(), EntityType.ENTITY_VEHICLE, durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
        if (se != null && se.getScore() != null)
            setSeatBeltScore(se.getScore());
        else
            setSeatBeltScore(NO_SCORE);
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(getSeatBeltScore(), ScoreBoxSizes.MEDIUM));
    }

    @Override
    protected void initEvents() {
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
        List<Event> tempEvents = new ArrayList<Event>();
        tempEvents = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types, showExcludedEvents);
        events = new ArrayList<EventReportItem>();
        for (Event event : tempEvents) {
            event.setAddressStr(getAddress(event.getLatLng()));
            events.add(new EventReportItem(event, getUser().getPerson().getTimeZone(), getMeasurementType()));
        }
        tableStatsBean.reset(ROWCOUNT, events.size());
        sortEvents();
    }

    @Override
    protected void initTrends() {
        seatBeltScoreHistoryOverall = createFusionMultiLineDef(getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
    }

    public Integer getSeatBeltScore() {
        if (seatBeltScore == null)
            initScores();
        return seatBeltScore;
    }

    public void setSeatBeltScore(Integer seatBeltScore) {
        this.seatBeltScore = seatBeltScore;
    }

    public String getSeatBeltScoreHistoryOverall() {
        if (seatBeltScoreHistoryOverall == null)
            initTrends();
        return seatBeltScoreHistoryOverall;
    }

    public void setSeatBeltScoreHistoryOverall(String seatBeltScoreHistoryOverall) {
        this.seatBeltScoreHistoryOverall = seatBeltScoreHistoryOverall;
    }

    public String getSeatBeltScoreStyle() {
        if (seatBeltScore == null)
            initScores();
        return seatBeltScoreStyle;
    }

    public void setSeatBeltScoreStyle(String seatBeltScoreStyle) {
        this.seatBeltScoreStyle = seatBeltScoreStyle;
    }

    public List<EventReportItem> getSeatBeltEvents() {
        if (events == null)
            initEvents();
        return events;
    }

    public void setSeatBeltEvents(List<EventReportItem> seatBeltEvents) {
        this.events = seatBeltEvents;
    }

    public void setDuration(Duration duration) {
        durationBean.setDuration(duration);
        initScores();
        initTrends();
        initEvents();
    }

    public Duration getDuration() {
        return durationBean.getDuration();
    }

    public EventReportItem getClearItem() {
        return clearItem;
    }

    public void setClearItem(EventReportItem clearItem) {
        this.clearItem = clearItem;
    }

    public ReportCriteria buildReport() {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SEATBELT, getGroupHierarchy().getTopGroup().getName(), getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("ENTITY_NAME", getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getSeatBeltEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", getSeatBeltScore() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SEATBELT);
        reportCriteria.addChartDataSet(createJasperMultiLineDef(getVehicle().getVehicleID(), scoreTypes, durationBean.getDuration()));
        reportCriteria.setMainDataset(events);
        return reportCriteria;
    }

    public void exportReportToPdf() {
        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
    }

    public void emailReport() {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

    public void exportReportToExcel() {
        getReportRenderer().exportReportToExcel(buildReport(), getFacesContext());
    }

    @Override
    public void sortEvents() {
        eventsListsMap = new HashMap<String, List<EventReportItem>>();
        eventsListsMap.put("OVERALL", events);
    }
}
