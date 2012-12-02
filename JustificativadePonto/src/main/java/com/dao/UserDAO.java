package com.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.model.Perfil;
import com.model.User;

public class UserDAO implements IUserDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addUser(User user) {
		getSessionFactory().getCurrentSession().save(user);
	}

	public void deleteUser(User user) {
		getSessionFactory().getCurrentSession().delete(user);
	}

	public void updateUser(User user) {
		getSessionFactory().getCurrentSession().update(user);
	}

	public User getUserById(User user) {
		int id = user.getUserId();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from User u where u.userId=?");
		q.setParameter(0, id).list();
		User p;
		try {
			p = (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;
	}

	public User getUserByCpf(User user) {
		
		String cpf = user.getCpf();
/*		System.out.println("NOME: " + user.getNome());
		System.out.println("CPF: " + user.getCpf());*/
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from User where cpf=?");

		q.setParameter(0, cpf).list();
		User p;
		try {
			p = (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;
	}

	public User getUserByEmail(User user) {
		
/*		System.out.println("NOME: " + user.getNome());
		System.out.println("CPF: " + user.getEmail());*/
		
		String email = user.getEmail();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from User where email=?");

		q.setParameter(0, email).list();
		User p;
		try {
			p = (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
		return p;
	}

	public Boolean buscaPorLogin(User user) {

/*		System.out.println("USER => " + user.getEmail());
		System.out.println("SENHA => " + user.getSenha());*/

		Query q = getSessionFactory().getCurrentSession().createQuery(
				"from User u where u.email = "
						+ ":pLogin and u.senha = :pSenha");
		q.setParameter("pLogin", user.getEmail());
		// q.setParameter("pSenha",
		// Criptografia.encodePassword(user.getSenha()));
		q.setParameter("pSenha", user.getSenha());

		User p;
		try {
			p = (User) q.uniqueResult();
			if (p == null) {
				return false;
			} else {
				return true;
			}
		} catch (NonUniqueResultException e) {
			return false;
		}
	}

	public List<Perfil> getPerfis(User user) {

		int idUser = user.getUserId();

		String hdl = "select u from User_perfis u where u.User_id = :iduser";
		Query query = getSessionFactory().getCurrentSession().createQuery(hdl);
		// getSessionFactory().getCurrentSession().createQuery("from justificativadeponto.user_perfis where user_id=?");
		query.setInteger("iduser", idUser).list();
		/* q.setParameter(0, id).list(); */
		try {
			return query.list();
		} catch (NonUniqueResultException e) {
			return null;
		}
	}

	public List<User> getUsers() {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from User").list();
		return Collections.unmodifiableList(list);
		// return list;
	}

    @Override
    public User recuperar(Integer id) {
        return (User) getSessionFactory().getCurrentSession().load(User.class, id);
    }
}
