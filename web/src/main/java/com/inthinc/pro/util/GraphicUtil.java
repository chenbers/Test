package com.inthinc.pro.util;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.faces.context.FacesContext;

import com.inthinc.pro.charts.DateLabels;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.BaseScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.MpgEntity;

public class GraphicUtil {

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
	

	public static <T extends BaseScore> List<String> createDateLabelList(List<T> scoreList, Duration duration, Locale locale)
	{
		List<Date> dateList = new ArrayList<Date>();
		for (BaseScore item : scoreList)
		{
			dateList.add(item.getDate());
		}
		
		DateLabels dateLabels = new DateLabels(locale);
		if (duration.equals(Duration.DAYS))
		{
			return dateLabels.createDayLabelList(dateList);
		}
		
		return dateLabels.createMonthLabelList(dateList);
		
	}
	
	public static <T extends BaseScore> List<String> createDateLabelList(List<T> scoreList, Duration duration, String dateFormat, Locale locale)
	{
		List<Date> dateList = new ArrayList<Date>();
		for (BaseScore item : scoreList)
		{
			dateList.add(item.getDate());
		}
		
		DateLabels dateLabels = new DateLabels(locale, dateFormat);
		if (duration.equals(Duration.DAYS))
		{
			return dateLabels.createDayLabelList(dateList);
		}
		
		return dateLabels.createMonthLabelList(dateList);
		
	}
	public static String getXYControlParameters(boolean animate, Locale locale) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<chart ");
		sb.append("caption=\'\' ");
		sb.append("subCaption=\'\' ");
		sb.append("lineThickness=\'4\' ");
		sb.append("showValues=\'0\' ");
		sb.append("showBorder=\'0\' ");
		sb.append("drawAnchors=\'1\' ");
		sb.append("anchorRadius=\'3\' ");
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
		sb.append("imageSaveURL=\'" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/FusionChartsSave.jsp\' ");
		sb.append("imageSave=\'1\' ");
		if(animate)
		{
		    sb.append("animation=\'1\' ");
		}
		else
		{
		    sb.append("animation=\'0\' ");
		}
		sb.append("bgColor=\'#ffffff\' ");
		sb.append("borderThickness=\'1\' ");
		sb.append("showToolTips=\'1\' ");
		sb.append("showLegend=\'0\' ");
		sb.append("decimals=\'1\' forceDecimals=\'1\' ");
		sb.append("yAxisMaxValue=\'5\' ");
	    sb.append("decimalSeparator=\'");
        sb.append(getDecimalSeparator(locale));
        sb.append("\' thousandSeparator=\'");
        sb.append(getGroupingSeparator(locale));
        sb.append("\' ");

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
		sb.append("imageSaveURL=\'" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secured/FusionChartsSave.jsp\' ");
        sb.append("imageSave=\'1\' ");
		sb.append("showPercentValues=\'1\' ");
		sb.append("smartLineThickness=\'1\' ");
		sb.append("smartLineColor=\'333333\' ");
		sb.append("baseFont=\'Verdana\' ");
		sb.append("baseFontSize=\'12\' ");
		sb.append("labelDistance=\'10\' ");
		sb.append("bgColor=\'#ffffff\'>");
		
		return sb.toString();
	}
    
    public static String createLineControlParameters(Locale locale)
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
        sb.append("decimals=\'1\' forceDecimals=\'1\' ");
        sb.append("yAxisMaxValue=\'5\' ");
        sb.append("decimalSeparator=\'");
        sb.append(getDecimalSeparator(locale));
        sb.append("\' thousandSeparator=\'");
        sb.append(getGroupingSeparator(locale));
        sb.append("\' ");
        sb.append("bgColor=\'#ffffff\' ");
        sb.append("showBorder=\'0\' ");
        sb.append("lineColor=\'#93C034\' ");
        sb.append("lineThickness=\'2\' ");
        sb.append("drawAnchors=\'0\' ");
        sb.append("numVDivLines=\'4\' ");
        sb.append("plotFillColor=\'#A8C634\' >");
        
        return sb.toString();
    }

    public static String createMiniLineControlParameters(Locale locale)
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
        sb.append("decimals=\'1\' forceDecimals=\'1\' ");
        sb.append("yAxisMaxValue=\'5\' ");
        sb.append("decimalSeparator=\'");
        sb.append(getDecimalSeparator(locale));
        sb.append("\' thousandSeparator=\'");
        sb.append(getGroupingSeparator(locale));
        sb.append("\' ");
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
		
