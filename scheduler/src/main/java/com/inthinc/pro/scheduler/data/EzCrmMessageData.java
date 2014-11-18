package com.inthinc.pro.scheduler.data;

import java.util.List;
import java.util.Locale;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.RedFlagEzCrmDataType;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class EzCrmMessageData {
    private String[] dataArray;
    //private AlertMessageBuilder msgBuilder;

    private Locale  locale;
    private AlertMessageType alertMessageType;
    private List<String> ezParamList;
    
    public EzCrmMessageData (AlertMessageBuilder builder) {
        if (builder == null || !builder.isEzCrm())
            throw new IllegalArgumentException("AlertMessageBuilder is not an EzCrm Alert!");
        
        this.locale = builder.getLocale();
        this.alertMessageType = builder.getAlertMessageType();
        this.ezParamList = builder.getEzParameterList();
        this.dataArray = new String[RedFlagEzCrmDataType.getMax()];
    }
    
    public EzCrmMessageData(AlertMessageType msgType, Locale loc, List<String> params) {
        this.locale = loc;
        this.alertMessageType = msgType;
        this.ezParamList = params;
        this.dataArray = new String[RedFlagEzCrmDataType.getMax()];
    }
    
    public String getMessage() {
        // process each data type line
        processDataLines();

        // Return msg
        String msg = new String();
        for (String s : dataArray) {
            msg += s;
        }
        return msg;
    }
    
    private String[] getLineParams(int start, int count) {
        String[] array = new String[count];

        for (int i = start, j = 0; i < start + count; ++i, ++j) {
            array[j] = ezParamList.get(i);
        }
        return array;
    }
    
    private void processDataLines() {
        RedFlagEzCrmDataType curType = RedFlagEzCrmDataType.EZCRM_DATA_TYPE_GROUP;
        String sUnknown = LocalizedMessage.getString("EzCrm.UNKNOWN", locale);
        String sNoGpsLock = LocalizedMessage.getString("EzCrm.UNKNOWN", locale);
        try {
            for (RedFlagEzCrmDataType type : RedFlagEzCrmDataType.values()) {
            
                String[] lineParams;
                String text;
                curType = type;
    
                switch (type) {
                    case EZCRM_DATA_TYPE_GROUP:
                        lineParams = getLineParams(1, 1);
                        text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        break;
                    case EZCRM_DATA_TYPE_SEVERITY:
                        {
                            String[] cat = LocalizedMessage.getString("EzCrm.Category", locale).split(";");
                            lineParams = getLineParams(2, 1);
                            lineParams[0] = cat[Integer.parseInt(lineParams[0])];
                            text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        }
                        break;
                    case EZCRM_DATA_TYPE_DATE:
                        lineParams = getLineParams(3, 1);
                        text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        break;
                    case EZCRM_DATA_TYPE_DRIVER:
                        lineParams = getLineParams(4, 3);
                        if (lineParams[1].isEmpty())
                            lineParams[1] = sUnknown;
                        if (lineParams[2].isEmpty())
                            lineParams[2] = sUnknown;
                        text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        break;
                    case EZCRM_DATA_TYPE_VEHICLE:
                        lineParams = getLineParams(7, 5);
                        text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        break;
                    case EZCRM_DATA_TYPE_EVENT:
                        {
                            String[] preParams;
                            switch (alertMessageType) {
                                case ALERT_TYPE_SPEEDING:
                                    preParams = getLineParams(18, 2);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                case ALERT_TYPE_EXIT_ZONE:
                                    preParams = getLineParams(18, 2);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                case ALERT_TYPE_ENTER_ZONE:
                                    preParams = getLineParams(18, 2);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                case ALERT_TYPE_FIRMWARE_CURRENT:
                                    preParams = getLineParams(18, 1);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                case ALERT_TYPE_WITNESS_UPDATED:
                                    preParams = getLineParams(18, 1);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                case ALERT_TYPE_QSI_UPDATED: // Status only
                                    {
                                        String[] stat = LocalizedMessage.getString("EzCrm.QSIStatus", locale).split(";");
                                        preParams = getLineParams(18, 1);
                                        if (preParams[0].isEmpty())
                                            preParams[0] = Integer.toString(3);
                                        preParams[0]=stat[Integer.parseInt(preParams[0])-1];
                                        lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    }
                                    break;
                                case ALERT_TYPE_ZONES_CURRENT: // Status only
                                    {
                                        String[] stat = LocalizedMessage.getString("EzCrm.QSIStatus", locale).split(";");
                                        preParams = getLineParams(18, 1);
                                        if (preParams[0].isEmpty())
                                            preParams[0] = Integer.toString(3);
                                        preParams[0]=stat[Integer.parseInt(preParams[0])-1];
                                        lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    }
                                    break;
                                case ALERT_TYPE_IDLING:
                                    preParams = getLineParams(18, 1);
                                    lineParams = LocalizedMessage.getStringWithValues("EzCrm."+alertMessageType.toString(), locale, preParams).split(";");
                                    break;
                                default:
                                    lineParams = LocalizedMessage.getString("EzCrm."+alertMessageType.toString(), locale).split(";");
                            }
                            text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        }
                        break;
                    case EZCRM_DATA_TYPE_LOCATION:
                        lineParams = getLineParams(12, 3);
                        if (lineParams[0].isEmpty())
                            lineParams[0] = sNoGpsLock;
                        if (lineParams[1].isEmpty())
                            lineParams[1] = sNoGpsLock;
                        if (lineParams[2].isEmpty())
                            lineParams[2] = sUnknown;
                        text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        break;
                    case EZCRM_DATA_TYPE_ODOMETER:
                        {
                            String[] m = LocalizedMessage.getString("EzCrm.Odometer", locale).split(";");
                            String[] mt = getLineParams(17, 1);
                            String[] tmp = getLineParams(15, 1);
                            lineParams = new String[] {tmp[0], m[Integer.parseInt(mt[0])]};
                            text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        }
                        break;
                    case EZCRM_DATA_TYPE_SPEED:
                        {
                            String[] m = LocalizedMessage.getString("EzCrm.Speed", locale).split(";");
                            String[] mt = getLineParams(17, 1);
                            String[] tmp = getLineParams(16, 1);
                            lineParams = new String[] {tmp[0], m[Integer.parseInt(mt[0])]};
                            text = LocalizedMessage.getStringWithValues(type.toString(), locale, lineParams);
                        }
                        break;
                    default:
                        {
                            text = new String("");
                        }
                        break;
                }
                
                this.dataArray[type.ordinal()] = text;
            }
        }
        catch(Exception e){
            System.out.println("Exception in provessDataLines: curType = "+curType.toString());
            System.out.println(e.toString());
        }
    }
}