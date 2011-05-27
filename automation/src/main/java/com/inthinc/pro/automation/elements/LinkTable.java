package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class LinkTable extends ClickableTableObject implements Clickable {
	
	 public LinkTable(SeleniumEnums anEnum) {
	        super(anEnum);
	    }
	    public LinkTable(SeleniumEnums anEnum, Integer replaceNumber) {
	        super(anEnum, replaceNumber);
	    }
	    public LinkTable(SeleniumEnums anEnum, String replaceWord) {
	        super(anEnum, replaceWord);
	    }
	    public LinkTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
	        super(anEnum, replaceWord, replaceNumber);
	    }
	


}
