﻿/**
* @class ColorExt
* @author FusionCharts Technologies LLP, www.fusioncharts.com
* @version 3.2
*
* Copyright (C) FusionCharts Technologies LLP, 2010
*
* ColorExt class groups a bunch of Color related functions.
*/
import com.fusioncharts.extensions.StringExt;
class com.fusioncharts.extensions.ColorExt {
	/**
	* Since ColorExt class is just a grouping of color related methods,
	* we do not want any instances of it (as all methods wil be static).
	* So, we declare a private constructor
	*/
	private function ColorExt() {
		//Private constructor to avoid creation of instances
	}
	/**
	* formatHexColor method helps us format a given hexadecimal color
	* in the format as required by FusionCharts. FusionCharts needs that
	*  - the hex value shouldn't contain leading spaces
	*  - the hex value shouldn't contain # character
	* @param	sourceHexColor	The hex color code to be formatted
	* @param	isBarColumn		Boolean to determine whether column or bar
	* @return					The formatted hex color code without
	*							spaces or #.
	*/
	public static function formatHexColor(sourceHexColor:String, isBarColumn:Boolean):String {
		//Trim the leading spaces before #
		sourceHexColor = StringExt.leftTrimChar(sourceHexColor, " ");
		//Trim the #
		sourceHexColor = StringExt.leftTrimChar(sourceHexColor, "#");
		//For 2D bar and colum if value is space or # only then replace it with black
		if(sourceHexColor.length == 0 && isBarColumn){
			sourceHexColor = "000000";
		}
		//Return the formatted color
		return sourceHexColor;
	}
	/**
	* getDarkColor method helps us find a darker shade for a given
	* hex color string, based on the intensity specified.
	* @param	sourceHexColor	The hex color code (without #) for which
	*							we need a darker shade.
	* @param	intensity		Intensity of the darker shade which we need.
	*							It can be between 0 and 1. Lower the intensity,
	*							darker the color i.e., 0 is black and 1 is original
	*							color specified.
	* @return					The RGB numeric value of the darker color calculated.
	*/
	public static function getDarkColor(sourceHexColor:String, intensity:Number):Number {
		//Check whether the intensity is in right range
		intensity = ((intensity>1) || (intensity<0)) ? 1 : intensity;
		//Format the color in RGB notation
		var sourceclrRGB:Number = parseInt(sourceHexColor, 16);
		//Now, get the r,g,b values separated out of the specified color
		var r:Number = Math.floor(sourceclrRGB/65536);
		var g:Number = Math.floor((sourceclrRGB-r*65536)/256);
		var b:Number = sourceclrRGB-r*65536-g*256;
		//Now, get the darker color based on the Intesity Specified
		var darkColor:Number = (r*intensity) << 16 | (g*intensity) << 8 | (b*intensity);
		return (darkColor);
	}
	/**
	* getLightColor method helps us find a lighter shade for a given
	* hex color string, based on the intensity specified.
	* @param	sourceHexColor	The hex color code (without #) for which
	*							we need a lighter shade.
	* @param	intensity		Intensity of the lighter shade which we need.
	*							It can be between 0 and 1. Lower the intensity,
	*							lighter the color. 0 would mean black.
	* @return					The RGB numeric value of the lighter color calculated.
	*/
	public static function getLightColor(sourceHexColor:String, intensity:Number):Number {
		//Check whether the intensity is in right range
		intensity = ((intensity>1) || (intensity<0)) ? 1 : intensity;
		//Format the color in RGB notation
		var sourceclrRGB:Number = parseInt(sourceHexColor, 16);
		//Now, get the r,g,b values separated out of the specified color
		var r:Number = Math.floor(sourceclrRGB/65536);
		var g:Number = Math.floor((sourceclrRGB-r*65536)/256);
		var b:Number = sourceclrRGB-r*65536-g*256;
		//Now, get the lighter color based on the Intesity Specified
		var lightColor:Number = (256-((256-r)*intensity)) << 16 | (256-((256-g)*intensity)) << 8 | (256-((256-b)*intensity));
		return (lightColor);
	}
	/**
	* parseColorList method takes a list of hex colors separated
	* by comma and returns an array of the individual hex colors
	* after validating each color code.
	*	@param	strColors	List of colors separated by comma e.g.,
	*						FF0000,F1F1F1,FFCCDD etc.
	*	@param	isBarColumn	Boolean to determine whether  bar or column
	*	@return			An array whose each cell contains a single
	*						color code (validated).
	*/
	public static function parseColorList(strColors:String, isBarColumn:Boolean):Array {
		//Create am array to store input colors and final colors
		var arrInputColors:Array = new Array();
		//Output colors
		var arrColors:Array = new Array();
		//Count of valid colors
		var numCount:Number = 0;
		var i:Number;
		var strColor:String;
		//Split the colors which are separated by comma
		arrInputColors = strColors.split(",");
		//Now, run through each color in the input array and check for it's validity
		for (i=0; i<arrInputColors.length; i++) {
			//Check for the validity of hex color
			strColor = ColorExt.formatHexColor(arrInputColors[i], isBarColumn);
			//Now, if the color is empty, we do not add it - else we do
			if (strColor != "" && strColor != null) {
				//Store it in the array
				arrColors[numCount] = parseInt(strColor, 16);
				//Increase the counter
				numCount++;
			}
		}
		//Return the final list of colors
		return arrColors;
	}
	/**
	* parseAlphaList method takes a list of alphas separated
	* by comma and returns an array of the individual alphas
	*	@param	strAlphas	List of alphas separated by comma e.g.,
	*						20,30,40 etc.
	*	@param	numColors	Number of colors for which we've to build
	*						the alpha list
	*	@param	useLegacy	Whether to keep 100 default value for backword competibility
	*	@return			An array whose each cell contains a single
	*						alpha value (validated).
	*/
	public static function parseAlphaList(strAlphas:String, numColors:Number, useLegacy:Boolean):Array {
		//Input list of alpha
		var arrInputAlphas:Array = new Array();
		//Final list
		var arrAlphas:Array = new Array();
		//Extract the input alphas
		arrInputAlphas = strAlphas.split(",");
		//Count of valid alphas
		var alpha:Number;
		//Last defined Alpha
		var lastDefinedAlpha:Number;
		//Loop variable
		var i:Number;
		//Change the alpha matrix to number (from string base)
		for (i=0; i<numColors; i++) {
			//Get the alpha
			alpha = arrInputAlphas[i];
			//Now, if the alpha is non-numeric or undefined, we set our own values
			alpha = (isNaN(alpha) || (alpha == undefined)) ? ((useLegacy || isNaN(lastDefinedAlpha))? 100: lastDefinedAlpha) : Number(alpha);
			//Update Last Defined Alpha 
			lastDefinedAlpha = isNaN(arrInputAlphas[i])? lastDefinedAlpha : Number(arrInputAlphas[i]);
			//Store it in the array
			arrAlphas[i] = alpha;
		}
		//Return the array
		return arrAlphas;
	}
	/**
	* parseRatioList method takes a list of color division ratios
	* (on base of 100%) separated by comma and returns an array of
	* the individual ratios (on base of 255 hex).
	*	@param	strRatios	List of ratios (on base of 100%) separated by
	*						comma e.g., 20,40,40 or 5,5,90 etc.
	*	@param	numColors	Number of colors for which we've to build
	*						the ratio list
	*	@return			An array whose each cell contains a single
	*						ratio value (on base of 255 hex).
	*/
	public static function parseRatioList(strRatios:String, numColors:Number):Array {
		//Arrays to store input and final ratio
		var arrInputRatios:Array = new Array();
		var arrRatios:Array = new Array();
		//Split the user input ratios
		arrInputRatios = strRatios.split(",");
		//Sum of ratios
		var sumRatio:Number = 0;
		var ratio:Number;
		//Loop variable
		var i:Number;
		//First, check if all ratios are numbers and calculate sum
		for (i=0; i<numColors; i++) {
			//Get the ratio
			ratio = arrInputRatios[i];
			//Now, if the ratio is non-numeric or undefined, we set our own values
			ratio = (isNaN(ratio) || (ratio == undefined)) ? 0 : Math.abs(Number(ratio));
			//If ratio is greater than 100, restrict it to 100
			ratio = (ratio>100) ? 100 : ratio;
			//Allot it to final array
			arrRatios[i] = ratio;
			//Add to sum
			sumRatio += ratio;
		}
		//Total ratio inputted by user should not exceed 100
		sumRatio = (sumRatio>100) ? 100 : sumRatio;
		//If more colors are present than the number of ratios, we need to
		//proportionately append the rest of values
		if (arrInputRatios.length<numColors) {
			for (i=arrInputRatios.length; i<numColors; i++) {
				arrRatios[i] = (100-sumRatio)/(numColors-arrInputRatios.length);
			}
		}
		//Now, convert ratio percentage to actual values from 0 to 255 (Hex base) 
		arrRatios[-1] = 0;
		var prevRatio:Number = 0;
		for (i=0; i<numColors; i++) {
			prevRatio = Number(arrRatios[i-1]);
			arrRatios[i] = prevRatio+Number(arrRatios[i]/100*255);
			//Bind to ceiling limit - 255 for hex ratio
			arrRatios[i] = (arrRatios[i]>255) ? 255 : arrRatios[i];
		}
		//Return the ratios array
		return arrRatios;
	}
	/**
	 * RGB2HSL method returns the Hue, Luminance and Saturation color
	 * values for a given RGB value. Hue is returned in degree (base 360),
	 * luminance (light) in % and saturation in % too.
	 *	@param	rgbCode		RGB color code.
	 *	@return				Object having the following properties
	 *						h - hue
	 *						l - luminance
	 *						s - saturation
	 * Due to approximations in rounding, there might be a slight variation 
	 * in color in 35% of conversions from HSL to RGB and back. The variation
	 * is minute and occurs only in one color channel.
	*/	
	public static function RGB2HSL(rgbCode:Number):Object {
		//Extract r,g & b components from the color.	
		var r:Number = rgbCode/65536;
		var g:Number = (rgbCode-(Math.floor(r)*65536))/256;
		var b:Number = (rgbCode-(Math.floor(r)*65536)-(Math.floor(g)*256));
		//Make in ratio of 255
		r = r/255;
		g = g/255;
		b = b/255;
		//Initialize Variables
		var v:Number, m:Number, vm:Number;
		var r2:Number, g2:Number, b2:Number;
		//Hue, Saturation and Light
		var h:Number = 0;
		//Default to Black
		var s:Number = 0;
		var l:Number = 0;
		v = Math.max(Math.max(r, g), b);
		m = Math.min(Math.min(r, g), b);
		//Light
		l = (m+v)/2;
		//Light cannot be less than 0
		if (l<0) {
			throw new Error("Invalid Hex Code Specified");
			return new Object();
		}
		vm = v-m;
		s = vm;
		if (s>=0) {
			var d:Number = (l<=0.5) ? (v+m) : (2-v-m);
			s = s/d;
		} else {
			throw new Error("Invalid Hex Code Specified");
			return new Object();
		}
		r2 = (v-r)/vm;
		g2 = (v-g)/vm;
		b2 = (v-b)/vm;
		if (r == v) {
			h = (g == m ? 5+b2 : 1-g2);
		} else if (g == v) {
			h = (b == m ? 1+r2 : 3-b2);
		} else {
			h = (r == m ? 3+g2 : 5-r2);
		}
		//Convert hue into degrees
		h = (h/6)*360;
		h = (h>=360)?0:h;
		//Saturation and Light into 100%
		l = l*100;
		s = s*100;
		//Create an object representation of the same
		var rtnObj:Object = new Object();
		rtnObj.h = h;
		rtnObj.l = l;
		rtnObj.s = s;
		return rtnObj;
	}
	/**
	 * HSL2RGB method converts HSL color code into RGB code.
	 *	@param	h	Hue component of color (in base 360 degree).
	 *	@param	s	Saturation component of color (in base 100%)
	 *	@param	l	Luminance component of color (in base 100%)
	 *	@return		RGB code for the color
	*/
	public static function HSL2RGB(h:Number, s:Number, l:Number):Number {
		var v:Number;
		//RGB Components
		var r:Number, g:Number, b:Number;
		//Convert h, s, l from respective bases to 0-1 ratio
		h = h/360;
		s = s/100;
		l = l/100;
		//Default to gray color
		r = l;
		g = l;
		b = l;
		v = (l<=0.5) ? (l*(1+s)) : (l+s-l*s);
		if (v>0) {
			var m:Number, sv:Number, sextant:Number;
			var fract:Number, vsf:Number, mid1:Number, mid2:Number;
			m = l+l-v;
			sv = (v-m)/v;
			h *= 6;
			sextant = Math.floor(h);
			fract = h-sextant;
			vsf = v*sv*fract;
			mid1 = m+vsf;
			mid2 = v-vsf;
			switch (sextant) {
			case 0 :
				r = v;
				g = mid1;
				b = m;
				break;
			case 1 :
				r = mid2;
				g = v;
				b = m;
				break;
			case 2 :
				r = m;
				g = v;
				b = mid1;
				break;
			case 3 :
				r = m;
				g = mid2;
				b = v;
				break;
			case 4 :
				r = mid1;
				g = m;
				b = v;
				break;
			case 5 :
				r = v;
				g = m;
				b = mid2;
				break;
			}
		}
		//Multiply by 255 
		r = r*255;
		g = g*255;
		b = b*255;
		//Join them back using Bit operators
		var rgb:Number = r << 16 | g << 8 | b;
		return rgb;
	}
}
