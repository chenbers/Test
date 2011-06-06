package com.inthinc.pro.backing;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.backing.ui.ColorSelectorStandard;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.DateCategoryChart;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

@KeepAlive
public class TrendBean extends CustomSortBean<TrendBeanItem> {

    private static final long serialVersionUID = -6423801673221354796L;

    private static final Logger logger = Logger.getLogger(TrendBean.class);
    private ScoreDAO scoreDAO;
    private DurationBean durationBean;
    private boolean dataReady;
    private List<TrendBeanItem> trendBeanItems;
    Map<Integer, List<ScoreableEntity>> groupTrendMap;
    private String lineDef;

    private TrendBeanItem summaryItem;

    private static final Integer ROWS_PER_PAGE = 5;

    private Integer start = 1;
    private Integer end = ROWS_PER_PAGE;

    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    private Boolean animateChartData = Boolean.TRUE;

    private Boolean maximized;
    private Map<Integer, Boolean> groupVisibleState;
    private Integer groupID;
    private Group group;

    private void createTrendBeanItems() {
        setTrendBeanItems(new ArrayList<TrendBeanItem>());
        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
        s = getScores();

        Collections.sort(s, new Comparator<ScoreableEntity>() {
            public int compare(ScoreableEntity se1, ScoreableEntity se2) {
                if (se1 != null && se2 != null)
                    return se1.getIdentifier().compareToIgnoreCase(se2.getIdentifier());
                else
                    return -1;

            }
        });

        // Populate the table
        int count = 0;
        ColorSelectorStandard cs = new ColorSelectorStandard();
        for (ScoreableEntity score : s) {
            TrendBeanItem trendBeanItem = new TrendBeanItem();
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            se.setSe(score);
            se.setStyle(ScoreBox.GetStyleFromScore(score.getScore(), ScoreBoxSizes.SMALL));
            se.setColorKey(cs.getEntityColorKey(count++));

            CrashSummary crashSummary = scoreDAO.getGroupCrashSummaryData(score.getEntityID());
            trendBeanItem.setCrashSummary(crashSummary);
            trendBeanItem.setScoreableEntityPkg(se);
            trendBeanItem.setScoreableEntity(score);

            Integer groupID = se.getSe().getEntityID();
            if (getGroupVisibleState().get(groupID) != null) {
                se.setShow((Boolean) getGroupVisibleState().get(groupID));
            }
            getTrendBeanItems().add(trendBeanItem);
        }
        dataReady = true;
    }

    private List<ScoreableEntity> getScores() {
        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
        try {
            s = scoreDAO.getScores(getGroupID(), getDurationBean().getDuration(), ScoreType.SCORE_OVERALL);
            if (s == null) {
                s = Collections.emptyList();
            }
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }

        return s;
    }

    @Override
    public Comparator<TrendBeanItem> createComparator() {
        Comparator<TrendBeanItem> comparator = null;
        if (getSortColumn().equals("se.identifier")) {
            comparator = new Comparator<TrendBeanItem>() {

                @Override
                public int compare(TrendBeanItem tbi1, TrendBeanItem tbi2) {
                    return tbi1.getGroupName().compareTo(tbi2.getGroupName());
                }
            };
        } else if (getSortColumn().equals("se.score")) {
            comparator = new Comparator<TrendBeanItem>() {

                @Override
                public int compare(TrendBeanItem tbi1, TrendBeanItem tbi2) {
                    return tbi1.getScoreableEntity().getScore().compareTo(tbi2.getScoreableEntity().getScore());
                }
            };
        } else if (getSortColumn().equals("se.crashes")) {
            comparator = new Comparator<TrendBeanItem>() {

                @Override
                public int compare(TrendBeanItem tbi1, TrendBeanItem tbi2) {
                    return tbi1.getCrashesPerMillionMiles().compareTo(tbi2.getCrashesPerMillionMiles());
                }
            };
        }

        return comparator;
    }

    @Override
    public List<TrendBeanItem> getItems() {
        return getTrendBeanItems();
    }

    public List<TrendBeanItem> getTrendBeanItems() {
        if (trendBeanItems == null)
            createTrendBeanItems();
        return trendBeanItems;
    }

    public void setTrendBeanItems(List<TrendBeanItem> trendBeanItems) {
        this.trendBeanItems = trendBeanItems;
    }

    public Map<Integer, List<ScoreableEntity>> getGroupTrendMap() {
        if (groupTrendMap == null) {
            groupTrendMap = getScoreDAO().getTrendScores(getGroupID(), getDurationBean().getDuration());
        }
        return groupTrendMap;
    }

