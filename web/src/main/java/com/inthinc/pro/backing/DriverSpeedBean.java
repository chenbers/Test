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
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class DriverSpeedBean extends BasePerformanceEventsBean
{
    private static final Logger                logger         = Logger.getLogger(DriverSpeedBean.class);

    
    public DriverSpeedBean() {
		super();
		
		selectedBreakdown="OVERALL";
	}

    @Override
    protected List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType)
    {
        return scoreDAO.getDriverTrendDaily(id, duration, scoreType);
    }
    
    @Override
    protected void initScores()
    {
        Map<ScoreType, ScoreableEntity> tempMap = scoreDAO.getDriverScoreBreakdownByType(getDriverID(), durationBean.getDuration(), ScoreType.SCORE_SPEEDING);

        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();

        // TODO This needs to be cleaned up. The style should be pushed to the .xhtml page and a null ScoreableEntity needs to be handled better.
        // Fixed for quick realease.
        ScoreableEntity se = tempMap.get(ScoreType.SCORE_SPEEDING);
        if (se != null)
        {
            scoreMap.put(ScoreType.SCORE_SPEEDING.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

            se = tempMap.get(ScoreType.SCORE_SPEEDING_21_30);
            scoreMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

            se = tempMap.get(ScoreType.SCORE_SPEEDING_31_40);
            scoreMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

            se = tempMap.get(ScoreType.SCORE_SPEEDING_41_54);
            scoreMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

            se = tempMap.get(ScoreType.SCORE_SPEEDING_55_64);
            scoreMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

            se = tempMap.get(ScoreType.SCORE_SPEEDING_65_80);
            scoreMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), se.getScore());
            styleMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));
        }
        else
        {
            scoreMap.put(ScoreType.SCORE_SPEEDING.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));

            scoreMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));

            scoreMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));

            scoreMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));

            scoreMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));

            scoreMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), EMPTY_SCORE_VALUE);
            styleMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), ScoreBox.GetStyleFromScore(null, ScoreBoxSizes.MEDIUM));
        }
    }

    @Override
	protected List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType)
	{
	    return scoreDAO.getDriverTrendCumulative(id, duration, scoreType);
	}

	@Override
    protected void initTrends()
    {
        Integer id = getDriver().getDriverID();
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_SPEEDING.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING));
        trendMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_21_30));
        trendMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_31_40));
        trendMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_41_54));
        trendMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_55_64));
        trendMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_65_80));
    }

    @Override
    protected void initEvents()
    {
        List<Event> tempEvents = new ArrayList<Event>();
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);

        tempEvents = eventDAO.getEventsForDriver(getDriver().getDriverID(), durationBean.getStartDate(), durationBean.getEndDate(), types, showExcludedEvents);
        events = new ArrayList<EventReportItem>();

        for (Event event : tempEvents)
        {
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            events.add(new EventReportItem(event, this.getDriver().getPerson().getTimeZone(),getMeasurementType()));
        }
        sortEvents();
    }

    public ReportCriteria buildReport(ReportType reportType)
    {
        ReportCriteria reportCriteria = new ReportCriteria(reportType, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("ENTITY_NAME", this.getDriver().getPerson().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", eventsListsMap.size());
        reportCriteria.addParameter("OVERALL_SCORE", getScoreMap().get(ScoreType.SCORE_SPEEDING.toString()) / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.addParameter("SCORE_TWENTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_21_30.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_THIRTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_31_40.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FOURTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_41_54.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FIFTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_55_64.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_SIXTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_65_80.toString()) / 10.0D);
        reportCriteria.setLocale(getLocale());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SPEEDING);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_21_30);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_31_40);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_41_54);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_55_64);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_65_80);
        reportCriteria.addChartDataSet(this.createJasperMultiLineDef(getDriver().getDriverID(), scoreTypes, durationBean.getDuration()));
        reportCriteria.setMainDataset(this.events);

        return reportCriteria;
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReport(ReportType.DRIVER_SPEED), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(ReportType.DRIVER_SPEED), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReport(ReportType.DRIVER_SPEED), getFacesContext());
    }

    @Override
    public void sortEvents()
    {
    	eventsListsMap = new HashMap<String, List<EventReportItem>>();
        List<EventReportItem> speedAll = new ArrayList<EventReportItem>();
        eventsListsMap.put("OVERALL", speedAll);
        List<EventReportItem> speed20 = new ArrayList<EventReportItem>();
        eventsListsMap.put("TWENTYONE", speed20);
        List<EventReportItem> speed30 = new ArrayList<EventReportItem>();
        eventsListsMap.put("THIRTYONE", speed30);
        List<EventReportItem> speed40 = new ArrayList<EventReportItem>();
        eventsListsMap.put("FOURTYONE", speed40);
        List<EventReportItem> speed50 = new ArrayList<EventReportItem>();
        eventsListsMap.put("FIFTYFIVE", speed50);
        List<EventReportItem> speed60 = new ArrayList<EventReportItem>();
        eventsListsMap.put("SIXTYFIVE", speed60);

        speedAll.addAll(events);

        SpeedingEvent event;
        for (EventReportItem eri : events)
        {
            event = (SpeedingEvent)eri.getEvent();
            
            if(event.getSpeedLimit() == null)
                continue;
            
            if (event.getSpeedLimit() >= 0 && event.getSpeedLimit() < 31)
            {
                speed20.add(eri);
            }
            else if (event.getSpeedLimit() > 30 && event.getSpeedLimit() < 41)
            {
                speed30.add(eri);
            }
            else if (event.getSpeedLimit() > 40 && event.getSpeedLimit() < 55)
            {
                speed40.add(eri);
            }
            else if (event.getSpeedLimit() > 54 && event.getSpeedLimit() < 65)
            {
                speed50.add(eri);
            }
            else if (event.getSpeedLimit() > 64 && event.getSpeedLimit() < 81)
            {
                speed60.add(eri);
            }
        }
        filteredEvents = eventsListsMap.get(selectedBreakdown);
    }
    
   
//    @Override
//	public void excludeEventAction() {
//		
//	    Integer result = eventDAO.forgive(getDriver().getDriverID(), clearItem.getEvent().getNoteID());
//	    if(result >= 1)
//	        {
//	            initEvents();
//	            tableStatsBean.updateSize(getEventsListsMap().get(selectedBreakdown).size());
//	        }
//	}

//    @Override
//	public void includeEventAction() {
//		
//	    Integer result = eventDAO.unforgive(getDriver().getDriverID(), clearItem.getEvent().getNoteID());
//	    if(result >= 1)
//	        {
//	            initEvents();
//	            tableStatsBean.updateSize(getEventsListsMap().get(selectedBreakdown).size());
//	        }
//	}

}
