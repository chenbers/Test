package com.inthinc.pro.backing.dao;


public class Result
{
    String name;
    String value;
    public Result(String name, String value)
    {
        super();
        this.name = name;
        this.value = value;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
