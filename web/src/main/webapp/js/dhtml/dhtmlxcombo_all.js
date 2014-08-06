//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
dhtmlx=function(obj){for (var a in obj)dhtmlx[a]=obj[a];return dhtmlx};dhtmlx.extend_api=function(name,map,ext){var t = window[name];if (!t)return;window[name]=function(obj){if (obj && typeof obj == "object" && !obj.tagName && !(obj instanceof Array)){var that = t.apply(this,(map._init?map._init(obj):arguments));for (var a in dhtmlx)if (map[a])this[map[a]](dhtmlx[a]);for (var a in obj){if (map[a])this[map[a]](obj[a]);else if (a.indexOf("on")==0){this.attachEvent(a,obj[a])}}}else
    var that = t.apply(this,arguments);if (map._patch)map._patch(this);return that||this};window[name].prototype=t.prototype;if (ext)dhtmlXHeir(window[name].prototype,ext)};dhtmlxAjax={get:function(url,callback){var t=new dtmlXMLLoaderObject(true);t.async=(arguments.length<3);t.waitCall=callback;t.loadXML(url)
    return t},
    post:function(url,post,callback){var t=new dtmlXMLLoaderObject(true);t.async=(arguments.length<4);t.waitCall=callback;t.loadXML(url,true,post)
        return t},
    getSync:function(url){return this.get(url,null,true)
    },
    postSync:function(url,post){return this.post(url,post,null,true)}};function dtmlXMLLoaderObject(funcObject, dhtmlObject, async, rSeed){this.xmlDoc="";if (typeof (async)!= "undefined")
    this.async=async;else
    this.async=true;this.onloadAction=funcObject||null;this.mainObject=dhtmlObject||null;this.waitCall=null;this.rSeed=rSeed||false;return this};dtmlXMLLoaderObject.prototype.waitLoadFunction=function(dhtmlObject){var once = true;this.check=function (){if ((dhtmlObject)&&(dhtmlObject.onloadAction != null)){if ((!dhtmlObject.xmlDoc.readyState)||(dhtmlObject.xmlDoc.readyState == 4)){if (!once)return;once=false;if (typeof dhtmlObject.onloadAction == "function")dhtmlObject.onloadAction(dhtmlObject.mainObject, null, null, null, dhtmlObject);if (dhtmlObject.waitCall){dhtmlObject.waitCall.call(this,dhtmlObject);dhtmlObject.waitCall=null}}}};return this.check};dtmlXMLLoaderObject.prototype.getXMLTopNode=function(tagName, oldObj){if (this.xmlDoc.responseXML){var temp = this.xmlDoc.responseXML.getElementsByTagName(tagName);if(temp.length==0 && tagName.indexOf(":")!=-1)
    var temp = this.xmlDoc.responseXML.getElementsByTagName((tagName.split(":"))[1]);var z = temp[0]}else
    var z = this.xmlDoc.documentElement;if (z){this._retry=false;return z};if ((_isIE)&&(!this._retry)){var xmlString = this.xmlDoc.responseText;var oldObj = this.xmlDoc;this._retry=true;this.xmlDoc=new ActiveXObject("Microsoft.XMLDOM");this.xmlDoc.async=false;this.xmlDoc["loadXM"+"L"](xmlString);return this.getXMLTopNode(tagName, oldObj)};dhtmlxError.throwError("LoadXML", "Incorrect XML", [
    (oldObj||this.xmlDoc),
    this.mainObject
]);return document.createElement("DIV")};dtmlXMLLoaderObject.prototype.loadXMLString=function(xmlString){{
    try{var parser = new DOMParser();this.xmlDoc=parser.parseFromString(xmlString, "text/xml")}catch (e){this.xmlDoc=new ActiveXObject("Microsoft.XMLDOM");this.xmlDoc.async=this.async;this.xmlDoc["loadXM"+"L"](xmlString)}};this.onloadAction(this.mainObject, null, null, null, this);if (this.waitCall){this.waitCall();this.waitCall=null}};dtmlXMLLoaderObject.prototype.loadXML=function(filePath, postMode, postVars, rpc){if (this.rSeed)filePath+=((filePath.indexOf("?") != -1) ? "&" : "?")+"a_dhx_rSeed="+(new Date()).valueOf();this.filePath=filePath;if ((!_isIE)&&(window.XMLHttpRequest))
    this.xmlDoc=new XMLHttpRequest();else {if (document.implementation&&document.implementation.createDocument){this.xmlDoc=document.implementation.createDocument("", "", null);this.xmlDoc.onload=new this.waitLoadFunction(this);this.xmlDoc.load(filePath);return}else
    this.xmlDoc=new ActiveXObject("Microsoft.XMLHTTP")};if (this.async)this.xmlDoc.onreadystatechange=new this.waitLoadFunction(this);this.xmlDoc.open(postMode ? "POST" : "GET", filePath, this.async);if (rpc){this.xmlDoc.setRequestHeader("User-Agent", "dhtmlxRPC v0.1 ("+navigator.userAgent+")");this.xmlDoc.setRequestHeader("Content-type", "text/xml")}else if (postMode)this.xmlDoc.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');this.xmlDoc.setRequestHeader("X-Requested-With","XMLHttpRequest");this.xmlDoc.send(null||postVars);if (!this.async)(new this.waitLoadFunction(this))()};dtmlXMLLoaderObject.prototype.destructor=function(){this.onloadAction=null;this.mainObject=null;this.xmlDoc=null;return null};dtmlXMLLoaderObject.prototype.xmlNodeToJSON = function(node){var t={};for (var i=0;i<node.attributes.length;i++)t[node.attributes[i].name]=node.attributes[i].value;t["_tagvalue"]=node.firstChild?node.firstChild.nodeValue:"";for (var i=0;i<node.childNodes.length;i++){var name=node.childNodes[i].tagName;if (name){if (!t[name])t[name]=[];t[name].push(this.xmlNodeToJSON(node.childNodes[i]))}};return t};function callerFunction(funcObject, dhtmlObject){this.handler=function(e){if (!e)e=window.event;funcObject(e, dhtmlObject);return true};return this.handler};function getAbsoluteLeft(htmlObject){return getOffset(htmlObject).left};function getAbsoluteTop(htmlObject){return getOffset(htmlObject).top};function getOffsetSum(elem) {var top=0, left=0;while(elem){top = top + parseInt(elem.offsetTop);left = left + parseInt(elem.offsetLeft);elem = elem.offsetParent};return {top: top, left: left}};function getOffsetRect(elem) {var box = elem.getBoundingClientRect();var body = document.body;var docElem = document.documentElement;var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop;var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft;var clientTop = docElem.clientTop || body.clientTop || 0;var clientLeft = docElem.clientLeft || body.clientLeft || 0;var top = box.top + scrollTop - clientTop;var left = box.left + scrollLeft - clientLeft;return {top: Math.round(top), left: Math.round(left) }};function getOffset(elem) {if (elem.getBoundingClientRect && !_isChrome){return getOffsetRect(elem)}else {return getOffsetSum(elem)}};function convertStringToBoolean(inputString){if (typeof (inputString)== "string")
    inputString=inputString.toLowerCase();switch (inputString){case "1":
    case "true":
    case "yes":
    case "y":
    case 1:
    case true:
        return true;break;default: return false}};function getUrlSymbol(str){if (str.indexOf("?")!= -1)
    return "&"
else
    return "?"
};function dhtmlDragAndDropObject(){if (window.dhtmlDragAndDrop)return window.dhtmlDragAndDrop;this.lastLanding=0;this.dragNode=0;this.dragStartNode=0;this.dragStartObject=0;this.tempDOMU=null;this.tempDOMM=null;this.waitDrag=0;window.dhtmlDragAndDrop=this;return this};dhtmlDragAndDropObject.prototype.removeDraggableItem=function(htmlNode){htmlNode.onmousedown=null;htmlNode.dragStarter=null;htmlNode.dragLanding=null};dhtmlDragAndDropObject.prototype.addDraggableItem=function(htmlNode, dhtmlObject){htmlNode.onmousedown=this.preCreateDragCopy;htmlNode.dragStarter=dhtmlObject;this.addDragLanding(htmlNode, dhtmlObject)};dhtmlDragAndDropObject.prototype.addDragLanding=function(htmlNode, dhtmlObject){htmlNode.dragLanding=dhtmlObject};dhtmlDragAndDropObject.prototype.preCreateDragCopy=function(e){if ((e||event)&& (e||event).button == 2)
    return;if (window.dhtmlDragAndDrop.waitDrag){window.dhtmlDragAndDrop.waitDrag=0;document.body.onmouseup=window.dhtmlDragAndDrop.tempDOMU;document.body.onmousemove=window.dhtmlDragAndDrop.tempDOMM;return false};window.dhtmlDragAndDrop.waitDrag=1;window.dhtmlDragAndDrop.tempDOMU=document.body.onmouseup;window.dhtmlDragAndDrop.tempDOMM=document.body.onmousemove;window.dhtmlDragAndDrop.dragStartNode=this;window.dhtmlDragAndDrop.dragStartObject=this.dragStarter;document.body.onmouseup=window.dhtmlDragAndDrop.preCreateDragCopy;document.body.onmousemove=window.dhtmlDragAndDrop.callDrag;window.dhtmlDragAndDrop.downtime = new Date().valueOf();if ((e)&&(e.preventDefault)){e.preventDefault();return false};return false};dhtmlDragAndDropObject.prototype.callDrag=function(e){if (!e)e=window.event;dragger=window.dhtmlDragAndDrop;if ((new Date()).valueOf()-dragger.downtime<100) return;if ((e.button == 0)&&(_isIE))
    return dragger.stopDrag();if (!dragger.dragNode&&dragger.waitDrag){dragger.dragNode=dragger.dragStartObject._createDragNode(dragger.dragStartNode, e);if (!dragger.dragNode)return dragger.stopDrag();dragger.dragNode.onselectstart=function(){return false};dragger.gldragNode=dragger.dragNode;document.body.appendChild(dragger.dragNode);document.body.onmouseup=dragger.stopDrag;dragger.waitDrag=0;dragger.dragNode.pWindow=window;dragger.initFrameRoute()};if (dragger.dragNode.parentNode != window.document.body){var grd = dragger.gldragNode;if (dragger.gldragNode.old)grd=dragger.gldragNode.old;grd.parentNode.removeChild(grd);var oldBody = dragger.dragNode.pWindow;if (_isIE){var div = document.createElement("Div");div.innerHTML=dragger.dragNode.outerHTML;dragger.dragNode=div.childNodes[0]}else
    dragger.dragNode=dragger.dragNode.cloneNode(true);dragger.dragNode.pWindow=window;dragger.gldragNode.old=dragger.dragNode;document.body.appendChild(dragger.dragNode);oldBody.dhtmlDragAndDrop.dragNode=dragger.dragNode};dragger.dragNode.style.left=e.clientX+15+(dragger.fx
    ? dragger.fx*(-1)
    : 0)
    +(document.body.scrollLeft||document.documentElement.scrollLeft)+"px";dragger.dragNode.style.top=e.clientY+3+(dragger.fy
    ? dragger.fy*(-1)
    : 0)
    +(document.body.scrollTop||document.documentElement.scrollTop)+"px";if (!e.srcElement)var z = e.target;else
    z=e.srcElement;dragger.checkLanding(z, e)};dhtmlDragAndDropObject.prototype.calculateFramePosition=function(n){if (window.name){var el = parent.frames[window.name].frameElement.offsetParent;var fx = 0;var fy = 0;while (el){fx+=el.offsetLeft;fy+=el.offsetTop;el=el.offsetParent};if ((parent.dhtmlDragAndDrop)){var ls = parent.dhtmlDragAndDrop.calculateFramePosition(1);fx+=ls.split('_')[0]*1;fy+=ls.split('_')[1]*1};if (n)return fx+"_"+fy;else
    this.fx=fx;this.fy=fy};return "0_0"};dhtmlDragAndDropObject.prototype.checkLanding=function(htmlObject, e){if ((htmlObject)&&(htmlObject.dragLanding)){if (this.lastLanding)this.lastLanding.dragLanding._dragOut(this.lastLanding);this.lastLanding=htmlObject;this.lastLanding=this.lastLanding.dragLanding._dragIn(this.lastLanding, this.dragStartNode, e.clientX,
    e.clientY, e);this.lastLanding_scr=(_isIE ? e.srcElement : e.target)}else {if ((htmlObject)&&(htmlObject.tagName != "BODY"))
    this.checkLanding(htmlObject.parentNode, e);else {if (this.lastLanding)this.lastLanding.dragLanding._dragOut(this.lastLanding, e.clientX, e.clientY, e);this.lastLanding=0;if (this._onNotFound)this._onNotFound()}}};dhtmlDragAndDropObject.prototype.stopDrag=function(e, mode){dragger=window.dhtmlDragAndDrop;if (!mode){dragger.stopFrameRoute();var temp = dragger.lastLanding;dragger.lastLanding=null;if (temp)temp.dragLanding._drag(dragger.dragStartNode, dragger.dragStartObject, temp, (_isIE
    ? event.srcElement
    : e.target))};dragger.lastLanding=null;if ((dragger.dragNode)&&(dragger.dragNode.parentNode == document.body))
    dragger.dragNode.parentNode.removeChild(dragger.dragNode);dragger.dragNode=0;dragger.gldragNode=0;dragger.fx=0;dragger.fy=0;dragger.dragStartNode=0;dragger.dragStartObject=0;document.body.onmouseup=dragger.tempDOMU;document.body.onmousemove=dragger.tempDOMM;dragger.tempDOMU=null;dragger.tempDOMM=null;dragger.waitDrag=0};dhtmlDragAndDropObject.prototype.stopFrameRoute=function(win){if (win)window.dhtmlDragAndDrop.stopDrag(1, 1);for (var i = 0;i < window.frames.length;i++){try{if ((window.frames[i] != win)&&(window.frames[i].dhtmlDragAndDrop))
    window.frames[i].dhtmlDragAndDrop.stopFrameRoute(window)}catch(e){}};try{if ((parent.dhtmlDragAndDrop)&&(parent != window)&&(parent != win))
    parent.dhtmlDragAndDrop.stopFrameRoute(window)}catch(e){}};dhtmlDragAndDropObject.prototype.initFrameRoute=function(win, mode){if (win){window.dhtmlDragAndDrop.preCreateDragCopy();window.dhtmlDragAndDrop.dragStartNode=win.dhtmlDragAndDrop.dragStartNode;window.dhtmlDragAndDrop.dragStartObject=win.dhtmlDragAndDrop.dragStartObject;window.dhtmlDragAndDrop.dragNode=win.dhtmlDragAndDrop.dragNode;window.dhtmlDragAndDrop.gldragNode=win.dhtmlDragAndDrop.dragNode;window.document.body.onmouseup=window.dhtmlDragAndDrop.stopDrag;window.waitDrag=0;if (((!_isIE)&&(mode))&&((!_isFF)||(_FFrv < 1.8)))
    window.dhtmlDragAndDrop.calculateFramePosition()};try{if ((parent.dhtmlDragAndDrop)&&(parent != window)&&(parent != win))
    parent.dhtmlDragAndDrop.initFrameRoute(window)}catch(e){};for (var i = 0;i < window.frames.length;i++){try{if ((window.frames[i] != win)&&(window.frames[i].dhtmlDragAndDrop))
    window.frames[i].dhtmlDragAndDrop.initFrameRoute(window, ((!win||mode) ? 1 : 0))}catch(e){}}};var _isFF = false;var _isIE = false;var _isOpera = false;var _isKHTML = false;var _isMacOS = false;var _isChrome = false;if (navigator.userAgent.indexOf('Macintosh')!= -1)
    _isMacOS=true;if (navigator.userAgent.toLowerCase().indexOf('chrome')>-1)
    _isChrome=true;if ((navigator.userAgent.indexOf('Safari')!= -1)||(navigator.userAgent.indexOf('Konqueror') != -1)){var _KHTMLrv = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf('Safari')+7, 5));if (_KHTMLrv > 525){_isFF=true;var _FFrv = 1.9}else
    _isKHTML=true}else if (navigator.userAgent.indexOf('Opera')!= -1){_isOpera=true;_OperaRv=parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf('Opera')+6, 3))}else if (navigator.appName.indexOf("Microsoft")!= -1){_isIE=true;if (navigator.appVersion.indexOf("MSIE 8.0")!= -1 && document.compatMode != "BackCompat") _isIE=8}else {_isFF=true;var _FFrv = parseFloat(navigator.userAgent.split("rv:")[1])
};dtmlXMLLoaderObject.prototype.doXPath=function(xpathExp, docObj, namespace, result_type){if (_isKHTML || (!_isIE && !window.XPathResult))
    return this.doXPathOpera(xpathExp, docObj);if (_isIE){if (!docObj)if (!this.xmlDoc.nodeName)docObj=this.xmlDoc.responseXML
else
    docObj=this.xmlDoc;if (!docObj)dhtmlxError.throwError("LoadXML", "Incorrect XML", [
    (docObj||this.xmlDoc),
    this.mainObject
]);if (namespace != null)docObj.setProperty("SelectionNamespaces", "xmlns:xsl='"+namespace+"'");if (result_type == 'single'){return docObj.selectSingleNode(xpathExp)}else {return docObj.selectNodes(xpathExp)||new Array(0)}}else {var nodeObj = docObj;if (!docObj){if (!this.xmlDoc.nodeName){docObj=this.xmlDoc.responseXML
}else {docObj=this.xmlDoc}};if (!docObj)dhtmlxError.throwError("LoadXML", "Incorrect XML", [
    (docObj||this.xmlDoc),
    this.mainObject
]);if (docObj.nodeName.indexOf("document")!= -1){nodeObj=docObj}else {nodeObj=docObj;docObj=docObj.ownerDocument};var retType = XPathResult.ANY_TYPE;if (result_type == 'single')retType=XPathResult.FIRST_ORDERED_NODE_TYPE
    var rowsCol = new Array();var col = docObj.evaluate(xpathExp, nodeObj, function(pref){return namespace
    }, retType, null);if (retType == XPathResult.FIRST_ORDERED_NODE_TYPE){return col.singleNodeValue};var thisColMemb = col.iterateNext();while (thisColMemb){rowsCol[rowsCol.length]=thisColMemb;thisColMemb=col.iterateNext()};return rowsCol}};function _dhtmlxError(type, name, params){if (!this.catches)this.catches=new Array();return this};_dhtmlxError.prototype.catchError=function(type, func_name){this.catches[type]=func_name};_dhtmlxError.prototype.throwError=function(type, name, params){if (this.catches[type])return this.catches[type](type, name, params);if (this.catches["ALL"])return this.catches["ALL"](type, name, params);alert("Error type: "+arguments[0]+"\nDescription: "+arguments[1]);return null};window.dhtmlxError=new _dhtmlxError();dtmlXMLLoaderObject.prototype.doXPathOpera=function(xpathExp, docObj){var z = xpathExp.replace(/[\/]+/gi, "/").split('/');var obj = null;var i = 1;if (!z.length)return [];if (z[0] == ".")obj=[docObj];else if (z[0] == ""){obj=(this.xmlDoc.responseXML||this.xmlDoc).getElementsByTagName(z[i].replace(/\[[^\]]*\]/g, ""));i++}else
    return [];for (i;i < z.length;i++)obj=this._getAllNamedChilds(obj, z[i]);if (z[i-1].indexOf("[")!= -1)
    obj=this._filterXPath(obj, z[i-1]);return obj};dtmlXMLLoaderObject.prototype._filterXPath=function(a, b){var c = new Array();var b = b.replace(/[^\[]*\[\@/g, "").replace(/[\[\]\@]*/g, "");for (var i = 0;i < a.length;i++)if (a[i].getAttribute(b))
    c[c.length]=a[i];return c};dtmlXMLLoaderObject.prototype._getAllNamedChilds=function(a, b){var c = new Array();if (_isKHTML)b=b.toUpperCase();for (var i = 0;i < a.length;i++)for (var j = 0;j < a[i].childNodes.length;j++){if (_isKHTML){if (a[i].childNodes[j].tagName&&a[i].childNodes[j].tagName.toUpperCase()== b)
    c[c.length]=a[i].childNodes[j]}else if (a[i].childNodes[j].tagName == b)c[c.length]=a[i].childNodes[j]};return c};function dhtmlXHeir(a, b){for (var c in b)if (typeof (b[c])== "function")
    a[c]=b[c];return a};function dhtmlxEvent(el, event, handler){if (el.addEventListener)el.addEventListener(event, handler, false);else if (el.attachEvent)el.attachEvent("on"+event, handler)};dtmlXMLLoaderObject.prototype.xslDoc=null;dtmlXMLLoaderObject.prototype.setXSLParamValue=function(paramName, paramValue, xslDoc){if (!xslDoc)xslDoc=this.xslDoc

    if (xslDoc.responseXML)xslDoc=xslDoc.responseXML;var item =
        this.doXPath("/xsl:stylesheet/xsl:variable[@name='"+paramName+"']", xslDoc,
            "http:/\/www.w3.org/1999/XSL/Transform", "single");if (item != null)item.firstChild.nodeValue=paramValue
};dtmlXMLLoaderObject.prototype.doXSLTransToObject=function(xslDoc, xmlDoc){if (!xslDoc)xslDoc=this.xslDoc;if (xslDoc.responseXML)xslDoc=xslDoc.responseXML

    if (!xmlDoc)xmlDoc=this.xmlDoc;if (xmlDoc.responseXML)xmlDoc=xmlDoc.responseXML


    if (!_isIE){if (!this.XSLProcessor){this.XSLProcessor=new XSLTProcessor();this.XSLProcessor.importStylesheet(xslDoc)};var result = this.XSLProcessor.transformToDocument(xmlDoc)}else {var result = new ActiveXObject("Msxml2.DOMDocument.3.0");try{xmlDoc.transformNodeToObject(xslDoc, result)}catch(e){result = xmlDoc.transformNode(xslDoc)}};return result};dtmlXMLLoaderObject.prototype.doXSLTransToString=function(xslDoc, xmlDoc){var res = this.doXSLTransToObject(xslDoc, xmlDoc);if(typeof(res)=="string")
    return res;return this.doSerialization(res)};dtmlXMLLoaderObject.prototype.doSerialization=function(xmlDoc){if (!xmlDoc)xmlDoc=this.xmlDoc;if (xmlDoc.responseXML)xmlDoc=xmlDoc.responseXML
    if (!_isIE){var xmlSerializer = new XMLSerializer();return xmlSerializer.serializeToString(xmlDoc)}else
        return xmlDoc.xml};dhtmlxEventable=function(obj){obj.dhx_SeverCatcherPath="";obj.attachEvent=function(name, catcher, callObj){name='ev_'+name.toLowerCase();if (!this[name])this[name]=new this.eventCatcher(callObj||this);return(name+':'+this[name].addEvent(catcher))};obj.callEvent=function(name, arg0){name='ev_'+name.toLowerCase();if (this[name])return this[name].apply(this, arg0);return true};obj.checkEvent=function(name){return (!!this['ev_'+name.toLowerCase()])
};obj.eventCatcher=function(obj){var dhx_catch = [];var z = function(){var res = true;for (var i = 0;i < dhx_catch.length;i++){if (dhx_catch[i] != null){var zr = dhx_catch[i].apply(obj, arguments);res=res&&zr}};return res};z.addEvent=function(ev){if (typeof (ev)!= "function")
    ev=eval(ev);if (ev)return dhx_catch.push(ev)-1;return false};z.removeEvent=function(id){dhx_catch[id]=null};return z};obj.detachEvent=function(id){if (id != false){var list = id.split(':');this[list[0]].removeEvent(list[1])}}};
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */

/*_TOPICS_
 @0:Initialization
 @1:Selection control
 @2:Add/delete
 @3:Reserved
 @4:Methods of Option object
 */

/**
 *  Build combobox from existing select control.
 *
 *
 *  @param   parent      {string} id of existing select control
 *  @param   size      {int } size of control, optional
 *  @return            {object} combobox object
 *  @type   public
 *  @topic   0
 *
 */

function dhtmlXComboFromSelect(parent,size){
    if (typeof(parent)=="string")
        parent=document.getElementById(parent);


    size=size||parent.getAttribute("width")||(window.getComputedStyle?window.getComputedStyle(parent,null)["width"]:(parent.currentStyle?parent.currentStyle["width"]:0));
    if ((!size)||(size=="auto"))
        size=parent.offsetWidth||100;

    var z=document.createElement("SPAN");

    if(parent.style.direction=="rtl") z.style.direction = "rtl";

    parent.parentNode.insertBefore(z,parent);
    parent.style.display='none';

    var s_type = parent.getAttribute('opt_type');

    var w= new dhtmlXCombo(z,parent.name,size,s_type,parent.tabIndex);

    var x=new Array();
    var sel=0;
    for (var i=0; i<parent.options.length; i++){
        if (parent.options[i].selected) sel=i;
        var label=parent.options[i].innerHTML;
        var val=parent.options[i].getAttribute("value");
        if ((typeof(val)=="undefined")||(val===null)) val=label;
        x[i]={value:val,text:label,img_src:parent.options[i].getAttribute("img_src")};
    }

    w.addOption(x);


    parent.parentNode.removeChild(parent);
    w.selectOption(sel,null,true);
    if (parent.onchange)
        w.attachEvent("onChange",parent.onchange);
    return w;
}

var dhtmlXCombo_optionTypes = [];
/**
 *     @desc: build combobox
 *     @param: parent - (string) id of existing object which will be used as container
 *     @param: name - (string) name of combobox - will be used in FORM
 *     @param: width - (int) size of combobox
 *     @param: tabIndex - (int) tab index, optional
 *     @type: public
 *     @topic: 0
 */
function dhtmlXCombo(parent,name,width,optionType,tabIndex){
    if (typeof(parent)=="string")
        parent=document.getElementById(parent);

    this.dhx_Event();
    this.optionType = (optionType != window.undefined && dhtmlXCombo_optionTypes[optionType]) ? optionType : 'default';
    this._optionObject = dhtmlXCombo_optionTypes[this.optionType];

    this._disabled = false;

    if(parent.style.direction == "rtl") this.rtl = true;
    else this.rtl = false;

    if (!window.dhx_glbSelectAr){
        window.dhx_glbSelectAr=new Array();
        window.dhx_openedSelect=null;
        window.dhx_SelectId=1;
        dhtmlxEvent(document.body,"click",this.closeAll);
        dhtmlxEvent(document.body,"keydown",function(e){ try { if ((e||event).keyCode==9)  window.dhx_glbSelectAr[0].closeAll(); } catch(e) {} return true; } );
    }

    if (parent.tagName=="SELECT")
        return dhtmlXComboFromSelect(parent);
    else
        this._createSelf(parent,name,width,tabIndex);
    dhx_glbSelectAr.push(this);
}

/**
 *     @desc: change control size
 *     @param: new_size - (int) new size value
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo.prototype.setSize = function(new_size){
    this.DOMlist.style.width=new_size+"px";
    if (this.DOMlistF) this.DOMlistF.style.width=new_size+"px";
    this.DOMelem.style.width=new_size+"px";
    this.DOMelem_input.style.width = Math.max(0,(new_size-19))+'px';
}
/**
 *     @desc: switch between combobox and auto-filter modes
 *     @param: mode - (boolean) enable filtering mode
 *     @param: url - (string) url for filtering from XML, optional
 *     @param: cache - (boolean) XML caching, optional
 *     @param: autosubload - (boolean) enable auto load additional suggestions on selecting last loaded option
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo.prototype.enableFilteringMode = function(mode,url,cache,autosubload){
    this._filter=convertStringToBoolean(mode);

    if (url){
        this._xml=url;
        this._autoxml=convertStringToBoolean(autosubload);
    }
    if (convertStringToBoolean(cache)) this._xmlCache=[];
    //this.DOMelem_button.style.display=(this._filter?"none":"");
}

dhtmlXCombo.prototype.setFilteringParam=function(name,value){
    if (!this._prs) this._prs=[];
    this._prs.push([name,value]);
}
/**
 *     @desc: disable combobox
 *     @param: mode - (boolean) disable combobox
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.disable = function(mode){
    var z=convertStringToBoolean(mode);
    if (this._disabled==z) return;
    this.DOMelem_input.disabled=z;
    this._disabled=z;
}
/**
 *     @desc: switch to readonly mode
 *     @param: mode - (boolean) readonly mode
 *     @param: autosearch - (boolean) true by default
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.readonly = function(mode,autosearch){
    this.DOMelem_input.readOnly=mode ? true : false;
    if(autosearch===false || mode===false){
        this.DOMelem.onkeyup=function(ev){ }
    } else {
        var that = this;
        this.DOMelem.onkeyup=function(ev){
            ev=ev||window.event;
            if (ev.keyCode!=9) ev.cancelBubble=true;
            if((ev.keyCode >= 48 && ev.keyCode <= 57)||(ev.keyCode >= 65 && ev.keyCode <= 90)){
                for(var i=0; i<that.optionsArr.length; i++){
                    var text = that.optionsArr[i].text;
                    if(text.toString().toUpperCase().indexOf(String.fromCharCode(ev.keyCode)) == 0){
                        that.selectOption(i);
                        break;
                    }
                }
                ev.cancelBubble=true;
            }
        }
    }
}

/**
 *     @desc: get Option by value
 *     @param: value - (string) value of option in question
 *     @type: public
 *     @return: option object
 *     @topic: 2
 */
dhtmlXCombo.prototype.getOption = function(value)
{
    for(var i=0; i<this.optionsArr.length; i++)
        if(this.optionsArr[i].value==value)
            return this.optionsArr[i];
    return null;
}
/**
 *     @desc: get Option by label
 *     @param: label - (string) label of option in question
 *     @type: public
 *     @return: option object
 *     @topic: 2
 */
dhtmlXCombo.prototype.getOptionByLabel = function(value)
{
    //value=value.replace(/&/g,"&amp;")
    for(var i=0; i<this.optionsArr.length; i++)
        if(this.optionsArr[i].text==value || this.optionsArr[i]._ctext==value)
            return this.optionsArr[i];
    return null;
}
/**
 *     @desc: get Option by index
 *     @param: ind - (int) index of option in question
 *     @type: public
 *     @return: option object
 *     @topic: 2
 */
dhtmlXCombo.prototype.getOptionByIndex = function(ind){
    return this.optionsArr[ind];
}
/**
 *     @desc: clear all options from combobox
 *     @type: public
 *     @param: value - (bool) clear current value as well
 *     @topic: 2
 */
dhtmlXCombo.prototype.clearAll = function(all)
{
    if (all) this.setComboText("");
    this.optionsArr=new Array();
    this.redrawOptions();
    if (all) this._confirmSelection();
}
/**
 *     @desc: delete option by value
 *     @param: value - (string) value of option in question
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.deleteOption = function(value)
{
    var ind=this.getIndexByValue(value);
    if(ind<0) return;
    if (this.optionsArr[ind]==this._selOption) this._selOption=null;
    this.optionsArr.splice(ind, 1);
    this.redrawOptions();
}

/**
 *     @desc: enable/disable immideatly rendering after changes in combobox
 *     @param: mode - (boolean) enable/disable
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.render=function(mode){
    this._skiprender=(!convertStringToBoolean(mode));
    this.redrawOptions();
}

/**
 *     @desc: update option in combobox
 *     @param: oldvalue - (string) index of option in question
 *     @param: avalue - (variable)
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.updateOption = function(oldvalue, avalue, atext, acss)
{
    var dOpt=this.getOption(oldvalue);
    if (typeof(avalue)!="object") avalue={text:atext,value:avalue,css:acss};
    dOpt.setValue(avalue);
    this.redrawOptions();
}
/**
 *     @desc: add new option
 *     @param: value - (variable) - different input for different kinds of options - please refer to examples
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.addOption = function(options)
{
    if (!arguments[0].length || typeof(arguments[0])!="object")
        args = [arguments];
    else
        args = options;

    this.render(false);
    for (var i=0; i<args.length; i++) {
        var attr = args[i];
        if (attr.length){
            attr.value = attr[0]||"";
            attr.text = attr[1]||"";
            attr.css = attr[2]||"";
        }
        this._addOption(attr);
    }
    this.render(true);
}

dhtmlXCombo.prototype._addOption = function(attr)
{
    dOpt = new this._optionObject();
    this.optionsArr.push(dOpt);
    dOpt.setValue.apply(dOpt,[attr]);
    this.redrawOptions();
}


/**
 *     @desc: return index of item by value
 *     @param: value - (string) value of option in question
 *     @type: public
 *     @return: option index
 *     @topic: 2
 */
dhtmlXCombo.prototype.getIndexByValue = function(val){
    for(var i=0; i<this.optionsArr.length; i++)
        if(this.optionsArr[i].value == val) return i;
    return -1;
}

/**
 *     @desc: get value of selected item
 *     @type: public
 *     @return: option value
 *     @topic: 2
 */
dhtmlXCombo.prototype.getSelectedValue = function(){
    return (this._selOption?this._selOption.value:null);
}
/**
 *     @desc: get current text in combobox
 *     @type: public
 *     @return: combobox text
 *     @topic: 2
 */
dhtmlXCombo.prototype.getComboText = function(){
    return this.DOMelem_input.value;
}
/**
 *     @desc: set text in covmbobox
 *     @param: text - (string) new text label
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.setComboText = function(text){
    this.DOMelem_input.value=text;
}

/**
 *     @desc: set text in covmbobox
 *     @param: text - (string) new text label
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.setComboValue = function(text){
    this.setComboText(text);
    for(var i=0; i<this.optionsArr.length; i++)
        if (this.optionsArr[i].data()[0]==text)
            return this.selectOption(i,null,true);
    this.DOMelem_hidden_input.value=text;
}

/**
 *     @desc: get value which will be sent with form
 *     @type: public
 *     @return: combobox value
 *     @topic: 2
 */
dhtmlXCombo.prototype.getActualValue = function(){
    return this.DOMelem_hidden_input.value;
}
/**
 *     @desc: get text of selected option
 *     @type: public
 *     @return: text of option
 *     @topic: 2
 */
dhtmlXCombo.prototype.getSelectedText = function(){
    return (this._selOption?this._selOption.text:"");
}
/**
 *     @desc: get index of selected option
 *     @type: public
 *     @return: option index
 *     @topic: 2
 */
dhtmlXCombo.prototype.getSelectedIndex = function(){
    for(var i=0; i<this.optionsArr.length; i++)
        if(this.optionsArr[i] == this._selOption) return i;
    return -1;
}
/**
 *     @desc: set name used while form submit
 *     @param: name - (string) new combobox name
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.setName = function(name){
    this.DOMelem_hidden_input.name = name;
    this.DOMelem_hidden_input2 = name.replace(/(\]?)$/, "_new_value$1");

    this.name = name;
}
/**
 *     @desc: show combox ( reversion to hide command )
 *     @param: mode - (boolean) enable/disable
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.show = function(mode){
    if (convertStringToBoolean(mode))
        this.DOMelem.style.display = "";
    else
        this.DOMelem.style.display = "none";
}

/**
 *     @desc: destroy object and any related HTML elements
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo.prototype.destructor = function()
{
    var _sID = this._inID;
    this.DOMParent.removeChild(this.DOMelem);
    this.DOMlist.parentNode.removeChild(this.DOMlist);
    var s=dhx_glbSelectAr;
    this.DOMParent=this.DOMlist=this.DOMelem=0;
    this.DOMlist.combo=this.DOMelem.combo=0;
    for(var i=0; i<s.length; i++)
    {
        if(s[i]._inID == _sID)
        {
            s[i] = null;
            s.splice(i,1);
            return;
        }
    }
}

/**
 *     @desc: create self HTML
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._createSelf = function(selParent, name, width, tab)
{
    if (width.toString().indexOf("%")!=-1){
        var self = this;
        var resWidht=parseInt(width)/100;
        window.setInterval(function(){
            if (!selParent.parentNode) return;
            var ts=selParent.parentNode.offsetWidth*resWidht-2;
            if (ts<0) return;
            if (ts==self._lastTs) return;
            self.setSize(self._lastTs=ts);},500);
        var width=parseInt(selParent.offsetWidth); //mm
    }

    var width=parseInt(width||100); //mm
    this.ListPosition = "Bottom"; //set optionlist positioning
    this.DOMParent = selParent;
    this._inID = null;
    this.name = name;

    this._selOption = null; //selected option object pointer
    this.optionsArr = Array();

    var opt = new this._optionObject();
    opt.DrawHeader(this,name, width,tab);
    //HTML select part 2 - options list DIV element
    this.DOMlist = document.createElement("DIV");
    this.DOMlist.className = 'dhx_combo_list'+(this.rtl?"_rtl":"")+' '+(dhtmlx.skin?dhtmlx.skin+"_list":"");
    this.DOMlist.style.width=width-(_isIE?0:0)+"px";
    if (_isOpera || _isKHTML )
        this.DOMlist.style.overflow="auto";
    this.DOMlist.style.display = "none";
    document.body.insertBefore(this.DOMlist,document.body.firstChild);
    if (_isIE )    {
        this.DOMlistF = document.createElement("IFRAME");
        this.DOMlistF.style.border="0px";
        this.DOMlistF.className = 'dhx_combo_list';
        this.DOMlistF.style.width=width-(_isIE?0:0)+"px";
        this.DOMlistF.style.display = "none";
        this.DOMlistF.src="javascript:false;";
        document.body.insertBefore(this.DOMlistF,document.body.firstChild);
    }



    this.DOMlist.combo=this.DOMelem.combo=this;

    this.DOMelem_input.onkeydown = this._onKey;
    this.DOMelem_input.onkeypress = this._onKeyF;
    this.DOMelem_input.onblur = this._onBlur;
    this.DOMelem.onclick = this._toggleSelect;
    this.DOMlist.onclick = this._selectOption;
    this.DOMlist.onmousedown = function(){
        this._skipBlur=true;
    }

    this.DOMlist.onkeydown = function(e){
        this.combo.DOMelem_input.focus();
        (e||event).cancelBubble=true;
        this.combo.DOMelem_input.onkeydown(e)
    }
    this.DOMlist.onmouseover = this._listOver;

}

dhtmlXCombo.prototype._listOver = function(e)
{
    e = e||event;
    e.cancelBubble = true;
    var node = (_isIE?event.srcElement:e.target);
    var that = this.combo;
    if ( node.parentNode == that.DOMlist ) {
        if(that._selOption) that._selOption.deselect();
        if(that._tempSel) that._tempSel.deselect();

        var i=0;
        for (i; i<that.DOMlist.childNodes.length; i++) {
            if (that.DOMlist.childNodes[i]==node) break;
        }
        var z=that.optionsArr[i];
        that._tempSel=z;
        that._tempSel.select();

        if ((that._autoxml)&&((i+1)==that._lastLength)){
            that._fetchOptions(i+1,that._lasttext||"");
        }
    }

}

/**
 *     @desc: place option list in necessary place on screen
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._positList = function()
{
    //if(this.ListAutoPosit) this.enableOptionAutoPositioning(true); //mm auto posit
    var pos=this.getPosition(this.DOMelem);
    if(this.ListPosition == 'Bottom'){
        this.DOMlist.style.top = pos[1]+this.DOMelem.offsetHeight-1+"px";
        this.DOMlist.style.left = pos[0]+"px";
    }
    else if(this.ListPosition == 'Top'){ //mm
        this.DOMlist.style.top = pos[1] - this.DOMlist.offsetHeight+"px";

        this.DOMlist.style.left = pos[0]+"px"; //mm
    }
    else{
        this.DOMlist.style.top = pos[1]+"px";
        this.DOMlist.style.left = pos[0]+this.DOMelem.offsetWidth+"px";
    }

}

dhtmlXCombo.prototype.getPosition = function(oNode,pNode){
    if (_isChrome){
        if(!pNode)
            var pNode =document.body;
        var divD=0;
        var divs=document.getElementsByClassName("datagrid_panel");
        if(divs) for (var i=0;i<divs.length;i++) if(divs[i].id=="")
        { var divD=   divs[i];
            divD.addEventListener("scroll",this.closeAll);}

        var a = oNode;
        var flag=0;
        var els = [];
        var offsetparents=[];
        while (a) {
            els.unshift(a);
            a = a.parentNode;
        }
        for(var i=0;i<els.length;i++) if(els[i].className=='datagrid_panel') flag=1;

        var oCurrentNode=oNode;
        var iLeft=0;
        var iTop=0;
        var scrLeft=0;
        var scrTop=0;

        if(divD&& flag==1) {scrLeft=divD.scrollLeft;scrTop=divD.scrollTop;}
        else {scrLeft=oCurrentNode.scrollLeft;scrTop=oCurrentNode.scrollTop;}

        while ((oCurrentNode)&&(oCurrentNode!=pNode)){
            iLeft+=oCurrentNode.offsetLeft-scrLeft;
            iTop+=oCurrentNode.offsetTop-scrTop;
            oCurrentNode=oCurrentNode.offsetParent;
            offsetparents.unshift(oCurrentNode);
        }
        if(divD) { iLeft+=(offsetparents.length-1)*scrLeft;
            iTop+=(offsetparents.length-1)*scrTop; }

        if (pNode == document.body ){
            if (_isIE && _isIE<8){

                if (document.documentElement.scrollTop)
                    iTop+=document.documentElement.scrollTop;
                if (document.documentElement.scrollLeft)
                    iLeft+=document.documentElement.scrollLeft;
            }
            else
            if (!_isFF){
                iLeft+=document.body.offsetLeft;
                iTop+=document.body.offsetTop;
            }
        }
        return new Array(iLeft,iTop);
    }
    var pos = getOffset(oNode);
    return [pos.left, pos.top];

}
/**
 *     @desc: correct current selection ( move it to first visible option )
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype._correctSelection = function(){
    if (this.getComboText()!="")
        for (var i=0; i<this.optionsArr.length; i++)
            if (!this.optionsArr[i].isHidden()){
                return this.selectOption(i,true,false);
            }
    this.unSelectOption();
}
/**
 *     @desc: select next option in combobox
 *     @param: step - (int) step size
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype.selectNext = function(step){
    var z=this.getSelectedIndex()+step;
    while (this.optionsArr[z]){
        if (!this.optionsArr[z].isHidden())
            return this.selectOption(z,false,false);
        z+=step;
    }
}
/**
 *     @desc: on keypressed handler
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._onKeyF = function(e){
    var that=this.parentNode.combo;
    var ev=e||event;
    ev.cancelBubble=true;
    if (ev.keyCode=="13" || ev.keyCode=="9" ){
        that._confirmSelection();
        that.closeAll();
    } else
    if (ev.keyCode=="27" ){
        that._resetSelection();
        that.closeAll();
    } else that._activeMode=true;
    if (ev.keyCode=="13" || ev.keyCode=="27" ){ //enter
        that.callEvent("onKeyPressed",[ev.keyCode])
        return false;
    }
    return true;
}
/**
 *     @desc: on keyup handler
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._onKey = function(e){
    var that=this.parentNode.combo;
    (e||event).cancelBubble=true;
    var ev=(e||event).keyCode;
    if (ev>15 && ev<19) return true; //shift,alt,ctrl
    if (ev==27) return;
    if ((that.DOMlist.style.display!="block")&&(ev!="13")&&(ev!="9")&&((!that._filter)||(that._filterAny)))
        that.DOMelem.onclick(e||event);

    if ((ev!="13")&&(ev!="9")){
        window.setTimeout(function(){ that._onKeyB(ev); },1);
        if (ev=="40" || ev=="38")
            return false;
    }
    else if (ev==9){
        that.closeAll();
        (e||event).cancelBubble=false;
    }
}
dhtmlXCombo.prototype._onKeyB = function(ev)
{
    if (ev=="40"){  //down
        var z=this.selectNext(1);
    } else if (ev=="38"){ //up
        this.selectNext(-1);
    } else{
        this.callEvent("onKeyPressed",[ev])
        if (this._filter) return this.filterSelf((ev==8)||(ev==46));
        for(var i=0; i<this.optionsArr.length; i++)
            if (this.optionsArr[i].data()[1]==this.DOMelem_input.value){
//                  ev.cancelBubble=true;
                this.selectOption(i,false,false);
                return false;
            }
        this.unSelectOption();
    }
    return true;
}


/**
 *     @desc: on data change handler
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._onBlur = function()
{
    var self = this.parentNode._self;
    window.setTimeout(function(){
        if (self.DOMlist._skipBlur) return !(self.DOMlist._skipBlur=false);
        self._confirmSelection();
        self.callEvent("onBlur",[]);
    },100)

}
/**
 *     @desc: redraw combobox options
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype.redrawOptions = function(){
    if (this._skiprender) return;
    for(var i=this.DOMlist.childNodes.length-1; i>=0; i--)
        this.DOMlist.removeChild(this.DOMlist.childNodes[i]);
    for(var i=0; i<this.optionsArr.length; i++)
        this.DOMlist.appendChild(this.optionsArr[i].render());





}
/**
 *     @desc: load list of options from XML
 *     @param: url - (string) xml url
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo.prototype.loadXML = function(url,afterCall){
    this._load=true;
    this.callEvent("onXLS",[]);
    if (this._prs)
        for (var i=0; i<this._prs.length; i++)
            url+=[getUrlSymbol(url),escape(this._prs[i][0]),"=",escape(this._prs[i][1])].join("");
    if ((this._xmlCache)&&(this._xmlCache[url])){
        this._fillFromXML(this,null,null,null,this._xmlCache[url]);
        if (afterCall) afterCall();
    }
    else{
        var xml=(new dtmlXMLLoaderObject(this._fillFromXML,this,true,true));
        if (afterCall) xml.waitCall=afterCall;
        xml._cPath=url;
        xml.loadXML(url);
    }
}

/**
 *     @desc: load list of options from XML string
 *     @param: astring - (string) xml string
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo.prototype.loadXMLString = function(astring){
    var xml=(new dtmlXMLLoaderObject(this._fillFromXML,this,true,true));
    xml.loadXMLString(astring);
}

/**
 *     @desc: on XML load handler
 *     @type: private
 *     @topic: 0
 */
dhtmlXCombo.prototype._fillFromXML = function(obj,b,c,d,xml){
    if (obj._xmlCache) obj._xmlCache[xml._cPath]=xml;

    //check that XML is correct
    var toptag=xml.getXMLTopNode("complete");
    if (toptag.tagName!="complete") return;
    var top=xml.doXPath("//complete");
    var options=xml.doXPath("//option");

    var add = false;

    obj.render(false);
    if ((!top[0])||(!top[0].getAttribute("add"))){
        obj.clearAll();
        obj._lastLength=options.length;
        if (obj._xml){
            if ((!options) || (!options.length))
                obj.closeAll();
            else {
                if (obj._activeMode){
                    obj._positList();
                    obj.DOMlist.style.display="block";
                    if (_isIE) obj._IEFix(true);
                }
            }}
    } else {
        obj._lastLength+=options.length;
        add = true;
    }

    for (var i=0; i<options.length; i++) {
        var attr = new Object();
        attr.text = options[i].firstChild?options[i].firstChild.nodeValue:"";
        for (var j=0; j<options[i].attributes.length; j++) {
            var a = options[i].attributes[j];
            if (a)
                attr[a.nodeName] = a.nodeValue;
        }
        obj._addOption(attr);
    }
    obj.render(add!=true || (!!options.length));

    if ((obj._load)&&(obj._load!==true))
        obj.loadXML(obj._load);
    else{
        obj._load=false;
        if ((!obj._lkmode)&&(!obj._filter))
            obj._correctSelection();
    }

    var selected=xml.doXPath("//option[@selected]");
    if (selected.length)
        obj.selectOption(obj.getIndexByValue(selected[0].getAttribute("value")),false,true);

    obj.callEvent("onXLE",[]);

}
/**
 *     @desc: deselect option
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.unSelectOption = function(){
    if (this._selOption) this._selOption.deselect();
    if(this._tempSel) this._tempSel.deselect();
    this._tempSel=this._selOption=null;
}

dhtmlXCombo.prototype._confirmSelection = function(data,status){
    if(arguments.length==0){
        var z=this.getOptionByLabel(this.DOMelem_input.value);
        data = z?z.value:this.DOMelem_input.value;
        status = (z==null);
        if (data==this.getActualValue()) return;
    }

    this.DOMelem_hidden_input.value=data;
    this.DOMelem_hidden_input2.value = (status?"true":"false");
    this.callEvent("onChange",[]);
    this._activeMode=false;
}
dhtmlXCombo.prototype._resetSelection = function(data,status){
    var z=this.getOption(this.DOMelem_hidden_input.value);
    this.setComboValue(z?z.data()[0]:this.DOMelem_hidden_input.value)
    this.setComboText(z?z.data()[1]:this.DOMelem_hidden_input.value)
}

/**
 *     @desc: select option
 *     @param: ind - (int) index of option in question
 *     @param: filter - (boolean) enable autocomplit range, optional
 *     @param: conf - (boolean) true for real selection, false for pre-selection
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.selectOption = function(ind,filter,conf){
    if (arguments.length<3) conf=true;
    this.unSelectOption();
    var z=this.optionsArr[ind];
    if (!z)  return;
    this._selOption=z;
    this._selOption.select();

    var corr=this._selOption.content.offsetTop+this._selOption.content.offsetHeight-this.DOMlist.scrollTop-this.DOMlist.offsetHeight;
    if (corr>0) this.DOMlist.scrollTop+=corr;
    corr=this.DOMlist.scrollTop-this._selOption.content.offsetTop;
    if (corr>0) this.DOMlist.scrollTop-=corr;
    var data=this._selOption.data();

    if (conf){
        this.setComboText(data[1]);
        this._confirmSelection(data[0],false);
    }

    if ((this._autoxml)&&((ind+1)==this._lastLength))
        this._fetchOptions(ind+1,this._lasttext||"");

    if (filter){
        var text=this.getComboText();
        if (text!=data[1]){
            this.setComboText(data[1]);
            dhtmlXRange(this.DOMelem_input,text.length+1,data[1].length);
        }
    }
    else
        this.setComboText(data[1]);
    this._selOption.RedrawHeader(this);

    /*Event*/
    this.callEvent("onSelectionChange",[]);
}
/**
 *     @desc: option on select handler
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype._selectOption = function(e)
{
    (e||event).cancelBubble = true;
    var node=(_isIE?event.srcElement:e.target);
    var that=this.combo;
    while (!node._self) {
        node = node.parentNode;
        if (!node)
            return;
    }

    var i=0;
    for (i; i<that.DOMlist.childNodes.length; i++) {
        if (that.DOMlist.childNodes[i]==node) break;
    }
    that.selectOption(i,false,true);
    that.closeAll();
    that.callEvent("onBlur",[])
    that._activeMode=false;
}
/**
 *     @desc: open list of options
 *     @type: public
 *     @topic: 2
 */
dhtmlXCombo.prototype.openSelect = function(){



    if (this._disabled) return;
    this.closeAll();
    this._positList();
    this.DOMlist.style.display="block";
    this.callEvent("onOpen",[]);
    if(this._tempSel) this._tempSel.deselect();
    if(this._selOption) this._selOption.select();
    if(this._selOption){
        var corr=this._selOption.content.offsetTop+this._selOption.content.offsetHeight-this.DOMlist.scrollTop-this.DOMlist.offsetHeight;
        if (corr>0) this.DOMlist.scrollTop+=corr;
        corr=this.DOMlist.scrollTop-this._selOption.content.offsetTop;
        if (corr>0) this.DOMlist.scrollTop-=corr;
    }
    /* if (this.autoOptionSize){
     var x=this.DOMlist.offsetWidth;

     for ( var i=0; i<this.optionsArr.length; i++){
     if(i==0) alert("this.DOMlist.childNodes[i].scrollWidth ="+ this.DOMlist.childNodes[i].scrollWidth + "> x= "+ x);
     if (this.DOMlist.childNodes[i].scrollWidth > x)
     x=this.DOMlist.childNodes[i].scrollWidth;
     }

     this.DOMlist.style.width=x+"px";
     }*/


    if (_isIE) this._IEFix(true);
    this.DOMelem_input.focus();

    if (this._filter) this.filterSelf();
}
/**
 *     @desc: open(close) list
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype._toggleSelect = function(e)
{
    var that=this.combo;
    if ( that.DOMlist.style.display == "block" ) {
        that.closeAll();
    } else {
        that.openSelect();
    }
    (e||event).cancelBubble = true;
}

dhtmlXCombo.prototype._fetchOptions=function(ind,text){
    if (text=="") { this.closeAll();  return this.clearAll();   }
    var url=this._xml+((this._xml.indexOf("?")!=-1)?"&":"?")+"pos="+ind+"&mask="+encodeURIComponent(text);
    this._lasttext=text;
    if (this._load) this._load=url;
    else {
        if (!this.callEvent("onDynXLS",[text,ind])) return;
        this.loadXML(url);
    }
}
/**
 *     @desc: filter list of options
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype.filterSelf = function(mode)
{
    var text=this.getComboText();
    if (this._xml){
        this._lkmode=mode;
        return this._fetchOptions(0,text);
    }
    try{ var filter=new RegExp("^"+text,"i"); } catch (e){ var filter=new RegExp("^"+text.replace(/([\[\]\{\}\(\)\+\*\\])/g,"\\$1")); }
    this.filterAny=false;
    for(var i=0; i<this.optionsArr.length; i++){
        var z=filter.test(this.optionsArr[i].text);
        this.filterAny|=z;
        this.optionsArr[i].hide(!z);
    }
    if (!this.filterAny) {
        this.closeAll();
        this._activeMode=true;
    }
    else {
        if (this.DOMlist.style.display!="block")
            this.openSelect();
        if (_isIE) this._IEFix(true);
    }

    if (!mode)
        this._correctSelection();
    else this.unSelectOption();
}




/**
 *     @desc: set hidden iframe for IE
 *     @type: private
 *     @topic: 2
 */
dhtmlXCombo.prototype._IEFix = function(mode){
    this.DOMlistF.style.display=(mode?"block":"none");
    this.DOMlistF.style.top=this.DOMlist.style.top;
    this.DOMlistF.style.left=this.DOMlist.style.left;
}
/**
 *     @desc: close opened combobox list
 *     @type: public
 *     @topic: 1
 */
dhtmlXCombo.prototype.closeAll = function()
{
    if(window.dhx_glbSelectAr)
        for (var i=0; i<dhx_glbSelectAr.length; i++){
            if (dhx_glbSelectAr[i].DOMlist.style.display=="block") {
                dhx_glbSelectAr[i].DOMlist.style.display = "none";
                if (_isIE) dhx_glbSelectAr[i]._IEFix(false);
            }
            dhx_glbSelectAr[i]._activeMode=false;
        }
}


/**
 *     @desc: create selection range in input control
 *     @param: InputId - (string) id of input ( object can be used as well )
 *     @param: Start - (int) start selection position
 *     @param: End - (int) end selection position
 *     @type: public
 *     @topic: 0
 */
function dhtmlXRange(InputId, Start, End)
{
    var Input = typeof(InputId)=='object' ? InputId : document.getElementById(InputId);
    try{    Input.focus();   } catch(e){};
    var Length = Input.value.length;
    Start--;
    if (Start < 0 || Start > End || Start > Length)
        Start = 0;
    if (End > Length)
        End = Length;
    if (Start==End) return;
    if (Input.setSelectionRange) {
        Input.setSelectionRange(Start, End);
    } else if (Input.createTextRange) {
        var range = Input.createTextRange();
        range.moveStart('character', Start);
        range.moveEnd('character', End-Length);
        range.select();
    }
}
/**
 *     @desc: combobox option object constructor
 *     @type: public
 *     @topic: 0
 */
dhtmlXCombo_defaultOption = function(){
    this.init();
}
/**
 *     @desc: option initialization function
 *     @type: private
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.init = function(){
    this.value = null;
    this.text = "";
    this.selected = false;
    this.css = "";
}
/**
 *     @desc: mark option as selected
 *     @type: public
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.select = function(){
    if (this.content){
        this.content.className="dhx_selected_option"+(dhtmlx.skin?" combo_"+dhtmlx.skin+"_sel":"");
    }
}
/**
 *     @desc: hide option
 *     @param: mode - (boolean)
 *     @type: public
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.hide = function(mode){
    this.render().style.display=mode?"none":"";
}
/**
 *     @desc: return hide state of option
 *     @type: public
 *     @return: hide state of option
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.isHidden = function(){
    return (this.render().style.display=="none");
}
/**
 *     @desc: mark option as not selected
 *     @type: public
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.deselect = function(){
    if (this.content) this.render();
    this.content.className="";
}
/**
 *     @desc: set value of option
 *     @param: value - (string) value
 *     @param: text - (string) text
 *     @param: css - (string) css style string
 *     @type: public
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.setValue = function(attr){
    this.value = attr.value||"";
    this.text = attr.text||"";
    this.css = attr.css||"";
    this.content=null;
}


/**
 *     @desc: render option
 *     @type: private
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.render = function(){
    if (!this.content){
        this.content=document.createElement("DIV");
        this.content._self = this;

        this.content.style.cssText='width:100%; overflow:hidden;'+this.css;
        if (_isOpera || _isKHTML ) this.content.style.padding="2px 0px 2px 0px";
        this.content.innerHTML=this.text;
        this._ctext=_isIE?this.content.innerText:this.content.textContent;
    }
    return this.content;
}
/**
 *     @desc: return option data
 *     @type: public
 *     @return: array of data related to option
 *     @topic: 4
 */
dhtmlXCombo_defaultOption.prototype.data = function(){
    if (this.content)
        return [this.value,this._ctext ? this._ctext : this.text];
}

dhtmlXCombo_defaultOption.prototype.DrawHeader = function(self, name, width, tab)
{
    var z=document.createElement("DIV");
    z.style.width = width+"px";
    z.className = 'dhx_combo_box '+(dhtmlx.skin||"");
    z._self = self;
    self.DOMelem = z;
    this._DrawHeaderInput(self, name, width,tab);
    this._DrawHeaderButton(self, name, width);
    self.DOMParent.appendChild(self.DOMelem);
}

dhtmlXCombo_defaultOption.prototype._DrawHeaderInput = function(self, name, width,tab)
{

    if(self.rtl && _isIE)  {
        var z=document.createElement('textarea');
        z.style.overflow = "hidden";
        z.style.whiteSpace="nowrap"
    }
    else {
        var z=document.createElement('input');
        z.setAttribute("autocomplete","off");
        z.type = 'text';
    }
    z.className = 'dhx_combo_input';

    if(self.rtl) {
        z.style.left = "18px";
        z.style.direction = "rtl";
        z.style.unicodeBidi = "bidi-override";
    }


    if (tab) z.tabIndex=tab;
    z.style.width = (width-19)+'px';
    self.DOMelem.appendChild(z);
    self.DOMelem_input = z;

    z = document.createElement('input');
    z.type = 'hidden';
    z.name = name;
    self.DOMelem.appendChild(z);
    self.DOMelem_hidden_input = z;

    z = document.createElement('input');
    z.type = 'hidden';
    z.name = (name||"").replace(/(\]?)$/, "_new_value$1");
    z.value="true";
    self.DOMelem.appendChild(z);
    self.DOMelem_hidden_input2 = z;
}

