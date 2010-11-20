package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;
@KeepAlive
public class DriverSeatBeltBean extends BasePerformanceEventsBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4455874218373654630L;

	private static final Logger   logger         = Logger.getLogger(DriverSeatBeltBean.class);

    private Integer               seatBeltScore;
    private String                seatBeltScoreHistoryOverall;
    private String                seatBeltScoreStyle;
    private static final Integer  NO_SCORE = -1;

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
		ScoreableEntity se = getPerformanceDataBean().getAverageScore(getDriver().getDriverID(), EntityType.ENTITY_DRIVER, durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
        
        if(se != null && se.getScore() != null)
            setSeatBeltScore(se.getScore());
        else
            setSeatBeltScore(NO_SCORE);
        
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(getSeatBeltScore(), ScoreBoxSizes.MEDIUM));
    }

    @Override
    protected void initEvents()
    {
        setDateFormatter();
        List<NoteType> types = new ArrayList<NoteType>();
        types.add(NoteType.SEATBELT);

        List<Event> tempEvents = new ArrayList<Event>();
        tempEvents = eventDAO.getEventsForDriver(getDriver().getDriverID(), durationBean.getStartDate(), durationBean.getEndDate(), types, getShowExcludedEvents());

        events = new ArrayList<EventReportItem>();
        for (Event event : tempEvents)
        {
            event.setAddressStr(getAddress(event.getLatLng()));
            events.add(new EventReportItem(event, this.getDriver().getPerson().getTimeZone(),getMeasurementType(),dateFormatter));
        }
        // Commented-out to prevent the page being reset, but the other
        //  function provided by the call is executed here
//        tableStatsBean.reset(ROWCOUNT, events.size());
        tableStatsBean.setTableRowCount(ROWCOUNT);
        tableStatsBean.setTableSize(events.size());
        sortEvents();
    }

    @Override
    protected void initTrends()
    {
    	if (durationBean.getDuration() == Duration.DAYS){
    		
    		seatBeltScoreHistoryOverall = createFusionMultiLineDefDays(getDriver().getDriverID(), ScoreType.SCORE_SEATBELT);   		
    	}
    	else{
    		
    		seatBeltScoreHistoryOverall = createFusionMultiLineDef(getDriver().getDriverID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
    	}
//        seatBeltScoreHistoryOverall = createFusionMultiLineDef(getDriver().getDriverID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
    }

    public Integer getSeatBeltScore()
    {
        if(seatBeltScore == null) 
            initScores();
        
        return seatBeltScore;
    }

    public void setSeatBeltScore(Integer seatBeltScore)
    {
        this.seatBeltScore = seatBeltScore;
    }

    public String getSeatBeltScoreHistoryOverall()
    {
        if(seatBeltScoreHistoryOverall == null)
            initTrends();
        
        return seatBeltScoreHistoryOverall;
    }

    public void setSeatBeltScoreHistoryOverall(String seatBeltScoreHistoryOverall)
    {
        this.seatBeltScoreHistoryOverall = seatBeltScoreHistoryOverall;
    }

    public String getSeatBeltScoreStyle()
    {
        if(seatBeltScore == null) 
            initScores();

        return seatBeltScoreStyle;
    }

    public void setSeatBeltScoreStyle(String seatBeltScoreStyle)
    {
        this.seatBeltScoreStyle = seatBeltScoreStyle;
    }

    public List<EventReportItem> getSeatBeltEvents()
    {
        if(events == null)
            initEvents();
        
        return events;
    }

    public void setSeatBeltEvents(List<EventReportItem> seatBeltEvents)
    {
        this.events = seatBeltEvents;
    }
    @Override
    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        initScores();
        initTrends();
        initEvents();
    }
    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    public EventReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(EventReportItem clearItem)
    {
        this.clearItem = clearItem;
    }

    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_SEATBELT, getGroupHierarchy().getTopGroup().getName(), getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("ENTITY_NAME", this.getDriver().getPerson().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", this.getSeatBeltEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", this.getSeatBeltScore() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SEATBELT);
        if (durationBean.getDuration() == Duration.DAYS){
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDefDays(getDriver().getDriverID(), scoreTypes));
        }
        else {
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDef(getDriver().getDriverID(), scoreTypes, durationBean.getDuration()));
        }
        
        // Prior to sending the data, get the addresses, if using google client side geocoding
        List<EventReportItem> local = new ArrayList<EventReportItem>();
        local.addAll(this.events);
        
        if ( super.getAddressFormat() == 3 ) {
            local.clear();
            local = this.populateAddresses(this.events);
        }
        
        reportCriteria.setMainDataset(local);

        return reportCriteria;
    }

//    @Override
//    public void exportReportToPdf()
//    {
//        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
//    }
//
//    @Override
//    public void emailReport()
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
        eventsListsMap.put("OVERALL", events);
		
	}

	public DriverSeatBeltBean() {
		super();
		selectedBreakdown="OVERALL";
	}

    
}
