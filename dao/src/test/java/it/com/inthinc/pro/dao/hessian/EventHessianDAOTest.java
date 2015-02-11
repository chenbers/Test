package it.com.inthinc.pro.dao.hessian;

import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import junit.framework.Assert;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test case for {@link com.inthinc.pro.dao.hessian.EventHessianDAO}
 */
public class EventHessianDAOTest {

    @Mocked
    SiloService mockedSiloService;
    EventHessianDAO eventHessianDAO;
    Map<String, Object> retMap;

    @Before
    public void init() {
        retMap = new HashMap<String, Object>();
        retMap.put("count", 1);
        eventHessianDAO = new EventHessianDAO();
        eventHessianDAO.setSiloService(mockedSiloService);
    }

    @Test
    public void testForgive() {
        new NonStrictExpectations() {{
            mockedSiloService.forgive(1, 1l, 100l, "test_forgive");
            result = retMap;
        }};

        Integer result = eventHessianDAO.forgive(1, 1l, 100l, "test_forgive");
        Assert.assertEquals(1, result.intValue());
    }
}
