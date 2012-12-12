package com.dao.impl;

import com.dao.IUserDAO;
import com.model.PerfilEnum;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.criterion.Property;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class UserDAO extends CrudDaoImpl<User> implements IUserDAO, Serializable {

	private static final long serialVersionUID = 1L;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

	public User recuperar(User user) {
		return recuperar(user.getId());
	}

	public User recuperarPorCpf(User user) {
		
		String cpf = user.getCpf();
		Query q = getSession().createQuery(
				"from User where cpf=:cpf");

		q.setParameter("cpf", cpf);

		try {
			return (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			throw new IllegalArgumentException("CPF informado retornou mais de 1 resultado");
		}

	}

    public User encontraPorAmostra(User user){
        Criteria criteria = getSession().createCriteria(User.class);
        if(user.getNome()!=null){
            criteria.add(Property.forName("nome").eq(user.getNome()));
        }
        if(user.getCpf()!=null){
            criteria.add(Property.forName("cpf").eq(user.getCpf()));
        }
        if(user.getEmail()!=null){
            criteria.add(Property.forName("email").eq(user.getEmail()));
        }
        if(user.getPerfil()!=null && !user.getPerfil().isEmpty()){
            criteria.add(Property.forName("perfil").in(user.getPerfil()));
        }

        return (User) criteria.uniqueResult();

    }

	public User recuperarPorEmail(User user) {
		
		String email = user.getEmail();

		Query q = getSession().createQuery(
				"from User where email=:email");

		q.setParameter("email", email);

		try {
			return (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			throw new IllegalArgumentException("Email informado retornou mais de 1 resultado");
		}

	}

	public User buscaPorLogin(User user) {

		Query q = getSession().createQuery(
                "from User u where u.email = :pLogin and u.senha = :pSenha");

		q.setParameter("pLogin", user.getEmail());
		q.setParameter("pSenha", user.getSenha());

		try {
            return (User) q.uniqueResult();
		} catch (NonUniqueResultException e) {
			throw new RuntimeException("Ocorreu um erro ao tentar encontrar o usuário");
		}

	}

	public List<User> todos() {
		List list = getSession().createQuery("from User").list();
		return Collections.unmodifiableList(list);
	}

    @Override
    public List<User> listar(EnumSet<PerfilEnum> perfis) {
        String hql = "from User u join u.perfil p where p in :perfil";
        Query query = getSession().createQuery(hql);
        query.setParameterList("perfil", perfis);
        return query.list();
    }

}
