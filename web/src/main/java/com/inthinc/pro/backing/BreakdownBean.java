package com.inthinc.pro.backing;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;

public class BreakdownBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(BreakdownBean.class);
	
	private String pieDef;	
	
	public String getPieDef() {		
		pieDef = createPieDef();
		logger.debug("returned string: " + pieDef);
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

}
