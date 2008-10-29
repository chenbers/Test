package com.inthinc.pro.backing;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

/**
 * @author David Gileadi
 */
public abstract class BaseAdminBean<T extends Selectable> extends BaseBean
{
    protected static final Logger  logger        = LogManager.getLogger(BaseAdminBean.class);

    protected List<T>              items;
    protected List<T>              filteredItems = new LinkedList<T>();
    protected String               filterValue;
    protected int                  page          = 1;
    protected Map<String, Boolean> columns       = getDefaultColumns();
    private T                      editItem;
    private boolean                batchEdit;
    private Map<String, Boolean>   updateField;

    /**
     * @return the items
     */
    public List<T> getItems()
    {
        return items;
    }

    /**
     * @return the filteredItems
     */
    public List<T> getFilteredItems()
    {
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
        if ((filterValue != null) && (filterValue.length() > 0))
        {
            filterValue = filterValue.toLowerCase();
            changed = !filterValue.equals(this.filterValue);
        }
        else
        {
            filterValue = null;
            changed = this.filterValue != null;
        }

        if (changed)
        {
            setPage(1);

            filteredItems.clear();
            this.filterValue = filterValue;
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
     * Called when the user chooses to add an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String add()
    {
        batchEdit = false;
        editItem = createAddItem();
        return getEditRedirect();
    }

    /**
     * Called when the user chooses to edit an item.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String edit()
    {
        batchEdit = false;

        // select only the item to be edited
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final int editIndex = Integer.parseInt(parameterMap.get("editIndex"));
        for (final T item : items)
            item.setSelected(false);
        filteredItems.get(editIndex).setSelected(true);

        return getEditRedirect();
    }

    /**
     * Called when the user chooses to add an item or edit one or more selected items. Default processing is first performed by {@link #internalEdit()}.
     * 
     * @return The result of calling {@link #getEditRedirect()}.
     */
    public String batchEdit()
    {
        batchEdit = true;
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
        if (!batchEdit)
            editItem.setSelected(false);
        editItem = null;
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
                deepCopy(editItem, item, ignoreFields);
        }
        else
        {
            editItem.setSelected(false);
            doSave(selected);
        }
        editItem = null;
        return getFinishedRedirect();
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     * 
     * @return The result of calling {@link #getFinishedRedirect()}.
     */
    public String delete()
    {
        final List<T> selected = getSelectedItems();
        doDelete(selected);
        items.removeAll(selected);
        return getFinishedRedirect();
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
            initUpdateField(items.get(0), "");
        }
        return updateField;
    }

    /**
     * Initializes the updateField map with property names from the given item.
     * 
     * @param item
     *            The item to populate with.
     * @param prefix
     *            A prefix to prepend to property names, used for calling recursively.
     */
    protected void initUpdateField(Object item, String prefix)
    {
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
        {
            final Class<?> clazz = descriptor.getPropertyType();
            if (clazz != null)
            {
                try
                {
                    if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum())
                        updateField.put(prefix + descriptor.getName(), false);
                    else
                    {
                        // get or create the container
                        final Method readMethod = descriptor.getReadMethod();
                        if (readMethod != null)
                        {
                            final Object targetProperty = readMethod.invoke(item, new Object[0]);
                            if (targetProperty != null)
                                initUpdateField(targetProperty, prefix + descriptor.getName() + '.');
                        }
                    }
                }
                catch (Throwable ex)
                {
                    throw new FatalBeanException("Could not init update field properties", ex);
                }
            }
        }
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
                deepCopy(selection, editItem, null);

                // null out properties that are not common
                for (T item : items)
                    compareAndNull(editItem, item);
            }
        }
        return editItem;
    }

    /**
     * Deep copies the source object into the target object.
     * 
     * @param source
     *            The source bean.
     * @param target
     *            The target bean.
     * @param ignoreProperties
     *            List of property names to ignore. Child property names are separated by dots, e.g. "property.child".
     */
    protected void deepCopy(Object source, Object target, List<String> ignoreProperties)
    {
        // deep-copy children
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(source.getClass()))
        {
            if ((ignoreProperties != null) && ignoreProperties.contains(descriptor.getName()))
                continue;

            final Class<?> clazz = descriptor.getPropertyType();
            if (clazz != null)
            {
                final Method readMethod = descriptor.getReadMethod();
                final PropertyDescriptor targetDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), descriptor.getName());
                final Method writeMethod = targetDescriptor.getWriteMethod();
                if ((readMethod != null) && (writeMethod != null))
                {
                    try
                    {
                        // create a destination
                        final Object sourceProperty = readMethod.invoke(source, new Object[0]);
                        if (sourceProperty != null)
                        {
                            // simple or deep copy
                            if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum())
                                writeMethod.invoke(target, new Object[] { sourceProperty });
                            else
                            {
                                // get or create the container
                                Object targetProperty = null;
                                final Method targetReader = targetDescriptor.getReadMethod();
                                if (targetReader != null)
                                    targetProperty = targetReader.invoke(target, new Object[0]);
                                if (targetProperty == null)
                                {
                                    targetProperty = BeanUtils.instantiateClass(sourceProperty.getClass());
                                    writeMethod.invoke(target, targetProperty);
                                }

                                // filter ignore properties by this property's prefix
                                final String prefix = descriptor.getName() + '.';
                                final LinkedList<String> childIgnore = new LinkedList<String>();
                                if (ignoreProperties != null)
                                    for (final String key : ignoreProperties)
                                        if (key.startsWith(prefix))
                                            childIgnore.add(key.substring(prefix.length()));

                                // recurse
                                deepCopy(sourceProperty, targetProperty, childIgnore);
                            }
                        }
                    }
                    catch (Throwable ex)
                    {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * Compares all properties of the editItem to the given item, nulling the property of the item if they aren't equal. Ignores null properties in the item.
     * 
     * @param item
     *            The item to compare and null.
     * @param compareTo
     *            The item to compare it to.
     */
    protected void compareAndNull(Object item, Object compareTo)
    {
        try
        {
            for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
            {
                final Method read1 = descriptor.getReadMethod();
                final Method read2 = BeanUtils.getPropertyDescriptor(compareTo.getClass(), descriptor.getName()).getReadMethod();
                if ((read1 != null) && (read2 != null))
                {
                    final Object o1 = read1.invoke(item, new Object[0]);
                    if (o1 != null)
                    {
                        final Object o2 = read2.invoke(compareTo, new Object[0]);
                        if (o2 != null)
                        {
                            // recursive or simple compare
                            final Class<?> clazz = descriptor.getPropertyType();
                            if (clazz != null)
                            {
                                if (!BeanUtils.isSimpleProperty(clazz) && !clazz.isEnum())
                                    compareAndNull(o1, o2);
                                else if (!o1.equals(o2))
                                {
                                    final Method write = descriptor.getWriteMethod();
                                    if (write != null)
                                    {
                                        if (clazz.isPrimitive())
                                            write.invoke(item, new Object[] { BeanUtils.instantiateClass(o1.getClass().getConstructor(String.class), new Object[] { "0" }) });
                                        else
                                            write.invoke(item, new Object[] { null });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable ex)
        {
            throw new FatalBeanException("Could not compare and null properties", ex);
        }
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
     * @return A redirect to navigate to for editing items.
     */
    protected abstract String getEditRedirect();

    /**
     * @return A redirect to navigate to when a user has finished or canceled editing.
     */
    protected abstract String getFinishedRedirect();
}
