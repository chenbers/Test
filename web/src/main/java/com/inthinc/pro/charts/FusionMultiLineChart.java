package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.XMLUtil;

public class FusionMultiLineChart implements BaseChart
{
    private static final Logger logger = Logger.getLogger(FusionMultiLineChart.class);
    
    private static final String LINE_CONTROL_PARAMS ="<chart " +
                                                     "caption=\'\' " +
                                                     "subcaption=\'\' " +
                                                     "xAxisName=\'\' " +
                                                     "yAxisMinValue=\'0\' " +
                                                     "yAxisName=\'\' " +
                                                     "numberPrefix=\'\' " +
                                                     "showValues=\'0\' " +
                                                     "showLabels=\'1\' " +
                                                     "slantLabels=\'1\' " +  
                                                     "labelDisplay=\'Rotate\' " +
                                                     "adjustDiv=\'0\' " +
                                                     "setAdaptiveYMin='0' " +
                                                     "borderColor=\'#cfcfcf\' " +
                                                     "vDivLineColor=\'#cfcfcf\' " +
                                                     "vDivLineThickness=\'1\' " +
                                                     "showAlternateHGridColor=\'1\' " +
                                                     "alternateHGridColor=\'#f0f0f0\' " +
                                                     "alternateHGridAlpha=\'100\' " +
                                                     "decimals=\'1\' " +
                                                     "forceDecimals=\'1\' " +
                                                     "yAxisMaxValue=\'5\' " +
                                                     "bgColor=\'#ffffff\' " +
                                                     "showBorder=\'0\' " +
                                                     "lineColor=\'#93C034\' " +
                                                     "lineThickness=\'2\' " +
                                                     "drawAnchors=\'1\' " +
                                                     "anchorRadius=\'2\' " +
                                                     "anchorSides=\'4\' " +
                                                     "anchorBorderThickness=\'4\' " +
                                                     "numVDivLines=\'4\' >";
    
    private static final String LINE_CHART_CAT_LABEL=" <category label=''{0}''/>";
    private static final String LINE_CHART_CAT_START="<categories>";
    private static final String LINE_CHART_CAT_END="</categories>";
    private static final String LINE_CHART_CLOSE = "</chart>"; 
    private static final String LINE_CHART_ITEM_FORMAT = "<set value=''{0}'' label=''{1}'' />";
    
    
    private static final String LINE_CHART_DATASET_START="<dataset seriesName=''{0}'' color=''{1}'' plotBorderColor=''{2}''>";
    
    private static final String LINE_CHART_DATASET_END="</dataset>";


    @Override
    public String getControlParameters()
    {
        
        return LINE_CONTROL_PARAMS;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
        return MessageFormat.format(LINE_CHART_ITEM_FORMAT, params);
    }

    @Override
    public String getClose()
    {
        return LINE_CHART_CLOSE;
    }

    public String getCategoryLabel(String label)
    {
        return MessageFormat.format(LINE_CHART_CAT_LABEL, new Object[] {XMLUtil.escapeXMLChars(label)});
    }
    public String getCategoriesStart()
    {
        return LINE_CHART_CAT_START;
    }
    public String getCategoriesEnd()
    {
        return LINE_CHART_CAT_END;
    }

    public String getChartDataSet(String title, String color, String borderColor, Object[] values, List<String> catLabels)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MessageFormat.format(LINE_CHART_DATASET_START, new Object[] { XMLUtil.escapeXMLChars(title), color, borderColor}));
        for (int i = 0; i < values.length; i++)
        {
            String label = title + " " + catLabels.get(i) + ", " + values[i] + " mpg";  
            buffer.append(getChartItem(new Object[] { values[i], XMLUtil.escapeXMLChars(label)}));
        }
        buffer.append(LINE_CHART_DATASET_END);
        return buffer.toString();
    }
    
}
