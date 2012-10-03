package com.inthinc.pro.model.pagination;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.event.NoteType;

public class FilterableEnumFactory implements TableFilterFactory {

    FilterableEnum filterableEnum;
    
    public FilterableEnumFactory() {
        
    }
    
    public FilterableEnumFactory(FilterableEnum filterableEnum) {
        this.filterableEnum = filterableEnum;
    }
    
    @Override
    public List<TableFilterField> getFilters(String propertyName) {
        List<TableFilterField>  tableFilterList = new ArrayList<TableFilterField>();
        tableFilterList.add(new TableFilterField(propertyName, filterableEnum.getFilter(), FilterOp.IN));
        return tableFilterList;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(filterableEnum == null ? "" : filterableEnum.getFilter());
        return sb.toString();
    }

    
}