    public void setGroupTrendMap(Map<Integer, List<ScoreableEntity>> groupTrendMap) {
        this.groupTrendMap = groupTrendMap;
    }

    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO graphicDAO) {
        this.scoreDAO = graphicDAO;
    }

    public void init() {
    	dataReady=false;
        setSortColumn("se.identifier");
    }

    public String getLineDef() {
        if (lineDef == null)
            createLineDef();
        return lineDef;
    }

    public void setLineDef(String lineDef) {
        this.lineDef = lineDef;
    }

    public void createLineDef() {
        StringBuffer sb = new StringBuffer();

        // Control parameters
        sb.append(GraphicUtil.getXYControlParameters(animateChartData, getLocale()));

        // Fetch to get parents children, qualifier is groupId (parent),
        // date from, date to
        List<TrendBeanItem> s = getTrendBeanItems();

        TrendBeanItem summaryItem = this.getSummaryItem();

        // X-coordinates
        // sb.append("<categories>");
        // sb.append(GraphicUtil.createMonthsString(getDurationBean().getDuration(),getLocale()));
        // sb.append("</categories>");

        // top group
        if (summaryItem != null) {
            ScoreableEntityPkg summaryPkg = summaryItem.getScoreableEntityPkg();

            // check the case of a fresh install
            if (!getGroupTrendMap().isEmpty()) {
                List<ScoreableEntity> summaryList = getGroupTrendMap().get(summaryPkg.getSe().getEntityID());
                List<Date> dateList = new ArrayList<Date>();
                for (ScoreableEntity item : summaryList) {
                    dateList.add(item.getDate());
                }
                DateCategoryChart chart = new DateCategoryChart(getDurationBean().getDuration(), dateList);
                sb.append(chart.getCategories(getLocale()));
                if (!getMaximized() || (getMaximized() && summaryItem.getShow())) {
                    addDataSet(sb, summaryPkg, summaryList);
                }
            }
        }
        int pgStart = start;
        int pgEnd = end;
        if (getMaximized()) {
            pgStart = 1;
            pgEnd = s.size();
        }

        for (int i = pgStart; i <= pgEnd; i++) {
            if (s.size() < i)
                continue;

            TrendBeanItem item = s.get(i - 1);
            ScoreableEntityPkg se = item.getScoreableEntityPkg();

            if (getMaximized() && !se.getShow()) {
                continue;
            }
            // Fetch to get children's observations
            List<ScoreableEntity> ss = getGroupTrendMap().get(se.getSe().getEntityID());

            // Y-coordinates
            addDataSet(sb, se, ss);
        }

        sb.append("</chart>");

        lineDef = sb.toString();
        System.out.println("linedef " + lineDef);
    }

    private int addDataSet(StringBuffer sb, ScoreableEntityPkg se, List<ScoreableEntity> ss) {
        
        // escape any bad characters
        String escaped = MiscUtil.escapeBadCharacters(se.getSe().getIdentifier());
        sb.append("<dataset seriesName=\'" + escaped + "\' color=\'");
        sb.append(se.getColorKey());
        sb.append("\'>");

        // Not a full range, pad w/ zero
        int holes = getDurationBean().getDuration().getDvqCount() - ss.size();
        // if (getDurationBean().getDuration() == Duration.DAYS)
        // {
        // holes = getDurationBean().getDuration().getNumberOfDays() - ss.size();
        // }
        // else
        // {
        // holes = GraphicUtil.convertToMonths(getDurationBean().getDuration()) - ss.size();
        // }
        for (int k = 0; k < holes; k++) {
            sb.append("<set value=\'0.0\' alpha=\'0\'/>");
        }

        ScoreableEntity sss = null;
        for (int j = 0; j < ss.size(); j++) {
            sss = ss.get(j);

            sb.append("<set value=\'");
            Float score = new Float((sss.getScore() == null || sss.getScore() < 0) ? 5 : sss.getScore() / 10.0);
            sb.append(score.toString()).substring(0, 3);
            sb.append("\'");
            if (sss.getScore() == null || sss.getScore() < 0) {
                sb.append(" alpha=\'0\' ");
            }
            sb.append(" anchorBgColor=\'" + se.getColorKey() + "\' anchorBgAlpha=\'100\'/>");
        }
        sb.append("</dataset>");
        return holes;
    }

    private TrendBeanItem createSummaryItem() {
        TrendBeanItem summaryTrendBeanItem = new TrendBeanItem();
        // ScoreableEntity score = getScoreDAO().getTrendSummaryScore(trendBeanState.getGroupID(), getDurationBean().getDuration(), ScoreType.SCORE_OVERALL);
        ScoreableEntity score = getScoreDAO().getSummaryScore(getGroupID(), getDurationBean().getDuration(), ScoreType.SCORE_OVERALL);
        if (score == null) {
            return null;
        }
        if (score.getScore() == null) {

            score.setScore(-1);
        }
        score.setEntityID(getGroupID());

        String summaryTitle = MessageUtil.formatMessageString("trendReport_summary", getGroupHierarchy().getGroup(getGroupID()).getName());
        score.setIdentifier(summaryTitle);

        ScoreableEntityPkg se = new ScoreableEntityPkg();
        se.setSe(score);
        se.setStyle(ScoreBox.GetStyleFromScore(score.getScore(), ScoreBoxSizes.SMALL));
        se.setColorKey(ColorSelectorStandard.StandardColors.BLACK.entitycolorKey());
        if (getGroupVisibleState().get(getGroupID()) != null) {
            se.setShow((Boolean) getGroupVisibleState().get(getGroupID()));
        }

        CrashSummary crashSummary = getScoreDAO().getGroupCrashSummaryData(score.getEntityID());

        summaryTrendBeanItem.setCrashSummary(crashSummary);
        summaryTrendBeanItem.setScoreableEntity(score);
        summaryTrendBeanItem.setScoreableEntityPkg(se);
        return summaryTrendBeanItem;
    }

    public void scrollerListener(DataScrollerEvent se) {
        this.start = (se.getPage() - 1) * ROWS_PER_PAGE + 1;
        this.end = (se.getPage()) * ROWS_PER_PAGE;

        // Partial page
        if (this.end > getTrendBeanItems().size()) {
            this.end = getTrendBeanItems().size();
        }

    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String exportToPDF() {
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
        return null;
    }

    public ReportCriteria buildReportCriteria() {
        ReportCriteria reportCriteria = reportCriteriaService.getTrendChartReportCriteria(getGroupID(), getDurationBean().getDuration(), getLocale());
        reportCriteria.setLocale(getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        return reportCriteria;
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setAnimateChartData(String animateChartData) {
        this.animateChartData = Boolean.valueOf(animateChartData);

    }

    public String isAnimateChartData() {
        return animateChartData.toString();
    }

    public TrendBeanItem getSummaryItem() {
        if (summaryItem == null) {
            summaryItem = createSummaryItem();
        }
        return summaryItem;
    }

    public void setSummaryItem(TrendBeanItem summaryItem) {
        this.summaryItem = summaryItem;
    }

    public DurationBean getDurationBean() {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean) {
        // if (this.durationBean == null && durationBean != null)
        // durationBean.addDurationChangeListener(this);
        this.durationBean = durationBean;

    }

    public void saveStateAction() {
        TrendBeanItem summaryItem = getSummaryItem();
        if (summaryItem != null) {
            getGroupVisibleState().put(summaryItem.getGroupID(), summaryItem.getShow());
        }

        for (TrendBeanItem item : getTrendBeanItems()) {
            getGroupVisibleState().put(item.getGroupID(), item.getShow());
        }
    }

    public Boolean getMaximized() {
        if (maximized == null)
            maximized = Boolean.FALSE;
        return maximized;
    }

    public void setMaximized(Boolean maximized) {
        this.maximized = maximized;
    }

    public Map<Integer, Boolean> getGroupVisibleState() {
        if (groupVisibleState == null)
            groupVisibleState = new HashMap<Integer, Boolean>();
        return groupVisibleState;
    }

    public void setGroupVisibleState(Map<Integer, Boolean> flyout) {
        this.groupVisibleState = flyout;
    }

    public Integer getGroupID() {
        // if (groupID == null)
        // groupID = getUser().getGroupID();
        return groupID;
    }

    public void setGroupID(Integer newGroupID) {
        if (groupID == null || !groupID.equals(newGroupID)) {
            setGroupVisibleState(null);
        }
        groupID = newGroupID;
        group = getGroupHierarchy().getGroup(groupID);
        if (group == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void durationChangeActionListener(ActionEvent event) {
        trendBeanItems = null;
        lineDef = null;
        groupTrendMap = null;
        summaryItem = null;
    }

	public boolean isDataReady() {
		return dataReady;
	}

	public void setDataReady(boolean dataReady) {
		this.dataReady = dataReady;
	}
	public void setDecimalSeparator(char decimalSeparator){
		
	}
	public void setThousandSeparator(char thousandSeparator){
		
	}
	public char getDecimalSeparator(){
		return new DecimalFormatSymbols(getLocale()).getDecimalSeparator();
	}
	public char getThousandSeparator(){
		return new DecimalFormatSymbols(getLocale()).getGroupingSeparator();
	}
    // @Override
    // public void onDurationChange(Duration d) {
    // setTrendBeanItems(null);
    // }
}
