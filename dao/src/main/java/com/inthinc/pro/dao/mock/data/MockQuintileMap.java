package com.inthinc.pro.dao.mock.data;

import com.inthinc.pro.model.QuintileMap;

public class MockQuintileMap
{
    QuintileMap quintileMap;
    Integer groupID;
    Integer driveQMetric;
    Integer duration;
    
    
    public MockQuintileMap(QuintileMap quintileMap, Integer groupID, Integer driveQMetric, Integer duration)
    {
        super();
        this.quintileMap = quintileMap;
        this.groupID = groupID;
        this.driveQMetric = driveQMetric;
        this.duration = duration;
    }
    public QuintileMap getQuintileMap()
    {
        return quintileMap;
    }
    public void setQuintileMap(QuintileMap quintileMap)
    {
        this.quintileMap = quintileMap;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Integer getDriveQMetric()
    {
        return driveQMetric;
    }
    public void setDriveQMetric(Integer driveQMetric)
    {
        this.driveQMetric = driveQMetric;
    }
    public Integer getDuration()
    {
        return duration;
    }
    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }
    
}
