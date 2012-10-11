package com.inthinc.pro.sbs.model;

import java.util.ArrayList;
import java.util.List;

import org.postgis.Point;

public class StreetSegment {

	private String linkId;

	private int featureId;

	private String numbers;
	private String address;
	private String zipCode;
	private List<Point> streetSegmentPoints;
	private List<Point> endPoints;
	

	private int category;
	private int speedLimit;
	
	private float opacity = 0.9f;
	
	
	private boolean containsAddress;
	
	public int getFeatureId() {
		return featureId;
	}
	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}
	
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public List<Point> getEndPoints() {
		return endPoints;
	}
	public void setEndPoints(List<Point> endPoints) {
		this.endPoints = endPoints;
	}
	private void makeEndPoints(){
		
		endPoints = new ArrayList<Point>();
		if ((streetSegmentPoints != null) && (streetSegmentPoints.size()>0)){
			
			endPoints.add(streetSegmentPoints.get(0));
			endPoints.add(streetSegmentPoints.get(streetSegmentPoints.size()-1));
		}
	}
	public boolean isContainsAddress() {
		return containsAddress;
	}
	public void setContainsAddress(boolean containsAddress) {
		this.containsAddress = containsAddress;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAddress(String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft,  
    		String fedirp, String st_typ_bef, String fename, String st_nm_suff, String fetype, String fedirs, int inAddress){
		
		List<String> processedAddress = makeAddress(fromAddrRight, toAddrRight, fromAddrLeft, toAddrLeft, 
    		fedirp, st_typ_bef, fename, st_nm_suff, fetype, fedirs, inAddress);
		
		address = processedAddress.get(1);
	}		
	public void setAddress(String fromAddrRight, 
    		String fedirp, String st_typ_bef, String fename, String st_nm_suff, String fetype, String fedirs, int inAddress){
		
        StringBuilder builder = new StringBuilder();
        append(builder, fromAddrRight);
//        append(builder, "*");
        append(builder, " ");
        append(builder, fedirp);
        append(builder, st_typ_bef);
        append(builder, fename);
        append(builder, st_nm_suff);
        append(builder, fetype);
        append(builder, fedirs);
        
        address = builder.toString();
	}	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public List<Point> getStreetSegmentPoints() {
		return streetSegmentPoints;
	}
	public void setStreetSegmentPoints(List<Point> streetSegmentPoints) {
		
		this.streetSegmentPoints = streetSegmentPoints;
		makeEndPoints();
	}
	public void setStreetSegmentPoints(String tigerLine){
        //Get line segment
//         logger.info(tigerLine);
		streetSegmentPoints = new ArrayList<Point>();
        tigerLine = tigerLine.substring("MULTILINESTRING((".length(),
        		 tigerLine.length() - 2);
        String[] points = tigerLine.split(",");
        for (String point1 : points) {

            Double lng1 = Double.parseDouble(point1.substring(0, point1
                    .indexOf(" ")));
            Double lat1 = Double.parseDouble(point1.substring(point1
                    .indexOf(" ")));
            Point point = new Point(lat1,lng1);
            streetSegmentPoints.add(point);
        }
		makeEndPoints();
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
	public void setSpeedLimit(int fromSpeedLimit, int toSpeedLimit, int category){
		
		speedLimit = deduceSpeedLimit(fromSpeedLimit, toSpeedLimit, category);
	}
    private List<String> getNumbers (String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft, int address) {
    	
    	int fromRight = -1, fromLeft = -1, toRight = -1, toLeft = -1;
    	//Get ducks in a row
        if ((fromAddrRight!=null)&& (!fromAddrRight.isEmpty())){
        	try {
        		fromRight = Integer.parseInt(fromAddrRight);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((toAddrRight!=null)&& (!toAddrRight.isEmpty())){
        	try {
        		toRight = Integer.parseInt(toAddrRight);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((fromAddrLeft!=null)&& (!fromAddrLeft.isEmpty())){
        	try {
        		fromLeft = Integer.parseInt(fromAddrLeft);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((toAddrLeft!=null)&& (!toAddrLeft.isEmpty())){
        	try {
        		toLeft = Integer.parseInt(toAddrLeft);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        //If the values are in the wrong order swap them around
        if (toRight < fromRight){
        	//swap them round
        	String temp = toAddrRight;
        	toAddrRight = fromAddrRight;
        	fromAddrRight = temp;
        	
        	int i = toRight;
        	toRight = fromRight;
        	fromRight = i;
        }
        if (toLeft < fromLeft){
        	//swap them round
        	String temp = toAddrLeft;
        	toAddrLeft = fromAddrLeft;
        	fromAddrLeft = temp;
        	
        	int i = toLeft;
        	toLeft = fromLeft;
        	fromLeft = i;
      }
        StringBuilder builder = new StringBuilder();
        if ((fromAddrRight!=null)&& (!fromAddrRight.isEmpty())){
        	
	        append(builder, fromAddrRight);
	        append(builder, "-");
	        if ((toAddrRight!=null)&& (!toAddrRight.isEmpty())){
		        append(builder, toAddrRight);
	        }
	        append(builder, ",");
        }
        if ((fromAddrLeft!=null)&& (!fromAddrLeft.isEmpty())){
        	
	        append(builder, fromAddrLeft);
	        append(builder, "-");
	        if ((toAddrLeft!=null)&& (!toAddrLeft.isEmpty())){
	        	
	        	append(builder, toAddrLeft);
	        }
        }
        List<String> result = new ArrayList<String>();
        result.add(builder.toString().trim());
        containsAddress = false;
        //Check if the address supplied is in the address range
        if ((address == -1) ||((address != -1)&& ((( address >= fromLeft)&&(address <= toLeft)) || ((address >= fromRight)&&(address <= toRight))))){
        	
        	containsAddress = true;
        }
        
        return result;

    }
    private List<String> makeAddress(String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft, 
    		String fedirp, String st_typ_bef, String fename, String st_nm_suff, String fetype, String fedirs, int address){
    		
    		List<String> numbers = getNumbers(fromAddrRight,toAddrRight,fromAddrLeft,toAddrLeft, address);
            StringBuilder builder = new StringBuilder();
            append(builder,numbers.get(0));
            append(builder, "*");
            append(builder, fedirp);
            append(builder, st_typ_bef);
            append(builder, fename);
            append(builder, st_nm_suff);
            append(builder, fetype);
            append(builder, fedirs);
            
            numbers.add(builder.toString().trim());
            return numbers;
        }

    private void append(StringBuilder builder, String str) {
        if (str != null) {
            builder.append(str.trim() + " ");
        }
    }

    private int deduceSpeedLimit(int fromSpeedLimit, int toSpeedLimit, int category){
    	
    	int speedLimit = Math.max(fromSpeedLimit,toSpeedLimit);
    	if (speedLimit == 0){
        	
    		switch(category){
    		case 1:
    			return 75;
    		case 2:
    			return 75;
    		case 3:
    			return 60;
    		case 4:
    			return 50;
    		case 5:
    			return 40;
    		case 6:
    			return 30;
    		case 7:
    			return 20;
    		case 8:
    			return 5;
    		default:
    			return 75;
    		}
        }
    	else return speedLimit;
    }
	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	public boolean isIncluded(){
		
		return opacity > 0.8f;
	}
}