dhtmlXCombo_defaultOption.prototype._DrawHeaderButton = function(self, name, width)
{
    var z=document.createElement('img');
    z.className = (self.rtl)?'dhx_combo_img_rtl':'dhx_combo_img';
    if(dhtmlx.image_path) dhx_globalImgPath = dhtmlx.image_path;
    z.src = (window.dhx_globalImgPath?dhx_globalImgPath:"")+'combo_select'+(dhtmlx.skin?"_"+dhtmlx.skin:"")+'.gif';
    self.DOMelem.appendChild(z);
    self.DOMelem_button=z;
}

dhtmlXCombo_defaultOption.prototype.RedrawHeader = function(self)
{
}


dhtmlXCombo_optionTypes['default'] = dhtmlXCombo_defaultOption;

dhtmlXCombo.prototype.dhx_Event=function()
{
    this.dhx_SeverCatcherPath="";

    this.attachEvent = function(original, catcher, CallObj)
    {
        CallObj = CallObj||this;
        original = 'ev_'+original;
        if ( ( !this[original] ) || ( !this[original].addEvent ) ) {
            var z = new this.eventCatcher(CallObj);
            z.addEvent( this[original] );
            this[original] = z;
        }
        return ( original + ':' + this[original].addEvent(catcher) );   //return ID (event name & event ID)
    }
    this.callEvent=function(name,arg0){
        if (this["ev_"+name]) return this["ev_"+name].apply(this,arg0);
        return true;
    }
    this.checkEvent=function(name){
        if (this["ev_"+name]) return true;
        return false;
    }

    this.eventCatcher = function(obj)
    {
        var dhx_catch = new Array();
        var m_obj = obj;
        var func_server = function(catcher,rpc)
        {
            catcher = catcher.split(":");
            var postVar="";
            var postVar2="";
            var target=catcher[1];
            if (catcher[1]=="rpc"){
                postVar='<?xml version="1.0"?><methodCall><methodName>'+catcher[2]+'</methodName><params>';
                postVar2="</params></methodCall>";
                target=rpc;
            }
            var z = function() {
            }
            return z;
        }
        var z = function()
        {
            if (dhx_catch)
                var res=true;
            for (var i=0; i<dhx_catch.length; i++) {
                if (dhx_catch[i] != null) {
                    var zr = dhx_catch[i].apply( m_obj, arguments );
                    res = res && zr;
                }
            }
            return res;
        }
        z.addEvent = function(ev)
        {
            if ( typeof(ev) != "function" )
                if (ev && ev.indexOf && ev.indexOf("server:") == 0)
                    ev = new func_server(ev,m_obj.rpcServer);
                else
                    ev = eval(ev);
            if (ev)
                return dhx_catch.push( ev ) - 1;
            return false;
        }
        z.removeEvent = function(id)
        {
            dhx_catch[id] = null;
        }
        return z;
    }

    this.detachEvent = function(id)
    {
        if (id != false) {
            var list = id.split(':');            //get EventName and ID
            this[ list[0] ].removeEvent( list[1] );   //remove event
        }
    }
};


