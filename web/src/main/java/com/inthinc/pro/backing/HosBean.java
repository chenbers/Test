package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class HosBean extends BaseBean {
    
    private static Logger logger = Logger.getLogger(HosBean.class);
    
    private static final long serialVersionUID = 1L;

    private Integer driverID;
    private Interval interval;
    private Driver driver;
    private HOSDAO hosDAO;
    private DriverDAO driverDAO;
    private PageData pageData;
    private int page;
    
    
    protected HosLogView item;
    protected List<HosLogView> items;
    private boolean               batchEdit;
    private boolean               selectAll;
    private Map<String, Boolean> updateField;

    
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
    private String badDates;
    private Date startDate;
    private Date endDate;
    
    private static final String EDIT_REDIRECT = "pretty:hosEdit";    
    private static final String VIEW_REDIRECT = "pretty:hos";    

    public HosBean() {
        super();
        hosDAO = new MockHOSDAO();
    }
    
    public void init()
    {
        pageData = new PageData();
        page = 0;
        pageData.initPage(page);
        if ( startDate == null ) {
            startDate = new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone).toDate();
        }
        if ( endDate == null ) {
            endDate = new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(1).toDate();
        }
        initInterval();
    }

    // date range stuff
    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    public String getBadDates() {
        return badDates;
    }

    public void setBadDates(String badDates) {
        this.badDates = badDates;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
        initInterval();
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
        initInterval();
    }

    private Interval initInterval() {
        try {
            setBadDates(null);
            if (startDate == null) {
                setBadDates(MessageUtil.getMessageString("noStartDate",getLocale()));
                return null;
            }
            if (this.endDate == null) {
                setBadDates(MessageUtil.getMessageString("noEndDate",getLocale()));
                return null;
            }

            DateMidnight start = new DateMidnight(new DateTime(startDate.getTime(), dateTimeZone), dateTimeZone);
            DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(1);
            setInterval(new Interval(start, end));
            return getInterval();
        }
        catch (Exception e) {
            setBadDates(MessageUtil.getMessageString("endDateBeforeStartDate",getLocale()));
            return null;
        }
    }
    
    public void refresh() {
        
        if (initInterval() != null) {
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
        if ((this.driverID == null && driverID != null) || !this.driverID.equals(driverID)) {
            loadDriver(driverID);
            items = null;
        }
        
        this.driverID = driverID;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
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
        List<HOSRecord> plainRecords = hosDAO.getHOSRecords(getDriverID(), getInterval());
        LinkedList<HosLogView> items = new LinkedList<HosLogView>();
        for (final HOSRecord rec : plainRecords)
            items.add(createLogView(rec));
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

    }
    
    public String add()
    {
        batchEdit = false;
        item = createAddItem();
        item.setSelected(false);

        for (HosLogView item : getSelectedItems())
            item.setSelected(false);

        return EDIT_REDIRECT;
    }

    
    private HosLogView createAddItem() {
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setLogTime(new Date());
        hosRecord.setDriverID(driverID);
        hosRecord.setDriverDotType(driver.getDriverDOTType());
        
        
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
            
            
            //we need to validate the item before we copy the properties. 
            if(!validateSaveItem(item))
            {
                return null;
            }

            // copy properties
            for (HosLogView t : selected)
                BeanUtil.deepCopy(item, t, ignoreFields);
        }

        // validate
        if (!validate(selected)) {
            return null;
        }

        final boolean add = isAdd();
        try
        {
            doSave(selected, add);
            if (items == null)
                getItems();
        }
        catch (HessianException e)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Hessian error while saving", e);
            return null;
        }

        if (add)
        {
            items.add(item);
        }

        // deselect all edited items
        for (HosLogView  item : getSelectedItems())
            item.setSelected(false);

        return VIEW_REDIRECT;
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

        for (HosLogView log : saveItems)
        {
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

    class MockHOSDAO  implements HOSDAO {
        
        List<HOSRecord> plainRecords;
        
        private List<HOSRecord> getMockHOSRecords() {
            if (plainRecords != null)
                return plainRecords;
            plainRecords = new ArrayList<HOSRecord>();
            plainRecords.add(new HOSRecord(0,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278689179387l),new Date(1278689179387l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,true,"EDITOR",false));
            plainRecords.add(new HOSRecord(1,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278637680830l),new Date(1278637680830l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,true,"EDITOR",false));
            plainRecords.add(new HOSRecord(2,130,RuleSetType.US_OIL,130,"11242145",true,null,new Date(1278611514000l),new Date(1278611604020l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(3,130,RuleSetType.US_OIL,130,"11242145",true,null,new Date(1278609387000l),new Date(1278609699063l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(4,130,RuleSetType.US_OIL,130,"11242145",true,null,new Date(1278609362000l),new Date(1278609561450l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(5,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278604275473l),new Date(1278604275473l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(6,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278561583307l),new Date(1278561583307l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(7,130,RuleSetType.US_OIL,130,"10684232",false,null,new Date(1278541618000l),new Date(1278542136930l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(8,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278517811497l),new Date(1278517811497l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(9,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278464987223l),new Date(1278464987223l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(10,130,RuleSetType.US_OIL,130,"10630346",false,null,new Date(1278455593000l),new Date(1278455690580l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(11,130,RuleSetType.US_OIL,130,"10630346",false,null,new Date(1278444264000l),new Date(1278444377410l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(12,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278432319753l),new Date(1278432319753l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(13,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278396080443l),new Date(1278396080443l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(14,130,RuleSetType.US_OIL,130,"10630346",false,null,new Date(1278387033000l),new Date(1278387233243l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"17.2 mi W of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(15,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278383206000l),new Date(1278383383043l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"21.2 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(16,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278382157000l),new Date(1278382242240l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"20.4 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(17,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278380882000l),new Date(1278380999513l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"20.4 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(18,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278374789000l),new Date(1278374871507l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(19,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278374194000l),new Date(1278374348917l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(20,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278371734000l),new Date(1278371813743l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(21,130,RuleSetType.US_OIL,130,"10844067",true,null,new Date(1278371606000l),new Date(1278371711570l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(22,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278363326693l),new Date(1278363326693l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(23,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278183531717l),new Date(1278183531717l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(24,130,RuleSetType.US_OIL,130,"10630346",false,null,new Date(1278170787000l),new Date(1278170987367l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(25,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278168685080l),new Date(1278168685080l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(26,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278130737537l),new Date(1278130737537l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(27,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1278122573000l),new Date(1278122766710l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(28,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1278118696000l),new Date(1278119097453l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(29,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1278118623000l),new Date(1278118901653l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(30,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1278117844000l),new Date(1278117947167l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(31,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278117829000l),new Date(1278117909743l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7453990",true,false,"",false));
            plainRecords.add(new HOSRecord(32,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278117126000l),new Date(1278117206073l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7453990",true,false,"",false));
            plainRecords.add(new HOSRecord(33,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1278117098000l),new Date(1278117268340l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(34,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1278117088000l),new Date(1278117236637l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(35,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278109790000l),new Date(1278109907637l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7453990",true,false,"",false));
            plainRecords.add(new HOSRecord(36,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278109760000l),new Date(1278109875903l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(37,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278098724000l),new Date(1278099493333l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY_AT_WELL,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(38,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278098636000l),new Date(1278099000260l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(39,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278072819000l),new Date(1278072928450l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(40,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278072058000l),new Date(1278072208717l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(41,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278071075000l),new Date(1278071243353l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(42,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278069333000l),new Date(1278069444870l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(43,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278069195000l),new Date(1278069361430l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(44,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278066446000l),new Date(1278066527597l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(45,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278066394000l),new Date(1278066486030l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","2785234",true,false,"",false));
            plainRecords.add(new HOSRecord(46,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278064835017l),new Date(1278064835017l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(47,130,RuleSetType.US_OIL,null,null,false,null,new Date(1278023044590l),new Date(1278023044590l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(48,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278016810000l),new Date(1278016922270l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(49,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278012765000l),new Date(1278012874477l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(50,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1278012524000l),new Date(1278012668033l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(51,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277988236000l),new Date(1277988334470l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(52,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277979815523l),new Date(1277979815523l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(53,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277941643600l),new Date(1277941643600l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(54,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277911068097l),new Date(1277911068097l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(55,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277874633103l),new Date(1277874629663l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(56,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277872105000l),new Date(1277872207577l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(57,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277869992000l),new Date(1277870121240l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(58,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277869368000l),new Date(1277869479350l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(59,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277862006000l),new Date(1277862208913l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(60,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277861990000l),new Date(1277862146523l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(61,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277860192000l),new Date(1277860304520l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"20.4 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(62,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277859780000l),new Date(1277859893620l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"20.4 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(63,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277853296000l),new Date(1277853445777l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(64,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277853104000l),new Date(1277853320697l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(65,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277850791000l),new Date(1277850994090l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(66,130,RuleSetType.US_OIL,130,"10830721",true,null,new Date(1277850771000l),new Date(1277850873120l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(67,130,RuleSetType.US_OIL,130,"11134018",true,null,new Date(1277837405000l),new Date(1277837514083l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(68,130,RuleSetType.US_OIL,130,"11134018",true,null,new Date(1277834930000l),new Date(1277835274043l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Redlands, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(69,130,RuleSetType.US_OIL,130,"11134018",true,null,new Date(1277834904000l),new Date(1277835200213l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Redlands, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(70,130,RuleSetType.US_OIL,130,"10830718",true,null,new Date(1277832936000l),new Date(1277833020000l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Fruitvale, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(71,130,RuleSetType.US_OIL,130,"10830718",true,null,new Date(1277832130000l),new Date(1277832430080l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(72,130,RuleSetType.US_OIL,130,"10830718",true,null,new Date(1277832084000l),new Date(1277832183123l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(73,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277827309860l),new Date(1277827309860l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(74,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277785634110l),new Date(1277785634110l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(75,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277782228000l),new Date(1277782477157l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(76,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277779834000l),new Date(1277779946810l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(77,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277778216000l),new Date(1277778334657l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(78,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277771403000l),new Date(1277771551607l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(79,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277770794000l),new Date(1277770883597l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(80,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277761749000l),new Date(1277762464933l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(81,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277761743000l),new Date(1277762335210l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(82,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277749102000l),new Date(1277749217600l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(83,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277748329000l),new Date(1277748454070l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(84,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277747104000l),new Date(1277747229487l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(85,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277743612000l),new Date(1277743732980l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Rifle, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(86,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277742836000l),new Date(1277742993407l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Rifle, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(87,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277738760000l),new Date(1277739242040l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(88,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277738691000l),new Date(1277738800657l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(89,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277734047470l),new Date(1277734047470l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(90,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277519253840l),new Date(1277519253840l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(91,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277514726000l),new Date(1277514833163l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(92,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277514591000l),new Date(1277514706520l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(93,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277513791000l),new Date(1277513911020l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(94,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277511481000l),new Date(1277511600433l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(95,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277511034000l),new Date(1277511135783l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(96,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277503600000l),new Date(1277503709877l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(97,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277502056000l),new Date(1277502222080l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"21.6 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(98,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277500173000l),new Date(1277500309920l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"22 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(99,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277499855000l),new Date(1277500010727l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"22 mi SW of Meeker, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(100,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277489970000l),new Date(1277490219757l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(101,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1277489858000l),new Date(1277490089847l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(102,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277479506277l),new Date(1277479506277l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(103,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277425516773l),new Date(1277425516773l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(104,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277392968057l),new Date(1277392968057l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(105,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277339070063l),new Date(1277339070063l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(106,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277330554000l),new Date(1277330636087l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(107,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277330216000l),new Date(1277330357350l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(108,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277329572000l),new Date(1277329653770l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(109,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277326886000l),new Date(1277327030220l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(110,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277325032000l),new Date(1277325122373l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(111,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277324668000l),new Date(1277324751740l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(112,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277324503000l),new Date(1277324651927l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(113,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277321720000l),new Date(1277322058657l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"17.2 mi NW of De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(114,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277321708000l),new Date(1277322080813l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"17.2 mi NW of De Beque, CO",null,null,null,"","452206",true,false,"",false));
            plainRecords.add(new HOSRecord(115,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277309272917l),new Date(1277309272917l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(116,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277272906643l),new Date(1277272753627l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.DEVICE,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(117,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277263790000l),new Date(1277263906237l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","7452206",true,false,"",false));
            plainRecords.add(new HOSRecord(118,130,RuleSetType.US_OIL,130,"11242141",true,null,new Date(1277263682000l),new Date(1277263827720l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"11374302","HALLIBURTON",true,false,"",false));
            plainRecords.add(new HOSRecord(119,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277263475000l),new Date(1277263607247l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","7452206",true,false,"",false));
            plainRecords.add(new HOSRecord(120,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277245949000l),new Date(1277246105070l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","7452206",true,false,"",false));
            plainRecords.add(new HOSRecord(121,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277245917000l),new Date(1277246069523l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(122,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277245873000l),new Date(1277246066557l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(123,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277224247000l),new Date(1277224367997l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY_AT_WELL,HOSOrigin.DEVICE,"16.9 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(124,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277216089000l),new Date(1277216182347l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"17 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(125,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277214870000l),new Date(1277215023650l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"15.6 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(126,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277212379000l),new Date(1277212500103l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"15.5 mi NW of De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(127,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277210273000l),new Date(1277210424470l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(128,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277207993000l),new Date(1277208220817l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(129,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277205361000l),new Date(1277205537580l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(130,130,RuleSetType.US_OIL,130,"10951836",true,null,new Date(1277205164000l),new Date(1277205269590l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(131,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277200673453l),new Date(1277200673453l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(132,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277162710243l),new Date(1277162710243l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(133,130,RuleSetType.US_OIL,null,null,false,null,new Date(1277134153717l),new Date(1277134153717l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(134,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276907312233l),new Date(1276907312233l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(135,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276874197633l),new Date(1276874197633l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(136,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276823317493l),new Date(1276823317493l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(137,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276820030000l),new Date(1276820168150l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(138,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276819511000l),new Date(1276819595047l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(139,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276819333000l),new Date(1276819417620l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(140,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276808630000l),new Date(1276808727417l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Naples, UT",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(141,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276807968000l),new Date(1276808053527l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Naples, UT",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(142,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276798679000l),new Date(1276798803897l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Fruita, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(143,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276797971000l),new Date(1276798148917l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Fruita, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(144,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276796223000l),new Date(1276796327753l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(145,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276796154000l),new Date(1276796259800l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(146,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276795681000l),new Date(1276795776433l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(147,130,RuleSetType.US_OIL,130,"10662550",true,null,new Date(1276795646000l),new Date(1276795752197l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(148,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276789093323l),new Date(1276789093323l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(149,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276734318493l),new Date(1276734318493l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(150,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276701181410l),new Date(1276701181410l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(151,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276652428573l),new Date(1276652428573l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(152,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276649306000l),new Date(1276649453533l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(153,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276648309000l),new Date(1276648402860l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(154,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276648289000l),new Date(1276648390357l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(155,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276647216000l),new Date(1276647306353l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(156,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276646986000l),new Date(1276647270210l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(157,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276636111000l),new Date(1276636193107l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Naples, UT",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(158,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276635425000l),new Date(1276635507690l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Naples, UT",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(159,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276628909000l),new Date(1276629008000l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"31.7 mi S of Rangely, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(160,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276628481000l),new Date(1276628612357l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"31.7 mi S of Rangely, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(161,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276623697000l),new Date(1276623784700l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(162,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276623246000l),new Date(1276623366647l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(163,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276622668000l),new Date(1276622896590l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(164,130,RuleSetType.US_OIL,130,"10830714",true,null,new Date(1276622621000l),new Date(1276622775150l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(165,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276615986490l),new Date(1276615986490l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(166,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276565149440l),new Date(1276565149440l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(167,130,RuleSetType.US_OIL,130,"10684232",false,null,new Date(1276543112000l),new Date(1276543469840l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7423223",true,false,"",false));
            plainRecords.add(new HOSRecord(168,130,RuleSetType.US_OIL,130,"10684232",false,null,new Date(1276543086000l),new Date(1276543243007l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(169,130,RuleSetType.US_OIL,130,"10684232",false,null,new Date(1276536849000l),new Date(1276537014480l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(170,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276528721590l),new Date(1276528721590l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(171,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276288809373l),new Date(1276288809373l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(172,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276281985000l),new Date(1276282109993l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(173,130,RuleSetType.US_OIL,130,"10965834",true,null,new Date(1276275692000l),new Date(1276275817890l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","",true,false,"",false));
            plainRecords.add(new HOSRecord(174,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276265531000l),new Date(1276265658340l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(175,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276262979000l),new Date(1276263181040l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(176,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276261155000l),new Date(1276261280083l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Battlement Mesa, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(177,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276258846000l),new Date(1276258997913l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(178,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276258352000l),new Date(1276258498640l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"De Beque, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(179,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276256996000l),new Date(1276257233553l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Palisade, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(180,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276256783000l),new Date(1276256897717l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Palisade, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(181,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276255395000l),new Date(1276255478753l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.DRIVING,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(182,130,RuleSetType.US_OIL,130,"10971659",true,null,new Date(1276255215000l),new Date(1276255319673l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.DEVICE,"Clifton, CO",null,null,null,"","7422867",true,false,"",false));
            plainRecords.add(new HOSRecord(183,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276253391463l),new Date(1276253391463l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(184,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276211355567l),new Date(1276211355567l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(185,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276182900210l),new Date(1276182900210l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(186,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276129515413l),new Date(1276129515413l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));
            plainRecords.add(new HOSRecord(187,130,RuleSetType.US_OIL,null,null,false,null,new Date(1276097509250l),new Date(1276097509250l),TimeZone.getTimeZone("US/Mountain"),HOSStatus.ON_DUTY,HOSOrigin.KIOSK,"Grand Junction, Colorado",null,null,null,null,null,true,false,"",false));

//            Integer vehicleID = (driver == null) ? null : driver.getDriverID();
//            for (HOSRecord rec : plainRecords)
//                rec.setVehicleID((rec.getVehicleName() == null) ? null : vehicleID);
            
            return plainRecords;
        }

        @Override
        public List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval) {
            // TODO: filter by driverID, interval
            return filter(getMockHOSRecords(), driverID, interval);
        }

        private List<HOSRecord> filter(List<HOSRecord> mockHOSRecords, Integer driverID, Interval interval) {
            List<HOSRecord> filteredList = new ArrayList<HOSRecord>();
            if (interval != null) {
                for (HOSRecord rec : getMockHOSRecords()) {
                    if (interval.contains(rec.getLogTime().getTime())) { // && rec.getDriverID().equals(driverID)) {
                        filteredList.add(rec);
                    }
                }
            }
            return filteredList;
        }

        @Override
        public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, List<HOSStatus> statusFilterList) {
            // TODO: filter by driverID, interval, status
            return getMockHOSRecords();
        }

        @Override
        public List<HOSVehicleDayData> getHOSVehicleDataByDay(Integer driverID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer create(Integer id, HOSRecord entity) {
            entity.setHosLogID(getMockHOSRecords().size());
            plainRecords.add(entity);
            return entity.getHosLogID();
        }

        @Override
        public Integer deleteByID(Integer id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSRecord findByID(Integer id) {
            for (HOSRecord rec :  getMockHOSRecords()) {
                    if (rec.getHosLogID().equals(id))
                        return rec;
            }
            return null;
        }

        @Override
        public Integer update(HOSRecord entity) {
            for (HOSRecord rec :  getMockHOSRecords()) {
                if (rec.getHosLogID().equals(entity.getHosLogID())) {
                    BeanUtils.copyProperties(entity, rec);
                    return rec.getHosLogID();
                }
            }
            return null;
        }
        
    }
}

    
