package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.MpgEntityPkg;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class MpgBean extends BaseDurationBean {

	private static final Logger logger = Logger.getLogger(MpgBean.class);

    private MpgDAO mpgDAO;
    private NavigationBean navigation;
    private List<MpgEntityPkg> mpgEntities = new ArrayList<MpgEntityPkg>();
    
	
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
        List<MpgEntityPkg> entities = null;
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
    
    public List<MpgEntityPkg> getMpgEntities()
    {
        if (mpgEntities.size() == 0)
        {
            // Date range qualifiers
            int numDaysBack = getDuration().getNumberOfDays();
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(endDate, numDaysBack);
            
            List<MpgEntity> tempEntities = mpgDAO.getEntities(this.getNavigation().getGroupID(), startDate, endDate);
    
            // Populate the table
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            for (MpgEntity entity : tempEntities)
            {
                MpgEntityPkg pkg = new MpgEntityPkg();
                pkg.setEntity(entity);
                GroupHierarchy groupHierarchy = getGroupHierarchy();
                GroupLevel groupLevel = groupHierarchy.getGroupLevel(entity.getEntityID());
                if (groupLevel == null)
                {
                    groupLevel = groupHierarchy.getGroupLevel(entity.getGroupID());
                }
                if (groupLevel != null)
                {
                    pkg.setGoTo(contextPath + groupLevel.getUrl() + "?groupID=" + entity.getEntityID());
                    this.mpgEntities.add(pkg);
                }
            }
        }
        return this.mpgEntities;
    }
    
    public void setMpgEntities(List<MpgEntityPkg> mpgEntities)
    {
        this.mpgEntities = mpgEntities;
    }
}
