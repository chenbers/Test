package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.postgis.Point;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import com.iwi.teenserver.dao.GenericDataAccess;
import com.iwi.teenserver.model.SpeedLimitChangeRequest;
import com.iwi.teenserver.model.User;

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
    private boolean getAll;
    private String deleteMe;
    
	public SpeedLimitChangeRequestBean() {
		super();
		changeRequests = new ArrayList<SBSChangeRequest>();
		maplat = 40.0;
		maplng = -111.0;
		mapzoom = 10;
		lat =360;
		lng = 360;
		getAll = false;
		deleteMe = null;
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

	public synchronized List<SBSChangeRequest> getChangeRequests() {
		
		if ((lat < 360)&& (lng < 360)){
			
//			try{
//				SBSChangeRequest sbscr = Tiger.getCompleteChains(lat, lng);
//				
//				sbscr.setAddress(sbscr.getAddress()+"/"+address);
//				changeRequests.add(0,sbscr);
				
				int i = changeRequests.size();
				SBSChangeRequest sbscr = new SBSChangeRequest();
				sbscr.setAddress(address);
				sbscr.setCategory(i);
				sbscr.setComment("hello");
				sbscr.setLinkId(""+i);
				sbscr.setSpeedLimit(30);
				List<Point> streetSegment = new ArrayList<Point>();
				logger.debug("lat,lng is: "+lat+","+lng);
				streetSegment.add(new Point(lat,lng));
				for (int j=0; j<3; j++){
					double randomLat = lat+Math.random()/10;
					double randomLng = lng+Math.random()/10;
					logger.debug("randomLat,randomLng is: "+randomLat+","+randomLng);
					
					Point point = new Point(randomLat,randomLng);
					streetSegment.add(point);
				}
				sbscr.setStreetSegment(streetSegment);
				sbscr.setZipCode("84121");
				changeRequests.add(0, sbscr);
				maplat = lat;
				maplng = lng;
				lat = 360;
				lng = 360;
	
				return changeRequests;
//			}
//			catch(SQLException sqle){
//				
//				logger.debug("addSegment - SQLException: "+sqle.getMessage());
//				return null;
//			}
//			catch(ParserConfigurationException pce){
//				
//				logger.debug("addSegment - ParserConfigurationException: "+pce.getMessage());
//				return null;
//			}
		}
		else if (getAll){
			
			getAll = false;
			
			return changeRequests;
		}
		else if (deleteMe != null){

			String[] deleteItems = deleteMe.split("\\s");
		    for (int x=0; x<deleteItems.length; x++){
		    		try{
		    			Integer deleteInt = new Integer(deleteItems[x]);
				    	 changeRequests.remove(deleteInt.intValue());
		    		}
		    		catch (NumberFormatException nfe){
		    			logger.debug("delete checked items NumberFormatException on "+deleteItems[x]);
		    		}
		    }
			deleteMe = null;
			return changeRequests;
		}
		return null;
	}

	public void setChangeRequests(List<SBSChangeRequest> changeRequests) {
		this.changeRequests = changeRequests;
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
		logger.debug("getLatestChangeRequest called - size is: "+changeRequests.size());
		return changeRequests.size() > 0?changeRequests.get(0):null;
	}

	public void setChangeRequest(SBSChangeRequest changeRequestBean) {
		
	}
	public String delete(){
		
		Iterator<SBSChangeRequest> it = changeRequests.iterator();
		while(it.hasNext()){
			
			SBSChangeRequest sbscr = it.next();
			
			if (sbscr.isSelected()){
				
				it.remove();
			}
		}
		getAll = true;
		return "go_adminSBSCR";
	}

	public String saveRequests()
	{
		logger.info("saveRequests");
		if (changeRequests != null){
			
			Iterator<SBSChangeRequest> it = changeRequests.iterator();
			
			while (it.hasNext()){
				SBSChangeRequest slb = it.next();
				if (slb.getLinkId()!= null){
					logger.info("saveRequests" +slb.getLinkId());
					saveRequest(slb);
				}
			}
		}
		changeRequests.clear();
		return "go_adminSBSCR";

	}

	public void  saveRequest(SBSChangeRequest speedLimitBean)
	{
		SpeedLimitChangeRequest changeRequest = speedLimitBean.getChangeRequest();
//		logger.debug(getUser().getFirst());
//		logger.debug(changeRequest.getLinkId());
//		if (teenServerDAO == null)logger.debug("dataAccess is null");
//		else logger.debug(dataAccess.getClass());
		
//		teenServerDAO.saveSpeedLimitChangeRequest(new com.iwi.teenserver.model.User(), changeRequest);
//		sendEmailToUser(speedLimitBean);
	}

//	public void sendEmailToUser(SBSChangeRequest speedLimitBean) {
//		
//		try {
//			MimeMessage message = mailSender.createMimeMessage();
//			// use the true flag to indicate you need a multipart message
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			//String emailAddresses = "jacquie_howard@hotmail.com";
//			String emailAddresses =getUser().getEmail();
//			if (emailAddresses == null) return;
//			String email[] = emailAddresses.split(";");
//			helper.setTo(email[0]);
//			logger.debug("sendEmailToUser email address is "+email[0]);
//		
//			SimpleMailMessage justTheText 
//					= new SimpleMailMessage(getSpeedLimitChangeRequestReceivedMessage());
//			String text = justTheText.getText();
//			text = StringUtils.replace(text, "%ADDRESS%",speedLimitBean.getAddress().getAddr1());
//			text = StringUtils.replace(text, "%SPEED%",""+speedLimitBean.getSpeedLimitChange());
//			helper.setText(text,true);
//			helper.setFrom(justTheText.getFrom());
//			helper.setSubject(justTheText.getSubject());
//			
//			mailSender.send(message);
//			
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public String getDeleteMe() {
		return deleteMe;
	}

	public void setDeleteMe(String deleteMe) {
		this.deleteMe = deleteMe;
	}
}
