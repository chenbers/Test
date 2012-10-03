package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAdminEnum;
import com.inthinc.pro.selenium.pageEnums.UtilEnum;

public class PageUtil extends AdminBar {
	
    public class UtilCheckBoxes {}
    
	public class UtilLinks {}

	public class UtilTextFields {
		
		public TextField id() {
			return new TextField(UtilEnum.ID_FIELD);
		}
		
		public TextField accountid() {
			return new TextField(UtilEnum.ACCOUNTID_FIELD);
		}
		
		public TextField deviceid() {
			return new TextField(UtilEnum.DEVICEID_FIELD);
		}
		
		public TextField acctid() {
			return new TextField(UtilEnum.ACCTID_FIELD);
		}
		
		public TextField status() {
			return new TextField(UtilEnum.STATUS_FIELD);
		}
	
		public TextField name() {
			return new TextField(UtilEnum.NAME_FIELD);
		}
		
		public TextField imei() {
			return new TextField(UtilEnum.IMEI_FIELD);
		}
		
		public TextField sim() {
			return new TextField(UtilEnum.SIM_FIELD);
		}
		
		public TextField serialnum() {
			return new TextField(UtilEnum.SERIALNUM_FIELD);
		}

		public TextField phone() {
			return new TextField(UtilEnum.PHONE_FIELD);
		}
		
		public TextField activated() {
			return new TextField(UtilEnum.ACTIVATED_FIELD);
		}
		
		public TextField baseid() {
			return new TextField(UtilEnum.BASEID_FIELD);
		}
		
		public TextField productver() {
			return new TextField(UtilEnum.PRODUCTVER_FIELD);
		}
		
		public TextField emumd5() {
			return new TextField(UtilEnum.EMUMD5_FIELD);
		}	
		
		public TextField productversion() {
			return new TextField(UtilEnum.PRODUCTVERSION_FIELD);
		}		
		
		public TextField mcmid() {
			return new TextField(UtilEnum.MCMID_FIELD);
		}		
		
		public TextField altimei() {
			return new TextField(UtilEnum.ALTIMEI_FIELD);
		}
		
	}

	public class UtilButtons {
		
    	public Button populate() {
    		return new Button(UtilEnum.POPULATE_BUTTON);
    	}
    	
    	public Button run() {
    		return new Button(UtilEnum.RUN_BUTTON);
    	}
	}

	public class UtilDropDowns {
		
    	public DropDown method() {
    		return new DropDown(UtilEnum.METHOD_DROPDOWN);
    	}
	}

	public class UtilTexts {}
	
    public UtilCheckBoxes _checkBox() {
        return new UtilCheckBoxes();
    }
	
	public UtilTextFields _textField() {
		return new UtilTextFields();
	}

	public UtilTexts _text() {
		return new UtilTexts();
	}

	public UtilLinks _link() {
		return new UtilLinks();
	}

	public UtilButtons _button() {
		return new UtilButtons();
	}

	public UtilDropDowns _dropDown() {
		return new UtilDropDowns();
	}


	public String getExpectedPath() {
		return UtilEnum.MY_ACCOUNT_URL.getURL();
	}

    public SeleniumEnums setUrl() {
        return UtilEnum.MY_ACCOUNT_URL; 
    }

    protected boolean checkIsOnPage() {
    	return _dropDown().method().isPresent();
    }

}
