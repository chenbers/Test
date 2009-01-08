package com.inthinc.pro.sbs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	
    public static SBSChangeRequest getCompleteChains(double lat, double lng) throws SQLException, ParserConfigurationException
    {

		Connection connection = ds.getConnection();
		Statement stmt = connection.createStatement();
		
		String point = "Point(" + lng + " " + lat + ")";
		
		String query = "select link_id as ogc_fid,st_nm_pref as fedirp,st_typ_bef,st_nm_base as fename,st_nm_suff,st_typ_aft as fetype ,l_refaddr as fraddl,l_nrefaddr as toaddl,"+
				"r_refaddr as fraddr,r_nrefaddr as toaddr,l_postcode as zipL,r_postcode as zipR,"+
				"distance(the_geom,setsrid(GeomFromText('"+point+"',32767),4326)) as dist, "+
				"astext(the_geom) as tigerline, COALESCE(streets.iwi_speed_cat,"+ 
				"streets_sif.speed_cat, streets.speed_cat) AS speed_cat, COALESCE(streets.iwi_fr_spd_lim, streets_sif.fr_spd_lim," +
				"streets.fr_spd_lim) AS fr_spd_lim,COALESCE(streets.iwi_to_spd_lim," +
				"streets_sif.to_spd_lim, streets.to_spd_lim) AS to_spd_lim "+
				"from streets LEFT JOIN streets_sif USING (link_id)"+
				"where the_geom && " +
				"expand(setsrid(geomFromText('"+point+"',32767),4326),0.002) order by dist limit 1";
		/*       String query = "select ogc_fid,tlid,fedirp,fename,fetype,fedirs,fraddl,toaddl,fraddr,toaddr,zipL,zipR,countyL,countyR,stateL,stateR,us_fips.placename as placenameL,cfcc,distance(wkb_geometry,GeomFromText('"
		        + point
		        + "',32767)) as dist from COMPLETECHAIN,US_FIPS where wkb_geometry &&  expand(geomFromText('"
		        + point
		        + "',32767),0.002) "
		        + " and COMPLETECHAIN.placeL = US_FIPS.fipsplace_numeric and COMPLETECHAIN.stateL = US_FIPS.state_numeric and COMPLETECHAIN.countyL = US_FIPS.fipscounty_numeric"
		        + " and cfcc like 'A%' order by dist limit 10";*/
		
		ResultSet rs = stmt.executeQuery(query);
		SBSChangeRequest sbsChangeRequest = new SBSChangeRequest();
		
		while (rs.next()){
			
			sbsChangeRequest.setLinkId(rs.getString("ogc_fid"));
			sbsChangeRequest.setZipCode(rs.getString("zipL"));
			sbsChangeRequest.setNumbers(getNumbers(rs.getString("fraddr"),rs.getString("toaddr"),
													rs.getString("fraddl"),rs.getString("toaddl")));
			sbsChangeRequest.setAddress(makeAddress(rs.getString("fraddr"),rs.getString("toaddr"),
													rs.getString("fraddl"),rs.getString("toaddl"),
													rs.getString("fedirp"),rs.getString("st_typ_bef"),
													rs.getString("fename"),rs.getString("st_nm_suff"),
													rs.getString("fetype"),""));
			sbsChangeRequest.setSpeedLimit(deduceSpeedLimit(rs.getInt("fr_spd_lim"),rs.getInt("to_spd_lim"),rs.getInt("speed_cat")));
			
			sbsChangeRequest.setCategory(rs.getInt("speed_cat"));
			sbsChangeRequest.setStreetSegment(rs.getString("tigerLine"));
		}
		
		return sbsChangeRequest;
	
	}
    private static String getNumbers (String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft){
        StringBuilder builder = new StringBuilder();
        append(builder, fromAddrRight);
        append(builder, "-");
        append(builder, toAddrRight);
        append(builder, ",");
        append(builder, fromAddrLeft);
        append(builder, "-");
        append(builder, toAddrLeft);
        
        return builder.toString().trim();

    }
    private static String makeAddress(String fromAddrRight, String toAddrRight, String fromAddrLeft, String toAddrLeft, String fedirp, String st_typ_bef, String fename, String st_nm_suff, String fetype, String fedirs){
    	
            StringBuilder builder = new StringBuilder();
            append(builder, fromAddrRight);
            append(builder, "-");
            append(builder, toAddrRight);
            append(builder, ",");
            append(builder, fromAddrLeft);
            append(builder, "-");
            append(builder, toAddrLeft);
            append(builder, "*");
            append(builder, fedirp);
            append(builder, st_typ_bef);
            append(builder, fename);
            append(builder, st_nm_suff);
            append(builder, fetype);
            append(builder, fedirs);
            return builder.toString().trim();
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
