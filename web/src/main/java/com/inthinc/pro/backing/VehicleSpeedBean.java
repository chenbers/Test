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
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSpeedBean extends BasePerformanceBean
{
    private static final Logger                logger         = Logger.getLogger(VehicleSpeedBean.class);

    private ScoreDAO                           scoreDAO;
    private EventDAO                           eventDAO;
    private EventReportItem                    clearItem;
    private String                             emailAddress;
    private String                             selectedSpeed  = "OVERALL";
    private List<EventReportItem>              filteredSpeedingEvents;
    private List<EventReportItem>              speedingEvents;
    private Map<String, List<EventReportItem>> speedingListsMap;

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
        Map<ScoreType, ScoreableEntity> tempMap = scoreDAO.getVehicleScoreBreakdownByType(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_SPEEDING);

        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        ScoreableEntity se = tempMap.get(ScoreType.SCORE_SPEEDING);
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

    private void initTrends()
    {
        Integer id = navigation.getVehicle().getVehicleID();
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_SPEEDING.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING));
        trendMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_21_30));
        trendMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_31_40));
        trendMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_41_54));
        trendMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_55_64));
        trendMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), createFusionMultiLineDef(id, durationBean.getDuration(), ScoreType.SCORE_SPEEDING_65_80));
    }

    public void initEvents() 
    {
        List<Event> tempEvents = new ArrayList<Event>();
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);

        tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types);
        speedingEvents = new ArrayList<EventReportItem>();

        for (Event event : tempEvents)
        {
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            speedingEvents.add(new EventReportItem(event, getUser().getPerson().getTimeZone()));
        }
        sortSpeedingEvents();
        setTableStatsBean();
    }

    public List<EventReportItem> getFilteredSpeedingEvents()
    {
        if (filteredSpeedingEvents == null)
            initEvents();

        return filteredSpeedingEvents;
    }

    public void setFilteredSpeedingEvents(List<EventReportItem> filteredSpeedingEvents)
    {
        this.filteredSpeedingEvents = filteredSpeedingEvents;
    }

    public String getSelectedSpeed()
    {
        return selectedSpeed;
    }

    public void setSelectedSpeed(String selectedSpeed)
    {
        this.selectedSpeed = selectedSpeed;
        setFilteredSpeedingEvents(speedingListsMap.get(selectedSpeed));
        setTableStatsBean();
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

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    public Map<String, List<EventReportItem>> getSpeedingListsMap()
    {
        return speedingListsMap;
    }

    public void setSpeedingListsMap(Map<String, List<EventReportItem>> speedingListsMap)
    {
        this.speedingListsMap = speedingListsMap;
    }

    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        initScores();
        initTrends();
        initEvents();
        logger.debug("VehicleSpeedBean - setDuration called");
    }

    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    public List<EventReportItem> getSpeedingEvents()
    {
        return speedingEvents;
    }

    public void setSpeedingEvents(List<EventReportItem> speedingEvents)
    {
        this.speedingEvents = speedingEvents;
        sortSpeedingEvents();
        setTableStatsBean();
    }

    public void clearEventAction()
    {
        eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getEvent().getNoteID());
        initEvents();
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
        if(trendMap == null)
            initTrends();
        
        return trendMap;
    }



    public ReportCriteria buildReport(ReportType reportType)
    {
        ReportCriteria reportCriteria = new ReportCriteria(reportType, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("REPORT_NAME", "Vehicle Performance: Speed");
        reportCriteria.addParameter("ENTITY_NAME", this.getNavigation().getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", speedingListsMap.size());
        reportCriteria.addParameter("OVERALL_SCORE", getScoreMap().get(ScoreType.SCORE_SPEEDING.toString()) / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.addParameter("SCORE_TWENTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_21_30.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_THIRTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_31_40.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FOURTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_41_54.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FIFTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_55_64.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_SIXTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_65_80.toString()) / 10.0D);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SPEEDING);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_21_30);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_31_40);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_41_54);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_55_64);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_65_80);
        reportCriteria.addChartDataSet(createJasperMultiLineDef(navigation.getVehicle().getVehicleID(), scoreTypes, durationBean.getDuration()));
        reportCriteria.setMainDataset(speedingEvents);

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
        getReportRenderer().exportSingleReportToPDF(buildReport(ReportType.VEHICLE_SPEED), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(ReportType.VEHICLE_SPEED), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReport(ReportType.VEHICLE_SPEED), getFacesContext());
    }

    private void setTableStatsBean()
    {
        tableStatsBean.setPage(1);
        tableStatsBean.setTableRowCount(10);
        tableStatsBean.setTableSize(filteredSpeedingEvents.size());
    }

    // public String filterEventsAction(){
    //      
    // filteredSpeedingEvents = new ArrayList<EventReportItem>();
    //      
    // if (selectedSpeed == 0){
    //          
    // filteredSpeedingEvents.addAll(speedingEvents);
    //
    // }
    // else {
    //          
    // for (EventReportItem eri: speedingEvents){
    //              
    // if(eri.getEvent().getEventType().getKey().equals(selectedSpeed)){
    //                  
    // filteredSpeedingEvents.add(eri);
    // }
    // }
    // }
    // setTableStatsBean();
    // return "";
    // }

    private void sortSpeedingEvents()
    {
        speedingListsMap = new HashMap<String, List<EventReportItem>>();
        List<EventReportItem> speedAll = new ArrayList<EventReportItem>();
        speedingListsMap.put("OVERALL", speedAll);
        List<EventReportItem> speed20 = new ArrayList<EventReportItem>();
        speedingListsMap.put("TWENTYONE", speed20);
        List<EventReportItem> speed30 = new ArrayList<EventReportItem>();
        speedingListsMap.put("THIRTYONE", speed30);
        List<EventReportItem> speed40 = new ArrayList<EventReportItem>();
        speedingListsMap.put("FORTYONE", speed40);
        List<EventReportItem> speed50 = new ArrayList<EventReportItem>();
        speedingListsMap.put("FIFTYFIVE", speed50);
        List<EventReportItem> speed60 = new ArrayList<EventReportItem>();
        speedingListsMap.put("SIXTYFIVE", speed60);

        speedAll.addAll(speedingEvents);

        SpeedingEvent event;
        for (EventReportItem eri : speedingEvents)
        {
            event = (SpeedingEvent)eri.getEvent();
            
            if(event.getSpeedLimit() == null)
                continue;
            
            if (event.getSpeedLimit() > 20 && event.getSpeedLimit() < 31)
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
        filteredSpeedingEvents = speedingListsMap.get(selectedSpeed);
    }
}
