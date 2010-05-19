package com.inthinc.pro.backing;

//import static com.inthinc.pro.backing.BaseBean.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.json.JSONObject;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
/**
 * TreeNavigationBean
 * 
 * @author jacquiehoward
 * 
 * Backing bean for the drop down tree navigation 
 *
 */
public class TreeNavigationBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	private List<JsTreeNode> recentNodes;

    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    
    private Integer groupID;
    private Group group;
    
    private Integer currentGroupID;
    private String currentGroupName;
    private JsTreeNode currentNode;
    private JsTreeNode navigationTree;
    
    public void init(){
        
        setGroupID(getUser().getGroupID());
        currentGroupName=group.getName();
     }
        
 	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public List<JsTreeNode> getRecentNodes() {
		return recentNodes;
	}

	public void setRecentNodes(List<JsTreeNode> recentNodes) {
		this.recentNodes = recentNodes;
	}
//  [
//  { data : "A node", children : [ { data : "Only child" } ], state : "open" },
//  { data : "Some other node" }
//]

    public JsTreeNode getNavigationTree() {
        return navigationTree;
    }
    
    public void setNavigationTree(JsTreeNode navigationTree) {
        this.navigationTree = navigationTree;
    }
    
    public Integer getGroupID() {
        return groupID;
    }
    
    public void setGroupID(Integer groupID)
    {
        // Lets not load a new group if we don't need to
        if (this.groupID != groupID && groupDAO != null && groupID != 0) // groupID ZERO is for Unknown Driver
        {
            group = getGroupHierarchy().getGroup(groupID);
            
            //build the json tree
            setNavigationTree(new JsTreeNode(group, getGroupHierarchy()));
         }
        this.groupID = groupID;
    
    }
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    public Integer getCurrentGroupID() {
        return currentGroupID;
    }
    
    public void setCurrentGroupID(Integer currentGroupID) {
        this.currentGroupID = currentGroupID;
        currentGroupName = getGroupHierarchy().getGroup(currentGroupID).getName();
    }
    
    public String getCurrentGroupName() {
        return currentGroupName;
    }
    
    public void setCurrentGroupName(String currentGroupName) {
        this.currentGroupName = currentGroupName;
    }
    
    public JsTreeNode getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(JsTreeNode currentNode) {
        this.currentNode = currentNode;
    }

    private void getDriverSubtree()
    {
        FacesContext facesContext =  getFacesContext();
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        JSONObject json = null; // = // Do some logic here
        populateWithJSON(response, json);
        facesContext.responseComplete();
     }

    public void populateWithJSON(HttpServletResponse response,JSONObject json) {
        if(json!=null) {
            response.setContentType("text/x-json;charset=UTF-8");           
            response.setHeader("Cache-Control", "no-cache");
            try {
                 response.getWriter().write(json.toString());
            } catch (IOException e) {
//                throw new Exception("IOException in populateWithJSON", e);
            }                               
        }
    }
	public class JsTreeNode {
		
		private List<JsTreeNode> children;
		private NodeData data;
		private String state;
		private Map<String, String> attributes;
		
		public JsTreeNode(Group group, GroupHierarchy groupHierarchy) {
		    
		    data = new NodeData("<span>"+group.getName()+"</span>",null);
		    data.addAttribute("href","/web/app/dashboard/"+group.getGroupID());
		    data.addAttribute("id", "navigationTree:"+group.getGroupID());
		    attributes = new HashMap<String,String>();
		    switch (group.getType()){
		        case FLEET:
		            attributes.put("rel", "fleet");
		            setState("closed");
		            break;
		        case DIVISION:
                    attributes.put("rel", "group");
                    setState("closed");
                   break;
                case TEAM:
                    attributes.put("rel", "team");
                    setState("none");
                    break;
		    }
		    attributes.put("groupid", group.getGroupID().toString());
		    if (groupHierarchy.getChildren(group)!= null){
		        
    		    List<JsTreeNode> children = new ArrayList<JsTreeNode>();
       		    for (Group childGroup: groupHierarchy.getChildren(group)){
    		        
    		        children.add(new JsTreeNode(childGroup, groupHierarchy));
    		    }
       		    setChildren(children);
		    }
		            
		}
        public List<JsTreeNode> getChildren() {
			return children;
		}
		public void setChildren(List<JsTreeNode> children) {
			this.children = children;
		}
		public NodeData getData() {
			return data;
		}
		public void setData(NodeData data) {
			this.data = data;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public Map<String, String> getAttributes() {
			return attributes;
		}
		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}
	}
	public class NodeData{
	    
	    private String title;
	    private Map<String, String> attributes;
	    
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public Map<String, String> getAttributes() {
            return this.attributes;
        }
        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
	    public void addAttribute(String key, String value){
	        
	        this.attributes.put(key,value);
	    }
        public NodeData(String title, Map<String, String> attributes) {
            
            super();
            this.title = title;
            this.attributes = attributes;
            
            if (this.attributes == null){
                this.attributes = new HashMap<String, String>();
            }
        }
	}
 }
