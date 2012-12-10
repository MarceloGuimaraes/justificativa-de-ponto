package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaJustificativaPontoPorUsuario;
import com.model.JustificativaPonto;
import com.model.User;
import org.hibernate.Query;

import java.util.List;

public class ConsultaJustificativaPontoPorUsuario extends Dao implements IConsultaJustificativaPontoPorUsuario {

    public List<JustificativaPonto> todosPorData(int startIndex, int pageSize, User user){
        String hql = "from JustificativaPonto j left join fetch j.historico join fetch j.solicitante solic " +
                "join fetch j.coordenador coord " +
                "where solic = :user or coord = :user or j.superintendente = :user or j.rh = :user " +
                "order by j.dtCriacao asc";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", user);

        return query.list();
    }

    @Override
    public int count(User user) {
        String hql = "select count(j) from JustificativaPonto j " +
                "where j.solicitante = :user or j.coordenador = :user or j.superintendente = :user or j.rh = :user ";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user);

        return ((Long)query.uniqueResult()).intValue();
    }
}
