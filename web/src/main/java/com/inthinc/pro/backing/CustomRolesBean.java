/**
 * 
 */
package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.Roles;
import com.inthinc.pro.model.security.SiteAccessPoint;
import com.inthinc.pro.util.MessageUtil;
/**
 * @author David Gileadi
 */
public class CustomRolesBean extends BaseAdminBean<CustomRolesBean.CustomRoleView> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0 };
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("roleName");
        
    }
    private RoleDAO roleDAO;
    
    @Override
    protected List<CustomRoleView> loadItems() {
        // get the people
        final Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init(getAccountID());
        roles.removeUneditableRoles(); //hardcoded to admin and normal
        // convert the roles to CustomRoleViews
        final LinkedList<CustomRoleView> items = new LinkedList<CustomRoleView>();
        for (final Role role : roles.getRoleList()) {
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
        customRoleView.initializeAccessPoints();
        customRoleView.setSelectedAccessPointsFromRoleAccessPoints();
        customRoleView.setSelectAllAccessPoints();
          
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
        customRole.initializeAccessPoints();

        return customRole;
    }

    @Override
    public CustomRoleView getItem() {
        final CustomRoleView item = super.getItem();
        return item;
    }

    @Override
    public String save() {
        if (isBatchEdit()) {
        }
        
        final String result = super.save();
        // revert partial-edit changes if user editable
        if (result != null) {
            items = null;
            getItems();
        }               
        
        return result;
    }


    @Override
    protected boolean validateSaveItem(CustomRoleView customRole) {
    	
    	//create the access points from the selected list
    	customRole.setRoleAccessPointsFromSelectedAccessPoints();
    	
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

            //List<Integer, List<Integer>> dependencies = new ArrayList<Integer, List<Integer>>();
            //customRole.contains(accessPointID);
            List<AccessPoint> parentPoints = new ArrayList<AccessPoint>();
            for(AccessPoint as: customRole.getAccessPts()){
                Integer parent = AccessPointEnum.getParent(as.getAccessPtID());
                AccessPoint parentPoint = new AccessPoint(parent, 15);
                if(parent != null && !customRole.contains(parent) && !parentPoints.contains(parentPoint)){
                    parentPoints.add(parentPoint);
                }       
            }
            customRole.addAccessPts(parentPoints);
            
            // insert or update
            if (create){
            	
                customRole.setRoleID(roleDAO.create(getAccountID(), customRole));
            }
           else {
                
        	   roleDAO.update(customRole); 
            }

            final String summary = MessageUtil.formatMessageString(create ? "customRole_added" : "customRole_updated", customRole.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }
    public  enum AccessPointEnum{
        USERSACCESS(null, 2),
        VEHICLESACCESS(null, 3),   
        DEVICESACCESS(null, 4),   
        ZONESACCESS(null, 5),   
        REDFLAGSACCESS(null, 7),   
        REPORTSACCESS(null, 8),   
        ORGANIZATIONACCESS(null, 9),   
        SPEEDBYSTREETACCESS(null, 10),  
        LIVEFLEETACCESS(null, 11),  
        CRASHREPORTACCESS(null, 12),  
        FORGIVEACCESS(null, 13),  
        HOSACCESS(null, 14),  
        WAYSMARTACCESS(null, 15),  
        USEREDITINFO(2,16),  
        DRIVEREDITINFO(2,17),  
        RFIDEDITINFO(2,18),  
        VEHICLESCREATE(3,19),  
        VEHICLESDRIVER(3,20),  
        VEHICLESWIRELINE(3,21),  
        VEHICLESSPEED(3,22),
        HAZARDACCESS(null, 23),
        FORMACCESS(null, 24),
        ;  

        
        private Integer id;
        private Integer parent;
        private AccessPointEnum(Integer parent, Integer id){
            this.parent = parent;
            this.id = id;
        }
        public Integer getID() {
            return id;
        }
        public Integer getParent() {
            return parent;
        }
        private static final Map<Integer, AccessPointEnum> lookup = new HashMap<Integer, AccessPointEnum>();
        static
        {
            for (AccessPointEnum p : EnumSet.allOf(AccessPointEnum.class))
            {
                lookup.put(p.id, p);
            }
        }

        public static Integer getParent(Integer code)
        {
            AccessPointEnum parent =  lookup.get(code);
            if(parent == null){
                logger.error("getParent("+code+") couldn't locate a parent");
                return null;
            }
            return parent.parent;
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
        private LinkedHashMap<Integer,AccessPointView> accessPointSelection;
        private boolean allAccessPointsSelected = false;
        
        @SuppressWarnings("serial")
        public static class AccessPointView extends AccessPoint {

        	
        	public AccessPointView(AccessPoint ap){
        		
        		super(ap.getAccessPtID(),ap.getMode());
        	}
    		public String getMsgKey(){
    			
    			return SiteAccessPoints.getAccessPointById(getAccessPtID()).getMsgKey();
    		}
    		public boolean isSelected(){
    			
    			return getMode() > 0;
    		}
    		public void setSelected(boolean selected){
    			
    			setMode(selected?15:0);
    		}
        }
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
		public void initializeAccessPoints(){
			List<AccessPoint> apList = SiteAccessPoints.getAccessPoints();
			accessPointSelection = new LinkedHashMap<Integer,AccessPointView>();
			for(AccessPoint ap:apList){
			    String msgKey = SiteAccessPoints.getAccessPointById(ap.getAccessPtID()).getMsgKey(); 
			    if (msgKey != null && msgKey.toLowerCase().contains("hos") && !bean.getAccountIsHOS())
			        continue;
                if (msgKey != null && msgKey.toLowerCase().contains("waysmart") && !bean.getAccountIsWaysmart())
                    continue;
                if (msgKey != null && msgKey.toLowerCase().contains("hazards") && !bean.getAccountIsRHAEnabled())
                    continue;
                if (msgKey != null && msgKey.toLowerCase().contains("forms") && !bean.getAccountIsFormsEnabled())
                    continue;
			        
				accessPointSelection.put(ap.getAccessPtID(), new AccessPointView(ap));
			}
		}
		/*
		 * Sets the select all checkbox based on whether all the individual access points are selected
		 */
		public void setSelectAllAccessPoints(){
			
			for (AccessPointView ap: getAccessPointSelection()){
				
				if (ap.getMode()!=15) {
					
					allAccessPointsSelected = false;
					return;
				}
			}
			allAccessPointsSelected = true;
		}
		public void doSelectAllAccessPoints(){
			
			Integer mode = isAllAccessPointsSelected()?15:0;
			
			for (AccessPoint ap: new ArrayList<AccessPoint>(accessPointSelection.values())){
				
				ap.setMode(mode);
			}
		}
		public List<AccessPointView> getAccessPointSelection() {
			return new ArrayList<AccessPointView>(accessPointSelection.values());
		}

		public boolean isAllAccessPointsSelected() {
			
			return allAccessPointsSelected;
		}
		public void setAllAccessPointsSelected(boolean allAccessPointsSelected) {
			this.allAccessPointsSelected = allAccessPointsSelected;
		}
		public void checkAllSelected(){
			
			for (AccessPoint ap: new ArrayList<AccessPoint>(accessPointSelection.values())){
				
				if (ap.getMode() == 0) {
					
					allAccessPointsSelected = false;
					return;
				}
			}
			allAccessPointsSelected = true;
			
		}
		private void setRoleAccessPointsFromSelectedAccessPoints(){
			
			List<AccessPoint> aps = new ArrayList<AccessPoint>();

			for(AccessPoint ap:  new ArrayList<AccessPoint>(accessPointSelection.values())){
				
				if (ap.getMode() > 0) {
					aps.add(ap);
				}
			}
			setAccessPts(aps);
		}
		
		private void setSelectedAccessPointsFromRoleAccessPoints(){
			
			for(AccessPoint ap:getAccessPts()){
				
				if(ap.getMode() == 15) {
					
					if(accessPointSelection.get(ap.getAccessPtID()) != null){
						
						accessPointSelection.get(ap.getAccessPtID()).setMode(15);
					}
				}
			}
		}
	}
	@Override
	protected void doDelete(List<CustomRoleView> deleteItems) {

        final FacesContext context = FacesContext.getCurrentInstance();
        for (final CustomRoleView role : deleteItems) {
            roleDAO.deleteByID(role.getId());
            // add a message
            final String summary = MessageUtil.formatMessageString("customRole_deleted", role.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
		
	}

    public Map<Integer,SiteAccessPoint> getAccessPointMap(){
		
		return SiteAccessPoints.getAccessPointMap();
	}
}
