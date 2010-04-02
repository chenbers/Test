package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.Bar2DStacked;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

public class TeamSpeedBean extends BaseBean {
    private ScoreDAO scoreDAO;
    
    private Map<ScoreType, Map<String,String>> barDefMap;
    private Map<ScoreType, Map<String,String>> newBarDefMap;
    private Map<ScoreType, Map<String,String>> speedOverBarDefMap;
    private Map<ScoreType, Map<String,Integer>> overallScoreMap;
    private List<TabAction> actions;
    private TabAction selectedAction;
    
    private TeamCommonBean teamCommonBean;
    
    private List<HashMap> speedTotals;
    private HashMap graphicLabels;
    
    private Integer totDrivers;
    private Integer totDriversSpeeding;
    
    private Integer groupID;
    private static final Logger logger = Logger.getLogger(TeamSpeedBean.class);

    public TeamSpeedBean() {
        logger.debug("TeamSpeedBean - constructor"); 
        
        // Get the labels for the graphs
        graphicLabels = new HashMap();
        
        if ( this.getMeasurementType().equals(MeasurementType.ENGLISH) ) {
            graphicLabels.put("zeroToThirty", MessageUtil.getMessageString("MeasurementType.ENGLISH_SCORE_SPEEDING_21_30"));
            graphicLabels.put("thirtyOneToFourty", MessageUtil.getMessageString("MeasurementType.ENGLISH_SCORE_SPEEDING_31_40"));
            graphicLabels.put("fourtyOneToFiftyFour", MessageUtil.getMessageString("MeasurementType.ENGLISH_SCORE_SPEEDING_41_54"));
            graphicLabels.put("fiftyFiveToSixtyFour", MessageUtil.getMessageString("MeasurementType.ENGLISH_SCORE_SPEEDING_55_64"));
            graphicLabels.put("sixtyFiveAndUp", MessageUtil.getMessageString("MeasurementType.ENGLISH_SCORE_SPEEDING_65_80"));


        } else {
            graphicLabels.put("zeroToThirty", MessageUtil.getMessageString("MeasurementType.METRIC_SCORE_SPEEDING_21_30"));
            graphicLabels.put("thirtyOneToFourty", MessageUtil.getMessageString("MeasurementType.METRIC_SCORE_SPEEDING_31_40"));
            graphicLabels.put("fourtyOneToFiftyFour", MessageUtil.getMessageString("MeasurementType.METRIC_SCORE_SPEEDING_41_54"));
            graphicLabels.put("fiftyFiveToSixtyFour", MessageUtil.getMessageString("MeasurementType.METRIC_SCORE_SPEEDING_55_64"));
            graphicLabels.put("sixtyFiveAndUp", MessageUtil.getMessageString("MeasurementType.METRIC_SCORE_SPEEDING_65_80"));                     
        }
        
        graphicLabels.put("teamTotal", MessageUtil.getMessageString("teamTotal"));
               
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
        TabAction action = findTab("speed");
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
    
    public String getSelectedNewBarDef() {
        TabAction action = findTab("speed");
        ScoreType scoreType = action.getScoreType();
        TimeFrame timeFrame = teamCommonBean.getTimeFrame();
        
        if (getNewBarDefMap().get(scoreType) == null) {
            
            getNewBarDefMap().put(scoreType,  new HashMap<String, String>());
        }
        if (newBarDefMap.get(scoreType).get(timeFrame.name()) == null){
            
            newBarDefMap.get(scoreType).put(timeFrame.name(), createBarChart(scoreType));            
        }
        return getNewBarDefMap().get(scoreType).get(timeFrame.name());
    }

    public String getNewBarDef(Integer type) {
       return getNewBarDefMap().get(ScoreType.valueOf(type)).get(teamCommonBean.getTimeFrame().getDuration());
    }         
    
    public String createPieChart(ScoreType scoreType) {
        List<ScoreableEntity> scoreDataList = null;        
        try {
            logger.debug("TeamPieChartBean 2d score groupID[" + getGroupID() + "] scoreType " + scoreType);  
            scoreDataList = getScoreableEntitiesPie();                 
        }
        catch (Exception e) {
            scoreDataList = new ArrayList<ScoreableEntity>();
        }

        // Create the pie string
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();
        
        // Control parameters
        sb.append(pie.getControlParameters());
        sb.append(" caption = \'Speeding Count by Speed Limit\'>");
               
        if (scoreDataList.size() > 0) {
            ScoreableEntity se = null;
            for (int i = 0; i < scoreDataList.size(); i++)
            {
                se = (ScoreableEntity) scoreDataList.get(i);
                Integer percent = se.getScore();
                
                if(percent == 0) // Do not display 0% pie slices.
                    continue;
                sb.append("<set value=\'" + percent.toString() + "\' " + "label=\'\'" + 
                        " color=\'" + (OverallScoreBean.entityColorKey.get(i)) + "\'/>");
            }
        }
        
        sb.append(pie.getClose());
        return sb.toString();
    }
    
    public String createBarChart(ScoreType scoreType) {
        List<Integer> milesDriven = null;
        List<Integer> milesSpeeding = null;
        
        try {
            logger.debug("TeamPieChartBean 2d score groupID[" + getGroupID() + "] scoreType " + scoreType);             
            milesDriven = getMilesDriven();
            milesSpeeding = getMilesSpeeding(); 
        }
        catch (Exception e) {
            milesDriven = new ArrayList<Integer>();
            milesSpeeding = new ArrayList<Integer>();
        }

        // Create the bar string
        StringBuffer sb = new StringBuffer();
        Bar2DMultiAxisChart bar = new Bar2DMultiAxisChart(null,null);
        
        // Control parameters
        sb.append(bar.getControlParameters());
        sb.append(" baseFont=\'Verdana\' ");
        sb.append(" baseFontSize=\'12\' ");
        sb.append(" caption=\"Speeding Distance by Speed Limit (");
        sb.append(MessageUtil.getMessageString(this.getMeasurementType()+"_mph"));       
        sb.append(")\">");
        
        // Categories
        sb.append("<categories>");
        sb.append("<category label=\"");sb.append(graphicLabels.get("zeroToThirty"));sb.append("\"/>");        
        sb.append("<category label=\"");sb.append(graphicLabels.get("thirtyOneToFourty"));sb.append("\"/>");  
        sb.append("<category label=\"");sb.append(graphicLabels.get("fourtyOneToFiftyFour"));sb.append("\"/>");  
        sb.append("<category label=\"");sb.append(graphicLabels.get("fiftyFiveToSixtyFour"));sb.append("\"/>");  
        sb.append("<category label=\"");sb.append(graphicLabels.get("sixtyFiveAndUp"));sb.append("\"/>");          
        sb.append("</categories>");

        // Miles speeding
        sb.append("<dataset>");
        sb.append("<dataset seriesName=\"Speeding Distance\" color=\"FF0000\" showValues=\"0\">");
        for (int i = 0; i < milesSpeeding.size(); i++)
        {            
            Number miles = MeasurementConversionUtil.convertMilesToKilometers(milesSpeeding.get(i), this.getMeasurementType());
            sb.append("<set value=\"" + miles.toString() + "\"/>");           
        }
        sb.append("</dataset>");

        // Miles driven
        sb.append("<dataset seriesName=\"Driving Distance\" color=\"00FF00\" showValues=\"0\">");
        for (int i = 0; i < milesDriven.size(); i++)
        {
            Number miles = MeasurementConversionUtil.convertMilesToKilometers(milesDriven.get(i), this.getMeasurementType());
            sb.append("<set value=\"" + miles.toString() + "\"/>");           
        }
        sb.append("</dataset>");
        sb.append("</dataset>");
        
        // Percent speeding
        sb.append("<lineSet seriesName=\"% Distance\"  color=\"0000FF\" showValues=\"0\" lineThickness=\"4\">");
        for (int i= 0; i < milesSpeeding.size(); i++ ) 
        {           
            Float avgOvr = (float)100.0*((float)milesSpeeding.get(i)/(float)milesDriven.get(i));
            sb.append("<set value=\"" + avgOvr.toString() + "\"/>"); 
        }
        sb.append("</lineSet>");
        
        // Close string
        sb.append("</chart>");
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

    public void setNewBarDefMap(Map<ScoreType, Map<String,String>> newBarDefMap) {
        this.newBarDefMap = newBarDefMap;
    }     

    public Map<ScoreType, Map<String,String>> getNewBarDefMap() {
        if (newBarDefMap == null) {
            newBarDefMap = new HashMap<ScoreType, Map<String,String>>();
        }
        return newBarDefMap;
    }

    public void setSpeedOverBarDefMap(Map<ScoreType, Map<String,String>> speedOverBarDefMap) {
        this.speedOverBarDefMap = speedOverBarDefMap;
    }    
    
    public Map<ScoreType, Map<String,String>> getSpeedOverBarDefMap() {
        if (speedOverBarDefMap == null) {
            speedOverBarDefMap = new HashMap<ScoreType, Map<String,String>>();
        }
        return speedOverBarDefMap;
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
        TabAction action = findTab("speed");       
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
        this.speedTotals = this.getSpeedTotals();
    }

    public Integer getTotDrivers() {
        return totDrivers;
    }

    public void setTotDrivers(Integer totDrivers) {
        this.totDrivers = totDrivers;
    }

    public Integer getTotDriversSpeeding() {
        return totDriversSpeeding;
    }

    public void setTotDriversSpeeding(Integer totDriversSpeeding) {
        this.totDriversSpeeding = totDriversSpeeding;
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
    
    public List<HashMap> getSpeedTotals() {
        ArrayList<HashMap> speedTot = new ArrayList<HashMap>();
        HashMap<String,String> totals = new HashMap<String,String>();
                
        int zeroToThirty = 0;
        int thirtyOneToFourty = 0;
        int fourtyOneToFiftyFour = 0;
        int fiftyFiveToSixtyFour = 0;
        int sixtyFiveAndUp = 0;
                
        this.totDrivers = 0;
        this.totDriversSpeeding = 0;
        
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            totDrivers++;
            
            if ( dvsw.getScore().getSpeedEvents() != null && dvsw.getScore().getSpeedEvents().intValue() > 0 ) {
                
                totDriversSpeeding++;
                
                if ( dvsw.getScore().getSpeedEvents1() != null ) {
                    zeroToThirty += dvsw.getScore().getSpeedEvents1().intValue();
                }
                if ( dvsw.getScore().getSpeedEvents2() != null ) {
                    thirtyOneToFourty += dvsw.getScore().getSpeedEvents2().intValue();
                           }
                if ( dvsw.getScore().getSpeedEvents3() != null ) {
                    fourtyOneToFiftyFour += dvsw.getScore().getSpeedEvents3().intValue();
                }
                if ( dvsw.getScore().getSpeedEvents4() != null ) {
                    fiftyFiveToSixtyFour += dvsw.getScore().getSpeedEvents4().intValue();
                }
                if ( dvsw.getScore().getSpeedEvents5() != null ) {
                    sixtyFiveAndUp += dvsw.getScore().getSpeedEvents5().intValue();
                }
            }
        }
               
        totals.put("zeroToThirty", Integer.toString(zeroToThirty));
        totals.put("thirtyOneToFourty", Integer.toString(thirtyOneToFourty));
        totals.put("fourtyOneToFiftyFour", Integer.toString(fourtyOneToFiftyFour));
        totals.put("fiftyFiveToSixtyFour", Integer.toString(fiftyFiveToSixtyFour));
        totals.put("sixtyFiveAndUp", Integer.toString(sixtyFiveAndUp));
        totals.put("total", Integer.toString(zeroToThirty + thirtyOneToFourty + fourtyOneToFiftyFour + fiftyFiveToSixtyFour + sixtyFiveAndUp));
        
        speedTot.add(totals);
        
        return speedTot;
    }
    
    public HashMap getGraphicLabels() {
        return graphicLabels;
    }

    public void setGraphicLabels(HashMap graphicLabels) {
        this.graphicLabels = graphicLabels;
    }

    private List<ScoreableEntity> getScoreableEntitiesPie() {
        List<ScoreableEntity> local = new ArrayList<ScoreableEntity>();
        
        List<HashMap> observations = speedTotals;
        int totObs = 0;
        HashMap observation = observations.get(0);
        
        String zeroToThirty = (String)observation.get("zeroToThirty");
        totObs += Integer.parseInt(zeroToThirty);
        String thirtyoneToFourty = (String)observation.get("thirtyOneToFourty");
        totObs += Integer.parseInt(thirtyoneToFourty);
        String fourtyoneToFiftyFour = (String)observation.get("fourtyOneToFiftyFour");
        totObs += Integer.parseInt(fourtyoneToFiftyFour);
        String fiftyfiveToSixtyFour = (String)observation.get("fiftyFiveToSixtyFour");
        totObs += Integer.parseInt(fiftyfiveToSixtyFour);
        String sixtyFiveAndUp = (String)observation.get("sixtyFiveAndUp");
        totObs += Integer.parseInt(sixtyFiveAndUp);
        
        ScoreableEntity se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(zeroToThirty)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(thirtyoneToFourty)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(fourtyoneToFiftyFour)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(fiftyfiveToSixtyFour)/totObs);
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((100)*Integer.parseInt(sixtyFiveAndUp)/totObs);
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
           
            if ( (dvsw.getScore().getSpeeding() != null) && 
                 (dvsw.getScore().getSpeeding().intValue() >= 0) ) {
                totScoreableDrivers++;
                totalScores += dvsw.getScore().getSpeeding().intValue();
            }
        }
        
        se.setScore( (totScoreableDrivers != 0)? totalScores/totScoreableDrivers:0);
        
        return se;
    }
    
