package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.pagination.PageParams;

public class RedFlagPaginationTableDataProvider   extends BaseNotificationPaginationDataProvider<RedFlag> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8187242169731166444L;

	private static final Logger logger = Logger.getLogger(RedFlagPaginationTableDataProvider.class);
    
	private RedFlagDAO              redFlagDAO;
	private Integer 				groupID;
    private AlertMessageDAO         alertMessageDAO;



    public RedFlagPaginationTableDataProvider() {
	    
//		logger.info("RedFlagPaginationTableDataProvider");
	}

	@Override
	public List<RedFlag> getItemsByRange(int firstRow, int endRow) {
		
		if (endRow < 0)
			return new ArrayList<RedFlag>();
		
		if (endDate == null || startDate == null) {
			initStartEndDates();
		}
	
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, pageParams);
		alertMessageDAO.fillInRedFlagMessageInfo(redFlagList);
		return redFlagList;
	}

	@Override
	public int getRowCount() {
		if (groupID == null)
			return 0;

		initStartEndDates();
		return redFlagDAO.getRedFlagCount(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, getFilters());
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public RedFlagDAO getRedFlagDAO() {
		return redFlagDAO;
	}

	public void setRedFlagDAO(RedFlagDAO redFlagDAO) {
		this.redFlagDAO = redFlagDAO;
	}

    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }

}
