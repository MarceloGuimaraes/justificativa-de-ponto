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

/**
 * User: xonda
 * Date: 21/07/13
 * Time: 14:46
 */
public class ConsultaJustificativaPorAprovadorDao extends Dao implements IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> {

    protected ConsultaJustificativaPorAprovadorDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<JustificativaPontoGrid> todos(User filtro, int startIndex, int pageSize) {
        String hql = "select j as justificativa from JustificativaPonto j " +
                "join j.historico h " +
                "where h.data = (select max(hh.data) from HistoricoJustificativaPonto hh where hh.justificativaPonto = j) " +
                "and h.class = EncaminhamentoJustificativaPonto " +
                "and h.responsavel.id = :responsavel " +
                "order by j.solicitante.nome";

        final Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("responsavel", filtro.getId())
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public long count(User filtro) {
        String hql = "select count(j) from JustificativaPonto j " +
                "join j.historico h " +
                "where h.data = (select max(hh.data) from HistoricoJustificativaPonto hh where hh.justificativaPonto = j) " +
                "and h.class = EncaminhamentoJustificativaPonto " +
                "and h.responsavel.id = :responsavel";
        final Query query = getSession().createQuery(hql)
                .setParameter("responsavel", filtro.getId());

        return  (Long)query.uniqueResult();
    }
}
