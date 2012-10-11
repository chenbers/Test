package com.inthinc.pro.sbs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.sbs.SBSChangeRequest;
import com.inthinc.pro.sbs.baseDao.TigerDAO;

public class JdbcTigerDAO extends SimpleJdbcDaoSupport implements TigerDAO {

	private static Logger logger = Logger.getLogger("com.iwi.sbs.dao.jdbc.JdbcTigerDAO");
	
	private static final String COMPLETE_CHAINS = "select link_id as ogc_fid,st_nm_pref as fedirp,st_typ_bef,st_nm_base as fename,st_nm_suff,st_typ_aft as fetype ,l_refaddr as fraddl,l_nrefaddr as toaddl,"+
	"r_refaddr as fraddr,r_nrefaddr as toaddr,l_postcode as zipL,r_postcode as zipR,"+
	"distance(the_geom,setsrid(GeomFromText(?,32767),4326)) as dist, "+
	"astext(the_geom) as tigerline, COALESCE(streets.iwi_speed_cat,"+ 
	"streets_sif.speed_cat, streets.speed_cat) AS speed_cat, COALESCE(streets.iwi_fr_spd_lim, streets_sif.fr_spd_lim," +
	"streets.fr_spd_lim) AS fr_spd_lim,COALESCE(streets.iwi_to_spd_lim," +
	"streets_sif.to_spd_lim, streets.to_spd_lim) AS to_spd_lim "+
	"from streets LEFT JOIN streets_sif USING (link_id)"+
	"where the_geom && " +
	"expand(setsrid(geomFromText(?,32767),4326),0.002) order by dist limit 10";
	
	
	@Override
	public SBSChangeRequest getCompleteChains(double lat, double lng, final int address)
			throws ParserConfigurationException {
		
		String point = "Point(" + lng + " " + lat + ")";
		List<SBSChangeRequest> matches = null;
		
		try {
			matches = getSimpleJdbcTemplate().query(COMPLETE_CHAINS, 
				
				new ParameterizedRowMapper<SBSChangeRequest>() {
					public SBSChangeRequest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
						SBSChangeRequest ss = new SBSChangeRequest();
						
						ss.setAddress(rs.getString("fraddr"),rs.getString("toaddr"),
								rs.getString("fraddl"),rs.getString("toaddl"),
								rs.getString("fedirp"),rs.getString("st_typ_bef"),
								rs.getString("fename"),rs.getString("st_nm_suff"),
								rs.getString("fetype"),"", address);
						ss.setLinkId(rs.getString("ogc_fid"));
						ss.setZipCode(rs.getString("zipL"));
						ss.setSpeedLimit(rs.getInt("fr_spd_lim"),rs.getInt("to_spd_lim"),rs.getInt("speed_cat"));
						
						ss.setCategory(rs.getInt("speed_cat"));
						ss.setStreetSegmentPoints(rs.getString("tigerLine"));
						
						return ss;
					}
			},
			point,point);
		} catch (Exception e) {
			if (logger.isDebugEnabled()){
				
				logger.debug("Error accessing Postgres database: "+e.getMessage());
			}
			return null;
		}
		
		// No match
		if ( matches == null ) {
			if (logger.isDebugEnabled()){
				
				logger.debug("No match for: " + lat + " " + lng);
			}
			return null;
		}
		
		for (SBSChangeRequest ss: matches){
		//test if we have a segment in the right range and return 
			if (ss.isContainsAddress()){
				
				return ss;
			}
		}
		// Nothing with correct address? return first item or null
		if (matches.size() > 0) {
			return matches.get(0);
		}
		else {
			return null;
		}

	}

}
