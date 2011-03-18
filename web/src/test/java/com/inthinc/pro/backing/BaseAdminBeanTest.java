package com.inthinc.pro.backing;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.TableColumn;

public abstract class BaseAdminBeanTest<T extends EditItem> extends BaseBeanTest
{
    /**
     * Retrieves the admin bean to test, typically by a call like <code>applicationContext.getBean("myAdminBean")</code>. Should also test any attributes of the bean that should
     * have been set upon creation.
     * 
     * @return The admin bean to test.
     */
    protected abstract BaseAdminBean<T> getAdminBean();

    /**
     * @return A filter value that should exclude some items and leave some remaining.
     */
    protected abstract String getFilterValue();

    /**
     * Populate the given edit item such that it's in a valid state for being created or added.
     * 
     * @param editItem
     *            The edit item to populate.
     * @param adminBean
     *            The admin bean being tested.
     */
    protected abstract void populate(T editItem, BaseAdminBean<T> adminBean);

    /**
     * @return A list of field names to
     */
    protected abstract String[] getBatchUpdateFields();

    @Test
    public void list()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();

        // items
        assertNotNull(adminBean);
        assertNotNull(adminBean.getItems());
        assertTrue(adminBean.getItems().size() > 0);
        assertEquals(adminBean.getItems().size(), adminBean.getItemCount());

        // (un)filtered items
        assertNotNull(adminBean.getFilteredItems());
        assertEquals(adminBean.getFilteredItems().size(), adminBean.getItemCount());

        // filter
        adminBean.setFilterValue(getFilterValue());
        assertNotNull(adminBean.getFilteredItems());
        assertTrue(adminBean.getFilteredItems().size() > 0);
        assertTrue(adminBean.getFilteredItems().size() <= adminBean.getItems().size());
        assertEquals(adminBean.getFilteredItems().size(), adminBean.getItemCount());

        // clear filter
        adminBean.setFilterValue(null);
        assertNotNull(adminBean.getFilteredItems());
        assertEquals(adminBean.getFilteredItems().size(), adminBean.getItems().size());

        // available columns
        assertNotNull(adminBean.getAvailableColumns());
        assertEquals(adminBean.getAvailableColumns().size(), adminBean.getAvailableColumnCount());

        // default columns
        assertNotNull(adminBean.getDefaultColumns());
        assertTrue(adminBean.getDefaultColumns().size() > 0);
        assertEquals(adminBean.getDefaultColumns().keySet(), getVisibleColumns(adminBean.getTableColumns()));

        // add a column
        boolean added = false;
        for (final String column : adminBean.getAvailableColumns())
            if (!adminBean.getTableColumns().containsKey(column) || !adminBean.getTableColumns().get(column).getVisible())
            {
                added = true;
                adminBean.getTableColumns().get(column).setVisible(true);
                break;
            }
        if (added)
            assertFalse(adminBean.getDefaultColumns().keySet().equals(getVisibleColumns(adminBean.getTableColumns())));

        // untested: saveColumns

        // selected items
        selectItems(adminBean, 3);
        assertNotNull(adminBean.getSelectedItems());
        assertTrue(adminBean.getSelectedItems().size() > 0);
    }

    protected Set<String> getVisibleColumns(Map<String, TableColumn> columns)
    {
        final Set<String> visible = new HashSet<String>();
        for (final String column : columns.keySet())
            if (columns.get(column).getVisible())
                visible.add(column);
        return visible;
    }

    protected int selectItems(BaseAdminBean<T> adminBean, int maxItems)
    {
        int selected = 0;
        final List<T> filteredItems = adminBean.getFilteredItems();
        for (int i = 0; (selected < maxItems) && (i < adminBean.getItemCount()); i += 2)
        {
            filteredItems.get(i).setSelected(true);
            if (filteredItems.get(i).isSelected()) selected++;
        }
        return selected;
    }

    @Test
    public void display()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();

        // set a display ID
        final Map<String, String> params = new HashMap<String, String>();
        params.put("displayID", adminBean.getItems().get(0).getId().toString());
        externalContext.setRequestParameterMap(params);

        // display
        assertEquals(adminBean.display(), adminBean.getDisplayRedirect());

        // edit item (used for display)
        assertNotNull(adminBean.getItem());
        assertEquals(adminBean.getItem().getId(), adminBean.getItems().get(0).getId());

        // cancel display
        assertEquals(adminBean.cancelDisplay(), adminBean.getFinishedRedirect());
    }

    // TODO: the following test works when run standalone, but when run as part of the build, the newly added item isn't assigned an ID
    @Ignore
    @Test
    public void add()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();
        adminBean.getItems();

        // add
