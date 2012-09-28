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
import javax.faces.webapp.UIComponentBodyTag;

/**
 * @author Cagatay Civici
 * @author Kenan Sevindik
 * 
 * Tag class for the component
 */
public class AuthorizeTag extends UIComponentBodyTag
{
    private String ifAllGranted = null;

    private String ifAnyGranted = null;

    private String ifNotGranted = null;

    public void release()
    {
        super.release();
        ifAllGranted = null;
        ifAnyGranted = null;
        ifNotGranted = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        if (ifAllGranted != null)
        {
            if (isValueReference(ifAllGranted))
            {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(ifAllGranted);
                component.setValueBinding("ifAllGranted", vb);
            }
            else
            {
                component.getAttributes().put("ifAllGranted", ifAllGranted);
            }
        }

        if (ifAnyGranted != null)
        {
            if (isValueReference(ifAnyGranted))
            {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(ifAnyGranted);
                component.setValueBinding("ifAnyGranted", vb);
            }
            else
            {
                component.getAttributes().put("ifAnyGranted", ifAnyGranted);
            }
        }

        if (ifNotGranted != null)
        {
            if (isValueReference(ifNotGranted))
            {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(ifNotGranted);
                component.setValueBinding("ifNotGranted", vb);
            }
            else
            {
                component.getAttributes().put("ifNotGranted", ifNotGranted);
            }
        }
    }

    public String getComponentType()
    {
        return Authorize.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }

    public String getIfAllGranted()
    {
        return ifAllGranted;
    }

    public void setIfAllGranted(String ifAllGranted)
    {
        this.ifAllGranted = ifAllGranted;
    }

    public String getIfAnyGranted()
    {
        return ifAnyGranted;
    }

    public void setIfAnyGranted(String ifAnyGranted)
    {
        this.ifAnyGranted = ifAnyGranted;
    }

    public String getIfNotGranted()
    {
        return ifNotGranted;
    }

    public void setIfNotGranted(String ifNotGranted)
    {
        this.ifNotGranted = ifNotGranted;
    }
}