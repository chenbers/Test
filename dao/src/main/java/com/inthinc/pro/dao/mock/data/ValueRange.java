package com.inthinc.pro.dao.mock.data;

public class ValueRange
{
    Comparable low;
    Comparable high;

    public ValueRange(Comparable low, Comparable high)
    {
        super();
        this.low = low;
        this.high = high;
    }

    public Comparable getLow()
    {
        return low;
    }
    public void setLow(Comparable low)
    {
        this.low = low;
    }
    public Object getHigh()
    {
        return high;
    }
    public void setHigh(Comparable high)
    {
        this.high = high;
    }
    
}
