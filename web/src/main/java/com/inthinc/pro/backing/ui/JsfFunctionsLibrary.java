package com.inthinc.pro.backing.ui;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.facelets.tag.AbstractTagLibrary;

public class JsfFunctionsLibrary extends AbstractTagLibrary
{
    /** Namespace used to import this library in Facelets pages  */
    public static final String NAMESPACE = "http://pro.tiwi.com/jsffunctions";

    /**  Current instance of library. */
    public static final JsfFunctionsLibrary INSTANCE = new JsfFunctionsLibrary();
    
    public JsfFunctionsLibrary() {
        super(NAMESPACE);

        try {
            Method[] methods = JsfFunctions.class.getMethods();

            for (int i = 0; i < methods.length; i++) {
                if (Modifier.isStatic(methods[i].getModifiers())) {
                    this.addFunction(methods[i].getName(), methods[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
