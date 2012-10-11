package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamOverallEnum;
import com.inthinc.pro.selenium.pageEnums.TeamSpeedEnum;

public class PageTeamSpeed extends TeamBar {

	public class TeamOverallScoreLinks extends TeamBarLinks {
	}

	public class TeamOverallScoreTexts extends TeamBarTexts {

		public Text overallScore() {
			return new Text(TeamSpeedEnum.SPEED_SCORE);
		}

		public Text overallScoreLabel() {
			return new Text(TeamSpeedEnum.SPEED_SCORE_LABEL);
		}
		
	    public Text limitHeader() {
	          return new Text(TeamSpeedEnum.LIMIT_TABLE_TITLE);
	    }

		public Text categoryOneSpeedsHeader() {
			return new Text(TeamSpeedEnum.LIMIT_1_30_HEADER);
		}

		public Text categoryTwoSpeedsHeader() {
			return new Text(TeamSpeedEnum.LIMIT_31_40_HEADER);
		}

		public Text categoryThreeSpeedsHeader() {
			return new Text(TeamSpeedEnum.LIMIT_41_54_HEADER);
		}

		public Text categoryFourSpeedsHeader() {
			return new Text(TeamSpeedEnum.LIMIT_55_64_HEADER);
		}

		public Text categoryFiveSpeedsHeader() {
			return new Text(TeamSpeedEnum.LIMIT_65_80_HEADER);
		}

		public Text categoryTotalHeader() {
			return new Text(TeamSpeedEnum.LIMIT_TOTAL_HEADER);
		}

        public Text categoryOneSpeeds() {
            return new Text(TeamSpeedEnum.LIMIT_1_30_NUMBER);
        }

        public Text categoryTwoSpeeds() {
            return new Text(TeamSpeedEnum.LIMIT_31_40_NUMBER);
        }

        public Text categoryThreeSpeeds() {
            return new Text(TeamSpeedEnum.LIMIT_41_54_NUMBER);
        }

        public Text categoryFourSpeeds() {
            return new Text(TeamSpeedEnum.LIMIT_55_64_NUMBER);
        }

        public Text categoryFiveSpeeds() {
            return new Text(TeamSpeedEnum.LIMIT_65_80_NUMBER);
        }

        public Text categoryTotal() {
            return new Text(TeamSpeedEnum.LIMIT_TOTAL_NUMBER);
        }
        
        public Text categoryOnePercentage() {
            return new Text(TeamSpeedEnum.LIMIT_1_30_PERCENT);
        }

        public Text categoryTwoPercentage() {
            return new Text(TeamSpeedEnum.LIMIT_31_40_PERCENT);
        }

        public Text categoryThreePercentage() {
            return new Text(TeamSpeedEnum.LIMIT_41_54_PERCENT);
        }

        public Text categoryFourPercentage() {
            return new Text(TeamSpeedEnum.LIMIT_55_64_PERCENT);
        }

        public Text categoryFivePercentage() {
            return new Text(TeamSpeedEnum.LIMIT_65_80_PERCENT);
        }

        public Text categoryTotalPercentage() {
            return new Text(TeamSpeedEnum.LIMIT_TOTAL_PERCENT);
        }
        
        public Text violationsSubtitle() {
            return new Text(TeamSpeedEnum.LIMIT_TABLE_SUBTITLE);
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
        return _text().categoryTotalPercentage().isPresent() &&
               _text().categoryTotal().isPresent();
    }

}
