package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class SeatbeltClicksReportCriteria extends ReportCriteria{
    
    private static Logger logger = Logger.getLogger(SeatbeltClicksReportCriteria.class);
    
    private SeatbeltClicksReportCriteria(Locale locale){
        super(ReportType.SEATBELT_CLICKS_REPORT, null, locale);
        logger.debug(String.format("Creating SeatbeltClicksReportCriteria with locale %s", locale.toString()));
    }
    
    public static class Builder{
        
        private Locale locale;
        
        private DateTimeZone dateTimeZone;
        
        private List<Integer> groupIDs;
        
        private TimeFrame timeFrame;
        
        private GroupReportDAO groupReportDAO;
        
        private GroupHierarchy groupHierarchy;
        
        public Builder(GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, List<Integer> groupIDs,TimeFrame timeFrame) {
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupIDs = groupIDs;
            this.timeFrame = timeFrame;
            this.groupHierarchy = groupHierarchy;
			this.groupReportDAO = groupReportDAO;
        }
        
        public void setDateTimeZone(DateTimeZone dateTimeZone) {
            this.dateTimeZone = dateTimeZone;
        }
        
        public DateTimeZone getDateTimeZone() {
            return dateTimeZone;
        }
        
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
        
        public Locale getLocale() {
            return locale;
        }
        
        public List<Integer> getGroupIDs() {
            return groupIDs;
        }
        
        public GroupReportDAO getGroupReportDAO() {
            return groupReportDAO;
        }
        
        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }
        
        public TimeFrame getInterval() {
            return timeFrame;
        }
        
        public SeatbeltClicksReportCriteria build(){
            logger.debug(String.format("Building SeatbeltClicksCriteria with locale %s",locale));
			List<DriverVehicleScoreWrapper> resultsList = new ArrayList<DriverVehicleScoreWrapper>();
			 
			for (Integer groupID : this.groupIDs)
				resultsList.addAll(groupReportDAO.getDriverScores(groupID, timeFrame.getInterval(), groupHierarchy));
			 
            
            List<SeatbeltClicksReportCriteria.SeatbeltClicksWrapper> seatbeltClicksWrappers = new ArrayList<SeatbeltClicksReportCriteria.SeatbeltClicksWrapper>();
            for(DriverVehicleScoreWrapper dvsw : resultsList){
				String driverName = dvsw.getDriver().getPerson().getLast() + ", " + dvsw.getDriver().getPerson().getFirst();

				float seatbelt = ((dvsw.getScore().getSeatbelt() != null) ? dvsw.getScore().getSeatbelt().intValue() : 0)/10.0F;
				String groupName = groupHierarchy.getFullGroupName(dvsw.getDriver().getGroupID());
                SeatbeltClicksReportCriteria.SeatbeltClicksWrapper seatbeltClicksWrapper = new SeatbeltClicksReportCriteria.SeatbeltClicksWrapper(driverName, dvsw.getScore().getTrips().intValue(), dvsw.getScore().getSeatbeltClicks().intValue(), dvsw.getScore().getOdometer6().intValue(), seatbelt, groupName);
                seatbeltClicksWrappers.add(seatbeltClicksWrapper);
            }
            Collections.sort(seatbeltClicksWrappers);
            
            SeatbeltClicksReportCriteria criteria = new SeatbeltClicksReportCriteria(this.locale);
            criteria.setMainDataset(seatbeltClicksWrappers);
            criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), this.dateTimeZone.toTimeZone());
            
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            return criteria;
            
        }
    }
    
    public static class SeatbeltClicksWrapper  implements Comparable<SeatbeltClicksWrapper>{
        
        private String groupPath;
		private String driverName;
		private Integer trips;
		private Integer seatbeltClicks;
		private Integer drivingMiles;
		private float seatbeltScore;
        
        public SeatbeltClicksWrapper(String driverName, Integer trips, Integer seatbeltClicks, Integer drivingMiles, Float seatbeltScore, String groupPath) {
            this.driverName = driverName;
            this.trips = trips;
            this.seatbeltClicks = seatbeltClicks;
            this.drivingMiles = drivingMiles;
            this.seatbeltScore = seatbeltScore;
            this.groupPath = groupPath;
        }

        public String getGroupPath() {
            return groupPath;
        }

        public void setGroupPath(String groupPath) {
            this.groupPath = groupPath;
        }

        public Integer getTrips() {
            return trips;
        }
        
        public void setTrips(Integer trips) {
            this.trips = trips;
        }
        
        public Integer getDrivingMiles() {
            return drivingMiles;
        }
        
        public void setDrivingMiles(Integer drivingMiles) {
            this.drivingMiles = drivingMiles;
        }
        
        public Integer getSeatbeltClicks() {
            return seatbeltClicks;
        }
        
        public void setSeatbeltClicks(Integer seatbeltClicks) {
            this.seatbeltClicks = seatbeltClicks;
        }

        public Float getSeatbeltScore() {
            return seatbeltScore;
        }
        
        public void setSeatbeltScore(Float seatbeltScore) {
            this.seatbeltScore = seatbeltScore;
        }

        @Override
        public int compareTo(SeatbeltClicksWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }
    }
}
