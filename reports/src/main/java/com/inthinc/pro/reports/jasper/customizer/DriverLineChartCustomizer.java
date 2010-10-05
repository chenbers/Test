package com.inthinc.pro.reports.jasper.customizer;

import java.awt.Font;
import java.util.Locale;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

import com.inthinc.pro.reports.util.MessageUtil;

public class DriverLineChartCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jrChart)
    {
        //Set a fixed range for the score range
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        plot.getRangeAxis().setRange(0.0, 5.0);
        
        Locale locale = (Locale)getParameterValue("REPORT_LOCALE");
        if(locale == null) {
        	locale = Locale.getDefault();
        }

        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));

        //Change the font of the category axis
        Font font = plot.getDomainAxis().getLabelFont();
        Font newFont = new Font(font.getName(),font.getStyle(),6);
        plot.getDomainAxis().setTickLabelFont(newFont);
        plot.setNoDataMessage(MessageUtil.getMessageString("noAvailableData",locale));
    }
}