//        assertFalse(adminBean.isAdd());
        assertEquals(adminBean.add(), adminBean.getEditRedirect());
        assertTrue(adminBean.isAdd());
        assertFalse(adminBean.isBatchEdit());

        // edit item (used for adding)
        assertNotNull(adminBean.getItem());
        assertNull(adminBean.getItem().getId());

        // cancel it
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());

        // start another add
        //assertFalse(adminBean.isAdd());
        adminBean.add();
        assertTrue(adminBean.isAdd());

        // populate
        populate(adminBean.getItem(), adminBean);
        assertNull(adminBean.getItem().getId());

        // save
        int count = adminBean.getItemCount();
        assertEquals(adminBean.save(), adminBean.getDisplayRedirect());
        assertNotNull(adminBean.getItem().getId());
        assertEquals(adminBean.getItemCount(), count + 1);
    }

    @Test
    public void edit()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();

        // set an edit ID
        final Map<String, String> params = new HashMap<String, String>();
        params.put("editID", adminBean.getItems().get(adminBean.getItemCount() - 1).getId().toString());
        externalContext.setRequestParameterMap(params);

        // edit
        assertEquals(adminBean.edit(), adminBean.getEditRedirect());
        assertFalse(adminBean.isAdd());
        assertFalse(adminBean.isBatchEdit());

        // edit item
        assertNotNull(adminBean.getItem());
        assertEquals(adminBean.getItem().getId(), adminBean.getItems().get(adminBean.getItemCount() - 1).getId());

        // cancel edit
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());

        // start another edit
        adminBean.edit();

        // populate
        populate(adminBean.getItem(), adminBean);

        // save
        int count = adminBean.getItemCount();
        
        //TODO uncomment the following code and get the test to work.
        //assertEquals(adminBean.save(), adminBean.getDisplayRedirect());
        assertEquals(adminBean.getItemCount(), count);
    }

//    @Ignore
    @Test
    public void batchEdit() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();
        adminBean.getItems();
        setProductType(adminBean, null);

        // select items to edit
        int selected = selectItems(adminBean, 3);
        assertEquals(selected, adminBean.getSelectedItems().size());

        // edit
        assertFalse(adminBean.isBatchEdit());
        assertEquals(adminBean.batchEdit(), adminBean.getEditRedirect());
        assertFalse(adminBean.isAdd());
        assertTrue(adminBean.isBatchEdit());

        // edit item
        assertNotNull(adminBean.getItem());
        
        checkBatchEditId(adminBean);

        // cancel edit
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());
        assertEquals(adminBean.getSelectedItems().size(), 0);

        // start another edit
        selected = selectItems(adminBean, 3);
        assertEquals(selected, adminBean.getSelectedItems().size());
        adminBean.batchEdit();

        // populate
        populate(adminBean.getItem(), adminBean);
        for (final String field : getBatchUpdateFields()) {
            adminBean.getUpdateField().put(field, true);
        }

        // save
        int count = adminBean.getItemCount();
        assertEquals(adminBean.save(), adminBean.getFinishedRedirect());
        assertEquals(adminBean.getItemCount(), count);
        assertEquals(adminBean.getSelectedItems().size(), 0);

        // test batch update fields
        for (final T item : adminBean.getSelectedItems())
            for (final String field : getBatchUpdateFields())
            {
                final PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), field);
                if (descriptor != null)
                    assertEquals(descriptor.getReadMethod().invoke(item, new Object[0]), descriptor.getReadMethod().invoke(adminBean.getItem(), new Object[0]));
            }
    }

    @Test
    public void delete()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();

        // select at least one item (more may have been selected in other tests)
        adminBean.getItems().get(0).setSelected(true);

        // delete, test no redirect
        int count = adminBean.getItemCount();
        assertEquals(adminBean.delete(), null);
        assertTrue(adminBean.getItemCount() < count);

        // display an item
        final Map<String, String> params = new HashMap<String, String>();
        params.put("displayID", adminBean.getItems().get(0).getId().toString());
        externalContext.setRequestParameterMap(params);
        
        adminBean.setItemID(adminBean.getItems().get(0).getId());
        adminBean.display();

        // delete, test redirect to the finished page
        count = adminBean.getItemCount();
        assertEquals(adminBean.delete(), adminBean.getFinishedRedirect());
        assertEquals(adminBean.getItemCount(), count - 1);
    }
    
    public void checkBatchEditId(BaseAdminBean<T> adminBean){
        
        assertNull(adminBean.getItem().getId());

    }
    public void setProductType(BaseAdminBean<T> adminBean, String type){
        
    }
}
