package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.CustomMap;

public interface CustomMapDAO extends GenericDAO<CustomMap, Integer>{
    
    List<CustomMap> getCustomMapsByAcctID(Integer acctID);

}
