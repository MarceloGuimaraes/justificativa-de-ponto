package com.dao;

import java.util.List;

import com.model.PerfilEnum;
import com.model.User;

public interface IUserDAO {

	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User getUserById(User user);
	public User getUserByCpf(User user);
	public User getUserByEmail(User user);
	public Boolean buscaPorLogin(User user);
	public List<PerfilEnum> getPerfis(User user);
	public List<User> getUsers();
    User recuperar(Integer id);
}
