package com.inthinc.pro.backing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.postgis.Point;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.inthinc.pro.sbs.Tiger;
import com.iwi.teenserver.dao.GenericDataAccess;

public class SpeedLimitChangeRequestBean extends BaseBean{

    private static final Logger logger = Logger.getLogger(DriverSeatBeltBean.class);

    private NavigationBean navigation;
    private GenericDataAccess teenServerDAO; //From teenserverDAO
    private List<SBSChangeRequest> changeRequests;
    private JavaMailSenderImpl mailSender;
    private SimpleMailMessage speedLimitChangeRequestReceivedMessage;
    private double lat;
    private double lng;
    private String address;
    private double go;
    private double maplat;
    private double maplng;
    private int mapzoom;
     
	public SpeedLimitChangeRequestBean() {
		super();
		changeRequests = new ArrayList<SBSChangeRequest>();
		maplat = 40.0;
		maplng = -111.0;
		mapzoom = 10;
		lat =360;
		lng = 360;
	}

	public SimpleMailMessage getSpeedLimitChangeRequestReceivedMessage() {
		return speedLimitChangeRequestReceivedMessage;
	}

	public void setSpeedLimitChangeRequestReceivedMessage(
			SimpleMailMessage speedLimitChangeRequestReceivedMessage) {
		this.speedLimitChangeRequestReceivedMessage = speedLimitChangeRequestReceivedMessage;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public GenericDataAccess getTeenServerDAO() {
		return teenServerDAO;
	}

	public void setTeenServerDAO(GenericDataAccess teenServerDAO) {
		this.teenServerDAO = teenServerDAO;
	}

	public NavigationBean getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}

	public List<SBSChangeRequest> getChangeRequests() {
		
		if ((lat < 360)&& (lng < 360)){
			
			try{
				SBSChangeRequest sbscr = Tiger.getCompleteChains(lat, lng);
				changeRequests.add(0,sbscr);
			
//				int i = changeRequests.size();
//				SBSChangeRequest sbscr = new SBSChangeRequest();
//				sbscr.setAddress(address);
//				sbscr.setCategory(i);
//				sbscr.setComment("hello");
//				sbscr.setLinkId(""+i);
//				sbscr.setSpeedLimit(30);
//				List<Point> streetSegment = new ArrayList<Point>();
//				for (int j=0; j<3; j++){
//					
//					Point point = new Point(lat+Math.random()/10,lng+Math.random()/10);
//					streetSegment.add(point);
//				}
//				sbscr.setStreetSegment(streetSegment);
//				sbscr.setZipCode("84121");
//				changeRequests.add(0, sbscr);
				maplat = lat;
				maplng = lng;
//				lat = 360;
//				lng = 360;
	
				return changeRequests;
			}
			catch(SQLException sqle){
				
				logger.debug("addSegment - SQLException: "+sqle.getMessage());
				return null;
			}
			catch(ParserConfigurationException pce){
				
				logger.debug("addSegment - ParserConfigurationException: "+pce.getMessage());
				return null;
			}
		}
		return null;
	}

	public void setChangeRequests(List<SBSChangeRequest> changeRequests) {
		this.changeRequests = changeRequests;
	}

	public void saveRequests(){
		
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getGo() {
		return go;
	}

	public void setGo(double go) {
		this.go = go;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getMaplng() {
		return maplng;
	}

	public void setMaplng(double maplng) {
		this.maplng = maplng;
	}

	public double getMaplat() {
		return maplat;
	}

	public void setMaplat(double maplat) {
		this.maplat = maplat;
	}

	public int getMapzoom() {
		return mapzoom;
	}

	public void setMapzoom(int mapzoom) {
		this.mapzoom = mapzoom;
	}

	public SBSChangeRequest getLatestChangeRequest() {
		
		return changeRequests.size() > 0?changeRequests.get(0):null;
	}

	public void setChangeRequest(SBSChangeRequest changeRequestBean) {
		
	}
}
