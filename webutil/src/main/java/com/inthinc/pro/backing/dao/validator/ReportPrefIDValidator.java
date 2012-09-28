package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.model.ReportSchedule;

public class ReportPrefIDValidator extends DefaultValidator {

	ReportScheduleDAO reportScheduleDAO;
	

	@Override
	public boolean isValid(String input) {
		
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			ReportSchedule reportSchedule = reportScheduleDAO.findByID(id);
			
			return (reportSchedule != null && isValidAccountID(reportSchedule.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The reportScheduleID is not valid.";
	}
	
	public ReportScheduleDAO getReportScheduleDAO() {
		return reportScheduleDAO;
	}

	public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO) {
		this.reportScheduleDAO = reportScheduleDAO;
	}


}