//combo
(function(){
    dhtmlx.extend_api("dhtmlXCombo",{
        _init:function(obj){
            if (obj.image_path)
                dhx_globalImgPath=obj.image_path;
            return [obj.parent, obj.name, (obj.width||"100%"), obj.type, obj.index ];
        },
        filter:"filter_command",
        auto_height:"enableOptionAutoHeight",
        auto_position:"enableOptionAutoPositioning",
        auto_width:"enableOptionAutoWidth",
        xml:"loadXML",
        readonly:"readonly",
        items:"addOption"
    },{
        filter_command:function(data){
            if (typeof data == "string")
                this.enableFilteringMode(true,data);
            else
                this.enableFilteringMode(data);
        }
    });
})();


//(c)dhtmlx ltd. www.dhtmlx.com

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */

dhtmlXCombo_imageOption = function(){this.init()};dhtmlXCombo_imageOption.prototype = new dhtmlXCombo_defaultOption;dhtmlXCombo_imageOption.prototype.setValue = function(attr){this.value = attr.value||"";this.text = attr.text||"";this.css = attr.css||"";this.img_src = attr.img_src||this.getDefImage()};dhtmlXCombo_imageOption.prototype.render = function(){if (!this.content){this.content=document.createElement("DIV");this.content._self = this;this.content.style.cssText='width:100%;overflow:hidden;'+this.css;var html = '';if (this.img_src != '')html += '<img style="float:left;" src="'+this.img_src+'" />';html += '<div style="float:left">'+this.text+'</div>';this.content.innerHTML=html};return this.content};dhtmlXCombo_imageOption.prototype.data = function(){return [this.value,this.text,this.img_src]};dhtmlXCombo_imageOption.prototype.DrawHeader = function(self, name, width)
{var z=document.createElement("DIV");z.style.width = width+"px";z.className = 'dhx_combo_box';z._self = self;self.DOMelem = z;this._DrawHeaderImage(self, name, width);this._DrawHeaderInput(self, name, width-23);this._DrawHeaderButton(self, name, width);self.DOMParent.appendChild(self.DOMelem)};dhtmlXCombo_imageOption.prototype._DrawHeaderImage = function(self, name, width)
{var z= document.createElement('img');z.className = (self.rtl)? 'dhx_combo_option_img_rtl':'dhx_combo_option_img';z.style.visibility = 'hidden';self.DOMelem.appendChild(z);self.DOMelem_image=z};dhtmlXCombo_imageOption.prototype.RedrawHeader = function(self)
{self.DOMelem_image.style.visibility = 'visible';self.DOMelem_image.src = this.img_src};dhtmlXCombo_imageOption.prototype.getDefImage = function(self){return ""};dhtmlXCombo.prototype.setDefaultImage=function(url){dhtmlXCombo_imageOption.prototype.getDefImage=function(){return url}};dhtmlXCombo_optionTypes['image'] = dhtmlXCombo_imageOption;dhtmlXCombo_checkboxOption = function(){this.init()};dhtmlXCombo_checkboxOption.prototype = new dhtmlXCombo_defaultOption;dhtmlXCombo_checkboxOption.prototype.setValue = function(attr){this.value = attr.value||"";this.text = attr.text||"";this.css = attr.css||"";this.checked = attr.checked||0};dhtmlXCombo_checkboxOption.prototype.render = function(){if (!this.content){this.content=document.createElement("DIV");this.content._self = this;this.content.style.cssText='width:100%;overflow:hidden;'+this.css;var html = '';if(this.checked)html += '<input style="float:left;" type="checkbox" checked />';else html += '<input style="float:left;" type="checkbox" />';html += '<div style="float:left">'+this.text+'</div>';this.content.innerHTML=html;this.content.firstChild.onclick = function(e) {this.parentNode.parentNode.combo.DOMelem_input.focus();(e||event).cancelBubble=true;if(!this.parentNode.parentNode.combo.callEvent("onCheck",[this.parentNode._self.value,this.checked])){this.checked=!this.checked;return false}}};return this.content};dhtmlXCombo_checkboxOption.prototype.data = function(){return [this.value,this.text,this.render().firstChild.checked]};dhtmlXCombo_checkboxOption.prototype.DrawHeader = function(self, name, width)
{self.DOMelem = document.createElement("DIV");self.DOMelem.style.width = width+"px";self.DOMelem.className = 'dhx_combo_box';self.DOMelem._self = self;this._DrawHeaderCheckbox(self, name, width);this._DrawHeaderInput(self, name, width-18);this._DrawHeaderButton(self, name, width);self.DOMParent.appendChild(self.DOMelem)};dhtmlXCombo_checkboxOption.prototype._DrawHeaderCheckbox = function(self, name, width)
{var z= document.createElement('input');z.type='checkbox';z.className = (self.rtl)? 'dhx_combo_option_img_rtl':'dhx_combo_option_img';z.style.visibility = 'hidden';z.onclick = function(e) {(e||event).cancelBubble=true};self.DOMelem.appendChild(z);self.DOMelem_checkbox = z};dhtmlXCombo_checkboxOption.prototype.RedrawHeader = function(self)
{self.DOMelem_checkbox.style.visibility = '';self.DOMelem_checkbox.checked = this.content.firstChild.checked};dhtmlXCombo_optionTypes['checkbox'] = dhtmlXCombo_checkboxOption;dhtmlXCombo.prototype.getChecked=function(){var res=[];for(var i=0;i<this.optionsArr.length;i++)if(this.optionsArr[i].data()[2])
    res.push(this.optionsArr[i].value)
    return res};dhtmlXCombo.prototype.setChecked=function(index,mode){this.optionsArr[index].content.firstChild.checked=(!(mode===false))};dhtmlXCombo.prototype.setCheckedByValue=function(value,mode){return this.setChecked(this.getIndexByValue(value),mode)};
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
dhtmlXCombo.prototype.attachChildCombo = function(_chcombo,xml){if(!this._child_combos){this._child_combos = []};this._has_childen = 1;this._child_combos[this._child_combos.length] = _chcombo;_chcombo.show(0);var that = this;var _arg_length = arguments.length;this.attachEvent("onChange",function(){for(var i = 0;i < that._child_combos.length;i++){if(that._child_combos[i]==_chcombo){_chcombo.show(1);_chcombo.callEvent("onMasterChange",[that.getActualValue(),that])}};if(that.getActualValue()=="") {that.showSubCombo(that,0);return};if(_chcombo._xml){if(_arg_length ==1)xml = _chcombo._xml;_chcombo._xml = that.deleteParentVariable(xml);_chcombo._xml += ((_chcombo._xml.indexOf("?")!=-1)?"&":"?")+"parent="+that.getActualValue()}else{if(xml){_chcombo.clearAll(true);_chcombo.loadXML(xml+((xml.indexOf("?")!=-1)?"&":"?")+"parent="+that.getActualValue())}}})
};dhtmlXCombo.prototype.setAutoSubCombo = function(xml,name){if(arguments.length == 1)name = "subcombo";if(!this._parentCombo){var z = new dhtmlXCombo(this.DOMParent,name,this.DOMelem.style.width)
    z._parentCombo = this}else {var z = new dhtmlXCombo(this._parentCombo.DOMParent,name,this._parentCombo.DOMelem.style.width)
    z._parentCombo = this._parentCombo};if(this._filter)z._filter = 1;if(this._xml){if(arguments.length > 0)z._xml = xml;else
    z._xml = this._xml;xml = z._xml;z._autoxml = this._autoxml;if(this._xmlCache)z._xmlCache=[]};this.attachChildCombo(z,xml)
    return z};dhtmlXCombo.prototype.detachChildCombo = function(_chcombo){for(var i = 0;i < this._child_combos.length;i++){this._child_combos[i] == _chcombo;this._child_combos.splice(i,1)};_chcombo.show(1)};dhtmlXCombo.prototype.showSubCombo = function(combo,state){if(combo._child_combos){for(var i = 0;i < combo._child_combos.length;i++){combo._child_combos[i].show(state);combo.showSubCombo(combo._child_combos[i],0)}}};dhtmlXCombo.prototype.deleteParentVariable = function(str){str = str.replace(/parent\=[^&]*/g,"").replace(/\?\&/,"?");return str};
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */

