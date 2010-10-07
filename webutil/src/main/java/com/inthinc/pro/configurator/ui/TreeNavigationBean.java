package com.inthinc.pro.configurator.ui;

//import static com.inthinc.pro.backing.BaseBean.logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
/**
 * TreeNavigationBean
 * 
 * @author jacquiehoward
 * 
 * Backing bean for the drop down tree navigation 
 *
 */
public class TreeNavigationBean extends UsesBaseBean{

	private static final long serialVersionUID = 1L;
    private GroupDAO groupDAO;

    private GroupHierarchy groupHierarchy;
	private Integer groupID;
    private Group group;
    
    private NavigationTreeNode<NodeData> navigationTree;
    
    public void init(){
        
        if(getBaseBean().getUser()!= null){
            
        	groupHierarchy = getBaseBean().getGroupHierarchy();
        	groupID = getBaseBean().getUser().getGroupID();
        	buildTreeFromGroupID();
        }
    }
        
    public GroupHierarchy getGroupHierarchy() {
		return groupHierarchy;
	}

	public void setGroupHierarchy(GroupHierarchy groupHierarchy) {
		this.groupHierarchy = groupHierarchy;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

    public NavigationTreeNode<NodeData> getNavigationTree() {
        return navigationTree;
    }
    
    public void setNavigationTree(NavigationTreeNode<NodeData> navigationTree) {
        this.navigationTree = navigationTree;
    }
    
    public Integer getGroupID() {
        return groupID;
    }
    
    public Object buildTreeFromGroupID()
    {
        // Lets not load a new group if we don't need to
        if (groupDAO != null && groupID != 0) // groupID ZERO is for Unknown Driver
        {
            group = groupHierarchy.getGroup(groupID);
            
            //build the tree
            navigationTree = new NavigationTreeNode<NodeData>();
            buildTree(group, groupHierarchy, navigationTree);
        }
        return null;
    }
    public void buildTree(Group group, GroupHierarchy groupHierarchy, NavigationTreeNode<NodeData> parentNode) {
    	
        NavigationTreeNode<NodeData> treeNode = new NavigationTreeNode<NodeData>();
        
        treeNode.setData(createNodeData(group));
        
        parentNode.addChild(group.getGroupID(), treeNode);
        
        buildSubTrees(group, groupHierarchy,treeNode);
    }
    private NodeData createNodeData(Group group){
    	
        NodeData nodeData = new NodeData();
        
        nodeData.setTitle(group.getName());
        switch (group.getType()){
            case FLEET:
                nodeData.setType("fleet");
                break;
            case DIVISION:
            	nodeData.setType("group");
               break;
            case TEAM:
            	nodeData.setType("team");
                break;
        }
        nodeData.setId(group.getGroupID());
//        nodeData.addAttribute("id", "navigationTree_"+group.getGroupID());
        
        return nodeData;

    }
    private void buildSubTrees(Group group, GroupHierarchy groupHierarchy, NavigationTreeNode<NodeData> treeNode){
    	
        if (groupHierarchy.getChildren(group)!= null){
            
            for (Group childGroup: groupHierarchy.getChildren(group)){
                
                 buildTree(childGroup,groupHierarchy,treeNode);
            }
        }
    }
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public void refresh(Integer accountID){
		
		List<Group> groups = groupDAO.getGroupsByAcctID(accountID);
		GroupHierarchy groupHierarchy= new GroupHierarchy(groups);
		setGroupID(groupHierarchy.getTopGroup().getGroupID());
		setGroupHierarchy(groupHierarchy);
		buildTreeFromGroupID();

	}
    public String getSelectedGroupHierarchy(Integer groupID){
    	return groupHierarchy.getFullGroupName(groupID);
    }

	public class NavigationTreeNode<T> implements TreeNode<T>{
		
        private static final long serialVersionUID = 1L;
        private T data;
        private TreeNode<T> parent;
        
        private Map<Object, TreeNode<T>> childrenMap = 
            new LinkedHashMap<Object, TreeNode<T>>();
		
		@Override
		public T getData() {
			return data;
		}
        @Override
		public void setData(T data) {
			this.data = data;
		}
		public boolean hasChildren(){
		    
		    return !((childrenMap == null) || (childrenMap.isEmpty()));
		}
//		public String getFullPath(){
//		    
//		    String fullName = getGroupHierarchy().getFullGroupName(Integer.parseInt(getAttributes().get("groupid")));
//            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
//                fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
//            }
//            return fullName;
//		}
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
	    
		private Integer id;

		private Map<String, String> attributes;
	    
	    public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
        public Map<String, String> getAttributes() {
            return this.attributes;
        }
	    public void addAttribute(String key, String value){
	        
	        this.attributes.put(key,value);
	    }
        public NodeData() {
            
        	this.attributes = new HashMap<String, String>();
        }
        public void setTitle(String title){
        	
            attributes.put("title", title);
        }
        public String getTitle(){
            
            return attributes.get("title");
        }
        public void setType(String type){
        	
        	attributes.put("type", type);
        }
        public String getType(){
            
            return attributes.get("type");
        }

        @Override
        public String toString() {
            return attributes.get("title");
        }
	} }
