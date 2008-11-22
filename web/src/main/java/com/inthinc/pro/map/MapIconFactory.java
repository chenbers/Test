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

import com.inthinc.pro.util.WebUtil;

public class MapIconFactory {
	
	private static final Logger logger = Logger.getLogger(MapIconFactory.class);
	
	private IconCreator ic;
	
	public List<MapIcon> makeMapIcons(int count){
		
		ic = new IconCreatorFromURL();
		
		List<MapIcon> icons = new ArrayList<MapIcon>();
		
		for (int i=1; i<=count; i++){
			
			icons.add(ic.getMapIcon(i));
		}
		return icons;
	}
	
//	public MapIcon makeMapIcon(){
//		
//		MapIcon mapIcon = new MapIcon();
//		mapIcon.setUrl(makeImage());
//		
//		return mapIcon;
//	}
		
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
	/**
	 * Saves a BufferedImage to the given file, pathname must not have any
	 * periods "." in it except for the one before the format, i.e. C:/images/fooimage.png
	 * @param img
	 * @param saveFile
	 */
	public void saveImage(BufferedImage img, String savePath) {
		try {
		    String format = (savePath.endsWith(".png")) ? "png" : (savePath.endsWith(".gif"))?"gif":"jpg";
		    logger.debug("saveImage - ref is: "+savePath);
		    logger.debug("realPath is: "+new WebUtil().getRealPath(savePath));
		    logger.debug("images realPath is: "+new WebUtil().getRealPath("/"));
			ImageIO.write(img, format, new File(new WebUtil().getRealPath("/")+savePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private abstract class IconCreator {
		
		public abstract MapIcon getMapIcon(int number);
	}
	
	private class IconCreatorFromURL extends IconCreator{
		
		public MapIcon getMapIcon(int number){
			
			try {
				// try to to Load the img
				WebUtil webUtil = new WebUtil();
				StringBuffer pathBuffer = new StringBuffer(webUtil.getFullRequestContextPath());
				pathBuffer.append("/images/googleMapIcons/icon_"+number+".png");
				String path = pathBuffer.toString();
				logger.debug("IconCreatorFromURL path is "+path);
				
				// Will trigger the exception that transfers to creating from scratch.
				BufferedImage loadImg = loadImage(path);
				
				return new MapIcon(path);
			}
			catch (IOException ioe){
				
				logger.debug("IconCreatorFromURL transferring to IconCreatorFromScratch");
				ic = new IconCreatorFromScratch();
				return ic.getMapIcon(number);
			}
		}
		
	}
	private class IconCreatorFromScratch extends IconCreator{
		
		public MapIcon getMapIcon(int number){
				
			// Load the image to copy from
			try{
				WebUtil webUtil = new WebUtil();
				String path = webUtil.getFullRequestContextPath();
				StringBuffer pathBuffer = new StringBuffer(path);
				logger.debug("IconCreatorFromScratch path is "+path);
				pathBuffer.append("/images/icon_1.png");
				BufferedImage loadImg = loadImage(pathBuffer.toString());
				BufferedImage newImage = changeColor(loadImg, new Color(0xCC0000), new Color(0xffe6e6),getForegroundColor(number), getBackgroundColor(number));
				String savePath = "images/googleMapIcons/icon_"+number+".png";
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
	}
}
