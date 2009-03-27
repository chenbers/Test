package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

public class FusionMultiAreaChart implements BaseChart
{
    private static final Logger logger = Logger.getLogger(FusionMultiLineChart.class);
    
    private static final String AREA_CONTROL_PARAMS ="<chart " +
                                                     "bgcolor=\'#ffffff\' " +
                                                     "borderColor=\'#cfcfcf\' " +
                                                     "pYAxisName=\'Score\' " +
                                                     "showValues=\'0\' " +
                                                     "rotateLabels=\'1\' " +
                                                     "slantLabels=\'1\' " +
                                                     "yAxisMaxValue=\'5\' " +
                                                     
                                                     "bgColor=\'#ffffff\' " +
                                                     
                                                     "drawAnchors=\'1\' " +
                                                     "anchorRadius=\'2\' " +
                                                     "anchorSides=\'4\' " +
                                                     "anchorBorderThickness=\'4\' " +
                                                     "anchorBgColor=\'#0000ff\' " +
                                                    
                                                  
                                                     
                                                     "anchorAlpha=\'100\' " +
                                                     
                                                     "lineThickness=\'4\' " +
                                                     
                                                     "connectNullData=\'1\' " +
                                                     "areaOverColumns=\'0\'>";
    
    private static final String AREA_CHART_CAT_LABEL=" <category label=''{0}''/>";
    private static final String AREA_CHART_CAT_START="<categories>";
    private static final String AREA_CHART_CAT_END="</categories>";
    private static final String AREA_CHART_CLOSE = "</chart>"; 
    private static final String AREA_CHART_ITEM_FORMAT = "<set value=''{0}'' label=''{1}'' />";
    private static final String AREA_CHART_ITEM_FORMAT_DASHED = "<set value=''{0}'' label=''{1}'' dashed=''1''/>";
    
    private static final String AREA_CHART_DATASET_START="<dataset seriesName=''{0}'' renderAs=\"Area\" color=''{1}'' anchorBorderColor=''{1}'' alpha =''100'' drawAnchors=''1''>";
    private static final String AREA_CHART_DATASET_LINE_START="<dataset seriesName=''{0}'' renderAs=\"Line\" color=''{1}'' anchorBorderColor=''{1}'' alpha =''100''>";
    
    private static final String AREA_CHART_DATASET_END="</dataset>";


    @Override
    public String getControlParameters()
    {
        
        return AREA_CONTROL_PARAMS;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        String item = MessageFormat.format(AREA_CHART_ITEM_FORMAT, params);
        return item;
    }
    
    public String getChartItemDashed(Object[] params)
    {
        String item = MessageFormat.format(AREA_CHART_ITEM_FORMAT_DASHED, params);
        return item;
    }

    @Override
    public String getClose()
    {
        return AREA_CHART_CLOSE;
    }

    public String getCategoryLabel(String label)
    {
        return MessageFormat.format(AREA_CHART_CAT_LABEL, new Object[] {label});
    }
    public String getCategoriesStart()
    {
        return AREA_CHART_CAT_START;
    }
    public String getCategoriesEnd()
    {
        return AREA_CHART_CAT_END;
    }

    public String getChartAreaDataSet(String title, String color, Object[] values, List<String> catLabels)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(AREA_CHART_DATASET_START, new Object[] { title, color}));
        for (int i = 0; i < values.length; i++)
        {
            String label = title + " " + catLabels.get(i) + ", " + values[i];  
            
            if(i < values.length-1 && values[i+1] == null && values[i-1] != null)
                buffer.append(getChartItemDashed(new Object[] { values[i], label}));
            else
                buffer.append(getChartItem(new Object[] { values[i], label}));
        }
        buffer.append(AREA_CHART_DATASET_END);
        return buffer.toString();
    }
    
    public String getChartLineDataSet(String title, String color, Object[] values, List<String> catLabels)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(AREA_CHART_DATASET_LINE_START, new Object[] { title, color}));
        for (int i = 0; i < values.length; i++)
        {
            String label = title + " " + catLabels.get(i) + ", " + values[i];
            
            if(i < values.length-1 && values[i+1] == null && values[i-1] != null)
                buffer.append(getChartItemDashed(new Object[] { values[i], label}));
            else
                buffer.append(getChartItem(new Object[] { values[i], label}));
        }
        buffer.append(AREA_CHART_DATASET_END);
        return buffer.toString();
    }
    
}
