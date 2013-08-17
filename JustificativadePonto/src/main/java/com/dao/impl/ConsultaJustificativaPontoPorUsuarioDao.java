package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
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
        String hql = "select j.id as id, " +
                "j.data as data, " +
                "j.dataSolicitacao as dataSolicitacao, " +
                "j.dataSolicitacaoFim as dataSolicitacaoFim, " +
                "j.solicitante.nome as nomeSolicitante, " +
                "j.motivo as motivo, " +
                "j.status as status " +
                "from JustificativaPonto j " +
                "where (j.solicitante.id = :user or " +
                "j.id in (select justificativaPonto.id from EncaminhamentoJustificativaPonto where responsavel.id = :user)) " +
                "order by j." + ordenador + " asc";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", filtro.getId())
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public long count(User user) {
        String hql = "select count(j) from JustificativaPonto j " +
                "where j.solicitante.id = :user or " +
                "j.id in (select justificativaPonto.id from EncaminhamentoJustificativaPonto where responsavel.id = :user)";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user.getId());

        return (Long)query.uniqueResult();
    }
}
