package com.inthinc.pro.backing;

import java.text.NumberFormat;
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
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.model.LineGraphData;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseBean
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
    private Integer tmpGroupID = null;
    
    private ReportRenderer reportRenderer;

    public TrendBean()
    {
        super();  
        
        //Check if the scroller is submitting a form, not
        //  just initial load.  If so, grab the groupID associated
        //  to this page.
        Map m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator imap = m.entrySet().iterator();
        while (imap.hasNext()) {
           Map.Entry entry = (Map.Entry) imap.next();
           String key = (String) entry.getKey();
           String value = (String) entry.getValue();
           
           // groupID, can get going backwards from the hidden in the
           //   scroller or backward from the hidden in the ajax
           if (         key.equalsIgnoreCase("trendtable:hiddengroupid") ) {              
               tmpGroupID = new Integer(value);              
           }
           if (         key.equalsIgnoreCase("trend_form:dateLinksWithGrpId") ) {
               tmpGroupID = new Integer(value);
           }
        }
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

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<ScoreableEntity> s = null;
        s = getScores();
        
        // Adjust the count values
        this.maxCount = s.size(); 
//        this.end = this.numRowsPerPg;
//        if ( this.maxCount < this.end ) {
//            this.end = this.maxCount;
//        }

        // X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(navigation.getDuration()));
        sb.append("</categories>");

        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(
                this.navigation.getGroupID(), navigation.getDuration());;
        ColorSelectorStandard cs = new ColorSelectorStandard();
        
        for (int i = this.start; i <= this.end; i++)
        {
            if (s.size() < i)
                continue;
            
            ScoreableEntity se = s.get(i-1);
            // Fetch to get children's observations
            List<ScoreableEntity> ss = groupTrendMap.get(se.getEntityID());

            // Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append(cs.getEntityColorKey(i-1).substring(1));
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
                Float score = new Float((sss.getScore()==null) ? 0 : sss.getScore() / 10.0);
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
            if (score.getEntityType().equals(EntityType.ENTITY_GROUP))
            {
                // TODO: if getGroupHierarchy().getGroupLevel(score.getEntityID()) returns null 
                // this should an error -- someone trying to access a group they shouldn't
                String url = "";
                if (getGroupHierarchy().getGroupLevel(score.getEntityID()) != null)
                    url = getGroupHierarchy().getGroupLevel(score.getEntityID()).getUrl();
                se.setGoTo(contextPath + url + "?groupID=" + score.getEntityID());
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
                  
        // existing navigation
        if ( this.navigation.getDuration() == null ) {
            this.navigation.setDuration(Duration.DAYS);
        }
        if ( this.navigation.getStart() != 0 ) {              
            this.start = this.navigation.getStart();
        } 
        if ( this.navigation.getEnd() != 0 ) {              
            this.end = this.navigation.getEnd();
        } 
         
        // tmpGroupID implies back on the same page
        if ( this.tmpGroupID != null ) {
            this.navigation.setGroupID(tmpGroupID);
            
            List<ScoreableEntity> s = null;
            s = getScores();
            this.maxCount = s.size();                
            
        // no temp, coming from somewhere, breadcrumb, etc.
        //  we will re-initialize...
        } else {            
            List<ScoreableEntity> s = null;
            s = getScores();
            this.maxCount = s.size();
            this.start = 1;
            this.end = this.numRowsPerPg;
            if ( this.end > this.maxCount ) {
                this.end = this.maxCount;
            }
        }
                
        
    }

    public Integer getMaxCount()
    {
        return this.maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getStart()
    {   
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
    
    public String exportToPDF()
    {
        List<LineGraphData> lineGraphDataList = new ArrayList<LineGraphData>();
        List<ScoreableEntityPkg> scoreableEntityDataSet = createScoreableEntities();
        List<ScoreableEntity> s = null;
        s = getScores();
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(
                this.navigation.getGroupID(), navigation.getDuration());
        
        
        
        for(int i = 0;i < groupTrendMap.size(); i++)
        {
            ScoreableEntity se = s.get(i);
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());
            
            // Not a full range, pad w/ zero
            int holes = 0;
            if (navigation.getDuration() == Duration.DAYS)
            {
                holes = navigation.getDuration().getNumberOfDays() - scoreableEntityList.size();            
            }
            else
            {
                holes = GraphicUtil.convertToMonths(navigation.getDuration()) - scoreableEntityList.size();
            }
            int index = 0;
            for (int k = 0; k < holes; k++)
            {
                lineGraphDataList.add(new LineGraphData(se.getEntityID().toString(),index++,0F,se.getIdentifier()));
            }
            for(ScoreableEntity scoreableEntity:scoreableEntityList)
            {
               Float score = new Float((scoreableEntity.getScore()==null) ? 0 : scoreableEntity.getScore() / 10.0);
               lineGraphDataList.add(new LineGraphData(scoreableEntity.getEntityID().toString(),index++,score,se.getIdentifier()));
            }
            
        }
        
        reportRenderer.exportTrendReportToPDF(lineGraphDataList, scoreableEntityDataSet);
        return null;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

}
