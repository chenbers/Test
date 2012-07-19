package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsAddEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/add"),
    TITLE("Add Forms", Xpath.start().span(Id.clazz("add")).toString()),

    NAME_LABEL("Name:", "//label[@for='renameFormField']"),
    DESCRIPTION_LABEL("Description:", "//label[@for='description']"),
    TRIGGER_LABEL("Trigger:", "//label[@for='select-trigger']"),
    ROUTE_LABEL("Route Trigger Expression:", "//label[@for='routeTrigger']"),
    VERSION_LABEL("Version:", "//label[@for='version']"),
    HOS_LABEL("HOS:", "//label[@for='hos-filter']"),
    VEHICLE_TAGS_LABEL("Vehicle Tags:", "//label[@for='tags']"),
    FILTER_GROUPS_LABEL("Filter Groups:", "//label[@for='group-search']"),
    PROPERTIES_LABEL("Properties", "//div[@class='propertiesPane']/h3"),
    VIEW_PROPERTIES_LABEL("First add a control, then select it to view its properties here.", "//li[@class='emptyData']"),
    
    SAVE_TOP("Save", "edit-form:editFormsBuildSave1"),
    CANCEL_TOP("Cancel", "edit-form:editFormsBuildCancel1"),
    SAVE_BOTTOM("Save", "edit-form:editFormsBuildSave2"),
    CANCEL_BOTTOM("Cancel", "edit-form:editFormsBuildCancel2"),

    NAME_FIELD(null, "renameFormField"),
    DESCRIPTION_FIELD(null, "description"),
    TRIGGER_DROPDOWN(null, "select-trigger"),
    ROUTE_TRIGGER_EXPRESSION_FIELD(null, "routeTrigger"),
    HOS_DROPDOWN(null, "select-hos-status"),
    VERSION_TEXT(null, "version"),
    VEHICLE_TAGS_DROPDOWN(null, "tag_textext"),
    FILTER_GROUPS_FIELD(null, "group-search"),  
    CHECKBOX(null, "//ins[@class='jstree-checkbox']"),
    
    ADD_NEW_FIELD_TEXT("Add new field", "//div[@class='toolPalette']/h3"),
    TEXT_LINK("Text", "//a[@class='toolButton inputText ui-draggable']"),
    NUMERIC_LINK("Numeric", "//a[@class='toolButton inputNumeric ui-draggable']"),
    DATE_LINK("Date", "//a[@class='toolButton inputDate ui-draggable']"),
    CHOOSE_ONE_LINK("Choose One", "//a[@class='toolButton inputSelectOne ui-draggable']"),
    SELECT_MULTIPLE_LINK("Select Multiple", "//a[@class='toolButton inputSelectMany ui-draggable']"),
    GROUP_LINK("Group", "//a[@class='toolButton group ui-draggable']"),
 
    PREVIEW_AREA(null, "//div[@class='workspace']"),
    DELETE_CONTROL(null, ""),
//All these fields and checkboxes may need unique id's cause in many cases they are pointing
//to two different objects    
    DATANAME_FIELD("untitled###", "property_Data Name"),
    CAPTION_FIELD(null, ""),
    HINT_FIELD(null, ""),
    DEFAULT_VALUE(null, "property_Default Value"),
    READ_ONLY_CHECKBOX(null, "property_Read Only"),
    REQUIRED_CHECKBOX(null, "property_Required"),
    LENGTH_CHECKBOX(null, "property_range_enabled"),
    RANGE_CHECKBOX(null, "property_range_enabled"),
    MINIMUM_FIELD(null, "//input[@class='editorTextfield min']"),
    MINIMUM_INCLUSIVE_CHECKBOX(null, "property_range_min_inclusive"),
    MAXIMUM_FIELD(null, "//input[@class='editorTextfield max']"),
    MAXIMUM_INCLUSIVE_CHECKBOX(null, "property_range_max_inclusive"),
    INVALID_TEXT_FIELD(null, ""),
    KIND_DROPDOWN(null, "//select[@class='editorSelect']"),
    ADD_OPTION_LINK("Add Option", "//a[@class='addOption']"),
    BULK_EDIT_LINK("bulk edit", "//a[@class='optionsEditorLink']"),
    OPTION_FIELD(null, ""),
    OPTION_UNDERLYING_VALUE_FIELD(null, ""),
    OPTION_REMOVE(null, "//a[@class='removeOption']"),//we are going to need to be able to reference which option # you want to remove
    LABEL_FIELD(null, ""),
    LOOPED_CHECKBOX(null, "property_Looped"),
    DISPLAY_ON_ONE_SCREEN_CHECKBOX(null, "property_Display On One Screen"),
    
    ADVANCED_ARROW(null, "//div[@class='icon']"),
    RELEVANCE_FIELD(null, "property_Relevance"),
    CONSTRAINT_FIELD(null, "property_Constraint"),
    INSTANCE_DESTINATION(null, "property_Instance Destination"),
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
