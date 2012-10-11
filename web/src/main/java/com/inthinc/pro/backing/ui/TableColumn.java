package com.inthinc.pro.backing.ui;

public class TableColumn
{
    Boolean visible;
    Boolean canHide;
    String colLabel;
    
    public TableColumn(Boolean visible, String colLabel)
    {
        this.visible = visible;
        this.colLabel = colLabel;
        this.canHide = true;
    }
    public Boolean getVisible()
    {
        return visible;
    }
    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }
    public String getColLabel()
    {
        return colLabel;
    }
    public void setColLabel(String colLabel)
    {
        this.colLabel = colLabel;
    }
    public Boolean getCanHide()
    {
        return canHide;
    }
    public void setCanHide(Boolean canHide)
    {
        this.canHide = canHide;
    }
    

}
