package com.inthinc.pro.backing;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;

public class ZonesBean extends BaseBean
{
    private List<Zone> items;
    private List<Zone> filteredItems = new LinkedList<Zone>();
    private String     filterValue;
    protected int      page          = 1;
    private Zone       item;
    private boolean    editing;
    private ZoneDAO    zoneDAO;

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

        return false;
    }

    /**
     * @return the current page
     */
    public int getPage()
    {
        return page;
    }

    /**
     * @param page
     *            the current page to set
     */
    public void setPage(int page)
    {
        this.page = page;
    }

    /**
     * Called when the user chooses to add an item.
     */
    public void add()
    {
        item = new Zone();
        item.setCreated(new Date());
        editing = true;
    }

    /**
     * Called when the user chooses to display an item.
     */
    public void display()
    {
        selectItem("zoneID");
        editing = false;
    }

    private void selectItem(String idKey)
    {
        item = null;
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get(idKey) != null)
        {
            final int editID = Integer.parseInt(parameterMap.get(idKey));
            for (final Zone zone : items)
                if (zone.getZoneID().equals(editID))
                {
                    item = zone;
                    break;
                }
        }
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public void edit()
    {
        editing = true;
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public void cancelEdit()
    {
        editing = false;
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
            item.setZoneID(zoneDAO.create(getUser().getPerson().getGroupID(), item));
        else
            zoneDAO.update(item);

        // add a message
        final String summary = MessageUtil.formatMessageString(add ? "zone_added" : "zone_updated", item.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        if (add)
        {
            items.add(item);
            applyFilter();
        }

        editing = false;
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public void delete()
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        // TODO: disconnect from zone alerts

        zoneDAO.deleteByID(item.getZoneID());

        // add a message
        final String summary = MessageUtil.formatMessageString("zone_deleted", item.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        items.remove(item);
        filteredItems.remove(item);
        item = null;
    }

    public Zone getItem()
    {
        return item;
    }

    public String getPointsString()
    {
        if (item != null)
            return item.getPointsString();
        return null;
    }

    public void setPointsString(String pointsString)
    {
        if (editing && (item != null) && (pointsString != null))
            try
            {
                item.setPointsString(pointsString);
            }
            catch (IllegalArgumentException e)
            {
                final String summary = MessageUtil.formatMessageString("zone_missingPoints", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                throw e;
            }
    }

    public String getAddress()
    {
        if (item != null)
            return item.getAddress();
        return null;
    }

    public void setAddress(String address)
    {
        // the address isn't settable from the hidden field
    }

    /**
     * @return Whether the current edit operation is an item add.
     */
    public boolean isAdd()
    {
        return (item != null) && (item.getZoneID() == null);
    }

    public boolean isEditing()
    {
        return editing;
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
        if (item != null)
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            if ((item.getPoints() == null) || (item.getPoints().size() == 0))
            {
                final String summary = MessageUtil.formatMessageString("zone_missing", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
            else if (item.getPoints().size() <= 3)
            {
                final String summary = MessageUtil.formatMessageString("zone_missingPoints", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
        }
        return true;
    }
}
