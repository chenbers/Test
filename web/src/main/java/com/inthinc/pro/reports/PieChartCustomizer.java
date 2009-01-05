package com.inthinc.pro.reports;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class PieChartCustomizer extends JRAbstractChartCustomizer
{
    
    @Override
    public void customize(JFreeChart jFreeChart, JRChart arg1)
    {
        PiePlot plot = (PiePlot)jFreeChart.getPlot();
        plot.setIgnoreNullValues(false);
        plot.setIgnoreZeroValues(false);
        
        
        
    }

}
