package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.Bar2DMultiAxisChart;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.util.MessageUtil;

public class TeamSpeedBean extends BaseBean {
    
//  Request scope bean for new team page 
    private static final long serialVersionUID = -2065512595055745287L;
    
    private Map<ScoreType, Map<String,String>> pieDefMap;
    private Map<ScoreType, Map<String,String>> newBarDefMap;
    private Map<ScoreType, Map<String,Integer>> overallScoreMap;
    
    private TeamCommonBean teamCommonBean;
    
    private HashMap<String,String> graphicLabels;
    
    private Integer totDrivers;
    private Integer totDriversSpeeding;
    
    private ScoreType scoreType = ScoreType.SCORE_SPEEDING;

    private static final Logger logger = Logger.getLogger(TeamSpeedBean.class);

    public TeamSpeedBean() {
        
        // Get the labels for the graphs
        graphicLabels = new HashMap<String,String>();
        
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

    public String getPieDef(Integer type) {
       return getPieDefMap().get(ScoreType.valueOf(type)).get(teamCommonBean.getTimeFrame().getDuration());
    }
    
    public String getSelectedNewBarDef() {
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
        List<ScoreableEntity> scoreDataList = getScoreableEntitiesPie(); 
        List<String> worstOffenders = getWorstOffenders();

        // Create the pie string
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();
        
        // Control parameters
        sb.append(pie.getControlParameters());
        sb.append(" caption = \'" + MessageUtil.getMessageString("teamSpeedPieLabel") + "\'>");
               
        if (scoreDataList.size() > 0) {
            ScoreableEntity se = null;
            for (int i = 0; i < scoreDataList.size(); i++)
            {
                se = (ScoreableEntity) scoreDataList.get(i);
                Integer percent = se.getScore();
                
                if(percent == 0) // Do not display 0% pie slices.
                    continue;
                sb.append("<set value=\'" + percent.toString() + "\' " + "toolText=\'Worst offender: " + 
                        worstOffenders.get(i)+ "\'" +                                                 
                        " color=\'" + (OverallScoreBean.entityColorKey.get(i)) + "\'/>");
            }
        }
        
        sb.append(pie.getClose());
        return sb.toString();
    }
    
    public String createBarChart(ScoreType scoreType) {
        // Snag data
        List<Float> milesDriven = getMilesDriven();
        List<Float> milesSpeeding = getMilesSpeeding(); 

        // Create the bar string
        StringBuffer sb = new StringBuffer();
        Bar2DMultiAxisChart bar = new Bar2DMultiAxisChart(null,null);
        
        // Control parameters
        sb.append(bar.getControlParameters());
        sb.append(" baseFont=\'Verdana\' ");
        sb.append(" baseFontSize=\'12\' ");
        sb.append(" caption=\'");
        sb.append(MessageUtil.getMessageString("teamSpeedBarLabelTop"));                
        sb.append(" (");
        sb.append(MessageUtil.getMessageString(this.getMeasurementType()+"_mph"));       
        sb.append(")\'>");
        
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
        sb.append("<dataset seriesName=\'");
        sb.append(MessageUtil.getMessageString("teamSpeedBarSpeedingDistance"));
        sb.append("\' color=\"FF0000\" showValues=\"0\">");
        for (int i = 0; i < milesSpeeding.size(); i++)
        {            
            Number miles = MeasurementConversionUtil.convertMilesToKilometers(milesSpeeding.get(i), this.getMeasurementType());
            sb.append("<set value=\"" + miles.toString() + "\"/>");           
        }
        sb.append("</dataset>");

        // Miles driven
        sb.append("<dataset seriesName=\'");
        sb.append(MessageUtil.getMessageString("teamSpeedBarDrivingDistance"));
        sb.append("\' color=\"00FF00\" showValues=\"0\">");
        for (int i = 0; i < milesDriven.size(); i++)
        {
            Number miles = MeasurementConversionUtil.convertMilesToKilometers(milesDriven.get(i), this.getMeasurementType());
            sb.append("<set value=\"" + miles.toString() + "\"/>");           
        }
        sb.append("</dataset>");
        sb.append("</dataset>");
        
        // Percent speeding
        sb.append("<lineSet seriesName=\'");
        sb.append(MessageUtil.getMessageString("teamSpeedBarPercentDistance"));
        sb.append("\' color=\"0000FF\" showValues=\"0\" lineThickness=\"4\">");
        
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

    public Map<ScoreType, Map<String,String>> getPieDefMap() {
        if (pieDefMap == null) {
            pieDefMap = new HashMap<ScoreType, Map<String,String>>();
        }
        return pieDefMap;
    }

    public void setPieDefMap(Map<ScoreType, Map<String,String>> pieDefMap) {
        this.pieDefMap = pieDefMap;
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

    public String exportToPDF() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<HashMap<String,String>> getSpeedTotals() {
        ArrayList<HashMap<String,String>> speedTot = new ArrayList<HashMap<String,String>>();
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
    
    public HashMap<String,String> getGraphicLabels() {
        return graphicLabels;
    }

    public void setGraphicLabels(HashMap<String,String> graphicLabels) {
        this.graphicLabels = graphicLabels;
    }

    private List<ScoreableEntity> getScoreableEntitiesPie() {
        List<ScoreableEntity> local = new ArrayList<ScoreableEntity>();
        
        List<HashMap<String,String>> observations = this.getSpeedTotals();
        
        int totObs = 0;
        HashMap<String,String> observation = observations.get(0);
        
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
        double factor = 100.0/totObs;
      
        se.setScore((int)(Math.round(factor*Integer.parseInt(zeroToThirty))));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(thirtyoneToFourty)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(fourtyoneToFiftyFour)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(fiftyfiveToSixtyFour)));
        local.add(se);
        se = new ScoreableEntity();
        
        se.setScore((int)Math.round(factor*Integer.parseInt(sixtyFiveAndUp)));
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
    
    protected List<Float> getMilesSpeeding() {
        ArrayList<Float> miles = new ArrayList<Float>();
                
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        float zeroToThirty = 0;
        float thirtyOneToFourty = 0;
        float fourtyOneToFiftyFour = 0;
        float fiftyFiveToSixtyFour = 0;
        float sixtyFiveAndUp = 0;
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if ( dvsw.getScore().getSpeedOdometer1() != null ) {
                zeroToThirty += (float)dvsw.getScore().getSpeedOdometer1().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getSpeedOdometer2() != null ) {
                thirtyOneToFourty += dvsw.getScore().getSpeedOdometer2().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getSpeedOdometer3() != null ) {
                fourtyOneToFiftyFour += dvsw.getScore().getSpeedOdometer3().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getSpeedOdometer4() != null ) {
                fiftyFiveToSixtyFour += dvsw.getScore().getSpeedOdometer4().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getSpeedOdometer5() != null ) {
                sixtyFiveAndUp += dvsw.getScore().getSpeedOdometer5().intValue()/100.0;
            } 
              
        }
        
        miles.add(new Float(zeroToThirty));
        miles.add(new Float(thirtyOneToFourty));
        miles.add(new Float(fourtyOneToFiftyFour));
        miles.add(new Float(fiftyFiveToSixtyFour));
        miles.add(new Float(sixtyFiveAndUp));
        
        return miles;
    }
       
    protected List<Float> getMilesDriven() {
        ArrayList<Float> miles = new ArrayList<Float>();
                
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        float zeroToThirty = 0;
        float thirtyOneToFourty = 0;
        float fourtyOneToFiftyFour = 0;
        float fiftyFiveToSixtyFour = 0;
        float sixtyFiveAndUp = 0;
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if ( dvsw.getScore().getOdometer1() != null ) {
                zeroToThirty += (float)dvsw.getScore().getOdometer1().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getOdometer2() != null ) {
                thirtyOneToFourty += (float)dvsw.getScore().getOdometer2().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getOdometer3() != null ) {
                fourtyOneToFiftyFour += (float)dvsw.getScore().getOdometer3().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getOdometer4() != null ) {
                fiftyFiveToSixtyFour += (float)dvsw.getScore().getOdometer4().intValue()/100.0;
            }
            
            if ( dvsw.getScore().getOdometer5() != null ) {
                sixtyFiveAndUp += (float)dvsw.getScore().getOdometer5().intValue()/100.0;
            } 
              
        }
        
        miles.add(new Float(zeroToThirty));
        miles.add(new Float(thirtyOneToFourty));
        miles.add(new Float(fourtyOneToFiftyFour));
        miles.add(new Float(fiftyFiveToSixtyFour));
        miles.add(new Float(sixtyFiveAndUp));
        
        return miles;
    }  

    private List<String> getWorstOffenders() {
        List<String> worst = new ArrayList<String>();
        
        List<DriverVehicleScoreWrapper> local = 
            teamCommonBean.getCachedResults().get(teamCommonBean.getTimeFrame().name());
        
        String zeroToThirty = "None found";
        int zeroToThirtyCount = 0;
        String thirtyOneToFourty = "None found";
        int thirtyOneToFourtyCount = 0;
        String fourtyOneToFiftyFour = "None found";
        int fourtyOneToFiftyFourCount = 0;
        String fiftyFiveToSixtyFour = "None found";
        int fiftyFiveToSixtyFourCount = 0;
        String sixtyFiveAndUp = "None found";
        int sixtyFiveAndUpCount = 0;
        
        for ( DriverVehicleScoreWrapper dvsw: local ) {
            
            if ( dvsw.getScore().getSpeedEvents1() != null && 
                 dvsw.getScore().getSpeedEvents1().intValue() > zeroToThirtyCount ) {
                zeroToThirty = new String(dvsw.getDriver().getPerson().getFullName());
                zeroToThirtyCount = dvsw.getScore().getSpeedEvents1().intValue();
            }
            
            if ( dvsw.getScore().getSpeedEvents2() != null && 
                 dvsw.getScore().getSpeedEvents2().intValue() > thirtyOneToFourtyCount ) {
                thirtyOneToFourty = new String(dvsw.getDriver().getPerson().getFullName());
                thirtyOneToFourtyCount = dvsw.getScore().getSpeedEvents2().intValue();
            }
            
            if ( dvsw.getScore().getSpeedEvents3() != null && 
                 dvsw.getScore().getSpeedEvents3().intValue() > fourtyOneToFiftyFourCount ) {
                fourtyOneToFiftyFour = new String(dvsw.getDriver().getPerson().getFullName());
                fourtyOneToFiftyFourCount = dvsw.getScore().getSpeedEvents3().intValue();
            }
            
            if ( dvsw.getScore().getSpeedEvents4() != null && 
                 dvsw.getScore().getSpeedEvents4().intValue() > fiftyFiveToSixtyFourCount ) {
                fiftyFiveToSixtyFour = new String(dvsw.getDriver().getPerson().getFullName());
                fiftyFiveToSixtyFourCount = dvsw.getScore().getSpeedEvents4().intValue();
            }
            
            if ( dvsw.getScore().getSpeedEvents5() != null && 
                 dvsw.getScore().getSpeedEvents5().intValue() > sixtyFiveAndUpCount ) {
                sixtyFiveAndUp = new String(dvsw.getDriver().getPerson().getFullName());
                sixtyFiveAndUpCount = dvsw.getScore().getSpeedEvents5().intValue();
            } 
              
        }
        
        worst.add(zeroToThirty + ", " + String.valueOf(zeroToThirtyCount));
        worst.add(thirtyOneToFourty + ", " + String.valueOf(thirtyOneToFourtyCount));
        worst.add(fourtyOneToFiftyFour + ", " + String.valueOf(fourtyOneToFiftyFourCount));
        worst.add(fiftyFiveToSixtyFour + ", " + String.valueOf(fiftyFiveToSixtyFourCount));
        worst.add(sixtyFiveAndUp + ", " + String.valueOf(sixtyFiveAndUpCount));
        
        return worst;
    }    
}
