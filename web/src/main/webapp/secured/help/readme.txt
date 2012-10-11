If a new RoboHelp build is generated there are a couple of Hacks to get around issues due to using old versions of robohelp/framemaker:

1 - Launching the help from RoboHelp_CSH.js allows for a window to be specified (we are calling this in includes/header.xhtml)  -- NewWindow is the name:
                	<a class="tb-help" href='javascript:RH_ShowHelp(0, "#{helpBean.path}>NewWindow", 15, #{(empty helpMapIDOverride) ? helpBean.mapID : helpMapIDOverride})'>
                		#{messages.header_help}
                	</a>


    In the Robohelp generated file WebHelp/whcshdata.htm  
     change:
	addWindow("NewWindow",true,0,"","","","","NewWindow",2,2,"toc|ndx|nls|gls","toc");

     to:
	addWindow("NewWindow",true,0,"","","","","Help",2,2,"toc|ndx|nls|gls","toc");


2 - With the above change when the help launches in firefox it selects the parent of the topic we are linking to (IE works fine).  To get around this edit WebHelp/whthost.js.
	Add these lines right after the variable declarations at the top:

var gExpandSuppress = true;

function endExpandSuppression()
{
    gExpandSuppress = false;
}

setTimeout('endExpandSuppression()', 5000);

	In the top of the function checkBookItem(nIdx) add this:

if(gbNav6 && gExpandSuppress) return;