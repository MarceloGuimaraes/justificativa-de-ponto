package com.service;

import java.util.List;

import com.model.PerfilEnum;
import com.model.User;

public interface IUserService {
	
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User getUserById(User user);
	public User getUserByCpf(User user);
	public User getUserByEmail(User user);
	public List<User> getUsers();
	public List<PerfilEnum> getPerfilUser(User user);
    User recuperar(Integer id);
	public abstract boolean isExiteUser(User user);
	public abstract boolean buscaPorLogin(User user);

}
