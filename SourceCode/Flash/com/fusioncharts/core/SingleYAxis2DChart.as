﻿ /**
* @class SingleYAxis2DChart
* @author FusionCharts Technologies LLP, www.fusioncharts.com
* @version 3.2
*
* Copyright (C) FusionCharts Technologies LLP, 2010
* SingleYAxis2DChart extends SingleYAxisChart class to encapsulate
* functionalities of a single series 2D chart with a single axes. All such
* charts extend this class.
*/
//Import parent class
import com.fusioncharts.core.SingleYAxisChart;
//Extensions
import com.fusioncharts.extensions.ColorExt;
import com.fusioncharts.extensions.StringExt;
import com.fusioncharts.extensions.MathExt;
import com.fusioncharts.extensions.DrawingExt;
class com.fusioncharts.core.SingleYAxis2DChart extends SingleYAxisChart 
{
	//Instance variables
	/**
	* Constructor function. We invoke the super class'
	* constructor.
	*/
	function SingleYAxis2DChart (targetMC : MovieClip, depth : Number, width : Number, height : Number, x : Number, y : Number, debugMode : Boolean, lang : String, scaleMode:String, registerWithJS:Boolean, DOMId:String)
	{
		//Invoke the super class constructor
		super (targetMC, depth, width, height, x, y, debugMode, lang, scaleMode, registerWithJS, DOMId);
	}
	/**
	* feedMacros method feeds macros and their respective values
	* to the macro instance. This method is to be called after
	* calculatePoints, as we set the canvas and chart co-ordinates
	* in this method, which is known to us only after calculatePoints.
	*	@return	Nothing
	*/
	private function feedMacros () : Void 
	{
		//Feed macros one by one
		//Chart dimension macros
		this.macro.addMacro ("$chartStartX", this.x);
		this.macro.addMacro ("$chartStartY", this.y);
		this.macro.addMacro ("$chartWidth", this.width);
		this.macro.addMacro ("$chartHeight", this.height);
		this.macro.addMacro ("$chartEndX", this.width);
		this.macro.addMacro ("$chartEndY", this.height);
		this.macro.addMacro ("$chartCenterX", this.width / 2);
		this.macro.addMacro ("$chartCenterY", this.height / 2);
		//Canvas dimension macros
		this.macro.addMacro ("$canvasStartX", this.elements.canvas.x);
		this.macro.addMacro ("$canvasStartY", this.elements.canvas.y);
		this.macro.addMacro ("$canvasWidth", this.elements.canvas.w);
		this.macro.addMacro ("$canvasHeight", this.elements.canvas.h);
		this.macro.addMacro ("$canvasEndX", this.elements.canvas.toX);
		this.macro.addMacro ("$canvasEndY", this.elements.canvas.toY);
		this.macro.addMacro ("$canvasCenterX", this.elements.canvas.x + (this.elements.canvas.w / 2));
		this.macro.addMacro ("$canvasCenterY", this.elements.canvas.y + (this.elements.canvas.h / 2));
	}
	/**
	* drawCanvas method renders the chart canvas. Here, since we've introduced useRoundEdges,
	* we now draw condition based canvas.
	*	@return	Nothing
	*/
	private function drawCanvas () : Void 
	{
		//Create a new movie clip container for canvas
		var canvasMC = this.cMC.createEmptyMovieClip ("Canvas", this.dm.getDepth ("CANVAS"));
		
		//Parse the color, alpha and ratio array
		var canvasColor : Array = ColorExt.parseColorList (this.params.canvasBgColor);
		var canvasAlpha : Array = ColorExt.parseAlphaList (this.params.canvasBgAlpha, canvasColor.length, this.params.useLegacyCanvasBgAlpha);
		var canvasRatio : Array = ColorExt.parseRatioList (this.params.canvasBgRatio, canvasColor.length);
		
		//if bgAngle is not a number - assign the first value (Array) or default
		if(isNaN( Number(this.params.canvasBgAngle))){
			//check if this value is provided as an array - as other related values can be 
			//provided in an array format. if yes get the first value. 
			var cnvAngles:Array = this.params.canvasBgAngle.split(",");
			if(cnvAngles.length > 1){
				//get the first value - which also could be a non-numeric value
				if(!isNaN ( Number(cnvAngles[0]))){
					this.params.canvasBgAngle = cnvAngles[0];
				}else{
					//assign deafult.
					this.params.canvasBgAngle = this.defColors.get2DCanvasBgAngle (this.params.palette);
				}
			}else{
				//assign default.
				this.params.canvasBgAngle = this.defColors.get2DCanvasBgAngle (this.params.palette);
			}
		}
			
		//Create matrix object
		var matrix : Object = {
			matrixType : "box", w : this.elements.canvas.w, h : this.elements.canvas.h, x : - (this.elements.canvas.w / 2) , y : - (this.elements.canvas.h / 2) , r : MathExt.toRadians (this.params.canvasBgAngle)
		};
		//Start the fill.
		canvasMC.beginGradientFill ("linear", canvasColor, canvasAlpha, canvasRatio, matrix);
		
		if (this.params.useRoundEdges==true){
			//Rounded Canvas
			DrawingExt.drawRoundedRect(canvasMC, -this.elements.canvas.w/2, -this.elements.canvas.h/2, this.elements.canvas.w, this.elements.canvas.h, {tl:this.roundEdgeRadius,tr:this.roundEdgeRadius,bl:0,br:0}, {t:"", l:"", b:"", r:""}, {t:0, b:0, l:0, r:0}, {t:0, l:0, b:0, r:0});
			canvasMC._x = this.elements.canvas.x + this.elements.canvas.w/2;
			canvasMC._y = this.elements.canvas.y + this.elements.canvas.h/2;
		}else{		
			//Normal Canvas - Rectangular			
			//Set border properties - not visible
			//canvasMC.lineStyle(this.params.canvasBorderThickness, parseInt(this.params.canvasBorderColor, 16), this.params.canvasBorderAlpha);
			canvasMC.lineStyle ();
			//Move to (-w/2, 0);
			canvasMC.moveTo ( - (this.elements.canvas.w / 2) , - (this.elements.canvas.h / 2));
			
			//Draw the rectangle with center registration point
			canvasMC.lineTo (this.elements.canvas.w / 2, - (this.elements.canvas.h / 2));
			canvasMC.lineTo (this.elements.canvas.w / 2, this.elements.canvas.h / 2);
			canvasMC.lineTo ( - (this.elements.canvas.w / 2) , this.elements.canvas.h / 2);
			canvasMC.lineTo ( - (this.elements.canvas.w / 2) , - (this.elements.canvas.h / 2));
			//Set the x and y position
			canvasMC._x = this.elements.canvas.x + this.elements.canvas.w / 2;
			canvasMC._y = this.elements.canvas.y + this.elements.canvas.h / 2;
			//End Fill
			canvasMC.endFill ();
			// --------------------------- DRAW CANVAS BORDER --------------------------//
			//Canvas Border
			if (this.params.canvasBorderAlpha > 0)
			{
				//Create a new movie clip container for canvas
				var canvasBorderMC = this.cMC.createEmptyMovieClip ("CanvasBorder", this.dm.getDepth ("CANVASBORDER"));
				//Set border properties
				canvasBorderMC.lineStyle (this.params.canvasBorderThickness, parseInt (this.params.canvasBorderColor, 16) , this.params.canvasBorderAlpha);
				
				/**
				 * For scroll charts, very small plots were not showing their tooltip at presence of scroller. As scroller was drawn above the plots and 
				 * positioned with respect to the canvas border (top edge of canvas-border bottom line). At present the canvas border is drawn outside the 
				 * canvas area and it’s height, which is 1 pixel bigger than canvas height which allow rollover each plot and show all tooltips. To avoid 
				 * existing charts disparity in display, there is a boolean value named ‘isScrollChart’ which keeps track whether it is scroll charts or not.
                 */
				 //For scroll charts draw the canvas border seperately.
				if(this.params.isScrollChart){
					//Move to (-w/2 - this.params.canvasBorderThickness/2 ,-h/2 - this.params.canvasBorderThickness/2);
					canvasBorderMC.moveTo ( - (this.elements.canvas.w / 2) - this.params.canvasBorderThickness/2 , - (this.elements.canvas.h / 2) -this.params.canvasBorderThickness/2);
					//Draw the rectangle with center registration point
					canvasBorderMC.lineTo (this.elements.canvas.w / 2 + this.params.canvasBorderThickness/2, - (this.elements.canvas.h / 2)-this.params.canvasBorderThickness/2);
					canvasBorderMC.lineTo (this.elements.canvas.w / 2 + this.params.canvasBorderThickness/2, this.elements.canvas.h / 2 + this.params.canvasBorderThickness/2 +1);
					canvasBorderMC.lineTo ( - (this.elements.canvas.w / 2) - this.params.canvasBorderThickness/2 , this.elements.canvas.h / 2 +this.params.canvasBorderThickness/2+1);
					canvasBorderMC.lineTo ( - (this.elements.canvas.w / 2)- this.params.canvasBorderThickness/2 , - (this.elements.canvas.h / 2)- this.params.canvasBorderThickness/2);
				} else {
					//Move to (-w/2, -h/2);
					canvasBorderMC.moveTo ( - (this.elements.canvas.w / 2), - (this.elements.canvas.h / 2));
					//Draw the rectangle with center registration point
					canvasBorderMC.lineTo (this.elements.canvas.w / 2, - (this.elements.canvas.h / 2));
					canvasBorderMC.lineTo (this.elements.canvas.w / 2, this.elements.canvas.h / 2);
					canvasBorderMC.lineTo ( - (this.elements.canvas.w / 2), this.elements.canvas.h / 2);
					canvasBorderMC.lineTo ( - (this.elements.canvas.w / 2) , - (this.elements.canvas.h / 2));
				}
				//Set the x and y position
				canvasBorderMC._x = this.elements.canvas.x + this.elements.canvas.w / 2;
				canvasBorderMC._y = this.elements.canvas.y + this.elements.canvas.h / 2;
			}			
		}
		//Apply animation
		if (this.params.animation)
		{
			if (this.params.useRoundEdges==false || this.params.useRoundEdges==undefined){
				this.styleM.applyAnimation (canvasBorderMC, this.objects.CANVAS, this.macro, canvasBorderMC._x, - this.elements.canvas.w / 2, canvasBorderMC._y, - this.elements.canvas.h / 2, 100, 100, 100, null);
			}
			this.styleM.applyAnimation (canvasMC, this.objects.CANVAS, this.macro, canvasMC._x, - this.elements.canvas.w / 2, canvasMC._y, - this.elements.canvas.h / 2, 100, 100, 100, null);
		}
		//Apply filters
		this.styleM.applyFilters (canvasMC, this.objects.CANVAS);
		clearInterval (this.config.intervals.canvas);
	}
	/**
	* reInit method re-initializes the chart. This method is basically called
	* when the user changes chart data through JavaScript. In that case, we need
	* to re-initialize the chart, set new XML data and again render.
	*/
	public function reInit () : Void 
	{
		//Invoke super class's reInit
		super.reInit ();
		//Change local parameters here
		
	}
}
