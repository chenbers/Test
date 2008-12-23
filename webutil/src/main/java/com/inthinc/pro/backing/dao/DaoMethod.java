package com.inthinc.pro.backing.dao;

import java.lang.reflect.Method;

public class DaoMethod
{
    Method method;
    int interfaceIdx;
    public DaoMethod(Method method, int interfaceIdx)
    {
        super();
        this.method = method;
        this.interfaceIdx = interfaceIdx;
    }
    public Method getMethod()
    {
        return method;
    }
    public void setMethod(Method method)
    {
        this.method = method;
    }
    public int getInterfaceIdx()
    {
        return interfaceIdx;
    }
    public void setInterfaceIdx(int interfaceIdx)
    {
        this.interfaceIdx = interfaceIdx;
    }
    
    
}
