package com.inthinc.pro.backing;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;

public class ZonesBean extends BaseBean
{
    private static final Logger logger        = LogManager.getLogger(ZonesBean.class);

    private List<Zone>          items;
    private List<Zone>          filteredItems = new LinkedList<Zone>();
    private String              filterValue;
    private Zone                editItem;
    private ZoneDAO             zoneDAO;

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    /**
     * @return the items
     */
    public List<Zone> getItems()
    {
        if (items == null)
        {
            items = zoneDAO.getZonesInGroupHierarchy(getUser().getPerson().getGroupID());
            applyFilter();
        }
        return items;
    }

    /**
     * @return the filteredItems
     */
    public List<Zone> getFilteredItems()
    {
        if (items == null)
            getItems();
        return filteredItems;
    }

    /**
     * @return the number of filtered items.
     */
    public int getItemCount()
    {
        return filteredItems.size();
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue()
    {
        return filterValue;
    }

    /**
     * @param filterValue
     *            the filterValue to set
     */
    public void setFilterValue(String filterValue)
    {
        boolean changed = false;
        if ((filterValue != null) && (filterValue.trim().length() > 0))
        {
            filterValue = filterValue.trim().toLowerCase();
            changed = !filterValue.equals(this.filterValue);
        }
        else
        {
            filterValue = null;
            changed = this.filterValue != null;
        }

        if (changed)
        {
            this.filterValue = filterValue;
            applyFilter();
        }
    }

    /**
     * Applies the filter.
     */
    private void applyFilter()
    {
        filteredItems.clear();
        if (filterValue != null)
        {
            final boolean matchAllWords = matchAllFilterWords();
            final String[] filterWords = filterValue.split("\\s+");
            for (final Zone item : items)
            {
                boolean matched = false;
                for (final String word : filterWords)
                {
                    matched = matchesFilter(item, word);

                    // we can break if we didn't match and we're required to match all words,
                    // or if we did match and we're only required to match one word
                    if (matched ^ matchAllWords)
                        break;
                }
                if (matched)
                    filteredItems.add(item);
            }
        }
        else
            filteredItems.addAll(items);
    }

    /**
     * @return Whether matching all filter words is required to match an item. The default is <code>true</code>.
     */
    private boolean matchAllFilterWords()
    {
        return true;
    }

    /**
     * @param item
     *            The item to filter in or out of the results.
     * @param filterWord
     *            The lowercase filter word. If there are multiple words in the filter string this method will be called once for each word.
     * @return Whether the item matches the filter string.
     */
    private boolean matchesFilter(Zone item, String filterWord)
    {
        if ((item.getName() != null) && item.getName().toLowerCase().startsWith(filterWord))
            return true;
        if (item.getAddress() != null)
        {
            String[] words = item.getAddress().toLowerCase().split("\\s+");
            for (final String word : words)
                if (word.startsWith(filterWord))
                    return true;
        }
        if ((item.getZoneType() != null) && item.getZoneType().getName().toLowerCase().startsWith(filterWord))
            return true;

        return false;
    }

    /**
     * Called when the user chooses to add an item.
     */
    public void add()
    {
        editItem = new Zone();
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public void edit()
    {
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get("editID") != null)
        {
            final int editID = Integer.parseInt(parameterMap.get("editID"));
            for (final Zone item : items)
                if (item.getZoneID().equals(editID))
                {
                    editItem = item;
                    break;
                }
        }
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public void cancelEdit()
    {
        editItem = null;
    }

    /**
     * Called when the user clicks to save changes when adding or editing.
     */
    public void save()
    {
        // validate
        if (!validate())
            return;

        final boolean add = isAdd();

        final FacesContext context = FacesContext.getCurrentInstance();

        if (add)
            editItem.setZoneID(zoneDAO.create(getUser().getPerson().getAccountID(), editItem));
        else
            zoneDAO.update(editItem);

        // add a message
        final String summary = MessageUtil.formatMessageString(add ? "zone_added" : "zone_updated", editItem.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        if (add)
        {
            items.add(editItem);
            applyFilter();
        }
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public void delete()
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        // TODO: disconnect from zone alerts

        zoneDAO.deleteByID(editItem.getZoneID());

        // add a message
        final String summary = MessageUtil.formatMessageString("zone_deleted", editItem.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        items.remove(editItem);
        filteredItems.remove(editItem);
        editItem = null;
    }

    /**
     * @return Whether the current edit operation is an item add.
     */
    public boolean isAdd()
    {
        return (editItem != null) && (editItem.getZoneID() == null);
    }

    /**
     * @return An item that the user can edit. Either returns a new item to be added, a single item to be edited, or an item that represents multiple items being batch edited.
     */
    public Zone getEditItem()
    {
        return editItem;
    }

    /**
     * Perform custom validation on the list of items to save. If invalid, messages may be displayed via code similar to:
     * 
     * <pre>
     * final String summary = MessageUtil.getMessageString(&quot;error_message&quot;);
     * final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
     * context.addMessage(&quot;my-form:component-id&quot;, message);
     * </pre>
     * 
     * @return Whether the edit item passed validation.
     */
    private boolean validate()
    {
        return true;
    }
}
