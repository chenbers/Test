package com.inthinc.pro.table.model.provider;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RedFlagDAO;
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
	

	public RedFlagPaginationTableDataProvider() {
	    
		logger.info("RedFlagPaginationTableDataProvider");
	}

	@Override
	public List<RedFlag> getItemsByRange(int firstRow, int endRow) {
		
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return redFlagDAO.getRedFlagPage(groupID, getDaysBack(), RedFlagDAO.INCLUDE_FORGIVEN, pageParams);
	}

	@Override
	public int getRowCount() {

		return redFlagDAO.getRedFlagCount(groupID, getDaysBack(), RedFlagDAO.INCLUDE_FORGIVEN, getFilters());
	}


	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getDaysBack() {
		if (daysBack == null)
			return Integer.valueOf(0);
		return daysBack;
	}

	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
	}

	public RedFlagDAO getRedFlagDAO() {
		return redFlagDAO;
	}

	public void setRedFlagDAO(RedFlagDAO redFlagDAO) {
		this.redFlagDAO = redFlagDAO;
	}


}
