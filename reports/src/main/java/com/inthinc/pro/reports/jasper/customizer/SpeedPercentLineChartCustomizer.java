package com.inthinc.pro.reports.jasper.customizer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

public class SpeedPercentLineChartCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jrChart)
    {
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        
        plot.getRangeAxis().setRange(0.0, 1.0);

    
    }
}
