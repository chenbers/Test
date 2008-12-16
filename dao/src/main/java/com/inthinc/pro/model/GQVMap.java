package com.inthinc.pro.model;

import java.util.List;

public class GQVMap extends BaseEntity
{
    private Group group;
    private List<DriveQMap> driveQV;
    
    public Group getGroup()
    {
        return group;
    }
    public void setGroup(Group group)
    {
        this.group = group;
    }
    public List<DriveQMap> getDriveQV()
    {
        return driveQV;
    }
    public void setDriveQV(List<DriveQMap> driveQV)
    {
        this.driveQV = driveQV;
    }
    


}
