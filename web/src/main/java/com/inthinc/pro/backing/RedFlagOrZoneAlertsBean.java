package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.AlertTypeSelectItems;
import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Delay;
import com.inthinc.pro.model.LimitType;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class RedFlagOrZoneAlertsBean extends BaseAdminAlertsBean<RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RedFlagOrZoneAlertsBean.class);
    private static final int[] CALL_LIMIT_COUNT = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private static final int[] CALL_LIMIT_MINUTES = new int[] {10, 15, 30, 45, 60, 120};
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
    private RedFlagAlertDAO redFlagAlertsDAO;
    private ZonesBean       zonesBean;

    private HtmlDataTable phNumbersDataTable;
    private HtmlDataTable emailTosDataTable;
    
    public void setZonesBean(ZonesBean zonesBean)
    {
        this.zonesBean = zonesBean;
    }

    public List<SelectItem> getAlertTypeSelectItems() {
        
        return AlertTypeSelectItems.INSTANCE.getSelectItems();
    }
    @Override
    public void initFilterValues(){
        super.initFilterValues();
        for(String column:AVAILABLE_COLUMNS){
            filterValues.put(column, null);
        }
    }

    @Override
    protected List<RedFlagOrZoneAlertView> loadItems() {
        List<RedFlagAlert> plainRedFlagOrZoneAlerts = null;
        if (this.getProUser().isAdmin()) {
            plainRedFlagOrZoneAlerts = redFlagAlertsDAO.getRedFlagAlertsByUserIDDeep(getUser().getUserID());
        }
        else {
            plainRedFlagOrZoneAlerts = redFlagAlertsDAO.getRedFlagAlertsByUserID(getUser().getUserID());
        }

        // convert the RedFlagAlerts to RedFlagOrZoneAlertViews
        final LinkedList<RedFlagOrZoneAlertView> items = new LinkedList<RedFlagOrZoneAlertView>();
        for (final RedFlagAlert flag : plainRedFlagOrZoneAlerts)
            items.add(createRedFlagOrZoneAlertView(flag));
        return items;
    }

    @Override
    public String fieldValue(RedFlagOrZoneAlertView item, String column)
    {
        if("zone".equals(column)){
            if(item.getZone() != null)
                return item.getZone().getName();
        }
        return super.fieldValue(item, column);
    }
    /**
     * Creates a RedFlagOrZoneAlertView object from the given RedFlagAlert object.
     * 
     * @param flag
     *            The flag.
     * @return The new RedFlagOrZoneAlertView object.
     */
    private RedFlagOrZoneAlertView createRedFlagOrZoneAlertView(RedFlagAlert flag) {
        final RedFlagOrZoneAlertView alertView = new RedFlagOrZoneAlertView();
        alertView.setAnytime(true);
        
        // i think this should be checking the passed in alert, if not null?
        if ( flag == null ) {
            if (alertView.getStartTOD() == null)
                alertView.setStartTOD(RedFlagAlert.MIN_TOD);
            if (alertView.getStopTOD() == null)
                alertView.setStopTOD(RedFlagAlert.MIN_TOD);
            alertView.setAnytime(isAnytime(alertView));

        } else {
            if (flag.getStartTOD() == null) {
                alertView.setStartTOD(RedFlagAlert.MIN_TOD);
            } else {
                alertView.setStartTOD(flag.getStartTOD());
            }
            if (flag.getStopTOD() == null) {
                alertView.setStopTOD(RedFlagAlert.MIN_TOD);   
            } else {
                alertView.setStopTOD(flag.getStopTOD());
            }
            alertView.setAnytime(isAnytime(alertView));            
        }
        
        alertView.setSelected(false);
        alertView.setRedFlagOrZoneAlertsBean(this);
        
        if(flag != null){
            if (flag.getTypes() == null || flag.getTypes().isEmpty()){
                alertView.setEventSubCategory(EventSubCategory.DRIVING_STYLE);
            }
            else{
                alertView.setEventSubCategory(deriveEventSubCategory(flag));
            }
            alertView.setAlertID(flag.getAlertID());
            
            boolean found = false;
            if ( flag.getTypes() != null && findType(flag,AlertMessageType.ALERT_TYPE_HARD_ACCEL) ) {
                if( flag.getHardAcceleration() != null ) {
                    found = true;
                }
            }           
            alertView.setHardAccelerationSelected(found);
            
                    found = false;
            if ( flag.getTypes() != null && findType(flag,AlertMessageType.ALERT_TYPE_HARD_BRAKE) ) {
                if( flag.getHardBrake() != null ) {
                    found = true;
                }
            }
            alertView.setHardBrakeSelected(found);
            
                    found = false;
            if ( flag.getTypes() != null && findType(flag,AlertMessageType.ALERT_TYPE_HARD_TURN) ) {
                if( flag.getHardTurn() != null ) {
                    found = true;
                }
            }        
            alertView.setHardTurnSelected(found);
            
                found = false;
            if ( flag.getTypes() != null && findType(flag,AlertMessageType.ALERT_TYPE_HARD_BUMP) ) {
                if( flag.getHardVertical() != null ) {
                    found = true;
                }
            }             
            alertView.setHardVerticalSelected(found);
            
            BeanUtils.copyProperties(flag, alertView);
        }
        else{
            alertView.setEventSubCategory(EventSubCategory.DRIVING_STYLE);
            alertView.setHardAccelerationSelected(false);
            alertView.setHardBrakeSelected(false);
            alertView.setHardTurnSelected(false);
            alertView.setHardVerticalSelected(false);
        }
        
        // this loads the selected types
        alertView.getSelectedAlertMessageTypes(flag);

        List<String> displayedPhNumbers = new ArrayList<String>();
        if(flag.getEscalationList() != null && !flag.getEscalationList().isEmpty()){
            for(AlertEscalationItem item: flag.getEscalationList()){
                if(item.getEscalationOrder().equals(-1))
                    alertView.setEscEmail(personDAO.findByID(item.getPersonID()).getFullNameWithPriEmail());
                else
                    displayedPhNumbers.add(personDAO.findByID(item.getPersonID()).getFullNameWithPriPhone());
            }
        }
        alertView.setPhNumbers(displayedPhNumbers);
        ensureEmptySlot(alertView.getPhNumbers());//ensure empty slot
        
        alertView.setDelay(Delay.valueOf(flag.getEscalationTimeBetweenRetries()));
        if(flag.getMaxEscalationTries() != null) {
            alertView.setLimitType(LimitType.COUNT);
            alertView.setLimitValue(flag.getMaxEscalationTries());
        } else {
            alertView.setLimitType(LimitType.TIME);
            alertView.setLimitValue(flag.getMaxEscalationTryTime());
        }
        
        return alertView;
    }
    
    private boolean findType(RedFlagAlert flg,AlertMessageType amtIn) {
        for (int i = 0; i<flg.getTypes().size();i++) {
            AlertMessageType amt = flg.getTypes().get(i);
            if ( amt.equals(amtIn)) {
                return true;
            }
        }
        return false;
    }
    public static String findOwnerName(Integer userID){
        String results ="";
        if(userID != null)
            results = userDAO.findByID(userID).getPerson().getFullName();
        return results;
    }
    private static void ensureEmptySlot(List<String> list) {
        if(null == list) list = new ArrayList<String>();
        if(!list.isEmpty()) {
            String lastString = null;
            lastString = list.get(list.size()-1);
            
            if(!"".equals(lastString))
                list.add("");
        }else{
            list.add("");
        }
    }
    private EventSubCategory deriveEventSubCategory(RedFlagAlert flag){
        
        AlertMessageType alertMessageType = flag.getTypes().get(0);
        
        return EventSubCategory.valueForAlertMessageType(alertMessageType);
    }
    @Override
    public List<String> getAvailableColumns() {
        if (!getProUser().isAdmin()) {
            return AVAILABLE_COLUMNS.subList(0, 5);
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
        final RedFlagAlert alert = new RedFlagAlert();
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String zoneID = parameterMap.get("zones-form:zone"); // TODO find out what this should be
        zonesBean.loadZones();
        
        if (zoneID != null) 
        {
            alert.setZoneID(Integer.valueOf(zoneID));
            List<AlertMessageType> amtLst = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE));
            alert.setTypes(amtLst);
        }
        else
        {
            final List<SelectItem> zones = getZones();
            if ((zones != null) && (zones.size() > 0))
                alert.setZoneID((Integer)zones.get(0).getValue());
        }
        RedFlagOrZoneAlertView redFlagOrZoneAlertView = createRedFlagOrZoneAlertView(alert);
        redFlagOrZoneAlertView.setAccountID(getAccountID());
        redFlagOrZoneAlertView.setUserID(getUserID());
        
        
        return redFlagOrZoneAlertView;
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
            redFlagAlertsDAO.deleteByID(flag.getAlertID());
            // add a message
            final String summary = MessageUtil.formatMessageString("redFlag_deleted", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    public String cancelEdit() {
        getItem().setEventSubCategory(null);
        return super.cancelEdit();
    }
    @Override
    public String edit() {
        RedFlagOrZoneAlertView view = this.getItem();
        ensureEmptySlot(view.getPhNumbers());
        ensureEmptySlot(view.getEmailTos());
            
        String results = super.edit();
        return results;
    }
    
    @Override
    public String save() {
        final Map<String, Boolean> updateField = getUpdateField();

        // can't set if the red flag type is not set on the edit
        if ( getItem().getEventSubCategory() != null ) {
            setAlertTypesFromSubCategory();
        }
        
        if (isBatchEdit()) {
            // eventSubCategory is ALWAYS true now...
            getUpdateField().put("eventSubCategory", true);
            
            // the following appears to be a business rule that, if you aren't changing the red flag type,
            //  you can't change some other fields? removing severity level from rule....
//            boolean updateType = Boolean.TRUE.equals(getUpdateField().get("type"));            
            boolean updateType = Boolean.TRUE.equals(getUpdateField().get("eventSubCategory"));               
//            updateField.put("severityLevel", updateType);
            updateField.put("speedSettings", updateType);
            updateField.put("hardAcceleration", updateType);
            updateField.put("hardBrake", updateType);
            updateField.put("hardTurn", updateType);
            updateField.put("hardVertical", updateType);
            
            if (updateField.get("anytime")) {
                updateField.put("startTOD", true);
                updateField.put("stopTOD", true);
            }
//            final boolean defineAlerts = Boolean.TRUE.equals(updateField.get("defineAlerts"));
        }
        // null out unselected items
        if (EventSubCategory.SPEED.equals(getItem().getEventSubCategory())) {
            Boolean selected[]=this.getItem().getSpeedSelected();
            Integer settings[]=this.getItem().getSpeedSettings();
            for (int i=0; i<selected.length; i++)
            {
                if (!selected[i])
                    settings[i]=null;
            }
            getItem().setSpeedSettings(settings);
        }
        else if (EventSubCategory.DRIVING_STYLE.equals(getItem().getEventSubCategory())) {
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
    private void setAlertTypesFromSubCategory(){
        RedFlagOrZoneAlertView redFlagAlert = getItem();
        Set<AlertMessageType> alertMessageTypes = redFlagAlert.getEventSubCategory().getAlertMessageTypeSet();
        List<AlertMessageType> selectedTypes = new ArrayList<AlertMessageType>();
        for(AlertMessageType amt:alertMessageTypes){
            if(redFlagAlert.getSelectedAlertTypes().get(amt.name())){
                selectedTypes.add(amt);
                
                // if you find one, tell the update to do it
                if ( this.isBatchEdit() ) {
                    this.getUpdateField().put("types", true);
                }
            }
        }
        redFlagAlert.setTypes(selectedTypes);
    }

    @Override
    protected RedFlagOrZoneAlertView revertItem(RedFlagOrZoneAlertView editItem) {
        return createRedFlagOrZoneAlertView(redFlagAlertsDAO.findByID(editItem.getAlertID()));
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
        boolean checkSubTypes = false;  // see if we need to check the subtypes for the category
        
        if ((saveItem.getName() == null) || (saveItem.getName().length() == 0) && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("name")))) {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlag-name", message);
            valid = false;
        }
  
        // this check is only relevant if single editing. if batch editing, eventSubCategory will either be set (all the same) which
        //  will correctly expose the subtype and will then HAVE to be edited, or null (bunch of different ones) which
        //  implies the only thing that can be edited is the common stuff...
        if (saveItem.getEventSubCategory() == null && !isBatchEdit() )
//        if (saveItem.getEventSubCategory() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("eventSubCategory"))))          
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeTypeMessage"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
            valid=false;
        
        // single flag, always check
        } else if ( !isBatchEdit() ) {
            checkSubTypes = true;
        // multiple flags, never check
        } 
        
        // only check the speed settings if they select the type
        if (EventSubCategory.SPEED.equals(saveItem.getEventSubCategory()) && checkSubTypes){
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
//        else if (EventSubCategory.DRIVING_STYLE.equals(saveItem.getEventSubCategory())){
//             
//            boolean styleValid = saveItem.isHardAccelerationSelected() 
//                                || saveItem.isHardBrakeSelected() 
//                                || saveItem.isHardTurnSelected()
//                                || saveItem.isHardVerticalSelected();
//            
//            if (!styleValid){
//                
//                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeDrivingStyleMessage"), null);
//                FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
//                
//                valid = false;
//            }
//        }
//        else if (EventSubCategory.OFFHOURS.equals(saveItem.getEventSubCategory())){
//            if (saveItem.isAnytime()) {
//                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editRedFlag_typeOffHourMessage"), null);
//                FacesContext.getCurrentInstance().addMessage("edit-form:editRedFlagType", message);
//                
//                valid = false;
//            }
//        }
        
        // just validating if a zone id is selected, the subtypes will be checked later
        else if(EventSubCategory.ZONES.equals(saveItem.getEventSubCategory()) && checkSubTypes){
//            if ((!Boolean.TRUE.equals(saveItem.getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE)) && 
//                    !Boolean.TRUE.equals(saveItem.getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE))) 
//                    && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("defineAlerts"))))
//            {
//                final String summary = MessageUtil.formatMessageString("editZoneAlert_noAlerts");
//                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
//                FacesContext.getCurrentInstance().addMessage("edit-form:editZoneAlert-arrival", message);
//                valid = false;
//            }
            
            //Validate required Zone id is selected
            if ((saveItem.getZoneID() == null)) {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
                FacesContext.getCurrentInstance().addMessage("edit-form:editZoneAlert-zoneID", message); 
                valid = false;
            }
            
            //Validate required name is valid
//            if ((saveItem.getName() == null) || (saveItem.getName().length() == 0)
//                    && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("name"))))
//            {
//                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
//                FacesContext.getCurrentInstance().addMessage("edit-form:editZoneAlert-name", message);
//                valid = false;
//            }
           
        }
        
        // validate escalation e-mail matches an existing email address for this user
        boolean isInPicker = this.getEscalationEmailPicker().containsLabel(saveItem.getEscEmail());
        if((saveItem.getEscEmail() != null && saveItem.getEscEmail().trim().length() > 0) && !isInPicker) {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editAlerts_picker_unknownEmail"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:escEmailAddressInput", message);
            valid = false;            
        }
        //if batch editing, users cannot remove escEmail (as this may be required because of other entries in escalationList)
        boolean batchEditingEscEmail = (isBatchEdit() && getUpdateField().get("emailEscalationPersonID")); 
        if(batchEditingEscEmail && (this.getItem().getEscEmail() == null  || this.getItem().getEscEmail().equals(""))) {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("editAlerts_noBatchRemoveEscEmail"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:escEmailAddressInput", message);
            valid = false;  
        }
        
        // check on selected types
        if ( valid  && checkSubTypes ) {
            if ( !saveItem.validateSelectedAlertTypes() ) {
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("atLeastOne"), null);
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
                flag.setStartTOD(RedFlagAlert.MIN_TOD);
                flag.setStopTOD(RedFlagAlert.MIN_TOD);
            }
            // since getItem auto-creates the below, null 'em here before saving
//            if (flag.getSpeedSettings() != null && flag.getSpeedSettings()[0] == null) {
//                flag.setSpeedSettings(null);
//            }
//            if(flag.getEmailTos() != null && !flag.getEmailTos().isEmpty()) {
//                flag.getEmailTos().remove("");
//            }
//            flag.setEmailTo(flag.getEmailTos());
            
            if(item.getEscEmail() == null || item.getEscEmail().equals("")){
                flag.clearEscEmail();
            } else {
                AlertEscalationItem newEscEmail = new AlertEscalationItem(flag.getEmailEscalationPersonID(), -1);
                flag.clearEscEmail();
                flag.getEscalationList().add(0,newEscEmail);
            }

            if(isBatchEdit()){
                if(item.getPhNumbers() == null || item.getPhNumbers().isEmpty()) {
                    flag.clearPhNumbers();
                } else {
                    ArrayList<AlertEscalationItem> newPhNums = new ArrayList<AlertEscalationItem>();
                    for(AlertEscalationItem item: flag.getEscalationList()){
                        if(item.getEscalationOrder()>=0){
                            newPhNums.add(item);
                        }
                    }
                    flag.clearPhNumbers();
                    flag.getEscalationList().addAll(newPhNums);
                }
            }
           
            copyVoiceEscalationItems(flag, getItem());
            
            flag.setEscalationTimeBetweenRetries(item.getEscalationTimeBetweenRetries());
            if(LimitType.COUNT.equals(item.getLimitType())) {
                flag.setMaxEscalationTries(item.getLimitValue());
                flag.setMaxEscalationTryTime(null);
            } else if(LimitType.TIME.equals(item.getLimitType())) {
                flag.setMaxEscalationTryTime(item.getLimitValue());
                flag.setMaxEscalationTries(null);
            }
            flag.setEscalationTimeBetweenRetries(item.getDelay().getCode());
            
            if (create) {
                flag.setAlertID(redFlagAlertsDAO.create(getAccountID(), flag));
            }
            else {
                redFlagAlertsDAO.update(flag);
            }
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "redFlag_added" : "redFlag_updated", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }
    private void copyVoiceEscalationItems(RedFlagOrZoneAlertView to, RedFlagOrZoneAlertView from){
        if (getUpdateField().get("escalationPersonIDs")){
            to.setEscalationPersonIDs(from.getVoiceEscalationPersonIDs());
        }
    }

    @Override
    protected String getDisplayRedirect() {
        
//        if (getItem().getType().equals(AlertMessageType.ALERT_TYPE_ZONES)){
//          
//             return "pretty:adminZone";
//        }
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
    
    public void addPhNumberSlot_plusAddItem() {
        addPhNumberSlot();
        getEscalationPeoplePicker().addItem();
    }
    public void addPhNumberSlot() {
        try {
            boolean okToAddAnother = true;
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> map = context.getExternalContext().getRequestParameterMap();
            for (String key : map.keySet()) {
                if (key.endsWith("phNumInput")) {
                    String[] words = key.split(":");
                    int fieldIndex = Integer.parseInt(words[2]);
                    // if the user changed ANY fields update phNumbers
                    if (!map.get(key).equalsIgnoreCase(getItem().getPhNumbers().get(fieldIndex))) {
                        getItem().getPhNumbers().set(fieldIndex, map.get(key));
                        //phNumberPersonIDs.set(fieldIndex, Integer.parseInt(map.get(key)));
                    }
                    // if there are ANY empty slots, it is NOT okToAddAnother
                    if (map.get(key) == null || map.get(key).equals("")) {
                        okToAddAnother = false;
                    }
                }
            }

            if (okToAddAnother) {
                getItem().getPhNumbers().add("");
            }
        } catch (Exception e) {
            logger.error(e);
            logger.error("addPhNumberSlot() failed");
        }
    }

    public static class RedFlagOrZoneAlertView extends RedFlagAlert implements BaseAdminAlertsBean.BaseAlertView {

        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private Boolean anytime;
        @Column(updateable = false)
        private boolean selected;
        @Column(updateable = false)
        private Map<String,Boolean> selectedAlertTypes;
        private Boolean[] speedSelected;
        @Column(updateable = false)
        private RedFlagOrZoneAlertsBean redFlagOrZoneAlertsBean;
        @Column(updateable = false)
        private Zone              zone;
        @Column(updateable = false)
        private EventSubCategory eventSubCategory;
        @Column(updateable = false)
        private String newEmail;
        @Column(updateable = false)
        private Integer removeId;

        @Column(updateable = false)
        private Delay delay;
        @Column(updateable = false)
        private Integer limitValue;
        @Column(updateable = false)
        private LimitType limitType;
        
        @Column(updateable = false)
        private String escEmail;
        @Column(updateable = false)
       private List<String> phNumbers;
        
        @Column(updateable = false)
        private List<String> emailTos;
        
        public RedFlagOrZoneAlertView() {
            super();
            phNumbers = new ArrayList<String>();
            emailTos = new ArrayList<String>();

            ensureEmptySlot(phNumbers);
            ensureEmptySlot(emailTos);

            initAlertMessageTypeMap();
        }
        public void clearPhNumbers() {
            Iterator<AlertEscalationItem> iterator =getEscalationList().iterator();
            while(iterator.hasNext()){
                AlertEscalationItem item = iterator.next();
                if(item.getEscalationOrder()>=0) {
                    iterator.remove();
                }
            }
        }
        public void clearEscEmail() {
            Iterator<AlertEscalationItem> iterator =getEscalationList().iterator();
            while(iterator.hasNext()){
                AlertEscalationItem item = iterator.next();
                if(item.getEscalationOrder()<0) {
                    iterator.remove();
                    break;
                }
            }
        }
        private void initAlertMessageTypeMap(){
            
            selectedAlertTypes = new HashMap<String, Boolean>();
            for (AlertMessageType amt : EnumSet.allOf(AlertMessageType.class)){
                selectedAlertTypes.put(amt.name(), false);
            }
            for (EventSubCategory esc : EnumSet.allOf(EventSubCategory.class)){
                selectAlertMessageTypeIfOnlyOneInEventSubCategory(esc);
            }
        }
        private void selectAlertMessageTypeIfOnlyOneInEventSubCategory(EventSubCategory anEventSubCategory){
            Set<AlertMessageType> selectedSet = anEventSubCategory.getAlertMessageTypeSet();
            if(selectedSet != null && selectedSet.size() == 1){
                Iterator<AlertMessageType> it = selectedSet.iterator();
                selectedAlertTypes.put(it.next().name(), true); 
            }
        }
        private void getSelectedAlertMessageTypes(RedFlagAlert flag){
            List<AlertMessageType> selectedAlertMessageTypes = flag.getTypes();
            if (selectedAlertMessageTypes != null){
                for(AlertMessageType amt : selectedAlertMessageTypes){
                    selectedAlertTypes.put(amt.name(), true);
                }
            }
        }

        public void removePhNumber() {
            phNumbers.remove(redFlagOrZoneAlertsBean.phNumbersDataTable.getRowData());
        }

        public void removeEmailTo() {
            emailTos.remove(redFlagOrZoneAlertsBean.emailTosDataTable.getRowData());
        }

        public void addEmailToSlot() {
            try {
                boolean okToAddAnother = true;
                FacesContext context = FacesContext.getCurrentInstance();
                Map<String, String> map = context.getExternalContext().getRequestParameterMap();
                for (String key : map.keySet()) {
                    if (key.endsWith("emailAddressesInput")) {
                        String[] words = key.split(":");
                        int fieldIndex = Integer.parseInt(words[2]);
                        // if the user changed ANY fields update emailTos
                        if (!map.get(key).equalsIgnoreCase(emailTos.get(fieldIndex))) {
                            emailTos.set(fieldIndex, map.get(key));
                        }
                        // if there are ANY empty slots, it is NOT okToAddAnother
                        if (map.get(key) == null || map.get(key).equals("")) {
                            okToAddAnother = false;
                        }
                    }
                }

                if (okToAddAnother) {
                    emailTos.add("");
                }
            } catch (Exception e) {
                logger.error(e);
                logger.error("addEmailTosSlot() failed");
            }
        }
        public String getZonesString() {
            return getZoneName();
        }
        @Override
        public Integer getId() {
            // TODO Auto-generated method stub
            return getAlertID();
        }
        @Override
        public String getFullName(){
            if(super.getFullName() == null && super.getUserID() != null)
                super.setFullName(findOwnerName(getUserID()));
            return super.getFullName();
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
                super.setStartTOD(RedFlagAlert.MIN_TOD);
            }
            else {
                super.setStartTOD(startTOD);
            }
        }

        @Override
        public void setStopTOD(Integer stopTOD) {
            if (stopTOD == null) {
                super.setStopTOD(RedFlagAlert.MIN_TOD);
            }
            else {
                super.setStopTOD(stopTOD);
            }
        }

        public EventSubCategory getEventSubCategory() {
            return eventSubCategory;
        }
        public void setEventSubCategory(EventSubCategory eventSubCategory) {
            this.eventSubCategory = eventSubCategory;
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
            if ( selectedAlertTypes.containsKey(AlertMessageType.ALERT_TYPE_HARD_TURN.name()) &&
                 selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_ACCEL.name()) != null ) {
                return selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_ACCEL.name()).booleanValue();
            }
            return false;
        }

        public void setHardAccelerationSelected(boolean hardAccelerationSelected) {

            selectedAlertTypes.put(AlertMessageType.ALERT_TYPE_HARD_ACCEL.name(), hardAccelerationSelected);
        }

        public boolean isHardBrakeSelected() {
            if ( selectedAlertTypes.containsKey(AlertMessageType.ALERT_TYPE_HARD_TURN.name()) &&
                 selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_BRAKE.name()) != null ) {
                return selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_BRAKE.name()).booleanValue();
            }
            return false;
        }

        public void setHardBrakeSelected(boolean hardBrakeSelected) {
            selectedAlertTypes.put(AlertMessageType.ALERT_TYPE_HARD_BRAKE.name(), hardBrakeSelected);
        }

        public boolean isHardTurnSelected() {
            if ( selectedAlertTypes.containsKey(AlertMessageType.ALERT_TYPE_HARD_TURN.name()) &&
                 selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_TURN.name()) != null ) {
                return selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_TURN.name()).booleanValue();
            }
            return false;

        }

        public void setHardTurnSelected(boolean hardTurnSelected) {
            selectedAlertTypes.put(AlertMessageType.ALERT_TYPE_HARD_TURN.name(), hardTurnSelected);
        }

        public boolean isHardVerticalSelected() {
            if ( selectedAlertTypes.containsKey(AlertMessageType.ALERT_TYPE_HARD_BUMP.name()) && 
                 selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_BUMP.name()) != null ) {
                return selectedAlertTypes.get(AlertMessageType.ALERT_TYPE_HARD_BUMP.name()).booleanValue();
            }
            return false;
        }

        public void setHardVerticalSelected(boolean hardVerticalSelected) {
            selectedAlertTypes.put(AlertMessageType.ALERT_TYPE_HARD_BUMP.name(), hardVerticalSelected);
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
        @Override
        public Integer getZoneID() {
            if( super.getZoneID() == null){
                setZoneID(((Integer)redFlagOrZoneAlertsBean.getZones().get(0).getValue()));
            }
            return super.getZoneID();
        }
        public Zone getZone()
        {
            if (getZoneID() == null){
                setZoneID(((Integer)redFlagOrZoneAlertsBean.getZones().get(0).getValue()));
            }
            if (/*zone == null && */getZoneID() != null)
                zone = redFlagOrZoneAlertsBean.getZoneByID(getZoneID());
            return zone;
        }
        public String getZoneName(){
            if (EventSubCategory.ZONES.equals(getEventSubCategory())){
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
            List<SelectItem> results = new ArrayList<SelectItem>();
            int[] limitBy;
            if(LimitType.COUNT.equals(getLimitType()))
                limitBy = CALL_LIMIT_COUNT;
            else
                limitBy = CALL_LIMIT_MINUTES;
            
            for(int i: limitBy){
             results.add(new SelectItem(i*DateUtil.SECONDS_IN_MINUTE, i+""));   
            }
            return results;
        }
        public Delay getDelay() {
            return delay;
        }

        public void setDelay(Delay delay) {
            this.delay = delay;
        }
        public String getLimitValueDisplay() {
            if(LimitType.TIME.equals(getLimitType()) && limitValue != null)
                return (limitValue/DateUtil.SECONDS_IN_MINUTE)+"";
            
            return limitValue+"";
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
            if ((getEventSubCategory() != null) && (EventSubCategory.ZONES.equals(getEventSubCategory()))){
               return getZone().getPointsString();
            }
            return null;
        }
        public void setZonePointsString(String pointsString){
            if ((getEventSubCategory() != null) && (EventSubCategory.ZONES.equals(getEventSubCategory()))){
                getZone().setPointsString(pointsString);
            }
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

//        private void setEmailTos(){
//            Iterator<String> it = emailTos.iterator();
//            while(it.hasNext()){
//                if (it.next().isEmpty()) it.remove();
//            }
//            this.setEmailTo(emailTos);
//        }
        public Map<String, Boolean> getSelectedAlertTypes() {
            return selectedAlertTypes;
        }
        
        private boolean validateSelectedAlertTypes(){
            if ( selectedAlertTypes != null ) {
                Set<AlertMessageType> selectedSet = eventSubCategory.getAlertMessageTypeSet();
                for(AlertMessageType amt:selectedSet){
                    if (selectedAlertTypes.get(amt.name())){
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public Integer getEmailEscalationPersonID() {
            return super.getEmailEscalationPersonID();
        }
        @Override
        public List<Integer> getVoiceEscalationPersonIDs() {
             return super.getVoiceEscalationPersonIDs();
        }
        @Override
        public void setEmailEscalationPersonID(Integer escEmailPersonID) {
            super.setEmailEscalationPersonID(escEmailPersonID);            
        }
        @Override
        public void setEscalationPersonIDs(List<Integer> voiceEscalationPersonIDs) {
            AlertEscalationItem lastResortEmail = null;
            List<AlertEscalationItem> oldEscalationList = getEscalationList();
            
            if(null != oldEscalationList) {
                for(AlertEscalationItem item: oldEscalationList){
                    if(item.getEscalationOrder().equals(-1)){
                        lastResortEmail = item;
                    }
                }
            }
            List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
            if(lastResortEmail != null){
                escalationList.add(lastResortEmail);
            }
            int i=1;
            for(Integer personID : voiceEscalationPersonIDs){
                escalationList.add(new AlertEscalationItem(personID,i++));
            }
            setEscalationList(escalationList);
        }
        public Object addEmail(){
          emailTos.add("");
          return null;
        }
        public Object removeEmail(){
            
            if (removeId == null) return null;
            emailTos.remove(removeId.intValue());
            return null;
        }
        public String getNewEmail() {
            return newEmail;
        }
        public void setNewEmail(String newEmail) {
            this.newEmail = newEmail;
        }
        public Integer getRemoveId() {
            return removeId;
        }
        public void setRemoveId(Integer removeId) {
            this.removeId = removeId;
        }
    }
    
    public void setRedFlagAlertsDAO(RedFlagAlertDAO redFlagAlertsDAO) {
        this.redFlagAlertsDAO = redFlagAlertsDAO;
    }
}
