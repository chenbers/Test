/**
 * 
 */
package com.inthinc.pro.service.validation.rules;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.validation.annotations.ValidGroupIDs;

/**
 * Constraint validator for Grou.
 * Implements the validation rules for Locales 
 * 
 * @author dcueva
 */
@Component
public class GroupIDsValidator extends AbstractServiceValidator implements ConstraintValidator<ValidGroupIDs, List<Integer>> {
	
	@SuppressWarnings("unused")
	private static final String DEFAULT_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.message}";
	private static final String NO_GROUP_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.noGroup.message}"; 
	private static final String NO_NEGATIVE_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.noNegative.message}";
	private static final String NOT_IN_HIER_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.notInHierarchy.message}";  
	private static final String NOT_FOUND_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.notFound.message}"; 	
	
	@Autowired
	GroupDAOAdapter groupDAOAdapter;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(ValidGroupIDs validGroupIDsAnnotation) {
		// No need to get info from the annotation here
		// In the future the annotation could take a list of accepted locales
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(List<Integer> groupIDs, ConstraintValidatorContext context) {
		
		if (groupIDs.isEmpty()) return violationTemplate(context, Response.Status.BAD_REQUEST, "", NO_GROUP_TEMPLATE);

        for (Integer groupID : groupIDs) 
            try {
            	if (groupID < 0)  return violationTemplate(context, Response.Status.BAD_REQUEST, groupID, NO_NEGATIVE_TEMPLATE);
            	
            	// Get group from DB
            	Group group = groupDAOAdapter.findByID(groupID);

            	// Group does not exist at all in the back-end
            	if (group == null) return violationTemplate(context, Response.Status.NOT_FOUND, groupID, NOT_FOUND_TEMPLATE);
            	
            } catch (AccessDeniedException e) {
            	// Group is not in user hierarchy
            	return violationTemplate(context, Response.Status.FORBIDDEN, groupID, NOT_IN_HIER_TEMPLATE);
            }            	

        return true;
	}
	// Setter for testing purpose
	void setGroupDAOAdapter(GroupDAOAdapter groupDAOAdapter){
	    this.groupDAOAdapter = groupDAOAdapter;
	}
}
