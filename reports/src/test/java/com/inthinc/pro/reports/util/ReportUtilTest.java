package com.inthinc.pro.reports.util;

import java.util.Locale;

import junit.framework.TestCase;

public class ReportUtilTest extends TestCase {

	public void testGetScoreLegendString() {
		
		assertEquals(ReportUtil.getScoreLegendString(1.1, 2.0, Locale.GERMAN), "1,1 - 2,0");
		assertEquals(ReportUtil.getScoreLegendString(1.1, 2.0, Locale.US), "1.1 - 2.0");
	}

}
