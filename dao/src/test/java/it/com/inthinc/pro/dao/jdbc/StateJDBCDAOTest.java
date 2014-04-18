package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.jdbc.StateJDBCDAO;
import com.inthinc.pro.model.State;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StateJDBCDAOTest extends SimpleJdbcDaoSupport {

    private static ITData itData = new ITData();
    private Integer stateID;


    @Before
    public void setUpBeforeTest() throws Exception {
        itData = new ITData();

    }

    @Test
    public void getStatesTest() throws Exception {
        StateJDBCDAO stateDAO = new StateJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        stateDAO.setDataSource(dataSource);

        List<State> statesList = stateDAO.getStates();

        assertTrue(statesList.size() > 0);
        assertTrue(statesList.size() == 80);
    }

    @Test
    public void findByIDTest() {
        StateJDBCDAO stateDAO = new StateJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        stateDAO.setDataSource(dataSource);

        //findById method
        State state = stateDAO.findByID(1);
        assertNotNull(state);
        assertTrue(state.getName().equals("Alabama"));
        assertTrue(state.getAbbrev().equals("AL"));

        //findById method
        state = stateDAO.findByID(3);
        assertNotNull(state);
        assertTrue(state.getName().equals("Arizona"));
        assertTrue(state.getAbbrev().equals("AZ"));
    }

}
