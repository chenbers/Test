package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamOverallEnum;

public class PageTeamOverallScore extends TeamBar {

	public class TeamOverallScoreLinks extends TeamBarLinks {
	}

	public class TeamOverallScoreTexts extends TeamBarTexts {

		public Text overallScore() {
			return new Text(TeamOverallEnum.OVERALL_SCORE);
		}

		public Text overallScoreLabel() {
			return new Text(TeamOverallEnum.OVERALL_SCORE_LABEL);
		}

		public Text _na_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_NA);
		}

		public Text _0_1_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_0_1);
		}

		public Text _1_2_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_1_2);
		}

		public Text _2_3_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_2_3);
		}

		public Text _3_4_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_3_4);
		}

		public Text _4_5_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_4_5);
		}

		public Text total_ScoreCount() {
			return new Text(TeamOverallEnum.SCORE_TOTAL);
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
        return _text().total_ScoreCount().isPresent() &&
               _text().overallScoreLabel().isPresent();
    }

}
