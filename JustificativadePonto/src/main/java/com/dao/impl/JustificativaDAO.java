package com.dao.impl;

import com.dao.IJustificativaDAO;
import com.model.JustificativaPonto;
import org.hibernate.SessionFactory;

import java.io.Serializable;

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

		return recuperar(justificativa.getId());
	}

}
