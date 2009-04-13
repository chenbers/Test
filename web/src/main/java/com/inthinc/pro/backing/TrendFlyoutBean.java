package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendFlyoutBean extends BaseBean
{

    private static final Logger logger = Logger.getLogger(TrendFlyoutBean.class);

    private ScoreDAO scoreDAO;
    private NavigationBean navigation;

    private String lineDef = new String();

    private List<ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
    
    private Integer maxCount = 0;
    private Integer start = 1;
    private Integer end = maxCount;
    
    private String countString = null;
    private Integer tmpGroupID = null;
    
    private String sortItFirst = "true";
    private String sortItSecond = "false";    
    
    private ReportRenderer reportRenderer;
    
    private int selected = -1;
    private boolean resetFlyout = false;
    private Boolean animateChartData = Boolean.TRUE;
    
    public TrendFlyoutBean()
    {
        super();  
        
        Map m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator imap = m.entrySet().iterator();
        while (imap.hasNext()) {
           Map.Entry entry = (Map.Entry) imap.next();
           String key = (String) entry.getKey();
           String value = (String) entry.getValue();
           
           // groupID backward from the hidden in the ajax
           if (         key.equalsIgnoreCase("trend_form:dateLinksWithGrpId") ) {
               tmpGroupID = new Integer(value);
           }
          
           // selected a line to change
           if (         key.equalsIgnoreCase("selected") ) {
               selected = (new Integer(value)).intValue();
           }
           
           // entering flyout, re-initialize state
           if (         key.equalsIgnoreCase("flyoutReset") ) {
               resetFlyout = true;
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

        logger.debug("Animate Chart: " + animateChartData);
        // Control parameters
        sb.append(GraphicUtil.getXYControlParameters(animateChartData));

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<ScoreableEntity> s = null;
        s = getScores();
        
        // Apply any sorting
        if ( this.sortItFirst.equalsIgnoreCase("true") ) 
        {
            s = sortOnIdentifier(s);
        } 
        if ( this.sortItSecond.equalsIgnoreCase("true") )
        {
            s = sortOnScore(s);
        }
        
        // Adjust the count values
        this.start = 1;
        this.maxCount = s.size(); 
        this.end = this.maxCount;
//        this.end = this.numRowsPerPg;
//        if ( this.maxCount < this.end ) {
//            this.end = this.maxCount;
//        }

        // X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(navigation.getDurationBean().getDuration()));
        sb.append("</categories>");

        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(
                this.navigation.getGroupID(), navigation.getDurationBean().getDuration());
        ColorSelectorStandard cs = new ColorSelectorStandard();
        
        for (int i = 1; i <= this.maxCount; i++)
        {
            if (s.size() < i)
                continue;
            
            ScoreableEntity se = s.get(i-1);
           
            // Show it?
            String key = String.valueOf(se.getEntityID().intValue());
            Boolean localShow = (Boolean)this.navigation.getFlyout().get(key);
            if ( (localShow != null) && !localShow) {
                continue;
            } else {            
            // Fetch to get children's observations
            List<ScoreableEntity> ss = groupTrendMap.get(se.getEntityID());

            // Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append(cs.getEntityColorKey(i-1).substring(1));
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
        
        // Apply any sorting
        if ( this.sortItFirst.equalsIgnoreCase("true") ) 
        {
            s = sortOnIdentifier(s);
        } 
        if ( this.sortItSecond.equalsIgnoreCase("true") )
        {
            s = sortOnScore(s);
        }

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
            
            se.setShow(true);
            String key = String.valueOf(se.getSe().getEntityID().intValue());
            if ( this.navigation.getFlyout().get(key) != null ) {
                se.setShow( (Boolean)this.navigation.getFlyout().get(key) );
            }  
                      
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
        this.start = 1;
        this.maxCount = this.scoreableEntities.size();
        this.end = this.maxCount;
        
        return this.scoreableEntities;
    }
    private List<ScoreableEntity> sortOnIdentifier(List<ScoreableEntity> sLocal )
    {
        List<ScoreableEntity> sTemp = sLocal;
        
        Collections.sort(sTemp, new Comparator() {
            public int compare(Object o1, Object o2) {
                ScoreableEntity s1 = (ScoreableEntity)o1;
                ScoreableEntity s2 = (ScoreableEntity)o2;
                return s2.getIdentifier().compareToIgnoreCase(s1.getIdentifier());
            }            
        });  
    
        if ( this.navigation.getSortedFirst().equalsIgnoreCase("false") )             
        {
            Collections.reverse(sTemp);            
        }        
        
        return sTemp;
    }
        
    private List<ScoreableEntity> sortOnScore(List<ScoreableEntity> sLocal )
    {
        List<ScoreableEntity> sTemp = sLocal;
        
        Collections.sort(sTemp, new Comparator() {
            public int compare(Object o1, Object o2) {
                ScoreableEntity s1 = (ScoreableEntity)o1;
                ScoreableEntity s2 = (ScoreableEntity)o2;
                return s2.getScore().compareTo(s1.getScore());      
            }            
        });  
    
        if ( this.navigation.getSortedSecond().equalsIgnoreCase("false") )            
        {
            Collections.reverse(sTemp);            
        }        
        
        return sTemp;
    }
    
    
    private List<ScoreableEntity> getScores() {
        List<ScoreableEntity> s = null;
        try
        {
            s = scoreDAO.getScores(this.navigation.getGroupID(), navigation.getDurationBean().getDuration(), ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        
        return s;
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
         
        // tmpGroupID implies back on the same page
        if ( this.tmpGroupID != null ) {
            this.navigation.setGroupID(tmpGroupID);
            
            List<ScoreableEntity> s = null;
            s = getScores();
            this.start = 1;
            this.end = s.size();
            this.maxCount = s.size();                           
        } 
                
        // control of the display of lines        
        setState();  
                              
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
    
    public String exportToPDF()
    {
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria,getFacesContext());
        return null;
    }
    
    public ReportCriteria buildReportCriteria()
    {
        List<CategorySeriesData> lineGraphDataList = new ArrayList<CategorySeriesData>();
        List<ScoreableEntityPkg> scoreableEntityDataSet = createScoreableEntities();
        List<ScoreableEntity> s = null;
        s = getScores();
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(
                this.navigation.getGroupID(), navigation.getDurationBean().getDuration());
        
        List<String> monthList = GraphicUtil.createMonthList(navigation.getDurationBean().getDuration());
        
        for(int i = 0;i < groupTrendMap.size(); i++)
        {
            ScoreableEntity se = s.get(i);
            ColorSelectorStandard cs = new ColorSelectorStandard();
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());
            
            // Not a full range, pad w/ zero
            int holes = 0;
            if (navigation.getDurationBean().getDuration() == Duration.DAYS)
            {
                holes = navigation.getDurationBean().getDuration().getNumberOfDays() - scoreableEntityList.size();            
            }
            else
            {
                holes = GraphicUtil.convertToMonths(navigation.getDurationBean().getDuration()) - scoreableEntityList.size();
            }
            int index = 0;
            for (int k = 0; k < holes; k++)
            {
                lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(),monthList.get(index++),0F,se.getIdentifier()));
            }
            for(ScoreableEntity scoreableEntity:scoreableEntityList)
            {
               Float score = new Float((scoreableEntity.getScore()==null) ? 0 : scoreableEntity.getScore() / 10.0);
               lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(),monthList.get(index++),score,se.getIdentifier()));
            }
            
        }
       
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TREND,getNavigation().getGroup().getName());
        reportCriteria.addChartDataSet(lineGraphDataList);
        reportCriteria.setMainDataset(scoreableEntityDataSet);
        reportCriteria.setDuration(getNavigation().getDurationBean().getDuration());
        reportCriteria.setRecordsPerReportParameters(8, "se.identifier", "seriesID");
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
    
    public String getSortItFirst()
    {
        return sortItFirst;
    }

    public void setSortItFirst(String sortItFirst)
    {
        this.sortItFirst = sortItFirst;
        
        if (        this.navigation.getSortedFirst().equalsIgnoreCase("true") )
        {
            this.navigation.setSortedFirst("false");
        } 
        else if (   this.navigation.getSortedFirst().equalsIgnoreCase("false") )
        {
            this.navigation.setSortedFirst("true");
        }
    }

    public String getSortItSecond()
    {
        return sortItSecond;
    }
    
    public void setSortItSecond(String sortItSecond)
    {
        this.sortItSecond = sortItSecond;
        
        if (        this.navigation.getSortedSecond().equalsIgnoreCase("true") )
        {
            this.navigation.setSortedSecond("false");
        } 
        else if (   this.navigation.getSortedSecond().equalsIgnoreCase("false") )
        {
            this.navigation.setSortedSecond("true");
        }
    }

    private void setState() 
    {
        // reset the state of the flyout
        if ( this.resetFlyout ) {
            this.navigation.getFlyout().clear();
        }
        
        // check for a checkbox selected
        if ( this.selected != -1 ) {                   
            String key = String.valueOf(selected);
            Boolean found = (Boolean)this.navigation.getFlyout().get(key);
            
            // Found it
            if ( found != null ) {
                
                // Was true, set false
                if ( found ) {
                    this.navigation.getFlyout().put(key, false);
                    
                // Was false, set true
                } else {
                    this.navigation.getFlyout().put(key, true);
                }           
                
            // New, assumed on, set off
            } else {
                this.navigation.getFlyout().put(key, false);
            }
        }
            
    }

    public void setAnimateChartData(String animateChartData)
    {
        logger.debug("setAnimateChartData: " + animateChartData);
        this.animateChartData = Boolean.valueOf(animateChartData);
    }

    public String getAnimateChartData()
    {
        return animateChartData.toString();
    }

}
