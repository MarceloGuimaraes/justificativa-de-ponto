package com.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.model.Perfil;
public class PerfilDAO implements IPerfilDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addPerfil(Perfil perfil) {
		getSessionFactory().getCurrentSession().save(perfil);
	}

	public void deletePerfil(Perfil perfil) {
		getSessionFactory().getCurrentSession().delete(perfil);
	}

	public void updatePerfil(Perfil perfil) {
		getSessionFactory().getCurrentSession().update(perfil);
	}

	public Perfil getPerfilById(Perfil perfil) {
		int id = perfil.getPerfilId();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from Perfil where id=?");

		q.setParameter(0, id).list();
		Perfil p;
		try {
			p = (Perfil) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;
	}

	public Perfil getPerfilByTipo(Perfil perfil) {
		String tipo = perfil.getTipo();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from Perfil where tipo=?");
		q.setParameter(0, tipo).list();
		Perfil p;
		try {
			p = (Perfil) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;

	} 

	public List<Perfil> getPerfils() {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from Perfil").list();

		return Collections.unmodifiableList(list);
		// return list;
	}
 
	@SuppressWarnings("unchecked")
	public List<Perfil> getTiposPerfil() {
		System.out.println("getTiposPerfil = 1");
		List<Perfil> listResult = getSessionFactory().getCurrentSession().createQuery("tipo from Perfil").list();
		System.out.println("getTiposPerfil = 2");	
		return listResult;
	}

}
