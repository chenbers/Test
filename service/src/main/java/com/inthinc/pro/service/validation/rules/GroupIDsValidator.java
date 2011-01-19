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

import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.validation.annotations.ValidGroupIDs;

/**
 * Constraint validator for Grou. Implements the validation rules for Locales
 * 
 * @author dcueva
 */
@Component
public class GroupIDsValidator extends AbstractServiceValidator implements ConstraintValidator<ValidGroupIDs, List<String>> {

    @SuppressWarnings("unused")
    private static final String DEFAULT_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.message}";
    private static final String NO_GROUP_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.noGroup.message}";
    private static final String NOT_IN_HIER_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.notInHierarchy.message}";
    private static final String INVALID_GROUP_ID = "{com.inthinc.pro.service.validation.annotations.ValidGroupID.message}";

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
    public boolean isValid(List<String> groupIDs, ConstraintValidatorContext context) {

        if (groupIDs.isEmpty())
            return violationTemplate(context, Response.Status.BAD_REQUEST, "", NO_GROUP_TEMPLATE);

        for (String groupID : groupIDs) {
            try {
                Integer id = Integer.parseInt(groupID);

                if (id < 0) {
                    return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_GROUP_ID);
                }

                try {
                    groupDAOAdapter.findByID(id);
                } catch (AccessDeniedException e) {
                    return violationTemplate(context, Response.Status.FORBIDDEN, groupID, NOT_IN_HIER_TEMPLATE);
                }
            } catch (NumberFormatException e) {
                return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_GROUP_ID);
            }
        }

        return true;
    }

    // Setter for testing purpose
    void setGroupDAOAdapter(GroupDAOAdapter groupDAOAdapter) {
        this.groupDAOAdapter = groupDAOAdapter;
    }
}
