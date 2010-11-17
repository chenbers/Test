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

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.AlertTypeSelectItems;
import com.inthinc.pro.dao.RedFlagAndZoneAlertsDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.RedFlagOrZoneAlert;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.util.MessageUtil;

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
        final RedFlagOrZoneAlertView flagView = new RedFlagOrZoneAlertView();
        flagView.setAnytime(true);
        flagView.setHardAccelerationSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardAccelerationNull());
        flagView.setHardBrakeSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardBrakeNull());
        flagView.setHardTurnSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardTurnNull());
        flagView.setHardVerticalSelected(flag instanceof RedFlagAlert && !((RedFlagAlert)flag).isHardVerticalNull());
        BeanUtils.copyProperties(flag, flagView);
        if (flagView.getStartTOD() == null)
            flagView.setStartTOD(BaseAlert.MIN_TOD);
        if (flagView.getStopTOD() == null)
            flagView.setStopTOD(BaseAlert.MIN_TOD);
        flagView.setAnytime(isAnytime(flagView));
        flagView.setSelected(false);
        flagView.setId(flag.getId());
        return flagView;
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
        return createRedFlagOrZoneAlertView(redFlagAndZoneAlertsDAO.findByID(editItem.getId()));
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
                flag.setId(redFlagAndZoneAlertsDAO.create(getAccountID(), flag));
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
        return "pretty:adminRedFlagOrZone";
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
        private Integer     id;
        
        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public void setId(Integer id) {
            this.id = id;
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
            if (zone == null && getZoneID() != null)
                zone = redFlagOrZoneAlertsBean.getZoneByID(getZoneID());
            return zone;
        }
        public String getZoneName(){
            
            if (getZoneID() != null){
                return getZone().getName();
            }
            return null;
        }
    }
}
