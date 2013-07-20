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
        String hql = "select distinct j as justificativa from EncaminhamentoJustificativaPonto hist " +
                "join hist.justificativaPonto j " +
                "join fetch j.solicitante solic " +
                "where solic = :user or hist.responsavel = :resp " +
                "order by j." + ordenador + " asc";

        Identificacao identificacao = new Identificacao();
        identificacao.setNome(filtro.getNome());
        identificacao.setCpf(filtro.getCpf());
        identificacao.setEmail(filtro.getEmail());
        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", filtro)
                .setParameter("resp", identificacao)
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public long count(User user) {
        String hql = "select count(j.id) from EncaminhamentoJustificativaPonto hist " +
                "join hist.justificativaPonto j " +
                "where j.solicitante = :user or hist.responsavel = :resp";

        Identificacao identificacao = new Identificacao();
        identificacao.setNome(user.getNome());
        identificacao.setCpf(user.getCpf());
        identificacao.setEmail(user.getEmail());

        Query query = getSession().createQuery(hql)
                .setParameter("user", user)
                .setParameter("resp", identificacao);

        return ((Long)query.uniqueResult()).intValue();
    }
}
