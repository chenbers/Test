package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.SensitivityType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.util.SelectItemUtil;
/**
 * @author David Gileadi
 */
public class DevicesBean extends BaseAdminBean<DevicesBean.DeviceView>
{
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 5, 7 };
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("imei");
        AVAILABLE_COLUMNS.add("sim");
        AVAILABLE_COLUMNS.add("serialNum");
        AVAILABLE_COLUMNS.add("phone");
        AVAILABLE_COLUMNS.add("ephone");
        AVAILABLE_COLUMNS.add("status");
        
    }
    private DeviceDAO deviceDAO;
    private VehicleDAO vehicleDAO;
    private VehiclesBean vehiclesBean;

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
    
    public List<SensitivityType> getSensitivityTypes(){
        List<SensitivityType> sensitivityTypeList = new ArrayList<SensitivityType>();
        sensitivityTypeList.add(SensitivityType.HARD_VERT_SETTING);
        sensitivityTypeList.add(SensitivityType.HARD_ACCEL_SETTING);
        sensitivityTypeList.add(SensitivityType.HARD_BRAKE_SETTING);
        sensitivityTypeList.add(SensitivityType.HARD_TURN_SETTING);
        return sensitivityTypeList;
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
        if (device.getPhone() != null)
            deviceView.setPhone(MiscUtil.formatPhone(device.getPhone()));
        if (device.getEphone() != null)
            deviceView.setEphone(MiscUtil.formatPhone(device.getEphone()));
        return deviceView;
    }

    @Override
    public String fieldValue(DeviceView device, String columnName)
    {
        if (columnName.equals("vehicleID"))
        {
            if (device.getVehicle() != null)
                return device.getVehicle().getName();
            return null;
        }
        else if (columnName.equals("status"))
        {
            if (device.getStatus() != null)
                return MessageUtil.getMessageString(device.getStatus().getDescription().toLowerCase());
            return null;
        }
        return super.fieldValue(device, columnName);
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
        device.setAccountID(getAccountID());
        // device.setSpeedSettings(new Integer[Device.NUM_SPEEDS]);
        return createDeviceView(device);
    }

    @Override
    public DeviceView getItem()
    {
        final DeviceView item = super.getItem();
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
    protected Boolean authorizeAccess(DeviceView item)
    {
        Integer acctID = item.getAccountID();

        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID))
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    protected boolean validateSaveItem(DeviceView deviceView)
    {
        boolean valid = true;
        final String required = "required";
        // Ephone
        if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("ephone")))
        {
            if (deviceView.getEphone() == null || deviceView.getEphone().equals(""))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(required);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:ephone", message);
            }
//            else if (deviceView.getEphone() != null && !deviceView.getEphone().matches("\\D?\\d{3}\\D*\\d{3}\\D?\\d{4}")
//            		&& !deviceView.getPhone().matches("\\+\\d*"))
            else if(deviceView.getEphone() != null && 
            		((deviceView.getEphone().length() >22) || (MiscUtil.unformatPhone(deviceView.getEphone()).length() > 15)) )
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_phoneFormat");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:ephone", message);
            }
        }
        // Name
        if ((deviceView.getName() == null || deviceView.getName().equals("")) && !isBatchEdit() || (isBatchEdit() && getUpdateField().get("name")))
        {
            valid = false;
            final String summary = MessageUtil.getMessageString(required);
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            getFacesContext().addMessage("edit-form:name", message);
        }
        Device byImei = null;
        if (deviceView.getImei() != null)
            byImei = deviceDAO.findByIMEI(deviceView.getImei());
        // IMEI
        if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("imei")))
        {
            if ((deviceView.getImei() == null || deviceView.getImei().equals("")))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(required);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:imei", message);
            }
            else if (!deviceView.getImei().matches("[0-9]{15}")) // format
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_imeiFormat");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:imei", message);
            }
            else if ((byImei != null) && !byImei.getDeviceID().equals(getItem().getDeviceID())) // unique
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_uniqueImei");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:imei", message);
            }
        }
        // SIM
        if ((deviceView.getSim() == null || deviceView.getSim().equals("")) && !isBatchEdit() || (isBatchEdit() && getUpdateField().get("sim")))
        {
            valid = false;
            final String summary = MessageUtil.getMessageString(required);
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            getFacesContext().addMessage("edit-form:sim", message);
        }
        // PHONE
        if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("phone")))
        {
            if (deviceView.getPhone() == null || deviceView.getPhone().equals(""))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(required);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:phone", message);
            }
