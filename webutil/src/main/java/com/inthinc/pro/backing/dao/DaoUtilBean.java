package com.inthinc.pro.backing.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.model.Ordering;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.impl.ReportServiceImpl;
import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.backing.dao.model.Param;
import com.inthinc.pro.backing.dao.model.Result;
import com.inthinc.pro.backing.dao.validator.ValidatorType;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;

public class DaoUtilBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DaoUtilBean.class);

    SiloServiceCreator siloServiceCreator;
    ReportServiceCreator reportServiceCreator;
    String errorMsg;
	private Boolean formatResults;


	String selectedMethod;
    String selectedMethodDescription;
    Map<String, DaoMethod> methodMap;
    List<Param> paramList;

    List<String> columnHeaders;
    List<List<Result>> records;
    int recordCount;
	Map<String, Object> sortOrder;

	private Class<?> dataAccessInterfaces[] = { SiloServiceImpl.class, ReportServiceImpl.class };
    private Object dataAccess[];
    private List<String> excludedMethods = Arrays.asList("toString", "getClass", "equals", "wait", "hashCode", "notify", "notifyAll");
    private List<CrudType> excludedTypes;
	private List<String> excludedFields= Arrays.asList("serialVersionUID");

    private DateFormatBean dateFormatBean;
    
    private Integer populateID;
    private Boolean populateAvailable;
    
    public static final String NO_RESULTS = "No results were returned";
	public List<String> getExcludedMethods() {
    	return excludedMethods;
    }
	public DateFormatBean getDateFormatBean() {
		return dateFormatBean;
	}

	public void setDateFormatBean(DateFormatBean dateFormatBean) {
		this.dateFormatBean = dateFormatBean;
	}

	public void init() {
		setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE));
    	if (!isSuperuser())
    	{
    		setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE, CrudType.CREATE, CrudType.DELETE, CrudType.UPDATE, CrudType.READ_RESTRICTED));
    	}

    	setFormatResults(true);
    	dateFormatBean.setTimeZoneStr(getUser().getPerson().getTimeZone().getID());
    	
    	initMethodMap();
    }
    
    public void initMethodMap()
    {
        methodMap = new TreeMap<String, DaoMethod>();
        for (int j = 0; j < dataAccessInterfaces.length; j++)
        {
            Method[] methods = dataAccessInterfaces[j].getMethods();
            for (int i = 0; i < methods.length; i++)
            {
            	DaoMethod daoMethod = new DaoMethod(methods[i], j);
                if (excludedMethods.contains(methods[i].getName()))
                	continue;
                if (methods[i].isAnnotationPresent(MethodDescription.class)) {
                	MethodDescription methodDescription = methods[i].getAnnotation(MethodDescription.class);
                    CrudType crudType = methodDescription.crudType();
                	if (excludedTypes.contains(crudType)) 
                		continue;
                	daoMethod.setCrudType(crudType);
                	daoMethod.setModelClass(methods[i].getAnnotation(MethodDescription.class).modelClass());
                    if (!daoMethod.getModelClass().equals(java.util.Map.class)) {
                    	daoMethod.setMapperClass(methods[i].getAnnotation(MethodDescription.class).mapperClass());
                    }
                    daoMethod.setDescription(methods[i].getAnnotation(MethodDescription.class).description());
                    daoMethod.setPopulateMethod(methodDescription.populateMethod());
                }
                
                methodMap.put(methods[i].getName(), daoMethod);
            }
        }
    }
    private Object getDataAccess(int interfaceIdx) throws MalformedURLException
    {
        if (dataAccess == null)
        {
            dataAccess = new Object[dataAccessInterfaces.length];
            dataAccess[0] = siloServiceCreator.getService();
            dataAccess[1] = reportServiceCreator.getService();
        }
        return dataAccess[interfaceIdx];
    }

    public String getSelectedMethod()
    {
        return selectedMethod;
    }

    public void setSelectedMethod(String selectedMethod)
    {
    	setErrorMsg(null);
        this.selectedMethod = selectedMethod;
        setParamList(null);
        setRecords(null);
        setSelectedMethodDescription(null);
        columnHeaders = null;
        setPopulateAvailable(false);
        if (selectedMethod != null) {
        	DaoMethod m = getMethodMap().get(selectedMethod);
        	setSelectedMethodDescription((m == null || m.getDescription() == null) ? "" : m.getDescription());
        	setPopulateAvailable(methodMap.get(m.getPopulateMethod()) != null);
        }
        

    }

    public List<SelectItem> getMethodSelectList()
    {
        List<SelectItem> methodList = new ArrayList<SelectItem>();
        methodList.add(new SelectItem(null, "--Select a Method--"));
        for (String methodName : getMethodMap().keySet())
        {
            methodList.add(new SelectItem(methodName, methodName));
        }
        return methodList;
    }

    public Map<String, DaoMethod> getMethodMap()
    {
        return methodMap;
    }
    

    public void setMethodMap(Map<String, DaoMethod> methodMap)
    {
        this.methodMap = methodMap;
    }

    public List<Param> getParamList()
    {
        if (paramList == null &&
       		selectedMethod != null && 
       		methodMap.get(selectedMethod) != null)
        {
            Method method = methodMap.get(selectedMethod).getMethod();

            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length == 0)
            	return null;
            
            Annotation annotation[][] = method.getParameterAnnotations();
            paramList = new ArrayList<Param>();
            
            for (int i = 0; i < paramTypes.length; i++)
            {
                DaoParam webParm = getDaoParamAnnotaion(annotation, i);
                if(paramTypes[i].getSimpleName().equals("List"))
                	continue;	// not supported
                if(paramTypes[i].getSimpleName().equals("Map"))
                {
                    if (webParm != null)
                    {
						for (Field field : webParm.type().getDeclaredFields())
						{
			                Column column = null;

							String fieldName = field.getName();
							if (excludedFields.contains(fieldName))
								continue;
						
			                if (field.isAnnotationPresent(Column.class))
			                    column = field.getAnnotation(Column.class);
			                if (column != null)
			                {
			                    // If the field has been annotated with the @Column(updateable=false), then skip
			                    if (!column.updateable())
			                        continue;
			                    if (!column.name().isEmpty())
			                        fieldName = column.name();
			                }
			                Class<?> fieldType = field.getType();
			                if (Boolean.class.isAssignableFrom(fieldType) || 
			                		Number.class.isAssignableFrom(fieldType) || 
			                		String.class.isAssignableFrom(fieldType) || 
			                		Character.class.isAssignableFrom(fieldType) || 
			                		Date.class.isAssignableFrom(fieldType) || 
			                		TimeZone.class.isAssignableFrom(fieldType)|| 
			                		fieldType.isEnum()) {

			                    Param param = new Param();
	                            param.setParamName(fieldName);
	                            param.setParamInputDesc(webParm.name() + " " + fieldName + " (" + fieldType.getSimpleName() + ")");
	                            param.setParamType(fieldType.equals(java.util.Date.class) ? Long.class : fieldType);
//	                            param.setParamType(webParm.type());
	                            param.setInputType(fieldType);
	                            param.setIndex(i);
	                            if (webParm.isAccountID() && !isSuperuser()) {
	                                param.setIsAccountID(true);
	                            	param.setParamValue(getUser().getPerson().getAcctID().toString());
	                            }
	                            else {
	                                param.setIsAccountID(false);
	                            } 
	                            if (param.getInputType().equals(java.util.Date.class)) {
	                            	param.setParamValue(new Date());
	                            }
	                            param.setValidatorType(ValidatorType.DEFAULT);
		                        paramList.add(param);
			                }
			                else {
			                	// not supported
			                }
						}
                    }
                }
                else
                {
                    Param param = new Param();
                    if (webParm != null)
                    {
                        param.setParamName(webParm.name());
                        param.setParamInputDesc(webParm.inputDesc());
                        param.setInputType(webParm.type().equals(java.lang.Object.class) ? paramTypes[i] : webParm.type());
                        if (webParm.isAccountID() && !isSuperuser()) {
                            param.setIsAccountID(true);
                        	param.setParamValue(getUser().getPerson().getAcctID().toString());
                        }
                        else {
                            param.setIsAccountID(false);
                        }
                        if (param.getInputType().equals(java.util.Date.class)) {
                        	param.setParamValue(new Date());
                        }
                        param.setValidatorType(webParm.validator());
                    }
                    else
                    {
                        param.setParamName("" + (i + 1));
                        param.setParamInputDesc("");
                        param.setInputType(paramTypes[i]);
                        param.setIsAccountID(false);
                        param.setValidatorType(ValidatorType.DEFAULT);
                    }
                    param.setIndex(i);
                    param.setParamType(paramTypes[i]);
                    paramList.add(param);
                }                    
            }
        }
        return paramList;
    }

    private DaoParam getDaoParamAnnotaion(Annotation[][] annotation, int i)
    {
        if (annotation == null || annotation.length <= i)
            return null;

        for (int j = 0; j < annotation[i].length; j++)
        {
            if (annotation[i][j].annotationType() == DaoParam.class)
            {
                return (DaoParam) annotation[i][j];
            }
        }

        return null;
    }
    public void resultsAction()
    {
    	setErrorMsg(null);
    	setRecords(null);
    	
        try
        {
        	Object args[] = getArgsFromParamList();

        	DaoMethod daomethod = methodMap.get(selectedMethod);
            Object dataAccess = getDataAccess(daomethod.getInterfaceIdx());
            InvocationHandler handler = Proxy.getInvocationHandler(dataAccess);

            Method method = daomethod.getMethod();
            processResults(method, handler.invoke(dataAccess, method, args));
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            setErrorMsg(e.getMessage() + e);
        }
        catch (Throwable t)
        {
//            t.printStackTrace();
            setErrorMsg(t.getMessage() + t);
        }
    }
    
    public Object[] getArgsFromParamList() throws Exception {
    	
        Method method = methodMap.get(selectedMethod).getMethod();
        Class<?>[] paramTypes = method.getParameterTypes();
        Annotation annotation[][] = method.getParameterAnnotations();

        Object args[] = new Object[paramTypes.length];
        int cnt = 0;
        for (int i = 0; i < paramTypes.length; i++) {
            DaoParam webParm = getDaoParamAnnotaion(annotation, i);
            if(paramTypes[i].getSimpleName().equals("List"))
            {
				args[cnt++] = Collections.EMPTY_LIST; 
            }
            else if(paramTypes[i].getSimpleName().equals("Map"))
            {
                if (webParm != null)
                {
                	// put the fields into a map 
                	Map<String, Object> map = new HashMap<String, Object>();
					for (Field field : webParm.type().getDeclaredFields())
					{
						String mname = field.getName();
		                Column column = null;
						String fieldName = field.getName();
						if (excludedFields.contains(fieldName))
							continue;
						if (field.isAnnotationPresent(Column.class))
		                    column = field.getAnnotation(Column.class);
		                if (column != null)
		                {
		                    // If the field has been annotated with the @Column(updateable=false), then skip
		                    if (!column.updateable())
		                        continue;
		                    if (!column.name().isEmpty())
		                        mname = column.name();
		                }
		                Param p = findParam(mname, i);
		                if (p != null) {
		                	if (!p.getParamValue().toString().isEmpty())
		                		map.put(mname, p.getConvertedParamValue());
		                }
					}
					args[cnt++] = map;
                }
            }
            else
            {
                Param param = findParam(i);
//                Object value = param.getParamValue();
//                System.out.println(param.getParamName() + " " + 
//                					param.getParamValue() + " " + 
//                					param.getConvertedParamValue() + " " + 
//                					value.getClass().getName());
                args[cnt++] = (param == null) ? null : param.getConvertedParamValue();
            }                    
        	
        }
        return args;
	}

	private Param findParam(String mname, int index) {
    	for (Param param : paramList)
    		if (param.getParamName().equals(mname) && param.getIndex() == index)
    			return param;
		return null;
	}
    private Param findParam(int index) {
    	for (Param param : paramList)
    		if (param.getIndex() == index)
    			return param;
		return null;
	}
	@SuppressWarnings("unchecked")
	private void processResults(Method method, Object returnObject) throws InstantiationException, IllegalAccessException
    {
        records = new ArrayList<List<Result>>();
        
        DaoMethod daoMethod = getMethodMap().get(selectedMethod);
                

        // if the method returns a List or Maps
        if (method.getReturnType().isAssignableFrom(List.class))
        {
        	initColumnHeaders((List<Map<String, Object>>) returnObject);
            
            // populate recordList
            for (Map<String, Object> map : (List<Map<String, Object>>) returnObject)
            {
                records.add(processRow(daoMethod, map));
            }
        }
        else
        {
        	Map<String, Object> recordMap = (Map<String, Object>)returnObject;
        	initColumnHeaders(Arrays.asList(recordMap));
            records.add(processRow(daoMethod, recordMap));
        }
        
        recordCount = records.size();
        if (recordCount == 0)
            setErrorMsg(NO_RESULTS);
    }

	private List<Result> processRow(DaoMethod daoMethod, Map<String, Object> recordMap) throws InstantiationException,
																					IllegalAccessException {
		List<Result> fieldValueList = new ArrayList<Result>();
		
		Map<String, Object> formattedRecordMap = null;
		if (getFormatResults()) {
			Class<?> modelClass = daoMethod.getModelClass();
			if (modelClass != null && !modelClass.equals(java.util.Map.class)) {
				BaseUtilMapper mapper = daoMethod.getMapperClass().newInstance();
				mapper.setDateFormatBean(getDateFormatBean());
				
				// convert to the model object so we can get type info
				// and then back to a map that is formatted for display
				Object obj = mapper.convertToModelObject(recordMap, modelClass);
				formattedRecordMap = mapper.convertToMap(obj);
			}
		}
		for (String header : columnHeaders)
		{
			Result result = new Result();
			if (formattedRecordMap != null)
				result.setDisplay(formattedRecordMap.get(header) == null ? "" : formattedRecordMap.get(header).toString());
			else result.setDisplay(recordMap.get(header) == null ? "" : recordMap.get(header).toString());
			Object obj = recordMap.get(header);
			if (obj != null && java.util.Map.class.isAssignableFrom(obj.getClass()))
				result.setSort(obj.toString());
			else result.setSort(obj);
	    	fieldValueList.add(result);
		}
		return fieldValueList;
	}

    private void initColumnHeaders(List<Map<String, Object>> returnObjectList) {
        columnHeaders = new ArrayList<String>();
        for (Map<String, Object> map : returnObjectList)
        {
            for (String key : map.keySet())
            {
                if (!columnHeaders.contains(key))
                    columnHeaders.add(key);
            }
        }
        Collections.sort(columnHeaders);
        
        sortOrder = new HashMap<String, Object>();
        for (String col : columnHeaders)
        	sortOrder.put(col, Ordering.UNSORTED);
    }

    public void exportToCSV()
    {
    	System.out.println("export to csv");
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=\"tiwiPro.csv\"");

        OutputStream out = null;
        try
        {
            out = response.getOutputStream();
            writeCSV(out);
            
            out.flush();
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
       
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
    }
    
	private boolean writeCSV(OutputStream outStream) {
		if (records == null)
			return false;
		try {
			PrintStream out = new PrintStream(outStream);
			StringBuilder buffer = new StringBuilder();
			for (String columnHeader : columnHeaders) {
				buffer.append(csvItem(columnHeader));
				buffer.append(",");
				
			}
			out.println(buffer.toString());
			for (List<Result> row : records) {
				buffer = new StringBuilder();
				for (Result col : row) {
					buffer.append(csvItem(col.getDisplay()));
					buffer.append(",");
				}
				out.println(buffer.toString());
			}
			out.close();
		} catch (Exception e) {
    		setErrorMsg("File cannot be created.");
    		return false;
		}
		return true;
	}

	private String csvItem(String display) {
	    if (display == null)
	        return "";
	    if (display.contains(","))
	        return "\"" + display + "\"";
        return display;
    }
    public void setParamList(List<Param> paramList)
    {
        this.paramList = paramList;
    }

    @SuppressWarnings("unchecked")
    public List<String> getColumnHeaders()
    {
        return (List<String>) (columnHeaders == null ? (Collections.emptyList()) : columnHeaders);
    }

    public void setColumnHeaders(List<String> columnHeaders)
    {
        this.columnHeaders = columnHeaders;
    }

    public List<List<Result>> getRecords()
    {
        return records;
    }

    public void setRecords(List<List<Result>> records)
    {
        this.records = records;
        setRecordCount(this.records == null ? 0: this.records.size());
    }

    public SiloServiceCreator getSiloServiceCreator()
    {
        return siloServiceCreator;
    }

    public void setSiloServiceCreator(SiloServiceCreator siloServiceCreator)
    {
        this.siloServiceCreator = siloServiceCreator;
    }

    public ReportServiceCreator getReportServiceCreator()
    {
        return reportServiceCreator;
    }

    public void setReportServiceCreator(ReportServiceCreator reportServiceCreator)
    {
        this.reportServiceCreator = reportServiceCreator;
    }

    public String getSelectedMethodDescription()
    {
        return selectedMethodDescription;
    }

    public void setSelectedMethodDescription(String selectedMethodDescription)
    {
        this.selectedMethodDescription = selectedMethodDescription;
    }

    public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<CrudType> getExcludedTypes() {
		return excludedTypes;
	}

	public void setExcludedTypes(List<CrudType> excludedTypes) {
		this.excludedTypes = excludedTypes;
	}
    public Map<String, Object> getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Map<String, Object> sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean getFormatResults() {
		return formatResults;
	}

	public void setFormatResults(Boolean formatResults) {
		this.formatResults = formatResults;
	}

    public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
    public Integer getPopulateID() {
        return populateID;
    }
    public void setPopulateID(Integer populateID) {
        this.populateID = populateID;
    }
    public void populateAction() {
        DaoMethod selected = this.methodMap.get(this.selectedMethod);
        DaoMethod populateMethod = this.methodMap.get(selected.getPopulateMethod());
        System.out.println("populateAction -- ID: " + getPopulateID() + " " + selected.getPopulateMethod() + " " + (populateMethod == null ? "not found" : "found"));

        if (populateMethod != null && getPopulateID() != null) {
            Object dataAccess;
            try {
                dataAccess = getDataAccess(populateMethod.getInterfaceIdx());
                InvocationHandler handler = Proxy.getInvocationHandler(dataAccess);

                Method method = populateMethod.getMethod();
                processPopulateResults(method, handler.invoke(dataAccess, method, new Object[] {getPopulateID()}));
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    private void processPopulateResults(Method method, Object returnObject) throws InstantiationException, IllegalAccessException
    {
        DaoMethod daoMethod = getMethodMap().get(selectedMethod);
        Map<String, Object> recordMap = (Map<String, Object>)returnObject;
        
        for (Param param : paramList) {
            Object value = recordMap.get(param.getParamName());
            
            if (param.getInputType().equals(java.util.Date.class) && value != null) {
                param.setParamValue(new Date(Long.parseLong(value.toString()) * 1000l));
            }
            else param.setParamValue(value);
            System.out.println(param.getParamName() + " = " + param.getParamValue() + " " + (param.getParamValue() != null ? param.getParamValue().getClass().getName() : "") + " "+ param.getParamType().getName());     
        }
        
    }

    public Boolean getPopulateAvailable() {
        return populateAvailable;
    }
    public void setPopulateAvailable(Boolean populateAvailable) {
        this.populateAvailable = populateAvailable;
    }
}
