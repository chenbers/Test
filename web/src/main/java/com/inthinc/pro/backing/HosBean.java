package com.inthinc.pro.backing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSIridiumJDBCDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class HosBean extends BaseBean {
    
    private static Logger logger = Logger.getLogger(HosBean.class);
    
    private static final long serialVersionUID = 1L;

    private Integer driverID;
    private Driver driver;
    private HOSDAO hosDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
    private FwdCmdSpoolWSIridiumJDBCDAO fcsIridiumDAO;
    private PageData pageData;
    private int page;
    private List<SelectItem> drivers;
    private List<SelectItem> vehicles;
    private DateRange dateRange;
    
    protected HosLogView item;
    protected List<HosLogView> items;
    private boolean               batchEdit;
    private boolean               selectAll;
    private Map<String, Boolean> updateField;
    private String sendLogsMsg;

    private static final String EDIT_REDIRECT = "pretty:hosEdit";    
    private static final String VIEW_REDIRECT = "pretty:hos";    
    protected final static String BLANK_SELECTION = "&#160;";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss z");

    public HosBean() {
        super();

    }
    
    public List<SelectItem> getDrivers() {
        if (drivers == null) {
            drivers = new ArrayList<SelectItem>();
            SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
            blankItem.setEscape(false);
            drivers.add(blankItem);
            for (Driver d : driverDAO.getAllDrivers(this.getUser().getGroupID())) {
                drivers.add(new SelectItem(d.getDriverID(), d.getPerson().getFullName()));
            }
        }
        sort(drivers);
        return drivers;
    }
    public List<SelectItem> getVehicles() {
        if (vehicles == null) {
            vehicles = new ArrayList<SelectItem>();
            SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
            blankItem.setEscape(false);
            vehicles.add(blankItem);
            for (Vehicle v : vehicleDAO.getVehiclesInGroupHierarchy(this.getUser().getGroupID())) {
                vehicles.add(new SelectItem(v.getVehicleID(), v.getName()));
            }
        }
        sort(vehicles);
        return vehicles;
    }


    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(
                        o2.getLabel().toLowerCase());
            }
        });
    }

    public void init()
    {
        pageData = new PageData();
        page = 0;
        pageData.initPage(page);
        // use the timezone of the logged on user for date range
        dateRange = new DateRange(getLocale(), getDateTimeZone().toTimeZone());
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public void refresh() {
        
        if (dateRange.getInterval() != null) {
            items = null;
        }
    }
    // end date range stuff
    public List<SelectItem> getStatuses() {
        return SelectItemUtil.toList(HOSStatus.class, false);
    }

    public List<SelectItem> getDots() {
        return SelectItemUtil.toList(RuleSetType.class, false, RuleSetType.SLB_INTERNAL);
    }
    
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        setSendLogsMsg(null);
        
        if (this.driverID == null) {
            if (driverID != null) {
                this.driverID = driverID;
                loadDriver(driverID);
                items = null;
            }
            return;
        }
        if (!this.driverID.equals(driverID)) {
            loadDriver(driverID);
            items = null;
        }
        
        this.driverID = driverID;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    private void loadDriver(Integer driverID)
    {
        driver = driverDAO.findByID(driverID);
    }
    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }


