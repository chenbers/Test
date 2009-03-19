package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagAlertsBean extends BaseAdminAlertsBean<RedFlagAlertsBean.RedFlagAlertView>
{
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2 };

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("type");
    }

    private RedFlagAlertDAO           redFlagAlertDAO;

    public void setRedFlagAlertDAO(RedFlagAlertDAO redFlagAlertDAO)
    {
        this.redFlagAlertDAO = redFlagAlertDAO;
    }

    @Override
    protected List<RedFlagAlertView> loadItems()
    {
        // get the red flags
        final List<RedFlagAlert> plainRedFlagAlerts = redFlagAlertDAO.getRedFlagAlerts(getAccountID());

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
    private RedFlagAlertView createRedFlagAlertView(RedFlagAlert flag)
    {
        final RedFlagAlertView flagView = new RedFlagAlertView();
        flagView.setAnytime(true);
        BeanUtils.copyProperties(flag, flagView);
        if(flagView.getStartTOD() == null)
            flagView.setStartTOD(BaseAlert.MIN_TOD);
        if(flagView.getStopTOD() == null)
            flagView.setStopTOD(BaseAlert.MIN_TOD);
        flagView.setAnytime(isAnytime(flagView));
        flagView.setSelected(false);
        return flagView;
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
        return "redFlagsHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_RED_FLAG_PREFS;
    }

    @Override
    protected RedFlagAlertView createAddItem()
    {
        final RedFlagAlert flag = new RedFlagAlert();
        return createRedFlagAlertView(flag);
    }

    @Override
    public RedFlagAlertView getItem()
    {
        final RedFlagAlertView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[Device.NUM_SPEEDS]);
        if (item.getSpeedLevels() == null)
            item.setSpeedLevels(new RedFlagLevel[Device.NUM_SPEEDS]);
        return item;
    }
    
    @Override
    public String batchEdit()
    {
        String returnString = super.batchEdit();
        getItem().setAnytime(isAnytime(getItem()));
        return returnString;
    }

    @Override
    protected void doDelete(List<RedFlagAlertView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final RedFlagAlertView flag : deleteItems)
        {
            redFlagAlertDAO.deleteByID(flag.getRedFlagAlertID());

            // add a message
            final String summary = MessageUtil.formatMessageString("redFlag_deleted", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    public String cancelEdit()
    {
        getItem().setType(null);

        return super.cancelEdit();
    }

    @Override
    public String save()
    {
        final Map<String, Boolean> updateField = getUpdateField();
        if (isBatchEdit())
        {
            updateField.put("type", true);
            if(updateField.get("anytime")){
                updateField.put("startTOD",true);
                updateField.put("stopTOD",true);
            }
        }

        // null out unselected items
        if (getItem().getType().equals("speed"))
        {
            for (int i = 0; i < getItem().getSpeedLevels().length; i++)
                if (!getItem().getSpeedSelected()[i])
                    getItem().getSpeedLevels()[i] = RedFlagLevel.NONE;

            getItem().setHardAccelerationLevel(RedFlagLevel.NONE);
            getItem().setHardBrakeLevel(RedFlagLevel.NONE);
            getItem().setHardTurnLevel(RedFlagLevel.NONE);
            getItem().setHardVerticalLevel(RedFlagLevel.NONE);
            getItem().setHardAccelerationSelected(false);
            getItem().setHardBrakeSelected(false);
            getItem().setHardTurnSelected(false);
            getItem().setHardVerticalSelected(false);
            getItem().setSeatBeltLevel(RedFlagLevel.NONE);
            getItem().setCrashLevel(RedFlagLevel.NONE);

            // if batch editing and changing speed, make sure the nulled items get set
            if (isBatchEdit())
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && updateField.get(key))
                    {
                        updateField.put("hardAccelerationLevel", true);
                        updateField.put("hardBrakeLevel", true);
                        updateField.put("hardTurnLevel", true);
                        updateField.put("hardVerticalLevel", true);
                        updateField.put("seatBeltLevel", true);
                        updateField.put("crashLevel", true);
                        break;
                    }
        }
        else if (getItem().getType().equals("drivingStyle"))
        {
            getItem().setSpeedSettings(null);
            getItem().setSpeedLevels(null);
            getItem().setSpeedSelected(null);
            getItem().setSeatBeltLevel(RedFlagLevel.NONE);
            getItem().setCrashLevel(RedFlagLevel.NONE);

            if (!getItem().isHardAccelerationSelected())
                getItem().setHardAccelerationLevel(RedFlagLevel.NONE);
            if (!getItem().isHardBrakeSelected())
                getItem().setHardBrakeLevel(RedFlagLevel.NONE);
            if (!getItem().isHardTurnSelected())
                getItem().setHardTurnLevel(RedFlagLevel.NONE);
            if (!getItem().isHardVerticalSelected())
                getItem().setHardVerticalLevel(RedFlagLevel.NONE);

            // if batch editing and changing driving style, make sure the nulled items get set
            if (isBatchEdit())
            {
                updateField.put("hardAccelerationLevel", updateField.get("hardAcceleration"));
                updateField.put("hardBrakeLevel", updateField.get("hardBrake"));
                updateField.put("hardTurnLevel", updateField.get("hardTurn"));
                updateField.put("hardVerticalLevel", updateField.get("hardVertical"));

                if (updateField.get("hardAcceleration") || updateField.get("hardBrake") || updateField.get("hardTurn") || updateField.get("hardVertical"))
                {
                    for (final String key : updateField.keySet())
                        if (key.startsWith("speed") && (key.length() <= 7))
                            updateField.put(key, true);
                    updateField.put("seatBeltLevel", true);
                    updateField.put("crashLevel", true);
                    
                }
            }
        }
        else if (getItem().getType().equals("seatBelt"))
        {
            getItem().setSpeedSettings(null);
            getItem().setSpeedLevels(null);
            getItem().setSpeedSelected(null);
            getItem().setHardAccelerationLevel(RedFlagLevel.NONE);
            getItem().setHardBrakeLevel(RedFlagLevel.NONE);
            getItem().setHardTurnLevel(RedFlagLevel.NONE);
            getItem().setHardVerticalLevel(RedFlagLevel.NONE);
            getItem().setHardAccelerationSelected(false);
            getItem().setHardBrakeSelected(false);
            getItem().setHardTurnSelected(false);
            getItem().setHardVerticalSelected(false);
            getItem().setCrashLevel(RedFlagLevel.NONE);

            // if batch editing and changing seatBelt, make sure the nulled items get set
            if (isBatchEdit() && updateField.get("seatBeltLevel"))
            {
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7))
                        updateField.put(key, true);
                updateField.put("hardAccelerationLevel", true);
                updateField.put("hardBrakeLevel", true);
                updateField.put("hardTurnLevel", true);
                updateField.put("hardVerticalLevel", true);
            }
        }
        else if (getItem().getType().equals("crash"))
        {
            getItem().setSpeedSettings(null);
            getItem().setSpeedLevels(null);
            getItem().setSpeedSelected(null);
            getItem().setHardAccelerationLevel(RedFlagLevel.NONE);
            getItem().setHardBrakeLevel(RedFlagLevel.NONE);
            getItem().setHardTurnLevel(RedFlagLevel.NONE);
            getItem().setHardVerticalLevel(RedFlagLevel.NONE);
            getItem().setHardAccelerationSelected(false);
            getItem().setHardBrakeSelected(false);
            getItem().setHardTurnSelected(false);
            getItem().setHardVerticalSelected(false);
            getItem().setSeatBeltLevel(RedFlagLevel.NONE);

            // if batch editing and changing crash, make sure the nulled items get set
            if (isBatchEdit() && updateField.get("crashLevel"))
            {
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7))
                        updateField.put(key, true);
                updateField.put("hardAccelerationLevel", true);
                updateField.put("hardBrakeLevel", true);
                updateField.put("hardTurnLevel", true);
                updateField.put("hardVerticalLevel", true);
            }
        }

        return super.save();
    }

    @Override
    protected RedFlagAlertView revertItem(RedFlagAlertView editItem)
    {
        return createRedFlagAlertView(redFlagAlertDAO.findByID(editItem.getRedFlagAlertID()));
    }
    
    protected boolean validateSaveItem(RedFlagAlertView saveItem)
    {
        boolean valid = super.validateSaveItem(saveItem);
        valid = super.validateSaveItem(saveItem);
        if ((saveItem.getName() == null) || (saveItem.getName().length() == 0)
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("name"))))
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:name", message);
            valid = false;
        }
        return valid;
    }

    @Override
    protected void doSave(List<RedFlagAlertView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final RedFlagAlertView flag : saveItems)
        {
            // if batch editing, copy individual speed settings by hand
            if (isBatchEdit())
            {
                flag.setSpeedSelected(null);

                final Map<String, Boolean> updateField = getUpdateField();
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7) && (updateField.get(key) == true))
                    {
                        final int index = Integer.parseInt(key.substring(5));
                        flag.getSpeedSettings()[index] = getItem().getSpeedSettings()[index];
                        flag.getSpeedLevels()[index] = getItem().getSpeedLevels()[index];
                    }
            }

            // since getItem auto-creates the below, null 'em here before saving
            if (flag.getSpeedSettings() != null && flag.getSpeedSettings()[0] == null)
            {
                flag.setSpeedSettings(null);
                flag.setSpeedLevels(null);
            }

            if (create)
                flag.setRedFlagAlertID(redFlagAlertDAO.create(getAccountID(), flag));
            else
                redFlagAlertDAO.update(flag);

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "redFlag_added" : "redFlag_updated", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "go_adminRedFlag";
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditRedFlag";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminRedFlags";
    }

    public static class RedFlagAlertView extends RedFlagAlert implements BaseAdminAlertsBean.BaseAlertView
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private Boolean           anytime;
        @Column(updateable = false)
        private boolean           selected;
        @Column(updateable = false)
        private String            type;
        @Column(updateable = false)
        private Boolean[]         speedSelected;
        @Column(updateable = false)
        private Boolean           hardAccelerationSelected;
        @Column(updateable = false)
        private Boolean           hardBrakeSelected;
        @Column(updateable = false)
        private Boolean           hardTurnSelected;
        @Column(updateable = false)
        private Boolean           hardVerticalSelected;

        public Integer getId()
        {
            return getRedFlagAlertID();
        }

        public boolean isAnytime()
        {
            return anytime;
        }

        public void setAnytime(boolean anytime)
        {
            this.anytime = anytime;
        }
        
        @Override
        public void setStartTOD(Integer startTOD)
        {
            if(startTOD == null)
            {
                super.setStartTOD(BaseAlert.MIN_TOD);
            }
            else
            {
                super.setStartTOD(startTOD);
            }
        }
        
        @Override
        public void setStopTOD(Integer stopTOD)
        {
          
            if(stopTOD == null)
            {
                super.setStopTOD(BaseAlert.MIN_TOD);
            }
            else
            {
                super.setStopTOD(stopTOD);
            }
        }

        public String getType()
        {
            if (type == null)
            {
                if (getSeatBeltLevel() != null && getSeatBeltLevel() != RedFlagLevel.NONE)
                    type = "seatBelt";
                else if (isHardAccelerationSelected() || isHardBrakeSelected() || isHardTurnSelected() || isHardVerticalSelected())
                    type = "drivingStyle";
                else if (getCrashLevel() != null && getCrashLevel() != RedFlagLevel.NONE)
                    type = "crash";
                else
                    type = "speed";
            }
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public Boolean[] getSpeedSelected()
        {
            if ((speedSelected == null) && (getSpeedLevels() != null))
            {
                speedSelected = new Boolean[Device.NUM_SPEEDS];
                for (int i = 0; i < speedSelected.length; i++)
                    speedSelected[i] = (getSpeedLevels()[i] != null) && (getSpeedLevels()[i] != RedFlagLevel.NONE);
            }
            return speedSelected;
        }

        public void setSpeedSelected(Boolean[] speedSelected)
        {
            this.speedSelected = speedSelected;
        }

        public boolean isHardAccelerationSelected()
        {
            if (hardAccelerationSelected == null)
            {
                final RedFlagLevel level = super.getHardAccelerationLevel();
                hardAccelerationSelected = (level != null) && (level != RedFlagLevel.NONE);
            }
            return hardAccelerationSelected;
        }

        public void setHardAccelerationSelected(boolean hardAccelerationSelected)
        {
            this.hardAccelerationSelected = hardAccelerationSelected;
        }

        public boolean isHardBrakeSelected()
        {
            if (hardBrakeSelected == null)
            {
                final RedFlagLevel level = super.getHardBrakeLevel();
                hardBrakeSelected = (level != null) && (level != RedFlagLevel.NONE);
            }
            return hardBrakeSelected;
        }

        public void setHardBrakeSelected(boolean hardBrakeSelected)
        {
            this.hardBrakeSelected = hardBrakeSelected;
        }

        public boolean isHardTurnSelected()
        {
            if (hardTurnSelected == null)
            {
                final RedFlagLevel level = super.getHardTurnLevel();
                hardTurnSelected = (level != null) && (level != RedFlagLevel.NONE);
            }
            return hardTurnSelected;
        }

        public void setHardTurnSelected(boolean hardTurnSelected)
        {
            this.hardTurnSelected = hardTurnSelected;
        }

        public boolean isHardVerticalSelected()
        {
            if (hardVerticalSelected == null)
            {
                final RedFlagLevel level = super.getHardVerticalLevel();
                hardVerticalSelected = (level != null) && (level != RedFlagLevel.NONE);
            }
            return hardVerticalSelected;
        }

        public void setHardVerticalSelected(boolean hardVerticalSelected)
        {
            this.hardVerticalSelected = hardVerticalSelected;
        }

        @Override
        public RedFlagLevel getHardAccelerationLevel()
        {
            if (!isHardAccelerationSelected())
                return RedFlagLevel.NONE;
            return super.getHardAccelerationLevel();
        }

        @Override
        public RedFlagLevel getHardBrakeLevel()
        {
            if (!isHardBrakeSelected())
                return RedFlagLevel.NONE;
            return super.getHardBrakeLevel();
        }

        @Override
        public RedFlagLevel getHardTurnLevel()
        {
            if (!isHardTurnSelected())
                return RedFlagLevel.NONE;
            return super.getHardTurnLevel();
        }

        @Override
        public RedFlagLevel getHardVerticalLevel()
        {
            if (!isHardVerticalSelected())
                return RedFlagLevel.NONE;
            return super.getHardVerticalLevel();
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
