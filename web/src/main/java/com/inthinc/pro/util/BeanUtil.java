package com.inthinc.pro.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * Methods for working with beans, complements the methods in {@link BeanUtils}.
 * 
 * @author David Gileadi
 */
public class BeanUtil
{
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
     * @param ignoreProperties
     *            List of property names to ignore. Child property names are separated by dots, e.g. "property.child".
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static void deepCopy(Object source, Object target, List<String> ignoreProperties)
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
                final Method writeMethod = targetDescriptor.getWriteMethod();
                if ((readMethod != null) && (writeMethod != null))
                {
                    try
                    {
                        // create a destination
                        final Object sourceProperty = readMethod.invoke(source, new Object[0]);
                        if (sourceProperty != null)
                        {
                            // simple or deep copy
                            if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum())
                                writeMethod.invoke(target, new Object[] { sourceProperty });
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

                                // filter ignore properties by this property's prefix
                                final String prefix = descriptor.getName() + '.';
                                final LinkedList<String> childIgnore = new LinkedList<String>();
                                if (ignoreProperties != null)
                                    for (final String key : ignoreProperties)
                                        if (key.startsWith(prefix))
                                            childIgnore.add(key.substring(prefix.length()));

                                // recurse
                                deepCopy(sourceProperty, targetProperty, childIgnore);
                            }
                        }
                    }
                    catch (Throwable ex)
                    {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * Deeply compares all properties of the item to the given compare item, nulling the property of the item if they aren't equal. Ignores already-null properties in the item.
     * 
     * @param item
     *            The item to compare and init.
     * @param compareTo
     *            The item to compare it to.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static void compareAndInit(Object item, Object compareTo)
    {
        try
        {
            for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
            {
                final Method read1 = descriptor.getReadMethod();
                final Method read2 = BeanUtils.getPropertyDescriptor(compareTo.getClass(), descriptor.getName()).getReadMethod();
                if ((read1 != null) && (read2 != null))
                {
                    final Object o1 = read1.invoke(item, new Object[0]);
                    if (o1 != null)
                    {
                        final Object o2 = read2.invoke(compareTo, new Object[0]);
                        if (o2 != null)
                        {
                            // recursive or simple compare
                            final Class<?> clazz = descriptor.getPropertyType();
                            if (clazz != null)
                            {
                                if (!BeanUtils.isSimpleProperty(clazz) && !clazz.isEnum())
                                    compareAndInit(o1, o2);
                                else if (!o1.equals(o2))
                                {
                                    final Method write = descriptor.getWriteMethod();
                                    if (write != null)
                                    {
                                        if (clazz.isPrimitive())
                                            write.invoke(item, new Object[] { BeanUtils.instantiateClass(o1.getClass().getConstructor(String.class), new Object[] { "0" }) });
                                        else
                                            write.invoke(item, new Object[] { null });
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
        populatePropertyNames(item, "", names);
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
     */
    private static void populatePropertyNames(Object item, String prefix, LinkedList<String> names)
    {
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
        {
            final Class<?> clazz = descriptor.getPropertyType();
            if (clazz != null)
            {
                try
                {
                    if (BeanUtils.isSimpleProperty(clazz) || clazz.isEnum())
                    {
                        if (!"class".equals(descriptor.getName()))
                            names.add(prefix + descriptor.getName());
                    }
                    else
                    {
                        // get or create the container
                        final Method readMethod = descriptor.getReadMethod();
                        if (readMethod != null)
                        {
                            final Object targetProperty = readMethod.invoke(item, new Object[0]);
                            if (targetProperty != null)
                                populatePropertyNames(targetProperty, prefix + descriptor.getName() + '.', names);
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
}
