package com.service;

import com.dao.IUserDAO;
import com.model.PerfilEnum;
import com.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

@Transactional(readOnly = true)
public class UserService implements IUserService,Serializable {


    private static final long serialVersionUID = 1L;

    // UserDAO is injected...
    IUserDAO userDAO;

    @Transactional(readOnly = false)
    public void addUser(User user) {
        userDAO.adicionar(user); 		// verifica se existe o ID cadastrado
    }

    @Override
    public boolean isExisteUser(User user) {
        //Verifica se existe usuario user.getNome()
        if (getUserByCpf(user) != null || getUserByEmail(user) != null)
            return true;
        else
            return false;
    }

    @Override
    public User buscaPorLogin(User user) {
        return userDAO.buscaPorLogin(user);
    }

    @Override
    public List<User> recuperaCoordenadores() {
        return userDAO.listar(EnumSet.of(PerfilEnum.COORDENADOR));
    }

    @Override
    public List<User> recuperaSuperintendentes() {
        return userDAO.listar(EnumSet.of(PerfilEnum.SUPERINTENDENTE));
    }

    @Override
    public List<User> recuperaRH() {
        return userDAO.listar(EnumSet.of(PerfilEnum.RH));
    }

    @Override
    public User recuperar(Integer id) {
        return userDAO.recuperar(id);
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        userDAO.deletar(user);
    }

    @Transactional(readOnly = false)
    public void updateUser(User user) {
        userDAO.atualizar(user);
    }

    public User getUserById(User user) {
        return userDAO.recuperar(user);
    }

    public User getUserByCpf(User user) {
        return userDAO.recuperarPorCpf(user);
    }

    public User getUserByEmail(User user) {
        return userDAO.recuperarPorEmail(user);
    }

    public List<User> getUsers() {
        return userDAO.todos();
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
