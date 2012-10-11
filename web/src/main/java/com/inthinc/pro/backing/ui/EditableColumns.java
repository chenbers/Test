package com.inthinc.pro.backing.ui;

import java.util.List;
import java.util.Map;


public interface EditableColumns
{
    public Map<String, TableColumn> getTableColumns();
    public void setTableColumns(Map<String, TableColumn> tableColumns);
    public List<String> getAvailableColumns();
    public String saveColumns();
}
