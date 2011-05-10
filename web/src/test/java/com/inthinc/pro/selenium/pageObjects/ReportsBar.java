package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public abstract class ReportsBar extends NavigationBar {

	protected class ReportsBarTexts extends NavigationBarTexts {
	}

	protected class ReportsBarButtons extends NavigationBarButtons {
	}

	protected class ReportsBarTextFields extends NavigationBarTextFields {
	}

	protected class ReportsBarDropDowns extends NavigationBarDropDowns {

		private SeleniumEnums[] enums = { ReportsBarEnum.SEATBELT_SCORE_DHX,
				ReportsBarEnum.STYLE_SCORE_DHX, ReportsBarEnum.SPEED_SCORE_DHX,
				ReportsBarEnum.OVERALL_SCORE_DHX };

		protected DhxDropDown score(SeleniumEnums DHX, SeleniumEnums arrow,
				String page) {
			return new DhxDropDown(DHX, page).tableOptions(enums)
					.dropDownButton(arrow);
		}
	}

	public class ReportsPopUps {
		private String page;

		public ReportsPopUps(String page) {
			this.page = page;
		}

		public ReportsEmail emailReport() {
			return new ReportsEmail();
		}

		public ReportsEditColumns editColumns() {
			return new ReportsEditColumns();
		}

		public class ReportsEmail {

			public PopUpButton _button() {
				return new PopUpButton();
			}

			public PopUpTextField _textField() {
				return new PopUpTextField();
			}

			public PopUpText _text() {
				return new PopUpText();
			}

			public class PopUpButton {
				public TextButton email() {
					return new TextButton(ReportsBarEnum.EMAIL_REPORT_SEND,
							page);
				}

				public TextButton cancel() {
					return new TextButton(ReportsBarEnum.EMAIL_REPORT_CANCEL,
							page);
				}

				public Button close() {
					return new Button(ReportsBarEnum.EMAIL_REPORT_X, page);
				}
			}

			public class PopUpText {
				public Text title() {
					return new Text(ReportsBarEnum.EMAIL_REPORT_TITLE);
				}

				public Text subTitle() {
					return new Text(ReportsBarEnum.EMAIL_REPORT_SUB_TITLE);
				}

				public Text header() {
					return new Text(ReportsBarEnum.EMAIL_REPORT_HEADER, page);
				}
			}

			public class PopUpTextField {
				public TextField emails() {
					return new TextField(ReportsBarEnum.EMAIL_REPORT_TEXTFIELD,
							page);
				}
			}
		}

		public class ReportsEditColumns {
			public PopUpText _text() {
				return new PopUpText();
			}

			public PopUpButton _button() {
				return new PopUpButton();
			}

			public class PopUpButton {

				public TextButton save() {
					return new TextButton(ReportsBarEnum.EDIT_COLUMNS_SAVE,
							page);
				}

				public TextButton cancel() {
					return new TextButton(ReportsBarEnum.EDIT_COLUMNS_CANCEL,
							page);
				}

				public TextButton close() {
					return new TextButton(ReportsBarEnum.EDIT_COLUMNS_X);
				}
			}

			public class PopUpText {

				public TableText entry() {
					return new TableText(ReportsBarEnum.EDIT_COLUMNS_LABEL,
							page);
				}

				public Text header() {
					return new Text(ReportsBarEnum.EDIT_COLUMNS_HEADER);
				}

				public Text title() {
					return new Text(ReportsBarEnum.EDIT_COLUMNS_TITLE);
				}

			}

			public CheckBox _checkBox() {
				return new CheckBox(ReportsBarEnum.EDIT_COLUMNS_CHECKBOX, page);
			}
		}
	}

	protected class ReportsBarLinks extends NavigationBarLinks {

		public TextLink drivers() {
			return new TextLink(ReportsBarEnum.DRIVERS);
		}

		public TextLink vehicles() {
			return new TextLink(ReportsBarEnum.VEHICLES);
		}

		public TextLink idling() {
			return new TextLink(ReportsBarEnum.IDLING);
		}

		public TextLink devices() {
			return new TextLink(ReportsBarEnum.DEVICES);
		}

		public TextLink waySmart() {
			return new TextLink(ReportsBarEnum.WAYSMART);
		}
	}

}
