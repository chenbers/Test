<?php
require_once('ed.php');
$oChart = new Charts();
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
    "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <title>Enterprise Dashboard</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
        <script src="../../FusionCharts/FusionCharts.js" type="text/javascript"></script>

		<style type="text/css">
		  body
			{
			background-color: #FFFF99;
			}
		  #charts
			{
			display: block;
			width: 400px;
			background-color: #ffaaaa;
			margin: 10px auto;
			}
		  #charts div
			{
			display: inline-block;
			}
		</style>
	
    </head>
    <body>

		<div id='charts'>
		<?php
		$oChart->makeBar(91, 0);
		?>
		</div>

		<div id='charts'>
		<?php
		$oChart->makeBar(91, 1);
		?>
		</div>

    </body>
</html>
