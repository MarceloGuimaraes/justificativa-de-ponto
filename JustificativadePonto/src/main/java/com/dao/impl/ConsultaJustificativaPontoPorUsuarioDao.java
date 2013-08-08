package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import com.model.Identificacao;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class ConsultaJustificativaPontoPorUsuarioDao extends Dao implements IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> {

    private String ordenador;

    public ConsultaJustificativaPontoPorUsuarioDao(SessionFactory sessionFactory, String ordenador) {
        super(sessionFactory);
        this.ordenador = ordenador;
    }

    @Override
    public List<JustificativaPontoGrid> todos(User filtro, int startIndex, int pageSize) {
        String hql = "select distinct j as justificativa from JustificativaPonto j " +
                "join fetch j.solicitante solic " +
                "where solic = :user or " +
                "j.id in (select justificativaPonto.id from EncaminhamentoJustificativaPonto where responsavel.id = :resp) " +
                "order by j." + ordenador + " asc";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", filtro)
                .setParameter("resp", filtro.getId())
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public long count(User user) {
        String hql = "select count(j) from JustificativaPonto j " +
                "where j.solicitante = :user or " +
                "j.id in (select justificativaPonto.id from EncaminhamentoJustificativaPonto where responsavel.id = :resp)";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user)
                .setParameter("resp", user.getId());

        return (Long)query.uniqueResult();
    }
}