//            else if (deviceView.getPhone() != null && !deviceView.getPhone().matches("\\D?\\d{3}\\D*\\d{3}\\D?\\d{4}")
//            		&& !deviceView.getPhone().matches("\\+\\d*"))
            else if(deviceView.getEphone() != null && 
            		((deviceView.getEphone().length() > 22) || (MiscUtil.unformatPhone(deviceView.getEphone()).length() > 15)) )
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_phoneFormat");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:phone", message);
            }
        }
        return valid;
    }

    @Deprecated
    public void validateImei(FacesContext context, UIComponent component, Object value)
    {
        final String imei = (String) value;
        if (!imei.matches("[0-9]{15}"))
        {
            final String summary = MessageUtil.getMessageString("editDevice_imeiFormat");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            throw new ValidatorException(message);
        }
        // unique
        final Device byImei = deviceDAO.findByIMEI(imei);
        if ((byImei != null) && !byImei.getDeviceID().equals(getItem().getDeviceID()))
        {
            final String summary = MessageUtil.getMessageString("editDevice_uniqueImei");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            throw new ValidatorException(message);
        }
    }

    @Override
    protected DeviceView revertItem(DeviceView editItem)
    {
        return createDeviceView(deviceDAO.findByID(editItem.getDeviceID()));
    }

    @Override
    protected void doSave(List<DeviceView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final DeviceView device : saveItems)
        {
//            device.setPhone(MiscUtil.unformatPhone(device.getPhone()));
//            device.setEphone(MiscUtil.unformatPhone(device.getEphone()));
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
            setVehicleDevice(device.getOldVehicleID(), null);
        if (device.getVehicleID() != null)
            for (final DeviceView otherDevice : getItems())
                if (device.getVehicleID().equals(otherDevice.getVehicleID()) && !otherDevice.getDeviceID().equals(device.getDeviceID()))
                {
                    vehicleDAO.clearVehicleDevice(device.getVehicleID(), otherDevice.getDeviceID());
                    otherDevice.setVehicleID(null);
                    otherDevice.setOldVehicleID(null);
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
        return "pretty:adminDevice";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditDevice";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminDevices";
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(DeviceStatus.class, false, DeviceStatus.DELETED);
    }
    
    @Override
    public void resetList()
    {
        super.resetList();
        vehiclesBean.resetList();
    }

    public static class DeviceView extends Device implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private VehicleDAO vehicleDAO;
        @Column(updateable = false)
        private Integer oldVehicleID;
        @Column(updateable = false)
        private Vehicle vehicle;
        @Column(updateable = false)
        private boolean selected;

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
  /*
    
    private List<Vehicle> vehicleList;
    private List<Device> accountDeviceList;
    private List<DeviceReportItem> deviceList;

    public void init()
    {
        vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());
        accountDeviceList = deviceDAO.getDevicesByAcctID(getAccountID());
        deviceList = new ArrayList<DeviceReportItem>();
        for (Device device : accountDeviceList)
        {
            for (Vehicle vehicle : vehicleList)
            {
                if (vehicle.getDeviceID().equals(device.getDeviceID()))
                {
                    DeviceReportItem item = new DeviceReportItem();
                    item.setDevice(device);
                    item.setVehicle(vehicle);
                    deviceList.add(item);
                }
            }
        }
    }


*/