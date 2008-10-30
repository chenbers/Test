package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.inthinc.pro.model.Duration;

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
		
        if ( duration == Duration.DAYS ) {
            for ( int i = 1; i < 31; i++ ) {
                sb.append("<category label=\'");
                sb.append(String.valueOf(i));
                sb.append("\'/>");
            }       
        } else {
            int num = convertToMonths(duration);
                        
            //What month is it? Remember, implemented with jan as 0. Also,
            //add 12 so the short date ranges work...
            GregorianCalendar calendar = new GregorianCalendar();       
            int currentMonth = calendar.get(GregorianCalendar.MONTH)+1;
            
            //Start there
            for ( int i = currentMonth + 12 - num; i < currentMonth + 12 ; i++ ) {
                sb.append("<category label=\'");
                sb.append(monthLbls.get(i));
                sb.append("\'/>");
            }           
        }
		
		return sb.toString();
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
			
		sb.append("divLineColor=\'cfcfcf\' ");
		sb.append("divLineIsDashed=\'1\' ");
			 
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
	
	public static String createFakeLineData()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'APR\' />");
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'MAY\' />");
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'JUN\' />");
		sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'JUL\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'AUG\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'SEP\' />");
	    sb.append("<set value=\'" + getRandomScore().toString() + "' label=\'OCT\' />");
		
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
		sb.append("numberSuffix=\'%\' ");
		sb.append("yAxisMaxValue=\'100\'>");
		
		return sb.toString();
	}	
		
	public static String createFakeBarData() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<set label=\'West Coast\' value=\'93\' color=\'7FC0CE\' />");
		sb.append("<set label=\'Midwest\' value=\'98\' color=\'DDDAC9\' />");
		sb.append("<set label=\'Lakes\' value=\'89\' color=\'84949F\' />");
		sb.append("<set label=\'South\' value=\'92\' color=\'A093B4\' />");
		sb.append("<set label=\'New England\' value=\'85\' color=\'A6B493\' />");
		
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
}