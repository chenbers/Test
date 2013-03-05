<?php
//We've included ../Includes/FusionCharts.php, which contains functions
//to help us easily embed the charts.
include("../Includes/FusionCharts.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>
		FusionCharts - Simple Column 2D Chart - With Multilingual characters
        </title>
        <?php
       
        ?>
        <script type="text/javascript" LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></script>

        <!--[if IE 6]>
        <script type="text/javascript" src="../assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>

<script>
          /* select the element name, css selector, background etc */
          DD_belatedPNG.fix('img');

          /* string argument can be any CSS selector */
        </script>
        <![endif]-->

        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
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
    </head>
    <body>
        <div id="wrapper">

            <div id="header">
              <div class="logo"><a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">PHP Class Examples</h1>
</div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">Example using multilingual text from database</h2>

                    <div class="gen-chart-render">

                        <center>
                            <?php

                            //This page demonstrates the ease of generating charts using FusionCharts.
                            //For this chart, we've retrieved multilingual text from database
                            //through a XML relayer script file Data/getXMLFromDatabase.php
                            //The file Data/getXMLFromDatabase.php is already ANSI encoded without BOM
                            //We add the BOM in the file using script

                            //Create the chart - Column 2D Chart
                            echo renderChart("../../FusionCharts/Column2D.swf", "Data/getXMLFromDatabase_writeBOM.php", "", "myFirst", 500, 400, false, false);
                            ?>
                        </center>

                    </div>
                    <div class="clear"></div>
                    <p>&nbsp;</p>
                    <p class="small">  </p>

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
    </body>
</html>
