(function($){ 

	$.fn.scoreBox = function(options){
		var settings = $.extend({
			size:"small",
			notApplicableText:"N/A"
		},options||{});
		this.each(function(){
			var wrappedSet = $(this);
			while(wrappedSet.html().indexOf("<") >= 0){
				wrappedSet = $.getChildWrappedSet(wrappedSet);
			}
			
			var score = wrappedSet.html();
			wrappedSet.html(score != parseFloat(score)?settings.notApplicableText:score);
			
			var scoreCssClass = ScoreBox.getScoreCssClass(score);
			var sizeCssClass = ScoreBox.getSizeCssClass(settings.size);
			//$(this).attr("class",$(this).attr("class") + " score " + scoreCssClass + " " + sizeCssClass);
			var innerHtml = $(this).html();
			$(this).html("");
			$("<table cellpadding='0' cellspacing='0' class='score " + scoreCssClass + " " + sizeCssClass +"'><tr><td>" + innerHtml + "</td></tr></table>").appendTo($(this));
		});
	};	
	
	$.getChildWrappedSet = function(parent){
		return parent.children();
	};
	
})(jQuery);


var ScoreBox = {};

ScoreBox.getScoreCssClass = function(score){
	
	if(score != parseFloat(score)){
		return "score_na";
	}
	
	score = new Number(score);
	
	if(score >= 0 && score <= 1)
		return "score_1";
	else if(score > 1 && score <= 2)
		return "score_2";
	else if(score > 2 && score <= 3)
		return "score_3";
	else if(score > 3 && score <= 4)
		return "score_4";
	else if(score > 4)
		return "score_5";
		
	return "score_na";
};

ScoreBox.getSizeCssClass = function(size){
	if(size == "xx-small")
		return "score_xx-small";
	else if(size == "small" || size == "")
		return "score_small";
	else if(size == "medium")
		return "score_medium";
	else if(size == "large")
		return "score_large";
	else if(size == "xx-large")
		return "score_xx-large";
};

