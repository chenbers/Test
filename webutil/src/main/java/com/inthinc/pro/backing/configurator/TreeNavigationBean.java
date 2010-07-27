package com.inthinc.pro.backing.configurator;

//import static com.inthinc.pro.backing.BaseBean.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.GroupDAO;
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
	
    private GroupDAO groupDAO;

    private Integer groupID;
    private Group group;
    
    private String currentGroupName;
    private JsTreeRoot navigationTree;
    
    private List<String> selectedNodeChildren = new ArrayList<String>();  
    private String nodeTitle;
    
    public void init(){
        
        if(getUser()!= null){
            
            buildTreeFrom(getUser().getGroupID());

            currentGroupName=group.getName();
        }
     }
        
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
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
    
    public void buildTreeFrom(Integer groupID)
    {
        // Lets not load a new group if we don't need to
        if (this.groupID != groupID && groupDAO != null && groupID != 0) // groupID ZERO is for Unknown Driver
        {
            group = getGroupHierarchy().getGroup(groupID);
            
            //build the tree
            setNavigationTree(new JsTreeRoot(group, getGroupHierarchy()));
            openParentPath(group);
        }
        this.groupID = groupID;
    
    }
    public List<String> getSelectedNodeChildren() {
        return selectedNodeChildren;
    }
    public void setSelectedNodeChildren(List<String> selectedNodeChildren) {
        this.selectedNodeChildren = selectedNodeChildren;
    }
    public String getNodeTitle() {
        return nodeTitle;
    }
    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public void setCurrentGroupID(Integer currentGroupID) {
        
        if(groupID==null){

            buildTreeFrom(getUser().getGroupID());
        }
        currentGroupName = getGroupHierarchy().getGroup(currentGroupID).getName();
        //find node in the tree and set it selected
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
        
        if (group == null) return;
        
        Group child = group;
        Group parent = getGroupHierarchy().getParentGroup(group);
        if (parent == null){
            
            navigationTree.open(child.getGroupID());
        }
        else{
            
            while (parent != null){
                
                navigationTree.open(parent.getGroupID());
                parent = getGroupHierarchy().getParentGroup(parent);
            }
        }
    }

    public void refresh(){

        setNavigationTree(new JsTreeRoot(group, getGroupHierarchy()));
        openParentPath(group);
    }
    public class JsTreeRoot {
        
        private Map<Integer, NavigationTreeNode<NodeData>> navigationTreeMap;
        private NavigationTreeNode<NodeData> navigationTree;
        
        public JsTreeRoot(Group group, GroupHierarchy groupHierarchy) {
            
            navigationTreeMap = new HashMap<Integer,NavigationTreeNode<NodeData>>();
            navigationTree = new NavigationTreeNode<NodeData>();
            buildTree(group, groupHierarchy, navigationTree);
         }

         public NavigationTreeNode<NodeData> getNavigationTree() {
            return navigationTree;
        }

        public void setNavigationTree(NavigationTreeNode<NodeData> navigationTree) {
            this.navigationTree = navigationTree;
        }
        public void open(Integer groupID){
            
            if (groupID == null) return;
            NavigationTreeNode<NodeData> jsTreeNode = findTreeNode(groupID);
            if (jsTreeNode == null) return;
            
            jsTreeNode.setState("open");
        }
        public void closeAll(){
            
            for(NavigationTreeNode<NodeData> treeNode:navigationTreeMap.values()){
                
                     treeNode.closeNode();
            }
        }
        private NavigationTreeNode<NodeData> findTreeNode(Integer groupID){
            
            if (groupID == null) return null;

            return navigationTreeMap.get(groupID);
            
        }
        public void buildTree(Group group, GroupHierarchy groupHierarchy, NavigationTreeNode<NodeData> parentNode) {
            
            NavigationTreeNode<NodeData> treeNode = new NavigationTreeNode<NodeData>();
            
            navigationTreeMap.put(group.getGroupID(),treeNode);
            
            NodeData data = new NodeData(group.getName(),null);

            Map<String, String> attributes = new HashMap<String,String>();
            switch (group.getType()){
                case FLEET:
                    data.addAttribute("rel", "fleet");
                    break;
                case DIVISION:
                    data.addAttribute("rel", "group");
                   break;
                case TEAM:
                    data.addAttribute("rel", "team");
                    break;
            }
            data.addAttribute("groupid", group.getGroupID().toString());
            data.addAttribute("id", "navigationTree_"+group.getGroupID());
            
            treeNode.setData(data);
            treeNode.setAttributes(attributes);
            parentNode.addChild(group.getGroupID(), treeNode);
            
            if (groupHierarchy.getChildren(group)!= null){
                
                for (Group childGroup: groupHierarchy.getChildren(group)){
                    
                     buildTree(childGroup,groupHierarchy,treeNode);
                }
            }
        }

    }
	public class NavigationTreeNode<T extends NodeData> implements TreeNode<T>{
		
        private static final long serialVersionUID = 1L;
        private T data;
        private TreeNode<T> parent;
        
        private Map<Object, TreeNode<T>> childrenMap = 
            new LinkedHashMap<Object, TreeNode<T>>();

		private String state;
		private Map<String, String> attributes;
		
		@Override
		public T getData() {
			return data;
		}
        @Override
		public void setData(T data) {
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
		    
		    return !((childrenMap == null) || (childrenMap.isEmpty()));
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
        @Override
        public void addChild(Object identifier, TreeNode<T> child) {
 
            child.setParent(this);
            childrenMap.put(identifier, child);
        }
        @Override
        public TreeNode<T> getChild(Object id) {
 
            return childrenMap.get(id);
        }
        @Override
        public TreeNode<T> getParent() {

            return parent;
        }
        @Override
        public boolean isLeaf() {
 
            return !hasChildren();
        }
        @Override
        public void removeChild(Object id) {
 
            childrenMap.remove(id);
        }
        @Override
        public void setParent(TreeNode<T> parent) {

            this.parent = parent;
        }
        @Override
        public Iterator<Entry<Object, TreeNode<T>>> getChildren() {

            return childrenMap.entrySet().iterator();

        }
        public String toString() {
            return data.toString();
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
        public String getType(){
            
            return attributes.get("rel");
        }

        @Override
        public String toString() {
            return title;
        }
	}
 }
