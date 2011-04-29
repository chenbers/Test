package com.inthinc.pro.selenium.util;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

public class XAxisInfo {
    
    // time frames
    private ArrayList<String> thirtyDays = new ArrayList<String>();
    private ArrayList<String> threeMonths = new ArrayList<String>();
    private ArrayList<String> sixMonths = new ArrayList<String>();
    private ArrayList<String> twelveMonths = new ArrayList<String>();
    
    // speed ranges
    private ArrayList<String> speedRanges = new ArrayList<String>();
    
    // style categories
    private ArrayList<String> styleCats = new ArrayList<String>();

    public ArrayList<String> getThirtyDays() {
        DateTime dt = new DateTime();
        thirtyDays.add(dt.minusDays(-1).toString());
        
        for ( int i = 0; i < 29; i++ ) {
            thirtyDays.add(dt.minusDays(i).toString());
        }
        
        Collections.reverse(thirtyDays);
        
        return thirtyDays;
    }

    public void setThirtyDays(ArrayList<String> thirtyDays) {
        this.thirtyDays = thirtyDays;
    }

    public ArrayList<String> getThreeMonths() {
        threeMonths = setMonths(3);
        
        return threeMonths;
    }

    public void setThreeMonths(ArrayList<String> threeMonths) {
        this.threeMonths = threeMonths;
    }

    public ArrayList<String> getSixMonths() {
        sixMonths = setMonths(6);
        
        return sixMonths;
    }

    public void setSixMonths(ArrayList<String> sixMonths) {
        this.sixMonths = sixMonths;
    }

    public ArrayList<String> getTwelveMonths() {
        twelveMonths = setMonths(12);
        
        return twelveMonths;
    }

    public void setTwelveMonths(ArrayList<String> twelveMonths) {
        this.twelveMonths = twelveMonths;
    }

    private ArrayList<String> setMonths(int howMany) {
        ArrayList<String> tmp = new ArrayList<String>();
        DateTime dt = new DateTime();
        tmp.add(dt.toString());
        
        for ( int i = 0; i < howMany-1; i++ ) {
            tmp.add(dt.minusMonths(i+1).toString());
        }
        
        Collections.reverse(tmp);
        
        return tmp;
    }

    public ArrayList<String> getSpeedRanges() {
        return speedRanges;
    }

    public void setSpeedRanges(ArrayList<String> speedRanges) {
        this.speedRanges = speedRanges;
    }

    public ArrayList<String> getStyleCats() {
        return styleCats;
    }

    public void setStyleCats(ArrayList<String> styleCats) {
        this.styleCats = styleCats;
    }
}
