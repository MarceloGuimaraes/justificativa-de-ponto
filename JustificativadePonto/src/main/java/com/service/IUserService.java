package com.service;

import java.util.List;

import com.model.User;

public interface IUserService {
	
	void addUser(User user);
	void updateUser(User user);
	void deleteUser(User user);
	User getUserById(User user);
	User getUserByCpf(User user);
	User getUserByEmail(User user);
	List<User> getUsers();
    User recuperar(Integer id);
	boolean isExisteUser(User user);
	User buscaPorLogin(User user);
    List<User> recuperaCoordenadores();
    List<User> recuperaSuperintendentes();
    List<User> recuperaRH();
}
