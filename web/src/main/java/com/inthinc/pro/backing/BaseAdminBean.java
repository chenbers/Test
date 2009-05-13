package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;

/**
 * @author David Gileadi
 */
public abstract class BaseAdminBean<T extends EditItem> extends BaseBean implements TablePrefOptions<T>
{
    protected static final Logger logger        = LogManager.getLogger(BaseAdminBean.class);

    private List<Group>           allGroups;
    protected GroupDAO            groupDAO;
    protected List<T>             items;
    protected List<T>             filteredItems = new LinkedList<T>();
    protected String              filterValue;
    protected int                 page          = 1;
    private boolean               displayed;
    protected T                   item;
    private Integer               itemID;
    private boolean               batchEdit;
    private boolean               selectAll;
    private Map<String, Boolean>  updateField;
    protected TablePreferenceDAO  tablePreferenceDAO;
    protected TablePreference     tablePreference;
    private TablePref<T>          tablePref;

    public void initBean()
    {
        tablePref = new TablePref<T>(this);
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public TablePref<T> getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref<T> tablePref)
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
     * @return A list of all available groups.
     */
    protected List<Group> getAllGroups()
    {
        if (allGroups == null)
        {
            final GroupHierarchy hierarchy = getGroupHierarchy();
            if (hierarchy.getTopGroup().getType() == GroupType.FLEET)
                allGroups = hierarchy.getGroupList();
            else
                allGroups = groupDAO.getGroupsByAcctID(getAccountID());
        }
        return allGroups;
    }

    /**
     * @return The top-level group.
     */
    protected Group getTopGroup()
    {
        for (final Group group : getAllGroups())
            if (group.getType() == GroupType.FLEET)
                return group;
        return null;
    }

    /**
     * @return the items
     */
    public List<T> getItems()
    {
        if ((items == null) || (items.size() == 0))
        {
            items = loadItems();
            if (items == null)
                items = new LinkedList<T>();
            applyFilter(getPage());
        }
        return items;
    }

    /**
     * @return the filteredItems
     */
    public List<T> getFilteredItems()
    {
        getItems();
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
    
    public void refreshItems()
    {
        items = null;
        item = null;
        //page = 1;
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
            applyFilter(1);
        }
    }

    /**
     * Applies the filter.
     */
    protected void applyFilter(int page)
    {
        setPage(page);

        filteredItems.clear();
        filteredItems.addAll(items);
        tablePref.filter(filteredItems, filterValue, matchAllFilterWords());
    }

    /**
     * @return Whether matching all filter words is required to match an item. The default is <code>true</code>.
     */
    protected boolean matchAllFilterWords()
    {
        return true;
    }

    /**
     * Returns the value of the property of the given item described by the given column name. The default implementation calls TablePref.fieldValue.
     * 
     * @param item
     *            The item to get the value from.
     * @param column
     *            The name of the column to get the value of.
     * @return The value or <code>null</code> if unavailable.
     */
    public String fieldValue(T item, String column)
    {
        return TablePref.fieldValue(item, column);
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
        setItemID(item.getId());
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
        allGroups = null;

        // deselect all previous items
        for (final T item : getSelectedItems())
            item.setSelected(false);

        return getEditRedirect();
    }
    
    public void view(){
        if(itemID != null)
            selectItem(itemID);
    }
    
    public void setItemID(Integer itemID)
    {
        this.itemID = itemID;
    }

    public Integer getItemID()
    {
        return itemID;
    }

    /**
     * Called when the user chooses to edit an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String edit()
    {
        displayed = !selectItem("editID");
        allGroups = null;
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
            selectItem(editID);
            return true;
        }
        return false;
    }
    
    /**
     * Populates the edit item based on the value in the given param name.
     * 
     * @param paramName
     *            The name of a param containing the edit item's ID.
     */
    protected void selectItem(Integer id)
    {
            for (final T t : getItems())
                t.setSelected(t.getId().equals(id));

            item = null;
            getItem();
            item.setSelected(false);
    }
   
    
    


