package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class VehicleSpeedBean extends BasePerformanceEventsBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4530706169066069581L;
//	private static final Logger                logger         = Logger.getLogger(VehicleSpeedBean.class);


    public VehicleSpeedBean() {
		super();
        selectedBreakdown="SCORE_SPEEDING";
        
        scoreTitle = MessageUtil.getMessageString("vehicle_speed_overall");
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
    protected void initScores()
    {
        Map<ScoreType, ScoreableEntity> tempMap = getPerformanceDataBean().getAverageScoreBreakdown(getVehicle().getVehicleID(), EntityType.ENTITY_VEHICLE, durationBean.getDuration(), ScoreType.SCORE_SPEEDING);

        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        for (ScoreType subType : ScoreType.SCORE_SPEEDING.getSubTypes())
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
        Integer id = getVehicle().getVehicleID();
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
        setDateFormatter();

        List<Event> tempEvents = new ArrayList<Event>();
        List<NoteType> types = EventSubCategory.SPEED.getNoteTypesInSubCategory();

        tempEvents = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types,getShowExcludedEvents());
        events = new ArrayList<EventReportItem>();

        for (Event event : tempEvents)
        {
            event.setAddressStr(getAddress(event.getLatLng()));
            events.add(new EventReportItem(event, getUser().getPerson().getTimeZone(),getMeasurementType(), dateFormatter));
        }
        sortEvents();
      
    }


//	public EventReportItem getClearItem()
//    {
//        return clearItem;
//    }
//
//    public void setClearItem(EventReportItem clearItem)
//    {
//        this.clearItem = clearItem;
//    }

    public ReportCriteria buildReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SPEED, getGroupHierarchy().getTopGroup().getName(), getLocale());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("ENTITY_NAME", getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", eventsListsMap.size());
        reportCriteria.addParameter("OVERALL_SCORE", getScoreMap().get(ScoreType.SCORE_SPEEDING.toString()) / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.addParameter("SCORE_TWENTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_21_30.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_THIRTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_31_40.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FOURTYONE", getScoreMap().get(ScoreType.SCORE_SPEEDING_41_54.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_FIFTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_55_64.toString()) / 10.0D);
        reportCriteria.addParameter("SCORE_SIXTYFIVE", getScoreMap().get(ScoreType.SCORE_SPEEDING_65_80.toString()) / 10.0D);
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SPEEDING);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_21_30);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_31_40);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_41_54);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_55_64);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_65_80);
        if (durationBean.getDuration() == Duration.DAYS){
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDefDays(getVehicle().getVehicleID(), scoreTypes));
        }
        else {
        	
        	reportCriteria.addChartDataSet(createJasperMultiLineDef(getVehicle().getVehicleID(), scoreTypes, durationBean.getDuration()));
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
//   public void exportReportToPdf()
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
        List<EventReportItem> speedAll = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING.toString(), speedAll);
        List<EventReportItem> speed20 = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), speed20);
        List<EventReportItem> speed30 = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), speed30);
        List<EventReportItem> speed40 = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), speed40);
        List<EventReportItem> speed50 = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), speed50);
        List<EventReportItem> speed60 = new ArrayList<EventReportItem>();
        eventsListsMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), speed60);

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
   
}
