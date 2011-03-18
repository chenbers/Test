package com.inthinc.pro.table.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.Expression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.DataProvider;
import org.richfaces.model.ExtendedFilterField;
import org.richfaces.model.FilterField;
import org.richfaces.model.Modifiable;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;

import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterFactory;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.table.CustomFilterField;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.table.model.provider.PaginationDataProvider;


public class PaginationTableDataModel<T> extends ExtendedDataModel implements Serializable, Modifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5823194019411336605L;
	private PaginationDataProvider<T> dataProvider;
	private Object rowKey;
	private List<Object> wrappedKeys = Collections.synchronizedList(new ArrayList<Object>());
	private Map<Object, T> wrappedData = Collections.synchronizedMap(new HashMap<Object, T>());
	List<FilterField> filterFields;
	List<SortField2> sortFields;
	private PageData pageData;

	
	public PaginationTableDataModel(PaginationDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}
	/**
	 * This method never called from framework.
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
	 */
	@Override
	public Object getRowKey() {
		return rowKey;
	}

	/**
	 * This method normally called by Visitor before request Data Row.
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
	 */
	@Override
	public void setRowKey(Object key) {
		rowKey = key;
	}

	/** 
	 * This is main part of Visitor pattern. Method called by framework many times during request processing. 
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext, org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range, java.lang.Object)
	 */
	@Override
	public synchronized void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {

		int rowC = getRowCount();
		int firstRow = ((SequenceRange) range).getFirstRow();
		int numberOfRows = ((SequenceRange) range).getRows();

		if (numberOfRows <= 0) {
			numberOfRows = rowC;
		}
		if (firstRow > rowC) {
			firstRow = 0;
		}
		
		if (wrappedKeys.size() > 0) { 
			for (Object key : wrappedKeys) {
				setRowKey(key);
				visitor.process(context, key, argument);
			}
		} else { // if not serialized, than we request data from data provider
//			wrappedKeys = Collections.synchronizedList(new ArrayList<Object>());
			int endRow = firstRow + numberOfRows;
			if (endRow > rowC){
				endRow = rowC; 
			}
			Integer rowIndex = firstRow;
			for (T item : loadData(firstRow, endRow-1)) {
				Object key = rowIndex;
				wrappedKeys.add(key);
				wrappedData.put(key, item);
				rowIndex++;
				visitor.process(context, key, argument);
			}
		}
	}//walk

	/**
	 * Load range of data items from the source.
	 * Starting from startRow, and up to and including endRow
	 * @param startRow
	 * @param endRow
	 * @return list of ordered data
	 */
	protected List<T> loadData(int startRow, int endRow) {
		if (startRow < 0){
			startRow = 0;
		}
		int rowCount = getRowCount();
		if (endRow >= rowCount){
			endRow = rowCount-1;
		}
		//load all from provider and get sublist
		return dataProvider.getItemsByRange(startRow, endRow);
	}//loadData

	/**
	 * This method must return actual data rows count from the Data Provider. It is used by pagination control
	 * to determine total number of data items.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	private Integer tableRowCount; // better to buffer row count locally

	@Override
	public int getRowCount() {
		if (tableRowCount == null) { // || !rowCount.equals(actualRowCount)) {
			Integer actualRowCount = dataProvider.getRowCount();
			tableRowCount = actualRowCount;
			if (pageData != null) {
				pageData.updatePage(tableRowCount);
			}
		} 
		return tableRowCount.intValue();
	}

	/**
	 *  This is main way to obtain data row. It is intensively used by framework. 
	 * We strongly recommend use of local cache in that method. 
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowData()
	 */
	@Override
	public T getRowData() {
		if (rowKey == null) {
			return null;
		} else {
			return  getObjectByKey(rowKey);
		}
	}

	public Object getKey(T o) {
		return dataProvider.getKey(o);
	}

	public T getObjectByKey(Object key) {
		return wrappedData.get(key);
	}

	private Integer rowIndex;

	/**
	 * Unused rudiment from old JSF staff. (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		return rowIndex.intValue();
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#setRowIndex(int)
	 */
	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	@Override
	public void setWrappedData(Object data) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Never called by framework.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#isRowAvailable()
	 */
	@Override
	public boolean isRowAvailable() {
		return getRowData() != null;
	}

	
	/**
	 * Resets internal cached data. Call this method to reload data from data
	 * provider on first access for data.
	 */
	public void reset(){
		resetPage();
		tableRowCount = null;
		rowIndex = -1;
	}
	public synchronized void resetPage(){
		wrappedKeys.clear();
		wrappedData.clear();
		rowKey = null;
	}

	public DataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PaginationDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public void modify(List<FilterField> mFilterFields, List<SortField2> mSortFields) {
		
		if (filtersChanged(mFilterFields)) {
			this.filterFields = mFilterFields;
			appendFilters();
			reset();
		}
		if (sortsChanged(mSortFields)) {
			this.sortFields = mSortFields;
			appendSorts();
			resetPage();
		}
	}
	private boolean sortsChanged(List<SortField2> mSortFields) {
		if ((this.sortFields == null || this.sortFields.size() == 0) && 
			 (mSortFields != null && mSortFields.size() != 0))
			return true;

		if (sortFields == null)
			return false;
		
		for (SortField2 sortField : sortFields) {
			String propertyName = getPropertyName(sortField.getExpression());
			Ordering ordering = sortField.getOrdering();
			for (SortField2 mSortField : mSortFields) {
				String mPropertyName = getPropertyName(mSortField.getExpression());
				if (propertyName.equals(mPropertyName)) {
					Ordering mOrdering = mSortField.getOrdering();
					if (ordering == null && mOrdering == null )
						continue;
					if ((ordering == null && mOrdering != null ) ||
						(ordering != null && mOrdering == null ) ||
						!ordering.equals(mOrdering))
						return true;
				}
				else {
					return true;
				}
				
			}
		}
		return false;
	}
	private boolean filtersChanged(List<FilterField> mFilterFields) {
		if (this.filterFields == null && mFilterFields != null)
			return true;
		
		for (FilterField filterField : filterFields) {
			String propertyName = getPropertyName(filterField.getExpression());
			if (filterField instanceof ExtendedFilterField) {
				String filterValue = ((ExtendedFilterField) filterField).getFilterValue();
				for (FilterField mFilterField : mFilterFields) {
					String mPropertyName = getPropertyName(mFilterField.getExpression());
					if (propertyName.equals(mPropertyName)) {
						if (filterField instanceof ExtendedFilterField) {
							String mFilterValue = ((ExtendedFilterField) mFilterField).getFilterValue();
							if (filterValue == null && (mFilterValue == null || mFilterValue.isEmpty()))
								continue;
							if ((filterValue == null && mFilterValue != null ) ||
								(filterValue != null && mFilterValue == null ) ||
								!filterValue.equals(mFilterValue))
								return true;
						}
					}
				}
			}
		}
		return false;
	}
	private void appendFilters() {
		if (filterFields != null) {
			for (FilterField filterField : filterFields) {
				String propertyName = getPropertyName(filterField.getExpression());

				if (filterField instanceof CustomFilterField) {
					Object filterObject = ((CustomFilterField) filterField).getFilterObject();
					if (filterObject instanceof TableFilterFactory) {
						List<TableFilterField> tableFilterFieldList = ((TableFilterFactory)filterObject).getFilters(propertyName);
						for (TableFilterField tableFilterField : tableFilterFieldList) {
							dataProvider.addFilterField(tableFilterField);
						}
						return;
					}
				}
				if (filterField instanceof ExtendedFilterField) {
					String filterValue = ((ExtendedFilterField) filterField).getFilterValue();
					dataProvider.addFilterField(new TableFilterField(propertyName, filterValue));
				}
			}
		}
	}

	private void appendSorts() {
		if (sortFields != null) {
			for (SortField2 sortField : sortFields) {
				Ordering ordering = sortField.getOrdering();
				
				if (Ordering.ASCENDING.equals(ordering) || Ordering.DESCENDING.equals(ordering)) {
					String propertyName = getPropertyName( sortField.getExpression());
					dataProvider.addSortField(new TableSortField(Ordering.ASCENDING.equals(ordering) ? SortOrder.ASCENDING : SortOrder.DESCENDING, propertyName));
				}
			}
		}
	}
	
	private String getPropertyName(Expression expression) {
		try {
			// not sure if this is correct way to do this,
			// trying to get the name of the field to sort on from something like #{item.field}, just need field
			if (expression.getExpressionString() == null)
				return "";
			
			String propName = expression.getExpressionString().trim();
			if (propName.startsWith("#{"))
				propName = propName.substring(2);
			if (propName.endsWith("}"))
				propName = propName.substring(0, propName.length()-1);
			
			int fieldIdx = propName.lastIndexOf(".");
			if (fieldIdx != -1)
				propName = propName.substring(fieldIdx+1);
			
			return propName;
			
		} catch (ELException e) {
			throw new FacesException(e.getMessage(), e);
		}
	}
	public PageData getPageData() {
		return pageData;
	}
	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}
}
