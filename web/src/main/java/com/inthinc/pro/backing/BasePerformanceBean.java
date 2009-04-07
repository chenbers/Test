package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.charts.Line;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public abstract class BasePerformanceBean extends BaseBean
{
    private static final Logger    logger         = Logger.getLogger(VehicleSpeedBean.class);
    protected NavigationBean       navigation;
    protected DurationBean         durationBean;
    protected TableStatsBean       tableStatsBean;
    protected AddressLookup        addressLookup;
    protected ReportRenderer       reportRenderer;
    
    protected Map<String, Integer> scoreMap;
    protected Map<String, String>  styleMap;
    protected Map<String, String>  trendMap;

    protected abstract List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType);

    public String createFusionLineDef(Integer id, Duration duration, ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());
        List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(duration); 
        logger.debug(duration.toString() + " monthList " + monthList.size());
        
        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            }
            else
            {
                sb.append(line.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(line.getClose());
        return sb.toString();
    }
    
    public List<CategorySeriesData> createJasperMultiLineDef(Integer id, List<ScoreType> scoreTypes, Duration duration)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType scoreType : scoreTypes)
        {
            List<ScoreableEntity> scoreList = getTrendCumulative(id, duration, scoreType);

            List<String> monthList = GraphicUtil.createMonthList(duration, "M/dd");
            int count = 0;
            for (ScoreableEntity se : scoreList)
            {
                Double score = null;
                if (se.getScore() != null)
                    score = se.getScore() / 10.0D;

                returnList.add(new CategorySeriesData(MessageUtil.getMessageString(scoreType.toString()), 
                                                      monthList.get(count).toString(), 
                                                      score, monthList.get(count).toString()));

                count++;
            }
        }
        return returnList;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public Map<String, Integer> getScoreMap()
    {
        return scoreMap;
    }

    public void setScoreMap(Map<String, Integer> scoreMap)
    {
        this.scoreMap = scoreMap;
    }

    public Map<String, String> getStyleMap()
    {
        return styleMap;
    }

    public void setStyleMap(Map<String, String> styleMap)
    {
        this.styleMap = styleMap;
    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    public Map<String, String> getTrendMap()
    {
        return trendMap;
    }

    public void setTrendMap(Map<String, String> trendMap)
    {
        this.trendMap = trendMap;
    }

    public TableStatsBean getTableStatsBean()
    {
        return tableStatsBean;
    }

    public void setTableStatsBean(TableStatsBean tableStatsBean)
    {
        this.tableStatsBean = tableStatsBean;
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }
}
