package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.TablePreference;

public class TablePreferenceHessianDAO extends GenericHessianDAO<TablePreference, Integer> implements TablePreferenceDAO
{
    public List<TablePreference> getTablePreferencesByUserID(Integer userID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getTablePreferencesByUserID(userID), TablePreference.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        // TODO: remove when method is implemented on back end
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }

    }

}