	public static String getBarControlParameters(Locale locale) {
	    String vehicleType = MessageUtil.getMessageString("mpg_vehicle_type");
        StringBuilder sb = new StringBuilder();
        sb.append("<chart ")
        .append("caption=\'\' ")
        .append("xAxisName=\'\' ")
        .append("yAxisName=\'\' ")
        .append("showValues=\'0\' ")
        .append("decimals=\'1\' ")
       .append("decimalSeparator=\'")
        .append(getDecimalSeparator(locale))
        .append("\' thousandSeparator=\'")
        .append(getGroupingSeparator(locale))
        .append("\' ")
        .append("formatNumberScale=\'0\' ")
        .append("numberSuffix=\'\' ")
        .append("animation=\'1\' ")
        .append("bgColor=\'#ffffff\' ")
        .append("borderThickness=\'1\' ")
        .append("showToolTips=\'1\' ")
        .append("showLegend=\'1\' ")
        .append("legendPosition=\'BOTTOM\' ")
        .append("legendCaption=\'").append(vehicleType).append(":\' ")
        .append("legendMarkerCircle=\'0\' ")
//        .append("legendBgColor=\'E5E5E5\' ")
        .append("legendBorderColor=\'CCCCCC\' ")
        .append("legendBorderThickness=\'0\' ")
        .append("legendShadow=\'0\' ")
        .append("labelDisplay=\'\' ")
        .append("slantLabels=\'\' ")
        .append("canvasBorderColor=\'#ffffff\' ")
        .append("canvasBorderThickness=\'0\' ")
        .append("showPlotBorder=\'0\' ")
        .append("plotFillRatio=\'100\' ");
        
        sb.append(">");
        return sb.toString();
	}	
	
	public static String createMpgXML(List<MpgEntity> entities, MeasurementType measurementType, FuelEfficiencyType fuelEfficiencyType)
	{

        String lightString = MessageUtil.getMessageString("mpg_light_column");
        String mediumString = MessageUtil.getMessageString("mpg_medium_column");
        String heavyString = MessageUtil.getMessageString("mpg_heavy_column");
	    StringBuilder sb = new StringBuilder();
	    int numEntities = entities.size();
	    Double[] light = new Double[numEntities];
	    Double[] medium = new Double[numEntities];
	    Double[] heavy = new Double[numEntities];

        sb.append("<categories>");
        for (int i = 0; i < numEntities; i++)
        {
            MpgEntity entity = entities.get(i);
            light[i] = entity.getLightValue();
            medium[i] = entity.getMediumValue();
            heavy[i] = entity.getHeavyValue();
                  
            sb.append("<category label=\"" + entity.getEntityName().replaceAll("'", "\'") + "\"/>");
        }
        sb.append("</categories>");
        
        sb.append("<dataset seriesName=\'").append(lightString).append("\'>");
        for (Double value : light)
        {
            sb.append("<set value=\'" + (value != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(value, measurementType, fuelEfficiencyType).toString() : "0") + "\'/>");
        }
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\'").append(mediumString).append("\'>");
        for (Double value : medium)
        {
            sb.append("<set value=\'" + (value != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(value, measurementType, fuelEfficiencyType).toString() : "0") + "\'/>");
        }
        sb.append("</dataset>");

        sb.append("<dataset seriesName=\'").append(heavyString).append("\'>");
        for (Double value : heavy)
        {
            sb.append("<set value=\'" + (value != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(value, measurementType, fuelEfficiencyType).toString() : "0") + "\'/>");
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
	

//	public static final double roundDouble(double d, int places) 
//	{
//	    return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
//	}
	
	public static final int getHoles(Duration d, int size)
	{
//        int holes = 0;
        
//        if ( d == Duration.DAYS)
//        {
//            holes = d.getNumberOfDays() - size;            
//        }
//        else
//        {
//            holes = d.getDvqCode() - size;
//        }   
        
//        return holes;
			return d.getDvqCount() - size;
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
//	    int size = 0;
//	    
//        if ( d == Duration.DAYS ) {
//            size = 30;     
//        } else {
//            size = convertToMonths(d);                    
//        }
//        
//        return size;	
		return d.getDvqCount();
	}
	public static final char getGroupingSeparator(Locale locale){
		
		return new DecimalFormatSymbols(locale).getGroupingSeparator();
	}
	public static final char getDecimalSeparator(Locale locale){
		
		return new DecimalFormatSymbols(locale).getDecimalSeparator();
	}
	
	public static final String getStyleString() {
	    return "<styles>"+
                    "<definition>" +
                        "<style name=\'toolTipFont\' type=\'font\' isHTML=\'1\'/>" +
                    "</definition>" +
                    "<application>" +
                        "<apply toObject=\'TOOLTIP\' styles=\'toolTipFont\' />" +
                    "</application>" +
               "</styles>";	    	    
	}
}