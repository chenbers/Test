package com.inthinc.pro.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;

public class FormUtil
{
    /**
     * Clears out all the values of all the inputs for a specific UIForm. This clears out the values from the Component TREE. 
     * 
     * This helps with AJAX form submissions and validation errors. Example: The design of a page is to switch between add,edit, and view states without 
     * performing full page refreshes. If a validation error occurs, form fields will maintain the values until the view refreshes, regardless of the values
     * stored in the model. Call resetForm() to reset the values to match those stored in the model.
     * 
     * @param uiForm
     */
    public static void resetForm(UIForm uiForm)
    {
        clearChildren(uiForm);
    }
    
    /**
     * Clears out the values of all childred which are UIInputs for the UIComponent supplied in the method signature.
     * 
     * @param uiComponent
     */
    public static void clearChildren(UIComponent uiComponent)
    {
        for(UIComponent childComponent : uiComponent.getChildren())
        {
            if(childComponent instanceof UIInput){
                ((UIInput)childComponent).resetValue();
            }
            
            if(childComponent.getChildCount() > 0){
                clearChildren(childComponent);
            }
                                                  
        }
    }
}
