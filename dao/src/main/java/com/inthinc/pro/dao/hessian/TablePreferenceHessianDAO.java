package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.TablePrefMapper;
import com.inthinc.pro.model.TablePreference;

public class TablePreferenceHessianDAO extends GenericHessianDAO<TablePreference, Integer> implements TablePreferenceDAO
{
    public TablePreferenceHessianDAO()
    {
        super();
        super.setMapper(new TablePrefMapper());
    }
    
    public List<TablePreference> getTablePreferencesByUserID(Integer userID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getTablePrefsByUserID(userID), TablePreference.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}
