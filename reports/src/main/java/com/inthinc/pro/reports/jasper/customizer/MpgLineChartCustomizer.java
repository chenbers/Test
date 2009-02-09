package com.inthinc.pro.reports.jasper.customizer;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class MpgLineChartCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jrChart)
    {
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        
        //Change the font of the category axis
        Font font = plot.getDomainAxis().getLabelFont();
        Font newFont = new Font(font.getName(),font.getStyle(),6);
        plot.getDomainAxis().setTickLabelFont(newFont);
        plot.setNoDataMessage("No Data Available");
    }
}
