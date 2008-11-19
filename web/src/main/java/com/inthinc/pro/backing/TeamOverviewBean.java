package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.charts.Bar3D;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.MessageUtil;

public class TeamOverviewBean extends BaseDurationBean
{

    private Integer             overallScore;
    private ScoreDAO          scoreDAO;
    private Map<ScoreType, String>        barDefMap;
    private List<TabAction>  actions;
    private TabAction selectedAction;
    private NavigationBean navigation;
    
    private Integer groupID;
    
    private static final Logger logger = Logger.getLogger(TeamOverviewBean.class);

    private void init()
    {
        
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());

        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL);
        setOverallScore(scoreableEntity.getScore());
    }

    public Integer getOverallScore()
    {
        if (overallScore == null)
        {
            init();
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
logger.debug("##### setOverallScore: " + overallScore);        
        this.overallScore = overallScore;
    }

    public String getOverallScoreStyle()
    {
        ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.LARGE);
        return sb.getScoreStyle();
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO graphicDAO)
    {
        this.scoreDAO = graphicDAO;
    }

    public String getSelectedBarDef()
    {
logger.debug("getSelectedBarDef() ");        
        TabAction action = getSelectedAction();
        
        ScoreType scoreType = action.getScoreType();
        if (getBarDefMap().get(scoreType) == null)
        {
                barDefMap.put(scoreType, createBar3DChart(scoreType));
        }
        
        return getBarDefMap().get(scoreType);
    }

    public String getBarDef(Integer type)
    {
        return getBarDefMap().get(ScoreType.valueOf(type));
    }

    public String createBar3DChart(ScoreType scoreType)
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());
        
        List<ScoreTypeBreakdown> scoreDataList = scoreDAO.getScoreBreakdownByType(getGroupID(), startDate, endDate, scoreType);
        
        List<String>categoryLabelList = new ArrayList<String>();
        for (ScoreType subType : scoreType.getSubTypes())
        {
            categoryLabelList.add(MessageUtil.getMessageString(subType.toString()));
        }
        
        StringBuffer sb = new StringBuffer();
        Bar3D bar3d = new Bar3D();
        
        // Control parameters
        sb.append(bar3d.getControlParameters());
        sb.append(bar3d.getCategories(categoryLabelList));
        for (ScoreCategory category : EnumSet.allOf(ScoreCategory.class))
        {
            List<Object> valueList = new ArrayList<Object>();
            for (ScoreTypeBreakdown scoreTypeBreakdown : scoreDataList)
            {
                valueList.add(scoreTypeBreakdown.getPercentageList().get(category.getCode()-1).getScore());
            }
            sb.append(bar3d.getChartDataSet(category.getRange(), category.getColor(), valueList.toArray(new Object[0])));
        }
        
        sb.append(bar3d.getClose());

        return sb.toString();
    }

    public Map<ScoreType, String> getBarDefMap()
    {
        if (barDefMap == null)
        {
            barDefMap = new HashMap<ScoreType, String>();
        }
        
        return barDefMap;
    }

    public void setBarDefMap(Map<ScoreType, String> barDefMap)
    {
        this.barDefMap = barDefMap;
    }

    public void setActions(List<TabAction> actions)
    {
        this.actions = actions;
    }

    public List<TabAction> getActions()
    {
        if (actions == null)
        {
            String[] actionKeys = {"overall","driving","speed","seatbelt"};
            int[]width = {108,104,70,85};
            ScoreType[] scoreTypes = {ScoreType.SCORE_OVERALL, ScoreType.SCORE_DRIVING_STYLE, ScoreType.SCORE_SPEEDING, ScoreType.SCORE_SEATBELT};
            actions = new ArrayList<TabAction>();
            
            for (int i = 0; i < actionKeys.length; i++)
            {
                actions.add(new TabAction(actionKeys[i], actionKeys[i], MessageUtil.getMessageString("teamOverviewSideNav_"+actionKeys[i]), actionKeys[i]+"_on", actionKeys[i]+"_off", scoreTypes[i], width[i]));
            }
        }
        return actions;
    }

    public TabAction getSelectedAction()
    {
        if (selectedAction == null)
        {
            setSelectedAction(getActions().get(0));
        }
        return selectedAction;
    }

    public void setSelectedAction(TabAction selectedAction)
    {
        this.selectedAction = selectedAction;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public Integer getGroupID()
    {
        setGroupID(navigation.getGroupID());
        if (groupID == null)
        {
            setGroupID(getUser().getPerson().getGroupID());
        }
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    @Override
    public void setDuration(Duration duration)
    {
        super.setDuration(duration);
        
        setOverallScore(null);
        setBarDefMap(null);
        
    }

}
