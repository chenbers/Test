package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class BreakdownBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(BreakdownBean.class);
	
	private String pieDef;	
	
	//The following five may need to be placed in BaseBean
	private Duration duration = Duration.DAYS;
	private String styleClass30Days = "on";
	private String styleClass3Months = "";
	private String styleClass6Months = "";
	private String styleClass12Months = "";
	
	public String getPieDef() {		
		pieDef = createPieDef();
//		logger.debug("returned string: " + pieDef);
		return pieDef;
	}

	public void setPieDef(String pieDef) {
		this.pieDef = pieDef;
	}
	
	public String createPieDef() {
		StringBuffer sb = new StringBuffer();
		
		//Control parameters
		sb.append(GraphicUtil.getPieControlParameters());
			
		//Pie parameters
		sb.append(GraphicUtil.createFakePieData());		
		
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
