package com.inthinc.pro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.service.note.Attribute;
import com.inthinc.pro.service.note.AttributeType;
import com.inthinc.pro.service.note.Note;
import com.inthinc.pro.service.note.NoteType;

@Path("/eventService")
public class NoteService {
	
	private MCMService mcmService;
	
	@GET
	@Path("/createSpeedEvent/{imei}/{vehicleID}/{lat}/{lng}/{currentSpeed}/{speedLimit}")
	public String createSpeedEvent(@PathParam("imei")String imei,
			@PathParam("vehicleID")Integer vehicleID,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit){
		
		Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT,speedLimit);
		Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED,45);
		Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED,speed);
		Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE,0);
		Note note = new Note(NoteType.SPEEDING,new Date(),latitude,longitude,speed,0,
				speedLimitAttribute,averageSpeedAttribute,topSpeedAttribute,distanceAttribute);
		
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(note.getBytes());
		List<Map> mapList = mcmService.note(imei, byteList);
		
		return "0";
	}
	
	@PUT
	@Path("/createBeginTripEvent/{imei}/{vehicleID}/{lat}/{lng}")
	public String createBeginTripEvent(){
		
		return null;
	}
	
	@PUT
	@Path("/createEndTripEventt/{lat}/{lng}")
	public String createEndTripEvent(){
		
		return null;
	}
	
	@PUT
	@Path("/createCurrentLocationEvent/{lat}/{lng}")
	public String createCurrentLocationEvent(){
		
		return null;
	}

	public void setMcmService(MCMService mcmService) {
		this.mcmService = mcmService;
	}

	public MCMService getMcmService() {
		return mcmService;
	}

}
