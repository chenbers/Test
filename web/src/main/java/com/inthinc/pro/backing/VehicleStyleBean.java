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

public class VehicleStyleBean extends BasePerformanceBean
{
    private static final Logger   logger            = Logger.getLogger(VehicleStyleBean.class);

    private ScoreDAO              scoreDAO;
    private EventDAO              eventDAO;
    private EventReportItem       clearItem;
    private String                emailAddress;
    private List<EventReportItem> styleEvents;
    private List<EventReportItem> filteredStyleEvents;
    private String                selectedEventType = "";
    private final Integer         ROWCOUNT          = 10;

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
        Map<ScoreType, ScoreableEntity> tempMap = scoreDAO
                .getVehicleScoreBreakdownByType(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE);

        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();

        ScoreableEntity se = tempMap.get(ScoreType.SCORE_DRIVING_STYLE);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), se.getScore());
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

        se = tempMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), se.getScore());
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

        se = tempMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), se.getScore());
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

        se = tempMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), se.getScore());
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));

        se = tempMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), se.getScore());
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), ScoreBox.GetStyleFromScore(se.getScore(), ScoreBoxSizes.MEDIUM));
    }

    private void initTrends()
    {
        Integer id = navigation.getVehicle().getVehicleID();
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));
    }

    public void initEvents()
    {
        List<Event> tempEvents = new ArrayList<Event>();
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

        tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types);
        styleEvents = new ArrayList<EventReportItem>();

        for (Event event : tempEvents)
        {
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            styleEvents.add(new EventReportItem(event, getUser().getPerson().getTimeZone()));
        }
        filterEventsAction();
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

    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        initScores();
        initTrends();
        initEvents();
        tableStatsBean.reset(ROWCOUNT, filteredStyleEvents.size());
    }

    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    public List<EventReportItem> getStyleEvents()
    {
        if(styleEvents == null)
            initEvents();
            
        return styleEvents;
    }

    public void setStyleEvents(List<EventReportItem> styleEvents)
    {
        this.styleEvents = styleEvents;
        filterEventsAction();
    }

    public void ClearEventAction()
    {
        Integer result = eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getEvent().getNoteID());
        if(result >= 1)
            {
                filteredStyleEvents.remove(clearItem);
                tableStatsBean.updateSize(filteredStyleEvents.size());
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

    @Override
    public Map<String, Integer> getScoreMap()
    {
        if (scoreMap == null)
            initScores();

        return scoreMap;
    }

    @Override
    public Map<String, String> getStyleMap()
    {
        if (styleMap == null)
            initScores();

        return styleMap;
    }

    @Override
    public Map<String, String> getTrendMap()
    {
        if (trendMap == null)
            initTrends();

        return trendMap;
    }

    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_STYLE, getGroupHierarchy().getTopGroup().getName());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("ENTITY_NAME", getNavigation().getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getStyleEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()) / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_style"));
        reportCriteria.addParameter("SCORE_HARDBRAKE", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDACCEL", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDTURN", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_HARDBUMP", getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()) / 10.0D);
        reportCriteria.setLocale(getUser().getLocale());

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        reportCriteria.addChartDataSet(createJasperMultiLineDef(navigation.getVehicle().getVehicleID(), scoreTypes, durationBean.getDuration()));
        reportCriteria.setMainDataset(getStyleEvents());

        return reportCriteria;
    }

    public String getEmailAddress()
    {
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

    public List<EventReportItem> getFilteredStyleEvents()
    {
        if(filteredStyleEvents == null)
        {
            initEvents();
            filterEventsAction();
            tableStatsBean.reset(ROWCOUNT, filteredStyleEvents.size());
        }
            
        return filteredStyleEvents;
    }

    public void setFilteredStyleEvents(List<EventReportItem> filteredStyleEvents)
    {
        this.filteredStyleEvents = filteredStyleEvents;
    }

    public String getSelectedEventType()
    {
        return selectedEventType;
    }

    public void setSelectedEventType(String selectedEventType)
    {
        this.selectedEventType = selectedEventType;
    }

    public String filterEventsAction()
    {
        filteredStyleEvents = new ArrayList<EventReportItem>();

        if (selectedEventType.isEmpty())
        {
            
            filteredStyleEvents.addAll(getStyleEvents());
        }
        else
        {
            for (EventReportItem eri : getStyleEvents())
            {
                if (eri.getEvent().getEventType().name().equals(selectedEventType))
                {
                    filteredStyleEvents.add(eri);
                }
            }
        }
        tableStatsBean.reset(ROWCOUNT, filteredStyleEvents.size());
        return "";
    }
}
