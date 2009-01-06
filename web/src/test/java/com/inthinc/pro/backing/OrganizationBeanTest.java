package com.inthinc.pro.backing;

import static org.easymock.classextension.EasyMock.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.richfaces.component.UITree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;

import com.inthinc.pro.backing.model.TreeNodeImpl;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;

public class OrganizationBeanTest extends BaseBeanTest
{
    private static OrganizationBean organizationBean;
    

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
        loginUser("superuser101");
        organizationBean = (OrganizationBean)applicationContext.getBean("organizationBean");
    }
    
    @Test
    public void testOrganizationBeanEdit(){
        setup();
        TreeNodeImpl treeNode = organizationBean.getTopLevelNodes();
        organizationBean.getSelectedPerson();
        
        assertNotNull(treeNode);
        
        organizationBean.edit();
        
        organizationBean.getParentGroups();
        organizationBean.getPeopleSelectItems();
        organizationBean.getGroupTypeList();
        
        Group selectedGroup = organizationBean.getInProgressGroupNode().getGroup();
        selectedGroup.setAccountID(1);
        selectedGroup.setDescription("Test");
        selectedGroup.setType(GroupType.DIVISION);
        selectedGroup.setMapCenter(new LatLng(0,0));
        selectedGroup.setMapZoom(5);
        selectedGroup.setManagerID(220);
        
        organizationBean.update();
    }
    
    @Test
    public void testOrganizationBeanAdd(){
        setup();
        TreeNodeImpl treeNode = organizationBean.getTopLevelNodes();
        organizationBean.setTopLevelNodes(treeNode);
        
        assertNotNull(treeNode);
        
        organizationBean.add();
        
        organizationBean.getParentGroups();
        organizationBean.getPeopleSelectItems();
        organizationBean.getGroupTypeList();
        
        Group selectedGroup = organizationBean.getInProgressGroupNode().getGroup();
        selectedGroup.setAccountID(1);
        selectedGroup.setDescription("Test");
        selectedGroup.setType(GroupType.DIVISION);
        selectedGroup.setMapCenter(new LatLng(0,0));
        selectedGroup.setMapZoom(5);
        selectedGroup.setManagerID(220);
        organizationBean.setSelectedParentGroup(treeNode.getGroup());
        
        
        organizationBean.save();
    }
    
    @Test
    public void testTreeViewListeners(){
        setup();
        TreeNodeImpl treeNode = organizationBean.getTopLevelNodes();
        organizationBean.setTopLevelNodes(treeNode);
        
        assertNotNull(treeNode);
        
        //Test select node;
        UITree uiComponent = createMock(UITree.class);
        TreeNodeImpl childNode = organizationBean.getSelectedGroupNode().getChildAt(0);
        expect(uiComponent.getRowData()).andReturn(childNode).anyTimes();
        replay(uiComponent);
        NodeSelectedEvent nodeSelectEvent = new NodeSelectedEvent(uiComponent,null);
        organizationBean.selectNode(nodeSelectEvent);
        assertEquals(childNode, organizationBean.getSelectedGroupNode());
        
        //Process Drop
        TreeNodeImpl dragNode = organizationBean.getSelectedGroupNode().getChildAt(0);
        TreeNodeImpl dropNode = organizationBean.getSelectedGroupNode();
        DropEvent dropEvent = createMock(DropEvent.class);
        expect(dropEvent.getDragValue()).andReturn(dragNode).times(1)
            .andReturn(dropNode).times(1).andReturn(dragNode).times(1);
        expect(dropEvent.getDropValue()).andReturn(dragNode).times(2)
            .andReturn(dropNode).anyTimes();
        replay(dropEvent);
        organizationBean.processDrop(dropEvent);
        organizationBean.processDrop(dropEvent);
        organizationBean.processDrop(dropEvent);
        assertEquals(dragNode.getParent(), dropNode);
        
        
    }
    
    @Test
    public void testLoadTree(){
        setup();
        TreeNodeImpl treeNode = organizationBean.getTopLevelNodes();
        organizationBean.setTopLevelNodes(treeNode);
        
        
        for(int i = 0;i<4;i++){
            if(treeNode.getAllowsChildren()){
                treeNode = treeNode.getChildAt(0);
            }
        }
        
    }
    
    
}
