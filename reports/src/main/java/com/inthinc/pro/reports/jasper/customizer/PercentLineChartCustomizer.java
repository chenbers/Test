package com.inthinc.pro.reports.jasper.customizer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

public class PercentLineChartCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jrChart)
    {
//        Locale locale = (Locale)getParameterValue("REPORT_LOCALE");
//        if(locale == null) {
//        	locale = Locale.getDefault();
//        }
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        
        plot.getRangeAxis().setRange(0.0, 1.0);
//        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));
    }
}
