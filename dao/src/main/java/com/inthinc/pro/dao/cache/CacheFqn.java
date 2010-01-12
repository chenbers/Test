package com.inthinc.pro.dao.cache;

import java.util.ArrayList;
import java.util.List;

import org.jboss.cache.Fqn;
public class CacheFqn {

	public static Fqn<String> root = Fqn.fromString(Fqn.SEPARATOR + "root");
	
	
    @SuppressWarnings("unchecked")
	public static Fqn<?> getListFqn(Class clazz, Object id, Class listClazz)
	{
    	List<String> names = new ArrayList<String>();
    	names.add(clazz.getSimpleName());
    	names.add(id.toString());
    	names.add(listClazz.getSimpleName()+"List");
    	return Fqn.fromRelativeList(root, names);
		
	}
    @SuppressWarnings("unchecked")
	public static Fqn<?> getListFqn(Fqn<String> base, Class clazz, Object id, Class listClazz)
	{
    	List<String> names = new ArrayList<String>();
    	names.add(clazz.getSimpleName());
    	names.add(id.toString());
    	names.add(listClazz.getSimpleName()+"List");
    	return Fqn.fromRelativeList(base, names);
		
	}
    @SuppressWarnings("unchecked")
	public static Fqn<?> getFqn(Class clazz)
	{
    	List<String> names = new ArrayList<String>();
    	names.add(clazz.getSimpleName());
    	return Fqn.fromRelativeList(root, names);
	}

    @SuppressWarnings("unchecked")
	public static Fqn<?> getFqn(Class clazz, Object id)
	{
    	List<String> names = new ArrayList<String>();
    	names.add(clazz.getSimpleName());
    	names.add(id.toString());
    	return Fqn.fromRelativeList(root, names);
	}

}
