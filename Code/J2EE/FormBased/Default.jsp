<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>JSP Form based Example</title>
        <!-- ../assets/ui/css/ ../assets/ui/js/ ../assets/ui/images/ -->
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
    </head>
	<BODY>
	
	<%
	/*
	In this example, we first present a form to the user, to input data.
	For a demo, we present a very simple form intended for a Restaurant to indicate
	sales of its various product categories at lunch time (for a week). 
	The form is rendered in this page (Default.jsp). It submits its data to
	Chart.jsp. In Chart.jsp, we retrieve this data, convert into XML and then
	render the chart.
	*/
	
	//So, basically this page is just a form. 
	%>
	   <div id="wrapper">

            <div id="header">
               

                <div class="logo"><a class="imagelink" href="http://www.fusioncharts.com" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">JSP Form Examples</h1>
            </div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">JSP Form-Based Data Example</h2>

                    <div class="gen-chart-render">
	
		
			<p class='text'>Please enter how many items of each category you
			sold within this week. We'll plot this data on a Pie chart.</p>
			<p class='text'>To keep things simple, we're not validating for
			non-numeric data here. So, please enter valid numeric values only. In
			your real-world applications, you can put your own validators.</p>
			<FORM NAME='SalesForm' ACTION='Chart.jsp' METHOD='POST'>
			<table width='50%' align='center' cellpadding='2' cellspacing='1'
				border='0' class='text'>
				<tr>
					<td width='50%' align='right'><B>Soups:</B> &nbsp;</td>
					<td width='50%'><input type='text' size='5' name='Soups'
						value='108'> bowls</td>
				</tr>
				<tr>
					<td width='50%' align='right'><B>Salads:</B> &nbsp;</td>
					<td width='50%'><input type='text' size='5' name='Salads'
						value='162'> plates</td>
				</tr>
				<tr>
					<td width='50%' align='right'><B>Sandwiches:</B> &nbsp;</td>
					<td width='50%'><input type='text' size='5' name='Sandwiches'
						value='360'> pieces</td>
				</tr>
				<tr>
					<td width='50%' align='right'><B>Beverages:</B> &nbsp;</td>
					<td width='50%'><input type='text' size='5' name='Beverages'
						value='171'> cans</td>
				</tr>
				<tr>
					<td width='50%' align='right'><B>Desserts:</B> &nbsp;</td>
					<td width='50%'><input type='text' size='5' name='Desserts'
						value='99'> plates</td>
				</tr>
				<tr>
					<td width='50%' align='right'>&nbsp;</td>
					<td width='50%'><input type='submit' value='Chart it!'></td>
				</tr>
				<table>
			</FORM>
		

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
