package com.inthinc.pro.model.hos;

import com.inthinc.pro.model.BaseEntity;

public class HOSOccupantHistory extends BaseEntity {
    
    int offset;
    int occupantID;
    int startDayOfYear;    

    public HOSOccupantHistory(int offset, int occupantID, int startDayOfYear)
    {
        this.offset = offset;
        this.occupantID = occupantID;
        this.startDayOfYear = startDayOfYear;
    }

    public int getOffset(){return this.offset;}
    public int getOccupantID(){return this.occupantID;}
    public int getStartDayOfYear(){return this.startDayOfYear;}
}
