package com.inthinc.pro.reports.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.util.ReportUtil;

public class ReportCriteriaServiceImpl implements ReportCriteriaService
{
    private GroupDAO groupDAO;
    private ScoreDAO scoreDAO;
    private MpgDAO mpgDAO;
    private VehicleDAO vehicleDAO;
    private EventDAO eventDAO;
    private RedFlagDAO redFlagDAO;
  

    private DeviceDAO deviceDAO;

    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImpl.class);

    @Override
    public ReportCriteria getDriverReportCriteria(Integer groupID, Duration duration)
    {
        Group group = groupDAO.findByID(groupID);
        List<DriverReportItem> driverReportItems = scoreDAO.getDriverReportData(groupID, duration);
        for (DriverReportItem driverReportItem : driverReportItems)
        {
            Group tmpGroup = groupDAO.findByID(driverReportItem.getGroupID());
            driverReportItem.setGroup(tmpGroup.getName());

            // Vehicle, none assigned
            if (driverReportItem.getVehicle() == null)
            {
                Vehicle v = new Vehicle();
                v.setName("None Assigned");
                driverReportItem.setVehicle(v);
            }

        }
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_REPORT, group.getName());
        reportCriteria.setMainDataset(driverReportItems);

        return reportCriteria;
    }

    @Override
    public ReportCriteria getMpgReportCriteria(Integer groupID, Duration duration)
    {
        Group group = groupDAO.findByID(groupID);
        List<MpgEntity> entities = mpgDAO.getEntities(group, duration);

        List<CategorySeriesData> seriesData = new ArrayList<CategorySeriesData>();

        for (MpgEntity entity : entities)
        {
            String seriesID = entity.getEntityName();
            Number heavyValue = entity.getHeavyValue();
            Number mediumValue = entity.getMediumValue();
            Number lightValue = entity.getLightValue();
            seriesData.add(new CategorySeriesData("Light", seriesID, lightValue, seriesID));
            seriesData.add(new CategorySeriesData("Medium", seriesID, mediumValue, seriesID));
            seriesData.add(new CategorySeriesData("Heavy", seriesID, heavyValue, seriesID));
        }

        ReportCriteria reportCriteria = new ReportCriteria(ReportType.MPG_GROUP, group.getName());
        reportCriteria.setMainDataset(entities);
        reportCriteria.addChartDataSet(seriesData);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(4, "entityName", "category");
        return reportCriteria;
    }

    @Override
    public ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration)
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, duration, ScoreType.SCORE_OVERALL);

        // TODO: Needs to be optimized by not calling on every report.
        Group group = groupDAO.findByID(groupID);

        String overallScore = format.format((double) ((double) scoreableEntity.getScore() / (double) 10.0));
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.OVERALL_SCORE, group.getName());
        reportCriteria.setMainDataset(getPieScoreData(ScoreType.SCORE_OVERALL, groupID, duration));
        reportCriteria.addParameter("OVERALL_SCORE", overallScore);
        reportCriteria.addParameter("DRIVER_STYLE_DATA", getPieScoreData(ScoreType.SCORE_DRIVING_STYLE, groupID, duration));
        reportCriteria.addParameter("SEATBELT_USE_DATA", getPieScoreData(ScoreType.SCORE_SEATBELT, groupID, duration));
        reportCriteria.addParameter("SPEED_DATA", getPieScoreData(ScoreType.SCORE_SPEEDING, groupID, duration));
        reportCriteria.setDuration(duration);
        return reportCriteria;
    }

    private List<PieScoreData> getPieScoreData(ScoreType scoreType, Integer groupID, Duration duration)
    {
        // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try
        {
            logger.debug("getting scores for groupID: " + groupID);
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(groupID, duration, scoreType);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        List<PieScoreData> pieChartDataList = new ArrayList<PieScoreData>();

        for (int i = 0; i < s.size(); i++)
        {
            PieScoreRange pieScoreRange = PieScoreRange.valueOf(i);
            pieChartDataList.add(new PieScoreData(pieScoreRange.getLabel(), s.get(i).getScore(), pieScoreRange.getLabel()));
        }
        return pieChartDataList;
    }

    @Override
    public ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration)
    {
        List<CategorySeriesData> lineGraphDataList = new ArrayList<CategorySeriesData>();
        List<ScoreableEntity> s = getScores(groupID, duration);
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(groupID, duration);

        List<String> monthList = ReportUtil.createMonthList(duration);

        for (int i = 0; i < groupTrendMap.size(); i++)
        {
            ScoreableEntity se = s.get(i);
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());

            // Not a full range, pad w/ zero
            int holes = 0;
            if (duration == Duration.DAYS)
            {
                holes = duration.getNumberOfDays() - scoreableEntityList.size();
            }
            else
            {
                holes = ReportUtil.convertToMonths(duration) - scoreableEntityList.size();
            }
            int index = 0;
            for (int k = 0; k < holes; k++)
            {
                lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), 0F, se.getIdentifier()));
            }
            for (ScoreableEntity scoreableEntity : scoreableEntityList)
            {
                if(scoreableEntity.getScore() != null && scoreableEntity.getScore() >= 0)
                {
                    Float score = new Float((scoreableEntity.getScore() == null || scoreableEntity.getScore() < 0) ? 5 : scoreableEntity.getScore() / 10.0);
                    lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), score, se.getIdentifier()));
                }
               
            }

        }

        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TREND, group.getName());
        reportCriteria.addChartDataSet(lineGraphDataList);
        reportCriteria.setMainDataset(s);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(8, "identifier", "seriesID");
        return reportCriteria;
    }

    @Override
    public ReportCriteria getVehicleReportCriteria(Integer groupID, Duration duration)
    {
        Group group = groupDAO.findByID(groupID);
        List<VehicleReportItem> vehicleReportItems = scoreDAO.getVehicleReportData(groupID, duration);
        
        for(VehicleReportItem vehicleReportItem:vehicleReportItems)
        {
            Group tmpGroup = groupDAO.findByID(vehicleReportItem.getGroupID());
            vehicleReportItem.setGroup(tmpGroup.getName());
    
            //Driver, none assigned
            if ( vehicleReportItem.getDriver() == null ) {
                Driver d = new Driver();
                Person p = new Person();
                p.setFirst("None");
                p.setLast("Assigned");
                d.setPerson(p);
                vehicleReportItem.setDriver(d);
            }          
        }
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT, group.getName());
        reportCriteria.setMainDataset(vehicleReportItems);

        return reportCriteria;
    }

    private List<ScoreableEntity> getScores(Integer groupID, Duration duration)
    {
        List<ScoreableEntity> s = null;
        try
        {
            s = scoreDAO.getScores(groupID, duration, ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }

        return s;
    }

    @Override
    public ReportCriteria getDevicesReportCriteria(Integer groupID)
    {
        Group group = groupDAO.findByID(groupID);
        List<Vehicle> vehicList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        List<DeviceReportItem> deviceReportItems = new ArrayList<DeviceReportItem>();
        for( Vehicle v: vehicList )
        {
            if ( v.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(v.getDeviceID());            
 
                DeviceReportItem deviceReportItem = new DeviceReportItem();
                
                deviceReportItem.setDevice(dev);
                deviceReportItem.getDevice().setEphone(formatPhone(deviceReportItem.getDevice().getEphone()));
                deviceReportItem.getDevice().setPhone(formatPhone(deviceReportItem.getDevice().getPhone()));
                deviceReportItem.setVehicle(v);
                
                deviceReportItems.add(deviceReportItem);
            }
        }
        
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,group.getName());
        reportCriteria.setMainDataset(deviceReportItems);
        return reportCriteria;
    }
    
    private String formatPhone(String incoming)
    {
        String result = "";

        if (incoming != null)
        {
            result = "(" + incoming.substring(0, 3) + ") " + incoming.substring(3, 6) + "-" + incoming.substring(6, 10);
        }

        return result;
    }

    @Override
    public ReportCriteria getIdlingReportCriteria(Integer groupID,Date startDate,Date endDate)
    {
        Group group = groupDAO.findByID(groupID);
        
            
//        final Calendar endDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        startDate.roll(Calendar.DATE, -7);  //Roll back 7 days

        List<IdlingReportItem> idlingReportItems = scoreDAO.getIdlingReportData(groupID,startDate, endDate);
   
        for ( IdlingReportItem idlingReportItem: idlingReportItems ) {
                    
            //Group name
            Group tmpGroup = groupDAO.findByID(idlingReportItem.getGroupID());
            idlingReportItem.setGroup(tmpGroup.getName());
            
            //Total idling            
            Float tot = idlingReportItem.getLowHrs() + idlingReportItem.getHighHrs();
            idlingReportItem.setTotalHrs(tot);
            
            //Percentages, if any driving
            idlingReportItem.setLowPercent("0.0");
            idlingReportItem.setHighPercent("0.0");
            idlingReportItem.setTotalPercent("0.0");
            Float totHrs = new Float(idlingReportItem.getDriveTime()) +
            idlingReportItem.getLowHrs() + idlingReportItem.getHighHrs();                
            
            if ( totHrs != 0.0f ) {
                Float lo = 100.0f*idlingReportItem.getLowHrs()/totHrs;  
                String fmt = lo.toString();
                idlingReportItem.setLowPercent(fmt.substring(0,3));  
                
                Float hi = 100.0f*idlingReportItem.getHighHrs()/totHrs;
                fmt = hi.toString();
                idlingReportItem.setHighPercent(fmt.substring(0,3));
                
                Float to = 100.0f*idlingReportItem.getTotalHrs()/totHrs;
                fmt = to.toString();
                idlingReportItem.setTotalPercent(fmt.substring(0,3));
            }          
        }   
        
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_REPORT,group.getName());
        reportCriteria.setMainDataset(idlingReportItems);
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getEventsReportCriteria(Integer groupID)
    {
        //List<Event> eventList = eventDAO.getViolationEventsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EVENT_REPORT,tmpGroup.getName());
        //reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getRedFlagsReportCriteria(Integer groupID)
    {
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(groupID, 7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.RED_FLAG_REPORT,tmpGroup.getName());
        reportCriteria.setMainDataset(redFlagList);
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getWarningsReportCriteria(Integer groupID)
    {
        List<Event> eventList = eventDAO.getWarningEventsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.WARNING_REPORT,tmpGroup.getName());
        reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }
    
    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }
    

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO)
    {
        this.redFlagDAO = redFlagDAO;
    }

    public RedFlagDAO getRedFlagDAO()
    {
        return redFlagDAO;
    }


}
