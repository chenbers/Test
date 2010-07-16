package com.inthinc.pro.reports.hos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.hos.model.DayTotals;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;

public class HosDriverDailyLogGraph {
	
	Logger logger = Logger.getLogger(HosDriverDailyLogGraph.class);
	
	DecimalFormat decimalFormat = new DecimalFormat("00.00");
	private static final int Y_OFFSET = 23;
	
	public HosDriverDailyLogGraph()
	{
	}
	
	
    public Image drawHosLogGraph(BufferedImage bi, List<HOSRecAdjusted> hosRecList, DayTotals dayTotals)
	{
		int startIncrement = 0;
		int endIncrement = -1;

		boolean firstSeg = true;
		BasicStroke bsReg = new BasicStroke(2);
		BasicStroke bsBol = new BasicStroke(4);
		
		HOSStatus lastStatus = HOSStatus.OFF_DUTY;

		for (HOSRecAdjusted hosRec : hosRecList)
		{
            startIncrement = hosRec.getStartIncrement();
            endIncrement = startIncrement + hosRec.getTotalIncrements();
            
			if (hosRec.getTotalIncrements() > 0) {
			    // TODO: this case may already be handled by the adjusted list stuff
				if (firstSeg && startIncrement > 0)
				{
					//We have a situation where no data exists for beginning part of day,
					//so draw offduty for beginning section
					drawSegment(bi, HOSStatus.OFF_DUTY, HOSStatus.OFF_DUTY, 0, startIncrement, bsBol, false);
					lastStatus = HOSStatus.OFF_DUTY;
				}
				
				drawSegment(bi, hosRec.getAdjustedStatus(), lastStatus, startIncrement, endIncrement,          
					            (hosRec.isEdited()) ? bsBol: bsReg, !firstSeg);
				lastStatus= hosRec.getAdjustedStatus();
			}
			
			firstSeg = false;
		}
		// totals
		Graphics2D graphics = bi.createGraphics();
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(3));
		graphics.setFont(new Font(null,Font.BOLD,10)); 

		int startTotalsX = 612;
        int startTotalsY = 39;
		graphics.drawString(decimalFormat.format(dayTotals.getOffDuty()/4.0), startTotalsX, startTotalsY);
		graphics.drawString(decimalFormat.format(dayTotals.getSleeperBerth()/4.0), startTotalsX, startTotalsY += Y_OFFSET);
		graphics.drawString(decimalFormat.format(dayTotals.getDriving()/4.0), startTotalsX, startTotalsY += Y_OFFSET);
		graphics.drawString(decimalFormat.format(dayTotals.getOnDuty()/4.0), startTotalsX, startTotalsY += Y_OFFSET);

		graphics.drawString(decimalFormat.format(dayTotals.getTotal()/4.0), startTotalsX, startTotalsY += Y_OFFSET);
		graphics.dispose();
			
		return bi;
	}
	
	

    private void drawSegment(BufferedImage bi, HOSStatus status, HOSStatus lastStatus, int startSegment, int endSegment, BasicStroke lineStroke, 
	        boolean drawVertical)
	{
		final int START_POS_X = 70;
        final int START_POS_Y = 35;
		final float INCREMENT_SIZE = (float)5.6;

		int x1 = (int) (START_POS_X + (INCREMENT_SIZE * startSegment));
		int x2 = (int) (START_POS_X + (INCREMENT_SIZE * endSegment));
		int y1 = START_POS_Y + lastStatus.getCode() * Y_OFFSET;
		int y2 = START_POS_Y + status.getCode() * Y_OFFSET;

		Graphics2D graphics = bi.createGraphics();
		graphics.setColor(Color.black);
		graphics.setStroke(lineStroke);

		if (drawVertical)
		{
			//draw vertical lines connecting segments
			graphics.drawLine(x1, y1, x1, y2);
		}

		graphics.drawLine(x1, y2, x2, y2);

		graphics.dispose();
	}

}
