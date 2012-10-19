package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.List;
import org.apache.log4j.Logger;

import com.inthinc.pro.util.XMLUtil;

/*
 *  FusionCharts utility class for: MSCombiDY2D.swf
 *  See XML output example at bottom.
 */
public class FusionMultiAreaChart implements BaseChart
{
    private static final Logger logger = Logger.getLogger(FusionMultiLineChart.class);
    
    private static final String AREA_CONTROL_PARAMS ="<chart " +
                                                     "bgcolor=\'#ffffff\' " +
                                                     "borderColor=\'#ffffff\' " +
                                                     "pYAxisName=\'Name\' " +
                                                     "pYAxisMaxValue=\'5\' " +
                                                     "sYAxisName=\'Name\' " +
                                                     "showValues=\'0\' " +
                                                     "rotateLabels=\'1\' " +
                                                     "slantLabels=\'1\' " +
                                                     "connectNullData=\'1\' " +
                                                     "drawAnchors=\'1\' " +
                                                     "anchorSides=\'4\' " +
                                                     "forceDecimals=\'1\' " +
                                                     "decimals=\'1\' " +
                                                     "showLegend=\'1\' " +
                                                     "showLabels=\'1\' " +
                                                     "showYAxisValues=\'1\' " +
                                                     
                                                     "showDivLineSecondaryValue=\'1\' " + 
                                                     "showSecondaryLimits=\'1\' " +
                                                     
                                                     "chartLeftMargin=\'3\' " +
                                                     "chartRightMargin=\'3\' " +
                                                     
                                                     "areaOverColumns=\'0\'> ";
    
    private static final String CHART_CAT_START="<categories>";
    private static final String CHART_CAT_LABEL=" <category label=''{0}''/>";
    private static final String CHART_CAT_END="</categories>";
    
    private static final String CHART_ITEM_FORMAT = "<set toolText=''{1}'' value=''{0,number,#.##}'' />";
    private static final String CHART_ITEM_FORMAT_DASHED = "<set toolText=''{1}'' value=''{0,number,#.##}'' dashed=''1''/>";
    
    private static final String CHART_DATASET_AREA_START="<dataset seriesName=''{0}'' renderAs=\"Area\" color=''{1}'' anchorBorderColor=''#333333'' anchorAlpha =''100'' parentYAxis=''P''>";
    private static final String CHART_DATASET_LINE_START="<dataset seriesName=''{0}'' renderAs=\"Line\" color=''{1}'' parentYAxis=''P'' alpha=''75''>";
    private static final String CHART_DATASET_COLUMN_START="<dataset seriesName=''{0}'' renderAs=\"Column\" color=''{1}'' parentYAxis=''S'' alpha=''25''>";
 
    private static final String CHART_DATASET_END="</dataset>";
    private static final String CHART_CLOSE = "</chart>"; 
    
    @Override
    public String getControlParameters()
    {
        return AREA_CONTROL_PARAMS;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
        return MessageFormat.format(CHART_ITEM_FORMAT, params);
    }
    
    public String getChartItemDashed(Object[] params)
    {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
        return MessageFormat.format(CHART_ITEM_FORMAT_DASHED, params);
    }

    @Override
    public String getClose()
    {
        return CHART_CLOSE;
    }

    public String getCategoryLabel(String label)
    {
        return MessageFormat.format(CHART_CAT_LABEL, new Object[] {label});
    }
    public String getCategoriesStart()
    {
        return CHART_CAT_START;
    }
    public String getCategoriesEnd()
    {
        return CHART_CAT_END;
    }

    public String getChartAreaDataSet(String title, String color, Object[] values, List<String> valueTexts, List<String> catLabels)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(CHART_DATASET_AREA_START, new Object[] { XMLUtil.escapeXMLChars(title), color}));
        for (int i = 0; i < values.length; i++)
        {
            String label = catLabels.get(i) + " " + title + ": " + valueTexts.get(i);
            
            //Set Chart item to "Dashed" version if next item in array is null, see example XML output at bottom.
            if(values[i] != null && i < values.length-1 && values[i+1] == null)
                buffer.append(getChartItemDashed(new Object[] { values[i], label}));
            else
                buffer.append(getChartItem(new Object[] { values[i], label}));
        }
        buffer.append(CHART_DATASET_END);
        return buffer.toString();
    }
    
