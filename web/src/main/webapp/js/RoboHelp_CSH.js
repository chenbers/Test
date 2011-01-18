// Macromedia
// Copyright© 1998-2004 eHelp® Corporation.All rights reserved.
// RoboHelp_CSH.js
// The Helper function for WebHelp Context Sensitive Help

//     Syntax:
//     function RH_ShowHelp(hParent, a_pszHelpFile, uCommand, dwData)
//
//     hParent
//          Reserved - Use 0
//   
//     pszHelpFile
//          WebHelp: 
//               Path to help system start page ("http://www.myurl.com/help/help.htm" or "/help/help.htm")
//               For custom windows (defined in Help project), add ">" followed by the window name ("/help/help.htm>mywin")
//
//          WebHelp Enterprise: 
//               Path to RoboEngine server ("http://RoboEngine/roboapi.asp")
//               If automatic merging is turned off in RoboEngine Configuration Manager, specify the project name in the URL ("http://RoboEngine/roboapi.asp?project=myproject")
//               For custom windows (defined in Help project), add ">" followed by the window name ("http://RoboEngine/roboapi.asp>mywindow")
//
//     uCommand
//          Command to display help. One of the following:
//                    HH_HELP_CONTEXT     // Displays the topic associated with the Map ID sent in dwData
//											if 0, then default topic is displayed.				
//               The following display the default topic and the Search, Index, or TOC pane. 
//               Note: The pane displayed in WebHelp Enterprise will always be the window's default pane.
//                    HH_DISPLAY_SEARCH 
//                    HH_DISPLAY_INDEX
//                    HH_DISPLAY_TOC
//
//     dwData
//          Map ID associated with the topic to open (if using HH_HELP_CONTEXT), otherwise 0
//
//     Examples:
//     <p>Click for <A HREF='javascript:RH_ShowHelp(0, "help/help.htm", 0, 10)'>Help</A> (map number 10)</p>
//     <p>Click for <A HREF='javascript:RH_ShowHelp(0, "help/help.htm>mywindow", 0, 100)'>Help in custom window (map number 100)</A></p>


var gbNav6=false;
var gbNav61=false;
var gbNav4=false;
var gbIE4=false;
var gbIE=false;
var gbIE5=false;
var gbIE55=false;

var gAgent=navigator.userAgent.toLowerCase();
var gbMac=(gAgent.indexOf("mac")!=-1);
var gbSunOS=(gAgent.indexOf("sunos")!=-1);
var gbOpera=(gAgent.indexOf("opera")!=-1);

var HH_DISPLAY_TOPIC = 0;
var HH_DISPLAY_TOC = 1;
var HH_DISPLAY_INDEX = 2;
var HH_DISPLAY_SEARCH = 3;
var HH_HELP_CONTEXT = 15;

var gVersion=navigator.appVersion.toLowerCase();

var gnVerMajor=parseInt(gVersion);
var gnVerMinor=parseFloat(gVersion);

gbIE=(navigator.appName.indexOf("Microsoft")!=-1);
if(gnVerMajor>=4)
{
	if(navigator.appName=="Netscape")
	{
		gbNav4=true;
		if(gnVerMajor>=5)
			gbNav6=true;
	}
	gbIE4=(navigator.appName.indexOf("Microsoft")!=-1);
}
if(gbNav6)
{
	document.gnPageWidth=innerWidth;
	document.gnPageHeight=innerHeight;
	var nPos=gAgent.indexOf("netscape");
	if(nPos!=-1)
	{
		var nVersion=parseFloat(gAgent.substring(nPos+10));
		if(nVersion>=6.1)
			gbNav61=true;
	}
}else if(gbIE4)
{
	var nPos=gAgent.indexOf("msie");
	if(nPos!=-1)
	{
		var nVersion=parseFloat(gAgent.substring(nPos+5));
		if(nVersion>=5)
			gbIE5=true;
		if(nVersion>=5.5)
			gbIE55=true;
	}
}

function RH_ShowHelp(hParent, a_pszHelpFile, uCommand, dwData)
{
	// this function only support WebHelp
	var strHelpPath = a_pszHelpFile;
	var strWnd = "";
	var nPos = a_pszHelpFile.indexOf(">");
	if (nPos != -1)
	{
		strHelpPath = a_pszHelpFile.substring(0, nPos);
		strWnd = a_pszHelpFile.substring(nPos+1); 
	}
	if (isServerBased(strHelpPath))
		RH_ShowWebHelp_Server(hParent, strHelpPath, strWnd, uCommand, dwData);
	else
		RH_ShowWebHelp(hParent, strHelpPath, strWnd, uCommand, dwData);
}

function RH_ShowWebHelp_Server(hParent, strHelpPath, strWnd, uCommand, dwData)
{
	// hParent never used.
	ShowWebHelp_Server(strHelpPath, strWnd, uCommand, dwData);
}

function RH_ShowWebHelp(hParent, strHelpPath, strWnd, uCommand, dwData)
{
	// hParent never used.
	ShowWebHelp(strHelpPath, strWnd, uCommand, dwData);
}


