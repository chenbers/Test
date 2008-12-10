package com.inthinc.pro.backing;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseDurationBean
{

    private static final Logger logger = Logger.getLogger(TrendBean.class);

    private ScoreDAO scoreDAO;
    private NavigationBean navigation;

    private String lineDef = new String();

    private List<ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
    
    private Integer numRowsPerPg = 1;
    private Integer maxCount = 0;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String countString = null;
    private Integer tmpGroupID = null;

    public TrendBean()
    {
        super();  
//        logger.debug("constructor"); 
        
        //Check if the scroller is submitting a form, not
        //  just initial load.  If so, grab the groupID associated
        //  to this page.
        Map m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator imap = m.entrySet().iterator();
        while (imap.hasNext()) {
           Map.Entry entry = (Map.Entry) imap.next();
           String key = (String) entry.getKey();
           String value = (String) entry.getValue();
//           logger.debug("key " + key + " value " + value);
           
           //Group ID
           if (         key.equalsIgnoreCase("trendtable:hiddengroupid") ) {              
               tmpGroupID = new Integer(value);              
           }
        }
    }

    public String getLineDef()
    {      
//        logger.debug("lineDef");
        lineDef = createLineDef();
        return lineDef;
    }

    public void setLineDef(String lineDef)
    {
        this.lineDef = lineDef;
    }

    private String createLineDef()
    {
//        logger.debug("createLineDef");
        StringBuffer sb = new StringBuffer();
        lineDef = new String();

        // Control parameters
        sb.append(GraphicUtil.getXYControlParameters());

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<ScoreableEntity> s = null;
        s = getScores();
        this.maxCount = s.size();        

        // X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(navigation.getDuration()));
        sb.append("</categories>");

        // Loop over returned set of group ids, controlled by scroller
        List<ScoreableEntity> ss = null;
        ColorSelectorStandard cs = new ColorSelectorStandard();
        
        for (int i = this.start; i <= this.end; i++)
        {
            if (s.size() < i)
                continue;
            
            ScoreableEntity se = s.get(i-1);
            // Fetch to get children's observations
            ss = scoreDAO.getScores(se.getEntityID(), navigation.getDuration(), ScoreType.SCORE_OVERALL_TIME);

            // Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append(cs.getEntityColorKey(i-1).substring(1));
//            sb.append((GraphicUtil.entityColorKey.get(i)).substring(1));
            sb.append("\'>");

            // Not a full range, pad w/ zero
            int holes = 0;
            if (navigation.getDuration() == Duration.DAYS)
            {
                holes = navigation.getDuration().getNumberOfDays() - ss.size();            
            }
            else
            {
                holes = GraphicUtil.convertToMonths(navigation.getDuration()) - ss.size();
            }
            for (int k = 0; k < holes; k++)
            {
                sb.append("<set value=\'0.0\'/>");
            }

            ScoreableEntity sss = null;
            for (int j = 0; j < ss.size(); j++)
            {
                sss = ss.get(j);

                sb.append("<set value=\'");
                Float score = new Float(sss.getScore() / 10.0);
                sb.append(score.toString()).substring(0, 3);
                sb.append("'/>");
            }
            sb.append("</dataset>");
        }

        sb.append("</chart>");

        return sb.toString();
    }

    public List<ScoreableEntityPkg> getScoreableEntities()
    {   
//        logger.debug("getscoreableentities");
        createScoreableEntities();
        return scoreableEntities;
    }
    
    public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities)
    {
        this.scoreableEntities = scoreableEntities;
    }    

    public List<ScoreableEntityPkg> createScoreableEntities()
    {
//        logger.debug("createscoreableentities");
        if ( scoreableEntities != null ) {
            logger.debug("scoreableentities size " + scoreableEntities.size());
        }        
        this.scoreableEntities = new ArrayList<ScoreableEntityPkg>();
        List<ScoreableEntity> s = null;
        s = getScores();

        // Populate the table
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        ScoreBox sb = new ScoreBox(0, ScoreBoxSizes.SMALL);
        int cnt = 0;                
        ColorSelectorStandard cs = new ColorSelectorStandard();
        for (ScoreableEntity score : s)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(cs.getEntityColorKey(cnt++));
//            se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
            if (score.getEntityType().equals(EntityType.ENTITY_GROUP))
            {
                se.setGoTo(contextPath + getGroupHierarchy().getGroupLevel(score.getEntityID()).getUrl() + "?groupID=" + score.getEntityID());
            }
            scoreableEntities.add(se);
            score = null;
        }
        this.maxCount = this.scoreableEntities.size();        
        return this.scoreableEntities;
    }
    
    private List<ScoreableEntity> getScores() {
        List<ScoreableEntity> s = null;
        try
        {
            s = scoreDAO.getScores(this.navigation.getGroupID(), navigation.getDuration(), ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        
        return s;
    }
        
    public void scrollerListener(DataScrollerEvent se)     
    {  
        this.start = (se.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (se.getPage())*this.numRowsPerPg;
        
        //Partial page
        if ( this.end > this.scoreableEntities.size() ) {
            this.end = this.scoreableEntities.size();
        }

        navigation.setStart(this.start);
        navigation.setEnd(this.end);
        
//        logger.debug("from scroller, start " + this.start + " end "
//                + this.end);
    } 

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO graphicDAO)
    {
        this.scoreDAO = graphicDAO;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
//        logger.debug("setnavigation");
        this.navigation = navigation;
        
        if ( this.navigation != null ) {            
            if ( this.navigation.getDuration() == null ) {
                this.navigation.setDuration(Duration.DAYS);
            }
            if ( this.navigation.getStart() != 0 ) {              
                this.start = this.navigation.getStart();
            }
            if ( this.navigation.getEnd() != 0 ) {              
                this.end = this.navigation.getEnd();
            }
        }
                
        if ( this.tmpGroupID != null ) {
            navigation.setGroupID(tmpGroupID);
            
            //Reset the groupID from the datascroller hidden             
            List<ScoreableEntity> s = null;
            s = getScores();
            this.maxCount = s.size();
            
            //Reset the count parameters
            this.start = 1;
            this.end = this.numRowsPerPg;
            
            //Partial page
            if ( this.end > maxCount ) {
                this.end = maxCount;
            }
            navigation.setStart(this.start);
            navigation.setEnd(this.end);                       
        }
//        logger.debug("final stats " + 
//                " start " + this.navigation.getStart() + 
//                " end " + this.navigation.getEnd());
    }

    public Integer getMaxCount()
    {
//        logger.debug("getmaxcount");  
        return this.maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getStart()
    {   
//        logger.debug("getstart");
        return start;
    }

    public void setStart(Integer start)
    {        
        this.start = start;
    }

    public Integer getEnd()
    {
//        logger.debug("getend");
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public String getCountString()
    {
//        logger.debug("getcountstring");
        countString = "Showing " + this.start + " to " + 
            this.end + " of " + this.maxCount + " records";
        return countString;
    }

    public void setCountString(String countString)
    {
        this.countString = countString;
    }

    public Integer getNumRowsPerPg()
    {
//        logger.debug("getnumrowsperpg");
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

}
