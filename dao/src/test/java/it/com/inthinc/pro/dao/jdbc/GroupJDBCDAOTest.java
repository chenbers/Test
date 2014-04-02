package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.jdbc.GroupJDBCDAO;
import com.inthinc.pro.model.Group;
import it.config.ITDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GroupJDBCDAOTest extends SimpleJdbcDaoSupport {

    static GroupJDBCDAO groupJDBCDAO;
    static int GROUP_ID = 0;
    static int ACCOUNT_ID = 777;

    @BeforeClass
    public static void setupOnce() {
        groupJDBCDAO = new GroupJDBCDAO();
        groupJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        createTestGroup();
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deleteTestGroup(GROUP_ID);
        } catch (Throwable t) {/*ignore*/}
    }

    @Test
    public void getGroupHierarchyTest() throws Exception {
        List<Group> groupList = groupJDBCDAO.getGroupHierarchy(ACCOUNT_ID, GROUP_ID);
        assertTrue(groupList.size() > 0);
        boolean found = false;
        for (Group gr: groupList){
            if (gr.getGroupID().equals(GROUP_ID)){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void getGroupsByAcctIDTest() throws Exception {
        List<Group> groupListByAccount = groupJDBCDAO.getGroupsByAcctID(ACCOUNT_ID);
        assertTrue(groupListByAccount.size() > 0);
        boolean found = false;
        for (Group gr: groupListByAccount){
            if (gr.getGroupID().equals(GROUP_ID)){
                found = true;
                break;
            }
        }
        assertTrue(found);

        boolean onlyMyAccountId = true;
        for (Group gr: groupListByAccount){
            if (!gr.getAccountID().equals(ACCOUNT_ID)){
                onlyMyAccountId = false;
                break;
            }
        }
        assertTrue(onlyMyAccountId);

    }

    @Test
    public void findByIDTest() throws Exception {
        Group groupFindByID = groupJDBCDAO.findByID(GROUP_ID);
        assertNotNull(groupFindByID);
        assertTrue(groupFindByID.getGroupID().equals(GROUP_ID));
        assertEquals(groupFindByID.getPath(),"/0/4/"+GROUP_ID+"/");
    }

    @Test
    public void updateTest() throws Exception {
        Group groupFindByID = groupJDBCDAO.findByID(GROUP_ID);
        assertNotNull(groupFindByID);
        groupFindByID.setName("abc123");
        groupJDBCDAO.update(groupFindByID);
        Group groupFindByID2 = groupJDBCDAO.findByID(GROUP_ID);
        assertNotNull(groupFindByID2);
        assertEquals(groupFindByID2.getName(), "abc123");
    }

    private static void createTestGroup() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("acctID", String.valueOf(ACCOUNT_ID));
        groupJDBCDAO.getSimpleJdbcTemplate().update("insert into groups (acctID, name, `desc`, parentID, status, groupPath, addrID, mapZoom, zoneRev, mapLat, mapLng) values (:acctID, 'test-group-name', 'test-group-desc', 4, 1, '/1/2/3/', 972, 17, 2, '28.065', '-82.3664')", params);
        GROUP_ID = groupJDBCDAO.getSimpleJdbcTemplate().queryForInt("select LAST_INSERT_ID()");
        groupJDBCDAO.updateGroupPathById(GROUP_ID);
    }

    private static void deleteTestGroup(int testGroupId) {
        groupJDBCDAO.deleteByID(testGroupId);
    }

}


