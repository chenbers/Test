package com.inthinc.pro.backing.dao;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.convert.Convert;


public class Param
{
    Class<?> paramType;
    String paramValue;
    String paramName;
    String paramInputDesc;
    Class<?> paramConvert;
    public Param()
    {
        
    }
    public Class<?> getParamType()
    {
        return paramType;
    }
    public void setParamType(Class<?> paramType)
    {
        this.paramType = paramType;
    }
    public String getParamTypeName()
    {
        return paramType.toString();
    }
    public String getParamValue()
    {
        return paramValue;
    }
    public void setParamValue(String paramValue)
    {
        this.paramValue = paramValue;
    }
    public String getParamName()
    {
        return paramName;
    }
    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }
    public String getParamInputDesc()
    {
        return paramInputDesc;
    }
    public void setParamInputDesc(String paramInputDesc)
    {
        this.paramInputDesc = paramInputDesc;
    }
    
    public Object getParamValueObject()
    {
        if (paramConvert != null)
        {
            try
            {
                paramValue = ((Convert)paramConvert.newInstance()).convert(paramValue.toString());
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (paramType.isInstance(Integer.valueOf(0)))
        {
            return Integer.valueOf(paramValue);
        }
        if (paramType.isInstance(Long.valueOf(0l)))
        {
            return Long.valueOf(paramValue);
        }
        return paramValue;
    }

    public Object[] getParamValueObjectArray()
    {
        if (paramType.isInstance(new Integer[0]))
        {
            List<Integer> list = new ArrayList<Integer>();
            
            String params[] = paramValue.split(",");
            for (int i = 0; i < params.length; i++)
            {
                list.add(Integer.valueOf(params[i]));
            }
            
            return list.toArray(new Integer[0]);
        }
        return null;
    }
    public Class<?> getParamConvert()
    {
        return paramConvert;
    }
    public void setParamConvert(Class<?> paramConvert)
    {
        this.paramConvert = paramConvert;
    }
}
