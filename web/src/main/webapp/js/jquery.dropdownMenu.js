
/**
 * Context Menu
 */

(function($){
	$.fn.dropdownMenu = function(options){
		var settings = $.extend({
			menuItems:{item1:{label:"Item 1",action:function(){alert("Item 1 clicked");},icon:""}},
			width:"120px"
		},options||{});
		
		
		var menu = $("<div class='dropdown-menu' style='display:none;width:" + settings.width + "'><ul></ul></div>");
		
		for (var key in settings.menuItems) {
		 
		   var menuItem = $.extend({
			   label:"Item 1",
			   action:function(){return false;},
			   icon:""
		   },settings.menuItems[key]||{});
		   var listItem = $("<li><a href='#' style='background: url(" + menuItem.icon + ") left center no-repeat'>" + menuItem.label + "</a></li>").bind("click",menuItem.action);
		   listItem.appendTo(menu);
		}
		
		menu.appendTo(document.body);
		
		
		$(this).bind("click",{menu:menu,button:$(this)},$.openMenu);
	};
	
	var closeMenu = false;
	
	$.openMenu = function(event){
		var offset = event.data.button.offset();
	    var buttonHeight = event.data.button.height();
	    event.data.menu
	    .css({'top': offset.top + buttonHeight, 'left': offset.left})
	    .click().show(200);
	    
	    //$(event.data.menu).one("mouseenter",{menu:event.data.menu,button:event.data.button},$.openMenu);
	    $(event.data.menu).one("mouseleave",{menu:event.data.menu,button:event.data.button},$.closeMenu);
	    //$(this).one("mouseleave",{menu:event.data.menu,button:event.data.button},$.closeMenu);
	};
	
	$.closeMenu = function(event){
		event.data.menu.hide(100);
		event.data.button.one('click',{menu:event.data.menu,button:event.data.button},$.openMenu);
	};
})(jQuery);