    private List<Integer> getMilesSpeeding() {
        ArrayList<Integer> miles = new ArrayList<Integer>();
                
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        int zeroToThirty = 0;
        int thirtyOneToFourty = 0;
        int fourtyOneToFiftyFour = 0;
        int fiftyFiveToSixtyFour = 0;
        int sixtyFiveAndUp = 0;
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if ( dvsw.getScore().getSpeedOdometer1() != null ) {
                zeroToThirty += dvsw.getScore().getSpeedOdometer1().intValue();
            }
            
            if ( dvsw.getScore().getSpeedOdometer2() != null ) {
                thirtyOneToFourty += dvsw.getScore().getSpeedOdometer2().intValue();
            }
            
            if ( dvsw.getScore().getSpeedOdometer3() != null ) {
                fourtyOneToFiftyFour += dvsw.getScore().getSpeedOdometer3().intValue();
            }
            
            if ( dvsw.getScore().getSpeedOdometer4() != null ) {
                fiftyFiveToSixtyFour += dvsw.getScore().getSpeedOdometer4().intValue();
            }
            
            if ( dvsw.getScore().getSpeedOdometer5() != null ) {
                sixtyFiveAndUp += dvsw.getScore().getSpeedOdometer5().intValue();
            } 
              
        }
        
        miles.add(new Integer(zeroToThirty));
        miles.add(new Integer(thirtyOneToFourty));
        miles.add(new Integer(fourtyOneToFiftyFour));
        miles.add(new Integer(fiftyFiveToSixtyFour));
        miles.add(new Integer(sixtyFiveAndUp));
        
