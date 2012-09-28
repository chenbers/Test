package com.inthinc.pro.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.WebUtil;

public class MapIconFactory {

	private static final Logger logger = Logger.getLogger(MapIconFactory.class);
	
	private static final String ICON_MARKERS_DIRECTORY = "/googleMapIcons/";
	private static final String ICON_LEGENDS_DIRECTORY = "/legendIcons/";
	private static final String ICON_MAP_LEGEND_DIRECTORY = "/mapLegendIcons/";
	private static final String TEAM_LEGEND_DIRECTORY="/teamLegend/";
	
	public enum IconType {
								MARKER(ICON_MARKERS_DIRECTORY),
								LEGEND(ICON_LEGENDS_DIRECTORY),
								MAP_LEGEND(ICON_MAP_LEGEND_DIRECTORY),
								TEAM_LEGEND(TEAM_LEGEND_DIRECTORY);
								
		private final String url;
		private int count;
		private List<MapIcon> iconList;
		
		private IconType(String folder){
						
			StringBuffer urlBuffer = new StringBuffer(WebUtil.getRequestContextPath());
			urlBuffer.append("/images"+folder);
			StringBuffer pathBuffer = new StringBuffer(WebUtil.getRealPath(""));
			pathBuffer.append("/images"+folder);
			
			url=urlBuffer.toString();
			String path = pathBuffer.toString();
			
			iconList = new ArrayList<MapIcon>();
			
			File file = new File(path);
			if (file.exists() && file.isDirectory()){
				
				String[] fileArray = file.list();
				count = fileArray.length;
				
				//create map
				for (int i = 0; i<count; i++){
					for(int j=0; j<count; j++){
						if (fileArray[j].contains("_"+i+".")){
							
							iconList.add(new MapIcon(url+fileArray[j]));
						}
					}
				}
				
			}
		}

		public boolean hasIcons(){
			
			return count > 0;
		}

//		public String getIcon(int number){
//			
//			//if there are more requests than icons it will recycle
//			int next = number%count;
//			return path+fileList.get(next);
//		}
		
		public List<MapIcon> getIconList(int count){
			
			if (!hasIcons()) return null;
			
			List<MapIcon> returnList = new ArrayList<MapIcon>();
			int cycle = count;
			while (cycle > 0 ){
				
				returnList.addAll(iconList);
				cycle-=iconList.size();
			}
			return returnList;
		}
	}
	public List<MapIcon> getMapIcons(IconType type, int count){
		
		return type.getIconList(count);
	}
//	private MapIcon getMapIcon(IconType type, int number){
//		
//		if (!type.hasIcons())return null;
//
//		return new MapIcon(type.getIcon(number));
//	}
	
//	public  BufferedImage loadImage(String ref) throws IOException{ 
//		
//		logger.debug("loadImage path is "+ref);
//		
//       BufferedImage bimg = null;  
//       bimg = ImageIO.read(new URL(ref));  
//            
//       return bimg;  
//	}  
//	public BufferedImage changeColor(BufferedImage image, Color ofcolor, Color obcolor, Color nfcolor, Color nbcolor) {
//		
//		BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g = dimg.createGraphics();
//	//	g.setComposite(AlphaComposite.Src);
//		g.drawImage(image, null, 0, 0);
//		g.dispose();
//		for(int i = 0; i < dimg.getHeight(); i++) {
//			for(int j = 0; j < dimg.getWidth(); j++) {
//				if(dimg.getRGB(j, i) == ofcolor.getRGB()) {
//					
//					dimg.setRGB(j, i, nfcolor.getRGB());
//				}
//				if (dimg.getRGB(j,i)== obcolor.getRGB()) {
//					
//					dimg.setRGB(j, i, nbcolor.getRGB());
//				}
//			}
//		}
//		return dimg;
//	}
	
//	private abstract class IconCreator {
//		
//		public abstract MapIcon getMapIcon(IconType type, int number);
//	}
//	
//	private class IconCreatorFromURL extends IconCreator{
//		
//		public MapIcon getMapIcon(IconType type, int number){
//			
//			if (!type.hasIcons())return null;
//			
//			//if there are more requests than icons it will recycle
//			int next = number%type.getCount();
//					
//			return new MapIcon(type.getPath());
//		}
//		
//	}

}
