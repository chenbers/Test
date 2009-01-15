package com.inthinc.pro.backing;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.sbs.Tiger;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.validators.EmailValidator;
import com.iwi.teenserver.dao.GenericDataAccess;
import com.iwi.teenserver.model.SpeedLimitChangeRequest;

public class SpeedLimitChangeRequestBean extends BaseBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SpeedLimitChangeRequestBean.class);

    private NavigationBean 			navigation;
    private MessageSource  			messageSource;
    private GenericDataAccess 		teenServerDAO; //From teenserverDAO
    private int 					sbsUserId;
    private String 					sbsUserName;
    private List<SBSChangeRequest> 	changeRequests;
    private JavaMailSenderImpl 		mailSender;
    private SimpleMailMessage 		speedLimitChangeRequestReceivedMessage;
    private double 					lat;
    private double 					lng;
    private String 					address;
    private double 					go;
    private double 					maplat;
    private double 					maplng;
    private int 					mapzoom;
    private String 					emailAddress;
    private String 					caption;
    private String 					message;
    private boolean					requestSent;
    private boolean					success;
    private boolean					emailSent;
    
	public boolean isRequestSent() {
		return requestSent;
	}
	public void setRequestSent(boolean requestSent) {
		this.requestSent = requestSent;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public SpeedLimitChangeRequestBean() {
		super();
	}
	public void init(){
		
		changeRequests = new ArrayList<SBSChangeRequest>();
		maplat = 360;
		maplng = 360;
		mapzoom = 10;
		lat =360;
		lng = 360;
		caption = MessageUtil.getMessageString("sbs_caption_select");
		emailAddress = getUser().getPerson().getEmail();
		message = MessageUtil.getMessageString("sbs_emailIntro");
		requestSent = false;
		success = false;
		emailSent = false;
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
		
		return changeRequests;
	}

	public String addChangeRequest() {
		
		setCaption(MessageUtil.getMessageString("sbs_caption_select"));

		if ((lat < 360)&& (lng < 360)){
			
			try{
				SBSChangeRequest sbscr = Tiger.getCompleteChains(lat, lng, getAddressNumber(address));
				
				if (!duplicateLinkId(sbscr.getLinkId())){
					sbscr.setAddress(this.makeCompositeAddress(address, sbscr.getAddress()));
					changeRequests.add(0,sbscr);
				
//				int i = changeRequests.size();
//				SBSChangeRequest sbscr = new SBSChangeRequest();
//				sbscr.setAddress(address);
//				sbscr.setCategory(i);
//				sbscr.setComment("hello");
//				sbscr.setLinkId(""+i);
//				sbscr.setSpeedLimit(30);
//				List<Point> streetSegment = new ArrayList<Point>();
//				logger.debug("lat,lng is: "+lat+","+lng);
//				streetSegment.add(new Point(lat,lng));
//				for (int j=0; j<3; j++){
//					double randomLat = lat+Math.random()/1000;
//					double randomLng = lng+Math.random()/1000;
//					logger.debug("randomLat,randomLng is: "+randomLat+","+randomLng);
//					
//					Point point = new Point(randomLat,randomLng);
//					streetSegment.add(point);
//				}
//				sbscr.setStreetSegment(streetSegment);
//				sbscr.setZipCode("84121");
//				changeRequests.add(0, sbscr);
					maplat = lat;
					maplng = lng;
					lat = 360;
					lng = 360;
	
					return "";
				}
	
			}
			catch(SQLException sqle){
				
				logger.debug("addSegment - SQLException: "+sqle.getMessage());
				setCaption("Could not access database - please try again later");
				return null;
			}
			catch(ParserConfigurationException pce){
				
				logger.debug("addSegment - ParserConfigurationException: "+pce.getMessage());
				setCaption("Could not interpret latitude and longitude - please try again");
				return null;
			}
		}
			
		return "";
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
		
		setMapCenter();
		return maplng;
	}

	public void setMaplng(double maplng) {
		this.maplng = maplng;
	}

	public double getMaplat() {
		
		setMapCenter();
		return maplat;
	}
	private void setMapCenter(){
		
		if (maplat >= 360){
			
			//use default map center from hierarchy
			LatLng mapCenter = getGroupHierarchy().getTopGroup().getMapCenter();
			maplat = mapCenter.getLat();
			maplng = mapCenter.getLng();
		}		
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
		return "";
	}

	public String deleteAll(){
		
		changeRequests.clear();
		
		return "";
	}
	public String saveRequestsAction()
	{
		logger.info("saveRequests");
		requestSent = true;
		if (changeRequests != null){
			
			Iterator<SBSChangeRequest> it = changeRequests.iterator();
			
			while (it.hasNext()){
				SBSChangeRequest slb = it.next();
				if (slb.getLinkId()!= null){
					logger.info("saveRequests" +slb.getLinkId());
					saveRequest(slb);
				}
				else {
					
					success = false;
				}
			}
		}
		else{
			logger.debug("No change requests submitted");
		}
		return "";

	}

	public void  saveRequest(SBSChangeRequest speedLimitBean)
	{
		SpeedLimitChangeRequest changeRequest = speedLimitBean.getChangeRequest();
		changeRequest.setUserID(sbsUserId);
		logger.debug(changeRequest.getLinkId());
		String emailAddresses =getEmailAddress();
		if ((emailAddress != null) && !emailAddress.isEmpty()){
			
			String email[] = emailAddresses.split(";");
			changeRequest.setEmail(email[0]);
		}
		//Check for valid mandatory fields
		if (speedLimitBean.isGood()){
			
			com.iwi.teenserver.model.User user = teenServerDAO.getUserById(getSbsUserId());
			if (user != null){
				
				teenServerDAO.saveSpeedLimitChangeRequest(user, changeRequest);
				if ((emailAddress != null) && !emailAddress.isEmpty()){
					
					sendEmailToUser(speedLimitBean);
				}
			}
			else {
				logger.debug("saveRequest: User not found.");
				message =MessageUtil.getMessageString("sbs_caption_error");;
			}
				
		}
		else {
			logger.debug("saveRequest: changeRequest not complete.");
			message =MessageUtil.getMessageString("sbs_caption_error");;
		}
	}

	public void sendEmailToUser(SBSChangeRequest speedLimitBean) {
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			//String emailAddresses = "jacquie_howard@hotmail.com";
			String emailAddresses =getEmailAddress();
			if (emailAddresses == null) return;
			String email[] = emailAddresses.split(";");
			helper.setTo(email[0]);

			logger.debug("sendEmailToUser email address is "+email[0]);
		
			SimpleMailMessage justTheText 
					= new SimpleMailMessage(getSpeedLimitChangeRequestReceivedMessage());
			String text = justTheText.getText();
			text = StringUtils.replace(text, "%ADDRESS%",speedLimitBean.getAddress());
			text = StringUtils.replace(text, "%SPEED%",""+speedLimitBean.getNewSpeedLimit());
			helper.setText(text,true);
			helper.setFrom(justTheText.getFrom());
			helper.setSubject(justTheText.getSubject());
			speedLimitBean.setEmail(email[0]);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			// 
			logger.debug("sendEmailToUser email could not be sent "+e.getMessage());
			message =MessageUtil.getMessageString("sbs_caption_noEmail");
					
		}
	}

	public boolean getRenderTable(){
		
		return (changeRequests.size()>0)|| (lat<360);
	}
