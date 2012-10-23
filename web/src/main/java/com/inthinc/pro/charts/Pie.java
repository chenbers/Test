package com.inthinc.pro.charts;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.XMLUtil;

public class Pie implements BaseChart
{
    
    private static final Logger logger = Logger.getLogger(Pie.class);
    
    private static final String PIE_CONTROL_PARAMS = 
                                                    "<chart "+               
                                                    "showBorder=\'0\' " +
                                                    "decimals=\'0\' " +
                                                    "enableSmartLabels=\'1\' " +
                                                    "enableRotation=\'1\' " +
                                                    "startingAngle=\'90\' " +
                                                    "use3DLighting=\'1\' " +
                                                    "radius3D=\'30\' " +
                                                    "showPercentValues=\'1\' " +
                                                    "smartLineThickness=\'1\' " +
                                                    "smartLineColor=\'333333\' " +
                                                    "baseFont=\'Verdana\' " +
                                                    "baseFontSize=\'12\' " +
                                                    "labelDistance=\'10\' " +
                                                    "bgColor=\'#ffffff\' ";
    
    private static final String PIE_CHART_CLOSE = "</chart>"; 
    private static final String PIE_CHART_ITEM_FORMAT = "<set value=''{0}'' label=''{1}'' color=''{2}''/>";

    @Override
    public String getControlParameters() 
    {
        return PIE_CONTROL_PARAMS;
    }

    @Override
    public String getChartItem(Object[] params)
    {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
        return MessageFormat.format(PIE_CHART_ITEM_FORMAT, params);
    }

    @Override
    public String getClose()
    {
        return PIE_CHART_CLOSE;
    }

}
