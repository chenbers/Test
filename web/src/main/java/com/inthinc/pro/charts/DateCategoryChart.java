package com.inthinc.pro.charts;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.inthinc.pro.model.BaseScore;
import com.inthinc.pro.model.Duration;

public class DateCategoryChart <T extends BaseScore> implements CategoryChart {

    private static final String CHART_CAT_LABEL=" <category label=''{0}''/>";
    private static final String CHART_CAT_START="<categories>";
    private static final String CHART_CAT_END="</categories>";

    Duration duration;
    List<Date> dateList;
    List<T> scoreList;
    
	public DateCategoryChart(Duration duration, List<Date> dateList)
	{
		this.duration = duration;
		this.dateList = dateList;
	}

	@Override
	public String getCategories(Locale locale) {
		StringBuilder builder = new StringBuilder();
		builder.append(CHART_CAT_START);
		List<String> labelList = createDateLabelList(locale);
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

	protected List<String> createDateLabelList(Locale locale)
	{
	    DateLabels dateLabels = new DateLabels(locale);
		if (duration == Duration.DAYS) {
			return dateLabels.createDayLabelList(dateList);
		}
		return dateLabels.createMonthLabelList(dateList);
	}


}
