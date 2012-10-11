package com.inthinc.pro.model.pagination;

public interface FilterableEnum {
    
    Object getFilter(); 
    
    Boolean includeNull();

}
