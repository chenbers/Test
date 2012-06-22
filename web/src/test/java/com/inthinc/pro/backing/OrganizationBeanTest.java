package com.inthinc.pro.backing;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.richfaces.component.UITree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;

import com.inthinc.pro.backing.model.BaseTreeNodeImpl;
import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;

public class OrganizationBeanTest extends BaseBeanTest
{
    private OrganizationBean organizationBean;
    private TreeNavigationBean treeNavigationBean;
    private static Map<String, Object> conversationContext = new HashMap<String, Object>();//Mimics the t:saveState 

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    private void setup()
    {
        loginUser("custom101");
        organizationBean = (OrganizationBean) applicationContext.getBean("organizationBean");
        treeNavigationBean = (TreeNavigationBean)applicationContext.getBean("treeNavigationBean");
        
        Integer  groupID = getUser().getGroupID();

        treeNavigationBean.setCurrentGroupID(groupID);

    }

    @Test
    public void testOrganizationBeanEdit()
    {
        setup();
        GroupTreeNodeImpl treeNode = organizationBean.getRootGroupNode();
        organizationBean.getSelectedPerson();

        assertNotNull(treeNode);

        organizationBean.edit();

        organizationBean.getParentGroups();
        organizationBean.getPeopleSelectItems();
        organizationBean.getGroupTypeList();
        
        Group selectedGroup = organizationBean.getTempGroupTreeNode().getGroup();
        selectedGroup.setAccountID(1);
        selectedGroup.setDescription("Test EDIT");
        selectedGroup.setType(GroupType.DIVISION);
        selectedGroup.setMapCenter(new LatLng(0, 0));
        selectedGroup.setMapZoom(5);
        selectedGroup.setManagerID(220);
        
        organizationBean.setSelectedParentGroupID(selectedGroup.getGroupID()); //Causes validation to fail. Tests will still pass though
        organizationBean.update();
    }

    @Test
    public void testOrganizationBeanAdd()
    {
        setup();
        GroupTreeNodeImpl treeNode = organizationBean.getRootGroupNode();
        organizationBean.setRootGroupNode(treeNode);

        assertNotNull(treeNode);

        organizationBean.add();

        organizationBean.getParentGroups();
        organizationBean.getPeopleSelectItems();
        organizationBean.getGroupTypeList();

        Group selectedGroup = organizationBean.getTempGroupTreeNode().getGroup();
        selectedGroup.setAccountID(1);
        selectedGroup.setDescription("Test ADD");
        selectedGroup.setType(GroupType.DIVISION);
        selectedGroup.setMapCenter(new LatLng(0, 0));
        selectedGroup.setMapZoom(5);
        selectedGroup.setManagerID(220);
        organizationBean.setSelectedParentGroupID(treeNode.getGroup().getGroupID());
        //assertEquals(treeNode, organizationBean.getTopLevelNodes());
        organizationBean.save();
        assertEquals(treeNode, organizationBean.getRootGroupNode());
        
        //Reset for other tests
        organizationBean.setSelectedGroupNode(treeNode);
    }

