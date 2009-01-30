package com.inthinc.pro.backing;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public class WarningsBean extends BaseEventsBean
{
    private static final Logger     logger                  = Logger.getLogger(WarningsBean.class);
    
    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getWarningEventsForGroup(groupID, 7);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.WARNINGS;
    }

    public String showAllFromRecentAction()
    {
        setSearchText(null);
        setCategoryFilter(null);
        setEventFilter(null);
        
        refreshAction();
        return "go_warnings";
    }
}
