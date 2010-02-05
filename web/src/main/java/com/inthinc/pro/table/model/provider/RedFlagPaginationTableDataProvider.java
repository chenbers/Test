package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.pagination.PageParams;

public class RedFlagPaginationTableDataProvider   extends GenericPaginationTableDataProvider<RedFlag> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8187242169731166444L;

	private static final Logger logger = Logger.getLogger(RedFlagPaginationTableDataProvider.class);
    
	private RedFlagDAO              redFlagDAO;
	private Integer 				groupID;
	private Integer					daysBack;
    private Date endDate;
    private Date startDate;


	public RedFlagPaginationTableDataProvider() {
	    
		logger.info("RedFlagPaginationTableDataProvider");
	}

	@Override
	public List<RedFlag> getItemsByRange(int firstRow, int endRow) {
		
		if (endRow < 0)
			return new ArrayList<RedFlag>();
		
		if (endDate == null || startDate == null) {
			initStartEndDates();
		}
	
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return redFlagDAO.getRedFlagPage(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, pageParams);
	}

	@Override
	public int getRowCount() {
		if (groupID == null)
			return 0;

		initStartEndDates();
		return redFlagDAO.getRedFlagCount(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, getFilters());
	}
	private void initStartEndDates() {
	    endDate = new Date();
	    startDate = DateUtil.getDaysBackDate(endDate, getDaysBack());
	}


	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getDaysBack() {
		if (daysBack == null)
			return Integer.valueOf(1);
		return daysBack;
	}

	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
		this.startDate = null;
		this.endDate = null;
	}

	public RedFlagDAO getRedFlagDAO() {
		return redFlagDAO;
	}

	public void setRedFlagDAO(RedFlagDAO redFlagDAO) {
		this.redFlagDAO = redFlagDAO;
	}


}
