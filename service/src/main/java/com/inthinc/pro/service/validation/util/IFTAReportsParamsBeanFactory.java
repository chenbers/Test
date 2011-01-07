/**
 * 
 */
package com.inthinc.pro.service.validation.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;
import com.inthinc.pro.util.GroupList;

/**
 * Produces instances of IFTAReportsParamsBean.
 * 
 * @author dcueva
 */
@Component
public class IFTAReportsParamsBeanFactory {

    @Autowired
    private ApplicationContext applicationContext;
	
	/**
	 * Obtains an empty instance of the paramters bean from the application context.
	 * This bean must have the principal alreade autowired.
	 * 
	 * @return parameters bean
	 */
	private IFTAReportsParamsBean getBeanInstanceFromSpring() {
		return (IFTAReportsParamsBean) applicationContext.getBean("IFTAReportsParamsBean");
	}

	/**
	 * Overloaded convenience method.
	 * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	public IFTAReportsParamsBean getBean(Integer groupID, Locale locale, MeasurementType measurementType) {
		return getBean(groupID, null, null, locale, measurementType);	
	}

	/**
	 * Overloaded convenience method.
	 * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	public IFTAReportsParamsBean getBean(List<Integer> groupIDList, Locale locale, MeasurementType measurementType) {
	    return getBean(groupIDList, null, null, locale, measurementType);	
	}
	
	/**
	 * Overloaded convenience method.
	 * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	public IFTAReportsParamsBean getBean(Integer groupID, Date startDate, Date endDate, Locale locale,
			MeasurementType measurementType) {
		List<Integer> groupIDList = new ArrayList<Integer>();
		groupIDList.add(groupID);
		return getBean(groupIDList, startDate, endDate, locale, measurementType);
	}
	
	/**
	 * Overloaded convenience method.
	 * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	public IFTAReportsParamsBean getBean(GroupList groupList, Locale locale, MeasurementType measurementType) {
		return getBean(groupList.getValueList(), null, null, locale, measurementType);	
	}
	
	/**
	 * Overloaded convenience method.
	 * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	public IFTAReportsParamsBean getBean(GroupList groupList, Date startDate, Date endDate, Locale locale,
			MeasurementType measurementType) {
		return getBean(groupList.getValueList(), startDate, endDate, locale, measurementType);
	}
	
    /**
     * Overloaded convenience method.
     * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
     */
    public IFTAReportsParamsBean getBean(Integer groupID, Locale locale) {
        return getBean(groupID, null, null, locale, null);   
    }

    /**
     * Overloaded convenience method.
     * @see #getBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
     */
    public IFTAReportsParamsBean getBean(Integer groupID, Date startDate, Date endDate, Locale locale) {
        return getBean(groupID, null, null, locale, null);   
    }

    /**
	 * Simply fill the bean with the passed parameters.
	 * @return Bean filled with the incoming parameters
	 */
	public IFTAReportsParamsBean getBean(List<Integer> groupIDList, Date startDate, Date endDate, 
			Locale locale, MeasurementType measurementType) {
		
		IFTAReportsParamsBean paramsBean = getBeanInstanceFromSpring();
		paramsBean.setGroupIDList(groupIDList);
		paramsBean.setStartDate(startDate);
		paramsBean.setEndDate(endDate);
		paramsBean.setLocale(parseLocale(locale));
		paramsBean.setMeasurementType(measurementType);
		return paramsBean;
	}

	/**
	 * JAXB and RestEasy do not parse locales coming as fr_CA, and put the whole string as the language.</br>
	 * This method performs a quick parsing assuming the default toString() behavior from {@ Locale}</br>
	 * Example: "en", "de_DE", "_GB", "en_US_WIN", "de__POSIX"
	 * </br></br>
	 * Note: this method will NOT validate the locale. </br>
	 * Following post from <a href="http://stackoverflow.com/questions/3684747/how-to-validate-a-locale-in-java">Stack Overflow</a></br>  
	 * 
	 * @param locale The locale to parse
	 * @return A locale correctly parsed, or the same argument if it cannot be parsed
	 */
	private Locale parseLocale(Locale locale) {
		if (locale == null) return null;
		String[] parts = locale.getLanguage().split("_");
		switch (parts.length) {
		    case 3: return new Locale(parts[0], parts[1], parts[2]);
		    case 2: return new Locale(parts[0], parts[1]);
		    case 1: return new Locale(parts[0]);
			default: return locale; // not parseable
		}
	}	
	
}
