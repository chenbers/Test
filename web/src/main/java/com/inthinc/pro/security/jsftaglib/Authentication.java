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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

/**
 * @author Cagatay Civici
 * @author Kenan Sevindik
 * 
 * Component that displayes user info
 */
public class Authentication extends UIComponentBase
{

    public static final String COMPONENT_TYPE = "com.inthinc.pro.security.jsftaglib.Authentication";

    public static final String COMPONENT_FAMILY = "com.inthinc.pro.security.jsftaglib";

    private String operation;

    public Authentication()
    {
        super();
        setRendererType(null);
    }

    public void encodeBegin(FacesContext context) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        String result = "Unsupported Authentication object operation...";
        String operation = getOperation();
        if (operation != null && operation.equals("username"))
        {
            result = SecurityJsfUtils.getRemoteUser();
        }

        writer.write(result);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getOperation()
    {
        if (operation != null)
            return operation;

        ValueBinding vb = getValueBinding("operation");
        if (vb != null)
            return (String) vb.getValue(getFacesContext());
        else
            return null;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = operation;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this.operation = (String) values[1];
    }
}