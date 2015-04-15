package model;

import java.util.Date;

import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.reports.performance.model.PayrollData;

public class PayrollDataTest {
	@Test
	public void compareToTest() {
		 PayrollData item1 = new PayrollData("group1", "address", 1, "Foo,Bar", "CJONES", new Date(), 
				 HOSStatus.ON_DUTY, 20);
		 PayrollData item2 = new PayrollData("group1", "address", 1, "Foo,Bar", "CRYSTALJ", new Date(), 
				 HOSStatus.ON_DUTY, 20);
		 PayrollData item3 = new PayrollData("group1", "address", 1, "Wow,Bar", "CRYSTALJ", new Date(), 
				 HOSStatus.ON_DUTY, 20);
		 PayrollData item4 = new PayrollData("group2", "address", 1, "Foo,Bar", "CRYSTALJ", new Date(), 
				 HOSStatus.ON_DUTY, 20);
		 //group,names are the same, comapre empid
		 assertTrue("",item1.compareTo(item2)<0);
		 //groups are the same, compare names
		 assertTrue("",item1.compareTo(item3)<0);
		 //compare groups
		 assertTrue("",item1.compareTo(item4)<0);
        PayrollData item5 = new PayrollData("group1", "address", 1, "Wow,Bar", null, new Date(), HOSStatus.ON_DUTY, 20);
        PayrollData item6 = new PayrollData("group1", "address", 1, "Wow,Bar", null, new Date(), HOSStatus.ON_DUTY, 20);
        assertTrue("", item5.compareTo(item6) == 0);
        item6 = new PayrollData("group1", "address", 1, "Wow,Bar", "CRYSTALJ", new Date(), HOSStatus.ON_DUTY, 20);
        assertTrue("", item5.compareTo(item6) < 0);
        assertTrue("", item6.compareTo(item5) > 0);
    }
}
