package com.inthinc.pro.backing.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GroupLevel
{
    FLEET(1, "FLEET", "/secured/groupOverview", "go_home"), 
    DIVISION(2, "DIVISION", "/secured/groupOverview", "go_division"), 
    TEAM(3, "TEAM", "/secured/groupOverview", "go_team");

    private String description;
    private int    code;
    private String url;
    private String location;

    private GroupLevel(int code, String description, String url, String location)
    {
        this.code = code;
        this.description = description;
        this.url = url;
        this.location = location;
    }

    private static final Map<Integer, GroupLevel> lookup = new HashMap<Integer, GroupLevel>();
    static
    {
        for (GroupLevel p : EnumSet.allOf(GroupLevel.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GroupLevel getGroupLevel(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getDescription(){
    	return this.description;
    }

}
