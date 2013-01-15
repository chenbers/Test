package com.inthinc.pro.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.TeamCommonBean;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Zone;

public class MiscUtil
{
    static public int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * Removes any duplicates from a given list of SelectItems
     * @param items the list of SelectItems to de-duplicate
     * @return true if the original list of SelectItems changed
     */
    public static boolean deDupSelectItems(List<SelectItem> items) {
        boolean changed = false;
        sortSelectItems(items);
        SelectItem temp = new SelectItem();
        for(final Iterator<SelectItem> it = items.iterator(); it.hasNext();){
            SelectItem i = it.next();
            if(!i.getLabel().equals(temp.getLabel()))
                temp = i;
            else {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }
    /**
     * Removes from <code>orig</code> any SelectItems that are contained in <code>remove</code>, comparing on label only.
     * @param orig the list of SelectItems that might be modified
     * @param remove the list of SelectItems that will be removed from <code>orig</code>
     * @return true if <code>orig</code> was modified
     */
    public static boolean removeAllByLabel(List<SelectItem> orig, List<SelectItem> remove){
        boolean changed = false;
        for (final Iterator<SelectItem> origIterator = orig.iterator(); origIterator.hasNext();) {
            SelectItem item = origIterator.next();
            for(SelectItem removeItem: remove){
                if(item.getLabel().equalsIgnoreCase(removeItem.getLabel())){
                    origIterator.remove();
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }
    public static void sortSelectItems(List<SelectItem> items)
    {
        Collections.sort(items, new Comparator<SelectItem>()
        {
            @Override
            public int compare(SelectItem o1, SelectItem o2)
            {
                return o1.getLabel().compareToIgnoreCase(o2.getLabel());
            }
        });
    }

    public static String formatPhone(String phone)
    {
    	return phone;
//        if ((phone == null) || (phone.length() == 0))
//            return null;
//        if (phone.length() != 10)
//        {
//            final String unfo = unformatPhone(phone);
//            if (unfo.length() == 10)
//                phone = unfo;
//            else if (unfo.length() > 10)
//            	return MessageUtil.formatMessageString("internationalPhoneFormat", unfo);
//            else
//                return phone;
//        }
//        return MessageUtil.formatMessageString("phoneFormat", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
    }

    public static String unformatPhone(String phone)
    {
        if (phone == null)
            return null;
        return phone.replaceAll("\\D", "");
    }
    
    public static String findZoneName(List<Zone> zoneList,LatLng latLng) {
        String zoneName = null;

        for ( Zone z: zoneList ) {
            if ( inPolygon(z,latLng) ) {
                return z.getName();
            }
        }
        
        if ( zoneName == null ) {
            zoneName = MessageUtil.getMessageString("sbs_badLatLng");
        }
        return zoneName;
    }
    
    private static boolean inPolygon(Zone z,LatLng latLng) {
        
        ZonePoly zonePoly = new ZonePoly(z.getPoints());
        return zonePoly.contains(latLng.getLat(), latLng.getLng());
    }
    
    
    public static int whichMethodToUse(TeamCommonBean teamCommonBean) {
        // Assume daily value (0)
        int rc = 0;
    
        if(("teamVehicleStatistics").equals(teamCommonBean.getSelectedTabId()))
        	return 3;
        
        // Not daily?
        if (        teamCommonBean.getTimeFrame().equals(TimeFrame.WEEK) ) {
            return 1;
            
        } else if ( teamCommonBean.getTimeFrame().equals(TimeFrame.MONTH) || 
                    teamCommonBean.getTimeFrame().equals(TimeFrame.YEAR) ) {
            return 2;
        }
        
        return rc;
    }
    /**
     * Check to see if a String contains data.
     * @param s the String to test
     * @return true if the String contains data.
     */
    public static boolean notEmpty(String s){
        return s!=null && s.length()>0;
    }
    
    public static String escapeBadCharacters(String string) {
        StringBuffer sb = new StringBuffer();
        
        for (int i=0; i<string.length(); i++) {
            char c = string.charAt(i);
            if (c == '\'') {
                sb.append("&quot;");
            } else {
                sb.append(c);
            }
          }

        return sb.toString();
    }
}
