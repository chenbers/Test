package com.inthinc.pro.reports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.util.ReportUtil;

public class ReportCriteriaServiceImpl implements ReportCriteriaService
{
    private GroupDAO groupDAO;
    private ScoreDAO scoreDAO;
    
    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImpl.class);
    
    @Override
    public ReportCriteria getDriverReportCriteria(Integer group)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ReportCriteria getMpgReportCriteria(Integer groupID, Duration duration)
    {
        // TODO Auto-generated method stub
        return null;
    }  
    
    @Override
    public ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration)
    {
        List<CategorySeriesData> lineGraphDataList = new ArrayList<CategorySeriesData>();
        List<ScoreableEntity> s = getScores(groupID,duration);
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(groupID, duration);
        
        List<String> monthList = ReportUtil.createMonthList(duration);
        
        for(int i = 0;i < groupTrendMap.size(); i++)
        {
            ScoreableEntity se = s.get(i);
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());
            
            
            // Not a full range, pad w/ zero
            int holes = 0;
            if (duration == Duration.DAYS)
            {
                holes = duration.getNumberOfDays() - scoreableEntityList.size();            
            }
            else
            {
                holes = ReportUtil.convertToMonths(duration) - scoreableEntityList.size();
            }
            int index = 0;
            for (int k = 0; k < holes; k++)
            {
                lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(),monthList.get(index++),0F,se.getIdentifier()));
            }
            for(ScoreableEntity scoreableEntity:scoreableEntityList)
            {
               Float score = new Float((scoreableEntity.getScore()==null) ? 5 : scoreableEntity.getScore() / 10.0);
               lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(),monthList.get(index++),score,se.getIdentifier()));
            }
            
        }
       
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TREND,group.getName());
        reportCriteria.addChartDataSet(lineGraphDataList);
        reportCriteria.setMainDataset(s);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(8, "identifier", "seriesID");
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getVehicleReportCriteria(Integer group)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    private List<ScoreableEntity> getScores(Integer groupID, Duration duration) {
        List<ScoreableEntity> s = null;
        try
        {
            s = scoreDAO.getScores(groupID, duration, ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        
        return s;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }
    
    
    
    
    
    
}
