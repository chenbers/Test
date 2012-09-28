package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;


public interface RedFlagDAO extends GenericDAO<RedFlag, Integer>
{
    public static final Integer EXCLUDE_FORGIVEN = 0;
    public static final Integer INCLUDE_FORGIVEN = 1;

    
     /**
      * Retrieve the total count of filtered list of red flags for all of the drivers under 
      * the specified group and time frame.
      * 
      * @param groupID
      * @return
      */
      Integer getRedFlagCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<TableFilterField> filterList);


     /**
      * Retrieve the sorted/filtered sublist of red flags for all of the drivers under 
      * the specified group and time frame.
      * 
      * @param groupID
      * @return
      */
      List<RedFlag> getRedFlagPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams);


}