//    @Override
    public List<HosLogView> getItems() {
        if (items == null) {
            setItems(loadItems());
            initPageData();
        }
        
        return items;
    }
    
    private void initPageData() {
        pageData = new PageData();
        pageData.initPage(items.size());
        page = pageData.getCurrentPage();
    }

    public void setItems(List<HosLogView> items) {
        this.items = items;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
    

    public HosLogView getItem() {
        if (item == null)
        {
            batchEdit = false;

            int selected = 0;
            HosLogView selection = null;
            for (HosLogView t : getItems())
                if (t.isSelected())
                {
                    selection = t;
                    selected++;
                    if (selected > 1)
                        break;
                }
            
            if(logger.isTraceEnabled())
                logger.trace("BEGIN getItem: " + selection);
            if (selected == 0)
                item = createAddItem();
            else if (selected == 1)
                item = selection;
            else
            {
                batchEdit = true;
                item = createAddItem();
                BeanUtil.deepCopy(selection, item);

                // null out properties that are not common
                for (HosLogView t : getSelectedItems())
                    BeanUtil.compareAndInit(item, t);
            } 
            
            if(logger.isTraceEnabled())
                logger.trace("END getItem: " + item);
        }
        return item;
        
    }

    public void setItem(HosLogView item) {
        this.item = item;
    }
    
    public boolean isBatchEdit() {
        return batchEdit;
    }

    public void setBatchEdit(boolean batchEdit) {
        this.batchEdit = batchEdit;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
    protected List<HosLogView> loadItems() {
logger.info("in loadItems()");

        LinkedList<HosLogView> items = new LinkedList<HosLogView>();
        if (getDriverID() == null)
            return items;
        
        Interval interval = dateRange.getInterval();
        List<HOSRecord> plainRecords = hosDAO.getHOSRecords(getDriverID(), interval, false);
        if (plainRecords == null)
            return items;
        for (final HOSRecord rec : plainRecords) {
            if (interval.contains(rec.getLogTime().getTime()))
                    items.add(createLogView(rec));
        }
        return items;
    }
    

    private HosLogView createLogView(HOSRecord hosRecord)
    {
        final HosLogView hosLogView = new HosLogView();
        BeanUtils.copyProperties(hosRecord, hosLogView);
        hosLogView.setSelected(false);
        hosLogView.setDriverName((driver != null) ? driver.getPerson().getFullName() : "");
        return hosLogView;
    }
    

    public static class HosLogView extends HOSRecord implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private boolean selected;

        private String driverName;
        

        public HosLogView()
        {
            super();
        }

        public Integer getId()
        {
            return getHosLogID();
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
        
        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

        public int getTimeInSec() {
            DateTime dateTime = new DateTime(getLogTime(), DateTimeZone.forID(getTimeZone().getID()));
            return dateTime.getSecondOfDay();
        }

        public void setTimeInSec(int timeInSec) {
            try
            {
            DateTime dateTime = new DateMidnight(getLogTime(), DateTimeZone.forID(getTimeZone().getID())).toDateTime().plusSeconds(timeInSec);
            setLogTime(dateTime.toDate());
            }
            catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            }
            
        }

        public boolean getCanDelete() {
            return getOrigin() != HOSOrigin.DEVICE;
        }

        public boolean getIsWebLogin() {
            return getOrigin() == HOSOrigin.KIOSK;
        }
        
        public String getTimezoneName() {
//            return getTimeZone().getDisplayName(false, TimeZone.SHORT);
            return getTimeZone().getID();
        }
    }
    
    public String add()
    {
        if (driverID == null)
            return "";
        
        batchEdit = false;
        item = createAddItem();
        item.setSelected(false);
        
        if (items != null)
            for (HosLogView item : getSelectedItems())
                item.setSelected(false);

        return EDIT_REDIRECT;
    }

    public String delete()
    {
        
        List<HosLogView> selected = getSelectedItems();
        if (selected.size() != 1) 
            return VIEW_REDIRECT;   //shouldn't happen
        
        HosLogView delItem = selected.get(0);
        hosDAO.deleteByID(delItem.getId());
        items.remove(delItem);
        
        return VIEW_REDIRECT;
    }
    
    private HosLogView createAddItem() {
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setLogTime(new Date());
        hosRecord.setDriverID(driverID);
        hosRecord.setDriverDotType(driver.getDot());
        
        return createLogView(hosRecord);
    }

    public List<HosLogView> getSelectedItems()
    {
        LinkedList<HosLogView> selected = new LinkedList<HosLogView>();
        for (HosLogView t : getItems())
            if (t.isSelected())
                selected.add(t);

        if (selected.size() == 0 && !isAdd())
            selected.add(item);

        return selected;
    }
    
    public boolean isAdd()
    {
        return !isBatchEdit() && (getItem() != null) && (getItem().getId() == null);
    }


    public String batchEdit()
    {
        item = null;
        getItem();

        // take off if nothing was selected
        if (isAdd())
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            final String summary = MessageUtil.getMessageString("adminTable_noneSelected");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            context.addMessage(null, message);

            item = null;
            return "";
        }

        // select no fields for update
        for (final String key : getUpdateField().keySet())
            updateField.put(key, false);

        return EDIT_REDIRECT;
        
    }

    public Map<String, Boolean> getUpdateField()
    {
        if ((updateField == null) || (updateField.size() == 0))
        {
            if (updateField == null)
                updateField = new HashMap<String, Boolean>();
            if (getItem() != null)
                for (final String name : BeanUtil.getPropertyNames(getItem()))
                    updateField.put(name, false);
        }
        return updateField;
    }

    public String save()
    {
        boolean driverChange = false;
        List<HosLogView> selected = getSelectedItems();
        if ((selected.size() == 0) && isAdd())
            selected.add(item);

        if (batchEdit)
        {
            // get the fields to update
            final LinkedList<String> ignoreFields = new LinkedList<String>();
            for (final String key : updateField.keySet())
                if (!updateField.get(key))
                    ignoreFields.add(key);
                
            if (updateField.get("vehicleID"))
                ignoreFields.remove("vehicleName");

            if (updateField.get("driverID"))
                driverChange = true;

            //we need to validate the item before we copy the properties. 
            if(!validateSaveItem(item))
            {
                return null;
            }
            
            updateVehicleName();
            // copy properties
            for (HosLogView t : selected)
                BeanUtil.deepCopy(item, t, ignoreFields);
        }
        else {
            updateVehicleName();
            if (!item.getDriverID().equals(getDriverID())) {
                driverChange = true;
            }
        }

        // validate
        if (!validate(selected)) {
            return null;
        }

        final boolean add = isAdd();
        try
        {
            doSave(selected, add);
        }
        catch (HessianException e)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Hessian error while saving", e);
            return null;
        }
        
        if (driverChange) {
            items = null;
        }
        else {
            if (add)
            {
                items.add(item);
            }
            
            // deselect all edited items
            for (HosLogView  item : getSelectedItems())
                item.setSelected(false);
            }

        return VIEW_REDIRECT;
    }
    private void updateVehicleName() {
        if (item != null && item.getVehicleID() != null) {
            Vehicle vehicle = vehicleDAO.findByID(item.getVehicleID());
            item.setVehicleName(vehicle.getName());
        }
        
    }

    protected boolean validate(List<HosLogView> saveItems)
    {
        boolean valid = true;
        for (final HosLogView saveItem : saveItems)
        {
            valid = validateSaveItem(saveItem);
            if(!valid)
                break;
        }
        return valid;
    }

    private boolean validateSaveItem(HosLogView log) {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        // required Location
        if (log.getLocation() == null || log.getLocation().isEmpty() && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("location")))) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            context.addMessage("edit-form:editHosLog_location", message);
        }
        // future date not allowed
        if (!isBatchEdit() && log.getLogTime().after(new Date())) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_future_date_not_allowed"), null);
            context.addMessage("edit-form:editHosLog_dateTime", message);
            
        }
        
        return valid;
    }
    
    public String cancelEdit()
    {
        // revert the edit item
        if (!isBatchEdit() && !isAdd())
        {
            final int index = getItems().indexOf(getItem());
            if (index != -1)
                items.set(index, revertItem(item));
        }

        // deselect all edit items
        for (HosLogView  item : getSelectedItems())
            item.setSelected(false);

        return VIEW_REDIRECT;
    }
    
    protected HosLogView revertItem(HosLogView editItem)
    {
        return createLogView(hosDAO.findByID(editItem.getHosLogID()));
    }

    protected void doSave(List<HosLogView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        
        Integer editUserID = getUser().getUserID();

        for (HosLogView log : saveItems)
        {
            log.setEditUserID(editUserID);
            log.setEdited(true);
            if (create)
                log.setHosLogID(hosDAO.create(getDriverID(), log));
            else
                hosDAO.update(log);

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "hosLog_added" : "hosLog_updated", log.getId());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }

    }

    public void doSelectAll()
    {
        for (HosLogView log : getItems())
            log.setSelected(selectAll);
    }

    public String edit()
    {
        selectItem("editID");
        return EDIT_REDIRECT;
    }
    

    protected boolean selectItem(String paramName)
    {
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get(paramName) != null)
        {
            final int editID = Integer.parseInt(parameterMap.get(paramName));
            selectItem(editID);
            return true;
        }
        return false;
    }
    
    protected void selectItem(Integer id)
    {
            for (HosLogView log : getItems())
                log.setSelected(log.getId().equals(id));

            item = null;
            getItem();
            item.setSelected(false);
    }
   

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        pageData.initPage(page, getItems().size());
    }
    
    public String getSendLogsMsg() {
        return sendLogsMsg;
    }

    public void setSendLogsMsg(String sendLogsMsg) {
        this.sendLogsMsg = sendLogsMsg;
    }

    public void sendLogs() {
        setSendLogsMsg(null);

        if (this.getDriverID() == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDriver");
            return;
        }
        
        Vehicle vehicle = vehicleDAO.findByDriverID(getDriverID());
        if (vehicle == null) {
            setSendLogsMsg("hosSendLogsToDevice.noVehicle");
            return;
        }

        if (vehicle.getDeviceID() == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDevice");
            return;
            
        }
        Device device = deviceDAO.findByID(vehicle.getDeviceID());
        if (device == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDevice");
            return;
            
        }
        List<HOSRecord> recordList = getHosLogsForDriver(driver);
        
        try {
            List<ByteArrayOutputStream > logShipList = HOSUtil.packageLogsToShip(recordList, driver);
            
            for (ByteArrayOutputStream output :  logShipList ) {    
              queueForwardCommand(device.getImei(), output.toByteArray(), ForwardCommandID.HOSLOG_SUMMARY);
            }
        }
        catch (Exception e) {
            setSendLogsMsg("hosSendLogsToDevice.logShippingError");
            return;
        }
    }
    private List<HOSRecord> getHosLogsForDriver(Driver driver) {
        final RuleSetType dotType = driver.getDot() == null ? RuleSetType.NON_DOT : driver.getDot();

        final int daysBack = dotType.getLogShipDaysBack();
        DateTime currentDate = new DateTime();
        Interval interval = new Interval(currentDate.minusDays(daysBack), currentDate);

        return hosDAO.getHOSRecords(driver.getDriverID(), interval, true);
    }
    
    private void queueForwardCommand(String address, byte[] data, int command) 
    {
        logger.debug("queueForwardCommand Begin");
        ForwardCommandSpool fcs = new ForwardCommandSpool(data, command, address);
        if (fcsIridiumDAO.add(fcs) == -1)
            throw new ProDAOException("Iridium Forward command spool failed.");
                
    }


    public FwdCmdSpoolWSIridiumJDBCDAO getFcsIridiumDAO() {
        return fcsIridiumDAO;
    }

    public void setFcsIridiumDAO(FwdCmdSpoolWSIridiumJDBCDAO fcsIridiumDAO) {
        this.fcsIridiumDAO = fcsIridiumDAO;
    }


}

    
