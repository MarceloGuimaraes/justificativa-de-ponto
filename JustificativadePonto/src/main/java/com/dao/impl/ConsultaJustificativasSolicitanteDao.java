package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaJustificativaPontoPorUsuarioDao;
import com.domain.dto.JustificativaPontoGrid;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class ConsultaJustificativasSolicitanteDao extends Dao implements IConsultaJustificativaPontoPorUsuarioDao {

    protected ConsultaJustificativasSolicitanteDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<JustificativaPontoGrid> todos(int startIndex, int pageSize, User user) {
        String hql = "select j as justificativa from JustificativaPonto j " +
                "where j.solicitante = :user order by j.solicitante.nome";

        Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("user", user)
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoGrid.class));

        return query.list();
    }

    @Override
    public int count(User user) {
        String hql = "select count(j) from JustificativaPonto j " +
                "where j.solicitante = :user";

        Query query = getSession().createQuery(hql)
                .setParameter("user", user);

        return  ((Long)query.uniqueResult()).intValue();
    }
}
