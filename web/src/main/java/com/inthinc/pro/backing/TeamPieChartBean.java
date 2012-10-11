package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

public class TeamPieChartBean extends BaseBean {

//  Request scope bean for new team page 
    private static final long serialVersionUID = 6110042545459269849L;
    
    private Map<ScoreType, Map<String,String>> pieDefMap;
    private Map<ScoreType, Map<String,Integer>> overallScoreMap;
    
    private TeamCommonBean teamCommonBean;
    
    private List<HashMap<String,String>> overallTotals;
    
    private ScoreType scoreType = ScoreType.SCORE_OVERALL;
    
    private static final Logger logger = Logger.getLogger(TeamPieChartBean.class);

    public TeamPieChartBean() {}

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

    public String getSelectedPieDef() {
        TimeFrame timeFrame = teamCommonBean.getTimeFrame();
        
        if (getPieDefMap().get(scoreType) == null) {
            
            getPieDefMap().put(scoreType,  new HashMap<String, String>());
        }
        if (pieDefMap.get(scoreType).get(timeFrame.name()) == null){
            
            pieDefMap.get(scoreType).put(timeFrame.name(), createPieChart(scoreType));            
        }
        return getPieDefMap().get(scoreType).get(timeFrame.name());
    }
    
    public String createPieChart(ScoreType scoreType) {
        List<ScoreableEntity> scoreDataList = getScoreableEntitiesPie();                 

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

        // Create the pie string
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();
        
        // Control parameters
        sb.append(pie.getControlParameters());
        sb.append(" caption = \'" + MessageUtil.getMessageString("team_overall_title") + "\'>");
               
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

    public Map<ScoreType, Map<String,String>> getPieDefMap() {
        if (pieDefMap == null) {
            pieDefMap = new HashMap<ScoreType, Map<String,String>>();
        }
        return pieDefMap;
    }

    public void setPieDefMap(Map<ScoreType, Map<String,String>> pieDefMap) {
        this.pieDefMap = pieDefMap;
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
    
    public List<HashMap<String,String>> getOverallTotals() {
        overallTotals = new ArrayList<HashMap<String,String>>();
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
    
    private List<ScoreableEntity> getScoreableEntitiesPie() {
        List<ScoreableEntity> local = new ArrayList<ScoreableEntity>();
        
        List<HashMap<String,String>> observations = getOverallTotals();
        int totObs = 0;
        HashMap<String,String> observation = observations.get(0);
        
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
        double factor = (100)/totObs;
        
        se.setScore((int)Math.round(factor*Integer.parseInt(zeroToOne)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(oneToTwo)));
        local.add(se);
        se = new ScoreableEntity();
                
        se.setScore((int)Math.round(factor*Integer.parseInt(twoToThree)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(threeToFour)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(fourToFive)));
        local.add(se);
        
        return local;
    }
    
    private ScoreableEntity getScoreableEntityNumber() {
        ScoreableEntity se = new ScoreableEntity();
        
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        int totScoreableDrivers = 0;
        int totalScores = 0;

        for ( DriverVehicleScoreWrapper dvsw: local ) {
            if ( (dvsw.getScore().getOverall() != null) && 
                 (dvsw.getScore().getOverall().intValue() >= 0) ) {
                totScoreableDrivers++;
                totalScores += dvsw.getScore().getOverall().intValue();
            }
        }
        
        se.setScore( (totScoreableDrivers != 0)? totalScores/totScoreableDrivers:0);
        
        return se;
    }
}