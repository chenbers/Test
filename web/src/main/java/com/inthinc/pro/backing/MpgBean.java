package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.util.GraphicUtil;

public class MpgBean extends BaseDurationBean {

	private static final Logger logger = Logger.getLogger(MpgBean.class);

    private MpgDAO mpgDAO;
    private NavigationBean navigation;
    private List<MpgEntity> mpgEntities = new ArrayList<MpgEntity>();
    
	
	private String barDef;	

	
	public String getBarDef() {
	    if (barDef == null)
	    {
	        barDef = createBarDef();
	    }
		return barDef;
	}

	public void setBarDef(String barDef) {
		this.barDef = barDef;
	}
	
	public String createBarDef() {
		StringBuffer sb = new StringBuffer();
		
		//Control parameters
		sb.append(GraphicUtil.getBarControlParameters());

        // get the data
        List<MpgEntity> entities = null;
        try
        {
            // TODO: This is not correct. getUser().getGroupID() needs to be changed to the current group in the navigation
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            entities = getMpgEntities();
            sb.append(GraphicUtil.createMpgXML(entities)); 
        }
        catch (Exception e)
        {
            logger.error("mpgDAO error", e);
        }
			
		//Bar parameters
		//MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
		//THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU 
		//PAD THE FRONT WITH ZEROES
		//sb.append(GraphicUtil.createFakeBarData());		
		
		sb.append("</chart>");
		
		return sb.toString();
	}

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }
    
    public List<MpgEntity> getMpgEntities()
    {
        // Date range qualifiers
        int numDaysBack = getDuration().getNumberOfDays();
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, numDaysBack);
        
        return mpgDAO.getEntities(this.getNavigation().getGroupID(), startDate, endDate);
    }
    
    public void setMpgEntities(List<MpgEntity> mpgEntities)
    {
        this.mpgEntities = mpgEntities;
    }
}
