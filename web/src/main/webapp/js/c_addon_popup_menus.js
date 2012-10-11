/*
========================================
 Popup Menus v1.0
 Add-on for SmartMenus v6.0+
========================================
 (c)2007 ET VADIKOM-VASIL DINKOV
========================================
*/


// ===
function c_show(m,e){if(typeof c_dl=="undefined"||!c_dl)return;u=c_gO(m);if(!u||u.IN!=2)return;if(!u.PP){alert('ERROR\n\nSmartMenus 6 Popup Menus Add-on:\n\nYou are calling the "'+m+'" menu, which is not set as a popup menu in the config file.\nThe c_show() function can only be used to show menus that have "Position" set to \'popup\'.');return}c_mV();if(u.style.display=="block")return;c_hD();c_S[1]=u;var S,w,h,x,y,mouseX,mouseY,t,targetX,targetY,targetW,targetH,menuW,menuH,C,c;S=u.style;if(!u.FM){S.visibility="hidden";u.FM=1}S.display="block";w=u.offsetWidth;h=u.offsetHeight;c=c_gW();mouseX=e.pageX||e.clientX+c.x-(c_rL()?c_dE.offsetWidth-c.w:0);mouseY=e.pageY||e.clientY+c.y;t=e.target||e.srcElement;while(t.nodeType!=1)t=t.parentNode;C=c_cA(t);targetX=C.x;targetY=C.y;targetW=t.offsetWidth;targetH=t.offsetHeight;menuW=u.offsetWidth;menuH=u.offsetHeight;x=!arguments[2]?mouseX:eval(arguments[2]);y=!arguments[3]?mouseY:eval(arguments[3]);if(c_r&&x<c.x)x=c.x;else if(x+w>c.x+c.w)x=c.x+c.w-w;if(h<c.h&&y+h>c.y+c.h)y=c.y+c.h-h;else if(h>=c.h||y<c.y)y=c.y;S.right="auto";S.left=x+"px";S.top=y+"px";if(c_F[0])c_iF(u,w,h,x,y);if(c_F[1])c_hS();c_sH(u)};function c_hide(){if(typeof c_dl=="undefined"||!c_dl)return;c_mU()};function c_oF(){c_mV();c_c=this;if(!c_gL(c_c).parentNode.PP)c_sM(1)}