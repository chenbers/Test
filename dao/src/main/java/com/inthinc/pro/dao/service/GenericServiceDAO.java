package com.inthinc.pro.dao.service;

import java.io.Serializable;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.SiloAware;

public abstract class GenericServiceDAO<T, ID> implements GenericDAO<T, ID> , Serializable, SiloAware {

    private static final long serialVersionUID = 1L;

}
