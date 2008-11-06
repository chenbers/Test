package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.util.BeanUtil;

/**
 * @author David Gileadi
 */
public abstract class BaseAdminBean<T extends EditItem> extends BaseBean
{
    protected static final Logger  logger        = LogManager.getLogger(BaseAdminBean.class);

    protected List<T>              items;
    protected List<T>              filteredItems = new LinkedList<T>();
    protected String               filterValue;
    protected int                  page          = 1;
    protected Map<String, Boolean> columns;
    private boolean                displayed;
    private T                      editItem;
    private boolean                batchEdit;
    private Map<String, Boolean>   updateField;

    /**
     * @return the items
     */
    public List<T> getItems()
    {
        if (items == null)
        {
            items = loadItems();
            applyFilter();
        }
        return items;
    }

    /**
     * @return the filteredItems
     */
    public List<T> getFilteredItems()
    {
        if (items == null)
        {
            items = loadItems();
            applyFilter();
        }
        return filteredItems;
    }

    /**
     * Load the list of items.
     * 
     * @return The list of items to use.
     */
    protected abstract List<T> loadItems();

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
    protected void applyFilter()
    {
        setPage(1);

        filteredItems.clear();
        if (filterValue != null)
        {
            final boolean matchAllWords = matchAllFilterWords();
            final String[] filterWords = filterValue.split("\\s+");
            for (final T item : items)
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
    protected boolean matchAllFilterWords()
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
    protected abstract boolean matchesFilter(T item, String filterWord);

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
        for (final T item : items)
            item.setSelected(false);
    }

    /**
     * @return The list of available columns for type T.
     */
    public abstract List<String> getAvailableColumns();

    /**
     * @return The number of columns returned by {@link #getAvailableColumns()}.
     */
    public int getAvailableColumnCount()
    {
        return getAvailableColumns().size();
    }

    /**
     * @return A map of column keys and boolean values for whether a column is selected. Only the default-selected columns need be in the map, but their value must be
     *         <code>true</code>.
     */
    public abstract Map<String, Boolean> getDefaultColumns();

    /**
     * @return the columns
     */
    public Map<String, Boolean> getColumns()
    {
        if (columns == null)
            columns = getDefaultColumns();
        return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(Map<String, Boolean> columns)
    {
        this.columns = columns;
    }

    /**
     * Called when the user chooses new visible columns.
     */
    public abstract void saveColumns();

    /**
     * Called when the user chooses to display an item.
     * 
     * @return The result of calling {@link #getDisplayRedirect()}.
     */
    public String display()
    {
        displayed = true;
        selectEditItem("displayID");
        return getDisplayRedirect();
    }

    /**
     * Called when the user chooses to cancel editing.
     * 
     * @return The result of calling {@link #getFinishedRedirect()}.
     */
    public String cancelDisplay()
    {
        displayed = false;
        editItem = null;
        return getFinishedRedirect();
    }

    /**
     * Called when the user chooses to add an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String add()
    {
        displayed = false;
        batchEdit = false;
        editItem = createAddItem();
        editItem.setSelected(false);

        return getEditRedirect();
    }

    /**
     * Called when the user chooses to edit an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String edit()
    {
        displayed = !selectEditItem("editID");
        return getEditRedirect();
    }

    /**
     * Populates the edit item based on the value in the given param name.
     * 
     * @param paramName
     *            The name of a param containing the edit item's ID.
     */
    private boolean selectEditItem(String paramName)
    {
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get(paramName) != null)
        {
            final int editID = Integer.parseInt(parameterMap.get(paramName));
            for (final T item : items)
                item.setSelected(item.getId().equals(editID));

            editItem = null;
            getEditItem();
            editItem.setSelected(false);
            return true;
        }
        return false;
    }

    /**
     * Called when the user chooses to add an item or edit one or more selected items. Default processing is first performed by {@link #internalEdit()}.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String batchEdit()
    {
        displayed = false;
        editItem = null;
        getEditItem();

        // take off if nothing was selected
        if (isAdd())
            return getFinishedRedirect();

        // select no fields for update
        for (final String key : getUpdateField().keySet())
            updateField.put(key, false);

        return getEditRedirect();
    }

    /**
     * Called when the user chooses to cancel editing.
     * 
     * @return The result of calling {@link #getFinishedRedirect()}.
     */
    public String cancelEdit()
    {
        if (displayed)
            return getDisplayRedirect();
        else
            return getFinishedRedirect();
    }

    /**
     * Called when the user clicks to save changes when adding or editing.
     * 
     * @return The result of calling {@link #getFinishedRedirect()}.
     */
    public String save()
    {
        final List<T> selected = getSelectedItems();
        if (batchEdit)
        {
            // get the fields to update
            final LinkedList<String> ignoreFields = new LinkedList<String>();
            for (final String key : updateField.keySet())
                if (!updateField.get(key))
                    ignoreFields.add(key);

            // copy properties
            for (final T item : selected)
                BeanUtil.deepCopy(editItem, item, ignoreFields);
        }
        else
        {
            final boolean add = isAdd();
            doSave(selected);
            if (add)
            {
                items.add(editItem);
                applyFilter();
            }
        }

        return getDisplayRedirect();
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public String delete()
    {
        final List<T> selected = getSelectedItems();
        doDelete(selected);
        items.removeAll(selected);
        filteredItems.removeAll(selected);

        if (displayed)
            return getFinishedRedirect();
        else
            return null;
    }

    /**
     * @return Whether the current edit operation is an item add.
     */
    public boolean isAdd()
    {
        return !isBatchEdit() && (editItem != null) && (editItem.getId() == null);
    }

    /**
     * @return Whether the current edit operation is for multiple items.
     */
    public boolean isBatchEdit()
    {
        return batchEdit;
    }

    /**
     * @return A map for storing whether a given field should be updated when editing multiple items.
     */
    public Map<String, Boolean> getUpdateField()
    {
        if ((updateField == null) && (items.size() > 0))
        {
            updateField = new HashMap<String, Boolean>();
            for (final String name : BeanUtil.getPropertyNames(items.get(0)))
                updateField.put(name, false);
        }
        return updateField;
    }

    /**
     * @return An item that the user can edit. Either returns a new item to be added, a single item to be edited, or an item that represents multiple items being batch edited.
     */
    @SuppressWarnings("unchecked")
    public T getEditItem()
    {
        if (editItem == null)
        {
            batchEdit = false;

            int selected = 0;
            T selection = null;
            for (T item : items)
                if (item.isSelected())
                {
                    selection = item;
                    selected++;
                    if (selected > 1)
                        break;
                }
            if (selected == 0)
                editItem = createAddItem();
            else if (selected == 1)
                editItem = selection;
            else
            {
                batchEdit = true;
                editItem = createAddItem();
                BeanUtil.deepCopy(selection, editItem);

                // null out properties that are not common
                for (T item : getSelectedItems())
                    BeanUtil.compareAndInit(editItem, item);
            }
        }
        return editItem;
    }

    /**
     * @return The list of selected items.
     */
    protected List<T> getSelectedItems()
    {
        final LinkedList<T> selected = new LinkedList<T>();
        for (final T item : filteredItems)
            if (item.isSelected())
                selected.add(item);
        return selected;
    }

    /**
     * @return A new item for the user to populate on an "add" page.
     */
    protected abstract T createAddItem();

    /**
     * Save the given list of items.
     * 
     * @param saveItems
     *            The items to save.
     */
    protected abstract void doSave(List<T> saveItems);

    /**
     * Delete the given list of items.
     * 
     * @param deleteItems
     *            The items to delete.
     */
    protected abstract void doDelete(List<T> deleteItems);

    /**
     * @return A redirect to navigate to for displaying an item.
     */
    protected abstract String getDisplayRedirect();

    /**
     * @return A redirect to navigate to for editing items.
     */
    protected abstract String getEditRedirect();

    /**
     * @return A redirect to navigate to when a user has finished or canceled editing.
     */
    protected abstract String getFinishedRedirect();
}
