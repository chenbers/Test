package com.inthinc.pro.reports.performance.model;

import com.inthinc.hos.model.HOSStatus;

import java.util.Date;

public class BreakHOSRec implements Comparable<BreakHOSRec> {

		private Long totalSeconds;
		private HOSStatus status;
		private Date logTimeDate;
		public BreakHOSRec(HOSStatus status, Date logTimeDate, Long totalSeconds) {
			this.status = status;
			this.logTimeDate = logTimeDate;
			this.totalSeconds = totalSeconds;
		}

		public HOSStatus getStatus() {
			return status;
		}

		public void setStatus(HOSStatus status) {
			this.status = status;
		}

		public Date getLogTimeDate() {
			return logTimeDate;
		}

		public void setLogTimeDate(Date logTimeDate) {
			this.logTimeDate = logTimeDate;
		}

		public Long getTotalSeconds() {
			return totalSeconds;
		}

		public void setTotalSeconds(Long totalSeconds) {
			this.totalSeconds = totalSeconds;
		}


		@Override
		public int compareTo(BreakHOSRec o) {
	        // natural order is log time descending (most recent first)
	        if (o.getLogTimeDate() == null && getLogTimeDate() == null)
	            return 0;
	        if (o.getLogTimeDate() == null && getLogTimeDate() != null)
	            return -1;
	        if (o.getLogTimeDate() != null && getLogTimeDate() == null)
	            return 1;
	        
	        return (o.getLogTimeDate().compareTo(getLogTimeDate()));
		}
		
		public void dump() {
	        System.out.println("new BreakHOSRec(" +
	                "new Date(" + new Date(logTimeDate.getTime()) + "l)," +
	                "HOSStatus." + status.getName() + "," +
	                totalSeconds + "),");
		}
}
