package com.inthinc.pro.dao.cache.model;

import java.io.Serializable;

import org.jboss.cache.pojo.annotation.Replicable;

@Replicable
public class MockPerson  implements Serializable {

	String name;
	public MockPerson(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return name;
	}
}
