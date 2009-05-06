package com.inthinc.pro.backing;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSeatBeltBean extends BasePerformanceBean
{
    private static final Logger   logger         = Logger.getLogger(VehicleSeatBeltBean.class);

    private ScoreDAO              scoreDAO;
    private EventDAO              eventDAO;

    private Integer               seatBeltScore;
    private String                seatBeltScoreHistoryOverall;
    private String                seatBeltScoreStyle;
    private EventReportItem       clearItem;
    private String                emailAddress;
    private static final Integer  NO_SCORE       = -1;
    private final Integer         ROWCOUNT       = 10;

    private List<EventReportItem> seatBeltEvents ;

    @Override
    protected List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType)
    {
        return scoreDAO.getVehicleTrendCumulative(id, duration, scoreType);
    }
    
    @Override
    protected List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType)
    {
        return scoreDAO.getVehicleTrendDaily(id, duration, scoreType);
    }
    
    private void initScores()
    {
        ScoreableEntity se = scoreDAO.getVehicleAverageScoreByType(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
        
        if(se != null && se.getScore() != null)
            setSeatBeltScore(se.getScore());
        else
            setSeatBeltScore(NO_SCORE);
        
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(getSeatBeltScore(), ScoreBoxSizes.MEDIUM));
    }

    public void initEvents()
    {
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);

        List<Event> tempEvents = new ArrayList<Event>();
        tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types);

        seatBeltEvents = new ArrayList<EventReportItem>();
        for (Event event : tempEvents)
        {
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            seatBeltEvents.add(new EventReportItem(event, getUser().getPerson().getTimeZone()));
        }
        tableStatsBean.reset(ROWCOUNT, seatBeltEvents.size());
    }

    public void initTrends()
    {
        seatBeltScoreHistoryOverall = createFusionMultiLineDef(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
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

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public List<EventReportItem> getSeatBeltEvents()
    {
        if(seatBeltEvents == null)
            initEvents();
        
        return seatBeltEvents;
    }

    public void setSeatBeltEvents(List<EventReportItem> seatBeltEvents)
    {
        this.seatBeltEvents = seatBeltEvents;
    }

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

    public void ClearEventAction()
    {
        Integer result = eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getEvent().getNoteID());
        if(result >= 1)
            {
                seatBeltEvents.remove(clearItem);
                tableStatsBean.updateSize(seatBeltEvents.size());
            }
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
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_SEATBELT, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("ENTITY_NAME", navigation.getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getSeatBeltEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", getSeatBeltScore() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.setLocale(getUser().getLocale());

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SEATBELT);
        reportCriteria.addChartDataSet(createJasperMultiLineDef(navigation.getVehicle().getVehicleID(), scoreTypes, durationBean.getDuration()));
        reportCriteria.setMainDataset(seatBeltEvents);

        return reportCriteria;
    }

    public String getEmailAddress()
    {
        if(emailAddress == null){
            emailAddress = getProUser().getUser().getPerson().getPriEmail();
        }
        
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReport(), getFacesContext());
    }
}
