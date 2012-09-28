package com.inthinc.pro.backing;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.util.MessageUtil;

public class UnknownDriverBean extends BaseAdminBean<UnknownDriverBean.UnknownDriverView> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Map<String, TimeZone> TIMEZONES;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final String REQUIRED_KEY = "required";
    static {
        // time zones
        final List<String> timeZones = SupportedTimeZones.getSupportedTimeZones();
        // sort by offset from GMT
        Collections.sort(timeZones, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final TimeZone t1 = TimeZone.getTimeZone(o1);
                final TimeZone t2 = TimeZone.getTimeZone(o2);
                return t1.getRawOffset() - t2.getRawOffset();
            }
        });
        TIMEZONES = new LinkedHashMap<String, TimeZone>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones) {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
            if (offsetHours < 0)
                TIMEZONES.put(timeZone.getID() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
            else
                TIMEZONES.put(timeZone.getID() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')', timeZone);
        }
    }
    private PersonDAO personDAO;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    protected List<UnknownDriverView> loadItems() {
    	Person unknownDriverPerson = getUnknownDriver().getPerson();
        final List<UnknownDriverView> items = new ArrayList<UnknownDriverView>();
        items.add(createUnknownDriverView(unknownDriverPerson));
        return items;
    }

    private UnknownDriverView createUnknownDriverView(Person person) {
        final UnknownDriverView unknownDriverView = new UnknownDriverView();
        if (logger.isTraceEnabled())
            logger.trace("createUnknownDriverView: BEGIN " + person);
        BeanUtils.copyProperties(person, unknownDriverView);
        unknownDriverView.setSelected(true);
        if (logger.isTraceEnabled())
            logger.trace("createUnknownDriverView: END " + unknownDriverView);
        return unknownDriverView;
    }


    @Override
    protected boolean validateSaveItem(UnknownDriverView person) {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        // required Time Zone
        if (person.getTimeZone() == null) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString(REQUIRED_KEY), null);
            context.addMessage("edit-form:editUnknownDriver-timeZone", message);
        }
        return valid;
    }

    @Override
    protected Boolean authorizeAccess(UnknownDriverView item) {
        Integer acctID = item.getAcctID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected UnknownDriverView revertItem(UnknownDriverView editItem) {
        if (logger.isTraceEnabled())
            logger.trace("revertItem" + editItem);
        return createUnknownDriverView(this.getUnknownDriver().getPerson());
    }

    @Override
    protected void doSave(List<UnknownDriverView> saveItems, boolean create) {
        for (final UnknownDriverView person : saveItems) {
       		personDAO.update(person);
       		getUnknownDriver().setPerson(person);
        }
    }
    @Override
    protected String getDisplayRedirect() {
        return "pretty:adminUnknownDriver";
    }

    @Override
    protected String getEditRedirect() {
        return "pretty:adminEditUnknownDriver";
    }

    @Override
    protected String getFinishedRedirect() {
        return "pretty:adminUnknownDriver";
    }

    public Map<String, TimeZone> getTimeZones() {
        return TIMEZONES;
    }

    public static class UnknownDriverView extends Person implements EditItem {
        @Column(updateable = false)
        private static final long serialVersionUID = 8954277815270194338L;

        @Column(updateable = false)
        private boolean selected;

        public Integer getId() {
            return getPersonID();
        }

        public String getName() {
            return getFirst() + ' ' + getLast();
        }
        
		@Override
        public boolean isSelected() {
            return selected;
        }

		@Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
   }

	@Override
	protected UnknownDriverView createAddItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDelete(List<UnknownDriverView> deleteItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getAvailableColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Boolean> getDefaultColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnLabelPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableType getTableType() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
