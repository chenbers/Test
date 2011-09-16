package com.inthinc.pro.sbs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.backing.SBSChangeRequest;
import com.inthinc.pro.sbs.baseDao.TigerDAO;

public class JdbcTigerDAO extends SimpleJdbcDaoSupport implements TigerDAO {

	private static Logger logger = Logger.getLogger("com.iwi.sbs.dao.jdbc.JdbcTigerDAO");
	
    private static final String COMPLETE_CHAINS = "select link_id as ogc_fid,st_nm_pref as fedirp,st_typ_bef,st_nm_base as fename,st_nm_suff,st_typ_aft as fetype ,l_refaddr as fraddl,l_nrefaddr as toaddl,"+
    "r_refaddr as fraddr,r_nrefaddr as toaddr,l_postcode as zipL,r_postcode as zipR,"+
    "distance(the_geom,setsrid(GeomFromText(?,32767),4326)) as dist, "+
    "astext(the_geom) as tigerline, speed_cat, fr_spd_lim, to_spd_lim, kph "+
    "from streets_view4 "+
    "where the_geom && " +
    "expand(setsrid(geomFromText(?,32767),4326),0.0008) order by dist limit 10";
	
	
	@Override
	public List<SBSChangeRequest> getCompleteChains(double lat, double lng, final int address, int limit)
			throws ParserConfigurationException {
		
		String point = "Point(" + lng + " " + lat + ")";
		List<SBSChangeRequest> matches = null;
		
        try {
            matches = getSimpleJdbcTemplate().query(COMPLETE_CHAINS,

            new ParameterizedRowMapper<SBSChangeRequest>() {
                public SBSChangeRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SBSChangeRequest ss = new SBSChangeRequest();

                    ss.setAddress(
                            rs.getString("fraddr"), 
                            rs.getString("toaddr"), 
                            rs.getString("fraddl"), 
                            rs.getString("toaddl"), 
                            rs.getString("fedirp"), 
                            rs.getString("st_typ_bef"), 
                            rs.getString("fename"), 
                            rs.getString("st_nm_suff"), 
                            rs.getString("fetype"), 
                            "", 
                            address);
                    ss.setLinkId(rs.getString("ogc_fid"));
                    ss.setZipCode(rs.getString("zipL"));
                    ss.setSpeedLimit(rs.getInt("fr_spd_lim"), rs.getInt("to_spd_lim"), rs.getInt("speed_cat"));
                    ss.setKilometersPerHour(rs.getBoolean("kph"));
                    ss.setCategory(rs.getInt("speed_cat"));
                    ss.setStreetSegmentPoints(rs.getString("tigerLine"));
                    ss.setDistance(rs.getDouble("dist"));

                    return ss;
                }
            }, point, point);
        } catch (Exception e) {

            logger.info("Error accessing Postgres database: " + e.getMessage());
            return null;
        }
		
		// No match
		if ( (matches == null) || matches.isEmpty() ) {
		    
			logger.info("No match for: " + lat + " " + lng);
			return null;
		}
		
		if (limit > 1){
      		int count = 0;
      		Iterator<SBSChangeRequest> it = matches.iterator();
      		
            //Find segments in the right range and return 
    		while (it.hasNext()){
    		    
    			if (it.next().getDistance() < .0002d){
    				
    			    count++;
    			}
    			else {
    			    
    			    break;
    			}
    		}
    		
    		if (count > 0){
    		    
    		    return  matches.subList(0, count);
    		}
    		else {
    		    
    		    //If there aren't any then just return the top one
    		    return matches.subList(0, 1);
    		}
		}
		else {
		    
            return matches.subList(0, 1);
		}

	}

}
