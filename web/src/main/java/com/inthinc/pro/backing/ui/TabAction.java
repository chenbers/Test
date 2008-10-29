package com.inthinc.pro.backing.ui;

public class TabAction
{
    String key;
    String action;
    String displayString;
    String style;
    
    
    public TabAction(String key, String action, String displayString, String style)
    {
        this.key = key;
        this.action = action;
        this.displayString = displayString;
        this.style = style;
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
    
}
