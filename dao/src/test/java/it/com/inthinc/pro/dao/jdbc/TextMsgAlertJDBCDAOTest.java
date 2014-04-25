package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.TextMsgAlertJDBCDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TextMsgAlertJDBCDAOTest extends SimpleJdbcDaoSupport {
    private static SiloService siloService;
    private static ITData itData = new ITData();
    private Integer acctID;
    static int TEST_GROUP_ID = 3384;


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

    }

    @Test
    public void getTextMsgCountTest() throws Exception {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        //filterList.add(new TableFilterField("vehicleID", "7978"));
        //filterList.add(new TableFilterField("flags", "0"));
        Calendar c1 = Calendar.getInstance();
        c1.set(1900, Calendar.JANUARY, 1);  //January 1st 2013
        Date startDate=c1.getTime();
        Date endDate=new Date();

        TextMsgAlertJDBCDAO textDAO = new TextMsgAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        textDAO.setDataSource(dataSource);


        int count = textDAO.getTextMsgCount(TEST_GROUP_ID,startDate,endDate, filterList);

        Assert.assertTrue("expected to be 1 or >1", count > 0);
    }


    @Test
    public void getTextMsgPageTest() throws Exception {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        TextMsgAlertJDBCDAO textDAO = new TextMsgAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        textDAO.setDataSource(dataSource);

        Calendar c1 = Calendar.getInstance();
        c1.set(1900, Calendar.JANUARY, 1);  //January 1st 2013
        Date startDate=c1.getTime();
        Date endDate=new Date();

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);

        //filterList.add(new TableFilterField("vehicleID", "7978"));
        List<MessageItem> messages = textDAO.getTextMsgPage(3384,startDate,endDate, filterList,pp);
        Assert.assertTrue("expected to be 1 or >1", messages.size() > 0);




    }

    @Test
    public void getSentTextMsgsByGroupIDTest() throws Exception {


        TextMsgAlertJDBCDAO textDAO = new TextMsgAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        textDAO.setDataSource(dataSource);

        Calendar c1 = Calendar.getInstance();
        c1.set(2013, Calendar.JANUARY, 1);  //January 1st 2013
        Date startDate=c1.getTime();
        Date stopDate= new Date();


        List<MessageItem> messages = textDAO.getSentTextMsgsByGroupID(1,startDate,stopDate);
        Assert.assertTrue("expected to be 1 or >1", messages.size() > 0);
    }


}
