package com.inthinc.pro.backing;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.TableType;

public interface TablePrefOptions
{
    public String getColumnLabelPrefix();
    public TablePreferenceDAO getTablePreferenceDAO();
    public List<String> getAvailableColumns();
    public Map<String, Boolean> getDefaultColumns();
    public TableType getTableType();
    public Integer getUserID();

}
