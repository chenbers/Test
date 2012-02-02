package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;
import org.richfaces.model.SwingTreeNodeImpl;

import com.inthinc.pro.model.BaseEntity;
import com.inthinc.pro.model.DOTOfficeType;

public abstract class BaseTreeNodeImpl<T extends BaseEntity> extends SwingTreeNodeImpl implements Serializable, Comparable<BaseTreeNodeImpl>
{
    
    private static final Logger logger = Logger.getLogger(BaseTreeNodeImpl.class);
    
    protected String  label;
    protected Integer id;

    
    public DOTOfficeType getSubType() {
        return null;
    }

    protected T baseEntity;
    
    protected List<BaseTreeNodeImpl> childNodes;
    protected BaseTreeNodeImpl parentNode;
    
    private List<BaseTreeNodeImpl> breadCrumbList;
    
    private TreeNodeType treeNodeType;
    
    public BaseTreeNodeImpl(T baseEntity,BaseTreeNodeImpl parentNode)
    {
       initialize(baseEntity, parentNode);
    }
    
    public BaseTreeNodeImpl(T baseEntity)
    {
       initialize(baseEntity, null);
    }
    
    protected void initialize(T baseEntity, BaseTreeNodeImpl parentNode)
    {
        this.parentNode = parentNode;
        this.baseEntity = baseEntity;
        this.setData(this);
    }
    
    @Override
    public int compareTo(BaseTreeNodeImpl baseTreeNodeImpl)
    {
        if(!this.getTreeNodeType().equals(baseTreeNodeImpl.getTreeNodeType()))
        {
            return this.getTreeNodeType().compareTo(baseTreeNodeImpl.getTreeNodeType());
        } else
        {
            return this.getLabel().compareToIgnoreCase(baseTreeNodeImpl.getLabel());
        }
    }
    
    public TreeNodeType getTreeNodeType()
    {
        if(treeNodeType == null)
        {
            treeNodeType = loadTreeNodeType();
        }
        
        return treeNodeType;
    }
    
    public void setTreeNodeType(TreeNodeType treeNodeType)
    {
        this.treeNodeType = treeNodeType;
    }
    
    /**
     * TreeNode Implementation
     */

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration children()
    {
        return Collections.enumeration(getChildrenNodes());
    }

    /**
     * This is not used by the tree component
     */
    public List<BaseTreeNodeImpl> getChildrenNodes()
    {
        if (childNodes == null)
        {
            childNodes = loadChildNodes();
        }
        return childNodes;
    }
    
    public int getBreadCrumbCount()
    {
        return getGroupBreadCrumb().size();
    }
    
    public List<BaseTreeNodeImpl> getGroupBreadCrumb()
    {
        if (breadCrumbList == null)
        {
            breadCrumbList = new ArrayList<BaseTreeNodeImpl>();
            //We set the number of crumbs to show at 3.
            loadBreadCrumbs(this, breadCrumbList,4);
            Collections.reverse(breadCrumbList);
        }
        return breadCrumbList;
    }

    /**
     * 
     * @param node
     * @param breadCrumbList
     * @param levelIndex - is essentially a counter and indicates how high up the hierarchy we need to walk.
     */
    private void loadBreadCrumbs(BaseTreeNodeImpl node, List<BaseTreeNodeImpl> breadCrumbList,int levelIndex)
    {
        switch (node.getTreeNodeType()) {
        case FLEET:
        case DIVISION:
        case TEAM:
            breadCrumbList.add(node);
        }
        if (node.getParent() != null && node.getParent().getId() != null && node.getBaseEntity() != null && levelIndex > 1)
        {
            loadBreadCrumbs(node.getParent(), breadCrumbList,--levelIndex);
        }
    }
    
    public void setParent(BaseTreeNodeImpl parentNode)
    {
        Integer currentParentId = -1;
        Integer newParentId = null;
        
        if(this.parentNode != null)
        {
            currentParentId = this.parentNode.getId();
        }
        
        if(parentNode != null)
        {
            newParentId = parentNode.getId();
        }

        if (parentNode == null)
        {
            this.parentNode.removeChildNode(this);
            this.parentNode = parentNode;
        }
        else if (parentNode.getBaseEntity() != null && !currentParentId.equals(newParentId))
        {
            parentNode.addChildNode(this);
            if (this.parentNode != null)
            {
                this.parentNode.removeChildNode(this);
            }
            this.parentNode = parentNode;
        }
    }
    
    public void removeChildNode(BaseTreeNodeImpl baseTreeNodeImpl)
    {
        getChildrenNodes().remove(baseTreeNodeImpl);
    }
    
    public void addChildNode(BaseTreeNodeImpl baseTreeNodeImpl)
    {
        getChildrenNodes().add(baseTreeNodeImpl);
    }
    
    @Override
    public BaseTreeNodeImpl getChildAt(int childIndex)
    {
        return getChildrenNodes().get(childIndex);
    }

    @Override
    public int getChildCount()
    {
        return getChildrenNodes().size();
    }

    @Override
    public int getIndex(javax.swing.tree.TreeNode node)
    {
        TreeNodeImpl treeNode = (TreeNodeImpl) node;
        return getChildrenNodes().indexOf(treeNode);
    }
    
    @Override
    public boolean isLeaf()
    { 
        return getChildrenNodes().size() > 0 ? false : true;
        
    }
    
    @Override
    public void addChild(Object key, TreeNode node)
    {
        logger.info("Add Child Called");
        super.addChild(key, node);
    }
    
    public void sortChildren()
    {
        Collections.sort(childNodes);
    }
    
    public abstract TreeNodeType loadTreeNodeType();
    
    protected abstract List<BaseTreeNodeImpl> loadChildNodes();
    
    public abstract BaseTreeNodeImpl getParent();
    
    public abstract boolean getAllowsChildren();
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("ID=");
        sb.append(getId());
        sb.append(", Label=");
        sb.append(getLabel());
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        if (obj instanceof BaseTreeNodeImpl)
        {
            EqualsBuilder eb = new EqualsBuilder().append(this.getId(), ((BaseTreeNodeImpl)obj).getId());
            isEqual = eb.isEquals();
        }
        return isEqual;
    }
    
    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder(5,9);
        hcb.append(this.getId());
        return hcb.toHashCode();
    }
    
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public T getBaseEntity()
    {
        return baseEntity;
    }

    public void setBaseEntity(T baseEntity)
    {
        this.baseEntity = baseEntity;
    }
    
    
    
    
    
    
}
