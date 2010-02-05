/**
 * 
 */
package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.reports.util.MessageUtil;
/**
 * @author David Gileadi
 */
public class CustomRolesBean extends BaseAdminBean<CustomRolesBean.CustomRoleView> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0 };
    private static final String REQUIRED_KEY = "required";
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("roleName");
    }
    private RoleDAO roleDAO;
    
    @Override
    protected List<CustomRoleView> loadItems() {
        // get the people
        final List<Role> roles = roleDAO.getRoles(getAccountID());
        // convert the people to CustomRoleViews
        final LinkedList<CustomRoleView> items = new LinkedList<CustomRoleView>();
        for (final Role role : roles) {
            if (logger.isDebugEnabled())
                logger.debug("CustomRole Loaded: " + role.getName());
            items.add(createCustomRoleView(role));
        }
        return items;
    }

    /**
     * Creates a CustomRoleView object from the given CustomRole object and score.
     * 
     * @param Role
     *            The CustomRole.
     * @return The new CustomRoleView object.
     */
    private CustomRoleView createCustomRoleView(Role role) {
        final CustomRoleView customRoleView = new CustomRoleView();
        if (logger.isTraceEnabled())
            logger.trace("createCustomRoleView: BEGIN " + role.getName());
        customRoleView.bean = this;
        BeanUtils.copyProperties(role, customRoleView);
         if (logger.isTraceEnabled())
            logger.trace("createCustomRoleView: END " + customRoleView);
        return customRoleView;
    }

    @Override
    public String fieldValue(CustomRoleView customRole, String column) {
        if (column.equals("roleName")) {
            if (customRole.getName() != null)
                return customRole.getName().toLowerCase();
            return null;
        }
         else
            return super.fieldValue(customRole, column);
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public String getColumnLabelPrefix() {
        return "customRoleHeader_";
    }

    @Override
	public List<String> getAvailableColumns() {
	    return AVAILABLE_COLUMNS;
	}

	@Override
    public TableType getTableType() {
        return TableType.ADMIN_CUSTOMROLE;
    }

    @Override
    protected CustomRoleView createAddItem() {
        final CustomRoleView customRole = new CustomRoleView();
        customRole.bean = this;
        customRole.setAcctID(getAccountID());
        return customRole;
    }

    @Override
    public CustomRoleView getItem() {
        final CustomRoleView item = super.getItem();
        return item;
    }

    @Override
    public String save() {
        
        return null;
    }


    @Override
    protected boolean validateSaveItem(CustomRoleView customRole) {
    	
        return true;
    }
    @Override
    protected Boolean authorizeAccess(CustomRoleView item) {
        Integer acctID = item.getAcctID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected CustomRoleView revertItem(CustomRoleView editItem) {
        if (logger.isTraceEnabled())
            logger.trace("revertItem" + editItem);
        return createCustomRoleView(editItem);
//        return createCustomRoleView(roleDAO.getCustomRoleByID(editItem.getRoleID()));
    }

    @Override
    protected void doSave(List<CustomRoleView> saveItems, boolean create) {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final CustomRoleView customRole : saveItems) {

//            final String summary = MessageUtil.formatMessageString(create ? "customRole_added" : "customRole_updated", customRole.getName());
//            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
//            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect() {
        return "pretty:adminCustomRole";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditCustomRole";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminCustomRoles";
    }


    public List<String> findCustomRoles(Object event) {
        final List<String> results = new ArrayList<String>();
        final String name = event.toString().trim().toLowerCase();
        if (name.length() > 0)
            for (final CustomRoleView customRole : getItems())
                if (customRole.getName().toLowerCase().contains(name))
                    results.add(customRole.getName());
        return results;
    }

  	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	public static class CustomRoleView extends Role implements EditItem {
        @Column(updateable = false)
        private static final long serialVersionUID = 8954277815270194338L;
        @Column(updateable = false)
        private CustomRolesBean bean;
        @Column(updateable = false)
        private boolean selected;
		@Override
		public Integer getId() {
			return getRoleID();
		}
		@Override
		public boolean isSelected() {
			return selected;
		}
		@Override
		public void setSelected(boolean selected) {
			this.selected = selected;
		}

	}
	@Override
	protected void doDelete(List<CustomRoleView> deleteItems) {
		// TODO Auto-generated method stub
		
	}
}
