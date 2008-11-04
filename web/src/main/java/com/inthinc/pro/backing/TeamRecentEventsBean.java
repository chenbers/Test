package com.inthinc.pro.backing;

import java.util.List;

import com.inthinc.pro.model.Event;

public class TeamRecentEventsBean extends BaseBean
{
    private List<Event> recentEvents;
    
    private NavigationBean navigation;


    public List<Event> getRecentEvents()
    {
        return recentEvents;
    }

    public void setRecentEvents(List<Event> recentEvents)
    {
        this.recentEvents = recentEvents;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
}
