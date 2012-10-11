package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.model.CrashReport;

public class CrashReportIDValidator extends DefaultValidator {

	CrashReportDAO crashReportDAO;
	

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
			CrashReport crashReport = crashReportDAO.findByID(id);
			Integer accountID = (crashReport.getDriver() != null && crashReport.getDriver().getPerson() != null) ? crashReport.getDriver().getPerson().getAcctID() : null;
				
			return (crashReport != null && isValidAccountID(accountID));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The crashReportID is not valid.";
	}
	public CrashReportDAO getCrashReportDAO() {
		return crashReportDAO;
	}

	public void setCrashReportDAO(CrashReportDAO crashReportDAO) {
		this.crashReportDAO = crashReportDAO;
	}

}
