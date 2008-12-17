package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.postgis.Point;

public class SBSChangeRequest {
	
	private String linkId;
	private String address;
	private String zipCode;
	private List<Point> streetSegment;
	private int category;
	private int speedLimit;
	private int newSpeedLimit;
	private String comment;
	private boolean selected;
	
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

//	String query = "select link_id as ogc_fid,st_nm_pref as fedirp,st_typ_bef,st_nm_base as fename,st_nm_suff,st_typ_aft as fetype ,l_refaddr as fraddl,l_nrefaddr as toaddl,"+
//			"r_refaddr as fraddr,r_nrefaddr as toaddr,l_postcode as zipL,r_postcode as zipR,"+
//			"distance(the_geom,setsrid(GeomFromText('"+point+"',32767),4326)) as dist, "+
//			"astext(the_geom) as tigerline, COALESCE(streets.iwi_speed_cat,"+ 
//			"streets_sif.speed_cat, streets.speed_cat) AS speed_cat, COALESCE(streets.iwi_fr_spd_lim, streets_sif.fr_spd_lim," +
//			"streets.fr_spd_lim) AS fr_spd_lim,COALESCE(streets.iwi_to_spd_lim," +
//			"streets_sif.to_spd_lim, streets.to_spd_lim) AS to_spd_lim "+
//			"from streets LEFT JOIN streets_sif USING (link_id)"+
//			"where the_geom && " +
//			"expand(setsrid(geomFromText('"+point+"',32767),4326),0.002) order by dist limit 1";

}
