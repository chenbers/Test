<?php
//We've included ../Includes/FusionCharts_Gen.php, which contains
//FusionCharts PHP Class to help us easily embed charts
//We've also used ../Includes/DBConn.php to easily connect to a database
include("../Includes/FusionCharts_Gen.php");
include("../Includes/DBConn.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title> </title>
        <title>
	FusionCharts XT - Database and Drill-Down Example
        </title>
        <?php
        ?>
        <script LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></script>

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
                    <h2 class="headline">Detailed report for the factory</h2>

                    <div class="gen-chart-render">

                        <center>
                            <?php
                            //This page is invoked from Default.php. When the user clicks on a pie
                            //slice in Default.php, the factory Id is passed to this page. We need
                            //to get that factory id, get information from database and then show
                            //a detailed chart.

                            //Request the factory Id from Querystring
                            $FactoryId = $_REQUEST['FactoryId'];

                            # Create a column 3d chart object
                            $FC = new FusionCharts("Column2D","600","300");

                            # Set Relative Path of swf file.
                            $FC->setSWFPath("../../FusionCharts/");

                            // Store Chart attributes in a variable
                            $strParam="caption=Factory " . $FactoryId . " Output;subcaption=(In Units);xAxisName=Date; formatNumberScale=0;decimals=0;rotateLabels=1;slantLabels=1";

                            #  Set chart attributes
                            $FC->setChartParams($strParam);

                            // Connet to the DataBase
                            $link = connectToDB();

                            //Now, we get the data for that factory
                            //storing chart values in 'Quantity' column and category names in 'DDate'
                            $strQuery = "select Quantity, DATE_FORMAT(DatePro,'%m/%d/%Y') as DDate from Factory_Output where FactoryId=" . $FactoryId;
                            $result = mysql_query($strQuery) or die(mysql_error());

                            //Pass the SQL query result to the FusionCharts PHP Class' function
                            //that will extract data from database and add to the chart.
                            if ($result) {
                                $FC->addDataFromDatabase($result, "Quantity", "DDate");
                            }
                            mysql_close($link);

                            //Create the chart
                            $FC->renderChart();
                            ?>
                            <br/>
                                <a href="Default.php?animate=0" style="margin-left: 360px;" class="qua qua-button"><span>Back to Summary</span></a>
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
