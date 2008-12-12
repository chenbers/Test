package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.RedFlagPrefDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.RedFlagPref;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagsAdminBean extends BaseAdminBean<RedFlagsAdminBean.RedFlagPrefView>
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

    private RedFlagPrefDAO            redFlagPrefDAO;

    public void setRedFlagPrefDAO(RedFlagPrefDAO redFlagPrefDAO)
    {
        this.redFlagPrefDAO = redFlagPrefDAO;
    }

    @Override
    protected List<RedFlagPrefView> loadItems()
    {
        // get the red flags
        final List<RedFlagPref> plainRedFlagPrefs = redFlagPrefDAO.getRedFlagPrefs(getAccountID());

        // convert the RedFlagPrefs to RedFlagPrefViews
        final LinkedList<RedFlagPrefView> items = new LinkedList<RedFlagPrefView>();
        for (final RedFlagPref flag : plainRedFlagPrefs)
            items.add(createRedFlagPrefView(flag));

        return items;
    }

    /**
     * Creates a RedFlagPrefView object from the given RedFlagPref object.
     * 
     * @param flag
     *            The flag.
     * @return The new RedFlagPrefView object.
     */
    private RedFlagPrefView createRedFlagPrefView(RedFlagPref flag)
    {
        final RedFlagPrefView flagView = new RedFlagPrefView();
        BeanUtils.copyProperties(flag, flagView);
        flagView.setSelected(false);
        return flagView;
    }

    @Override
    protected boolean matchesFilter(RedFlagPrefView flag, String filterWord)
    {
        for (final String column : getTableColumns().keySet())
            if (getTableColumns().get(column).getVisible())
            {
                boolean matches = false;
                try
                {
                    matches = String.valueOf(org.apache.commons.beanutils.BeanUtils.getProperty(flag, column.replace('_', '.'))).toLowerCase().startsWith(filterWord);
                }
                catch (Exception e)
                {
                    logger.error("Error filtering on column " + column, e);
                }

                if (matches)
                    return true;
            }

        return false;
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
    protected RedFlagPrefView createAddItem()
    {
        final RedFlagPref flag = new RedFlagPref();
        flag.setSpeedSettings(new Integer[15]);
        return createRedFlagPrefView(flag);
    }

    @Override
    public RedFlagPrefView getItem()
    {
        final RedFlagPrefView item = super.getItem();
        if (item.getSpeedSettings() == null)
            item.setSpeedSettings(new Integer[15]);
        if (!item.isSensitivitiesInverted())
            item.invertSensitivities();
        return item;
    }

    @Override
    protected void doDelete(List<RedFlagPrefView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final RedFlagPrefView flag : deleteItems)
        {
            redFlagPrefDAO.deleteByID(flag.getRedFlagPrefID());

            // add a message
            final String summary = MessageUtil.formatMessageString("redFlag_deleted", flag.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    public String save()
    {
        if (getItem().isSensitivitiesInverted())
            getItem().invertSensitivities();

        return super.save();
    }

    @Override
    protected void doSave(List<RedFlagPrefView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final RedFlagPrefView flag : saveItems)
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
                flag.setRedFlagPrefID(redFlagPrefDAO.create(getAccountID(), flag));
            else
                redFlagPrefDAO.update(flag);

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

    public static class RedFlagPrefView extends RedFlagPref implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getRedFlagPrefID();
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
