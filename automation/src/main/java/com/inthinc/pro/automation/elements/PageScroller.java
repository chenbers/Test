package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class PageScroller extends ClickableObject implements Clickable {

	public PageScroller(SeleniumEnums anEnum, String pageID){
		super(anEnum);
		if (pageID!=null){
			if (pageID.endsWith("_table")){
				pageID+="_table";
			}
			String id = myEnum.getIDs()[0].replaceFirst("/", "");
				myEnum.setID("//table[@id='"+pageID+"']/tbody/tr"+id);
		}
	}
	
	public PageScroller(SeleniumEnums anEnum, String pageID, Integer pageNumber){
        this(anEnum, pageID);
        replaceNumber(pageNumber);
    }
}