function ShowWebHelp_Server(strHelpPath, strWnd, uCommand, nMapId)
{
	var a_pszHelpFile = "";
	if (uCommand == HH_HELP_CONTEXT)
	{
		if (strHelpPath.indexOf("?") == -1)
			a_pszHelpFile = strHelpPath + "?ctxid=" + nMapId;
		else
			a_pszHelpFile = strHelpPath + "&ctxid=" + nMapId;
	}
	else
	{
		if (strHelpPath.indexOf("?") == -1)
			a_pszHelpFile = strHelpPath + "?ctxid=0";
		else
			a_pszHelpFile = strHelpPath + "&ctxid=0";
	}

	if (strWnd)
		a_pszHelpFile += ">" + strWnd;
	

	if (gbIE4)
	{
		a_pszHelpFile += "&cmd=newwnd&rtype=iefrm";
		loadData(a_pszHelpFile);
	}
	else if (gbNav4)
	{
		a_pszHelpFile += "&cmd=newwnd&rtype=nswnd";
		var sParam = "left="+screen.width+",top="+screen.height+",width=100,height=100";
		window.open(a_pszHelpFile, "__webCshStub", sParam);
	}
	else
	{
		var sParam = "left="+screen.width+",top="+screen.height+",width=100,height=100";
		if (gbIE5)
			window.open("about:blank", "__webCshStub", sParam);
		window.open(a_pszHelpFile, "__webCshStub");
	}
}


function ShowWebHelp(strHelpPath, strWnd, uCommand, nMapId)
{
	var a_pszHelpFile = "";
	if (uCommand == HH_DISPLAY_TOPIC)
	{
		a_pszHelpFile = strHelpPath + "#<id=0";
	}
	if (uCommand == HH_HELP_CONTEXT)
	{
		a_pszHelpFile = strHelpPath + "#<id=" + nMapId;
	}
	else if (uCommand == HH_DISPLAY_INDEX)
	{
		a_pszHelpFile = strHelpPath + "#<cmd=idx";
	}
	else if (uCommand == HH_DISPLAY_SEARCH)
	{
		a_pszHelpFile = strHelpPath + "#<cmd=fts";
	}
	else if (uCommand == HH_DISPLAY_TOC)
	{
		a_pszHelpFile = strHelpPath + "#<cmd=toc";
	}
	if (strWnd)
		a_pszHelpFile += ">>wnd=" + strWnd;

	alert(a_pszHelpFile);
	if (a_pszHelpFile)
	{
		if (gbIE4) {
			loadData(a_pszHelpFile);
		}
		else if (gbNav4) {
			var sParam = "left="+screen.width+",top="+screen.height+",width=100,height=100";
			window.open(a_pszHelpFile, "__webCshStub", sParam);
		}
		else
		{
			var sParam = "left="+screen.width+",top="+screen.height+",width=100,height=100";
			if (gbIE5)
				window.open("about:blank", "__webCshStub", sParam);
			window.open(a_pszHelpFile, "__webCshStub");
		}
	}
}

function isServerBased(a_pszHelpFile)
{
	if (a_pszHelpFile.length > 0)
	{
		var nPos = a_pszHelpFile.lastIndexOf('.');
		if (nPos != -1 && a_pszHelpFile.length >= nPos + 4)
		{
			var sExt = a_pszHelpFile.substring(nPos, nPos + 4);
			if (sExt.toLowerCase() == ".htm")
			{
				return false;
			}
		}
	}
	return true;
}

function getElement(sID)
{
	if(document.getElementById)
		return document.getElementById(sID);
	else if(document.all)
		return document.all(sID);
	return null;
}

function loadData(sFileName)
{
	if(!getElement("dataDiv"))
	{
		if(!insertDataDiv())
		{
			gsFileName=sFileName;
			return;
		}
	}
	var sHTML="";
	if(gbMac)
		sHTML+="<iframe name=\"__WebHelpCshStub\" src=\""+sFileName+"\"></iframe>";
	else
		sHTML+="<iframe name=\"__WebHelpCshStub\" style=\"visibility:hidden;width:0;height:0\" src=\""+sFileName+"\"></iframe>";
	
	var oDivCon=getElement("dataDiv");
	if(oDivCon)
	{
		if(gbNav6)
		{
			if(oDivCon.getElementsByTagName&&oDivCon.getElementsByTagName("iFrame").length>0)
			{
				oDivCon.getElementsByTagName("iFrame")[0].src=sFileName;
			}
			else
				oDivCon.innerHTML=sHTML;
		}
		else
			oDivCon.innerHTML=sHTML;
	}
}

function insertDataDiv()
{
	var sHTML="";
	if(gbMac)
		sHTML+="<div id=dataDiv style=\"display:none;\"></div>";
	else
		sHTML+="<div id=dataDiv style=\"visibility:hidden\"></div>";

	document.body.insertAdjacentHTML("beforeEnd",sHTML);
	return true;
}
