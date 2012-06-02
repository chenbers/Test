package com.inthinc.pro.backing;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.TableType;

public interface TablePrefOptions<T>
{
	public String getColumnLabelPrefix();
    public TablePreferenceDAO getTablePreferenceDAO();
    public List<String> getAvailableColumns();
    public Map<String, Boolean> getDefaultColumns();
    public TableType getTableType();
    public Integer getUserID();

    /**
     * Return the value of the field named by the given column. For convenience, simply call {@link TablePref#fieldValue(Object, String)} for sensibly-named columns.
     * 
     * @param o
     *            The object to get the value from.
     * @param column
     *            The column to get the field value of.
     * @return The field value, as a user-visible string.
     */
    public String fieldValue(T o, String column);
    
    public void columnsChanged();

}
