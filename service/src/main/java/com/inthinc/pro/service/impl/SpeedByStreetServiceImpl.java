package com.inthinc.pro.service.impl;

import javax.xml.parsers.ParserConfigurationException;

import com.inthinc.pro.sbs.SBSChangeRequest;
import com.inthinc.pro.sbs.baseDao.TigerDAO;
import com.inthinc.pro.service.SpeedByStreetService;
import com.iwi.teenserver.dao.GenericDataAccess;
import com.iwi.teenserver.model.SpeedLimitChangeRequest;
import com.iwi.teenserver.model.User;

public class SpeedByStreetServiceImpl implements SpeedByStreetService {
	
	private TigerDAO tigerDAO;
	private GenericDataAccess teenServerDAO;
	private int sbsUserId;
	private String sbsUserName;
	
	public String getSpeedLimit(Double latitude,Double longitude)
    {
		SBSChangeRequest sbsChangeRequest=null;
		try {
			sbsChangeRequest = tigerDAO.getCompleteChains(latitude.doubleValue(), longitude.doubleValue(),-1);
		} catch (ParserConfigurationException e) {
			return "Could Not Be Found";
		}
		if(sbsChangeRequest != null)
			return String.valueOf(sbsChangeRequest.getSpeedLimit()) + "," + sbsChangeRequest.getLinkId();
		else
			return "Could Not Be Found";
    }
	

	public String requestSpeedLimitChange(Integer linkId,Integer speedLimit,String address,
			String comment)
	{
		SpeedLimitChangeRequest sbsChangeRequest = new SpeedLimitChangeRequest();
		sbsChangeRequest.setLinkId(new Integer(linkId));
		sbsChangeRequest.setNewSpeedLimit(speedLimit);
		sbsChangeRequest.setAddress(address);
		sbsChangeRequest.setComment(comment);
		sbsChangeRequest.setStatus(SpeedLimitChangeRequest.STATUS_NEW);
		sbsChangeRequest.setUserID(getSbsUserId());
	
		User user = teenServerDAO.getUserById(getSbsUserId());
		
		try {
			sbsChangeRequest = teenServerDAO.saveSpeedLimitChangeRequest(user, sbsChangeRequest);
			return "0";
		}catch(Exception ex)
		{
			return "1";
		}
	}
	
	public void setTigerDAO(TigerDAO tigerDAO) {
		this.tigerDAO = tigerDAO;
	}

	public TigerDAO getTigerDAO() {
		return tigerDAO;
	}
	
	public int getSbsUserId() {
		return sbsUserId;
	}

	public void setSbsUserId(int sbsUserId) {
		this.sbsUserId = sbsUserId;
	}

	public String getSbsUserName() {
		return sbsUserName;
	}

	public void setSbsUserName(String sbsUserName) {
		this.sbsUserName = sbsUserName;
	}

	public GenericDataAccess getTeenServerDAO() {
		return teenServerDAO;
	}

	public void setTeenServerDAO(GenericDataAccess teenServerDAO) {
		this.teenServerDAO = teenServerDAO;
	}
	
	
	
	




}
