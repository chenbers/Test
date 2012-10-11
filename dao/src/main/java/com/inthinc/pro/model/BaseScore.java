package com.inthinc.pro.model;

import java.util.Date;

public class BaseScore extends BaseEntity {
    private Date date;
    
    public BaseScore()
    {
    	
    }
    public BaseScore(Date date)
    {
    	this.date = date;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }

}
