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
        BeanUtils.copyProperties(flag, flagView);
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
        flag.setSpeedSettings(new Integer[15]);
        return createRedFlagAlertView(flag);
    }

    @Override
    public RedFlagAlertView getItem()
    {
        final RedFlagAlertView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[15]);
        if (item.getSpeedLevels() == null)
            item.setSpeedLevels(new RedFlagLevel[15]);
        return item;
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
    protected void doSave(List<RedFlagAlertView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final RedFlagAlertView flag : saveItems)
        {
            // if batch editing, copy individual speed settings by hand
            if (isBatchEdit())
            {
                final Map<String, Boolean> updateField = getUpdateField();
                for (final String key : updateField.keySet())
                    if (key.startsWith("speed") && (key.length() <= 7) && (updateField.get(key) == true))
                    {
                        final int index = Integer.parseInt(key.substring(5));
                        flag.getSpeedSettings()[index] = getItem().getSpeedSettings()[index];
                    }
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
        private static final long    serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private boolean              anytime;
        @Column(updateable = false)
        private boolean              selected;
        @Column(updateable = false)
        private String               type;
        @Column(updateable = false)
        private List<RedFlagSetting> settings;

        public Integer getId()
        {
            return getRedFlagAlertID();
        }

        public boolean isAnytime()
        {
            if (!anytime)
                anytime = BaseAdminAlertsBean.isAnytime(this);
            return anytime;
        }

        public void setAnytime(boolean anytime)
        {
            this.anytime = anytime;
            BaseAdminAlertsBean.onSetAnytime(this, anytime);
        }

        public String getType()
        {
            if (type == null)
                type = "speed";
            return type;
        }

        public void setType(String type)
        {
            if ((type != this.type) && (type != null) && !type.equals(this.type))
                settings = null;
            this.type = type;
        }

        public List<RedFlagSetting> getSettings()
        {
            if (settings == null)
            {
                settings = new LinkedList<RedFlagSetting>();
                if (getType().equals("speed"))
                {
                    for (int i = 0; i < getSpeedSettings().length; i++)
                        settings.add(new RedFlagSetting("speed" + i, String.valueOf((i + 1) * 5), getSpeedSettings()[i], (getSpeedLevels()[i] != null)
                                && !getSpeedLevels()[i].equals(RedFlagLevel.NONE), getSpeedLevels()[i]));
                }
                else if (getType().equals("drivingStyle"))
                {
                    settings.add(new RedFlagSetting("hardAcceleration", MessageUtil.getMessageString("editDevice_hardAcceleration"), getHardAcceleration(),
                            (getHardAccelerationLevel() != null) && !getHardAccelerationLevel().equals(RedFlagLevel.NONE), getHardAccelerationLevel()));
                    settings.add(new RedFlagSetting("hardBrake", MessageUtil.getMessageString("editDevice_hardBrake"), getHardBrake(), (getHardBrakeLevel() != null)
                            && !getHardBrakeLevel().equals(RedFlagLevel.NONE), getHardBrakeLevel()));
                    settings.add(new RedFlagSetting("hardTurn", MessageUtil.getMessageString("editDevice_hardTurn"), getHardTurn(), (getHardTurnLevel() != null)
                            && !getHardTurnLevel().equals(RedFlagLevel.NONE), getHardTurnLevel()));
                    settings.add(new RedFlagSetting("hardVertical", MessageUtil.getMessageString("editDevice_hardVertical"), getHardVertical(), (getHardVerticalLevel() != null)
                            && !getHardVerticalLevel().equals(RedFlagLevel.NONE), getHardVerticalLevel()));
                }
                else if (getType().equals("safety"))
                {
                    settings.add(new RedFlagSetting("seatBelt", null, null, (getSeatBeltLevel() != null) && !getSeatBeltLevel().equals(RedFlagLevel.NONE), getSeatBeltLevel()));
                }
            }
            return settings;
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

    public static class RedFlagSetting
    {
        private String       label;
        private Integer      value;
        private boolean      selected;
        private RedFlagLevel severity;

        public RedFlagSetting(String id, String label, Integer value, boolean selected, RedFlagLevel severity)
        {
            this.label = label;
            this.value = value;
            this.selected = selected;
            this.severity = severity;
        }

        public String getLabel()
        {
            return label;
        }

        void setLabel(String label)
        {
            this.label = label;
        }

        public Integer getValue()
        {
            return value;
        }

        public void setValue(Integer value)
        {
            this.value = value;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }

        public RedFlagLevel getSeverity()
        {
            return severity;
        }

        public void setSeverity(RedFlagLevel severity)
        {
            this.severity = severity;
        }
    }
}
