package com.inthinc.pro.backing.paging;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.PersonBean;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;

@KeepAlive
public class PagingAdminPersonsBean extends BasePagingAdminBean<PersonBean.PersonView> {
    private static final long serialVersionUID = 1239753414611862228L;
    private PersonBean personBean;

    @Override
    public void init() {
        super.init();
        personBean.setColumnsChangedListener(this);
    }

    public PersonBean getPersonBean() {
        return personBean;
    }

    public void setPersonBean(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public TableSortField getDefaultSort() {
        return new TableSortField(SortOrder.ASCENDING, "fullName");
    }
}
