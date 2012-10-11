package com.inthinc.pro.table;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;

import org.richfaces.component.html.HtmlColumn;
import org.richfaces.model.FilterField;


public class UIFilterColumn extends HtmlColumn {


	@Override
	public FilterField getFilterField(){
		
		
		ValueExpression filterBy = getValueExpression("custFilterBy");

		if (filterBy != null) {
			Object filterObject = getCustFilterValue();
			return new CustomFilterField(filterBy, filterObject);
		}
		
		FilterField field = super.getFilterField();
		
		
		return field;
	}

	public Object getCustFilterValue(){
		ValueExpression ve = getValueExpression("custFilterValue");
		if (ve != null) {
		    Object value = null;
		    try {
				value = ve.getValue(getFacesContext().getELContext());
		    } catch (ELException e) {
				throw new FacesException(e);
		    } 
		    return value;
		} 
		return super.getFilterValue();
	}

}
