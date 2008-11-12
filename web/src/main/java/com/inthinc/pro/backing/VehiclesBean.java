package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.SafetyDevice;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleSensitivity;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.MessageUtil;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView>
{
    private static final List<String>             AVAILABLE_COLUMNS;
    private static final int[]                    DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 12 };

    private static final Map<String, String>      YEARS;
    private static final Map<String, VehicleType> TYPES;
    private static final Map<String, State>       STATES;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("year");
        AVAILABLE_COLUMNS.add("make");
        AVAILABLE_COLUMNS.add("model");
        AVAILABLE_COLUMNS.add("color");
        AVAILABLE_COLUMNS.add("type");
        AVAILABLE_COLUMNS.add("VIN");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("license");
        AVAILABLE_COLUMNS.add("state");
        AVAILABLE_COLUMNS.add("active");
        AVAILABLE_COLUMNS.add("hardAccelerationLevel");
        AVAILABLE_COLUMNS.add("hardTurnLevel");
        AVAILABLE_COLUMNS.add("hardVerticalLevel");

        // years
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        final int nextYear = cal.get(Calendar.YEAR);
        YEARS = new TreeMap<String, String>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.compareTo(o1);
            }
        });
        for (int year = 1970; year <= nextYear; year++)
            YEARS.put(String.valueOf(year), String.valueOf(year));

        // types
        TYPES = new LinkedHashMap<String, VehicleType>();
        for (final VehicleType type : VehicleType.values())
            TYPES.put(type.getDescription(), type);

        // states
        STATES = new TreeMap<String, State>();
        for (final State state : State.values())
            STATES.put(state.getName(), state);
    }

    private VehicleDAO                            vehicleDAO;
    private DriverDAO                             driverDAO;
    private GroupDAO                              groupDAO;
    private TreeMap<String, Integer>              groups;
    private TreeMap<Integer, String>              groupNames;
    private List<Driver>                          drivers;
    private TreeMap<Integer, Boolean>             driverAssigned;

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    @Override
    protected List<VehicleView> loadItems()
    {
        // get the vehicles
        final List<Vehicle> plainVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());

        // convert the Vehicles to VehicleViews
        final LinkedList<VehicleView> items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
            items.add(createVehicleView(vehicle));

        return items;
    }

    /**
     * Creates a VehicleView object from the given Vehicle object.
     * 
     * @param vehicle
     *            The vehicle.
     * @return The new VehicleView object.
     */
    private VehicleView createVehicleView(Vehicle vehicle)
    {
        final VehicleView vehicleView = new VehicleView();
        BeanUtils.copyProperties(vehicle, vehicleView);
        vehicleView.setOldGroupID(vehicle.getGroupID());
        vehicleView.setOldDriverID(vehicle.getDriverID());
        vehicleView.setSelected(false);
        return vehicleView;
    }

    @Override
    protected boolean matchesFilter(VehicleView vehicle, String filterWord)
    {
        for (final String column : getColumns().keySet())
            if (columns.get(column))
            {
                boolean matches = false;
                if (column.equals("driver"))
                    matches = (vehicle.getDriver() != null)
                            && (vehicle.getDriver().getPerson() != null)
                            && (vehicle.getDriver().getPerson().getFirst().toLowerCase().startsWith(filterWord) || vehicle.getDriver().getPerson().getLast().toLowerCase()
                                    .startsWith(filterWord));
                else if (column.equals("group"))
                    matches = (vehicle.getGroup() != null) && vehicle.getGroup().getName().toLowerCase().startsWith(filterWord);
                else if (column.equals("active"))
                    matches = (vehicle.getActive() != null)
                            && ((vehicle.getActive() && MessageUtil.getMessageString("active").toLowerCase().startsWith(filterWord)) || ((!vehicle.getActive() && MessageUtil
                                    .getMessageString("inactive").toLowerCase().startsWith(filterWord))));
                else
                    try
                    {
                        matches = String.valueOf(org.apache.commons.beanutils.BeanUtils.getProperty(vehicle, column.replace('_', '.'))).toLowerCase().startsWith(filterWord);
                    }
                    catch (Exception e)
                    {
                        logger.error("Error filtering on column " + column, e);
                    }

                if (matches)
                    return true;
            }

        return false;
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public void saveColumns()
    {
        // TODO: save the columns
    }

    @Override
    protected VehicleView createAddItem()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setActive(true);
        vehicle.setSafetyDevices(new ArrayList<SafetyDevice>());
        vehicle.setSensitivity(new VehicleSensitivity());
        return createVehicleView(vehicle);
    }

    @Override
    public String cancelEdit()
    {
        getEditItem().setGroupID(getEditItem().getOldGroupID());
        getEditItem().setDriverID(getEditItem().getOldDriverID());
        return super.cancelEdit();
    }

    @Override
    protected void doDelete(List<VehicleView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final VehicleView vehicle : deleteItems)
        {
            vehicleDAO.deleteByID(vehicle.getVehicleID());

            // add a message
            final String summary = MessageUtil.formatMessageString("vehicle_deleted", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    public List<Driver> getDrivers()
    {
        if ((drivers == null) && (getEditItem().getGroupID() != null))
            drivers = driverDAO.getAllDrivers(getEditItem().getGroupID());
        return drivers;
    }

    public TreeMap<Integer, String> getGroupNames()
    {
        if (groupNames == null)
        {
            groupNames = new TreeMap<Integer, String>();
            final GroupHierarchy hierarchy = getProUser().getGroupHierarchy();
            for (final Group group : hierarchy.getGroupList())
                groupNames.put(group.getGroupID(), group.getName());
        }
        return groupNames;
    }

    public TreeMap<Integer, Boolean> getDriverAssigned()
    {
        if (driverAssigned == null)
        {
            driverAssigned = new TreeMap<Integer, Boolean>();
            for (final VehicleView vehicle : getItems())
                if (vehicle.getDriverID() != null)
                    driverAssigned.put(vehicle.getDriverID(), true);
        }
        return driverAssigned;
    }

    public void chooseDriver()
    {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String driverID = params.get("driverID");
        if (driverID != null)
            getEditItem().setDriverID(Integer.parseInt(driverID));

        if (Boolean.parseBoolean(params.get("immediate")) && !isAdd() && !isBatchEdit())
            assignDriver(getEditItem());
    }

    @Override
    protected void doSave(List<VehicleView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final VehicleView vehicle : saveItems)
        {
            if (create)
                vehicle.setVehicleID(vehicleDAO.create(getUser().getPerson().getAccountID(), vehicle));
            else
                vehicleDAO.update(vehicle);

            if (vehicle.isDriverChanged())
                assignDriver(vehicle);

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "vehicle_added" : "vehicle_updated", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    private void assignDriver(final VehicleView vehicle)
    {
        if (vehicle.getDriverID() != null)
            for (final VehicleView otherVehicle : getItems())
                if (vehicle.getDriverID().equals(otherVehicle.getDriverID()) && !otherVehicle.getVehicleID().equals(vehicle.getVehicleID()))
                {
                    vehicleDAO.setVehicleDriver(otherVehicle.getVehicleID(), null);
                    otherVehicle.setDriverID(null);
                    break;
                }

        vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), vehicle.getDriverID());
        vehicle.setOldDriverID(vehicle.getDriverID());

        if (vehicle.getDriverID() != null)
            getDriverAssigned().put(vehicle.getDriverID(), true);
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "go_adminVehicle";
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditVehicle";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminVehicles";
    }

    public Map<String, String> getYears()
    {
        return YEARS;
    }

    public Map<String, VehicleType> getTypes()
    {
        return TYPES;
    }

    public Map<String, State> getStates()
    {
        return STATES;
    }

    public TreeMap<String, Integer> getGroups()
    {
        if (groups == null)
        {
            groups = new TreeMap<String, Integer>();
            final GroupHierarchy hierarchy = getProUser().getGroupHierarchy();
            for (final Group group : hierarchy.getGroupList())
                groups.put(group.getName(), group.getGroupID());
        }
        return groups;
    }

    public class VehicleView extends Vehicle implements EditItem
    {
        @Column(updateable = false)
        private Integer oldGroupID;
        @Column(updateable = false)
        private Group   group;
        @Column(updateable = false)
        private Integer oldDriverID;
        @Column(updateable = false)
        private Driver  driver;
        @Column(updateable = false)
        private boolean selected;

        public Integer getId()
        {
            return getVehicleID();
        }

        Integer getOldGroupID()
        {
            return oldGroupID;
        }

        void setOldGroupID(Integer oldGroupID)
        {
            this.oldGroupID = oldGroupID;
        }

        @Override
        public void setGroupID(Integer groupID)
        {
            super.setGroupID(groupID);
            group = null;

            if ((driver != null) && !driver.getGroupID().equals(groupID))
                setDriverID(null);
            driver = null;
            drivers = null;
        }

        public Group getGroup()
        {
            if (group == null)
                group = groupDAO.findByID(getGroupID());
            return group;
        }

        Integer getOldDriverID()
        {
            return oldDriverID;
        }

        void setOldDriverID(Integer oldDriverID)
        {
            this.oldDriverID = oldDriverID;
        }

        public boolean isDriverChanged()
        {
            return (oldDriverID != getDriverID()) && ((getDriverID() == null) || !getDriverID().equals(oldDriverID));
        }

        @Override
        public void setDriverID(Integer driverID)
        {
            super.setDriverID(driverID);
            driver = null;
        }

        public Driver getDriver()
        {
            if (driver == null)
                driver = driverDAO.findByID(getDriverID());
            return driver;
        }

        public Double getCostPerHourDollars()
        {
            if (getCostPerHour() != null)
                return ((double) getCostPerHour()) / 100;
            return null;
        }

        public void setCostPerHourDollars(Double costPerHourDollars)
        {
            if ((costPerHourDollars != null) && (costPerHourDollars > 0))
                setCostPerHour((int) (costPerHourDollars * 100));
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }
}
