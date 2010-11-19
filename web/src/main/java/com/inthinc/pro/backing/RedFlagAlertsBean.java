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
import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagAlertsBean extends BaseAdminAlertsBean<RedFlagAlertsBean.RedFlagAlertView> implements Serializable{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2066762539439571492L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 3 };
    private static final int[] DEFAULT_ADMIN_COLUMN_INDICES = new int[] { 4 };
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("type");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("owner"); // admin only

    }
    private RedFlagAlertDAO redFlagAlertDAO;

    public List<SelectItem> getAlertTypeSelectItems() {
        return AlertTypeSelectItems.INSTANCE.getSelectItems();
    }

    public void setRedFlagAlertDAO(RedFlagAlertDAO redFlagAlertDAO) {
        this.redFlagAlertDAO = redFlagAlertDAO;
    }

    @Override
    protected List<RedFlagAlertView> loadItems() {
    	List<RedFlagAlert> plainRedFlagAlerts = null;
    	if (this.getProUser().isAdmin()) {
    		plainRedFlagAlerts = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(getUser().getUserID());
    	}
    	else {
    		plainRedFlagAlerts = redFlagAlertDAO.getRedFlagAlertsByUserID(getUser().getUserID());
    	}

        // convert the RedFlagAlerts to RedFlagAlertViews
        final LinkedList<RedFlagAlertView> items = new LinkedList<RedFlagAlertView>();
        for (final RedFlagAlert flag : plainRedFlagAlerts)
            items.add(createRedFlagAlertView(flag));
        return items;
    }

    /**
     * Creates a RedFlagAlertView object from the given RedFlagAlert object.
     * 
     * @param flag
     *            The flag.
     * @return The new RedFlagAlertView object.
     */
    private RedFlagAlertView createRedFlagAlertView(RedFlagAlert flag) {
        final RedFlagAlertView flagView = new RedFlagAlertView();
        flagView.setAnytime(true);
        flagView.setHardAccelerationSelected(!flag.isHardAccelerationNull());
        flagView.setHardBrakeSelected(!flag.isHardBrakeNull());
        flagView.setHardTurnSelected(!flag.isHardTurnNull());
        flagView.setHardVerticalSelected(!flag.isHardVerticalNull());
        BeanUtils.copyProperties(flag, flagView);
        if (flagView.getStartTOD() == null)
            flagView.setStartTOD(BaseAlert.MIN_TOD);
        if (flagView.getStopTOD() == null)
            flagView.setStopTOD(BaseAlert.MIN_TOD);
        flagView.setAnytime(isAnytime(flagView));
        flagView.setSelected(false);
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
    protected RedFlagAlertView createAddItem() {
        final RedFlagAlert flag = new RedFlagAlert();
        RedFlagAlertView redFlagAlertView = createRedFlagAlertView(flag);
        redFlagAlertView.setAccountID(getAccountID());
        redFlagAlertView.setUserID(getUserID());
        return redFlagAlertView;
    }

    @Override
    public RedFlagAlertView getItem() {
        final RedFlagAlertView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS]);
        return item;
    }

    @Override
    protected void doDelete(List<RedFlagAlertView> deleteItems) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final RedFlagAlertView flag : deleteItems) {
            redFlagAlertDAO.deleteByID(flag.getAlertID());
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
    protected RedFlagAlertView revertItem(RedFlagAlertView editItem) {
        return createRedFlagAlertView(redFlagAlertDAO.findByID(editItem.getAlertID()));
    }

    @Override
    protected Boolean authorizeAccess(RedFlagAlertView item) {
        Integer acctID = item.getAccountID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    protected boolean validateSaveItem(RedFlagAlertView saveItem) {
    	
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
    protected void doSave(List<RedFlagAlertView> saveItems, boolean create) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final RedFlagAlertView flag : saveItems) {
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
                flag.setAlertID(redFlagAlertDAO.create(getAccountID(), flag));
            else
                redFlagAlertDAO.update(flag);
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "redFlag_added" : "redFlag_updated", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect() {
        return "pretty:adminRedFlag";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditRedFlag";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminRedFlags";
    }
    @Override
	public String getAlertPage() {

		return "editRedFlag";
	}

    public static class RedFlagAlertView extends RedFlagAlert implements BaseAdminAlertsBean.BaseAlertView {

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

        @Override
        public Integer getId() {
            // TODO Auto-generated method stub
            return getAlertID();
        }
    }
}