dhtmlXCombo.prototype.enableOptionAutoPositioning = function(fl){if(!this.ListAutoPosit)this.ListAutoPosit = 1;this.attachEvent("onOpen",function(){this._setOptionAutoPositioning(fl)})
    this.attachEvent("onXLE",function(){this._setOptionAutoPositioning(fl)})

};dhtmlXCombo.prototype._setOptionAutoPositioning = function(fl){if((typeof(fl)!="undefined")&&(!convertStringToBoolean(fl))){this.ListPosition = "Bottom";this.ListAutoPosit = 0;return true
};var pos = this.getPosition(this.DOMelem);var bottom = this._getClientHeight() - pos[1] - this.DOMelem.offsetHeight;var height = (this.autoHeight)?(this.DOMlist.scrollHeight):parseInt(this.DOMlist.offsetHeight);if((bottom < height)&&(pos[1] > height)){this.ListPosition = "Top"}else this.ListPosition = "Bottom";this._positList()};dhtmlXCombo.prototype._getClientHeight = function(){return ((document.compatMode=='CSS1Compat') &&(!window.opera))?document.documentElement.clientHeight:document.body.clientHeight};dhtmlXCombo.prototype.setOptionWidth = function(width){if(arguments.length > 0){this.DOMlist.style.width = width+"px";if (this.DOMlistF)this.DOMlistF.style.width = width+"px"}};dhtmlXCombo.prototype.setOptionHeight = function(height){if(arguments.length>0){if(_isIE)this.DOMlist.style.height = this.DOMlistF.style.height = height+"px";else
    this.DOMlist.style.height = height+"px";this._positList()}};dhtmlXCombo.prototype.enableOptionAutoWidth = function(fl){if(!this._listWidthConf)this._listWidthConf = parseInt(this.DOMlist.style.width);if(arguments.length == 0){var fl = 1};if(convertStringToBoolean(fl)) {this.autoOptionWidth = 1;this.awOnOpen = this.attachEvent("onOpen",function(){this._setOptionAutoWidth()});this.awOnXLE = this.attachEvent("onXLE",function(){this._setOptionAutoWidth()})}else {if(typeof(this.awOnOpen)!= "undefined"){this.autoOptionWidth = 0;this.detachEvent(this.awOnOpen);this.detachEvent(this.awOnXLE);this.setOptionWidth(this._listWidthConf)}}};dhtmlXCombo.prototype._setOptionAutoWidth = function(){this.setOptionWidth(1);var x = this.DOMlist.offsetWidth;for ( var i=0;i<this.optionsArr.length;i++){var optWidth = (_isFF)?(this.DOMlist.childNodes[i].scrollWidth - 2):this.DOMlist.childNodes[i].scrollWidth;if (optWidth > x){x = this.DOMlist.childNodes[i].scrollWidth}};this.setOptionWidth(x)};dhtmlXCombo.prototype.enableOptionAutoHeight = function(fl,maxHeight){if(!this._listHeightConf)this._listHeightConf = (this.DOMlist.style.height=="")?100:parseInt(this.DOMlist.style.height);if(arguments.length==0)var fl = 1;this.autoHeight = convertStringToBoolean(fl);if(this.autoHeight){this.ahOnOpen = this.attachEvent("onOpen",function(){this._setOptionAutoHeight(fl,maxHeight);if(_isIE)this._setOptionAutoHeight(fl,maxHeight)})
    if(!this.awOnOpen)this.ahOnXLE = this.attachEvent("onXLE",function(){var that = this;window.setTimeout(function(){that.callEvent("onOpen",[])},1)})
}else {if(typeof(this.ahOnOpen)!= "undefined"){this.detachEvent(this.ahOnOpen);this.detachEvent(this.ahOnXLE);this.setOptionHeight(this._listHeightConf)}}};dhtmlXCombo.prototype._setOptionAutoHeight = function(fl,maxHeight){if(convertStringToBoolean(fl)){this.setOptionHeight(1);var height = 0;if (this.optionsArr.length > 0){if(this.DOMlist.scrollHeight > this.DOMlist.offsetHeight){height= this.DOMlist.scrollHeight + 2}else height= this.DOMlist.offsetHeight;if((arguments.length > 1)&&(maxHeight)){var maxHeight = parseInt(maxHeight);height = (height>maxHeight)?maxHeight:height};this.setOptionHeight(height)
}}};
//v.2.5 build 91111

/*
 Copyright DHTMLX LTD. http://www.dhtmlx.com
 You allowed to use this component or parts of it under GPL terms
 To use it on other terms or get Professional edition of the component please contact us at sales@dhtmlx.com
 */