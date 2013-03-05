﻿<%@ Page Language="VB" AutoEventWireup="true" CodeFile="TransparentChart.aspx.vb"
    Inherits="BasicExample_TransparentChart" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
 <title>FusionCharts XT ASP.NET(VB) Samples</title>
    <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="../assets/ui/js/jquery.min.js"></script>

    <script type="text/javascript" src="../assets/ui/js/lib.js"></script>
    <script type="text/javascript" src="../FusionCharts/FusionCharts.js"></script> 

    <!--[if IE 6]>
        <script type="text/javascript" src="../assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>

<script>
          /* select the element name, css selector, background etc */
          DD_belatedPNG.fix('img');

          /* string argument can be any CSS selector */
        </script>
        <![endif]-->
    <style type="text/css">
        h2.headline
        {
            font: normal 110%/137.5% "Trebuchet MS" , Arial, Helvetica, sans-serif;
            padding: 0;
            margin: 25px 0 25px 0;
            color: #7d7c8b;
            text-align: center;
        }
        p.small
        {
            font: normal 68.75%/150% Verdana, Geneva, sans-serif;
            color: #919191;
            padding: 0;
            margin: 0 auto;
            width: 664px;
            text-align: center;
        }
    </style>
</head>
<body style="background-color: #E3DCFB">
    <form id="form1" runat="server">
    <div id="wrapper">
        <div id="header">
            
            <div class="logo">
                <a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank">
                    <img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75"
                        alt="FusionCharts XT logo" /></a></div>
            <h1 class="brand-name">FusionCharts XT</h1>
            <h1 class="logo-text">
                FusionCharts - ASP.NET(VB) Samples</h1>
        </div>
        <div class="content-area">
            <div id="content-area-inner-main">
                <h2 class="headline">
                    Creating Transparent chart</h2>
                <center>
                <div class="gen-chart-render" style="background-color:#9d7fbd; border:1px solid #745C92; width: 700px; ">
                 <center><asp:Literal ID="Literal1" runat="server"></asp:Literal></center>
                </div>
                </center>
                <div class="clear">
                </div>
                <p>&nbsp;
                    </p>
                <p class="small"></p>
                <div class="underline-dull">
                </div>
            </div>
        </div>
        <div id="footer">
            <ul>
                <li><a href="../Default.aspx"><span>&laquo; Back to list of examples</span></a></li>
                <li cl ass="pipe">|</li>
                <li><a href="../NoChart.html"><span>Unable to see the chart above?</span></a></li>
            </ul>
        </div>
    </div>
    </form>
</body>
</html>
