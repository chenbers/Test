package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView> implements PersonChangeListener, Serializable
{
    private static final long                     serialVersionUID       = 1L;

    private static final List<String>             AVAILABLE_COLUMNS;
    private static final int[]                    DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 12, 13 };

    private static final Map<String, String>      YEARS;
    private static final Map<String, State>       STATES;
    private Map<Integer, Device>                  devices;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("driverID");
        AVAILABLE_COLUMNS.add("groupID");
        AVAILABLE_COLUMNS.add("year");
        AVAILABLE_COLUMNS.add("make");
        AVAILABLE_COLUMNS.add("model");
        AVAILABLE_COLUMNS.add("color");
        AVAILABLE_COLUMNS.add("vtype");
        AVAILABLE_COLUMNS.add("VIN");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("license");
        AVAILABLE_COLUMNS.add("state");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("deviceID");

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

        // states
        STATES = new TreeMap<String, State>();
        for (final State state : States.getStates().values())
            STATES.put(state.getName(), state);
    }

    private VehicleDAO                            vehicleDAO;
    private DriverDAO                             driverDAO;
    private DeviceDAO                             deviceDAO;
    private List<Driver>                          drivers;
    private TreeMap<Integer, Boolean>             driverAssigned;
    
    private CacheBean cacheBean;
    
    public CacheBean getCacheBean() {
		return cacheBean;
	}

	public void setCacheBean(CacheBean cacheBean) {
		this.cacheBean = cacheBean;
	}


    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    @Override
    protected List<VehicleView> loadItems()
    {
        // Get all the vehicles
        final List<Vehicle> plainVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());
        
        // Get all the devices
        final List<Device> plainDevices = deviceDAO.getDevicesByAcctID(getAccountID());
        
        // Map all devices by deviceID
        devices = new HashMap<Integer, Device>();
        for (final Device device : plainDevices)
        {
            devices.put(device.getDeviceID(), device);
        }
        
        // Wrap Vehicles and Devices
        final LinkedList<VehicleView> items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
        {
            VehicleView view = createVehicleView(vehicle);
            view.setDevice(devices.get(vehicle.getDeviceID()));
            
            items.add(view);   
        }

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
        vehicleView.bean = this;
        BeanUtils.copyProperties(vehicle, vehicleView);
        vehicleView.setOldGroupID(vehicle.getGroupID());
        vehicleView.setOldDriverID(vehicle.getDriverID());
        vehicleView.setSelected(false);
        return vehicleView;
    }

    @Override
    public String fieldValue(VehicleView vehicle, String column)
    {
        if (column.equals("driverID"))
        {
            if ((vehicle.getDriver() != null) && (vehicle.getDriver().getPerson() != null))
                return vehicle.getDriver().getPerson().getFullName();
            return null;
        }
        else if (column.equals("groupID"))
        {
            if (vehicle.getGroup() != null)
                return vehicle.getGroup().getName();
        }
        else if (column.equals("status"))
        {
            if (vehicle.getStatus() != null)
                return MessageUtil.getMessageString(vehicle.getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("deviceID"))
        {
            if(vehicle.getDevice() != null)
                return vehicle.getDevice().getName();
            return null;
        }

        return super.fieldValue(vehicle, column);
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
    public String getColumnLabelPrefix()
    {
        return "vehiclesHeader_";
    }
   
    @Override
    protected Boolean authorizeAccess(VehicleView item)
    {
        Group group = getGroupHierarchy().getGroup(item.getGroupID());

        if (group != null)
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_VEHICLES;
    }

    @Override
    protected VehicleView createAddItem()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setStatus(Status.ACTIVE);
        return createVehicleView(vehicle);
    }

    @Override
    public String batchEdit()
    {
        final String redirect = super.batchEdit();
        if (getItem().getDevice() == null)
            for (final VehicleView vehicle : getSelectedItems())
                if (vehicle.getDevice() != null)
                {
                    final Device device = new Device();
                    getItem().device = device;
                    break;
                }
        return redirect;
    }

    @Override
    public String cancelEdit()
    {
        getItem().setGroupID(getItem().getOldGroupID());
        getItem().setDriverID(getItem().getOldDriverID());
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
        if (drivers == null)
            drivers = driverDAO.getAllDrivers(getUser().getGroupID());
        return drivers;
    }

    @Override
    public void personListChanged()
    {
        drivers = null;
    }
    
    public Integer getAccountID()
    {
        return getProUser().getGroupHierarchy().getTopGroup().getAccountID();

    }
    
    public TreeMap<Integer, String> getGroupNames()
    {
        final TreeMap<Integer, String> groupNames = new TreeMap<Integer, String>();
        for (final Group group : getGroupHierarchy().getGroupList())
            groupNames.put(group.getGroupID(), group.getName());
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
            getItem().setDriverID(Integer.parseInt(driverID));

        if (Boolean.parseBoolean(params.get("immediate")) && !isAdd() && !isBatchEdit())
            assignDriver(getItem());

        drivers = null;
    }

    @Override
    public String save()
    {
        // prefix the sensitivity settings with "device."
        final Map<String, Boolean> updateField = getUpdateField();
        Map<String,Boolean> tempUpdateField = new HashMap<String, Boolean>();
        
        
        if (updateField != null)
        {
            for (final String key : updateField.keySet())
            {
                if (key.startsWith("hard"))
                {
                    tempUpdateField.put("device." + key,  updateField.get(key));
                }
            }
            
            for(final String key : tempUpdateField.keySet())
            {
                updateField.put(key, tempUpdateField.get(key));
            }
        }
        
        updateField.put("device.speedSettings", false);
        updateField.put("device.deviceID", false);
        updateField.put("device.accountID", false);
        updateField.put("device.vehicleID", false);
        updateField.put("device.status", false);
        updateField.put("device.name", false);
        updateField.put("device.imei", false);
        updateField.put("device.sim", false);

        // if ((getItem().getDevice() != null) && getItem().getDevice().isSensitivitiesInverted())
        // getItem().getDevice().invertSensitivities();

        // TODO: this should be refactored, but to be on the save side, just clear the cache and force a refresh when vehicle(s) change
        cacheBean.setVehicleMap(null);
        return super.save();
    }
    
    
    @Override
    protected boolean validateSaveItem(VehicleView vehicle)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        final String required = "required";
        // Required fields check
        if(vehicle.getMake() == null || vehicle.getMake().equals("")
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("make"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-make", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        
        if(vehicle.getModel() == null || vehicle.getModel().equals("")
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("model"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-model", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        
        if((vehicle.getGroupID() == null)
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("groupID"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-groupID", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        
        // unique VIN
        if (!isBatchEdit()){
            final Vehicle byVIN = vehicleDAO.findByVIN(vehicle.getVIN());
            if ((byVIN != null) && !byVIN.getVehicleID().equals(vehicle.getVehicleID()))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editVehicle_uniqueVIN");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:editVehicle-VIN", message);
            }
        }
        
        return valid;
    }

    @Override
    protected VehicleView revertItem(VehicleView editItem)
    {
        return createVehicleView(vehicleDAO.findByID(editItem.getVehicleID()));
    }

    @Override
    protected void doSave(List<VehicleView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final VehicleView vehicle : saveItems)
        {
            if (create)
                vehicle.setVehicleID(vehicleDAO.create(vehicle.getGroupID(), vehicle));
            else
                vehicleDAO.update(vehicle);
            vehicle.setOldGroupID(vehicle.getGroupID());

            if (vehicle.isDriverChanged())
                assignDriver(vehicle);

            if (vehicle.getDevice() != null && vehicle.getDeviceID() != null && vehicle.getDeviceID() != 0)
            {
                // if batch editing, copy individual speed settings by hand
                if (isBatchEdit())
                {
                    final Map<String, Boolean> updateField = getUpdateField();
                    for (final String key : updateField.keySet())
                        if (key.startsWith("speed") && (key.length() <= 7) && (updateField.get(key) == true))
                        {
                            final int index = Integer.parseInt(key.substring(5));
                            vehicle.getDevice().getSpeedSettings()[index] = getItem().getDevice().getSpeedSettings()[index];
                        }
                }

                vehicle.getDevice().setDeviceID(vehicle.getDeviceID()); //TODO Patch Work - why isn't the device id already there????
                deviceDAO.update(vehicle.getDevice());
            }

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "vehicle_added" : "vehicle_updated", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }

        drivers = null;
    }

    private void assignDriver(final VehicleView vehicle)
    {
        vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), vehicle.getDriverID());
        vehicle.setOldDriverID(vehicle.getDriverID());

        if (vehicle.getDriverID() != null)
            getDriverAssigned().put(vehicle.getDriverID(), true);
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "pretty:adminVehicle";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditVehicle";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminVehicles";
    }

    public Map<String, String> getYears()
    {
        return YEARS;
    }

    public List<SelectItem> getTypes()
    {
        return SelectItemUtil.toList(VehicleType.class, false);
    }

    public Map<String, State> getStates()
    {
        return STATES;
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public TreeMap<String, Integer> getTeams()
    {
        final TreeMap<String, Integer> teams = new TreeMap<String, Integer>();
        for (final Group group : getGroupHierarchy().getGroupList())
            if (group.getType() == GroupType.TEAM)
                teams.put(group.getName(), group.getGroupID());
        return teams;
    }

    public static class VehicleView extends Vehicle implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = -2051727502162908991L;

        @Column(updateable = false)
        private VehiclesBean      bean;
        @Column(updateable = false)
        private Integer           oldGroupID;
        @Column(updateable = false)
        private Group             group;
        @Column(updateable = false)
        private Integer           oldDriverID;
        @Column(updateable = false)
        private Driver            driver;
        @Column(updateable = false)
        private Device            device;
        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getVehicleID();
        }

        @Override
        public Integer getWeight()
        {
            final Integer weight = super.getWeight();
            if ((weight != null) && (weight == 0))
                return null;
            return weight;
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
            driver = null;
            bean.drivers = null;
        }

        public Group getGroup()
        {
            if (group == null)
                group = bean.groupDAO.findByID(getGroupID());
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
                driver = bean.driverDAO.findByID(getDriverID());
            return driver;
        }

        public Device getDevice()
        {
            if (device == null)
                device = bean.deviceDAO.findByID(getDeviceID());
            return device;
        }
        
        public void setDevice(Device device)
        {
            this.device = device;
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