        return miles;
    }
       
    private List<Integer> getMilesDriven() {
        ArrayList<Integer> miles = new ArrayList<Integer>();
                
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        int zeroToThirty = 0;
        int thirtyOneToFourty = 0;
        int fourtyOneToFiftyFour = 0;
        int fiftyFiveToSixtyFour = 0;
        int sixtyFiveAndUp = 0;
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if ( dvsw.getScore().getOdometer1() != null ) {
                zeroToThirty += dvsw.getScore().getOdometer1().intValue();
            }
            
            if ( dvsw.getScore().getOdometer2() != null ) {
                thirtyOneToFourty += dvsw.getScore().getOdometer2().intValue();
            }
            
            if ( dvsw.getScore().getOdometer3() != null ) {
                fourtyOneToFiftyFour += dvsw.getScore().getOdometer3().intValue();
            }
            
            if ( dvsw.getScore().getOdometer4() != null ) {
                fiftyFiveToSixtyFour += dvsw.getScore().getOdometer4().intValue();
            }
            
            if ( dvsw.getScore().getOdometer5() != null ) {
                sixtyFiveAndUp += dvsw.getScore().getOdometer5().intValue();
            } 
              
        }
        
        miles.add(new Integer(zeroToThirty));
        miles.add(new Integer(thirtyOneToFourty));
        miles.add(new Integer(fourtyOneToFiftyFour));
        miles.add(new Integer(fiftyFiveToSixtyFour));
        miles.add(new Integer(sixtyFiveAndUp));
        
        return miles;
    }    
}
