package com.dao;

import com.dao.impl.CrudDaoImpl;
import com.model.JustificativaPonto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

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
		return Collections.unmodifiableList(list);
	}

    @Override
    public List<JustificativaPonto> todos(int startIndex, int pageSize, Order... orders) {
        Criteria criteria = getSession().createCriteria(getEntityClass())
                .setFirstResult(startIndex)
                .setMaxResults(pageSize);
        for(Order o : orders){
            criteria.addOrder(o);
        }

        return Collections.unmodifiableList(criteria.list());

    }

}
