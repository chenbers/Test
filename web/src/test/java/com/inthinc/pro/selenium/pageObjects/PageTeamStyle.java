package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamOverallEnum;
import com.inthinc.pro.selenium.pageEnums.TeamStyleEnum;

public class PageTeamStyle extends TeamBar {

	public class TeamOverallScoreLinks extends TeamBarLinks {
	}

	public class TeamOverallScoreTexts extends TeamBarTexts {

		public Text overallScore() {
			return new Text(TeamStyleEnum.STYLE_SCORE);
		}

		public Text overallScoreLabel() {
			return new Text(TeamStyleEnum.STYLE_SCORE_LABEL);
		}
	}

	public class TeamOverallScoreTextFields extends TeamBarTextFields {
	}

	public class TeamOverallScoreButtons extends TeamBarButtons {
	}

	public class TeamOverallScoreDropDowns extends TeamBarDropDowns {
	}

	public class TeamOverallScorePopUps extends MastheadPopUps {
	}

	public TeamOverallScoreLinks _link() {
		return new TeamOverallScoreLinks();
	}

	public TeamOverallScoreTexts _text() {
		return new TeamOverallScoreTexts();
	}

	public TeamOverallScoreButtons _button() {
		return new TeamOverallScoreButtons();
	}

	public TeamOverallScoreTextFields _textField() {
		return new TeamOverallScoreTextFields();
	}

	public TeamOverallScoreDropDowns _dropDown() {
		return new TeamOverallScoreDropDowns();
	}

	public TeamOverallScorePopUps _popUp() {
		return new TeamOverallScorePopUps();
	}

    @Override
    public SeleniumEnums setUrl() {
        return TeamOverallEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _text().overallScore().isPresent() &&
               _text().overallScoreLabel().isPresent();
    }

}
