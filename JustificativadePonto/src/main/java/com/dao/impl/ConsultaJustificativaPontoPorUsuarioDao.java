package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaJustificativaPontoPorUsuarioDao;
import com.domain.dto.JustificativaPontoGrid;
import com.model.Identificacao;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class ConsultaJustificativaPontoPorUsuarioDao extends Dao implements IConsultaJustificativaPontoPorUsuarioDao {

    private String ordenador;

    public ConsultaJustificativaPontoPorUsuarioDao(SessionFactory sessionFactory, String ordenador) {
        super(sessionFactory);
        this.ordenador = ordenador;
    }

    public List<JustificativaPontoGrid> todos(int startIndex, int pageSize, User user){
        String hql = "select distinct j as justificativa from EncaminhamentoJustificativaPonto hist " +
                "join hist.justificativaPonto j " +
                "join fetch j.solicitante solic " +
                "where solic = :user or hist.responsavel = :resp " +
                "order by j." + ordenador + " asc";

        Identificacao identificacao = new Identificacao();
        identificacao.setNome(user.getNome());
        identificacao.setCpf(user.getCpf());
        identificacao.setEmail(user.getEmail());
        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", user)
                .setParameter("resp", identificacao)
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public int count(User user) {
        String hql = "select count(j) from EncaminhamentoJustificativaPonto hist " +
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