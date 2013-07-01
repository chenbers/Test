package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.inthinc.pro.util.MessageUtil;


// I don't think this is used anymore and could probably be removed.
public class UnknownDriverBean extends BaseAdminBean<UnknownDriverBean.UnknownDriverView> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final String REQUIRED_KEY = "required";
    private PersonDAO personDAO;
    private TimeZonesBean timeZonesBean;

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
        return timeZonesBean.getTimeZones();
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
		
    public TimeZonesBean getTimeZonesBean() {
        return timeZonesBean;
    }

    public void setTimeZonesBean(TimeZonesBean timeZonesBean) {
        this.timeZonesBean = timeZonesBean;
    }

}
