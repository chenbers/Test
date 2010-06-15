/**
 * 
 * Score Box jQuery Widget.
 * 
 * Usage instructions
 * 
 * <a4j:loadStyle src="/css/scoreBox.css"/>
 * <a4j:loadScript src="/js/scorebox.js"/>
 * jQuery(function(){
 * 		jQuery('jQuery Selector').scoreBox({size:'medium'});
 * })
 * 
 * Richfaces jQuery tag example: (creates the same jQuery as above)
 * 
 * <a4j:loadStyle src="/css/scoreBox.css"/>
 * <a4j:loadScript src="/js/scorebox.js"/>
 * <rich:jQuery selector="jQuery Selector" timing="onload" query="scoreBox({size:'med'})" />
 * 
 * Then in your .xhtml page
 * 
 * <div id="some id">#{entity.score}</div>
 * 	 
 * 
 */

(function($){ 

	$.fn.scoreBox = function(options){
		var settings = $.extend({
			size:"sm",
			notApplicableText:"N/A",
			pixels:null
		},options||{});
		this.each(function(){
			var wrappedSet = $(this);
			while(wrappedSet.html().indexOf("<") >= 0){
				wrappedSet = $.getChildWrappedSet(wrappedSet);
			}
			var score = wrappedSet.html();
			var parseableScore = score.replace(",", ".");
			wrappedSet.html(parseableScore != parseFloat(parseableScore)?settings.notApplicableText:score);
						
			var innerHtml = $(this).html();
			$(this).html("");
			
			
			var scoreCssClass = "score_" + ScoreBox.getScoreCssSuffix(parseableScore);
			var sizeCssClass = "score_" + settings.size;
			
			var wrapperDivStyle = "";
			var outerDivStyle = "display: table; #position: relative; overflow: hidden; width:100%;height:100%;";
			var middleDivStyle = "#position: relative;  #top: 50%; display: table-cell; vertical-align: middle;width:100%;";
			var innerDivStyle = "#position: relative;  #top: -50%; width:100%;text-align:center";
			if(settings.pixels){
				var fontSize = settings.pixels * .45;
				wrapperDivStyle += "width:" + settings.pixels + "px;height:" + settings.pixels + "px;font-size:" + fontSize + "px";
				
				//If the user defines the pixels manually, wipe out the size class
				sizeCssClass = "";
			}
			
			//Create new dom object for score box;
			$("<div class='score " + scoreCssClass + " " + sizeCssClass + "' style='"+ wrapperDivStyle+ "'><div style='" + outerDivStyle + "'><div style='" + middleDivStyle + "'><div style='" + innerDivStyle + "'>" + innerHtml + "</div></div></div></div>").appendTo($(this));
		});
	};	
	
	$.getChildWrappedSet = function(parent){
		return parent.children();
	};
	
})(jQuery);


var ScoreBox = {};

ScoreBox.getScoreCssSuffix = function(score){
	
	if(score != parseFloat(score)){
		return "na";
	}
	
	score = new Number(score);
	
	if(score >= 0 && score <= 1)
		return "1";
	else if(score > 1 && score <= 2)
		return "2";
	else if(score > 2 && score <= 3)
		return "3";
	else if(score > 3 && score <= 4)
		return "4";
	else if(score > 4)
		return "5";
		
	return "na";
};

