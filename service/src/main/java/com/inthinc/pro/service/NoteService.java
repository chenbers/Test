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
import com.inthinc.pro.service.note.Heading;
import com.inthinc.pro.service.note.Note;
import com.inthinc.pro.service.note.NoteType;

@Path("/eventService")
public class NoteService {
	
	private MCMService mcmService;
	
	@GET
	@Path("/createSpeedEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}")
	public String createSpeedEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit){
		
		Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT,speedLimit);
		Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED,45);
		Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED,speed);
		Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE,0);
		Note note = new Note(NoteType.SPEEDING,new Date(),latitude,longitude,speed,0,9,1,
				speedLimitAttribute,averageSpeedAttribute,topSpeedAttribute,distanceAttribute);
		
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(note.getBytes());
		List<Map> mapList = mcmService.note(imei, byteList);
		
		return "0";
	}
	
	@GET
	@Path("/createIgnitionOnEvent/{imei}/{lat}/{lng}/{currentSpeed}")
	public String createBeginTripEvent(
        	@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed){
		
        Note note = new Note(NoteType.IGNITION_ON,new Date(),latitude,longitude,speed,0,9,1);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        return "0";
	}
	
	@GET
	@Path("/createIgnitionOffEvent/{imei}/{lat}/{lng}/{currentSpeed}")
	public String createEndTripEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed){
		
	    Attribute mpgAttribute = new Attribute(AttributeType.ATTR_TYPE_MPG,25);
        Note note = new Note(NoteType.IGNITION_OFF,new Date(),latitude,longitude,speed,0,9,1,mpgAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
		return "0";
	}
	
	@GET
	@Path("/createCurrentLocationEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}")
	public String createCurrentLocationEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit,
			@PathParam("bearing")Integer bearing){
		
	    Integer heading = Heading.valueOf(bearing).getCode();
		Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT,speedLimit);
		Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY,146);
		Note note = new Note(NoteType.LOCATION,new Date(),latitude,longitude,speed,0,9,heading,speedLimitAttribute,boundryAttribute);
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(note.getBytes());
		List<Map> mapList = mcmService.note(imei, byteList);
		
		if(mapList.size() > 0){
		    return mapList.get(0).toString();
		}
		return "0";
	}

	public void setMcmService(MCMService mcmService) {
		this.mcmService = mcmService;
	}

	public MCMService getMcmService() {
		return mcmService;
	}

}
