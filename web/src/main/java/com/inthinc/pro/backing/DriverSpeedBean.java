package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.Line;
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
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class DriverSpeedBean extends BaseBean
{
    private static final Logger   logger         = Logger.getLogger(DriverSpeedBean.class);
    private NavigationBean        navigation;
    private DurationBean          durationBean;
    private ScoreDAO              scoreDAO;
    private EventDAO              eventDAO;
    private TableStatsBean        tableStatsBean;
    private AddressLookup         addressLookup;

    private Integer               speedScoreOverall;
    private String                speedScoreOverallStyle;
    private Integer               speedScoreTwentyOne;
    private String                speedScoreTwentyOneStyle;
    private Integer               speedScoreThirtyOne;
    private String                speedScoreThirtyOneStyle;
    private Integer               speedScoreFortyOne;
    private String                speedScoreFortyOneStyle;
    private Integer               speedScoreFiftyFive;
    private String                speedScoreFiftyFiveStyle;
    private Integer               speedScoreSixtyFive;
    private String                speedScoreSixtyFiveStyle;

    private String                speedScoreHistoryOverall;
    private String                speedScoreHistoryTwentyOne;
    private String                speedScoreHistoryThirtyOne;
    private String                speedScoreHistoryFortyOne;
    private String                speedScoreHistoryFiftyFive;
    private String                speedScoreHistorySixtyFive;

    private static final Integer  NO_SCORE       = -1;
    private List<EventReportItem> speedingEvents = new ArrayList<EventReportItem>();
    private EventReportItem       clearItem;
    private ReportRenderer        reportRenderer;
    private String                emailAddress;
//    private Boolean               initComplete = false;

    private List<EventReportItem> filteredSpeedingEvents; 	//filtered list
    
    private String 				  selectedSpeed  = "OVERALL";

    private Map<String, List<EventReportItem>> speedingListsMap;
    
    public List<EventReportItem> getFilteredSpeedingEvents() {
		return filteredSpeedingEvents;
	}

	public void setFilteredSpeedingEvents(
			List<EventReportItem> filteredSpeedingEvents) {
		this.filteredSpeedingEvents = filteredSpeedingEvents;
	}

	public String getSelectedSpeed() {
		return selectedSpeed;
	}

	public void setSelectedSpeed(String selectedSpeed) {
		
		this.selectedSpeed = selectedSpeed;
		setFilteredSpeedingEvents(speedingListsMap.get(selectedSpeed));
    	setTableStatsBean();
	}

	private void init()
    {
        if (navigation.getDriver() == null)
        {
            return;
        }
        int driverID = navigation.getDriver().getDriverID();

        Map<ScoreType, ScoreableEntity> scoreMap = scoreDAO.getDriverScoreBreakdownByType(driverID, durationBean.getDuration(), ScoreType.SCORE_SPEEDING);

        ScoreableEntity se = scoreMap.get(ScoreType.SCORE_SPEEDING);
        setSpeedScoreOverall(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_21_30);
        setSpeedScoreTwentyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_31_40);
        setSpeedScoreThirtyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_41_54);
        setSpeedScoreFortyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_55_64);
        setSpeedScoreFiftyFive(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_65_80);
        setSpeedScoreSixtyFive(se == null ? NO_SCORE : se.getScore());
        
        getViolations();
        
//        initComplete = true;
    }

    public void getViolations()
    {
//        if (speedingEvents.isEmpty())
//        {
            List<Event> tempEvents = new ArrayList<Event>();
            List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);

            tempEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), durationBean.getStartDate(), durationBean.getEndDate(), types);
            speedingEvents = new ArrayList<EventReportItem>();
            
            for (Event event : tempEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
                speedingEvents.add(new EventReportItem(event, this.navigation.getDriver().getPerson().getTimeZone()));
            }
            sortSpeedingEvents();
//            filterEventsAction();
        	setTableStatsBean();

