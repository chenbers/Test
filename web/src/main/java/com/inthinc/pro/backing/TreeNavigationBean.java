package com.inthinc.pro.backing;

//import static com.inthinc.pro.backing.BaseBean.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    
    private String currentGroupName;
    private JsTreeRoot navigationTree;
    
    private final int maxRecentNodes = 3;
    
    public void init(){
        
        setGroupID(getUser().getGroupID());

        currentGroupName=group.getName();
        recentNodes = new ArrayList<JsTreeNode>();
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
    public boolean getRenderRecentNodes(){
        
        return !recentNodes.isEmpty();
    }
    public void setRenderRecentNodes(boolean renderRecentNodes){
        
    }

    public JsTreeRoot getNavigationTree() {
        return navigationTree;
    }
    
    public void setNavigationTree(JsTreeRoot navigationTree) {
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
            setNavigationTree(new JsTreeRoot(group, getGroupHierarchy()));
            openParentPath(group);
        }
        this.groupID = groupID;
    
    }
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public void setCurrentGroupID(Integer currentGroupID) {
        
        currentGroupName = getGroupHierarchy().getGroup(currentGroupID).getName();
        //find node in the tree and set it selected
        navigationTree.setCurrentNode(currentGroupID);
        openParentPath(getGroupHierarchy().getGroup(currentGroupID));
     }
    public String getCurrentGroupName() {
        return currentGroupName;
    }
    
    public void setCurrentGroupName(String currentGroupName) {
        this.currentGroupName = currentGroupName;
    }
    
    private void openParentPath(Group group){
        navigationTree.closeAll();
        Group child = group;
        Group parent = getGroupHierarchy().getParentGroup(group);
        if (parent == null){
            
            navigationTree.open(child.getGroupID());
        }
        else{
            
            while (parent != null){
                
                navigationTree.open(parent.getGroupID());
                child = parent;
                parent = getGroupHierarchy().getParentGroup(parent);
            }
        }
    }
    private void checkRecents(){
        //Make sure that the recent items still exist in the tree
        // And swap to the new node
        List<JsTreeNode> newRecents = new ArrayList<JsTreeNode>();
        for(JsTreeNode recentNode :recentNodes){
            
            JsTreeNode treeNode = navigationTree.findTreeNode(Integer.parseInt(recentNode.getAttributes().get("groupid")));
            if (treeNode != null){
                
                newRecents.add(treeNode);
            }
            recentNodes = newRecents;
        }
    }
    public void refresh(){

        setNavigationTree(new JsTreeRoot(group, getGroupHierarchy()));
        checkRecents();
        openParentPath(group);
    }
//    private void getDriverSubtree()
//    {
//        FacesContext facesContext =  getFacesContext();
//        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
//        JSONObject json = new JSONObject();
//        try{
//            json.append("data", "drivers");
//        }
//        catch (JSONException jsone){
//            
//        }
//        populateWithJSON(response, json);
//        facesContext.responseComplete();
//     }
//
//    public void populateWithJSON(HttpServletResponse response,JSONObject json) {
//        if(json!=null) {
//            response.setContentType("text/x-json;charset=UTF-8");           
//            response.setHeader("Cache-Control", "no-cache");
//            try {
//                 response.getWriter().write(json.toString());
//            } catch (IOException e) {
////                throw new Exception("IOException in populateWithJSON", e);
//            }                               
//        }
//    }
    public class JsTreeRoot {
        
        private Map<Integer, JsTreeNode> navigationTreeMap;
        private JsTreeNode navigationTree;
        private JsTreeNode currentNode;
        
        public JsTreeRoot(Group group, GroupHierarchy groupHierarchy) {
            
            navigationTreeMap = new HashMap<Integer,JsTreeNode>();
            navigationTree = new JsTreeNode(group, groupHierarchy,navigationTreeMap);
         }

         public JsTreeNode getNavigationTree() {
            return navigationTree;
        }

        public void setNavigationTree(JsTreeNode navigationTree) {
            this.navigationTree = navigationTree;
        }
        public void setCurrentNode(Integer groupID){
            
            JsTreeNode nextNode = findTreeNode(groupID);
            
            if (currentNode != null){
                
                currentNode.getData().addAttribute("class", "");
                Iterator<JsTreeNode> it = recentNodes.iterator();
                while(it.hasNext()){
                    
                    JsTreeNode jsTreeNode = it.next();
                    if((jsTreeNode==currentNode) || (jsTreeNode == findTreeNode(groupID))){
                        
                        it.remove(); 
                    }
                }
                if (currentNode != nextNode){
                    
                    recentNodes.add(0, currentNode);
                }
                if (recentNodes.size()>maxRecentNodes){
                    
                    recentNodes.remove(maxRecentNodes);
                }
            }
            //find node in the tree and set it selected
            currentNode = nextNode;
            currentNode.getData().addAttribute("class", "selectedNavigationTreeNode");
            
         
        }
        public void open(Integer groupID){
            
            findTreeNode(groupID).setState("open");
        }
        public void closeAll(){
            
            for(JsTreeNode treeNode:navigationTreeMap.values()){
                
                     treeNode.closeNode();
            }
        }
        private JsTreeNode findTreeNode(Integer groupID){
            
            return navigationTreeMap.get(groupID);
            
//            if (fromHere.getAttributes().get("groupid").equals(groupID.toString())) {
//                
//                return fromHere;
//            }
//            else {
//                
//                if (fromHere.getChildren() == null) return null;
//                
//                for(JsTreeNode child:fromHere.getChildren()){
//                    
//                    JsTreeNode theNode = findTreeNode(child,groupID);
//                    if ( theNode != null) return theNode;
//                }
//            }
//            return null;
        }
       public JsTreeNode getCurrentNode() {
            return currentNode;
        }

        public void setCurrentNode(JsTreeNode currentNode) {
            this.currentNode = currentNode;
        }
    }
	public class JsTreeNode {
		
		private List<JsTreeNode> children;
		private NodeData data;
		private String state;
		private Map<String, String> attributes;
		
		public JsTreeNode(Group group, GroupHierarchy groupHierarchy, Map<Integer,JsTreeNode>navigationTreeMap) {
		    
		    navigationTreeMap.put(group.getGroupID(),this);
		    
		    data = new NodeData(group.getName(),null);
		    data.addAttribute("href",getExternalContext().getRequestContextPath()+"/app/dashboard/"+group.getGroupID());

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
		    attributes.put("id", "navigationTree_"+group.getGroupID());
		    if (groupHierarchy.getChildren(group)!= null){
		        
    		    List<JsTreeNode> children = new ArrayList<JsTreeNode>();
       		    for (Group childGroup: groupHierarchy.getChildren(group)){
    		        
    		        children.add(new JsTreeNode(childGroup, groupHierarchy, navigationTreeMap));
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
		public boolean hasChildren(){
		    
		    return !((children == null) || (children.isEmpty()));
		}
		public void closeNode(){
		    
            if (hasChildren()){
                
                setState("closed");
            }
            else {
                
                setState("none");
            }

		}
		public String getFullPath(){
		    
		    String fullName = getGroupHierarchy().getFullGroupName(Integer.parseInt(getAttributes().get("groupid")));
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
            }
            return fullName;
		}
		public String getType(){
		    
		    return attributes.get("rel");
		}
		public String getImage(){
		    
		   if(attributes.get("rel").equals("fleet")) return "/images/ico_truck.png";
		   else if (attributes.get("rel").equals("group")) return "/images/ico_trucks.png";
           else if (attributes.get("rel").equals("team")) return "/images/ico_team.png";
		   return "";
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
