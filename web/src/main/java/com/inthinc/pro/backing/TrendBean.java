package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDatascroller;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseDurationBean
{

    private static final Logger logger = Logger.getLogger(TrendBean.class);

    private ScoreDAO scoreDAO;
    private NavigationBean navigation;

    private String lineDef = new String();

    private List<ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
    
    private Integer numRowsPerPg = 2;
    private Integer maxCount = 0;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String countString = null;

    public TrendBean()
    {
        super();        
    }

    public String getLineDef()
    {       
        lineDef = createLineDef();
        return lineDef;
    }

    public void setLineDef(String lineDef)
    {
        this.lineDef = lineDef;
    }

    private String createLineDef()
    {
        StringBuffer sb = new StringBuffer();
        lineDef = new String();

        // Control parameters
        sb.append(GraphicUtil.getXYControlParameters());

        // Date range qualifiers
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, navigation.getDuration().getNumberOfDays());

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<ScoreableEntity> s = null;
        try
        {
            // TODO: This is not correct. getUser().getGroupID() needs to be changed to the current group in the navigation         
            s = scoreDAO.getScores(this.navigation.getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }

        // X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(navigation.getDuration()));
        sb.append("</categories>");

        // Loop over returned set of group ids, controlled by scroller
        List<ScoreableEntity> ss = null;
        for ( int i = this.start; i <= this.end; i++ )             
        {
            ScoreableEntity se = s.get(i-1);
            // Fetch to get children's observations
            ss = scoreDAO.getScores(se.getEntityID(), startDate, endDate, ScoreType.SCORE_OVERALL_TIME);

            // Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append((GraphicUtil.entityColorKey.get(i-1)).substring(1));
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
        createScoreableEntities();
        return scoreableEntities;
    }
    
    public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities)
    {
        this.scoreableEntities = scoreableEntities;
    }    

    public List<ScoreableEntityPkg> createScoreableEntities()
    {
        List<ScoreableEntity> s = null;
        this.scoreableEntities = new ArrayList<ScoreableEntityPkg>();
        try
        {
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(endDate, navigation.getDuration().getNumberOfDays());
            s = scoreDAO.getScores(this.navigation.getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }

        // Populate the table
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        ScoreBox sb = new ScoreBox(0, ScoreBoxSizes.SMALL);
        int cnt = 0;                
        for (ScoreableEntity score : s)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
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
    }

    public Integer getMaxCount()
    {
        this.maxCount = this.scoreableEntities.size();     
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getStart()
    {   
        createScoreableEntities();
        return start;
    }

    public void setStart(Integer start)
    {        
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public String getCountString()
    {
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
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

}
