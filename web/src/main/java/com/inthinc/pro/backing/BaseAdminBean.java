package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;

/**
 * @author David Gileadi
 */
public abstract class BaseAdminBean<T extends EditItem> extends BaseBean implements TablePrefOptions
{
    protected static final Logger logger        = LogManager.getLogger(BaseAdminBean.class);

    protected List<T>             items;
    protected List<T>             filteredItems = new LinkedList<T>();
    protected String              filterValue;
    protected int                 page          = 1;
    private boolean               displayed;
    private T                     item;
    private boolean               batchEdit;
    private Map<String, Boolean>  updateField;
    protected TablePreferenceDAO  tablePreferenceDAO;
    protected TablePreference     tablePreference;

    private TablePref             tablePref;

    public void initBean()
    {
        tablePref = new TablePref(this);
    }

    public TablePref getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref tablePref)
    {
        this.tablePref = tablePref;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

    @Override
    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }

    public void getTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

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
            for (final T t : items)
            {
                boolean matched = false;
                for (final String word : filterWords)
                {
                    matched = matchesFilter(t, word);

                    // we can break if we didn't match and we're required to match all words,
                    // or if we did match and we're only required to match one word
                    if (matched ^ matchAllWords)
                        break;
                }
                if (matched)
                    filteredItems.add(t);
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
     * Determine whether the given item matches the filter word. The default implementation gets the value of each displayed column as a property from the item, converts the value
     * to a string, converts it to lowercase, splits it into words and matches the filter word against each word. Override to do more efficient or custom testing.
     * 
     * @param item
     *            The item to filter in or out of the results.
     * @param filterWord
     *            The lowercase filter word. If there are multiple words in the filter string this method will be called once for each word.
     * @return Whether the item matches the filter string.
     */
    protected boolean matchesFilter(T item, String filterWord)
    {
        for (final String column : getTableColumns().keySet())
            if (getTableColumns().get(column).getVisible())
                try
                {
                    final String[] words = String.valueOf(org.apache.commons.beanutils.BeanUtils.getProperty(item, column.replace('_', '.'))).toLowerCase().split("\\W+");
                    for (final String word : words)
                        if (word.startsWith(filterWord))
                            return true;
                }
                catch (Exception e)
                {
                    logger.error("Error filtering on column " + column, e);
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
        for (final T t : items)
            t.setSelected(false);
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
     * Called when the user chooses to display an item.
     * 
     * @return The result of calling {@link #getDisplayRedirect()}.
     */
    public String display()
    {
        displayed = true;
        selectItem("displayID");
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
        item = null;
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
        item = createAddItem();
        item.setSelected(false);

        return getEditRedirect();
    }

    /**
     * Called when the user chooses to edit an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String edit()
    {
        displayed = !selectItem("editID");
        return getEditRedirect();
    }

    /**
     * Populates the edit item based on the value in the given param name.
     * 
     * @param paramName
     *            The name of a param containing the edit item's ID.
     */
    protected boolean selectItem(String paramName)
    {
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get(paramName) != null)
        {
            final int editID = Integer.parseInt(parameterMap.get(paramName));
            for (final T t : items)
                t.setSelected(t.getId().equals(editID));

            item = null;
            getItem();
            item.setSelected(false);
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
        item = null;
        getItem();

        // take off if nothing was selected
        if (isAdd())
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            final String summary = MessageUtil.getMessageString("adminTable_noneSelected");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            context.addMessage(null, message);

            item = null;
            return getFinishedRedirect();
        }

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
        item = null;

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
            for (final T t : selected)
                BeanUtil.deepCopy(item, t, ignoreFields);
        }

        // validate
        if (!validate(selected))
            return null;

        final boolean add = isAdd();
        doSave(selected, add);

        if (add)
        {
            items.add(item);
            applyFilter();
        }

        if (isBatchEdit())
            return getFinishedRedirect();
        else
            return getDisplayRedirect();
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public String delete()
    {
        final List<T> selected = getSelectedItems();
        if (selected.size() == 0)
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            final String summary = MessageUtil.getMessageString("adminTable_noneSelected");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            context.addMessage(null, message);
            return null;
        }

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
        return !isBatchEdit() && (item != null) && (item.getId() == null);
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
    public T getItem()
    {
        if (item == null)
        {
            batchEdit = false;

            int selected = 0;
            T selection = null;
            for (T t : getItems())
                if (t.isSelected())
                {
                    selection = t;
                    selected++;
                    if (selected > 1)
                        break;
                }
            if (selected == 0)
                item = createAddItem();
            else if (selected == 1)
                item = selection;
            else
            {
                batchEdit = true;
                item = createAddItem();
                BeanUtil.deepCopy(selection, item);

                // null out properties that are not common
                for (T t : getSelectedItems())
                    BeanUtil.compareAndInit(item, t);
            }
        }
        return item;
    }

    /**
     * @return The list of selected items.
     */
    protected List<T> getSelectedItems()
    {
        final LinkedList<T> selected = new LinkedList<T>();
        for (final T t : filteredItems)
            if (t.isSelected())
                selected.add(t);

        if ((selected.size() == 0) && (item != null))
            selected.add(item);

        return selected;
    }

    /**
     * @return A new item for the user to populate on an "add" page.
     */
    protected abstract T createAddItem();

    /**
     * Perform custom validation on the list of items to save. If invalid, messages may be displayed via code similar to:
     * 
     * <pre>
     * final String summary = MessageUtil.getMessageString(&quot;error_message&quot;);
     * final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
     * context.addMessage(&quot;my-form:component-id&quot;, message);
     * </pre>
     * 
     * @param saveItems
     *            The items to save.
     * @return Whether the items passed validation.
     */
    protected boolean validate(List<T> saveItems)
    {
        return true;
    }

    /**
     * Save the given list of items.
     * 
     * @param saveItems
     *            The items to save.
     * @param create
     *            Whether the items are to be created (<code>true</code>) or updated (<code>false</code>).
     */
    protected abstract void doSave(List<T> saveItems, boolean create);

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
