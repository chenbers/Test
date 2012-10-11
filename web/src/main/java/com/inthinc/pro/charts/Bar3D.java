package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;


public class Bar3D implements BaseChart
{
    
    private static final Logger logger = Logger.getLogger(Bar3D.class);
    

    private static final String BAR3D_CONTROL_PARAMS =
                                "<chart " +
                                "palette=\'2\' " +
                                "caption=\'\' " +
                                "shownames=\'1\' " +
                                "showvalues=\'0\' " +
                                "decimals=\'0\' " +
                                "numberPrefix=\'%\' " +
                                "clustered=\'0\' " +
                                "exeTime=\'1.5\' " +
                                "showPlotBorder=\'0\' " +
                                "zGapPlot=\'30\' " +
                                "zDepth=\'90\' " +
                                "divLineEffect=\'emboss\' " +
                                "startAngX=\'10\' " +
                                "endAngX=\'18\' " +
                                "startAngY=\'-10\' " +
                                "endAngY=\'-40\' " +
                                "showBorder=\'0\' " +
                                "bgColor=\'#ffffff\' " +
                                "yAxisMaxValue=\'100\' " +
                                "showLegend=\'0\' " +
                                ">";
    
    private static final String BAR3D_CHART_CLOSE = "</chart>"; 

    private static final String BAR3D_CHART_CATEGORIES_START = "<categories>";
    private static final String BAR3D_CHART_CATEGORIES_END = "</categories>";
    private static final String BAR3D_CHART_CATEGORY = "<category label=''{0}''/>";
    
    private static final String BAR3D_CHART_DATASET_START="<dataset seriesName=''{0}'' color=''{1}'' showValues=''0''>";
    private static final String BAR3D_CHART_DATASET_ITEM="<set value=''{0}''/>";
    
    private static final String BAR3D_CHART_DATASET_END="</dataset>";


    @Override
    public String getChartItem(Object[] params)
    {
        String item = MessageFormat.format(BAR3D_CHART_DATASET_ITEM, params);
        return item;
    }

    public String getChartDataSet(String title, String color, Object[] values)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(BAR3D_CHART_DATASET_START, new Object[] { title, color}));
        for (Object value : values)
        {
            buffer.append(getChartItem(new Object[] { value}));
        }
        buffer.append(BAR3D_CHART_DATASET_END);
        return buffer.toString();
    }


    
    @Override
    public String getClose()
    {
        return BAR3D_CHART_CLOSE;
    }

    @Override
    public String getControlParameters()
    {
        return BAR3D_CONTROL_PARAMS;
    }
    
    public String getCategories(List<String> categoryLabelList)
    {
        StringBuffer buffer = new StringBuffer(BAR3D_CHART_CATEGORIES_START);
        for (String categoryLabel : categoryLabelList)
        {
            buffer.append(MessageFormat.format(BAR3D_CHART_CATEGORY, new Object[] { categoryLabel}));
        }
        buffer.append(BAR3D_CHART_CATEGORIES_END);
        return buffer.toString();
    }
    
    
    

}