    /**
     * Called when the user chooses to add an item or edit one or more selected items. Default processing is first performed by {@link #internalEdit()}.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String batchEdit()
    {
        displayed = false;
        allGroups = null;
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
        // revert the edit item
        if (!isBatchEdit() && !isAdd())
        {
            final int index = getItems().indexOf(getItem());
            if (index != -1)
                items.set(index, revertItem(item));
            applyFilter(getPage());
        }

        // deselect all edit items
        for (final T item : getSelectedItems())
            item.setSelected(false);

        if (displayed)
        {
            setItemID(item.getId());
            return getDisplayRedirect();
        }
        else
        {
            item = null;
            return getFinishedRedirect();
        }
    }

    /**
     * Called when the user clicks to save changes when adding or editing.
     * 
     * @return The result of calling {@link #getFinishedRedirect()}.
     */
    public String save()
    {
        final List<T> selected = getSelectedItems();
        if ((selected.size() == 0) && isAdd())
            selected.add(item);

        if (batchEdit)
        {
            // get the fields to update
            final LinkedList<String> ignoreFields = new LinkedList<String>();
            for (final String key : updateField.keySet())
                if (!updateField.get(key))
                    ignoreFields.add(key);
            
            //we need to validate the item before we copy the properties. 
            if(!validateBatchEdit(item))
            {
                return null;
            }

            // copy properties
            for (final T t : selected)
                BeanUtil.deepCopy(item, t, ignoreFields);
        }

        // validate
        if (!validate(selected))
            return null;

        final boolean add = isAdd();
        try
        {
            doSave(selected, add);
            applyFilter(this.getPage());
        }
        catch (HessianException e)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Hessian error while saving", e);
            return null;
        }

        if (add)
        {
            items.add(item);
            applyFilter(1);
        }

        // deselect all edited items
        for (final T item : getSelectedItems())
            item.setSelected(false);

        // redirect
        if (isBatchEdit())
        {
            item = null;
            //return getFinishedRedirect()
            //Reload the item list (We'll see how this works or if it's too much of a performance hit). 
            //TODO Mike - verify performance.
            return getResetListRedirect();
        }
        else{
            setItemID(item.getId());
            return getDisplayRedirect();
        }
            
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

        try
        {
            doDelete(selected);
        }
        catch (HessianException e)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Hessian error while deleting", e);
            return null;
        }

        items.removeAll(selected);
        filteredItems.removeAll(selected);
        selected.clear();
        item = null;

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
        if ((updateField == null) || (updateField.size() == 0))
        {
            if (updateField == null)
                updateField = new HashMap<String, Boolean>();
            if (getItems().size() > 0)
                for (final String name : BeanUtil.getPropertyNames(getItem()))
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
    public List<T> getSelectedItems()
    {
        final LinkedList<T> selected = new LinkedList<T>();
        for (final T t : filteredItems)
            if (t.isSelected())
                selected.add(t);

        if ((selected.size() == 0) && (item != null) && !isAdd())
            selected.add(item);

        return selected;
    }

    /**
     * @return Whether all filtered items are selected.
     */
    public boolean isSelectAll()
    {
        selectAll = getFilteredItems().size() > 0;
        for (final T t : getFilteredItems())
            if (!t.isSelected())
                selectAll = false;
        return selectAll;
    }

    /**
     * Selectes or deselects all filtered items.
     * 
     * @param select
     *            Whether to select or deselect the items.
     */
    public void setSelectAll(boolean select)
    {
        this.selectAll = select;
    }

    public void doSelectAll()
    {
        for (final T t : getFilteredItems())
            t.setSelected(selectAll);
    }

    /**
     * @return A new item for the user to populate on an "add" page.
     */
    protected abstract T createAddItem();

    /**
     * Perform custom on an individual save item. validation errors can be display using the following code. 
     * 
     * <pre>
     * final String summary = MessageUtil.getMessageString(&quot;error_message&quot;);
     * final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
     * context.addMessage(&quot;my-form:component-id&quot;, message);
     * </pre>
     * 
     * @param saveItem
     *            The item to save.
     * @return Whether the items passed validation.
     */
    protected abstract boolean validateSaveItem(T saveItem);
    
    
    /**
     * Perform custom validation on the list of items to save.
     * 
     * 
     * @param saveItems
     *            The items to save.
     * @return Whether the items passed validation.
     */
    protected boolean validate(List<T> saveItems)
    {
        boolean valid = true;
        for (final T saveItem : saveItems)
        {
            valid = validateSaveItem(saveItem);
            if(!valid)
                break;
        }
        return valid;
    }
   
    
    /**
     * Performs custom validation on the batch edit item. If there is invalid data, we don't want the data to make it into the orginal selected list
     * The changes won't make it back to the db, but if they are propogated to the selected list, 
     * they will show up in the data table or where ever else they are needed.
     * 
     * 
     * @param batchEditItem
     * @return
     */
    protected boolean validateBatchEdit(T batchEditItem)
    {
        return validateSaveItem(batchEditItem);
    }

    /**
     * Revert the given item to pre-edit state.
     * 
     * @param editItem
     *            The item to revert.
     * @return The reverted item; doesn't have to be the same instance as the given editItem.
     */
    protected abstract T revertItem(T editItem);

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
    
    /**
     * @return before redericting clears the list so that the next time the list is loaded, it's a new one.
     */
    public String getResetListRedirect()
    {
        refreshItems();
        return getFinishedRedirect();
    }
    
    public void resetList()
    {
        refreshItems();
    }

    

}
