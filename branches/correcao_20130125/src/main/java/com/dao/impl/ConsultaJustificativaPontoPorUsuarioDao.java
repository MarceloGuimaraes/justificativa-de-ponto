package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaJustificativaPontoPorUsuarioDao;
import com.model.JustificativaPonto;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class ConsultaJustificativaPontoPorUsuarioDao extends Dao implements IConsultaJustificativaPontoPorUsuarioDao {

    private String ordenador;

    public ConsultaJustificativaPontoPorUsuarioDao(SessionFactory sessionFactory, String ordenador) {
        super(sessionFactory);
        this.ordenador = ordenador;
    }

    public List<JustificativaPonto> todos(int startIndex, int pageSize, User user){
        String hql = "from JustificativaPonto j left join fetch j.historico join fetch j.solicitante solic " +
                "join fetch j.coordenador coord " +
                "where solic = :user or coord = :user or j.superintendente = :user " +
                "order by j." + ordenador + " asc";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", user);

        return query.list();
    }

    @Override
    public int count(User user) {
        String hql = "select count(j) from JustificativaPonto j " +
                "where j.solicitante = :user or j.coordenador = :user or j.superintendente = :user";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user);

        return ((Long)query.uniqueResult()).intValue();
    }
}
