package com.inthinc.pro.backing.dao.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.backing.dao.DateFormatBean;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;


public class DaoUtilMapperTest {

	@Test
	public void testObjectToDisplayMap() {
		
		DateFormatBean dateFormatBean = new DateFormatBean();
		Date date = new Date();
		
		
		User user = new User(10, 11, null, Status.ACTIVE, "usernameTest", "passwordTest", 123);
		user.setCreated(date);
		
		DaoUtilMapper mapper = new DaoUtilMapper();
		mapper.setDateFormatBean(dateFormatBean);
		
		Map<String,Object> displayMap = mapper.convertToMap(user);
		
		assertEquals("userID", "10", displayMap.get("userID").toString());
		assertEquals("usernameTest", "usernameTest", displayMap.get("username").toString());
		
		String expectedDateStr = dateFormatBean.formatDate(date);
		assertEquals("created", expectedDateStr, displayMap.get("created").toString());
		
	}
}
