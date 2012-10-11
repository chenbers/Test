package com.inthinc.pro.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanInitializationException;

import com.inthinc.pro.backing.BaseAdminBean;
import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.dao.util.SimpleType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReferenceEntity;
import com.inthinc.pro.model.configurator.ProductType;

/**
 * Methods for working with beans, complements the methods in {@link BeanUtils}.
 * 
 * @author David Gileadi
 */
public class BeanUtil
{
	
    protected static final Logger logger        = LogManager.getLogger(BeanUtil.class);

    /**
     * Creates a deep clone of the given source object. Requires that all complex members must have a no-argument constructor.
     * 
     * @param source
     *            The object to clone.
     * @return The new clone.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static Object deepClone(Object source)
    {
        final Object clone = BeanUtils.instantiateClass(source.getClass());
        deepCopy(source, clone, null);
        return clone;
    }

    /**
     * Deep copies the source object into the target object. Requires that all complex members must have a no-argument constructor.
     * 
     * @param source
     *            The source bean.
     * @param target
     *            The target bean.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static void deepCopy(Object source, Object target)
    {
        deepCopy(source, target, null);
    }

    /**
     * Deep copies the source object into the target object. Requires that all complex members must have a no-argument constructor.
     * 
     * @param source
     *            The source bean.
     * @param target
     *            The target bean.
     * @param ignoreProperties
     *            Optional list of property names to ignore. Child property names are separated by dots, e.g. "property.child".
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static void deepCopy(Object source, Object target, List<String> ignoreProperties)
    {
        final HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put(source, target);
        deepCopy(source, target, ignoreProperties, map, false);
    }

	public static void deepCopyNonNull(Object source, Object target) {
        deepCopy(source, target, null, null, true);
		
	}
    /**
     * Deep copies the source object into the target object. Requires that all complex members must have a no-argument constructor.
     * 
     * @param source
     *            The source bean.
     * @param target
     *            The target bean.
     * @param ignoreProperties
     *            Optional list of property names to ignore. Child property names are separated by dots, e.g. "property.child".
     * @param map
     *            A map of source-to-target copies we've already made, to handle circular references.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    @SuppressWarnings("unchecked")
    private static void deepCopy(Object source, Object target, List<String> ignoreProperties, Map<Object, Object> map, boolean skipNull)
    {
        // deep-copy children
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(source.getClass()))
        {
            if ((ignoreProperties != null) && ignoreProperties.contains(descriptor.getName()))
                continue;

            final Class<?> clazz = descriptor.getPropertyType();
            if (clazz != null)
            {
                final Method readMethod = descriptor.getReadMethod();
                final PropertyDescriptor targetDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), descriptor.getName());
                if (targetDescriptor != null)
                {
                    final Method writeMethod = targetDescriptor.getWriteMethod();
                    if ((readMethod != null) && (writeMethod != null))
                    {
                        try
                        {
                            // create a destination
                            final Object sourceProperty = readMethod.invoke(source, new Object[0]);

                            //if the value is null, just write it to the target.
                            if(sourceProperty == null) {
                            	if (!skipNull)
                            		writeMethod.invoke(target, sourceProperty);
                            }
                            // simple copy
                            else if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum() || clazz.isArray() || clazz.isAssignableFrom(Date.class) 
                                    || clazz.isAssignableFrom(TimeZone.class) || ReferenceEntity.class.isAssignableFrom(clazz) 
                                    || BaseBean.class.isAssignableFrom(clazz) 
                                    || (SimpleType.valueOf(source.getClass()) != null && SimpleType.valueOf(source.getClass()).getSimpleTypes().contains(clazz))
                                    || clazz.isAssignableFrom(Locale.class))
                                writeMethod.invoke(target, sourceProperty);
                            // circular reference: simply store the target reference
                            else if (map.get(sourceProperty) != null)
                                writeMethod.invoke(target, map.get(sourceProperty));
                            // deep copy
                            else
                            {
                                // get or create the container
                                Object targetProperty = null;
                                final Method targetReader = targetDescriptor.getReadMethod();
                                if (targetReader != null)
                                    targetProperty = targetReader.invoke(target, new Object[0]);
                                if (targetProperty == null)
                                {
                                    targetProperty = BeanUtils.instantiateClass(sourceProperty.getClass());
                                    writeMethod.invoke(target, targetProperty);
                                }

                                // collection, map or custom object
                                if (targetProperty instanceof Collection)
                                {
                                    ((Collection) targetProperty).clear();
                                    ((Collection) targetProperty).addAll((Collection) sourceProperty);
                                }
                                else if (targetProperty instanceof Map)
                                {
                                    ((Map) targetProperty).clear();
                                    ((Map) targetProperty).putAll((Map) sourceProperty);
                                }
                                else
                                {
                                    // filter ignore properties by this property's prefix
                                    final String prefix = descriptor.getName() + '.';
                                    final LinkedList<String> childIgnore = new LinkedList<String>();
                                    if (ignoreProperties != null)
                                        for (final String key : ignoreProperties)
                                            if (key.startsWith(prefix))
                                                childIgnore.add(key.substring(prefix.length()));

                                    // recurse
                                    map.put(sourceProperty, targetProperty);
                                    deepCopy(sourceProperty, targetProperty, childIgnore, map, skipNull);
                                    map.remove(sourceProperty);
                                }
                            }
                        }

                        catch (Throwable ex)
                        {
                            throw new FatalBeanException("Could not copy properties from source to target " + 
                                    ((readMethod != null) ? readMethod.getName() : "") + " " + ((writeMethod != null) ? writeMethod.getName() : "") , ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Deeply compares all properties of the item to the given compare item, nulling the property of the item if they aren't equal. Ignores already-null properties in the item.
     * Collections and maps are compared, and only common items are kept. If there are no common items, empty is used.
     * 
     * @param item
     *            The item to compare and init.
     * @param compareTo
     *            The item to compare it to.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    @SuppressWarnings("unchecked")
    public static void compareAndInit(Object item, Object compareTo)
    {
        final HashSet<Object> compared = new HashSet<Object>();
        compared.add(item);
        compareAndInit(item, compareTo, compared);
    }

    /**
     * Deeply compares all properties of the item to the given compare item, nulling the property of the item if they aren't equal. Ignores already-null properties in the item.
     * Collections and maps are compared, and only common items are kept. If there are no common items, empty is used.
     * 
     * @param item
     *            The item to compare and init.
     * @param compareTo
     *            The item to compare it to.
     * @param compared
     *            A map of objects we've already compared, to handle circular references.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    private static void compareAndInit(Object item, Object compareTo, Set<Object> compared)
    {
        try
        {
            for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
            {
                final Method read1 = descriptor.getReadMethod();
                final PropertyDescriptor compareDescriptor = BeanUtils.getPropertyDescriptor(compareTo.getClass(), descriptor.getName());
                if (compareDescriptor != null)
                {
                    final Method read2 = compareDescriptor.getReadMethod();
                    if ((read1 != null) && (read2 != null))
                    {
                        final Object o1 = read1.invoke(item, new Object[0]);
                        if (o1 != null)
                        {
                            if (compared.contains(o1))
                                continue;

                            final Object o2 = read2.invoke(compareTo, new Object[0]);
                            if (o2 != null)
                            {
                                final Class<?> clazz = descriptor.getPropertyType();
                                if (clazz != null)
                                {
                                    // recursive compare

                                    if (!BeanUtils.isSimpleProperty(clazz) && !clazz.isEnum() && !Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz)
                                            && !clazz.isArray() && !Date.class.isAssignableFrom(clazz) && !TimeZone.class.isAssignableFrom(clazz) 
                                            && !ReferenceEntity.class.isAssignableFrom(clazz) && !BaseBean.class.isAssignableFrom(clazz) 
                                            && !(SimpleType.valueOf(item.getClass()) != null && SimpleType.valueOf(item.getClass()).getSimpleTypes().contains(clazz))
                                            && !Locale.class.isAssignableFrom(clazz))
                                    {
                                        compared.add(o1);
                                        compareAndInit(o1, o2, compared);
                                    }
                                    // include common items
                                    else if (Collection.class.isAssignableFrom(clazz))
                                    {
                                        ((Collection<?>) o1).retainAll((Collection<?>) o2);
                                    }
                                    else if (Map.class.isAssignableFrom(clazz))
                                    {
                                        ((Map<?, ?>) o1).entrySet().retainAll(((Map<?, ?>) o2).entrySet());
                                    }
                                    // simple compare
                                    else if ((clazz.isArray() && !Arrays.deepEquals((Object[]) o1, (Object[]) o2)) || !o1.equals(o2))
                                    {
                                        final Method write = descriptor.getWriteMethod();
                                        if (write != null)
                                        {
                                            if (clazz.isPrimitive())
                                                write.invoke(item, new Object[] { BeanUtils.instantiateClass(o1.getClass().getConstructor(String.class), new Object[] { "0" }) });
                                            else
                                                try
                                                {
                                                    write.invoke(item, new Object[] { null });
                                                }
                                                catch (InvocationTargetException e)
                                                {
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable ex)
        {
            throw new FatalBeanException("Could not compare and init properties", ex);
        }
    }

    /**
     * Returns all property names of the given item, including child property names in the form "property.child".
     * 
     * @param item
     *            The item to get the names for.
     * @return An array of property names.
     */
    public static String[] getPropertyNames(Object item)
    {
        final LinkedList<String> names = new LinkedList<String>();
        final HashSet<Object> handled = new HashSet<Object>();
        handled.add(item);
        populatePropertyNames(item, "", names, handled);
        return names.toArray(new String[names.size()]);
    }

