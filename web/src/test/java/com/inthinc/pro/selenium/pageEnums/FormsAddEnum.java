package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum FormsAddEnum implements SeleniumEnums {
    DEFAULT_URL("forms/build"),
    TITLE("Add Form", "//form[@id='formForm']/legend"),

    NAME_LABEL("Name:", "//label[@for='renameFormField']"),
    DESCRIPTION_LABEL("Description:", "//label[@for='description']"),
    TRIGGER_LABEL("Trigger:", "//label[@for='select-trigger']"),
    ROUTE_LABEL("Route Trigger Expression:", "//label[@for='routeTrigger']"),
//    VERSION_LABEL("Version:", "//label[@for='version']"),
    STATUS_LABEL("Status:", "//label[@for='select-status']"),
    HOS_LABEL("HOS:", "//label[@for='hos-filter']"),
    VEHICLE_TAGS_LABEL("Vehicle Tags:", "//label[@for='tags']"),
    SELECT_GROUP_LABEL("Select Group", "//span[@class='navbar-text']"),
    PROPERTIES_LABEL("Properties", "//div[@class='propertiesPane']/h3"),
    VIEW_PROPERTIES_LABEL("First add a control, then select it to view its properties here.", "//li[@class='emptyData']"),
    
    SAVE_TOP("Save", "//a[@id='saveForm-top']"),
    CANCEL_TOP("Cancel", "//a[@id='cancelForm-top']"),
    SAVE_BOTTOM("Save", "//a[@id='saveForm-bottom']"), 
    CANCEL_BOTTOM("Cancel", "//a[@id='cancelForm-bottom']"),

    NAME_FIELD(null, "//input[@id='renameFormField']"),
    DESCRIPTION_FIELD(null, "//input[@id='description']"),
    TRIGGER_DROPDOWN(null, "//select[@id='select-trigger']"),
    TRIGGER_PRODUCT(null, "//select[@id='select-triggerProductType']"),
    TRIGGER_ACTION(null, "//select[@id='select-triggerActionType']"),
    STATUS_DROPDOWN(null, "//select[@id='select-status']"),
    ROUTE_TRIGGER_EXPRESSION_FIELD(null, "routeTrigger"),
    VERSION_TEXT(null, "//input[@id='version']"),
    HOS_DROPDOWN(null, "//select[@id='select-hos-status']"),
    VEHICLE_TAGS_DROPDOWN(null, "//div[@class='text-wrap']"),
    VEHICLE_TAGS_ARROW(null, "//div[@class='text-arrow']"),
    FILTER_GROUPS_FIELD(null, "//div/span/input[@id='group-search']"),
    GROUPS_ARROW(null, "//ins[@class='jstree-icon']"),			//needs more work so I can select specific arrows
    GROUPS_CHECKBOX(null, "//ins[@class='jstree-checkbox']"),	//needs more work so I can select specific checkboxes
    GROUPS_NAV_TEXT(null,"//li[contains(@class,'jstree-checked')]/a"),
    GROUPS_NAV_CHECKBOX(null,"//li[contains(@class,'jstree-checked')]/a/ins[@class='jstree-checkbox']"),
    GROUPS_NAV_SEARCHED(null,"//li/a[@class='jstree-search']"),
    GROUPS_NAV_SEARCHED_TEXT(null,"//li/a[@class='jstree-search']"),
    GROUPS_NAV_SEARCHED_CHECKBOX(null,"//li/a[@class='jstree-search']/ins[@class='jstree-checkbox']"),
    
    ADD_FIELD_TEXT("Add Field", "//span[@class='brand']"),
    TEXT_LINK("Text", "//a[@class='toolButton inputText ui-draggable']"),
    NUMERIC_LINK("Numeric", "//a[@class='toolButton inputNumeric ui-draggable']"),
    DATE_LINK("Date", "//a[@class='toolButton inputDate ui-draggable']"),
    CHOOSE_ONE_LINK("Choose One", "//a[@class='toolButton inputSelectOne ui-draggable']"),
    SELECT_MULTIPLE_LINK("Select Multiple", "//a[@class='toolButton inputSelectMany ui-draggable']"),
 
    PREVIEW_AREA(null, "//div[@class='workspaceScrollArea']"),
    CONTROL_FLOW_ARROW(null, "//div[###]/div[2]/div[11]"),
    DELETE_CONTROL(null, "//a[@class='deleteControl']"),
    DELETE_ROW(null,"//div[2]/div[3]/div[2]/div[2]/div/div[2]/div[###]/div/a[@id='delete_form']"),	// this will delete the specified field by row
    
    DATANAME_FIELD("untitled###", "property_name"),
    CAPTION_FIELD(null, "//li[2]/div/ul/li/input"),
    HINT_FIELD(null, "//li[3]/div/ul/li/input"),
    DEFAULT_VALUE(null, "//input[@id='property_defaultValue']"),
    READ_ONLY_CHECKBOX(null, "//input[@id='property_readOnly']"),
    REQUIRED_CHECKBOX(null, "property_required"),
    LENGTH_CHECKBOX(null, "property_range_enabled"),
    MINIMUM_TEXT_FIELD(null, "//input[@class='editorTextfield min']"),
    MINIMUM_TEXT_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"),
    MAXIMUM_TEXT_FIELD(null, "//input[@class='editorTextfield max']"),
    MAXIMUM_TEXT_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"),
    RANGE_NUMERIC_CHECKBOX(null, "property_range_enabled"),
    MINIMUM_NUMERIC_FIELD(null, "//input[@class='editorTextfield min']"),
    MINIMUM_NUMERIC_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"),
    MAXIMUM_NUMERIC_FIELD(null, "//input[@class='editorTextfield max']"),
    MAXIMUM_NUMERIC_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"),
    RANGE_DATE_CHECKBOX(null, "property_range_enabled"),
    MINIMUM_DATE_TEXTFIELD(null, "//div/input[@class='editorTextfield min hasDatepicker']"),
    MINIMUM_DATE_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"), 
    MAXIMUM_DATE_TEXTFIELD(null, "//div/input[@class='editorTextfield max hasDatepicker']"),
    MAXIMUM_DATE_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"), 
    MINIMUM_ADD_NUMERIC_USE_EXPRESSION("Use Expression","//input[@id='property_range_min_expression']"),
    MAXIMUM_ADD_NUMERIC_USE_EXPRESSION("Use Expression","//input[@id='property_range_max_expression']"),
    INVALID_TEXT_FIELD(null, "//li[8]/div/ul/li/input"),
    KIND_DROPDOWN(null, "//select[@class='editorSelect']"),
    ADD_OPTION_LINK("Add Option", "//a[@class='addOption']"),
    BULK_EDIT_LINK("bulk edit", "//a[@class='optionsEditorLink']"),
    OPTION_FIELD(null, "//li[###]/div/div/ul/li/input"),
    OPTION_UNDERLYING_VALUE_FIELD(null, "//li[###]/div/div[2]/input"),
    OPTION_REMOVE(null, "//li[###]/div[@class='uiText']/a[@class='removeOption']"),
    
    FIRST_TEXT_ELEMENT(null,"//div[contains(@class,'control inputText')]"),
    FIRST_NUMERIC_ELEMENT(null,"//div[contains(@class,'control inputNumeric')]"),
    FIRST_DATE_ELEMENT(null,"//div[contains(@class,'control inputDate')]"),
    FIRST_SELECT_ONE_ELEMENT(null,"//div[contains(@class,'control inputSelectOne')]"),
    FIRST_SELECT_MULTIPLE_ELEMENT(null,"//div[contains(@class,'control inputSelectMany')]"),
    
    ADVANCED_ARROW(null, "//div[@class='icon']"),
    RELEVANCE_FIELD(null, "property_Relevance"),
    CONSTRAINT_FIELD(null, "property_Constraint"),
    INSTANCE_DESTINATION_FIELD(null, "property_Instance Destination"),
    
    NAME_TEXT_ERROR(null, "//*[@id='name.errors']"),		//"Name is a required field."
    CONTROL_TEXT_ERROR(null, "//*[@id='payload.errors']"),	//"Form must have at least one control."
    GROUP_TEXT_ERROR(null, "//*[@id='groupIDs.errors']"),	//"Must select at least one group."
    DATA_NAME_ERROR(null, "//ul[@class='errorList']"),		//"This property is required"
    
    //GROUPS ITEMS THAT ARE NOT BEING IMPLEMENTED YET:
    GROUP_LINK("Group", "//a[@class='toolButton group ui-draggable']"),
    LABEL_FIELD(null, ""),//This one was part of Groups, which is not being implemented yet
    LOOPED_CHECKBOX(null, "property_Looped"),
    DISPLAY_ON_ONE_SCREEN_CHECKBOX(null, "property_Display On One Screen")
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsAddEnum(String url){
    	this.url = url;
    }
    private FormsAddEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
