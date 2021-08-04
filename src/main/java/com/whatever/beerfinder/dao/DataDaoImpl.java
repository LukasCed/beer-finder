package com.whatever.beerfinder.dao;

import com.whatever.beerfinder.dao.interfaces.DataDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class DataDaoImpl<T> implements DataDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DataDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public List<T> list() {
        return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
    }

}