//            tableStatsBean.setPage(1);
//            tableStatsBean.setTableRowCount(10);
//            tableStatsBean.setTableSize(speedingEvents.size());
////        }
    }

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getDriverTrendCumulative
                                            (navigation.getDriver().getDriverID(), durationBean.getDuration(), scoreType);

        DateFormat dateFormatter = new SimpleDateFormat(durationBean.getDuration().getDatePattern());

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            }
            else
            {
                sb.append(line.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

    // SPEED OVERALL SCORE PROPERTY
    public Integer getSpeedScoreOverall()
    {
//        if(!initComplete) 
//            init();
//        
        return speedScoreOverall;
    }

    public void setSpeedScoreOverall(Integer speedScoreOverall)
    {
        this.speedScoreOverall = speedScoreOverall;
        setSpeedScoreOverallStyle(ScoreBox.GetStyleFromScore(speedScoreOverall, ScoreBoxSizes.MEDIUM));
    }

    public String getSpeedScoreOverallStyle()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreOverallStyle;
    }

    public void setSpeedScoreOverallStyle(String speedScoreOverallStyle)
    {
        this.speedScoreOverallStyle = speedScoreOverallStyle;
    }

    // SPEED SCORE 21-30 MPH
    public Integer getSpeedScoreTwentyOne()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreTwentyOne;
    }

    public void setSpeedScoreTwentyOne(Integer speedScoreTwentyOne)
    {
        this.speedScoreTwentyOne = speedScoreTwentyOne;
        setSpeedScoreTwentyOneStyle(ScoreBox.GetStyleFromScore(speedScoreTwentyOne, ScoreBoxSizes.MEDIUM));
    }

    public String getSpeedScoreTwentyOneStyle()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreTwentyOneStyle;
    }

    public void setSpeedScoreTwentyOneStyle(String speedScoreTwentyOneStyle)
    {
        this.speedScoreTwentyOneStyle = speedScoreTwentyOneStyle;
    }

    // SPEED SCORE 31-40 MPH
    public Integer getSpeedScoreThirtyOne()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreThirtyOne;
    }

    public void setSpeedScoreThirtyOne(Integer speedScoreThirtyOne)
    {
        setSpeedScoreThirtyOneStyle(ScoreBox.GetStyleFromScore(speedScoreThirtyOne, ScoreBoxSizes.MEDIUM));
        this.speedScoreThirtyOne = speedScoreThirtyOne;
    }

    public String getSpeedScoreThirtyOneStyle()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreThirtyOneStyle;
    }

    public void setSpeedScoreThirtyOneStyle(String speedScoreThirtyOneStyle)
    {
        this.speedScoreThirtyOneStyle = speedScoreThirtyOneStyle;
    }

    // SPEED SCORE 41-54 MPH
    public Integer getSpeedScoreFortyOne()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreFortyOne;
    }

    public void setSpeedScoreFortyOne(Integer speedScoreFortyOne)
    {
        setSpeedScoreFortyOneStyle(ScoreBox.GetStyleFromScore(speedScoreFortyOne, ScoreBoxSizes.MEDIUM));
        this.speedScoreFortyOne = speedScoreFortyOne;
    }

    public String getSpeedScoreFortyOneStyle()
    {
//        if(!initComplete) 
//            init();
//        
        return speedScoreFortyOneStyle;
    }

    public void setSpeedScoreFortyOneStyle(String speedScoreFortyOneStyle)
    {
        this.speedScoreFortyOneStyle = speedScoreFortyOneStyle;
    }

    // SPEED SCORE 55-64 MPH
    public Integer getSpeedScoreFiftyFive()
    {
//        if(!initComplete) 
//            init();
//        
        return speedScoreFiftyFive;
    }

    public void setSpeedScoreFiftyFive(Integer speedScoreFiftyFive)
    {
        setSpeedScoreFiftyFiveStyle(ScoreBox.GetStyleFromScore(speedScoreFiftyFive, ScoreBoxSizes.MEDIUM));
        this.speedScoreFiftyFive = speedScoreFiftyFive;
    }

    public String getSpeedScoreFiftyFiveStyle()
    {
//        if(!initComplete) 
//            init();
        
        return speedScoreFiftyFiveStyle;
    }

    public void setSpeedScoreFiftyFiveStyle(String speedScoreFiftyFiveStyle)
    {
        this.speedScoreFiftyFiveStyle = speedScoreFiftyFiveStyle;
    }

    // SPEED SCORE 65+
    public Integer getSpeedScoreSixtyFive()
    {
//        if(!initComplete) 
//            init();
        
        
        return speedScoreSixtyFive;
    }

    public void setSpeedScoreSixtyFive(Integer speedScoreSixtyFive)
    {
        setSpeedScoreSixtyFiveStyle(ScoreBox.GetStyleFromScore(speedScoreSixtyFive, ScoreBoxSizes.MEDIUM));
        this.speedScoreSixtyFive = speedScoreSixtyFive;
    }

    public String getSpeedScoreSixtyFiveStyle()
    {
//        if(!initComplete) 
//            init();
//        
        return speedScoreSixtyFiveStyle;
    }

    public void setSpeedScoreSixtyFiveStyle(String speedScoreSixtyFiveStyle)
    {
        this.speedScoreSixtyFiveStyle = speedScoreSixtyFiveStyle;
    }

    // OVERALL HISTORY PROPERTY
    public String getSpeedScoreHistoryOverall()
    {
        setSpeedScoreHistoryOverall(createLineDef(ScoreType.SCORE_SPEEDING));
        return speedScoreHistoryOverall;
    }

    public void setSpeedScoreHistoryOverall(String speedScoreHistoryOverall)
    {
        this.speedScoreHistoryOverall = speedScoreHistoryOverall;
    }

    // SCORE HISTORY 21-30 MPH
    public String getSpeedScoreHistoryTwentyOne()
    {
        setSpeedScoreHistoryTwentyOne(createLineDef(ScoreType.SCORE_SPEEDING_21_30));
        return speedScoreHistoryTwentyOne;
    }

    public void setSpeedScoreHistoryTwentyOne(String speedScoreHistoryTwentyOne)
    {
        this.speedScoreHistoryTwentyOne = speedScoreHistoryTwentyOne;
    }

    // SPEED HISTORY 31-40 MPH
    public String getSpeedScoreHistoryThirtyOne()
    {
        setSpeedScoreHistoryThirtyOne(createLineDef(ScoreType.SCORE_SPEEDING_31_40));
        return speedScoreHistoryThirtyOne;
    }

    public void setSpeedScoreHistoryThirtyOne(String speedScoreHistoryThirtyOne)
    {
        this.speedScoreHistoryThirtyOne = speedScoreHistoryThirtyOne;
    }

    // SPEED HISTORY 41-54 MPH
    public String getSpeedScoreHistoryFortyOne()
    {
        setSpeedScoreHistoryFortyOne(createLineDef(ScoreType.SCORE_SPEEDING_41_54));
        return speedScoreHistoryFortyOne;
    }

    public void setSpeedScoreHistoryFortyOne(String speedScoreHistoryFortyOne)
    {
        this.speedScoreHistoryFortyOne = speedScoreHistoryFortyOne;
    }

    // SPEED HISTORY 55-64 MPH
    public String getSpeedScoreHistoryFiftyFive()
    {
        setSpeedScoreHistoryFiftyFive(createLineDef(ScoreType.SCORE_SPEEDING_55_64));
        return speedScoreHistoryFiftyFive;
    }

    public void setSpeedScoreHistoryFiftyFive(String speedScoreHistoryFiftyFive)
    {
        this.speedScoreHistoryFiftyFive = speedScoreHistoryFiftyFive;
    }

    // SPEED HISTORY 65+
    public String getSpeedScoreHistorySixtyFive()
    {
        setSpeedScoreHistorySixtyFive(createLineDef(ScoreType.SCORE_SPEEDING_65_80));
        return speedScoreHistorySixtyFive;
    }

    public void setSpeedScoreHistorySixtyFive(String speedScoreHistorySixtyFive)
    {
        this.speedScoreHistorySixtyFive = speedScoreHistorySixtyFive;
    }

    // DAO PROPERTY
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

    public TableStatsBean getTableStatsBean()
    {
//        getViolations();
        return tableStatsBean;
    }

    public void setTableStatsBean(TableStatsBean tableStatsBean)
    {
        this.tableStatsBean = tableStatsBean;
    }

    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        init();
    }
    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    // SPEEDING EVENTS LIST
    public List<EventReportItem> getSpeedingEvents()
    {
//        getViolations();
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
        Integer temp = eventDAO.forgive(navigation.getDriver().getDriverID(), clearItem.getEvent().getNoteID());
        // logger.debug("Clearing event " + clearItem.getNoteID() + " result: " + temp.toString());

        getViolations();
    }

    public EventReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(EventReportItem clearItem)
    {
        this.clearItem = clearItem;
    }

    // NAVIGATION PROPERTY
    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

