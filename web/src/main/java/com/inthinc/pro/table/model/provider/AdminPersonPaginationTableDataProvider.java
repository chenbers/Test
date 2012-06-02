package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.PersonBean;
import com.inthinc.pro.backing.PersonBean.PersonView;
import com.inthinc.pro.dao.jdbc.AdminPersonJDBCDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminPersonPaginationTableDataProvider extends AdminPaginationTableDataProvider<PersonBean.PersonView>{

    private static final long serialVersionUID = 7174258339086174126L;
    
    private static final Logger logger = Logger.getLogger(AdminPersonPaginationTableDataProvider.class);

    private PersonBean personBean;
    
    private AdminPersonJDBCDAO adminPersonJDBCDAO;

// TODO: REFACTOR GETTING THE GROUP IdS

    @Override
    public List<PersonBean.PersonView> getItemsByRange(int firstRow, int endRow) {
        
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
//        List<Person> personList = getAdminDAO().getPersonPage(getGroupID(), pageParams);
        List<Person> personList = adminPersonJDBCDAO.getPeople(personBean.getGroupHierarchy().getGroupIDList(getGroupID()), pageParams);
        
        List<PersonView> items = new ArrayList<PersonView>();
        for (final Person person : personList) {
            items.add(personBean.createPersonView(person));
        }
        
        personBean.setItems(items);

        return items;
    }

    @Override
    public int getRowCount() {
        if (getGroupID() == null)
            return 0;

        Integer rowCount = adminPersonJDBCDAO.getCount(personBean.getGroupHierarchy().getGroupIDList(getGroupID()), getFilters());
        logger.info("getRowCount returns: " + rowCount + " for groups " + personBean.getGroupHierarchy().getGroupIDList(getGroupID()) + " filters " + getFilters().toString());
        return rowCount;
    }
    public PersonBean getPersonBean() {
        return personBean;
    }

    public void setPersonBean(PersonBean personBean) {
        this.personBean = personBean;
    }
    public AdminPersonJDBCDAO getAdminPersonJDBCDAO() {
        return adminPersonJDBCDAO;
    }

    public void setAdminPersonJDBCDAO(AdminPersonJDBCDAO adminPersonJDBCDAO) {
        this.adminPersonJDBCDAO = adminPersonJDBCDAO;
    }
}
