package com.inthinc.pro.backing.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.model.SelectItem;
import javax.jws.WebParam;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.EventAttr;


public class DaoUtilBean 
{
    private static final Logger logger = Logger.getLogger(DaoUtilBean.class);
    
    SiloServiceCreator siloServiceCreator;
    
    String selectedMethod;
    Map<String, DaoMethod>methodMap;
    List<Param> paramList;
    List<Result> results;
    private static final Integer ROWS_PER_INSTANCE = 30;

    private Class<?> dataAccessInterfaces[] = {
            SiloServiceImpl.class
    };
    private Object dataAccess[];
    private Object getDataAccess(int interfaceIdx) throws MalformedURLException
    {
        if (dataAccess == null)
        {
            dataAccess = new Object[dataAccessInterfaces.length];
            dataAccess[0] = siloServiceCreator.getService();
        }
        return dataAccess[interfaceIdx];
    }

    public String getSelectedMethod()
    {
        return selectedMethod;
    }

    public void setSelectedMethod(String selectedMethod)
    {
        this.selectedMethod = selectedMethod;
        setParamList(null);
        setResults(null);
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
                    if (methods[i].getName().startsWith("get") && !methods[i].getName().equals("getClass") )
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
                        paramList= new ArrayList<Param>();
                    Param param = new Param();
                    DaoParam webParm = getDaoParamAnnotaion(annotation, i);
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
                        param.setParamConvert(webParm.inputConvert());
                        param.setDateType(webParm.isDate());
                    }
                    param.setParamValue(null);
                    
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
                return (DaoParam)annotation[i][j]; 
            }
        }

        return null;
    }

    public void resultsAction() 
    {
            Object args[] = new Object[(paramList == null) ? 0 : paramList.size()];
            int cnt = 0;
            if (paramList != null)
            {
                for (Param param : paramList)
                {
                    logger.debug(param.getParamName() + " = " + param.getParamValue());
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
            
            try
            {
                DaoMethod daomethod = methodMap.get(selectedMethod);
                Object dataAccess = getDataAccess(daomethod.getInterfaceIdx());
                InvocationHandler handler = Proxy.getInvocationHandler(dataAccess); 

                Method method = daomethod.getMethod();

                clearResults();
                
                setReturnResults(method, handler.invoke(dataAccess, method, args));
            }
            catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
    }

    private void clearResults()
    {
        setResults(new ArrayList<Result>());
    }

    private void setReturnResults(Method method, Object returnObject)
    {
        List<Result> resultsList = new ArrayList<Result>();

        
        if (method.getReturnType().isAssignableFrom(List.class))
        {
            for (Map map : (List<Map>)returnObject)
            {
                List<Result> result = getResultListFromMap(map);
                resultsList.addAll(result);
                for (int i = 0; i < ROWS_PER_INSTANCE-result.size(); i++)
                {
                    resultsList.add(new Result("", ""));
                }
            }
        }
        else
        {
            List<Result> result = getResultListFromMap((Map)returnObject);
            resultsList.addAll(result);
        }
        
        setResults(resultsList);
        
        
    }


    private List<Result> getResultListFromMap(Map returnMap)
    {
        List<Result> resultList = new ArrayList<Result>();
        for (Object key : returnMap.keySet())
        {
            // special case for event attributes
            if (key.toString().equals("attrMap"))
            {
                resultList.addAll(getResultListFromEventAttrMap(returnMap.get(key)));
            }
            else
            {
                resultList.add(new Result(key.toString(), returnMap.get(key).toString()));
            }
        }
        
        return resultList;
    }

    private Collection<? extends Result> getResultListFromEventAttrMap(Object object)
    {
        List<Result> resultList = new ArrayList<Result>();
        Map<Integer, Integer> attrMap = (Map<Integer, Integer>)object;
        for (Integer key : attrMap.keySet())
        {
            String attrName = EventAttr.getFieldName(key);
            resultList.add(new Result("ATTR: " + (attrName == null ? key.toString() : attrName), attrMap.get(key).toString()));
        }
        return resultList;
    }

    public void setParamList(List<Param> paramList)
    {
        this.paramList = paramList;
    }


    public List<Result> getResults()
    {
        return results;
    }

    public void setResults(List<Result> resultsList)
    {
        this.results = resultsList;
    }

    public SiloServiceCreator getSiloServiceCreator()
    {
        return siloServiceCreator;
    }

    public void setSiloServiceCreator(SiloServiceCreator siloServiceCreator)
    {
        this.siloServiceCreator = siloServiceCreator;
    }

}
