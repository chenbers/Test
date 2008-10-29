package com.inthinc.pro.backing.ui;

import com.inthinc.pro.model.ScoreType;

public class TabAction
{
    String key;
    String action;
    String displayString;
    String style;
    ScoreType scoreType;
    
    
    public TabAction(String key, String action, String displayString, String style, ScoreType scoreType)
    {
        this.key = key;
        this.action = action;
        this.displayString = displayString;
        this.style = style;
        this.scoreType = scoreType;
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
    public String getStyle()
    {
        return style;
    }
    public void setStyle(String style)
    {
        this.style = style;
    }
    public ScoreType getScoreType()
    {
        return scoreType;
    }
    public void setScoreType(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }
    
}
