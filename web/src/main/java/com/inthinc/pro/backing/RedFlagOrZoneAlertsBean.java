package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jfree.util.Log;
import org.richfaces.component.html.HtmlDataTable;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.AlertTypeSelectItems;
import com.inthinc.pro.dao.RedFlagAndZoneAlertsDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Delay;
import com.inthinc.pro.model.LimitType;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.RedFlagOrZoneAlert;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class RedFlagOrZoneAlertsBean extends BaseAdminAlertsBean<RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView> implements Serializable {

    private static final long serialVersionUID = -2066762539439571492L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 3, 4 };
    private static final int[] DEFAULT_ADMIN_COLUMN_INDICES = new int[] { 5 };
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("type");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("zone");
        AVAILABLE_COLUMNS.add("owner"); // admin only
    }
    private RedFlagAndZoneAlertsDAO redFlagAndZoneAlertsDAO;
    private ZonesBean               zonesBean;

    public void setZonesBean(ZonesBean zonesBean)
    {
        this.zonesBean = zonesBean;
    }

    public List<SelectItem> getAlertTypeSelectItems() {
        
        return AlertTypeSelectItems.INSTANCE.getSelectItems();
    }

    public void setRedFlagAndZoneAlertsDAO(RedFlagAndZoneAlertsDAO redFlagAndZoneAlertsDAO) {
        this.redFlagAndZoneAlertsDAO = redFlagAndZoneAlertsDAO;
    }

    @Override
    protected List<RedFlagOrZoneAlertView> loadItems() {
        List<RedFlagOrZoneAlert> plainRedFlagOrZoneAlerts = null;
        if (this.getProUser().isAdmin()) {
            plainRedFlagOrZoneAlerts = redFlagAndZoneAlertsDAO.getRedFlagAndZoneAlertsByUserIDDeep(getUser().getUserID());
        }
        else {
            plainRedFlagOrZoneAlerts = redFlagAndZoneAlertsDAO.getRedFlagAndZoneAlertsByUserID(getUser().getUserID());
        }

        // convert the RedFlagAlerts to RedFlagOrZoneAlertViews
        final LinkedList<RedFlagOrZoneAlertView> items = new LinkedList<RedFlagOrZoneAlertView>();
        for (final RedFlagOrZoneAlert flag : plainRedFlagOrZoneAlerts)
            items.add(createRedFlagOrZoneAlertView(flag));
        return items;
    }

    /**
     * Creates a RedFlagOrZoneAlertView object from the given RedFlagAlert object.
     * 
     * @param flag
     *            The flag.
     * @return The new RedFlagOrZoneAlertView object.
     */
    private RedFlagOrZoneAlertView createRedFlagOrZoneAlertView(RedFlagOrZoneAlert flag) {
        final RedFlagOrZoneAlertView alertView = new RedFlagOrZoneAlertView();
        alertView.setAnytime(true);
        alertView.setHardAccelerationSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardAccelerationNull());
        alertView.setHardBrakeSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardBrakeNull());
        alertView.setHardTurnSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardTurnNull());
        alertView.setHardVerticalSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardVerticalNull());
        BeanUtils.copyProperties(flag, alertView);
        if (alertView.getStartTOD() == null)
            alertView.setStartTOD(BaseAlert.MIN_TOD);
        if (alertView.getStopTOD() == null)
            alertView.setStopTOD(BaseAlert.MIN_TOD);
        alertView.setAnytime(isAnytime(alertView));
        alertView.setSelected(false);
        alertView.setRedFlagOrZoneAlertsBean(this);

        alertView.setAlertID(flag.getAlertID());
        return alertView;
    }

    @Override
    public List<String> getAvailableColumns() {
        if (!getProUser().isAdmin()) {
            return AVAILABLE_COLUMNS.subList(0, 4);
        }
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        if (getProUser().isAdmin()) {
            for (int i : DEFAULT_ADMIN_COLUMN_INDICES)
                columns.put(availableColumns.get(i), true);
        }
        return columns;
    }

    @Override
    public String getColumnLabelPrefix() {
        return "redFlagsHeader_";
    }

    @Override
    public TableType getTableType() {
        return TableType.ADMIN_RED_FLAG_PREFS;
    }

    @Override
    protected RedFlagOrZoneAlertView createAddItem() {

//will it be RedFlagAlert
        
        final RedFlagAlert redFlag = new RedFlagAlert();
        RedFlagOrZoneAlertView RedFlagOrZoneAlertView = createRedFlagOrZoneAlertView(redFlag);
        RedFlagOrZoneAlertView.setAccountID(getAccountID());
        RedFlagOrZoneAlertView.setUserID(getUserID());
//        return RedFlagOrZoneAlertView;
//ZoneAlert
        final ZoneAlert zoneAlert = new ZoneAlert();
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String zoneID = parameterMap.get("zones-form:zone");
        zonesBean.loadZones();
        
        if (zoneID != null)
            zoneAlert.setZoneID(Integer.valueOf(zoneID));
        else
        {
            final List<SelectItem> zones = getZones();
            if ((zones != null) && (zones.size() > 0))
                zoneAlert.setZoneID((Integer)zones.get(0).getValue());
        }
        zoneAlert.setAccountID(getAccountID());
        zoneAlert.setUserID(getUserID());
        return createRedFlagOrZoneAlertView(zoneAlert);

    }

    @Override
    public RedFlagOrZoneAlertView getItem() {
        final RedFlagOrZoneAlertView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS]);
        return item;
    }

    @Override
    protected void doDelete(List<RedFlagOrZoneAlertView> deleteItems) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final RedFlagOrZoneAlertView flag : deleteItems) {
            redFlagAndZoneAlertsDAO.deleteByEntity(flag);
            // add a message
            final String summary = MessageUtil.formatMessageString("redFlag_deleted", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    public String cancelEdit() {
        getItem().setType(null);
        return super.cancelEdit();
    }
    
    @Override
    public String save() {
        final Map<String, Boolean> updateField = getUpdateField();
        
        if (isBatchEdit()) {
            boolean updateType = Boolean.TRUE.equals(getUpdateField().get("type"));
            updateField.put("severityLevel", updateType);
            updateField.put("speedSettings", updateType);
            updateField.put("hardAcceleration", updateType);
            updateField.put("hardBrake", updateType);
            updateField.put("hardTurn", updateType);
            updateField.put("hardVertical", updateType);
            if (updateField.get("anytime")) {
                updateField.put("startTOD", true);
                updateField.put("stopTOD", true);
            }
            final boolean defineAlerts = Boolean.TRUE.equals(updateField.get("defineAlerts"));
            updateField.put("arrival", defineAlerts);
            updateField.put("departure", defineAlerts);
        }
        // null out unselected items
        if (AlertMessageType.ALERT_TYPE_SPEEDING.equals(getItem().getType())) {
            Boolean selected[]=this.getItem().getSpeedSelected();
            Integer settings[]=this.getItem().getSpeedSettings();
            for (int i=0; i<selected.length; i++)
            {
                if (!selected[i])
                    settings[i]=null;
            }
            getItem().setSpeedSettings(settings);
        }
        else if (AlertMessageType.ALERT_TYPE_AGGRESSIVE_DRIVING.equals(getItem().getType())) {
            if (!getItem().isHardAccelerationSelected())
                getItem().setHardAcceleration(null);
            if (!getItem().isHardTurnSelected())
                getItem().setHardTurn(null);
            if (!getItem().isHardBrakeSelected())
                getItem().setHardBrake(null);
            if (!getItem().isHardVerticalSelected())
                getItem().setHardVertical(null);
        }
        return super.save();
    }

    @Override
    protected RedFlagOrZoneAlertView revertItem(RedFlagOrZoneAlertView editItem) {
        return createRedFlagOrZoneAlertView(redFlagAndZoneAlertsDAO.findByID(editItem.getAlertID()));
    }

    @Override
    protected Boolean authorizeAccess(RedFlagOrZoneAlertView item) {
        Integer acctID = item.getAccountID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    protected boolean validateSaveItem(RedFlagOrZoneAlertView saveItem) {
        
        boolean valid = super.validateSaveItem(saveItem);
        if ((saveItem.getName() == null) || (saveItem.getName().length() == 0) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("name")))) {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlag-name", message);
            valid = false;
        }
  
        if (saveItem.getType() == null)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeTypeMessage"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
            valid=false;
        }
        if (AlertMessageType.ALERT_TYPE_SPEEDING.equals(saveItem.getType())){
            boolean speedValid = false;
            for(int i=0;i< saveItem.getSpeedSelected().length; i++){
                
                speedValid=speedValid||saveItem.getSpeedSelected()[i];
            }
            if (!speedValid){
                
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeSpeedingMessage"), null);
                FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
                valid = false;
                
            }
        }
        else if (AlertMessageType.ALERT_TYPE_AGGRESSIVE_DRIVING.equals(saveItem.getType())){
             
            boolean styleValid = saveItem.isHardAccelerationSelected() 
                                || saveItem.isHardBrakeSelected() 
                                || saveItem.isHardTurnSelected()
                                || saveItem.isHardVerticalSelected();
            
            if (!styleValid){
                
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeDrivingStyleMessage"), null);
                FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
                
                valid = false;
            }
        }
        else if (AlertMessageType.ALERT_TYPE_OFFHOUR.equals(saveItem.getType())){
            if (saveItem.isAnytime()) {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeOffHourMessage"), null);
                FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
                
                valid = false;
            }
        }
         
        return valid;
    }

    @Override
    protected void doSave(List<RedFlagOrZoneAlertView> saveItems, boolean create) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final RedFlagOrZoneAlertView flag : saveItems) {
            // if batch editing, copy individual speed settings by hand
            if (isBatchEdit()) {
                flag.setSpeedSelected(null);
                if (flag.getSpeedSettings() == null)
                    flag.setSpeedSettings(new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS]);
                final Map<String, Boolean> updateField = getUpdateField();
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7) && (updateField.get(key) == true)) {
                        final int index = Integer.parseInt(key.substring(5));
                        flag.getSpeedSettings()[index] = getItem().getSpeedSettings()[index];
                     }
            }
            if (flag.isAnytime()) {
                flag.setStartTOD(BaseAlert.MIN_TOD);
                flag.setStopTOD(BaseAlert.MIN_TOD);
            }
            // since getItem auto-creates the below, null 'em here before saving
            if (flag.getSpeedSettings() != null && flag.getSpeedSettings()[0] == null) {
                flag.setSpeedSettings(null);
            }
            if (create)
                flag.setAlertID(redFlagAndZoneAlertsDAO.create(getAccountID(), flag));
            else
                redFlagAndZoneAlertsDAO.update(flag);
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "redFlag_added" : "redFlag_updated", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect() {
        
        if (getItem().getType().equals(AlertMessageType.ALERT_TYPE_ZONES)){
          
             return "pretty:adminZone";
        }
        return "pretty:adminRedFlag";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditRedFlagOrZone";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminRedFlagsAndZones";
    }
    @Override
    public String getAlertPage() {

        return "editRedFlag";
    }
    public List<SelectItem> getZones()
    {
        List<SelectItem> zoneList = zonesBean.getZoneIDs();
        return zoneList;
    }

    protected Zone getZoneByID(Integer zoneID)
    {
        for (final Zone zone : zonesBean.getZones())
            if (zone.getZoneID() != null && zone.getZoneID().equals(zoneID))
                return zone;
        return null;
    }
    @Override
    public void resetList()
    {
        zonesBean.clearZones();
        super.resetList();
    }

    public static class RedFlagOrZoneAlertView extends RedFlagOrZoneAlert implements BaseAdminAlertsBean.BaseAlertView {

        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private Boolean anytime;
        @Column(updateable = false)
        private boolean selected;
        @Column(updateable = false)
        private Boolean[] speedSelected;
        @Column(updateable = false)
        private Boolean hardAccelerationSelected;
        @Column(updateable = false)
        private Boolean hardBrakeSelected;
        @Column(updateable = false)
        private Boolean hardTurnSelected;
        @Column(updateable = false)
        private Boolean hardVerticalSelected;
        @Column(updateable = false)
        private RedFlagOrZoneAlertsBean    redFlagOrZoneAlertsBean;
        @Column(updateable = false)
        private Zone              zone;
        private Delay delay;
        private Integer limitValue;
        private LimitType limitType;
        
        private HtmlDataTable phNumbersDataTable;
        private HtmlDataTable emailTosDataTable;

        private String escEmail;
        private List<String> phNumbers;
        private List<String> emailTos;//TODO: jwimmer: init wrong name? also this VALUE should be the same as baseAlert.EmailTo(String???)
        private Integer alertID;
        
        public RedFlagOrZoneAlertView() {
            super();
            //TODO: jwimmer: remove fake testing data
            phNumbers = new ArrayList<String>();
            emailTos = new ArrayList<String>();
            if(phNumbers.isEmpty()){
                phNumbers.add("80155551212");
                phNumbers.add("2037289200");
            }
            if(emailTos.isEmpty()) {
                emailTos.add("john@company.com");
                emailTos.add("mary@company.com");
            }
            //TODO: jwimmer: end of fake testing data
            
            //initialize to ensure that the last item is an empty slot
            String lastString = phNumbers.get(phNumbers.size()-1);
            if(null != lastString && !"".equals(lastString)){
                phNumbers.add("");
            }
            lastString = emailTos.get(emailTos.size()-1);
            if(null != lastString && !"".equals(lastString)){
                emailTos.add("");
            }
        }
        
        public void removePhNumber() {
            phNumbers.remove(phNumbersDataTable.getRowData());
        }

        public void addPhNumberSlot() {
            try {
                int indexForPhNumToAdd = 0;
                String paramForPhNumToAdd = "";
                FacesContext context = FacesContext.getCurrentInstance();
                Map<String, String> map = context.getExternalContext().getRequestParameterMap();
                for (String key : map.keySet()) {

                    if (key.endsWith("phNumInput")) {
                        String[] words = key.split(":");
                        int rowIndex = Integer.parseInt(words[2]);
                        if (rowIndex > indexForPhNumToAdd)
                            paramForPhNumToAdd = key;
                        indexForPhNumToAdd = Math.max(rowIndex, indexForPhNumToAdd);
                    }
                }

                String value = map.get(paramForPhNumToAdd);
                if (!"".equals(value)) {
                    phNumbers.add(value);
                    phNumbers.remove("");
                    phNumbers.add("");
                }
            } catch (Exception e) {
               Log.error("addPhNumberSlot() failed");
            }
        }

        public void removeEmailTo() {
            //System.out.println("removeEmailToSlot: "+(String)emailTosDataTable.getRowData());
            emailTos.remove(emailTosDataTable.getRowData());
        }

        public void addEmailToSlot() {
            try {
                int indexForEmailToAdd = 0;
                String paramForEmailToAdd = "";
                FacesContext context = FacesContext.getCurrentInstance();
                Map<String, String> map = context.getExternalContext().getRequestParameterMap();
                for (String key : map.keySet()) {    System.out.println(key.toString()+" : "+map.get(key)); 
                    if (key.endsWith("emailAddressesInput")) {     
                        String[] words = key.split(":");
                        int rowIndex = Integer.parseInt(words[2]);
                        if (rowIndex > indexForEmailToAdd)
                            paramForEmailToAdd = (String) key;
                        indexForEmailToAdd = Math.max(rowIndex, indexForEmailToAdd);
                    }
                }
                String value = map.get(paramForEmailToAdd);
                if (!"".equals(value)) {
                    emailTos.add(value);
                    emailTos.remove("");
                    emailTos.add("");
                }
            } catch (Exception e) {
               Log.error("addPhNumberSlot() failed");
            }
        }
        

        @Override
        public Integer getId() {
            // TODO Auto-generated method stub
            return getAlertID();
        }
        public boolean isAnytime() {
            return anytime;
        }

        public void setAnytime(boolean anytime) {
            this.anytime = anytime;
        }

        @Override
        public void setStartTOD(Integer startTOD) {
            if (startTOD == null) {
                super.setStartTOD(BaseAlert.MIN_TOD);
            }
            else {
                super.setStartTOD(startTOD);
            }
        }

        @Override
        public void setStopTOD(Integer stopTOD) {
            if (stopTOD == null) {
                super.setStopTOD(BaseAlert.MIN_TOD);
            }
            else {
                super.setStopTOD(stopTOD);
            }
        }

        public Boolean[] getSpeedSelected() {
            if ((speedSelected == null)) {
                speedSelected = new Boolean[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
                for (int i = 0; i < speedSelected.length && getSpeedSettings()!=null; i++)
                    speedSelected[i] = getSpeedSettings()[i]!=null;
            }
            return speedSelected;
        }

        public void setSpeedSelected(Boolean[] speedSelected) {
            this.speedSelected = speedSelected;
        }

        public boolean isHardAccelerationSelected() {
            return hardAccelerationSelected;
        }

        public void setHardAccelerationSelected(boolean hardAccelerationSelected) {
            this.hardAccelerationSelected = hardAccelerationSelected;
        }

        public boolean isHardBrakeSelected() {
            return hardBrakeSelected;
        }

        public void setHardBrakeSelected(boolean hardBrakeSelected) {
            this.hardBrakeSelected = hardBrakeSelected;
        }

        public boolean isHardTurnSelected() {
            return hardTurnSelected;
        }

        public void setHardTurnSelected(boolean hardTurnSelected) {
            this.hardTurnSelected = hardTurnSelected;
        }

        public boolean isHardVerticalSelected() {
            return hardVerticalSelected;
        }

        public void setHardVerticalSelected(boolean hardVerticalSelected) {
            this.hardVerticalSelected = hardVerticalSelected;
        }

        @Override
        public RedFlagLevel getSeverityLevel() {
            return super.getSeverityLevel();
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        void setRedFlagOrZoneAlertsBean(RedFlagOrZoneAlertsBean redFlagOrZoneAlertsBean)
        {
            this.redFlagOrZoneAlertsBean = redFlagOrZoneAlertsBean;
        }
        public Zone getZone()
        {
            if (/*zone == null && */getZoneID() != null)
                zone = redFlagOrZoneAlertsBean.getZoneByID(getZoneID());
            return zone;
        }
        public String getZoneName(){
            if (getType().equals(AlertMessageType.ALERT_TYPE_ZONES)){
                if (getZoneID() != null){
                    return getZone().getName();
                }
            }
            return null;
        }
        public List<SelectItem> getDelays() {
            return SelectItemUtil.toList(Delay.class, false);
        }
        public List<SelectItem> getLimitTypes() {
            return SelectItemUtil.toList(LimitType.class, false);
        }
        public List<SelectItem> getLimitValues() {
            //TODO: jwimmer: what are the ACTUAL values and how to we want to get them?
            List<SelectItem> results = new ArrayList<SelectItem>();
            results.add(new SelectItem(4,"4"));
            results.add(new SelectItem(5,"5"));
            results.add(new SelectItem(6,"6"));
            results.add(new SelectItem(7,"7"));
            return results;
        }
        public Delay getDelay() {
            return delay;
        }

        public void setDelay(Delay delay) {
            this.delay = delay;
        }

        public Integer getLimitValue() {
            return limitValue;
        }

        public void setLimitValue(Integer limitValue) {
            this.limitValue = limitValue;
        }

        public LimitType getLimitType() {
            return limitType;
        }

        public void setLimitType(LimitType limitType) {
            this.limitType = limitType;
        }

        public String getEscEmail() {            
            return escEmail;
        }

        public void setEscEmail(String escEmail) {
            this.escEmail = escEmail;
        }
        public String getZonePointsString(){
            if (getType().equals(AlertMessageType.ALERT_TYPE_ZONES)){
                if (getZoneID() != null){
                    return getZone().getPointsString();
                }
            }
            return null;
        }
        public void setZonePointsString(String pointsString){
            getZone().setPointsString(pointsString);
        }

        public List<String> getPhNumbers() {
            return phNumbers;
        }

        public void setPhNumbers(List<String> phNumbers) {
            this.phNumbers = phNumbers;
        }

        public List<String> getEmailTos() {
            return emailTos;
        }

        public void setEmailTos(List<String> emailTos) {
            this.emailTos = emailTos;
        }

        @Override
        public Integer getAlertID() {
            return alertID;
        }

        @Override
        public void setAlertID(Integer alertID) {
            this.alertID = alertID;
            
        }

        public HtmlDataTable getPhNumbersDataTable() {
            return phNumbersDataTable;
        }

        public void setPhNumbersDataTable(HtmlDataTable phNumbersDataTable) {
            this.phNumbersDataTable = phNumbersDataTable;
        }

        public HtmlDataTable getEmailTosDataTable() {
            return emailTosDataTable;
        }

        public void setEmailTosDataTable(HtmlDataTable emailTosDataTable) {
            this.emailTosDataTable = emailTosDataTable;
        }
    }
}
