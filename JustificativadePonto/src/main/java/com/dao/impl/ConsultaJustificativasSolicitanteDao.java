package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class ConsultaJustificativasSolicitanteDao extends Dao implements IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> {

    protected ConsultaJustificativasSolicitanteDao(SessionFactory sessionFactory) {
        super(sessionFactory);
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
                "where j.solicitante.id = :user " +
                "order by j.solicitante.nome";

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
                "where j.solicitante = :user";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user);

        return  ((Long)query.uniqueResult()).intValue();
    }
}
