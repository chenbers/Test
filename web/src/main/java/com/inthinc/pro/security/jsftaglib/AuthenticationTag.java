/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inthinc.pro.security.jsftaglib;

/**
 * The classes in this package were copied from the acegi jsf taglib jar and
 * modified to work with spring security 2.0. Once the facelets tag lib is
 * created by the Spring Security group, we can get rid of this package and use
 * theirs.
 * 
 */

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentELTag;
import javax.faces.webapp.UIComponentTag;

/**
 * @author Cagatay Civici
 * 
 * Tag class for the component
 */
public class AuthenticationTag extends UIComponentTag
{
    private String operation = null;

    public void release()
    {
        super.release();
        operation = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        if (operation != null)
        {
            if (isValueReference(operation))
            {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(operation);
                component.setValueBinding("operation", vb);
            }
            else
            {
                ((Authentication) component).setOperation(operation);
            }
        }
    }

    public String getComponentType()
    {
        return Authentication.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }
}
