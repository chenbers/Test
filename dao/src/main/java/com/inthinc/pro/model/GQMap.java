package com.inthinc.pro.model;

public class GQMap extends BaseEntity
{
    private Group group;
    private DriveQMap driveQ;
    
    public Group getGroup()
    {
        return group;
    }
    public void setGroup(Group group)
    {
        this.group = group;
    }
    public DriveQMap getDriveQ()
    {
        return driveQ;
    }
    public void setDriveQ(DriveQMap driveQ)
    {
        this.driveQ = driveQ;
    }

}
