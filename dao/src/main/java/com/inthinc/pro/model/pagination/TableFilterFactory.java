package com.inthinc.pro.model.pagination;

import java.util.List;

public interface TableFilterFactory {
	List<TableFilterField> getFilters(String propertyName);

}
