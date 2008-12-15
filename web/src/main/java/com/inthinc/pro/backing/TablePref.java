package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;

public class TablePref
{
    
    private TablePrefOptions tablePrefOptions;
    
    private Map<String, TableColumn>    tableColumns;
    private TablePreference tablePreference;
    
    public TablePref(TablePrefOptions tablePrefOptions)
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
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(tablePrefOptions.getColumnLabelPrefix() + column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);

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
        for (TablePreference pref : tablePreferenceList)
        {
            if (pref.getTableType().equals(tablePrefOptions.getTableType()))
            {
                setTablePreference(pref);
                return;
            }
        }
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

}
