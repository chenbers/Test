package com.inthinc.pro.reports.tabular;

import java.util.List;


public interface Tabular {
    
    List<String> getColumnHeaders();
    List<List<Result>> getTableRows();
    List<ColumnHeader> getColumnSummaryHeaders();

}
