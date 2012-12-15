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
        if(user.getSenha()!=null){
            criteria.add(Property.forName("senha").eq(user.getSenha()));
        }
        if(user.getPerfil()!=null && !user.getPerfil().isEmpty()){
            criteria.add(Property.forName("perfil").in(user.getPerfil()));
        }

        return (User) criteria.uniqueResult();

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
