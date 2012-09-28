package com.inthinc.pro.dao.mock.data;

import java.util.List;

public class ValueList
{
    List<Object> objList;
    
    public ValueList(List<Object> objList)
    {
        this.objList = objList;
    }

    public List<Object> getObjList()
    {
        return objList;
    }

    public void setObjList(List<Object> objList)
    {
        this.objList = objList;
    }
}
