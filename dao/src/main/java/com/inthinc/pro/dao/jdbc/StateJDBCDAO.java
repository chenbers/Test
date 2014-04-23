package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.StateDAO;
import com.inthinc.pro.model.State;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateJDBCDAO extends SimpleJdbcDaoSupport implements StateDAO {

    private static final String GET_STATE = "SELECT * FROM state s";
    private static final String FIND_STATE_BY_ID = GET_STATE + " where s.stateID=:stateID";

    private ParameterizedRowMapper<State> stateParameterizedRowMapper;

    {
        stateParameterizedRowMapper = new ParameterizedRowMapper<State>() {
            @Override
            public State mapRow(ResultSet rs, int rowNum) throws SQLException {

                State stateItem = new State();

                stateItem.setStateID(rs.getInt("s.stateID"));
                stateItem.setAbbrev(rs.getString("s.abbrev"));
                stateItem.setName(rs.getString("s.name"));

                return stateItem;

            }
        };
    }

    public List<State> getStates() {
        Map<String, Object> params = new HashMap<String, Object>();

        List<State> states = getSimpleJdbcTemplate().query(GET_STATE, stateParameterizedRowMapper, params);

        return states;
    }


    public State findByID(Integer stateID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("stateID", stateID);
        StringBuilder findState = new StringBuilder(FIND_STATE_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findState.toString(), stateParameterizedRowMapper, args);

    }


    public Integer create(Integer integer, State entity) {
        throw new NotImplementedException();
    }

    public Integer update(State entity) {
        throw new NotImplementedException();
    }


    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}
