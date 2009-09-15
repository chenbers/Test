package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class DateCategoryChart implements CategoryChart {

    private static final String CHART_CAT_LABEL=" <category label=''{0}''/>";
    private static final String CHART_CAT_START="<categories>";
    private static final String CHART_CAT_END="</categories>";

    Duration duration;
    
	public DateCategoryChart(Duration duration)
	{
		this.duration = duration;
	}
	
	@Override
	public String getCategories() {
		StringBuilder builder = new StringBuilder();
		builder.append(CHART_CAT_START);
		List<String> labelList = GraphicUtil.createMonthList(getDuration());
		for (String label : labelList) {
			builder.append(MessageFormat.format(CHART_CAT_LABEL, new Object[] {label}));
		}
		builder.append(CHART_CAT_END);
		return builder.toString();
	}

	@Override
	public String getChartItem(Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getControlParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

}
