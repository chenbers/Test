package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

/**
 * @author David Gileadi
 */
public class DevicesBean extends BaseAdminBean<DevicesBean.DeviceView>
{
    private static final List<String>              AVAILABLE_COLUMNS;
    private static final int[]                     DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 4, 7 };

    private static final Map<String, DeviceStatus> STATUSES;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("imei");
        AVAILABLE_COLUMNS.add("sim");
        AVAILABLE_COLUMNS.add("phone");
        AVAILABLE_COLUMNS.add("ephone");
        AVAILABLE_COLUMNS.add("activated");
        AVAILABLE_COLUMNS.add("status");

        // statuses
        STATUSES = new LinkedHashMap<String, DeviceStatus>();
        for (final DeviceStatus status : DeviceStatus.values())
            if (status != DeviceStatus.DELETED)
                STATUSES.put(MessageUtil.getMessageString("status" + status.getCode()), status);
    }

    private DeviceDAO                              deviceDAO;
    private VehicleDAO                             vehicleDAO;
    private VehiclesBean                           vehiclesBean;

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setVehiclesBean(VehiclesBean vehiclesBean)
    {
        this.vehiclesBean = vehiclesBean;
    }

    @Override
    protected List<DeviceView> loadItems()
    {
        // get the devices
        final List<Device> plainDevices = deviceDAO.getDevicesByAcctID(getAccountID());

        // convert the Devices to DeviceViews
        final LinkedList<DeviceView> items = new LinkedList<DeviceView>();
        for (final Device device : plainDevices)
            items.add(createDeviceView(device));

        return items;
    }

    /**
     * Creates a DeviceView object from the given Device object.
     * 
     * @param device
     *            The device.
     * @return The new DeviceView object.
     */
    private DeviceView createDeviceView(Device device)
    {
        final DeviceView deviceView = new DeviceView();
        BeanUtils.copyProperties(device, deviceView);
        deviceView.setVehicleDAO(vehicleDAO);
        deviceView.setOldVehicleID(device.getVehicleID());
        deviceView.setSelected(false);
        return deviceView;
    }

    @Override
    protected boolean matchesFilter(DeviceView device, String filterWord)
    {
        for (final String column : getTableColumns().keySet())
            if (getTableColumns().get(column).getVisible())
            {
                boolean matches = false;
                if (column.equals("vehicle"))
                    matches = (device.getVehicle() != null) && (device.getVehicle().getName() != null) && device.getVehicle().getName().toLowerCase().startsWith(filterWord);
                else if (column.equals("status"))
                    matches = (device.getStatus() != null) && MessageUtil.getMessageString(device.getStatus().getDescription().toLowerCase()).toLowerCase().startsWith(filterWord);

                if (matches)
                    return true;
            }

        return super.matchesFilter(device, filterWord);
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
        return "devicesHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_DEVICES;
    }

    @Override
    protected DeviceView createAddItem()
    {
        final Device device = new Device();
        device.setStatus(DeviceStatus.NEW);
        device.setSpeedSettings(new Integer[Device.NUM_SPEEDS]);
        return createDeviceView(device);
    }

    @Override
    public DeviceView getItem()
    {
        final DeviceView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[Device.NUM_SPEEDS]);
        return item;
    }

    @Override
    public String cancelEdit()
    {
        getItem().setVehicleID(getItem().getOldVehicleID());
        return super.cancelEdit();
    }

    @Override
    protected void doDelete(List<DeviceView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final DeviceView device : deleteItems)
        {
            if (device.getVehicleID() != null)
                setVehicleDevice(device.getVehicleID(), null);

            deviceDAO.deleteByID(device.getDeviceID());

            // add a message
            final String summary = MessageUtil.formatMessageString("device_deleted", device.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    public List<VehicleView> getVehicles()
    {
        return vehiclesBean.getItems();
    }

    public void chooseVehicle()
    {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String vehicleID = params.get("vehicleID");
        if (vehicleID != null)
            getItem().setVehicleID(Integer.parseInt(vehicleID));

        if (Boolean.parseBoolean(params.get("immediate")) && !isAdd() && !isBatchEdit())
            assignVehicle(getItem());
    }

    @Override
    public String save()
    {
        
        return super.save();
    }

    @Override
    protected boolean validate(List<DeviceView> saveItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;

        for (final DeviceView device : saveItems)
        {
            // unique IMEI
            final Device byImei = deviceDAO.findByIMEI(device.getImei());
            if ((byImei != null) && !byImei.getDeviceID().equals(device.getDeviceID()))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_uniqueImei");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:imei", message);
            }
        }
        return valid;
    }

    @Override
    protected void doSave(List<DeviceView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final DeviceView device : saveItems)
        {
            // if batch editing, copy individual speed settings by hand
            if (isBatchEdit())
            {
                final Map<String, Boolean> updateField = getUpdateField();
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7) && (updateField.get(key) == true))
                    {
                        final int index = Integer.parseInt(key.substring(5));
                        device.getSpeedSettings()[index] = getItem().getSpeedSettings()[index];
                    }
            }

            if (create)
                device.setDeviceID(deviceDAO.create(getAccountID(), device));
            else
                deviceDAO.update(device);

            if (device.isVehicleChanged())
                assignVehicle(device);

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "device_added" : "device_updated", device.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    private void assignVehicle(final DeviceView device)
    {
        if (device.getOldVehicleID() != null)
        {
            vehicleDAO.clearVehicleDevice(device.getOldVehicleID(), device.getDeviceID());
            setVehicleDevice(device.getOldVehicleID(), null);
        }

        if (device.getVehicleID() != null)
            for (final DeviceView otherDevice : getItems())
                if (device.getVehicleID().equals(otherDevice.getVehicleID()) && !otherDevice.getDeviceID().equals(device.getDeviceID()))
                {
                    otherDevice.setVehicleID(null);
                    break;
                }

        vehicleDAO.setVehicleDevice(device.getVehicleID(), device.getDeviceID());
        setVehicleDevice(device.getVehicleID(), device.getDeviceID());
        device.setOldVehicleID(device.getVehicleID());
    }

    private void setVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        for (final VehicleView vehicle : getVehicles())
            if (vehicleID.equals(vehicle.getVehicleID()))
            {
                vehicle.setDeviceID(deviceID);
                break;
            }
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "go_adminDevice";
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditDevice";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminDevices";
    }

    public Map<String, DeviceStatus> getStatuses()
    {
        return STATUSES;
    }

    public static class DeviceView extends Device implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private VehicleDAO        vehicleDAO;
        @Column(updateable = false)
        private Integer           oldVehicleID;
        @Column(updateable = false)
        private Vehicle           vehicle;
        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getDeviceID();
        }

        public void setVehicleDAO(VehicleDAO vehicleDAO)
        {
            this.vehicleDAO = vehicleDAO;
        }

        Integer getOldVehicleID()
        {
            return oldVehicleID;
        }

        void setOldVehicleID(Integer oldVehicleID)
        {
            this.oldVehicleID = oldVehicleID;
        }

        public boolean isVehicleChanged()
        {
            return (oldVehicleID != getVehicleID()) && ((getVehicleID() == null) || !getVehicleID().equals(oldVehicleID));
        }

        @Override
        public void setVehicleID(Integer vehicleID)
        {
            super.setVehicleID(vehicleID);
            vehicle = null;
        }

        public Vehicle getVehicle()
        {
            if (vehicle == null && getVehicleID() != null)
                vehicle = vehicleDAO.findByID(getVehicleID());
            return vehicle;
        }

        @Override
        public String getEphone()
        {
            return MiscUtil.formatPhone(super.getEphone());
        }

        @Override
        public String getPhone()
        {
            return MiscUtil.formatPhone(super.getPhone());
        }

        @Override
        public void setEphone(String ephone)
        {
            super.setEphone(MiscUtil.unformatPhone(ephone));
        }

        @Override
        public void setPhone(String phone)
        {
            super.setPhone(MiscUtil.unformatPhone(phone));
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
