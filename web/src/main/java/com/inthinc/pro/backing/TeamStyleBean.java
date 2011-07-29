package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.charts.Bar3D;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

public class TeamStyleBean extends BaseBean {
    
//  Request scope bean for new team page 
    private static final long serialVersionUID = 3887694723869910138L;
    
    private Map<ScoreType, Map<String,String>> barDefMap;
    private Map<ScoreType, Map<String,Integer>> overallScoreMap;

    private TeamCommonBean teamCommonBean;
    
    private ScoreType scoreType = ScoreType.SCORE_DRIVING_STYLE;
    
    private static final Logger logger = Logger.getLogger(TeamStyleBean.class);

    public TeamStyleBean() {}

    private Integer initOverallScore(ScoreType scoreType) {
        ScoreableEntity scoreableEntity = getScoreableEntityNumber();
        if (scoreableEntity == null || scoreableEntity.getScore() == null) {
            return -1;
        }
        return scoreableEntity.getScore();
    }

    public String getOverallScoreStyle() {
        return ScoreBox.GetStyleFromScore(getSelectedOverallScore(), ScoreBoxSizes.LARGE);
    }

    public String getSelectedBarDef() {
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
        List<ScoreTypeBreakdown> scoreDataList = getScoreTypeBreakdownBar(scoreType);    

        List<String> categoryLabelList = new ArrayList<String>();
        boolean first = true;

        for (ScoreType subType : scoreType.getSubTypes()) {
            if (first) {
                categoryLabelList.add(MessageUtil.getMessageString(scoreType.toString(), getLocale()));
                first = false;
            }
            else {
                categoryLabelList.add(MessageUtil.getMessageString(subType.toString(), getLocale()));
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
                sb.append(bar3d.getChartDataSet(MessageUtil.getMessageString(category.toString(), getLocale()), category.getColor(), valueList.toArray(new Object[0])));
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
        TimeFrame timeFrame = teamCommonBean.getTimeFrame();
        
        if (getOverallScoreMap().get(scoreType) == null) {
            
            overallScoreMap.put(scoreType, new HashMap<String, Integer>());
        }
        if (overallScoreMap.get(scoreType).get(timeFrame.name()) == null){
            
            overallScoreMap.get(scoreType).put(timeFrame.name(), initOverallScore(scoreType));
        }
        return getOverallScoreMap().get(scoreType).get(timeFrame.name());
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

    public String exportToPDF() {
        // TODO Auto-generated method stub
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
        
        entity.setEntityID(teamCommonBean.getGroupID());
        entity.setEntityType(EntityType.ENTITY_GROUP);
        entity.setScore(0);
        if ( size != 0 ) {
            entity.setScore(value*100/size);
        }
        entity.setScoreType(scoreType);

        return entity;
    }
}