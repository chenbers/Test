package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.listener.DurationChangeListener;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends CustomSortBean<ScoreableEntityPkg> implements DurationChangeListener
{

    private static final Logger logger = Logger.getLogger(TrendBean.class);

    private ScoreDAO scoreDAO;
    private NavigationBean navigation;

    private String lineDef = new String();

    private List<ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
    
    private Integer numRowsPerPg = 5;
    private Integer maxCount = 0;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String countString = null;
    private Integer tmpGroupID = null;
    
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    private Boolean animateChartData = Boolean.TRUE;

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
        
        setSortColumn("se.identifier");
    }
    
    public void init()
    {
        createScoreableEntities();
        navigation.getDurationBean().addDurationChangeListener(this);
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
    
    @Override
    public Comparator<ScoreableEntityPkg> createComparator()
    {
        Comparator<ScoreableEntityPkg> comparator = null;
        if(getSortColumn().equals("se.identifier"))
        {
            comparator = new Comparator<ScoreableEntityPkg>()
            {
            
                @Override
                public int compare(ScoreableEntityPkg se1, ScoreableEntityPkg se2)
                {
                    return se1.getSe().getIdentifier().compareTo(se2.getSe().getIdentifier());
                }
            };
        }
        else
        {
            comparator = new Comparator<ScoreableEntityPkg>()
            {
            
                @Override
                public int compare(ScoreableEntityPkg se1, ScoreableEntityPkg se2)
                {
                    return se1.getSe().getScore().compareTo(se2.getSe().getScore());
                }
            };
        }
        
        return comparator;
    }
    
    @Override
    public List<ScoreableEntityPkg> getItems()
    {
        return getScoreableEntities();
    }

    private String createLineDef()
    {
        StringBuffer sb = new StringBuffer();
        lineDef = new String();

        // Control parameters
        sb.append(GraphicUtil.getXYControlParameters(animateChartData));

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<ScoreableEntityPkg> s = getScoreableEntities();
        
        // Adjust the count values
        this.maxCount = s.size(); 

        // X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(navigation.getDurationBean().getDuration()));
        sb.append("</categories>");

        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(
                this.navigation.getGroupID(), navigation.getDurationBean().getDuration());;
        ColorSelectorStandard cs = new ColorSelectorStandard();
        
        for (int i = this.start; i <= this.end; i++)
        {
            if (s.size() < i)
                continue;
            
            ScoreableEntityPkg se = s.get(i-1);
            // Fetch to get children's observations
            List<ScoreableEntity> ss = groupTrendMap.get(se.getSe().getEntityID());

            // Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append(se.getColorKey());
            sb.append("\'>");

            // Not a full range, pad w/ zero
            int holes = 0;
            if (navigation.getDurationBean().getDuration() == Duration.DAYS)
            {
                holes = navigation.getDurationBean().getDuration().getNumberOfDays() - ss.size();            
            }
            else
            {
                holes = GraphicUtil.convertToMonths(navigation.getDurationBean().getDuration()) - ss.size();
            }
            for (int k = 0; k < holes; k++)
            {
                sb.append("<set value=\'0.0\' alpha=\'0\'/>");
            }

            ScoreableEntity sss = null;
            for (int j = 0; j < ss.size(); j++)
            {
                sss = ss.get(j);

                sb.append("<set value=\'");
                Float score = new Float((sss.getScore()==null || sss.getScore() < 0) ? 5 : sss.getScore() / 10.0);
                sb.append(score.toString()).substring(0, 3);
                sb.append("\'");
                if(sss.getScore() == null || sss.getScore() < 0)
                {
                    sb.append("alpha=\'0\' ");
                }
                sb.append("/>");
            }
            sb.append("</dataset>");
        }

        sb.append("</chart>");

        return sb.toString();
    }

    public List<ScoreableEntityPkg> getScoreableEntities()
    {   
        return scoreableEntities;
    }
    
    public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities)
    {
        this.scoreableEntities = scoreableEntities;
    }    

    public List<ScoreableEntityPkg> createScoreableEntities()
    {
        if (scoreableEntities != null && !scoreableEntities.isEmpty()) {
            logger.debug("scoreableentities size " + scoreableEntities.size());
        }        
        this.scoreableEntities = new ArrayList<ScoreableEntityPkg>();
        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
        s = getScores();
        
       
        
        Collections.sort(s, new Comparator<ScoreableEntity>() {
            public int compare(ScoreableEntity se1, ScoreableEntity se2) {
                if(se1 != null && se2 != null)
                    return se1.getIdentifier().compareToIgnoreCase(se2.getIdentifier());
                else
                    return -1;

               
            }            
        });  

        // Populate the table
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        int cnt = 0;                
        ColorSelectorStandard cs = new ColorSelectorStandard();
        for (ScoreableEntity score : s)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            se.setSe(score);
            se.setStyle(ScoreBox.GetStyleFromScore(score.getScore(), ScoreBoxSizes.SMALL));
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
        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
        try
        {
            s = scoreDAO.getScores(this.navigation.getGroupID(), navigation.getDurationBean().getDuration(), ScoreType.SCORE_OVERALL);
            if(s == null)
            {
                s = Collections.emptyList();
            }
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
        logger.debug("setting navigation");
        this.navigation = navigation;
                  
        // existing navigation
        if ( this.navigation.getDurationBean().getDuration() == null ) {
            this.navigation.getDurationBean().setDuration(Duration.DAYS);
        }
        if ( this.navigation.getStart() != 0 ) {              
            this.start = this.navigation.getStart();
        } 
        if ( this.navigation.getEnd() != 0 ) {              
            this.end = this.navigation.getEnd();
        } 
        if ( this.navigation.getNumRowsPerPg() != null ) {
            int local = (new Integer(this.navigation.getNumRowsPerPg())).intValue();
            this.numRowsPerPg = local;
        }
//        if ( this.navigation.getNumRowsPerPg() != 0 ) {
//            this.numRowsPerPg = this.navigation.getNumRowsPerPg();
//        }        
         
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
            if (s != null)
            {
                maxCount = s.size();
            }
            else
            {
                maxCount = 0;
            }
            this.start = 1;
            this.end = this.numRowsPerPg;
            if ( this.end > this.maxCount ) {
                this.end = this.maxCount;
            }
            
            // key to making the flyout function
            this.navigation.setStart(this.start);
            this.navigation.setEnd(this.end);
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
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria,getFacesContext());
        return null;
    }
    
    public ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = reportCriteriaService.getTrendChartReportCriteria(this.navigation.getGroupID(), this.navigation.getDurationBean().getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        return reportCriteria;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }
    
    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public void setAnimateChartData(String animateChartData)
    {
        this.animateChartData = Boolean.valueOf(animateChartData);
        
    }

    public String isAnimateChartData()
    {
        return animateChartData.toString();
    }
    
    @Override
    public void onDurationChange(Duration d)
    {
        createScoreableEntities();
    }

}
