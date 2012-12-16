package com.dao.impl;

import com.dao.IJustificativaDAO;
import com.dao.impl.CrudDaoImpl;
import com.model.JustificativaPonto;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class JustificativaDAO extends CrudDaoImpl<JustificativaPonto> implements IJustificativaDAO, Serializable {

	private static final long serialVersionUID = 1L;

    protected JustificativaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<JustificativaPonto> getEntityClass() {
        return JustificativaPonto.class;
    }

    @Override
	public JustificativaPonto recuperar(
            JustificativaPonto justificativa) {

		return recuperar(justificativa.getJustificativaId());
	}

}
