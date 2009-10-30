package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.reports.ReportCriteria;


public class IdlePercentageBeanTest extends BaseBeanTest {

	private static final String EXPECTED_CHART_XML = "<chart bgColor=''#ffffff'' showBorder=''0'' showToolTips=''1'' showValues=''0'' showLabels=''1'' rotateLabels=''1'' slantLabels=''1'' connectNullData=''1'' decimals=''0'' SYAxisMinValue=''0'' SYAxisMaxValue=''100'' SNumberSuffix=''%'' showLegend=''1'' legendPosition=''BOTTOM'' legendMarkerCircle=''0'' legendBorderThickness=''0'' legendShadow=''0'' chartLeftMargin=''3'' chartRightMargin=''3'' areaOverColumns=''0''> " +
			"<categories> <category label=''{0}''/> <category label=''{1}''/> <category label=''{2}''/></categories>" +
			"<dataset>" +
			"<dataset seriesName=''Idle Time'' color=''6b9d1b'' showValues=''0''> <set value=''0.5''/> <set value=''2.5''/> <set value=''5.0''/></dataset>" +
			"<dataset seriesName=''Driving Time'' color=''cccccc'' showValues=''0''> <set value=''1.0''/> <set value=''5.0''/> <set value=''10.0''/></dataset>" +
			"</dataset>" +
			"<lineset seriesName=''% Idle'' color=''006600'' showValues=''0'' > <set value=''50''/> <set value=''50''/> <set value=''50''/></lineset></chart>";
	
	private static final String EXPECTED_TOTAL_DRIVING = "16.00 hrs"; 
	private static final String EXPECTED_TOTAL_IDLING = "8.00 hrs (50.00%)"; 

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void spring()
    {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
     
        IdlePercentageBean bean = (IdlePercentageBean)applicationContext.getBean("idlePercentageBean");
        
        assertNotNull(bean.getDurationBean());
        assertNotNull(bean.getReportCriteriaService());
        assertNotNull(bean.getScoreDAO());
    }


    @Test
    public void jasper()
    {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        IdlePercentageBean bean = (IdlePercentageBean)applicationContext.getBean("idlePercentageBean");
        bean.getDurationBean().setDuration(Duration.THREE);
        
        ReportCriteria reportCriteria = bean.buildReportCriteria();
        
        assertNotNull(reportCriteria);
        
        Map<String, Object> paramMap = reportCriteria.getPramMap();
        assertNotNull("Line Chart Data set", paramMap.get("SUB_DATASET_1"));
        assertNotNull("Bar Chart Data set", paramMap.get("SUB_DATASET_2"));

        //        ReportRenderer rrb = (ReportRenderer)applicationContext.getBean("reportRenderer");
//        rrb.exportSingleReportToPDF(reportCriteria, facesContext);
        

/*
 * This didn't work        
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        MockHttpServletResponse mockResponse = (MockHttpServletResponse)response;
        byte[] pdfOutput = null;
        try {
			pdfOutput = ((MockServletOutputStream)mockResponse.getOutputStream()).content();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        File file = new File("c:\\temp\\unitTest.pdf");
        try {
			FileWriter writer = new FileWriter(file);
			writer.write(new String(pdfOutput));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
*/        	
        
    }

    @Test
    @Ignore
    public void fusionCharts()
    {
        IdlePercentageBean bean = (IdlePercentageBean)applicationContext.getBean("idlePercentageBean");
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        bean.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
        bean.getDurationBean().setDuration(Duration.THREE);
        
        List<IdlePercentItem> idlePercentItemList = new ArrayList<IdlePercentItem> ();
        idlePercentItemList.add(new IdlePercentItem(3600l, 1800l));	// 1 hr driving, 1/2 hr idle
        idlePercentItemList.add(new IdlePercentItem(18000l, 9000l));	// 5 hr driving, 2 1/2 hr idle
        idlePercentItemList.add(new IdlePercentItem(36000l, 18000l));	// 10 hr driving, 5 hr idle
        
    	bean.initChartData(idlePercentItemList);
    	
    	String chartXML = bean.getChartDef();
    	assertNotNull("chart xml should be set", chartXML);
    	String expectedChartXml = fillInMonths(EXPECTED_CHART_XML, 3);
//    	System.out.println(expectedChartXml);
    	assertEquals(expectedChartXml,  chartXML);

    	
    	String totalDriving = bean.getTotalDriving();
    	System.out.println(totalDriving);
    	assertEquals(EXPECTED_TOTAL_DRIVING,  totalDriving);
    	
    	String totalIdling = bean.getTotalIdling();
    	System.out.println(totalIdling);
    	assertEquals(EXPECTED_TOTAL_IDLING,  totalIdling);
    }

}
