package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.parsers.ParserConfigurationException;

import com.inthinc.pro.sbs.SBSChangeRequest;
import com.inthinc.pro.sbs.baseDao.TigerDAO;
import com.iwi.teenserver.dao.GenericDataAccess;
import com.iwi.teenserver.model.SpeedLimitChangeRequest;
import com.iwi.teenserver.model.User;

@Path("/sbs")
public class SpeedByStreetService {
	
	private TigerDAO tigerDAO;
	private GenericDataAccess teenServerDAO;
	private int sbsUserId;
	private String sbsUserName;
	
	
	@GET
	@Path("/speedLimit/{lat}/{lng}")
	public String getSpeedLimit(@PathParam("lat")Double latitude,
				@PathParam("lng")Double longitude)
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
	
	@GET
	@Path("/requestSpeedChange/{linkId}/{speedLimit}/{address}/{commment}")
	public String requestSpeedLimitChange(@PathParam("linkId")Integer linkId,@PathParam("speedLimit")Integer speedLimit,@PathParam("address")String address,
			@PathParam("commment")String comment)
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
