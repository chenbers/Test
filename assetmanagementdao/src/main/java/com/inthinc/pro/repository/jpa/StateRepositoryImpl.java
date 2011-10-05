package com.inthinc.pro.repository.jpa;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.State;
import com.inthinc.pro.repository.StateRepository;

@Repository
public class StateRepositoryImpl extends GenericRepositoryImpl<State, Integer> implements
		StateRepository {

}
