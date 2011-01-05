package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.model.TablePreference;

public class TablePref<T>
{
    private static final Logger logger        = LogManager.getLogger(TablePref.class);
    
    private TablePrefOptions<T> tablePrefOptions;
    
    private Map<String, TableColumn>    tableColumns;
    private TablePreference tablePreference;
    
    public TablePref(TablePrefOptions<T> tablePrefOptions)
    {
        this.tablePrefOptions = tablePrefOptions;
    }
    public Map<String, TableColumn> getTableColumns()
    {
        if (tableColumns == null)
        {
            List<Boolean> visibleList = getTablePreference().getVisible();
            tableColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : tablePrefOptions.getAvailableColumns())
            {
                Boolean visible = false;
                if (cnt < visibleList.size())
                    visible = visibleList.get(cnt++);
                TableColumn tableColumn = new TableColumn(visible, tablePrefOptions.getColumnLabelPrefix() + column);
                if (column.equals("clear") ||                    
                    column.equals("edit") ||
                    column.equals("details") )
                {
                    tableColumn.setCanHide(false);
                }

                tableColumns.put(column, tableColumn);
            }
        }
        
        return tableColumns;
    }

    public String saveColumns()
    {
        TablePreference pref = getTablePreference();
        int cnt = 0;
        for (String column : tablePrefOptions.getAvailableColumns())
        {
            pref.getVisible().set(cnt++, tableColumns.get(column).getVisible());
        }
        setTablePreference(pref);
        tablePrefOptions.getTablePreferenceDAO().update(pref);
        return null;
    
    }

    public String cancelEdit()
    {
        // force re-init
//        tablePreference = null;
        tableColumns = null;
        return null;
    }
    
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        this.tableColumns = tableColumns;
    }

    public TablePreference getTablePreference()
    {
        if (tablePreference == null)
        {
            initTablePreference();
        }
        return tablePreference;
    }

    public void setTablePreference(TablePreference tablePreference)
    {
        this.tablePreference = tablePreference;
    }

    public void initTablePreference()
    {
        List<TablePreference> tablePreferenceList = tablePrefOptions.getTablePreferenceDAO().getTablePreferencesByUserID(tablePrefOptions.getUserID());
        boolean found = false;
        for (TablePreference pref : tablePreferenceList)
        {
            if (pref.getTableType() != null && pref.getTableType().equals(tablePrefOptions.getTableType()))
            {                
                int columnCount = tablePrefOptions.getAvailableColumns().size();                
                // case were we add/delete some columns after some preferences are already saved in backend
                // in this case delete from db and re-init to all visible
                if (pref.getVisible().size() != columnCount)
                {
                	tablePrefOptions.getTablePreferenceDAO().deleteByID(pref.getTablePrefID());
                }
                else {
                	if (found) {
                		// duplicate for this user/table type, so delete it
                		tablePrefOptions.getTablePreferenceDAO().deleteByID(pref.getTablePrefID());
                        logger.info("delete duplicate table pref for userID: " + tablePrefOptions.getUserID() + " table type: " + tablePrefOptions.getTableType());
                	}
                	else {
                		setTablePreference(pref);
                		found = true;
                	}
                }
            }
        }
        if (found)
        	return;
        
        TablePreference pref = new TablePreference();
        pref.setUserID(tablePrefOptions.getUserID());
        pref.setTableType(tablePrefOptions.getTableType());
        List<Boolean>visibleList = new ArrayList<Boolean>();
        final Map<String, Boolean> defaultColumns = tablePrefOptions.getDefaultColumns();
        for (String column : tablePrefOptions.getAvailableColumns())
        {
            Boolean visible = defaultColumns.get(column);
            visibleList.add((visible == null) ? false : visible);
        }
        pref.setVisible(visibleList);
        
        Integer tablePrefID = tablePrefOptions.getTablePreferenceDAO().create(tablePrefOptions.getUserID(), pref);
        pref.setTablePrefID(tablePrefID);
        setTablePreference(pref);   
    }

    /**
     * Filter the given list of items by the given filter string.
     * 
     * @param filterItems The items to filter, in place.
     * @param filterValue The value to filter by, to be split into words.
     * @param matchAllWords Whether all words are required to be matched, or only some.
     */
    public void filter(List<T> filterItems, String filterValue, boolean matchAllWords)
    {
        if ((filterValue != null) && (filterValue.length() > 0))
        {
            final String[] filterWords = filterValue.toLowerCase().split("\\s+");
            for (Iterator<T> i = filterItems.iterator(); i.hasNext();)
            {
                final T o = i.next();
                boolean matched = false;
                for (final String word : filterWords)
                {
                    for (final String column : getTableColumns().keySet())
                        if (getTableColumns().get(column).getVisible())
                            {
                                final String value = tablePrefOptions.fieldValue(o, column);
                                if ((value != null) && value.toLowerCase().contains(word))
                                    matched = true;
                            }

                    // we can break if we didn't match and we're required to match all words,
                    // or if we did match and we're only required to match one word
                    if (matched ^ matchAllWords)
                        break;
                }
                if (!matched)
                    i.remove();
            }
        }
    }

    /**
     * Returns the value of the property of the given item described by the given column name. Converts the column name to property syntax and uses BeanUtils to get the property
     * value. For example, the column named "person_fullName" would be translated to "person.fullName", and BeanUtils would be used to call item.getPerson().getFullName().
     * 
     * @param item
     *            The item to get the value from.
     * @param column
     *            The name of the column to get the value of. The name of the column must match the name of a (possibly nested) property as described above.
     * @return The value or <code>null</code> if unavailable.
     */
    public static String fieldValue(Object item, String column)
    {
        try
        {
            return String.valueOf(BeanUtils.getProperty(item, column.replace('_', '.')));
        }
        catch (NestedNullException e)
        {
            // ignore nested nulls
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
                logger.debug("Error filtering on column " + column, e);
        }
        return null;
    }
}
