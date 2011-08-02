package com.inthinc.pro.rally;

public enum RallyWebServices {
	
	PROJECT("Project"),
	TEST_CASE("TestCase"),
	TEST_FOLDER("TestFolder"),
	WORKSPACE("Workspace"),
	TYPE_DEFINITION("TypeDefinition"),
	TEST_CASE_RESULTS("TestCaseResult"),
	TEST_SET("TestSet"),
	
	INTHINC(665449629), 
	SANDBOX(558474157),   
	;
	
	
	private String value, get, name;
	private Integer id;
	private static final int major = 1, minor = 26;
	public static final String address = "rally1.rallydev.com";
	public static final String host = "https://"+address+"/slm/webservice/"+major+"."+minor+"/";
	

    public static final String username = "dtanner@inthinc.com";
    public static final String password = "aOURh7PL5v";
	
	private RallyWebServices(String value) {
		this.name = value;
		this.value = host+value.toLowerCase();
		this.get = host+value.toLowerCase()+".js";
		this.id = 0;
	}
	
	private RallyWebServices(Integer id) {
		this.value = host+"workspace/"+id;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	
	public String getMethod() {
		return get;
	}
	
	public Integer getID() {
		return id;
	}
}
