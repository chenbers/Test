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
			notApplicableText:"N/A"
		},options||{});
		this.each(function(){
			var wrappedSet = $(this);
			while(wrappedSet.html().indexOf("<") >= 0){
				wrappedSet = $.getChildWrappedSet(wrappedSet);
			}
			

			var score = wrappedSet.html();
			wrappedSet.html(score != parseFloat(score)?settings.notApplicableText:score);
			
			
			var scoreCssClass = ScoreBox.getScoreCssSuffix(score);
			
			var innerHtml = $(this).html();
			$(this).html("");
			$("<table cellpadding='0' cellspacing='0' class='score score_" + settings.size + "_"+ scoreCssClass + "'><tr><td>" + innerHtml + "</td></tr></table>").appendTo($(this));
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

