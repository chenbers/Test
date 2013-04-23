package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.report.DVIRViolationReportDAO;
import com.inthinc.pro.model.dvir.DVIRViolationReport;
import com.inthinc.pro.model.event.NoteType;

public class DVIRViolationReportJDBCDAO extends GenericJDBCDAO implements DVIRViolationReportDAO{

	/**
	 * Generated serial Version ID
	 */
	private static final long serialVersionUID = 6149304319504943377L;

	@Override
	public Collection<DVIRViolationReport> getDVIRViolationsForGroup(List<Integer> groupIDs, Interval interval) {		
		List<DVIRViolationReport> retVal = new ArrayList<DVIRViolationReport>();
		
		Connection conn = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		
		try{
			conn = this.getConnection();
			stmnt = conn.createStatement();
			
			String sqlStatement = buildViolationSQL(groupIDs, interval);
			
			resultSet = stmnt.executeQuery(sqlStatement);
			while(resultSet.next()){
				DVIRViolationReport dvirViolationReport = new DVIRViolationReport(resultSet.getDate("dateTime"), 
						resultSet.getString("driverName"), 
						resultSet.getString("vehicleName"), 
						resultSet.getString("groupName"), 
						NoteType.valueOf(resultSet.getInt("noteType")));
				
				retVal.add(dvirViolationReport);
			}
		} catch(SQLException e){
			System.out.println(e);
			
		} finally { // clean up and release the connection
	        close(resultSet);
	        close(stmnt);
	        close(conn);
	    } 
		
		return retVal;
		
		
		/* The following is used if this class extends spring SimpleJdbcDaoSupport */
		
//		return getSimpleJdbcTemplate().query(buildViolationSQL(groupIDs, interval), new ParameterizedRowMapper<DVIRViolationReport>() {
//
//			@Override
//			public DVIRViolationReport mapRow(ResultSet rs, int arg1) throws SQLException {
//				return new DVIRViolationReport(rs.getDate("dateTime"), 
//						rs.getString("driverName"), 
//						rs.getString("vehicleName"), 
//						rs.getString("groupName"), 
//						NoteType.valueOf(rs.getInt("noteType")));
//			}
//		});
	}
	
	public static String buildViolationSQL(List<Integer> groupIDs, Interval interval) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT note.time as 'dateTime', CONCAT(p.first, ' ', p.middle, ' ', p.last) as 'driverName', v.name as 'vehicleName', g.name as 'groupName', note.type as 'noteType'")
		.append(" FROM ")
		.append("( ");
		
		// Create iterator for unions of the note tables found in the schema.
		for(int i = 0; i < 32; i++){
			
			sb.append("SELECT deviceID, driverID, vehicleId, groupID, time, type")
			.append(" FROM ");
			
			// lil' logic here to increment the note table name.
			StringBuilder noteTableName = new StringBuilder("note");
			if(i<10){
				noteTableName.append("0" + String.valueOf(i));
			} else {
				noteTableName.append(String.valueOf(i));
			}
			
			sb.append(noteTableName)
			 // Build Clause for the noteTypes to include
			.append(" WHERE ")
			.append("type IN ( ")
			.append(String.valueOf(NoteType.DVIR_DRIVEN_UNSAFE.getCode())).append(", ")
			.append(String.valueOf(NoteType.DVIR_DRIVEN_NOPREINSPEC.getCode())).append(", ")
			.append(String.valueOf(NoteType.DVIR_DRIVEN_NOPOSTINSPEC.getCode()))
			.append(" ) ");
			
			// Build Clause for group IDS
			if(groupIDs != null && groupIDs.size() > 0)
			{
				sb.append("AND ")
				.append("groupID IN ( ");
				for(Integer id : groupIDs)
				{
					sb.append(String.valueOf(id)).append(",");
				}
				sb.deleteCharAt(sb.length() -1)// remove extra comma
				.append(" ) "); 
			}
			
			// Build between clause for interval
			if(interval != null){
				sb.append("AND ")
				.append("time BETWEEN ")
				.append("'")
				.append(interval.getStart())
				.append("'")
				.append(" AND ")
				.append("'")
				.append(interval.getEnd())
				.append("'");
			}
			
			// Union this bad boy for all the note tables in the schema.
			if(i < 31){
				sb.append(" UNION ");
			}
			
		}
		
		sb.append(" ) AS note ")
		.append("left join driver d on d.driverID = note.driverID ")
		.append("left join vehicle v on v.vehicleID = note.vehicleID ")
		.append("left join groups g on g.groupID = note.groupID ")
		.append("left join person p on p.personID = d.personID ");
		
		// Add the order by clause
		sb.append("order by noteType, dateTime");
		
		return sb.toString();
	}

}
