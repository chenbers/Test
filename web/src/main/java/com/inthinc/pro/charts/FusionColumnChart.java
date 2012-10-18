package com.inthinc.pro.charts;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.XMLUtil;

public class FusionColumnChart implements BaseChart
{
    private static final Logger logger = Logger.getLogger(Line.class);
    
    private static final String COLUMN_CONTROL_PARAMS = 
                                                    "<chart " +
                                                    "caption=\'\' " +
                                                    "subcaption=\'\' " +
                                                    "xAxisName=\'\' " +
                                                    "yAxisMinValue=\'0\' " +
                                                    "yAxisName=\'\' " +
                                                    "numberPrefix=\'\' " +
                                                    "showValues=\'0\' " +
                                                    "showLabels=\'1\' " +
                                                    "slantLabels='1' " +  
                                                    "labelDisplay='Rotate' " +
                                                    "showYAxisValues=\'1\' " +  
                                                    "adjustDiv=\'0\' " +
                                                    "setAdaptiveYMin='0' " +
                                                    "borderColor=\'#cfcfcf\' " +
                                                    "vDivLineColor=\'#cfcfcf\' " +
                                                    "vDivLineThickness=\'1\' " +
                                                    "showAlternateHGridColor=\'1\' " +
                                                    "alternateHGridColor=\'#f0f0f0\' " +
                                                    "alternateHGridAlpha=\'100\' " +
                                                    "decimals=\'0\' " +
                                                    "yAxisMaxValue=\'5\' " +
                                                    "yAxisValueDecimals=\'0\' " +
                                                    "bgColor=\'#ffffff\' " +
                                                    "showBorder=\'0\' " +
                                                    "numVDivLines=\'4\'>";
    
    private static final String COLUMN_CHART_CLOSE = "</chart>"; 
    private static final String COLUMN_CHART_ITEM_FORMAT = "<set value=''{0}'' label=''{1}'' color=''#A8C634''/>";

    @Override
    public String getControlParameters()
    {
        
        return COLUMN_CONTROL_PARAMS;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
        return MessageFormat.format(COLUMN_CHART_ITEM_FORMAT, params);
    }

    @Override
    public String getClose()
    {
        return COLUMN_CHART_CLOSE;
    }
}
