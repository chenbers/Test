package com.inthinc.pro.reports.jasper.customizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class MpgCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jChart)
    {
        CategoryPlot plot = (CategoryPlot)jFreeChart.getPlot();
        plot.setNoDataMessage("No Data Available");
        ValueAxis valueAxis = plot.getRangeAxis();
        //valueAxis.setAutoRange(true);
        
    }

}
