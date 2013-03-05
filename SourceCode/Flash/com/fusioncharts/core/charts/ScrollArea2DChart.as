﻿ /**
* @class ScrollArea2DChart
* @author FusionCharts Technologies LLP, www.fusioncharts.com
* @version 3.2
*
* Copyright (C) FusionCharts Technologies LLP, 2010
*
* ScrollArea2DChart chart extends the SingleYAxis2DVerticalChart class to render a
* Multi-series 2D Area Chart with scrolling capabilities.
*/
//Import Chart class
import com.fusioncharts.core.Chart;
//Parent SingleYAxis2DVerticalChart Class
import com.fusioncharts.core.SingleYAxis2DVerticalChart;
//Error class
import com.fusioncharts.helper.FCError;
//Import Logger Class
import com.fusioncharts.helper.Logger;
//Style Object
import com.fusioncharts.core.StyleObject;
//Delegate
import mx.utils.Delegate;
//Legend Class
import com.fusioncharts.helper.AdvancedLegend;
import com.fusioncharts.helper.LegendIconGenerator;
import com.fusioncharts.helper.LabelMetrics;
import com.fusioncharts.helper.LabelRenderer;

import flash.display.BitmapData;
//Extensions
import com.fusioncharts.extensions.ColorExt;
import com.fusioncharts.extensions.StringExt;
import com.fusioncharts.extensions.MathExt;
import com.fusioncharts.extensions.DrawingExt;
//Scroll Bar 
import com.fusioncharts.components.FCChartHScrollBar;
class com.fusioncharts.core.charts.ScrollArea2DChart extends SingleYAxis2DVerticalChart 
{
	//Version number (if different from super Chart class)
	//private var _version:String = "3.0.0";
	//Instance variables
	//List of chart objects
	private var _arrObjects : Array;
	private var xmlData : XML;
	//Array to store x-axis categories (labels)
	private var categories : Array;
	//Array to store datasets
	private var dataset : Array;
	//Number of data sets
	private var numDS : Number;
	//Number of data items
	private var num : Number;
	//Reference to legend component of chart
	private var lgnd:AdvancedLegend;
	//Reference to legend movie clip
	private var lgndMC : MovieClip;
	//Flag to store whether scroll bar would be required or not
	private var scrollRequired:Boolean;
	//Scroll Content holder MC - We'll draw the chart and reference movie clips within this MC.
	private var scrollableMC:MovieClip;
	//Chart Content holder - scrollable
	private var scrollContentMC:MovieClip;
	//Reference movie clip, based on which we'll scroll
	private var scrollRefMC:MovieClip;
	//Masking MovieClip
	private var maskMC:MovieClip;	
	//Scroll Bar Container MC
	private var scrollBarMC:MovieClip;
	//Reference to scroll bar
	private var scrollB:FCChartHScrollBar;
	//Variable to store the instance of the label metrics class.
	private var labelMetrics:LabelMetrics;
	/**
	* Constructor function. We invoke the super class'
	* constructor and then set the objects for this chart.
	*/
	function ScrollArea2DChart (targetMC : MovieClip, depth : Number, width : Number, height : Number, x : Number, y : Number, debugMode : Boolean, lang : String, scaleMode:String, registerWithJS:Boolean, DOMId:String)
	{
		//Invoke the super class constructor
		super (targetMC, depth, width, height, x, y, debugMode, lang, scaleMode, registerWithJS, DOMId);
		//Log additional information to debugger
		//We log version from this class, so that if this class version
		//is different, we can log it		
		this.log ("Chart Type", "Scroll Area 2D Chart", Logger.LEVEL.INFO);
		_version = getFCVersion();
		this.log ("Version", _version, Logger.LEVEL.INFO);
		//List Chart Objects and set them
		_arrObjects = new Array ("BACKGROUND", "CANVAS", "CAPTION", "SUBCAPTION", "YAXISNAME", "XAXISNAME", "DIVLINES", "VDIVLINES", "YAXISVALUES", "HGRID", "VGRID", "DATALABELS", "DATAVALUES", "TRENDLINES", "TRENDVALUES", "DATAPLOT", "ANCHORS", "TOOLTIP", "VLINES", "LEGEND", "SCROLLPANE", "VLINELABELS");
		super.setChartObjects (_arrObjects);
		//Initialize the containers for chart
		this.categories = new Array ();
		this.dataset = new Array ();
		//Initialize the number of data elements present
		this.numDS = 0;
		this.num = 0;
		//By default, we assume that scroll bar wouldn't be required.
		this.scrollRequired = false;
		//As it is scroll chart set isScrollChart to true
		this.params.isScrollChart = true;
		//create the instance
		labelMetrics = new LabelMetrics();
		//set the chart type - for labels management / panel calculation.
		labelMetrics.chartType = "line";
	}
	/**
	* render method is the single call method that does the rendering of chart:
	* - Parsing XML
	* - Calculating values and co-ordinates
	* - Visual layout and rendering
	* - Event handling
	*/
	public function render (isRedraw:Boolean) : Void 
	{
		//reset all variables for label management
		labelMetrics.reset();
		//Parse the XML Data document
		this.parseXML ();
		//If it's a re-draw then do not animate
		if (isRedraw){
			this.params.animation = false;
			this.defaultGlobalAnimation = 0;
		}
		//Now, if the number of data elements is 0, we show pertinent
		//error.
		if (this.numDS * this.num == 0)
		{
			tfAppMsg = this.renderAppMessage (_global.getAppMessage ("NODATA", this.lang));
			//Add a message to log.
			this.log ("No Data to Display", "No data was found in the XML data document provided. Possible cases can be: <LI>There isn't any data generated by your system. If your system generates data based on parameters passed to it using dataURL, please make sure dataURL is URL Encoded.</LI><LI>You might be using a Single Series Chart .swf file instead of Multi-series .swf file and providing multi-series data or vice-versa.</LI>", Logger.LEVEL.ERROR);
			//Expose rendered method
			this.exposeChartRendered();
			//Also raise the no data event
			if (!isRedraw){
				this.raiseNoDataExternalEvent();
			}
		} else 
		{
			//validation for single category item. In case of single category we revert back to previous label management methods
			if(this.num == 1){
				this.params.XTLabelManagement = false;
			}
			//Detect number scales
			this.detectNumberScales ();
			//Calculate the axis limits
			this.calculateAxisLimits ();
			//Calculate exact number of div lines
			this.calcDivs ();
			//Set Style defaults
			this.setStyleDefaults ();
			//Validate trend lines
			this.validateTrendLines ();
			//Allot the depths for various charts objects now
			this.allotDepths ();
			//Calculate Points
			this.calculatePoints (isRedraw);
			//Calculate vLine Positions
			this.calcVLinesPos ();
			//Calculate trend line positions
			this.calcTrendLinePos ();
			//Feed macro values
			super.feedMacros ();
			//Remove application message
			this.removeAppMessage (this.tfAppMsg);
			//Set tool tip parameter
			this.setToolTipParam ();
			//-----Start Visual Rendering Now------//
			//Create scroll related container movie clips
			this.createContainerMC();
			//Draw background
			this.drawBackground ();
			//Set click handler
			this.drawClickURLHandler ();
			//Load background SWF
			this.loadBgSWF ();
			//Update timer
			this.timeElapsed = (this.params.animation) ? this.styleM.getMaxAnimationTime (this.objects.BACKGROUND) : 0;
			//Draw canvas
			this.config.intervals.canvas = setInterval (Delegate.create (this, drawCanvas) , this.timeElapsed);
			//Draw headers
			this.config.intervals.headers = setInterval (Delegate.create (this, drawHeaders) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation) ? this.styleM.getMaxAnimationTime (this.objects.CANVAS, this.objects.CAPTION, this.objects.SUBCAPTION, this.objects.YAXISNAME, this.objects.XAXISNAME) : 0;
			//Draw div lines
			this.config.intervals.divLines = setInterval (Delegate.create (this, drawDivLines) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation) ? this.styleM.getMaxAnimationTime (this.objects.DIVLINES, this.objects.YAXISVALUES) : 0;
			//Vertical div lines
			this.config.intervals.vDivLines = setInterval (Delegate.create (this, drawVDivLines) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation && (this.params.numVDivLines > 0)) ? this.styleM.getMaxAnimationTime (this.objects.VDIVLINES) : 0;
			//Horizontal grid
			this.config.intervals.hGrid = setInterval (Delegate.create (this, drawHGrid) , this.timeElapsed);
			//Vertical grid
			this.config.intervals.vGrid = setInterval (Delegate.create (this, drawVGrid) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation && (this.params.showAlternateHGridColor || this.params.showAlternateVGridColor)) ? this.styleM.getMaxAnimationTime (this.objects.HGRID, this.objects.VGRID) : 0;
			//Draw labels
			this.config.intervals.labels = setInterval (Delegate.create (this, drawLabels) , this.timeElapsed);
			//Draw scroll bar
			this.config.intervals.scrollBar = setInterval (Delegate.create (this, createScrollBar) , this.timeElapsed);
			//Draw line chart
			this.config.intervals.plot = setInterval (Delegate.create (this, drawAreaChart) , this.timeElapsed);
			//Legend
			this.config.intervals.legend = setInterval (Delegate.create (this, drawLegend) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation && (this.num > 1)) ? this.styleM.getMaxAnimationTime (this.objects.DATALABELS, this.objects.DATAPLOT, this.objects.LEGEND) : 0;
			//Data Values
			this.config.intervals.dataValues = setInterval (Delegate.create (this, drawValues) , this.timeElapsed);
			//Anchors
			this.config.intervals.anchors = setInterval (Delegate.create (this, drawAnchors) , this.timeElapsed);
			//Draw trend lines
			this.config.intervals.trend = setInterval (Delegate.create (this, drawTrendLines) , this.timeElapsed);
			//Draw vertical div lines
			this.config.intervals.vLine = setInterval (Delegate.create (this, drawVLines) , this.timeElapsed);
			//Update timer
			this.timeElapsed += (this.params.animation) ? this.styleM.getMaxAnimationTime (this.objects.ANCHORS, this.objects.TRENDLINES, this.objects.TRENDVALUES, this.objects.VLINES, this.objects.DATAVALUES) : 0;
			//Dispatch event that the chart has loaded.
			this.config.intervals.renderedEvent = setInterval(Delegate.create(this, exposeChartRendered) , this.timeElapsed);
			//Render context menu
			//We do not put context menu interval as we need the menu to appear
			//right from start of the play.
			this.setContextMenu ();
		}
	}
	/**
	* returnDataAsObject method creates an object out of the parameters
	* passed to this method. The idea is that we store each data point
	* as an object with multiple (flexible) properties. So, we do not
	* use a predefined class structure. Instead we use a generic object.
	*	@param	value		Value for the data.
	*	@param	color		Hex Color code.
	* 	@param	displayValue	Value that will be displayed on the chart
	*	@param	alpha		Alpha of the line
	*	@param	toolText	Tool tip text (if specified).
	*	@param	link		Link (if any) for the data.
	*	@param	showValue	Flag to show/hide value for this data.
	*	@param	isDashed	Flag whether the line would be dashed.
	*	@param	anchorSides				Number of sides of the anchor.
	*	@param	anchorRadius			Radius of the anchor (in pixels).
	*	@param	anchorBorderColor		Border color of the anchor.
	*	@param	anchorBorderThickness	Border thickness of the anchor.
	*	@param	anchorBgColor			Background color of the anchor.
	*	@param	anchorAlpha				Alpha of the anchor.
	*	@param	anchorBgAlpha			Background (fill) alpha of the anchor.
	*	@return			An object encapsulating all these properies.
	*/
	private function returnDataAsObject (value : Number, color : String, displayValue:String, alpha : Number, toolText : String, link : String, showValue : Number, isDashed : Boolean, anchorSides : Number, anchorRadius : Number, anchorBorderColor : String, anchorBorderThickness : Number, anchorBgColor : String, anchorAlpha : Number, anchorBgAlpha : Number) : Object 
	{
		//Create a container
		var dataObj : Object = new Object ();
		//Store the values
		dataObj.value = value;
		dataObj.color = color;
		//Explicitly specified display value
		dataObj.exDispVal = displayValue;
		dataObj.alpha = alpha;
		dataObj.toolText = toolText;
		dataObj.link = link;
		dataObj.showValue = (showValue == 1) ? true : false;
		dataObj.dashed = isDashed;
		//Anchor properties
		dataObj.anchorSides = anchorSides;
		dataObj.anchorRadius = anchorRadius;
		dataObj.anchorBorderColor = anchorBorderColor;
		dataObj.anchorBorderThickness = anchorBorderThickness;
		dataObj.anchorBgColor = anchorBgColor;
		dataObj.anchorAlpha = anchorAlpha;
		dataObj.anchorBgAlpha = anchorBgAlpha;
		//If the given number is not a valid number or it's missing
		//set appropriate flags for this data point
		dataObj.isDefined = ((dataObj.alpha == 0) || isNaN (value)) ? false : true;
		//Other parameters
		//X & Y Position of data point
		dataObj.x = 0;
		dataObj.y = 0;
		//X & Y Position of value tb
		dataObj.valTBX = 0;
		dataObj.valTBY = 0;
		//Return the container
		return dataObj;
	}
	/**
	* returnDataAsCat method returns data of a <category> element as
	* an object
	*	@param	label		Label of the category.
	*	@param	showLabel	Whether to show the label of this category.
	*	@param	toolText	Tool-text for the category
	*	@return			A container object with the given properties
	*/
	private function returnDataAsCat (label : String, showLabel : Number, toolText : String) : Object
	{
		//Create container object
		var catObj : Object = new Object ();
		catObj.label = label;
		catObj.showLabel = ((showLabel == 1) && (label != undefined) && (label != null) && (label != "")) ? true : false;
		catObj.toolText = toolText;
		//X and Y Position
		catObj.x = 0;
		catObj.y = 0;
		//Return container
		return catObj;
	}
	/**
	* parseXML method parses the XML data, sets defaults and validates
	* the attributes before storing them to data storage objects.
	*/
	private function parseXML () : Void 
	{
		//Get the element nodes
		var arrDocElement : Array = this.xmlData.childNodes;
		//Loop variable
		var i : Number;
		var j : Number;
		var k : Number;
		//Look for <graph> element
		for (i = 0; i < arrDocElement.length; i ++)
		{
			//If it's a <graph> element, proceed.
			//Do case in-sensitive mathcing by changing to upper case
			if (arrDocElement [i].nodeName.toUpperCase () == "GRAPH" || arrDocElement [i].nodeName.toUpperCase () == "CHART")
			{
				//Extract attributes of <graph> element
				this.parseAttributes (arrDocElement [i]);
				//Extract common attributes/over-ride chart specific ones
				this.parseCommonAttributes (arrDocElement [i], true);
				//Now, get the child nodes - first level nodes
				//Level 1 nodes can be - CATEGORIES, DATASET, TRENDLINES, STYLES etc.
				var arrLevel1Nodes : Array = arrDocElement [i].childNodes;
				var setNode : XMLNode;
				//Iterate through all level 1 nodes.
				for (j = 0; j < arrLevel1Nodes.length; j ++)
				{
					if (arrLevel1Nodes [j].nodeName.toUpperCase () == "CATEGORIES")
					{
						//Categories Node.
						var categoriesNode : XMLNode = arrLevel1Nodes [j];
						//Convert attributes to array
						var categoriesAtt : Array = this.getAttributesArray (categoriesNode);
						//Extract attributes of this node.
						this.params.catFont = getFV (categoriesAtt ["font"] , this.params.outCnvBaseFont);
						this.params.catFontSize = getFN (categoriesAtt ["fontsize"] , this.params.outCnvBaseFontSize);
						this.params.catFontColor = formatColor (getFV (categoriesAtt ["fontcolor"] , this.params.outCnvBaseFontColor));
						//Get reference to child node.
						var arrLevel2Nodes : Array = arrLevel1Nodes [j].childNodes;
						//Iterate through all child-nodes of CATEGORIES element
						//and search for CATEGORY or VLINE node
						for (k = 0; k < arrLevel2Nodes.length; k ++)
						{
							if (arrLevel2Nodes [k].nodeName.toUpperCase () == "CATEGORY")
							{
								//Category Node.
								//Update counter
								this.num ++;
								//Extract attributes
								var categoryNode : XMLNode = arrLevel2Nodes [k];
								var categoryAtt : Array = this.getAttributesArray (categoryNode);
								//Category label.
								var catLabel : String = getFV (categoryAtt ["label"] , categoryAtt ["name"] , "");
								var catShowLabel : Number = getFN (categoryAtt ["showlabel"] , categoryAtt ["showname"] , this.params.showLabels);
								var catToolText : String = getFV (categoryAtt ["tooltext"] , categoryAtt ["hovertext"] , catLabel);
								//Store it in data container.
								this.categories [this.num] = this.returnDataAsCat (catLabel, catShowLabel, catToolText);
							} 
							else if (arrLevel2Nodes [k].nodeName.toUpperCase () == "VLINE")
							{
								//Vertical axis division Node - extract child nodes
								var vLineNode : XMLNode = arrLevel2Nodes [k];
								//Parse and store
								this.parseVLineNode (vLineNode, this.num);
							}
						}
					} else if (arrLevel1Nodes [j].nodeName.toUpperCase () == "DATASET")
					{
						//Increment
						this.numDS ++;
						//Dataset node.
						var dataSetNode : XMLNode = arrLevel1Nodes [j];
						//Get attributes array
						var dsAtts : Array = this.getAttributesArray (dataSetNode);
						//Create storage object in dataset array
						this.dataset [this.numDS] = new Object ();
						//Store attributes
						this.dataset [this.numDS].seriesName = getFV (dsAtts ["seriesname"] , "");
						this.dataset [this.numDS].color = formatColor (getFV (dsAtts ["color"] , this.params.plotFillColor, this.defColors.getColor ()));
						this.dataset [this.numDS].alpha = getFN (dsAtts ["alpha"] , this.params.plotFillAlpha);
						this.dataset [this.numDS].showValues = toBoolean (getFN (dsAtts ["showvalues"] , this.params.showValues));
						this.dataset [this.numDS].includeInLegend = toBoolean (getFN (dsAtts ["includeinlegend"] , 1));
						//Dash Properties
						this.dataset [this.numDS].dashed = toBoolean (getFN (dsAtts ["dashed"] , this.params.plotBorderDashed));
						//Data set anchors
						this.dataset [this.numDS].drawAnchors = toBoolean (getFN (dsAtts ["drawanchors"] , dsAtts ["showanchors"] , this.params.drawAnchors));
						this.dataset [this.numDS].anchorSides = getFN (dsAtts ["anchorsides"] , this.params.anchorSides);
						this.dataset [this.numDS].anchorRadius = getFN (dsAtts ["anchorradius"] , this.params.anchorRadius);
						this.dataset [this.numDS].anchorBorderColor = formatColor (getFV (dsAtts ["anchorbordercolor"] , this.params.anchorBorderColor, this.dataset [this.numDS].color));
						this.dataset [this.numDS].anchorBorderThickness = getFN (dsAtts ["anchorborderthickness"] , this.params.anchorBorderThickness);
						this.dataset [this.numDS].anchorBgColor = formatColor (getFV (dsAtts ["anchorbgcolor"] , this.params.anchorBgColor));
						this.dataset [this.numDS].anchorAlpha = getFN (dsAtts ["anchoralpha"] , this.params.anchorAlpha);
						this.dataset [this.numDS].anchorBgAlpha = getFN (dsAtts ["anchorbgalpha"] , this.params.anchorBgAlpha);
						//Area Border Properties
						this.dataset [this.numDS].showPlotBorder = toBoolean (getFN (dsAtts ["showplotborder"] , dsAtts ["showareaborder"] , this.params.showPlotBorder));
						this.dataset [this.numDS].plotBorderColor = formatColor (getFV (dsAtts ["plotbordercolor"] , dsAtts ["areabordercolor"] , this.params.plotBorderColor));
						this.dataset [this.numDS].plotBorderThickness = getFN (dsAtts ["plotborderthickness"] , dsAtts ["areaborderthickness"] , this.params.plotBorderThickness);
						this.dataset [this.numDS].plotBorderAlpha = getFN (dsAtts ["plotborderalpha"] , this.params.plotBorderAlpha);
						//Create data array under it.
						this.dataset [this.numDS].data = new Array ();
						//Get reference to child node.
						var arrLevel2Nodes : Array = arrLevel1Nodes [j].childNodes;
						//Iterate through all child-nodes of DATASET element
						//and search for SET node
						//Counter
						var setCount : Number = 0;
						//Whether any anchor is visible or not
						var anyAnchorVisible:Boolean = false;
						//Whether any plot is visible
						var anyPlotVisible:Boolean = false;
						//Previous plot alpha
						var previousPlotAlpha:Number = 0;
						//Visible set count
						var visibleSetCount:Number = 0;
						for (k = 0; k < arrLevel2Nodes.length; k ++)
						{
							if (arrLevel2Nodes [k].nodeName.toUpperCase () == "SET")
							{
								//Set Node. So extract the data.
								//Update counter
								setCount ++;
								//Get reference to node.
								setNode = arrLevel2Nodes [k];
								//Get attributes
								var atts : Array;
								atts = this.getAttributesArray (setNode);
								//Now, get value.
								var setValue : Number = this.getSetValue (atts ["value"]);
								//Get explicitly specified display value
								var setExDispVal : String = getFV( atts["displayvalue"], "");
								//We do NOT unescape the link, as this will be done
								//in invokeLink method for the links that user clicks.
								var setLink : String = getFV (atts ["link"] , "");
								var setToolText : String = getFV (atts ["tooltext"] , atts ["hovertext"]);
								var setColor : String = formatColor (getFV (atts ["color"] , this.dataset [this.numDS].color));
								var setAlpha : Number = getFN (atts ["alpha"] , this.dataset [this.numDS].alpha);
								var setShowValue : Number = getFN (atts ["showvalue"] , this.dataset [this.numDS].showValues);
								var setDashed : Boolean = toBoolean (getFN (atts ["dashed"] , this.dataset [this.numDS].dashed));
								//Anchor properties for individual set
								var setAnchorSides : Number = getFN (atts ["anchorsides"] , this.dataset [this.numDS].anchorSides);
								var setAnchorRadius : Number = getFN (atts ["anchorradius"] , this.dataset [this.numDS].anchorRadius);
								var setAnchorBorderColor : String = formatColor (getFV (atts ["anchorbordercolor"] , this.dataset [this.numDS].anchorBorderColor));
								var setAnchorBorderThickness : Number = getFN (atts ["anchorborderthickness"] , this.dataset [this.numDS].anchorBorderThickness);
								var setAnchorBgColor : String = formatColor (getFV (atts ["anchorbgcolor"] , this.dataset [this.numDS].anchorBgColor));
								var setAnchorAlpha : Number = getFN (atts ["anchoralpha"] , this.dataset [this.numDS].anchorAlpha);
								var setAnchorBgAlpha : Number = getFN (atts ["anchorbgalpha"] , this.dataset [this.numDS].anchorBgAlpha);
								//Depending on the alpha set on each anchor we need to check whether all anchors are invisible.
								if(!anyAnchorVisible){
									if(setAlpha > 0 && setAnchorAlpha > 0 &&  setAnchorRadius != 0 && !isNaN(setValue)){
										anyAnchorVisible = true;
									}
								}
								//Whether plots are to be shown or not
								if(!anyPlotVisible){
									if(!isNaN(setValue) && setAlpha > 0 && (previousPlotAlpha > 0 || setShowValue ==1)){
										anyPlotVisible = true;
									}
								}
								//If no of visible set is more than 1 and connectNullData is true
								if(!isNaN(setValue) && setAlpha > 0 && !anyPlotVisible){
									visibleSetCount++;
									anyPlotVisible = (visibleSetCount >1 &&  this.params.connectNullData)? true:anyPlotVisible;
								}
								//Reset previous plot alpha
								previousPlotAlpha = setAlpha;
								//Store all these attributes as object.
								this.dataset [this.numDS].data [setCount] = this.returnDataAsObject (setValue, setColor, setExDispVal, setAlpha, setToolText, setLink, setShowValue, setDashed, setAnchorSides, setAnchorRadius, setAnchorBorderColor, setAnchorBorderThickness, setAnchorBgColor, setAnchorAlpha, setAnchorBgAlpha);
							}
						}
						//If any plot is visible in the dataset, that dataset's legend item should appear.
						//Also it has to consider that dataset legend is included.
						if(this.dataset [this.numDS].includeInLegend){
							this.dataset [this.numDS].includeInLegend = (anyPlotVisible || anyAnchorVisible)? true:false;
						}
					} else if (arrLevel1Nodes [j].nodeName.toUpperCase () == "STYLES")
					{
						//Styles Node - extract child nodes
						var arrStyleNodes : Array = arrLevel1Nodes [j].childNodes;
						//Parse the style nodes to extract style information
						super.parseStyleXML (arrStyleNodes);
					} else if (arrLevel1Nodes [j].nodeName.toUpperCase () == "TRENDLINES")
					{
						//Trend lines node
						var arrTrendNodes : Array = arrLevel1Nodes [j].childNodes;
						//Parse the trend line nodes
						super.parseTrendLineXML (arrTrendNodes);
					}
				}
			}
		}
		//Delete all temporary objects used for parsing XML Data document
		delete setNode;
		delete arrDocElement;
		delete arrLevel1Nodes;
		delete arrLevel2Nodes;
	}
	/**
	* parseAttributes method parses the attributes and stores them in
	* chart storage objects.
	* Starting ActionScript 2, the parsing of XML attributes have also
	* become case-sensitive. However, prior versions of FusionCharts
	* supported case-insensitive attributes. So we need to parse all
	* attributes as case-insensitive to maintain backward compatibility.
	* To do so, we first extract all attributes from XML, convert it into
	* lower case and then store it in an array. Later, we extract value from
	* this array.
	* @param	graphElement	XML Node containing the <graph> element
	*							and it's attributes
	*/
	private function parseAttributes (graphElement : XMLNode) : Void 
	{
		//Array to store the attributes
		var atts : Array = this.getAttributesArray (graphElement);
		//NOW IT'S VERY NECCESARY THAT WHEN WE REFERENCE THIS ARRAY
		//TO GET AN ATTRIBUTE VALUE, WE SHOULD PROVIDE THE ATTRIBUTE
		//NAME IN LOWER CASE. ELSE, UNDEFINED VALUE WOULD SHOW UP.
		//Extract attributes pertinent to this chart
		//Which palette to use?
		this.params.palette = getFN (atts ["palette"] , 1);
		//Palette colors to use
		this.params.paletteColors = getFV(atts["palettecolors"],"");
		//Set palette colors before parsing the <set> nodes.
		this.setPaletteColors();
		// ---------- PADDING AND SPACING RELATED ATTRIBUTES ----------- //
		//captionPadding = Space between caption/subcaption and canvas start Y
		this.params.captionPadding = getFN (atts ["captionpadding"] , 10);
		//Canvas Padding is the space between the canvas left/right border
		//and first/last data point
		this.params.canvasPadding = Number(atts ["canvaspadding"]);
		//we need to keep track whether canvasPadding attribute is explicitly defined by user
		if(isNaN(this.params.canvasPadding)){
			//set default value to 0
			this.params.canvasPadding = 0;
		}else{
			//set the boolean value that this is explicitly defined value
			labelMetrics.defaultCanvasPadding = false;
		}
		//set the canvasPadding to labelMetrics
		labelMetrics.canvasPadding = this.params.canvasPadding;
		//Padding for x-axis name - to the right
		this.params.xAxisNamePadding = getFN (atts ["xaxisnamepadding"] , 5);
		//Padding for y-axis name - from top
		this.params.yAxisNamePadding = getFN (atts ["yaxisnamepadding"] , 5);
		//Y-Axis Values padding - Horizontal space between the axis edge and
		//y-axis values or trend line values (on left/right side).
		this.params.yAxisValuesPadding = getFN (atts ["yaxisvaluespadding"] , 2);
		//Label padding - Vertical space between the labels and canvas end position
		this.params.labelPadding = getFN (atts ["labelpadding"] , atts ["labelspadding"] , 3);
		//Value padding - vertical space between the anchors and start of value textboxes
		this.params.valuePadding = getFN (atts ["valuepadding"] , 5);
		//Padding of legend from right/bottom side of canvas
		this.params.legendPadding = getFN (atts ["legendpadding"] , 6);
		//Chart Margins - Empty space at the 4 sides
		this.params.chartLeftMargin = getFN (atts ["chartleftmargin"] , 15);
		this.params.chartRightMargin = getFN (atts ["chartrightmargin"] , 15);
		this.params.chartTopMargin = getFN (atts ["charttopmargin"] , 15);
		this.params.chartBottomMargin = getFN (atts ["chartbottommargin"] , 15);
		
		labelMetrics.updateProp("chartObj", {chartWidth: this.width, chartLeftMargin: this.params.chartLeftMargin, chartRightMargin:this.params.chartRightMargin});
		
		// -------------------------- HEADERS ------------------------- //
		//Chart Caption and sub Caption
		this.params.caption = getFV (atts ["caption"] , "");
		this.params.subCaption = getFV (atts ["subcaption"] , "");
		//X and Y Axis Name
		this.params.xAxisName = getFV (atts ["xaxisname"] , "");
		this.params.yAxisName = getFV (atts ["yaxisname"] , "");
		//Adaptive yMin - if set to true, the y min will be based on the values
		//provided. It won't be set to 0 in case of all positive values
		this.params.setAdaptiveYMin = toBoolean (getFN (atts ["setadaptiveymin"] , 0));
		// --------------------- CONFIGURATION ------------------------- //
		//The upper and lower limits of y and x axis
		this.params.yAxisMinValue = atts ["yaxisminvalue"];
		this.params.yAxisMaxValue = atts ["yaxismaxvalue"];
		//Number of points to show on the chart at any point of time
		//We do not assume a default value now, as we'll later calculate the same.
		this.params.numVisiblePlot = atts["numvisibleplot"];
		//Whether to set animation for entire chart.
		this.params.animation = toBoolean (getFN (this.defaultGlobalAnimation, atts ["animation"] , 1));
		//Whether to set the default chart animation
		this.params.defaultAnimation = toBoolean (getFN (atts ["defaultanimation"] , 1));
		//Whether null data points are to be connected or left broken
		this.params.connectNullData = toBoolean (getFN (atts ["connectnulldata"] , 0));
		//Configuration to set whether to show the labels
		this.params.showLabels = toBoolean (getFN (atts ["showlabels"] , atts ["shownames"] , 1));
		//Label Display Mode - WRAP, STAGGER, ROTATE or NONE
		this.params.labelDisplay = getFV (atts ["labeldisplay"] , "AUTO");
		//Remove spaces and capitalize
		this.params.labelDisplay = StringExt.removeSpaces (this.params.labelDisplay);
		this.params.labelDisplay = this.params.labelDisplay.toUpperCase ();
		//Option to show vertical x-axis labels
		this.params.rotateLabels = getFV (atts ["rotatelabels"] , atts ["rotatenames"]);
		//Whether to slant label (if rotated)
		this.params.slantLabels = toBoolean (getFN (atts ["slantlabels"] , atts ["slantlabel"] , 0));
		//Angle of rotation based on slanting
		this.config.labelAngle = (this.params.slantLabels == true) ? 315 : 270;
		//If rotateLabels has been explicitly specified, we assign ROTATE value to this.params.labelDisplay
		this.params.labelDisplay = (this.params.rotateLabels == "1") ? "ROTATE" : this.params.labelDisplay;
		//Step value for labels - i.e., show all labels or skip every x label
		this.params.labelStep = int (getFN (atts ["labelstep"] , 1));
		//Cannot be less than 1
		this.params.labelStep = (this.params.labelStep < 1) ? 1 : this.params.labelStep;
		//Number of stagger lines
		this.params.staggerLines = int (getFN (atts ["staggerlines"] , 2));
		//Cannot be less than 2
		this.params.staggerLines = (this.params.staggerLines < 2) ? 2 : this.params.staggerLines;
		//Configuration whether to show data values
		this.params.showValues = toBoolean (getFN (atts ["showvalues"] , 1));
		//Whether to rotate values
		this.params.rotateValues = toBoolean (getFN (atts ["rotatevalues"] , 0));
		//Option to show/hide y-axis values
		this.params.showYAxisValues = getFN (atts ["showyaxisvalues"] , atts ["showyaxisvalue"] , 1);
		this.params.showLimits = toBoolean (getFN (atts ["showlimits"] , this.params.showYAxisValues));
		this.params.showDivLineValues = toBoolean (getFN (atts ["showdivlinevalue"] , atts ["showdivlinevalues"] , this.params.showYAxisValues));
		//Y-axis value step- i.e., show all y-axis or skip every x(th) value
		this.params.yAxisValuesStep = int (getFN (atts ["yaxisvaluesstep"] , atts ["yaxisvaluestep"] , 1));
		//Cannot be less than 1
		this.params.yAxisValuesStep = (this.params.yAxisValuesStep < 1) ? 1 : this.params.yAxisValuesStep;
		//Whether to automatically adjust div lines
		this.params.adjustDiv = toBoolean (getFN (atts ["adjustdiv"] , 1));
		//Whether to rotate y-axis name
		this.params.rotateYAxisName = toBoolean (getFN (atts ["rotateyaxisname"] , 1));
		//Max width to be alloted to y-axis name - No defaults, as it's calculated later.
		this.params.yAxisNameWidth = (this.params.yAxisName != undefined && this.params.yAxisName != "") ? atts ["yaxisnamewidth"] : 0;
		//Click URL
		this.params.clickURL = getFV (atts ["clickurl"] , "");
		// ------------------------- COSMETICS -----------------------------//
		//Background properties - Gradient
		this.params.bgColor = getFV (atts ["bgcolor"] , this.defColors.get2DBgColor (this.params.palette));
		this.params.bgAlpha = getFV (atts ["bgalpha"] , this.defColors.get2DBgAlpha (this.params.palette));
		this.params.bgRatio = getFV (atts ["bgratio"] , this.defColors.get2DBgRatio (this.params.palette));
		this.params.bgAngle = getFV (atts ["bgangle"] , this.defColors.get2DBgAngle (this.params.palette));
		//Border Properties of chart
		this.params.showBorder = toBoolean (getFN (atts ["showborder"] , 1));
		this.params.borderColor = formatColor (getFV (atts ["bordercolor"] , this.defColors.get2DBorderColor (this.params.palette)));
		this.params.borderThickness = getFN (atts ["borderthickness"] , 1);
		this.params.borderAlpha = getFN (atts ["borderalpha"] , this.defColors.get2DBorderAlpha (this.params.palette));
		//Canvas background properties - Gradient
		this.params.canvasBgColor = getFV (atts ["canvasbgcolor"] , this.defColors.get2DCanvasBgColor (this.params.palette));
		this.params.canvasBgAlpha = getFV (atts ["canvasbgalpha"] , this.defColors.get2DCanvasBgAlpha (this.params.palette));
		this.params.canvasBgRatio = getFV (atts ["canvasbgratio"] , this.defColors.get2DCanvasBgRatio (this.params.palette));
		this.params.canvasBgAngle = getFV (atts ["canvasbgangle"] , this.defColors.get2DCanvasBgAngle (this.params.palette));
		//Canvas Border properties
		this.params.canvasBorderColor = formatColor (getFV (atts ["canvasbordercolor"] , this.defColors.get2DCanvasBorderColor (this.params.palette)));
		this.params.canvasBorderThickness = getFN (atts ["canvasborderthickness"] , 1);
		//disregard negative value
		if(this.params.canvasBorderThickness < 0){
			this.params.canvasBorderThickness = 1;
		}
		this.params.canvasBorderAlpha = getFN (atts ["canvasborderalpha"] , this.defColors.get2DCanvasBorderAlpha (this.params.palette));
		//Scroll Properties
		//Color for the scroll bar
		this.params.scrollColor = formatColor(getFV(atts["scrollcolor"], this.defColors.get2DAltHGridColor (this.params.palette)));
		//Now canvas border is drawn completely outside the canvas area for scroll charts.
		//Scroll padding default value is 1 due to show every plot's tooltip even that is very minor and scrollbar does not come above plots. 
		this.params.scrollPadding = getFN (atts ["scrollpadding"] ,1);
		//Validate scrollpadding
		this.params.scrollPadding = (this.params.scrollPadding < 1) ? 1 : this.params.scrollPadding;
		//Height of scroll bar
		this.params.scrollHeight = getFN (atts ["scrollheight"] , 16);
		//Width of plus and minus button
		this.params.scrollBtnWidth = getFN (atts ["scrollbtnwidth"] , 16);
		//Padding between the button and the face.
		this.params.scrollBtnPadding = getFN (atts ["scrollbtnpadding"] , 0);
		//Whether to scroll to end
		this.params.scrollToEnd = getFN (atts ["scrolltoend"] , 0);
		//Plot gradient color
		if (atts ["plotgradientcolor"] == "")
		{
			//If some one doesn't want to specify a plot gradient color
			//i.e., he opts for solid fills
			this.params.plotGradientColor = ""
		}else
		{
			this.params.plotGradientColor = formatColor (getFV (atts ["plotgradientcolor"] , this.defColors.get2DPlotGradientColor (this.params.palette)));
		}
		//added attribute to control plotGradientColor. As the default behaviour of any blank attribute is to revert to the default attribute.
		//Since V 3.2.2 - sr3 - we discourage using plotGradientColor with blank ("") value.
		this.params.usePlotGradientColor = toBoolean ( getFN ( atts['useplotgradientcolor'], 1));
		if(!this.params.usePlotGradientColor){
			this.params.plotGradientColor = "";
		}
		//Area fill properties
		this.params.plotFillAngle = getFN (atts ["plotfillangle"] , 270);
		this.params.plotFillColor = getFV (atts ["plotfillcolor"] , atts ["areabgcolor"]);
		this.params.plotFillAlpha = getFN (atts ["plotfillalpha"] , atts ["areaalpha"] , 70);
		//Area Border Properties
		this.params.showPlotBorder = toBoolean (getFN (atts ["showplotborder"] , atts ["showareaborder"] , 1));
		this.params.plotBorderColor = formatColor (getFV (atts ["plotbordercolor"] , atts ["areabordercolor"] , "666666"));
		this.params.plotBorderThickness = getFN (atts ["plotborderthickness"] , atts ["areaborderthickness"] , 1);
		this.params.plotBorderAlpha = getFN (atts ["plotborderalpha"] , (this.params.showPlotBorder == true) ?95 : 0);
		//Plot is dashed
		this.params.plotBorderDashed = toBoolean (getFN (atts ["plotborderdashed"] , 0));
		//Dash Properties
		this.params.plotBorderDashLen = getFN (atts ["plotborderdashlen"] , 5);
		this.params.plotBorderDashGap = getFN (atts ["plotborderdashgap"] , 4);
		//Legend properties
		this.params.showLegend = toBoolean (getFN (atts ["showlegend"] , 1));
		//Alignment position
		this.params.legendPosition = getFV (atts ["legendposition"] , "BOTTOM");
		//Legend position can be either RIGHT or BOTTOM -Check for it
		this.params.legendPosition = (this.params.legendPosition.toUpperCase () == "RIGHT") ?"RIGHT" : "BOTTOM";
		this.params.interactiveLegend = toBoolean(getFN(atts ["interactivelegend"] , 1));
		this.params.legendCaption = getFV(atts ["legendcaption"] , "");
		this.params.legendMarkerCircle = toBoolean(getFN(atts ["legendmarkercircle"] , 0));
		this.params.legendBorderColor = formatColor (getFV (atts ["legendbordercolor"] , this.defColors.get2DLegendBorderColor (this.params.palette)));
		this.params.legendBorderThickness = getFN (atts ["legendborderthickness"] , 1);
		this.params.legendBorderAlpha = getFN (atts ["legendborderalpha"] , 100);
		this.params.legendBgColor = formatColor (getFV (atts ["legendbgcolor"] , this.defColors.get2DLegendBgColor (this.params.palette)));
		this.params.legendBgAlpha = getFN (atts ["legendbgalpha"] , 100);
		this.params.legendShadow = toBoolean (getFN (atts ["legendshadow"] , 1));
		this.params.legendAllowDrag = toBoolean (getFN (atts ["legendallowdrag"] , 0));
		this.params.legendScrollBgColor = formatColor (getFV (atts ["legendscrollbgcolor"] , "CCCCCC"));
		this.params.legendScrollBarColor = formatColor (getFV (atts ["legendscrollbarcolor"] , this.params.legendBorderColor));
		this.params.legendScrollBtnColor = formatColor (getFV (atts ["legendscrollbtncolor"] , this.params.legendBorderColor));
		this.params.reverseLegend = toBoolean (getFN (atts ["reverselegend"] , 0));		
		this.params.legendIconScale = getFN (atts ["legendiconscale"] , 1);
		if(this.params.legendIconScale <= 0 || this.params.legendIconScale > 5){
			this.params.legendIconScale = 1;
		}
		this.params.legendNumColumns = Math.round(getFN (atts ["legendnumcolumns"] , 0));
		if(this.params.legendNumColumns < 0){
			this.params.legendNumColumns = 0;
		}
		this.params.minimiseWrappingInLegend = toBoolean (getFN (atts ["minimisewrappinginlegend"] , 0));
		//Horizontal grid division Lines - Number, color, thickness & alpha
		//Necessarily need a default value for numDivLines.
		this.params.numDivLines = getFN (atts ["numdivlines"] , 4);
		this.params.divLineColor = formatColor (getFV (atts ["divlinecolor"] , this.defColors.get2DDivLineColor (this.params.palette)));
		this.params.divLineThickness = getFN (atts ["divlinethickness"] , 1);
		this.params.divLineAlpha = getFN (atts ["divlinealpha"] , this.defColors.get2DDivLineAlpha (this.params.palette));
		this.params.divLineIsDashed = toBoolean (getFN (atts ["divlineisdashed"] , 0));
		this.params.divLineDashLen = getFN (atts ["divlinedashlen"] , 4);
		this.params.divLineDashGap = getFN (atts ["divlinedashgap"] , 2);
		//Vertical div lines
		this.params.numVDivLines = getFN (atts ["numvdivlines"] , 0);
		this.params.vDivLineColor = formatColor (getFV (atts ["vdivlinecolor"] , this.params.divLineColor));
		this.params.vDivLineThickness = getFN (atts ["vdivlinethickness"] , this.params.divLineThickness);
		this.params.vDivLineAlpha = getFN (atts ["vdivlinealpha"] , this.params.divLineAlpha);
		this.params.vDivLineIsDashed = toBoolean (getFN (atts ["vdivlineisdashed"] , this.params.divLineIsDashed));
		this.params.vDivLineDashLen = getFN (atts ["vdivlinedashlen"] , this.params.divLineDashLen);
		this.params.vDivLineDashGap = getFN (atts ["vdivlinedashgap"] , this.params.divLineDashGap);
		//Zero Plane properties
		this.params.showZeroPlane = true;
		this.params.zeroPlaneColor = formatColor (getFV (atts ["zeroplanecolor"] , this.params.divLineColor));
		this.params.zeroPlaneThickness = getFN (atts ["zeroplanethickness"] , (this.params.divLineThickness == 1) ? 2 : this.params.divLineThickness);
		this.params.zeroPlaneAlpha = getFN (atts ["zeroplanealpha"] , this.params.divLineAlpha * 2);
		//Alternating grid colors
		this.params.showAlternateHGridColor = toBoolean (getFN (atts ["showalternatehgridcolor"] , 1));
		this.params.alternateHGridColor = formatColor (getFV (atts ["alternatehgridcolor"] , this.defColors.get2DAltHGridColor (this.params.palette)));
		this.params.alternateHGridAlpha = getFN (atts ["alternatehgridalpha"] , this.defColors.get2DAltHGridAlpha (this.params.palette));
		this.params.showAlternateVGridColor = toBoolean (getFN (atts ["showalternatevgridcolor"] , 0));
		this.params.alternateVGridColor = formatColor (getFV (atts ["alternatevgridcolor"] , this.defColors.get2DAltVGridColor (this.params.palette)));
		this.params.alternateVGridAlpha = getFN (atts ["alternatevgridalpha"] , this.defColors.get2DAltVGridAlpha (this.params.palette));
		//Shadow properties
		this.params.showShadow = toBoolean (getFN (atts ["showshadow"] , 0));
		//Anchor Properties
		this.params.drawAnchors = toBoolean (getFN (atts ["drawanchors"] , atts ["showanchors"] , 1));
		this.params.anchorSides = getFN (atts ["anchorsides"] , 0);
		this.params.anchorRadius = getFN (atts ["anchorradius"] , 3);
		this.params.anchorBorderColor = atts ["anchorbordercolor"];
		this.params.anchorBorderThickness = getFN (atts ["anchorborderthickness"] , 1);
		this.params.anchorBgColor = formatColor (getFV (atts ["anchorbgcolor"] , this.defColors.get2DAnchorBgColor (this.params.palette)));
		this.params.anchorAlpha = getFN (atts ["anchoralpha"] , 0);
		this.params.anchorBgAlpha = getFN (atts ["anchorbgalpha"] , this.params.anchorAlpha);
		// ------------------------- NUMBER FORMATTING ---------------------------- //
		//Option whether the format the number (using Commas)
		this.params.formatNumber = toBoolean (getFN (atts ["formatnumber"] , 1));
		//Option to format number scale
		this.params.formatNumberScale = toBoolean (getFN (atts ["formatnumberscale"] , 1));
		//Number Scales
		this.params.defaultNumberScale = getFV (atts ["defaultnumberscale"] , "");
		this.params.numberScaleUnit = getFV (atts ["numberscaleunit"] , "K,M");
		this.params.numberScaleValue = getFV (atts ["numberscalevalue"] , "1000,1000");
		//Number prefix and suffix
		this.params.numberPrefix = getFV (atts ["numberprefix"] , "");
		this.params.numberSuffix = getFV (atts ["numbersuffix"] , "");
		//whether to scale recursively
		this.params.scaleRecursively = toBoolean( getFN (atts ['scalerecursively'], 0));
		//By default we show all - so set as -1
		this.params.maxScaleRecursion = getFN(atts["maxscalerecursion"], -1);
		//Setting space as default scale separator.
		this.params.scaleSeparator = getFV(atts["scaleseparator"] , " ");
		//Decimal Separator Character
		this.params.decimalSeparator = getFV (atts ["decimalseparator"] , ".");
		//Thousand Separator Character
		this.params.thousandSeparator = getFV (atts ["thousandseparator"] , ",");
		//Input decimal separator and thousand separator. In some european countries,
		//commas are used as decimal separators and dots as thousand separators. In XML,
		//if the user specifies such values, it will give a error while converting to
		//number. So, we accept the input decimal and thousand separator from user, so that
		//we can covert it accordingly into the required format.
		this.params.inDecimalSeparator = getFV (atts ["indecimalseparator"] , "");
		this.params.inThousandSeparator = getFV (atts ["inthousandseparator"] , "");
		//Decimal Precision (number of decimal places to be rounded to)
		this.params.decimals = getFV (atts ["decimals"] , atts ["decimalprecision"]);
		//Force Decimal Padding
		this.params.forceDecimals = toBoolean (getFN (atts ["forcedecimals"] , 0));
		//y-Axis values decimals
		this.params.yAxisValueDecimals = getFV (atts ["yaxisvaluedecimals"] , atts ["yaxisvaluesdecimals"] , atts ["divlinedecimalprecision"] , atts ["limitsdecimalprecision"]);
	}
	/**
	* getMaxDataValue method gets the maximum y-axis data value present
	* in the data.
	*	@return	The maximum value present in the data provided.
	*/
	private function getMaxDataValue () : Number 
	{
		var maxValue : Number;
		var firstNumberFound : Boolean = false;
		var i : Number, j : Number;
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				//By default assume the first non-null number to be maximum
				if (firstNumberFound == false)
				{
					if (this.dataset [i].data [j].isDefined == true)
					{
						//Set the flag that "We've found first non-null number".
						firstNumberFound = true;
						//Also assume this value to be maximum.
						maxValue = this.dataset [i].data [j].value;
					}
				} else 
				{
					//If the first number has been found and the current data is defined, compare
					if (this.dataset [i].data [j].isDefined)
					{
						//Store the greater number
						maxValue = (this.dataset [i].data [j].value > maxValue) ? this.dataset [i].data [j].value : maxValue;
					}
				}
			}
		}
		return maxValue;
	}
	/**
	* getMinDataValue method gets the minimum y-axis data value present
	* in the data
	*	@reurns		The minimum value present in data
	*/
	private function getMinDataValue () : Number 
	{
		var minValue : Number;
		var firstNumberFound : Boolean = false;
		var i : Number, j : Number;
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				//By default assume the first non-null number to be minimum
				if (firstNumberFound == false)
				{
					if (this.dataset [i].data [j].isDefined == true)
					{
						//Set the flag that "We've found first non-null number".
						firstNumberFound = true;
						//Also assume this value to be minimum.
						minValue = this.dataset [i].data [j].value;
					}
				} else 
				{
					//If the first number has been found and the current data is defined, compare
					if (this.dataset [i].data [j].isDefined)
					{
						//Store the lesser number
						minValue = (this.dataset [i].data [j].value < minValue) ? this.dataset [i].data [j].value : minValue;
					}
				}
			}
		}
		return minValue;
	}
	/**
	* calculateAxisLimits method sets the axis limits for the chart.
	* It gets the minimum and maximum value specified in data and
	* based on that it calls super.getAxisLimits();
	*/
	private function calculateAxisLimits () : Void 
	{
		this.getAxisLimits (this.getMaxDataValue () , this.getMinDataValue () , ! this.params.setAdaptiveYMin, ! this.params.setAdaptiveYMin);
	}
	/**
	* setStyleDefaults method sets the default values for styles or
	* extracts information from the attributes and stores them into
	* style objects.
	*/
	private function setStyleDefaults () : Void 
	{
		//Default font object for Caption
		//-----------------------------------------------------------------//
		var captionFont = new StyleObject ();
		captionFont.name = "_SdCaptionFont";
		captionFont.align = "center";
		captionFont.valign = "top";
		captionFont.bold = "1";
		captionFont.font = this.params.outCnvBaseFont;
		captionFont.size = this.params.outCnvBaseFontSize+3;
		captionFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.CAPTION, captionFont, this.styleM.TYPE.FONT, null);
		delete captionFont;
		//-----------------------------------------------------------------//
		//Default font object for SubCaption
		//-----------------------------------------------------------------//
		var subCaptionFont = new StyleObject ();
		subCaptionFont.name = "_SdSubCaptionFont";
		subCaptionFont.align = "center";
		subCaptionFont.valign = "top";
		subCaptionFont.bold = "1";
		subCaptionFont.font = this.params.outCnvBaseFont;
		subCaptionFont.size = this.params.outCnvBaseFontSize+1;
		subCaptionFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.SUBCAPTION, subCaptionFont, this.styleM.TYPE.FONT, null);
		delete subCaptionFont;
		//-----------------------------------------------------------------//
		//Default font object for YAxisName
		//-----------------------------------------------------------------//
		var yAxisNameFont = new StyleObject ();
		yAxisNameFont.name = "_SdYAxisNameFont";
		yAxisNameFont.align = "center";
		yAxisNameFont.valign = "middle";
		yAxisNameFont.bold = "1";
		yAxisNameFont.font = this.params.outCnvBaseFont;
		yAxisNameFont.size = this.params.outCnvBaseFontSize;
		yAxisNameFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.YAXISNAME, yAxisNameFont, this.styleM.TYPE.FONT, null);
		delete yAxisNameFont;
		//-----------------------------------------------------------------//
		//Default font object for XAxisName
		//-----------------------------------------------------------------//
		var xAxisNameFont = new StyleObject ();
		xAxisNameFont.name = "_SdXAxisNameFont";
		xAxisNameFont.align = "center";
		xAxisNameFont.valign = "middle";
		xAxisNameFont.bold = "1";
		xAxisNameFont.font = this.params.outCnvBaseFont;
		xAxisNameFont.size = this.params.outCnvBaseFontSize;
		xAxisNameFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.XAXISNAME, xAxisNameFont, this.styleM.TYPE.FONT, null);
		delete xAxisNameFont;
		//-----------------------------------------------------------------//
		//Default font object for trend lines
		//-----------------------------------------------------------------//
		var trendFont = new StyleObject ();
		trendFont.name = "_SdTrendFontFont";
		trendFont.font = this.params.outCnvBaseFont;
		trendFont.size = this.params.outCnvBaseFontSize;
		trendFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.TRENDVALUES, trendFont, this.styleM.TYPE.FONT, null);
		delete trendFont;
		//-----------------------------------------------------------------//
		//Default font object for yAxisValues
		//-----------------------------------------------------------------//
		var yAxisValuesFont = new StyleObject ();
		yAxisValuesFont.name = "_SdYAxisValuesFont";
		yAxisValuesFont.align = "right";
		yAxisValuesFont.valign = "middle";
		yAxisValuesFont.font = this.params.outCnvBaseFont;
		yAxisValuesFont.size = this.params.outCnvBaseFontSize;
		yAxisValuesFont.color = this.params.outCnvBaseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.YAXISVALUES, yAxisValuesFont, this.styleM.TYPE.FONT, null);
		delete yAxisValuesFont;
		//-----------------------------------------------------------------//
		//Default font object for DataLabels
		//-----------------------------------------------------------------//
		var dataLabelsFont = new StyleObject ();
		dataLabelsFont.name = "_SdDataLabelsFont";
		dataLabelsFont.align = "center";
		dataLabelsFont.valign = "bottom";
		dataLabelsFont.font = this.params.catFont;
		dataLabelsFont.size = this.params.catFontSize;
		dataLabelsFont.color = this.params.catFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.DATALABELS, dataLabelsFont, this.styleM.TYPE.FONT, null);
		delete dataLabelsFont;
		//-----------------------------------------------------------------//
		//Default font object for DataValues
		//-----------------------------------------------------------------//
		var dataValuesFont = new StyleObject ();
		dataValuesFont.name = "_SdDataValuesFont";
		dataValuesFont.align = "center";
		dataValuesFont.valign = "middle";
		dataValuesFont.font = this.params.baseFont;
		dataValuesFont.size = this.params.baseFontSize;
		dataValuesFont.color = this.params.baseFontColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.DATAVALUES, dataValuesFont, this.styleM.TYPE.FONT, null);
		delete dataValuesFont;
		//-----------------------------------------------------------------//
		//Default font object for Legend
		//-----------------------------------------------------------------//
		var legendFont = new StyleObject ();
		legendFont.name = "_SdLegendFont";
		legendFont.font = this.params.outCnvBaseFont;
		legendFont.size = this.params.outCnvBaseFontSize;
		legendFont.color = this.params.outCnvBaseFontColor;
		legendFont.ishtml = 1;
		legendFont.leftmargin = 3;
		//Over-ride
		this.styleM.overrideStyle (this.objects.LEGEND, legendFont, this.styleM.TYPE.FONT, null);
		delete legendFont;
		//-----------------------------------------------------------------//
		//Default font object for ToolTip
		//-----------------------------------------------------------------//
		var toolTipFont = new StyleObject ();
		toolTipFont.name = "_SdToolTipFont";
		toolTipFont.font = this.params.baseFont;
		toolTipFont.size = this.params.baseFontSize;
		toolTipFont.color = this.params.baseFontColor;
		toolTipFont.bgcolor = this.params.toolTipBgColor;
		toolTipFont.bordercolor = this.params.toolTipBorderColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.TOOLTIP, toolTipFont, this.styleM.TYPE.FONT, null);
		delete toolTipFont;
		//-----------------------------------------------------------------//
		//Default font object for V-line labels
		//-----------------------------------------------------------------//
		var vLineLabelsFont = new StyleObject ();
		vLineLabelsFont.name = "_SdDataVLineLabelsFont";
		vLineLabelsFont.align = "center";
		vLineLabelsFont.valign = "bottom";
		vLineLabelsFont.font = this.params.baseFont;
		vLineLabelsFont.size = this.params.baseFontSize;
		vLineLabelsFont.color = this.params.baseFontColor;
		vLineLabelsFont.bgcolor = this.params.canvasBgColor;
		//Over-ride
		this.styleM.overrideStyle (this.objects.VLINELABELS, vLineLabelsFont, this.styleM.TYPE.FONT, null);
		delete vLineLabelsFont;
		//-----------------------------------------------------------------//
		//Default Effect (Shadow) object for DataPlot
		//-----------------------------------------------------------------//
		if (this.params.showShadow)
		{
			var dataPlotShadow = new StyleObject ();
			dataPlotShadow.name = "_SdDataPlotShadow";
			//Over-ride
			this.styleM.overrideStyle (this.objects.DATAPLOT, dataPlotShadow, this.styleM.TYPE.SHADOW, null);
			delete dataPlotShadow;
		}
		//-----------------------------------------------------------------//
		//Default Effect (Shadow) object for Legend
		//-----------------------------------------------------------------//
		if (this.params.legendShadow)
		{
			var legendShadow = new StyleObject ();
			legendShadow.name = "_SdLegendShadow";
			legendShadow.distance = 2;
			legendShadow.alpha = 90;
			legendShadow.angle = 45;
			//Over-ride
			this.styleM.overrideStyle (this.objects.LEGEND, legendShadow, this.styleM.TYPE.SHADOW, null);
			delete legendShadow;
		}
		//-----------------------------------------------------------------//
		//Default Animation object for DataPlot (if required)
		//-----------------------------------------------------------------//
		if (this.params.defaultAnimation)
		{
			//We need three animation objects.
			//1. Alpha for data plot
			var dataPlotAnim = new StyleObject ();
			dataPlotAnim.name = "_SdDataPlotAnimAlpha";
			dataPlotAnim.param = "_alpha";
			dataPlotAnim.easing = "none";
			dataPlotAnim.wait = 0.1;
			dataPlotAnim.start = 0;
			dataPlotAnim.duration = 1;
			//Over-ride
			this.styleM.overrideStyle (this.objects.DATAPLOT, dataPlotAnim, this.styleM.TYPE.ANIMATION, "_alpha");
			delete dataPlotAnim;
			//2. YScale for data plot
			var dataPlotAnimY = new StyleObject ();
			dataPlotAnimY.name = "_SdDataPlotAnimYScale";
			dataPlotAnimY.param = "_yscale";
			dataPlotAnimY.easing = "regular";
			dataPlotAnimY.wait = 0.1;
			dataPlotAnimY.start = 0;
			dataPlotAnimY.duration = 0.7;
			//Over-ride
			this.styleM.overrideStyle (this.objects.DATAPLOT, dataPlotAnimY, this.styleM.TYPE.ANIMATION, "_yscale");
			delete dataPlotAnimY;
			//3. Alpha effect for anchors
			var anchorsAnim = new StyleObject ();
			anchorsAnim.name = "_SdDataAnchorAnim";
			anchorsAnim.param = "_alpha";
			anchorsAnim.easing = "regular";
			anchorsAnim.wait = 0;
			anchorsAnim.start = 0;
			anchorsAnim.duration = 0.5;
			//Over-ride
			this.styleM.overrideStyle (this.objects.ANCHORS, anchorsAnim, this.styleM.TYPE.ANIMATION, "_alpha");
			delete anchorsAnim;
		}
		//-----------------------------------------------------------------//
		
	}
	/**
	* calcVLinesPos method calculates the x position for the various
	* vLines defined. Also, it validates them.
	*/
	private function calcVLinesPos ()
	{
		var i : Number;
		//Iterate through all the vLines
		for (i = 1; i <= numVLines; i ++)
		{
			//If the vLine is after 1st data and before last data
			if (this.vLines [i].index > 0 && this.vLines [i].index < this.num)
			{
				//Set it's x position
				this.vLines [i].x = this.categories [this.vLines [i].index].x + (this.categories [this.vLines [i].index + 1].x - this.categories [this.vLines [i].index].x) * this.vLines[i].linePosition;
			} else 
			{
				//Invalidate it
				this.vLines [i].isValid = false;
			}
		}
	}
	/**
	* calculatePoints method calculates the various points on the chart.
	*/
	private function calculatePoints (isRedraw:Boolean)
	{
		//Loop variable
		var i : Number, j : Number;
		//Feed empty data - By default there should be equal number of <categories>
		//and <set> element within each dataset. If in case, <set> elements fall short,
		//we need to append empty data at the end.
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				if (this.dataset [i].data [j] == undefined)
				{
					this.dataset [i].data [j] = this.returnDataAsObject (NaN);
				}
			}
		}
		//If user has not explicitly defined numVisiblePlot, we need to assume a value.
		if(this.params.numVisiblePlot ==undefined || this.params.numVisiblePlot=="" || isNaN(this.params.numVisiblePlot)){
			this.params.numVisiblePlot = Math.floor(this.width/75);
		} 
		// numVisiblePlot cannot be more than the max point or less than 2
		if(this.params.numVisiblePlot < 2 ||  this.params.numVisiblePlot > this.num)  {
			this.params.numVisiblePlot = this.num;
		}
		//Condition to check whether scroll is not required.
		if(this.params.numVisiblePlot < this.num)  {
			this.scrollRequired = true;
		} else {
			this.scrollRequired = false;
		}
		//Format all the numbers on the chart and store their display values
		//We format and store here itself, so that later, whenever needed,
		//we just access displayValue instead of formatting once again.
		//Also set tool tip text values
		var toolText : String
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				//Format and store
				this.dataset [i].data [j].displayValue = formatNumber (this.dataset [i].data [j].value, this.params.formatNumber, this.params.decimals, this.params.forceDecimals, this.params.formatNumberScale, this.params.defaultNumberScale, this.config.nsv, this.config.nsu, this.params.numberPrefix, this.params.numberSuffix, this.params.scaleRecursively, this.params.scaleSeparator, this.params.maxScaleRecursion);
				//Store formatted values
				this.dataset [i].data [j].formattedValue = this.dataset [i].data [j].displayValue;
				//Tool tip text.
				//Preferential Order - Set Tool Text (No concatenation) > SeriesName + Cat Name + Value
				if (this.dataset [i].data [j].toolText == undefined || this.dataset [i].data [j].toolText == "")
				{
					//If the tool tip text is not already defined
					//If labels have been defined
					toolText = (this.params.seriesNameInToolTip && this.dataset [i].seriesName != "") ? (this.dataset [i].seriesName + this.params.toolTipSepChar) : "";
					toolText = toolText + ((this.categories [j].toolText != "") ? (this.categories [j].toolText + this.params.toolTipSepChar) : "");
					toolText = toolText + this.dataset [i].data [j].displayValue;
					this.dataset [i].data [j].toolText = toolText;
				}
				//Choose display value for item (choice between explicit and actual value)
				if (this.dataset[i].data[j].exDispVal != "") {
					this.dataset[i].data[j].displayValue = this.dataset[i].data[j].exDispVal;
				}
			}
		}
		//We now need to calculate the available Width on the canvas.
		//Available width = total Chart width minus the list below
		// - Left and Right Margin
		// - yAxisName (if to be shown)
		// - yAxisValues
		// - Trend line display values (both left side and right side).
		var canvasWidth : Number = this.width - (this.params.chartLeftMargin + this.params.chartRightMargin);
		//Set canvas startX
		var canvasStartX : Number = this.params.chartLeftMargin;
		//Now, if y-axis name is to be shown, simulate it and get the width
		if (this.params.yAxisName != "")
		{
			//Get style object
			var yAxisNameStyle : Object = this.styleM.getTextStyle (this.objects.YAXISNAME);
			if (this.params.rotateYAxisName)
			{
				//Create text field to get width
				var yAxisNameObj : Object = createText (true, this.params.yAxisName, this.tfTestMC, 1, testTFX, testTFY, 90, yAxisNameStyle, true, this.height-(this.params.chartTopMargin+this.params.chartBottomMargin), canvasWidth/2);
				//Accomodate width and padding
				canvasStartX = canvasStartX + yAxisNameObj.width + this.params.yAxisNamePadding;
				canvasWidth = canvasWidth - yAxisNameObj.width - this.params.yAxisNamePadding;
				//Create element for yAxisName - to store width/height
				this.elements.yAxisName = returnDataAsElement (0, 0, yAxisNameObj.width, yAxisNameObj.height);
				this.params.yAxisNameWidth = yAxisNameObj.width;
			} else 
			{
				//If the y-axis name is not to be rotated
				//Calculate the width of the text if in full horizontal mode
				//Create text field to get width
				var yAxisNameObj : Object = createText (true, this.params.yAxisName, this.tfTestMC, 1, testTFX, testTFY, 0, yAxisNameStyle, false, 0, 0);
				//Get a value for this.params.yAxisNameWidth
				this.params.yAxisNameWidth = Number (getFV (this.params.yAxisNameWidth, yAxisNameObj.width));
				//Get the lesser of the width (to avoid un-necessary space)
				this.params.yAxisNameWidth = Math.min (this.params.yAxisNameWidth, yAxisNameObj.width);
				//Accomodate width and padding
				canvasStartX = canvasStartX + this.params.yAxisNameWidth + this.params.yAxisNamePadding;
				canvasWidth = canvasWidth - this.params.yAxisNameWidth - this.params.yAxisNamePadding;
				//Create element for yAxisName - to store width/height
				this.elements.yAxisName = returnDataAsElement (0, 0, this.params.yAxisNameWidth, yAxisNameObj.height);
			}
			delete yAxisNameStyle;
			delete yAxisNameObj;
		}
		
		labelMetrics.updateProp("yAxisNameObj", {yAxisNameWidth: this.params.yAxisNameWidth, yAxisNamePadding: this.params.yAxisNamePadding});
		
		//Accomodate width for y-axis values. Now, y-axis values conists of two parts
		//(for backward compatibility) - limits (upper and lower) and div line values.
		//So, we'll have to individually run through both of them.
		var yAxisValMaxWidth : Number = 0;
		//Also store the height required to render the text field
		var yAxisValMaxHeight:Number = 0
		var divLineObj : Object;
		var divStyle : Object = this.styleM.getTextStyle (this.objects.YAXISVALUES);
		//Iterate through all the div line values
		for (i = 1; i < this.divLines.length; i ++)
		{
			//If div line value is to be shown
			if (this.divLines [i].showValue)
			{
				//If it's the first or last div Line (limits), and it's to be shown
				if ((i == 1) || (i == this.divLines.length - 1))
				{
					if (this.params.showLimits)
					{
						//Get the width of the text
						divLineObj = createText (true, this.divLines [i].displayValue, this.tfTestMC, 1, testTFX, testTFY, 0, divStyle, false, 0, 0);
						//Accomodate
						yAxisValMaxWidth = (divLineObj.width > yAxisValMaxWidth) ? (divLineObj.width) : (yAxisValMaxWidth);
						yAxisValMaxHeight = (divLineObj.height > yAxisValMaxHeight) ? (divLineObj.height) : (yAxisValMaxHeight);
					}
				} else 
				{
					//It's a div interval - div line
					//So, check if we've to show div line values
					if (this.params.showDivLineValues)
					{
						//Get the width of the text
						divLineObj = createText (true, this.divLines [i].displayValue, this.tfTestMC, 1, testTFX, testTFY, 0, divStyle, false, 0, 0);
						//Accomodate
						yAxisValMaxWidth = (divLineObj.width > yAxisValMaxWidth) ? (divLineObj.width) : (yAxisValMaxWidth);
						yAxisValMaxHeight = (divLineObj.height > yAxisValMaxHeight) ? (divLineObj.height) : (yAxisValMaxHeight);
					}
				}
			}
		}
		delete divLineObj;
		//Also iterate through all trend lines whose values are to be shown on
		//left side of the canvas.
		//Get style object
		var trendStyle : Object = this.styleM.getTextStyle (this.objects.TRENDVALUES);
		var trendObj : Object;
		for (i = 1; i <= this.numTrendLines; i ++)
		{
			if (this.trendLines [i].isValid == true && this.trendLines [i].valueOnRight == false)
			{
				//If it's a valid trend line and value is to be shown on left
				//Get the width of the text
				trendObj = createText (true, this.trendLines [i].displayValue, this.tfTestMC, 1, testTFX, testTFY, 0, trendStyle, false, 0, 0);
				//Accomodate
				yAxisValMaxWidth = (trendObj.width > yAxisValMaxWidth) ? (trendObj.width) : (yAxisValMaxWidth);
			}
		}
		//Accomodate for y-axis/left-trend line values text width
		if (yAxisValMaxWidth > 0)
		{
			canvasStartX = canvasStartX + yAxisValMaxWidth + this.params.yAxisValuesPadding;
			canvasWidth = canvasWidth - yAxisValMaxWidth - this.params.yAxisValuesPadding;
		}
		var trendRightWidth : Number = 0;
		//Now, also check for trend line values that fall on right
		for (i = 1; i <= this.numTrendLines; i ++)
		{
			if (this.trendLines [i].isValid == true && this.trendLines [i].valueOnRight == true)
			{
				//If it's a valid trend line and value is to be shown on right
				//Get the width of the text
				trendObj = createText (true, this.trendLines [i].displayValue, this.tfTestMC, 1, testTFX, testTFY, 0, trendStyle, false, 0, 0);
				//Accomodate
				trendRightWidth = (trendObj.width > trendRightWidth) ? (trendObj.width) : (trendRightWidth);
			}
		}
		delete trendObj;
		//Accomodate trend right text width
		if (trendRightWidth > 0)
		{
			canvasWidth = canvasWidth - trendRightWidth - this.params.yAxisValuesPadding;
		}
		
		labelMetrics.updateProp("yAxisValueObj", {yAxisValMaxWidth: yAxisValMaxWidth, yAxisValuesPadding: this.params.yAxisValuesPadding, yAxisValueAtRight:trendRightWidth});
		
		//Round them off finally to avoid distorted pixels
		canvasStartX = int (canvasStartX);
		canvasWidth = int (canvasWidth);
		//We finally have canvas Width and canvas Start X
		//-----------------------------------------------------------------------------------//
		//Now, we need to calculate the available Height on the canvas.
		//Available height = total Chart height minus the list below
		// - Chart Top and Bottom Margins
		// - Space for Caption, Sub Caption and caption padding
		// - Height of data labels
		// - xAxisName
		//Initialize canvasHeight to total height minus margins
		var canvasHeight : Number = this.height - (this.params.chartTopMargin + this.params.chartBottomMargin);
		//Set canvasStartY
		var canvasStartY : Number = this.params.chartTopMargin;
		//Now, if we've to show caption
		if (this.params.caption != "")
		{
			//Create text field to get height
			var captionObj : Object = createText (true, this.params.caption, this.tfTestMC, 1, testTFX, testTFY, 0, this.styleM.getTextStyle (this.objects.CAPTION) , true, canvasWidth, canvasHeight/4);
			//Store the height
			canvasStartY = canvasStartY + captionObj.height;
			canvasHeight = canvasHeight - captionObj.height;
			//Create element for caption - to store width & height
			this.elements.caption = returnDataAsElement (0, 0, captionObj.width, captionObj.height);
			delete captionObj;
		}
		//Now, if we've to show sub-caption
		if (this.params.subCaption != "")
		{
			//Create text field to get height
			var subCaptionObj : Object = createText (true, this.params.subCaption, this.tfTestMC, 1, testTFX, testTFY, 0, this.styleM.getTextStyle (this.objects.SUBCAPTION) , true, canvasWidth, canvasHeight/4);
			//Store the height
			canvasStartY = canvasStartY + subCaptionObj.height;
			canvasHeight = canvasHeight - subCaptionObj.height;
			//Create element for sub caption - to store height
			this.elements.subCaption = returnDataAsElement (0, 0, subCaptionObj.width, subCaptionObj.height);
			delete subCaptionObj;
		}
		//Now, if either caption or sub-caption was shown, we also need to adjust caption padding
		if (this.params.caption != "" || this.params.subCaption != "")
		{
			//Account for padding
			canvasStartY = canvasStartY + this.params.captionPadding;
			canvasHeight = canvasHeight - this.params.captionPadding;
		}
		
		//Accomodate space for scroll bar (if to be shown)
		if (this.scrollRequired){
			canvasHeight = canvasHeight - this.params.scrollHeight - this.params.scrollPadding;
		}
		//-------- Begin: Data Label calculation --------------//
		var labelObj : Object;
		var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);		
		//Based on label step, set showLabel of each data point as required.
		//Visible label count
		var visibleCount : Number = 0;
		for (i=1; i<=this.num; i++) {
			//Now, the label can be preset to be hidden (set via XML)
			if (this.categories[i].showLabel) {
				visibleCount++;
			}
		}
		//First if the label display is set as Auto, figure the best way 
		//to render them.
		if (this.params.labelDisplay=="AUTO"){
			//Here, we check how many labels are to be shown.
			//Based on that, we decide a fit between WRAP and ROTATE
			//Priority is given to WRAP mode over ROTATE mode
			//WRAP mode is set when at least 5 characters can be shown
			//on the chart, without having to break up into multiple lines.
			//Else, rotate mode is selected.
			//If in rotate mode and a single line cannot be fit, we also 
			//set labelStep, based on available space.
			//Get the width required to plot the characters in wrap mode
			//Storage for string to be simulated.
			var str:String = "";
			//Minimum width required to plot this string in both wrap
			//and rotate mode.
			var wrapMinWidth:Number = 0, rotateMinWidth:Number=0;
			//Width of total drawing canvas
			var canvasTotalWidth:Number = canvasWidth*(this.num/this.params.numVisiblePlot);
			//Build the string by adding upper-case letters A,B,C,D...
			for (i=1; i<=WRAP_MODE_MIN_CHARACTERS; i++){
				//Build the string from upper case A,B,C...
				str = str + String.fromCharCode(64+i);
			}
			//Simulate width of this text field - without wrapping
			labelObj = createText (true, str, this.tfTestMC, 1, testTFX, testTFY, 0, labelStyleObj, false, 0, 0);
			wrapMinWidth = labelObj.width;
			//If we've space to accommodate this width for all labels, render in wrap mode
			if (visibleCount*wrapMinWidth <= canvasTotalWidth){
				//Render all labels in wrap mode
				this.params.labelDisplay="WRAP";
			}else{
				//Since we do not have space to accommodate the width of wrap mode:
				//Render in rotate mode
				this.params.labelDisplay="ROTATE";
				//Figure the minimum width required to display 1 line of text in rotated mode				
				labelObj = createText (true, str, this.tfTestMC, 1, testTFX, testTFY, this.config.labelAngle, labelStyleObj, false, 0, 0);
				//Store the mimimum width required for rotated mode
				rotateMinWidth = labelObj.width;
				//If all the labels cannot be accommodated in minimum width (without getting trimmed),
				//then go for labelStep
				if ((visibleCount/this.params.labelStep)*rotateMinWidth>= canvasTotalWidth){
					//Figure out how many labels we can display
					var numFitLabels:Number = (canvasTotalWidth/rotateMinWidth);
					//Based on how many labels we've to display and how many we can, reset labelStep
					this.params.labelStep = Math.ceil(visibleCount/numFitLabels);					
				}
			}
		}
		//Now, based on user set/auto-calculated label step, set showLabel 
		//of each data point as required.
		//Reset Visible label count
		visibleCount = 0;
		var finalVisibleCount : Number = 0;
		for (i = 1; i <= this.num; i ++)
		{
			//Now, the label can be preset to be hidden (set via XML)
			if (this.categories [i].showLabel)
			{
				visibleCount ++;
				//If label step is defined, we need to set showLabel of those
				//labels which fall on step as false.
				if ((i - 1) % this.params.labelStep == 0)
				{
					this.categories [i].showLabel = true;
				} else 
				{
					this.categories [i].showLabel = false;
				}
			}
			//Update counter
			finalVisibleCount = (this.categories [i].showLabel) ? (finalVisibleCount + 1) : (finalVisibleCount);
		}
		//Store the final visible count
		this.config.finalVisibleCount = finalVisibleCount;
		//now depending on the final showLabel property on fisrt and last label and its display mode
		//we set the first label width property for improve label management. This values will only be used
		//when we go for new label management.
		var maxAllowableLabelHeight:Number = canvasHeight/3;
		if(this.categories [1].showLabel){
			if(this.params.labelDisplay == "ROTATE"){
				labelObj = getTextMetrics(this.categories [1].label, labelStyleObj, maxAllowableLabelHeight);
				labelMetrics.firstLabelWidth = labelObj.textFieldHeight;				
			}else{
				labelObj = getTextMetrics(this.categories [1].label, labelStyleObj);
				labelMetrics.firstLabelWidth = labelObj.textFieldWidth;
			}
		}else{
			labelMetrics.firstLabelWidth = 0;
		}
		if(this.categories [this.num].showLabel){
			if(this.params.labelDisplay == "ROTATE"){
				labelObj = getTextMetrics(this.categories [this.num].label, labelStyleObj, maxAllowableLabelHeight);
				labelMetrics.lastLabelWidth = labelObj.textFieldHeight;				
			}else{
				labelObj = getTextMetrics(this.categories [this.num].label, labelStyleObj);
				labelMetrics.lastLabelWidth = labelObj.textFieldWidth;
			}
		}else{
			labelMetrics.lastLabelWidth = 0;
		}
		labelObj = null;
		//------------------------------------Scroll Mode Label Management  Initial Calculation-------------------------------------------//
		if(this.scrollRequired){
			//Label object and style for simulation text.
			var labelObj : Object;
			var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);
			//Maximum allowable label height		
			this.config.maxAllowableLabelHeight = canvasHeight/3;
			//First simulated text object
			var fSimulatedText:Object;
			//Last simulated text object
			var lSimulatedText:Object;
			//First label width
			var firstLabelWidth:Number;
			//Last label width
			var lastLabelWidth:Number;
			var rotateWidthWithGap:Number;
			var rotateWidthWithoutGap:Number;
			//Placeholder to store max label height 
			this.config.maxLabelHeight = 0;
			this.config.labelAreaHeight = 0;
			//Maximum single line text height
			this.config.maxSingleLineTextHeight = 0;
			//For WRAP and STAGGER labeldisplay
			if (this.params.labelDisplay == "WRAP" || this.params.labelDisplay == "STAGGER" || this.params.labelDisplay == "NONE"){
				//First simulated text
				fSimulatedText = createText (true, this.categories [1].label, this.tfTestMC, 1, 0, 0, 0, labelStyleObj, false, 0, 0);
				//Last simulated text
				lSimulatedText =  createText (true, this.categories [this.num].label, this.tfTestMC, 1, 0, 0, 0, labelStyleObj, false, 0, 0);
				//First label width
				firstLabelWidth = (!isNaN(fSimulatedText.width))? fSimulatedText.width : 0;
				//Last label width
				lastLabelWidth = (!isNaN(lSimulatedText.width))? lSimulatedText.width : 0;
			//For ROTATE labeldisplay
			} else if (this.params.labelDisplay == "ROTATE"){
				
				//Maximum width for rotated label
				var maxLabelWidth : Number = this.config.maxAllowableLabelHeight;
				//Maximum height for rotated label
				var maxLabelHeight : Number = ((canvasWidth - (2 * this.params.canvasPadding)));
				//First simulated text
				fSimulatedText = createText (true, this.categories [1].label, this.tfTestMC, 1,  0, 0,this.config.labelAngle, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
				//Last simulated text
				lSimulatedText = createText (true, this.categories [this.num].label, this.tfTestMC, 1,  0, 0,this.config.labelAngle, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
				//First label width
				firstLabelWidth = (!isNaN(fSimulatedText.width))? fSimulatedText.width : 0;
				//Last label width
				lastLabelWidth = (!isNaN(lSimulatedText.width))? lSimulatedText.width : 0;
			}
			//Remove simulated text fields
			fSimulatedText.tf.removeTextField();
			lSimulatedText.tf.removeTextField();
			//Delete objects
			delete fSimulatedText;
			delete lSimulatedText;
			//When first label is not to be shown in the chart, the width should be zero
			firstLabelWidth = (!this.categories [1].showLabel)? 0 : firstLabelWidth;
			//When last label is not to be shown in the chart, the width should be zero
			lastLabelWidth = (!this.categories [this.num].showLabel)? 0 : lastLabelWidth;
			//------------------------------------EoF Scroll Mode Label Management Initial Calculation-------------------------------------------//
		}else{
			//Data labels can be rendered in 3 ways:
			//1. Normal - no staggering - no wrapping - no rotation
			//2. Wrapped - no staggering - no rotation
			//3. Staggered - no wrapping - no rotation
			//4. Rotated - no staggering - no wrapping
			//Placeholder to store max height
			this.config.maxLabelHeight = 0;
			this.config.labelAreaHeight = 0;
			if (this.params.labelDisplay == "ROTATE")
			{
				//Calculate the maximum width that could be alloted to the labels.
				//Note: Here, width is calculated based on canvas height, as the labels
				//are going to be rotated.
				var maxLabelWidth : Number = (canvasHeight / 3);
				var maxLabelHeight : Number = (canvasWidth*(this.num/this.params.numVisiblePlot)/ finalVisibleCount);
				//Store it in config for later usage
				this.config.wrapLabelWidth = maxLabelWidth;
				this.config.wrapLabelHeight = maxLabelHeight;
				//Case 4: If the labels are rotated, we iterate through all the string labels
				//provided to us and get the height and store max.
				for (i = 1; i <= this.num; i ++)
				{
					//If the label is to be shown
					if (this.categories [i].showLabel)
					{
						//Create text box and get height
						labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1, testTFX, testTFY, this.config.labelAngle, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
						//Store the larger
						this.config.maxLabelHeight = (labelObj.height > this.config.maxLabelHeight) ? (labelObj.height) : (this.config.maxLabelHeight);
					}
				}
				//Store max label height as label area height.
				this.config.labelAreaHeight = this.config.maxLabelHeight;
				//for slanted labels the maxLabelHeight may exceed the limit defined for the height. 
				//we take the mimimum of two
				//Store max label height as label area height.
				this.config.labelAreaHeight = Math.min(maxLabelWidth, this.config.maxLabelHeight);
			} else if (this.params.labelDisplay == "WRAP")
			{
				//Case 2 (WRAP): Create all the labels on the chart. Set width as
				//(canvasWidth*(this.num/this.params.numVisiblePlot)/ finalVisibleCount);
				//Set max height as 50% of available canvas height at this point of time. Find all
				//and select the max one.
				var maxLabelWidth : Number = (canvasWidth*(this.num/this.params.numVisiblePlot)/ finalVisibleCount);
				var maxLabelHeight : Number = (canvasHeight / 3);
				//Store it in config for later usage
				this.config.wrapLabelWidth = maxLabelWidth;
				this.config.wrapLabelHeight = maxLabelHeight;
				for (i = 1; i <= this.num; i ++)
				{
					//If the label is to be shown
					if (this.categories [i].showLabel)
					{
						//Create text box and get height
						labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1, testTFX, testTFY, 0, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
						//Store the larger
						this.config.maxLabelHeight = (labelObj.height > this.config.maxLabelHeight) ? (labelObj.height) : (this.config.maxLabelHeight);
					}
				}
				//Store max label height as label area height.
				this.config.labelAreaHeight = this.config.maxLabelHeight;
			} else 
			{
				//Case 1,3: Normal or Staggered Label
				//We iterate through all the labels, and if any of them has &lt or < (HTML marker)
				//embedded in them, we add them to the array, as for them, we'll need to individually
				//create and see the text height. Also, the first element in the array - we set as
				//ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_=....
				//Create array to store labels.
				var strLabels : Array = new Array ();
				strLabels.push ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_=/*-+~`");
				//Now, iterate through all the labels and for those visible labels, whcih have < sign,
				//add it to array.
				for (i = 1; i <= this.num; i ++)
				{
					//If the label is to be shown
					if (this.categories [i].showLabel)
					{
						if ((this.categories [i].label.indexOf ("&lt;") > - 1) || (this.categories [i].label.indexOf ("<") > - 1))
						{
							strLabels.push (this.categories [i].label);
						}
					}
				}
				//Now, we've the array for which we've to check height (for each element).
				for (i = 0; i < strLabels.length; i ++)
				{
					//Create text box and get height
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1, testTFX, testTFY, 0, labelStyleObj, false, 0, 0);
					//Store the larger
					this.config.maxLabelHeight = (labelObj.height > this.config.maxLabelHeight) ? (labelObj.height) : (this.config.maxLabelHeight);
				}
				//We now have the max label height. If it's staggered, then store accordingly, else
				//simple mode
				if (this.params.labelDisplay == "STAGGER")
				{
					//Here we again validate the stagger lines. The minimum is validated at the time of parsing
					//the maximum is validated here.
					if(this.params.staggerLines > this.num){
						this.params.staggerLines = this.num;
					}
					//Multiply max label height by stagger lines.
					this.config.labelAreaHeight = this.params.staggerLines * this.config.maxLabelHeight;
				} else 
				{
					this.config.labelAreaHeight = this.config.maxLabelHeight;
				}
			}
			if (this.config.labelAreaHeight > 0)
			{
				//Deduct the calculated label height from canvas height
				canvasHeight = canvasHeight - this.config.labelAreaHeight - this.params.labelPadding;
			}
		}
		
		//Delete objects
		delete labelObj;
		delete labelStyleObj;
		//Accomodate space for xAxisName (if to be shown);
		if (this.params.xAxisName != "")
		{
			//Create text field to get height
			var xAxisNameObj : Object = createText (true, this.params.xAxisName, this.tfTestMC, 1, testTFX, testTFY, 0, this.styleM.getTextStyle (this.objects.XAXISNAME) , true, canvasWidth, canvasHeight/2);
			//Store the height
			canvasHeight = canvasHeight - xAxisNameObj.height - this.params.xAxisNamePadding;
			//Object to store width and height of xAxisName
			this.elements.xAxisName = returnDataAsElement (0, 0, xAxisNameObj.width, xAxisNameObj.height);
			delete xAxisNameObj;
		}
		//We have canvas start Y and canvas height
		//We now check whether the legend is to be drawn
		if (this.params.showLegend)
		{
			
			//Object to store dimensions
			var lgndDim : Object;
			
			//MC removed if present before recreation of the MC
			if(this.lgndMC){
				this.lgndMC.removeMovieClip();
			}

			//Create container movie clip for legend
			this.lgndMC = this.cMC.createEmptyMovieClip ("Legend", this.dm.getDepth ("LEGEND"));
			//When clickURL exist legend interactivity should  false
			this.params.interactiveLegend = (this.params.interactiveLegend  && (this.params.clickURL == "" || this.params.clickURL == null || this.params.clickURL == undefined ))?  true : false;
			//Create instance of legend
			if (this.params.legendPosition == "BOTTOM")
			{
				//Maximum Height - 50% of stage
				lgnd = new AdvancedLegend (lgndMC, this.styleM.getTextStyle (this.objects.LEGEND) , this.params.interactiveLegend , this.params.legendPosition, canvasStartX + canvasWidth / 2, this.height / 2, canvasWidth, (this.height - (this.params.chartTopMargin + this.params.chartBottomMargin)) * 0.5, this.params.legendAllowDrag, this.width, this.height, this.params.legendBgColor, this.params.legendBgAlpha, this.params.legendBorderColor, this.params.legendBorderThickness, this.params.legendBorderAlpha, this.params.legendScrollBgColor, this.params.legendScrollBarColor, this.params.legendScrollBtnColor, this.params.legendNumColumns);
			} 
			else 
			{
				//Maximum Width - 40% of stage
				lgnd = new AdvancedLegend (lgndMC, this.styleM.getTextStyle (this.objects.LEGEND) , this.params.interactiveLegend , this.params.legendPosition, this.width / 2, canvasStartY + canvasHeight / 2, (this.width - (this.params.chartLeftMargin + this.params.chartRightMargin)) * 0.4, canvasHeight, this.params.legendAllowDrag, this.width, this.height, this.params.legendBgColor, this.params.legendBgAlpha, this.params.legendBorderColor, this.params.legendBorderThickness, this.params.legendBorderAlpha, this.params.legendScrollBgColor, this.params.legendScrollBarColor, this.params.legendScrollBtnColor, this.params.legendNumColumns);
			}
			
			if(this.params.minimiseWrappingInLegend){
				lgnd.minimiseWrapping = true;
			}
			
			//Get the height of single line text for legend items (to be used to specify icon width and height)
			var iconHeight:Number = lgnd.getIconHeight()*this.params.legendIconScale;
			
			var j:Number;
			var objIcon:Object;
			
			for (i = 1; i <= this.numDS; i ++)
			{
				//Adjust working index for reverseLegend option
				j = (this.params.reverseLegend) ? this.numDS - i + 1 : i;
				//Validation of item eligibility
				if (this.dataset [j].includeInLegend && this.dataset [j].seriesName != "")
				{
					objIcon = {fillColor: parseInt(this.dataset [j].color, 16), intraIconPaddingPercent: 0.3};
					//Create the 2 icons
					var objIcons:Object = LegendIconGenerator.getIcons(LegendIconGenerator.AREA, iconHeight, true, objIcon);
					//State specific icon BitmapData
					var bmpd1:BitmapData = objIcons.active;
					var bmpd2:BitmapData = objIcons.inactive;
					//Add the item to legend
					lgnd.addItem (this.dataset [j].seriesName, j, true, bmpd1, bmpd2);
				}
			}			
			
			//If user has defined a caption for the legend, set it
			if (this.params.legendCaption!=""){
				lgnd.setCaption(this.params.legendCaption);
			}
			
			if (this.params.legendPosition == "BOTTOM")
			{
				lgndDim = lgnd.getDimensions ();
				//Now deduct the height from the calculated canvas height
				canvasHeight = canvasHeight - lgndDim.height - this.params.legendPadding;
				//Re-set the legend position
				this.lgnd.resetXY (canvasStartX + canvasWidth / 2, this.height - this.params.chartBottomMargin - lgndDim.height / 2);
			}
			else
			{
				//Get dimensions
				lgndDim = lgnd.getDimensions ();
				//Now deduct the width from the calculated canvas width
				canvasWidth = canvasWidth - lgndDim.width - this.params.legendPadding;
				//Right position
				this.lgnd.resetXY (this.width - this.params.chartRightMargin - lgndDim.width / 2, canvasStartY + canvasHeight / 2);
			}
		}
		//Legend Area height for xAxisName  y position calculation
		this.config.legendAreaHeight = (this.params.showLegend && this.params.legendPosition == "BOTTOM" && this.lgnd.items.length > 0)? (lgndDim.height + this.params.legendPadding) : 0;
		//------------Scroll Mode Label Management Final Calculation-----------------//
		if(this.scrollRequired){
			this.config.unitLabelWidth = this.config.interPointWidth = (canvasWidth - (this.params.canvasPadding)) / (this.params.numVisiblePlot - 1);
			if(((this.config.unitLabelWidth/2) > this.params.canvasPadding) && ((firstLabelWidth/2) > this.params.canvasPadding || (lastLabelWidth/2) > this.params.canvasPadding)){
				this.params.canvasPadding = Math.min(this.config.unitLabelWidth, Math.max(firstLabelWidth,lastLabelWidth))/2;
			}
			//Left panel changed width
			this.config.leftPanelWidth = this.params.canvasPadding;
			//Right panel changed width
			this.config.rightPanelWidth = this.params.canvasPadding;
			//Reset inter point width
			this.config.interPointWidth = (canvasWidth - (this.params.canvasPadding)) / (this.params.numVisiblePlot - 1);
			//Reset unit label width
			this.config.unitLabelWidth = this.config.interPointWidth;
			//Store all labels simulation width
			getSimulationWidth();
			//Manage labaels in scroll Mode
			manageScrollLabels();
			//Deduct label area height 
			if (this.config.labelAreaHeight > 0)
			{
				//Deduct the calculated label height from canvas height
				canvasHeight = canvasHeight - this.config.labelAreaHeight - this.params.labelPadding;
			}
		}
		//----------- HANDLING CUSTOM CANVAS MARGINS --------------//
		//Before doing so, we take into consideration, user's forced canvas margins (if any defined)
		//If the user's forced values result in overlapping of chart items, we ignore.
		if (this.params.canvasLeftMargin!=-1 && this.params.canvasLeftMargin>canvasStartX){
			//Update width (deduct the difference)
			canvasWidth = canvasWidth - (this.params.canvasLeftMargin-canvasStartX);
			//Update left start position
			canvasStartX = this.params.canvasLeftMargin;		
			//valid left margin is defined
			this.validCanvasLeftMargin = true;
		}
		if (this.params.canvasRightMargin!=-1 && (this.params.canvasRightMargin>(this.width - (canvasStartX+canvasWidth)))){
			//Update width (deduct the difference)
			canvasWidth = canvasWidth - (this.params.canvasRightMargin-(this.width - (canvasStartX+canvasWidth)));			
			//valid right margin is defined
			this.validCanvasRightMargin = true;
		}
		if (this.params.canvasTopMargin!=-1 && this.params.canvasTopMargin>canvasStartY){
			//Update height (deduct the difference)
			canvasHeight = canvasHeight - (this.params.canvasTopMargin-canvasStartY);
			//Update top start position
			canvasStartY = this.params.canvasTopMargin;		
		}
		if (this.params.canvasBottomMargin!=-1 && (this.params.canvasBottomMargin>(this.height - (canvasStartY+canvasHeight)))){
			//Update height(deduct the difference)
			canvasHeight = canvasHeight - (this.params.canvasBottomMargin-(this.height - (canvasStartY+canvasHeight)));
		}
		labelMetrics.updateProp("canvasMarginObj", {canvasLeftMargin: this.params.canvasLeftMargin, canvasRightMargin: this.params.canvasRightMargin,
													validCanvasLeftMargin:this.validCanvasLeftMargin, validCanvasRightMargin:this.validCanvasRightMargin});
		//------------ END OF CUSTOM CANVAS MARGIN HANDLING --------------------//
		//check if new label management is required
		if(this.params.XTLabelManagement && !this.scrollRequired){
			labelMetrics.calculatePanels();
			//now we should go for final label management calculation
			var metricsObj:Object = labelMetrics.getChartMetrics( this.num, labelMetrics.totalWorkingObj.totalWorkingWidth, 
												  labelMetrics.leftPanelObj.leftPanelMinWidth, labelMetrics.rightPanelObj.rightPanelMinWidth, 
												  labelMetrics.firstLabelWidth, labelMetrics.lastLabelWidth,
												  labelMetrics.changeCanvasPadding);
			//store globally.
			this.config.labelMetricsObj = metricsObj;
			if(labelMetrics.changeCanvasPadding){
				this.params.canvasPadding = metricsObj.canvasPaddingIncrease;
			}else{
				//now here we need to decrease the canvasWidth. The total change in both the panel 
				//is the total change in canvas width and the change only in the left panel is the amount
				//of shift in canvas start X
				var spaceAtLeft:Number = labelMetrics.leftPanelObj.leftPanelMinWidth;
				if(metricsObj.leftPanelWidth > spaceAtLeft){
					//decraese canvas width
					canvasWidth = canvasWidth - (metricsObj.leftPanelWidth - spaceAtLeft);
					//also shift canvs start X
					canvasStartX +=  metricsObj.leftPanelWidth - spaceAtLeft
				}
				var spaceAtRight:Number = labelMetrics.rightPanelObj.rightPanelMinWidth;
				//also deduct if any changes in the right panel
				if(metricsObj.rightPanelWidth > spaceAtRight){
					//decraese canvas width
					canvasWidth = canvasWidth - (metricsObj.rightPanelWidth - spaceAtRight);
				}
			}
		}

		//Based on canvas height that has been calculated, re-adjust yaxisLabelStep
		this.adjustYAxisLabelStep(yAxisValMaxHeight, canvasHeight);
		//Create an element to represent the canvas now.
		this.elements.canvas = returnDataAsElement (canvasStartX, canvasStartY, canvasWidth, canvasHeight);
		//Base Plane position - Base plane is the y-plane from which area chart emanates.
		//If there's a 0 value in between yMin,yMax, base plane represents 0 value.
		//Else, it's yMin
		if (this.config.yMax > 0 && this.config.yMin < 0)
		{
			//Negative number present - so set basePlanePos as 0 value
			this.config.basePlanePos = this.getAxisPosition (0, this.config.yMax, this.config.yMin, this.elements.canvas.y, this.elements.canvas.toY, true, 0);
		} else if (this.config.yMax <= 0 && this.config.yMin < 0)
		{
			//All negative numbers
			this.config.basePlanePos = this.getAxisPosition (this.config.yMax, this.config.yMax, this.config.yMin, this.elements.canvas.y, this.elements.canvas.toY, true, 0);
		} 
		else 
		{
			//No negative numbers - so set basePlanePos as yMin value
			this.config.basePlanePos = this.getAxisPosition (this.config.yMin, this.config.yMax, this.config.yMin, this.elements.canvas.y, this.elements.canvas.toY, true, 0);
		}
		//Adjust canvas start y
		this.config.basePlanePos = this.config.basePlanePos - this.elements.canvas.y;
		//We now need to calculate the position of area points on the chart.
		//Now, calculate the width between two points on chart
		if (this.scrollRequired){
			var interPointWidth : Number = this.config.interPointWidth;
		}else{
			//Scroll isn't required - so we show all the points
			var interPointWidth : Number = (this.elements.canvas.w - (2 * this.params.canvasPadding)) / (this.num - 1);
		}		
		//Store inter point width
		this.config.interPointWidth = interPointWidth;
		//If there is only 1 point, store interpoint width as canvas width itself
		if (this.num==1){
			this.config.interPointWidth = this.elements.canvas.w - (2 * this.params.canvasPadding);
		}
		//Store y-max and y-min positions for data
		this.config.yMaxPos = 0;
		this.config.yMinPos = 0;
		//Now, store the positions of the columns
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				//X-Position
				//Now, if there is only 1 point on the chart, we center it. Else, we get even X.
				this.dataset [i].data [j].x = (this.num == 1) ? (this.elements.canvas.w / 2) : (this.params.canvasPadding + (interPointWidth * (j - 1)));
				if (i == 1)
				{
					this.categories [j].x = this.dataset [i].data [j].x;
				}
				//Set the y position
				this.dataset [i].data [j].y = this.getAxisPosition (this.dataset [i].data [j].value, this.config.yMax, this.config.yMin, this.elements.canvas.y, this.elements.canvas.toY, true, 0) -  this.elements.canvas.y;
				//Store max and min positions
				this.config.yMaxPos = (this.dataset [i].data [j].y > this.config.yMaxPos) ? this.dataset [i].data [j].y : this.config.yMaxPos;
				this.config.yMinPos = (this.dataset [i].data [j].y < this.config.yMinPos) ? this.dataset [i].data [j].y : this.config.yMinPos;
				//Store value textbox y position
				this.dataset [i].data [j].valTBY = this.dataset [i].data [j].y;
			}
		}
	}
	/**
	* allotDepths method allots the depths for various chart objects
	* to be rendered. We do this before hand, so that we can later just
	* go on rendering chart objects, without swapping.
	*/
	private function allotDepths () : Void 
	{
		//Background
		this.dm.reserveDepths ("BACKGROUND", 1);
		//Click URL Handler
		this.dm.reserveDepths ("CLICKURLHANDLER", 1);
		//Background SWF
		this.dm.reserveDepths ("BGSWF", 1);
		//Canvas
		this.dm.reserveDepths ("CANVAS", 1);
		//If vertical grid is to be shown
		if (this.params.showAlternateVGridColor)
		{
			this.dm.reserveDepths ("VGRID", Math.ceil ((this.params.numVDivLines + 1) / 2));
		}
		//If horizontal grid is to be shown
		if (this.params.showAlternateHGridColor)
		{
			this.dm.reserveDepths ("HGRID", Math.ceil ((this.divLines.length + 1) / 2));
		}
		//Vertical Div Lines
		this.dm.reserveDepths ("VDIVLINES", this.params.numVDivLines);
		//Div Lines and their labels
		this.dm.reserveDepths ("DIVLINES", (this.divLines.length * 2));
		//Caption
		this.dm.reserveDepths ("CAPTION", 1);
		//Sub-caption
		this.dm.reserveDepths ("SUBCAPTION", 1);
		//X-Axis Name
		this.dm.reserveDepths ("XAXISNAME", 1);
		//Y-Axis Name
		this.dm.reserveDepths ("YAXISNAME", 1);
		//Trend lines below plot (lines and their labels)
		this.dm.reserveDepths ("TRENDLINESBELOW", this.numTrendLinesBelow);
		this.dm.reserveDepths ("TRENDVALUESBELOW", this.numTrendLinesBelow);
		//Depth for the scroll content movie
		this.dm.reserveDepths ("SCROLLCONTENT", 1);
		//Mask for scrolling
		this.dm.reserveDepths ("SCROLLMASK", 1);
		//Data Labels
		this.dm.reserveDepths ("DATALABELS", this.num);
		//Line Chart
		this.dm.reserveDepths ("DATAPLOT", this.numDS);
		//Zero Plane
		this.dm.reserveDepths ("ZEROPLANE", 2);
		//Vertical div lines
		this.dm.reserveDepths ("VLINES", this.numVLines);
		//Vertical div lines labels
		this.dm.reserveDepths ("VLINELABELS", this.numVLines);
		//Canvas Border
		this.dm.reserveDepths ("CANVASBORDER", 1);
		//Scroll bar
		this.dm.reserveDepths ("SCROLLBAR", 1);
		//Anchors
		this.dm.reserveDepths ("ANCHORS", this.num * this.numDS);
		//Trend lines below plot (lines and their labels)
		this.dm.reserveDepths ("TRENDLINESABOVE", (this.numTrendLines - this.numTrendLinesBelow));
		this.dm.reserveDepths ("TRENDVALUESABOVE", (this.numTrendLines - this.numTrendLinesBelow));
		//Data Values
		this.dm.reserveDepths ("DATAVALUES", this.num * this.numDS);
		//Legend
		this.dm.reserveDepths ("LEGEND", 1);
	}
	//--------------- VISUAL RENDERING METHODS -------------------------//
	/**
	 * createContainerMC method creates the containers for various scrolling
	 * related movie clips.
	*/
	private function createContainerMC():Void{
		//Create the scrollable movie clip (parent)
		this.scrollableMC = this.cMC.createEmptyMovieClip("ScrollableMC",this.dm.getDepth("SCROLLCONTENT"));
		//Inside scrollableMC, we need to create 2 movie clips - Content and Ref.
		this.scrollRefMC = this.scrollableMC.createEmptyMovieClip("RefMC",1);
		this.scrollContentMC = this.scrollableMC.createEmptyMovieClip("ContentMC",2);
		
		//Create empty movie clip containers for mask and scroll bar container
		this.maskMC = this.cMC.createEmptyMovieClip("MaskMC",this.dm.getDepth("SCROLLMASK"));
		this.scrollBarMC = this.cMC.createEmptyMovieClip("ScrollBarMC",this.dm.getDepth("SCROLLBAR"));
		//Reposition the scrollableMC at the beginning of canvas
		this.scrollableMC._x = this.elements.canvas.x;
		this.scrollableMC._y = this.elements.canvas.y;
		//Position the scroll bar too
		//Scroll bar must align with canvas left edge.
		this.scrollBarMC._x = this.elements.canvas.x;
		this.scrollBarMC._y = this.elements.canvas.toY + this.params.scrollPadding;
		//Inside Ref MC, we need to draw a line from 0,0 to max possible width.
		//Draw a hidden line - we create a hidden line spanning the whole width of the chart - since flash internally
		//returns the width of the MC as the width of the API drawn within and we need width with respect to starting
		//position of the API in the parent MC. So, we have created a maximum possible line in the parent MC and hence
		//flash returns the actual width of the MC which we require.
		this.scrollRefMC.lineStyle(1, 0x000000, 0);
		this.scrollRefMC.moveTo(0, 0);
		this.scrollRefMC.lineTo(this.categories[this.num].x+this.params.canvasPadding, 0);		
	}
	/**
	* drawHeaders method renders the following on the chart:
	* CAPTION, SUBCAPTION, XAXISNAME, YAXISNAME
	*/
	private function drawHeaders ()
	{
		//Render caption
		if (this.params.caption != "")
		{
			var captionStyleObj : Object = this.styleM.getTextStyle (this.objects.CAPTION);
			captionStyleObj.vAlign = "bottom";
			//Switch the alignment to lower case
			captionStyleObj.align = captionStyleObj.align.toLowerCase();
			//Now, based on alignment, decide the xPosition of the caption
			var xPos:Number = (captionStyleObj.align=="center")?(this.elements.canvas.x + (this.elements.canvas.w / 2)):((captionStyleObj.align=="left")?(this.elements.canvas.x):(this.elements.canvas.toX));
			var captionObj : Object = createText (false, this.params.caption, this.cMC, this.dm.getDepth ("CAPTION") , xPos , this.params.chartTopMargin, 0, captionStyleObj, true, this.elements.caption.w, this.elements.caption.h);
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (captionObj.tf, this.objects.CAPTION, this.macro, captionObj.tf._x , 0, captionObj.tf._y , 0, 100, null, null, null);
			}
			//Apply filters
			this.styleM.applyFilters (captionObj.tf, this.objects.CAPTION);
			//Delete
			delete captionObj;
			delete captionStyleObj;
		}
		//Render sub caption
		if (this.params.subCaption != "")
		{
			var subCaptionStyleObj : Object = this.styleM.getTextStyle (this.objects.SUBCAPTION);
			subCaptionStyleObj.vAlign = "top";
			//Switch the alignment to lower case
			subCaptionStyleObj.align = subCaptionStyleObj.align.toLowerCase();
			//Now, based on alignment, decide the xPosition of the caption
			var xPos:Number = (subCaptionStyleObj.align=="center")?(this.elements.canvas.x + (this.elements.canvas.w / 2)):((subCaptionStyleObj.align=="left")?(this.elements.canvas.x):(this.elements.canvas.toX));
			var subCaptionObj : Object = createText (false, this.params.subCaption, this.cMC, this.dm.getDepth ("SUBCAPTION") , xPos , this.elements.canvas.y - this.params.captionPadding, 0, subCaptionStyleObj, true, this.elements.subCaption.w, this.elements.subCaption.h);
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (subCaptionObj.tf, this.objects.SUBCAPTION, this.macro, subCaptionObj.tf._x , 0, subCaptionObj.tf._y, 0, 100, null, null, null);
			}
			//Apply filters
			this.styleM.applyFilters (subCaptionObj.tf, this.objects.SUBCAPTION);
			//Delete
			delete subCaptionObj;
			delete subCaptionStyleObj;
		}
		//Render x-axis name
		if (this.params.xAxisName != "")
		{
			var xAxisNameStyleObj : Object = this.styleM.getTextStyle (this.objects.XAXISNAME);
			xAxisNameStyleObj.align = "center";
			xAxisNameStyleObj.vAlign = "bottom";
			var xAxisNameObj : Object = createText (false, this.params.xAxisName, this.cMC, this.dm.getDepth ("XAXISNAME") , this.elements.canvas.x + (this.elements.canvas.w / 2) , this.height -(this.params.chartBottomMargin + this.config.legendAreaHeight + this.elements.xAxisName.h + this.params.xAxisNamePadding), 0, xAxisNameStyleObj, true, this.elements.xAxisName.w, this.elements.xAxisName.h);
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (xAxisNameObj.tf, this.objects.XAXISNAME, this.macro, this.elements.canvas.x + (this.elements.canvas.w / 2) - (this.elements.subCaption.w / 2) , 0, this.height -(this.params.chartBottomMargin + this.config.legendAreaHeight + this.elements.xAxisName.h + this.params.xAxisNamePadding), 0, 100, null, null, null);
			}
			//Apply filters
			this.styleM.applyFilters (xAxisNameObj.tf, this.objects.XAXISNAME);
			//Delete
			delete xAxisNameObj;
			delete xAxisNameStyleObj;
		}
		//Render y-axis name
		if (this.params.yAxisName != "")
		{
			var yAxisNameStyleObj : Object = this.styleM.getTextStyle (this.objects.YAXISNAME);
			//Set alignment parameters
			yAxisNameStyleObj.align = "left";
			yAxisNameStyleObj.vAlign = "middle";
			//If the name is to be rotated
			if (this.params.rotateYAxisName)
			{
				if(this.params.centerYAxisName){
					//Center y axis name with respect to chart.
					var yAxisNameObj : Object = createText(false, this.params.yAxisName, this.cMC, this.dm.getDepth("YAXISNAME"), this.params.chartLeftMargin, this.height/2, 270, yAxisNameStyleObj, true, this.elements.yAxisName.h, this.elements.yAxisName.w);
				} else {
					//Center y axis name with respect to canvas.
					var yAxisNameObj : Object = createText (false, this.params.yAxisName, this.cMC, this.dm.getDepth ("YAXISNAME") , this.params.chartLeftMargin, this.elements.canvas.y + (this.elements.canvas.h / 2), 270, yAxisNameStyleObj, true, this.elements.yAxisName.h, this.elements.yAxisName.w);
				}
				//Apply animation
				if (this.params.animation)
				{
					this.styleM.applyAnimation (yAxisNameObj.tf, this.objects.YAXISNAME, this.macro, yAxisNameObj.tf._x, 0, yAxisNameObj.tf._y , 0, 100, null, null, null);
				}
			} else 
			{
				//We show horizontal name
				//Adding 1 to this.params.yAxisNameWidth and then passing to avoid line breaks
				var yAxisNameObj : Object = createText (false, this.params.yAxisName, this.cMC, this.dm.getDepth ("YAXISNAME") , this.params.chartLeftMargin, this.elements.canvas.y + (this.elements.canvas.h / 2) , 0, yAxisNameStyleObj, true, this.params.yAxisNameWidth + 1, this.elements.canvas.h);
				//Apply animation
				if (this.params.animation)
				{
					this.styleM.applyAnimation (yAxisNameObj.tf, this.objects.YAXISNAME, this.macro, this.params.chartLeftMargin, 0, yAxisNameObj.tf._y, 0, 100, null, null, null);
				}
			}
			//Apply filters
			this.styleM.applyFilters (yAxisNameObj.tf, this.objects.YAXISNAME);
			//Delete
			delete yAxisNameObj;
			delete yAxisNameStyleObj;
		}
		//Clear Interval
		clearInterval (this.config.intervals.headers);
	}
	/**
	 * createScrollBar method creates the scroll bar
	*/
	private function createScrollBar(){
		//Initialize the scroll bar if required
		if (this.scrollRequired){
			//The width of the scroller bar is now same with the canvas width. Because in scroll charts canvas-border is drawn outside canvas.
			this.scrollB = new FCChartHScrollBar(this.scrollableMC, this.scrollRefMC, this.scrollBarMC, this.maskMC, this.elements.canvas.w, this.elements.canvas.h + this.params.scrollPadding + this.params.scrollHeight + this.params.labelPadding + this.config.labelAreaHeight, this.elements.canvas.w , this.params.scrollHeight, this.params.scrollBtnWidth, this.params.scrollBtnPadding, 0, 0);
			this.scrollB.setColor(this.params.scrollColor);			
			//If we've to scroll to end
			if (this.params.scrollToEnd){
				this.scrollB.scrollToEnd();
			}
			//Apply filters and animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (this.scrollBarMC, this.objects.SCROLLPANE, this.macro, null, 0, null, 0, 100, null, null, null);
			}
			//Apply filters
			this.styleM.applyFilters (scrollBarMC, this.objects.SCROLLPANE);
		}
		//this.scrollB.invalidate();
		clearInterval(this.config.intervals.scrollBar);
	}
	/**
	* drawLabels method draws labels with new label management or with the previous label management
	**/
	private function drawLabels()
	{
		if(this.params.XTLabelManagement && !this.scrollRequired){
			drawExtremeLabels();
		}else{
			drawNormalLabels()
		}
		//Clear interval
		clearInterval (this.config.intervals.labels);
	}
	/**
	 * drawExtremeLabels method initiates centralised x-axis label rendering.
	 */
	 private function drawExtremeLabels()
	 {
		var labelRenderer:LabelRenderer = new LabelRenderer();
		var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);
		var depth : Number = this.dm.getDepth ("DATALABELS");
		var styleManager:Object = this.styleM;
		var macroRef:Object = this.macro;
		var objId:Number = this.objects.DATALABELS;
		labelRenderer.dummyMC = this.tfTestMC;
		labelRenderer.testXPos = testTFX;
		labelRenderer.testYPos = testTFY;
		labelRenderer.scrollChart = true;
		labelRenderer.renderXAxisLabels(this.scrollContentMC, depth, this.num, this.categories, labelStyleObj, this.config, this.params.labelDisplay,
										this.elements.canvas, this.params.labelPadding, this.params.staggerLines, this.params.labelStep,
										this.params.animation,
										styleManager, macroRef, objId,
										this.params.showExtremeLabelRegion)
	 }
	/**
	* drawNormalLabels method draws the x-axis labels based on the parameters and on the previous approach.
	*/
	private function drawNormalLabels ()
	{
		var labelObj : Object;
		var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);
		var labelYShift : Number;
		var staggerCycle : Number = 0;
		var staggerAddFn : Number = 1;
		var depth : Number = this.dm.getDepth ("DATALABELS");
		var i : Number;
		var count:Number = 0;
		var labelWidth:Number, labelHeight:Number;
		for (i = 1; i <= this.num; i ++)
		{
			//If the label is to be shown
			if (this.categories [i].showLabel)
			{
				if(this.scrollRequired){
			
					//Set the label width
					if (this.params.labelDisplay == "WRAP"){
						labelWidth = this.categories [i].labelWidthMax;
						labelHeight = this.config.labelAreaHeight;
					}else if (this.params.labelDisplay == "ROTATE"){
						if(this.params.slantLabels){
							
							var tFormat:TextFormat = new TextFormat();
							//Font properties
							tFormat.font = labelStyleObj.font;
							tFormat.size = labelStyleObj.size;
							tFormat.color = parseInt(labelStyleObj.color, 16);
							//Text decoration
							tFormat.bold = labelStyleObj.bold;
							tFormat.italic = labelStyleObj.italic;
							tFormat.underline = labelStyleObj.underline;
							//Margin and spacing
							tFormat.leftMargin = labelStyleObj.leftMargin;
							tFormat.letterSpacing = labelStyleObj.letterSpacing;
							
							var minMetrics:Object = tFormat.getTextExtent('W');
							var unitH:Number = minMetrics.textFieldHeight;
							
							//Max height of the slant label
							labelHeight = unitH;

							//Restricting slant labels to a maximum height, for a proper presentation
							//Here, bottom-right corner can't go below half-way of the height of the labelAreaBox
							//In the limiting case, the text field is almost a square
							if(this.config.labelAreaHeight/2 < labelHeight * Math.sin(45*Math.PI/180)){
								//Changing label height, so as to have a rectangular text field (where w > h)
								var reducedUnitLabelWidth:Number = (this.config.labelAreaHeight/2)/Math.sin(45*Math.PI/180);
								labelHeight = reducedUnitLabelWidth * Math.cos(45*Math.PI/180);
								
							}
							
							//Max width of the slant label
							labelWidth = (this.config.labelAreaHeight - labelHeight * Math.sin(45*Math.PI/180)) / Math.cos(45*Math.PI/180);
						}else{
							labelWidth = this.config.maxAllowableLabelHeight;
							labelHeight = this.categories [i].labelWidthMax;
						}
					}
					if (this.params.labelDisplay == "ROTATE")
					{
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						//Create text box and get height
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0)) + this.params.labelPadding, this.config.labelAngle, labelStyleObj, true, labelWidth, labelHeight);
					} else if (this.params.labelDisplay == "WRAP")
					{
						//Case 2 (WRAP)
						//Set alignment
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding, 0, labelStyleObj, true, labelWidth, labelHeight);
					} else if (this.params.labelDisplay == "STAGGER")
					{
						//Case 3 (Stagger)
						//Set alignment
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						//Need to get cyclic position for staggered textboxes
						//Matrix formed is of 2*this.params.staggerLines - 2 rows
						count++;
						//Maximum allowed width for label
						var labelAllowedWidth:Number;
						var pos : Number = count % (2 * this.params.staggerLines - 2);
						//Last element needs to be reset
						pos = (pos == 0 || isNaN(pos) ) ? (2 * this.params.staggerLines - 2) : pos;
						//Cyclic iteration
						pos = (pos > this.params.staggerLines) ? (this.params.staggerLines - (pos % this.params.staggerLines)) : pos;
						//Get position to 0 base
						pos --;
					
						//For first label
						if(i == 1){
							if(this.num > 1){
								labelAllowedWidth = 2 * Math.min(this.config.unitLabelWidth, this.config.leftPanelWidth);
							}else{
								labelAllowedWidth = this.config.unitLabelWidth;
							}
						//For last label
						}else if(i == this.num){
							labelAllowedWidth = 2 * Math.min(this.config.unitLabelWidth, this.config.rightPanelWidth);
						//For in between labels
						}else{
							labelAllowedWidth = 2*this.config.unitLabelWidth;
						}
						//The minimum between allowd width and simulated width need to use as width.
						labelAllowedWidth = Math.min(this.categories [i].simulatedWidth, labelAllowedWidth);
						//Shift accordingly
						var labelYShift : Number = this.config.maxLabelHeight * ((pos < 0)? 0 : pos);
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding + labelYShift, 0, labelStyleObj, true, labelAllowedWidth, this.config.maxLabelHeight);
					} else 
					{
						//Render normal label
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding, 0, labelStyleObj, false, 0, 0);
					}
				}else{
			
					if (this.params.labelDisplay == "ROTATE")
					{
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						//Create text box and get height
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0)) + this.params.labelPadding, this.config.labelAngle, labelStyleObj, true, this.config.wrapLabelWidth, this.config.wrapLabelHeight);
					} else if (this.params.labelDisplay == "WRAP")
					{
						//Case 2 (WRAP)
						//Set alignment
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding, 0, labelStyleObj, true, this.config.wrapLabelWidth, this.config.wrapLabelHeight);
					} else if (this.params.labelDisplay == "STAGGER")
					{
						//Case 3 (Stagger)
						//Set alignment
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						//Need to get cyclic position for staggered textboxes
						//Matrix formed is of 2*this.params.staggerLines - 2 rows
						count++;
						var pos : Number = count % (2 * this.params.staggerLines - 2);
						//Last element needs to be reset
						pos = (pos == 0 || isNaN(pos)) ? (2 * this.params.staggerLines - 2) : pos;
						//Cyclic iteration
						pos = (pos > this.params.staggerLines) ? (this.params.staggerLines - (pos % this.params.staggerLines)) : pos;
						//Get position to 0 base
						pos --;
						//Shift accordingly
						var labelYShift : Number = this.config.maxLabelHeight * ((pos < 0)? 0 : pos);
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding + labelYShift, 0, labelStyleObj, false, 0, 0);
					} else 
					{
						//Render normal label
						labelStyleObj.align = "center";
						labelStyleObj.vAlign = "bottom";
						labelObj = createText (false, this.categories [i].label, this.scrollContentMC, depth, this.categories [i].x, this.elements.canvas.h + ((this.scrollRequired)?(this.params.scrollHeight + this.params.scrollPadding):(0))  + this.params.labelPadding, 0, labelStyleObj, false, 0, 0);
					}
				}
				//Apply filter
				this.styleM.applyFilters (labelObj.tf, this.objects.DATALABELS);
				//Apply animation
				if (this.params.animation)
				{
					this.styleM.applyAnimation (labelObj.tf, this.objects.DATALABELS, this.macro, null, 0, null, 0, 100, null, null, null);
				}
				//Increase depth
				depth ++;
			}
		}
	}
	/**
	* drawVLines method draws the vertical axis lines on the chart. 
	* This is an over-riding method, as need to plot the vLines inside
	* another movie clip at different x co-ordinates.
	*/
	private function drawVLines () : Void 
	{
		var depth : Number = this.dm.getDepth ("VLINES");
		//Depth of label
		var labelDepth : Number = this.dm.getDepth("VLINELABELS");
		//Label
		var vLineLabel:Object;
		//Get the font properties for v-line labels
		var vLineFont:Object = this.styleM.getTextStyle (this.objects.VLINELABELS);
		//Store vline color
		var vLineFontColor:String  = vLineFont.color;
		//Movie clip container
		var vLineMC : MovieClip;
		//Loop var
		var i : Number;
		//Iterate through all the v div lines
		for (i = 1; i <= this.numVLines; i ++)
		{
			if (this.vLines [i].isValid == true)
			{
				//If it's a valid line, proceed
				//Check if we've to draw the label of the same
				if (this.vLines[i].label != "") {
					//Customize the font properties for the same.
					vLineFont.borderColor = (this.vLines[i].showLabelBorder == true)?(this.vLines[i].color):("");
					//Reset vLineFont color
					vLineFont.color = vLineFontColor;
					//When font color is taken from base font color only  we can override it with vline color
					if(this.params.baseFontColor == vLineFont.color || this.vLines[i].lineColorDefined){
						//Set the color as well
						vLineFont.color = this.vLines[i].color;
					}
					//Set the alignment position
					vLineFont.align = this.vLines[i].labelHAlign;
					vLineFont.vAlign = (this.vLines[i].labelPosition<0.95)?this.vLines[i].labelVAlign:"top";
					//Create the label now
					vLineLabel = createText (false, this.vLines[i].label, this.scrollContentMC, labelDepth, this.vLines[i].x, (this.elements.canvas.h*this.vLines[i].labelPosition), 0, vLineFont, false, 0, 0);
					//Apply filters
					this.styleM.applyFilters (vLineLabel.tf, this.objects.VLINELABELS);
					//Animation 
					if (this.params.animation){
						this.styleM.applyAnimation (vLineLabel.tf, this.objects.VLINELABELS, this.macro, null, 0, null, 0, 100, null, null, null);
					}
					//Increment depth
					labelDepth++;
				}
				//Create a movie clip for the line
				vLineMC = this.scrollContentMC.createEmptyMovieClip ("vLine_" + i, depth);
				//Just draw line
				vLineMC.lineStyle (this.vLines [i].thickness, parseInt (this.vLines [i].color, 16) , this.vLines [i].alpha);
				//Now, if dashed line is to be drawn
				if ( ! this.vLines [i].isDashed)
				{
					//Draw normal line line keeping 0,0 as registration point
					vLineMC.moveTo (0, 0);
					vLineMC.lineTo (0, - this.elements.canvas.h);
				} else 
				{
					//Dashed Line line
					DrawingExt.dashTo (vLineMC, 0, 0, 0, - this.elements.canvas.h, this.vLines [i].dashLen, this.vLines [i].dashGap);
				}
				//Re-position line
				vLineMC._x = this.vLines [i].x;
				vLineMC._y = this.elements.canvas.h;
				//Apply animation
				if (this.params.animation)
				{
					this.styleM.applyAnimation (vLineMC, this.objects.VLINES, this.macro, null, 0, null, 0, 100, null, 100, null);
				}
				//Apply filters
				this.styleM.applyFilters (vLineMC, this.objects.VLINES);
				//Increase depth
				depth ++;
			}
		}
		delete vLineMC;
		//Clear interval
		clearInterval (this.config.intervals.vLine);
	}
	/**
	* drawAreaChart method draws the area on the chart
	*/
	private function drawAreaChart () : Void 
	{
		/**
		* The movie clip structure for each area (dataset) would be :
		* |- Holder
		* |- |- Chart
		* We create child movie clip as we need to animate xscale
		* and y scale. So, we need to position Chart Movie clip at 0,0
		* inside holder movie clip and then readjust Holder movie clip's
		* X and Y Position as per chart's canvas.
		*/
		var m : Number;
		var depth : Number = this.dm.getDepth ("DATAPLOT");
		for (m = 1; m <= this.numDS; m ++)
		{
			//Create holder movie clip
			var holderMC : MovieClip = this.scrollContentMC.createEmptyMovieClip ("ChartHolder_" + m, depth);
			//Register with object Manager
			objM.register(holderMC, "DATAPLOT_" + m, "DATAPLOTS_"+m)
			//Create chart movie clip inside holder movie clip
			var chartMC : MovieClip = holderMC.createEmptyMovieClip ("Chart", 1);
			//Loop variables
			var i, j;
			//Variables to store the max and min Y positions
			var maxY : Number, minY : Number;
			//Find the index of the first defined data
			//Initialize with (this.num+1) so that if no defined data is found,
			//next loop automatically terminates
			var firstIndex : Number = this.num + 1;
			var lastIndex : Number = - 1;
			//Storage container for next plot index
			var nxt : Number;
			for (i = 1; i < this.num; i ++)
			{
				if (this.dataset [m].data [i].isDefined)
				{
					firstIndex = i;
					break;
				}
			}
			//Get index of last defined data
			for (i = this.num; i > 0; i --)
			{
				if (this.dataset [m].data [i].isDefined)
				{
					lastIndex = i;
					break;
				}
			}
			//Create matrix object for gradient fill
			var matrix : Object = {
				matrixType : "box", w : (this.dataset [m].data [lastIndex].x - this.dataset [m].data [firstIndex].x) , h : (this.config.yMaxPos - this.config.yMinPos) , x : this.elements.canvas.x, y : this.elements.canvas.y, r : MathExt.toRadians (this.params.plotFillAngle)
			};
			//Now, we draw the areas inside chart
			for (i = firstIndex; i < this.num; i ++)
			{
				//We continue only if this data index is defined
				if (this.dataset [m].data [i].isDefined)
				{
					//Get next Index
					nxt = i + 1;
					//Now, if next index is not defined, we can have two cases:
					//1. Draw gap between this data and next data.
					//2. Connect the next index which could be found as defined.
					//Case 1. If connectNullData is set to false and next data is not
					//defined. We simply continue to next value of the loop
					if (this.params.connectNullData == false && this.dataset [m].data [nxt].isDefined == false)
					{
						//Discontinuous plot. So ignore and move to next.
						continue;
					}
					//Now, if nxt data is undefined, we need to find the index of the post data
					//which is not undefined
					if (this.dataset [m].data [nxt].isDefined == false)
					{
						//Initiate nxt as -1, so that we can later break if no next defined data
						//could be found.
						nxt = - 1;
						for (j = i + 1; j <= this.num; j ++)
						{
							if (this.dataset [m].data [j].isDefined == true)
							{
								nxt = j;
								break;
							}
						}
						//If nxt is still -1, we break
						if (nxt == - 1)
						{
							break;
						}
					}
					//Set line style to empty - as the border is drawn later on.
					chartMC.lineStyle ();
					//Move to current data position
					chartMC.moveTo (this.dataset [m].data [i].x, this.dataset [m].data [i].y);
					//Set fill parameters
					if (this.params.plotGradientColor != "")
					{
						//Fill as gradient
						chartMC.beginGradientFill ("linear", [parseInt (this.dataset [m].data [i].color, 16) , parseInt (this.params.plotGradientColor, 16)] , [this.dataset [m].data [i].alpha, this.dataset [m].data [i].alpha] , [0, 255] , matrix);
					} else 
					{
						//Solid fill
						chartMC.beginFill (parseInt (this.dataset [m].data [i].color, 16) , this.dataset [m].data [i].alpha);
					}
					//Move to the next data
					chartMC.lineTo (this.dataset [m].data [nxt].x, this.dataset [m].data [nxt].y);
					//Draw the line from next data to base plan
					chartMC.lineTo (this.dataset [m].data [nxt].x, this.config.basePlanePos);
					//Draw the line back to previous data x, base plane
					chartMC.lineTo (this.dataset [m].data [i].x, this.config.basePlanePos);
					//We draw the line back from current data to base plan
					//only if it's the first data or the previous data was null (& connectNull=false)
					chartMC.lineTo (this.dataset [m].data [i].x, this.dataset [m].data [i].y);
					//End fill
					chartMC.endFill ();
					//Now, tat the entire fill is drawn, we're concerned with the border.
					//If border is to be shown, we need to redraw over the fill surface
					//Otherwise dashed borders are not filling the surface
					if (this.dataset [m].showPlotBorder)
					{
						//Set line style
						chartMC.lineStyle (this.dataset [m].plotBorderThickness, parseInt (this.dataset [m].plotBorderColor, 16) , this.dataset [m].plotBorderAlpha);
						//Now, based on whether we've to draw a normal or dashed line, we draw it
						//Line from current data point to next one.
						if (this.dataset [m].data [i].dashed)
						{
							//Draw a dashed line
							DrawingExt.dashTo (chartMC, this.dataset [m].data [i].x, this.dataset [m].data [i].y, this.dataset [m].data [nxt].x, this.dataset [m].data [nxt].y, this.params.plotBorderDashLen, this.params.plotBorderDashGap);
							//Now, we draw the line from data to bottom plane only if next data is undefined
							//(and connectNullData == false) or if it's last data
							if (this.dataset [m].showPlotBorder && ((nxt == lastIndex) || (this.params.connectNullData == false && this.dataset [m].data [i + 2].isDefined == false)))
							{
								DrawingExt.dashTo (chartMC, this.dataset [m].data [nxt].x, this.dataset [m].data [nxt].y, this.dataset [m].data [nxt].x, this.config.basePlanePos, this.params.plotBorderDashLen, this.params.plotBorderDashGap);
							}
							//Draw the line back to previous data x, base plane
							DrawingExt.dashTo (chartMC, this.dataset [m].data [nxt].x, this.config.basePlanePos, this.dataset [m].data [i].x, this.config.basePlanePos, this.params.plotBorderDashLen, this.params.plotBorderDashGap);
							//We draw the line back from current data to base plan
							//only if it's the first data or the previous data was null (& connectNull=false)
							if (this.dataset [m].showPlotBorder && (i == firstIndex || (this.params.connectNullData == false && this.dataset [m].data [i - 1].isDefined == false)))
							{
								DrawingExt.dashTo (chartMC, this.dataset [m].data [i].x, this.config.basePlanePos, this.dataset [m].data [i].x, this.dataset [m].data [i].y, this.params.plotBorderDashLen, this.params.plotBorderDashGap);
							}
						} else 
						{
							//Draw point to next line
							chartMC.lineTo (this.dataset [m].data [nxt].x, this.dataset [m].data [nxt].y);
							chartMC.moveTo (this.dataset [m].data [nxt].x, this.dataset [m].data [nxt].y);
							//Now, we draw the line from data to bottom plane only if next data is undefined
							//(and connectNullData == false) or if it's last data
							if (this.dataset [m].showPlotBorder && ((nxt == lastIndex) || (this.params.connectNullData == false && this.dataset [m].data [i + 2].isDefined == false)))
							{
								chartMC.lineTo (this.dataset [m].data [nxt].x, this.config.basePlanePos);
							}
							//Draw the line back to previous data x, base plane
							chartMC.moveTo (this.dataset [m].data [nxt].x, this.config.basePlanePos);
							chartMC.lineTo (this.dataset [m].data [i].x, this.config.basePlanePos);
							//We draw the line back from current data to base plan
							//only if it's the first data or the previous data was null (& connectNull=false)
							if (this.dataset [m].showPlotBorder && (i == firstIndex || (this.params.connectNullData == false && this.dataset [m].data [i - 1].isDefined == false)))
							{
								chartMC.moveTo (this.dataset [m].data [i].x, this.config.basePlanePos);
								chartMC.lineTo (this.dataset [m].data [i].x, this.dataset [m].data [i].y);
							}
						}
					}
					//Get maxY and minY
					maxY = (maxY == undefined || (this.dataset [m].data [i].y > maxY)) ? this.dataset [m].data [i].y : maxY;
					minY = (minY == undefined || (this.dataset [m].data [i].y < minY)) ? this.dataset [m].data [i].y : minY;
					//Update loop index (required when connectNullData is true and there is
					//a sequence of empty sets.) Since we've already found the "next" defined
					//data, we update loop to that to optimize.
					i = nxt - 1;
				}
			}
			//Now, we need to adjust the chart movie clip to 0,0 position as center
			chartMC._x = - (this.elements.canvas.w / 2);
			chartMC._y = - (maxY) + (maxY - this.config.basePlanePos);
			//Set the position of holder movie clip now
			holderMC._x = (this.elements.canvas.w / 2);
			holderMC._y = (maxY) - (maxY - this.config.basePlanePos);
			/*	According to Adobe's documentation- a filter is not applied if the resulting image exceeds 2880 pixels in width or height. 
				For example,if zoom in on a large movie clip with a filter applied, the filter is turned off if the resulting image exceeds 
				the limit of 2880 pixels. But practically it turns off filter after dimention excedeeing 2860-2865 pixels approximately.
				So we are not applying the filter on a movieclip beyond 2860 pixels (width/ height) for optimizing the process and 
				avoid unexpected error occurance.
				Bug Id: #FCXT-439
			*/
			if(holderMC._width <= 2860){
				//Apply filter
				this.styleM.applyFilters (holderMC, this.objects.DATAPLOT);
			}
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (holderMC, this.objects.DATAPLOT, this.macro, null, 0, null, 0, 100, 100, 100, null);
			}
			//Increment depth
			depth ++;
		}
		//Clear interval
		clearInterval (this.config.intervals.plot);
	}
	/**
	* drawAnchors method draws the anchors on the chart
	*/
	private function drawAnchors () : Void 
	{
		//Variables
		var anchorMC : MovieClip;
		var depth : Number = this.dm.getDepth ("ANCHORS");
		var i : Number;
		var j : Number;
		//Create function storage containers for Delegate functions
		var fnRollOver : Function, fnClick : Function;
		//Iterate through all points
		for (i = 1; i <= this.numDS; i ++)
		{
			if (this.dataset [i].drawAnchors)
			{
				for (j = 1; j <= this.num; j ++)
				{
					//If defined
					if (this.dataset [i].data [j].isDefined)
					{
						//Create an empty movie clip for this anchor
						anchorMC = this.scrollContentMC.createEmptyMovieClip ("Anchor_" + i, depth);
						//Register with object Manager
						objM.register(anchorMC, "DATAANCHORS_" + i + "_" + j, "DATAANCHORS_"+i)
						//Set the line style and fill
						anchorMC.lineStyle (this.dataset [i].data [j].anchorBorderThickness, parseInt (this.dataset [i].data [j].anchorBorderColor, 16) , 100);
						anchorMC.beginFill (parseInt (this.dataset [i].data [j].anchorBgColor, 16) , this.dataset [i].data [j].anchorBgAlpha);
						if(this.dataset[i].data[j].anchorSides < 3){
							//Draw the circle
							DrawingExt.drawCircle(anchorMC, 0, 0, this.dataset[i].data[j].anchorRadius, this.dataset[i].data[j].anchorRadius, 0, 360)
						} else {
							//Draw the polygon
							DrawingExt.drawPoly(anchorMC, 0, 0, this.dataset[i].data[j].anchorSides, this.dataset[i].data[j].anchorRadius, 90);
						}
						//Set the x and y Position
						anchorMC._x = this.dataset [i].data [j].x;
						anchorMC._y = this.dataset [i].data [j].y;
						//Set the alpha of entire anchor
						anchorMC._alpha = this.dataset [i].data [j].anchorAlpha;
						//Apply animation
						if (this.params.animation)
						{
							this.styleM.applyAnimation (anchorMC, this.objects.ANCHORS, this.macro, null, 0, null, 0, this.dataset [i].data [j].anchorAlpha, 100, 100, null);
						}
						//Apply filters
						this.styleM.applyFilters (anchorMC, this.objects.ANCHORS);
						//Event handlers for tool tip
						if (this.params.showToolTip)
						{
							//Create Delegate for roll over function columnOnRollOver
							fnRollOver = Delegate.create (this, dataOnRollOver);
							//Set the index
							fnRollOver.dsindex = i;
							fnRollOver.index = j;
							//Assing the delegates to movie clip handler
							anchorMC.onRollOver = fnRollOver;
							//Set roll out and mouse move too.
							anchorMC.onRollOut = anchorMC.onReleaseOutside = Delegate.create (this, dataOnRollOut);
						}
						//Click handler for links - only if link for this anchor has been defined and click URL
						//has not been defined.
						if (this.dataset [i].data [j].link != "" && this.dataset [i].data [j].link != undefined && this.params.clickURL == "")
						{
							//Create delegate function
							fnClick = Delegate.create (this, dataOnClick);
							//Set index
							fnClick.dsindex = i;
							fnClick.index = j;
							//Assign
							anchorMC.onRelease = fnClick;
						} else 
						{
							//Do not use hand cursor
							anchorMC.useHandCursor = (this.params.clickURL == "") ? false : true;
							//Enable for clickURL
							this.invokeClickURLFromPlots(anchorMC);
						}
						//Increase depth
						depth ++;
					}
				}
			}
		}
		//Clear interval
		clearInterval (this.config.intervals.anchors);
	}
	/**
	* drawValues method draws the values on the chart.
	*/
	private function drawValues () : Void 
	{
		//Get value text style
		var valueStyleObj : Object = this.styleM.getTextStyle (this.objects.DATAVALUES);
		//Individual properties
		var isBold : Boolean = valueStyleObj.bold;
		var isItalic : Boolean = valueStyleObj.italic;
		var font : String = valueStyleObj.font;
		var angle : Number = 0;
		//Container object
		var valueObj : MovieClip;
		//Depth
		var depth : Number = this.dm.getDepth ("DATAVALUES");
		//Loop var
		var i : Number, j : Number;
		var yPos : Number;
		var align : String, vAlign : String;
		////Iterate through all points
		for (i = 1; i <= this.numDS; i ++)
		{
			for (j = 1; j <= this.num; j ++)
			{
				//If defined and value is to be shown
				if (this.dataset [i].data [j].isDefined && this.dataset [i].data [j].showValue)
				{
					//Get the y position based on next data's position
					//If this data value is positive, we show textbox above
					if (this.dataset [i].data [j].value >= 0)
					{
						//Above
						vAlign = "top";
						yPos = this.dataset [i].data [j].valTBY - this.params.valuePadding;
					} else 
					{
						//Below
						vAlign = "bottom";
						yPos = this.dataset [i].data [j].valTBY + this.params.valuePadding;
					}
					//Align position
					align = "center";
					//Convey alignment to rendering object
					valueStyleObj.align = align;
					valueStyleObj.vAlign = vAlign;
					//Now, if the labels are to be rotated
					if (this.params.rotateValues)
					{
						valueStyleObj.bold = isBold;
						valueStyleObj.italic = isItalic;
						valueStyleObj.font = font;
						angle = 270;
					} else 
					{
						//Normal horizontal label - Store original properties
						valueStyleObj.bold = isBold;
						valueStyleObj.italic = isItalic;
						valueStyleObj.font = font;
						angle = 0;
					}
					valueObj = createText (false, this.dataset [i].data [j].displayValue, this.scrollContentMC, depth, this.dataset [i].data [j].x, yPos, angle, valueStyleObj, false, 0, 0);
					//Next, we adjust those labels are falling out of top canvas area
					if (((yPos - valueObj.height) <= 0))
					{
						//Data value is colliding with the upper side of canvas. So we need to place it within
						//the area
						if ( ! this.params.rotateValues)
						{
							valueObj.tf._y = yPos + (2 * this.params.valuePadding);
						} else 
						{
							valueObj.tf._y = yPos + (2 * this.params.valuePadding) + valueObj.height;
						}
					}
					//Now, we adjust those labels are falling out of bottom canvas area
					if (((yPos + valueObj.height) >= this.elements.canvas.h))
					{
						//Data value is colliding with the lower side of canvas. So we need to place it within
						//the area
						if ( ! this.params.rotateValues)
						{
							valueObj.tf._y = yPos - (2 * this.params.valuePadding) - valueObj.height;
						} else 
						{
							valueObj.tf._y = yPos - (2 * this.params.valuePadding);
						}
					}
					//Register with object Manager
					objM.register(valueObj.tf, "DATAVALUE_" + i + "_" + j, "DATAVALUES_"+i)
					//Apply filter
					this.styleM.applyFilters (valueObj.tf, this.objects.DATAVALUES);
					//Apply animation
					if (this.params.animation)
					{
						this.styleM.applyAnimation (valueObj.tf, this.objects.DATAVALUES, this.macro, null, 0, null, 0, 100, null, null, null);
					}
					//Increase depth
					depth ++;
				}
			}
		}
		//Clear interval
		clearInterval (this.config.intervals.dataValues);
	}
	/**
	* drawVDivLines method draws the vertical div lines on the chart
	*/
	private function drawVDivLines () : Void 
	{
		var yPos : Number;
		var depth : Number = this.dm.getDepth ("VDIVLINES");
		//Movie clip container
		var vDivLineMC : MovieClip;
		//Get the horizontal spacing between two vertical div lines
		if (this.num==1){
			var horSpace : Number = this.config.interPointWidth/ (this.params.numVDivLines-1);
		}else{
			if (this.params.numVDivLines>1){
				var horSpace : Number = ((this.num-1) * this.config.interPointWidth) / (this.params.numVDivLines-1);
			}else{
				var horSpace : Number = ((this.num-1) * this.config.interPointWidth) / (this.params.numVDivLines+1);
			}
		}
		//Get x position
		var xPos : Number = (this.params.numVDivLines==1)?(this.params.canvasPadding + horSpace):(this.params.canvasPadding);
		var i : Number;
		for (i = 1; i <= this.params.numVDivLines; i ++)
		{			
			//Create the movie clip
			vDivLineMC = this.scrollContentMC.createEmptyMovieClip ("vDivLine_" + i, depth);
			//Draw the line
			vDivLineMC.lineStyle (this.params.vDivLineThickness, parseInt (this.params.vDivLineColor, 16) , this.params.vDivLineAlpha);
			if (this.params.vDivLineIsDashed)
			{
				//Dashed line
				DrawingExt.dashTo (vDivLineMC, 0, - this.elements.canvas.h / 2, 0, this.elements.canvas.h / 2, this.params.vDivLineDashLen, this.params.vDivLineDashGap);
			} else 
			{
				//Draw the line keeping 0,0 as registration point
				vDivLineMC.moveTo (0, - this.elements.canvas.h / 2);
				//Normal line
				vDivLineMC.lineTo (0, this.elements.canvas.h / 2);
			}
			//Re-position the div line to required place
			vDivLineMC._x = xPos;
			vDivLineMC._y = (this.elements.canvas.h / 2);
			//Apply animation and filter effects to vertical div line
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (vDivLineMC, this.objects.VDIVLINES, this.macro, null, 0, null, 0, 100, null, 100, null);
			}
			//Apply filters
			this.styleM.applyFilters (vDivLineMC, this.objects.VDIVLINES);
			//Update x position for next line
			xPos = xPos + horSpace;
			//Increment depth
			depth ++;
		}
		//Clear interval
		clearInterval (this.config.intervals.vDivLines);
	}
	/**
	* drawHGrid method draws the horizontal grid background color
	*/
	private function drawVGrid () : Void 
	{
		//If we're required to draw vertical grid color
		//and numVDivLines > 1
		if (this.params.showAlternateVGridColor && this.params.numVDivLines > 1)
		{
			//Movie clip container
			var gridMC : MovieClip;
			//Loop variable
			var i : Number;
			//Get depth
			var depth : Number = this.dm.getDepth ("VGRID");
			//X Position
			var xPos : Number, xPosEnd : Number;
			var width : Number;
			//Get the horizontal spacing between two vertical div lines
			if (this.num==1){
				var horSpace : Number = this.config.interPointWidth/ (this.params.numVDivLines-1);
			}else{
				var horSpace : Number = ((this.num-1) * this.config.interPointWidth) / (this.params.numVDivLines-1);
			}
			for (i = 1; i <= this.params.numVDivLines; i = i + 2)
			{
				//Get x Position
				xPos = (i - 1) * horSpace + this.params.canvasPadding;
				//Get x end position
				xPosEnd = xPos + horSpace;
				//If it's last div line, then we need to restrict end point
				xPosEnd = (i == this.params.numVDivLines) ? (xPos + this.params.canvasPadding) : (xPosEnd);
				//Get the width of the grid.
				width = xPosEnd - xPos;
				//Create the movie clip
				gridMC = this.scrollContentMC.createEmptyMovieClip ("VGridBg_" + i, depth);
				//Set line style to null
				gridMC.lineStyle ();
				//Set fill color
				gridMC.moveTo ( - (width / 2) , - (this.elements.canvas.h / 2));
				gridMC.beginFill (parseInt (this.params.alternateVGridColor, 16) , this.params.alternateVGridAlpha);
				//Draw rectangle
				gridMC.lineTo (width / 2, - (this.elements.canvas.h / 2));
				gridMC.lineTo (width / 2, this.elements.canvas.h / 2);
				gridMC.lineTo ( - (width / 2) , this.elements.canvas.h / 2);
				gridMC.lineTo ( - (width / 2) , - (this.elements.canvas.h / 2));
				//End Fill
				gridMC.endFill ();
				//Place it in right location
				gridMC._x = xPosEnd - (width / 2);
				gridMC._y = this.elements.canvas.h / 2;
				//Apply animation
				if (this.params.animation)
				{
					this.styleM.applyAnimation (gridMC, this.objects.VGRID, this.macro, null, 0, null, 0, 100, 100, 100, null);
				}
				//Apply filters
				this.styleM.applyFilters (gridMC, this.objects.VGRID);
				//Increase depth
				depth ++;
			}
		}
		//Clear interval
		clearInterval (this.config.intervals.vGrid);
	}
	/**
	* drawLegend method renders the legend
	*/
	private function drawLegend () : Void
	{
		if (this.params.showLegend)
		{
			this.lgnd.render ();
			//If it's interactive legend, listen to events
			if (this.params.interactiveLegend){
				this.lgnd.addEventListener("legendClick",this);
			}
			//Apply filter
			this.styleM.applyFilters (lgndMC, this.objects.LEGEND);
			//Apply animation
			if (this.params.animation)
			{
				this.styleM.applyAnimation (lgndMC, this.objects.LEGEND, this.macro, null, 0, null, 0, 100, null, null, null);
			}
		}
		//Clear interval
		clearInterval (this.config.intervals.legend);
	}
	/**
	* setContextMenu method sets the context menu for the chart.
	* For this chart, the context items are "Print Chart".
	*/
	private function setContextMenu () : Void 
	{
		var chartMenu : ContextMenu = new ContextMenu ();
		chartMenu.hideBuiltInItems ();
		if (this.params.showPrintMenuItem){
			//Create a print chart contenxt menu item
			var printCMI : ContextMenuItem = new ContextMenuItem ("Print Chart", Delegate.create (this, printChart));
			//Push print item.
			chartMenu.customItems.push (printCMI);
		}
		//If the export data item is to be shown
		if (this.params.showExportDataMenuItem){
			chartMenu.customItems.push(super.returnExportDataMenuItem());
		}
		//Add export chart related menu items to the context menu
		this.addExportItemsToMenu(chartMenu);
		if (this.params.showFCMenuItem){
			//Push "About FusionCharts" Menu Item
			chartMenu.customItems.push(super.returnAbtMenuItem());		
		}
		//Assign the menu to cMC movie clip
		this.cMC.menu = chartMenu;
	}
	// -------------------- EVENT HANDLERS --------------------//
	/**
	* dataOnRollOver is the delegat-ed event handler method that'll
	* be invoked when the user rolls his mouse over an anchor.
	* This function is invoked, only if the tool tip is to be shown.
	* Here, we show the tool tip.
	*/
	private function dataOnRollOver () : Void 
	{
		//Index of data is stored in arguments.caller.index
		var dsindex : Number = arguments.caller.dsindex;
		var index : Number = arguments.caller.index;
		//Set tool tip text
		this.tTip.setText (this.dataset [dsindex].data [index].toolText);
		//Show the tool tip
		this.tTip.show ();
		//Stop invoke clickURL 
		this.isClickMCPressed = false;
	}
	/**
	* dataOnRollOut method is invoked when the mouse rolls out
	* of anchor. We just hide the tool tip here.
	*/
	private function dataOnRollOut () : Void 
	{
		//Hide the tool tip
		this.tTip.hide ();
		//Stop invoke clickURL 
		this.isClickMCPressed = false;
	}
	/**
	* dataOnClick is invoked when the user clicks on a anchor (if link
	* has been defined). We invoke the required link.
	*/
	private function dataOnClick () : Void 
	{
		//Index of column is stored in arguments.caller.index
		var dsindex : Number = arguments.caller.dsindex;
		var index : Number = arguments.caller.index;
		//Invoke the link
		super.invokeLink (this.dataset [dsindex].data [index].link);
	}
	/**
	 * legendClick method is the event handler for legend. In this method,
	 * we toggle the visibility of dataset.
	*/
	private function legendClick(target:Object):Void{
		if (this.chartDrawn){
			//Update the container flag that the data-set is now visible/invisible
			objM.toggleGroupVisibility("DATAVALUES_"+target.index, target.active);
			objM.toggleGroupVisibility("DATAPLOTS_"+target.index, target.active);
			objM.toggleGroupVisibility("DATAANCHORS_"+target.index, target.active);
			//Raise Legend Click external event
			this.raiseLegendItemClickedEvent({datasetIndex:target.index, datasetName:this.dataset [target.index].seriesName});
		}else{
			lgnd.cancelClickEvent(target.intIndex);
		}
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
		//Now initialize things that are pertinent to this class
		//but not defined in super class.
		this.categories = new Array ();
		this.dataset = new Array ();
		//Initialize the number of data elements present
		this.numDS = 0;
		this.num = 0;
		//Destroy the legend
		this.lgnd.destroy ();
		if (this.params.interactiveLegend){
			//Remove listener for legend object.
			this.lgnd.removeEventListener("legendClick", this);
		}
		//Re-set scroll properties
		this.scrollRequired = false;
		//As it is scroll chart set isScrollChart to true
		this.params.isScrollChart = true;
	}
	/**
	* remove method removes the chart by clearing the chart movie clip
	* and removing any listeners.
	*/
	public function remove () : Void 
	{
		super.remove ();
		//Remove class pertinent objects
		if (this.params.interactiveLegend){
			//Remove listener for legend object.
			this.lgnd.removeEventListener("legendClick", this);
		}
		//Remove legend
		this.lgnd.destroy ();
		lgndMC.removeMovieClip ();
		//Remove scroll bar related objects
		this.scrollB.destroy();
		scrollableMC.removeMovieClip();
		scrollContentMC.removeMovieClip();
		scrollRefMC.removeMovieClip();
		maskMC.removeMovieClip();
		scrollBarMC.removeMovieClip();
	}
	//---------------DATA EXPORT HANDLERS-------------------//
	/**
	 * Returns the data of the chart in CSV/TSV format. The separator, qualifier and line
	 * break character is stored in params (during common parsing).
	 * @return	The data of the chart in CSV/TSV format, as specified in the XML.
	 */
	public function exportChartDataCSV():String {
		var strData:String = "";
		var strQ:String = this.params.exportDataQualifier;
		var strS:String = this.params.exportDataSeparator;
		var strLB:String = this.params.exportDataLineBreak;
		var i:Number, j:Number;
		strData = strQ + ((this.params.xAxisName!="")?(this.params.xAxisName):("Label")) + strQ + ((this.numDS > 0)? (strS): "");
		//Add all the series names
		for (i = 1; i <= this.numDS; i++) {
			strData += strQ + ((this.dataset[i].seriesName != "")?(this.dataset[i].seriesName):("")) + strQ + ((i < this.numDS)?(strS):(strLB));
		}
		(this.numDS ==0)? strData +=strLB : strData;
		//Iterate through each data-items and add it to the output
		for (i = 1; i <= this.num; i ++)
		{
			//Add the category label
			strData += strQ + (this.categories [i].label)  + strQ + ((this.numDS > 0)? (strS): "");
			//Add the individual value for datasets
			for (j = 1; j <= this.numDS; j ++)
			{
				 strData += strQ + ((this.dataset[j].data[i].isDefined==true)?((this.params.exportDataFormattedVal==true)?(this.dataset[j].data[i].formattedValue):(this.dataset[j].data[i].value)):(""))  + strQ + ((j<this.numDS)?strS:"");
			}
			if (i < this.num) {
				strData += strLB;
			}
		}
		return strData;
	}
	/**
	  * getSimulationWidth method stores simulation width for each category object.
	  */
	private function getSimulationWidth(){
		var labelObj : Object;
		var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);
		var i:Number;
		for (i = 1; i <= this.num; i ++)
		{
			//Whethere the label has to be shown or not.
			if(this.categories [i].showLabel){
				if (this.params.labelDisplay == "WRAP" || this.params.labelDisplay == "STAGGER" || this.params.labelDisplay == "NONE")
				{
					//Create text box and get width and height
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1, 0, 0, 0, labelStyleObj, false, 0, 0);
					//Label width
					this.categories [i].simulatedWidth = (!isNaN(labelObj.width))? labelObj.width : 0;
					///Label height
					labelObj.height = (!isNaN(labelObj.height))? labelObj.height : 0;
					//For STAGGER and NONE label display mode set maximum label height
					if(this.params.labelDisplay == "STAGGER" || this.params.labelDisplay == "NONE"){
						this.config.maxLabelHeight = Math.max(this.config.maxLabelHeight,labelObj.height);
					}
				}else if (this.params.labelDisplay == "ROTATE")
				{
					var maxLabelWidth : Number = this.config.maxAllowableLabelHeight;
					var maxLabelHeight : Number = (2*this.params.canvasPadding)+ (this.config.interPointWidth*(this.num-1));
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1,  0, 0,this.config.labelAngle, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
					this.categories [i].simulatedWidth = (!isNaN(labelObj.width))? labelObj.width : 0;
					//Maximum single line text height for rotated text
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1, 0, 0, 0, labelStyleObj, false, 0, 0);
					this.config.maxSingleLineTextHeight = (this.config.maxSingleLineTextHeight < labelObj.height)? labelObj.height:this.config.maxSingleLineTextHeight;
				}
			//When label not to be shown set the simulation width  zero
			}else
			{
				this.categories [i].simulatedWidth = 0;
			}
		}
		//Remove simulated textfield
		labelObj.tf.removeTextField();
		//Delete objects
		delete labelObj;
		delete labelStyleObj;
	}
	/**
	  * manageScrollLabels method manages the label width
	  */
	private function manageScrollLabels(){
		var labelObj : Object;
		var labelStyleObj : Object = this.styleM.getTextStyle (this.objects.DATALABELS);
		//previous label width
		var prevLabelWidth:Number;
		//Next label width
		var nextLabelWidth:Number;
		//Width allowd to use from previous label
		var widthAllowedFromPrev:Number;
		//Width allowd to use from next label
		var widthAllowedFromNext:Number;
		this.config.labelAreaHeight = 0;
		this.config.maxLabelHeight = 0;
		//Loop Variable
		var i:Number;
		for (i = 1; i <= this.num; i ++)
		{ 
			//This calculation needed when there is more than one plot
			if( this.num > 1){
				//Reset previous label allowed width
				widthAllowedFromPrev = 0;
				//Reset next label allowed width
				widthAllowedFromNext = 0;
				//When simulated width is more than unit label width
				if(this.categories [i].simulatedWidth > this.config.unitLabelWidth){
					//Width allowed from previous
					//For first label chart will check the available space in the left panel
					if( i == 1){
						if((this.config.unitLabelWidth/2)< this.config.leftPanelWidth  ){
							
							widthAllowedFromPrev = (this.config.leftPanelWidth - (this.config.unitLabelWidth/2));
						}
					//For rest labels chart will check the available space in the previous label
					}else{
						//When step more than 1
						if(this.params.labelStep > 1){
							widthAllowedFromPrev = this.config.unitLabelWidth/2;
						}else if (this.categories [i-1].simulatedWidth < this.config.unitLabelWidth){
							widthAllowedFromPrev = (this.config.unitLabelWidth - this.categories [i-1].simulatedWidth)/2;
						}
					}
					//Width allowed from next
					//For last label chart will check the available space in the right panel
					if( i == this.num){
						if(this.config.rightPanelWidth > ( this.config.unitLabelWidth/2)){
							widthAllowedFromNext = (this.config.rightPanelWidth -  this.config.unitLabelWidth/2);
						}
					}else{
						//When step more than 1
						if(this.params.labelStep > 1){
							widthAllowedFromNext = this.config.unitLabelWidth/2;
						}else if (this.categories [i+1].simulatedWidth < this.config.unitLabelWidth){
							widthAllowedFromNext = (this.config.unitLabelWidth - this.categories [i+1].simulatedWidth)/2;
						}
					}
					//The twice min of these previous and next allowed width can be added to the unit label width for ith label.
					this.categories [i].labelWidthMax = this.config.unitLabelWidth + 2*Math.min(widthAllowedFromPrev,widthAllowedFromNext);
				//For the label smaller than unit label width
				}else{
					this.categories [i].labelWidthMax = this.categories [i].simulatedWidth;
				}
			//For 1 and only label it will equal to the unit label width.
			}else{
				this.categories [i].labelWidthMax = this.config.unitLabelWidth;
			}
			//The maximum width should the lesser one beteewn simulated width and  label max width.
			this.categories [i].labelWidthMax = Math.min(this.categories [i].simulatedWidth, this.categories [i].labelWidthMax);
			//get Max Text height-------------
			if(this.categories [i].showLabel){
				if (this.params.labelDisplay == "WRAP"){
					var maxLabelWidth : Number = this.categories [i].labelWidthMax;
					var maxLabelHeight : Number = this.config.maxAllowableLabelHeight;
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1,  0, 0,0, labelStyleObj, true, this.categories [i].labelWidthMax, maxLabelHeight);
					this.config.labelAreaHeight = Math.max(this.config.labelAreaHeight, labelObj.height);
				}else if (this.params.labelDisplay == "ROTATE"){
					var maxLabelWidth : Number = this.config.maxAllowableLabelHeight;
					var maxLabelHeight : Number = this.categories [i].labelWidthMax;
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1,  0, 0,this.config.labelAngle, labelStyleObj, true, maxLabelWidth, maxLabelHeight);
					this.config.labelAreaHeight = Math.max(this.config.labelAreaHeight, labelObj.height);
					if(this.params.slantLabels){
						this.config.labelAreaHeight = Math.min(this.config.labelAreaHeight,this.config.maxAllowableLabelHeight);
					}
				}else{
					labelObj = createText (true, this.categories [i].label, this.tfTestMC, 1,  0, 0,0, labelStyleObj, true, 0, 0);
					this.config.maxLabelHeight = Math.max(this.config.maxLabelHeight, labelObj.height);
				}
			}
		}
		if (this.params.labelDisplay == "STAGGER"){
			//Here we again validate the stagger lines. The minimum is validated at the time of parsing
			//the maximum is validated here.
			if(this.params.staggerLines > this.num){
				this.params.staggerLines = this.num;
			}
			this.config.labelAreaHeight = this.config.maxLabelHeight* (this.params.staggerLines);
		}
		//Remove simulated textfield
		labelObj.tf.removeTextField();
		labelObj.tf.removeMovieClip();
		//Delete objects
		delete labelObj;
		delete labelStyleObj;
		
	}
}