//    public String getChartLineDataSet(String title, String color, Object[] values,  List<String> valueTexts, List<String> catLabels)
//    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(MessageFormat.format(CHART_DATASET_LINE_START, new Object[] { title, color}));
//        for (int i = 0; i < values.length; i++)
//        {
//            String label = catLabels.get(i) + " " + title + ": " + valueTexts.get(i);
//            
//            //Set Chart item to "Dashed" version if next item in array is null, see example XML output at bottom.
//            if(values[i] != null && i < values.length-1 && values[i+1] == null) 
//                buffer.append(getChartItemDashed(new Object[] { values[i], label}));
//            else
//                buffer.append(getChartItem(new Object[] { values[i], label}));
//        }
//        buffer.append(CHART_DATASET_END);
//        return buffer.toString();
//    }
    
    public String getChartBarDataSet(String title, String color, Object[] values,  List<String> valueTexts, List<String> catLabels)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(CHART_DATASET_COLUMN_START, new Object[] { title, color}));
        for (int i = 0; i < values.length; i++)
        {
            String label = catLabels.get(i) + " " + title  + ": " + valueTexts.get(i);  
            buffer.append(getChartItem(new Object[] { values[i], label}));
        }
        buffer.append(CHART_DATASET_END);
        return buffer.toString();
    }

}

//<chart bgcolor='#ffffff' 
//    borderColor='#ffffff' 
//    pYAxisName='Score' 
//    pYAxisMaxValue='5' 
//    sYAxisName='Miles' 
//    showValues='0' 
//    rotateLabels='1' 
//    slantLabels='1' 
//    connectNullData='1' 
//    drawAnchors='1' 
//    anchorSides='4' 
//    forceDecimals='1' 
//    showLabels='1' 
//    showYAxisValues='1' 
//    areaOverColumns='0'> 
//    
//    <categories> 
//        <category label='02'/> 
//        <category label='03'/>
//         <category label='04'/> 
//        <category label='05'/> 
//        <category label='06'/> 
//    </categories>
//    
//    <dataset seriesName='Daily Score' renderAs="Line" color='#407EFF' parentYAxis='P' alpha='75'>
//        <set value='3.8' label='Daily Score 02, 3.8'/>
//        <set value='5' label='Daily Score 03, 5.0'/>
//        <set value='4' label='Daily Score 04, 4.0'/>
//        <set value='2.3' label='Daily Score 05, 2.3'/>
//        <set value='3' label='Daily Score 06, 3.0'/>
//        <set value='2.8' label='Daily Score 07, 2.8' dashed='1'/>   *** the item after this is null. use "Dashed"
//        <set value='null' label='Daily Score 08, null'/>
//        <set value='3' label='Daily Score 09, null'/>
//        <set value='2.7' label='Daily Score 10, 2.7'/>
//    </dataset>
//    
//    <dataset seriesName='Cumulative Score' renderAs="Area" color='#B0CB48' anchorAlpha ='100' parentYAxis='P'>
//        <set value='3' label='Cumulative Score 02, 3.0'/>
//        <set value='3' label='Cumulative Score 03, 3.0'/>
//        <set value='3' label='Cumulative Score 04, 3.0'/>
//        <set value='2.8' label='Cumulative Score 05, 2.8'/>
//        <set value='2.8' label='Cumulative Score 06, 2.8'/>
//        <set value='2.8' label='Cumulative Score 07, 2.8'/>
//        <set value='2.8' label='Cumulative Score 08, 2.8'/>
//    </dataset>
//    
//    <dataset seriesName='Mileage' renderAs="Column" color='#C0C0C0' parentYAxis='S' alpha='25'>
//        <set value='44' label='Mileage 02, 44'/>
//        <set value='11' label='Mileage 03, 11'/>
//        <set value='11' label='Mileage 04, 11'/>
//        <set value='35' label='Mileage 05, 35'/>
//        <set value='23' label='Mileage 06, 23'/>
//        <set value='26' label='Mileage 07, 26'/>
//        <set value='0' label='Mileage 08, 0'/>
//        <set value='35' label='Mileage 09, 35'/>
//        <set value='55' label='Mileage 10, 55'/>
//        <set value='29' label='Mileage 11, 29'/>
//    </dataset>
//    </chart>
