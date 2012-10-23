package com.inthinc.pro.reports.jasper.customizer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;

import com.inthinc.pro.reports.util.MessageUtil;

public class PieChartCustomizer extends JRAbstractChartCustomizer
{
    private static List<String> entityColorKey = new ArrayList<String>()
    {
        {
            add("FF0101");
            add("FF6601");
            add("F6B305");
            add("1E88C8");
            add("6B9D1B");
        }
    };
    
    @SuppressWarnings("deprecation")
    @Override
    public void customize(JFreeChart jFreeChart, JRChart arg1)
    {
        PiePlot plot = (PiePlot)jFreeChart.getPlot();
        plot.setIgnoreNullValues(true);
        plot.setIgnoreZeroValues(true);
        Locale locale = (Locale)getParameterValue("REPORT_LOCALE");
        if(locale == null) {
            locale = Locale.getDefault();
        }
        plot.setNoDataMessage(MessageUtil.getMessageString("noAvailableData",locale));
        
        //Looks better with a white border.
        Color color = Color.WHITE;
        plot.setSectionOutlinePaint(color);
        plot.setShadowXOffset(4);
        plot.setShadowYOffset(4);
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setShadowPaint(new Color(0xE7E6E6,false));
        plot.setLabelPaint(Color.BLACK);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelPadding(new RectangleInsets(0,0,0,0));
        plot.setLabelBackgroundPaint(null);
        plot.setLabelFont(new Font(plot.getLabelFont().getFontName(),plot.getLabelFont().getStyle(),8));
        int colorIndex = 0;
        
        //We have to do this for those charts that have 0's and have the legends turned off.
        for(int i = 0;i < plot.getDataset().getItemCount();i++)
        {
            Number number = plot.getDataset().getValue(i);
            if(number.intValue() == 0)
            {
                colorIndex++;
            }else
            {
                Integer hexInt = Integer.valueOf(entityColorKey.get(colorIndex),16);
                Color newColor = new Color(hexInt.intValue(),false);
                plot.setSectionPaint(colorIndex, newColor);
                colorIndex++;
            }
        }
        
        
       
        
    }

}
