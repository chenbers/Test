package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.MessageUtil;

public class TeamOverviewBean extends BaseDurationBean
{

    private Integer             overallScore;
    private ScoreDAO          scoreDAO;
    private Map<ScoreType, String>        pieDefMap;
    private List<TabAction>  actions;
    private TabAction selectedAction;
    private NavigationBean navigation;
    private Map<ScoreType, TabAction> actionScoreTypeMap;
    
    private Integer groupID;
    
    private static final Logger logger = Logger.getLogger(TeamOverviewBean.class);

    private void init()
    {
        
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());

        ScoreableEntity scoreableEntity = scoreDAO.getOverallScore(getGroupID(), startDate, endDate);
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

    public String getOverallPieDef()
    {
        return getPieDefMap().get(ScoreType.SCORE_OVERALL);
    }
    
    public String getDriveStylePieDef()
    {
        return getPieDefMap().get(ScoreType.SCORE_DRIVING_STYLE);
    }

    public String getSpeedPieDef()
    {
        return getPieDefMap().get(ScoreType.SCORE_SPEEDING);
    }
    
    public String getSeatbeltPieDef()
    {
        return getPieDefMap().get(ScoreType.SCORE_SEATBELT);
    }
    
    public String getCoachingPieDef()
    {
        return getPieDefMap().get(ScoreType.SCORE_COACHING_EVENTS);
    }

    public String getSelectedPieDef()
    {
logger.debug("getSelectedPieDef() ");        
        TabAction action = getSelectedAction();
        if (action == null)
        {
logger.debug("selected action is null");        
            
            return "";
        }
        
        
        return getPieDefMap().get(action.getScoreType());
    }

    public String getPieDef(Integer type)
    {
        return getPieDefMap().get(ScoreType.getScoreType(type));
    }

    public String createPieDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();

        // Control parameters
        sb.append(pie.getControlParameters());

        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());
        List<ScoreableEntity> scoreList = scoreDAO.getScores(getGroupID(), startDate, endDate, scoreType);
        ScoreBreakdown scoreBreakdown = new ScoreBreakdown(scoreList);
        Integer numScores = scoreBreakdown.getNumScores();
        if (numScores == 0)
        {
            // TODO:  What color/text (see use case)?
            sb.append(pie.getChartItem(new Object[] {100, "No Data To Display", "F6B305"}));
        }
        else
        {
            Map<ScoreCategory, Integer> countMap = scoreBreakdown.getCountMap();
            for (Map.Entry<ScoreCategory, Integer> item : countMap.entrySet())
            {
                if (item.getValue().intValue() == 0)
                {
                    continue;
                }
                Integer value = (item.getValue() * 100 / numScores);
                sb.append(pie.getChartItem(new Object[] {value, "", item.getKey().getColor()}));

            }

        }
        sb.append(pie.getClose());

        return sb.toString();
    }

    public Map<ScoreType, String> getPieDefMap()
    {
        if (pieDefMap == null)
        {
            pieDefMap = new HashMap<ScoreType, String>();
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                pieDefMap.put(scoreType, createPieDef(scoreType));
            }
        }
        return pieDefMap;
    }

    public void setPieDefMap(Map<ScoreType, String> pieDefMap)
    {
        this.pieDefMap = pieDefMap;
    }

    public void setActions(List<TabAction> actions)
    {
        this.actions = actions;
    }

    public List<TabAction> getActions()
    {
        if (actions == null)
        {
            String[] actionKeys = {"overall","drivestyle","speed","seatbelt","coaching"};
            ScoreType[] scoreTypes = {ScoreType.SCORE_OVERALL, ScoreType.SCORE_DRIVING_STYLE, ScoreType.SCORE_SPEEDING, ScoreType.SCORE_SEATBELT, ScoreType.SCORE_COACHING_EVENTS};
            actions = new ArrayList<TabAction>();
            
            for (int i = 0; i < actionKeys.length; i++)
            {
                actions.add(new TabAction(actionKeys[i], actionKeys[i], MessageUtil.getMessageString("teamOverviewSideNav_"+actionKeys[i]), "ls_tab_"+actionKeys[i], scoreTypes[i]));
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
            setGroupID(getUser().getGroupID());
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
        setPieDefMap(null);
        
    }

}
