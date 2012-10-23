package com.inthinc.pro.reports.jasper.customizer;

import java.util.Locale;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

import com.inthinc.pro.reports.util.MessageUtil;

public class StackedbarChartCustomizer extends JRAbstractChartCustomizer {

	@Override
	public void customize(JFreeChart jFreeChart, JRChart jasperChart) {

      Locale locale = (Locale)getParameterValue("REPORT_LOCALE");
      if(locale == null) {
      	locale = Locale.getDefault();
      }
      CategoryPlot plot = jFreeChart.getCategoryPlot();
      
      plot.getRangeAxis().setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));
      plot.setNoDataMessage(MessageUtil.getMessageString("noAvailableData",locale));

	}

}
