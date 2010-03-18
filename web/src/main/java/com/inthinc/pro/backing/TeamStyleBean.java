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
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

public class TeamStyleBean extends BaseBean {
    private ScoreDAO scoreDAO;
    
    private Map<ScoreType, Map<String,String>> barDefMap;
    private Map<ScoreType, Map<String,Integer>> overallScoreMap;
    private List<TabAction> actions;
    private TabAction selectedAction;

    private TeamCommonBean teamCommonBean;
    
    private Integer groupID;
    private static final Logger logger = Logger.getLogger(TeamStyleBean.class);

    public TeamStyleBean() {
        logger.debug("TeamStyleBean - constructor");        
    }

    private Integer initOverallScore(ScoreType scoreType) {
        ScoreableEntity scoreableEntity = getScoreableEntityNumber();
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
            
            barDefMap.get(scoreType).put(timeFrame.name(), createBar3DChart(scoreType));
        }
        return getBarDefMap().get(scoreType).get(timeFrame.name());
    }

    public String getBarDef(Integer type) {
       return getBarDefMap().get(ScoreType.valueOf(type)).get(teamCommonBean.getTimeFrame().getDuration());
    }

    public String createBar3DChart(ScoreType scoreType) {
        List<ScoreTypeBreakdown> scoreDataList = null;
        try {
            logger.debug("TeamStyleBean 3D BAR score groupID[" + getGroupID() + "] scoreType " + scoreType);
            scoreDataList = getScoreTypeBreakdownBar(scoreType);
        }
        catch (Exception e) {
            scoreDataList = new ArrayList<ScoreTypeBreakdown>();
        }
        List<String> categoryLabelList = new ArrayList<String>();
        boolean first = true;
        if (scoreType.equals(ScoreType.SCORE_SPEEDING)) {
            for (ScoreType subType : scoreType.getSubTypes()) {
                if (subType.equals(ScoreType.SCORE_SPEEDING))
                    categoryLabelList.add(MessageUtil.getMessageString(ScoreType.SCORE_OVERALL.toString(), getLocale()));
                else
                    categoryLabelList.add(MessageUtil.getMessageString(getMeasurementType() + "_" + subType.toString(), getLocale()));
            }
        }
        else {
            for (ScoreType subType : scoreType.getSubTypes()) {
                if (first) {
                    categoryLabelList.add(MessageUtil.getMessageString(ScoreType.SCORE_OVERALL.toString(), getLocale()));
                    first = false;
                }
                else {
                    categoryLabelList.add(MessageUtil.getMessageString(subType.toString(), getLocale()));
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        Bar3D bar3d = new Bar3D();
        // Control parameters
        sb.append(bar3d.getControlParameters());
        sb.append(bar3d.getCategories(categoryLabelList));
        if (scoreDataList.size() > 0) {
            List<ScoreCategory> categoryList = Collections.list(Collections.enumeration(EnumSet.allOf(ScoreCategory.class)));
            Collections.reverse(categoryList);
            for (ScoreCategory category : categoryList) {
                List<Object> valueList = new ArrayList<Object>();
                for (ScoreTypeBreakdown scoreTypeBreakdown : scoreDataList) {
                    if (scoreTypeBreakdown.getPercentageList().size() > category.getCode() - 1)
                        valueList.add(scoreTypeBreakdown.getPercentageList().get(category.getCode() - 1).getScore());
                    else
                        valueList.add(0);
                }
                sb.append(bar3d.getChartDataSet(category.getRange(), category.getColor(), valueList.toArray(new Object[0])));
            }
        }
        sb.append(bar3d.getClose());
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

    public Map<ScoreType, Map<String,Integer>> getOverallScoreMap() {
        if (overallScoreMap == null) {
            overallScoreMap = new HashMap<ScoreType, Map<String,Integer>>();
        }
        return overallScoreMap;
    }

    public void setOverallScoreMap(Map<ScoreType, Map<String,Integer>> overallScoreMap) {
        this.overallScoreMap = overallScoreMap;
    }

    public Integer getSelectedOverallScore() {
        TabAction action = getSelectedAction();        
        ScoreType scoreType = action.getScoreType();
        TimeFrame timeFrame = teamCommonBean.getTimeFrame();
        
        if (getOverallScoreMap().get(scoreType) == null) {
            
            overallScoreMap.put(scoreType, new HashMap<String, Integer>());
        }
        if (overallScoreMap.get(scoreType).get(timeFrame.name()) == null){
            
            overallScoreMap.get(scoreType).put(timeFrame.name(), initOverallScore(scoreType));
        }
        return getOverallScoreMap().get(scoreType).get(timeFrame.name());
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
    
    private ScoreableEntity getScoreableEntityNumber() {
        ScoreableEntity se = new ScoreableEntity();
        
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        int totScoreableDrivers = 0;
        int totalScores = 0;

        for ( DriverVehicleScoreWrapper dvsw: local ) {
            if ( (dvsw.getScore().getDrivingStyle() != null) && 
                 (dvsw.getScore().getDrivingStyle().intValue() >= 0) ) {
                totScoreableDrivers++;
                totalScores += dvsw.getScore().getDrivingStyle().intValue();
            }
        }
        
        se.setScore( (totScoreableDrivers != 0)? totalScores/totScoreableDrivers:0);
        
        return se;
    }    
    
    private List<ScoreTypeBreakdown> getScoreTypeBreakdownBar(ScoreType scoreType) {
        
        // What we are sending back
        List<ScoreTypeBreakdown> scoreTypeBreakdownList = new ArrayList<ScoreTypeBreakdown>();

        // Data to sift through
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
               
        // Run through all the subtypes of style
        for (ScoreType subType : scoreType.getSubTypes()) {
            int nA = 0;
            int zeroToOne = 0;
            int oneToTwo = 0;
            int twoToThree = 0;
            int threeToFour = 0;
            int fourToFive = 0;
            int totDrivers = 0;
            
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();            
        
            // Find the number in each 
            for ( DriverVehicleScoreWrapper dvsw: local ) {
                
                // Load the value for the subtype
                Integer typeValue = loadTypeValue(subType,dvsw);
                
                if ( typeValue != null ) {
                                        
                    if(         typeValue.intValue() <  0 ) {
                        nA++;
            
                    } else if(  typeValue.intValue() >= 0 && typeValue.intValue() <= 10 ) {
                        zeroToOne++;
            
                    } else if ( typeValue.intValue() >  10 && typeValue.intValue() <= 20 ) {
                        oneToTwo++;
            
                    } else if ( typeValue.intValue() >  20 && typeValue.intValue() <= 30 ) {
                        twoToThree++;
            
                    } else if ( typeValue.intValue() >  30 && typeValue.intValue() <= 40 ) {
                        threeToFour++;
            
                    } else if ( typeValue.intValue() >  40 ) {
                        fourToFive++;
                    }
                    totDrivers++;
                }
        
            }                           
            
            // Save the percentages                
            scoreList.add(createScoreableEntity(zeroToOne,totDrivers,subType)); 
            scoreList.add(createScoreableEntity(oneToTwo,totDrivers,subType));                  
            scoreList.add(createScoreableEntity(twoToThree,totDrivers,subType));  
            scoreList.add(createScoreableEntity(threeToFour,totDrivers,subType));  
            scoreList.add(createScoreableEntity(fourToFive,totDrivers,subType));              
            
            // Save this subtype
            ScoreTypeBreakdown scoreTypeBreakdown = new ScoreTypeBreakdown();
            scoreTypeBreakdown.setScoreType(subType);
            scoreTypeBreakdown.setPercentageList(scoreList);
            
            scoreTypeBreakdownList.add(scoreTypeBreakdown);            
        }
        
        return scoreTypeBreakdownList;
    }
    
    private Integer loadTypeValue(ScoreType st,DriverVehicleScoreWrapper dvsw) {

        if (        st.equals(ScoreType.SCORE_DRIVING_STYLE) ) {
            return (dvsw.getScore().getDrivingStyle() != null) ? new Integer(dvsw.getScore().getDrivingStyle().intValue()) : null;

        } else if ( st.equals(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL) ) {
            return (dvsw.getScore().getAggressiveAccel() != null) ? new Integer(dvsw.getScore().getAggressiveAccel().intValue()) : null;
           
        } else if ( st.equals(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE) ) {
            return (dvsw.getScore().getAggressiveBrake() != null) ? new Integer(dvsw.getScore().getAggressiveBrake().intValue()) : null;
            
        } else if ( st.equals(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP) ) {
            return (dvsw.getScore().getAggressiveBump() != null) ? new Integer(dvsw.getScore().getAggressiveBump().intValue()) : null;
            
        } else if ( st.equals(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN) ) {
            return (dvsw.getScore().getAggressiveTurn() != null) ? new Integer(dvsw.getScore().getAggressiveTurn().intValue()) : null;
            
        }
        
        return null;
    }
    
    private ScoreableEntity createScoreableEntity(int value,int size, ScoreType scoreType) {
        ScoreableEntity entity = new ScoreableEntity();
        
        entity.setEntityID(groupID);
        entity.setEntityType(EntityType.ENTITY_GROUP);
        entity.setScore(value*100/size);
        entity.setScoreType(scoreType);

        return entity;
    }
}

