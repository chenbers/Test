package com.inthinc.pro.table.dao.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.table.dao.model.SimpleTableItem;
import com.inthinc.pro.table.dao.pagination.PaginationDAOService;

import com.inthinc.pro.table.dao.model.TableFilterField;
import com.inthinc.pro.table.dao.model.TableSortField;
import com.inthinc.pro.table.dao.model.TableSortField.SortOrder;


public class SimplePaginationDAO implements PaginationDAOService<SimpleTableItem> {

	protected static final Logger logger = LogManager.getLogger(SimplePaginationDAO.class);
	List<SimpleTableItem> itemList;
	
	Map<String, Comparator<SimpleTableItem>> comparatorMap;
	
	public void init()
	{
		logger.info("init SimplePaginationDAO");
		itemList = new ArrayList<SimpleTableItem>();
		for (int i = 0; i < 200; i++)
			itemList.add(new SimpleTableItem("Test Item " + i, Integer.valueOf(i), Float.valueOf(i)));
		
		comparatorMap = new HashMap<String, Comparator<SimpleTableItem>> ();
		comparatorMap.put("strItem", new StrItemComparator());
		comparatorMap.put("intItem", new IntItemComparator());
		comparatorMap.put("floatItem", new FloatItemComparator());
	}
	@Override
	public int countAll(List<TableFilterField> filter) {
System.out.println("countAll ");		
		List<SimpleTableItem> filteredList = getFilteredList(filter, itemList);
		System.out.println("countAll: " + filteredList.size());
		return filteredList.size();
	}

	@Override
	public List<SimpleTableItem> getItemsByRange(int firstRow, int lastRow,
			List<TableSortField> sort, List<TableFilterField> filter) {
System.out.println("getItemsByRange " + firstRow + " - " + lastRow);		
		List<SimpleTableItem> filteredList = getFilteredList(filter, itemList);
		List<SimpleTableItem> sortedList = getSortedList(sort, filteredList);
		if (firstRow < 0)
			firstRow = 0;
		if (lastRow > sortedList.size())
			lastRow = sortedList.size();
		
		return sortedList.subList(firstRow, lastRow);
	}
	
	private List<SimpleTableItem> getSortedList(List<TableSortField> sort, List<SimpleTableItem> list) {
		if (sort == null || sort.size() == 0)
			return list;
		for (TableSortField sortField : sort) {
			String baseField = sortField.getField();
			int idx = baseField.lastIndexOf(".");
			if (idx != -1) {
				baseField = baseField.substring(idx+1);
			}
			logger.info("sort field: " + baseField);
			Comparator<SimpleTableItem> comparator = comparatorMap.get(baseField);
			if (comparator != null) {
				Collections.sort(list, comparator);
				System.out.println("first item: " + list.get(0).getIntItem());
				if (sortField.getOrder().equals(SortOrder.DESCENDING)) {
					logger.info("sort reverse");
					Collections.reverse(list);
					System.out.println("first item after reverse: " + list.get(0).getIntItem());
				}
			}
			else {
				logger.error("error finding comparator for field: " + sortField.getField() + " (" + baseField + ")");
			}
				
		}
		return list;
	}
	
	private List<SimpleTableItem> getFilteredList(List<TableFilterField> filter, List<SimpleTableItem> originalList) {
		List<SimpleTableItem> list = originalList.subList(0, originalList.size());
		if (filter == null || filter.size() == 0)
			return list;
		List<SimpleTableItem> filteredList = new ArrayList<SimpleTableItem>();
		for (TableFilterField filterField : filter) {
			if (filterField.getField().contains("strItem")) {
				for (SimpleTableItem item : originalList) {
					if (item.getStrItem().startsWith(filterField.getFilter())) {
						filteredList.add(item);
					}
				}
			}
			else if (filterField.getField().contains("intItem")) {
				for (SimpleTableItem item : originalList) {
					if (item.getIntItem().toString().startsWith(filterField.getFilter())) {
						filteredList.add(item);
					}
				}
			}
			else if (filterField.getField().contains("floatItem")) {
				for (SimpleTableItem item : originalList) {
					if (item.getFloatItem().toString().startsWith(filterField.getFilter())) {
						filteredList.add(item);
					}
				}
			}
			originalList = filteredList;
			filteredList = new ArrayList<SimpleTableItem>();
		}
		return originalList;
	}
	
	class StrItemComparator implements Comparator<SimpleTableItem> {

		@Override
		public int compare(SimpleTableItem o1, SimpleTableItem o2) {
			return o1.getStrItem().compareTo(o2.getStrItem());
		}
		
	}

	class IntItemComparator implements Comparator<SimpleTableItem> {

		@Override
		public int compare(SimpleTableItem o1, SimpleTableItem o2) {
			return o1.getIntItem().compareTo(o2.getIntItem());
		}
		
	}
	class FloatItemComparator implements Comparator<SimpleTableItem> {

		@Override
		public int compare(SimpleTableItem o1, SimpleTableItem o2) {
			return o1.getFloatItem().compareTo(o2.getFloatItem());
		}
		
	}
}
