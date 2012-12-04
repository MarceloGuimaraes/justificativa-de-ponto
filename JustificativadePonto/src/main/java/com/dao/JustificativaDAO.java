package com.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.model.JustificativaPonto;

public class JustificativaDAO implements IJustificativaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addJustificativaPonto(JustificativaPonto justificativa) {
		getSessionFactory().getCurrentSession().save(justificativa);
	}

	public void updateJustificativaPonto(JustificativaPonto justificativa) {
		getSessionFactory().getCurrentSession().update(justificativa);
	}

	public void deleteJustificativaPonto(JustificativaPonto justificativa) {
		getSessionFactory().getCurrentSession().delete(justificativa);
	}

	public JustificativaPonto getJustificativaPontoById(
			JustificativaPonto justificativa) {

		int id = justificativa.getJustificativaId();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from JustificativaPonto where justificativaId=?");

		q.setParameter(0, id).list();
		JustificativaPonto p;
		try {
			p = (JustificativaPonto) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;
	}

	public List<JustificativaPonto> getJustificativaPontos() {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from JustificativaPonto j left join fetch j.historico").list();
		return Collections.unmodifiableList(list);
		// return list;

	}

}
