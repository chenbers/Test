package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.postgis.Point;

import com.iwi.teenserver.model.SpeedLimitChangeRequest;

public class SBSChangeRequest implements EditItem{
	
	private String linkId;
	private String address;
	private String zipCode;
	private List<Point> streetSegment;
	private int category;
	private int speedLimit;
	private int newSpeedLimit;
	private String comment;
	private boolean selected;
	private SpeedLimitChangeRequest changeRequest;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public SBSChangeRequest() {
		super();
		streetSegment = new ArrayList<Point>();
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public List<Point> getStreetSegment() {
		return streetSegment;
	}
	public void setStreetSegment(List<Point> streetSegment) {
		this.streetSegment = streetSegment;
	}
	public void setStreetSegment(String tigerLine){
        //Get line segment
//         logger.info(tigerLine);
		streetSegment = new ArrayList<Point>();
        tigerLine = tigerLine.substring("MULTILINESTRING((".length(),
        		 tigerLine.length() - 2);
        String[] points = tigerLine.split(",");
        for (String point1 : points) {

            Double lng1 = Double.parseDouble(point1.substring(0, point1
                    .indexOf(" ")));
            Double lat1 = Double.parseDouble(point1.substring(point1
                    .indexOf(" ")));
            Point point = new Point(lat1,lng1);
            streetSegment.add(point);
        }
	}
	public int getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
	public int getNewSpeedLimit() {
		return newSpeedLimit;
	}
	public void setNewSpeedLimit(int newSpeedLimit) {
		this.newSpeedLimit = newSpeedLimit;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}	
	public SpeedLimitChangeRequest getChangeRequest() {
		return changeRequest;
	}
	public void setChangeRequest(SpeedLimitChangeRequest changeRequest) {
		this.changeRequest = changeRequest;
	}

}
