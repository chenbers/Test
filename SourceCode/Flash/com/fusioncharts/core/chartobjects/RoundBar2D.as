﻿/**
* @class RoundBar2D
* @author FusionCharts Technologies LLP, www.fusioncharts.com
* @version 3.2
*
* Copyright (C) FusionCharts Technologies LLP, 2010
*
* RoundBar2D extends the movie clip class and represents a single
* 2D rounded bar to be drawn on the chart. It takes in the required
* parameters (dimensions) and draws a bar.
*/
//Import required classes
import com.fusioncharts.extensions.MathExt;
import com.fusioncharts.extensions.DrawingExt;
import com.fusioncharts.extensions.ColorExt;
import flash.filters.ColorMatrixFilter;
class com.fusioncharts.core.chartobjects.RoundBar2D extends MovieClip {
	//Instance variables
	//Movie clip containers
	private var mc:MovieClip;
	//Width and height
	private var w:Number;
	private var h:Number;
	//Color
	private var color:String;
	//Border properties
	private var borderThickness:Number;
	private var borderAlpha:Number;
	//Constant
	private var radius:Number;
	/**
	 * Constructor method. Takes in parameters and stores
	 * in local variables.
	*/
	function RoundBar2D(target:MovieClip, w:Number, h:Number, r:Number, color:String, borderThickness:Number, borderAlpha:Number) {
		//Store parameters in instance variables
		this.mc = target;
		this.w = w;
		this.h = h;
		this.radius = r;
		this.color = color;
		this.borderThickness = borderThickness;
		this.borderAlpha = borderAlpha;
		//If the width is less than round radius, we adjust round radius
		if (Math.abs(this.w)<this.radius){
			this.radius = Math.floor(Math.abs(this.w)/2);
		}
	}
	/**
	 * draw method draws the bar.
	*/
	public function draw():Void {
		this.mc.lineStyle(borderThickness, ColorExt.getDarkColor(this.color, 0.7), borderAlpha, true, "none", "round", "miter", 1);
		//Darker color shade
		var colorShade:Number = ColorExt.getDarkColor(this.color, 0.63);
		//Create matrix object
		var matrix:Object = {matrixType:"box", w:w, h:h, x:0, y:-h/2, r:Math.PI/2};
		//Start the fill.
		//If you need without press effect - uncomment this line - this.mc.beginGradientFill("linear", [ColorExt.getDarkColor(this.color, 0.75), ColorExt.getLightColor(this.color, 0.85), ColorExt.getDarkColor(this.color, 0.8)], [100, 100, 100], [0, 205, 255], matrix);
		this.mc.beginGradientFill("linear", [ColorExt.getDarkColor(this.color, 0.75), ColorExt.getLightColor(this.color, 0.25), ColorExt.getDarkColor(this.color, 0.8), ColorExt.getLightColor(this.color, 0.65), ColorExt.getDarkColor(this.color, 0.8)], [100, 100, 100, 100, 100], [0, 25, 60, 205, 255], matrix);
		//Draw rectangle
		if (w>=0) {
			//Draw the rounded rect.			
			DrawingExt.drawRoundedRect(this.mc, 0, -h/2, w, h, {tl:0, tr:this.radius, bl:0, br:this.radius}, {t:colorShade, r:colorShade, l:colorShade, b:colorShade}, {t:borderAlpha, r:borderAlpha, l:0, b:borderAlpha}, {t:borderThickness, r:borderThickness, l:borderThickness, b:borderThickness});
		} else {
			//Draw the rounded rect.
			DrawingExt.drawRoundedRect(this.mc, w, -h/2, -w, h, {bl:this.radius, br:0, tl:this.radius, tr:0}, {t:colorShade, r:colorShade, l:colorShade, b:colorShade}, {t:0, r:borderAlpha, l:borderAlpha, b:borderAlpha}, {t:borderThickness, r:borderThickness, l:borderThickness, b:borderThickness});
		}
		this.mc.endFill();
		//We now need to brighten the bar to get the glassy effect. However, if the color
		//itself is very light, we do not brighten. 
		//We assume that a color which has a luminance less than 85% is a light color
		var hslObj:Object = ColorExt.RGB2HSL(parseInt(this.color, 16));
		
		//Brighten the column and then change contrast to get the glassy effect
		var aBrighten:Array = [1.1, 0, 0, 0, 0, 0, 1.1, 0, 0, 0, 0, 0, 1.1, 0, 0, 0, 0, 0, 1, 0];
		this.mc.filters = [new ColorMatrixFilter(aBrighten)];
		// When the bar width is very thin ( w < 2), a Overlay Movieclip is drawn within it 
		// so that user comfortably rollover on the plot. The Overlay is a transparent one, so there is no chance of 
		// Visual disparity. [BugId: FCXT-437]
		if( Math.abs(w) < 2){
			//Create the overlay movieclip
			var ol =  this.mc.createEmptyMovieClip("overlay",1);
			//No line required 
			ol.lineStyle();
			//Make white fill with completely transparent
			ol.beginFill(0xFFFFFF, 0);
			//Draw the rectangle of 2 pixel height
			ol.moveTo(1,-h/2);
			ol.lineTo(1,h/2);
			ol.lineTo(-1,h/2);
			ol.lineTo(-1,-h/2);
			ol.moveTo(1,-h/2);
			ol.endFill();
		}
	}
}