    /*
     * There was a bug specifically releated to this process. This unit test should protect it from occuring again.
     */
    @Test
    public void testOrganizationBeanAddEdit()
    {
        setup();
        
        //Request Add
        GroupTreeNodeImpl topLevelTreeNode = organizationBean.getRootGroupNode();
        organizationBean.setRootGroupNode(topLevelTreeNode);
        //We should be adding to the the root node here.
        organizationBean.add();
        GroupTreeNodeImpl inProgressNodeImpl = organizationBean.getTempGroupTreeNode();
        saveState(organizationBean);
        
        //Request Save
        Group selectedGroup = inProgressNodeImpl.getGroup();
        selectedGroup.setAccountID(1);
        selectedGroup.setDescription("Test");
        selectedGroup.setName("Test");
        selectedGroup.setType(GroupType.DIVISION);
        selectedGroup.setMapCenter(new LatLng(0, 0));
        selectedGroup.setMapZoom(5);
        selectedGroup.setManagerID(220);
        organizationBean = (OrganizationBean) applicationContext.getBean("organizationBean");
        restoreState(organizationBean);
        assertEquals(topLevelTreeNode, organizationBean.getRootGroupNode());
        
        organizationBean.save();
       
        assertNull(organizationBean.getTempGroupTreeNode());
        assertNull(organizationBean.getSelectedParentGroupID());
        assertEquals(topLevelTreeNode,organizationBean.getSelectedGroupNode().getParent());
        assertEquals("Test",organizationBean.getSelectedGroupNode().getLabel());
        saveState(organizationBean);
       
        //Request Edit
        organizationBean = (OrganizationBean) applicationContext.getBean("organizationBean");
        restoreState(organizationBean);
        organizationBean.edit();
        GroupTreeNodeImpl selectedNodeImpl = organizationBean.getSelectedGroupNode();
        inProgressNodeImpl = organizationBean.getTempGroupTreeNode();
        assertEquals(topLevelTreeNode, organizationBean.getRootGroupNode());
        saveState(organizationBean);
        
        //Request Update
        Group selectedGroupForEdit = inProgressNodeImpl.getGroup();
        selectedGroupForEdit.setAccountID(1);
        selectedGroupForEdit.setDescription("Test 4");
        selectedGroupForEdit.setName("Test 4");
        selectedGroupForEdit.setType(GroupType.DIVISION);
        selectedGroupForEdit.setMapCenter(new LatLng(0, 0));
        selectedGroupForEdit.setMapZoom(5);
        selectedGroupForEdit.setManagerID(220);
        
        organizationBean = (OrganizationBean) applicationContext.getBean("organizationBean");
        restoreState(organizationBean);
        assertEquals(topLevelTreeNode, organizationBean.getRootGroupNode());
        
        organizationBean.update();
        assertEquals(selectedNodeImpl, organizationBean.getSelectedGroupNode()); //Theese should be the same.
        assertEquals("Test 4", organizationBean.getSelectedGroupNode().getGroup().getName()); //Theese should be the same.
        
        
        
        
        //organizationBean.setSelectedParentGroup(treeNode.getGroup());
         
    }
    
    private void saveState(OrganizationBean organizationBean)
    {
      //saveState
        conversationContext.put("topLevelNodes",organizationBean.getRootGroupNode());
        conversationContext.put("selectedGroupNode",organizationBean.getSelectedGroupNode());
        conversationContext.put("inProgressGroupNode",organizationBean.getTempGroupTreeNode());
        conversationContext.put("selectedParentGroupID",organizationBean.getSelectedParentGroupID());
        conversationContext.put("treeStateMap",organizationBean.getTreeStateMap());
        conversationContext.put("organizationHierarchy",organizationBean.getOrganizationHierarchy());
    }
    
    private void restoreState(OrganizationBean organizationBean)
    {
        organizationBean.setRootGroupNode((GroupTreeNodeImpl)conversationContext.get("topLevelNodes"));
        organizationBean.setSelectedGroupNode((GroupTreeNodeImpl)conversationContext.get("selectedGroupNode"));
        organizationBean.setTempGroupTreeNode((GroupTreeNodeImpl)conversationContext.get("inProgressGroupNode"));
        organizationBean.setSelectedParentGroupID((Integer)conversationContext.get("selectedParentGroupID"));
        organizationBean.setTreeStateMap((Map)conversationContext.get("treeStateMap"));
        organizationBean.setOrganizationHierarchy((GroupHierarchy)conversationContext.get("organizationHierarchy"));
    }

    @Test
    @Ignore
    public void testTreeViewListeners()
    {
        setup();
        GroupTreeNodeImpl treeNode = organizationBean.getRootGroupNode();
        organizationBean.setRootGroupNode(treeNode);

        assertNotNull(treeNode);

        // Test select node;
        UITree uiComponent = createMock(UITree.class);
        BaseTreeNodeImpl childNode = organizationBean.getSelectedGroupNode().getChildAt(0);
        expect(uiComponent.getRowData()).andReturn(childNode).anyTimes();
        replay(uiComponent);
        NodeSelectedEvent nodeSelectEvent = new NodeSelectedEvent(uiComponent, null);
        organizationBean.selectNode(nodeSelectEvent);
        assertEquals(childNode, organizationBean.getSelectedGroupNode());

        // Process Drop
        BaseTreeNodeImpl dragNode = organizationBean.getSelectedGroupNode().getChildAt(0);
        GroupTreeNodeImpl dropNode = organizationBean.getSelectedGroupNode();
        DropEvent dropEvent = createMock(DropEvent.class);
        expect(dropEvent.getDragValue()).andReturn(dragNode).times(1).andReturn(dropNode).times(1).andReturn(dragNode).times(1);
        expect(dropEvent.getDropValue()).andReturn(dragNode).times(2).andReturn(dropNode).anyTimes();
        replay(dropEvent);
        organizationBean.processDrop(dropEvent);
        organizationBean.processDrop(dropEvent);
        organizationBean.processDrop(dropEvent);
        assertEquals(dragNode.getParent(), dropNode);
    }

}
