package com.dao.impl;

import com.dao.Dao;
import com.dao.ICrudDao;
import org.hibernate.Hibernate;

import java.io.Serializable;

public abstract class CrudDaoImpl<T> extends Dao implements ICrudDao<T> {

    protected abstract Class<T> getEntityClass();

    @Override
    public T recuperar(Serializable id) {
        return (T) getSession().get(getEntityClass(), id);
    }

    @Override
    public Serializable adicionar(T t) {
        return getSession().save(t);
    }

    @Override
    public T atualizar(T t) {
        return (T) getSession().merge(t);
    }

    @Override
    public void deletar(T t) {
        getSession().delete(t);
    }

    @Override
    public void initialize(Object o) {
        Hibernate.initialize(o);
    }

}
