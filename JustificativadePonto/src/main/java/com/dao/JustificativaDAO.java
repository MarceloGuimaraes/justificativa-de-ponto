package com.dao;

import com.dao.impl.CrudDaoImpl;
import com.model.JustificativaPonto;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class JustificativaDAO extends CrudDaoImpl<JustificativaPonto> implements IJustificativaDAO, Serializable {

	private static final long serialVersionUID = 1L;

    @Override
    protected Class<JustificativaPonto> getEntityClass() {
        return JustificativaPonto.class;
    }

    @Override
	public JustificativaPonto recuperar(
            JustificativaPonto justificativa) {

		return recuperar(justificativa.getJustificativaId());
	}

    @Override
	public List<JustificativaPonto> todos() {
		List list = getSession()
				.createQuery("from JustificativaPonto j left join fetch j.historico").list();
		return list;
	}

    @Override
    public List<JustificativaPonto> todos(int startIndex, int pageSize) {
        String hql = "from JustificativaPonto j left join fetch j.historico join fetch j.solicitante " +
                "join fetch j.coordenador order by j.dtCriacao asc";

        Query query = getSession().createQuery(hql);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        List<JustificativaPonto> resultado = query.list();

        return resultado;

    }

    @Override
    public int count() {
        return ((Long)getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

}
