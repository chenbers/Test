<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
'We've included ../Includes/FusionCharts.asp, which contains functions
'to help us easily embed the charts.
%>
<!-- #INCLUDE FILE="../Includes/FusionCharts.asp" -->
<HTML>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>FusionCharts - Transparent chart</title>
        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../assets/ui/js/jquery.min.js"></script>
        <script type="text/javascript" src="../assets/ui/js/lib.js"></script>
        <!--[if IE 6]>
        <script type="text/javascript" src="../assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>

<script>
          /* select the element name, css selector, background etc */
          DD_belatedPNG.fix('img');

          /* string argument can be any CSS selector */
        </script>
        <![endif]-->

        <style type="text/css">
            h2.headline {
                font: normal 110%/137.5% "Trebuchet MS", Arial, Helvetica, sans-serif;
                padding: 0;
                margin: 25px 0 25px 0;
                color: #7d7c8b;
                text-align: center;
            }
            p.small {
                font: normal 68.75%/150% Verdana, Geneva, sans-serif;
                color: #919191;
                padding: 0;
                margin: 0 auto;
                width: 664px;
                text-align: center;
            }
        </style>
       
        <SCRIPT LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></SCRIPT>

    </head>
    <BODY>

        <div id="wrapper">

            <div id="header">
               

               <div class="logo"><a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">PHP Basic Examples</h1>
            </div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">Creating Transparent chart</h2>

                    <div class="gen-chart-render">
                        <%

                        'This page demonstrates how you can show transparent charts on a page
                        'For this example, all the charts use the pre-built TransparentData.xml (contained in /Data/ folder)
                        'However, you can very easily change the data source for any chart.

                        'IMPORTANT NOTE: Each chart necessarily needs to have a unique ID on the page.
                        'If you do not provide a unique Id, only the last chart might be visible.
                        'Here, we've used the ID chart1, chart2 and chart3 for the 3 charts on page.

						'Set the chart mode opaque. This would affect all the charts
						'Rendered after this decalration.
						Call SetTransparentChart(True)
		 
                        'Create the chart - Column 3D Chart setting transparency to true
                        Call renderChart("../../FusionCharts/Column3D.swf", "Data/Data_Transparent.xml", "", "chart1", 600, 300, false, true)
                        %>
                        <BR><BR>
                        <%
                        'Now, create a Column 3D Chart again without transparency
						'Set the chart mode opaque. This would affect all the charts
						'Rendered after this decalration.
						Call SetTransparentChart(False)
                        Call renderChart("../../FusionCharts/Column2D.swf", "Data/Data_Transparent.xml", "", "chart2", 600, 300, false, true)
                        %>
                    </div>
                    <div class="clear"></div>
                    <p>&nbsp;</p>
                    <p class="small"> <!--<p class="small">This dashboard was created using FusionCharts XT, FusionWidgets v3 and FusionMaps v3 You are free to reproduce and distribute this dashboard in its original form, without changing any content, whatsoever. <br />
            &copy; All Rights Reserved</p>
          <p>&nbsp;</p>-->
                    </p>

                    <div class="underline-dull"></div>
                </div>
            </div>

            <div id="footer">
                <ul>
                    <li><a href="../index.html"><span>&laquo; Back to list of examples</span></a></li>
                    <li class="pipe">|</li>
                    <li><a href="../NoChart.html"><span>Unable to see the chart above?</span></a></li>
                </ul>
            </div>
        </div>
    </BODY>
</HTML>
