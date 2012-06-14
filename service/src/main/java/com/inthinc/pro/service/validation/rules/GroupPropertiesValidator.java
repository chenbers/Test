package com.inthinc.pro.service.validation.rules;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper;
import com.inthinc.pro.service.validation.annotations.ValidGroupProperties;

@Component
public class GroupPropertiesValidator extends AbstractServiceValidator implements ConstraintValidator<ValidGroupProperties, Group> {
    @SuppressWarnings("unused")
    // private static final String DEFAULT_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.message}";
    // private static final String NO_GROUP_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.noGroup.message}";
    // private static final String NOT_IN_HIER_TEMPLATE = "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.notInHierarchy.message}";
    // private static final String INVALID_GROUP_ID = "{com.inthinc.pro.service.validation.annotations.ValidGroupID.message}";
    private static final String FORBIDDEN_ACCOUNT_CHANGE = "{com.inthinc.pro.service.validation.annotations.ValidGroupProperties.forbiddenAccountChange.message}";
    private static final String INVALID_MANAGER = "{com.inthinc.pro.service.validation.annotations.ValidGroupProperties.invalidManager.message}";
    private static final String INVALID_PARENT = "{com.inthinc.pro.service.validation.annotations.ValidGroupProperties.invalidParent.message}";
    @Autowired
    GroupDAOAdapter groupDAOAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(ValidGroupProperties validGroupPropertiesAnnotation) {
        // No need to get info from the annotation here
        // In the future the annotation could take a list of accepted locales
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    // public boolean isValid(List<String> groupIDs, ConstraintValidatorContext context) {
    //
    // if (groupIDs.isEmpty())
    // return violationTemplate(context, Response.Status.BAD_REQUEST, "", NO_GROUP_TEMPLATE);
    //
    // for (String groupID : groupIDs) {
    // try {
    // Integer id = Integer.parseInt(groupID);
    //
    // if (id < 0) {
    // return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_GROUP_ID);
    // }
    //
    // try {
    // groupDAOAdapter.findByID(id);
    // } catch (AccessDeniedException e) {
    // return violationTemplate(context, Response.Status.FORBIDDEN, groupID, NOT_IN_HIER_TEMPLATE);
    // }
    // } catch (NumberFormatException e) {
    // return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_GROUP_ID);
    // }
    // }
    //
    // return true;
    // }

    // Setter for testing purpose
    void setGroupDAOAdapter(GroupDAOAdapter groupDAOAdapter) {
        this.groupDAOAdapter = groupDAOAdapter;
    }

    @Override
    public boolean isValid(Group group, ConstraintValidatorContext context) {
        if (group.getGroupID() != null) {
            Group original = groupDAOAdapter.findByID(group.getGroupID());
            // Should check that if the parent id is different that it exists in the
            // same account
            if (!original.getAccountID().equals(group.getAccountID()))
                return violationTemplate(context, Response.Status.FORBIDDEN, FORBIDDEN_ACCOUNT_CHANGE);
        }
        if (!isGoodParent(group)) {
            return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_PARENT);
        }
        if (group.getManagerID() == null)
            return true;
        Person manager = groupDAOAdapter.getPersonByID(group.getManagerID());
        if (manager != null) {
            group.setManagerID(adjustForDeletedManager(manager));
            if (isGoodManager(group.getAccountID(), manager))
                return true;
        }
        return violationTemplate(context, Response.Status.BAD_REQUEST, INVALID_MANAGER);
    }

    private void checkManagersExist(List<Group> list) {
        for (Group group : list) {
            Person manager = groupDAOAdapter.getPersonByID(group.getManagerID());
            if (manager != null) {
                group.setManagerID(adjustForDeletedManager(manager));
            }
        }
    }

    private Integer adjustForDeletedManager(Person manager) {
        return manager.getStatus().equals(com.inthinc.pro.model.Status.DELETED) ? null : manager.getPersonID();

    }

    private boolean isGoodManager(Integer accountID, Person manager) {
        return (manager != null) && (!manager.getStatus().equals(com.inthinc.pro.model.Status.DELETED)) && manager.getAccountID().equals(accountID);
    }

    private boolean isGoodParent(Group group) {
        if (group.getParentID() == null)
            return true;
        Group parent = groupDAOAdapter.findByID(group.getParentID());
        return (parent != null) && parent.getAccountID().equals(group.getAccountID());
    }
}
