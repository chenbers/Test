package com.inthinc.pro.sbs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import com.inthinc.pro.backing.SBSChangeRequest;

public class Tiger {
	

    private static DataSource ds;

    static {
 
        try {
            Context ctx = new InitialContext();
            
            Context envCtx = (Context) ctx.lookup("java:/comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/tiger");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
//    public static SBSChangeRequest getCompleteChains(double lat, double lng, int address) throws SQLException, ParserConfigurationException
//    {
//
//		Connection connection = ds.getConnection();
//		Statement stmt = connection.createStatement();
//		
//		String point = "Point(" + lng + " " + lat + ")";
//		
//		String query = "select link_id as ogc_fid,st_nm_pref as fedirp,st_typ_bef,st_nm_base as fename,st_nm_suff,st_typ_aft as fetype ,l_refaddr as fraddl,l_nrefaddr as toaddl,"+
//				"r_refaddr as fraddr,r_nrefaddr as toaddr,l_postcode as zipL,r_postcode as zipR,"+
//				"distance(the_geom,setsrid(GeomFromText('"+point+"',32767),4326)) as dist, "+
//				"astext(the_geom) as tigerline, COALESCE(streets.iwi_speed_cat,"+ 
//				"streets_sif.speed_cat, streets.speed_cat) AS speed_cat, COALESCE(streets.iwi_fr_spd_lim, streets_sif.fr_spd_lim," +
//				"streets.fr_spd_lim) AS fr_spd_lim,COALESCE(streets.iwi_to_spd_lim," +
//				"streets_sif.to_spd_lim, streets.to_spd_lim) AS to_spd_lim "+
//				"from streets LEFT JOIN streets_sif USING (link_id)"+
//				"where the_geom && " +
//				"expand(setsrid(geomFromText('"+point+"',32767),4326),0.002) order by dist limit 10";
//		/*       String query = "select ogc_fid,tlid,fedirp,fename,fetype,fedirs,fraddl,toaddl,fraddr,toaddr,zipL,zipR,countyL,countyR,stateL,stateR,us_fips.placename as placenameL,cfcc,distance(wkb_geometry,GeomFromText('"
//		        + point
//		        + "',32767)) as dist from COMPLETECHAIN,US_FIPS where wkb_geometry &&  expand(geomFromText('"
//		        + point
//		        + "',32767),0.002) "
//		        + " and COMPLETECHAIN.placeL = US_FIPS.fipsplace_numeric and COMPLETECHAIN.stateL = US_FIPS.state_numeric and COMPLETECHAIN.countyL = US_FIPS.fipscounty_numeric"
//		        + " and cfcc like 'A%' order by dist limit 10";*/
//		
//		ResultSet rs = stmt.executeQuery(query);
//		List<SBSChangeRequest> backups = new ArrayList<SBSChangeRequest>();
//		while (rs.next()){
//			
//			SBSChangeRequest sbsChangeRequest = new SBSChangeRequest();
//			List<String> processedAddress = makeAddress(rs.getString("fraddr"),rs.getString("toaddr"),
//					rs.getString("fraddl"),rs.getString("toaddl"),
//					rs.getString("fedirp"),rs.getString("st_typ_bef"),
//					rs.getString("fename"),rs.getString("st_nm_suff"),
//					rs.getString("fetype"),"", address);
//			sbsChangeRequest.setAddress(processedAddress.get(2));
//			sbsChangeRequest.setLinkId(rs.getString("ogc_fid"));
//			sbsChangeRequest.setZipCode(rs.getString("zipL"));
//			sbsChangeRequest.setSpeedLimit(deduceSpeedLimit(rs.getInt("fr_spd_lim"),rs.getInt("to_spd_lim"),rs.getInt("speed_cat")));
//			
//			sbsChangeRequest.setCategory(rs.getInt("speed_cat"));
//			sbsChangeRequest.setStreetSegment(rs.getString("tigerLine"));
//			
//			//test if we have a segment in the right range and return 
//			if (processedAddress.get(1).equals("*")){
//				
//				return sbsChangeRequest;
//			}
//			else {
//
//				backups.add(sbsChangeRequest);
//			}
//		}
//		if (backups.size() > 0) {
//			return backups.get(0);
//		}
//		else {
//			return null;
//		}
//	
//	}
    private static List<String> getNumbers (String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft, int address) {
    	
    	int fromRight = -1, fromLeft = -1, toRight = -1, toLeft = -1;
    	//Get ducks in a row
        if ((fromAddrRight!=null)&& (!fromAddrRight.isEmpty())){
        	try {
        		fromRight = Integer.parseInt(fromAddrRight);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((toAddrRight!=null)&& (!toAddrRight.isEmpty())){
        	try {
        		toRight = Integer.parseInt(toAddrRight);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((fromAddrLeft!=null)&& (!fromAddrLeft.isEmpty())){
        	try {
        		fromLeft = Integer.parseInt(fromAddrLeft);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        if ((toAddrLeft!=null)&& (!toAddrLeft.isEmpty())){
        	try {
        		toLeft = Integer.parseInt(toAddrLeft);
        	}
        	catch (NumberFormatException nfe){
        		
        	}
        }
        //If the values are in the wrong order swap them around
        if (toRight < fromRight){
        	//swap them round
        	String temp = toAddrRight;
        	toAddrRight = fromAddrRight;
        	fromAddrRight = temp;
        	
        	int i = toRight;
        	toRight = fromRight;
        	fromRight = i;
        }
        if (toLeft < fromLeft){
        	//swap them round
        	String temp = toAddrLeft;
        	toAddrLeft = fromAddrLeft;
        	fromAddrLeft = temp;
        	
        	int i = toLeft;
        	toLeft = fromLeft;
        	fromLeft = i;
      }
        StringBuilder builder = new StringBuilder();
        if ((fromAddrRight!=null)&& (!fromAddrRight.isEmpty())){
        	
	        append(builder, fromAddrRight);
	        append(builder, "-");
	        if ((toAddrRight!=null)&& (!toAddrRight.isEmpty())){
		        append(builder, toAddrRight);
	        }
	        append(builder, ",");
        }
        if ((fromAddrLeft!=null)&& (!fromAddrLeft.isEmpty())){
        	
	        append(builder, fromAddrLeft);
	        append(builder, "-");
	        if ((toAddrLeft!=null)&& (!toAddrLeft.isEmpty())){
	        	
	        	append(builder, toAddrLeft);
	        }
        }
        List<String> result = new ArrayList<String>();
        result.add(builder.toString().trim());
        String ok ="";
        //Check if the address supplied is in the address range
        if ((address == -1) ||((address != -1)&& ((( address >= fromLeft)&&(address <= toLeft)) || ((address >= fromRight)&&(address <= toRight))))){
        	
        	ok = "*";
        }
        result.add(ok);
        
        return result;

    }
    private static List<String> makeAddress(String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft, 
    		String fedirp, String st_typ_bef, String fename, String st_nm_suff, String fetype, String fedirs, int address){
    		
    		List<String> numbers = getNumbers(fromAddrRight,toAddrRight,fromAddrLeft,toAddrLeft, address);
            StringBuilder builder = new StringBuilder();
            append(builder,numbers.get(0));
            append(builder, "*");
            append(builder, fedirp);
            append(builder, st_typ_bef);
            append(builder, fename);
            append(builder, st_nm_suff);
            append(builder, fetype);
            append(builder, fedirs);
            
            numbers.add(builder.toString().trim());
            return numbers;
        }

    private static void append(StringBuilder builder, String str) {
        if (str != null) {
            builder.append(str.trim() + " ");
        }
    }

    private static int deduceSpeedLimit(int fromSpeedLimit, int toSpeedLimit, int category){
    	
    	int speedLimit = Math.max(fromSpeedLimit,toSpeedLimit);
    	if (speedLimit == 0){
        	
    		switch(category){
    		case 1:
    			return 75;
    		case 2:
    			return 75;
    		case 3:
    			return 60;
    		case 4:
    			return 50;
    		case 5:
    			return 40;
    		case 6:
    			return 30;
    		case 7:
    			return 20;
    		case 8:
    			return 5;
    		default:
    			return 0;
    		}
        }
    	else return speedLimit;
    }
}
