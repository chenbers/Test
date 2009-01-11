package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.faces.context.FacesContext;

import com.inthinc.pro.charts.Line;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.wrapper.MpgEntityPkg;

public class GraphicUtil {
	private static List <String> monthLbls = new ArrayList<String>() {
		{
			add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
			add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
			add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
			add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
		}
	};
	public static List <String> entityColorKey = new ArrayList<String>(){
		{
			add(new String("#880000"));add(new String("#008800"));
			add(new String("#000088"));add(new String("#888800"));
			add(new String("#880088"));add(new String("#008888"));
			add(new String("#FF0000"));add(new String("#00FF00"));
			add(new String("#0000FF"));add(new String("#FF00FF"));	
		}
	};

	public static String createFakeXYData(int num) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		
		//This will create floats between 0 and 5>>the 3 in the substring
		for (int i = 0; i < num; i++ ) {
			float f = 5.0f*r.nextFloat();
			sb.append("<set value=\'");
			sb.append(((new Float(f)).toString()).substring(0,3));
			sb.append("'/>");			
		}
		
		return sb.toString();
	}
	
	public static String createMonthsString(Duration duration) {
		String lbl = new String();
		StringBuffer sb = new StringBuffer();
		
		List<String> monthList = createMonthList(duration);
		for(String month:monthList)
		{
		    sb.append("<category label=\'"); 
		    sb.append(month);
            sb.append("\'/>");
		}
		
//        if ( duration == Duration.DAYS ) {
//            for ( int i = 1; i < 31; i++ ) {
//                sb.append("<category label=\'");
//                sb.append(String.valueOf(i));
//                sb.append("\'/>");
//            }       
//        } else {
//            int num = convertToMonths(duration);
//                        
//            //What month is it? Remember, implemented with jan as 0. Also,
//            //add 12 so the short date ranges work...
//            GregorianCalendar calendar = new GregorianCalendar();       
//            int currentMonth = calendar.get(GregorianCalendar.MONTH)+1;
//            
//            //Start there
//            for ( int i = currentMonth + 12 - num; i < currentMonth + 12 ; i++ ) {
//                sb.append("<category label=\'");
//                sb.append(monthLbls.get(i));
//                sb.append("\'/>");
//            }           
//        }
		
		return sb.toString();
	}
	
	public static List<String> createMonthList(Duration duration)
	{
	    List<String> monthList = new ArrayList<String>();
	    
	    if ( duration == Duration.DAYS ) {
            for ( int i = 1; i < 31; i++ ) {
                monthList.add(String.valueOf(i));
            }       
        } else {
            int num = convertToMonths(duration);             
            //What month is it? Remember, implemented with jan as 0. Also,
            //add 12 so the short date ranges work...
            GregorianCalendar calendar = new GregorianCalendar();       
            int currentMonth = calendar.get(GregorianCalendar.MONTH)+1;
            
            //Start there
            for ( int i = currentMonth + 12 - num; i < currentMonth + 12 ; i++ ) {
                monthList.add(monthLbls.get(i));
            }           
        }
	    
	    return monthList;
	}
	
	public static int convertToMonths(Duration duration) {
	    int months = 0;
	    
	    if (           duration.equals(Duration.THREE) ) {
	        return 3;
	    } else if (    duration.equals(Duration.SIX) ) {
	        return 6;
	    } else if (    duration.equals(Duration.TWELVE) ) {
	        return 12;
	    }
	    
	    return months;
	}
	
	public static String getXYControlParameters() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<chart ");
		sb.append("caption=\'\' ");
		sb.append("subCaption=\'\' ");
		sb.append("lineThickness=\'4\' ");
		sb.append("showValues=\'0\' ");
		sb.append("showBorder=\'0\' ");
		sb.append("drawAnchors=\'0\' ");
		sb.append("borderColor=\'#cfcfcf\' ");
		sb.append("vDivLineColor=\'#cfcfcf\' ");
		sb.append("vDivLineThickness=\'1\' ");
		sb.append("showAlternateHGridColor=\'1\' ");
		sb.append("alternateHGridColor=\'#f0f0f0\' ");
		sb.append("alternateHGridAlpha=\'100\' ");
		sb.append("slantLabels='1' ");                    //TESTING
		sb.append("labelDisplay='Rotate' ");              //TESTING
		sb.append("divLineColor=\'cfcfcf\' ");
		sb.append("divLineIsDashed=\'1\' ");
		sb.append("imageSaveURL=\'" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/FusionChartsSave.jsp\'");
		sb.append("imageSave=\'1\'");	 
		sb.append("animation=\'1\' ");
		sb.append("bgColor=\'#ffffff\' ");
		sb.append("borderThickness=\'1\' ");
		sb.append("showToolTips=\'1\' ");
		sb.append("showLegend=\'0\' ");
		sb.append("forceDecimals=\'1\' ");
		sb.append("yAxisMaxValue=\'5\' ");
		sb.append("chartLeftMargin=\'10\' ");
		sb.append("chartRightMargin=\'10\' ");
		sb.append("chartTopMargin=\'10\' ");
		sb.append("chartBottomMargin=\'10\' ");
		sb.append("canvasPadding=\'0\' ");

		sb.append("setAdaptiveYMin=\'0\' ");
			
		sb.append("numDivLines=\'9\' ");
		sb.append("adjustDiv='0'>");
		
		return sb.toString();
	}
	
	public static String getPieControlParameters() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<chart ");				
		sb.append("showBorder=\'0\' ");
		sb.append("decimals=\'0\' ");
		sb.append("enableSmartLabels=\'1\' ");
		sb.append("enableRotation=\'1\' ");
		sb.append("startingAngle=\'90\' ");
		sb.append("use3DLighting=\'1\' ");
		sb.append("radius3D=\'30\' ");
		sb.append("imageSaveURL=\'" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/FusionChartsSave.jsp\'");
        sb.append("imageSave=\'1\'");
		sb.append("showPercentValues=\'1\' ");
		sb.append("smartLineThickness=\'1\' ");
		sb.append("smartLineColor=\'333333\' ");
		sb.append("baseFont=\'Verdana\' ");
		sb.append("baseFontSize=\'12\' ");
		sb.append("labelDistance=\'10\' ");
		sb.append("bgColor=\'#ffffff\'>");
		
		return sb.toString();
	}
    
    public static String createLineControlParameters()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<chart ");
        sb.append("caption=\'\' ");
        sb.append("subcaption=\'\' ");
        sb.append("xAxisName=\'\' ");
        sb.append("yAxisMinValue=\'0\' ");
        sb.append("yAxisName=\'\' ");
        sb.append("numberPrefix=\'\' ");
        sb.append("showValues=\'0\' ");
        sb.append("adjustDiv=\'0\' ");
        sb.append("setAdaptiveYMin='0' ");
        sb.append("borderColor=\'#cfcfcf\' ");
        sb.append("vDivLineColor=\'#cfcfcf\' ");
        sb.append("vDivLineThickness=\'1\' ");
        sb.append("showAlternateHGridColor=\'1\' ");
        sb.append("alternateHGridColor=\'#f0f0f0\' ");
        sb.append("alternateHGridAlpha=\'100\' ");
        sb.append("forceDecimals=\'1\' ");
        sb.append("yAxisMaxValue=\'5\' ");
        sb.append("bgColor=\'#ffffff\' ");
        sb.append("showBorder=\'0\' ");
        sb.append("lineColor=\'#93C034\' ");
        sb.append("lineThickness=\'2\' ");
        sb.append("drawAnchors=\'0\' ");
        sb.append("numVDivLines=\'4\' ");
        sb.append("plotFillColor=\'#A8C634\' >");
        
        return sb.toString();
    }

    public static String createMiniLineControlParameters()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<chart ");
        sb.append("caption=\'\' ");
        sb.append("subcaption=\'\' ");
        sb.append("xAxisName=\'\' ");
        sb.append("yAxisMinValue=\'0\' ");
        sb.append("yAxisName=\'\' ");
        sb.append("numberPrefix=\'\' ");
        sb.append("showValues=\'0\' ");
        sb.append("adjustDiv=\'0\' ");
        sb.append("setAdaptiveYMin='0' ");
        sb.append("borderColor=\'#cfcfcf\' ");
        sb.append("vDivLineColor=\'#cfcfcf\' ");
        sb.append("vDivLineThickness=\'1\' ");
        sb.append("showAlternateHGridColor=\'1\' ");
        sb.append("alternateHGridColor=\'#f0f0f0\' ");
        sb.append("alternateHGridAlpha=\'100\' ");
        sb.append("forceDecimals=\'1\' ");
        sb.append("yAxisMaxValue=\'5\' ");
        sb.append("bgColor=\'#ffffff\' ");
        sb.append("showBorder=\'0\' ");
        sb.append("lineColor=\'#93C034\' ");
        sb.append("lineThickness=\'2\' ");
        sb.append("drawAnchors=\'0\' ");
        sb.append("numVDivLines=\'4\' >");

        return sb.toString();
    }

    public static String createFakeMiniLineData()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<categories>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append("</categories>");

        sb.append("<dataset seriesName=\"Light\" color=\"B1D1DC\" plotBorderColor=\"B1D1DC\"> ");
        sb.append("<set value=\"42\"/>");
        sb.append("<set value=\"45\"/>");
        sb.append("<set value=\"33\"/>");
        sb.append("<set value=\"29\"/>");
        sb.append("<set value=\"36\"/>");
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\"Medium\" color=\"C8A1D1\" plotBorderColor=\"C8A1D1\"> ");
        sb.append("<set value=\"22\"/>");
        sb.append("<set value=\"27\"/>");
        sb.append("<set value=\"33\"/>");
        sb.append("<set value=\"37\"/>");
        sb.append("<set value=\"33\"/>");
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\"Heavy\" color=\"A8C634\" plotBorderColor=\"A8C634\"> ");
        sb.append("<set value=\"17\"/>");
        sb.append("<set value=\"22\"/>");
        sb.append("<set value=\"26\"/>");
        sb.append("<set value=\"22\"/>");
        sb.append("<set value=\"23\"/>");
        sb.append("</dataset>");

        return sb.toString();

    }
	
	public static String createFakeLineData()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'19600\' />");
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'19700\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'20000\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'21000\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'21135\' />");
		
		return sb.toString();
		
	}
	
	public static String createFakePieData() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<set value=\'18\' label=\'\' color=\'FF0101\'/>");
		sb.append("<set value=\'17\' label=\'\' color=\'6B9D1B\'/>");
		sb.append("<set value=\'10\'  label=\'\' color=\'1E88C8\'/>");
		sb.append("<set value=\'35\'  label=\'\' color=\'F6B305\'/>");
		sb.append("<set value=\'20\'  label=\'\' color=\'FF6601\'/>");
		
		return sb.toString();
	}
		
	public static String getBarControlParameters() {
        StringBuffer sb = new StringBuffer();
        sb.append("<chart ");
        sb.append("caption=\'\' ");
        sb.append("xAxisName=\'\' ");
        sb.append("yAxisName=\'\' ");
        sb.append("showValues=\'0\' ");
        sb.append("decimals=\'0\' ");
        sb.append("formatNumberScale=\'0\' ");
        sb.append("numberSuffix=\'\' ");
        sb.append("animation=\'1\' ");
        sb.append("bgColor=\'#ffffff\' ");
        sb.append("borderThickness=\'1\' ");
        sb.append("showToolTips=\'1\' ");
        sb.append("showLegend=\'0\' ");
        sb.append("legendBorderThickness=\'0\' ");
        sb.append("legendShadow=\'0\' ");
        sb.append("labelDisplay=\'\' ");
        sb.append("slantLabels=\'\' ");
        sb.append("canvasBorderColor=\'#ffffff\' ");
        sb.append("canvasBorderThickness=\'0\' ");
        sb.append("showPlotBorder=\'0\' ");
        sb.append("plotFillRatio=\'100\' ");
        sb.append(">");
        return sb.toString();
	}	
	
	public static String createMpgXML(List<MpgEntityPkg> entities)
	{
	    StringBuffer sb = new StringBuffer();
	    int numEntities = entities.size();
	    Integer[] light = new Integer[numEntities];
	    Integer[] medium = new Integer[numEntities];
	    Integer[] heavy = new Integer[numEntities];

        sb.append("<categories>");
        for (int i = 0; i < numEntities; i++)
        {
            MpgEntityPkg pkg = entities.get(i);
            MpgEntity entity = pkg.getEntity();
            light[i] = entity.getLightValue();
            medium[i] = entity.getMediumValue();
            heavy[i] = entity.getHeavyValue();
            sb.append("<category label=\'" + entity.getEntityName() + "\'/>");
        }
        sb.append("</categories>");
        
        sb.append("<dataset seriesName=\'Light\'>");
        for (Integer value : light)
        {
            sb.append("<set value=\'" + (value != null ? value.toString() : "0") + "\'/>");
        }
        sb.append("</dataset>");
        
        sb.append("<dataset seriesName=\'Medium\'>");
        for (Integer value : medium)
        {
            sb.append("<set value=\'" + (value != null ? value.toString() : "0") + "\'/>");
        }
        sb.append("</dataset>");
        
        sb.append("<dataset seriesName=\'Heavy\'>");
        for (Integer value : heavy)
        {
            sb.append("<set value=\'" + (value != null ? value.toString() : "0") + "\'/>");
        }
        sb.append("</dataset>");
        return sb.toString();
	}
		
	public static String createFakeBarData() {
		StringBuffer sb = new StringBuffer();

        sb.append("<categories>");
        sb.append("<category label=\'One\'/>");
        sb.append("<category label=\'Two\'/>");
        sb.append("<category label=\'Three\'/>");
        sb.append("</categories>");


        sb.append("<dataset seriesName=\'Light\'>");
        sb.append("<set value=\'23\'/>");
        sb.append("<set value=\'45\'/>");
        sb.append("<set value=\'33\'/>");
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\'Medium\'>");
        sb.append("<set value=\'33\'/>");
        sb.append("<set value=\'55\'/>");
        sb.append("<set value=\'43\'/>");
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\'Heavy\'>");
        sb.append("<set value=\'55\'/>");
        sb.append("<set value=\'12\'/>");
        sb.append("<set value=\'38\'/>");
        sb.append("</dataset>");
		return sb.toString();
	}
	
	public static Integer getRandomScore()
	{
        return MiscUtil.randomInt(0, 50);
	    
	}
	

	public static final double roundDouble(double d, int places) 
	{
	    return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
	}
	
	public static final int getHoles(Duration d, int size)
	{
        int holes = 0;
        if ( d == Duration.DAYS)
        {
            holes = d.getNumberOfDays() - size;            
        }
        else
        {
            holes = GraphicUtil.convertToMonths(d) - size;
        }   
        
        return holes;
	}
	
	public static final String pad(int holes,List<String> monthList)
	{
	    Line line = new Line();
	    StringBuffer sb = new StringBuffer();
	    
        for (int k = 0; k < holes; k++)
        {            
            String itm = line.getChartItem(new Object[] { (double) (0.0d), 
                    monthList.get(k) });
            sb.append(itm);
        }
        
        return sb.toString();
	}
	
	public static final int getDurationSize(Duration d) 
	{
	    int size = 0;
	    
        if ( d == Duration.DAYS ) {
            size = 30;     
        } else {
            size = convertToMonths(d);                    
        }
        
        return size;	    
	}
}