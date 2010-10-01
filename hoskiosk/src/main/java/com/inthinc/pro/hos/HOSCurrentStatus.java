package com.inthinc.pro.hos;

import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.MinutesData;
import com.inthinc.hos.model.MinutesRemainingData;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSCurrentStatus extends HOSBase {

    HOSStatus status;
    Long statusMinutes;
    Long onDutyMinutes;
    Long onDutyAvailableMinutes;

    public HOSCurrentStatus(HOSDAO hosDAO) {
        super(hosDAO);
    }
    
    
    public Long getStatusMinutes() {
        return statusMinutes;
    }
    public void setStatusMinutes(Long statusMinutes) {
        this.statusMinutes = statusMinutes;
    }
    public Long getOnDutyMinutes() {
        return onDutyMinutes;
    }
    public void setOnDutyMinutes(Long onDutyMinutes) {
        this.onDutyMinutes = onDutyMinutes;
    }
    public Long getOnDutyAvailableMinutes() {
        return onDutyAvailableMinutes;
    }
    public void setOnDutyAvailableMinutes(Long onDutyAvailableMinutes) {
        this.onDutyAvailableMinutes = onDutyAvailableMinutes;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }


    public void init(Driver driver) {
        DateTime currentDate = new DateTime();
        List<HOSRecord> hosRecordList = fetchHosRecordList(currentDate, driver);
        if (hosRecordList == null)
            return;
  
        RuleSetType driverRuleSetType = driver.getDriverDOTType();
        List<HOSRec> recListForHoursRemainingCalc = HOSUtil.getRecListFromLogList(hosRecordList, currentDate.toDate(), true);
        HOSRules hosRules =  RuleSetFactory.getRulesForRuleSetType(driverRuleSetType); 
        MinutesRemainingData minutesRemainingData = hosRules.getDOTMinutesRemaining(recListForHoursRemainingCalc, currentDate.toDate());
        long currentStatusMin = hosRules.getCurrentStatusMinutes();

        MinutesData minutesData = hosRules.getDOTMinutes();

        HOSRec lastRec = (recListForHoursRemainingCalc.size() == 0) ? null : recListForHoursRemainingCalc.get(0); 
        setStatus(lastRec == null ? HOSStatus.OFF_DUTY : lastRec.getStatus());
        setStatusMinutes(currentStatusMin);
        setOnDutyMinutes(minutesData.getOnDutyMinutes());
        setOnDutyAvailableMinutes(minutesRemainingData.getOnDutyDOTMinutesRemaining());
        
    }
}
