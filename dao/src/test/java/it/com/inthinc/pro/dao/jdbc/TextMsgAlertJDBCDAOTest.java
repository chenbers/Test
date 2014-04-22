package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.TextMsgAlertJDBCDAO;
import com.inthinc.pro.model.MessageItem;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TextMsgAlertJDBCDAOTest extends SimpleJdbcDaoSupport {
    private static SiloService siloService;
    private static ITData itData = new ITData();
    private Integer acctID;


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        }

    private static void initApp() throws Exception {
    }

    @Test
    public void getTextMsgAlertsByAcctIDTest() throws Exception {
        TextMsgAlertJDBCDAO textDAO = new TextMsgAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        textDAO.setDataSource(dataSource);


        List<MessageItem> messagesList = textDAO.getTextMsgAlertsByAcctID(25);

        assertTrue(messagesList.size() > 0);
        assertTrue(messagesList.size()==140);


    }



}
