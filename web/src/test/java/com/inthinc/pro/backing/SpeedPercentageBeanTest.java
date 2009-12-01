package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.reports.ReportCriteria;

public class SpeedPercentageBeanTest extends BaseBeanTest {

    private static final String EXPECTED_CHART_XML = "<chart adjustDiv=''0'' numDivLines=''4'' yAxisValueDecimals=''2'' bgColor=''#ffffff'' showBorder=''0'' showToolTips=''1'' showValues=''0'' showLabels=''1'' rotateLabels=''1'' slantLabels=''1'' connectNullData=''1'' SYAxisMinValue=''0'' SYAxisMaxValue=''100'' SNumberSuffix=''%'' showLegend=''1'' legendPosition=''BOTTOM'' legendMarkerCircle=''0'' legendBorderThickness=''0'' legendShadow=''0'' decimals=''2'' forcedecimals=''1'' chartLeftMargin=''3'' chartRightMargin=''3'' areaOverColumns=''0''> " 
            + "<categories> <category label=''{0}''/> <category label=''{1}''/> <category label=''{2}''/></categories>"
            + "<dataset>"
            + "<dataset seriesName=''Speeding Distance'' color=''1e88c8'' showValues=''0''> <set value=''5''/> <set value=''10''/> <set value=''15''/></dataset>"
            + "<dataset seriesName=''Driving Distance'' color=''cccccc'' showValues=''0''> <set value=''10''/> <set value=''20''/> <set value=''30''/></dataset>"
            + "</dataset>"
            + "<lineset seriesName=''% Distance Speeding'' color=''000066'' showValues=''0'' > <set value=''50''/> <set value=''50''/> <set value=''50''/></lineset></chart>";

    private static final String EXPECTED_TOTAL_DISTANCE = "60 mi";
    private static final String EXPECTED_TOTAL_SPEEDING = "30 mi (50.00%)";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void spring() {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);

        SpeedPercentageBean bean = (SpeedPercentageBean) applicationContext.getBean("speedPercentageBean");

        assertNotNull(bean.getDurationBean());
        assertNotNull(bean.getReportCriteriaService());
        assertNotNull(bean.getScoreDAO());
    }

    @Test
    public void jasper() {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);

        SpeedPercentageBean bean = (SpeedPercentageBean) applicationContext.getBean("speedPercentageBean");
        bean.getDurationBean().setDuration(Duration.THREE);

        ReportCriteria reportCriteria = bean.buildReportCriteria();

        assertNotNull(reportCriteria);

        Map<String, Object> paramMap = reportCriteria.getPramMap();
        assertNotNull("Line Chart Data set", paramMap.get("SUB_DATASET_1"));
        assertNotNull("Bar Chart Data set", paramMap.get("SUB_DATASET_2"));

        // ReportRenderer rrb = (ReportRenderer)applicationContext.getBean("reportRenderer");
        // rrb.exportSingleReportToPDF(reportCriteria, facesContext);

        /*
         * This didn't work HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse(); MockHttpServletResponse mockResponse =
         * (MockHttpServletResponse)response; byte[] pdfOutput = null; try { pdfOutput = ((MockServletOutputStream)mockResponse.getOutputStream()).content(); } catch (IOException
         * e) { // TODO Auto-generated catch block e.printStackTrace(); }
         * 
         * File file = new File("c:\\temp\\unitTest.pdf"); try { FileWriter writer = new FileWriter(file); writer.write(new String(pdfOutput)); writer.close(); } catch (IOException
         * e) { // TODO Auto-generated catch block e.printStackTrace(); }
         */

    }

    @Test
    @Ignore
    public void fusionCharts() {
        SpeedPercentageBean bean = (SpeedPercentageBean) applicationContext.getBean("speedPercentageBean");
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        bean.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
        bean.getDurationBean().setDuration(Duration.THREE);

        Date month[] = this.getMonths(3);

        List<SpeedPercentItem> speedPercentItemList = new ArrayList<SpeedPercentItem>();
        speedPercentItemList.add(new SpeedPercentItem(1000, 500, month[2]));
        speedPercentItemList.add(new SpeedPercentItem(2000, 1000, month[1]));
        speedPercentItemList.add(new SpeedPercentItem(3000, 1500, month[0]));
        
        Collections.sort(speedPercentItemList);

        bean.initChartData(speedPercentItemList);

        String chartXML = bean.getChartDef();
        assertNotNull("chart xml should be set", chartXML);
        // System.out.println(chartXML);
        String expectedChartXml = fillInMonths(EXPECTED_CHART_XML, 3);
        // System.out.println(expectedChartXml);

        assertEquals(expectedChartXml, chartXML);

        String totalDistance = bean.getTotalDistance();
        // System.out.println(totalDistance);
        assertEquals(EXPECTED_TOTAL_DISTANCE, totalDistance);

        String totalSpeeding = bean.getTotalSpeeding();
        // System.out.println(totalSpeeding);
        assertEquals(EXPECTED_TOTAL_SPEEDING, totalSpeeding);
    }

}