//    public Boolean getInitComplete()
//    {
//        return initComplete;
//    }
//
//    public void setInitComplete(Boolean initComplete)
//    {
//        this.initComplete = initComplete;
//    }

    public List<CategorySeriesData> createJasperDef(List<ScoreType> scoreTypes)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType st : scoreTypes)
        {
            List<ScoreableEntity> scoreList = scoreDAO.getDriverTrendCumulative(navigation.getDriver().getDriverID(), durationBean.getDuration(), st);
                 

            List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());
            int count = 0;
            for (ScoreableEntity se : scoreList)
            {
                Double score = null;
                if (se.getScore() != null)
                    score = se.getScore() / 10.0D;

                returnList.add(new CategorySeriesData(MessageUtil.getMessageString(st.toString()), monthList.get(count).toString(), score, monthList.get(count).toString()));

                count++;
            }
        }
        return returnList;
    }

    public ReportCriteria buildReport(ReportType reportType)
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(reportType, getGroupHierarchy().getTopGroup().getName());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter("REPORT_NAME", "Driver Performance: Speed");
        reportCriteria.addParameter("ENTITY_NAME", this.getNavigation().getDriver().getPerson().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getSpeedingEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", this.getSpeedScoreOverall() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.addParameter("SCORE_TWENTYONE", this.getSpeedScoreTwentyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_THIRTYONE", this.getSpeedScoreThirtyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_FOURTYONE", this.getSpeedScoreFortyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_FIFTYFIVE", this.getSpeedScoreFiftyFive() / 10.0D);
        reportCriteria.addParameter("SCORE_SIXTYFIVE", this.getSpeedScoreSixtyFive() / 10.0D);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SPEEDING);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_21_30);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_31_40);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_41_54);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_55_64);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_65_80);
        reportCriteria.addChartDataSet(this.createJasperDef(scoreTypes));
        reportCriteria.setMainDataset(this.speedingEvents);

        return reportCriteria;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
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
	private void setTableStatsBean(){
		
        tableStatsBean.setPage(1);
        tableStatsBean.setTableRowCount(10);
        tableStatsBean.setTableSize(filteredSpeedingEvents.size());

	}
