package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.ParserConfigurationException;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.sbs.baseDao.TigerDAO;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.validators.EmailValidator;
import com.iwi.teenserver.dao.GenericDataAccess;
import com.iwi.teenserver.model.SpeedLimitChangeRequest;

@KeepAlive
public class SpeedLimitChangeRequestBean extends BaseBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SpeedLimitChangeRequestBean.class);

    private NavigationBean 			navigation;
    private GenericDataAccess 		teenServerDAO; //From teenserverDAO
    private TigerDAO				tigerDao;
	private int 					sbsUserId;
    private String 					sbsUserName;
    private List<SBSChangeRequest> 	changeRequests;
    private JavaMailSenderImpl 		mailSender;
    private double 					lat;
    private double 					lng;
    private String 					address;
    private double 					go;
    private double 					maplat;
    private double 					maplng;
    private int 					mapzoom;
    private String 					caption;
    private String 					message;
    private boolean					requestSent;
    private boolean					success;
    private boolean					emailSent;
    
    private double  eventLat;
    private double  eventLng;
    private int     limit;
    
    private static final int outOfRangeLatLngDummyValue = 360;
    
    public TigerDAO getTigerDao() {
		return tigerDao;
	}
	public void setTigerDao(TigerDAO tigerDao) {
		this.tigerDao = tigerDao;
	}
	
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
		maplat = outOfRangeLatLngDummyValue;
		maplng = outOfRangeLatLngDummyValue;
		mapzoom = 10;
		lat =outOfRangeLatLngDummyValue;
		lng = outOfRangeLatLngDummyValue;
		caption = MessageUtil.getMessageString("sbs_caption_select");
		message = MessageUtil.getMessageString("sbs_emailIntro");
		requestSent = false;
		success = false;
		emailSent = false;
		
		eventLat = outOfRangeLatLngDummyValue;
		eventLng = outOfRangeLatLngDummyValue;
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
	
	public void addChangeRequest() {
		
		setCaption(MessageUtil.getMessageString("sbs_caption_select"));

		if ((lat < outOfRangeLatLngDummyValue)&& (lng < outOfRangeLatLngDummyValue)){
			
			try{
				List<SBSChangeRequest> sbscrList = tigerDao.getCompleteChains(lat, lng, getAddressNumber(address), limit);
				
				if ((sbscrList ==  null)||(sbscrList.isEmpty())){
					
					setCaption(MessageUtil.getMessageString("sbs_noNearbyRoad"));
					return;
				}
				int addAt = 0;
				for(SBSChangeRequest sbscr:sbscrList){
				    
    				if (!duplicateLinkId(sbscr.getLinkId())){
    					sbscr.setAddress(this.makeCompositeAddress(address, sbscr.getAddress()));
    					changeRequests.add(addAt++,sbscr);
    					
     				}
				}
                centerMap();
                
			}
			catch(ParserConfigurationException pce){
				
				logger.debug("addSegment - ParserConfigurationException: "+pce.getMessage());
				setCaption(MessageUtil.getMessageString("sbs_badLatLng"));
				return;
			}
		}
			
		return;
	}
	
	private void centerMap(){
		
		maplat = lat;
		maplng = lng;
//		lat = outOfRangeLatLngDummyValue;
//		lng = outOfRangeLatLngDummyValue;

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
		
		if (maplat >= outOfRangeLatLngDummyValue){
			
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
		
        String[] emails = getEmailAddress().split(",");
        List<String> emailList = Arrays.asList(emails);
		if (emailList.size() > 0){
			
			changeRequest.setEmail(emailList.get(0));
		}
		//Check for valid mandatory fields
		if (speedLimitBean.isGood()){
			
//			com.iwi.teenserver.model.User user = teenServerDAO.getUserById(getSbsUserId());
//			if (user != null){
				com.iwi.teenserver.model.User user = new com.iwi.teenserver.model.User();
				user.setId(getSbsUserId());
				teenServerDAO.saveSpeedLimitChangeRequest(user, changeRequest);
					
				sendEmailToUser(speedLimitBean);
//			}
//			else {
//				logger.debug("saveRequest: User not found.");
//				message =MessageUtil.getMessageString("sbs_caption_error");
//			}
				
		}
		else {
			logger.debug("saveRequest: changeRequest not complete.");
			message =MessageUtil.getMessageString("sbs_caption_error");
		}
	}

	public void sendEmailToUser(SBSChangeRequest speedLimitBean) {
		
		try {
			MimeMessage emailText = mailSender.createMimeMessage();
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(emailText, true);
			//String emailAddresses = "jacquie_howard@hotmail.com";

	        String[] emails = getEmailAddress().split(",");
	        List<String> emailList = Arrays.asList(emails);
			if (emailList.size() > 0){
				helper.setTo(emailList.get(0));
	
				logger.debug("sendEmailToUser email address is "+emailList.get(0));
				
//				String mphText = MessageUtil.getMessageString(MeasurementType.ENGLISH.toString() + "_mph");
				
				Integer newSpeed = speedLimitBean.getNewSpeedLimit();
//				if(getMeasurementType().equals(MeasurementType.METRIC))
//				{
//				    mphText = MessageUtil.getMessageString(MeasurementType.METRIC.toString() + "_mph");
//				    newSpeed = MeasurementConversionUtil.fromMPHtoKPH(newSpeed).intValue();
//				}
				String text = MessageUtil.formatMessageString("sbs_emailText",newSpeed,speedLimitBean.getAddress());
				
				helper.setText(text,true);
				helper.setFrom(MessageUtil.getMessageString("sbs_email_from"));
				helper.setSubject(MessageUtil.getMessageString("sbs_email_subject"));
				speedLimitBean.setEmail(emailList.get(0));
				
				mailSender.send(emailText);
				message =MessageUtil.getMessageString("sbs_caption_queued");
			}
				
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
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length(), ",");
				for (int i=1; i<gTokens.length; i++){
					
					compositeAddress.append(gTokens[i]);
					compositeAddress.append(",");
				}
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length(), "");
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
				compositeAddress.replace(compositeAddress.length()-1, compositeAddress.length(), "");
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
    public double getEventLat() {
        return eventLat;
    }
    public void setEventLat(double eventLat) {
        this.eventLat = eventLat;
    }
    public double getEventLng() {
        return eventLng;
    }
    public void setEventLng(double eventLng) {
        this.eventLng = eventLng;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
