package com.inthinc.pro.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.ColorSelector;
import com.inthinc.pro.util.ColorSelectorStandard;
import com.inthinc.pro.util.WebUtil;

public class MapIconFactory {
	
	private static final Logger logger = Logger.getLogger(MapIconFactory.class);
	
	private IconCreator ic;
	
	public List<MapIcon> makeMapIcons(String baseURL, String seedIcon, int count){
		
		ic = new IconCreatorFromURL();
		
		List<MapIcon> icons = new ArrayList<MapIcon>();
		
		for (int i=0; i<count; i++){
			
			icons.add(ic.getMapIcon(baseURL, seedIcon, i));
		}
		return icons;
	}
		
	public  BufferedImage loadImage(String ref) throws IOException{ 
		
		logger.debug("loadImage path is "+ref);
		
       BufferedImage bimg = null;  
       bimg = ImageIO.read(new URL(ref));  
            
       return bimg;  
	}  
	public BufferedImage changeColor(BufferedImage image, Color ofcolor, Color obcolor, Color nfcolor, Color nbcolor) {
		
		BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
	//	g.setComposite(AlphaComposite.Src);
		g.drawImage(image, null, 0, 0);
		g.dispose();
		for(int i = 0; i < dimg.getHeight(); i++) {
			for(int j = 0; j < dimg.getWidth(); j++) {
				if(dimg.getRGB(j, i) == ofcolor.getRGB()) {
					
					dimg.setRGB(j, i, nfcolor.getRGB());
				}
				if (dimg.getRGB(j,i)== obcolor.getRGB()) {
					
					dimg.setRGB(j, i, nbcolor.getRGB());
				}
			}
		}
		return dimg;
	}
	
	private abstract class IconCreator {
		
		public abstract MapIcon getMapIcon(String baseURL, String seedIcon, int number);
	}
	
	private class IconCreatorFromURL extends IconCreator{
		
		public MapIcon getMapIcon(String baseURL, String seedIcon, int number){
			
			try {
				// try to to Load the img
				WebUtil webUtil = new WebUtil();
				StringBuffer pathBuffer = new StringBuffer(webUtil.getFullRequestContextPath());
				pathBuffer.append("/"+baseURL+number+".png");
				String path = pathBuffer.toString();
				logger.debug("IconCreatorFromURL path is "+path);
				
				// Will trigger the exception that transfers to creating from scratch.
				BufferedImage loadImg = loadImage(path);
				
				return new MapIcon(path);
			}
			catch (IOException ioe){
				
				logger.debug("IconCreatorFromURL transferring to IconCreatorFromScratch");
				ic = new IconCreatorFromScratch();
				return ic.getMapIcon(baseURL, seedIcon, number);
			}
		}
		
	}
	private class IconCreatorFromScratch extends IconCreator{
		
		public MapIcon getMapIcon(String baseURL, String seedIcon, int number){
				
			// Load the image to copy from
			try{
				WebUtil webUtil = new WebUtil();
				String path = webUtil.getFullRequestContextPath();
				StringBuffer pathBuffer = new StringBuffer(path);
				logger.debug("IconCreatorFromScratch path is "+path);
				pathBuffer.append(seedIcon);
				BufferedImage loadImg = loadImage(pathBuffer.toString());
				ColorSelector cs = new ColorSelectorStandard();
				Color main = cs.makeColor(number);
				float[] mainhsb = Color.RGBtoHSB(main.getRed(), main.getGreen(), main.getBlue(), null);
				BufferedImage newImage = changeColor(loadImg, 
													new Color(0x0000FF), 
													new Color(0xCCCCFF),
													main, 
													Color.getHSBColor(mainhsb[0], mainhsb[1]*0.2f, mainhsb[2]));
				String savePath = baseURL+number+".png";
				logger.debug("IconCreatorFromScratch save path is "+savePath);
				saveImage(newImage,savePath);
				
				return new MapIcon(path+"icon_"+number+".png");
			}
			catch(IOException ioe){
				
				return null;
			}
		}
		
		private Color getBackgroundColor(int number){
			
//			float div10 = new Float(((number%10)+(number/10)%10)%10);
			float mod10 = new Float(number%10);
			float tens = new Float((number%100)/10);
			float hh = mod10*0.1f;
			float ss = 0.1f+0.1f*(tens/3);
			float bb = 1.0f;
			
			return Color.getHSBColor(hh, ss, bb);
		}
		private Color getForegroundColor(int number){
			
			float div10 = new Float(((number%10)+(number/10)%10)%10);
//			float mod10 = new Float(number%10);
			float tens = new Float((number%100)/10);
			float h = div10*0.1f;
			float b = 0.9f-0.1f*(tens/4);
			float s = 1.0f;
			
			return Color.getHSBColor(h, s, b);
		}
		/**
		 * Saves a BufferedImage to the given file, pathname must not have any
		 * periods "." in it except for the one before the format, i.e. C:/images/fooimage.png
		 * @param img
		 * @param saveFile
		 */
		private void saveImage(BufferedImage img, String savePath) {
			try {
			    String format = (savePath.endsWith(".png")) ? "png" : (savePath.endsWith(".gif"))?"gif":"jpg";
			    logger.debug("saveImage - ref is: "+savePath);
			    logger.debug("realPath is: "+new WebUtil().getRealPath(savePath));
			    logger.debug("images realPath is: "+new WebUtil().getRealPath("/"));
				ImageIO.write(img, format, new File(new WebUtil().getRealPath("/")+savePath));
				checkWritten(savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private void checkWritten(String savePath){
			
			while(true){
				try {
					// try to to Load the img
					WebUtil webUtil = new WebUtil();
					StringBuffer pathBuffer = new StringBuffer(webUtil.getFullRequestContextPath());
//					pathBuffer.append("/");
					pathBuffer.append(savePath);
					String path = pathBuffer.toString();
					logger.debug("IconCreatorFromURL path is "+path);
					
					// Will trigger the exception that continues the loop.
					BufferedImage loadImg = loadImage(path);
					
					logger.debug("IconCreatorFromURL path is written: "+path);
					return;
				}
				catch (IOException ioe){
					//try again
					logger.debug("path does not exist yet");
				}
			}
		}

	}
}
