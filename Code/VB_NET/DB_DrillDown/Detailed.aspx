<%@ Page Language="VB" AutoEventWireup="false" CodeFile="Detailed.aspx.vb" Inherits="DBExample_Detailed" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <TITLE>FusionCharts XT - ASP.NET(VB) Samples
        </TITLE>
        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
    <script language="Javascript" src="../FusionCharts/FusionCharts.js"></script>

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
    <form id='form1' name='form1' method='post' runat="server">
        <div id="wrapper">

            <div id="header">
                

               <div class="logo"><a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">FusionCharts Drilldown and Database</h1>
            </div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">Click on any pie slice to clice it out right click to enable rotation mode.</h2>

                    <div class="gen-chart-render">
                    
                        <asp:Literal ID="Literal2" runat="server"></asp:Literal>
                        <br />
                        <a class="qua qua-button" style="margin-left: 360px" href="Default.aspx?animate=0"><span>Back to Summary</span></a>
                        <CENTER>
    
            </div>
                    <div class="clear"></div>
                    <p>&nbsp;</p>
                    <p class="small"> </p>

                    <div class="underline-dull"></div>
                </div>
            </div>

            <div id="footer">
                <ul>
                    <li><a href="../Default.aspx"><span>&laquo; Back to list of examples</span></a></li>
                    <li class="pipe">|</li>
                    <li><a href="../NoChart.html"><span>Unable to see the chart above?</span></a></li>
                </ul>
            </div>
        </div>
    </form>
</body>
</html>
