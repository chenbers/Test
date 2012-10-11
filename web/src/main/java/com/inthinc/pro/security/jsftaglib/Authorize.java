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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Cagatay Civici
 * @author Kenan Sevindik
 * 
 * Component that controls the rendering of secured components
 */
public class Authorize extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "com.inthinc.pro.security.jsftaglib.Authorize";

    public static final String COMPONENT_FAMILY = "com.inthinc.pro.security.jsftaglib";

    private String ifAllGranted = null;

    private String ifAnyGranted = null;

    private String ifNotGranted = null;

    private IAuthenticationMode authenticationMode;

    public Authorize()
    {
        setRendererType(null);
    }

    public void encodeChildren(FacesContext context) throws IOException
    {
        if (isUserInRole(getRoles()))
        {
            for (Iterator iter = getChildren().iterator(); iter.hasNext();)
            {
                UIComponent child = (UIComponent) iter.next();
                encodeRecursive(context, child);
            }
        }
    }

    public boolean isUserInRole(String roles)
    {
        return getAuthenticationMode().isUserInRole(roles);
    }

    public IAuthenticationMode getAuthenticationMode()
    {
        if (authenticationMode == null)
        {
            if (getIfAllGranted() != null)
                authenticationMode = new AllAuthenticationMode();
            else if (getIfAnyGranted() != null)
                authenticationMode = new AnyAuthenticationMode();
            else if (getIfNotGranted() != null)
                authenticationMode = new NotAuthenticationMode();
            else
                // default
                authenticationMode = new AnyAuthenticationMode();
        }
        return authenticationMode;
    }

    public void setAuthenticationMode(IAuthenticationMode authenticationMode)
    {
        this.authenticationMode = authenticationMode;
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    private String getRoles()
    {
        String roles = "";
        if (getIfAllGranted() != null)
            roles = getIfAllGranted();
        else if (getIfAnyGranted() != null)
            roles = getIfAnyGranted();
        else if (getIfNotGranted() != null)
            roles = getIfNotGranted();

        return roles;
    }

    protected void encodeRecursive(FacesContext context, UIComponent component) throws IOException
    {
        if (!component.isRendered())
            return;

        component.encodeBegin(context);
        if (component.getRendersChildren())
        {
            component.encodeChildren(context);
        }
        else
        {
            List children = component.getChildren();
            for (Iterator iter = children.iterator(); iter.hasNext();)
            {
                UIComponent child = (UIComponent) iter.next();
                encodeRecursive(context, child);
            }
        }
        component.encodeEnd(context);
    }

    public String getIfAllGranted()
    {
        if (ifAllGranted != null)
            return ifAllGranted;

        ValueBinding vb = getValueBinding("ifAllGranted");
        if (vb != null)
            return (String) vb.getValue(getFacesContext());
        else
            return null;
    }

    public void setIfAllGranted(String ifAllGranted)
    {
        this.ifAllGranted = ifAllGranted;
    }

    public String getIfAnyGranted()
    {
        if (ifAnyGranted != null)
            return ifAnyGranted;

        ValueBinding vb = getValueBinding("ifAnyGranted");
        if (vb != null)
            return (String) vb.getValue(getFacesContext());
        else
            return null;
    }

    public void setIfAnyGranted(String ifAnyGranted)
    {
        this.ifAnyGranted = ifAnyGranted;
    }

    public String getIfNotGranted()
    {
        if (ifNotGranted != null)
            return ifNotGranted;

        ValueBinding vb = getValueBinding("ifNotGranted");
        if (vb != null)
            return (String) vb.getValue(getFacesContext());
        else
            return null;
    }

    public void setIfNotGranted(String ifNotGranted)
    {
        this.ifNotGranted = ifNotGranted;
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = ifAllGranted;
        values[2] = ifAnyGranted;
        values[3] = ifNotGranted;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this.ifAllGranted = (String) values[1];
        this.ifAnyGranted = (String) values[2];
        this.ifNotGranted = (String) values[3];
    }

}