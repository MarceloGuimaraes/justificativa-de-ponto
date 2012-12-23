package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaJustificativaPontoDao;
import com.model.JustificativaPonto;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class ConsultaJustificativaPontoDao extends Dao implements IConsultaJustificativaPontoDao {

    private String ordenacao;

    public ConsultaJustificativaPontoDao(SessionFactory sessionFactory, String ordenacao) {
        super(sessionFactory);
        this.ordenacao = ordenacao;
    }

    @Override
    public List<JustificativaPonto> todos(int startIndex, int pageSize) {
        String hql = "from JustificativaPonto j left join fetch j.historico join fetch j.solicitante " +
                "join fetch j.coordenador order by j."+ordenacao+" asc";

        Query query = getSession().createQuery(hql);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.list();

    }

    @Override
    public int count() {
        String hql = "select count(j) from JustificativaPonto j";
        Query query = getSession().createQuery(hql);

        return ((Long)query.uniqueResult()).intValue();
    }
}
