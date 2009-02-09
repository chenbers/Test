package com.inthinc.pro.reports.jasper.customizer;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class DriverLineChartCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jrChart)
    {
        //Set a fixed range for the score range
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        plot.getRangeAxis().setRange(0.0, 5.0);
        
        //Change the font of the category axis
        Font font = plot.getDomainAxis().getLabelFont();
        Font newFont = new Font(font.getName(),font.getStyle(),6);
        plot.getDomainAxis().setTickLabelFont(newFont);
        plot.setNoDataMessage("No Data Available");
    }
}
