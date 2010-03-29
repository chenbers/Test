package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;

public abstract class BaseTableColumns extends BaseBean {

	private static final long serialVersionUID = -6946632928703112651L;
	
	private TablePreferenceDAO tablePreferenceDAO;
	private TablePreference tablePreference; 
    private Map<String, TableColumn> tableColumns;

	public abstract TableType getTableType();
    public abstract List<String> getAvailableColumns();
    public abstract Map<String, Boolean> getDefaultColumns();
    public abstract String getColumnLabelPrefix();

    public void init() {
    	initTablePreference();
    	initTableColumns();
    }
    
    private void initTableColumns() {
        List<Boolean> visibleList = getTablePreference().getVisible();
        tableColumns = new HashMap<String, TableColumn>();
        int cnt = 0;
        for (String column : getAvailableColumns())
        {
            Boolean visible = false;
            if (cnt < visibleList.size())
                visible = visibleList.get(cnt++);
            TableColumn tableColumn = new TableColumn(visible, getColumnLabelPrefix() + column);
            if (column.equals("clear") ||                    
                column.equals("edit") ||
                column.equals("details") )
            {
                tableColumn.setCanHide(false);
            }

            tableColumns.put(column, tableColumn);
        }
    }
		
	public void initTablePreference()
    {
        List<TablePreference> tablePreferenceList = getTablePreferenceDAO().getTablePreferencesByUserID(getUser().getUserID());
        for (TablePreference pref : tablePreferenceList)
        {
            if (pref.getTableType().equals(getTableType()))
            {                
                int columnCount = getAvailableColumns().size();                
                // case were we add/delete some columns after some preferences are already saved in backend
                // in this case delete from db and re-init to all visible
                if (pref.getVisible().size() != columnCount)
                {
                    getTablePreferenceDAO().deleteByID(pref.getTablePrefID());
                    break;
                }
                setTablePreference(pref);
                return;
            }
        }
        TablePreference pref = new TablePreference();
        pref.setUserID(getUser().getUserID());
        pref.setTableType(getTableType());
        List<Boolean>visibleList = new ArrayList<Boolean>();
        final Map<String, Boolean> defaultColumns = getDefaultColumns();
        for (String column : getAvailableColumns())
        {
            Boolean visible = defaultColumns.get(column);
            visibleList.add((visible == null) ? false : visible);
        }
        pref.setVisible(visibleList);
        
        Integer tablePrefID = getTablePreferenceDAO().create(getUser().getUserID(), pref);
        pref.setTablePrefID(tablePrefID);
        setTablePreference(pref);   
    }
	
    public void saveColumnsAction()
    {
        TablePreference pref = getTablePreference();
        int cnt = 0;
        for (String column : getAvailableColumns())
        {
            pref.getVisible().set(cnt++, tableColumns.get(column).getVisible());
        }
        setTablePreference(pref);
        getTablePreferenceDAO().update(pref);
    
    }

    public void cancelEditAction()
    {
    	initTableColumns();
    }


	public TablePreference getTablePreference() {
		return tablePreference;
	}
	public void setTablePreference(TablePreference tablePreference) {
		this.tablePreference = tablePreference;
	}
	public TablePreferenceDAO getTablePreferenceDAO() {
		return tablePreferenceDAO;
	}

	public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO) {
		this.tablePreferenceDAO = tablePreferenceDAO;
	}

    public Map<String, TableColumn> getTableColumns() {
		return tableColumns;
	}
	public void setTableColumns(Map<String, TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}
}
