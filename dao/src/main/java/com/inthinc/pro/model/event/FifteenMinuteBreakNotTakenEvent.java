package com.inthinc.pro.model.event;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

import java.util.Date;

public class FifteenMinuteBreakNotTakenEvent extends Event {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    private Long totalDrivingTime;
    private Long totalStopTime;
    private Long expectedStopDuration;
    private Date firstDrivingTime;
    private Date lastDrivingTime1stTrip;
    private Date lastDrivingTimeLastTrip;
    private Date violationStartTime;

    private static EventAttr[] eventAttrList = {
    };

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    public FifteenMinuteBreakNotTakenEvent(){
        super();
    }

    public FifteenMinuteBreakNotTakenEvent(Long totalDrivingTime, Long totalStopTime, Long expectedStopDuration, Date firstDrivingTime, Date lastDrivingTime1stTrip, Date lastDrivingTimeLastTrip, Date violationStartTime) {
        this.totalDrivingTime = totalDrivingTime;
        this.totalStopTime = totalStopTime;
        this.expectedStopDuration = expectedStopDuration;
        this.firstDrivingTime = firstDrivingTime;
        this.lastDrivingTime1stTrip = lastDrivingTime1stTrip;
        this.lastDrivingTimeLastTrip = lastDrivingTimeLastTrip;
        this.violationStartTime = violationStartTime;
    }

    public FifteenMinuteBreakNotTakenEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
                                           Long totalDrivingTime, Long totalStopTime, Long expectedStopDuration, Date firstDrivingTime, Date lastDrivingTime1stTrip, Date lastDrivingTimeLastTrip, Date violationStartTime){
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.totalDrivingTime = totalDrivingTime;
        this.totalStopTime = totalStopTime;
        this.expectedStopDuration = expectedStopDuration;
        this.firstDrivingTime = firstDrivingTime;
        this.lastDrivingTime1stTrip = lastDrivingTime1stTrip;
        this.lastDrivingTimeLastTrip = lastDrivingTimeLastTrip;
        this.violationStartTime = violationStartTime;
    }

    @Override
    public boolean isValidEvent() {
      return true;
    }

    public Long getTotalDrivingTime() {
        return totalDrivingTime;
    }

    public void setTotalDrivingTime(Long totalDrivingTime) {
        this.totalDrivingTime = totalDrivingTime;
    }

    public Long getTotalStopTime() {
        return totalStopTime;
    }

    public void setTotalStopTime(Long totalStopTime) {
        this.totalStopTime = totalStopTime;
    }

    public Long getExpectedStopDuration() {
        return expectedStopDuration;
    }

    public void setExpectedStopDuration(Long expectedStopDuration) {
        this.expectedStopDuration = expectedStopDuration;
    }

    public Date getFirstDrivingTime() {
        return firstDrivingTime;
    }

    public void setFirstDrivingTime(Date firstDrivingTime) {
        this.firstDrivingTime = firstDrivingTime;
    }

    public Date getLastDrivingTime1stTrip() {
        return lastDrivingTime1stTrip;
    }

    public void setLastDrivingTime1stTrip(Date lastDrivingTime1stTrip) {
        this.lastDrivingTime1stTrip = lastDrivingTime1stTrip;
    }

    public Date getLastDrivingTimeLastTrip() {
        return lastDrivingTimeLastTrip;
    }

    public void setLastDrivingTimeLastTrip(Date lastDrivingTimeLastTrip) {
        this.lastDrivingTimeLastTrip = lastDrivingTimeLastTrip;
    }

    public Date getViolationStartTime() {
        return violationStartTime;
    }

    public void setViolationStartTime(Date violationStartTime) {
        this.violationStartTime = violationStartTime;
    }

    public static void setEventAttrList(EventAttr[] eventAttrList) {
        FifteenMinuteBreakNotTakenEvent.eventAttrList = eventAttrList;
    }
}
