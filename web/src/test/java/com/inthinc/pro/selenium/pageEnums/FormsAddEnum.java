package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsAddEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/build"),
    TITLE("Add Form", "//form[@id='formForm']/legend"),

    NAME_LABEL("Name:", "//label[@for='renameFormField']"),
    DESCRIPTION_LABEL("Description:", "//label[@for='description']"),
    TRIGGER_LABEL("Trigger:", "//label[@for='select-trigger']"),
    ROUTE_LABEL("Route Trigger Expression:", "//label[@for='routeTrigger']"),
    VERSION_LABEL("Version:", "//label[@for='version']"),
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

    NAME_FIELD(null, "renameFormField"),
    DESCRIPTION_FIELD(null, "description"),
    TRIGGER_DROPDOWN(null, "select-trigger"),
    TRIGGER_PRODUCT(null, "select-triggerProductType"),
    TRIGGER_ACTION(null, "select-triggerActionType"),
    STATUS_DROPDOWN(null, "select-status"),
    ROUTE_TRIGGER_EXPRESSION_FIELD(null, "routeTrigger"),
    VERSION_TEXT(null, "//input[@id='version']"),
    HOS_DROPDOWN(null, "//select[@id='select-hos-status']"),
    VEHICLE_TAGS_DROPDOWN(null, "//div[@class='text-wrap']"),
    VEHICLE_TAGS_ARROW(null, "//div[@class='text-arrow']"),
    FILTER_GROUPS_FIELD("Filter Groups:", "group-search"), 
    GROUPS_ARROW(null, "//ins[@class='jstree-icon']"),//needs more work so I can select specific arrows
    GROUPS_CHECKBOX(null, "//ins[@class='jstree-checkbox']"),//needs more work so I can select specific checkboxes
    
    ADD_FIELD_TEXT("Add Field", "//span[@class='brand']"),
    TEXT_LINK("Text", "//a[@class='toolButton inputText ui-draggable']"),
    NUMERIC_LINK("Numeric", "//a[@class='toolButton inputNumeric ui-draggable']"),
    DATE_LINK("Date", "//a[@class='toolButton inputDate ui-draggable']"),
    CHOOSE_ONE_LINK("Choose One", "//a[@class='toolButton inputSelectOne ui-draggable']"),
    SELECT_MULTIPLE_LINK("Select Multiple", "//a[@class='toolButton inputSelectMany ui-draggable']"),
 
    PREVIEW_AREA(null, "//div[@class='workspaceScrollArea']"),
    CONTROL_FLOW_ARROW(null, "//div[###]/div[2]/div[11]"),
    DELETE_CONTROL(null, "//a[@class='deleteControl']"),
    
    DATANAME_FIELD("untitled###", "property_Data Name"),
    CAPTION_FIELD(null, "//li[2]/div/ul/li/input"),
    HINT_FIELD(null, "//li[3]/div/ul/li/input"),
    DEFAULT_VALUE(null, "//input[@id='property_Default Value']"),
    READ_ONLY_CHECKBOX(null, "//input[@id='property_Read Only']"),
    REQUIRED_CHECKBOX(null, "property_Required"),
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
    INVALID_TEXT_FIELD(null, "//li[8]/div/ul/li/input"),
    KIND_DROPDOWN(null, "//select[@class='editorSelect']"),
    ADD_OPTION_LINK("Add Option", "//a[@class='addOption']"),
    BULK_EDIT_LINK("bulk edit", "//a[@class='optionsEditorLink']"),
    OPTION_FIELD(null, "//li[###]/div/div[1]/ul/li/input"),
    OPTION_UNDERLYING_VALUE_FIELD(null, "//li[###]/div/div[2]/input"),
    OPTION_REMOVE(null, "//a[@class='removeOption']"),//we are going to need to be able to reference which option # you want to remove
    
    ADVANCED_ARROW(null, "//div[@class='icon']"),
    RELEVANCE_FIELD(null, "property_Relevance"),
    CONSTRAINT_FIELD(null, "property_Constraint"),
    INSTANCE_DESTINATION_FIELD(null, "property_Instance Destination"),
    
    NAME_TEXT_ERROR("Name is a required field", "//label[@id='displayNameError']"),
    CONTROL_TEXT_ERROR("Form needs at least one control", "//label[@id='payloadError']"),
    GROUP_TEXT_ERROR("Must select at least one group", "//label[@id='groupIDsError']"),
    DATA_NAME_ERROR("This property is required", "//ul[@class='errorList']"),
    
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
