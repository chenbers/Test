package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;

public class CellblockMapper extends AbstractMapper {

    private static final long serialVersionUID = 1L;

    @ConvertColumnToField(columnName = "cellStatus")
    public void cellStatusColumnToCellStatusField(Cellblock cellblock, Object value)
    {
        if (cellblock == null || value == null) return;
        
        if (value instanceof Integer){
            
            cellblock.setCellStatus(((Integer)value).intValue()==0?CellStatusType.DISABLED:CellStatusType.ENABLED);
        }
    }
    
    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "cellStatus")
    public void cellStatusFieldToCellStatusColumn(Cellblock cellblock, Object value)
    {
        if (cellblock == null || value == null || cellblock.getCellStatus() == null) return;
        
        if (Map.class.isInstance(value))
        {
            ((Map<String, Object>)value).put("cellStatus",new Integer(cellblock.getCellStatus().equals(CellStatusType.DISABLED)?0:1));
        }

    }
    @ConvertColumnToField(columnName = "providerType")
    public void providerTypeToProvider(Cellblock cellblock, Object value)
    {
        if (cellblock == null || value == null) return;
        
        if (value instanceof Integer){
            
            cellblock.setProvider(CellProviderType.valueOf((Integer)value));
        }
    }
    
    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "provider")
    public void providerToProviderType(Cellblock cellblock, Object value)
    {
        if (cellblock == null || value == null || cellblock.getCellStatus() == null) return;
        
        if (Map.class.isInstance(value))
        {
            ((Map<String, Object>)value).put("providerType",new Integer(cellblock.getProvider().getCode()));
        }

    }
   
}
