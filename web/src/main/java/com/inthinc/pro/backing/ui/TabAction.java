package com.inthinc.pro.backing.ui;

import com.inthinc.pro.model.ScoreType;

public class TabAction
{
    String key;
    String action;
    String displayString;
    String onStyle;
    String offStyle;
    ScoreType scoreType;
    int width;
    
    
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public TabAction(String key, String action, String displayString, String onStyle, String offStyle, ScoreType scoreType, int width)
    {
        this.key = key;
        this.action = action;
        this.displayString = displayString;
        this.onStyle = onStyle;
        this.offStyle = offStyle;
        this.scoreType = scoreType;
        this.width = width;
    }
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public String getAction()
    {
        return action;
    }
    public void setAction(String action)
    {
        this.action = action;
    }
    public String getDisplayString()
    {
        return displayString;
    }
    public void setDisplayString(String displayString)
    {
        this.displayString = displayString;
    }
    public ScoreType getScoreType()
    {
        return scoreType;
    }
    public void setScoreType(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }
    public String getOnStyle()
    {
        return onStyle;
    }
    public void setOnStyle(String onStyle)
    {
        this.onStyle = onStyle;
    }
    public String getOffStyle()
    {
        return offStyle;
    }
    public void setOffStyle(String offStyle)
    {
        this.offStyle = offStyle;
    }
    
}
