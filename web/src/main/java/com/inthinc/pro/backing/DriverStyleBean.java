package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class DriverStyleBean extends BasePerformanceEventsBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2981067321885761800L;
//	private static final Logger   logger            = Logger.getLogger(DriverStyleBean.class);

    public DriverStyleBean() {
		super();
		
		selectedBreakdown="OVERALL";
	}

    @Override
    protected List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendCumulative(id, EntityType.ENTITY_DRIVER, duration, scoreType);
    }

    protected List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendDaily(id, EntityType.ENTITY_DRIVER, duration, scoreType);
    }
    
    @Override
    protected void initScores()
    {
        Map<ScoreType, ScoreableEntity> tempMap = getPerformanceDataBean().getAverageScoreBreakdown(getDriverID(), EntityType.ENTITY_DRIVER, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE);

        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        for (ScoreType subType : ScoreType.SCORE_DRIVING_STYLE.getSubTypes())
        {
        	ScoreableEntity se = tempMap.get(subType);
        	if (se != null && se.getScore() != null)
        	{
        		scoreMap.put(subType.toString(), se.getScore());
        		styleMap.put(subType.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));
        	}
        	else
        	{
        		scoreMap.put(subType.toString(), EMPTY_SCORE_VALUE);
        		styleMap.put(subType.toString(), ScoreBox.GetStyleFromScore(NO_SCORE, ScoreBoxSizes.MEDIUM));

        	}
        }
    }

	@Override
    protected void initTrends()
    {
        Integer id = getDriver().getDriverID();
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));
    }

    @Override
    protected void initEvents()
    {
        List<Event> tempEvents = new ArrayList<Event>();
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

        tempEvents = eventDAO.getEventsForDriver(getDriver().getDriverID(), durationBean.getStartDate(), durationBean.getEndDate(), types, getShowExcludedEvents());
        events = new ArrayList<EventReportItem>();

        for (Event event : tempEvents)
        {
            event.setAddressStr(getAddress(event.getLatLng()));
            events.add(new EventReportItem(event, this.getDriver().getPerson().getTimeZone(),getMeasurementType()));
        }
        sortEvents();
    }


    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_STYLE, getGroupHierarchy().getTopGroup().getName(), getLocale());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("ENTITY_NAME", getDriver().getPerson().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()) / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_style"));
        reportCriteria.addParameter("SCORE_HARDBRAKE", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDACCEL", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDTURN", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDBUMP", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()) / 10.0D);
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        if (durationBean.getDuration() == Duration.DAYS){
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDefDays(getDriver().getDriverID(), scoreTypes));
        }
        else {
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDef(getDriver().getDriverID(), scoreTypes, durationBean.getDuration()));
        }
        reportCriteria.setMainDataset(getEvents());

        return reportCriteria;
    }

//    @Override
//    public void exportReportToPdf()
//    {
//        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
//    }
//
//    @Override
//   public void emailReport()
//    {
//        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
//    }
//
//    @Override
//    public void exportReportToExcel()
//    {
//        getReportRenderer().exportReportToExcel(buildReport(), getFacesContext());
//    }


	@Override
    public void sortEvents()
    { 
    	eventsListsMap = new HashMap<String, List<EventReportItem>>();
    	
        List<EventReportItem> sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put("OVERALL", sortedEvents);
        sortedEvents.addAll(getEvents());
        filteredEvents = sortedEvents;
       
        sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put(EventType.HARD_BRAKE.name(), sortedEvents);
        sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put(EventType.HARD_ACCEL.name(), sortedEvents);
        sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put(EventType.HARD_TURN.name(), sortedEvents);
        sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put(EventType.HARD_VERT.name(), sortedEvents);
        sortedEvents = new ArrayList<EventReportItem>();
        eventsListsMap.put(EventType.UNKNOWN.name(), sortedEvents);

        for (EventReportItem eri : getEvents())
        {
        	 eventsListsMap.get(eri.getEvent().getEventType().name()).add(eri);
        }
         
        tableStatsBean.reset(ROWCOUNT, filteredEvents.size());
    }

}
