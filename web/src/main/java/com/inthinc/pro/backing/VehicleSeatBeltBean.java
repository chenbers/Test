package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSeatBeltBean extends BaseBean
{
    private static final Logger   logger         = Logger.getLogger(VehicleSeatBeltBean.class);

    private NavigationBean        navigation;
    private DurationBean          durationBean;
    private ScoreDAO              scoreDAO;
    private EventDAO              eventDAO;
    private TableStatsBean        tableStatsBean;
    private AddressLookup         addressLookup;

    private Integer               seatBeltScore;
    private String                seatBeltScoreHistoryOverall;
    private String                seatBeltScoreStyle;
    private EventReportItem       clearItem;
    private ReportRenderer        reportRenderer;
    private String                emailAddress;

    private List<EventReportItem> seatBeltEvents = new ArrayList<EventReportItem>();

    private void init()
    {
        ScoreableEntity seatBeltSe = scoreDAO.getVehicleAverageScoreByType(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_SEATBELT);
        if (seatBeltSe == null)
            setSeatBeltScore(-1);
        else
            setSeatBeltScore(seatBeltSe.getScore());
    }

    public void getViolations()
    {
        if (seatBeltEvents.isEmpty())
        {
            List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);

            List<Event> tempEvents = new ArrayList<Event>();
            tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types);

            for (Event event : tempEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
                seatBeltEvents.add(new EventReportItem(event, getUser().getPerson().getTimeZone()));
            }

            tableStatsBean.setPage(1);
            tableStatsBean.setTableRowCount(10);
            tableStatsBean.setTableSize(seatBeltEvents.size());
        }
    }

    // SCORE PROPERTIES
    public Integer getSeatBeltScore()
    {
        return seatBeltScore;
    }

    public void setSeatBeltScore(Integer seatBeltScore)
    {
        this.seatBeltScore = seatBeltScore;
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(seatBeltScore, ScoreBoxSizes.MEDIUM));
    }

    // SCORE HISTORY PROPERTIES
    public String getSeatBeltScoreHistoryOverall()
    {
        setSeatBeltScoreHistoryOverall(createLineDef(ScoreType.SCORE_SEATBELT));
        return seatBeltScoreHistoryOverall;
    }

    public void setSeatBeltScoreHistoryOverall(String seatBeltScoreHistoryOverall)
    {
        this.seatBeltScoreHistoryOverall = seatBeltScoreHistoryOverall;
    }

    // SCOREBOX STYLE PROPERTIES
    public String getSeatBeltScoreStyle()
    {
        return seatBeltScoreStyle;
    }

    public void setSeatBeltScoreStyle(String seatBeltScoreStyle)
    {
        this.seatBeltScoreStyle = seatBeltScoreStyle;
    }

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), scoreType, GraphicUtil
                .getDurationSize(durationBean.getDuration()));

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

    // DAO PROPERTIES
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

    // SEATBELT EVENTS LIST
    public List<EventReportItem> getSeatBeltEvents()
    {
        getViolations();
        return seatBeltEvents;
    }

    public void setSeatBeltEvents(List<EventReportItem> seatBeltEvents)
    {
        this.seatBeltEvents = seatBeltEvents;
    }

    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        init();
        seatBeltEvents.clear();
        getViolations();
    }
    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    // NAVIGATION BEAN PROPERTIES
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

    public TableStatsBean getTableStatsBean()
    {
        getViolations();
        return tableStatsBean;
    }

    public void setTableStatsBean(TableStatsBean tableStatsBean)
    {
        this.tableStatsBean = tableStatsBean;
    }

    public void ClearEventAction()
    {
        Integer temp = eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getEvent().getNoteID());
        // logger.debug("Clearing event " + clearItem.getNoteID() + " result: " + temp.toString());

        seatBeltEvents.clear();
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

    public List<CategorySeriesData> createJasperDef(List<ScoreType> scoreTypes)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType st : scoreTypes)
        {
            List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), st, GraphicUtil
                    .getDurationSize(durationBean.getDuration()));

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

    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SEATBELT, getGroupHierarchy().getTopGroup().getName());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Vehicle Performance: Seat Belt");
        reportCriteria.addParameter("ENTITY_NAME", this.getNavigation().getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", this.getSeatBeltEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", this.getSeatBeltScore() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SEATBELT);
        reportCriteria.addChartDataSet(this.createJasperDef(scoreTypes));
        reportCriteria.setMainDataset(this.seatBeltEvents);

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
