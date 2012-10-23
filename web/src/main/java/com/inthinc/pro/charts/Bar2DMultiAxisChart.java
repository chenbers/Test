package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.XMLUtil;

public class Bar2DMultiAxisChart extends DateCategoryChart {

    private static final String CHART_CONTROL_PARAMS ="<chart " +
                                                // y axis formatting
            "adjustDiv=\'0\' " +
            "numDivLines=\'4\' " +
            "yAxisValueDecimals=\'2\' " +
    											// background color/border
    		"bgColor=\'#ffffff\' " +
    		"showBorder=\'0\' " +
    		"showToolTips=\'1\' " +
    		"showValues=\'0\' " +
    											// x-axis labels
		    "showLabels=\'1\' " +
		    "rotateLabels=\'1\' " +
		    "slantLabels=\'1\' " +
		    "connectNullData=\'1\' " +
		    									// secondary y-axis
            "SYAxisMinValue=\'0\' " +
//            "SYAxisMaxValue=\'50\' " +
            "SNumberSuffix=\'%\' " +
            									// legend parameters
		    "showLegend=\'1\' " +
            "legendPosition=\'BOTTOM\' " +
            "legendMarkerCircle=\'0\' " + 
            "legendBorderThickness=\'0\' " +
            "legendShadow=\'0\' " +

            									// chart general
            "decimals=\'2\' " +
            "forcedecimals=\'1\' " +
            

            									// chart margins
		    "chartLeftMargin=\'3\' " +
		    "chartRightMargin=\'3\' " +
		    "areaOverColumns=\'0\' ";
    


    private static final String CHART_CLOSE = 
        GraphicUtil.getStyleString() + "</chart>"; 

    
    private static final String SERIES_START = "<dataset seriesName=''{0}'' color=''{1}'' showValues=''0''>";
    private static final String LINE_SERIES_START = "<lineset seriesName=''{0}'' color=''{1}'' showValues=''0'' >";
     
    private static final String SERIES_VALUE = " <set value=''{0}''/>";
    private static final String SERIES_VALUE_WITH_TOOLTIP = " <set value=''{0}'' tooltext=''{1}''/>";
    private static final String SERIES_END = "</dataset>";
    private static final String LINE_SERIES_END = "</lineset>";


    public Bar2DMultiAxisChart(Duration duration, List<Date> dateList) {
    	super(duration, dateList);
    }

    public String getSeries(String title, String color, boolean isLine, List<?> values)
    {
        StringBuilder buffer = new StringBuilder();
        if (isLine)
        {
        	buffer.append(MessageFormat.format(LINE_SERIES_START, new Object[] { XMLUtil.escapeXMLChars(title), color}));
        }
        else
        {
        	buffer.append(MessageFormat.format(SERIES_START, new Object[] { XMLUtil.escapeXMLChars(title), color}));
        }
        for (Object value : values)
        {
            buffer.append(getChartItem(new Object[] { value.toString()}));
        }
        if (isLine)
        {
        	buffer.append(LINE_SERIES_END);
        }
        else
        {
        	buffer.append(SERIES_END);
        }
        return buffer.toString();

    }
    public String getSeries(String title, String color, boolean isLine, List<?> values, List<String> tooltipText)
    {
        StringBuilder buffer = new StringBuilder();
        if (isLine)
        {
        	buffer.append(MessageFormat.format(LINE_SERIES_START, new Object[] { XMLUtil.escapeXMLChars(title), color}));
        }
        else
        {
        	buffer.append(MessageFormat.format(SERIES_START, new Object[] { XMLUtil.escapeXMLChars(title), color}));
        }
        int i = 0;
        for (Object value : values)
        {
            buffer.append(getChartItemWithTooltip(new Object[] { value.toString(), tooltipText.get(i++)}));
        }
        if (isLine)
        {
        	buffer.append(LINE_SERIES_END);
        }
        else
        {
        	buffer.append(SERIES_END);
        }
        return buffer.toString();

    }
    
	@Override
	public String getChartItem(Object[] params) {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
		return MessageFormat.format(SERIES_VALUE, params);
	}

	public String getChartItemWithTooltip(Object[] params) {
        for (int i = 0; i < params.length; i++) {
            params[i] = XMLUtil.escapeXMLChars(params[i]);
        }
		return MessageFormat.format(SERIES_VALUE_WITH_TOOLTIP, params);
	}

	@Override
	public String getClose() {
		return CHART_CLOSE;
	}

	@Override
	public String getControlParameters() {
		return CHART_CONTROL_PARAMS;
	}

}
