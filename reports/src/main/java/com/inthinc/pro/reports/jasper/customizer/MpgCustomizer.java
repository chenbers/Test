package com.inthinc.pro.reports.jasper.customizer;


import java.util.Locale;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

import com.inthinc.pro.reports.util.MessageUtil;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class MpgCustomizer extends JRAbstractChartCustomizer
{
    @Override
    public void customize(JFreeChart jFreeChart, JRChart jChart)
    {
        Locale locale = (Locale)getParameterValue("REPORT_LOCALE");
        if(locale == null) {
        	locale = Locale.getDefault();
        }

    	CategoryPlot plot = (CategoryPlot)jFreeChart.getPlot();
        plot.setNoDataMessage(MessageUtil.getMessageString("noAvailableData",locale));
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));
        
        //If all the values of the dataset are zero, then the chart does not by defualt show a range on the y axis. 
        //It simply displays 0.000000 which is not desireable. Here we are checking to see if all values are zero, and if
        //they are, we are setting a default y axis range.
        boolean foundData = false;
      
        for(int i = 0;i < plot.getDataset().getColumnCount();i++)
        {
            for(int j = 0; j < plot.getDataset().getRowCount();j++)
            {
                Number value = plot.getDataset().getValue(j,i);
                if(value != null && value.intValue() > 0){
                    foundData = true;
                    break;
                }
            }
        }
        if(!foundData)
        {
            plot.getRangeAxis().setRange(0.0, 30.0);
        }
    }

}
