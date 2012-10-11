package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GQVMap extends BaseEntity
{
    private Group group;
    @Column(name="driveQV", type=com.inthinc.pro.model.DriveQMap.class, updateable=false)
    private List<DriveQMap> driveQV;
    
    public GQVMap()
    {
        
    }
    
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
