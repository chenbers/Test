package com.inthinc.pro.backing.ui;

import java.awt.Color;

public class ColorSelectorStandard extends ColorSelector {

	public enum StandardColors {	RED(Color.RED,"#ff0000"), 
									GREEN(Color.GREEN,"#00ff00"), 
									BLUE(Color.BLUE,"#0000ff"),
									ORANGE(new Color(0xff9900),"#ff9900"),
									PURPLE(new Color(0x9900ff),"#9900ff"),
									TURQUOISE (new Color(0x0099ff),"#0099ff"),
									CYAN(Color.CYAN,"#00ffff"), 
									YELLOW(Color.YELLOW,"#ffff00"), 
									MAGENTA(Color.MAGENTA,"#ff00ff"), 
									MAROON (new Color(0x990066),"#990066"),
									OCHRE (new Color(0xcc9933),"#cc9933"),
									HUNTER (new Color(0x009933),"#009933"),
									GRAY (new Color(0x99cccc),"#99cccc"),
									BABY_PINK (new Color(0xff99ff),"#ff99ff"),
									PEACH (new Color(0xffcc99),"#ffcc99"),
									LEMON (new Color(0xcccc66),"#cccc66"),
									PISTACHIO (new Color(0x99ff99),"#99ff99"),
									SKY_BLUE (new Color(0x99ccff),"#99ccff"),
									LILAC (new Color(0xccccff),"#ccccff"),
									CHERRY (new Color(0xcc3333),"#cc3333"),
									TAN (new Color(0xcc6600),"#cc6600"),
									RED_BROWN (new Color(0xcc6699),"#cc6699"),
									MID_GREEN (new Color(0x66cc99),"#66cc99"),
									BLACKBERRY (new Color(0x666699),"#666699"),
									BLACK(new Color(0x000000),"#000000");


		private final Color color;
		private final String entityColorKey;
		
		StandardColors(Color color, String entityColorKey){
		
			this.color = color;
			this.entityColorKey = entityColorKey;
		}

		public Color color(){return color;}
		public String entitycolorKey() {return entityColorKey;}

//		public Color modify(int n){
//			int tens = n/10; //Determines cycle
//			int b = tens%3; //brightness 60, 80, 100 
//			int s = n/30; 	//saturation 20, 50, 80, 100
//			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//			float bri,sat;
//			
//			switch (b){
//			case 2:
//				bri = .6f;
//				break;
//			case 1:
//				bri = .8f;
//				break;
//			case 0:
//				bri = 1.0f;
//				break;
//			default:
//				bri = 1.0f;				
//			}
//			switch (s){
//			case 3:
//				sat = .2f;
//				break;
//			case 2:
//				sat = .5f;
//				break;
//			case 1:
//				sat = .8f;
//				break;
//			case 0:
//				sat = 1.0f;
//				break;
//			default:
//				sat = 1.0f;				
//			}
//			return Color.getHSBColor(hsb[0], sat, bri);
//		
//		
//		}
//		public Color darker(int n){
//			float nf = (float)n;
//			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//			return Color.getHSBColor(hsb[0], hsb[1], hsb[2] - nf*0.1f);
//		}
//		public Color lighter(int n){
//			float nf = (float)n;
//			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//			return Color.getHSBColor(hsb[0], hsb[1] - nf*0.1f, hsb[2]);
//		}
//		public Color tone(int n){
//			float nf = (float)n;
//			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//			return Color.getHSBColor(hsb[0], hsb[1] - nf*0.1f, hsb[2]- nf*0.1f);
//		}
	}
	/**
	* 
	*/
	public ColorSelectorStandard() {
	// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	* @see main.ColorSelector#makeColor(int)
	*/
	@Override
	public Color makeColor(int number) {
	
		int mod = number%24;

		StandardColors[] stdColors = StandardColors.values();
		
		return stdColors[mod].color;
	
	}
	public String getEntityColorKey(int number){
		
		int mod = number%24;
		
		StandardColors[] stdColors = StandardColors.values();
		
		return stdColors[mod].entityColorKey;
	}
}