    /**
     * Populates the given list with property names from the given item, recursively.
     * 
     * @param item
     *            The item to populate with.
     * @param prefix
     *            A prefix to prepend to property names, used for calling recursively.
     * @param names
     *            A list to append names to.
     * @param handled
     *            A set of objects we've already handled, for circular references.
     */
    private static void populatePropertyNames(Object item, String prefix, LinkedList<String> names, HashSet<Object> handled)
    {
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
        {
            final Class<?> clazz = descriptor.getPropertyType();
//            System.out.println(descriptor.getName());
            if (clazz != null)
            {
                try
                {
                    if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum() || clazz.isAssignableFrom(Date.class) || clazz.isAssignableFrom(TimeZone.class) || 
                            ReferenceEntity.class.isAssignableFrom(clazz) || BaseBean.class.isAssignableFrom(clazz) ||
                            clazz.isArray())
                    {
                        if (!"class".equals(descriptor.getName()))
                            names.add(prefix + descriptor.getName());
                    }
                    else if (java.util.List.class.isAssignableFrom(clazz)) {
                    	names.add(prefix + descriptor.getName());
                    }
                    else
                    {
                        // get or create the container
                        final Method readMethod = descriptor.getReadMethod();
                        if (readMethod != null)
                        {
                            final Object targetProperty = readMethod.invoke(item, new Object[0]);
                            if ((targetProperty != null) && !handled.contains(targetProperty))
                            {
                                handled.add(targetProperty);
                                populatePropertyNames(targetProperty, prefix + descriptor.getName() + '.', names, handled);
                            }
                        }
                    }
                }
                catch (Throwable ex)
                {
                    throw new FatalBeanException("Could not init update field properties", ex);
                }
            }
        }
    }
    
    /**
     * This is more of a specialized copy and init. Used for areas in the app where we store boolean arrays.
     * 
     * @param target - resulting item
     * @param source - item to compare resulting item with
     */
    public static void compareAndInitBoolList(List<Boolean> target,List<Boolean> source)
    {
        boolean reset = false;
        for(int i = 0;i < target.size();i++)
        {
            if(source.get(i) != null && target.get(i) != null && !target.get(i).equals(source.get(i)))
            {
                reset = true;
                break;
            }
        }
        
        if(reset)
            for(int i = 0;i < target.size();i++)
            {
                target.set(i, Boolean.FALSE);
            }
    }
    
    @SuppressWarnings("unchecked")
    public static Object getFieldValue(Object object, String propertyName){
        Class clazz = object.getClass();
        try{
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);
            Object [] nullArgs = {};
            Object property = propertyDescriptor.getReadMethod().invoke(object, nullArgs);
            if (property instanceof ProductType){
                return ((ProductType)property).name();
            }
            else
            if (property instanceof Enum){
                return ((Enum)property).name();
            }
            else{
                return property;
            }
        }
        catch(Exception e){
            return null;
        }
        
    }



    public static void copyProperty(Object source, Object dest, String propertyName) {
        Class clazz = source.getClass();
        try{
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);
            Object [] nullArgs = {};
            Object property = propertyDescriptor.getReadMethod().invoke(source, nullArgs);
            
            if (property == null) {
            	if (propertyDescriptor.getPropertyType().isAssignableFrom(Date.class)) {
            		property = new Date(0);
            	}
            	else if (propertyDescriptor.getPropertyType().isAssignableFrom(String.class)) {
            		property = new String("");
            	}
            }
            
            PropertyDescriptor writePropertyDescriptor = BeanUtils.getPropertyDescriptor(dest.getClass(), propertyName);
            Method writeMethod = writePropertyDescriptor.getWriteMethod();
            writeMethod.invoke(dest, property);
        }
        catch(Exception e){
        	logger.error("error in copyProperty" , e);
            return;
        }
        
    }


}
