package com.inthinc.pro.backing.ui;

public class ScoreBox 
{
	private Integer score; // value is 0 to 50 -- will be displayed divided by 10
	private String scoreStyle;
	private String size;

	
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
	
	
	private void calculateScoreColor()  //To be removed.
	{
	    //setScoreStyle(this.GetStyleFromScore(this.score, this.size));
	    
		if(score == null || score < 0)
		{
		    setScoreStyle("score_" + size + "_na");
		    return;
		}
	    
	    //Determine CSS Style
		if( score <= 10 && score >= 0){
			setScoreStyle("score_" + size + "_1");
		}
		else if(score <= 20 && score >= 0) {
			setScoreStyle("score_" + size + "_2");
		}
		else if(score <= 30 && score >= 0) {
			setScoreStyle("score_" + size + "_3");
		}
		else if(score <= 40 && score >= 0) {
			setScoreStyle("score_" + size + "_4");
		}
		else {
			setScoreStyle("score_" + size + "_5");
		}   
	}
	
	public static String GetStyleFromScore(Integer score, ScoreBoxSizes size)
	{
	    String style;
	    
       if(score == null || score < 0)
        {
           style = "score_" + size + "_na";
            return style;
        }
	    
	    //Determine CSS Style
        if( score <= 10 && score >= 0){
            style = "score_" + size.toString() + "_1";
        }
        else if(score <= 20 && score >= 0) {
            style = "score_" + size.toString() + "_2";
        }
        else if(score <= 30 && score >= 0) {
            style = "score_" + size.toString() + "_3";
        }
        else if(score <= 40 && score >= 0) {
            style = "score_" + size.toString() + "_4";
        }
        else {
            style = "score_" + size.toString() + "_5";
        }
        
        return style;
	}
	
	public Integer getScoreNow() {
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


