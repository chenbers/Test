package com.inthinc.pro.backing.ui;

public class ScoreBox 
{
	private Integer score; // value is 0 to 50 -- will be displayed divided by 10
	private String scoreStyle;
	private String size;
	
	// TODO: Is this correct? or is it 0-.9, 1 - 1.9, etc)
	// Color boxes define in PRD
	//0-1   red
	//1.1-2 orange
	//2.1-3 yellow
	//3.1-4 blue
	//4.1-5 green
	
	public ScoreBox(Integer score, ScoreBoxSizes _size)
	{
		this.size = _size.toString();
		this.score = score;
        calculateScoreColor();
		
	}
	
	private void calculateScoreColor()
	{
		//Determine CSS Style
		if( score <= 10){
			setScoreStyle("score_" + size + "_1");
		}
		else if(score <= 20) {
			setScoreStyle("score_" + size + "_2");
		}
		else if(score <= 30) {
			setScoreStyle("score_" + size + "_3");
		}
		else if(score <= 40) {
			setScoreStyle("score_" + size + "_4");
		}
		else {
			setScoreStyle("score_" + size + "_5");
		}
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
		calculateScoreColor();
	}

	public String getScoreStyle() {
		
		return scoreStyle;
	}

	public void setScoreStyle(String scoreStyle) {
		this.scoreStyle = scoreStyle;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}


