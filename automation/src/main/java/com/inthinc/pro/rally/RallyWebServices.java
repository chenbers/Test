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
	private static final int major = 1, minor = 40;
	public static final String address = "rally1.rallydev.com";
	public static final String host = "https://"+address+"/slm/webservice/"+major+"."+minor+"/";
	
	public static final String workSpaceString = 
			"{\"_rallyAPIMajor\": \"1\", " +
			"\"_rallyAPIMinor\": \"40\", " +
			"\"_ref\": \"https://rally1.rallydev.com/slm/webservice/1.40/workspace/###.js\", " +
			"\"_refObjectName\": \"Sand Box\", " +
			"\"_type\": \"Workspace\"}";

	

    public static final String username = "anelson@inthinc.com";
    public static final String password = "nel01023";
	
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
