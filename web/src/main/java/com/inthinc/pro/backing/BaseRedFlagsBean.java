package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BaseRedFlagsBean extends BaseBean
{
    private DriverDAO                driverDAO;
    private VehicleDAO               vehicleDAO;

    private Map<Integer, Driver>     driverMap;
    private Map<Integer, Vehicle>    vehicleMap;
    
    private ZoneDAO                  zoneDAO;

    private Map<EventType, String>   driverActionMap;
    private Map<EventType, String>   vehicleActionMap;

    private Event                    selectedEvent;

    private ReportRenderer           reportRenderer;
    private ReportCriteriaService    reportCriteriaService;
    private String                   emailAddress;
    private RedFlagReportItem        selectedRedFlag;

    private Integer                  page;

    protected SearchCoordinationBean searchCoordinationBean;

    public void initBean()
    {
        driverMap = new HashMap<Integer, Driver>();
        vehicleMap = new HashMap<Integer, Vehicle>();
        driverActionMap = new HashMap<EventType, String>();
        driverActionMap.put(EventType.HARD_ACCEL, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_BRAKE, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_TURN, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_VERT, "go_reportDriverStyle");
        driverActionMap.put(EventType.SPEEDING, "go_reportDriverSpeed");
        driverActionMap.put(EventType.SEATBELT, "go_reportDriverSeatBelt");
        driverActionMap.put(EventType.IDLING, "go_driverTrips");
        vehicleActionMap = new HashMap<EventType, String>();
        vehicleActionMap.put(EventType.HARD_ACCEL, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_BRAKE, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_TURN, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_VERT, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.SPEEDING, "go_reportVehicleSpeed");
        vehicleActionMap.put(EventType.SEATBELT, "go_reportVehicleSeatBelt");
        vehicleActionMap.put(EventType.IDLING, "go_vehicleTrips");

    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    protected void fillInDriver(Event event)
    {
        Driver driver = event.getDriver();
        if (driver == null)
        {
            driver = driverMap.get(event.getDriverID());
            if (driver == null)
            {
                driver = driverDAO.findByID(event.getDriverID());
                driverMap.put(event.getDriverID(), driver);
            }
            event.setDriver(driver);
        }
    }

    protected void fillInVehicle(Event event)
    {
        Vehicle vehicle = event.getVehicle();
        if (vehicle == null)
        {
            vehicle = vehicleMap.get(event.getVehicleID());
            if (vehicle == null)
            {
                vehicle = vehicleDAO.findByID(event.getVehicleID());
                vehicleMap.put(event.getVehicleID(), vehicle);
            }
            event.setVehicle(vehicle);
        }
    }

    public String driverAction()
    {
        String action = driverActionMap.get(selectedEvent.getEventType());
        if (action == null)
            return "go_driver";
        return action;
    }

    public String vehicleAction()
    {
        String action = vehicleActionMap.get(selectedEvent.getEventType());
        if (action == null)
            return "go_vehicle";
        return action;
    }

    public Event getSelectedEvent()
    {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent)
    {
        this.selectedEvent = selectedEvent;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public SearchCoordinationBean getSearchCoordinationBean()
    {
        return searchCoordinationBean;
    }

    public void setSearchCoordinationBean(SearchCoordinationBean searchCoordinationBean)
    {
        this.searchCoordinationBean = searchCoordinationBean;
    }

    public void searchAction()
    {
        page = 1;
        filterTableData();

    }
    protected abstract void filterTableDataWithoutSearch();

    protected abstract void filterTableData();

    protected Integer getEffectiveGroupId()
    {

        if (getSearchCoordinationBean().isGoodGroupId())
        {

            return getSearchCoordinationBean().getGroup().getGroupID();
        }
        else
        {

            return getUser().getGroupID();
        }
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getPage()
    {
        return page;
    }

    public RedFlagReportItem getSelectedRedFlag()
    {
        return selectedRedFlag;
    }

    public void setSelectedRedFlag(RedFlagReportItem selectedRedFlag)
    {
        this.selectedRedFlag = selectedRedFlag;
    }

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    public ZoneDAO getZoneDAO()
    {
        return zoneDAO;
    }

}
