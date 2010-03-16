package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.charts.Bar3D;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

import org.joda.time.Duration;

public class TeamPieChartBean extends BaseBean {
    private ScoreDAO scoreDAO;
    
    private Map<ScoreType, Map<String,String>> barDefMap;
    private Map<ScoreType, Map<Duration,Integer>> overallScoreMap;
    private List<TabAction> actions;
    private TabAction selectedAction;
    
    private TeamCommonBean teamCommonBean;
    
    private List<HashMap> overallTotals;
    
    private Integer groupID;
    private String ping;
    private static final Logger logger = Logger.getLogger(TeamOverviewBean.class);

    public TeamPieChartBean() {
        logger.debug("TeamPieChartBean - constructor");        
    }

    public String getPing() {
        getGroupID();
        return ping;
    }

    public void setPing(String ping) {
        getGroupID();
        this.ping = ping;
    }

    private Integer initOverallScore(ScoreType scoreType) {
        ScoreableEntity scoreableEntity = null;
//        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(getGroupID(), durationBean.getDuration(), scoreType);        
        if (scoreableEntity == null || scoreableEntity.getScore() == null)
            return -1;
        return scoreableEntity.getScore();
    }

    public String getOverallScoreStyle() {
        return ScoreBox.GetStyleFromScore(getSelectedOverallScore(), ScoreBoxSizes.LARGE);
    }

    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public String getSelectedBarDef() {
        TabAction action = getSelectedAction();
        ScoreType scoreType = action.getScoreType();
        TimeFrame timeFrame = teamCommonBean.getTimeFrame();
        
        if (getBarDefMap().get(scoreType) == null) {
            
            getBarDefMap().put(scoreType,  new HashMap<String, String>());
        }
        if (barDefMap.get(scoreType).get(timeFrame.name()) == null){
            
            barDefMap.get(scoreType).put(timeFrame.name(), createPieChart(scoreType));            
        }
        return getBarDefMap().get(scoreType).get(timeFrame.name());
    }

    public String getBarDef(Integer type) {
       return getBarDefMap().get(ScoreType.valueOf(type)).get(teamCommonBean.getTimeFrame().getDuration());
    }
    
    public String createPieChart(ScoreType scoreType) {
        List<ScoreableEntity> scoreDataList = null;        
        try {
            logger.debug("TeamPieChartBean 2d score groupID[" + getGroupID() + "] scoreType " + scoreType);
//            scoreDataList = scoreDAO.getScoreBreakdown(getGroupID(), durationBean.getDuration(), scoreType);  
            scoreDataList = getScoreableEntities();                 
        }
        catch (Exception e) {
            scoreDataList = new ArrayList<ScoreableEntity>();
        }
        List<String> categoryLabelList = new ArrayList<String>();
        boolean first = true;
       
        for (ScoreType subType : scoreType.getSubTypes()) {
            if (first) {
                categoryLabelList.add(MessageUtil.getMessageString(ScoreType.SCORE_OVERALL.toString(), getLocale()));
                first = false;
            }
            else {
                categoryLabelList.add(MessageUtil.getMessageString(subType.toString(), getLocale()));
            }
        }

        // Create the pie string
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();
        
        // Control parameters
        sb.append(pie.getControlParameters());
               
        if (scoreDataList.size() > 0) {
            ScoreableEntity se = null;
            for (int i = 0; i < scoreDataList.size(); i++)
            {
                se = (ScoreableEntity) scoreDataList.get(i);
                Integer percent = se.getScore();
                
                if(percent == 0) // Do not display 0% pie slices.
                    continue;
                
                sb.append("<set value=\'" + percent.toString() + "\' " + "label=\'\' color=\'" + (OverallScoreBean.entityColorKey.get(i)) + "\'/>");
            }
        }
        
        sb.append(pie.getClose());
        return sb.toString();
    }

    public Map<ScoreType, Map<String,String>> getBarDefMap() {
        if (barDefMap == null) {
            barDefMap = new HashMap<ScoreType, Map<String,String>>();
        }
        return barDefMap;
    }

    public void setBarDefMap(Map<ScoreType, Map<String,String>> barDefMap) {
        this.barDefMap = barDefMap;
    }

    public Map<ScoreType, Map<Duration,Integer>> getOverallScoreMap() {
        if (overallScoreMap == null) {
            overallScoreMap = new HashMap<ScoreType, Map<Duration,Integer>>();
        }
        return overallScoreMap;
    }

    public void setOverallScoreMap(Map<ScoreType, Map<Duration,Integer>> overallScoreMap) {
        this.overallScoreMap = overallScoreMap;
    }

    public Integer getSelectedOverallScore() {
        TabAction action = getSelectedAction();
        
        ScoreType scoreType = action.getScoreType();
        
        if (getOverallScoreMap().get(scoreType) == null) {
            
            overallScoreMap.put(scoreType, new HashMap<Duration, Integer>());
        }
        if (overallScoreMap.get(scoreType).get(teamCommonBean.getTimeFrame().getDuration()) == null){
            
            overallScoreMap.get(scoreType).put(teamCommonBean.getTimeFrame().getDuration(), initOverallScore(scoreType));
        }
        return getOverallScoreMap().get(scoreType).get(teamCommonBean.getTimeFrame().getDuration());
    }

