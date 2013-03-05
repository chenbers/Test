<?php

require_once('FusionCharts_Gen.php');


class Charts{

	function FusionCharts()
	{
	}
	
	function makeBar($val, $IDSuffix)
	{
		//---------- Creating First Chart ----------------------------------------------
		# Create FusionCharts PHP object
		$FC = new FusionCharts("Column3D","300","250", "myChartId".$IDSuffix);
		# set the relative path of the swf file
		$FC->setSWFPath("../../FusionCharts/");

		# Define chart attributes#  Set chart attributes
		$strParam="caption=Weekly Sales;subcaption=$val;xAxisName=Week;yAxisName=Revenue;numberPrefix=$";
		# Setting chart attributes
		$FC->setChartParams($strParam);

		# add chart values and category names for the First Chart
		$FC->addChartData("40800","Label=Week 1");
		$FC->addChartData("31400","Label=Week 2");
		$FC->addChartData("26700","Label=Week 3");
		$FC->addChartData("54400","Label=Week 4");
		//-------------------------------------------------------------------
		$FC->renderChart();
	}

}

















?>