package com.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IUserDAO;
import com.model.Perfil;
import com.model.User;

@Transactional(readOnly = true)
public class UserService implements IUserService,Serializable {

	
	private static final long serialVersionUID = 1L;
	
	// UserDAO is injected...
	IUserDAO userDAO;

	@Transactional(readOnly = false)
	public void addUser(User user) {
		getUserDAO().addUser(user); 		// verifica se existe o ID cadastrado
	}

	@Override
	public boolean isExiteUser(User user) {
/*		System.out.println("Verifica se existe usuário: " + user.getNome());*/
		if (getUserByCpf(user) != null || getUserByEmail(user) != null)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean buscaPorLogin(User user) {
		return getUserDAO().buscaPorLogin(user);
	}	
	
	@Override
	public List<Perfil> getPerfilUser(User user) {
		return getUserDAO().getPerfis(user);
	}	

	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		getUserDAO().deleteUser(user);
	}

	@Transactional(readOnly = false)
	public void updateUser(User user) {
		getUserDAO().updateUser(user);
	}

	public User getUserById(User user) {
		return getUserDAO().getUserById(user);
	}
	
	public User getUserByCpf(User user) {
		return getUserDAO().getUserByCpf(user);
	}

	public User getUserByEmail(User user) {
		return getUserDAO().getUserByEmail(user);
	}
	
	public List<User> getUsers() {
		return getUserDAO().getUsers();
	}


	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