    public void setActions(List<TabAction> actions) {
        this.actions = actions;
    }

    public List<TabAction> getActions() {
        if (actions == null) {
            String[] actionKeys = { "overall", "driving", "speed", "seatbelt" };
            int[] width = { 108, 104, 70, 85 };
            ScoreType[] scoreTypes = { ScoreType.SCORE_OVERALL, ScoreType.SCORE_DRIVING_STYLE, ScoreType.SCORE_SPEEDING, ScoreType.SCORE_SEATBELT };
            actions = new ArrayList<TabAction>();
            for (int i = 0; i < actionKeys.length; i++) {
                actions.add(new TabAction(actionKeys[i], actionKeys[i], MessageUtil.getMessageString("teamOverviewSideNav_" + actionKeys[i]), actionKeys[i] + "_on", actionKeys[i]
                        + "_off", scoreTypes[i], width[i]));
            }
        }
        return actions;
    }

    public TabAction getSelectedAction() {
        if (selectedAction == null) {
            setSelectedAction(getActions().get(0));
        }
        return selectedAction;
    }

    public void setSelectedAction(TabAction selectedAction) {
        this.selectedAction = selectedAction;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
        this.groupID = teamCommonBean.getGroupID();
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        if (this.groupID != null && !this.groupID.equals(groupID)) {
            logger.info("TeamOverviewBean groupID changed " + groupID);
//            setDuration(Duration.DAYS);
            setSelectedAction(null);
            setOverallScoreMap(null);
            setBarDefMap(null);
        }
        this.groupID = groupID;
    }

    public String exportToPDF() {
        // TODO Auto-generated method stub
        return null;
    }
        
    public void setOverall(ActionEvent ae) {
        this.selectedAction = findTab("overall");
    }
    
    public void setSpeed(ActionEvent ae) {
        this.selectedAction = findTab("speed");
    }
    
    public void setDrivingStyle(ActionEvent ae) {
        this.selectedAction = findTab("driving");
    }    
    
    public void setSeatbelt(ActionEvent ae) {
        this.selectedAction = findTab("seatbelt");
    }    
    
    private TabAction findTab(String key) {
        for ( TabAction ta: getActions() ) {
            if ( ta.getKey().equalsIgnoreCase(key) ) {
                return ta;
            }
        }
        return null;
    }
    
    public List<HashMap> getOverallTotals() {
        overallTotals = new ArrayList<HashMap>();
        HashMap<String,String> totals = new HashMap<String,String>();
        
        int nA = 0;
        int zeroToOne = 0;
        int oneToTwo = 0;
        int twoToThree = 0;
        int threeToFour = 0;
        int fourToFive = 0;
        
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if(         dvsw.getScore().getOverall().intValue() <  0 ) {
                nA++;
                
            } else if(  dvsw.getScore().getOverall().intValue() >= 0 && dvsw.getScore().getOverall().intValue() <= 10 ) {
                zeroToOne++;
                
            } else if ( dvsw.getScore().getOverall().intValue() >  10 && dvsw.getScore().getOverall().intValue() <= 20 ) {
                oneToTwo++;
                
            } else if ( dvsw.getScore().getOverall().intValue() >  20 && dvsw.getScore().getOverall().intValue() <= 30 ) {
                twoToThree++;
                
            } else if ( dvsw.getScore().getOverall().intValue() >  30 && dvsw.getScore().getOverall().intValue() <= 40 ) {
                threeToFour++;
                
            } else if ( dvsw.getScore().getOverall().intValue() >  40 ) {
                fourToFive++;
            }
            
        }
        
        totals.put("nA", Integer.toString(nA));
        totals.put("zeroToOne", Integer.toString(zeroToOne));
        totals.put("oneToTwo", Integer.toString(oneToTwo));
        totals.put("twoToThree", Integer.toString(twoToThree));
        totals.put("threeToFour", Integer.toString(threeToFour));
        totals.put("fourToFive", Integer.toString(fourToFive));
        
        overallTotals.add(totals);
        
        return overallTotals;
    }
    
    private List<ScoreableEntity> getScoreableEntities() {
        List<ScoreableEntity> local = new ArrayList<ScoreableEntity>();
        
        List<HashMap> observations = getOverallTotals();
        int totObs = 0;
        HashMap observation = observations.get(0);
        
        String zeroToOne = (String)observation.get("zeroToOne");
        totObs += Integer.parseInt(zeroToOne);
        String oneToTwo = (String)observation.get("oneToTwo");
        totObs += Integer.parseInt(oneToTwo);
        String twoToThree = (String)observation.get("twoToThree");
        totObs += Integer.parseInt(twoToThree);
        String threeToFour = (String)observation.get("threeToFour");
        totObs += Integer.parseInt(threeToFour);
        String fourToFive = (String)observation.get("fourToFive");
        totObs += Integer.parseInt(fourToFive);
        
        ScoreableEntity se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(zeroToOne)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(oneToTwo)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(twoToThree)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(threeToFour)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(fourToFive)/totObs);
        local.add(se);
        
        return local;
    }
}