//	public String filterEventsAction(){
//		
//		filteredSpeedingEvents = new ArrayList<EventReportItem>();
//		
//		if (selectedSpeed == 0){
//			
//			filteredSpeedingEvents.addAll(speedingEvents);
//
//		}
//		else {
//			
//			for (EventReportItem eri: speedingEvents){
//				
//				if(eri.getEvent().getEventType().getKey().equals(selectedSpeed)){
//					
//					filteredSpeedingEvents.add(eri);
//				}
//			}
//		}
//    	setTableStatsBean();
//		return "";
//	}
	
	private void sortSpeedingEvents(){
		
		speedingListsMap = new HashMap<String,List<EventReportItem>>();
		List<EventReportItem> speedAll = new ArrayList<EventReportItem>();
		speedingListsMap.put("OVERALL",speedAll);
		List<EventReportItem> speed20 = new ArrayList<EventReportItem>();
		speedingListsMap.put("TWENTYONE",speed20);
		List<EventReportItem> speed30 = new ArrayList<EventReportItem>();
		speedingListsMap.put("THIRTYONE",speed30);
		List<EventReportItem> speed40 = new ArrayList<EventReportItem>();
		speedingListsMap.put("FORTYONE",speed40);
		List<EventReportItem> speed50 = new ArrayList<EventReportItem>();
		speedingListsMap.put("FIFTYFIVE",speed50);
		List<EventReportItem> speed60 = new ArrayList<EventReportItem>();
		speedingListsMap.put("SIXTYFIVE",speed60);
		
		speedAll.addAll(speedingEvents);
		
		for (EventReportItem eri: speedingEvents){
			
			if((((SpeedingEvent)eri.getEvent()).getSpeedLimit() > 20)&&(((SpeedingEvent)eri.getEvent()).getSpeedLimit() < 31)){
				
				speed20.add(eri);
			}
			else if ((((SpeedingEvent)eri.getEvent()).getSpeedLimit() > 30)&&(((SpeedingEvent)eri.getEvent()).getSpeedLimit() < 41)){
				
				speed30.add(eri);
			}
			else if ((((SpeedingEvent)eri.getEvent()).getSpeedLimit() > 40)&&(((SpeedingEvent)eri.getEvent()).getSpeedLimit() < 55)){
				
				speed40.add(eri);
			}
			else if ((((SpeedingEvent)eri.getEvent()).getSpeedLimit() > 54)&&(((SpeedingEvent)eri.getEvent()).getSpeedLimit() < 65)){
				
				speed50.add(eri);
			}
			else if ((((SpeedingEvent)eri.getEvent()).getSpeedLimit() > 64)&&(((SpeedingEvent)eri.getEvent()).getSpeedLimit() < 81)){
				
				speed60.add(eri);
			}
		}
		filteredSpeedingEvents = speedingListsMap.get(selectedSpeed);
	}
}