//	public void RenderTable(boolean requestCount){
//		
//	}

	public int getSbsUserId() {
		return sbsUserId;
	}

	public void setSbsUserId(int sbsUserId) {
		this.sbsUserId = sbsUserId;
	}

	public String getEmailAddress() {
//		return "jacquie_howard@hotmail.com";
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getSbsUserName() {
		return sbsUserName;
	}

	public void setSbsUserName(String sbsUserName) {
		this.sbsUserName = sbsUserName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }
    
    public String resetAction(){
    	    	
    	init();
    	return "";
    }
	public boolean isEmailSent() {
		return emailSent;
	}
	public void setEmailSent(boolean emailSent) {
		this.emailSent = emailSent;
	}
	private String makeCompositeAddress(String gAddress, String tAddress){
		
		if ((gAddress == null) || (gAddress.isEmpty())){

			if ((tAddress == null) || (tAddress.isEmpty())){
				
				return null;
			}
			else {
				return tAddress;
			}
		}
		if ((tAddress == null) || (tAddress.isEmpty())){

			return gAddress;
		}
		StringBuffer compositeAddress = new StringBuffer();
		
		String gTokens[]= gAddress.split(",");
		String gStreet[]=gTokens[0].split(" ");

		int street = tAddress.indexOf('*');
		
		compositeAddress.append(tAddress.substring(0,street));//Copy numbers over
		String streetName =tAddress.substring(street+1).trim();
		String streetTokens[] = streetName.split(" ");
		
		int j;
		for(j=0;j<gStreet.length-1;j++){
			
			if (streetTokens[0].equalsIgnoreCase(gStreet[j])){
				
				for (int i=j; i<gStreet.length; i++){
					
					compositeAddress.append(gStreet[i]);
					compositeAddress.append(" ");
				}
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length()-1, ",");
				for (int i=1; i<gTokens.length; i++){
					
					compositeAddress.append(gTokens[i]);
					compositeAddress.append(",");
				}
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length()-1, "");
				break;
			}
		}
		if (j==gStreet.length-1){
			
			//Couldn't find street name in google address
			//So take whole of Google address and add to numbers and address from Tiger
			compositeAddress.append(streetName);
			compositeAddress.append(",");
			
			if (gTokens.length<3){
				
				compositeAddress.append(gAddress);
			}
			else {
				
				for (int i=gTokens.length-3; i<gTokens.length; i++){
					
					compositeAddress.append(gTokens[i]);
					compositeAddress.append(",");
				}
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length()-1, "");
			}
		}
		return compositeAddress.toString();
	}
	private int getAddressNumber(String address){
		int number = -1;
		try{
			String tokens[]= address.split(" ");

			number = Integer.parseInt(tokens[0]);
		}
		catch (NumberFormatException nfe){
			// there was no number
			
		}
		return number;
	}
    public void validateEmail(FacesContext context, UIComponent component, Object value)
    {
        String valueStr = (String) value;
        if (valueStr != null && valueStr.length() > 0)
        {
            new EmailValidator().validate(context, component, value);
        }
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private boolean duplicateLinkId(String linkId){
		
		for(SBSChangeRequest sbscr:changeRequests){
			
			if (sbscr.getLinkId().equals(linkId)) return true;
		}
		return false;
	}
}
