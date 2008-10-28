package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class FuelBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(FuelBean.class);
	
	private String barDef;	
	
	//The following five may need to be placed in BaseBean
	private Duration duration = Duration.DAYS;
	private String styleClass30Days = "on";
	private String styleClass3Months = "";
	private String styleClass6Months = "";
	private String styleClass12Months = "";
	
	public String getBarDef() {		
		barDef = createBarDef();
//		logger.debug("returned string: " + barDef);
		return barDef;
	}

	public void setBarDef(String barDef) {
		this.barDef = barDef;
	}
	
	public String createBarDef() {
		StringBuffer sb = new StringBuffer();
		
		//Control parameters
		sb.append(GraphicUtil.getBarControlParameters());
			
		//Bar parameters
		//MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
		//THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU 
		//PAD THE FRONT WITH ZEROES
		sb.append(GraphicUtil.createFakeBarData());		
		
		sb.append("</chart>");
		
		return sb.toString();
	}
	

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getStyleClass30Days() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.DAYS.toString()) ) {
			styleClass30Days = "on";
		}
	
		return styleClass30Days;
	}

	public void setStyleClass30Days(String styleClass30Days) {
		this.styleClass30Days = styleClass30Days;
	}

	public String getStyleClass3Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			styleClass3Months = "on";
		}
	
		return styleClass3Months;
	}

	public void setStyleClass3Months(String styleClass3Months) {
		this.styleClass3Months = styleClass3Months;
	}

	public String getStyleClass6Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			styleClass6Months = "on";
		}
	
		return styleClass6Months;
	}

	public void setStyleClass6Months(String styleClass6Months) {
		this.styleClass6Months = styleClass6Months;
	}

	public String getStyleClass12Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			styleClass12Months = "on";
		}
	
		return styleClass12Months;
	}

	public void setStyleClass12Months(String styleClass12Months) {
		this.styleClass12Months = styleClass12Months;
	}
	
	private void reset() {
		this.styleClass30Days = "";
		this.styleClass3Months = "";
		this.styleClass6Months = "";
		this.styleClass12Months = "";
	}

}
