package com.inthinc.pro.backing.dao;

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
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.impl.ReportServiceImpl;
import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;

public class DaoUtilBean
{
    private static final Logger logger = Logger.getLogger(DaoUtilBean.class);

    SiloServiceCreator siloServiceCreator;
    ReportServiceCreator reportServiceCreator;
    String errorMsg;


	String selectedMethod;
    String selectedMethodDescription;
    Map<String, DaoMethod> methodMap;
    List<Param> paramList;
    Address address;
//    List<Result> results;

    List<String> columnHeaders;
    List<List<String>> records;

//    private static final Integer ROWS_PER_INSTANCE = 30;

    private Class<?> dataAccessInterfaces[] = { SiloServiceImpl.class, ReportServiceImpl.class };
    private Object dataAccess[];
    private List<String> excludedMethods = Arrays.asList("toString", "getClass", "equals", "wait", "hashCode", "notify", "notifyAll");

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
    	setErrorMsg("");
        this.selectedMethod = selectedMethod;
        DaoMethod m = getMethodMap().get(selectedMethod);
        if (m.getMethod().isAnnotationPresent(MethodDescription.class))
            setSelectedMethodDescription(m.getMethod().getAnnotation(MethodDescription.class).description());
        else
            this.setSelectedMethodDescription("");
        setParamList(null);
//        setResults(null);
        columnHeaders = null;
        records = null;
    }

    public List<SelectItem> getMethodSelectList()
    {
        List<SelectItem> methodList = new ArrayList<SelectItem>();
        methodList.add(new SelectItem("--Select a Method--", "--Select a Method--"));
        for (String methodName : getMethodMap().keySet())
        {
            methodList.add(new SelectItem(methodName, methodName));
        }
        return methodList;
    }

    public Map<String, DaoMethod> getMethodMap()
    {
        if (methodMap == null)
        {
            methodMap = new TreeMap<String, DaoMethod>();
            for (int j = 0; j < dataAccessInterfaces.length; j++)
            {
                Method[] methods = dataAccessInterfaces[j].getMethods();
                for (int i = 0; i < methods.length; i++)
                {
                    if (
                    		
                    		/* methods[i].getName().startsWith("get") && */
                    		
                    		!excludedMethods.contains(methods[i].getName())
                    		)
                    {
                        methodMap.put(methods[i].getName(), new DaoMethod(methods[i], j));
                    }
                }
            }
        }
        return methodMap;
    }

    public void setMethodMap(Map<String, DaoMethod> methodMap)
    {
        this.methodMap = methodMap;
    }

    public List<Param> getParamList()
    {
        if (paramList == null)
        {
            if (selectedMethod != null && methodMap.get(selectedMethod) != null)
            {
                Method method = methodMap.get(selectedMethod).getMethod();

                Class<?>[] paramTypes = method.getParameterTypes();
                Annotation annotation[][] = method.getParameterAnnotations();

                for (int i = 0; i < paramTypes.length; i++)
                {
                    if (paramList == null)
                        paramList = new ArrayList<Param>();
                    DaoParam webParm = getDaoParamAnnotaion(annotation, i);
                    if(paramTypes[i].getSimpleName().equals("Map"))
                    {
                        if (webParm != null)
                        {
							for (Field field : webParm.type().getDeclaredFields())
							{
				                Column column = null;

								String mname = field.getName();
								if (mname.equals("serialVersionUID"))
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
				                Class fieldType = field.getType();
				                if (Boolean.class.isAssignableFrom(fieldType) || 
				                		Number.class.isAssignableFrom(fieldType) || 
				                		String.class.isAssignableFrom(fieldType) || 
				                		Character.class.isAssignableFrom(fieldType) || 
				                		Date.class.isAssignableFrom(fieldType) || 
				                		TimeZone.class.isAssignableFrom(fieldType)|| fieldType.isEnum()) {

				                    Param param = new Param();
				                    
									System.out.println(mname + " " + field.getType().getName());
		                            param.setParamName(mname);
		                        	
		                            param.setParamInputDesc("");
		                            param.setParamConvert(com.inthinc.pro.convert.BaseConvert.class);
		                            param.setParamType(field.getType());
		                            param.setDateType(field.getType().getSimpleName().equals("Date"));
		                            param.setParamValue(null);
		                            param.setParentType(webParm.type());
		                            if (field.getType().getSimpleName().equals("Date"))
		                            {
			                            param.setParamConvert(com.inthinc.pro.convert.DateConvert.class);
			                            param.setParamInputDesc("MM/dd/yyyy hh:mm");
		                            }
		                            
			                        paramList.add(param);
				                }
							}
                        }
                    }
                    else
                    {
//		                if (List.class.isAssignableFrom(fieldType) || 
                        Param param = new Param();
                        if (webParm != null)
                        {
                            param.setParamName(webParm.name());
                        }
                        else
                        {
                            param.setParamName("" + (i + 1));
                        }
                        param.setParamType(paramTypes[i]);
                        logger.debug(paramTypes[i].toString());
                        logger.debug(paramTypes[i].getName());
                        if (webParm != null)
                        {
                            param.setParamInputDesc(webParm.inputDesc());
                        }
                    	param.setDateType(false);
                        if (paramTypes[i].getSimpleName().equals("Date") || webParm!=null && webParm.isDate())
                        {
                        	param.setDateType(true);
                            param.setParamConvert(com.inthinc.pro.convert.DateConvert.class);
                        }
                        else
                        	param.setParamConvert(com.inthinc.pro.convert.BaseConvert.class);
                        param.setParamValue(null);

                        paramList.add(param);
                    }                    
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
    	setErrorMsg("");
    	//TODO We are only going to handle one complex entity type
    	int numEntityParams=0;
    	int numParams=0;
    	Map<String,Object> entityMap = new TreeMap<String,Object>();
    	if (paramList!=null)
    	{
        	for (Param param : paramList)
        	{
        		if (param.parentType!=null)
        		{
        			numEntityParams++;
        		}
        	}
        	numParams = paramList.size();
        	if (numEntityParams>0)
        	{
        		numParams = numParams-numEntityParams+1;
        	}
    	}
        Object args[] = new Object[(paramList == null) ? 0 : numParams];
        int cnt = 0;
        if (paramList != null)
        {
        	boolean entitySet=false;
            for (Param param : paramList)
            {
                logger.debug(param.getParamName() + " = " + param.getParamValue());
                if (param.getParentType()!=null)
                {
                	entitySet=true;
                    if (param.getParamType().isArray())
                    {
                    	entityMap.put(param.getParamName(), param.getParamValueObjectArray());
                    }
                    else
                    {
                    	entityMap.put(param.getParamName(), param.getParamValueObject());
                    }

                }
                else
                {
                	if (entitySet)
                	{
                		args[cnt++] = entityMap;
                		entitySet=false;
                	}
                    if (param.getParamType().isArray())
                    {
                        args[cnt++] = param.getParamValueObjectArray();
                    }
                    else
                    {
                        args[cnt++] = param.getParamValueObject();
                    }
                }
            }
        	if (entitySet)
        	{
        		args[cnt++] = entityMap;
        		entitySet=false;
        	}
        }

        try
        {
            DaoMethod daomethod = methodMap.get(selectedMethod);
            Object dataAccess = getDataAccess(daomethod.getInterfaceIdx());
            InvocationHandler handler = Proxy.getInvocationHandler(dataAccess);

            Method method = daomethod.getMethod();
            processResults(method, handler.invoke(dataAccess, method, args));

//            clearResults();
//
//            setReturnResults(method, handler.invoke(dataAccess, method, args));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            setErrorMsg(e.getMessage() + e);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            setErrorMsg(t.getMessage() + t);
        }
    }

    private void processResults(Method method, Object returnObject)
    {
        columnHeaders = new ArrayList<String>();
        records = new ArrayList<List<String>>();

        // if the method returns a List or Maps
        Account acct;
        if (method.getReturnType().isAssignableFrom(List.class))
        {
            List<Map<String, Object>> recordMaps = (List<Map<String, Object>>) returnObject;
            // populate the columnHeaders list
            for (Map<String, Object> map : recordMaps)
            {
                for (String key : map.keySet())
                {
                    if (!columnHeaders.contains(key))
                        columnHeaders.add(key);
                }
            }
            Collections.sort(columnHeaders);
            
            // populate recordList
            for (Map<String, Object> map : recordMaps)
            {
                List<String> recordList = new ArrayList<String>();
                for (String header : columnHeaders)
                {
                    if (map.containsKey(header))
                    {
                        recordList.add(map.get(header).toString());
                    }
                    else
                    {
                        recordList.add("");
                    }
                }
                records.add(recordList);
            }
        }
        else
        {
            Map<String, Object> recordMap = (Map<String, Object>)returnObject;
            List<String> recordList = new ArrayList<String>();
            for(Map.Entry<String, Object> entry : recordMap.entrySet())
            {
                columnHeaders.add(entry.getKey());
            }
            Collections.sort(columnHeaders);
            for (String header : columnHeaders)
            {
                if (recordMap.containsKey(header))
                {
                    recordList.add(recordMap.get(header).toString());
                }
                else
                {
                    recordList.add("");
                }
            }
            records.add(recordList);
        }
    }

//    private void clearResults()
//    {
//        setResults(new ArrayList<Result>());
//    }
//
//    private void setReturnResults(Method method, Object returnObject)
//    {
//        List<Result> resultsList = new ArrayList<Result>();
//
//        if (method.getReturnType().isAssignableFrom(List.class))
//        {
//            for (Map map : (List<Map>) returnObject)
//            {
//                List<Result> result = getResultListFromMap(map);
//                resultsList.addAll(result);
//                for (int i = 0; i < ROWS_PER_INSTANCE - result.size(); i++)
//                {
//                    resultsList.add(new Result("", ""));
//                }
//            }
//        }
//        else
//        {
//            List<Result> result = getResultListFromMap((Map) returnObject);
//            resultsList.addAll(result);
//        }
//
//        setResults(resultsList);
//
//    }
//
//    private List<Result> getResultListFromMap(Map returnMap)
//    {
//        List<Result> resultList = new ArrayList<Result>();
//        for (Object key : returnMap.keySet())
//        {
//            // special case for event attributes
//            if (key.toString().equals("attrMap"))
//            {
//                resultList.addAll(getResultListFromEventAttrMap(returnMap.get(key)));
//            }
//            else
//            {
//                resultList.add(new Result(key.toString(), returnMap.get(key).toString()));
//            }
//        }
//
//        return resultList;
//    }
//
//    private Collection<? extends Result> getResultListFromEventAttrMap(Object object)
//    {
//        List<Result> resultList = new ArrayList<Result>();
//        Map<Integer, Integer> attrMap = (Map<Integer, Integer>) object;
//        for (Integer key : attrMap.keySet())
//        {
//            String attrName = EventAttr.getFieldName(key);
//            resultList.add(new Result("ATTR: " + (attrName == null ? key.toString() : attrName), attrMap.get(key).toString()));
//        }
//        return resultList;
//    }

    public void setParamList(List<Param> paramList)
    {
        this.paramList = paramList;
    }

    public List<String> getColumnHeaders()
    {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders)
    {
        this.columnHeaders = columnHeaders;
    }

    public List<List<String>> getRecords()
    {
        return records;
    }

    public void setRecords(List<List<String>> records)
    {
        this.records = records;
    }
//
//    public List<Result> getResults()
//    {
//        return results;
//    }
//
//    public void setResults(List<Result> resultsList)
//    {
//        this.results = resultsList;
//    }

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
}
