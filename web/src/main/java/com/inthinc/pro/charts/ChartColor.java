package com.inthinc.pro.charts;


// these match the colors in the score legend

public enum ChartColor {
	
	GREEN("6b9d1b"),
	BLUE("1e88c8"),
	YELLOW("f6b305"),
	ORANGE("ff6601"),
	RED("ff0101"),
	DARK_GREEN("006600"),
	DARK_BLUE("000066"),
	DARK_YELLOW("ffff00"),
	DARK_ORANGE("ff6600"),
	DARK_RED("660000"),
	GRAY("cccccc");
	
	String rgb;
	ChartColor(String rgb)
	{
		this.rgb = rgb;
	}
	
	public String toString()
	{
		return rgb;
	}
}
