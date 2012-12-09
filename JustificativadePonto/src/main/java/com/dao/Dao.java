package com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class Dao {
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
