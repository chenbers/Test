package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class LinkTable extends ClickableTableObject implements Clickable, TableBased {
	
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
	    
	    @Override
		public Boolean isVisible(Integer row) {
	        myEnum.replaceNumber(row.toString());
			return super.isVisible();
		}
		@Override
		public Boolean isPresent(Integer row) {
	        myEnum.replaceNumber(row.toString());
			return super.isPresent();
		}
		@Override
		public ElementInterface focus(Integer row) {
	        myEnum.replaceNumber(row.toString());
			return super.focus();
		}
		@Override
		public Boolean assertVisibility(Integer row, Boolean visible) {
	        myEnum.replaceNumber(row.toString());
			return super.assertVisibility(visible);
		}
		
		
		
		
		@Override
		@Deprecated
		public Boolean isVisible() {
	    	addError(
					"TextTableLink.getText()",
					"please supply an Integer number for the row on the table)",
					ErrorLevel.FAIL);
			return null;
		}
		@Override
		@Deprecated
		public Boolean isPresent() {
	    	addError(
					"TextTable.isPresent()",
					"please supply an Integer number for the row on the table)",
					ErrorLevel.FAIL);
			return null;
		}
		@Override
		@Deprecated
		public ElementInterface focus() {
	    	addError(
					"TextTable.focus()",
					"please supply an Integer number for the row on the table)",
					ErrorLevel.FAIL);
			return null;
		}
		@Override
		@Deprecated
		public Boolean assertVisibility(Boolean visible) {
	    	addError(
					"TextTable.assertVisibility()",
					"please supply an Integer number for the row on the table)",
					ErrorLevel.FAIL);
			return null;
		}
}
