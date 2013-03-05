<?php
//We've included ../Includes/FusionCharts_Gen.php, which contains FusionCharts PHP Class
//to help us easily embed the charts.
include("../Includes/FusionCharts_Gen.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <TITLE>
	FusionCharts XT - Array Example using Combination Column 3D Line Chart
        </TITLE>
        <?php
        ?>
        <SCRIPT LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></SCRIPT>

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
        <?php
       
        ?>
    </head>
    <BODY>

        <div id="wrapper">

            <div id="header">
                

               <div class="logo"><a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">PHP Class Examples</h1>
</div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">Plotting Combination chart from data contained in Array</h2>

                    <div class="gen-chart-render">

                        <CENTER>
                            <?php
                            //In this example, using FusionCharts PHP Class
                            //we plot a Combination chart from data contained in arrays.


                            /* The arrays need to be of the following  structure :

		1. Array to store Category Namesnames :

		  A single dimensional array storing the category names

		2. A 2 Dimensional Array to store data values

			** Each row will store data for 1 dataset

		 Column 1 will store : Dataset Series Name.
		 Column 2 will store : Dataset attributes like parentYAxis=s etc.
			 (as list separated by delimiter.)
		 Column 3 and rest will store : values of the dataset

                            */

                            //Store Quarter Name
                            $arrDataCat[0] = "Quarter 1";
                            $arrDataCat[1] = "Quarter 2";
                            $arrDataCat[2] = "Quarter 3";
                            $arrDataCat[3] = "Quarter 4";

                            //Store Revenue Data
                            $arrData[0][0] = "Revenue";
                            $arrData[0][1] = "showValues=0;"; // Dataset Parameters
                            $arrData[0][2] = 576000;
                            $arrData[0][3] = 448000;
                            $arrData[0][4] = 956000;
                            $arrData[0][5] = 734000;

                            //Store Quantity Data
                            $arrData[1][0] = "Quantity";
                            $arrData[1][1] = "parentYAxis=S"; // Dataset Parameters
                            $arrData[1][2] = 576;
                            $arrData[1][3] = 448;
                            $arrData[1][4] = 956;
                            $arrData[1][5] = 734;

                            # Create combination chart object
                            $FC = new FusionCharts("MSColumn3DLineDY","600","300");

                            # Set Relative Path of swf file.
                            $FC->setSWFPath("../../FusionCharts/");

                            # Define chart attributes
                            $strParam="caption=Product A - Sales Details;PYAxisName=Revenue;SYAxisName=Quantity (in Units);numberPrefix=$;sNumberSuffix= unit";

                            # Set chart attributes
                            $FC->setChartParams($strParam);


                            # Pass the 2 arrays storing data and category names to
                            # FusionCharts PHP Class function addChartDataFromArray
                            $FC->addChartDataFromArray($arrData, $arrDataCat);

                            # Render the chart
                            $FC->renderChart();

                            ?>
                        </CENTER>
                        
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
    </BODY>
</HTML>
