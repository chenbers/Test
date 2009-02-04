package com.inthinc.pro.reports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.service.TrendReportService;
import com.inthinc.pro.reports.util.wrapper.ScoreableEntityPkg;


public class TrendReportServiceImpl implements TrendReportService
{
    
    private GroupDAO groupDAO;
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    private ScoreDAO scoreDAO;
    
    private static final Logger logger = Logger.getLogger(TrendReportServiceImpl.class);
    @Override
    public ReportCriteria getReportCriteria(Integer groupID, Duration duration)
    {
        return null;
    }
    
//    public List<ScoreableEntityPkg> createScoreableEntities()
//    {
//        List<ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
//        if ( scoreableEntities != null ) {
//            logger.debug("scoreableentities size " + scoreableEntities.size());
//        }        
//        List<ScoreableEntity> s = null;
//        s = getScores();
//        
//
//        // Populate the table
//        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//        int cnt = 0;                
//        ColorSelectorStandard cs = new ColorSelectorStandard();
//        for (ScoreableEntity score : s)
//        {
//            ScoreableEntityPkg se = new ScoreableEntityPkg();
//            se.setSe(score);
//            se.setStyle(ScoreBox.GetStyleFromScore(score.getScore(), ScoreBoxSizes.SMALL));
//            se.setColorKey(cs.getEntityColorKey(cnt++));
//            if (score.getEntityType().equals(EntityType.ENTITY_GROUP))
//            {
//                // TODO: if getGroupHierarchy().getGroupLevel(score.getEntityID()) returns null 
//                // this should an error -- someone trying to access a group they shouldn't
//                String url = "";
//                if (getGroupHierarchy().getGroupLevel(score.getEntityID()) != null)
//                    url = getGroupHierarchy().getGroupLevel(score.getEntityID()).getUrl();
//                se.setGoTo(contextPath + url + "?groupID=" + score.getEntityID());
//            }
//            scoreableEntities.add(se);
//            score = null;
//        }
//        this.maxCount = this.scoreableEntities.size();        
//        return this.scoreableEntities;
//    }
    
//    private List<ScoreableEntity> getScores() {
//        List<ScoreableEntity> s = null;
//        try
//        {
//            s = scoreDAO.getScores(this.navigation.getGroupID(), navigation.getDuration(), ScoreType.SCORE_OVERALL);
//        }
//        catch (Exception e)
//        {
//            logger.debug("graphicDao error: " + e.getMessage());
//        }
//        
//        return s;
//    }
}
