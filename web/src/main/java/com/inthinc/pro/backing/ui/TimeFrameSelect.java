package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.util.TimeFrameUtil;

public class TimeFrameSelect {
    private TimeFrame timeFrame;
    private Locale locale;
    
    public TimeFrameSelect(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public List<SelectItem> getAltTimeFrames() {
        
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(TimeFrame.TODAY, TimeFrameUtil.getTimeFrameStr(TimeFrame.TODAY, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.ONE_DAY_AGO, TimeFrameUtil.getTimeFrameStr(TimeFrame.ONE_DAY_AGO, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.SUN_SAT_WEEK, TimeFrameUtil.getTimeFrameStr(TimeFrame.SUN_SAT_WEEK, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.PAST_SEVEN_DAYS, TimeFrameUtil.getTimeFrameStr(TimeFrame.PAST_SEVEN_DAYS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.LAST_MONTH, TimeFrameUtil.getTimeFrameStr(TimeFrame.LAST_MONTH, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.LAST_THIRTY_DAYS, TimeFrameUtil.getTimeFrameStr(TimeFrame.LAST_THIRTY_DAYS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.THREE_MONTHS, TimeFrameUtil.getTimeFrameStr(TimeFrame.THREE_MONTHS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.SIX_MONTHS, TimeFrameUtil.getTimeFrameStr(TimeFrame.SIX_MONTHS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.YEAR, TimeFrameUtil.getTimeFrameStr(TimeFrame.YEAR, getLocale())));
        
        return selectItemList;
    }
    
    public List<SelectItem> getTimeFrames() {
        
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(TimeFrame.TODAY, TimeFrameUtil.getTimeFrameStr(TimeFrame.TODAY, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.ONE_DAY_AGO, TimeFrameUtil.getTimeFrameStr(TimeFrame.ONE_DAY_AGO, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.WEEK, TimeFrameUtil.getTimeFrameStr(TimeFrame.WEEK, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.LAST_THIRTY_DAYS, TimeFrameUtil.getTimeFrameStr(TimeFrame.LAST_THIRTY_DAYS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.THREE_MONTHS, TimeFrameUtil.getTimeFrameStr(TimeFrame.THREE_MONTHS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.SIX_MONTHS, TimeFrameUtil.getTimeFrameStr(TimeFrame.SIX_MONTHS, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.YEAR, TimeFrameUtil.getTimeFrameStr(TimeFrame.YEAR, getLocale())));
        
        return selectItemList;
    }

    public List<SelectItem> getAltDaysTimeFrames() {
        
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(TimeFrame.TODAY, TimeFrameUtil.getTimeFrameStr(TimeFrame.TODAY, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.ONE_DAY_AGO, TimeFrameUtil.getTimeFrameStr(TimeFrame.ONE_DAY_AGO, getLocale())));
        selectItemList.add(new SelectItem(TimeFrame.PAST_SEVEN_DAYS, TimeFrameUtil.getTimeFrameStr(TimeFrame.PAST_SEVEN_DAYS, getLocale())));
        return selectItemList;
    }
}
