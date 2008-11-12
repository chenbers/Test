package com.inthinc.pro.backing;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

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
     */
    protected abstract void populate(T editItem);

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
        assertTrue(adminBean.getFilteredItems().size() < adminBean.getItems().size());
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
        assertEquals(adminBean.getDefaultColumns().keySet(), adminBean.getColumns().keySet());

        // add a column
        for (final String column : adminBean.getAvailableColumns())
            if (!adminBean.getColumns().containsKey(column) || !adminBean.getColumns().get(column))
            {
                adminBean.getColumns().put(column, true);
                break;
            }
        assertFalse(adminBean.getDefaultColumns().keySet().equals(adminBean.getColumns().keySet()));

        // untested: saveColumns

        // selected items
        selectItems(adminBean, 3);
        assertNotNull(adminBean.getSelectedItems());
        assertTrue(adminBean.getSelectedItems().size() > 0);

        // paging clears selection
        adminBean.setPage(2);
        assertNotNull(adminBean.getSelectedItems());
        // we may have one selected item if we have an edit item
        assertTrue(adminBean.getSelectedItems().size() <= 1);
    }

    protected void selectItems(BaseAdminBean<T> adminBean, int maxItems)
    {
        int selected = 0;
        for (int i = 0; (selected < maxItems) && (i < adminBean.getItemCount()); i += 2)
        {
            adminBean.getFilteredItems().get(i).setSelected(true);
            selected++;
        }
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
        assertNotNull(adminBean.getEditItem());
        assertEquals(adminBean.getEditItem().getId(), adminBean.getItems().get(0).getId());

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
        assertEquals(adminBean.add(), adminBean.getEditRedirect());
        assertTrue(adminBean.isAdd());
        assertFalse(adminBean.isBatchEdit());

        // edit item (used for adding)
        assertNotNull(adminBean.getEditItem());
        assertNull(adminBean.getEditItem().getId());

        // cancel it
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());

        // start another add
        adminBean.add();
        assertTrue(adminBean.isAdd());

        // populate
        populate(adminBean.getEditItem());
        assertNull(adminBean.getEditItem().getId());

        // save
        int count = adminBean.getItemCount();
        assertEquals(adminBean.save(), adminBean.getDisplayRedirect());
        assertNotNull(adminBean.getEditItem().getId());
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
        assertNotNull(adminBean.getEditItem());
        assertEquals(adminBean.getEditItem().getId(), adminBean.getItems().get(adminBean.getItemCount() - 1).getId());

        // cancel edit
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());

        // start another edit
        adminBean.edit();

        // populate
        populate(adminBean.getEditItem());

        // save
        int count = adminBean.getItemCount();
        assertEquals(adminBean.save(), adminBean.getDisplayRedirect());
        assertEquals(adminBean.getItemCount(), count);
    }

    @Test
    public void batchEdit() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();
        adminBean.getItems();

        // select items to edit
        selectItems(adminBean, 3);
        assertEquals(adminBean.getSelectedItems().size(), 3);

        // edit
        assertEquals(adminBean.batchEdit(), adminBean.getEditRedirect());
        assertFalse(adminBean.isAdd());
        assertTrue(adminBean.isBatchEdit());

        // edit item
        assertNotNull(adminBean.getEditItem());
        assertNull(adminBean.getEditItem().getId());

        // cancel edit
        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());

        // start another edit
        adminBean.batchEdit();

        // populate
        populate(adminBean.getEditItem());
        for (final String field : getBatchUpdateFields())
            adminBean.getUpdateField().put(field, true);

        // save
        int count = adminBean.getItemCount();
        assertEquals(adminBean.save(), adminBean.getFinishedRedirect());
        assertEquals(adminBean.getItemCount(), count);

        // test batch update fields
        for (final T item : adminBean.getSelectedItems())
            for (final String field : getBatchUpdateFields())
            {
                final PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), field);
                assertEquals(descriptor.getReadMethod().invoke(item, new Object[0]), descriptor.getReadMethod().invoke(adminBean.getEditItem(), new Object[0]));
            }
    }

    @Test
    public void delete()
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<T> adminBean = getAdminBean();

        // select an item
        adminBean.getItems().get(0).setSelected(true);

        // delete, test no redirect
        int count = adminBean.getItemCount();
        assertEquals(adminBean.delete(), null);
        assertEquals(adminBean.getItemCount(), count - 1);

        // display an item
        final Map<String, String> params = new HashMap<String, String>();
        params.put("displayID", adminBean.getItems().get(0).getId().toString());
        externalContext.setRequestParameterMap(params);
        adminBean.display();

        // delete, test redirect to the finished page
        count = adminBean.getItemCount();
        assertEquals(adminBean.delete(), adminBean.getFinishedRedirect());
        assertEquals(adminBean.getItemCount(), count - 1);
    }
}
