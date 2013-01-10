package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;
import com.inthinc.pro.selenium.pageEnums.FormsManageEnum;

public class PageFormsManage extends FormsBar {

    public PageFormsManage() {
//        checkMe.add(FormsManageEnum.DELETE);
//        checkMe.add(FormsManageEnum.APPROVED);
//        checkMe.add(FormsManageEnum.SEARCH_TEXTFIELD);
//        checkMe.add(FormsManageEnum.SEARCH_BUTTON);
//        checkMe.add(FormsManageEnum.EDIT_COLUMNS);
//        checkMe.add(FormsManageEnum.PUBLISH);
//        checkMe.add(FormsManageEnum.FORMS_HEADER);
//        checkMe.add(FormsManageEnum.DESCRIPTION_HEADER);
//        checkMe.add(FormsManageEnum.APPROVED_HEADER);
//        checkMe.add(FormsManageEnum.STATUS_HEADER);
//        checkMe.add(FormsManageEnum.OWNER_HEADER);
//        checkMe.add(FormsManageEnum.EDIT_HEADER);
//        checkMe.add(FormsManageEnum.CHECKBOX);
//        checkMe.add(FormsManageEnum.FORMS_ENTRY);
//        checkMe.add(FormsManageEnum.DESCRIPTION_ENTRY);
//        checkMe.add(FormsManageEnum.APPROVED_ENTRY);
//        checkMe.add(FormsManageEnum.STATUS_ENTRY);
//        checkMe.add(FormsManageEnum.OWNER_ENTRY);
//        checkMe.add(FormsManageEnum.EDIT_ENTRY);        
    }

    public class FormsManageButtons {
    	public TextButton newForm() 			{return new TextButton(FormsManageEnum.NEW_FORM_BUTTON);}
    	public ButtonTable gear() 				{return new ButtonTable(FormsManageEnum.GEAR);}
    }

    public class FormsManageDropDowns {
    	public DropDown recordsPerPage() 		{return new DropDown(FormsManageEnum.RECORDS_DROPDOWN);}
    }
    
    public class FormsManageLinks extends FormsBarLinks {
    	// HAS CURRENTLY BEEN REMOVED        
//    	public TextLink sortByPublish() 		{return new TextLink(FormsManageEnum.PUBLISH_LINK);}
//    	public TextLink sortBySelectManage() 	{return new TextLink(FormsManageEnum.SELECT_MANAGE_LINK);}
//    	public TextLink sortBySelectPublished() {return new TextLink(FormsManageEnum.SELECT_PUBLISHED_LINK);}
        public TextLink sortByName() 			{return new TextLink(FormsManageEnum.NAME_LINK);}
        public TextLink sortByBaseFormId() 		{return new TextLink(FormsManageEnum.BASE_FORM_ID_LINK);}
        public TextLink sortByVersion() 		{return new TextLink(FormsManageEnum.VERSION_LINK);}      
        public TextLink sortByDescription() 	{return new TextLink(FormsManageEnum.DESCRIPTION_LINK);}
        public TextLink sortByStatus() 			{return new TextLink(FormsManageEnum.STATUS_LINK);}
        public TextLink sortByTrigger() 		{return new TextLink(FormsManageEnum.TRIGGER_LINK);}
        public TextTableLink edit() 			{return new TextTableLink(FormsManageEnum.EDIT_ENTRY_LINK);}
        public TextTableLink publish() 			{return new TextTableLink(FormsManageEnum.PUBLISH_ENTRY_LINK);}
        public TextTableLink copy() 			{return new TextTableLink(FormsManageEnum.COPY_ENTRY_LINK);}
        public TextLink previous() 				{return new TextLink(FormsManageEnum.PREVIOUS);}
        public TextTableLink pageNumber() 		{return new TextTableLink(FormsManageEnum.PAGE_NUMBER);}
        public TextLink next() 					{return new TextLink(FormsManageEnum.NEXT_MANAGE);}
    }

    public class FormsManageTextFields {
        public TextField search() 				{return new TextField(FormsManageEnum.SEARCH_TEXTFIELD);}
    }
    
    public class FormsManageTexts {
    	public Text title() 					{return new Text(FormsAddEnum.TITLE);}
    	public TextTable entryName() 			{return new TextTable(FormsManageEnum.NAME_ENTRY);}
    	public TextTable entryBaseFormId() 		{return new TextTable(FormsManageEnum.BASE_FORM_ID_ENTRY);}
    	public TextTable entryVersion() 		{return new TextTable(FormsManageEnum.VERSION_ENTRY);}
    	public TextTable entryDescription() 	{return new TextTable(FormsManageEnum.DESCRIPTION_ENTRY);}
    	public TextTable entryStatus() 			{return new TextTable(FormsManageEnum.STATUS_ENTRY);}
    	public TextTable entryTrigger() 		{return new TextTable(FormsManageEnum.TRIGGER_ENTRY);}
    	public Text entries() 					{return new Text(FormsManageEnum.ENTRIES_TEXT);}
    	public Text noRecordsError() 			{return new Text(FormsManageEnum.NO_RECORDS_FOUND_ERROR);}
    	public Text successAlert() 				{return new Text(FormsManageEnum.SUCCESS_ALERT);}
    }
    
    public class FormsTablesCheckBoxes {
// THESE ARE NOT ON THE PAGE ANYMORE
//    	public CheckBox checkAll() 				{return new CheckBox(FormsManageEnum.SELECT_ALL_CHECKBOX);}
//    	public CheckBoxTable entryCheck() 		{return new CheckBoxTable(FormsManageEnum.CHECKBOX_ENTRY);}
    }
    
    public class FormsManagePopUps extends MastheadPopUps {}

    public FormsManageButtons _button() 		{return new FormsManageButtons();}
    public FormsTablesCheckBoxes _checkBox() 	{return new FormsTablesCheckBoxes();}
    public FormsManageDropDowns _dropDown() 	{return new FormsManageDropDowns();}
    public FormsManageLinks _link() 			{return new FormsManageLinks();}
    public FormsManageTexts _text() 			{return new FormsManageTexts();}
    public FormsManageTextFields _textField() 	{return new FormsManageTextFields();}
    public FormsManagePopUps _popUp() 			{return new FormsManagePopUps();}

    public SeleniumEnums setUrl() 				{return FormsManageEnum.DEFAULT_URL;}

    protected boolean checkIsOnPage() {
        return _link().sortByStatus().isPresent() && _button().newForm().isPresent();
    }
    public TextTableLink publishDisabled() {return new TextTableLink(FormsManageEnum.PUBLISH_DISABLED_LINK);}
}
