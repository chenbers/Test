package com.inthinc.pro.backing.paging;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.pagination.EventCategoryFilter;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasePagingNotificationsBean<T> extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -628902123783350377L;

	protected final static Integer DAYS_BACK = 1;
	protected final static Integer MAX_DAYS_BACK = 7;

	private ReportRenderer reportRenderer;
	private ReportCriteriaService reportCriteriaService;

	protected Event clearItem;

	private EventDAO eventDAO;

	private EventCategoryFilter filterCategory;
	private Integer filterCategoryKey;
	
	private String filterForgiven;
	
    public String getFilterForgiven() {
        return filterForgiven;
    }

    public void setFilterForgiven(String filterForgiven) {
        this.filterForgiven = filterForgiven;
    }

    public Integer getFilterCategoryKey() {
		return filterCategoryKey;
	}

	public void setFilterCategoryKey(Integer filterCategoryKey) {
		if (filterCategoryKey != null && eventCategoryMap != null) {
			setFilterCategory(eventCategoryMap.get(filterCategoryKey));
		} else {
			setFilterCategory(null);
		}
		this.filterCategoryKey = filterCategoryKey;
	}

	public EventCategoryFilter getFilterCategory() {
		return filterCategory;
	}

	public void setFilterCategory(EventCategoryFilter filterCategory) {
		this.filterCategory = filterCategory;
	}

	protected abstract List<EventCategory> getCategories();

	protected Map<Integer, EventCategoryFilter> eventCategoryMap;

    protected List<SelectItemGroup> filterCategories;
    public List<SelectItemGroup> getFilterCategories() {
        if (filterCategories == null) {
            eventCategoryMap = new HashMap<Integer, EventCategoryFilter>();
            filterCategories = new ArrayList<SelectItemGroup>();
            filterCategories.add(getBlankGroup());
            for (EventCategory category : getCategories()) {
                if (category.getSubCategorySet() == null)
                    continue;
                for (EventSubCategory subCategory : category.getSubCategorySet()) {
                    String subCategoryStr = MessageUtil.getMessageString(subCategory.toString(), getLocale());
                    SelectItem[] items = getItemsBySubCategory(subCategory);
                    if (items != null && items.length > 0)
                        filterCategories.add(new SelectItemGroup(subCategoryStr, subCategoryStr, false, items));
                }
            }

        }
        return filterCategories;
    }
    
    protected SelectItem[] getItemsBySubCategory(EventSubCategory subCategory) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (EventType eventType : subCategory.getEventTypeSet()) {
            if ((eventType.isHOSOnlyEvent() && !getAccountIsHOS()) ||
                (eventType.isWaysmartOnlyEvent() && !getAccountIsWaysmart()))
                continue;
            items.add(new SelectItem(eventType.getCode(), MessageUtil.getMessageString(eventType.toString(), getLocale())));
            eventCategoryMap.put(eventType.getCode(), eventType.getEventCategoryFilter());
        }
        return items.toArray(new SelectItem[0]);
    }

    protected SelectItemGroup getBlankGroup(){
        SelectItem[] items = new SelectItem[1]; 
        items[0] = new SelectItem(EventType.UNKNOWN.getCode(), "");
        eventCategoryMap.put(EventType.UNKNOWN.getCode(), new EventCategoryFilter(EventType.UNKNOWN, null, null));
        SelectItemGroup itemGroup = new SelectItemGroup("","",false,items);      
        return itemGroup;
    }
    
    
	protected final static String BLANK_SELECTION = "&#160;";

	protected static void sort(List<SelectItem> selectItemList) {
		Collections.sort(selectItemList, new Comparator<SelectItem>() {
			@Override
			public int compare(SelectItem o1, SelectItem o2) {
				return o1.getLabel().toLowerCase().compareTo(
						o2.getLabel().toLowerCase());
			}
		});
	}

	public List<SelectItem> getTeams() {
		final List<SelectItem> teams = new ArrayList<SelectItem>();
		SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
		blankItem.setEscape(false);
		teams.add(blankItem);
		for (final Group group : getGroupHierarchy().getGroupList()) {
			String fullName = getGroupHierarchy().getFullGroupName(
					group.getGroupID());
			if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
				fullName = fullName.substring(0, fullName.length()
						- GroupHierarchy.GROUP_SEPERATOR.length());
			}

			teams.add(new SelectItem(group.getGroupID(), fullName));

		}
		sort(teams);

		return teams;
	}

    public List<SelectItem> getFilterForgivenSelections() {
        final List<SelectItem> filterSelections = new ArrayList<SelectItem>();
        SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
        blankItem.setEscape(false);
        filterSelections.add(blankItem);
        filterSelections.add(new SelectItem("0", MessageUtil.getMessageString("included", getLocale())));
        filterSelections.add(new SelectItem("1", MessageUtil.getMessageString("excluded", getLocale())));
        return filterSelections;
    }
	public Event getClearItem() {
		return clearItem;
	}

	public void setClearItem(Event clearItem) {
		this.clearItem = clearItem;
	}

	public void excludeEventAction() {

		if (clearItem != null && clearItem.getForgiven().intValue() == 0) {
			if (eventDAO
					.forgive(clearItem.getDriverID(), clearItem.getNoteID()) >= 1) {
				refreshPage();
			}
		}
	}

	public void includeEventAction() {

		if (clearItem != null && clearItem.getForgiven().intValue() == 1) {
			if (eventDAO.unforgive(clearItem.getDriverID(), clearItem
					.getNoteID()) >= 1) {
				refreshPage();
			}
		}
	}

	public abstract void refreshPage();

	public void init() {
	}

	public void setReportRenderer(ReportRenderer reportRenderer) {
		this.reportRenderer = reportRenderer;
	}

	public ReportRenderer getReportRenderer() {
		return reportRenderer;
	}

	public void setReportCriteriaService(
			ReportCriteriaService reportCriteriaService) {
		this.reportCriteriaService = reportCriteriaService;
	}

	public ReportCriteriaService getReportCriteriaService() {
		return reportCriteriaService;
	}

	public void exportReportToPdf() {
		getReportRenderer().exportSingleReportToPDF(initReportCriteria(),
				getFacesContext());
	}

	public void emailReport() {
		getReportRenderer().exportReportToEmail(initReportCriteria(), getEmailAddress(), getNoReplyEmailAddress());
	}

	public void exportReportToExcel() {
		getReportRenderer().exportReportToExcel(initReportCriteria(),
				getFacesContext());
	}

	private ReportCriteria initReportCriteria() {
		ReportCriteria reportCriteria = getReportCriteria();
		reportCriteria.setReportDate(new Date(), getUser().getPerson()
				.getTimeZone());
		reportCriteria.setMainDataset(getReportTableData());
		return reportCriteria;
	}

	protected abstract List<?> getReportTableData();

	protected abstract ReportCriteria getReportCriteria();

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

}
