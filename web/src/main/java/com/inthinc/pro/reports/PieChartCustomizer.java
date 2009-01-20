package com.inthinc.pro.reports;

import java.awt.Color;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.util.Rotation;

import com.inthinc.pro.backing.BreakdownBean;
import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class PieChartCustomizer extends JRAbstractChartCustomizer
{
    
    
    @SuppressWarnings("deprecation")
    @Override
    public void customize(JFreeChart jFreeChart, JRChart arg1)
    {
        PiePlot plot = (PiePlot)jFreeChart.getPlot();
        plot.setIgnoreNullValues(false);
        plot.setIgnoreZeroValues(false);
        plot.setNoDataMessage("No Data Available");
        
        //Looks better with a white border.
        Color color = Color.WHITE;
        plot.setSectionOutlinePaint(color);
        plot.setShadowXOffset(4);
        plot.setShadowYOffset(4);
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setShadowPaint(new Color(0xE7E6E6,false));
        plot.setLabelPaint(Color.BLACK);
        plot.setLabelShadowPaint(plot.getShadowPaint());
        plot.setLabelBackgroundPaint(plot.getBackgroundPaint());
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
                Integer hexInt = Integer.valueOf(BreakdownBean.entityColorKey.get(colorIndex),16);
                Color newColor = new Color(hexInt.intValue(),false);
                plot.setSectionPaint(colorIndex, newColor);
                colorIndex++;
            }
        }
        
    }

}
