package it.com.inthinc.pro.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.ITData;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.backing.dao.DAOHandler;
import com.inthinc.pro.backing.dao.annotation.DAODescription;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                "classpath:spring/applicationContext-serverPropertiesTest.xml",
                "classpath:spring/applicationContext-global.xml", 
                // this one is pure hessian impls used to test the mapping
//                "classpath:spring/applicationContext-hessiandao.xml" 
                "classpath:spring/applicationContext-dao.xml" 
                }, loader = com.inthinc.pro.spring.test.WebSessionContextLoader.class)

// Tests the Hessian vs dao implementations of the SiloService interface to verify the they return the same data (within reason)
// Currently testing only READ methods.
public class BaseServiceImpTest implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    //private static SiloService siloService;
    protected static DAOHandler daoHandler;

    private static final String XML_DATA_FILE = "IntegrationTest.xml";
    protected static ITData itData;
    private List<String> excludedMethods = Arrays.asList("toString", "getClass", "equals", "wait", "hashCode", "notify", "notifyAll");
    
    protected Map<String, Object[]> methodArgMap;
    protected Map<String, List<String>> ignoreMap = new TreeMap<String, List<String>>();
    protected static Map<String, List<String>> ignoreFieldMap = new HashMap<String, List<String>>();
        
    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.applicationContext = appContext;

    }

    protected void init() throws Exception{
        // dao
        daoHandler = (DAOHandler) applicationContext.getBean("daoHandler");

        itData = new ITData();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, (SiloService)applicationContext.getBean("siloServiceBean"), true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        initArgs();
    }
    
    protected void initArgs() {
    }
    
    @SuppressWarnings("unchecked")
    protected void compareResultMaps(String methodName, String prefix, Map<Object, Object> hessianResult, Map<Object, Object> daoResult) {
        for (Object key : hessianResult.keySet()) {
            if (isIgnoreField(methodName, prefix+key)) {
                continue;
            }
            Object hessianValue = hessianResult.get(key);
            Object daoValue = daoResult.get(key);

            if (List.class.isInstance(hessianValue)) {
                assertTrue(prefix+key + ": Hessian Result is a LIST but dao result is not", List.class.isInstance(daoValue));
                List<?> hessianValueList = (List<?>)hessianValue;
                List<?> daoValueList = (List<?>)daoValue;
                for (int i = 0; i < hessianValueList.size(); i++) {
                    if (Map.class.isInstance(hessianValueList.get(i))) {
                        compareResultMaps(methodName, prefix + key + ".", (Map<Object, Object>)hessianValueList.get(i), (Map<Object, Object>) daoValueList.get(i));
                    }
                    else {
                    	compareValues(methodName, prefix, key, hessianValueList.get(i), daoValueList.get(i));
                    }
                }
                    
            }
            else if (Map.class.isInstance(hessianValue)) {
                if (daoValue == null) {
                    assertNotNull(prefix + key + " daoValue is null but hessian is not hessian is " + hessianValue , daoValue);
                }
                else {
                   compareResultMaps(methodName, prefix + key + ".", (Map<Object, Object>)hessianValue, (Map<Object, Object>) daoValue);
                }
            }
            else {
            	compareValues(methodName, prefix, key, hessianValue, daoValue);
            }
        }
    }
    
    private void compareValues(String methodName, String prefix, Object key,
			Object hValue, Object dValue) {
    	
    	if (hValue != null && hValue instanceof String) {
    		hValue = hValue.toString().trim();
    		if (hValue.toString().isEmpty()) {
    			hValue = null;
    		}
    	}
    	if (dValue != null && dValue instanceof String) {
    		dValue = dValue.toString().trim();
    		if (dValue.toString().isEmpty()) {
    			dValue = null;
    		}
    	}
    	
    	if (dValue == null && hValue == null) {
    		return;
    	}

    	
    	if (hValue instanceof Double && dValue instanceof Double) {
            assertEquals(methodName + " - result key: " + prefix + key, (Double)hValue, (Double)dValue, 0.0001d);
    	}
    	else {
            assertEquals(methodName + " - result key: " + prefix + key, hValue, dValue);
    	}
	}

	private boolean isIgnoreField(String methodName, String fieldName) {
        List<String> ignoreFields = ignoreFieldMap.get(methodName);
        
        return ignoreFields == null ? false : ignoreFields.contains(fieldName);
        
    }

    public Object daoResult(DaoMethod daoMethod, Object[] args) throws Throwable {
        Method method = daoMethod.getMethod();
        Annotation annotation[][] = method.getParameterAnnotations();
        List<Class<?>> paramTypes = new ArrayList<Class<?>>();

        for (int i = 0; i < method.getParameterTypes().length; i++) {
            DaoParam webParm = getDaoParamAnnotaion(annotation, i);
            paramTypes.add(webParm.type());
        }
        
        return daoHandler.invoke(daoMethod, paramTypes, args);

    }

    protected boolean hasdaoImpl(DaoMethod daoMethod) {
        return daoMethod.getDaoID() != null && !daoMethod.getDaoID().isEmpty();

    }

    public Map<String, DaoMethod> initMethodMap(@SuppressWarnings("rawtypes") Class clazz, boolean includeAll) {
        
        Map<String, DaoMethod> methodMap = new TreeMap<String, DaoMethod>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            DaoMethod daoMethod = new DaoMethod(method, 0);
            if (excludedMethods.contains(method.getName()))
                continue;
            if (method.isAnnotationPresent(MethodDescription.class)) {
                MethodDescription methodDescription = method.getAnnotation(MethodDescription.class);
                CrudType crudType = methodDescription.crudType();
                if (crudType == CrudType.NOT_AVAILABLE || (!includeAll && !(crudType == CrudType.READ || crudType == CrudType.READ_RESTRICTED))) {
                    continue;
                }
                daoMethod.setCrudType(crudType);
                daoMethod.setModelClass(method.getAnnotation(MethodDescription.class).modelClass());
                if (!daoMethod.getModelClass().equals(java.util.Map.class)) {
                    daoMethod.setMapperClass(method.getAnnotation(MethodDescription.class).mapperClass());
                }
                daoMethod.setDescription(method.getAnnotation(MethodDescription.class).description());
                daoMethod.setPopulateMethod(methodDescription.populateMethod());
                if (method.isAnnotationPresent(DAODescription.class)) {
                    DAODescription daoDescription = method.getAnnotation(DAODescription.class);
                    daoMethod.setDaoMethod(daoDescription.daoMethod());
                    daoMethod.setDaoParamMapper(daoDescription.daoParamMapper());
                    daoMethod.setDaoReturnValueName(daoDescription.returnValueName());
                    daoMethod.setDaoID(daoDescription.daoID());
                    daoMethod.setUseMapper(daoDescription.useMapper());

                }
                else {
                    System.out.println(method.getName());
                }
            }
            methodMap.put(method.getName(), daoMethod);
        }
        return methodMap;
    }

    private DaoParam getDaoParamAnnotaion(Annotation[][] annotation, int i) {
        if (annotation == null || annotation.length <= i)
            return null;

        for (int j = 0; j < annotation[i].length; j++) {
            if (annotation[i][j].annotationType() == DaoParam.class) {
                return (DaoParam) annotation[i][j];
            }
        }

        return null;
    }

    public static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    
    
    @Test
    public void dummy() {
        assertTrue(true);
    }
}
