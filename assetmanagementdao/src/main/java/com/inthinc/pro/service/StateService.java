package com.inthinc.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.repository.StateRepository;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;
}
