package com.inthinc.pro.backing.ui;

public class ScoreBox 
{
	private Double score;
	private String scoreStyle;
	private String size;
	
	// Color boxes define in PRD
	//0-1   red
	//1.1-2 orange
	//2.1-3 yellow
	//3.1-4 blue
	//4.1-5 green
	
	public ScoreBox(double _score, ScoreBoxSizes _size)
	{
		this.size = _size.toString();
		
		setScore(_score);
		
		
	}
	
	private void CalculateScoreColor()
	{
		//Determine CSS Style
		if(		this.score <= 1.0){
			setScoreStyle("score_" + size + "_1");
		}
		else if(this.score  > 1.0 && this.score <= 1.9) {
			setScoreStyle("score_" + size + "_2");
		}
		else if(this.score >= 2.0 && this.score <= 2.9) {
			setScoreStyle("score_" + size + "_3");
		}
		else if(this.score >= 3.0 && this.score <= 3.9) {
			setScoreStyle("score_" + size + "_4");
		}
		else {
			setScoreStyle("score_" + size + "_5");
		}
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
		CalculateScoreColor();
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


