package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class ConsultaJustificativaPontoDao extends Dao implements IConsultaPaginadaDao<JustificativaPontoGrid> {

    private String ordenacao;

    public ConsultaJustificativaPontoDao(SessionFactory sessionFactory, String ordenacao) {
        super(sessionFactory);
        this.ordenacao = ordenacao;
    }

    @Override
    public List<JustificativaPontoGrid> todos(int startIndex, int pageSize) {
        String hql = "select j.id as id, " +
                "j.data as data, " +
                "j.dataSolicitacao as dataSolicitacao, " +
                "j.dataSolicitacaoFim as dataSolicitacaoFim, " +
                "j.solicitante.nome as nomeSolicitante, " +
                "j.motivo as motivo, " +
                "j.status as status " +
                "from JustificativaPonto j " +
                "order by j." + ordenacao + " asc";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));
        return query.list();

    }

    @Override
    public int count() {
        String hql = "select count(j) from JustificativaPonto j";
        Query query = getSession().createQuery(hql);

        return ((Long)query.uniqueResult()).intValue();
    }
}
