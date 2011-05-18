/*
 * Created on Feb 8, 2007
 *
 */
package com.inthinc.pro.backing.ui.component;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

/**
 * @author srijeeb
 *
 */
public class HTMLCustomSelectOneRadioTag extends UIComponentELTag {

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "customSelectOneRadio";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return "customSelectOneRadio.renderer";
	}

	private String name = null;
	private String value = null;
	private String styleClass = null;
	private String style = null;
	private String disabled = null;
	private String itemLabel = null;
	private String itemValue = null;
	private String onClick = null;
	private String onMouseOver = null;
	private String onMouseOut = null;
	private String onFocus = null;
	private String onBlur = null;
	private String overrideName = null;
	
	
	/**
	 * @return
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * @return
	 */
	public String getItemLabel() {
		return itemLabel;
	}

	/**
	 * @return
	 */
	public String getItemValue() {
		return itemValue;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getOnBlur() {
		return onBlur;
	}

	/**
	 * @return
	 */
	public String getOnClick() {
		return onClick;
	}

	/**
	 * @return
	 */
	public String getOnFocus() {
		return onFocus;
	}

	/**
	 * @return
	 */
	public String getOnMouseOut() {
		return onMouseOut;
	}

	/**
	 * @return
	 */
	public String getOnMouseOver() {
		return onMouseOver;
	}

	/**
	 * @return
	 */
	public String getOverrideName() {
		return overrideName;
	}

	/**
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @return
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setDisabled(String string) {
		disabled = string;
	}

	/**
	 * @param string
	 */
	public void setItemLabel(String string) {
		itemLabel = string;
	}

	/**
	 * @param string
	 */
	public void setItemValue(String string) {
		itemValue = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setOnBlur(String string) {
		onBlur = string;
	}

	/**
	 * @param string
	 */
	public void setOnClick(String string) {
		onClick = string;
	}

	/**
	 * @param string
	 */
	public void setOnFocus(String string) {
		onFocus = string;
	}

	/**
	 * @param string
	 */
	public void setOnMouseOut(String string) {
		onMouseOut = string;
	}

	/**
	 * @param string
	 */
	public void setOnMouseOver(String string) {
		onMouseOver = string;
	}

	/**
	 * @param string
	 */
	public void setOverrideName(String string) {
		overrideName = string;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		style = string;
	}

	/**
	 * @param string
	 */
	public void setStyleClass(String string) {
		styleClass = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		UICustomSelectOneRadio aUICustomSelectOneRadio 
			= (UICustomSelectOneRadio) component;

		if (name != null) {
				aUICustomSelectOneRadio.setName(name);
		}

		if (value != null) {
				aUICustomSelectOneRadio.setValue(value);
		}		
		if (styleClass != null) {
				aUICustomSelectOneRadio.setStyleClass(styleClass);
		}
		if (style != null) {
				aUICustomSelectOneRadio.setStyle(style);
		}
		if (disabled != null) {
				aUICustomSelectOneRadio.setDisabled(disabled);
		}
		if (itemLabel != null) {
				aUICustomSelectOneRadio.setItemLabel(itemLabel);
		}
		if (itemValue != null) {
				aUICustomSelectOneRadio.setItemValue(itemValue);
		}		
		if (onClick != null) {
				aUICustomSelectOneRadio.setOnClick(onClick);
		}		
		if (onMouseOver != null) {
				aUICustomSelectOneRadio.setOnMouseOver(onMouseOver);
		}		
		if (onMouseOut != null) {
				aUICustomSelectOneRadio.setOnMouseOut(onMouseOut);
		}		
		if (onFocus != null) {
				aUICustomSelectOneRadio.setOnFocus(onFocus);
		}			
		if (onBlur != null) {
				aUICustomSelectOneRadio.setOnBlur(onBlur);
		}

		if (overrideName != null) {
				aUICustomSelectOneRadio.setOverrideName(overrideName);
		}		
	}
//	public ValueBinding getValueBinding(String valueRef) {
//		ApplicationFactory af =
//			(ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
//		Application a = af.getApplication();
//		ExpressionFactory expressionFactory = new ExpressionFactory();
//		return (a.createValueBinding(valueRef));
//	}
}
