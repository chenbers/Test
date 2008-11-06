package com.inthinc.pro.charts;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.charts.ChartSizes;

public class Line implements BaseChart
{
    private static final Logger logger = Logger.getLogger(Line.class);
    
    private static final String LINE_CONTROL_PARAMS_SMALL = 
                                                    "<chart " +
                                                    "caption=\'\' " +
                                                    "subcaption=\'\' " +
                                                    "xAxisName=\'\' " +
                                                    "yAxisMinValue=\'0\' " +
                                                    "yAxisName=\'\' " +
                                                    "numberPrefix=\'\' " +
                                                    "showValues=\'0\' " +
                                                    "showLabels=\'0\' " +
                                                    "showYAxisValues=\'0\' " +
                                                    "adjustDiv=\'0\' " +
                                                    "setAdaptiveYMin='0' " +
                                                    "borderColor=\'#cfcfcf\' " +
                                                    "vDivLineColor=\'#cfcfcf\' " +
                                                    "vDivLineThickness=\'1\' " +
                                                    "showAlternateHGridColor=\'1\' " +
                                                    "alternateHGridColor=\'#f0f0f0\' " +
                                                    "alternateHGridAlpha=\'100\' " +
                                                    "forceDecimals=\'1\' " +
                                                    "yAxisMaxValue=\'5\' " +
                                                    "bgColor=\'#ffffff\' " +
                                                    "showBorder=\'0\' " +
                                                    "lineColor=\'#93C034\' " +
                                                    "lineThickness=\'2\' " +
                                                    "drawAnchors=\'0\' " +
                                                    "numVDivLines=\'4\' " + 
                                                    "plotFillColor=\'#A8C634\' >";
    
    private static final String LINE_CONTROL_PARAMS_LARGE = 
                                                    "<chart " +
                                                    "caption=\'\' " +
                                                    "subcaption=\'\' " +
                                                    "xAxisName=\'\' " +
                                                    "yAxisMinValue=\'0\' " +
                                                    "yAxisName=\'\' " +
                                                    "numberPrefix=\'\' " +
                                                    "showValues=\'0\' " +
                                                    "adjustDiv=\'0\' " +
                                                    "setAdaptiveYMin='0' " +
                                                    "borderColor=\'#cfcfcf\' " +
                                                    "vDivLineColor=\'#cfcfcf\' " +
                                                    "vDivLineThickness=\'1\' " +
                                                    "showAlternateHGridColor=\'1\' " +
                                                    "alternateHGridColor=\'#f0f0f0\' " +
                                                    "alternateHGridAlpha=\'100\' " +
                                                    "forceDecimals=\'1\' " +
                                                    "yAxisMaxValue=\'5\' " +
                                                    "bgColor=\'#ffffff\' " +
                                                    "showBorder=\'0\' " +
                                                    "lineColor=\'#93C034\' " +
                                                    "lineThickness=\'2\' " +
                                                    "drawAnchors=\'0\' " +
                                                    "numVDivLines=\'4\' " + 
                                                    "plotFillColor=\'#A8C634\' >";
    
    private static final String LINE_CHART_CLOSE = "</chart>"; 
    private static final String LINE_CHART_ITEM_FORMAT = "<set value=''{0}'' label=''{1}'' />";

    @Override
    public String getControlParameters()
    {
        
        return LINE_CONTROL_PARAMS_LARGE;
    }
    
    public String getControlParameters(ChartSizes size) 
    {
        if(size == ChartSizes.SMALL)
            return LINE_CONTROL_PARAMS_SMALL;
        else
            return LINE_CONTROL_PARAMS_LARGE;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        String item = MessageFormat.format(LINE_CHART_ITEM_FORMAT, params);
        return item;
    }

    @Override
    public String getClose()
    {
        return LINE_CHART_CLOSE;
    }
}
