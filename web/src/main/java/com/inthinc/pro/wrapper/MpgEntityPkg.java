package com.inthinc.pro.wrapper;

import com.inthinc.pro.model.MpgEntity;

public class MpgEntityPkg
{
    private MpgEntity entity;
    private String goTo;
    
    public MpgEntityPkg()
    {
        this.entity = new MpgEntity();
        this.goTo = "";
    }

    public MpgEntity getEntity()
    {
        return entity;
    }

    public void setEntity(MpgEntity entity)
    {
        this.entity = entity;
    }

    public String getGoTo()
    {
        return goTo;
    }

    public void setGoTo(String goTo)
    {
        this.goTo = goTo;
    }
}
